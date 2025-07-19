# AI Plugin for Xed Editor

This plugin provides AI-powered features for the Xed text editor, including:

*   **Intelligent Code Completion:** Get context-aware code completions that go beyond simple token matching.
*   **Code Generation:** Generate code from natural language descriptions.
*   **Code Refactoring:** Get intelligent refactoring suggestions to improve your code.
*   **Inline Chat and Q&A:** Ask coding-related questions and get answers from the AI without leaving the editor.

## Installation

1.  Download the latest release from the [releases page](https://github.com/Xed-Editor/pluginTemplate/releases).
2.  Open the Xed editor and go to `Settings > Plugins`.
3.  Click the "Install" button and select the downloaded `.apk` file.

## Usage

Once the plugin is installed, you will see four new buttons in the editor's control panel:

*   **AI:** Select a piece of code and click this button to get a completion from the AI.
*   **Generate Code:** Click this button to open a dialog where you can enter a natural language prompt to generate code.
*   **Refactor:** Select a piece of code and click this button to get refactoring suggestions from the AI.
*   **Chat:** Click this button to open a chat view where you can ask coding-related questions.

## Configuration

To use the AI-powered features, you need to configure your API key and the AI model.

1.  Go to `Settings > AI Plugin Settings`.
2.  Enter your API key in the "API Key" field.
3.  Enter the name of the AI model you want to use in the "AI Model" field. The default model is `gpt-3.5-turbo`.

## Building from Source

To build the plugin from source, you will need to have the Android SDK installed. You will also need to create a `local.properties` file in the `app` directory with the following content:

```
sdk.dir=/path/to/your/android/sdk
```

Then, you can build the plugin by running the following command:

```
./gradlew assembleRelease
```

The unsigned APK will be located in the `app/build/outputs/apk/release` directory.
