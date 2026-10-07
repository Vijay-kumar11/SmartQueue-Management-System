package model;

import java.sql.Timestamp;

public class QueueTicket {

    private int id;
    private int tokenNumber;
    private int userId;
    private int serviceId;
    private String status;
    private Timestamp createdAt;

    public QueueTicket(int id, int tokenNumber, int userId, int serviceId,
                       String status, Timestamp createdAt) {

        this.id = id;
        this.tokenNumber = tokenNumber;
        this.userId = userId;
        this.serviceId = serviceId;
        this.status = status;
        this.createdAt = createdAt;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getTokenNumber() {
        return tokenNumber;
    }

    public void setTokenNumber(int tokenNumber) {
        this.tokenNumber = tokenNumber;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getServiceId() {
        return serviceId;
    }

    public void setServiceId(int serviceId) {
        this.serviceId = serviceId;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Timestamp getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Timestamp createdAt) {
        this.createdAt = createdAt;
    }
}