import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Reader;
import java.util.ArrayList;
import java.util.Iterator;
import attendance.*;

/**
 * A class that reads and writes JSON files.
 */
public class FileManager {
    private String jsonPath;
    private final String JSON_FILE_NAME_MEMBERS = "member-list.json";
    private final String JSON_FILE_NAME_ATTENDANCE = "attendance-list.json";

    /**
     * Constructor of the FileManager.
     * @param jsonPath the path where is located the json file.
     */
    public FileManager(String jsonPath){
        this.jsonPath = jsonPath;
    }

    /**
     * Reads the JSON members file and retrieves a list.
     * @return a list of Members.
     */
    public ArrayList<Member> readFileMembers(){
        ArrayList<Member> jsonMembers = new ArrayList<>();
        JSONParser parser = new JSONParser();

        try (Reader reader = new FileReader(jsonPath + JSON_FILE_NAME_MEMBERS)) {

            JSONObject jsonObject = (JSONObject) parser.parse(reader);

            // loop members array
            JSONArray members = (JSONArray) jsonObject.get("members");
            Iterator<String> iterator = members.iterator();
            while (iterator.hasNext()) {
                Object slide = iterator.next();
                JSONObject jsonObject2 = (JSONObject) slide;
                String name = (String) jsonObject2.get("name");
                String id = (String) jsonObject2.get("id");
                Member member = new Member(name, id);
                jsonMembers.add(member);
            }

        } catch (IOException e) {
            e.printStackTrace();
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return jsonMembers;
    }

    /**
     * Write the list of members in the JSON file.
     * @param members a list with members.
     */
    public void writeFileMembers(ArrayList<Member> members){
        //Main JSON object
        JSONObject object = new JSONObject();

        //Create a JSON array
        JSONArray array = new JSONArray();

        for(int index = 0; index < members.size(); index++)
        {
            //Second JSON object with an array
            JSONObject arrayElement = new JSONObject();
            arrayElement.put("name", members.get(index).getMemberName());
            arrayElement.put("id", members.get(index).getMemberName());

            //insert in the array
            array.add(arrayElement);
        }

        //put everything inside the object
        object.put("members", array);

        try (FileWriter file = new FileWriter(jsonPath + JSON_FILE_NAME_MEMBERS)) {
            file.write(object.toJSONString());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Reads the JSON attendance file and retrieves a list.
     * @return a list of Attendance.
     */
    public ArrayList<Attendance> readFileAttendance(){
        ArrayList<Attendance> jsonAttendance = new ArrayList<>();
        JSONParser parser = new JSONParser();

        try (Reader reader = new FileReader(jsonPath + JSON_FILE_NAME_ATTENDANCE)) {

            JSONObject jsonObject = (JSONObject) parser.parse(reader);

            // loop members array
            JSONArray attendances = (JSONArray) jsonObject.get("attendance");
            Iterator<String> iterator = attendances.iterator();
            while (iterator.hasNext()) {
                Object slide = iterator.next();
                JSONObject jsonObject2 = (JSONObject) slide;
                String name = (String) jsonObject2.get("name");
                String id = (String) jsonObject2.get("id");
                String date = (String) jsonObject2.get("date");
                boolean attended = (boolean) jsonObject2.get("attended");

                //Add fields to the list
                Attendance attendance = new Attendance(new Member(name, id), Controller.convertDate(date), attended);
                jsonAttendance.add(attendance);
            }

        } catch (IOException e) {
            e.printStackTrace();
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return jsonAttendance;
    }

    /**
     * Write the list of attendances in the JSON file.
     * @param attendance a list with members.
     */
    public void writeFileAttendance(ArrayList<Attendance> attendance){
        //Main JSON object
        JSONObject object = new JSONObject();

        //Create a JSON array
        JSONArray array = new JSONArray();

        for(int index = 0; index < attendance.size(); index++)
        {
            //Second JSON object with an array
            JSONObject arrayElement = new JSONObject();
            arrayElement.put("name", attendance.get(index).getMember().getMemberName());
            arrayElement.put("id", attendance.get(index).getMember().getMemberName());
            arrayElement.put("date", attendance.get(index).getDate().toString());
            arrayElement.put("attended", attendance.get(index).isHasAttended());

            //insert in the array
            array.add(arrayElement);
        }

        //put everything inside the object
        object.put("attendance", array);

        try (FileWriter file = new FileWriter(jsonPath + JSON_FILE_NAME_ATTENDANCE)) {
            file.write(object.toJSONString());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}