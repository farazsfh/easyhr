package ProgramFiles;

public abstract class User implements java.io.Serializable {
    protected String userName;
    protected String password;
    
    public User(String userName, String password){
        this.userName = userName;
        this.password = password;
    }

    public String getUserName() {
        return this.userName;
    }

    protected String getPassword() {
        return this.password;
    }

}
