
import java.util.List;
import java.util.Scanner;

import dao.QueueHistoryDAO;
import dao.QueueDAO;
import dao.ServiceDAO;
import dao.UserDAO;
import model.QueueTicket;
import model.Service;
import model.User;
import service.QueueManager;

public class Main {

    public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in);

        UserDAO userDAO = new UserDAO();
        ServiceDAO serviceDAO = new ServiceDAO();
        QueueDAO queueDAO = new QueueDAO();
        QueueHistoryDAO historyDAO = new QueueHistoryDAO();
        QueueManager queueManager = new QueueManager();

        // =================================
        // SMART QUEUE MANAGEMENT SYSTEM
        // =================================

        System.out.println("=================================");
        System.out.println("   SMART QUEUE MANAGEMENT SYSTEM");
        System.out.println("=================================");
        System.out.println("              LOGIN");
        System.out.println("=================================");

        System.out.print("Enter Email: ");
        String email = scanner.nextLine();

        System.out.print("Enter Password: ");
        String password = scanner.nextLine();

        // =================================
        // FIND USER
        // =================================

        User user =
            userDAO.findUserByEmail(email);

        System.out.println();

        if (user == null) {

            System.out.println(
                "User not found."
            );

            scanner.close();
            return;
        }

        // =================================
        // CHECK PASSWORD
        // =================================

        if (!user.getPassword().equals(password)) {

            System.out.println(
                "Invalid password."
            );

            scanner.close();
            return;
        }

        // =================================
        // LOGIN SUCCESSFUL
        // =================================

        System.out.println(
            "Login Successful!"
        );

        System.out.println(
            "Welcome, " + user.getName() + "!"
        );

        System.out.println(
            "Role: " + user.getRole()
        );

        // =================================
        // CUSTOMER ROLE
        // =================================

        if (user.getRole().equals("CUSTOMER")) {

            boolean customerRunning = true;

            while (customerRunning) {

                System.out.println();
                System.out.println("=================================");
                System.out.println("          CUSTOMER MENU");
                System.out.println("=================================");
                System.out.println("1. View Services");
                System.out.println("2. Generate Queue Ticket");
                System.out.println("3. Check Queue Position");
                System.out.println("4. View My Tickets");
                System.out.println("5. Cancel Ticket");
                System.out.println("6. Exit");
                System.out.println("=================================");

                System.out.print("Enter your choice: ");

                int choice = scanner.nextInt();

                switch (choice) {

                    // =================================
                    // 1. VIEW SERVICES
                    // =================================

                    case 1:

                        System.out.println();

                        System.out.println(
                            "Available Services"
                        );

                        System.out.println(
                            "--------------------------"
                        );

                        serviceDAO.getAllServices();

                        break;

                    // =================================
                    // 2. GENERATE QUEUE TICKET
                    // =================================

                    case 2:

                        System.out.println();

                        System.out.println(
                            "Available Services"
                        );

                        System.out.println(
                            "--------------------------"
                        );

                        serviceDAO.getAllServices();

                        System.out.println();

                        System.out.print(
                            "Enter Service ID: "
                        );

                        int serviceId =
                            scanner.nextInt();

                        Service selectedService =
                            serviceDAO.getServiceById(
                                serviceId
                            );

                        if (selectedService == null) {

                            System.out.println();

                            System.out.println(
                                "Invalid Service ID."
                            );

                            break;
                        }

                        QueueTicket ticket =
                            queueManager.generateTicketAndGet(
                                user.getId(),
                                serviceId
                            );

                        if (ticket != null) {

                            System.out.println();

                            System.out.println(
                                "Queue ticket generated successfully!"
                            );

                            System.out.println(
                                "Ticket ID: "
                                + ticket.getId()
                            );

                            System.out.println(
                                "Token Number: "
                                + ticket.getTokenNumber()
                            );

                            System.out.println(
                                "Service: "
                                + selectedService.getServiceName()
                            );

                            System.out.println(
                                "Status: "
                                + ticket.getStatus()
                            );

                            System.out.println(
                                "Queue Position: "
                                + queueManager.getQueuePosition(
                                    ticket.getId()
                                )
                            );

                        } else {

                            System.out.println();

                            System.out.println(
                                "Failed to generate queue ticket."
                            );
                        }

                        break;

                    // =================================
                    // 3. CHECK QUEUE POSITION
                    // =================================

                    case 3:

                        System.out.println();

                        System.out.print(
                            "Enter Ticket ID: "
                        );

                        int ticketId =
                            scanner.nextInt();

                        QueueTicket customerTicket =
                            queueManager.getTicket(
                                ticketId
                            );

                        if (customerTicket == null) {

                            System.out.println();

                            System.out.println(
                                "Ticket not found."
                            );

                            break;
                        }

                        if (customerTicket.getUserId()
                            != user.getId()) {

                            System.out.println();

                            System.out.println(
                                "You can only check your own ticket."
                            );

                            break;
                        }

                        int position =
                            queueManager.getQueuePosition(
                                ticketId
                            );

                        System.out.println();

                        System.out.println(
                            "Ticket ID: "
                            + customerTicket.getId()
                        );

                        System.out.println(
                            "Token Number: "
                            + customerTicket.getTokenNumber()
                        );

                        System.out.println(
                            "Service: "
                            + queueManager.getServiceName(
                                customerTicket.getServiceId()
                            )
                        );

                        System.out.println(
                            "Status: "
                            + customerTicket.getStatus()
                        );

                        if (customerTicket.getStatus()
                            .equals("WAITING")) {

                            System.out.println(
                                "Queue Position: "
                                + position
                            );

                        } else {

                            System.out.println(
                                "Queue Position: Not applicable"
                            );
                        }

                        break;

                    // =================================
                    // 4. VIEW MY TICKETS
                    // =================================

                    case 4:

                        System.out.println();

                        System.out.println(
                            "My Queue Tickets"
                        );

                        System.out.println(
                            "--------------------------"
                        );

                        List<QueueTicket> tickets =
                            queueManager.getCustomerTickets(
                                user.getId()
                            );

                        if (tickets.isEmpty()) {

                            System.out.println(
                                "No tickets found."
                            );

                        } else {

                            for (QueueTicket myTicket : tickets) {

                                System.out.println(
                                    "Ticket ID: "
                                    + myTicket.getId()
                                );

                                System.out.println(
                                    "Token Number: "
                                    + myTicket.getTokenNumber()
                                );

                                System.out.println(
                                    "Service: "
                                    + queueManager.getServiceName(
                                        myTicket.getServiceId()
                                    )
                                );

                                System.out.println(
                                    "Status: "
                                    + myTicket.getStatus()
                                );

                                System.out.println(
                                    "Created At: "
                                    + myTicket.getCreatedAt()
                                );

                                System.out.println(
                                    "--------------------------"
                                );
                            }
                        }

                        break;

                    // =================================
                    // 5. CANCEL TICKET
                    // =================================

                    case 5:

                        System.out.println();

                        System.out.print(
                            "Enter Ticket ID: "
                        );

                        int cancelTicketId =
                            scanner.nextInt();

                        QueueTicket ticketToCancel =
                            queueManager.getTicket(
                                cancelTicketId
                            );

                        if (ticketToCancel == null) {

                            System.out.println();

                            System.out.println(
                                "Ticket not found."
                            );

                            break;
                        }

                        if (ticketToCancel.getUserId()
                            != user.getId()) {

                            System.out.println();

                            System.out.println(
                                "You can only cancel your own ticket."
                            );

                            break;
                        }

                        boolean cancelled =
                            queueManager.cancelTicket(
                                cancelTicketId
                            );

                        System.out.println();

                        if (cancelled) {

                            System.out.println(
                                "Ticket cancelled successfully."
                            );

                        } else {

                            System.out.println(
                                "Ticket cannot be cancelled."
                            );
                        }

                        break;

                    // =================================
                    // 6. EXIT
                    // =================================

                    case 6:

                        customerRunning = false;

                        System.out.println();

                        System.out.println(
                            "Thank you for using Smart Queue Management System!"
                        );

                        break;

                    // =================================
                    // INVALID CHOICE
                    // =================================

                    default:

                        System.out.println();

                        System.out.println(
                            "Invalid choice. Please try again."
                        );
                }
            }

        // =================================
        // STAFF ROLE
        // =================================

        } else if (user.getRole().equals("STAFF")) {

            boolean staffRunning = true;

            while (staffRunning) {

                System.out.println();
                System.out.println("=================================");
                System.out.println("            STAFF MENU");
                System.out.println("=================================");
                System.out.println("1. View Waiting Queue");
                System.out.println("2. Call Next Customer");
                System.out.println("3. Start Service");
                System.out.println("4. Complete Service");
                System.out.println("5. Skip Customer");
                System.out.println("6. View Ticket History");
                System.out.println("7. Exit");
                System.out.println("=================================");

                System.out.print("Enter your choice: ");

                int choice = scanner.nextInt();

                switch (choice) {

                    // =================================
                    // 1. VIEW WAITING QUEUE
                    // =================================

                    case 1:

                        System.out.println();

                        System.out.println(
                            "Available Services"
                        );

                        System.out.println(
                            "--------------------------"
                        );

                        serviceDAO.getAllServices();

                        System.out.println();

                        System.out.print(
                            "Enter Service ID: "
                        );

                        int serviceId =
                            scanner.nextInt();

                        Service selectedService =
                            serviceDAO.getServiceById(
                                serviceId
                            );

                        if (selectedService == null) {

                            System.out.println();

                            System.out.println(
                                "Invalid Service ID."
                            );

                            break;
                        }

                        List<QueueTicket> waitingTickets =
                            queueManager.getWaitingTickets(
                                serviceId
                            );

                        System.out.println();

                        System.out.println(
                            "Waiting Queue - "
                            + selectedService.getServiceName()
                        );

                        System.out.println(
                            "--------------------------"
                        );

                        if (waitingTickets.isEmpty()) {

                            System.out.println(
                                "No customers are waiting."
                            );

                        } else {

                            for (QueueTicket waitingTicket
                                : waitingTickets) {

                                System.out.println(
                                    "Ticket ID: "
                                    + waitingTicket.getId()
                                );

                                System.out.println(
                                    "Token Number: "
                                    + waitingTicket.getTokenNumber()
                                );

                                System.out.println(
                                    "Status: "
                                    + waitingTicket.getStatus()
                                );

                                System.out.println(
                                    "Created At: "
                                    + waitingTicket.getCreatedAt()
                                );

                                System.out.println(
                                    "--------------------------"
                                );
                            }
                        }

                        break;

                    // =================================
                    // 2. CALL NEXT CUSTOMER
                    // =================================

                    case 2:

                        System.out.println();

                        System.out.println(
                            "Available Services"
                        );

                        System.out.println(
                            "--------------------------"
                        );

                        serviceDAO.getAllServices();

                        System.out.println();

                        System.out.print(
                            "Enter Service ID: "
                        );

                        int callServiceId =
                            scanner.nextInt();

                        Service callService =
                            serviceDAO.getServiceById(
                                callServiceId
                            );

                        if (callService == null) {

                            System.out.println();

                            System.out.println(
                                "Invalid Service ID."
                            );

                            break;
                        }

                        QueueTicket calledTicket =
                            queueManager.callNextCustomer(
                                callServiceId
                            );

                        System.out.println();

                        if (calledTicket != null) {

                            System.out.println(
                                "Customer called successfully."
                            );

                            System.out.println(
                                "Ticket ID: "
                                + calledTicket.getId()
                            );

                            System.out.println(
                                "Token Number: "
                                + calledTicket.getTokenNumber()
                            );

                            System.out.println(
                                "Service: "
                                + callService.getServiceName()
                            );

                            System.out.println(
                                "Status: "
                                + calledTicket.getStatus()
                            );

                        } else {

                            System.out.println(
                                "No waiting customers found."
                            );
                        }

                        break;

                    // =================================
                    // 3. START SERVICE
                    // =================================

                    case 3:

                        System.out.println();

                        System.out.print(
                            "Enter Ticket ID: "
                        );

                        int startTicketId =
                            scanner.nextInt();

                        boolean serviceStarted =
                            queueManager.startService(
                                startTicketId
                            );

                        System.out.println();

                        if (serviceStarted) {

                            System.out.println(
                                "Service started successfully."
                            );

                        } else {

                            System.out.println(
                                "Service cannot be started."
                            );

                        }

                        break;

                    // =================================
                    // 4. COMPLETE SERVICE
                    // =================================

                    case 4:

                        System.out.println();

                        System.out.print(
                            "Enter Ticket ID: "
                        );

                        int completeTicketId =
                            scanner.nextInt();

                        boolean serviceCompleted =
                            queueManager.completeService(
                                completeTicketId
                            );

                        System.out.println();

                        if (serviceCompleted) {

                            System.out.println(
                                "Service completed successfully."
                            );

                        } else {

                            System.out.println(
                                "Service cannot be completed."
                            );
                        }

                        break;

                    // =================================
                    // 5. SKIP CUSTOMER
                    // =================================

                    case 5:

                        System.out.println();

                        System.out.print(
                            "Enter Ticket ID: "
                        );

                        int skipTicketId =
                            scanner.nextInt();

                        boolean customerSkipped =
                            queueManager.skipCustomer(
                                skipTicketId
                            );

                        System.out.println();

                        if (customerSkipped) {

                            System.out.println(
                                "Customer skipped successfully."
                            );

                        } else {

                            System.out.println(
                                "Customer cannot be skipped."
                            );
                        }

                        break;

                    // =================================
                    // 6. VIEW TICKET HISTORY
                    // =================================

                    case 6:

                        System.out.println();

                        System.out.print(
                            "Enter Ticket ID: "
                        );

                        int historyTicketId =
                            scanner.nextInt();

                        QueueTicket historyTicket =
                            queueManager.getTicket(
                                historyTicketId
                            );

                        if (historyTicket == null) {

                            System.out.println();

                            System.out.println(
                                "Ticket not found."
                            );

                            break;
                        }

                        System.out.println();

                        System.out.println(
                            "Ticket History"
                        );

                        System.out.println(
                            "--------------------------"
                        );

                        System.out.println(
                            "Ticket ID: "
                            + historyTicket.getId()
                        );

                        System.out.println(
                            "Token Number: "
                            + historyTicket.getTokenNumber()
                        );

                        System.out.println(
                            "Current Status: "
                            + historyTicket.getStatus()
                        );

                        System.out.println();

                        historyDAO.getHistoryByTicketId(
                            historyTicketId
                        );

                        break;

                    // =================================
                    // 7. EXIT
                    // =================================

                    case 7:

                        staffRunning = false;

                        System.out.println();

                        System.out.println(
                            "Thank you for using Smart Queue Management System!"
                        );

                        break;

                    // =================================
                    // INVALID CHOICE
                    // =================================

                    default:

                        System.out.println();

                        System.out.println(
                            "Invalid choice. Please try again."
                        );
                }
            }

        // =================================
        // ADMIN ROLE
        // =================================

        } else if (user.getRole().equals("ADMIN")) {

            boolean adminRunning = true;

            while (adminRunning) {

                System.out.println();
                System.out.println("=================================");
                System.out.println("            ADMIN MENU");
                System.out.println("=================================");
                System.out.println("1. View All Users");
                System.out.println("2. View All Services");
                System.out.println("3. View All Queue Tickets");
                System.out.println("4. View Ticket History");
                System.out.println("5. Exit");
                System.out.println("=================================");

                System.out.print("Enter your choice: ");

                int choice = scanner.nextInt();

                switch (choice) {

                    // =================================
                    // 1. VIEW ALL USERS
                    // =================================

                    case 1:

                        System.out.println();

                        System.out.println(
                            "All Users"
                        );

                        System.out.println(
                            "--------------------------"
                        );

                        userDAO.getAllUsers();

                        break;

                    // =================================
                    // 2. VIEW ALL SERVICES
                    // =================================

                    case 2:

                        System.out.println();

                        System.out.println(
                            "All Services"
                        );

                        System.out.println(
                            "--------------------------"
                        );

                        serviceDAO.getAllServices();

                        break;

                    // =================================
                    // 3. VIEW ALL QUEUE TICKETS
                    // =================================

                    case 3:

                        System.out.println();

                        System.out.println(
                            "All Queue Tickets"
                        );

                        System.out.println(
                            "--------------------------"
                        );

                        queueDAO.getAllTickets();

                        break;

                    // =================================
                    // 4. VIEW TICKET HISTORY
                    // =================================

                    case 4:

                        System.out.println();

                        System.out.print(
                            "Enter Ticket ID: "
                        );

                        int adminHistoryTicketId =
                            scanner.nextInt();

                        QueueTicket adminHistoryTicket =
                            queueManager.getTicket(
                                adminHistoryTicketId
                            );

                        if (adminHistoryTicket == null) {

                            System.out.println();

                            System.out.println(
                                "Ticket not found."
                            );

                            break;
                        }

                        System.out.println();

                        System.out.println(
                            "Ticket History"
                        );

                        System.out.println(
                            "--------------------------"
                        );

                        System.out.println(
                            "Ticket ID: "
                            + adminHistoryTicket.getId()
                        );

                        System.out.println(
                            "Token Number: "
                            + adminHistoryTicket.getTokenNumber()
                        );

                        System.out.println(
                            "Current Status: "
                            + adminHistoryTicket.getStatus()
                        );

                        System.out.println();

                        historyDAO.getHistoryByTicketId(
                            adminHistoryTicketId
                        );

                        break;

                    // =================================
                    // 5. EXIT
                    // =================================

                    case 5:

                        adminRunning = false;

                        System.out.println();

                        System.out.println(
                            "Thank you for using Smart Queue Management System!"
                        );

                        break;

                    // =================================
                    // INVALID CHOICE
                    // =================================

                    default:

                        System.out.println();

                        System.out.println(
                            "Invalid choice. Please try again."
                        );
                }
            }

        // =================================
        // UNKNOWN ROLE
        // =================================

        } else {

            System.out.println();

            System.out.println(
                "Unknown user role."
            );
        }

        scanner.close();
    }
}