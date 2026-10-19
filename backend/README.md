# FinLit Backend API

The FinLit backend is a REST API built with Java and Spring Boot. It handles authentication, database operations, adaptive learning logic, study-path progression, user progress, and AI-powered quiz generation.

## Features

- User registration and authentication using JWT.
- Password hashing with Argon2.
- Financial literacy topic and subtopic management.
- Study-path and learning activity management.
- Lesson and quiz submissions.
- Adaptive mastery tracking using Bayesian Knowledge Tracing (BKT) and ELO.
- Forgetting and spaced review mechanisms.
- Energy regeneration and consumption.
- Garden progression and rewards.
- AI-generated quiz and review content using Google Gemini.
- Administrative content management endpoints.

## Tech Stack

- Java
- Spring Boot
- Gradle
- PostgreSQL
- JDBC / JdbcTemplate
- JWT
- Argon2
- Google Gemini API

## Prerequisites

- JDK version specified by the project
- PostgreSQL
- Gradle, or the included Gradle Wrapper
- Google Gemini API key for AI-powered functionality

## Installation

Clone the repository and navigate to the backend directory.

```bash
git clone <repository-url>
cd backend
```

## Configuration

Configure the following environment variables:

| Variable | Description |
|---|---|
| `SPRING_DATASOURCE_URL` | PostgreSQL JDBC URL |
| `SPRING_DATASOURCE_USERNAME` | Database username |
| `SPRING_DATASOURCE_PASSWORD` | Database password |
| `GEMINI_API_KEY` | Google Gemini API key |

Ensure that the PostgreSQL database has been created and that the connection settings match your environment.

Never commit credentials, API keys, or production secrets to GitHub.

## Running Locally

On Windows:

```powershell
.\gradlew.bat bootRun
```

On macOS or Linux:

```bash
./gradlew bootRun
```

The application typically runs at:

```text
http://localhost:8080
```

## Building

```bash
./gradlew build
```

On Windows:

```powershell
.\gradlew.bat build
```

## API Integration

The Flutter frontend and React + Vite content management portal communicate with the backend through REST endpoints.

For local development, configure each frontend to use the appropriate local API URL. For production, configure them to use the deployed backend URL.

## Deployment

The backend is deployed on Railway. Database connection settings and other sensitive configuration should be provided through the deployment environment's variables.

## Related Components

- `../frontend/` — Flutter mobile application
- `../cmp/` — React + Vite Content Management Portal
