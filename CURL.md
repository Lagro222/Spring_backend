# cURL API Examples

Copy and paste these examples into a Bash-compatible shell after starting the application. They target `http://localhost:8080` by default.

## Setup

```bash
export BASE_URL="http://localhost:8080"
```

Most endpoints require a JWT. Register or log in, then set the token returned in the JSON response:

```bash
curl --request POST "$BASE_URL/auth/register" \
  --header "Content-Type: application/json" \
  --data '{"name":"Doe","firstname":"Jane","email":"jane@example.com","password":"change-me"}'

curl --request POST "$BASE_URL/auth/login" \
  --header "Content-Type: application/json" \
  --data '{"email":"jane@example.com","password":"change-me"}'

export TOKEN="paste-the-token-from-the-response-here"
```

For authenticated requests, use this header:

```bash
-H "Authorization: Bearer $TOKEN"
```

The API uses `localhost:8080` unless configured otherwise. Replace example IDs, names, credentials, and file paths with your own values. The examples below use `jq` only in the optional token extraction example; it is not required for the API calls.

## Tracks

```bash
# List all tracks
curl "$BASE_URL/tracks" -H "Authorization: Bearer $TOKEN"

# Search by ID
curl --get "$BASE_URL/tracks/search" \
  --data-urlencode "id=1" \
  -H "Authorization: Bearer $TOKEN"

# Search by title
curl --get "$BASE_URL/tracks/search" \
  --data-urlencode "title=Midnight Song" \
  -H "Authorization: Bearer $TOKEN"

# Create a track
curl --request POST "$BASE_URL/tracks" \
  -H "Authorization: Bearer $TOKEN" \
  -H "Content-Type: application/json" \
  --data '{"title":"Midnight Song","release_date":2024}'

# Update a track
curl --request PUT "$BASE_URL/tracks/update/1" \
  -H "Authorization: Bearer $TOKEN" \
  -H "Content-Type: application/json" \
  --data '{"title":"Midnight Song (Remastered)","release_date":2025}'

# Delete a track (requires an ADMIN role)
curl --request DELETE "$BASE_URL/tracks/1" \
  -H "Authorization: Bearer $TOKEN"

# Assign a track to an album
curl --request POST "$BASE_URL/tracks/1/album/1" \
  -H "Authorization: Bearer $TOKEN"

# Upload audio; set AUDIO_FILE to a local audio file
export AUDIO_FILE="./song.mp3"
curl --request POST "$BASE_URL/tracks/upload" \
  -H "Authorization: Bearer $TOKEN" \
  --form "file=@$AUDIO_FILE" \
  --form "releaseDate=2024" \
  --form "title=Midnight Song"

# Stream/play a track; -o saves the response as a file
curl "$BASE_URL/tracks/play/1" \
  -H "Authorization: Bearer $TOKEN" \
  --output "track-1.mp3"
```

## Artists

```bash
# List artists
curl "$BASE_URL/artists" -H "Authorization: Bearer $TOKEN"

# Get an artist by ID
curl "$BASE_URL/artists/1" -H "Authorization: Bearer $TOKEN"

# Search artists by name
curl "$BASE_URL/artists/search/Example%20Artist" \
  -H "Authorization: Bearer $TOKEN"

# Get an artist's tracks
curl "$BASE_URL/artists/1/tracks" -H "Authorization: Bearer $TOKEN"

# Create an artist
curl --request POST "$BASE_URL/artists/create" \
  -H "Authorization: Bearer $TOKEN" \
  -H "Content-Type: application/json" \
  --data '{"name":"Example Artist","genre":"Pop","country":"Canada"}'

# Link a track to an artist
curl --request POST "$BASE_URL/artists/1/track/1" \
  -H "Authorization: Bearer $TOKEN"

# Link an album to an artist
curl --request POST "$BASE_URL/artists/1/album/1" \
  -H "Authorization: Bearer $TOKEN"

# Update an artist
curl --request PUT "$BASE_URL/artists/update/1" \
  -H "Authorization: Bearer $TOKEN" \
  -H "Content-Type: application/json" \
  --data '{"name":"Example Artist","genre":"Indie Pop","country":"Canada"}'

# Delete an artist
curl --request DELETE "$BASE_URL/artists/1" \
  -H "Authorization: Bearer $TOKEN"
```

## Albums

```bash
# List albums
curl "$BASE_URL/albums" -H "Authorization: Bearer $TOKEN"

# Search album by ID
curl --get "$BASE_URL/albums/search" \
  --data-urlencode "id=1" \
  -H "Authorization: Bearer $TOKEN"

# Search albums by title
curl --get "$BASE_URL/albums/search" \
  --data-urlencode "title=First Album" \
  -H "Authorization: Bearer $TOKEN"

# Create an album
curl --request POST "$BASE_URL/albums/create" \
  -H "Authorization: Bearer $TOKEN" \
  -H "Content-Type: application/json" \
  --data '{"title":"First Album","releaseYear":2024}'

# Update an album
curl --request PUT "$BASE_URL/albums/update/1" \
  -H "Authorization: Bearer $TOKEN" \
  -H "Content-Type: application/json" \
  --data '{"title":"First Album (Deluxe)","releaseYear":2025}'

# Delete an album (requires an ADMIN role)
curl --request DELETE "$BASE_URL/albums/delete/1" \
  -H "Authorization: Bearer $TOKEN"
```

## Playlists

`playlistType` must be a valid `PlaylistType` enum value defined by the application.

```bash
# List playlists
curl "$BASE_URL/playlists" -H "Authorization: Bearer $TOKEN"

# Get a playlist by ID
curl "$BASE_URL/playlists/1" -H "Authorization: Bearer $TOKEN"

# Get tracks in a playlist
curl "$BASE_URL/playlists/1/play" -H "Authorization: Bearer $TOKEN"

# Search playlists by name
curl "$BASE_URL/playlists/search/My%20Playlist" \
  -H "Authorization: Bearer $TOKEN"

# Create a playlist
curl --request POST "$BASE_URL/playlists/create" \
  -H "Authorization: Bearer $TOKEN" \
  -H "Content-Type: application/json" \
  --data '{"name":"My Playlist","playlistType":"PUBLIC","isCollaborative":false}'

# Add a track to a playlist
curl --request POST "$BASE_URL/playlists/1/track/1" \
  -H "Authorization: Bearer $TOKEN"

# Update a playlist
curl --request PUT "$BASE_URL/playlists/update/1" \
  -H "Authorization: Bearer $TOKEN" \
  -H "Content-Type: application/json" \
  --data '{"name":"Updated Playlist","playlistType":"PUBLIC","isCollaborative":false}'
```

## Users

The like and follow actions operate on the authenticated user; the user ID is not included in those POST URLs.

```bash
# List users
curl "$BASE_URL/users" -H "Authorization: Bearer $TOKEN"

# Search users by name
curl "$BASE_URL/users/search/Jane" -H "Authorization: Bearer $TOKEN"

# Get user details by ID
curl "$BASE_URL/users/1" -H "Authorization: Bearer $TOKEN"

# Get tracks liked by a user
curl "$BASE_URL/users/1/liked" -H "Authorization: Bearer $TOKEN"

# Get artists followed by a user
curl "$BASE_URL/users/1/follow/artists" -H "Authorization: Bearer $TOKEN"

# Get playlists followed by a user
curl "$BASE_URL/users/1/follow/playlists" -H "Authorization: Bearer $TOKEN"

# Like a track as the authenticated user
curl --request POST "$BASE_URL/users/like/1" \
  -H "Authorization: Bearer $TOKEN"

# Follow an artist as the authenticated user
curl --request POST "$BASE_URL/users/follow/artist/1" \
  -H "Authorization: Bearer $TOKEN"

# Follow a playlist as the authenticated user
curl --request POST "$BASE_URL/users/follow/playlist/1" \
  -H "Authorization: Bearer $TOKEN"

# Update a user's profile
curl --request PUT "$BASE_URL/users/update/1" \
  -H "Authorization: Bearer $TOKEN" \
  -H "Content-Type: application/json" \
  --data '{"name":"Doe","firstname":"Jane","email":"jane@example.com","password":"change-me"}'

# Unfollow an artist
curl --request DELETE "$BASE_URL/users/1/follow/artist/1" \
  -H "Authorization: Bearer $TOKEN"

# Unfollow a playlist
curl --request DELETE "$BASE_URL/users/1/unfollow/playlist/1" \
  -H "Authorization: Bearer $TOKEN"
```

## Notes

- `GET` endpoints are protected and require a bearer token, as do the other non-authentication API calls.
- Track, album, playlist, and artist deletion permissions depend on the application's security rules. The configured rules explicitly require `ADMIN` for track and album deletes; artist deletes require authentication.
- The examples reflect the current controller routes. There is no playlist delete endpoint in the current controller.
- JSON property names are case-sensitive. For example, track requests use `release_date`, album requests use `releaseYear`, and upload form fields use `releaseDate` and `title`.
