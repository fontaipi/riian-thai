package com.fontaipi.riianthai.ui.page.consonant.detail.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.VolumeUp
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.fontaipi.riianthai.ui.theme.RiianThaiTheme

@Composable
fun Tag(
    modifier: Modifier = Modifier,
    icon: ImageVector? = null,
    text: String,
    color: Color = MaterialTheme.colorScheme.surfaceVariant,
) {
    Surface(
        shape = CircleShape,
        color = color,
    ) {
        Row(
            modifier = modifier
                .padding(horizontal = 16.dp, vertical = 8.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            icon?.let {
                Icon(imageVector = it, contentDescription = it.name)
            }
            Text(
                text = text,
                style = MaterialTheme.typography.bodyLarge.copy(fontWeight = FontWeight.Medium)
            )
        }
    }
}

@Preview
@Composable
private fun IconTagPreview() {
    RiianThaiTheme {
        Tag(
            icon = Icons.Rounded.VolumeUp,
            text = "Silent",
        )
    }
}