package com.rk.xed_editor_plugin_demo

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.rk.xededitor.settings.Settings
import org.json.JSONObject

@Composable
fun ChatView() {
    var message by remember { mutableStateOf("") }
    val chatHistory = remember { mutableStateOf(listOf<String>()) }

    Column(modifier = Modifier.padding(16.dp)) {
        LazyColumn(modifier = Modifier.weight(1f)) {
            items(chatHistory.value) { chat ->
                Text(text = chat)
            }
        }
        Row(modifier = Modifier.fillMaxWidth()) {
            TextField(
                value = message,
                onValueChange = { message = it },
                modifier = Modifier.weight(1f)
            )
            Button(onClick = {
                val apiKey = Settings.get("api_key", "")
                if (apiKey.isEmpty()) {
                    chatHistory.value = chatHistory.value + "Please set your API key in the settings"
                    return@Button
                }

                val model = Settings.get("ai_model", "gpt-3.5-turbo")
                chatHistory.value = chatHistory.value + "You: $message"
                AIAssistant.getCompletion(message, apiKey, model) { response ->
                    val aiMessage = try {
                        val jsonObject = JSONObject(response)
                        jsonObject.getJSONArray("choices").getJSONObject(0).getJSONObject("message").getString("content")
                    } catch (e: Exception) {
                        "Error parsing response"
                    }
                    chatHistory.value = chatHistory.value + "AI: $aiMessage"
                }
                message = ""
            }) {
                Text(text = "Send")
            }
        }
    }
}
