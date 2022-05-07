package gr9.eventmarket.database.payload.response;

public class MessageResponse {

    private final String message;

    public MessageResponse() { message = null; }

    public MessageResponse(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
    
}
