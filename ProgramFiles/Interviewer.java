package ProgramFiles;

import ProgramFiles.GuiFiles.SuperPanel;

import java.util.ArrayList;



public class Interviewer extends User {
    /** Stores all the attributes of a Interviewer
     *
     * Attributes:
     *
     * ApplicationsViewed: All applications interviewed
     * currentApplications: Applications that is allocated to the interviewer and haven't been matched
     * username: username to log in
     * password: password to log in
     *
     *
     */
    private ArrayList<UserApplication> ApplicationsViewed = new ArrayList<>();;
    private ArrayList<UserApplication> currentApplications = new ArrayList<>();;


    public Interviewer(String userName, String password) {
        super(userName, password);
    }



    /**
     * Update the status of an application after interviewing them
     *
     * @param a an UserApplication object
     * @param NextRoundStatus a boolean object, true means they pass to next round
     *
     */


    public void updateApplicant(UserApplication a, boolean NextRoundStatus){
        a.setPushToNextRound(NextRoundStatus);
        a.setReviewstatus(true);
        ApplicationsViewed.add(a);
        currentApplications.remove(a);
    }

    /**
     * Allow HR to match interviewer to interview an application
     *
     * @param a an UserApplication object
     */
    public void AddApplication(UserApplication a){
        currentApplications.add(a);
        a.setReviewstatus(false);
    }

    /**
     * Given an applicant withdraw their applications, update corresponding attribute.
     */

    public void withdraw_update(UserApplication a){
        currentApplications.remove(a);
    }


    /**
     * Getter to grab all current applications
     */
    public ArrayList<UserApplication> getCurrentApplications() { return currentApplications; }

    /**
     * Getter to grab userName
     */
    public String toString() {
        return getUserName();
    }

}
