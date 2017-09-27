<%@ page import="java.util.List" %>
<!DOCTYPE html>
<html>
<head>
	<meta charset="ISO-8859-1">
	<title>Money Track</title>
</head>
<body>
	<div>
		<div><h1>Expense</h1></div>
		<form method="post" action="Expense">
		<div>
			<div>
				<label>Date</label>
				<input type="text" name="edtDate" />
			</div>
		</div>
		<div>
			<div>
				<label>Amount</label>
				<input type="text" name="edtAmount" />
			</div>
		</div>
		<div>
			<div>
				<label>Currency</label>
				<select name="cbCurrency">
					<option value="PEN">PEN</option>
					<option value="USD">USD</option>
				</select>
			</div>
		</div>
		<div>
			<div>
				<label>Payment Type</label>
				<select name="cbPaymentType">
					<option value="CASH">Cash</option>
					<option value="DEBIT">Debit</option>
					<option value="CREDIT">Credit Card</option>
				</select>
			</div>
		</div>
		<div>
			<div>
				<label>Category</label>
				<select name="cbExpenseCategory">
					<option value="COMMUTE">Commute</option>
					<option value="LUNCH">Lunch</option>
					<option value="FUN">fun</option>
					<option value="FUN_TASTE">Fun Taste</option>
					<option value="DIEGO_SCHOOL">Diego School</option>
				</select>
			</div>
		</div>
		<div>
			<div>
				<label>Detail</label>
				<textarea rows="4" cols="40" name="txtDetail"></textarea>
			</div>
		</div>
		<div>
			<input type="submit" value="Record Expense">
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