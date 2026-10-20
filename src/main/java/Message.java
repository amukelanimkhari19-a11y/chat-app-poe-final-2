import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

/**
 * Message.java
 * PROG POE Part 2 — Sending Messages.
 *
 * Reference (regex-based validation, reused from Part 1):
 * Oracle. n.d. Class Pattern. Available at:
 * https://docs.oracle.com/javase/8/docs/api/java/util/regex/Pattern.html
 * [Accessed 27 September 2026].
 */
public class Message {

    private static final String CELL_REGEX = "^\\+27\\d{1,10}$";
    private static int totalMessagesSent = 0;
    private static final List<String> sentMessages = new ArrayList<>();

    private final String messageID;
    private final int messageNumber;
    private String recipientCell;
    private String messageText;
    private String messageHash;

    public Message(int messageNumber, String recipientCell, String messageText) {
        this(messageNumber, recipientCell, messageText, generateMessageID());
    }

    /** Overload with a fixed ID, so hash/ID behaviour can be unit-tested deterministically. */
    public Message(int messageNumber, String recipientCell, String messageText, String fixedMessageID) {
        this.messageNumber = messageNumber;
        this.recipientCell = recipientCell;
        this.messageText = messageText;
        this.messageID = fixedMessageID;
    }

    private static String generateMessageID() {
        long id = 1_000_000_000L + (long) (Math.random() * 9_000_000_000L);
        return String.valueOf(id);
    }

    /** Message ID must be no more than ten characters. */
    public boolean checkMessageID() {
        return messageID != null && messageID.length() <= 10;
    }

    /** "Message ID generated: <Message ID>" confirmation text. */
    public String reportMessageID() {
        return "Message ID generated: " + messageID;
    }

    /** Recipient cell number must contain the international code and be correctly formatted. */
    public String checkRecipientCell() {
        return Pattern.matches(CELL_REGEX, recipientCell)
                ? "Cell phone number successfully captured."
                : "Cell phone number is incorrectly formatted or does not contain an international "
                        + "code. Please correct the number and try again.";
    }

    /** Message must not exceed 250 characters. */
    public String checkMessageLength() {
        if (messageText.length() <= 250) {
            return "Message ready to send.";
        }
        int excess = messageText.length() - 250;
        return "Message exceeds 250 characters by " + excess + "; please reduce the size.";
    }

    /** First two digits of the ID : message number : first+last word of the message, in caps. */
    public String createMessageHash() {
        String idPrefix = messageID.substring(0, 2);
        String[] words = messageText.trim().split("\\s+");
        String firstWord = words[0].replaceAll("[^a-zA-Z]", "").toUpperCase();
        String lastWord = words[words.length - 1].replaceAll("[^a-zA-Z]", "").toUpperCase();
        messageHash = idPrefix + ":" + messageNumber + ":" + firstWord + lastWord;
        return messageHash;
    }

    /** Send, disregard, or store the message: "send" / "disregard" / "store". */
    public String sentMessage(String choice) {
        if (choice == null) {
            return "Invalid option.";
        }
        switch (choice.toLowerCase()) {
            case "send":
                totalMessagesSent++;
                sentMessages.add(printMessageDetails());
                return "Message successfully sent.";
            case "disregard":
                return "Press 0 to delete the message.";
            case "store":
                storeMessage();
                return "Message successfully stored.";
            default:
                return "Invalid option.";
        }
    }

    /** Full details in the required order: Message ID, Message Hash, Recipient, Message. */
    public String printMessageDetails() {
        return "Message ID: " + messageID
                + "\nMessage Hash: " + messageHash
                + "\nRecipient: " + recipientCell
                + "\nMessage: " + messageText;
    }

    /** All messages sent while the program has been running. */
    public static String printMessages() {
        return String.join("\n---\n", sentMessages);
    }

    /** Total number of messages sent. */
    public static int returnTotalMessagess() {
        return totalMessagesSent;
    }

    /** Appends this message to messages.json as a JSON object (one per line). */
    public void storeMessage() {
        String json = "{"
                + "\"messageID\":\"" + messageID + "\","
                + "\"messageNumber\":" + messageNumber + ","
                + "\"recipient\":\"" + recipientCell + "\","
                + "\"message\":\"" + messageText.replace("\"", "\\\"") + "\","
                + "\"messageHash\":\"" + messageHash + "\""
                + "}";
        try (FileWriter writer = new FileWriter("messages.json", true)) {
            writer.write(json + System.lineSeparator());
        } catch (IOException e) {
            System.out.println("Could not store message: " + e.getMessage());
        }
    }
}
