package com.my.servlet;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * doPost doGet 을 이용해서 JDBC 연결
 */

@WebServlet("/user")
public class UserServlet extends HttpServlet {
    // DB 연결 정보
    private static final String DB_URL = "application.properties 나 config.properties 에 작성한 url 넣어주기";
    private static final String DB_USER = "application.properties 나 config.properties 에 작성한 user 넣어주기";
    private static final String DB_PASSWORD = "application.properties 나 config.properties 에 작성한 password 넣어주기";

    @Override
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

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();

        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        List<User> userList = new ArrayList<>();


        try {
            // JDBC 자바와 데이터베이스 어떤 드라이버로 연결할 것인가. 선택
            Class.forName("com.mysql.cj.jdbc.Driver");

            // DB 연결
            conn = DriverManager.getConnection(DB_URL,DB_USER,DB_PASSWORD);

            // SQL 작성
            String sql = "SELECT * FROM users";
            pstmt = conn.prepareStatement(sql);

            // 실행
            rs = pstmt.executeQuery();

            // 결과를 List 담기
            while(rs.next()) {
                User u = new User();
                u.setId(rs.getLong("id"));
                u.setName(rs.getString("name"));
                u.setEmail(rs.getString("email"));
                userList.add(u);
            }

            // request 에 데이터 담기
            request.setAttribute("users",userList);

            // JSP로 전달하기
            request.getRequestDispatcher("/WEB-INF/jsp/userList.jsp").forward(request,response);

            // 결과 출력 또는 jsp 파일로 전달하기
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }
}
