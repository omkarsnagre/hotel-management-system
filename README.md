# Hotel Management System 🏨

## Overview 🌟
Welcome to the Hotel Management System! 🎉  
This is a simple Java application that helps manage hotel reservations.  
Whether you're booking a room or checking on existing reservations, this system has got you covered!

## Key Features 🔑
- **Create New Reservations**: Quickly add a new guest reservation.
- **View Reservations**: Check the list of current reservations.
- **Update Reservations**: Modify existing reservation details.
- **Delete Reservations**: Remove a reservation if needed.
- **Get Room Number**: Retrieve room information using reservation details.

## Tech Stack ⚙️
This project is built using:
- **Java**: For the main application logic.
- **MySQL**: To handle our database needs.
- **JDBC**: For connecting Java with the MySQL database.

## Getting Started 🚀
Want to run this on your own machine? Here’s how you can do it:

1. Clone the repo:
    ```bash
    git clone https://github.com/omkarsnagre/hotel-management-system.git
    ```

2. Set up MySQL:
   - Create a new database named `Hotel_management`.
   ```sql
   CREATE TABLE reservations (
       reservation_id INT NOT NULL AUTO_INCREMENT PRIMARY KEY,  
       guest_name VARCHAR(255) NOT NULL,                        
       room_number INT NOT NULL,                               
       contact_number VARCHAR(10) NOT NULL,                    
       reservation_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP    
   );
3. Configure your MySQL database settings in the HotelReservationSystem.java file:
 ```java
private static final String DB_URL = "jdbc:mysql://localhost:3306/Hotel_management"; // Database URL
private static final String DB_USER = "root"; // MySQL username
private static final String DB_PASSWORD = "root"; // MySQL password
```
4. Compile and run the application.

## Usage 📋
  Upon running the application, you'll be presented with a menu to choose your desired operation (reservation, viewing, editing, or exiting).
  Follow the prompts to input reservation details, view current reservations, edit existing bookings, and more.

## Contributing 🤝
Contributions are welcome! Feel free to open issues and pull requests for bug fixes, enhancements, or new features.

## Acknowledgments 🙏
Special thanks to all contributors and supporters of the Hotel Management System project.

## Contact 📫
I’d love to see your improvements! If you have any questions or just want to chat, you can reach me at omkarnagre777@gmail.com. Let’s connect!

## Happy booking! 🌆
