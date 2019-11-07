import java.time.DateTimeException;
import java.time.LocalDate;
import java.util.ArrayList;

import attendance.*;

import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

/**
 * This is the main class of the application.
 */
public class Controller {
    private HashMap<LocalDate, ArrayList<Attendance>> attendances;
    private ArrayList<Member> members;
    private View view;
    private FileManager fm;

    /**
     * Constructor of the Controller class.
     */
    public Controller() {
        attendances = new HashMap<>();
        members = new ArrayList<>();
        view = new View();
        fm = new FileManager();
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
                    members.addAll(fm.readFileMembers("src/main/resources/member-list.json"));
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
                    fm.writeJsonToFile(fm.convertAttendanceToJson(attendances),
                            "src/main/resources/attendance-list.json");
                    view.printSaveMessage();
                    break;
                case "4":
                    //Load attendance
                    attendances.putAll(fm.readFileAttendance("src/main/resources/attendance-list.json"));
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
        ArrayList<Attendance> attendance = new ArrayList<>();
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
                        attendance.add(new Attendance(member, true));
                        quit = true;
                        break;
                    case "n":
                        attendance.add(new Attendance(member, false));
                        quit = true;
                        break;
                    default:
                        view.printInvalidInput();
                        break;
                }
            }
            countMembers++;
        }

        attendances.put(date,attendance);

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
        Scanner scan = new Scanner(System.in);
        return scan.nextLine();
    }

    public void printAttendance(){
        //show list of dates
        attendances.entrySet().stream()
                .map(HashMap.Entry::getKey)
                .distinct()
                .forEach(System.out::println);

        //User chooses a date
        //If it is valid show attendance for that day
        view.printDateQuestion();
        LocalDate date = convertDate(userInput());
        if (date != null) {
            attendances.entrySet().stream()
                        .map(Map.Entry::getValue)
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