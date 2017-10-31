<%@ page import="java.util.Date" %>
<%@ page import="java.util.List" %>
<%@ page import="java.text.SimpleDateFormat" %>

<%@ page import="com.smartware.domain.MoneyMovement" %>
<%@ page import="com.smartware.domain.catalog.MoneyMovementOperation" %>

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
    <li><a href="GotSalary">Record Got Salary</a></li>
  </ul>

  <table border="1" width="100%">
    <tr>
      <th>Date</th>
      <th>Time</th>
      <th>Amount</th>
      <th>Currency</th>
      <th>Transaction</th>
    </tr>
<%
	List<MoneyMovement> moneyMovements = (List)request.getAttribute("moneyMovements");
	SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MMM-yyyy");
	SimpleDateFormat timeFormat = new SimpleDateFormat("HH:mm");
	for(MoneyMovement moneyMovement: moneyMovements) {
		Date date = moneyMovement.getDate();
		String operation = moneyMovement.getOperation().name();
		String urlDetail = "?id=" + Long.toString(moneyMovement.getId());
		if (moneyMovement.getOperation().equals(MoneyMovementOperation.EXPENSE)) {
			urlDetail = "Expense" + urlDetail;
		}
		else
		if (moneyMovement.getOperation().equals(MoneyMovementOperation.WITHDRAWAL)) {
			urlDetail = "Withdrawal" + urlDetail;
		}
		else
		if (moneyMovement.getOperation().equals(MoneyMovementOperation.CREDIT_CARD_PAYMENT)) {
			urlDetail = "CreditCardPayment" + urlDetail;
		}
		else
		if (moneyMovement.getOperation().equals(MoneyMovementOperation.GOT_SALARY)) {
			urlDetail = "GotSalary" + urlDetail;
		}
%>
    <tr>
      <td align="center"><%= dateFormat.format(date) %></td>
      <td align="center"><%= timeFormat.format(date) %></td>
      <td align="right"><%= String.format("%.2f", moneyMovement.getAmount()) %></td>
      <td align="center"><%= moneyMovement.getCurrency() %></td>
      <td align="left"><a href="<%= urlDetail %>">Detail: <%= operation %></a></td>
    </tr>
<%
	}
%>
  </table>
</body>
</html>
