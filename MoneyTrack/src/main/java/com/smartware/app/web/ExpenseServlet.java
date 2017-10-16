package com.smartware.app.web;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.smartware.common.Utils;
import com.smartware.domain.Expense;
import com.smartware.domain.catalog.Currency;
import com.smartware.domain.catalog.ExpenseCategory;
import com.smartware.domain.catalog.ExpensePaymentType;
import com.smartware.service.MoneyTrackService;

public class ExpenseServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	MoneyTrackService moneyTrackService = new MoneyTrackService();

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		Currency[] currencies = moneyTrackService.getCurrencies();
		request.setAttribute("currencies", currencies);

		ExpensePaymentType[] paymentTypes = moneyTrackService.getExpensePaymentTypes();
		request.setAttribute("paymentTypes", paymentTypes);

		ExpenseCategory[] expenseCategories = moneyTrackService.getExpenseCategories();
		request.setAttribute("expenseCategories", expenseCategories);

		long expenseId = 0;
		if (request.getParameter("id") != null) {
			expenseId = Long.valueOf(request.getParameter("id"));
		}
		else
		if (request.getAttribute("id") != null) {
			expenseId = (Long)request.getAttribute("id");
		}

		if (expenseId > 0) {
			Expense expense = moneyTrackService.getExpense(expenseId);

			request.setAttribute("expense", expense);
		}

		RequestDispatcher view = request.getRequestDispatcher("view/expense.jsp");
		view.forward(request, response); 
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		List<String> errorMsgs = new ArrayList<String>();

		Date date = null;
		if (Utils.isValidDateTimeString(request.getParameter("edtDate"))) {
			date = Utils.getDateTimeFromString(request.getParameter("edtDate"));
		}
		else {
			errorMsgs.add("Date: Invalid Date, format should be '" + Utils.DATE_TIME_DEFAULT_FORMAT + "'.");
		}

		Float amount = 0f;
		if (Utils.isValidFloatNumberString(request.getParameter("edtAmount"))) {
			amount = Utils.getFloatNumberFromString(request.getParameter("edtAmount"));
		}
		else
			errorMsgs.add("Amount: It should be a decimal number.");

		Currency currency = null;
		try {
			currency = Currency.valueOf(request.getParameter("cbCurrency"));
		}
		catch (Exception ex) {
			errorMsgs.add("Currency: " + ex);
		}

		ExpensePaymentType paymentType = null;
		try {
			paymentType = ExpensePaymentType.valueOf(request.getParameter("cbPaymentType"));
		}
		catch (Exception ex) {
			errorMsgs.add("Payment Type: " + ex);
		}

		ExpenseCategory expenseCategory = null;
		try {
			expenseCategory = ExpenseCategory.valueOf(request.getParameter("cbExpenseCategory"));
		}
		catch (Exception ex) {
			errorMsgs.add("Expense Category: " + ex);
		}

		String detail = null;
		detail = request.getParameter("txtDetail");

		if (errorMsgs.isEmpty()) {
			Expense expense = new Expense();
			expense.setDate(date);
			expense.setAmount(amount);
			expense.setCurrency(currency);
			expense.setPaymentType(paymentType);
			expense.setDetail(detail);
			expense.setCategory(expenseCategory);
			
			long id = moneyTrackService.recordExpense(expense);
			request.setAttribute("id", id);
		}
		else {
			request.setAttribute("errorMsgs", errorMsgs);
		}

		request.setAttribute("saveOperation", true);
		doGet(request, response);
	}

}
