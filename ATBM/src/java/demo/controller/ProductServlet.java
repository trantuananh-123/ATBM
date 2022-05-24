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
        try {
            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
            String dbURL = "jdbc:sqlserver://localhost:1433;databaseName=demo";
            String dbUsername = "sa";
            String dbPassword = "tom18102001";
            Connection connection = DriverManager.getConnection(dbURL,
                    dbUsername, dbPassword);
            Statement statement = connection.createStatement(
                    ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
            query = "SELECT * FROM product ORDER BY ID";
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
        } catch (ClassNotFoundException | SQLException ex) {
            Logger.getLogger(ProductServlet.class.getName()).log(Level.SEVERE, null, ex);
            sqlResult = "<p>Error loading the database driver: <br>" +
                    ex.getMessage() + "</p>";
        }
        request.setAttribute("list", list);
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
                    "ORDER BY ID";
            ResultSet resultSet = statement.executeQuery(query);
            while (resultSet.next()) {
                Product product = new Product();
                product.setId(resultSet.getInt(1));
                product.setName(resultSet.getString(1));
                product.setDescription(resultSet.getString(3));
                product.setPrice(resultSet.getFloat(4));
                list.add(product);
            }
            resultSet.beforeFirst();
        } catch (ClassNotFoundException | SQLException ex) {
            Logger.getLogger(ProductServlet.class.getName()).log(Level.SEVERE, null, ex);
            sqlResult = "<p>Error loading the database driver: <br>" +
                    ex.getMessage() + "</p>";
        }
        request.setAttribute("list", list);
        String url = "/product.jsp";
        request.setAttribute("sqlResult", sqlResult);
        request.setAttribute("name", name);
        request.setAttribute("query", query);
        getServletContext().getRequestDispatcher(url).forward(request, response);
    }

}
