<%@ page import="java.util.List" %>

<%@ page import="com.smartware.domain.catalog.MoneyMovementOperation" %>

<!DOCTYPE html>
<html>
<head>
  <meta charset="ISO-8859-1">
  <title>Money Track</title>
</head>
<body>
  <h1>Got Salary</h1>
  <form method="post" action="<%= MoneyMovementOperation.GOT_SALARY.getUrlRoot() %>">
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
      <td><label>Remarks</label></td>
      <td><textarea name="txtRemarks" rows="4" cols="40" <%= (Boolean)request.getAttribute("editable") ? "" : "readonly" %> ><%= request.getAttribute("strRemarks") %></textarea></td>
    </tr>
  </table>
<%
  if ((Boolean)request.getAttribute("editable")) {
%>
  <input type="submit" value="Record Got Salary" />
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
