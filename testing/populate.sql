-- Drop Tables To Start Fresh
DROP TABLE order_product_ids;
DROP TABLE product_review_ids;
DROP TABLE orders;
DROP TABLE products;
DROP TABLE reviews;
DROP TABLE users;
DROP TABLE visit_event;

-- Recreate All Of The Tables Needed
CREATE TABLE `products` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `brand` varchar(255) DEFAULT NULL,
  `category` varchar(255) DEFAULT NULL,
  `color` varchar(255) DEFAULT NULL,
  `description` varchar(255) DEFAULT NULL,
  `image_url` varchar(255) DEFAULT NULL,
  `price` float DEFAULT NULL,
  `product_name` varchar(255) DEFAULT NULL,
  `size` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

CREATE TABLE `reviews` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `description` varchar(255) DEFAULT NULL,
  `product_id` bigint DEFAULT NULL,
  `rating` int DEFAULT NULL,
  `title` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

CREATE TABLE `orders` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `country` varchar(255) DEFAULT NULL,
  `date` date NOT NULL,
  `first_name` varchar(255) DEFAULT NULL,
  `last_name` varchar(255) DEFAULT NULL,
  `status` varchar(255) DEFAULT NULL,
  `total` float DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

CREATE TABLE `product_review_ids` (
  `product_id` bigint NOT NULL,
  `review_ids` bigint NOT NULL,
  PRIMARY KEY (`product_id`,`review_ids`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

CREATE TABLE `order_product_ids` (
  `order_id` bigint NOT NULL,
  `product_ids` bigint NOT NULL,
  PRIMARY KEY (`order_id`,`product_ids`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

CREATE TABLE `users` (
  `id` int NOT NULL AUTO_INCREMENT,
  `email` varchar(255) NOT NULL,
  `password` varchar(255) NOT NULL,
  `role_code` varchar(255) NOT NULL,
  `username` varchar(255) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

CREATE TABLE `visit_event` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `date` date NOT NULL,
  `event` varchar(255) DEFAULT NULL,
  `ip_address` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- Populate Data Into The Tables
INSERT INTO users (email, password, role_code, username) VALUES ("jim123@hotmail.com", "83rcRwJIYCXUBqE6aRdX", "ADMIN", "jim123");
INSERT INTO users (email, password, role_code, username) VALUES ("eric4566@hotmail.com", "123456password", "USER", "eric231");

INSERT INTO products (brand, category, color, description, image_url, price, product_name, size) VALUES ("nike", "top", "blue", "Ultra thin shirt", "", 56.99, "Nike Shirt", "XXS");
INSERT INTO products (brand, category, color, description, image_url, price, product_name, size) VALUES ("nike", "top", "black", "Ultra thin oversized shirt", "", 56.99, "Nike Oversized Shirt", "XS");
INSERT INTO products (brand, category, color, description, image_url, price, product_name, size) VALUES ("nike", "top", "green", "Ultra thin tank top", "", 56.99, "Nike TankTop", "S");
INSERT INTO products (brand, category, color, description, image_url, price, product_name, size) VALUES ("nike", "bottom", "grey", "Thick pants", "", 79.99, "Nike Pants", "XXS");
INSERT INTO products (brand, category, color, description, image_url, price, product_name, size) VALUES ("nike", "bottom", "black", "Thick tech pants", "", 79.99, "Nike Tech Pants", "XS");
INSERT INTO products (brand, category, color, description, image_url, price, product_name, size) VALUES ("nike", "bottom", "white", "Thick cargo pants", "", 79.99, "Nike Cargo Pants", "S");
INSERT INTO products (brand, category, color, description, image_url, price, product_name, size) VALUES ("adidas", "accessories", "white", "Elastic", "", 19.99, "Adidas Headband", "XS");

INSERT INTO reviews (description, product_id, rating, title) VALUES ("kinda liked it", 1, 3, "Okay product");
INSERT INTO reviews (description, product_id, rating, title) VALUES ("liked it", 1, 4, "Good product");
INSERT INTO reviews (description, product_id, rating, title) VALUES ("didnt like it", 3, 1, "Bad product");
INSERT INTO reviews (description, product_id, rating, title) VALUES ("really liked it", 4, 5, "Amazing product");

INSERT INTO product_review_ids (product_id, review_ids) VALUES (1, 1);
INSERT INTO product_review_ids (product_id, review_ids) VALUES (1, 2);
INSERT INTO product_review_ids (product_id, review_ids) VALUES (3, 3);
INSERT INTO product_review_ids (product_id, review_ids) VALUES (4, 4);

INSERT INTO orders (country, date, first_name, last_name, status, total) VALUES ("canada", "2022-04-07", "eric", "kwok", 2, 56.99);
INSERT INTO orders (country, date, first_name, last_name, status, total) VALUES ("canada", "2022-06-07", "eric", "kwok", 2, 136.98);
INSERT INTO orders (country, date, first_name, last_name, status, total) VALUES ("canada", "2022-12-07", "eric", "kwok", 2, 136.98);
INSERT INTO orders (country, date, first_name, last_name, status, total) VALUES ("canada", "2022-01-07", "eric", "kwok", 2, 136.98);

INSERT INTO order_product_ids (order_id, product_ids) VALUES (1, 1);
INSERT INTO order_product_ids (order_id, product_ids) VALUES (2, 2);
INSERT INTO order_product_ids (order_id, product_ids) VALUES (2, 5);
INSERT INTO order_product_ids (order_id, product_ids) VALUES (3, 2);
INSERT INTO order_product_ids (order_id, product_ids) VALUES (3, 4);
INSERT INTO order_product_ids (order_id, product_ids) VALUES (4, 3);
INSERT INTO order_product_ids (order_id, product_ids) VALUES (4, 6);

INSERT INTO visit_event (date, event, ip_address) VALUES ("2022-04-07", "1.27.0.0.0", 3);
INSERT INTO visit_event (date, event, ip_address) VALUES ("2022-04-07", "1.27.0.0.0", 3);
INSERT INTO visit_event (date, event, ip_address) VALUES ("2022-04-07", "1.27.0.0.0", 3);
INSERT INTO visit_event (date, event, ip_address) VALUES ("2022-04-07", "1.27.0.0.0", 2);
INSERT INTO visit_event (date, event, ip_address) VALUES ("2022-04-07", "1.27.0.0.0", 2);
INSERT INTO visit_event (date, event, ip_address) VALUES ("2022-04-07", "1.27.0.0.0", 2);
INSERT INTO visit_event (date, event, ip_address) VALUES ("2022-04-07", "1.27.0.0.0", 2);
INSERT INTO visit_event (date, event, ip_address) VALUES ("2022-04-07", "1.27.0.0.0", 1);
INSERT INTO visit_event (date, event, ip_address) VALUES ("2022-04-07", "1.27.0.0.0", 1);
INSERT INTO visit_event (date, event, ip_address) VALUES ("2022-04-07", "1.27.0.0.0", 1);
INSERT INTO visit_event (date, event, ip_address) VALUES ("2022-04-07", "1.27.0.0.0", 1);
INSERT INTO visit_event (date, event, ip_address) VALUES ("2022-04-07", "1.27.0.0.0", 1);
