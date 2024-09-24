CREATE TABLE reservations (
    reservation_id INT NOT NULL AUTO_INCREMENT PRIMARY KEY,  -- Unique identifier for each reservation
    guest_name VARCHAR(255) NOT NULL,                        -- Name of the guest
    room_number INT NOT NULL,                                -- Room number assigned to the guest
    contact_number VARCHAR(10) NOT NULL,                    -- Contact number of the guest
    reservation_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP    -- Date and time of the reservation
);
