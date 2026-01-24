# FlowOrder – Backend Order Service Processing System

FlowOrder is a backend system designed to simulate a real-world order lifecycle, covering authentication, payments, inventory checks, and delivery flow.  
The project focuses on **backend architecture, security, and scalability concepts**, rather than UI.

This project was built to understand how production-grade backend systems are structured and how different components interact.

---

## Tech Stack

- Java 21
- Spring Boot
- Spring Security (JWT)
- PostgreSQL
- Redis
- Docker & Docker Compose

---

## Core Features

- JWT-based authentication and authorization
- Role-based access control (USER / ADMIN)
- Order lifecycle management
- Payment processing with idempotency protection
- Inventory confirmation after payment
- Logistics workflow (shipping and manual delivery)
- Redis-based rate limiting
- Event-driven internal workflow
- Fully containerized using Docker

---

## Order Workflow

1. User registers and logs in using JWT authentication
2. User creates an order → status **PENDING**
3. User makes a payment
4. Order status updates to **PAID**
5. Inventory service validates stock
    - If successful → order becomes **CONFIRMED**
    - (Failure handling planned as future improvement)
6. Logistics service handles shipping
7. Admin manually marks order as **DELIVERED**

This flow is designed to closely resemble real-world backend order systems.

---

## Architecture Overview

- Authentication handled using Spring Security + JWT
- Stateless API design
- Internal services communicate using application events
- Redis is used for:
    - Rate limiting
    - Idempotency (preventing duplicate payments)
- PostgreSQL stores all persistent data
- Docker Compose manages service orchestration

---

## Running the Project (Docker)

Make sure Docker and Docker Compose are installed.

```bash
docker-compose up
