package School;

import java.sql.*;
import java.util.Random;
import java.util.Scanner;

public class Admin {
    Scanner scanner = new Scanner(System.in);
    Boolean flag = true;

    Admission add_object = new Admission();
    StringQueue Option =new StringQueue(12);

    String db_url = "jdbc:mysql://localhost:3306/school_20";
    String db_user = "root";
    String db_password = "";
    Connection connect = DriverManager.getConnection(db_url, db_user, db_password);
    Statement state = connect.createStatement();

    public Admin() throws Exception {
        Option.enqueue("\n******************************");
        Option.enqueue("🔐 Welcome Admin");
        Option.enqueue("  1. Add Teacher");
        Option.enqueue("  2. View Teacher Details");
        Option.enqueue("  3. Add Student");
        Option.enqueue("  4. View Student Details");
        Option.enqueue("  5. View Class Marksheet");
        Option.enqueue("  6. View Parent Details");
        Option.enqueue("  7. View Fees Details");
        Option.enqueue("  8. Add Admission");
        Option.enqueue("  9. Cancel Admission");
        Option.enqueue("  0. Exit");
    }



    void Show_Admin() throws Exception {
        do {
            Option.display();

            int choice = 0;
            while (true) {
                try {
                    System.out.print("Enter your choice: ");
                    choice = scanner.nextInt();
                    break;
                } catch (Exception e) {
                    System.out.println("Invalid! Enter a valid integer.");
                    scanner.next();
                }
            }

            switch (choice) {
                case 1:
                    addTeacher();
                    break;
                case 2:
                    showTeacherDetails();
                    break;
                case 3:
                    addStudentDetails();
                    break;
                case 4:
                    int searchID;
                    while (true) {
                        try {
                            System.out.println("Enter Student_ID to get Details: ");
                            System.out.print("Enter 0 for all : ");
                            searchID = scanner.nextInt();

                            if (searchID == 0) {
                                allStudentDetails();
                                break;
                            } else if (searchID > 0) {
                                showStudentDetails(searchID);
                                break;
                            } else {
                                System.out.println("Student_ID cannot be 0 or negative. Try again.");
                            }
                        } catch (Exception e) {
                            System.out.println("Invalid input. Please enter a numeric Student_ID.");
                            scanner.nextLine();
                        }
                    }
                    break;

                case 5:
                    System.out.println("Enter class to get Marksheet");
                    int searchClass = scanner.nextInt();
                    showClassMarksheet(searchClass);
                    break;
                case 6:
                    System.out.println("Enter Student_ID to get Parents Details");
                    int searchParent = scanner.nextInt();
                    showParentDetails(searchParent);
                    break;
                case 7:
                    showFeesDetails();
                    break;
                case 8:
                    add_object.takeAdmission();
                    break;
                case 9:
                    System.out.println("Enter Student_ID for cancellation of admission");
                    int search_sid = scanner.nextInt();
                    canceladdmission(search_sid);
                    break;
                case 0:
                    flag = false;
                    break;

                default:
                    System.out.println("Enter Proper choose ");
                    System.out.println();
            }
        }
        while (flag);

    }

    void addTeacher() throws SQLException {
        //  scanner.nextLine();
        System.out.print("Enter Teacher Name: ");
        String name = scanner.nextLine();

        String subject = null;
        System.out.print("Select Subject : ");
        System.out.print("1.Maths : ");
        System.out.print("2.Science : ");
        System.out.print("3.S.S. : ");
        System.out.print("4.English : ");
        int Sub_no = -1;
        while (true) {
            System.out.print("Enter subject number (1-4): ");

            if (scanner.hasNextInt()) {
                Sub_no = scanner.nextInt();

                if (Sub_no >= 1 && Sub_no <= 4) {
                    System.out.println("Valid input: " + Sub_no);
                    break;
                } else {
                    System.out.println(" Invalid! Please enter a number between 1 and 4.");
                }
            } else {
                System.out.println("Invalid! Only numbers are allowed.");
                scanner.next();
            }
        }
        if (Sub_no == 1) {
            subject = "Maths";
        }
        if (Sub_no == 2) {
            subject = "Science";
        }
        if (Sub_no == 3) {
            subject = "S.S.";
        }
        if (Sub_no == 4) {
            subject = "English";
        }

        System.out.print("Enter Contact: ");
        String contact;
        do {
            contact = scanner.next();
            scanner.nextLine();
            if (contact.length() == 10 &&
                    contact.matches("[0-9]+") &&
                    (contact.startsWith("9") || contact.startsWith("8") || contact.startsWith("7"))) {
                break;
            } else {
                System.out.print("Enter a valid Mobile NO (Contains 10 digit only) : ");
            }
        } while (true);

        System.out.print("Enter Teacher Salary: ");
        int salary = scanner.nextInt();

        String insert = "INSERT INTO Teacher (Name, Subject, Contact, Salary)VALUES (?,?,?,?)";
        PreparedStatement pst = connect.prepareStatement(insert);
        pst.setString(1, name);
        pst.setString(2, subject);
        pst.setString(3, contact);
        pst.setInt(4, salary);
        pst.executeUpdate();

        System.out.println("Teacher added successfully.");
    }

    void showTeacherDetails() throws SQLException {
        String query = "SELECT * FROM Teacher";
        ResultSet result = state.executeQuery(query);
        while (result.next()) {
            System.out.println(result.getString(1) + " " +
                    result.getString(2) + " " +
                    result.getString(3) + " " +
                    result.getString(4) + " " +
                    result.getString(5));
        }
    }

    void addStudentDetails() throws Exception {
        String query = "SELECT MAX(Student_id) AS max_id FROM Student";
        ResultSet rs = state.executeQuery(query);
        int maxStudent = 0;
        if (rs.next()) {
            maxStudent= rs.getInt("max_id");
        }

        System.out.println("Enter details for Student  : ");
        System.out.print("First Name : ");
        String firstname = scanner.next();

        System.out.print("Second Name : ");
        String secondname = scanner.next();
        firstname = firstname.substring(0, 1).toUpperCase() + firstname.substring(1).toLowerCase();
        secondname = secondname.substring(0, 1).toUpperCase() + secondname.substring(1).toLowerCase();

        String Student_name = (firstname.concat(" " + secondname));

        String dob;
        int DD = 0, MM = 0, YYYY = 0;
        System.out.println("Enter DOB");
        while (true) {
            try {
                System.out.print("Date: ");
                DD = scanner.nextInt();

                System.out.print("Month: ");
                MM = scanner.nextInt();

                System.out.print("Year: ");
                YYYY = scanner.nextInt();

                if ((((MM == 1 || MM == 3 || MM == 5 || MM == 7 || MM == 8 || MM == 10 || MM == 12) && DD >= 1 && DD <= 31)
                        || ((MM == 4 || MM == 6 || MM == 9 || MM == 11) && DD >= 1 && DD <= 30)
                        || (MM == 2 && DD >= 1 && DD <= 28 && YYYY % 4 != 0)
                        || (MM == 2 && DD >= 1 && DD <= 29 && YYYY % 4 == 0)) && (YYYY > 2000 && YYYY < 2020)) {
                    dob = DD + "-" + MM + "-" + YYYY;
                    break;

                }
                else {
                    System.out.println("Invalid date. Please enter a valid DOB.");
                }

            } catch (Exception e) {
                System.out.println("Please enter numeric values only.");
                scanner.nextLine();
            }
        }

        String gmail;
        do {
            System.out.print("Gmail : ");
            gmail = scanner.next();
            scanner.nextLine();
            if (gmail.endsWith("@gmail.com") && gmail.indexOf("@") > 3) {
                break;
            } else {
                System.out.print("Enter a valid Gmail (ends with @gmail.com): ");
            }
        } while (true);

        String mobile;
        do {
            System.out.print("Mobile number : ");
            mobile = scanner.next();
            scanner.nextLine();
            if (mobile.length() == 10 &&
                    mobile.matches("[0-9]+") &&
                    (mobile.startsWith("9") || mobile.startsWith("8") || mobile.startsWith("7"))) {
                break;
            } else {
                System.out.print("Enter a valid Mobile NO (Contains 10 digit only) : ");
            }
        } while (true);

        int Standard = 0;
        do {
            System.out.print("Standard : ");
            Standard = scanner.nextInt();
            if (Standard == 5 || Standard == 6 || Standard == 6 || Standard == 7 || Standard == 8 || Standard == 9 || Standard == 10) {
                break;
            } else {
                System.out.println("Enter a valid Standard 5-10 : ");
            }
        } while (true);

        int rollNumber = new Random().nextInt(99) + 10;

        String gender = null;
        int Gen_no = 0;
        System.out.println("1. Female");
        System.out.println("2. Male");

        while (true) {
            try {
                System.out.print("Select Gender (1 or 2): ");
                Gen_no = scanner.nextInt();

                if (Gen_no == 1) {
                    gender = "Female";
                    break;
                } else if (Gen_no == 2) {
                    gender = "Male";
                    break;
                } else {
                    System.out.println("Invalid choice. Please enter 1 or 2.");
                }

            } catch (Exception e) {
                System.out.println("Invalid input. Please enter a number (1 or 2).");
                scanner.nextLine();
            }
        }

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

        String feesQuery = "";
        if (Standard == 5 || Standard == 6) {
            feesQuery = "INSERT INTO Fees (Student_id, Remaining_fees, Paid, Total) VALUES(" + (maxStudent + 1) + ", 30000.0 , 0.0, 30000.0)";
        }

        if (Standard == 7 || Standard == 8) {
            feesQuery = "INSERT INTO Fees (Student_id, Remaining_fees, Paid, Total) VALUES(" + (maxStudent + 1) + ", 40000.0 , 0.0, 40000.0)";
        }

        if (Standard == 9 || Standard == 10) {
            feesQuery = "INSERT INTO Fees (Student_id, Remaining_fees, Paid, Total) VALUES(" + (maxStudent + 1) + ", 50000.0 , 0.0, 50000.0)";
        }
        state.execute(feesQuery);
        scanner.nextLine();

        System.out.print("Enter Parent Name: ");
        String parentName = scanner.nextLine().trim();


        System.out.print("Enter Parent Mobile NO: ");
        String parentMobile="";
        do {
            parentMobile = scanner.next();
            scanner.nextLine();
            if (parentMobile.length() == 10 &&
                    parentMobile.matches("[0-9]+") &&
                    (parentMobile.startsWith("9") || parentMobile.startsWith("8") || parentMobile.startsWith("7"))) {
                break;
            } else {
                System.out.print("Enter a valid Mobile NO (Contains 10 digit only) : ");
            }
        } while (true);

        String parentQuery = "INSERT INTO Parents_details (Student_id, name, contact) VALUES (?, ?, ?)";
        PreparedStatement ps = connect.prepareStatement(parentQuery);
            ps.setInt(1, maxStudent + 1);
            ps.setString(2, parentName);
            ps.setString(3, parentMobile);

            int rows = ps.executeUpdate();

        System.out.println("Student added successfully.  ");
        System.out.println("  Student ID = "+(maxStudent+1));
    }

    void showStudentDetails(int SID) throws Exception {
        String select = "select * from Student where Student_id=" + SID;

        ResultSet result = state.executeQuery(select);
        while (result.next()) {
            System.out.println("\n--- Student Details ---");
            System.out.println("Name : " + result.getString(2));
            System.out.println("Roll No : " + result.getString(3));
            System.out.println("DOB : " + result.getString(4));
            System.out.println("Grade : " + result.getString(5));
            System.out.println("Gender : " + result.getString(6));
            System.out.println("Phone : " + result.getString(7));
            System.out.println("Email : " + result.getString(8));
            System.out.println("-------------------------------------");
        }
    }


    void showClassMarksheet(int STD) throws Exception {
        String select = "select * from Marksheet where Standard=" + STD;
        ResultSet r = state.executeQuery(select);
        while (r.next()) {
            System.out.println(r.getString(1) + " "
                    + r.getString(2) + " " +
                    r.getString(3) + " " +
                    r.getString(4) + " " +
                    r.getString(5) + " " +
                    r.getString(6) + " " +
                    r.getString(7) + " " +
                    r.getString(8) + " " +
                    r.getString(9));
        }
    }


    void showParentDetails(int searchParent) throws SQLException {
        //  String query = "SELECT Student.Name ,Student.RollNo, Parents_Details.Name, Parents_Details.contact FROM  Student JOIN Parents_Details  ON Student.Student_id = Parents_Details.Student_id WHERE Student.Student_id = "+searchParent;
        String query = "SELECT Student.Name AS StudentName, Student.Roll_no AS RollNo, " +
                "Parents_Details.Name AS ParentName, Parents_Details.Contact AS ParentContact " +
                "FROM Student " +
                "JOIN Parents_Details ON Student.Student_id = Parents_Details.Student_id " +
                "WHERE Student.Student_id = " + searchParent;

        ResultSet rs = state.executeQuery(query);
        while (rs.next()) {
            System.out.println(rs.getString("StudentName") + " " +
                    rs.getString("RollNo") + " " +
                    rs.getString("ParentName") + " " +
                    rs.getString("ParentContact"));
        }
    }

    void showFeesDetails() throws SQLException {
        String query = " SELECT s.Name AS StudentName  , s.Roll_No AS RollNo , s.Standard As STD ,f.Remaining_fees AS RemainingFees , f.Paid AS paidfee , f.Total  AS TotalFees  FROM  Student s JOIN Fees f ON s.Student_id = f.Student_id";
        ResultSet result = state.executeQuery(query);
        while (result.next()) {
            System.out.println(result.getString("StudentName") + " " +
                    result.getString("RollNo") + " " +
                    result.getString("STD") + " " +
                    result.getString("RemainingFees") + " " +
                    result.getString("paidfee") + " " +
                    result.getString("TotalFees"));
        }

    }

    void canceladdmission(int sid) throws Exception {
        showStudentDetails(sid);
        System.out.println("Confirm 1.Yesss 2.No ");
        int confirm = scanner.nextInt();
        if (confirm == 1) {
            String query = "DELETE FROM Student WHERE Student_id =" + sid;
            state.execute(query);
        } else {
            System.out.println("Admission is not cancel ");
        }

    }

    void allStudentDetails() throws Exception {
        String selectAll = "SELECT * FROM Student";
        ResultSet result = state.executeQuery(selectAll);

        System.out.println("\n---------------------- Student Details ----------------------");
        System.out.println("Name             |Roll No| DOB        |Grade| Gender | Phone        | Email");
        System.out.println("-------------------------------------------------------------------------------");

        while (result.next()) {
            String name = result.getString(2);
            String roll = result.getString(3);
            String dob = result.getString(4);
            String grade = result.getString(5);
            String gender = result.getString(6);
            String phone = result.getString(7);
            String email = result.getString(8);

            System.out.print(name);
            for (int i = name.length(); i < 17; i++) System.out.print(" ");
            System.out.print("| ");

            System.out.print(roll);
            for (int i = roll.length(); i < 8; i++) System.out.print(" ");
            System.out.print("| ");

            System.out.print(dob);
            for (int i = dob.length(); i < 12; i++) System.out.print(" ");
            System.out.print("| ");

            System.out.print(grade);
            for (int i = grade.length(); i < 5; i++) System.out.print(" ");
            System.out.print("| ");

            System.out.print(gender);
            for (int i = gender.length(); i < 6; i++) System.out.print(" ");
            System.out.print("| ");

            System.out.print(phone);
            for (int i = phone.length(); i < 12; i++) System.out.print(" ");
            System.out.print("| ");

            System.out.println(email);
        }

    }
}

