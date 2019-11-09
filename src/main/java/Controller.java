import java.time.DateTimeException;
import java.time.LocalDate;
import java.util.ArrayList;

import attendance.*;

import java.util.HashMap;
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
                    //Load members file
                    loadMemberList();
                    checkDuplicateId();
                    break;
                case "2":
                    // Check attendance
                    // if a member file was loaded
                    if(members.size() == 0){
                      view.printNoMembersInList();
                    }
                    else {
                        view.printDateQuestion();
                        LocalDate date = convertDate(userInput());
                        if (date != null) {
                            //Load attendances from file
                            loadAttendanceList();
                            //Check attendance
                            checkAttendance(date);
                            // Save attendance to file
                            fm.writeJsonToFile(fm.convertAttendanceToJson(attendances),
                                    "attendance-lists/","attendance-list.json");
                            view.printSaveMessage();
                        } else {
                            view.printInvalidInput();
                        }
                    }
                    break;
                case "3":
                    //Load attendance from file
                    loadAttendanceList();

                    // Display attendance
                    printAttendance();
                    break;
                case "4":
                    // Create new member file
                    System.out.println("To be implemented.");
                    break;
                case "5":
                    // Edit an existing member file
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
     * Loads a specific file with members list.
     */
    private void loadMemberList(){
        members.clear();
        view.printFileNameRequest();
        members.addAll(fm.readFileMembers("member-lists/" + userInput()));
        view.printLoadMessage();
    }

    /**
     * Loads the attendance list.
     */
    private void loadAttendanceList(){
        attendances.clear();
        attendances.putAll(fm.readFileAttendance("attendance-lists/attendance-list.json"));
    }

    /**
     * Method that allows user to check attendance for a specific day.
     * @param date
     */
    private void checkAttendance(LocalDate date) {
        ArrayList<Attendance> attendance = new ArrayList<>();
        int present = 0;
        int absent = 0;
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
                        present++;
                        break;
                    case "n":
                        attendance.add(new Attendance(member, false));
                        quit = true;
                        absent++;
                        break;
                    default:
                        view.printInvalidInput();
                        break;
                }
            }
        }

        attendances.put(date,attendance);

        view.printAttendanceComplete(present, absent);
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
        // if is there any data in the attendance file
        if(attendances.size() == 0){
            view.printNoAttendancesInList();
        }
        else {
            //show list of dates
            attendances.entrySet().stream()
                    .map(HashMap.Entry::getKey)
                    .forEach(System.out::println);

            //User chooses a date
            //If it is valid show attendance for that day
            view.printDateQuestion();
            LocalDate date = convertDate(userInput());
            if (date != null) {
                attendances.entrySet().stream()
                        .filter(map -> map.getKey().isEqual(date))
                        .map(HashMap.Entry::getValue)
                        .flatMap(ArrayList::stream)
                        .forEach(System.out::println);
            } else {
                view.printInvalidInput();
            }
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