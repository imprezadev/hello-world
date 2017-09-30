<%@ page import="java.util.List" %>
<%@ page import="java.text.SimpleDateFormat" %>


<%@ page import="com.smartware.domain.Expense" %>

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
					<option value="PEN" <%= (expense != null && "PEN".equals(expense.getCurrency().name())) ? "selected" : "" %> >PEN</option>
					<option value="USD" <%= (expense != null && "USD".equals(expense.getCurrency().name())) ? "selected" : "" %> >USD</option>
				</select>
			</div>
		</div>
		<div>
			<div>
				<label>Payment Type</label>
				<select name="cbPaymentType">
					<option></option>
					<option value="CASH" <%=(expense != null && "CASH".equals(expense.getPaymenType().name())) ? "selected" : "" %> >Cash</option>
					<option value="DEBIT" <%= (expense != null && "DEBIT".equals(expense.getPaymenType().name())) ? "selected" : "" %> >Debit</option>
					<option value="CREDIT" <%= (expense != null && "CREDIT".equals(expense.getPaymenType().name())) ? "selected" : "" %> >Credit Card</option>
				</select>
			</div>
		</div>
		<div>
			<div>
				<label>Category</label>
				<select name="cbExpenseCategory">
					<option></option>
					<option value="COMMUTE" <%= (expense != null && "COMMUTE".equals(expense.getCategory().name())) ? "selected" : "" %> >Commute</option>
					<option value="LUNCH" <%= (expense != null && "LUNCH".equals(expense.getCategory().name())) ? "selected" : "" %> >Lunch</option>
					<option value="FUN" <%= (expense != null && "FUN".equals(expense.getCategory().name())) ? "selected" : "" %> >fun</option>
					<option value="FUN_TASTE"  <%= (expense != null && "FUN_TASTE".equals(expense.getCategory().name())) ? "selected" : "" %> >Fun Taste</option>
					<option value="DIEGO_SCHOOL" <%= (expense != null && "DIEGO_SCHOOL".equals(expense.getCategory().name())) ? "selected" : "" %> >Diego School</option>
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