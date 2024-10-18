/* 
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Other/SQLTemplate.sql to edit this template
 */
/**
 * Author:  ethan
 * Created: 18 Oct 2024
 */

CREATE TABLE users
(
    user_id INTEGER NOT NULL GENERATED ALWAYS AS IDENTITY (START WITH 1, INCREMENT BY 1),
    user_email VARCHAR(255) NOT NULL,
    user_name VARCHAR(255) NOT NULL,
    user_password VARCHAR(255) NOT NULL,
    user_key VARCHAR(255),
    PRIMARY KEY (user_id)
);

CREATE TABLE favourites
(
    favourite_id INTEGER NOT NULL GENERATED ALWAYS AS IDENTITY (START WITH 1, INCREMENT BY 1),
    user_id INTEGER NOT NULL,
    city_name VARCHAR(255),
    PRIMARY KEY (favourite_id),
    FOREIGN KEY (user_id) REFERENCES users(user_id) ON DELETE CASCADE
);

CREATE TABLE posts
(
    post_id INTEGER NOT NULL GENERATED ALWAYS AS IDENTITY (START WITH 1, INCREMENT BY 1),
    user_id INTEGER NOT NULL,
    post_content VARCHAR(255) NOT NULL,
    post_created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    post_updated_at TIMESTAMP DEFAULT NULL,
    PRIMARY KEY (post_id),
    FOREIGN KEY (user_id) REFERENCES users(user_id) ON DELETE CASCADE
);

CREATE TABLE reviews
(
    review_id INTEGER NOT NULL GENERATED ALWAYS AS IDENTITY (START WITH 1, INCREMENT BY 1),
    user_id INTEGER NOT NULL,
    review_content VARCHAR(255) NOT NULL,
    review_city VARCHAR(255) NOT NULL,
    review_created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    review_updated_at TIMESTAMP DEFAULT NULL,
    review_rating INTEGER NOT NULL,
    PRIMARY KEY (review_id),
    FOREIGN KEY (user_id) REFERENCES users(user_id) ON DELETE CASCADE
);