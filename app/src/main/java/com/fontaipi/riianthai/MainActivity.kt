package com.fontaipi.riianthai

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.SystemBarStyle
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Surface
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import com.fontaipi.riianthai.data.database.dao.ConsonantDao
import com.fontaipi.riianthai.data.database.dao.WordDao
import com.fontaipi.riianthai.data.database.entity.ConsonantWordsCrossRef
import com.fontaipi.riianthai.model.Consonant
import com.fontaipi.riianthai.model.asEntity
import com.fontaipi.riianthai.ui.theme.RiianThaiTheme
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.serialization.json.Json
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    @Inject
    lateinit var consonantDao: ConsonantDao

    @Inject
    lateinit var wordDao: WordDao

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        CoroutineScope(Dispatchers.IO).launch {
            val jsonString = assets.open("consonants.json").readBytes().decodeToString()
            val consonants = Json.decodeFromString<List<Consonant>>(jsonString)
            if (consonantDao.countConsonants() == 0) {
                consonantDao.upsertConsonants(consonants.map { it.asEntity() })
            }
            if (wordDao.countWords() == 0) {
                wordDao.upsertWords(consonants.flatMap { it.exampleWords }.map { it.asEntity() })
                consonantDao.insertOrIgnoreWordCrossRefEntities(
                    consonants.flatMap { consonant ->
                        consonant.exampleWords.map { word ->
                            ConsonantWordsCrossRef(
                                consonantId = consonant.id,
                                wordId = word.id
                            )
                        }
                    }
                )
            }
        }

        setContent {
            LaunchedEffect(Unit) {
                enableEdgeToEdge(
                    statusBarStyle = SystemBarStyle.auto(
                        android.graphics.Color.TRANSPARENT,
                        android.graphics.Color.TRANSPARENT,
                    )
                )
            }

            RiianThaiTheme {
                Surface(
                    modifier = Modifier.fillMaxSize()
                ) {
                    RiianThaiApp()
                }
            }
        }
    }
}