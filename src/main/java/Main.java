import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.println("=== Registration ===");
        System.out.print("First name: ");
        String firstName = scanner.nextLine();
        System.out.print("Last name: ");
        String lastName = scanner.nextLine();
        System.out.print("Username (must contain '_', max 5 characters): ");
        String username = scanner.nextLine();
        System.out.print("Password (8+ chars, capital letter, number, special char): ");
        String password = scanner.nextLine();
        System.out.print("Cell number (e.g. +27838968976): ");
        String cellPhoneNumber = scanner.nextLine();

        Login login = new Login(firstName, lastName, username, password, cellPhoneNumber);
        System.out.println(login.registerUser());
        System.out.println(login.registerCellPhoneNumber());

        System.out.println("\n=== Login ===");
        System.out.print("Username: ");
        String loginUsername = scanner.nextLine();
        System.out.print("Password: ");
        String loginPassword = scanner.nextLine();

        boolean success = login.loginUser(loginUsername, loginPassword);
        System.out.println(login.returnLoginStatus(success));

        if (!success) {
            scanner.close();
            return;
        }

        System.out.println("\nWelcome to QuickChat.");
        System.out.print("How many messages would you like to enter? ");
        int numMessages = Integer.parseInt(scanner.nextLine().trim());
        int messagesCreated = 0;

        boolean running = true;
        while (running) {
            System.out.println("\n1) Send Messages");
            System.out.println("2) Show recently sent messages");
            System.out.println("3) Quit");
            System.out.print("Choose an option: ");
            String option = scanner.nextLine().trim();

            switch (option) {
                case "1":
                    for (; messagesCreated < numMessages; messagesCreated++) {
                        System.out.print("Recipient cell number (e.g. +27718693002): ");
                        String recipient = scanner.nextLine();
                        System.out.print("Message (max 250 characters): ");
                        String text = scanner.nextLine();

                        Message message = new Message(messagesCreated, recipient, text);

                        System.out.println(message.checkRecipientCell());
                        System.out.println(message.checkMessageLength());
                        message.createMessageHash();

                        System.out.print("Send, Store, or Disregard this message? ");
                        String choice = scanner.nextLine();
                        System.out.println(message.sentMessage(choice));

                        if (choice.equalsIgnoreCase("send")) {
                            System.out.println(message.printMessageDetails());
                        }
                    }
                    System.out.println("\nAll " + numMessages + " messages have been entered.");
                    break;
                case "2":
                    System.out.println("Coming Soon.");
                    break;
                case "3":
                    running = false;
                    System.out.println("Total messages sent: " + Message.returnTotalMessagess());
                    break;
                default:
                    System.out.println("Invalid option.");
            }
        }

        scanner.close();
    }
}
