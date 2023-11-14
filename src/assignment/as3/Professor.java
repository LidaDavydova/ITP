package assignment.as3;

import java.util.*;

public class Professor extends UniversityMember {
    private static final int MAX_LOAD = 2;
    private List<Course> assignedCourses;

    public Professor(String memberName) {
        super(memberName);
    }
    public boolean teach(Course course) {
        if (assignedCourses.size() < MAX_LOAD) {
            for (int idx=0; idx < assignedCourses.size(); idx++) {
                if (assignedCourses.get(idx) == course) {
                    return false;
                }
            }
            assignedCourses.add(course);
            return true;
        }
        return false;
    }
    public boolean exempt(Course course) {
        for (int idx=0; idx < assignedCourses.size(); idx++) {
            if (assignedCourses.get(idx) == course) {
                assignedCourses.remove(idx);
                return true;
            }
        }
        return false;
    }
    public void setAssignedCourses(List<Course> assignedCourses) {
        this.assignedCourses = assignedCourses;
    }
}
