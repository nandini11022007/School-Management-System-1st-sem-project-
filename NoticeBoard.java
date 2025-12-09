package School;

import java.sql.*;

public class NoticeBoard {
    String db_url="jdbc:mysql://localhost:3306/school_20";
    String db_user="root";
    String db_password="";
    Connection connect= DriverManager.getConnection(db_url,db_user,db_password);
    Statement state3=connect.createStatement();
    Node head;

    public NoticeBoard() throws SQLException {
    }

    class Node {
        String notice;
        Node next;

        Node(String notice) {
            this.notice = notice;
            this.next = null;
        }
    }

    public void addNotice(String noticeText) throws SQLException {
        Node newNode = new Node(noticeText);
        if (head == null) {
            head = newNode;
        } else {
            Node temp = head;
            while (temp.next != null)
                temp = temp.next;
                temp.next = newNode;
        }
        System.out.println("Notice Added: " + noticeText);
        String insertQuery = "INSERT INTO Notice (Notice) VALUES (?)";
        PreparedStatement pst = connect.prepareStatement(insertQuery);
        pst.setString(1, noticeText);
    }

    public void removeFirstNotice() {
        if (head == null) {
            System.out.println("No notices to remove.");
        } else {
            System.out.println("Removed Notice: " + head.notice);
            head = head.next;
        }
    }

    public void showNotices() {
        if (head == null) {
            System.out.println("No current notices.");
            return;
        }
        Node temp = head;
        System.out.println("📌 Current Notices:");
        while (temp != null) {
            System.out.println("- " + temp.notice);
            temp = temp.next;
        }
    }

}
