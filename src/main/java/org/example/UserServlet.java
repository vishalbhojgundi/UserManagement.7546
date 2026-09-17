package org.example;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;

@WebServlet("/register")
public class UserServlet extends HttpServlet {

    @Override
    protected void doPost(
            HttpServletRequest request,
            HttpServletResponse response)
            throws ServletException, IOException {

        // Get data from HTML form
        String name = request.getParameter("name");
        String gender = request.getParameter("gender");
        String email = request.getParameter("email");


        // Remove extra spaces
        if (name != null) {
            name = name.trim();
        }

        if (email != null) {
            email = email.trim();
        }


        // Server-side validation
        if (name == null ||
                name.isEmpty() ||
                !name.matches("[A-Za-z ]{2,50}")) {

            response.sendError(
                    HttpServletResponse.SC_BAD_REQUEST,
                    "Invalid name"
            );

            return;
        }


        if (gender == null ||
                (!gender.equals("Male") &&
                        !gender.equals("Female") &&
                        !gender.equals("Other"))) {

            response.sendError(
                    HttpServletResponse.SC_BAD_REQUEST,
                    "Invalid gender"
            );

            return;
        }


        if (email == null ||
                !email.matches(
                        "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$"
                )) {

            response.sendError(
                    HttpServletResponse.SC_BAD_REQUEST,
                    "Invalid email"
            );

            return;
        }


        // SQL query
        String sql =
                "INSERT INTO users (name, gender, email) " +
                        "VALUES (?, ?, ?)";


        try (
                Connection con =
                        DBConnection.getConnection();

                PreparedStatement ps =
                        con.prepareStatement(sql)
        ) {

            ps.setString(1, name);

            ps.setString(2, gender);

            ps.setString(3, email);


            int rows =
                    ps.executeUpdate();


            if (rows > 0) {

                /*
                 * IMPORTANT:
                 *
                 * Redirect after POST.
                 *
                 * This prevents the browser from
                 * submitting the same POST again
                 * when the user refreshes the page.
                 */

                response.sendRedirect(
                        "index.html?success=1"
                );

            }

        } catch (Exception e) {

            e.printStackTrace();

            response.sendError(
                    HttpServletResponse.SC_INTERNAL_SERVER_ERROR,
                    "Unable to save user: "
                            + e.getMessage()
            );
        }
    }
}