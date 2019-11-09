package attendance;

import org.junit.Test;

import static org.junit.Assert.*;

public class AttendanceTest {
    Member member = new Member("alfredo", "f3c4x");

    @Test
    public void isHasAttendedTrue() {
        Attendance attendance = new Attendance(member, true);

        assertEquals(true, attendance.isHasAttended());
    }

    @Test
    public void isHasAttendedFalse() {
        Attendance attendance = new Attendance(member, true);

        assertNotEquals(false, attendance.isHasAttended());
    }

    @Test
    public void hasAttendedToStringTrue() {
        Attendance attendance = new Attendance(member, true);

        assertEquals("Attended", attendance.hasAttendedToString());
    }

    @Test
    public void hasNotAttendedToStringTrue() {
        Attendance attendance = new Attendance(member, false);

        assertEquals("Not Attended", attendance.hasAttendedToString());
    }

    @Test
    public void hasAttendedToStringFalse() {
        Attendance attendance = new Attendance(member, true);

        assertNotEquals("Not Attended", attendance.hasAttendedToString());
    }
}