
package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import model.User;
import util.DatabaseConnection;

public class UserDAO {

    public boolean insertUser(User user) {

        String sql = "INSERT INTO users (name, email, password, role) VALUES (?, ?, ?, ?)";

        try {

            Connection connection = DatabaseConnection.getConnection();

            PreparedStatement statement = connection.prepareStatement(sql);

            statement.setString(1, user.getName());
            statement.setString(2, user.getEmail());
            statement.setString(3, user.getPassword());
            statement.setString(4, user.getRole());

            int rowsInserted = statement.executeUpdate();

            statement.close();
            connection.close();

            return rowsInserted > 0;

        } catch (SQLException e) {

            System.out.println("User insertion failed!");
            e.printStackTrace();

            return false;
        }
    }

    public void getAllUsers() {

        String sql = "SELECT * FROM users";

        try {

            Connection connection = DatabaseConnection.getConnection();

            PreparedStatement statement = connection.prepareStatement(sql);

            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {

                int id = resultSet.getInt("id");
                String name = resultSet.getString("name");
                String email = resultSet.getString("email");
                String role = resultSet.getString("role");

                System.out.println("ID: " + id);
                System.out.println("Name: " + name);
                System.out.println("Email: " + email);
                System.out.println("Role: " + role);
                System.out.println("--------------------------");
            }

            resultSet.close();
            statement.close();
            connection.close();

        } catch (SQLException e) {

            System.out.println("Failed to retrieve users!");
            e.printStackTrace();
        }
    }

    public User findUserByEmail(String email) {

        String sql = "SELECT * FROM users WHERE email = ?";

        try {

            Connection connection = DatabaseConnection.getConnection();

            PreparedStatement statement = connection.prepareStatement(sql);

            statement.setString(1, email);

            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {

                int id = resultSet.getInt("id");
                String name = resultSet.getString("name");
                String userEmail = resultSet.getString("email");
                String password = resultSet.getString("password");
                String role = resultSet.getString("role");

                User user = new User(
                    id,
                    name,
                    userEmail,
                    password,
                    role
                );

                resultSet.close();
                statement.close();
                connection.close();

                return user;
            }

            resultSet.close();
            statement.close();
            connection.close();

            return null;

        } catch (SQLException e) {

            System.out.println("Failed to find user!");
            e.printStackTrace();

            return null;
        }
    }
}
