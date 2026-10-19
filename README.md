# FinLit — Gamified Financial Literacy App

FinLit is a gamified financial literacy application designed to help Generation Z develop practical money management skills through interactive lessons, adaptive study paths, quizzes, and game-inspired progression.

The application covers budgeting, saving, investing, and credit management. It uses adaptive learning mechanisms to track users' knowledge and personalise their learning experience.

## Features

- Adaptive study paths based on learning progress and mastery.
- Interactive financial literacy lessons and quizzes.
- Learning mastery tracking using Bayesian Knowledge Tracing (BKT), ELO, and forgetting models.
- Gamified progression through energy mechanics, rewards, and a virtual garden.
- Review questions and AI-generated learning content using Google Gemini.
- Administrative content management portal.

## Project Structure

```text
FinLit/
├── backend/     # Spring Boot REST API
├── content_management_portal/     # React + Vite Content Management Portal
├── frontend/    # Flutter mobile application
└── README.md
```

## Tech Stack

| Component | Technologies |
|---|---|
| Mobile Frontend | Flutter, Dart |
| Backend | Java, Spring Boot, Gradle |
| Database | PostgreSQL |
| Content Management Portal | React, Vite, JavaScript |
| Authentication | JWT, Argon2 |
| Adaptive Learning | BKT, ELO, forgetting model |
| AI Integration | Google Gemini API |
| Deployment | Railway |

## Getting Started

Each component has its own setup instructions. Refer to the README in the corresponding directory.

- [Flutter Frontend](frontend/README.md)
- [Spring Boot Backend](backend/README.md)
- [Content Management Portal](content_management_portal/README.md)

## Deployment

The backend API and PostgreSQL database are hosted on Railway. The frontend and content management portal communicate with the backend through its configured API URL.

## Disclaimer

FinLit is an educational project intended to improve financial literacy. Its content is for educational purposes and should not be treated as personalised financial advice.

## Author

Developed as a Final Year Project focused on improving financial literacy among Generation Z through gamification and adaptive learning.
