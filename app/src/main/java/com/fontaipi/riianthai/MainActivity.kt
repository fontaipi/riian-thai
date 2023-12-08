package com.fontaipi.riianthai

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import com.fontaipi.riianthai.data.database.dao.ConsonantDao
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

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        CoroutineScope(Dispatchers.IO).launch {
            val jsonString = assets.open("consonants.json").readBytes().decodeToString()
            val consonants = Json.decodeFromString<List<Consonant>>(jsonString)
            if (consonantDao.countConsonants() == 0) {
                consonantDao.upsertConsonants(consonants.map { it.asEntity() })
            }
        }
        setContent {
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