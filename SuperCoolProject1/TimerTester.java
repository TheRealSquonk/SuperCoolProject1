import java.util.Timer;
import java.util.TimerTask;
import java.util.Scanner;
import java.io.*;

/**
 * 
 * @author YurMum
 * 
 * @date December 16th, 1991
 * 
 *       Accesses the UCLA Transcript Database
 * 
 *       Allows user to access a pre existing txt file transcript
 *       and set desired grade on a certain class. Why? Because is funny.
 * 
 * 
 * 
 * @todo fix so initial trasncript is printed with user input ID and updated
 *       trasncript has correct grades/ID
 * 
 */

public class TimerTester {

    public static void main(String[] args) {

        Scanner keyboard = new Scanner(System.in);

        runDecryptionScript();

        try {

            // Ask for Student ID

            String studentID = null;
            boolean proceed = false;

            while (!proceed) {
                System.out.print("Provide Student ID Number: ");
                studentID = keyboard.nextLine();

                if (studentID.length() != 9) {
                    System.out.println("Student ID must be 9 digits!");
                } else {
                    System.out.println("Student ID input accepted.\n");
                    proceed = true;
                }
            }

            // Read entire transcript into memory
            BufferedReader reader = new BufferedReader(new FileReader("UCLA_Transcript.txt"));
            StringBuilder transcript = new StringBuilder();

            String line;
            while ((line = reader.readLine()) != null) {
                transcript.append(line).append("\n");
            }
            reader.close();

            // Print transcript to screen
            System.out.println("===== CURRENT TRANSCRIPT =====");
            System.out.println(transcript.toString());

            // Ask which class the user wants to modify

            System.out.print("Which class do you want to modify? (ex: BIO 343): ");
            String targetClass = keyboard.nextLine();

            System.out.print("Input new grade: ");
            double newGrade = keyboard.nextDouble();
            keyboard.nextLine(); // clear leftover newline

            // Modify ONLY that line

            BufferedReader reader2 = new BufferedReader(new FileReader("UCLA_Transcript.txt"));
            StringBuilder updatedText = new StringBuilder();

            String currentLine;
            while ((currentLine = reader2.readLine()) != null) {

                if (currentLine.startsWith("Student ID")) {
                    currentLine = "Student ID: " + studentID; // update ID location
                }

                // Replace grade for chosen class
                if (currentLine.startsWith(targetClass)) {
                    String[] parts = currentLine.split(":");
                    currentLine = parts[0] + ": " + newGrade;
                }

                updatedText.append(currentLine).append("\n");
            }

            reader2.close();

            // Rewrite updated transcript to file

            PrintWriter writer = new PrintWriter("UCLA_Transcript.txt");
            writer.print(updatedText.toString());
            writer.close();

            System.out.println("\nGrade updated successfully!");
            System.out.println("Updated transcript saved.\n");
            System.out.println(transcript.toString());

        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());

        } finally {
            System.out.println("Ending Program.");
            keyboard.close();
        }
    }

    /**
     * Runs super secret decryption software on University database
     */

    public static void runDecryptionScript() {
        Timer timer = new Timer();
        TimerTask task = new TimerTask() {

            int count = 4;

            @Override
            public void run() {
                System.out.println("\nAccessing...\nFailed.\nReatempting...");
                count--;
                if (count <= 0) {
                    System.out.println("\nAccess Granted.");
                    timer.cancel();
                }
            }

        };

        System.out.println(
                "Running Decryption Script on ([UNIVERSITY OF CALIFORNIA LOS ANGELES] TRANSCRIPT DATABASE)...\n");

        timer.schedule(task, 2000, 2000);

        try {
            Thread.sleep(10000);

        } catch (InterruptedException e) {

        }
    }

}
