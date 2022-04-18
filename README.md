# EECS4413-Backend
Course Project for EECS4413

## Run Locally
1. Navigate to the Java project `shopcart-backend`
2. Build the docker image `docker build -t .`
3. Create a `.env` file with the following values to connect the backend service to the database
```env
PSCALE_DB_NAME=<MySQL_DB_NAME>
PSCALE_USERNAME=<MySQL_DB_USERNAME>
PSCALE_DB_URL=<MySQL_DB_CONNECTION_URL>
PSCALE_PASSWORD=<MySQL_DB_PASSWORD>
```
4. Run the previously built docker image `docker run -p 8080:8080 --env-file .env shopcart-backend`
5. The backend is successfully deployed

## API Endpoints
### Analytics
All analytics endpoints are prefixed with `/api/analytics`

- `GET /monthly/items`
  - returns JSON object mapping months to items sold
- `GET /website/usage`
  - returns JSON object containing page view Analytics
- `POST /website/usage`
  - accepts JSON object about where the page visit happened
- `DELETE /website/usage`
  - deletes website visit event

### Orders
All ordering endpoints are prefixed with `/api/orders`

- `GET`
  - returns all orders regardless of status in a JSON payload
- `GET /{order_id}`
  - returns order as JSON object
- `DELETE /{order_id}`
  - deletes order object from database from a request parameter order-id
- `POST /process`
  - accepts JSON payload of an order and adds that order to database
- `PUT /submit`
  - accepts JSON payload of an order and changes its status based on payment status to `/DummyPaymentService`

### Products
All products endpoints are prefixed with `/api/products`

- `GET`
  - returns JSON list of all product objects in database
- `GET /{product_id}`
  - returns product object details from a given product-id
- `POST`
  - accepts JSON object of a product and adds it to database
- `PUT /{product_id}`
  - accepts JSON object to update existing product objects
- `DELETE /{product_id}`
  - deletes product object from database by product-id

### Reviews
All reviews endpoints are prefixed with `/api/reviews`

- `GET /product/{product_id}`
  - gets reviews for a given product-id
- `GET /{review_id}`
  - gets review object for a given review-id
- `POST`
  - accepts a review JSON object and persists it to the database
- `PUT /{review_id}`
  - updates review based on a given review-id
- `DELETE /{review_id}`
  - deletes the review object based on a given review-id

### Users
All authentication and user controller endpoints are prefixed with `/api`

- `POST /authenticate`
  - accepts an AuthRequest object containing username and password, returns a JWT token on successful authentication
- `GET /users`
  - returns a JSON list of all user objects in database
- `POST /register`
  - accepts a `RegistrationRequest` object which persists the to-be registered user in the database

## Populate Database with Dummy Data
```sql
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
```
