package hotel.Reservation.System;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Scanner;

public class MainHotelReservation {

	// Database connection details
	private static final String url = "jdbc:mysql://localhost:3306/Hotel_management";
	private static final String username="root";
	private static final String password="root";
		
	public static void main(String[] args)throws ClassNotFoundException, SQLException {		
		try {
			// Loading MySQL JDBC driver
			Class.forName("com.mysql.cj.jdbc.Driver");
		} catch(ClassNotFoundException e) {
			System.out.println(e.getMessage());
		}
		
		try {
			// Establishing Java connection with the database
			Connection connection = DriverManager.getConnection(url,username,password);
			while(true) {
				System.out.println();
				System.out.println("**HOTEL MANAGEMENT SYSTEM**");
				Scanner scanner =new Scanner(System.in);
				// Displaying menu options
				System.out.println("1. NEW RESERVATION");
				System.out.println("2. VIEW RESERVATION");
				System.out.println("3. GET ROOM NUMBER");
				System.out.println("4. UPDATE RESERVATION");
				System.out.println("5. DELETE RESERVATION");
				System.out.println("0. EXIT");
				System.out.println("CHOOSE ANY OPTION.!!");
				int choice=scanner.nextInt();
				switch(choice) {
				case 1:
					// New room reservation
					reserveRoom(connection,scanner);
					break;
					
				case 2:
					// View all reservations
					viewReservations(connection);
					break;
					
				case 3:
					// Get room number for a specific reservation
					getRoomNumber(connection,scanner);
					break;
					
				case 4:
					// Update an existing reservation
					updateReservation(connection,scanner);
					break;
					
				case 5:
					// Delete Existing reservation
					deleteReservation(connection,scanner);
					break;
				case 0:
					// Exit system
					exit();
					scanner.close();
					return;
				default:
					System.out.println("INVALID CHOICE,TRY AGAIN.!!");
				}	
			}
			
		} catch(SQLException e) {
			System.out.println(e.getMessage());
		} catch(InterruptedException e) {
			throw new RuntimeException(e);
		}
	}

	// Method to reserve a room
	private static void reserveRoom(Connection connection, Scanner scanner) {
		try {
			System.out.print("ENTER GUEST NAME : ");
			String guestName=scanner.next();
			scanner.nextLine(); // Consume newline
			System.out.print("ENTER ROOM NUMBER : ");
			int roomNumber=scanner.nextInt();
			System.out.print("ENTER CONTACT NUMBER : ");
			String contactNumber=scanner.next();
			
			// SQL query to insert reservation into database
			String sql="INSERT INTO reservations (guest_name,room_number,contact_number) " +
			"VALUES('"+guestName+"',"+roomNumber+",'"+contactNumber+"')";
			
			// Execute the query
			try (Statement statement=connection.createStatement()) {
				int affectedRows=statement.executeUpdate(sql);
				if(affectedRows>0) {
					System.out.println("RESERVATION SUCCESSFULL.!!");	
				} else {
					System.out.println("RESERVATION FAILED.!!");
				}
			}
			
		} catch(SQLException e) {
			e.printStackTrace();
		}
	}
		
	// Method to view all reservations
	private static void viewReservations(Connection connection)throws SQLException {
		// SQL query to fetch all reservations 
		String sql="SELECT reservation_id,guest_name,room_number,contact_number,reservation_date FROM reservations";
		try (Statement statement=connection.createStatement(); ResultSet resultSet=statement.executeQuery(sql)) {
			System.out.println("CURRENT RESERVATIONS : ");
			System.out.println("+----------------+-----------------+---------------+----------------------+-------------------------+");
			System.out.println("| Reservation ID | Guest           | Room Number   | Contact Number       | Reservation Date        |");
			System.out.println("+----------------+-----------------+---------------+----------------------+-------------------------+");

			// Display each reservation in formatted output
			while(resultSet.next()) {
				int reservationId=resultSet.getInt("reservation_id");
				String guestName=resultSet.getString("guest_name");
				int roomNumber=resultSet.getInt("room_number");
				String contactNumber=resultSet.getString("contact_number");
				String reservationDate=resultSet.getTimestamp("reservation_date").toString();
				
				System.out.printf("| %-14d | %-15s | %-13d | %-20s | %-23s |\n", reservationId, guestName, roomNumber, contactNumber, reservationDate);
			}
			System.out.println("+----------------+-----------------+---------------+----------------------+-------------------------+");
		}
	}
	
	// Method to get room number using reservation ID and guest name
	private static void getRoomNumber(Connection connection, Scanner scanner) {
		try {
			System.out.print("ENTER RESERVATION ID : ");
			int reservationId=scanner.nextInt();
			System.out.print("ENTER GUEST NAME : ");
			String guestName=scanner.next();
			
			// SQL query to fetch room number based on reservation ID and guest name
			String sql="SELECT room_number FROM reservations WHERE reservation_id = " +reservationId + " AND guest_name='" + guestName+"'";
			try(Statement statement =connection.createStatement(); ResultSet resultSet=statement.executeQuery(sql)) {
				if(resultSet.next()) {
					int roomNumber = resultSet.getInt("room_number");
					System.out.println("ROOM NUMBER FOR RESERVATION ID "+ reservationId+ " AND GUEST "+guestName+" is "+roomNumber);
				} else {
					System.out.println("RESERVATION NOT FOUND FOR GIVEN ID AND NAME..!!");
				}
			}
		} catch(SQLException e) {
			e.printStackTrace();
		}
	}
	
	// Method to update an existing reservation
	private static void updateReservation(Connection connection, Scanner scanner) {
		try {
			System.out.print("ENTER RESERVATION ID TO UPDATE: ");
			int reservationId=scanner.nextInt();
			scanner.nextLine(); // Consume newline
			
			// To Check if the reservation exists before updating
			if(!reservationExists(connection,reservationId)) {
				System.out.println("RESERVATION NOT FOUND FOR THE GIVEN ID.");
				return;
			}
			
			// Get new reservation details from user
			System.out.print("ENTER NEW GUEST NAME : ");
			String newGuestName=scanner.nextLine();
			System.out.print("ENTER NEW ROOM NUMBER : ");
			int newRoomNumber=scanner.nextInt();
			System.out.print("ENTER NEW CONTACT NUMBER : ");
			String newContactNumber=scanner.next();
			
			// SQL query to update reservation details
			String sql="UPDATE reservations SET guest_name = '"+newGuestName+"', room_number = "+ newRoomNumber +", contact_number = '"+newContactNumber+"' WHERE reservation_id= "+reservationId;
			
			// Execute the query
			try (Statement statement=connection.createStatement()) {
				int affectedRows=statement.executeUpdate(sql);
				if(affectedRows>0) {
					System.out.println("RESERVATION UPDATED SUCCESSFULLY..!!");
				} else {
					System.out.println("RESERVATION UPDATE FAILED..!!");
				}
			}
		} catch(SQLException e) {
			e.printStackTrace();
		}
	}
		
	// Method to delete a reservation
	private static void deleteReservation(Connection connection, Scanner scanner) {
		try {
			System.out.print("ENTER RESERVATION ID TO DELETE : ");
			int reservationId=scanner.nextInt();
			
			// Check if reservation exists before deletion
			if(!reservationExists(connection,reservationId)) {
				System.out.println("RESERVATION ID NOT FOUND..!");
				return;
			}
			
			// SQL query to delete the reservation
			String sql="DELETE FROM reservations WHERE reservation_id = "+ reservationId;
			
			// Execute the query
			try (Statement statement =connection.createStatement()) {
				int affectedRows=statement.executeUpdate(sql);
				if(affectedRows>0) {
					System.out.println("RESERVATION DELETED SUCCESSFULLY..!!");
				} else {
					System.out.println("RESERVATION DELETION FAILED..");
				}
			}
		} catch(SQLException e) {
			e.printStackTrace();
		}
	}
	
	// Helper method to check if a reservation exists based on reservation ID
	private static boolean reservationExists(Connection connection, int reservationId) {
		try {
			String sql="SELECT reservation_id FROM reservations WHERE reservation_id = "+reservationId;
			try(Statement statement=connection.createStatement(); ResultSet resultSet=statement.executeQuery(sql)) {
				return resultSet.next();
			}
		} catch(SQLException e) {
			e.printStackTrace();
			return false;
		}
	}

	// Method to handle system exit with a count down animation
	public static void exit() throws InterruptedException {
		System.out.print("EXITING SYSTEM");
		int i=5;
		while(i!=0) {
			System.out.print(".");
			Thread.sleep(450);
			i--;
		}
		System.out.println();
		System.out.println("THANK YOU FOR USING HOTEL RESERVATION SYSTEM..!!");
	}
}
