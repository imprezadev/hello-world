<%@ page import="java.util.Date" %>
<%@ page import="java.util.List" %>
<%@ page import="java.text.SimpleDateFormat" %>
<%@ page import="com.smartware.domain.MoneyMovement" %>

<!DOCTYPE html>
<html>
<head>
	<meta charset="ISO-8859-1">
	<title>Money Track</title>
</head>
<body>
	<ul>
	  <li><a href="Expense">Record Expense</a></li>
	  <li><a href="CreditCardPayment">Record Credit Card Payment</a></li>
	  <li><a href="Withdrawal">Record Withdrawal</a></li>
	  <li><a href="Salary">Record Salary</a></li>
	</ul>
	
	<table border="1" width="100%">
	  <tr>
	    <th>Date</th>
	    <th>Time</th>
	    <th>Amount</th>
	    <th>Currency</th>
	  </tr>
<%
	List<MoneyMovement> moneyMovements = (List)request.getAttribute("moneyMovements");
	SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MMM");
	SimpleDateFormat timeFormat = new SimpleDateFormat("HH:mm");
	for(MoneyMovement moneyMovement: moneyMovements) {
		Date date = moneyMovement.getDate();
%>
	  <tr>
	    <td align="center"><% out.print(dateFormat.format(date)); %></td>
	    <td align="center"><% out.print(timeFormat.format(date)); %></td>
	    <td align="right"><% out.print(String.format("%.2f", moneyMovement.getAmount())); %></td>
	    <td align="center"><% out.print(moneyMovement.getCurrency()); %></td>
	  </tr>
<%
	}
%>
	</table>
</body>
</html>