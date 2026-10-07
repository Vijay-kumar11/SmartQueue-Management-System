package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import model.QueueTicket;
import util.DatabaseConnection;

public class QueueDAO {

    public boolean createTicket(QueueTicket ticket) {

        String sql = "INSERT INTO queue_tickets " +
                     "(token_number, user_id, service_id, status) " +
                     "VALUES (?, ?, ?, ?)";

        try {

            Connection connection =
                DatabaseConnection.getConnection();

            PreparedStatement statement =
                connection.prepareStatement(sql);

            statement.setInt(1, ticket.getTokenNumber());
            statement.setInt(2, ticket.getUserId());
            statement.setInt(3, ticket.getServiceId());
            statement.setString(4, ticket.getStatus());

            int rowsInserted =
                statement.executeUpdate();

            statement.close();
            connection.close();

            return rowsInserted > 0;

        } catch (SQLException e) {

            System.out.println(
                "Queue ticket creation failed!"
            );

            e.printStackTrace();

            return false;
        }
    }

    public void getAllTickets() {

        String sql =
            "SELECT * FROM queue_tickets";

        try {

            Connection connection =
                DatabaseConnection.getConnection();

            PreparedStatement statement =
                connection.prepareStatement(sql);

            ResultSet resultSet =
                statement.executeQuery();

            while (resultSet.next()) {

                QueueTicket ticket =
                    new QueueTicket(
                        resultSet.getInt("id"),
                        resultSet.getInt("token_number"),
                        resultSet.getInt("user_id"),
                        resultSet.getInt("service_id"),
                        resultSet.getString("status"),
                        resultSet.getTimestamp("created_at")
                    );

                System.out.println(
                    "Ticket ID: " + ticket.getId()
                );

                System.out.println(
                    "Token Number: " +
                    ticket.getTokenNumber()
                );

                System.out.println(
                    "User ID: " +
                    ticket.getUserId()
                );

                System.out.println(
                    "Service ID: " +
                    ticket.getServiceId()
                );

                System.out.println(
                    "Status: " +
                    ticket.getStatus()
                );

                System.out.println(
                    "Created At: " +
                    ticket.getCreatedAt()
                );

                System.out.println(
                    "--------------------------"
                );
            }

            resultSet.close();
            statement.close();
            connection.close();

        } catch (SQLException e) {

            System.out.println(
                "Failed to retrieve tickets!"
            );

            e.printStackTrace();
        }
    }

    public List<QueueTicket> getTicketsByUserId(
        int userId
    ) {

        List<QueueTicket> tickets =
            new ArrayList<>();

        String sql =
            "SELECT * FROM queue_tickets " +
            "WHERE user_id = ?";

        try {

            Connection connection =
                DatabaseConnection.getConnection();

            PreparedStatement statement =
                connection.prepareStatement(sql);

            statement.setInt(1, userId);

            ResultSet resultSet =
                statement.executeQuery();

            while (resultSet.next()) {

                QueueTicket ticket =
                    new QueueTicket(
                        resultSet.getInt("id"),
                        resultSet.getInt("token_number"),
                        resultSet.getInt("user_id"),
                        resultSet.getInt("service_id"),
                        resultSet.getString("status"),
                        resultSet.getTimestamp("created_at")
                    );

                tickets.add(ticket);
            }

            resultSet.close();
            statement.close();
            connection.close();

        } catch (SQLException e) {

            System.out.println(
                "Failed to retrieve customer tickets!"
            );

            e.printStackTrace();
        }

        return tickets;
    }

    public boolean updateTicketStatus(
        int ticketId,
        String newStatus
    ) {

        String sql =
            "UPDATE queue_tickets " +
            "SET status = ? " +
            "WHERE id = ?";

        try {

            Connection connection =
                DatabaseConnection.getConnection();

            PreparedStatement statement =
                connection.prepareStatement(sql);

            statement.setString(1, newStatus);
            statement.setInt(2, ticketId);

            int rowsUpdated =
                statement.executeUpdate();

            statement.close();
            connection.close();

            return rowsUpdated > 0;

        } catch (SQLException e) {

            System.out.println(
                "Ticket status update failed!"
            );

            e.printStackTrace();

            return false;
        }
    }

    public int getNextTokenNumber(
        int serviceId
    ) {

        String sql =
            "SELECT MAX(token_number) " +
            "FROM queue_tickets " +
            "WHERE service_id = ?";

        try {

            Connection connection =
                DatabaseConnection.getConnection();

            PreparedStatement statement =
                connection.prepareStatement(sql);

            statement.setInt(1, serviceId);

            ResultSet resultSet =
                statement.executeQuery();

            if (resultSet.next()) {

                int maxToken =
                    resultSet.getInt(1);

                if (resultSet.wasNull()) {

                    resultSet.close();
                    statement.close();
                    connection.close();

                    return 1;
                }

                resultSet.close();
                statement.close();
                connection.close();

                return maxToken + 1;
            }

            resultSet.close();
            statement.close();
            connection.close();

        } catch (SQLException e) {

            System.out.println(
                "Failed to generate next token number!"
            );

            e.printStackTrace();
        }

        return 1;
    }

    public QueueTicket getTicketById(
        int ticketId
    ) {

        String sql =
            "SELECT * FROM queue_tickets " +
            "WHERE id = ?";

        try {

            Connection connection =
                DatabaseConnection.getConnection();

            PreparedStatement statement =
                connection.prepareStatement(sql);

            statement.setInt(1, ticketId);

            ResultSet resultSet =
                statement.executeQuery();

            if (resultSet.next()) {

                QueueTicket ticket =
                    new QueueTicket(
                        resultSet.getInt("id"),
                        resultSet.getInt("token_number"),
                        resultSet.getInt("user_id"),
                        resultSet.getInt("service_id"),
                        resultSet.getString("status"),
                        resultSet.getTimestamp("created_at")
                    );

                resultSet.close();
                statement.close();
                connection.close();

                return ticket;
            }

            resultSet.close();
            statement.close();
            connection.close();

            return null;

        } catch (SQLException e) {

            System.out.println(
                "Failed to retrieve ticket!"
            );

            e.printStackTrace();

            return null;
        }
    }

    public QueueTicket getTicketByToken(
        int serviceId,
        int tokenNumber
    ) {

        String sql =
            "SELECT * FROM queue_tickets " +
            "WHERE service_id = ? " +
            "AND token_number = ?";

        try {

            Connection connection =
                DatabaseConnection.getConnection();

            PreparedStatement statement =
                connection.prepareStatement(sql);

            statement.setInt(1, serviceId);
            statement.setInt(2, tokenNumber);

            ResultSet resultSet =
                statement.executeQuery();

            if (resultSet.next()) {

                QueueTicket ticket =
                    new QueueTicket(
                        resultSet.getInt("id"),
                        resultSet.getInt("token_number"),
                        resultSet.getInt("user_id"),
                        resultSet.getInt("service_id"),
                        resultSet.getString("status"),
                        resultSet.getTimestamp("created_at")
                    );

                resultSet.close();
                statement.close();
                connection.close();

                return ticket;
            }

            resultSet.close();
            statement.close();
            connection.close();

            return null;

        } catch (SQLException e) {

            System.out.println(
                "Failed to retrieve ticket by token!"
            );

            e.printStackTrace();

            return null;
        }
    }

    public int getQueuePosition(
        int ticketId
    ) {

        String statusSql =
            "SELECT status " +
            "FROM queue_tickets " +
            "WHERE id = ?";

        String positionSql =
            "SELECT COUNT(*) " +
            "FROM queue_tickets t1 " +
            "WHERE t1.service_id = " +
            "(SELECT service_id " +
            "FROM queue_tickets " +
            "WHERE id = ?) " +
            "AND t1.status = 'WAITING' " +
            "AND t1.token_number < " +
            "(SELECT token_number " +
            "FROM queue_tickets " +
            "WHERE id = ?)";

        try {

            Connection connection =
                DatabaseConnection.getConnection();

            PreparedStatement statusStatement =
                connection.prepareStatement(
                    statusSql
                );

            statusStatement.setInt(1, ticketId);

            ResultSet statusResult =
                statusStatement.executeQuery();

            if (!statusResult.next()) {

                statusResult.close();
                statusStatement.close();
                connection.close();

                return 0;
            }

            String status =
                statusResult.getString("status");

            statusResult.close();
            statusStatement.close();

            if (!status.equals("WAITING")) {

                connection.close();

                return 0;
            }

            PreparedStatement positionStatement =
                connection.prepareStatement(
                    positionSql
                );

            positionStatement.setInt(1, ticketId);
            positionStatement.setInt(2, ticketId);

            ResultSet positionResult =
                positionStatement.executeQuery();

            if (positionResult.next()) {

                int ticketsBefore =
                    positionResult.getInt(1);

                positionResult.close();
                positionStatement.close();
                connection.close();

                return ticketsBefore + 1;
            }

            positionResult.close();
            positionStatement.close();
            connection.close();

        } catch (SQLException e) {

            System.out.println(
                "Failed to calculate queue position!"
            );

            e.printStackTrace();
        }

        return 0;
    }

    public QueueTicket getNextWaitingTicket(
        int serviceId
    ) {

        String sql =
            "SELECT * FROM queue_tickets " +
            "WHERE service_id = ? " +
            "AND status = 'WAITING' " +
            "ORDER BY token_number ASC " +
            "LIMIT 1";

        try {

            Connection connection =
                DatabaseConnection.getConnection();

            PreparedStatement statement =
                connection.prepareStatement(sql);

            statement.setInt(1, serviceId);

            ResultSet resultSet =
                statement.executeQuery();

            if (resultSet.next()) {

                QueueTicket ticket =
                    new QueueTicket(
                        resultSet.getInt("id"),
                        resultSet.getInt("token_number"),
                        resultSet.getInt("user_id"),
                        resultSet.getInt("service_id"),
                        resultSet.getString("status"),
                        resultSet.getTimestamp("created_at")
                    );

                resultSet.close();
                statement.close();
                connection.close();

                return ticket;
            }

            resultSet.close();
            statement.close();
            connection.close();

            return null;

        } catch (SQLException e) {

            System.out.println(
                "Failed to find next waiting ticket!"
            );

            e.printStackTrace();

            return null;
        }
    }

    public List<QueueTicket> getWaitingTickets(
        int serviceId
    ) {

        List<QueueTicket> tickets =
            new ArrayList<>();

        String sql =
            "SELECT * FROM queue_tickets " +
            "WHERE service_id = ? " +
            "AND status = 'WAITING' " +
            "ORDER BY token_number ASC";

        try {

            Connection connection =
                DatabaseConnection.getConnection();

            PreparedStatement statement =
                connection.prepareStatement(sql);

            statement.setInt(1, serviceId);

            ResultSet resultSet =
                statement.executeQuery();

            while (resultSet.next()) {

                QueueTicket ticket =
                    new QueueTicket(
                        resultSet.getInt("id"),
                        resultSet.getInt("token_number"),
                        resultSet.getInt("user_id"),
                        resultSet.getInt("service_id"),
                        resultSet.getString("status"),
                        resultSet.getTimestamp("created_at")
                    );

                tickets.add(ticket);
            }

            resultSet.close();
            statement.close();
            connection.close();

        } catch (SQLException e) {

            System.out.println(
                "Failed to retrieve waiting queue!"
            );

            e.printStackTrace();
        }

        return tickets;
    }
}