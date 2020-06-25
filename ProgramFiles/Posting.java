package ProgramFiles;

import java.util.Arrays;
import java.util.List;


import javax.swing.text.html.HTML;
import java.util.ArrayList;
import java.time.LocalDate;

public class Posting implements java.io.Serializable, NotificationSystem {
    /**
     * Stores all the attributes of a Posting
     * <p>
     * Attributes:
     * <p>
     * ID: Unique string to identify posting
     * JobDescription: description of the job
     * Title: title of the job
     * CreationDate: the date the posting is created.
     * CloseDate: the date that no more applications an be submitted
     * SystemDate: current date of the system
     * FillStatus: whether the job has been filled
     * PostStatus: whether posting is still open for submission of applications
     * AllApplicationSubmitted: An array list containing all the applications submitted to the job
     * RemainApplications: An array list containing all the applications that are still in the process of interview
     * CurrentRound: the current round of the interview
     * MaxRounds: Default is 4. Maximum rounds a posting can have
     * HiredAPplication: the application that got chosen
     */
    private String ID;
    private String JobDescription;
    private String Title;
    private String RequiredSkills;
    private LocalDate CreationDate;
    private LocalDate CloseDate; //Should wait until the posting is closed to start interviewing everyone.
    private LocalDate SystemDate;
    private boolean RequiresResume;
    private boolean RequiresCoverLetter;
    private boolean FilledStatus = false;
    private boolean PostStatus = true; // Check if the posting is still open or not. Could be closed for many reasons.

    // All Applications Submitted will be stored in AllApplicationsSubmitted
    private ArrayList<UserApplication> AllApplicationsSubmitted = new ArrayList<>();
    // Each round, we delete from the RemainApplications;
    private ArrayList<UserApplication> RemainApplications = new ArrayList<>();
    private String[] tags;
    private int CurrentRound = 0;
    private static final int MaxRounds = 4; //maximum number of rounds is 4 according to the handout
    private UserApplication HiredApplication;


    //Getters
    public String getID() {
        return ID;
    }
    public String getJobDescription() {
        return JobDescription;
    }
    public ArrayList<UserApplication> getAllApplications() {
        return this.AllApplicationsSubmitted;
    }
    public ArrayList<UserApplication> getRemainApplications() {
        return this.RemainApplications;
    }
    public UserApplication getHiredApplication() { return this.HiredApplication; }
    public LocalDate getSystemDate() {
        return this.SystemDate;
    }
    public LocalDate getCloseDate() {
        return this.CloseDate;
    } // No one can submit after getCLoseDate
    public String getTitle() {
        return this.Title;
    }
    public int getCurrentRound() {
        return this.CurrentRound;
    }
    public boolean getPostStatus() {
        return this.PostStatus;
    }
    public boolean getFilledStatus() {
        return this.FilledStatus;
    }

    // Setters
    public void setFilledStatus(boolean Status) {
        FilledStatus = Status;
    }
    public void setPostingStatus(boolean Status) {
        this.PostStatus = Status;
    }
    public void setHiredApplicant(UserApplication application) {
        this.HiredApplication = application;
    }
    public void setRemainApplications(ArrayList<UserApplication> apps) { this.RemainApplications = apps; }




    /**
     *  Constructor
     *
     * @param ID user name of the applicant
     * @param Title password of the account
     * @param JobDescription resume
     * @param CreationDate Cover Letter of a person
     * @param CloseDate local date time
     * @param SystemDate the date of the system when the posting is created
     * @param RequiresResume whether or not the posting requires resume
     * @param RequiresCoverLetter whether or not the posting requires cover letter
     * @param Tags an array list of strings
     */

    public Posting(String ID, String Title, String JobDescription, LocalDate CreationDate, LocalDate CloseDate, LocalDate SystemDate, Boolean RequiresResume,
                   Boolean RequiresCoverLetter, String[] Tags) {
        this.ID = ID;
        this.Title = Title;
        this.JobDescription = JobDescription;
        this.CreationDate = CreationDate;
        this.CloseDate = CloseDate;
        this.SystemDate = SystemDate;
        this.RequiresResume = RequiresResume;
        this.RequiresCoverLetter = RequiresCoverLetter;
        this.tags = Tags;
    }

    /**
     *  Hiring a specific application
     *  update the corresponding applicants, application and posting.
     *
     *  @param application an UserApplication object
     */

    public void hire(UserApplication application){
        Hiring_Update(application);
        application.Hiring_Update(application);
        application.getapplicant().Hiring_Update(application);
    }


    /**
     * Check if the current round of the posting is at 4th round
     *
     * @return boolean object
     */
    private boolean CheckMaxRound() {
        return CurrentRound <= 4;
    }

    /**
     * Check if a user have already submitted an application
     *
     * @param userName username of the applicant
     * @return boolean object
     */

    private boolean ContainUserSubmission(String userName) {
        //Posting must be false to start interview
        boolean found = false;
        for (int i = 0; i < this.AllApplicationsSubmitted.size(); ++i) {
            UserApplication CurrentApplication = this.AllApplicationsSubmitted.get(i);
            if (CurrentApplication.getapplicant().getUserName().equals(userName)) {
                found = true;
            }
        }
        return found;
    }
    /**
     * Return null if cannot find userName's application. Otherwise, return the application.
     *
     * @param userName username of applicant
     * @return UserApplication object
     */

    public UserApplication FindUserSubmission(String userName) {
        //Posting must be false to start interview
       UserApplication app = null;
        for (int i = 0; i < this.AllApplicationsSubmitted.size(); ++i) {
            UserApplication CurrentApplication = this.AllApplicationsSubmitted.get(i);
            if (CurrentApplication.getapplicant().getUserName().equals(userName)) {
                app = CurrentApplication;
            }
        }
        return app;
    }


    /**
     * Returns an int object counting number of remaining applications
     * @return  int
     */

    public int CountNumRemainApplicantions(){ return this.getRemainApplications().size(); }
    /**
     * Returns an int object counting number of unmatched applications
     *
     * @param p a Posting object
     * @return  int numebr of unmatched applications
     */
    public int CountUnmatchedApplications(Posting p){
        int NumUnmatched = 0;
        for (UserApplication ua: RemainApplications){
            if (!(ua.getMatched())){
                NumUnmatched = NumUnmatched + 1;
            }}
        return NumUnmatched;
    }

    /**
     * Returns an int object counting number of applications not interviewed
     * @return  an int object  that counts the number of unviewed applications
     */
    public int CountUnviewedApplications(){
        int NumUnviewed = 0;
        for (UserApplication ua: RemainApplications){
            if (!(ua.getReviewstatus())){
                NumUnviewed = NumUnviewed + 1;
            }}
        return NumUnviewed;
    }
    /**
     * Check whether or not the input tag is inside the posting
     * @return boolean true or false
     */

    private boolean hasTag(String input) {
        List<String> tagList = Arrays.asList(this.tags);
        return tagList.contains(input);
    }

    /**
     * Check whether or not the input tag has every single tag in the given array
     * @return boolean true or false
     */


    public boolean hasAllTags(String[] inputs) {
        for (String input : inputs) {
            if (!(this.hasTag(input))) {
                return false;
            }
        }

        return true;
    }
    /**
     * Requirement for a posting
     * @return boolean true or false for requirement
     */

    boolean requiresResume() {
        return this.RequiresResume;
    }

    /**
     * Requirement for a posting on cover letter
     * @return boolean true or false for requirement
     */

    boolean requiresCoverLetter() {
        return this.RequiresCoverLetter;
    }

    /**
     * Update the corresponding attributes and statuses once someone is hired
     *
     * @param app an UserApplication object
     */


    @Override
    public void Hiring_Update(UserApplication app) {
        setHiredApplicant(app);
        setFilledStatus(true);
        setPostingStatus(false);
    }

    /**
     * helper function for proceeding to the next round
     *
     * @return An array list of user applications
     */

    private ArrayList<UserApplication> RoundHelper() {
        ArrayList<UserApplication> Remains = new ArrayList<>();
        for (UserApplication CurrentApplication : this.RemainApplications) {
            if (CurrentApplication.getCurrentround() > 0 & CurrentApplication.isPushToNextRound()) {
                Remains.add(CurrentApplication);
            } else if (CurrentApplication.getCurrentround() == 0) {
                   Remains.add(CurrentApplication);
            }
            CurrentApplication.NextRound_Update();
            CurrentApplication.getapplicant().NextRound_Update(CurrentApplication);
            roundHelper(CurrentApplication.getapplicant());
        }
        return Remains;
    }

    private void roundHelper(Applicant applicant){
        for (UserApplication application:applicant.getallapplications()){
            application.NextRound_Update();
        }
    }

    /**
     * Given that we are moving forward to the next round, update the corresponding observers
     */

    void NextRound_Update() {
        if (CurrentRound == 0 & PostStatus) {
            //Make a shallow copy.
            this.CurrentRound = 1;
            for (UserApplication application:this.getAllApplications()){
                application.NextRound_Update();
                application.getapplicant().NextRound_Update(application);
            }
            this.RemainApplications = this.getAllApplications();
            setPostingStatus(false);
        } else if (CheckMaxRound() & this.CurrentRound >= 1) {
            //Next Round keeps all the applications that passed.
            //LOOP through all the applications and push applications to next rounds.
            this.RemainApplications = RoundHelper();
            this.CurrentRound = CurrentRound + 1;
            // If only one person is remained, that person is hired!
            if (this.RemainApplications.size() == 1) {
                // If only one person is remained, that person is hired!
                UserApplication SuccessApplication = this.RemainApplications.get(0);
                SuccessApplication.Hiring_Update(SuccessApplication);
                Hiring_Update(SuccessApplication);
                SuccessApplication.getapplicant().Hiring_Update(SuccessApplication);
            }
        } else if (!CheckMaxRound()){
                System.out.println("There can be no more than 4 interviews");
                System.out.println("Human Resource Must choose a person");
            }
        }



    /**
     * Given that we are withdrawing an application, update the corresponding attributes
     * @param app applications
     */
    @Override
    public void WithDraw_Update(UserApplication app) {
        this.getAllApplications().remove(app);
        this.getRemainApplications().remove(app);
    }

    /**
     * Update the corresponding attributes when a new application is submitted.
     *
     *  @param app An user application object.
     */
    @Override
    public void Submission_Update(UserApplication app) {
        if (this.PostStatus & !ContainUserSubmission(app.getapplicant().getUserName())) {
            this.AllApplicationsSubmitted.add(app);
            System.out.println("Submission Success.");
        } else if (!this.PostStatus) {
            //Posting status is false if posting is closed. meaning interviewing or job is filled
            System.out.println("Submission Failure. Posting is closed or Job is filled.");
        } else if (ContainUserSubmission(app.getapplicant().getUserName())) {
            System.out.println("User have already submitted an application. Remove before apply");
        }
    }


    public String toString() {
        return Title + '-' + ID;
    }

}
