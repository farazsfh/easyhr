package ProgramFiles;

import java.io.*;
import java.time.LocalDate;
import java.util.List;
import java.util.ArrayList;

import ProgramFiles.Exceptions.IncorrectPasswordException;
import ProgramFiles.Exceptions.UserExistsException;

public class Storage implements java.io.Serializable {

    /** Class to store all the information of the program
     *
     * Attributes:
     *
     * companies: All the companies in the program
     * applicants: All the applicants in the program
     * today: LocalDate current date of the program (Using system.today() at the moment)
     */
    private List<Applicant> applicants = new ArrayList<>();
    private List<Company> companies = new ArrayList<>();
    private LocalDate today;

    /**
     * Constructor method (used for serialization
     */
    public Storage() {
        readApplicants();
        readCompanies();
        UpdateDate();
        UpdateApplicants();
    }

    //GETTERS and Setters

    /**
     * Returns the applicants stored in the system.
     * @return the list of applicants of this Storage.
     */
    public List<Applicant> getApplicants() {
        return this.applicants;
    }

    /**
     * Returns the companies stored in the system.
     * @return the list of companies of this Storage.
     */
    public List<Company> getCompanies() {
        return this.companies;
    }

    public void setDate(LocalDate t){
        this.today = t;
    }

    /**
     * Returns Today's date
     */

    public LocalDate getDate()  { return this.today;}

    //APPLICANT METHODS

    /**
     * Add a new applicant with a given username, password, resume, and cover letter.
     *
     * @param username The username of the applicant being added.
     * @param password The password of the applicant being added.
     * @param resume The resume of the applicant being added.
     * @param coverLetter The cover letter of the applicant being added.
     * @return the newly added applicant.
     */
    public Applicant addApplicant(String username, String password, String resume, String coverLetter)
            throws UserExistsException {
        System.out.println(this.applicants);
        for (Applicant applicant : this.applicants) {
            if (applicant.getUserName().equals(username)) {
                throw new UserExistsException();
            }
        }
        Applicant applicant = new Applicant(username, password, resume, coverLetter, this.today);
        this.applicants.add(applicant);
        return applicant;
    }

    /**
     * Searches applicants for an applicant matching the given username and password and return it if found. If no such
     * applicant was found, returns null.
     *
     * @param username The username being logged in with.
     * @param password The password being logged in with.
     * @return The applicant with the matching username and password or null if none was found.
     */
    public Applicant loginApplicant(String username, String password) throws IncorrectPasswordException {
        System.out.println(applicants);
        for (Applicant applicant : this.applicants) {
            if (applicant.getUserName().equals(username) && applicant.getPassword().equals(password)) {
                return applicant;
            }
        }
        throw new IncorrectPasswordException();
    }

    /**
     * Searches each Company in companies for an HR matching the given username and password and return it if found. If
     * no such HR was found, returns null.
     *
     * @param username The username being logged in with.
     * @param password The password being logged in with.
     * @return The HR with the matching username and password or null if none was found.
     */
    public HR loginHR(String username, String password) throws IncorrectPasswordException {
        HR currHR;
        for (Company company : this.companies) {
            currHR = company.getHR(username);
            if (currHR != null && currHR.getPassword().equals(password)) {
                return currHR;
            }
        }
        throw new IncorrectPasswordException();
    }

    /**
     * Updates Date upon startup
     */
    private void UpdateDate(){
        today = LocalDate.now();
    }

    /**
     * Updates applicant info
     */
    private void UpdateApplicants(){
        for (Applicant app: applicants){
            app.deleteOldInfo();
        }
    }



    /**
     * Searches each Company in companies for an Interviewer matching the given username and password and return it if
     * found. If no such interviewer was found, returns null.
     *
     * @param username The username being logged in with.
     * @param password The password being logged in with.
     * @return The Interviewer with the matching username and password or null if none was found.
     */
    public Interviewer loginInterviewer(String username, String password) throws IncorrectPasswordException {
        Interviewer interviewer;
        for (Company company : this.companies) {
            interviewer = company.getInterviewer(username);
            if (interviewer != null && interviewer.getPassword().equals(password)) {
                return interviewer;
            }
        }
        throw new IncorrectPasswordException();
    }

    //COMPANY METHODS

    /**
     * Adds a new Company with the given name.
     *
     * @param name The name of the company being added.
     */
    public void addCompany(String name) {
        this.companies.add(new Company(name));
    }

    /**
     * Returns a List of companies matching the given name.
     *
     * @param name The name of copmanies being searched for.
     * @return A List of all companies in companies with name name.
     */
    public List<Company> getCompaniesByName(String name) {
        ArrayList<Company> companies = new ArrayList<>();
        for (Company company : this.companies) {
            if (company.getName().equals(name)) {
                companies.add(company);
            }
        }
        return companies;
    }

    //SERIALIZATION METHODS

    /**
     * Writes all of the Applicants in applicants to the disk in the working directory using serialization and catches
     * IOExceptions.
     */
    public void writeApplicants() {
        for (Applicant applicant : this.applicants) {
            try {
                FileOutputStream fileStream = new FileOutputStream("A" + applicant.getUserName() + ".ser");
                ObjectOutputStream outStream = new ObjectOutputStream(fileStream);
                outStream.writeObject(applicant);
                outStream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * Writes all of the Companies in companies to the disk in the working directory using serialization and catches
     * IOExceptions.
     */
    public void writeCompanies() {
        for (Company company : this.companies) {
            try {
                FileOutputStream fileStream = new FileOutputStream("C" + company.getName() + ".ser");
                ObjectOutputStream outStream = new ObjectOutputStream(fileStream);
                outStream.writeObject(company);
                outStream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * Finds all of the files in the working directory with the corresponding Applicant format, reads from them, and
     * adds each Applicant to applicants.
     */
    public void readApplicants() {
        String path = System.getProperty("user.dir");
        File[] directoryListing = new File(path).listFiles();
        Applicant applicant;
        if (directoryListing != null) {
            for (File file : directoryListing) {
                if (file.getName().length() >= 5 && file.getName().charAt(0) == 'A' &&
                        file.getName().charAt(file.getName().length() - 1) == 'r' &&
                        file.getName().charAt(file.getName().length() - 2) == 'e' &&
                        file.getName().charAt(file.getName().length() - 3) == 's' &&
                        file.getName().charAt(file.getName().length() - 4) == '.') {
                    applicant = readApplicant(file);
                    if (applicant != null)
                        this.applicants.add(readApplicant(file));
                }
            }
        }
    }

    /**
     * Finds all of the files in the working directory with the corresponding Company format, reads from them, and
     * adds each Company to companies.
     */
    public void readCompanies() {
        String path = System.getProperty("user.dir");
        File[] directoryListing = new File(path).listFiles();
        Company company;
        if (directoryListing != null) {
            for (File file : directoryListing) {
                if (file.getName().length() >= 5 && file.getName().charAt(0) == 'C' &&
                        file.getName().charAt(file.getName().length() - 1) == 'r' &&
                        file.getName().charAt(file.getName().length() - 2) == 'e' &&
                        file.getName().charAt(file.getName().length() - 3) == 's' &&
                        file.getName().charAt(file.getName().length() - 4) == '.') {
                    company = readCompany(file);
                    if (company != null)
                        this.companies.add(readCompany(file));
                }
            }
        }
    }

    /**
     * Returns the Applicant stored in the given .ser file.
     *
     * @param file The serialization file to be read from.
     * @return The Applicant stored in file.
     */
    public Applicant readApplicant(File file) {
        try {
            FileInputStream fileStream = new FileInputStream(file);
            BufferedInputStream bufferedStream = new BufferedInputStream(fileStream);
            ObjectInputStream objectStream = new ObjectInputStream(bufferedStream);
            return (Applicant) objectStream.readObject();
        } catch (IOException | ClassNotFoundException e) {
            System.out.println(e.getMessage());
            return null;
        }
    }

    /**
     * Returns the Company stored in the given .ser file.
     *
     * @param file The serialization file to be read from.
     * @return The Company stored in file.
     */
    public Company readCompany(File file) {
        try {
            FileInputStream fileStream = new FileInputStream(file);
            BufferedInputStream bufferedStream = new BufferedInputStream(fileStream);
            ObjectInputStream objectStream = new ObjectInputStream(bufferedStream);
            return (Company) objectStream.readObject();
        } catch (IOException | ClassNotFoundException e) {
            System.out.println(e.getMessage());
            return null;
        }
    }

}
