package attendance;

import org.junit.Test;

import static org.junit.Assert.*;

public class MemberTest {
    String name1 = "alfredo";
    String name2 = "jose";
    String id1 = "f32h";
    String id2 = "f4cx1";

    @Test
    public void getMemberNameTrue() {
        Member member = new Member(name1, id1);

        assertEquals(name1, member.getMemberName());
    }

    @Test
    public void getMemberNameFalse() {
        Member member = new Member(name1, id1);

        assertNotEquals(name2, member.getMemberName());
    }

    @Test
    public void setMemberNameTrue() {
        Member member = new Member(name1, id1);
        member.setMemberName(name2);

        assertEquals(name2, member.getMemberName());
    }

    @Test
    public void setMemberNameFalse() {
        Member member = new Member(name1, id1);
        member.setMemberName(name2);

        assertNotEquals(name1, member.getMemberName());
    }

    @Test
    public void getMemberIDTrue() {
        Member member = new Member(name1, id1);

        assertEquals(id1, member.getMemberID());
    }

    @Test
    public void getMemberIDFalse() {
        Member member = new Member(name1, id1);

        assertNotEquals(id2, member.getMemberID());
    }

    @Test
    public void setMemberIDTrue() {
        Member member = new Member(name1, id1);
        member.setMemberID(id2);

        assertEquals(id2, member.getMemberID());
    }

    @Test
    public void setMemberIDFalse() {
        Member member = new Member(name1, id1);
        member.setMemberID(id2);

        assertNotEquals(id1, member.getMemberID());
    }
}