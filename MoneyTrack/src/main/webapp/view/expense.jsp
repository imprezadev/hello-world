<%@ page import="java.util.List" %>
<%@ page import="java.text.SimpleDateFormat" %>

<%@ page import="com.smartware.domain.Expense" %>
<%@ page import="com.smartware.domain.catalog.Currency" %>
<%@ page import="com.smartware.domain.catalog.PaymentType" %>
<%@ page import="com.smartware.domain.catalog.ExpenseCategory" %>

<!DOCTYPE html>
<html>
<head>
	<meta charset="ISO-8859-1">
	<title>Money Track</title>
</head>
<body>
<%
	String date = "";
	String amount = "";
	Expense expense = null;
	if (request.getAttribute("expense") != null) {
		expense = (Expense)request.getAttribute("expense");

		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm");
		date = sdf.format(expense.getDate());

		amount = String.format("%.2f", expense.getAmount());
	}
%>
	<div>
		<div><h1>Expense</h1></div>
		<form method="post" action="Expense">
		<div>
			<div>
				<label>Date</label>
				<input type="text" name="edtDate" value="<%= date %>"/>
			</div>
		</div>
		<div>
			<div>
				<label>Amount</label>
				<input type="text" name="edtAmount" value="<%= amount %>"/>
			</div>
		</div>
		<div>
			<div>
				<label>Currency</label>
				<select name="cbCurrency">
					<option></option>
<%
	Currency[] currencies = (Currency[])request.getAttribute("currencies");
	boolean matchCurrency;
	for (Currency currency: currencies) {
		matchCurrency = (expense != null && expense.getCurrency().equals(currency));
%>
					<option value="<%= currency %>" <%= (matchCurrency) ? "selected" : "" %> ><%= currency %></option>
<%
	}
%>
				</select>
			</div>
		</div>
		<div>
			<div>
				<label>Payment Type</label>
				<select name="cbPaymentType">
					<option></option>
<%
	PaymentType[] paymentTypes = (PaymentType[])request.getAttribute("paymentTypes");
	boolean matchPaymentType;
	for (PaymentType paymentType: paymentTypes) {
		matchPaymentType = (expense != null && expense.getPaymenType().equals(paymentType));
%>
					<option value="<%= paymentType %>" <%= (matchPaymentType) ? "selected" : "" %> ><%= paymentType %></option>
<%
	}
%>
				</select>
			</div>
		</div>
		<div>
			<div>
				<label>Category</label>
				<select name="cbExpenseCategory">
					<option></option>
<%
	ExpenseCategory[] expenseCategories = (ExpenseCategory[])request.getAttribute("expenseCategories");
	boolean matchExpenseCategory;
	for (ExpenseCategory expenseCategory: expenseCategories) {
		matchExpenseCategory = (expense != null && expense.getCategory().equals(expenseCategory));
%>
					<option value="<%= expenseCategory %>" <%= (matchExpenseCategory) ? "selected" : "" %> ><%= expenseCategory %></option>
<%
	}
%>
				</select>
			</div>
		</div>
		<div>
			<div>
				<label>Detail</label>
				<textarea rows="4" cols="40" name="txtDetail"><%= (expense != null) ? expense.getDetail() : "" %></textarea>
			</div>
		</div>
		<div>
			<input type="submit" value="Record Expense" <%= (expense != null) ? "disabled" : "" %>>
		</div>
		</form>
	</div>
<%
	List<String> saveMessages = (List)request.getAttribute("saveMessages");
	
	if (saveMessages != null) {
		out.println("<br>");
		for (String msg: saveMessages) {
			out.println("<span>" + msg + "</span><br>");
		}
	}
%>

</body>
</html>