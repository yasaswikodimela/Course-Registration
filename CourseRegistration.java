import java.util.Scanner;

class Course {
    private String name;
    private String instructor;

    // Constructor

    public Course(String name, String instructor) {
        this.name = name;
        this.instructor = instructor;
    }

    // Display course details

    public void display() {
        System.out.printf("Course: %-10s | Instructor: %s%n", name, instructor);
    }

    // equals() to avoid duplicate registration
    @Override
	//indicates that a method is intended to override a method in a super class

    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (!(obj instanceof Course)) return false;
        Course other = (Course) obj;
        return this.name.equals(other.name) && this.instructor.equals(other.instructor);
    }
}

class Student {
    private String name;
    private Course[] registeredCourses;
    private int courseCount;

    private static final int MAX_COURSES = 3;   // per student
    private static int totalRegistrations = 0;  // across all students

    // Constructor

    public Student(String name) {
        this.name = name;
        registeredCourses = new Course[MAX_COURSES];
        courseCount = 0;
    }

    // Method to register course

    public void registerCourse(Course course) {
        // check for duplicate

        for (int i = 0; i < courseCount; i++) {
            if (registeredCourses[i].equals(course)) {
                System.out.println("\n⚠ You are already registered for this course!");
                return;
            }
        }

        if (courseCount < MAX_COURSES) {
            registeredCourses[courseCount++] = course;
            totalRegistrations++;
            System.out.println("\n Course registered successfully!");
        } else {
            System.out.println("\n You have reached the maximum course limit.");
        }
    }

    // Method overloading (to register by index instead of object)

    public void registerCourse(Course[] allCourses, int index) {
        if (index >= 0 && index < allCourses.length) {
            registerCourse(allCourses[index]);
        } else {
            System.out.println("\nInvalid course index.");
        }
    }

    // Show student info

    public void showStudentInfo() {
        System.out.println("\n--- Student Info ---");
        System.out.println("Name: " + name);
        System.out.println("Registered Courses:");

        if (courseCount == 0) {
            System.out.println("None");
        } else {
            for (int i = 0; i < courseCount; i++) {
                registeredCourses[i].display();
            }
        }
        System.out.println("Total Registered: " + courseCount);
    }

    // Static method

    public static int getTotalRegistrations() {
        return totalRegistrations;
    }
}

public class CourseRegistration {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        System.out.print("Enter Student Name: ");
        String studentName = sc.nextLine();

        Student student = new Student(studentName);

        // Predefined courses
        Course[] courses = {
            new Course("Math", "Dr. Smith"),
            new Course("Science", "Dr. Lee"),
            new Course("History", "Dr. Adams")
        };

        int choice;
        do {
            System.out.println("\n\n===== COURSE MENU =====");
            System.out.println("1. View All Courses");
            System.out.println("2. Register for a Course");
            System.out.println("3. View My Registered Courses");
            System.out.println("4. View Total Registrations (All Students)");
            System.out.println("5. Exit");
            System.out.print("Enter your choice: ");
            choice = sc.nextInt();
            sc.nextLine(); // clear buffer

            switch (choice) {
                case 1:
                    System.out.println("\n--- Available Courses ---");
                    for (int i = 0; i < courses.length; i++) {
                        System.out.printf("%d. ", (i + 1));
                        courses[i].display();
                    }
                    break;

                case 2:
                    System.out.print("Enter course number (1-" + courses.length + "): ");
                    int courseNum = sc.nextInt();
                    sc.nextLine();
                    student.registerCourse(courses, courseNum - 1); // overloaded method
                    break;

                case 3:
                    student.showStudentInfo();
                    break;

                case 4:
                    System.out.println("\n Total course registrations (all students): " + Student.getTotalRegistrations());
                    break;

                case 5:
                    System.out.println("\n Exiting... Thank you!");
                    break;

                default:
                    System.out.println("\n Invalid option. Try again.");
            }
        } while (choice != 5);

        sc.close();
    }
}
