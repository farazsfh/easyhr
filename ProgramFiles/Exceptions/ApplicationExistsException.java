package ProgramFiles.Exceptions;

public class ApplicationExistsException extends Exception {

    public String toString() {
        return "ApplicationExistsException: Applications Already Exists.";
    }

}
