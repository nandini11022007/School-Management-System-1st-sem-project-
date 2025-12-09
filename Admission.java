package School;

import java.sql.*;
import java.util.*;



public class Admission {
    Scanner scanner = new Scanner(System.in);
    StringQueue questions =new StringQueue(5);
    StringQueue option =new StringQueue(25);
    ArrayList<String> ans= new ArrayList<>(5);
    String db_url="jdbc:mysql://localhost:3306/school_20";
    String db_user="root";
    String db_password="";
    Connection connect= DriverManager.getConnection(db_url,db_user,db_password);
    Statement state=connect.createStatement();

    public Admission() throws Exception {
    }


    void takeAdmission() throws Exception {
        System.out.print("Enter Name: ");
        String name = scanner.nextLine();

        System.out.print("Enter Age: ");
        int age = Integer.parseInt(scanner.nextLine());

        System.out.print("Enter Grade: ");
        String grade = scanner.nextLine();

        questions.enqueue("1.What is H₂O commonly known as ? ");
        questions.enqueue("2.Which is the smallest prime number ? ");
        questions.enqueue("3.Which is the Area of Triangle ? ");
        questions.enqueue("4.Which organelle is known as the “Powerhouse of the Cell ? ");
        questions.enqueue("5.Which Mughal emperor built the Red Fort in Delhi ? ");

        option.enqueue("A.Oxygen");
        option.enqueue("B.Salt");
        option.enqueue("C.Nitrogen");
        option.enqueue("D.Water ");

        option.display();

        option.enqueue("A.1");
        option.enqueue("B.2");
        option.enqueue("C.3");
        option.enqueue("D.4");

        option.enqueue("A. 3.14 x R x R  ");
        option.enqueue("B. A x A ");
        option.enqueue("C.1/2 x BASE x HEIGHT");
        option.enqueue("D.2 x BASE x HEIGHT");

        option.enqueue("A.Nucleus");
        option.enqueue("B.Ribosome");
        option.enqueue("C.Mitochondria ");
        option.enqueue("D.Golgi body");

        option.enqueue("A.Isaac Newton");
        option.enqueue("B.Akbar");
        option.enqueue("C.Babur");
        option.enqueue("D.Shah Jahan ");

        ans.add("D");
        ans.add("B");
        ans.add("C");
        ans.add("C");
        ans.add("D");

        System.out.println("\n--- Admission Test ---");
        int score = 0;
        System.out.println("Student Name :"+name);
        System.out.println("Student Age :"+age);
        System.out.println("Student Grade :"+grade);
        System.out.println("--- 5 MCQs Contains 1 Mark each ---");
        System.out.println("Choose option Only !");
        for (int i = 0; i < 5; i++) {
            System.out.println(questions.dequeue());
            for (int j = 1; j < 5 ; j++) {
                System.out.println(option.dequeue());
            }
            System.out.print("Your ans:");
            String answer=scanner.next().toUpperCase();
            if (ans.get(i).equals(answer)){
                score++;
            }
        }
        System.out.println("Test Completed. You scored " + score + "/" + 10);
        processAdmission(name ,score);
    }

    private void processAdmission(String name ,int score ) throws Exception {
            if (score >= 3) {
                System.out.println("Admission Granted to " + name);
                System.out.println("Please fill this Form");
                add_StudentDetails();

            }
            else {
                System.out.println("Admission Denied to " + name + " !!! Score too low !!!");
            }
        }

    void add_StudentDetails () throws Exception{
        System.out.println("Enter details for student  : ");

        // Collecting student details
        System.out.print("First Name : ");
        String firstname = scanner.next();

        System.out.print("Second Name : ");
        String secondname = scanner.next();

        String Student_name = (firstname.concat(" " + secondname));

        // Collecting and validating Date of Birth
        String dob;
        System.out.println("Enter DOB: ");
        do {
            System.out.print("Date: ");
            int DD = scanner.nextInt();
            System.out.print("Month: ");
            int MM = scanner.nextInt();
            System.out.print("Year: ");
            int YYYY = scanner.nextInt();

            if (((MM == 1 || MM == 3 || MM == 5 || MM == 7 || MM == 8 || MM == 10 || MM == 12) && (DD < 32))
                    || ((MM == 4 || MM == 6 || MM == 9 || MM == 11) && (DD < 31))
                    || (MM == 2 && DD < 29 && YYYY % 4 != 0)
                    || (MM == 2 && DD < 28 && YYYY % 4 == 0)) {
                dob = DD + "-" + MM + "-" + YYYY;
                break;
            } else {
                System.out.println("Please Enter a valid DOB : ");
            }
        } while (true);

        // Generating email and collecting other details
        String gmail;
        do {
            System.out.print("Gmail : ");
            gmail = scanner.next();
            scanner.nextLine();
            if (gmail.endsWith("@gmail.com")) {
                break;
            } else {
                System.out.println("Please Enter a valid Gmail (ends with @gmail.com): ");
            }
        } while (true);

        String mobile;
        do {
            System.out.print("Mobile number : ");
            mobile = scanner.next();
            scanner.nextLine();
            if (mobile.length() == 10) {
                break;
            } else {
                System.out.println("Please Enter a valid Mobile number (Contains 10 digit) : ");
            }
        } while (true);

        System.out.print("Standard : ");
        int Standard = scanner.nextInt();

        System.out.print("Roll Number : ");
        int rollNumber = scanner.nextInt();

        System.out.print("Select Gender : ");
        System.out.print("1.Female : ");
        System.out.print("2.Male : ");
        String gender=null;
        int Gen_no = scanner.nextInt();
        if (Gen_no==1){ gender="Female";}
        if (Gen_no==2){ gender="Male";}
        System.out.println();

        String insertQuery = "INSERT INTO Student (Name, Roll_no, DOB, Standard, Gender, Mobile, Gmail) VALUES (?, ?, ?, ?, ?, ?, ?)";
        PreparedStatement pst = connect.prepareStatement(insertQuery);
        pst.setString(1, Student_name);
        pst.setInt(2, rollNumber);
        pst.setString(3, dob);
        pst.setInt(4, Standard);
        pst.setString(5, gender);
        pst.setString(6, mobile);
        pst.setString(7, gmail);

        pst.executeUpdate();
        System.out.println("Student added successfully.");

        String query = "SELECT * FROM Student";
        ResultSet rs = state.executeQuery(query);

        int maxStudent = 0;
        while (rs.next())  {
            maxStudent++;
        }
        String feesQuery = "INSERT INTO Fees (Student_id, Remaining_fees, Paid, Total) VALUES("+(maxStudent+1)+", 50000.0 , 0.0, 50000.0)";
        state.execute(feesQuery);
    }

}
