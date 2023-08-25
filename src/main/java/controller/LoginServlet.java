package controller;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import DAO.DoughDAO;
import DAO.IngredientDAO;
import DAO.PizzaDAO;
import DAO.UserDAO;
import model.Dough;
import model.Ingredient;
import model.Pizza;
import model.User;

@WebServlet("/LoginServlet")
public class LoginServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    private UserDAO userDAO;
    
    
    public void init() {
        userDAO = new UserDAO();
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    private void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String username = request.getParameter("username");
        String password = request.getParameter("password");

        // Effettua l'autenticazione tramite il DAO
        User authenticatedUser = userDAO.authenticateUser(username, password);

        if (authenticatedUser != null) {
            HttpSession session = request.getSession();
            session.setAttribute("user", authenticatedUser);
            List<Dough> doughList = DoughDAO.getAllDoughs();
            List<Ingredient> ingredientList = IngredientDAO.getAllIngredients();
            session.setAttribute("doughList", doughList);
            session.setAttribute("ingredientList", ingredientList);
//            session.setAttribute("userPizzas", authenticatedUser.getListaPizze());
            request.getRequestDispatcher("/dashboard.jsp").forward(request, response);

        } else {
            request.setAttribute("error", "true");
            request.getRequestDispatcher("login.jsp").forward(request, response);
        }
    }

    
    public void destroy() {
        userDAO.closeEntityManagerFactory();
    }
}
