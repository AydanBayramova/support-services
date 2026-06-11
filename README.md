# 🚀 Microservices Architecture Overview

This project is built as a scalable, event-driven microservices system designed for a modern delivery platform. Each service is responsible for a specific business domain and communicates via synchronous (OpenFeign) and asynchronous (RabbitMQ) mechanisms.

---

# 💳 Payment Service (The Financial Engine)

This service is responsible for maintaining financial integrity across the system and managing courier earnings.

## 🔑 Key Responsibilities

### Courier Earnings Calculation

Automatically calculates courier earnings upon order completion (`DELIVERED` status), typically using an 80/20 revenue split model.

### Wallet Management

Maintains a virtual wallet for each courier, tracking:

* Current balance
* Total turnover

### Idempotent Payment Processing

Prevents duplicate payments by ensuring each `order_id` is processed only once.

### Withdrawal Simulation

Provides APIs that simulate courier withdrawal of available balance.

## ⚙️ Advanced Features

### Optimistic Locking (`@Version`)

Ensures data consistency and prevents race conditions during concurrent financial updates.

---

# 🔔 Notification Service (The Communication Bridge)

This service handles all user-facing communication across the system.

## 🔑 Key Responsibilities

### Asynchronous Event Handling

Listens to events published by `ms-order` and `ms-payment` services and reacts in real time.

### Email Delivery

Sends real email notifications to users using `JavaMailSender`, including order status updates.

### Notification Auditing

Stores all notification attempts (successful or failed) for historical tracking and auditing.

## ⚙️ Advanced Features

### Strategy Pattern Implementation

Designed for extensibility, allowing future support for SMS and push notifications while following the Open/Closed principle.

---

# 📍 Tracking Service (The Real-Time Eye)

This service provides real-time tracking and historical movement data for couriers.

## 🔑 Key Responsibilities

### Location History Storage

Records courier movement with latitude, longitude, and timestamps.

### Live Tracking API

Provides real-time courier location updates for customers.

### Route Archiving

Stores full delivery routes from `ASSIGNED` to `DELIVERED` for analysis and history.

## ⚙️ Advanced Features

### Database Indexing Optimization

Uses indexing on `order_id` to efficiently handle and query large-scale geolocation data.

---

# ⭐ Rating Service (The Quality Feedback Engine)

This service collects and processes customer feedback to evaluate service quality.

## 🔑 Key Responsibilities

### Multi-Target Rating System

Allows customers to rate both courier and merchant independently for each order.

### Feedback Storage

Stores textual reviews and feedback for future analysis.

### Average Score Calculation

Computes aggregated ratings for couriers and restaurants based on historical data.

## 🛠 Shared Technology Stack

All microservices are built using a consistent and scalable technology stack:

* **Backend Framework:** Spring Boot 3.x
* **Messaging:** RabbitMQ (AMQP-based event-driven communication)
* **Database:** PostgreSQL
* **Schema Management:** Liquibase
* **Performance & Observability:** Spring AOP (centralized logging, monitoring)

### Clean Architecture Practices

* DTO Pattern
* MapStruct (object mapping)
* Java Records
