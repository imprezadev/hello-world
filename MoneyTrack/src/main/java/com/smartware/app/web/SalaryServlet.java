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
import com.smartware.domain.GotSalary;
import com.smartware.service.MoneyTrackService;

public class SalaryServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	MoneyTrackService moneyTrackService = new MoneyTrackService();

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String strDate   = (request.getParameter("edtDate") != null)   ? request.getParameter("edtDate")   : "";
		String strAmount = (request.getParameter("edtAmount") != null) ? request.getParameter("edtAmount") : "";
		String strRemarks = (request.getParameter("txtRemarks") != null) ? request.getParameter("txtRemarks") : "";

		Boolean editable = true;

		if (request.getAttribute("id") != null || request.getParameter("id") != null) {
			long gotSalaryId = (request.getParameter("id") != null) ? Long.valueOf(request.getParameter("id")) : (Long)request.getAttribute("id");

			GotSalary gotSalary = moneyTrackService.getGotSalary(gotSalaryId);

			strDate = Utils.getFormattedDateTime(gotSalary.getDate());
			strAmount = Utils.getFormattedFloat(gotSalary.getAmount());
			strRemarks = gotSalary.getRemarks();

			editable = false;
		}

		request.setAttribute("strDate", strDate);
		request.setAttribute("strAmount", strAmount);
		request.setAttribute("strRemarks", strRemarks);

		request.setAttribute("editable", editable);

		RequestDispatcher view = request.getRequestDispatcher("view/salary.jsp");
		view.forward(request, response); 
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String strDate        = request.getParameter("edtDate");
		String strAmount      = request.getParameter("edtAmount");
		String strRemarks     = request.getParameter("txtRemarks");

		List<String> errorMsgs = new ArrayList<String>();

		GotSalary gotSalary = new GotSalary();

		if (Utils.isValidDateTimeString(strDate)) {
			gotSalary.setDate(Utils.getDateTimeFromString(strDate));
		}
		else {
			errorMsgs.add("Date: Invalid Date, format should be '" + Utils.DATE_TIME_DEFAULT_FORMAT + "'.");
		}

		if (Utils.isValidFloatNumberString(strAmount)) {
			gotSalary.setAmount(Utils.getFloatNumberFromString(strAmount));
		}
		else
			errorMsgs.add("Amount: It should be a decimal number.");

		gotSalary.setRemarks(strRemarks);

		if (errorMsgs.isEmpty()) {
			long id = moneyTrackService.recordGotSalary(gotSalary);
			request.setAttribute("id", id);
		}
		else {
			request.setAttribute("errorMsgs", errorMsgs);
		}

		request.setAttribute("saveOperation", true);
		doGet(request, response);
	}

}
