package assignment.as3;

import java.util.Arrays;
import java.util.Scanner;
import java.util.ArrayList;
import java.util.List;

public final class UniversityCourseManagementSystem {
    private UniversityCourseManagementSystem() {
    }
    public static void main(String[] args) {
        fillInitialData();

        Scanner in = new Scanner(System.in);
        String cmd = in.nextLine();
        while (cmd != "") {
            if (cmd.equals("course")) {
                cmd = in.nextLine();
                if (cmd.indexOf(" ") == -1) {
                    final int i = cmd.indexOf("_");
                    cmd = cmd.toLowerCase();
                    if (cmd.charAt(0) != '_' && cmd.charAt(cmd.length()-1) != '_') {
                        if (isAlpha(cmd)) {
                            String crName = in.nextLine().toUpperCase();
                            if (crName.equals("BACHELOR")) {
                                Course cr = new Course(cmd, CourseLevel.BACHELOR);
                            } else if (crName.equals("MASTER")) {
                                Course cr = new Course(cmd, CourseLevel.MASTER);
                            } else { System.out.println("Wrong inputs"); break; };
                            
                            System.out.println("Added successfully");
                        } else { System.out.println("Wrong inputs"); }
                    } else { System.out.println("Wrong inputs"); }
                } else { System.out.println("Wrong inputs"); }
            }
            else if (cmd == "course"){
                //if (isAlpha(cmd))

                cmd = cmd.toLowerCase();
                if (cmd == "student") {

                }
            }
            cmd = in.nextLine();
        }
    }

    /**
     *
     * Initialization default Data
     */
    public static void fillInitialData() {
        List<String> list = new ArrayList<>();
        Course course1 = new Course("java_beginner", CourseLevel.BACHELOR);
        Course course2 = new Course("java_intermediate", CourseLevel.BACHELOR);
        Course course3 = new Course("python_basics", CourseLevel.BACHELOR);
        Course course4 = new Course("algorithms", CourseLevel.MASTER);
        Course course5 = new Course("advanced_programming", CourseLevel.MASTER);
        Course course6 = new Course("mathematical_analysis", CourseLevel.MASTER);
        Course course7 = new Course("computer_vision", CourseLevel.MASTER);

        Student st1 = new Student("Alice");
        st1.setEnrolledCourses(new ArrayList<>(Arrays.asList(course1, course2, course3)));
        Student st2 = new Student("Bob");
        st2.setEnrolledCourses(new ArrayList<>(Arrays.asList(course1, course4)));
        Student st3 = new Student("Alex");
        st3.setEnrolledCourses(new ArrayList<>(Arrays.asList(course5)));

        Professor pr1 = new Professor("Ali");
        pr1.setAssignedCourses(new ArrayList<>(Arrays.asList(course1, course2)));
        Professor pr2 = new Professor("Ahmed");
        pr2.setAssignedCourses(new ArrayList<>(Arrays.asList(course3, course5)));
        Professor pr3 = new Professor("Andrey");
        pr3.setAssignedCourses(new ArrayList<>(Arrays.asList(course6)));
    }
    public static boolean isAlpha(String name) {
        return name.matches("[a-zA-Z_]+");
    }
}


