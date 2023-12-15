package ics.mgs.error;

public class UserNotFound extends RuntimeException {
    public UserNotFound() {
        super("entered user is a non-existent user.");
    }
}
