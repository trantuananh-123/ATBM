package demo.controller;

import demo.model.Product;
import demo.util.SqlUtil;
import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.annotation.WebServlet;

@WebServlet(name = "ProductServlet", urlPatterns = {"/product"})
public class ProductServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws
            ServletException, IOException {
        String sqlResult = "";
        String query = "";
        List<Product> list = new ArrayList<>();
        int page = 1;
        if (request.getParameter("page") != null) {
            page = Integer.parseInt(request.getParameter("page"));
        }
        try {
            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
            String dbURL = "jdbc:sqlserver://localhost:1433;databaseName=demo";
            String dbUsername = "sa";
            String dbPassword = "tom18102001";
            Connection connection = DriverManager.getConnection(dbURL,
                    dbUsername, dbPassword);
            Statement statement = connection.createStatement(
                    ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
            query = "SELECT * FROM product ORDER BY ID OFFSET " + (page - 1) +
                    " ROWS FETCH NEXT 10 ROWS ONLY";
            ResultSet resultSet = statement.executeQuery(query);
            while (resultSet.next()) {
                Product product = new Product();
                product.setId(resultSet.getInt("id"));
                product.setName(resultSet.getString("name"));
                product.setDescription(resultSet.getString("description"));
                product.setPrice(resultSet.getFloat("price"));
                list.add(product);
            }
            resultSet.beforeFirst();
            sqlResult = SqlUtil.getHtmlTable(resultSet);
        } catch (ClassNotFoundException | SQLException ex) {
            Logger.getLogger(ProductServlet.class.getName()).log(Level.SEVERE, null, ex);
            sqlResult = "<p>Error loading the database driver: <br>" +
                    ex.getMessage() + "</p>";
        }
        request.setAttribute("list", list);
        request.setAttribute("noOfPages", 10);
        request.setAttribute("currentPage", page);
        request.setAttribute("sqlResult", sqlResult);
        request.setAttribute("query", query);
        String url = "/product.jsp";
        getServletContext().getRequestDispatcher(url).forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request,
            HttpServletResponse response)
            throws ServletException, IOException {
        String sqlResult = "";
        String query = "";
        List<Product> list = new ArrayList<>();
        int page = 1;
        if (request.getParameter("page") != null) {
            page = Integer.parseInt(request.getParameter("page"));
        }
        String name = request.getParameter("name");
        try {
            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
            String dbURL = "jdbc:sqlserver://localhost:1433;databaseName=demo";
            String dbUsername = "sa";
            String dbPassword = "tom18102001";
            Connection connection = DriverManager.getConnection(dbURL,
                    dbUsername, dbPassword);
            Statement statement = connection.createStatement(
                    ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
            query = "SELECT * FROM product WHERE name LIKE '%" + name + "%'" +
                    "ORDER BY ID OFFSET " + (page - 1) + " ROWS FETCH NEXT 10 ROWS ONLY";
            ResultSet resultSet = statement.executeQuery(query);
            while (resultSet.next()) {
                Product product = new Product();
                product.setId(resultSet.getInt("id"));
                product.setName(resultSet.getString("name"));
                product.setDescription(resultSet.getString("description"));
                product.setPrice(resultSet.getFloat("price"));
                list.add(product);
            }
            resultSet.beforeFirst();
            sqlResult = SqlUtil.getHtmlTable(resultSet);
        } catch (ClassNotFoundException | SQLException ex) {
            Logger.getLogger(ProductServlet.class.getName()).log(Level.SEVERE, null, ex);
            sqlResult = "<p>Error loading the database driver: <br>" +
                    ex.getMessage() + "</p>";
        }
        request.setAttribute("list", list);
        request.setAttribute("noOfPages", 10);
        request.setAttribute("currentPage", page);
        String url = "/product.jsp";
        request.setAttribute("sqlResult", sqlResult);
        request.setAttribute("query", query);
        getServletContext().getRequestDispatcher(url).forward(request, response);
    }

}
