import java.time.DateTimeException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Comparator;
import attendance.*;
import java.util.Scanner;

/**
 * This is the main class of the application.
 */
public class Controller {
    private ArrayList<Attendance> attendances;
    private ArrayList<Member> members;
    private View view;
    private FileManager fm;
    private Scanner scan;

    /**
     * Constructor of the Controller class.
     */
    public Controller() {
        attendances = new ArrayList<>();
        members = new ArrayList<>();
        view = new View();
        fm = new FileManager("src/main/resources/");
        scan = new Scanner(System.in);
    }

    /**
     * Method with the main routine that will keep program running
     * until user wants to quit.
     */
    private void start() {
        view.printWelcome();
        while (true) {
            view.printOptions();
            switch (userInput()) {
                case "1":
                    //Load members file to application
                    members.clear();
                    members.addAll(fm.readFileMembers());
                    view.printLoadMessage();
                    checkDuplicateId();
                    break;
                case "2":
                    // Check attendance
                    if(members.size() == 0){
                      view.printNoMembersInList();
                    }
                    else {
                        view.printDateQuestion();
                        LocalDate date = convertDate(userInput());
                        if (date != null) {
                            checkAttendance(date);
                        } else {
                            view.printInvalidInput();
                        }
                    }
                    break;
                case "3":
                    // Save attendance to file
                    attendances.addAll(fm.readFileAttendance());
                    fm.writeFileAttendance(attendances);
                    view.printSaveMessage();
                    break;
                case "4":
                    //Load attendance
                    attendances.addAll(fm.readFileAttendance());
                    // Display attendance
                    if(attendances.size() == 0){
                        view.printNoMembersInList();
                    }
                    else{
                        printAttendance();
                    }
                    break;
                case "5":
                    // Create and edit member file
                    System.out.println("To be implemented.");
                    break;
                case "6":
                    //quit
                    return;
                default:
                    view.printInvalidInput();
            }
            view.printReturnMenu();
            userInput();
        }
    }

    /**
     * Method that allows user to check attendance for a specific day.
     * @param date
     */
    private void checkAttendance(LocalDate date) {
        int countMembers = 0;
        for(Member member : members){
            boolean quit = false;
            while (!quit){
                //print the member
                System.out.println(member.getMemberID() + ", " + member.getMemberName());

                //print message
                view.printAttendanceMessage();

                switch (userInput().toLowerCase()){
                    case "y":
                        attendances.add(new Attendance(member, date, true));
                        quit = true;
                        break;
                    case "n":
                        attendances.add(new Attendance(member, date, false));
                        quit = true;
                        break;
                    default:
                        view.printInvalidInput();
                        break;
                }
            }
            countMembers++;
        }

        view.printAttendanceComplete(countMembers);
    }

    /**
     * Converts date from a string to local date type.
     * @param date
     * @return
     */
    public static LocalDate convertDate(String date) {
        LocalDate convertedDate;
        try {
            convertedDate = LocalDate.parse(date);
        } catch (DateTimeException e) {
            return null;
        }
        return convertedDate;
    }

    /**
     * Collect user input.
     * @return user input as a String.
     */
    public String userInput() {
        return scan.nextLine();
    }

    public void printAttendance(){
        //show list of dates
        attendances.stream()
                .map(Attendance::getDate)
                .distinct()
                .forEach(System.out::println);

        //User chooses a date
        //If it is valid show attendance for that day
        view.printDateQuestion();
        LocalDate date = convertDate(userInput());
        if (date != null) {
            attendances.stream()
                        .filter(attendance -> attendance.getDate().compareTo(date) == 0)
                        .sorted(Comparator.comparing(attendance -> attendance.getMember().getMemberName()))
                        .map(Attendance::toString)
                        .forEach(System.out::println);
        } else {
            view.printInvalidInput();
        }
    }

    /**
     * Collect accurate user input, that can be turned into int, otherwise return -1
     *
     * @param number the number in a String.
     * @return a number as an int.
     */
    private int convertIndexToInt(String number) {
        int convertedIndex;
        try {
            convertedIndex = Integer.parseInt(number);
            return convertedIndex;
        } catch (NumberFormatException e) {
            return -1;
        }
    }

    /**
     * Check for duplicate ids.
     */
    public void checkDuplicateId(){
        long size = members.stream()
                    .map(member -> member.getMemberID())
                    .distinct()
                    .count();

        if(size != members.size()){
            view.printDuplicateIdMessage();
        }
    }

    /**
     * Start of the application.
     * @param args
     */
    public static void main(String[] args) {
        Controller controller = new Controller();
        controller.start();
    }
}