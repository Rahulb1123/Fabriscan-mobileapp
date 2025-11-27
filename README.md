# FabriScan

## Description

FabriScan is an Android application designed to scan and analyze fabrics using a machine learning model. The app allows users to sign in with their Google account and stores scan results in a Firestore database.

## Features

- **User Authentication**: Secure sign-in with Google, powered by Firebase Authentication.
- **Fabric Scanning**: Utilizes the device's camera to scan fabrics.
- **ML-Powered Analysis**: Employs a TensorFlow Lite model to analyze fabric images.
- **Cloud Storage**: Saves scan history and user data in a Firestore database.

## Technologies Used

- **Android**: The core platform for the application.
- **Firebase**: For authentication and database services.
- **TensorFlow Lite**: For on-device machine learning.
- **Google Sign-In**: For a seamless authentication experience.
- **Material Design**: For a modern and intuitive user interface.

## Setup and Installation

To get started with FabriScan, follow these steps:

1. **Clone the repository**:
   ```bash
   git clone https://github.com/your-username/fabriscan.git
   ```

2. **Set up Firebase**:
   - Create a new project in the [Firebase Console](https://console.firebase.google.com/).
   - Add an Android app to your Firebase project with the package name `com.example.fabriscan`.
   - Download the `google-services.json` file and place it in the `app` directory of the project.

3. **Build the project**:
   - Open the project in Android Studio.
   - Let Gradle sync and build the project.
   - Run the app on an emulator or a physical device.

## Usage

Once the app is installed, you can sign in with your Google account to start scanning fabrics. The app will provide real-time analysis of the fabric and save the results to your account.

## Contributing

Contributions are welcome! If you have any ideas, suggestions, or bug reports, please open an issue or submit a pull request.

