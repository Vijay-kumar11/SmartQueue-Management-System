package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import util.DatabaseConnection;

public class QueueHistoryDAO {

    public boolean addHistory(int ticketId, String oldStatus, String newStatus) {

        String sql = "INSERT INTO queue_history " +
                     "(ticket_id, old_status, new_status) " +
                     "VALUES (?, ?, ?)";

        try {

            Connection connection = DatabaseConnection.getConnection();

            PreparedStatement statement = connection.prepareStatement(sql);

            statement.setInt(1, ticketId);
            statement.setString(2, oldStatus);
            statement.setString(3, newStatus);

            int rowsInserted = statement.executeUpdate();

            statement.close();
            connection.close();

            return rowsInserted > 0;

        } catch (SQLException e) {

            System.out.println("Queue history insertion failed!");
            e.printStackTrace();

            return false;
        }
    }

    public void getHistoryByTicketId(int ticketId) {

        String sql = "SELECT * FROM queue_history WHERE ticket_id = ?";

        try {

            Connection connection = DatabaseConnection.getConnection();

            PreparedStatement statement = connection.prepareStatement(sql);

            statement.setInt(1, ticketId);

            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {

                int id = resultSet.getInt("id");
                String oldStatus = resultSet.getString("old_status");
                String newStatus = resultSet.getString("new_status");

                System.out.println("History ID: " + id);
                System.out.println("Ticket ID: " + ticketId);
                System.out.println("Old Status: " + oldStatus);
                System.out.println("New Status: " + newStatus);
                System.out.println("Changed At: " + resultSet.getTimestamp("changed_at"));
                System.out.println("--------------------------");
            }

            resultSet.close();
            statement.close();
            connection.close();

        } catch (SQLException e) {

            System.out.println("Failed to retrieve queue history!");
            e.printStackTrace();
        }
    }
}