import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class MessageTest {

    // ----- Message length -----
    @Test
    void testMessageLengthSuccess() {
        Message m = new Message(0, "+27718693002", "Hi Mike, can you join us for dinner tonight?");
        assertEquals("Message ready to send.", m.checkMessageLength());
    }

    @Test
    void testMessageLengthFailure() {
        String longText = "A".repeat(260);
        Message m = new Message(0, "+27718693002", longText);
        assertEquals("Message exceeds 250 characters by 10; please reduce the size.", m.checkMessageLength());
    }

    // ----- Recipient cell number (reuses Part 1's international-code check) -----
    @Test
    void testRecipientCorrectlyFormatted() {
        Message m = new Message(0, "+27718693002", "Hi Mike, can you join us for dinner tonight?");
        assertEquals("Cell phone number successfully captured.", m.checkRecipientCell());
    }

    @Test
    void testRecipientIncorrectlyFormatted() {
        Message m = new Message(1, "08575975889", "Hi Keegan, did you receive the payment?");
        assertEquals(
            "Cell phone number is incorrectly formatted or does not contain an international "
                + "code. Please correct the number and try again.",
            m.checkRecipientCell());
    }

    // ----- Message hash (fixed ID so the result is deterministic) -----
    @Test
    void testMessageHashCorrect() {
        Message m = new Message(0, "+27718693002", "Hi Mike, can you join us for dinner tonight?", "0012345678");
        assertEquals("00:0:HITONIGHT", m.createMessageHash());
    }

    // ----- Message ID -----
    @Test
    void testMessageIDCreated() {
        Message m = new Message(0, "+27718693002", "Hi Mike, can you join us for dinner tonight?", "0012345678");
        assertEquals("Message ID generated: 0012345678", m.reportMessageID());
    }

    // ----- MessageSent -----
    @Test
    void testMessageSentSend() {
        Message m = new Message(0, "+27718693002", "Hi Mike, can you join us for dinner tonight?");
        m.createMessageHash();
        assertEquals("Message successfully sent.", m.sentMessage("send"));
    }

    @Test
    void testMessageSentDisregard() {
        Message m = new Message(1, "08575975889", "Hi Keegan, did you receive the payment?");
        assertEquals("Press 0 to delete the message.", m.sentMessage("disregard"));
    }

    @Test
    void testMessageSentStore() {
        Message m = new Message(0, "+27718693002", "Hi Mike, can you join us for dinner tonight?");
        m.createMessageHash();
        assertEquals("Message successfully stored.", m.sentMessage("store"));
    }
}
