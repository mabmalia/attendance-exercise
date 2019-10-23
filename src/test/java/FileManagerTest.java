import org.junit.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import static org.junit.Assert.*;

public class FileManagerTest {
    @Test
    public void checkMemberListNotEmptyTrue() {
        FileManager fm = new FileManager("src/test/resources/");
        ArrayList<Member> listOfMembers = new ArrayList<>();
        listOfMembers.addAll(fm.readFileMembers());
        assertTrue(listOfMembers.size() >0);
    }

    @Test
    public void writeMemberFileNotEmpty(){
        FileManager fm = new FileManager("src/test/resources/");

        //Create a list with 2 members
        ArrayList<Member> listOfMembers = new ArrayList<>();
        listOfMembers.add(new Member("Name1", "id1"));
        listOfMembers.add(new Member("Name2", "id2"));

        //Write the file with the members
        fm.writeFileMembers(listOfMembers);

        //Check if file is empty
        int fileSize;
        try{
            fileSize = Files.readAllLines(Paths.get("src/test/resources/member-list.json")).size();
        }
        catch(IOException e){
            fileSize = -1;
        }

        assertTrue(fileSize > 0);
    }

    @Test
    public void checkAttendanceListNotEmpty(){
        FileManager fm = new FileManager("src/test/resources/");
        ArrayList<Attendance> listOfAttendance = new ArrayList<>();
        listOfAttendance.addAll(fm.readFileAttendance());
        assertTrue(listOfAttendance.size() > 0);
    }
}