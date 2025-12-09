package School;
import java.sql.*;
import java.util.Scanner;

public class Parent {
    Student object_student=new Student();

    Scanner scanner=new Scanner(System.in);
    Boolean flag = true;

    String db_url="jdbc:mysql://localhost:3306/school_20";
    String db_user="root";
    String db_password="";
    Connection connect= DriverManager.getConnection(db_url,db_user,db_password);
    Statement state3=connect.createStatement();

    public Parent() throws Exception {}

    public void check_Parent() throws Exception {
        Boolean check=true;
        int sid=0;
        while (check) {
            System.out.println("******************************");
            System.out.println("🎒 Welcome Parent");
            System.out.print("Enter Username (SiD) :  ");
            sid = scanner.nextInt();
            System.out.print("Enter Your Pass (Rollno) :  ");
            int pass=scanner.nextInt();

            String selectQuery = "Select * from Student";
            ResultSet Result = state3.executeQuery(selectQuery);
            while (Result.next()){
                int checkSid=Result.getInt(1);
                int checkPass=Result.getInt(3);

                if ((checkSid==sid) && (checkPass==pass)){
                    check=false;
                    break;
                }

            }
            System.out.println("TRY Again");
        }
        if (check==false){
            ShowParent(sid);
        }

    }



    void ShowParent(int sid) throws Exception {
        do {
            System.out.println("\nChoose an option:");
            System.out.println("  1. View Your Details");
            System.out.println("  2. View Marksheet");
            System.out.println("  3. View Fees");
            System.out.println("  4. Fees deposit");
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
                case 1: Parents_details(sid);
                    break;

                case 2: object_student.getmark(sid);
                    break;

                case 3: object_student.getfees(sid);
                    break;

                case 4:
                    break;

                case 0:
                    System.out.println("Exiting...");
                    flag=false;
                    break;

                default:
                    System.out.println("Enter Proper choice. Try again ");
                    System.out.println();
            }
        }
        while (flag) ;

    }

    void Parents_details(int sid) throws SQLException {
        String select="select * from Parents_details where Student_id="+sid;
        Boolean isParent=state3.execute(select)  ;
        if (isParent) {
            ResultSet result = state3.executeQuery(select);
            while (result.next()) {
                System.out.println("Student ID: " + result.getInt(1) +
                        " | Parent Name : " + result.getString(2) +
                        " | Parents contact : " + result.getString(3));
            }
        }
        else {
            System.out.println("No Such Parent Details Exists");
        }

    }



}

