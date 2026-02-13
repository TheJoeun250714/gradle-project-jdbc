package com.my.servlet;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

/**
 * doPost doGet 을 이용해서 JDBC 연결
 */

@WebServlet("/user")
public class UserServlet extends HttpServlet {
    // DB 연결 정보
    private static final String DB_URL = "application.properties 나 config.properties 에 작성한 url 넣어주기";
    private static final String DB_USER = "application.properties 나 config.properties 에 작성한 user 넣어주기";
    private static final String DB_PASSWORD = "application.properties 나 config.properties 에 작성한 password 넣어주기";

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();

        // 파라미터 받기
        String name = request.getParameter("name");
        String email = request.getParameter("email");
        // import java.sql.Connection;
        Connection conn = null;
        // import java.sql.PreparedStatement;
        PreparedStatement pstmt = null;

        try {
            // 1. JDBC 드라이버 로드
            Class.forName("com.mysql.cj.jdbc.Driver");
            // 2. DB 연결
            conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
            // 3. SQL 작성
            String sql ="INSERT INTO users (name, email) VALUES (?,?)";
            pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, name);
            pstmt.setString(2, email);

            // 4. 위 작성된 sql 실행하고 결과 result 에 저장 굳이 안해도됨
            // debug 나 log 확인하고 싶을 때 result 에 저장해서 저장한 결과 확인하는 것!
            int result = pstmt.executeUpdate();

            // 5. bad case 결과 화면에서 보여주기
            out.println("<html><body>");
            if(result > 0) {
                out.println("<h1>사용자 등록 성공</h1>");
                out.println("<p>이름 "+ name +"</h1>");
                out.println("<p>이메일 "+ email +"</h1>");
            } else {
                out.println("<h1>등록실패</h1>");
            }
            out.println("<a href='/userForm.jsp'>돌아가기</a>");
            out.println("</body></html>");

        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
