package ProgramFiles;

import java.io.*;

public class IncrementIDGenerator implements IDGenerator {
    /**
     * Attributes for IncrementIDGenerator
     */
    private final String fileName = "currentID.txt";

    private String ID = readFile();
    /**
     * Increment the integer by 1 and write to the text file
     */
    private void writeFile(){
        try {
            String bytes = Integer.toString(Integer.parseInt(this.ID) + 1);
            byte[] buffer = bytes.getBytes();

            FileOutputStream outputStream =
                    new FileOutputStream("currentID.txt");

            outputStream.write(buffer);

            outputStream.close();
        }
        catch(IOException ex) {
        }
    }
    /**
     * Write to the file
     */
    private void writeFileFirst(){
        try {
            String bytes = "100000";
            byte[] buffer = bytes.getBytes();

            FileOutputStream outputStream =
                    new FileOutputStream("currentID.txt");

            outputStream.write(buffer);

            outputStream.close();
        }
        catch(IOException ex) {
        }
    }
    /**
     * Read the file
     */
    private String  readFile(){
        String line = null;
        try {
            FileReader fileReader =
                    new FileReader(fileName);

            BufferedReader bufferedReader =
                    new BufferedReader(fileReader);
            line = bufferedReader.readLine();

            // Always close files.
            bufferedReader.close();
        }
        catch(FileNotFoundException ex) {
        } catch (IOException e){}
        return line;
    }
    /**
     * Combine all helper functions and generate ID
     */

    public IncrementIDGenerator(){
        if (this.ID == null) {
            writeFileFirst();
            this.ID = readFile();
        }
        writeFile();
    }
    /**
     * Getter for ID
     * @return ID string
     */
    public String getNewID(){
        return this.ID;
    }
}
