CREATE TABLE coworking_space (
    space_id SERIAL PRIMARY KEY,
    type VARCHAR(255),
    price DECIMAL(10, 2),
    is_available BOOLEAN
);

CREATE TABLE reservations (
    reservation_id SERIAL PRIMARY KEY,
    customer_name VARCHAR(255),
    space_id INT,
    date DATE,
    start_time TIME,
    end_time TIME,
    FOREIGN KEY (space_id) REFERENCES coworking_space (space_id)
);
