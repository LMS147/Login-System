package loginsystem;

import javax.swing.*;
import java.util.ArrayList;
import java.util.Optional;

public class LoginSystem {

    // Lists to store different categories of messages
    public static final ArrayList<Message> sentMessages = new ArrayList<>();
    public static final ArrayList<Message> disregardedMessages = new ArrayList<>();
    public static final ArrayList<Message> storedMessages = new ArrayList<>();
    public static final ArrayList<String> messageHashes = new ArrayList<>();
    public static final ArrayList<String> messageIDs = new ArrayList<>();
    
    public static String loggedInUser = ""; // Global variable

    public static void main(String[] args) {
        Login loginSystem = new Login();

        // User registration
        String username = promptInput("Enter username:");
        String password = promptInput("Enter password:");
        String cellPhoneNumber = promptInput("Enter Phone Number (starting with +27):");
        showMessage(loginSystem.registerUser(username, password, cellPhoneNumber));

        // User login
        String loginUsername = promptInput("Enter username:");
        String loginPassword = promptInput("Enter password:");

        if (!loginSystem.returnLoginStatus(loginUsername, loginPassword)) {
            showMessage("Login failed. Exiting.");
            System.exit(0);
        }
        else {
             loggedInUser = loginUsername; // Save the logged-in user
        }

        // Login successful
        showMessage("Welcome to QuickChat.");

        // Launch main menu
        mainMenuLoop();
    }

   //Displays the main menu and handles user interaction.
    private static void mainMenuLoop() {
    while (true) {
        String choice = JOptionPane.showInputDialog(null,
                "QuickChat Main Menu:\n" +
                        "1. Send Message\n" +
                        "2. View Sent Messages\n" +
                        "3. View Stored Messages\n" +
                        "4. View Longest Sent Message\n" +
                        "5. Search Message by ID\n" +
                        "6. Search Messages by Recipient\n" +
                        "7. Delete Message by Hash\n" +
                        "8. Show Message Report\n" +
                        "9. Exit",
                "QuickChat", JOptionPane.QUESTION_MESSAGE);

        if (choice == null || choice.equals("9")) {
            JOptionPane.showMessageDialog(null, "Thank you for using QuickChat. Goodbye!");
            break;
        }

        // Execute selected action
        switch (choice) {
            case "1":
                sendMessages();
                break;
            case "2":
                showSentMessages();
                break;
            case "3":
                showStoredMessages();
                break;
            case "4":
                showLongestSentMessage();
                break;
            case "5":
                searchByMessageID();
                break;
            case "6":
                searchByRecipient();
                break;
            case "7":
                deleteMessageByHash();
                break;
            case "8":
                displayReport();
                break;
            default:
                JOptionPane.showMessageDialog(null, "Invalid choice. Please enter a number between 1 and 9.");
        }
    }
}

  private static void sendMessages() {
        String numStr = promptInput("Enter the number of messages you wish to send:");
        int numMessages;
        try {
            numMessages = Integer.parseInt(numStr);
        } catch (NumberFormatException e) {
            showMessage("Invalid number of messages.");
            return;
        }

        for (int i = 0; i < numMessages; i++) {
            Message message = new Message(i,i);
            message.setSender(loggedInUser);

            while (true) {
                String recipient = promptInput("Enter recipient number for message " + (i + 1) + ":");
                message.setRecipientNumber(recipient);
                if (message.checkRecipientCell() == 1) break;
                showMessage("Invalid cell phone number. Please try again.");
            }

            while (true) {
                String content = promptInput("Enter message content (max 250 characters):");
                if (content.length() <= 250) {
                    message.setMessageContent(content);
                    break;
                }
                showMessage("Message exceeds 250 characters by " + (content.length() - 250));
            }

            message.createMessageHash();
            messageHashes.add(message.getMessageHash());
            messageIDs.add(message.getMessageId());

            String result = message.sentMessage();
            showMessage(result);

            switch (result) {
                case "Message successfully sent." -> sentMessages.add(message);
                case "Press 0 to delete message." -> disregardedMessages.add(message);
                case "Message successfully stored." -> storedMessages.add(message);
            }
        }
    }

    private static void showSentMessages() {
        if (sentMessages.isEmpty()) {
            showMessage("No sent messages to display.");
            return;
        }
        StringBuilder sb = new StringBuilder("Sent Messages:\n");
        sentMessages.forEach(m -> sb.append("To: ").append(m.getRecipientNumber())
                .append("\nMessage: ").append(m.getMessageContent()).append("\n\n"));
        showMessage(sb.toString());
    }

    private static void showStoredMessages() {
        if (storedMessages.isEmpty()) {
            showMessage("No stored messages to display.");
            return;
        }
        StringBuilder sb = new StringBuilder("Stored Messages:\n");
        storedMessages.forEach(m -> sb.append("To: ").append(m.getRecipientNumber())
                .append("\nMessage: ").append(m.getMessageContent()).append("\n\n"));
        showMessage(sb.toString());
    }

    private static void showLongestSentMessage() {
        Optional<Message> longest = sentMessages.stream()
                .max((m1, m2) -> Integer.compare(m1.getMessageContent().length(), m2.getMessageContent().length()));
        longest.ifPresentOrElse(
                m -> showMessage("Longest Message:\n" + m.getMessageContent()),
                () -> showMessage("No messages found.")
        );
    }

    private static void searchByMessageID() {
        String id = promptInput("Enter message ID:");
        sentMessages.stream()
                .filter(m -> m.getMessageId().equals(id))
                .findFirst()
                .ifPresentOrElse(
                        m -> showMessage("Recipient: " + m.getRecipientNumber() + "\nMessage: " + m.getMessageContent()),
                        () -> showMessage("Message ID not found."));
    }

    private static void searchByRecipient() {
        String recipient = promptInput("Enter recipient number:");
        StringBuilder sb = new StringBuilder("Messages to " + recipient + ":\n");
        sentMessages.stream()
                .filter(m -> m.getRecipientNumber().equals(recipient))
                .forEach(m -> sb.append(m.getMessageContent()).append("\n"));

        if (sb.toString().equals("Messages to " + recipient + ":\n")) {
            showMessage("No messages found for recipient: " + recipient);
        } else {
            showMessage(sb.toString());
        }
    }

    private static void deleteMessageByHash() {
        String hash = promptInput("Enter message hash:");
        for (int i = 0; i < sentMessages.size(); i++) {
            if (sentMessages.get(i).getMessageHash().equals(hash)) {
                sentMessages.remove(i);
                messageHashes.remove(hash);
                showMessage("Message deleted successfully.");
                return;
            }
        }
        showMessage("Message hash not found.");
    }

    private static void displayReport() {
        if (sentMessages.isEmpty()) {
            showMessage("No messages to report.");
            return;
        }
        
    StringBuilder report = new StringBuilder("Sent Messages:\n");

    for (Message m : sentMessages) {
    report.append("MessageID: ").append(m.getMessageId()).append("\n")
          .append("Recipient: ").append(m.getRecipientNumber()).append("\n")
          .append("Content: ").append(m.getMessageContent()).append("\n")
          .append("Sender: ").append(m.getSender() != null ? m.getSender() : "Unknown").append("\n")
          .append("Timestamp: ").append(m.getTimestamp() != null ? m.getTimestamp() : "Unknown").append("\n")
          .append("Hash: ").append(m.getMessageHash()).append("\n")
          .append("------------------------------\n");
    }

    showMessage(report.toString());
    }

    private static String promptInput(String message) {
        return JOptionPane.showInputDialog(null, message);
    }

    private static void showMessage(String message) {
        JOptionPane.showMessageDialog(null, message);
    }
}