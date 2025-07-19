package com.rk.xed_editor_plugin_demo

import okhttp3.Call
import okhttp3.Callback
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.Response
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.ArgumentCaptor
import org.mockito.Mockito.any
import org.mockito.Mockito.doAnswer
import org.mockito.Mockito.mock
import org.mockito.Mockito.`when`
import org.mockito.junit.MockitoJUnitRunner
import java.util.concurrent.CountDownLatch
import org.junit.Assert.assertEquals

@RunWith(MockitoJUnitRunner::class)
class AIAssistantTest {

    @Test
    fun testGetCompletion() {
        val client = mock(OkHttpClient::class.java)
        val call = mock(Call::class.java)
        val response = mock(Response::class.java)
        val requestCaptor = ArgumentCaptor.forClass(Request::class.java)
        val latch = CountDownLatch(1)
        var result = ""

        `when`(client.newCall(requestCaptor.capture())).thenReturn(call)
        doAnswer {
            val callback = it.getArgument<Callback>(0)
            `when`(response.isSuccessful).thenReturn(true)
            `when`(response.body?.string()).thenReturn("""
                {
                    "choices": [
                        {
                            "message": {
                                "content": "This is a test response."
                            }
                        }
                    ]
                }
            """.trimIndent())
            callback.onResponse(call, response)
            latch.countDown()
        }.`when`(call).enqueue(any())

        AIAssistant.getCompletion("test prompt", "test_api_key", "test_model") {
            result = it
        }

        latch.await()

        assertEquals(
            """
                {
                    "choices": [
                        {
                            "message": {
                                "content": "This is a test response."
                            }
                        }
                    ]
                }
            """.trimIndent(),
            result
        )
    }
}
