/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/UnitTests/JUnit5TestClass.java to edit this template
 */
package loginsystem;

import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author masha
 */
public class MessageTest {
    
    public MessageTest() {
    }

    @Test
    public void testSetIdCounter() {
    }

    @Test
    public void testSetMessageId() {
    }

    @Test
    public void testSetRecipientNumber() {
    }

    @Test
    public void testSetMessageContent() {
    }

    @Test
    public void testSetBatchIndex() {
    }

    @Test
    public void testSetMessageHash() {
    }

    @Test
    public void testSetTotalSent() {
    }

    @Test
    public void testGetIdCounter() {
    }

    @Test
    public void testGetMessageId() {
    }

    @Test
    public void testGetRecipientNumber() {
    }

    @Test
    public void testGetMessageContent() {
    }

    @Test
    public void testGetBatchIndex() {
    }

    @Test
    public void testGetMessageHash() {
    }

    @Test
    public void testGetTotalSent() {
    }

    @Test
    public void testResetIdCounter() {
    }

    @Test
    public void testResetTotalSent() {
    }

    @Test
    public void testCheckMessageID() {
    }

      @Test
    public void testCheckMessageID_Valid() {
        Message msg = new Message(1,1); // messageId will be "0000000000"
        assertTrue(msg.checkMessageID());
    }

    @Test
    public void testCheckMessageID_Invalid() {
        Message msg = new Message(1,1);
        msg.setMessageId("123456");  // override the ID to a bad one
        assertFalse(msg.checkMessageID());
    }

    @Test
    public void testCheckRecipientCell_Valid() {
        Message msg = new Message(1,1);
        msg.setRecipientNumber("0821234567");
        assertEquals(1, msg.checkRecipientCell());
    }

    @Test
    public void testCheckRecipientCell_Invalid_Null() {
        Message msg = new Message(1,1);
        msg.setRecipientNumber(null);
        assertEquals(0, msg.checkRecipientCell());
    }

    @Test
    public void testCheckRecipientCell_Invalid_Empty() {
        Message msg = new Message(1,1);
        msg.setRecipientNumber("");
        assertEquals(0, msg.checkRecipientCell());
    }

    @Test
    public void testCheckRecipientCell_Invalid_NoLeadingZero() {
        Message msg = new Message(1,1);
        msg.setRecipientNumber("821234567");
        assertEquals(0, msg.checkRecipientCell());
    }

    @Test
    public void testCheckRecipientCell_Invalid_TooLong() {
        Message msg = new Message(1,1);
        msg.setRecipientNumber("08212345678");
        assertEquals(0, msg.checkRecipientCell());
    }

    @Test
    public void testCreateMessageHash() {
        Message msg = new Message(3,3);
        msg.setMessageContent("Hello world!");
        String expectedPrefix = msg.getMessageId().substring(0, 2) + ":3:HELLOWORLD";
        String actual = msg.createMessageHash();
        assertEquals(expectedPrefix, actual);
    }

    @Test
    public void testCreateMessageHash_EmptyMessage() {
        Message msg = new Message(1,1);
        msg.setMessageContent("");
        String expected = msg.getMessageId().substring(0, 2) + ":1:";
        String actual = msg.createMessageHash();
        assertEquals(expected, actual);
    }

    @Test
    public void testCheckMessageLength_Valid() {
        Message msg = new Message(1,1);
        msg.setMessageContent("Short message");
        String expected = "Message ready to send.";
        assertEquals(expected, msg.checkMessageLength());
    }

    @Test
    public void testCheckMessageLength_TooLong() {
        Message msg = new Message(1,1);
        msg.setMessageContent(new String(new char[260]).replace('\0', 'a'));
        String result = msg.checkMessageLength();
        assertTrue(result.contains("Message exceeds 250 characters by"));
    }
    
}
