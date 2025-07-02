/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/UnitTests/JUnit5TestClass.java to edit this template
 */
package loginsystem;

import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author masha
 */
public class LoginSystemTest {
    
   private Message message1;
    private Message message2;
    private Message message3;
    private Message message4;
    private Message message5;

    @Before
    public void setUp() {
        // Clear existing data before each test
        LoginSystem.sentMessages.clear();
        LoginSystem.storedMessages.clear();
        LoginSystem.disregardedMessages.clear();
        LoginSystem.messageHashes.clear();
        LoginSystem.messageIDs.clear();

        // Message 1: Sent
        message1 = new Message(0,0);
        message1.setRecipientNumber("+27834557896");
        message1.setMessageContent("Did you get the cake?");
        message1.createMessageHash();
        LoginSystem.sentMessages.add(message1);

        // Message 2: Ignored (disregarded)
        message2 = new Message(1,1);
        message2.setRecipientNumber("+27834484567");
        message2.setMessageContent("Yohoooo, I am at your gate.");
        message2.createMessageHash();
        LoginSystem.disregardedMessages.add(message2);

        // Message 3: Stored
        message3 = new Message(2,2);
        message3.setRecipientNumber("+27838884567");
        message3.setMessageContent("Where are you? You are late! I have asked you to be on time.");
        message3.createMessageHash();
        LoginSystem.storedMessages.add(message3);

        // Message 4: Sent
        message4 = new Message(3,3);
        message4.setRecipientNumber("0838884567");
        message4.setMessageContent("It is dinner time !");
        message4.createMessageHash();
        LoginSystem.sentMessages.add(message4);

        // Message 5: Stored
        message5 = new Message(4,4);
        message5.setRecipientNumber("+27838884567");
        message5.setMessageContent("Ok, I am leaving without you.");
        message5.createMessageHash();
        LoginSystem.storedMessages.add(message5);

        // Add hashes and IDs
        LoginSystem.messageHashes.add(message1.getMessageHash());
        LoginSystem.messageHashes.add(message2.getMessageHash());
        LoginSystem.messageHashes.add(message3.getMessageHash());
        LoginSystem.messageHashes.add(message4.getMessageHash());
        LoginSystem.messageHashes.add(message5.getMessageHash());

        LoginSystem.messageIDs.add(message1.getMessageId());
        LoginSystem.messageIDs.add(message2.getMessageId());
        LoginSystem.messageIDs.add(message3.getMessageId());
        LoginSystem.messageIDs.add(message4.getMessageId());
        LoginSystem.messageIDs.add(message5.getMessageId());
    }

    @Test
    public void testSentMessagesCount() {
        assertEquals(2, LoginSystem.sentMessages.size());
    }

    @Test
    public void testStoredMessagesCount() {
        assertEquals(2, LoginSystem.storedMessages.size());
    }

    @Test
    public void testDisregardedMessagesCount() {
        assertEquals(1, LoginSystem.disregardedMessages.size());
    }

    @Test
    public void testMessageHashUniqueness() {
        assertEquals(5, LoginSystem.messageHashes.stream().distinct().count());
    }

    @Test
    public void testSearchMessageById() {
        boolean found = LoginSystem.sentMessages.stream()
                .anyMatch(m -> m.getMessageId().equals(message1.getMessageId()));
        assertTrue(found);
    }

    @Test
    public void testDeleteMessageByHash() {
        String hashToDelete = message4.getMessageHash();
        LoginSystem.sentMessages.removeIf(m -> m.getMessageHash().equals(hashToDelete));
        assertFalse(LoginSystem.sentMessages.contains(message4));
    }

    @Test
    public void testLongestMessageDetection() {
        Message longest = LoginSystem.sentMessages.stream()
                .max((m1, m2) -> Integer.compare(m1.getMessageContent().length(), m2.getMessageContent().length()))
                .orElse(null);
        assertEquals(message1.getMessageContent(), longest.getMessageContent());
    }
    
}
