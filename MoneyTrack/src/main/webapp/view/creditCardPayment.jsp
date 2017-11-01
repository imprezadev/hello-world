<%@ page import="java.util.List" %>

<%@ page import="com.smartware.domain.catalog.Currency" %>
<%@ page import="com.smartware.domain.catalog.CreditCardPaymentType" %>
<%@ page import="com.smartware.domain.catalog.MoneyMovementOperation" %>

<!DOCTYPE html>
<html>
<head>
	<meta charset="ISO-8859-1">
	<title>Money Track</title>
</head>
<body>
  <a href="Main">Back to main</a>
  <h1>Credit Card Payment</h1>
  <form method="post" action="<%= MoneyMovementOperation.CREDIT_CARD_PAYMENT.getUrlRoot() %>">
  <table>
    <tr>
      <td><label>Date</label></td>
      <td><input type="text" name="edtDate" value="<%= request.getAttribute("strDate") %>" <%= (Boolean)request.getAttribute("editable") ? "" : "readonly" %> /></td>
    </tr>
    <tr>
      <td><label>Amount</label></td>
      <td><input type="text" name="edtAmount" value="<%= request.getAttribute("strAmount") %>" <%= (Boolean)request.getAttribute("editable") ? "" : "readonly" %> /></td>
    </tr>
    <tr>
      <td><label>Currency</label></td>
      <td>
        <select name="cbCurrency" <%= (Boolean)request.getAttribute("editable") ? "" : "disabled" %> >
          <option></option>
<%
  String strCurrency = (String)request.getAttribute("strCurrency");
  Currency[] currencies = (Currency[])request.getAttribute("currencies");
  boolean matchCurrency;
  for (Currency currency: currencies) {
    matchCurrency = (strCurrency != "" && strCurrency.equals(currency.name()));
%>
          <option value="<%= currency %>" <%= (matchCurrency) ? "selected" : "" %> ><%= currency.getName() %></option>
<%
  }
%>
        </select>
      </td>
    </tr>
    <tr>
      <td><label>Payment Type</label></td>
      <td>
        <select name="cbPaymentType" <%= (Boolean)request.getAttribute("editable") ? "" : "disabled" %> >
          <option></option>
<%
  String strPaymentType = (String)request.getAttribute("strPaymentType");
  CreditCardPaymentType[] paymentTypes = (CreditCardPaymentType[])request.getAttribute("paymentTypes");
  boolean matchPaymentType;
  for (CreditCardPaymentType paymentType: paymentTypes) {
    matchPaymentType = (strPaymentType != "" && strPaymentType.equals(paymentType.name()));
%>
          <option value="<%= paymentType %>" <%= (matchPaymentType) ? "selected" : "" %> ><%= paymentType.getName() %></option>
<%
  }
%>
        </select>
      </td>
    </tr>
    <tr>
      <td><label>Remarks</label></td>
      <td><textarea rows="4" cols="40" name="txtRemarks" <%= (Boolean)request.getAttribute("editable") ? "" : "readonly" %> ><%= request.getAttribute("strRemarks") %></textarea></td>
    </tr>
  </table>
<%
  if ((Boolean)request.getAttribute("editable")) {
%>
  <input type="submit" value="Record Credit Card Payment" />
<%
  }
%>
  </form>
<%
  Boolean afterSaveOperation = request.getAttribute("saveOperation") != null;
  Boolean errorAfterSaveOperation = request.getAttribute("saveOperation") != null && request.getAttribute("errorMsgs") != null;

  if (afterSaveOperation) {
    if (errorAfterSaveOperation) {
      List<String> errorMsgs = (List)request.getAttribute("errorMsgs");
      for (String errorMsg: errorMsgs) {
%>
  <li style="color:red" ><%= errorMsg %></li>
<%
      }
    }
    else {
%>
  <span style="color:blue" >Saved!!</span>
<%
    }
  }
%>

</body>
</html>
