package com.example.attendance;

import java.io.*;
import java.sql.*;
import javax.servlet.*;
import javax.servlet.http.*;

public class AttendanceServlet extends HttpServlet {
    private static final String DB_URL = "jdbc:mysql://localhost:3306/webappdb";
    private static final String USER = "root";
    private static final String PASS = "root";  // change if needed

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        response.setContentType("text/html");
        PrintWriter out = response.getWriter();

        int studentId = Integer.parseInt(request.getParameter("studentId"));
        String date = request.getParameter("date");
        String status = request.getParameter("status");

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection con = DriverManager.getConnection(DB_URL, USER, PASS);

            String query = "INSERT INTO Attendance (StudentID, Date, Status) VALUES (?, ?, ?)";
            PreparedStatement ps = con.prepareStatement(query);
            ps.setInt(1, studentId);
            ps.setString(2, date);
            ps.setString(3, status);

            int rows = ps.executeUpdate();
            if (rows > 0) {
                out.println("<h3>Attendance marked successfully for Student ID: " + studentId + "</h3>");
            }

            con.close();
        } catch (Exception e) {
            out.println("Error: " + e.getMessage());
        }
    }
}
