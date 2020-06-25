package ProgramFiles;
/**
 * A notification system that unifies the way different classes talk with each other
 *
 *
 */

public interface NotificationSystem {
    /**
     * Update relevant fields when an application is hired.
     *
     * @param app Userapplication object
     */
    public abstract void Hiring_Update(UserApplication app);

    /**
     * Update relevant fields when an application is sent to the next round
     *
     * @param app Userapplication object
     */

    public abstract void WithDraw_Update(UserApplication app);
    /**
     * Update relevant fields when an application is submitted to a post
     *
     * @param app An user application
     */

    public abstract void Submission_Update(UserApplication app);
}
