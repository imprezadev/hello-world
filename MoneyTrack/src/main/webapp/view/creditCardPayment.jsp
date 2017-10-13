<!DOCTYPE html>
<html>
<head>
	<meta charset="ISO-8859-1">
	<title>Money Track</title>
</head>
<body>
  <h1>Credit Card Payment</h1>
  <table>
    <tr>
      <td><label>Date</label></td>
      <td><input type="text" /></td>
    </tr>
    <tr>
      <td><label>Amount</label></td>
      <td><input type="text" /></td>
    </tr>
    <tr>
      <td><label>Currency</label></td>
      <td>
        <select>
          <option value="PEN">PEN</option>
          <option value="USD">USD</option>
        </select>
      </td>
    </tr>
    <tr>
      <td><label>Payment Type</label></td>
      <td>
        <select>
          <option value="CASH">Cash</option>
          <option value="DEBIT">Debit</option>
        </select>
      </td>
    </tr>
    <tr>
      <td><label>Detail</label></td>
      <td><textarea rows="4" cols="40" ></textarea></td>
    </tr>
  </table>
  <button>Record Credit Card Payment</button>
</body>
</html>
