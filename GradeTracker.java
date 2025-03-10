import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.Scanner;

public class StudentGradeTracker {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        // Get student details
        System.out.print("Enter Student Name: ");
        String studentName = scanner.nextLine();

        int rollNumber = getValidRollNumber(scanner);

        // Lists for subjects, marks, and total marks
        ArrayList<String> subjects = new ArrayList<>();
        ArrayList<Integer> marksObtained = new ArrayList<>();
        ArrayList<Integer> totalMarks = new ArrayList<>();

        String continueInput = "yes";

        while (continueInput.equalsIgnoreCase("yes")) {
            System.out.print("Enter the subject name: ");
            String subject = scanner.nextLine();
            subjects.add(subject);

            int obtainedMarks = getValidMarks(scanner, "Enter the marks obtained in " + subject + ": ");
            marksObtained.add(obtainedMarks);

            int totalMark = getValidMarks(scanner, "Enter the total marks for " + subject + ": ");
            totalMarks.add(totalMark);

            System.out.print("Do you want to enter another subject? (yes/no): ");
            continueInput = scanner.nextLine();
        }

        scanner.close();

        // Display student summary and results
        if (!marksObtained.isEmpty()) {
            double averageMarks = calculateAverage(marksObtained);
            int highestMarks = findHighestGrade(marksObtained);
            int lowestMarks = findLowestGrade(marksObtained);
            double averagePercentage = calculateAveragePercentage(marksObtained, totalMarks);
            String finalGrade = calculateFinalGrade(averagePercentage);

            System.out.println("\n--- Student Grade Report ---");
            System.out.println("Student Name : " + studentName);
            System.out.println("Roll Number  : " + rollNumber);
            System.out.println("\nGrade Statistics:");
            System.out.println("Average Marks    : " + averageMarks);
            System.out.println("Highest Marks    : " + highestMarks);
            System.out.println("Lowest Marks     : " + lowestMarks);
            System.out.println("Average Percentage : " + averagePercentage + "%");
            System.out.println("Final Grade      : " + finalGrade);

            // Subject-wise marks and grades
            System.out.println("\nSubject-wise Marks & Grades:");
            for (int i = 0; i < subjects.size(); i++) {
                String grade = calculateGrade(marksObtained.get(i), totalMarks.get(i));
                System.out.println(subjects.get(i) + ": " + marksObtained.get(i) + "/" + totalMarks.get(i) + " - Grade: " + grade);
            }
        } else {
            System.out.println("No subjects or marks were entered.");
        }
    }

    // Method to validate roll number input
    private static int getValidRollNumber(Scanner scanner) {
        int rollNumber;
        while (true) {
            try {
                System.out.print("Enter Roll Number: ");
                rollNumber = scanner.nextInt();
                scanner.nextLine(); // Consume newline
                if (rollNumber > 0) return rollNumber;
                else System.out.println("Roll number must be positive. Try again.");
            } catch (InputMismatchException e) {
                System.out.println("Invalid input! Please enter a valid roll number.");
                scanner.next(); // Clear invalid input
            }
        }
    }

    // Method to validate marks input
    private static int getValidMarks(Scanner scanner, String message) {
        int marks;
        while (true) {
            try {
                System.out.print(message);
                marks = scanner.nextInt();
                if (marks >= 0) {
                    scanner.nextLine(); // Consume newline
                    return marks;
                } else {
                    System.out.println("Marks cannot be negative. Try again.");
                }
            } catch (InputMismatchException e) {
                System.out.println("Invalid input! Please enter a valid number.");
                scanner.next(); // Clear invalid input
            }
        }
    }

    // Method to calculate average marks
    private static double calculateAverage(ArrayList<Integer> marksObtained) {
        int sum = marksObtained.stream().mapToInt(Integer::intValue).sum();
        return (double) sum / marksObtained.size();
    }

    // Method to find highest mark
    private static int findHighestGrade(ArrayList<Integer> marksObtained) {
        return marksObtained.stream().max(Integer::compareTo).orElse(0);
    }

    // Method to find lowest mark
    private static int findLowestGrade(ArrayList<Integer> marksObtained) {
        return marksObtained.stream().min(Integer::compareTo).orElse(0);
    }

    // Method to calculate average percentage
    private static double calculateAveragePercentage(ArrayList<Integer> marksObtained, ArrayList<Integer> totalMarks) {
        double totalObtained = marksObtained.stream().mapToInt(Integer::intValue).sum();
        double totalOutOf = totalMarks.stream().mapToInt(Integer::intValue).sum();
        return (totalObtained / totalOutOf) * 100;
    }

    // Method to determine grade per subject
    private static String calculateGrade(int obtained, int total) {
        double percentage = ((double) obtained / total) * 100;
        if (percentage >= 90) return "A+";
        else if (percentage >= 80) return "A";
        else if (percentage >= 70) return "B";
        else if (percentage >= 60) return "C";
        else if (percentage >= 50) return "D";
        else return "F";
    }

    // Method to determine final grade based on average percentage
    private static String calculateFinalGrade(double avgPercentage) {
        if (avgPercentage >= 90) return "A+";
        else if (avgPercentage >= 80) return "A";
        else if (avgPercentage >= 70) return "B";
        else if (avgPercentage >= 60) return "C";
        else if (avgPercentage >= 50) return "D";
        else return "F (Fail)";
    }
}
