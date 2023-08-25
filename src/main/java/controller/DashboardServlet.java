package controller;

import model.User;
import model.Pizza;

import java.io.IOException;
import java.util.ArrayList;
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
import model.Dough;
import model.Ingredient;

@WebServlet("/Dashboard")
public class DashboardServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	private PizzaDAO pizzaDAO;
	private DoughDAO doughDAO;
	private IngredientDAO ingredientDAO;

	@Override
	public void init() throws ServletException {
		super.init();
		pizzaDAO = new PizzaDAO();
		doughDAO = new DoughDAO();
		ingredientDAO = new IngredientDAO();

	}

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		HttpSession session = request.getSession();
		User authenticatedUser = (User) session.getAttribute("user");
		if (authenticatedUser != null) {
			List<Pizza> userPizzas = pizzaDAO.getPizzasByUserId(authenticatedUser.getId());
			List<Dough> doughList = doughDAO.getAllDoughs();
			List<Ingredient> ingredientList = ingredientDAO.getAllIngredients();
			session.setAttribute("doughList", doughList);
			session.setAttribute("ingredientList", ingredientList);
			session.setAttribute("userPizzas", userPizzas);
			request.getRequestDispatcher("/dashboard.jsp").forward(request, response);
		} else {
			response.sendRedirect(request.getContextPath() + "/login.jsp");
		}
	}

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException {
		HttpSession session = request.getSession();
		Long doughId = Long.parseLong(request.getParameter("dough"));
		String[] ingredientsIds = request.getParameterValues("ingredients");

		// Crea nuova pizza
		String name = request.getParameter("namePizza");
		Pizza newPizza = new Pizza();
		if (newPizza.getIngredients() == null) {
			newPizza.setIngredients(new ArrayList<>());
		}
		newPizza.setName(name);
		// Recupera impasto
		Dough dough = doughDAO.getDoughById(doughId);
		newPizza.setDough(dough);
		// Recupera ingredienti
		for (String id : ingredientsIds) {
			Ingredient ingredient = ingredientDAO.getIngredientById(Long.parseLong(id));
			newPizza.getIngredients().add(ingredient);
		}
		// Recupera utente loggato
		User user = (User) session.getAttribute("user");
		newPizza.setUser(user);
		pizzaDAO.savePizza(newPizza);
		System.out.println(newPizza.toString());
		response.sendRedirect(request.getContextPath() + "/Dashboard");

	}

	@Override
	public void destroy() {
		super.destroy();
		pizzaDAO.closeEntityManagerFactory();
		doughDAO.closeEntityManagerFactory();
		ingredientDAO.closeEntityManagerFactory();
	}
}
