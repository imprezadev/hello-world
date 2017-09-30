package com.smartware.app.web;

import java.io.IOException;
import java.util.Collections;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.smartware.domain.MoneyMovement;
import com.smartware.service.MoneyTrackService;

public class MainServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		MoneyTrackService moneyTrackService = new MoneyTrackService();
		List<MoneyMovement> moneyMovements = moneyTrackService.getMoneyMovements();
		Collections.sort(moneyMovements);
		Collections.reverse(moneyMovements);

		request.setAttribute("moneyMovements", moneyMovements);

		RequestDispatcher view = request.getRequestDispatcher("view/main.jsp");
		view.forward(request, response); 
	}

}
