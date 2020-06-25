package ProgramFiles;

import ProgramFiles.Exceptions.ApplicationExistsException;
import ProgramFiles.Exceptions.MissingRequirementException;
import java.time.LocalDate;
import java.time.Period;
import java.util.ArrayList;


public class Applicant extends User implements java.io.Serializable, NotificationSystem {
    /**
     * Class storing all the information of an applicant
     *
     * Attributes:
     *
     * applications: stores all the applications applicant ever submitted.
     * applicationsWithDrawn: stores all the applications withdrawn by the applicant
     * applicationsRejected: stores all the applications rejected by company
     * applicationsHired: stores all the applications accepted by Company;
     * applicationsLive: stores all the applications currently live and submitted;
     * applicationsInterview: stores all the applications currently live and received an interview;
     * resume: A string containing the applicant's resume
     * coverLetter: A string containing the applicant's cover letter
     * creationDate: LocalDate containing the the date the account was created
     * JobsHired: An ArrayList containing the current Jobs of the User
     * Dat: Stores current Date of the system (need for updating).
     *
     */

    private ArrayList<UserApplication> allapplications;
    private ArrayList<UserApplication> applicationsWithDrawn;
    private ArrayList<UserApplication> applicationsRejected;
    private ArrayList<UserApplication> applicationsHired;
    private ArrayList<UserApplication> applicationsSubmissionLive;
    private ArrayList<UserApplication> applicationsInterviewLive;
    private String resume;
    private String coverLetter;
    private LocalDate creationDate;
    private LocalDate Dat;


    /**
     *  Constructor
     *
     * @param userName user name of the applicant
     * @param password password of the account
     * @param resume  resume
     * @param coverLetter Cover Letter of a person
     * @param dt local date time
     */
    Applicant(String userName, String password, String resume, String coverLetter, LocalDate dt) {

        super(userName, password);
        this.resume = resume;
        this.coverLetter = coverLetter;
        this.Dat = LocalDate.now();
        this.creationDate = dt;
        this.allapplications = new ArrayList<>();
        this.applicationsWithDrawn = new ArrayList<>();
        this.applicationsHired = new ArrayList<>();
        this.applicationsRejected = new ArrayList<>();
        this.applicationsSubmissionLive = new ArrayList<>();
        this.applicationsInterviewLive = new ArrayList<>();
    }

    public String getType() {
        return "Applicant";
    }
    public ArrayList<UserApplication> getApplicationsWithDrawn() {
        return applicationsWithDrawn;
    }
    public ArrayList<UserApplication> getApplicationsRejected() {
        return applicationsRejected;
    }
    public ArrayList<UserApplication> getApplicationsHired () { return applicationsHired; }
    public ArrayList<UserApplication> getApplicationsSubmissionLive () { return applicationsSubmissionLive; }
    public ArrayList<UserApplication> getApplicationsInterviewLive () {
        return applicationsInterviewLive;
    }
    public ArrayList<UserApplication> getallapplications() {
        return allapplications;
    }
    public LocalDate getCreationDate() {
        return creationDate;
    }
    public String getResume() {
        return resume;
    }
    public String getCoverLetter() {
        return coverLetter;
    }



    /**
     * Test method for setting CurrentApplications
     */
    public void setApplications(ArrayList<UserApplication> applications) {
        this.allapplications = applications;
    }

    /**
     * Helper function for getLatest
     * Calculates latest Closing Date from a list of Applications
     *
     * @return LocalDate object
     */
    private LocalDate calculateLatestDate(ArrayList<UserApplication> apps){

        if (apps.size() > 0) {
            LocalDate latest = apps.get(0).getClosingDate();

            for (UserApplication app : apps) {
                if (app.getClosingDate() != null) {
                    if (latest == null) {
                        latest = app.getClosingDate();
                    } else if (latest.isBefore(app.getClosingDate())) {
                        latest = app.getClosingDate();
                    }
                }
            }
            if (latest != null)
                return latest;
        }
        return LocalDate.now();
    }
    /**
     * Returns the date the last application closed
     * If none of the applications are closed, then
     * return Current Local Time.now
     *
     * @return LocalDate object
     */
    private LocalDate getLatestApplication(){
        return calculateLatestDate(allapplications); }

    /**
     * removes cv and resume if last application was closed 30 days prior to today
     */
    void deleteOldInfo(){
        LocalDate latest = getLatestApplication();
        LocalDate today = Dat;

        Period p = Period.between(latest, today);

        if (p.getDays() > 30){
            this.coverLetter = "";
            this.resume = "";
        }

    }

    /**
     * @param cv : resume (String)
     */
    public void setResume(String cv) {
        this.resume = cv;
    }

    /**
     * @param coverLetter : CoverLetter (String)
     */
    public void setCoverLetter(String coverLetter) {
        this.coverLetter = coverLetter;
    }


    /**
     * Applying to a Job to a Posting. Checks various constraints to see whether the submission is valid.
     * Throws an exception if we see that the application already exists.
     *
     * @param p Posting Object Object
     * @throws ApplicationExistsException : Applications already exists
     */

    public void apply (Posting p) throws ApplicationExistsException, MissingRequirementException {
        UserApplication app = new UserApplication(this, resume, coverLetter, Dat, p.getTitle(), p);
        if (CheckSubmission(app)) {
            throw new ApplicationExistsException();
        }
        if (p.requiresResume() && p.requiresCoverLetter()) {
            if (resume.equals("") || coverLetter.equals("")) {
                throw new MissingRequirementException();
            } else {
                p.Submission_Update(app); // send application
                Submission_Update(app);
                app.Submission_Update(app);
            }
        } else if (p.requiresResume()) {
            if (resume.equals("")) {
                throw new MissingRequirementException();
            } else {
                p.Submission_Update(app); // send application
                Submission_Update(app);
                app.Submission_Update(app);
            }
        } else if (p.requiresCoverLetter()) {
            if (coverLetter.equals("")) {
                throw new MissingRequirementException();
            } else {
                p.Submission_Update(app); // send application
                Submission_Update(app);
                app.Submission_Update(app);
            }
        } else {
            p.Submission_Update(app); // send application
            Submission_Update(app);
            app.Submission_Update(app);
        }
    }

    /**
     * Given a application, withdraw it from a post. If the application is matched to an interviewer,
     * update the interviewer. Update the applicant, posting as well as the application itself
     *
     * @param app UserApplication Object
     */
    public void withdraw (UserApplication app){
            //Notify the relevant classes
            if (app.getMatched()){
                app.getInterviewer().withdraw_update(app);
            }
            app.getPosting().WithDraw_Update(app);
            WithDraw_Update(app);
            app.WithDraw_Update(app);
        }

    /**
     * Given an application that is hired, update the corresponding attributes
     *
     * @param app An UserApplication object
     */

    @Override
    public void Hiring_Update(UserApplication app) {
         app.setLetter("Congratulations on being hired!");
         applicationsHired.add(app);
         ApplicationsCleanUp(app);
    }

    /**
     * Given that we are moving forward to the next round, update the corresponding applications
     * @param app an Userapplication that the applicant currently owns
     */
     void NextRound_Update(UserApplication app) {
        // round 1 interview! add the user to interview live
        if (app.getCurrentround() == 1 & app.getMatched()){
            //First round, got an interview
            applicationsInterviewLive.add(app);
            applicationsSubmissionLive.remove(app);
        }
        else if (app.getCurrentround() == 1 & !app.getMatched()){
            //First round did not get interview
            app.setPushToNextRound(false);
            applicationsSubmissionLive.remove(app);
            applicationsRejected.add(app);
        } else if (app.getCurrentround() > 1 & !app.getMatched()) {
            // Subsequent round, did not get interview
            applicationsInterviewLive.remove(app);
            applicationsRejected.add(app);
        }
        //If got interview, but isn't pushed to the next round.
        if (app.getReviewstatus() & !app.isPushToNextRound()) {
            //Interviewed and not passed.
            applicationsInterviewLive.remove(app);
            applicationsRejected.add(app);
        }
        //In case of round 0, nothing happens.
    }

    /**
     * Given that we are withdrawing an application, update the corresponding attributes
     * @param app applications
     */
    @Override
    public void WithDraw_Update(UserApplication app) {
        applicationsWithDrawn.add(app);
        ApplicationsCleanUp(app);
    }


    /**
     * Check whether or not we have already submitted an application
     * @param app An user application object.
     * @return boolean true if we submitted
     */

    private boolean CheckSubmission(UserApplication app) {
        boolean found = false;
        ArrayList<UserApplication> merged = new ArrayList <>();
        merged.addAll(applicationsSubmissionLive);
        merged.addAll(applicationsInterviewLive);
        for (UserApplication applications : merged) {
            if (applications.getPostingTitle().equals(app.getPostingTitle())) {
                found = true;
            }
            }
        return found;
        }

    /**
     * Update the corresponding attributes when a new application is submitted.
     *
     *  @param app An user application object.
     */
    @Override
    public void Submission_Update(UserApplication app) {
            allapplications.add(app);
            applicationsSubmissionLive.add(app);
    }

    /**
     * Helper function to clean up the application attributes.
     */

    private void ApplicationsCleanUp(UserApplication app){
        applicationsSubmissionLive.remove(app);
        applicationsInterviewLive.remove(app);
    }
}
