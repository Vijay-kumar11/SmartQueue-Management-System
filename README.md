# Smart Queue Management System

A console-based queue management application developed using **Core Java, OOP, JDBC, and MySQL**.

The system manages customer queues using token numbers and provides separate workflows for **Customer, Staff, and Admin** users.

## Features

### Customer

* User login
* View available services
* Generate queue ticket
* View queue position
* View personal queue tickets
* Cancel waiting ticket

### Staff

* View waiting queue
* Call next customer
* Start customer service
* Complete service
* Skip customer
* View ticket history

### Admin

* View all users
* View all services
* View all queue tickets
* View ticket history

## Technologies Used

* **Java 26**
* **Core Java**
* **Object-Oriented Programming**
* **Java Collections**
* **Exception Handling**
* **JDBC**
* **MySQL**
* **MySQL Connector/J**
* **VS Code**
* **Git & GitHub**

## Project Architecture

The project follows a simple layered architecture:

```text
Main.java
    ↓
QueueManager
    ↓
DAO Classes
    ↓
DatabaseConnection
    ↓
JDBC
    ↓
MySQL
```

### Main Components

**Main.java**

Handles user interaction, login, menus, and input.

**QueueManager**

Contains the queue-related business logic and controls valid ticket status transitions.

**DAO Classes**

Handle database operations using JDBC.

* `UserDAO`
* `ServiceDAO`
* `QueueDAO`
* `QueueHistoryDAO`

**Model Classes**

Represent application entities.

* `User`
* `Service`
* `QueueTicket`
* `QueueHistory`

**DatabaseConnection**

Creates the JDBC connection to MySQL.

## Queue Workflow

A customer ticket follows these valid status transitions:

```text
WAITING
   ↓
CALLED
   ↓
SERVING
   ↓
COMPLETED
```

Additional valid flows:

```text
WAITING → CANCELLED

CALLED → SKIPPED
```

Invalid status transitions are rejected by the application.

## Database Structure

The application uses four main tables:

```text
users
services
queue_tickets
queue_history
```

### users

Stores customer, staff, and admin information.

### services

Stores the services available in the queue system.

### queue_tickets

Stores token numbers, users, services, ticket status, and creation time.

### queue_history

Stores every status change made to a queue ticket.

## Database Setup

### 1. Install MySQL

Make sure MySQL Server is installed and running.

### 2. Create the database

Open MySQL Workbench or the MySQL command line.

Run the SQL script provided in:

```text
database/smart_queue.sql
```

The script creates the database, tables, and sample users and services.

### 3. Configure the database connection

Open:

```text
src/util/DatabaseConnection.java
```

Update the MySQL username and password for your local MySQL installation.

Example:

```java
private static final String URL =
    "jdbc:mysql://localhost:3306/smart_queue";

private static final String USERNAME =
    "root";

private static final String PASSWORD =
    "YOUR_MYSQL_PASSWORD";
```

Replace `YOUR_MYSQL_PASSWORD` with your local MySQL password.

## MySQL Connector/J

The project uses **MySQL Connector/J** for JDBC connectivity.

The connector JAR is included in:

```text
lib/
```

The project is configured to reference this library through:

```text
.vscode/settings.json
```

## How to Run

### Using VS Code

1. Clone the repository.
2. Open the project in VS Code.
3. Make sure Java is installed.
4. Make sure MySQL Server is running.
5. Create the `smart_queue` database using `database/smart_queue.sql`.
6. Configure your MySQL credentials in `DatabaseConnection.java`.
7. Open:

```text
src/Main.java
```

8. Run `Main.java`.

The application starts with:

```text
SMART QUEUE MANAGEMENT SYSTEM
Project Started Successfully!
```

## Example Workflow

### Customer

```text
Login
  ↓
View Services
  ↓
Generate Queue Ticket
  ↓
Check Queue Position
  ↓
Wait for Staff
```

### Staff

```text
View Waiting Queue
  ↓
Call Next Customer
  ↓
Start Service
  ↓
Complete Service
```

### Admin

```text
View Users
  ↓
View Services
  ↓
View Queue Tickets
  ↓
View Ticket History
```

## Error Handling

The application handles common invalid operations such as:

* Invalid login credentials
* Invalid menu choices
* Invalid service ID
* Invalid ticket ID
* Invalid ticket status transitions
* Database operation failures

## Key Java Concepts Demonstrated

This project demonstrates practical usage of:

* Classes and Objects
* Encapsulation
* Constructors
* Inheritance concepts
* Interfaces concepts
* Methods
* Collections
* `ArrayList`
* Exception Handling
* JDBC
* `PreparedStatement`
* `ResultSet`
* SQL queries
* CRUD-style database operations
* Layered architecture
* Separation of business logic and database access

## Future Improvements

Possible future improvements include:

* Password hashing
* Better database transaction management
* Connection pooling
* Improved input validation
* Enum-based roles and ticket statuses
* Improved concurrent token generation
* Web-based user interface
* REST APIs
* Spring Boot backend
* React frontend
* Real-time customer notifications

## Author

**Vijay Kumar**

B.Tech CSE — 2026

GitHub:

`https://github.com/Vijay-kumar11`

## Project Status

**Version 1 — Completed**

Built as a Core Java + JDBC + MySQL project to demonstrate object-oriented programming, database connectivity, business logic, and queue management.
