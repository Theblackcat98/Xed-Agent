package com.rk.xed_editor_plugin_demo

import okhttp3.*
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.RequestBody.Companion.toRequestBody
import java.io.IOException

object AIAssistant {

    private val client = OkHttpClient()

    fun getCompletion(code: String, apiKey: String, model: String, callback: (String) -> Unit) {
        val json = """
            {
                "model": "$model",
                "messages": [
                    {
                        "role": "user",
                        "content": "$code"
                    }
                ]
            }
        """.trimIndent()

        val requestBody = json.toRequestBody("application/json".toMediaType())

        val request = Request.Builder()
            .url("https://api.openai.com/v1/chat/completions")
            .header("Authorization", "Bearer $apiKey")
            .post(requestBody)
            .build()

        client.newCall(request).enqueue(object : Callback {
            override fun onFailure(call: Call, e: IOException) {
                e.printStackTrace()
            }

            override fun onResponse(call: Call, response: Response) {
                if (response.isSuccessful) {
                    val responseBody = response.body?.string()
                    callback(responseBody ?: "")
                }
            }
        })
    }
}
