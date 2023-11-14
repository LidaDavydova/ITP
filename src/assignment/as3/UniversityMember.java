package assignment.as3;

public abstract class UniversityMember {
    public int numberOfMembers = 0;
    protected int memberId = 1;
    protected String memberName;

    public UniversityMember(int memberId, String memberName) {
        this.memberId = memberId;
        this.memberName = memberName;
        memberId++;
        numberOfMembers++;
    }
    public UniversityMember(String memberName) {
        this.memberName = memberName;
        memberId++;
        numberOfMembers++;
    }
}
