package School;
import java.sql.*;
import java.util.Hashtable;
import java.util.Scanner;

// 17-07-25
public class user {
    public user() throws Exception {
    }

    public static void main(String[] args) throws Exception {
        createDatabaseAndTables();
        verify();
    }

    static void verify () throws Exception {
            Boolean flag = true;
            Student object_student=new Student();
            Admin object_Admin=new Admin() ;
            Teacher object_Teacher = new Teacher();
            Parent object_Parent = new Parent();


        Hashtable<String , String> Password=new  Hashtable<>();
        Password.put("Admin" ,"Admin@123");
        Password.put("Teacher" ,"Teacher@123");
        Password.put("Student" ,"123456789");
        Password.put("Parent" ,"Parent@123");

            do {
                System.out.println("******************************");
                System.out.println("🎓 Welcome To School App");
                System.out.println("******************************");
                System.out.println("Choose your user :");
                System.out.println("  1. Admin");
                System.out.println("  2. Teacher");
                System.out.println("  3. Student");
                System.out.println("  4. Parent");
                System.out.println("  0. Exit");
                Scanner scanner = new Scanner(System.in);

                int choice=0;
                while (true) {
                    try {
                        System.out.print("Enter your choice: ");
                        choice = scanner.nextInt();
                        break;
                    } catch (Exception e) {
                        System.out.println("Invalid! Enter a valid integer.");
                        Thread.sleep(5000); //throws Interrupted Exception
                        scanner.next();
                    }
                }


                switch (choice) {
                    case 1:
                        Boolean pass = false;
                        while (pass != true) {
                            System.out.println("User Name : Admin");
                            System.out.print("Enter Your Password : ");
                            String Admin_Pass = scanner.next();
                            if (Admin_Pass.equals(Password.get("Admin"))) {
                                object_Admin.Show_Admin();
                                pass = true;
                            }
                            else if (Admin_Pass.equals("0")) {verify();}
                            else {
                                System.out.println("Incorect Password");
                                Thread.sleep(5000); //throws Interrupted Exception
                                System.out.println();
                                System.out.println("Enter 0 to change user");
                            }
                        }
                        break;

                    case 2:
                        pass = false;
                        while (pass != true) {
                            System.out.println("User Name : Teacher");
                            System.out.print("Enter Your Password : ");
                            String Teacher_Pass = scanner.next();
                            if (Teacher_Pass.equals(Password.get("Teacher"))) {
                                object_Teacher.Show_Teacher();
                                pass = true;
                            }
                            else if (Teacher_Pass.equals("0")) {verify();}
                            else {
                                System.out.println("Incorect Password");
                                Thread.sleep(5000); //throws Interrupted Exception
                                System.out.println();
                                System.out.println("Enter 0 to change user");
                            }
                        }
                        break;

                    case 3:
                        pass = false;
                        while (pass != true) {
                            System.out.println("User Name : Student");
                            System.out.print("Enter Your Password : ");
                            String Student_pass = scanner.next();
                            if (Student_pass.equals(Password.get("Student"))) {
                                object_student.check_Student();
                                pass = true;
                            }
                            else if (Student_pass.equals("0")) {verify();}
                            else {
                                System.out.println("Incorect Password");
                                Thread.sleep(5000); //throws Interrupted Exception
                                System.out.println();
                                System.out.println("Enter 0 to change user");

                            }
                        }
                        break;

                    case 4:
                        pass = false;
                        while (pass != true) {
                            System.out.println("User Name : Parent");
                            System.out.print("Enter Your Password : ");
                            String Parent_pass = scanner.next();
                            if (Parent_pass.equals(Password.get("Parent"))) {
                                object_Parent.check_Parent();
                                pass = true;
                            } else if (Parent_pass.equals("0")) {verify();}
                            else {
                                System.out.println("Incorect Password");
                                Thread.sleep(5000); //throws Interrupted Exception
                                System.out.println();
                                System.out.println("Enter 0 to change user");
                            }
                        }
                        break;

                    case 0:
                        System.out.println("Exiting...");
                        flag = false;
                        break;

                    default:
                        Thread.sleep(2000); //throws Interrupted Exception
                        System.out.println("Enter Proper choise ");
                        System.out.println();
                }
            }
            while (flag);
        }

         static void createDatabaseAndTables() {
             String db_url = "jdbc:mysql://localhost:3306/school_20";
             String db_user = "root";
             String db_password = "";

             try {
                 Connection connect = DriverManager.getConnection(db_url, db_user, db_password);
                 Statement stmt = connect.createStatement();

                 // 3. Create Student Table
                 String studentTable = "CREATE TABLE IF NOT EXISTS Student (" +
                         "Student_id INT AUTO_INCREMENT PRIMARY KEY," +
                         "Name VARCHAR(50)," +
                         "Roll_no VARCHAR(20)," +
                         "DOB VARCHAR(20)," +
                         "Standard INT," +
                         "Gender VARCHAR(10)," +
                         "Mobile VARCHAR(15)," +
                         "Gmail VARCHAR(50))";

                 // 4. Create Teacher Table
                 String teacherTable = "CREATE TABLE IF NOT EXISTS Teacher (" +
                         "Teacher_id INT AUTO_INCREMENT PRIMARY KEY," +
                         "Name VARCHAR(50)," +
                         "Subject VARCHAR(30)," +
                         "Contact VARCHAR(20)," +
                         "Salary INT)";

                 // 5. Create Marksheet Table
                 String marksheetTable = "CREATE TABLE IF NOT EXISTS Marksheet (" +
                         "Student_id INT," +
                         "Standard INT," +
                         "Maths INT," +
                         "Science INT," +
                         "SS INT," +
                         "English INT," +
                         "Total INT," +
                         "Percentage DOUBLE," +
                         "Status VARCHAR(20)," +
                         "FOREIGN KEY (Student_id) REFERENCES Student(Student_id))";

                 // 6. Create Fees Table
                 String feesTable = "CREATE TABLE IF NOT EXISTS Fees (" +
                         "Student_id INT," +
                         "Remaining_fees DOUBLE," +
                         "Paid DOUBLE," +
                         "Total DOUBLE," +
                         "FOREIGN KEY (Student_id) REFERENCES Student(Student_id))";

                 // 7. Create Parents_Details Table
                 String parentTable = "CREATE TABLE IF NOT EXISTS Parents_Details (" +
                         "Student_id INT," +
                         "Name VARCHAR(50)," +
                         "Contact VARCHAR(15)," +
                         "FOREIGN KEY (Student_id) REFERENCES Student(Student_id))";

                 // 8. Create TT (Time Table) Table
                 String Table9 = "CREATE TABLE IF NOT EXISTS TT9 (" +
                         "Days VARCHAR(20)," +
                         "Lecture_1 VARCHAR(30)" +
                         "Lecture_2 VARCHAR(30)" +
                         "Lecture_3 VARCHAR(30)" +
                         "Lecture_4 VARCHAR(30))";

                 String Table10 = "CREATE TABLE IF NOT EXISTS TT10b (" +
                         "Days VARCHAR(20)," +
                         "Lecture_1 VARCHAR(30)" +
                         "Lecture_2 VARCHAR(30)" +
                         "Lecture_3 VARCHAR(30)" +
                         "Lecture_4 VARCHAR(30))";

                 // 9. Create Attendance Table
                 String attendanceTable = "CREATE TABLE IF NOT EXISTS Attendance (" +
                         "Student_id INT," +
                         "Day VARCHAR(10)," +
                         "Date DATE," +
                         "Status VARCHAR(20)," +
                         "FOREIGN KEY (Student_id) REFERENCES Student(Student_id))";

                 String Notice_record = "CREATE TABLE IF NOT EXISTS Notice (Notice VARCHAR(100)";

                 // Execute all create statements
                 stmt.executeUpdate(studentTable);
                 stmt.executeUpdate(teacherTable);
                 stmt.executeUpdate(marksheetTable);
                 stmt.executeUpdate(feesTable);
                 stmt.executeUpdate(parentTable);
                 stmt.executeUpdate(Table9);
                 stmt.executeUpdate(Table10);
                 stmt.executeUpdate(attendanceTable);
                 stmt.executeUpdate(Notice_record);

             } catch (Exception e) {
                 {}
             }

         }
}
