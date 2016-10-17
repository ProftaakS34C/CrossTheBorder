package crosstheborder.lib;

import java.time.LocalTime;

/**
 *  Represents a single message typed in chat by an user.
 *  Logs the author, message and time.
 *
 *  @author Oscar de Leeuw
 */
public class Message {
    private User author;
    private String message;
    private LocalTime time;

    /**
     * Creates a new message object with the given author and message.
     * Also logs the time the message was made.
     *
     * @param author  The author of the message.
     * @param message The message.
     */
    public Message(User author, String message) {
        this.author = author;
        this.message = message;
        this.time = LocalTime.now();
    }

    /**
     * Gets the string value of this class.
     *
     * @return Returns a string in the form hh:mm author: message
     */
    @Override
    public String toString() {
        return String.format("%1$02d:%2$02d %3$s: %4$s", time.getHour(), time.getMinute(), author.getName(), message);
    }
}
