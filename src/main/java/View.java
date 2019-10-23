/**
 * This class represents an user interface.
 */
public class View {

    /**
     * Constructor of View class.
     */
    public View(){
    }

    /**
     * Print out a Welcome message to the user.
     */
    public void printWelcome() {
        System.out.println(">> Welcome to Attendance application\n");
    }

    /**
     * Print main menu.
     */
    public void printOptions(){
        System.out.println(">> Pick an option:");
        String[] actions = {">> (1) Load member file", ">> (2) Check attendance", ">> (3) Save attendance to file",
               ">> (4) Display attendance", ">> (5) Create and edit member file", ">> (6) Quit"};
        for (int i = 0; i < actions.length; i++) {
            System.out.println(actions[i]);
        }
    }

    /**
     * Print a message asking for any input to return to the main menu
     */
    public void printReturnMenu() {
        System.out.println(">> Please, press Enter to return to the menu");
    }

    /**
     * Print a message stating that the list was loaded.
     */
    public void printLoadMessage() {
        System.out.println(">> List loaded into the application.");
    }

    /**
     * Print a message stating that the list was saved.
     */
    public void printSaveMessage() {
        System.out.println(">> List saved in the file.");
    }

    /**
     * Print a message asking the user to insert a date.
     */
    public void printDateQuestion() {
        System.out.println(">> Insert a date to check attendance:");
    }

    /**
     * Print a message stating that the input was valid.
     */
    public void printInvalidInput() {
        System.out.println(">> Invalid input.");
    }

    /**
     * Print a message asking user to mark attendance
     */
    public void printAttendanceMessage() {
        System.out.println(">> Check attendance by pressing Y/N:");
    }

    /**
     * Print a message asking user to mark attendance
     */
    public void printNoMembersInList() {
        System.out.println(">> You need to load members into the application first.");
    }

    /**
     * Print a message asking user to mark attendance
     */
    public void printNoAttendancesInList() {
        System.out.println(">> There are no attendance sheets to check.");
    }

    /**
     * Print a message showing that attendance is complete.
     */
    public void printAttendanceComplete(int totalMembers) {
        System.out.println(">> Attendance complete for the " + totalMembers + " members of the class.");
    }

    /**
     * Print a message stating that are duplicate ids.
     */
    public void printDuplicateIdMessage() {
        System.out.println(">> WARNING: the list as members with duplicate ids!");
    }
}