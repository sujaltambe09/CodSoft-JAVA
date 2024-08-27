import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

class Student implements Serializable {
    private String name;
    private String rollNumber;
    private String grade;

    public Student(String name, String rollNumber, String grade) {
        this.name = name;
        this.rollNumber = rollNumber;
        this.grade = grade;
    }

    public String getName() {
        return name;
    }

    public String getRollNumber() {
        return rollNumber;
    }

    public String getGrade() {
        return grade;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setGrade(String grade) {
        this.grade = grade;
    }

    @Override
    public String toString() {
        return "Student{" +
                "Name='" + name + '\'' +
                ", Roll Number='" + rollNumber + '\'' +
                ", Grade='" + grade + '\'' +
                '}';
    }
}

class StudentManagementSystem {
    private List<Student> students = new ArrayList<>();
    private static final String FILE_NAME = "students.dat";

    public StudentManagementSystem() {
        loadStudents();
    }

    public void addStudent(Student student) {
        students.add(student);
        saveStudents();
        System.out.println("Student added successfully.");
    }

    public void removeStudent(String rollNumber) {
        students.removeIf(student -> student.getRollNumber().equals(rollNumber));
        saveStudents();
        System.out.println("Student removed successfully.");
    }

    public Student searchStudent(String rollNumber) {
        for (Student student : students) {
            if (student.getRollNumber().equals(rollNumber)) {
                return student;
            }
        }
        return null;
    }

    public void displayAllStudents() {
        if (students.isEmpty()) {
            System.out.println("No students to display.");
        } else {
            for (Student student : students) {
                System.out.println(student);
            }
        }
    }

    private void saveStudents() {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(FILE_NAME))) {
            oos.writeObject(students);
        } catch (IOException e) {
            System.err.println("Error saving students: " + e.getMessage());
        }
    }

    private void loadStudents() {
        File file = new File(FILE_NAME);
        if (file.exists()) {
            try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(FILE_NAME))) {
                students = (List<Student>) ois.readObject();
            } catch (IOException | ClassNotFoundException e) {
                System.err.println("Error loading students: " + e.getMessage());
            }
        }
    }
}

public class StudentManagement {
    public static void main(String[] args) {
        StudentManagementSystem sms = new StudentManagementSystem();
        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.println("\n--- Student Management System ---");
            System.out.println("1. Add Student");
            System.out.println("2. Remove Student");
            System.out.println("3. Search Student");
            System.out.println("4. Display All Students");
            System.out.println("5. Exit");
            System.out.print("Choose an option: ");
            int choice = scanner.nextInt();
            scanner.nextLine();  // Consume newline

            switch (choice) {
                case 1:
                    addStudent(sms, scanner);
                    break;
                case 2:
                    removeStudent(sms, scanner);
                    break;
                case 3:
                    searchStudent(sms, scanner);
                    break;
                case 4:
                    sms.displayAllStudents();
                    break;
                case 5:
                    System.out.println("Exiting the application.");
                    return;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }
    }

    private static void addStudent(StudentManagementSystem sms, Scanner scanner) {
        System.out.print("Enter student's name: ");
        String name = scanner.nextLine();
        System.out.print("Enter student's roll number: ");
        String rollNumber = scanner.nextLine();
        System.out.print("Enter student's grade: ");
        String grade = scanner.nextLine();

        // Input validation
        if (name.isEmpty() || rollNumber.isEmpty() || grade.isEmpty()) {
            System.out.println("Error: All fields are required.");
            return;
        }

        Student student = new Student(name, rollNumber, grade);
        sms.addStudent(student);
    }

    private static void removeStudent(StudentManagementSystem sms, Scanner scanner) {
        System.out.print("Enter student's roll number to remove: ");
        String rollNumber = scanner.nextLine();
        sms.removeStudent(rollNumber);
    }

    private static void searchStudent(StudentManagementSystem sms, Scanner scanner) {
        System.out.print("Enter student's roll number to search: ");
        String rollNumber = scanner.nextLine();
        Student student = sms.searchStudent(rollNumber);
        if (student != null) {
            System.out.println("Student found: " + student);
        } else {
            System.out.println("Student not found.");
        }
    }
}

