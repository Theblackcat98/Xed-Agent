package com.rk.xed_editor_plugin_demo

import okhttp3.Call
import okhttp3.Callback
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.RequestBody.Companion.toRequestBody
import okhttp3.Response
import org.json.JSONObject
import java.io.IOException

object AIAssistant {
    fun getCompletion(prompt: String, apiKey: String, model: String, onResponse: (String) -> Unit) {
        val client = OkHttpClient()

        val json = """
            {
                "model": "$model",
                "messages": [
                    {
                        "role": "user",
                        "content": "$prompt"
                    }
                ]
            }
        """.trimIndent()

        val request = Request.Builder()
            .url("https://api.openai.com/v1/chat/completions")
            .header("Authorization", "Bearer $apiKey")
            .post(json.toRequestBody("application/json".toMediaType()))
            .build()

        client.newCall(request).enqueue(object : Callback {
            override fun onFailure(call: Call, e: IOException) {
                onResponse("Error: ${e.message}")
            }

            override fun onResponse(call: Call, response: Response) {
                val responseBody = response.body?.string()
                if (response.isSuccessful && responseBody != null) {
                    onResponse(responseBody)
                } else {
                    onResponse("Error: ${response.message}")
                }
            }
        })
    }
}
