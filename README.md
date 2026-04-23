# Fleet Management API

A production-ready REST API for vehicle onboarding and subscription-based feature management, built with Spring Boot.

Inspired by real-world automotive IoT systems, this service manages vehicle registration, subscription packages, and feature access control (ADAS, Geofence, OTA updates).

## Features

- Vehicle onboarding and management
- Subscription-based feature access (Basic / Standard / Premium)
- JWT authentication and authorization
- Firmware version tracking and OTA update status
- Global exception handling with meaningful error responses
- API documentation via Swagger UI
- Containerized with Docker

## Tech Stack

- Java 17
- Spring Boot 3.2
- Spring Security + JWT
- PostgreSQL
- Docker
- Maven

## Architecture

```
Client → JWT Filter → Controller → Service → Repository → PostgreSQL
```

## API Endpoints

### Auth
| Method | Endpoint | Description |
|--------|----------|-------------|
| POST | /api/auth/register | Register a new user |
| POST | /api/auth/login | Login and get JWT token |

### Vehicles
| Method | Endpoint | Description |
|--------|----------|-------------|
| POST | /api/vehicles/onboard | Onboard a new vehicle |
| GET | /api/vehicles/{deviceId} | Get vehicle details |
| PUT | /api/vehicles/{deviceId}/firmware | Update firmware version |
| POST | /api/vehicles/{deviceId}/subscription | Assign subscription package |
| GET | /api/vehicles/{deviceId}/features | Get available features |

## Getting Started

### Prerequisites
- Java 17
- PostgreSQL
- Maven

### Run Locally

1. Clone the repo
```bash
git clone https://github.com/edwinmjose98/fleet-management-api.git
cd fleet-management-api
```

2. Create database
```sql
CREATE DATABASE taskmanager;
CREATE USER taskuser WITH PASSWORD 'taskpass';
GRANT ALL PRIVILEGES ON DATABASE taskmanager TO taskuser;
```

3. Run the app
```bash
./mvnw spring-boot:run
```

4. Access Swagger UI
```
http://localhost:8081/swagger-ui/index.html
```

### Run with Docker
```bash
docker build -t fleet-management-api .
docker run -p 8081:8081 \
  -e SPRING_DATASOURCE_URL=jdbc:postgresql://host.docker.internal:5432/taskmanager \
  -e SPRING_DATASOURCE_USERNAME=taskuser \
  -e SPRING_DATASOURCE_PASSWORD=taskpass \
  fleet-management-api
```

## Subscription Packages

| Package | Features |
|---------|----------|
| Basic | Geofence |
| Standard | Geofence, ADAS |
| Premium | Geofence, ADAS, OTA Update, Remote Lock, Remote Start, Diagnostics |

## Testing
```bash
./mvnw test
```