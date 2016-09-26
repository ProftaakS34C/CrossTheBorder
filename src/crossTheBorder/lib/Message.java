package crossTheBorder.lib;

import java.util.Calendar;
import java.util.Date;

/**
 *  Represents a single message typed in chat by an user.
 *  Logs the author, message and time.
 */
public class Message {
    private String author;
    private String message;
    private Date time;

    /**
     * Creates a new message object with the given author and message.
     * Also logs the time the message was made.
     *
     * @param author  The author of the message.
     * @param message The message.
     */
    public Message(String author, String message) {
        this.author = author;
        this.message = message;
        this.time = Calendar.getInstance().getTime();
    }

    /**
     * Gets the string value of this class.
     *
     * @return Returns a string in the form hh:mm author: message
     */
    @Override
    public String toString() {
        return this.time.getHours() + ":" + this.time.getMinutes() + " " +
                this.author + ": " + this.message;
    }
}
