# EasyWidgets

EasyWidgets is a Compose Multiplatform application designed to showcase and demonstrate various Jetpack Compose UI widgets across different platforms (Android, iOS, and potentially desktop and web).

## Features

- Browse widgets categorized by type (Basic, Layout, Input, Container)
- View detailed information about each widget
- See code snippets with syntax highlighting
- Interactive previews of widgets
- Favorite widgets for quick access
- Search functionality to find specific widgets

## Setup Instructions

### Prerequisites

- JDK 17 or later
- Android Studio or IntelliJ IDEA with Kotlin Multiplatform plugin
- Xcode (for iOS development on macOS)

### Running the application

#### Android

1. Open the project in Android Studio or IntelliJ IDEA.
2. Select the Android configuration from the run configurations dropdown.
3. Click Run button or press Shift+F10.

#### iOS (requires macOS)

1. Open the project in Android Studio or IntelliJ IDEA.
2. Select the iOS configuration from the run configurations dropdown.
3. Click Run button or press Shift+F10.

## Project Structure

- `composeApp/` - Shared multiplatform code
  - `src/commonMain/` - Code shared across all platforms
  - `src/androidMain/` - Android-specific code
  - `src/iosMain/` - iOS-specific code

## Architecture

The app follows MVVM architecture:

- **Models**: Data classes and repositories in `data` package
- **ViewModels**: Business logic in `viewmodel` package
- **Views**: UI components in `ui` package

## Widget Categories

- **Basic**: Text, Button, Image, Icon
- **Layout**: Column, Row, Box
- **Input**: TextField, Checkbox, Switch
- **Container**: Card, Surface

## License

This project is available under the MIT license.

## Contributing

Contributions are welcome! Feel free to submit issues or pull requests.

This is a Kotlin Multiplatform project targeting Android, iOS.

* `/composeApp` is for code that will be shared across your Compose Multiplatform applications.
  It contains several subfolders:
  - `commonMain` is for code that's common for all targets.
  - Other folders are for Kotlin code that will be compiled for only the platform indicated in the folder name.
    For example, if you want to use Apple's CoreCrypto for the iOS part of your Kotlin app,
    `iosMain` would be the right folder for such calls.

* `/iosApp` contains iOS applications. Even if you're sharing your UI with Compose Multiplatform, 
  you need this entry point for your iOS app. This is also where you should add SwiftUI code for your project.


Learn more about [Kotlin Multiplatform](https://www.jetbrains.com/help/kotlin-multiplatform-dev/get-started.html)…# EasyWidgets
