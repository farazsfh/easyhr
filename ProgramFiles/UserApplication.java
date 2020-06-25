package ProgramFiles;

import java.time.LocalDate;
import java.util.ArrayList;

public class  UserApplication implements java.io.Serializable,NotificationSystem {
    /**
     * This class stores info on every application created by a user.
     *
     * Attributes:
     *
     * resume: A string containing the applicant's resume
     * coverLetter: A string containing the applicant's cover letter
     * nameOfApplicant: String containing the name of the user who created this application
     * creationDate: LocalDate containing the the date the account was created
     * closingDate: LocalDate containing the date the application was closed
     * pushToNextRound: boolean determining whether to push the application to next round
     * hireStatus: boolean determining whether application was accepted
     * reviewStatus: boolean determining whether application was reviewed
     * currentRound: int containing the round the application is in
     * Letter: String that contains rejection letter (if application is rejected)
     * JobHiredInfo: Arraylist that stores the info if application is accepted for a job
     */
    private String resume;
    private String coverLetter;
    private Applicant applicant;
    private LocalDate creationDate;
    private LocalDate closingDate = null;
    private boolean pushToNextRound = false;
    private boolean hireStatus = false;
    private boolean reviewStatus = false;
    private int currentRound = 0;
    private Interviewer interviewer;
    private String Letter;
    private boolean matched = false;
    private ArrayList<String> JobHiredInfo;
    private String PostingTitle;
    private Posting Posting;

    // METHODS


    public UserApplication(Applicant Applicant, String resume, String coverLetter, LocalDate date, String PostingTitle, Posting p) {
        this.applicant = Applicant;
        this.resume = resume;
        this.coverLetter = coverLetter;
        this.creationDate = date;
        this.PostingTitle = PostingTitle;
        this.Posting = p;
    }

    /**
     * Returns the type of round the application is in as a String.
     *
     * @return The String translation of this application's round number.
     */
    public String roundNumberToString() {
        if (this.currentRound == 0) {
            return "This application has been received";
        } else if (this.currentRound == 1) {
            return "Phone interview";
        } else if (this.currentRound == 2) {
            return "Interview 1";
        } else if (this.currentRound == 3) {
            return "Interview 2";
        } else if (this.currentRound == 4) {
            return "Interview 3";
        } else {
            return "Invalid round";
        }
    }

    /**
     * Adds a closing date to the application.
     *
     * @param closingDate The date the application was closed.
     */

    public void CloseApplication(LocalDate closingDate){
        this.closingDate = closingDate;
    }

    /**
     * Returns the name of the applicant.
     *
     * @return The name of the applicant.
     */
    public String toString() {
        return this.Posting.getID()+ " - " +this.applicant.getUserName() + " - " + this.creationDate;
    }

    // GETTERS

    public LocalDate getClosingDate(){
        return this.closingDate;
    }
    public String getResume() {
        return resume;
    }
    public String getCoverLetter() {
        return coverLetter;
    }
    public Applicant getapplicant() {
        return applicant;
    }
    public LocalDate getCreationDate() {
        return creationDate;
    }
    public boolean isPushToNextRound() {
        return pushToNextRound;
    }
    public boolean getHirestatus() {
        return hireStatus;
    }
    public boolean getReviewstatus() {
        return reviewStatus;
    }
    public ArrayList getJobHired(){
        return JobHiredInfo;
    }
    public void setResume(String resume) {
        this.resume = resume;
    }
    public int getCurrentround() {
        return currentRound;
    }
    public String getLetter() {
        return Letter;
    }
    public boolean getMatched(){
        return matched;
    }
    public String getPostingTitle(){ return this.PostingTitle; }
    public Posting getPosting(){ return this.Posting; }
    // Setters

    public void setCoverLetter(String coverLetter) {
        this.coverLetter = coverLetter;
    }
    public void setNameOfApplicant(Applicant Applicant) {
        this.applicant= Applicant;
    }
    public void setCreationDate(LocalDate creationDate) {
        this.creationDate = creationDate;
    }
    public void setPushToNextRound(boolean pushToNextRound) {
        this.pushToNextRound = pushToNextRound;
    }
    public void setHirestatus(boolean status) {
        this.hireStatus = status;
    }
    public void setJobHired(ArrayList p){
        JobHiredInfo = p;
    }
    public void setReviewstatus (boolean status) {
        this.reviewStatus = status;
    }
    public void setCurrentround(int round) {
        this.currentRound = round;
    }
    public void setLetter (String Letter) {
        this.Letter = Letter;
    }
    public void setMatched(Boolean m){
        this.matched = m;
    }
    public void setPostingTitle(String title){ this.PostingTitle = title; }
    public void setPosting(Posting p){ this.Posting = p;}


    /**
     * Given an application that is hired, update the corresponding attributes
     *
     * @param app An userapplication objet
     */
    @Override
    public void Hiring_Update(UserApplication app) {
        setLetter("Congratulations You have been hired as " + PostingTitle);
        setHirestatus(true);
        this.setReviewstatus(true); //not reviewed by anyone
        CloseApplication(LocalDate.now());
    }



    public void NextRound_Update() {
        if (currentRound == 0) {
            this.setLetter("Posting is now closed. Please wait for a potential interview");
            this.currentRound++;
        }
        else if (currentRound > 0 & isPushToNextRound()) {
            this.setPushToNextRound(false); //Reset the next round status.
            this.setMatched(false); // not matched with interviewer
            this.setReviewstatus(false); //not reviewed by anyone
            this.setLetter("Congratulations, you passed " + this.roundNumberToString());
            this.currentRound++; //survived one more round
        } else if (!Posting.getRemainApplications().contains(this)) {
            this.CloseApplication(LocalDate.now());
            this.setLetter("Sorry, you did not get the Job");
        }
    }

    @Override
    public void WithDraw_Update(UserApplication app) {
        CloseApplication(LocalDate.now());
        setLetter("Application Withdrawn");
    }

    @Override
    public void Submission_Update(UserApplication app) {
        setLetter("Application Submitted");
    }

    public void InterviewNotification(Interviewer i) {
        setInterviewer(i);
        setMatched(true);
        setLetter("Congratulations!" + roundNumberToString() + " is scheduled! ");
    }

    public Interviewer getInterviewer() {
        return this.interviewer;

    }
    public void setInterviewer(Interviewer i) {
        this.interviewer = i;
    }

}
