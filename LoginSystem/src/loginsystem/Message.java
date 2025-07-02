package loginsystem;

import com.google.gson.Gson; // For JSON serialization
import javax.swing.*;
import java.io.FileWriter;
import java.io.IOException;
import java.util.regex.Pattern;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Message {
    
     private static int idCounter = 1;  // Static counter to ensure unique message IDs
     private String messageId; // Unique 10-digit message ID
     private String recipientNumber;
     private String messageContent;
     private int batchIndex;   // Index of the message in the current batch
     private String messageHash;   // Generated hash for the message
     static int totalSent = 0;
     private String timestamp;
     private String sender;

    //Constructor initializes a new message with a unique ID and batch index.
    public Message(int batchIndex,int id) {
        // Generate a 10-digit message ID with leading zeros if necessary
        this.messageId = String.format("%010d", idCounter++);
        this.batchIndex = batchIndex;
        this.messageId = String.valueOf(System.currentTimeMillis() + id);
        this.timestamp = getCurrentTimestamp(); // Set timestamp when message is created
    }
    
     private String getCurrentTimestamp() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        return LocalDateTime.now().format(formatter);
    }

    //setters
    public static void setIdCounter(int idCounter) {
        Message.idCounter = idCounter;
    }

    public void setMessageId(String messageId) {
        this.messageId = messageId;
    }

    public void setRecipientNumber(String recipientNumber) {
        this.recipientNumber = recipientNumber;
    }

    public void setMessageContent(String messageContent) {
        this.messageContent = messageContent;
    }

    public void setBatchIndex(int batchIndex) {
        this.batchIndex = batchIndex;
    }

    public void setMessageHash(String messageHash) {
        this.messageHash = messageHash;
    }

    public static void setTotalSent(int totalSent) {
        Message.totalSent = totalSent;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
    }

    public void setSender(String sender) {
        this.sender = sender;
    }
    
    //getters

    public static int getIdCounter() {
        return idCounter;
    }

    public String getMessageId() {
        return messageId;
    }

    public String getRecipientNumber() {
        return recipientNumber;
    }

    public String getMessageContent() {
        return messageContent;
    }

    public int getBatchIndex() {
        return batchIndex;
    }

    public String getMessageHash() {
        return messageHash;
    }

    public static int getTotalSent() {
        return totalSent;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public String getSender() {
        return sender;
    }
    
    //methods

    public static void resetIdCounter() { idCounter = 1; }
    public static void resetTotalSent() { totalSent = 0; }
    
    //Checks if the message ID is valid (exactly 10 digits).
    public boolean checkMessageID() {
        return messageId.length() == 10;
    }

    //Validates the recipient's phone number.
    public int checkRecipientCell() {
        // Check if the number is null or empty
        if (recipientNumber == null || recipientNumber.isEmpty()) {
            return 0;
        }

        // Check if the number starts with '0'
        if (!recipientNumber.startsWith("0")) {
            return 0;
        }

        // Check if the length is no more than 10
        if (recipientNumber.length() > 10) {
            return 0;
        }

        return 1;  // validates the return
    }

    //Generates a unique message hash based on message ID, batch index, and content
    public String createMessageHash() {
        // Split message content into words
        String[] words = messageContent.split("\\s+");
        // Extract the first word (removing non-alphabetic characters)
        String firstWord = words.length > 0 ? words[0].replaceAll("[^a-zA-Z]", "").toUpperCase() : "";
        // Extract the last word (removing non-alphabetic characters)
        String lastWord = words.length > 0 ? words[words.length - 1].replaceAll("[^a-zA-Z]", "").toUpperCase() : "";
        // Format the hash: first 2 digits of ID : batch index : firstWord + lastWord
        this.messageHash = String.format("%s:%d:%s%s",
                messageId.substring(0, 2),
                batchIndex,
                firstWord,
                lastWord);
        return messageHash;
    }

    //Provides options to send, disregard, or store the message.
    public String sentMessage() {
        String[] options = {"Send Message", "Disregard Message", "Store Message"};
        // Show a dialog with the three options
        int choice = JOptionPane.showOptionDialog(null,
                "Choose an action:",
                "Message Options",
                JOptionPane.DEFAULT_OPTION,
                JOptionPane.INFORMATION_MESSAGE,
                null,
                options,
                options[0]);
        switch (choice) {
            case 0: // Send Message
                totalSent++;
                return "Message successfully sent.";
            case 1: // Disregard Message
                return "Press 0 to delete message.";
            case 2: // Store Message
                storeMessage();
                return "Message successfully stored.";
            default:
                return "Message disregarded.";
        }
    }

    //Stores the message in a JSON file.
    public void storeMessage() {
        Gson gson = new Gson();
        // Convert the message object to JSON
        String json = gson.toJson(this);
        try (FileWriter fw = new FileWriter("messages.json", true)) {
            // Append the JSON to the file
            fw.write(json + "\n");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    //Validates the message length (must be <= 250 characters).
    public String checkMessageLength() {
        if (messageContent.length() <= 250) {
            return "Message ready to send.";
        } else {
            int excess = messageContent.length() - 250;
            return String.format("Message exceeds 250 characters by %d, please reduce size.", excess);
        }
    }
    
}
