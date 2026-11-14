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

- `POST /auth/register` — register a new user and receive a JWT token.
- `POST /auth/login` — log in with email and password and receive a JWT token.

### Tracks

The `TrackController` manages the music catalogue and audio files.

- `GET /tracks` — returns all tracks.
- `GET /tracks/search?id={id}` — fetches a single track by ID.
- `GET /tracks/search?title={title}` — searches tracks by title.
- `POST /tracks` — creates a new track using a `TrackRequestDTO`.
- `PUT /tracks/update/{id}` — updates an existing track by ID.
- `DELETE /tracks/{id}` — deletes a track.
- `POST /tracks/{trackId}/album/{albumID}` — assigns a track to an album.
- `POST /tracks/upload` — uploads an audio file with `file`, `releaseDate`, and `title` fields.
- `GET /tracks/play/{id}` — streams the audio file for a track.

### Artists

The `ArtistController` handles artist profiles and artist-track relationships.

- `GET /artists` — lists all artists.
- `GET /artists/{id}` — gets a single artist by ID.
- `GET /artists/search/{name}` — searches artists by name.
- `GET /artists/{artistId}/tracks` — returns all tracks associated with an artist.
- `POST /artists/create` — creates an artist.
- `POST /artists/{artistId}/track/{trackId}` — links a track to an artist.
- `POST /artists/{artistId}/album/{albumId}` — links an album to an artist.
- `PUT /artists/update/{id}` — updates artist information.
- `DELETE /artists/{id}` — deletes an artist.

### Albums

The `AlbumController` manages album records and their associations.

- `GET /albums` — lists all albums.
- `GET /albums/search?id={id}` — retrieves an album by ID.
- `GET /albums/search?title={title}` — searches albums by title.
- `POST /albums/create` — creates a new album.
- `PUT /albums/update/{id}` — updates an album.
- `DELETE /albums/delete/{id}` — deletes an album.

### Playlists

The `PlaylistController` handles user-created playlists and track assignment.

- `GET /playlists` — lists all playlists.
- `GET /playlists/{id}` — gets a playlist by ID.
- `GET /playlists/{id}/play` — retrieves the tracks inside a playlist.
- `GET /playlists/search/{name}` — searches playlists by name.
- `POST /playlists/create` — creates a playlist.
- `POST /playlists/{playlistId}/track/{trackId}` — adds a track to a playlist.
- `PUT /playlists/update/{id}` — updates playlist information.

### Users

The `UserController` handles profile management, likes, follows, and personal library actions.

- `GET /users` — lists all users.
- `GET /users/search/{name}` — searches users by name.
- `GET /users/{id}` — gets user details by ID.
- `GET /users/{id}/liked` — gets tracks liked by a user.
- `GET /users/{userId}/follow/artists` — gets artists followed by a user.
- `GET /users/{userId}/follow/playlists` — gets playlists followed by a user.
- `POST /users/like/{trackId}` — likes a track for the authenticated user.
- `POST /users/follow/artist/{artistId}` — follows an artist.
- `POST /users/follow/playlist/{playlistId}` — follows a playlist.
- `PUT /users/update/{userId}` — updates a user profile.
- `DELETE /users/{userId}/follow/artist/{artistId}` — unfollows an artist.
- `DELETE /users/{userId}/unfollow/playlist/{playlistId}` — unfollows a playlist.

## Security

The app uses Spring Security with JWT-based authentication. Most endpoints require authentication, and some actions are restricted by role (such as admin-only delete operations).

## Notes

- The project includes upload support for audio files.
- The upload and playback endpoints rely on the configured `UPLOAD_DIR`.
- The repository includes a `TODO.md` file, which suggests the project is still evolving and has several planned improvements.
- The app uses a role-based access model, with authenticated users and admin-specific restrictions configured in the security layer.

## Contributing

Contributions are welcome. You can create a feature branch, make your changes, and open a pull request.

## License

This repository does not currently include an explicit license file. If you plan to publish or distribute this project, it is recommended to add a license such as MIT or Apache 2.0.
