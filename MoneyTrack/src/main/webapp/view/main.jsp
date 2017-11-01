<%@ page import="java.util.List" %>

<%@ page import="com.smartware.common.Utils" %>

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
    <li><a href="<%= MoneyMovementOperation.EXPENSE.getUrlRoot() %>">Record Expense</a></li>
    <li><a href="<%= MoneyMovementOperation.CREDIT_CARD_PAYMENT.getUrlRoot() %>">Record Credit Card Payment</a></li>
    <li><a href="<%= MoneyMovementOperation.WITHDRAWAL.getUrlRoot() %>">Record Withdrawal</a></li>
    <li><a href="<%= MoneyMovementOperation.GOT_SALARY.getUrlRoot() %>">Record Got Salary</a></li>
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
	for(MoneyMovement moneyMovement: moneyMovements) {
%>
    <tr>
      <td align="center"><%= Utils.getFormattedDate(moneyMovement.getDate()) %></td>
      <td align="center"><%= Utils.getFormattedTime(moneyMovement.getDate()) %></td>
      <td align="right"><%= Utils.getFormattedFloat(moneyMovement.getAmount()) %></td>
      <td align="center"><%= moneyMovement.getCurrency() %></td>
      <td align="left"><a href="<%= moneyMovement.getOperation().getUrlRoot() + "?id=" + Long.toString(moneyMovement.getId()) %>">Detail: <%= moneyMovement.getOperation().name() %></a></td>
    </tr>
<%
	}
%>
  </table>
</body>
</html>
