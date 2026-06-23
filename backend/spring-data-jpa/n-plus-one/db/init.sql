-- =========================================
-- Drop tables
-- =========================================

DROP TABLE IF EXISTS orders;
DROP TABLE IF EXISTS users;

-- =========================================
-- Create users table
-- =========================================

CREATE TABLE users (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(255) NOT NULL
);

-- =========================================
-- Create orders table
-- =========================================

CREATE TABLE orders (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    product_name VARCHAR(255) NOT NULL,
    user_id BIGINT NOT NULL,

    CONSTRAINT fk_orders_user
        FOREIGN KEY (user_id)
        REFERENCES users(id)
);

-- =========================================
-- Users
-- =========================================

INSERT INTO users (name)
VALUES
    ('User 01'),
    ('User 02'),
    ('User 03'),
    ('User 04'),
    ('User 05'),
    ('User 06'),
    ('User 07'),
    ('User 08'),
    ('User 09'),
    ('User 10');

-- =========================================
-- Orders
-- =========================================

INSERT INTO orders (product_name, user_id)
VALUES

    ('Laptop', 1),
    ('Mouse', 1),

    ('Keyboard', 2),
    ('Monitor', 2),

    ('Laptop', 3),
    ('Headphone', 3),

    ('Mouse', 4),
    ('Webcam', 4),

    ('Monitor', 5),
    ('Keyboard', 5),

    ('Headphone', 6),
    ('Mouse', 6),

    ('Laptop', 7),
    ('Monitor', 7),

    ('Keyboard', 8),
    ('Webcam', 8),

    ('Laptop', 9),
    ('Headphone', 9),

    ('Monitor', 10),
    ('Mouse', 10);

-- =========================================
-- Verify
-- =========================================

SELECT COUNT(*) AS total_users
FROM users;

SELECT COUNT(*) AS total_orders
FROM orders;