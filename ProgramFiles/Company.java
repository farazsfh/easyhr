package ProgramFiles;

import ProgramFiles.Exceptions.UserExistsException;

import java.util.ArrayList;



public class Company implements java.io.Serializable {
    /** Stores all the attributes of a company.
     *
     * Attributes:
     *
     * name: (String) name of company
     * listofHr: HR workers of the company
     * Interviewers: Interviewers working for the company
     * Postings: Job postings owned by the company
     */


    private String name;

    private ArrayList<HR> listofHr;

    private ArrayList<Interviewer> Interviewers;

    private ArrayList<Posting> Postings;

    /**
     * Constructor class of company
     *
     * @param name : String name of the company.
     */

    Company(String name) {
        this.name = name;
        this.listofHr = new ArrayList<>();
        this.Interviewers = new ArrayList<>();
        this.Postings = new ArrayList<>();
    }

    /** Finds a posting of given name
     *
     * @param n : String name to be searched
     * @return : returns Posting
     */
    public Posting getPosting (String n){

        for (Posting p: Postings){
            if (p.getID().equals(n)){
                return p;
            }
        }
        return null;
    }

    /**
     * @return : All the postings of the company
     */
    public ArrayList<Posting> getAllPostings(){
        return Postings;
    }

    /**
     * Get all the postings given an arbitrary length of tags
     *
     * @param tags an arraylist of tags
     * @return An array list of postings
     */

    public ArrayList<Posting> getPostingsByTags(String[] tags) {
        ArrayList<Posting> filteredPostings = new ArrayList<Posting>();
        for (Posting posting : Postings) {
            if (posting.hasAllTags(tags)) {
                filteredPostings.add(posting);
            }
        }

        return filteredPostings;
    }

    /**
     * @return : All the HR of the company
     */
    public ArrayList<HR> getAllHR(){
        return listofHr;
    }

    /**
     * @return : All the Interviewer objects of the company
     */
    public ArrayList<Interviewer> getAllInterviewers(){
        return Interviewers;
    }

    /** Adds a posting to Postings list
     *
     * @param p : Adding a posting to a company
     */
    public void addPosting(Posting p){
        Postings.add(p);
    }

    /** Adds an HR to listOfHR
     *
     * @param h : HR object to be added
     * @throws UserExistsException : HR already exists
     */

    public void addHR(HR h)
        throws UserExistsException {

        for (HR hr : listofHr) {
            if (hr.getUserName().equals(h.getUserName())) {
                throw new UserExistsException();
            }
        }
        listofHr.add(h);
    }

    /** Adds an interviewer to Interviewers list
     *
     * @param I : Interviewer object
     * @return : returns the object added
     * @throws UserExistsException : Interviewer already exists
     */

    public void addInterviewer (Interviewer I)
        throws UserExistsException {

        for (Interviewer interviewer : Interviewers) {
            if (interviewer.getUserName().equals(I.getUserName())) {
                throw new UserExistsException();
            }
        }
        Interviewers.add(I);

    }

    /** Searches for an interviewer with the name (as a string)
     *
     * @param n : Name of interviewer
     * @return : interviewer object
     */
    Interviewer getInterviewer(String n){
        for (Interviewer i: Interviewers){
            if (i.getUserName().equals(n)){
                return i;
            }
        }
        return null;

    }

    /** Searches for an HR, with a string
     *
     * @param n : name of the HR
     * @return : HR object
     */
    HR getHR(String n){
        for (HR h: listofHr){
            if (h.getUserName().equals(n)){
                return h;
            }
        }
        return null;

    }

    /**
     * @return : String name of the company
     */
    String getName() {
        return this.name;
    }

}
