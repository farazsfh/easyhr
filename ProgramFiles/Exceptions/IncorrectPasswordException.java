package ProgramFiles.Exceptions;

public class IncorrectPasswordException extends Exception {

    public String toString() {

        return "IncorrectPasswordException: The password entered is incorrect.";

    }
}
