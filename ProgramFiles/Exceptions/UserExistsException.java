package ProgramFiles.Exceptions;

public class UserExistsException extends Exception {

    public String toString() {

        return "UserExistsException: A user with that username already exists.";

    }

}
