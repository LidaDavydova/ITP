package assignment.as3;

import java.util.List;

public class Student extends UniversityMember implements Enrollable {
    private static final int MAX_ENROLLMENT = 3;
    private List<Course> enrolledCourses;

    public Student(String memberName) {
        super(memberName);
    }

    public void setEnrolledCourses(List<Course> enrolledCourses) {
        this.enrolledCourses = enrolledCourses;
        for (Course course : enrolledCourses) {
            course.setEnrolledStudent(Student.this);
        }
    }

    @Override
    public boolean drop(Course course) {
        for (Course enrolledCr : enrolledCourses) {
            if (enrolledCr.equals(course)) {
                enrolledCourses.remove(course);
                return true;
            }
        }
        return false;
    }
    @Override
    public boolean enroll(Course course) {
        if (enrolledCourses.size() < MAX_ENROLLMENT && !course.isFull()) {
            for (Course enrolledCr : enrolledCourses) {
                if (enrolledCr.equals(course)) {
                    return false;
                }
            }
            enrolledCourses.add(course);
            return true;
        }
        return false;
    }
}
