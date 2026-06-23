# 🚀 Software Engineering Lab

> A collection of hands-on experiments, learning projects, and technical notes covering Software Engineering concepts from fundamentals to advanced topics.

---

## 🎯 Purpose

This repository is my personal learning laboratory.

The goal is not only to study technologies, but to understand them through:

* Building
* Experimenting
* Benchmarking
* Observing real behavior
* Documenting findings

Every topic should answer four questions:

* What is it?
* How does it work?
* When should it be used?
* What trade-offs does it introduce?

---

## 📚 Current Labs

### Backend

#### Spring Data JPA

| Lab                     | Status      |
| ----------------------- | ----------- |
| N+1 Query Problem       | ✅ Completed |
| Persistence Context     | ⏳ Planned   |
| Dirty Checking          | ⏳ Planned   |
| Transaction Propagation | ⏳ Planned   |
| Optimistic Locking      | ⏳ Planned   |
| Pessimistic Locking     | ⏳ Planned   |

---

## 🗂 Repository Structure

```text
software-engineering-lab

└── backend
    └── spring-data-jpa
        └── n-plus-one
```

Each lab is designed to be:

* Self-contained
* Reproducible
* Easy to run
* Easy to understand

Most labs include:

```text
lab
├── README.md
├── docker-compose.yml
├── db
│   └── init.sql
└── application
```

---

## 🎓 Learning Philosophy

This repository follows a simple principle:

> Learn by building.
>
> Understand by experimenting.
>
> Remember by documenting.

Reading is useful.

Running code is better.

Breaking things and observing the results is even better.

---

## 🚧 Planned Areas

The repository is expected to gradually expand into:

### Backend Engineering

* Spring Data JPA
* Spring Security
* Redis
* Kafka
* RabbitMQ
* gRPC

### Database Engineering

* MySQL
* PostgreSQL
* MongoDB

### System Design & Architecture

* Caching
* Rate Limiting
* Event-Driven Architecture
* CQRS
* Distributed Systems

### DevOps

* Docker
* Kubernetes
* GitHub Actions
* Nginx
* Terraform

### AI Engineering

* Prompt Engineering
* RAG
* AI Agents
* Vector Databases

### Frontend Engineering

* React
* Next.js
* TypeScript
* Frontend Performance

---

## ⚠️ Disclaimer

This repository is built primarily for learning and experimentation.

Some labs intentionally demonstrate:

* Anti-patterns
* Performance issues
* Incorrect implementations
* Common mistakes

before presenting optimized solutions.

The objective is to understand engineering trade-offs rather than only showcasing best practices.

---

## ⭐ Long-Term Goal

Build a practical Software Engineering knowledge base that documents the journey from beginner concepts to production-grade engineering practices.

Every lab should contain:

* Working source code
* Reproducible setup
* Technical documentation
* Practical examples
* Engineering discussions
* Lessons learned
