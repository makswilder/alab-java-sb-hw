# CoworkingSpace Application

![AndersenLabJavaCourse](https://github.com/user-attachments/assets/ffdfb565-56b0-46aa-82c4-b3fe07c6b2e8)

## Overview

The **CoworkingSpace Application** is a web-based platform built with **Spring Boot** that allows users to manage coworking space reservations. This application offers two primary user roles: **Admin** and **Customer**, each with its own set of capabilities. 

- **Admin**: Manages workspaces and reservations
- **Customer**: Books and manages personal reservations

The app is secured using **JWT-based authentication** through **Spring Security** and is backed by a **PostgreSQL** database.

## Features

### Admin Capabilities
- ✅ **Add a new workspace**: Admins can create new coworking spaces for customers to book.
- ✅ **Remove an existing workspace**: Admins can delete workspaces that are no longer in use.
- ✅ **View all reservations**: Admins can see a list of all current and past reservations across the platform.
- ✅ **Update workspace details**: Modify workspace descriptions, amenities, and availability.
- ✅ **Cancel any reservation**: Admins can cancel any customer reservation as needed.

### Customer Capabilities
- ✅ **Browse available coworking spaces**: Customers can explore workspaces available for booking.
- ✅ **Make a reservation**: Customers can book a space based on availability.
- ✅ **View personal reservations**: Customers can see a list of their past and upcoming bookings.
- ✅ **Cancel own reservation**: Customers can cancel their own reservations at any time.

## Tech Stack

- **Java 21**: Core language for backend development, adhering to Object-Oriented Programming principles.
- **Spring Boot**: Backend framework for building the RESTful API.
- **Spring Security (JWT Token)**: For handling user authentication and authorization securely.
- **Spring Data JPA**: ORM framework for interacting with the database seamlessly.
- **PostgreSQL**: Relational database used for storing user data, reservations, and workspace details.
- **Docker & Docker Compose**: For containerizing the application, ensuring easy deployment and scalability.
- **GitHub Actions (ci-cd-pipeline.yml)**: CI/CD pipeline for continuous integration and deployment, automating testing and build processes.
- **Mockito**: Framework for writing unit tests to ensure the application is robust and reliable.
- **Observer Design Pattern**: Used for notifying customers when their reservation is canceled or modified.
