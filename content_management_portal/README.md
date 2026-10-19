# FinLit Content Management Portal

The FinLit Content Management Portal (CMP) is a web-based administrative application built with React and Vite. It allows administrators to manage the financial literacy content used by the mobile application.

## Features

- Manage financial literacy topics.
- Manage subtopics within each topic.
- Create and maintain lesson content.
- Manage quiz questions, answer options, and explanations.
- Maintain study-path learning content.
- Deactivate content that is no longer required.
- Communicate with the Spring Boot backend through REST APIs.

## Tech Stack

- React
- JavaScript
- Vite
- REST API integration
- npm

## Prerequisites

- Node.js
- npm
- A running FinLit backend or access to the deployed API

## Installation

Clone the repository and navigate to the CMP directory.

```bash
git clone <repository-url>
cd cmp
npm install
```

## Configuration

Create a `.env` file in the CMP directory and configure the backend API URL using the environment variable expected by the application.

For example:

```env
VITE_API_BASE_URL=http://localhost:8080
```

Ensure the variable name matches the one used in the frontend source code.

Do not place private API keys or other secrets in Vite environment variables prefixed with `VITE_`, because these values can be exposed to browser users.

## Running Locally

```bash
npm run dev
```

Vite will display the local development URL in the terminal, commonly:

```text
http://localhost:5173
```

## Production Build

Build the application for production:

```bash
npm run build
```

Preview the production build locally:

```bash
npm run preview
```

## Backend Integration

The CMP uses the FinLit Spring Boot backend to retrieve and manage learning content.

Ensure that:

- The backend is running and accessible.
- The API base URL is configured correctly.
- The backend permits requests from the CMP's origin through appropriate CORS configuration.
- Administrative endpoints enforce the required authentication and authorisation.

## Deployment

The CMP can be deployed to a static web hosting platform that supports Vite builds. Configure the production API URL before building and deploying the application.

## Related Components

- `../frontend/` — Flutter mobile application
- `../backend/` — Spring Boot backend API
