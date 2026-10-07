CREATE DATABASE IF NOT EXISTS smart_queue;

USE smart_queue;


-- =========================================
-- USERS TABLE
-- =========================================

CREATE TABLE IF NOT EXISTS users (

    id INT PRIMARY KEY AUTO_INCREMENT,

    name VARCHAR(100) NOT NULL,

    email VARCHAR(100) NOT NULL UNIQUE,

    password VARCHAR(100) NOT NULL,

    role VARCHAR(20) NOT NULL
);


-- =========================================
-- SERVICES TABLE
-- =========================================

CREATE TABLE IF NOT EXISTS services (

    id INT PRIMARY KEY AUTO_INCREMENT,

    service_name VARCHAR(100) NOT NULL
);


-- =========================================
-- QUEUE TICKETS TABLE
-- =========================================

CREATE TABLE IF NOT EXISTS queue_tickets (

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
);


-- =========================================
-- QUEUE HISTORY TABLE
-- =========================================

CREATE TABLE IF NOT EXISTS queue_history (

    id INT PRIMARY KEY AUTO_INCREMENT,

    ticket_id INT NOT NULL,

    old_status VARCHAR(20),

    new_status VARCHAR(20) NOT NULL,

    changed_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,

    FOREIGN KEY (ticket_id)
        REFERENCES queue_tickets(id)
);


-- =========================================
-- INSERT SERVICES
-- =========================================

INSERT INTO services (service_name)
VALUES
    ('General Enquiry'),
    ('Cash Counter'),
    ('Document Verification'),
    ('Customer Support');


-- =========================================
-- INSERT SAMPLE USERS
-- =========================================

INSERT INTO users (name, email, password, role)
VALUES
    ('Vijay', 'vijay@gmail.com', 'YOUR_PASSWORD', 'CUSTOMER'),

    ('Staff User', 'staff@gmail.com', 'YOUR_PASSWORD', 'STAFF'),

    ('Admin User', 'admin@gmail.com', 'YOUR_PASSWORD', 'ADMIN');