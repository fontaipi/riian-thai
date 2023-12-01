package com.fontaipi.riianthai.ui.page.settings.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp

@Composable
fun SettingRow(
    icon: @Composable () -> Unit,
    title: String,
    subTitle: String? = null,
    onClick: () -> Unit,
    contentColor: Color = MaterialTheme.colorScheme.onSurface,
    content: @Composable (() -> Unit)? = null,
) {
    Surface(
        onClick = onClick,
        contentColor = contentColor,
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(64.dp)
                .padding(horizontal = 16.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(24.dp)
        ) {
            icon()
            Column {
                Text(text = title, style = MaterialTheme.typography.titleMedium)
                subTitle?.let {
                    Text(
                        text = it,
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.outline,
                        overflow = TextOverflow.Ellipsis,
                    )
                }
            }
            if (content != null) {
                Spacer(modifier = Modifier.weight(1f))
                content()
            } else {
                Spacer(modifier = Modifier.weight(1f))
            }
        }
    }

}