# lets-play

REST API with Spring Boot, JWT, and MongoDB.

## Prerequisites
- Java 17
- Docker (for MongoDB)
- Maven (optional, `./mvnw` is included)

## Configuration
Set the environment variables before starting the app:

```bash
export JWT_SECRET='change-this-to-a-long-random-secret'
export JWT_EXPIRATION_MS=3600000
export SEED_ADMIN_USERNAME='admin'
export SEED_ADMIN_PASSWORD='admin123'
export SEED_ADMIN_EMAIL='admin@letsplay.ma'
```

Notes:
- `JWT_SECRET` should be long and random (at least 32 bytes / 256 bits; base64 is recommended).
- If `JWT_SECRET` is not set, the app generates a temporary secret for that run.
- Admin seeding is skipped if any `SEED_ADMIN_*` value is missing.

## Run MongoDB
Use the provided script (Docker required):

```bash
./run_mongoDB.sh
```

## Run the App

```bash
./mvnw spring-boot:run
```

The API will be available at `http://localhost:8080`.

## HTTPS (Optional)
To enable HTTPS locally, generate a keystore and set SSL environment variables.

```bash
keytool -genkeypair \
  -alias lets-play \
  -keyalg RSA \
  -keysize 2048 \
  -storetype PKCS12 \
  -keystore ./keystore.p12 \
  -validity 3650

export SERVER_SSL_ENABLED=true
export SERVER_SSL_KEY_STORE=./keystore.p12
export SERVER_SSL_KEY_STORE_PASSWORD='your-password'
export SERVER_SSL_KEY_ALIAS='lets-play'
```

Restart the app and access it using HTTPS.

## API Summary

### Auth
- `POST /api/auth/register` (public)
- `POST /api/auth/login` (public)
- `GET /api/auth/health` (public)

### Users
- `GET /api/users` (admin only)
- `GET /api/users/{id}` (admin or same user)
- `POST /api/users` (admin only)
- `PUT /api/users/{id}` (admin or same user)
- `DELETE /api/users/{id}` (admin or same user)

### Products
- `GET /api/products` (public)
- `GET /api/products/{id}` (public)
- `POST /api/products` (authenticated)
- `PUT /api/products/{id}` (owner or admin)
- `DELETE /api/products/{id}` (owner or admin)
