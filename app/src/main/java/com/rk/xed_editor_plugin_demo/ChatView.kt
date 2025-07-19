package com.rk.xed_editor_plugin_demo

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.rk.xededitor.settings.Settings
import org.json.JSONObject

data class ChatMessage(val message: String, val isUser: Boolean)

@Composable
fun ChatView() {
    var message by remember { mutableStateOf("") }
    val conversation = remember { mutableStateListOf<ChatMessage>() }

    Column(modifier = Modifier.fillMaxSize()) {
        LazyColumn(
            modifier = Modifier.weight(1f)
        ) {
            items(conversation) { chatMessage ->
                Text(
                    text = chatMessage.message,
                    modifier = Modifier.padding(8.dp)
                )
            }
        }

        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            TextField(
                value = message,
                onValueChange = { message = it },
                modifier = Modifier.weight(1f),
                placeholder = { Text("Type a message...") }
            )
            Button(
                onClick = {
                    val userMessage = message
                    conversation.add(ChatMessage(userMessage, true))
                    message = ""

                    val apiKey = Settings.get("api_key", "")
                    val model = Settings.get("ai_model", "gpt-3.5-turbo")

                    AIAssistant.getCompletion(userMessage, apiKey, model) { response ->
                        val aiMessage = try {
                            val jsonObject = JSONObject(response)
                            jsonObject.getJSONArray("choices").getJSONObject(0).getJSONObject("message").getString("content")
                        } catch (e: Exception) {
                            "Error parsing response"
                        }
                        conversation.add(ChatMessage(aiMessage, false))
                    }
                },
                modifier = Modifier.padding(start = 8.dp)
            ) {
                Text("Send")
            }
        }
    }
}
