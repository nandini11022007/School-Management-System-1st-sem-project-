package School;
import java.sql.*;
import java.util.Scanner;

public class Student {
    Admin object_Admin = new Admin();
    Teacher object_teacher=new Teacher();
    NoticeBoard Notice=new NoticeBoard();

    Scanner scanner=new Scanner(System.in);
    Boolean flag = true;

    String db_url="jdbc:mysql://localhost:3306/school_20";
    String db_user="root";
    String db_password="";
    Connection connect= DriverManager.getConnection(db_url,db_user,db_password);
    Statement state3=connect.createStatement();

    public Student() throws Exception {}

    public void check_Student() throws Exception {
        Boolean check=true;
        int sid=0;
        while (check) {
            System.out.println("******************************");
            System.out.println("🎒 Welcome Student");
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
            Show_Studnet(sid);
        }

    }



    void Show_Studnet(int sid) throws Exception {
        do {
            System.out.println("\nChoose an option:");
            System.out.println("  1. View Your Details");
            System.out.println("  2. View Marksheet");
            System.out.println("  3. View Fees");
            System.out.println("  4. View Timetable");
            System.out.println("  5. View Notice Board");
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
                case 1: object_Admin.showStudentDetails(sid);
                    break;

                case 2: getmark(sid);
                    break;

                case 3: getfees(sid);
                    break;

                case 4: getTimeTable();
                    break;

                case 5:
                   Notice.showNotices();
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

    void getmark (int sid) throws Exception {
        String select="select * from Marksheet where Student_id="+sid;
        Boolean isStudent=state3.execute(select)  ;
        if (isStudent) {
            ResultSet result = state3.executeQuery(select);
            while (result.next()) {
                System.out.println("Roll: " + result.getInt(1) +
                        " | Maths : " + result.getInt(2) +
                        " | Science : " + result.getInt(3) +
                        " | SS : " + result.getInt(4) +
                        " | English : " + result.getInt(5) +
                        " | Percentage: " + result.getDouble(6));
            }
        }
        else {
            System.out.println("No Such Students Marks Exists");
        }
        
    }

    void getfees(int sid) throws Exception {
        String select = "SELECT * FROM Fees WHERE Student_id = " + sid;
        ResultSet result = state3.executeQuery(select);
        while (result.next()) {
            System.out.println("Roll: " + result.getInt(1) +
                    " | Total Fees: " + result.getDouble(2) +
                    " | Paid: " + result.getDouble(3) +
                    " | Remaining: " + result.getDouble(4));
        }
    }

    void getTimeTable() throws Exception {
        String select = "SELECT * FROM TimeTable";
        ResultSet result = state3.executeQuery(select);
        System.out.println("\nDay | Subject | Time");
        while (result.next()) {
            System.out.println(result.getString(1) +
                    " | " +result.getString(2) +
                    " | " +result.getString(3));
        }
    }
}

