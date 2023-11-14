package assignment.as3;

import java.util.*;

public class Course {
    private static final int CAPACITY = 3;
    private static int numberOfCourses = 0;
    private int courseId = 1;
    private String courseName = "";
    private List<Student> enrolledStudent = new ArrayList<Student>();
    private CourseLevel courseLevel;

    public Course(String courseName, CourseLevel courseLevel) {
        this.courseName = courseName;
        this.courseLevel = courseLevel;
        courseId++;
        numberOfCourses++;
    }

    public void setEnrolledStudent(Student student) {
        enrolledStudent.add(student);
    }

    public boolean isFull() {
        if (enrolledStudent.size() < CAPACITY) {
            return false;
        }
        return true;
    }
}
