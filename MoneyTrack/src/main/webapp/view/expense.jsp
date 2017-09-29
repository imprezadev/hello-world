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
				<input type="text" name="edtDate" value="<% out.print(date); %>"/>
			</div>
		</div>
		<div>
			<div>
				<label>Amount</label>
				<input type="text" name="edtAmount" value="<% out.print(amount); %>"/>
			</div>
		</div>
		<div>
			<div>
				<label>Currency</label>
				<select name="cbCurrency">
					<option></option>
					<option value="PEN" <% if (expense != null && "PEN".equals(expense.getCurrency().name())) out.print("selected"); %> >PEN</option>
					<option value="USD" <% if (expense != null && "USD".equals(expense.getCurrency().name())) out.print("selected"); %> >USD</option>
				</select>
			</div>
		</div>
		<div>
			<div>
				<label>Payment Type</label>
				<select name="cbPaymentType">
					<option></option>
					<option value="CASH" <% if (expense != null && "CASH".equals(expense.getPaymenType().name())) out.print("selected"); %> >Cash</option>
					<option value="DEBIT" <% if (expense != null && "DEBIT".equals(expense.getPaymenType().name())) out.print("selected"); %> >Debit</option>
					<option value="CREDIT" <% if (expense != null && "CREDIT".equals(expense.getPaymenType().name())) out.print("selected"); %> >Credit Card</option>
				</select>
			</div>
		</div>
		<div>
			<div>
				<label>Category</label>
				<select name="cbExpenseCategory">
					<option></option>
					<option value="COMMUTE" <% if (expense != null && "COMMUTE".equals(expense.getCategory().name())) out.print("selected"); %> >Commute</option>
					<option value="LUNCH" <% if (expense != null && "LUNCH".equals(expense.getCategory().name())) out.print("selected"); %> >Lunch</option>
					<option value="FUN" <% if (expense != null && "FUN".equals(expense.getCategory().name())) out.print("selected"); %> >fun</option>
					<option value="FUN_TASTE"  <% if (expense != null && "FUN_TASTE".equals(expense.getCategory().name())) out.print("selected"); %> >Fun Taste</option>
					<option value="DIEGO_SCHOOL" <% if (expense != null && "DIEGO_SCHOOL".equals(expense.getCategory().name())) out.print("selected"); %> >Diego School</option>
				</select>
			</div>
		</div>
		<div>
			<div>
				<label>Detail</label>
				<textarea rows="4" cols="40" name="txtDetail"><% if (expense != null) out.print(expense.getDetail()); %></textarea>
			</div>
		</div>
		<div>
			<input type="submit" value="Record Expense" <% if (expense != null) out.print("disabled"); %>>
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