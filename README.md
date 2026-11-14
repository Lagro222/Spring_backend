# Spring Backend

A Spring Boot backend for a music player application. The project provides authentication, user management, artist/album/track management, playlist support, and audio upload/playback features.

## Overview

This repository is built with Java and Spring Boot and uses PostgreSQL for persistence, Spring Security for authentication and authorization, and JWT for stateless API access. It is structured around a typical layered architecture:

- Controllers for exposing REST endpoints
- Services for business logic
- Repositories for database access
- Entities for persistence models
- DTOs for request/response mapping
- Security layer for JWT-based protection

## Features

- User registration and login
- JWT-based authentication
- Role-based authorization
- Artist management
- Album management
- Track management
- Playlist management
- Audio file upload
- Audio streaming/playback support
- Validation and centralized exception handling

## Tech Stack

- Java 21
- Spring Boot 4.1.0
- Spring Web MVC
- Spring Data JPA
- PostgreSQL
- Spring Security
- JWT (jjwt)
- Maven

## Project Structure

```text
Spring_backend/
├── .mvn/
├── src/
│   ├── main/
│   │   ├── java/com/store/app/
│   │   │   ├── controllers/
│   │   │   ├── services/
│   │   │   ├── repositories/
│   │   │   ├── entities/
│   │   │   ├── DTOs/
│   │   │   ├── enums/
│   │   │   ├── exception/
│   │   │   └── security/
│   │   └── resources/
│   │       └── application.properties
│   └── test/
├── .env.example.sh
├── .gitignore
├── pom.xml
├── mvnw
├── mvnw.cmd
├── TODO.md
└── README.md
```

## Prerequisites

Before running the project, make sure you have:

- Java 21+
- Maven
- PostgreSQL database
- A shell environment to load environment variables

## Configuration

This project reads environment variables from application properties. The main configuration is in:

```properties
spring.datasource.url=${DB_URL}
spring.datasource.username=${DB_USERNAME}
spring.datasource.password=${DB_PASSWORD}
app.upload.dir=${UPLOAD_DIR}
```

Create a local environment file based on the example:

```bash
cp .env.example.sh .env
source .env
```

Example values:

```bash
export DB_URL=jdbc:postgresql://localhost:5432/music_app
export DB_USERNAME=postgres
export DB_PASSWORD=your_password
export UPLOAD_DIR=./uploads
```

## Running the Application

Clone the repository:

```bash
git clone https://github.com/Lagro222/Spring_backend.git
cd Spring_backend
```

Run the app with Maven:

```bash
./mvnw clean install
./mvnw spring-boot:run
```

The application will start on the default Spring Boot port:

```text
http://localhost:8080
```

## Main API Endpoints

### Authentication

- `POST /auth/register` — register a new user
- `POST /auth/login` — login and receive JWT

### Tracks

- `GET /tracks` — get all tracks
- `GET /tracks/search?id={id}` — get track by ID
- `GET /tracks/search?title={title}` — search tracks by title
- `POST /tracks` — create a track
- `PUT /tracks/update/{id}` — update a track
- `DELETE /tracks/{id}` — delete a track
- `POST /tracks/upload` — upload an audio file
- `GET /tracks/play/{id}` — stream audio

### Artists

- `GET /artists`
- `POST /artists`
- `PUT /artists/{id}`
- `DELETE /artists/{id}`

### Albums

- `GET /albums`
- `POST /albums`
- `PUT /albums/{id}`
- `DELETE /albums/{id}`

### Playlists

- `GET /playlists`
- `POST /playlists`
- `PUT /playlists/{id}`
- `DELETE /playlists/{id}`

### Users

- `GET /users`
- `POST /users`
- `PUT /users/{id}`
- `DELETE /users/{id}`

## Security

The app uses Spring Security with JWT-based authentication. Most endpoints require authentication, and some actions are restricted by role (such as admin-only delete operations).

## Notes

- The project includes upload support for audio files.
- The upload and playback endpoints rely on the configured `UPLOAD_DIR`.
- The repository includes a `TODO.md` file, which suggests the project is still evolving and has several planned improvements.

## Contributing

Contributions are welcome. You can create a feature branch, make your changes, and open a pull request.

## License

This repository does not currently include an explicit license file. If you plan to publish or distribute this project, it is recommended to add a license such as MIT or Apache 2.0.

