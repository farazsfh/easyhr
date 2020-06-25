package ProgramFiles.GuiFiles;

import javax.swing.*;

import ProgramFiles.User;
import ProgramFiles.Storage;

public class SuperPanel extends JPanel {

    private static User currentUser;
    private static Storage storage;

    public static User getCurrentUser() { return currentUser; }

    public static Storage getStorage() { return storage; }

    public static void setCurrentUser(User newCurrentUser) { currentUser = newCurrentUser; }

    public static void setStorage(Storage newStorage) { storage = newStorage; }

}
