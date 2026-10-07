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

* Handles user interaction
* Login
* Menu navigation
* User input

**QueueManager**

* Contains queue-related business logic
* Handles valid ticket status transitions
* Coordinates DAO operations

**DAO Classes**

* `UserDAO`
* `ServiceDAO`
* `QueueDAO`
* `QueueHistoryDAO`

These classes handle database operations using JDBC.

**Model Classes**

* `User`
* `Service`
* `QueueTicket`
* `QueueHistory`

These classes represent the application's main entities.

**DatabaseConnection**

* Establishes the JDBC connection between Java and MySQL.

## Queue Workflow

```text
WAITING
   ↓
CALLED
   ↓
SERVING
   ↓
COMPLETED
```

Additional supported transitions:

```text
WAITING → CANCELLED

CALLED → SKIPPED
```

Invalid status transitions are rejected by the application.

## Database Structure

The project uses four main tables:

```text
users
services
queue_tickets
queue_history
```

### users

Stores customer, staff, and admin information.

### services

Stores the available queue services.

### queue_tickets

Stores generated queue tickets, token numbers, users, services, and ticket status.

### queue_history

Stores the history of ticket status changes.

## Demo Login Credentials

The repository includes public demo credentials so recruiters can test the different user roles.

### Customer

```text
Email:    vijay@gmail.com
Password: Demo@123
Role:     CUSTOMER
```

### Staff

```text
Email:    staff@gmail.com
Password: Demo@123
Role:     STAFF
```

### Admin

```text
Email:    admin@gmail.com
Password: Demo@123
Role:     ADMIN
```

> **Note:** These are public demo credentials created only for testing this GitHub project. They are not production credentials.

## Database Setup

### 1. Install MySQL

Make sure MySQL Server is installed and running on your system.

### 2. Create the Database

Open MySQL Workbench or the MySQL command line and run:

```text
database/smart_queue.sql
```

The SQL file creates:

* `smart_queue` database
* `users` table
* `services` table
* `queue_tickets` table
* `queue_history` table
* Demo users
* Demo services

The script is designed to avoid duplicate demo users and services when the setup is run again.

> For the cleanest demo experience, use a fresh `smart_queue` database.

### 3. Configure MySQL Credentials

Open:

```text
src/util/DatabaseConnection.java
```

Update the local MySQL username and password:

```java
private static final String USERNAME = "root";
private static final String PASSWORD = "YOUR_MYSQL_PASSWORD";
```

Replace `YOUR_MYSQL_PASSWORD` with your own local MySQL password.

**Do not commit your real database password to GitHub.**

## MySQL Connector/J

The project uses **MySQL Connector/J** for JDBC connectivity.

The required JAR file is included in:

```text
lib/
└── mysql-connector-j-26.7.0.jar
```

## How to Run

### Step 1 — Clone the Repository

```bash
git clone https://github.com/Vijay-kumar11/SmartQueue-Management-System.git
```

### Step 2 — Open the Project

Open the project folder in VS Code.

### Step 3 — Start MySQL

Make sure the MySQL server is running.

### Step 4 — Set Up the Database

Run:

```text
database/smart_queue.sql
```

### Step 5 — Configure Database Credentials

Update:

```text
src/util/DatabaseConnection.java
```

with your local MySQL username and password.

### Step 6 — Run the Application

Open:

```text
src/Main.java
```

Run `Main.java`.

The application starts with:

```text
=========================================
   SMART QUEUE MANAGEMENT SYSTEM
=========================================

Project Started Successfully!
```

## Example Customer Workflow

```text
Login
  ↓
View Services
  ↓
Generate Queue Ticket
  ↓
Check Queue Position
  ↓
View My Tickets
  ↓
Cancel Ticket if required
```

## Example Staff Workflow

```text
Login
  ↓
View Waiting Queue
  ↓
Call Next Customer
  ↓
Start Service
  ↓
Complete Service
```

Staff can also skip a customer and view ticket history.

## Example Admin Workflow

```text
Login
  ↓
View All Users
  ↓
View All Services
  ↓
View All Queue Tickets
  ↓
View Ticket History
```

## Error Handling

The application handles several invalid situations, including:

* Invalid login credentials
* Invalid menu choices
* Invalid service ID
* Invalid ticket ID
* Invalid ticket status transitions
* Database operation failures

Example:

```text
Invalid choice. Please try again.
```

and:

```text
Ticket not found.
```

## Key Java Concepts Demonstrated

* Classes and Objects
* Encapsulation
* Constructors
* Methods
* Java Collections
* `ArrayList`
* Exception Handling
* JDBC
* `PreparedStatement`
* `ResultSet`
* SQL queries
* CRUD-style database operations
* Layered architecture
* Separation of business logic and database access

## Project Architecture Explanation

The application follows a simple layered architecture:

```text
User
 ↓
Main.java
 ↓
QueueManager
 ↓
DAO Layer
 ↓
DatabaseConnection
 ↓
JDBC
 ↓
MySQL
```

This separation keeps user interaction, business logic, and database operations organized.

## Future Improvements

Possible improvements for future versions include:

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
* Real-time queue notifications

## Author

**Vijay Kumar**

B.Tech CSE — 2026

GitHub:

```text
https://github.com/Vijay-kumar11
```

## Project Status

**Version 1 — Completed**

Smart Queue Management System V1 was built using **Core Java + JDBC + MySQL** to demonstrate:

* Object-Oriented Programming
* Java Collections
* Exception Handling
* JDBC database connectivity
* MySQL database operations
* Business logic
* Queue management
* Role-based workflows
* Layered application architecture
* Git and GitHub project management
