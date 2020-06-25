package ProgramFiles.Exceptions;

public class MissingRequirementException extends Exception {

    public String toString() {

        return "MissingRequirementException: Resume or Cover Letter not found.";

    }
}
