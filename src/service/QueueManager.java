package service;

import java.util.List;

import dao.QueueDAO;
import dao.QueueHistoryDAO;
import dao.ServiceDAO;
import model.QueueTicket;
import model.Service;

public class QueueManager {

    private QueueDAO queueDAO;
    private QueueHistoryDAO historyDAO;
    private ServiceDAO serviceDAO;

    public QueueManager() {

        queueDAO = new QueueDAO();
        historyDAO = new QueueHistoryDAO();
        serviceDAO = new ServiceDAO();
    }

    public boolean generateTicket(int userId, int serviceId) {

        int nextToken =
            queueDAO.getNextTokenNumber(serviceId);

        QueueTicket ticket = new QueueTicket(
            0,
            nextToken,
            userId,
            serviceId,
            "WAITING",
            null
        );

        return queueDAO.createTicket(ticket);
    }

    public QueueTicket generateTicketAndGet(
        int userId,
        int serviceId
    ) {

        int nextToken =
            queueDAO.getNextTokenNumber(serviceId);

        QueueTicket ticket = new QueueTicket(
            0,
            nextToken,
            userId,
            serviceId,
            "WAITING",
            null
        );

        boolean created =
            queueDAO.createTicket(ticket);

        if (created) {

            return queueDAO.getTicketByToken(
                serviceId,
                nextToken
            );
        }

        return null;
    }

    public QueueTicket getTicket(int ticketId) {

        return queueDAO.getTicketById(ticketId);
    }

    public int getQueuePosition(int ticketId) {

        return queueDAO.getQueuePosition(ticketId);
    }

    public QueueTicket getNextWaitingTicket(int serviceId) {

        return queueDAO.getNextWaitingTicket(serviceId);
    }

    public List<QueueTicket> getWaitingTickets(int serviceId) {

        return queueDAO.getWaitingTickets(serviceId);
    }

    public int getWaitingCustomerCount(int serviceId) {

        List<QueueTicket> tickets =
            queueDAO.getWaitingTickets(serviceId);

        return tickets.size();
    }

    public QueueTicket callNextCustomer(int serviceId) {

        QueueTicket ticket =
            queueDAO.getNextWaitingTicket(serviceId);

        if (ticket != null) {

            String oldStatus =
                ticket.getStatus();

            boolean updated =
                queueDAO.updateTicketStatus(
                    ticket.getId(),
                    "CALLED"
                );

            if (updated) {

                historyDAO.addHistory(
                    ticket.getId(),
                    oldStatus,
                    "CALLED"
                );

                ticket.setStatus("CALLED");

                return ticket;
            }
        }

        return null;
    }

    public boolean startService(int ticketId) {

        QueueTicket ticket =
            queueDAO.getTicketById(ticketId);

        if (ticket == null) {
            return false;
        }

        String oldStatus =
            ticket.getStatus();

        if (!oldStatus.equals("CALLED")) {
            return false;
        }

        boolean updated =
            queueDAO.updateTicketStatus(
                ticketId,
                "SERVING"
            );

        if (updated) {

            historyDAO.addHistory(
                ticketId,
                oldStatus,
                "SERVING"
            );

            return true;
        }

        return false;
    }

    public boolean completeService(int ticketId) {

        QueueTicket ticket =
            queueDAO.getTicketById(ticketId);

        if (ticket == null) {
            return false;
        }

        String oldStatus =
            ticket.getStatus();

        if (!oldStatus.equals("SERVING")) {
            return false;
        }

        boolean updated =
            queueDAO.updateTicketStatus(
                ticketId,
                "COMPLETED"
            );

        if (updated) {

            historyDAO.addHistory(
                ticketId,
                oldStatus,
                "COMPLETED"
            );

            return true;
        }

        return false;
    }

    public boolean skipCustomer(int ticketId) {

        QueueTicket ticket =
            queueDAO.getTicketById(ticketId);

        if (ticket == null) {
            return false;
        }

        String oldStatus =
            ticket.getStatus();

        if (!oldStatus.equals("CALLED")) {
            return false;
        }

        boolean updated =
            queueDAO.updateTicketStatus(
                ticketId,
                "SKIPPED"
            );

        if (updated) {

            historyDAO.addHistory(
                ticketId,
                oldStatus,
                "SKIPPED"
            );

            return true;
        }

        return false;
    }

    public boolean cancelTicket(int ticketId) {

        QueueTicket ticket =
            queueDAO.getTicketById(ticketId);

        if (ticket == null) {
            return false;
        }

        String oldStatus =
            ticket.getStatus();

        if (!oldStatus.equals("WAITING")) {
            return false;
        }

        boolean updated =
            queueDAO.updateTicketStatus(
                ticketId,
                "CANCELLED"
            );

        if (updated) {

            historyDAO.addHistory(
                ticketId,
                oldStatus,
                "CANCELLED"
            );

            return true;
        }

        return false;
    }

    public List<QueueTicket> getCustomerTickets(int userId) {

        return queueDAO.getTicketsByUserId(userId);
    }

    public String getServiceName(int serviceId) {

        Service service =
            serviceDAO.getServiceById(serviceId);

        if (service != null) {

            return service.getServiceName();
        }

        return "Unknown Service";
    }

    public void getTicketHistory(int ticketId) {

        historyDAO.getHistoryByTicketId(ticketId);
    }
}