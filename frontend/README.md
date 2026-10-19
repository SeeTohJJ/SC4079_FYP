# FinLit Mobile Frontend

The FinLit mobile frontend is built with Flutter and Dart. It provides an interactive, gamified learning experience that helps users develop financial literacy through lessons, quizzes, adaptive study paths, and progress tracking.

## Features

- **Study Path:** Browse learning activities organised by subtopic.
- **Interactive Learning:** Complete lessons, quizzes, and review activities.
- **Progress Tracking:** Monitor learning progress and mastery.
- **Virtual Garden:** Visualise learning progress through plant growth and garden interactions.
- **Energy System:** Manage energy used to access learning activities.
- **AI-Generated Content:** Access generated quiz and review questions through the backend.
- **Authentication:** Register and sign in to access personalised learning.

## Tech Stack

- Flutter
- Dart
- REST API integration
- JWT-based authentication

## Prerequisites

- Flutter SDK
- Dart SDK
- Android Studio or another supported Flutter development environment
- A running FinLit backend

## Installation

Clone the repository and navigate to the frontend directory.

```bash
git clone <repository-url>
cd frontend
flutter pub get
```

## Configuration

Configure the backend API base URL in the application's API configuration.

For a local Android emulator, the backend URL is typically:

```text
http://10.0.2.2:8080
```

For a physical device, use the development machine's accessible local IP address. For production, use the deployed backend URL.

## Running the Application

```bash
flutter run
```

To check the Flutter environment:

```bash
flutter doctor
```

## Building

To build an Android APK:

```bash
flutter build apk --release
```

## Related Components

- `../backend/` — Spring Boot backend API
- `../cmp/` — React + Vite Content Management Portal
