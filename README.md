---

# ATTENTION

This system was tested on Apple M2 Max 2023.

# Billing System

Welcome to the Billing System project, a comprehensive solution designed to streamline billing and invoicing processes. This system leverages Scala, Docker, and PostgreSQL to create a robust backend capable of handling complex billing operations.

## Features

- Client's model CRUD 
- **ProcessSchedule**: Automatic debiting for connected services from customers

## Getting Started

### Prerequisites

- Docker
- Docker Compose
- SBT (Scala Build Tool)
- Postgresql

### Installation

1. Clone the repository:
    ```bash
    git clone https://github.com/haskiindahouse/billing_system.git
    ```

2. Navigate to the project directory:
    ```bash
    cd billing_system
    ```

3. Launch the services using Docker Compose:
    ```bash
    docker-compose up -d
    ```

### Configuration

- **Docker**: The `Dockerfile` and `docker-compose.yml` are used to containerize the application and its dependencies.
- **Scala**: Application configurations can be adjusted in `build.sbt` and `.scalafmt.conf`.

## Contribution

I'm welcome contributions to the Billing System project! If you're looking to contribute, please follow these steps:

1. Fork the repository.
2. Create a new branch for your feature or fix.
3. Write your code and ensure it adheres to the project's coding standards.
4. Submit a pull request detailing the changes introduced.

---
