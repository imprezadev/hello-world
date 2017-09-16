<%@ page import="java.util.List" %>
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
	
	<table border="1">
	  <tr>
	    <th>Date</th>
	    <th>Amount</th>
	    <th>Currency</th>
	  </tr>
<%
	List<MoneyMovement> moneyMovements = (List)request.getAttribute("moneyMovements");
	for(MoneyMovement moneyMovement: moneyMovements) {
%>
	  <tr>
	    <td align="left"><% out.print(moneyMovement.getDate()); %></td>
	    <td align="right"><% out.print(moneyMovement.getAmount()); %></td>
	    <td align="center"><% out.print(moneyMovement.getCurrency()); %></td>
	  </tr>
<%
	}
%>
	</table>
</body>
</html>