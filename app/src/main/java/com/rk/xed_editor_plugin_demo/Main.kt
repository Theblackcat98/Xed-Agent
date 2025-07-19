package com.rk.xed_editor_plugin_demo

import android.graphics.drawable.PaintDrawable
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Build
import androidx.compose.material3.Icon
import com.rk.components.compose.preferences.base.PreferenceGroup
import com.rk.components.compose.preferences.base.PreferenceLayout
import com.rk.controlpanel.ControlItem
import com.rk.extension.ExtensionAPI
import com.rk.extension.Hooks
import com.rk.extension.SettingsScreen
import com.rk.xededitor.MainActivity.MainActivity
import com.rk.xededitor.ui.components.SettingsToggle
import com.rk.xededitor.ui.screens.settings.feature_toggles.Feature
import com.rk.xededitor.Editor.Editor
import android.widget.Toast
import com.rk.xededitor.settings.Settings
import com.rk.xededitor.ui.components.SimpleEditText
import com.rk.xededitor.ui.components.rememberMutableState
import org.json.JSONObject

class Main : ExtensionAPI() {
    override fun onPluginLoaded() {
        addAiButton()
        addChatButton()
    }

    private fun addAiButton() {
        val button = ControlItem(
            name = "AI",
            icon = Icons.Default.Build,
            onClick = {
                val apiKey = Settings.get("api_key", "")
                if (apiKey.isEmpty()) {
                    Toast.makeText(MainActivity.context, "Please set your API key in the settings", Toast.LENGTH_SHORT).show()
                    return@ControlItem
                }

                val editor = Editor.getEditor()
                val selectedText = editor.selectedText
                if (selectedText.isNotEmpty()) {
                    val model = Settings.get("ai_model", "gpt-3.5-turbo")
                    AIAssistant.getCompletion(selectedText, apiKey, model) { response ->
                        val message = try {
                            val jsonObject = JSONObject(response)
                            jsonObject.getJSONArray("choices").getJSONObject(0).getJSONObject("message").getString("content")
                        } catch (e: Exception) {
                            "Error parsing response"
                        }

                        MainActivity.context.runOnUiThread {
                            editor.replaceSelection(message)
                        }
                    }
                }
            }
        )
        Hooks.add_control_item(button)
    }

    private fun addChatButton() {
        val button = ControlItem(
            name = "Chat",
            icon = Icons.Default.Build,
            onClick = {
                openChatView()
            }
        )
        Hooks.add_control_item(button)
    }

    private fun openChatView() {
        // This is a guess, as I don't have the SDK documentation
        try {
            Hooks.add_composable("Chat", Icons.Default.Build) {
                ChatView()
            }
        } catch (e: Exception) {
            Toast.makeText(MainActivity.context, "Error opening chat view", Toast.LENGTH_SHORT).show()
        }
    }

    override fun onAppCreated() {
       
    }

    override fun onAppLaunched() {
       
    }

    override fun onAppPaused() {
       
    }

    override fun onAppResumed() {
       
    }

    override fun onAppDestroyed() {
       
    }

    override fun onLowMemory() {
       
    }

    override fun getSettingsScreen(): SettingsScreen {
        return SettingsScreen(
            "AI Plugin Settings",
            "Configure the AI plugin",
            Icons.Default.Build
        ) {
            val apiKey by rememberMutableState(
                key = "api_key",
                default = ""
            )
            SimpleEditText(
                value = apiKey,
                onValueChange = { Settings.put("api_key", it) },
                label = "API Key",
                placeholder = "Enter your API key here"
            )

            val model by rememberMutableState(
                key = "ai_model",
                default = "gpt-3.5-turbo"
            )
            SimpleEditText(
                value = model,
                onValueChange = { Settings.put("ai_model", it) },
                label = "AI Model",
                placeholder = "Enter the AI model name"
            )
        }
    }
}
