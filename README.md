# My App WebView

A native Android app (Kotlin + Jetpack Compose) that loads
https://suman0405.pythonanywhere.com inside a WebView.

## Features
- Loads the target URL immediately on launch.
- JavaScript enabled in the WebView.
- All link clicks/navigation stay inside the app (no hand-off to Chrome).
- INTERNET permission added to the manifest.
- Material 3 top bar titled "My App Web View" with a refresh icon button.

## How to open
1. Open this folder in Android Studio (Koala/Ladybug or newer recommended).
2. Let Android Studio sync Gradle — if the wrapper jar is missing, use
   **File > Sync Project with Gradle Files**, or run
   `gradle wrapper --gradle-version 8.7` once from a terminal with
   internet access to regenerate `gradle/wrapper/gradle-wrapper.jar`
   and the `gradlew` / `gradlew.bat` scripts.
3. Run the `app` configuration on an emulator or device (minSdk 24+).

## Key files
- `app/src/main/AndroidManifest.xml` — INTERNET permission.
- `app/src/main/java/com/example/mywebviewapp/MainActivity.kt` — Compose UI,
  Scaffold + top bar + WebView wired up via `AndroidView`.
