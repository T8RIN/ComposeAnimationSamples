package com.t8rin.animation.samples.ui.components

import android.content.ClipData
import android.content.ClipboardManager
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.selection.SelectionContainer
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.content.ContextCompat
import com.t8rin.animation.samples.ui.components.model.AnimationType

@Composable
fun CodeExample(
    animationType: AnimationType
) {
    Surface(
        color = MaterialTheme.colorScheme.surfaceVariant,
        shape = RoundedCornerShape(16.dp),
        modifier = Modifier.fillMaxWidth()
    ) {
        Column(
            horizontalAlignment = Alignment.End
        ) {
            SelectionContainer {
                Text(
                    text = animationType.code,
                    modifier = Modifier.padding(16.dp),
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                    fontFamily = FontFamily.Monospace,
                    fontSize = 14.sp
                )
            }
            val clipboardManager = ContextCompat.getSystemService(
                LocalContext.current,
                ClipboardManager::class.java
            )
            Button(
                modifier = Modifier.padding(8.dp),
                onClick = {
                    clipboardManager?.setPrimaryClip(
                        ClipData.newPlainText("code", animationType.code)
                    )
                }
            ) {
                Text("Скопировать")
            }
        }
    }
}