package com.rk.xed_editor_plugin_demo

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun GenerateCodeDialog(onGenerate: (String) -> Unit, onDismiss: () -> Unit) {
    var prompt by remember { mutableStateOf("") }

    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text(text = "Generate Code") },
        text = {
            Column {
                Text(text = "Enter a prompt to generate code:")
                TextField(
                    value = prompt,
                    onValueChange = { prompt = it },
                    modifier = Modifier.fillMaxWidth()
                )
            }
        },
        confirmButton = {
            Row {
                Button(
                    onClick = { onGenerate(prompt) },
                    modifier = Modifier.padding(end = 8.dp)
                ) {
                    Text(text = "Generate")
                }
                Button(onClick = onDismiss) {
                    Text(text = "Cancel")
                }
            }
        }
    )
}
