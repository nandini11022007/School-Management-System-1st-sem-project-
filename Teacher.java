package School;

import java.sql.*;
import java.util.Scanner;

public class Teacher {
    Admin object=new Admin();
    NoticeBoard Notice=new NoticeBoard();
    Scanner scanner=new Scanner(System.in);
    Boolean flag = true;

    String db_url="jdbc:mysql://localhost:3306/school_20";
    String db_user="root";
    String db_password="";
    Connection connect= DriverManager.getConnection(db_url,db_user,db_password);
    Statement state = connect.createStatement();

    public Teacher() throws Exception {
    }



    void Show_Teacher() throws Exception {
        do {
            System.out.println("\n******************************");
            System.out.println("📘 Welcome Teacher ");
            System.out.println("******************************");
            System.out.println("Choose an option:");
            System.out.println("  1. View Your Details");
            System.out.println("  2. Add Student Details");
            System.out.println("  3. Get Student Details");
            System.out.println("  4. View Timetable");
            System.out.println("  5. View Class Marksheet");
            System.out.println("  6. Update Parent Details");
            System.out.println("  7. View Parent Details");
            System.out.println("  8. Add Notice");
            System.out.println("  9. Remove Notice");
            System.out.println("  0. Exit");

            int choice=0;
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
                    System.out.println("Enter Your Teacher ID to get Details");
                    int teacher_searchD=scanner.nextInt();
                    showTeacher(teacher_searchD);
                    break;

                case 2:
                    object.addStudentDetails();
                    break;

                case 3:
                    System.out.println("Enter Student_ID to get Details");
                    int search_ID=scanner.nextInt();
                    object.showStudentDetails(search_ID);
                    break;


                case 4:
                    System.out.println("Enter class to See Time Table");
                    int search_class=scanner.nextInt();
                    showTimetable(search_class);
                    break;

                case 5:
                    System.out.println("Enter class to get Marksheet");
                    int searchClass=scanner.nextInt();
                    object.showClassMarksheet(searchClass);
                    break;

                case 6:

                    break;


                case 7:
                    System.out.println("Enter Student_ID to get Parents Details");
                    int searchParent=scanner.nextInt();
                    object.showParentDetails(searchParent);
                    break;

                case 8:
                    System.out.println("Enter Notice To be added");
                    String note=scanner.next();
                    Notice.addNotice(note);
                    break;

                case 9:
                    Notice.removeFirstNotice();
                    break;

                case 0:
                    System.out.println("Exiting...");
                    flag=false;
                    break;

                default:
                    System.out.println("Enter Proper choise ");
                    System.out.println();
            }
        }
        while (flag) ;

    }
    void showTeacher(int teacher_searchD) throws SQLException {
        String query = "SELECT * FROM Teacher where Teacher_id ="+teacher_searchD;
        ResultSet result = state.executeQuery(query);
        while (result.next()) {
            System.out.println(result.getString(1) + " " +
                    result.getString(2) + " " +
                    result.getString(3) + " " +
                    result.getString(4) + " " +
                    result.getString(5) );
        }
    }

    void showTimetable(int clas) throws SQLException {
        String query="SELECT * From TT"+clas;
        ResultSet result = state.executeQuery(query);
        while (result.next()) {
            System.out.println(result.getString(1) + " " +
                    result.getString(2) + " " +
                    result.getString(3) + " " +
                    result.getString(4) + " " +
                    result.getString(5) );
        }
    }
}

