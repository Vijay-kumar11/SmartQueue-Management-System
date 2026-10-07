CREATE DATABASE IF NOT EXISTS smart_queue;

USE smart_queue;

-- =========================================
-- USERS TABLE
-- =========================================

CREATE TABLE IF NOT EXISTS users (

```
id INT PRIMARY KEY AUTO_INCREMENT,

name VARCHAR(100) NOT NULL,

email VARCHAR(100) NOT NULL UNIQUE,

password VARCHAR(100) NOT NULL,

role VARCHAR(20) NOT NULL
```

);

-- =========================================
-- SERVICES TABLE
-- =========================================

CREATE TABLE IF NOT EXISTS services (

```
id INT PRIMARY KEY AUTO_INCREMENT,

service_name VARCHAR(100) NOT NULL
```

);

-- =========================================
-- QUEUE TICKETS TABLE
-- =========================================

CREATE TABLE IF NOT EXISTS queue_tickets (

```
id INT PRIMARY KEY AUTO_INCREMENT,

token_number INT NOT NULL,

user_id INT NOT NULL,

service_id INT NOT NULL,

status VARCHAR(20) NOT NULL,

created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,

FOREIGN KEY (user_id)
    REFERENCES users(id),

FOREIGN KEY (service_id)
    REFERENCES services(id)
```

);

-- =========================================
-- QUEUE HISTORY TABLE
-- =========================================

CREATE TABLE IF NOT EXISTS queue_history (

```
id INT PRIMARY KEY AUTO_INCREMENT,

ticket_id INT NOT NULL,

old_status VARCHAR(20),

new_status VARCHAR(20) NOT NULL,

changed_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,

FOREIGN KEY (ticket_id)
    REFERENCES queue_tickets(id)
```

);

-- =========================================
-- INSERT SERVICES
-- =========================================

INSERT INTO services (service_name)
SELECT 'General Enquiry'
WHERE NOT EXISTS (
SELECT 1
FROM services
WHERE service_name = 'General Enquiry'
);

INSERT INTO services (service_name)
SELECT 'Cash Counter'
WHERE NOT EXISTS (
SELECT 1
FROM services
WHERE service_name = 'Cash Counter'
);

INSERT INTO services (service_name)
SELECT 'Document Verification'
WHERE NOT EXISTS (
SELECT 1
FROM services
WHERE service_name = 'Document Verification'
);

INSERT INTO services (service_name)
SELECT 'Customer Support'
WHERE NOT EXISTS (
SELECT 1
FROM services
WHERE service_name = 'Customer Support'
);

-- =========================================
-- INSERT DEMO USERS
-- =========================================

INSERT INTO users (name, email, password, role)
SELECT 'Vijay', '[vijay@gmail.com](mailto:vijay@gmail.com)', 'Demo@123', 'CUSTOMER'
WHERE NOT EXISTS (
SELECT 1
FROM users
WHERE email = '[vijay@gmail.com](mailto:vijay@gmail.com)'
);

INSERT INTO users (name, email, password, role)
SELECT 'Staff User', '[staff@gmail.com](mailto:staff@gmail.com)', 'Demo@123', 'STAFF'
WHERE NOT EXISTS (
SELECT 1
FROM users
WHERE email = '[staff@gmail.com](mailto:staff@gmail.com)'
);

INSERT INTO users (name, email, password, role)
SELECT 'Admin User', '[admin@gmail.com](mailto:admin@gmail.com)', 'Demo@123', 'ADMIN'
WHERE NOT EXISTS (
SELECT 1
FROM users
WHERE email = '[admin@gmail.com](mailto:admin@gmail.com)'
);

-- =========================================
-- DEMO LOGIN CREDENTIALS
-- =========================================
--------------------------------------------

-- Customer:
-- Email: [vijay@gmail.com](mailto:vijay@gmail.com)
-- Password: Demo@123
---------------------

-- Staff:
-- Email: [staff@gmail.com](mailto:staff@gmail.com)
-- Password: Demo@123
---------------------

-- Admin:
-- Email: [admin@gmail.com](mailto:admin@gmail.com)
-- Password: Demo@123
---------------------

-- =========================================
