package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import model.Service;
import util.DatabaseConnection;

public class ServiceDAO {

    public void getAllServices() {

        String sql = "SELECT * FROM services";

        try {

            Connection connection = DatabaseConnection.getConnection();

            PreparedStatement statement = connection.prepareStatement(sql);

            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {

                int id = resultSet.getInt("id");
                String serviceName = resultSet.getString("service_name");

                Service service = new Service(
                    id,
                    serviceName
                );

                System.out.println("Service ID: " + service.getId());
                System.out.println("Service Name: " + service.getServiceName());
                System.out.println("--------------------------");
            }

            resultSet.close();
            statement.close();
            connection.close();

        } catch (SQLException e) {

            System.out.println("Failed to retrieve services!");
            e.printStackTrace();
        }
    }

    public Service getServiceById(int serviceId) {

        String sql = "SELECT * FROM services WHERE id = ?";

        try {

            Connection connection = DatabaseConnection.getConnection();

            PreparedStatement statement = connection.prepareStatement(sql);

            statement.setInt(1, serviceId);

            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {

                int id = resultSet.getInt("id");
                String serviceName = resultSet.getString("service_name");

                Service service = new Service(
                    id,
                    serviceName
                );

                resultSet.close();
                statement.close();
                connection.close();

                return service;
            }

            resultSet.close();
            statement.close();
            connection.close();

        } catch (SQLException e) {

            System.out.println("Failed to retrieve service!");
            e.printStackTrace();
        }

        return null;
    }
}