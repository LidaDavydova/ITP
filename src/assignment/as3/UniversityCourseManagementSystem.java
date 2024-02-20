package assignment.as3;

import java.util.Arrays;
import java.util.Scanner;
import java.util.ArrayList;
import java.util.List;

/**
 * This is a UniversityCourseManagementSystem final class that has main, fillInitialData,
 * mainTeach, mainEnroll,10
1 3 2 5 4 6 7 9 8 10 mainDrop, rightCommand, isAlpha methods.
 * main works with scanned lines, fillInitialData fills initial data
 *  @author LidaDavydova
 *  @version 1.0
 */
public class UniversityCourseManagementSystem {
    /**
     * This private constructor did not allow to create instance of UniversityCourseManagementSystem
     */
    private static List<Course> allCourses = new ArrayList<>();
    private static List<Student> allStudents = new ArrayList<>();
    private static List<Professor> allProfessors = new ArrayList<>();
    private static List<String> commands = new ArrayList<>(Arrays.asList("course", "student",
            "professor", "enroll", "drop", "exempt", "teach"));
    /**
     * This method calls fillInitialData() and use while construction that takes command and
     * for each command generates own algorithm.
     * @param args array of String
     */
    public static void main(String[] args) {
        fillInitialData();

        Scanner in = new Scanner(System.in);
        String cmd = "";
        while (in.hasNext()) {
            cmd = in.next();
            if (cmd.equals("course")) {
                int f = 0;
                if (!in.hasNext()) {
                    System.out.println("Wrong inputs");
                    System.exit(0);
                }
                cmd = in.next(); //courseName
                if (cmd.indexOf(" ") == -1) {
                    cmd = cmd.toLowerCase();
                    if (!rightCommand(cmd)) {
                        System.exit(0);
                    }
                    if (cmd.charAt(0) != '_' && cmd.charAt(cmd.length() - 1) != '_') {
                        if (isAlphaPlus(cmd)) {
                            for (Course i : allCourses) {
                                if (i.getName().equals(cmd)) {
                                    System.out.println("Course exists");
                                    System.exit(0);
                                }
                            }
                            if (!in.hasNext()) {
                                System.out.println("Wrong inputs");
                                System.exit(0);
                            }
                            String crLevel = in.next().toUpperCase();
                            if (crLevel.equals("BACHELOR") || crLevel.equals("MASTER")) {
                                if (crLevel.equals("BACHELOR")) {
                                    Course cr = new Course(cmd, CourseLevel.BACHELOR);
                                    allCourses.add(cr);
                                } else {
                                    Course cr = new Course(cmd, CourseLevel.MASTER);
                                    allCourses.add(cr);
                                }
                                f = 1;
                                System.out.println("Added successfully");
                            }
                        }
                    }
                }
                if (f == 0) {
                    System.out.println("Wrong inputs");
                    System.exit(0);
                }
            } else if (cmd.equals("student")) {
                if (!in.hasNext()) {
                    System.out.println("Wrong inputs");
                    System.exit(0);
                }
                cmd = in.next(); //memberName
                cmd = cmd.toLowerCase();
                if (!rightCommand(cmd)) {
                    System.exit(0);
                }
                if (isAlpha(cmd)) {
                    allStudents.add(new Student(cmd));
                    System.out.println("Added successfully");
                } else {
                    System.out.println("Wrong inputs");
                    System.exit(0);
                }
            } else if (cmd.equals("professor")) {
                if (!in.hasNext()) {
                    System.out.println("Wrong inputs");
                    System.exit(0);
                }
                cmd = in.next(); //memberName
                cmd = cmd.toLowerCase();
                if (!rightCommand(cmd)) {
                    System.exit(0);
                }
                if (isAlpha(cmd)) {
                    allProfessors.add(new Professor(cmd));
                    System.out.println("Added successfully");
                } else {
                    System.out.println("Wrong inputs");
                    System.exit(0);
                }
            } else if (cmd.equals("enroll")) {
                mainEnroll(in);
            } else if (cmd.equals("drop")) {
                mainDrop(in);
            } else if (cmd.equals("exempt")) {
                int f = 0;
                if (!in.hasNext()) {
                    System.out.println("Wrong inputs");
                    System.exit(0);
                }
                cmd = in.next(); // memberId
                try {
                    int memberId = Integer.parseInt(cmd);
                    if (!in.hasNext()) {
                        System.out.println("Wrong inputs");
                        System.exit(0);
                    }
                    cmd = in.next(); // courseId
                    int courseId = Integer.parseInt(cmd);
                    for (Professor prfs : allProfessors) {
                        if (prfs.getMemberId() == memberId) {
                            for (Course crs : allCourses) {
                                if (crs.getCourseId() == courseId) {
                                    if (prfs.exempt(crs)) {
                                        System.out.println("Professor is exempted");
                                        f = 1;
                                        break;
                                    } else {
                                        System.exit(0);
                                    }
                                }
                            }
                        }
                    }
                    if (f == 0) {
                        System.out.println("Wrong inputs");
                        System.exit(0);
                    }
                } catch (NumberFormatException ex) {
                    System.out.println("Wrong inputs");
                    System.exit(0);
                }
            } else if (cmd.equals("teach")) {
                mainTeach(in);
            } else {

                System.out.println("Wrong inputs");
                System.exit(0);
            }
        }
    }

    /**
     * Method for professor is teaching course.
     * @param in Scanner
     */
    public static void mainTeach(Scanner in) {
        int f = 0;
        if (!in.hasNext()) {
            System.out.println("Wrong inputs");
            System.exit(0);
        }
        String cmd = in.next(); // memberId
        try {
            int memberId = Integer.parseInt(cmd);
            if (!in.hasNext()) {
                System.out.println("Wrong inputs");
                System.exit(0);
            }
            cmd = in.next(); // courseId
            int courseId = Integer.parseInt(cmd);
            for (Professor prfs : allProfessors) {
                if (prfs.getMemberId() == memberId) {
                    for (Course crs : allCourses) {
                        if (crs.getCourseId() == courseId) {
                            if (prfs.teach(crs)) {
                                System.out.println("Professor is successfully assigned to teach this course");
                                f = 1;
                                break;
                            } else {
                                System.exit(0);
                            }
                        }
                    }
                }
            }
            if (f == 0) {
                System.out.println("Wrong inputs");
                System.exit(0);
            }
        } catch (NumberFormatException ex) {
            System.out.println("Wrong inputs");
            System.exit(0);
        }
    }

    /**
     * Method for student is enrolling course.
     * @param in Scanner
     */
    public static void mainEnroll(Scanner in) {
        int f = 0;
        if (!in.hasNext()) {
            System.out.println("Wrong inputs");
            System.exit(0);
        }
        String cmd = in.next(); // memberId
        try {
            int memberId = Integer.parseInt(cmd);
            if (!in.hasNext()) {
                System.out.println("Wrong inputs");
                System.exit(0);
            }
            cmd = in.next(); // courseId
            int courseId = Integer.parseInt(cmd);
            for (Student stdn : allStudents) {
                if (stdn.getMemberId() == memberId) {
                    for (Course crs : allCourses) {
                        if (crs.getCourseId() == courseId) {
                            if (stdn.enroll(crs)) {
                                System.out.println("Enrolled successfully");
                                f = 1;
                                break;
                            } else {
                                System.exit(0);
                            }
                        }
                    }
                }
            }
            if (f == 0) {
                System.out.println("Wrong inputs");
                System.exit(0);
            }
        } catch (NumberFormatException ex) {
            System.out.println("Wrong inputs");
            System.exit(0);
        }
    }
    /**
     * Method for student drop from course.
     * @param in Scanner
     */
    public static void mainDrop(Scanner in) {
        int f = 0;
        if (!in.hasNext()) {
            System.out.println("Wrong inputs");
            System.exit(0);
        }
        String cmd = in.next(); // memberId
        try {
            int memberId = Integer.parseInt(cmd);
            if (!in.hasNext()) {
                System.out.println("Wrong inputs");
                System.exit(0);
            }
            cmd = in.next(); // courseId
            int courseId = Integer.parseInt(cmd);
            for (Student stdn : allStudents) {
                if (stdn.getMemberId() == memberId) {
                    for (Course crs : allCourses) {
                        if (crs.getCourseId() == courseId) {
                            if (stdn.drop(crs)) {
                                System.out.println("Dropped successfully");
                                f = 1;
                                break;
                            } else {
                                System.exit(0);
                            }
                        }
                    }
                }
            }
            if (f == 0) {
                System.out.println("Wrong inputs");
                System.exit(0);
            }
        } catch (NumberFormatException ex) {
            System.out.println("Wrong inputs");
            System.exit(0);
        }
    }
    /**
     * Initialization default Data, creates instances of Course, Professor, Student.
     */
    public static void fillInitialData() {
        Course course1 = new Course("java_beginner", CourseLevel.BACHELOR);
        Course course2 = new Course("java_intermediate", CourseLevel.BACHELOR);
        Course course3 = new Course("python_basics", CourseLevel.BACHELOR);
        Course course4 = new Course("algorithms", CourseLevel.MASTER);
        Course course5 = new Course("advanced_programming", CourseLevel.MASTER);
        Course course6 = new Course("mathematical_analysis", CourseLevel.MASTER);
        Course course7 = new Course("computer_vision", CourseLevel.MASTER);
        allCourses.add(course1);
        allCourses.add(course2);
        allCourses.add(course3);
        allCourses.add(course4);
        allCourses.add(course5);
        allCourses.add(course6);
        allCourses.add(course7);

        Student st1 = new Student("Alice");
        st1.setEnrolledCourses(new ArrayList<>(Arrays.asList(course1, course2, course3)));
        Student st2 = new Student("Bob");
        st2.setEnrolledCourses(new ArrayList<>(Arrays.asList(course1, course4)));
        Student st3 = new Student("Alex");
        st3.setEnrolledCourses(new ArrayList<>(Arrays.asList(course5)));
        allStudents.add(st1);
        allStudents.add(st2);
        allStudents.add(st3);

        Professor pr1 = new Professor("Ali");
        pr1.setAssignedCourses(new ArrayList<>(Arrays.asList(course1, course2)));
        Professor pr2 = new Professor("Ahmed");
        pr2.setAssignedCourses(new ArrayList<>(Arrays.asList(course3, course5)));
        Professor pr3 = new Professor("Andrey");
        pr3.setAssignedCourses(new ArrayList<>(Arrays.asList(course6)));
        allProfessors.add(pr1);
        allProfessors.add(pr2);
        allProfessors.add(pr3);
    }

    /**
     * This method look if scanned line is in the list of all commands.
     * @param cmd scanned line
     * @return false if it is a command, other time true
     */
    public static Boolean rightCommand(String cmd) {
        for (String cmdName : commands) {
            if (cmd.equals(cmdName)) {
                System.out.println("Wrong inputs");
                return false;
            }
        }
        return true;
    }
    /**
     * Checking string on only english letters.
     * @param name name of Course, Professor or Student
     * @return true if name matches only english letters, else false
     */
    public static boolean isAlpha(String name) {
        //return name.matches("[a-zA-Z]");
        for (int i = 0; i < name.length(); i++) {
            if (!(name.charAt(i) >= 'a' && name.charAt(i) <= 'z' || name.charAt(i) >= 'A' && name.charAt(i) <= 'Z')) {
                return false;
            }
        }
        return true;
    }
    /**
     * Checking string on only english letters plus _ .
     * @param name name of Course, Professor or Student
     * @return true if name matches only english letters and _ , else false
     */
    public static boolean isAlphaPlus(String name) {
        for (int i = 0; i < name.length(); i++) {
            if ((name.charAt(i) != '_') && !((name.charAt(i) >= 'a' && name.charAt(i) <= 'z')
                    || (name.charAt(i) >= 'A' && name.charAt(i) <= 'Z'))) {
                return false;
            }
        }
        return true;
    }
}

/**
 * This is a Course class that has setter for enrolled students, getter for name of course and id of course.
 * This class has method that look if course is full of students.
 *  @author LidaDavydova
 *  @version 1.0
 */
class Course {
    private static final int CAPACITY = 3;
    private static int numberOfCourses = 0;
    private int courseId = 0;
    private String courseName = "";
    private List<Student> enrolledStudents = new ArrayList<Student>();
    private CourseLevel courseLevel;

    /**
     * Course constructor.
     * @param courseName course name
     * @param courseLevel course level
     */
    public Course(String courseName, CourseLevel courseLevel) {
        numberOfCourses++;
        this.courseName = courseName;
        this.courseLevel = courseLevel;
        this.courseId = numberOfCourses;
    }
    /**
     * Add student in the list as enrolled.
     * @param student object of Student
     */
    public void setEnrolledStudent(Student student) {
        enrolledStudents.add(student);
    }
    /**
     * Look if course is full of enrolled students.
     * @return true or false
     */
    public boolean isFull() {
        if (enrolledStudents.size() < CAPACITY) {
            return false;
        }
        return true;
    }

    /**
     * Get course name.
     * @return courseName
     */
    public String getName() {
        return courseName;
    }

    /**
     * Get course id.
     * @return courseId
     */
    public int getCourseId() {
        return courseId;
    }
}

/**
 * This is a Student class that extends from abstract UniversityMember class and implements from Enrollable interface.
 * This class has drop, enroll methods, setter for enrolled courses.
 *  @author LidaDavydova
 *  @version 1.0
 */
class Student extends UniversityMember implements Enrollable {
    private static final int MAX_ENROLLMENT = 3;
    private List<Course> enrolledCourses = new ArrayList<Course>();
    /**
     * Constructor for Student.
     * @param memberName member name
     */
    public Student(String memberName) {
        super(0, memberName);
    }
    /**
     * This method sets list of courses for student in list of enrolled courses for student.
     * @param enrolledCourses list of enrolled courses
     */
    public void setEnrolledCourses(List<Course> enrolledCourses) {
        this.enrolledCourses = enrolledCourses;
        for (Course course : enrolledCourses) {
            course.setEnrolledStudent(Student.this);
        }
    }

    /**
     * This method drops student from course.
     * @param course object of Course
     * @return true if successfully or false
     */
    @Override
    public boolean drop(Course course) {
        for (Course enrolledCr : enrolledCourses) {
            if (enrolledCr.equals(course)) {
                enrolledCourses.remove(course);
                return true;
            }
        }
        System.out.println("Student is not enrolled in this course");
        return false;
    }

    /**
     * This method enrolls student to course.
     * @param course object of Course
     * @return true if successfully or false
     */
    @Override
    public boolean enroll(Course course) {
        for (Course enrolledCr : enrolledCourses) {
            if (enrolledCr.equals(course)) {
                System.out.println("Student is already enrolled in this course");
                return false;
            }
        }
        if (enrolledCourses.size() < MAX_ENROLLMENT && !course.isFull()) {
            enrolledCourses.add(course);
            return true;
        }

        if (enrolledCourses.size() >= MAX_ENROLLMENT) {
            System.out.println("Maximum enrollment is reached for the student");
            return false;
        }
        if (course.isFull()) {
            System.out.println("Course is full");
        }
        return false;
    }
}

/**
 * This is a Professor class that extends from abstract UniversityMember class.
 * This class has teach, exempt methods setter for assigned courses.
 *  @author LidaDavydova
 *  @version 1.0
 */
class Professor extends UniversityMember {
    private static final int MAX_LOAD = 2;
    private List<Course> assignedCourses = new ArrayList<>();
    /**
     * Constructor for Professor.
     * @param memberName member name
     */
    public Professor(String memberName) {
        super(0, memberName);
    }

    /**
     * Add processor to teach a course.
     * @param course object of Course
     * @return true if successfully, false if not
     */
    public boolean teach(Course course) {
        if (assignedCourses.size() < MAX_LOAD) {
            for (int idx = 0; idx < assignedCourses.size(); idx++) {
                if (assignedCourses.get(idx) == course) {
                    System.out.println("Professor is already teaching this course");
                    return false;
                }
            }
            assignedCourses.add(course);
            return true;
        }
        System.out.println("Professor's load is complete");
        return false;
    }

    /**
     * Add processor to exempt from course.
     * @param course object of Course
     * @return true if successfully, false if not.
     */
    public boolean exempt(Course course) {
        for (int idx = 0; idx < assignedCourses.size(); idx++) {
            if (assignedCourses.get(idx) == course) {
                assignedCourses.remove(idx);
                return true;
            }
        }
        System.out.println("Professor is not teaching this course");
        return false;
    }

    /**
     * Sets courses that are assigned for professor.
     * @param assignedCourses list of courses that has assigned
     */
    public void setAssignedCourses(List<Course> assignedCourses) {
        this.assignedCourses = assignedCourses;
    }
}

/**
 * This is abstract class that has member id, member name and number of members.
 * Member id generates automatically from number of members.
 * Member can be professor or student.
 * @author LidaDavydova
 * @version 1.0
 */
abstract class UniversityMember {
    private static int numberOfMembers = 0;
    private int memberId = 0;
    private String memberName;

    /**
     * Constructor for UniversityMember that as well increments a numberOfMembers.
     * @param memberId member id
     * @param memberName member name
     */
    public UniversityMember(int memberId, String memberName) {
        numberOfMembers++;
        this.memberId = numberOfMembers;
        this.memberName = memberName;
    }

    /**
     * This method gets memberId.
     * @return memberId
     */
    public int getMemberId() {
        return this.memberId;
    }
}

/**
 * This enum that describes types of course level.
 * @author LidaDavydova
 * @version 1.0
 */
enum CourseLevel {
    BACHELOR, MASTER;
}

/**
 * This interface that has two methods.
 * @author LidaDavydova
 * @version 1.0
 */
interface Enrollable {
    /**
     * This method drops someone from course.
     * @param course object of course
     * @return true or false
     */
    boolean drop(Course course);

    /**
     * This method enroll someone to course.
     * @param course object of course
     * @return true or false
     */
    boolean enroll(Course course);
}
