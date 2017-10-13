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
	String edtDate = "";
	String edtAmount = "";
	String cbCurrency = "";
	String cbPaymentType = "";
	String cbExpenseCategory = "";
	String txtDetail = "";

	Boolean afterSaveOperation = request.getAttribute("saveOperation") != null;
	Boolean errorAfterSaveOperation = request.getAttribute("saveOperation") != null && request.getAttribute("errorMsgs") != null;
	Boolean toShowExpense = request.getAttribute("expense") != null; 

	Expense expense = null;
	if (toShowExpense) {
		expense = (Expense)request.getAttribute("expense");

		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm");
		edtDate = sdf.format(expense.getDate());
		edtAmount = String.format("%.2f", expense.getAmount());
		cbCurrency = expense.getCurrency().name();
		cbPaymentType = expense.getPaymentType().name();
		cbExpenseCategory = expense.getCategory().name();
		txtDetail = expense.getDetail();
	}
	else {
		if (errorAfterSaveOperation) {
			edtDate = request.getParameter("edtDate");
			edtAmount = request.getParameter("edtAmount");
			cbCurrency = request.getParameter("cbCurrency");
			cbPaymentType = request.getParameter("cbPaymentType");
			cbExpenseCategory = request.getParameter("cbExpenseCategory");
			txtDetail = request.getParameter("txtDetail");
		}
	}
%>
  <h1>Expense</h1>

  <form method="post" action="Expense">
  <table>
    <tr>
      <td><label>Date</label></td>
      <td><input type="text" name="edtDate" value="<%= edtDate %>" <%= (toShowExpense) ? "disabled" : "" %> /></td>
    </tr>
    <tr>
      <td><label>Amount</label></td>
      <td><input type="text" name="edtAmount" value="<%= edtAmount %>" <%= (toShowExpense) ? "disabled" : "" %> /></td>
    </tr>
    <tr>
      <td><label>Currency</label></td>
      <td>
        <select name="cbCurrency" <%= (toShowExpense) ? "disabled" : "" %>>
          <option></option>
<%
	Currency[] currencies = (Currency[])request.getAttribute("currencies");
	boolean matchCurrency;
	for (Currency currency: currencies) {
		matchCurrency = (cbCurrency != "" && cbCurrency.equals(currency.name()));
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
        <select name="cbPaymentType" <%= (toShowExpense) ? "disabled" : "" %>>
          <option></option>
<%
	PaymentType[] paymentTypes = (PaymentType[])request.getAttribute("paymentTypes");
	boolean matchPaymentType;
	for (PaymentType paymentType: paymentTypes) {
		matchPaymentType = (cbPaymentType != "" && cbPaymentType.equals(paymentType.name()));
%>
          <option value="<%= paymentType %>" <%= (matchPaymentType) ? "selected" : "" %> ><%= paymentType.getName() %></option>
<%
	}
%>
        </select>
      </td>
    </tr>
    <tr>
      <td><label>Category</label></td>
      <td>
        <select name="cbExpenseCategory" <%= (toShowExpense) ? "disabled" : "" %>>
          <option></option>
<%
	ExpenseCategory[] expenseCategories = (ExpenseCategory[])request.getAttribute("expenseCategories");
	boolean matchExpenseCategory;
	for (ExpenseCategory expenseCategory: expenseCategories) {
		matchExpenseCategory = (cbExpenseCategory != "" && cbExpenseCategory.equals(expenseCategory.name()));
%>
          <option value="<%= expenseCategory %>" <%= (matchExpenseCategory) ? "selected" : "" %> ><%= expenseCategory.getName() %></option>
<%
	}
%>
        </select>
      </td>
    </tr>
    <tr>
      <td><label>Detail</label></td>
      <td><textarea rows="4" cols="40" name="txtDetail" <%= (toShowExpense) ? "disabled" : "" %>><%= txtDetail %></textarea></td>
    </tr>
  </table>
<%
	if (!toShowExpense) {
%>
  <input type="submit" value="Record Expense" />
<%
	}
%>
  </form>
  <br>
<%
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
