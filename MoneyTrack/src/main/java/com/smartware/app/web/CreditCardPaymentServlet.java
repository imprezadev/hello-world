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
import com.smartware.domain.CreditCardPayment;
import com.smartware.domain.catalog.CreditCardPaymentType;
import com.smartware.domain.catalog.Currency;
import com.smartware.service.MoneyTrackService;

public class CreditCardPaymentServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	MoneyTrackService moneyTrackService = new MoneyTrackService();

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String strDate        = (request.getParameter("edtDate") != null)       ? request.getParameter("edtDate")       : "";
		String strAmount      = (request.getParameter("edtAmount") != null)     ? request.getParameter("edtAmount")     : "";
		String strCurrency    = (request.getParameter("cbCurrency") != null)    ? request.getParameter("cbCurrency")    : "";
		String strPaymentType = (request.getParameter("cbPaymentType") != null) ? request.getParameter("cbPaymentType") : "";
		String strRemarks     = (request.getParameter("txtRemarks") != null)    ? request.getParameter("txtRemarks")    : "";

		Boolean editable = true;

		Currency[] currencies = moneyTrackService.getCurrencies();
		request.setAttribute("currencies", currencies);

		CreditCardPaymentType[] paymentTypes = moneyTrackService.getCreditCardPaymentTypes();
		request.setAttribute("paymentTypes", paymentTypes);

		if (request.getAttribute("id") != null || request.getParameter("id") != null) {
			long creditCardPaymentId = (request.getParameter("id") != null) ? Long.valueOf(request.getParameter("id")) : (Long)request.getAttribute("id");

			CreditCardPayment creditCardPayment = moneyTrackService.getCreditCardPayment(creditCardPaymentId);

			strDate = Utils.getFormattedDate(creditCardPayment.getDate());
			strAmount = Utils.getFormattedFloat(creditCardPayment.getAmount());
			strCurrency = creditCardPayment.getCurrency().name();
			strPaymentType = creditCardPayment.getPaymentType().name();
			strRemarks = creditCardPayment.getRemarks();

			editable = false;
		}

		request.setAttribute("strDate", strDate);
		request.setAttribute("strAmount", strAmount);
		request.setAttribute("strCurrency", strCurrency);
		request.setAttribute("strPaymentType", strPaymentType);
		request.setAttribute("strRemarks", strRemarks);

		request.setAttribute("editable", editable);

		RequestDispatcher view = request.getRequestDispatcher("view/creditCardPayment.jsp");
		view.forward(request, response); 
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String strDate        = request.getParameter("edtDate");
		String strAmount      = request.getParameter("edtAmount");
		String strCurrency    = request.getParameter("cbCurrency");
		String strPaymentType = request.getParameter("cbPaymentType");
		String strRemarks     = request.getParameter("txtRemarks");

		List<String> errorMsgs = new ArrayList<String>();

		CreditCardPayment creditCardPayment = new CreditCardPayment();

		if (Utils.isValidDateTimeString(strDate)) {
			creditCardPayment.setDate(Utils.getDateTimeFromString(strDate));
		}
		else {
			errorMsgs.add("Date: Invalid Date, format should be '" + Utils.DATE_TIME_DEFAULT_FORMAT + "'.");
		}

		if (Utils.isValidFloatNumberString(strAmount)) {
			creditCardPayment.setAmount(Utils.getFloatNumberFromString(strAmount));
		}
		else
			errorMsgs.add("Amount: It should be a decimal number.");

		Currency currency = null;
		try {
			currency = Currency.valueOf(strCurrency);
			creditCardPayment.setCurrency(currency);
		}
		catch (Exception ex) {
			errorMsgs.add("Currency: " + ex);
		}

		CreditCardPaymentType paymentType = null;
		try {
			paymentType = CreditCardPaymentType.valueOf(strPaymentType);
			creditCardPayment.setPaymentType(paymentType);
		}
		catch (Exception ex) {
			errorMsgs.add("Payment Type: " + ex);
		}

		creditCardPayment.setRemarks(strRemarks);

		if (errorMsgs.isEmpty()) {
			long id = moneyTrackService.recordCreditCardPayment(creditCardPayment);
			request.setAttribute("id", id);
		}
		else {
			request.setAttribute("errorMsgs", errorMsgs);
		}

		request.setAttribute("saveOperation", true);
		doGet(request, response);
	}
}
