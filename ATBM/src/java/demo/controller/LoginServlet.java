package demo.controller;

import demo.util.SqlUtil;
import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;
import java.sql.*;
import javax.servlet.annotation.WebServlet;

@WebServlet(name = "LoginServlet", urlPatterns = {"/login"})
public class LoginServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request,
            HttpServletResponse response)
            throws ServletException, IOException {
        String username = request.getParameter("username");
        String password = request.getParameter("password");
        String sqlResult = "";
        String query = "";
        String url = "";
        boolean isLoggedIn = false;
        try {
            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
            String dbURL = "jdbc:sqlserver://localhost:1433;databaseName=demo";
            String dbUsername = "sa";
            String dbPassword = "123456";
            Connection connection = DriverManager.getConnection(dbURL,
                    dbUsername, dbPassword);
            Statement statement = connection.createStatement(
                    ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
            query = "SELECT TOP 1 * FROM users WHERE username = '" + username +
                    "' AND password = '" + password + "'";
            ResultSet resultSet = statement.executeQuery(query);
            int totalRow = 0;
            while (resultSet.next()) {
                request.setAttribute("username", resultSet.getString("username"));
                request.setAttribute("password", resultSet.getString("password"));
                totalRow = 1;
            }
            resultSet.beforeFirst();
            if (totalRow > 0) {
                isLoggedIn = true;
                request.setAttribute("msg", "Login successfully!");
                sqlResult = SqlUtil.getHtmlTable(resultSet);
            } else {
                isLoggedIn = false;
                sqlResult = "";
                request.setAttribute("msg", "Login failed!");
            }
            resultSet.close();
            statement.close();
            connection.close();
        } catch (ClassNotFoundException e) {
            sqlResult = "<p>Error loading the database driver: <br>" +
                    e.getMessage() + "</p>";
        } catch (SQLException e) {
            sqlResult = "<p>Error executing the SQL statement: <br>" +
                    e.getMessage() + "</p>";
        }
        request.setAttribute("sqlResult", sqlResult);
        request.setAttribute("query", query);
        request.setAttribute("isLoggedIn", isLoggedIn);
        url = "/index.jsp";
        getServletContext().getRequestDispatcher(url).forward(request, response);
    }

}
