package com.smartware.app.web;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.smartware.common.Utils;
import com.smartware.domain.Withdrawal;
import com.smartware.domain.catalog.Currency;
import com.smartware.service.MoneyTrackService;

public class WithdrawalServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	MoneyTrackService moneyTrackService = new MoneyTrackService();

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String strDate     = (request.getParameter("edtDate") != null)    ? request.getParameter("edtDate")    : "";
		String strAmount   = (request.getParameter("edtAmount") != null)  ? request.getParameter("edtAmount")  : "";
		String strCurrency = (request.getParameter("cbCurrency") != null) ? request.getParameter("cbCurrency") : "";
		String strRemarks  = (request.getParameter("txtRemarks") != null) ? request.getParameter("txtRemarks") : "";

		Boolean editable = true;

		Currency[] currencies = moneyTrackService.getCurrencies();
		request.setAttribute("currencies", currencies);

		if (request.getAttribute("id") != null || request.getParameter("id") != null) {
			long withdrawalId = (request.getParameter("id") != null) ? Long.valueOf(request.getParameter("id")) : (Long)request.getAttribute("id");

			Withdrawal withdrawal = moneyTrackService.getWithdrawal(withdrawalId);

			strDate = Utils.getFormattedDateTime(withdrawal.getDate());
			strAmount = Utils.getFormattedFloat(withdrawal.getAmount());
			strCurrency = withdrawal.getCurrency().name();
			strRemarks = withdrawal.getRemarks();

			editable = false;
		}

		request.setAttribute("strDate", strDate);
		request.setAttribute("strAmount", strAmount);
		request.setAttribute("strCurrency", strCurrency);
		request.setAttribute("strRemarks", strRemarks);

		request.setAttribute("editable", editable);

		RequestDispatcher view = request.getRequestDispatcher("view/withdrawal.jsp");
		view.forward(request, response); 
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String strDate        = request.getParameter("edtDate");
		String strAmount      = request.getParameter("edtAmount");
		String strCurrency    = request.getParameter("cbCurrency");
		String strRemarks     = request.getParameter("txtRemarks");

		List<String> errorMsgs = new ArrayList<String>();

		Withdrawal withdrawal = new Withdrawal();

		if (Utils.isValidDateTimeString(strDate)) {
			withdrawal.setDate(Utils.getDateTimeFromString(strDate));
		}
		else {
			errorMsgs.add("Date: Invalid Date, format should be '" + Utils.DATE_TIME_DEFAULT_FORMAT + "'.");
		}

		if (Utils.isValidFloatNumberString(strAmount)) {
			withdrawal.setAmount(Utils.getFloatNumberFromString(strAmount));
		}
		else
			errorMsgs.add("Amount: It should be a decimal number.");

		Currency currency = null;
		try {
			currency = Currency.valueOf(strCurrency);
			withdrawal.setCurrency(currency);
		}
		catch (Exception ex) {
			errorMsgs.add("Currency: " + ex);
		}

		withdrawal.setRemarks(strRemarks);

		if (errorMsgs.isEmpty()) {
			long id = moneyTrackService.recordWithdrawal(withdrawal);
			request.setAttribute("id", id);
		}
		else {
			request.setAttribute("errorMsgs", errorMsgs);
		}

		request.setAttribute("saveOperation", true);
		doGet(request, response);
	}

}
