🐾 Stray Animal Emergency Response Platform

A **microservices-based emergency response system** designed to bridge the communication gap between **citizens, NGOs, and Municipal Corporations** during **stray animal accidents**. The platform ensures **fast reporting, smart location-based discovery, and real-time notifications** to reduce response time and prevent avoidable animal deaths.

---

 🚨 Problem Statement

In many cities, when a **stray animal meets with an accident**, there is:

* ❌ No centralized platform to report emergencies
* ❌ Delay in communication between citizens, NGOs, and municipal authorities
* ❌ Manual calling & forwarding leading to **loss of critical time**

Due to this delay, **many animals die without timely medical help**.

---

 ✅ Solution Overview

This platform provides a **single digital ecosystem** where:

* 👤 **Users** can report an injured stray animal with **images + details**
* 📍 The backend automatically identifies **nearby NGOs & Municipal bodies** based on **city/location**
* 🔔 **Real-time notifications** are sent instantly
* 🏥 NGOs/Municipal teams can **acknowledge and act quickly**

The system is built using **modern cloud‑native architecture** for scalability, reliability, and performance.

---

🧠 System Architecture

* **Microservices Architecture** (loosely coupled services)
* **Event‑Driven Communication** using RabbitMQ
* **Dockerized Services** for easy deployment
* **Secure APIs** using OAuth2 & Spring Security

```
User → API Gateway → Auth Service
                 → Complaint Service → RabbitMQ → Notification Service
                 → NGO Service
                 → Municipal Service
```

---

🛠️ Tech Stack

### Backend

* **Java 17**
* **Spring Boot**
* **Spring Security**
* **OAuth 2.0 / JWT**
* **Spring Cloud (Microservices)**

 Databases

* **MongoDB** (NoSQL, document‑based storage)

Messaging

* **RabbitMQ** (Asynchronous communication & notifications)

 DevOps & Infrastructure

* **Docker & Docker Compose**
* **Eureka Service Discovery**
* **API Gateway**

---

🔐 Security Features

* OAuth2 based authentication
* JWT token‑based authorization
* Role‑based access control:

  * USER
  * NGO
  * MUNICIPAL
  * ADMIN

---

 🧩 Microservices Breakdown

 1️⃣ API Gateway Service

* Central entry point for all requests
* Handles routing, authentication, and security

2️⃣ Auth Service

* User registration & login
* OAuth2 + JWT token generation

3️⃣ Complaint Service

* Accepts accident reports from users
* Stores animal details, images & location

4️⃣ NGO Service

* Manages NGO profiles
* Filters NGOs based on city/location

5️⃣ Municipal Service

* Manages municipal authority data
* City‑wise authority mapping

6️⃣ Notification Service

* Consumes RabbitMQ events
* Sends notifications to NGOs & Municipal bodies

---

 📍 Location‑Based Matching Logic

When a user submits a complaint:

1. Location (city) is extracted
2. Nearby NGOs & Municipal bodies are fetched
3. Event is published to RabbitMQ
4. Notification service triggers alerts

This ensures **minimum response time**.

---

## 🐳 Dockerized Setup

Each microservice runs in its own container:

* Independent scaling
* Environment consistency
* Easy cloud deployment (AWS ready)

---

## 🚀 How to Run Locally

### Prerequisites

* Docker & Docker Compose
* Java 17
* MongoDB

### Steps

```bash
# Clone the repository
git clone https://github.com/your-username/your-repo-name.git

# Move into project directory
cd your-repo-name

# Start all services
docker-compose up --build

----

📦 Future Enhancements

* 📱 Mobile App Integration
* 🗺️ Live Google Maps Tracking
* 📊 Admin Analytics Dashboard
* 🔔 SMS / WhatsApp Alerts
-----

💡 Why This Project Matters

* Saves animal lives 🐶🐱
* Reduces emergency response time
* Enables transparent & accountable action
* Real‑world social impact using technology

 👨‍💻 Author

**Developed by:** *[Your Name]*
**Role:** Backend / Java Microservices Developer
**Tech Focus:** Spring Boot • Microservices • Docker • Cloud . GitHub Actions CI CD 

⭐ Support
If you like this project, please ⭐ star the repository and share it.

Together, we can use technology to make cities more compassionate ❤️
