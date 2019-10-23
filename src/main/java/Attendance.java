import java.time.LocalDate;

/**
 * This class represents an Attendance object.
 */
public class Attendance {
    private Member member;
    private LocalDate date;
    private boolean hasAttended;

    /**
     * Constructor of the Attendance class.
     * @param member represents a student
     * @param date represents the date of attendance
     * @param hasAttended boolean
     */
    public Attendance(Member member, LocalDate date, boolean hasAttended) {
        this.member = member;
        this.date = date;
        this.hasAttended = hasAttended;
    }

    /**
     * Get the member
     * @return String member
     */
    public Member getMember() {
        return member;
    }

    /**
     * Get the date of attendance
     * @return LocalDate date of attendance
     */
    public LocalDate getDate() {
        return date;
    }

    /**
     * Get attendance
     * @return boolean hasAttended
     */
    public boolean isHasAttended() {
        return hasAttended;
    }

    /**
     * Get attendance
     * @return string hasAttended
     */
    public String hasAttendedToString() {

        return (hasAttended = true) ? "Attended" : "Not Attended";
    }


    /**
     * Concatenate all the fields.
     * @return
     */
    @Override
    public String toString() {
        return "Attendance{" +
                "member=" + member.getMemberID() + ", " + member.getMemberName() +
                ", date=" + date +
                ", hasAttended=" + hasAttendedToString() +
                '}';
    }
}