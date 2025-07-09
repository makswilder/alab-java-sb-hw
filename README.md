# CoworkingSpace Application

![AndersenLabJavaCourse](https://github.com/user-attachments/assets/ffdfb565-56b0-46aa-82c4-b3fe07c6b2e8)

# Coworking Space Reservation API

A Spring Boot application for managing coworking space reservations with role-based access and JWT-secured authentication.

## Features

### Admin

- Add, update, and remove workspaces
- View all reservations across the platform
- Cancel any customer reservation

### Customer

- Browse available coworking spaces
- Book new reservations
- View personal reservations
- Cancel own reservations

## Tech Stack

- Java 21
- Spring Boot 3 (REST API)
- Spring Security (JWT)
- Spring Data JPA
- PostgreSQL
- Docker & Docker Compose
- GitHub Actions (CI/CD pipeline)
- Mockito (Unit Testing)
- Observer Design Pattern (Reservation change notifications)

## Architecture

- Stateless JWT authentication via Spring Security
- Role-based access control (Admin, Customer)
- Service-Repository-Controller layered architecture
- Containerized deployment using Docker
- CI/CD pipeline for automated builds and tests 
