package ProgramFiles;
import java.util.ArrayList;




public class HR extends User {
    /** Stores all the attributes of a HR.
     *
     * Attributes:
     *
     * AllPosting: All postings in a company
     * username: username to log in
     * password: password to log in
     *
     *
     */

    private ArrayList<Posting> AllPosting;
    // Constructor: let's pass directly company's attribute posting into each HR.

    public HR(String userName, String password, ArrayList<Posting> CompanyPosting) {
        super(userName, password);
        this.AllPosting = CompanyPosting;
    }

    public ArrayList<Posting> getAllPosting(){return this.AllPosting;}

    /**
     * Grab all applications for a specific posting
     * Returns an ArrayList object consisting of UserApplication objects.
     * Theses are all the applications submitted by the applicant
     *
     * @param ID ID of a posting
     * @return  an array of UserApplication
     *
    */

    public ArrayList<UserApplication> GrabAllApplications(String ID){
        Posting p = SearchPosting(ID);
        return p.getAllApplications();
    }
    /**
     * Returns an ArrayList object consisting of UserApplication objects.
     * Theses are all the applications that are left in the interview process with a specific posting
     *
     * @param ID unique ID to identify the posting obeject.
     * @return  an array of UserApplication
     *
     */
    private ArrayList<UserApplication> GrabRemainApplications(String ID){
        //Grab all remaining applications for a specific posting
        Posting p = SearchPosting(ID);
        return p.getRemainApplications();
    }


    /**
     * Returns a boolean object specifying whether HR can proceed to the next round.
     * All matched applications must be reviewed in order to proceed
     *
     * @param ID unique ID to identify the posting obeject.
     * @return  boolean
     *
     */

    private boolean canProceed(String ID){
        Posting p = SearchPosting(ID);
        for (UserApplication ua: GrabRemainApplications(ID)){
            //If application is matched but not reviewed, we can't proceed.
            if ((ua.getMatched()) & (!ua.getReviewstatus())){
                return false;
            }
        }
        if (p.getCurrentRound() == 0) {
            System.out.println("can't push to next round. Has to start interview first by calling StartInterview()");
            return false;
            }

        return true;
    }
    /**
     * Returns an ArrayList object consisting of UserApplication objects.
     * Theses are all the applications that are left unmatched to an interviewer
     *
     * @param ID unique ID to identify the posting obeject.
     * @return  an array of UserApplication
     *
     */

    public ArrayList<UserApplication> GetUnmatchedApplications(String ID){
        ArrayList<UserApplication> Unmatched = new ArrayList<>();;
        for (UserApplication ua: GrabRemainApplications(ID)){
            if (!(ua.getMatched())){
                Unmatched.add(ua);
            }}
        return Unmatched;
    }


    /**
     * Returns an ArrayList object consisting of UserApplication objects.
     * Theses are all the applications that are not reviewed by an interviewer
     *
     * @param ID unique ID to identify the posting obeject.
     * @return  an array of UserApplication
     *
     */
    public ArrayList<UserApplication> GetUnviewedApplications(String ID){
        ArrayList<UserApplication> Unviewed = new ArrayList<>();;
        for (UserApplication ua: GrabRemainApplications(ID)){
            if (!(ua.getReviewstatus())){
                Unviewed.add(ua);
            }}
        return Unviewed;
    }


    /**
     * Set the candidate to be hired for a posting
     *
     * @param p a Posting object
     * @param a an UserApplication object
     *
     */

    public void SetHires(Posting p, UserApplication a){
        //Set the hire for a job.
        //Change the fill status
       p.Hiring_Update(a);
       a.Hiring_Update(a);
    }

    /**
     * Match an application to an interviewer
     *
     * @param i a Interviewer object
     * @param a an UserApplication object
     */

    public void MatchInterviewerApplication (Interviewer i, UserApplication a) {
        i.AddApplication(a);
        a.InterviewNotification(i);
    }

    /**
     * Start the interviewing process of a posting. Posting is now closed and no applications can be submitted
     *
     * @param ID unique ID to identify the posting obeject.
     */
    public void StartNextRoundInterview(String ID) {
        Posting p = SearchPosting(ID);
        if (p.getCurrentRound() > 0) {
            if (canProceed(ID)) {
                p.NextRound_Update();
            } else {
                System.out.println("Cannot proceed");
            }
        }
        else {
            StartInterview(ID);
        }
    }

    /**
     * Given an ID of a posting, search for it.
     *
     * @param ID unique ID to identify the posting obeject.
     */
    private Posting SearchPosting(String ID) {
        //Posting must be false to start interview
        for (Posting p : this.AllPosting){
            if (p.getID().equals(ID)){
                return p;
            }
        }
        return null;
    }


    /**
     * Start the interviewing process. Check relevant attributes to make sure everything is correct.
     *
     * @param ID unique ID to identify the posting obeject.
     */

    private void StartInterview(String ID) {
        //Posting must be false to start interview
        Posting p = SearchPosting(ID);
        p.NextRound_Update();
    }



}
