CREATE TABLE delivery (
    delivery_id BIGINT PRIMARY KEY AUTO_INCREMENT,
    package_quantity INT NOT NULL,
    delivery_deadline TIMESTAMP NOT NULL,
    status VARCHAR(20) NOT NULL DEFAULT 'PENDING',
    customer_id BIGINT NOT NULL,
    address_delivery_id BIGINT NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    FOREIGN KEY (customer_id) REFERENCES customer(customer_id),
    FOREIGN KEY (address_delivery_id) REFERENCES address_delivery(address_delivery_id)
);

