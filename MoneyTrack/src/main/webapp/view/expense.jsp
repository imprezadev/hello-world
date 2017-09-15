<!DOCTYPE html>
<html>
<head>
	<meta charset="ISO-8859-1">
	<title>Money Track</title>
</head>
<body>
	<div>
		<div><h1>Expense</h1></div>
		<div>
			<div>
				<label>Date</label>
				<input type="text" />
			</div>
		</div>
		<div>
			<div>
				<label>Amount</label>
				<input type="text" />
			</div>
		</div>
		<div>
			<div>
				<label>Currency</label>
				<select>
					<option value="PEN">PEN</option>
					<option value="USD">USD</option>
				</select>
			</div>
		</div>
		<div>
			<div>
				<label>Payment Type</label>
				<select>
					<option value="CASH">Cash</option>
					<option value="DEBIT">Debit</option>
					<option value="CREDIT">Credit Card</option>
				</select>
			</div>
		</div>
		<div>
			<div>
				<label>Category</label>
				<input list="categories" />
				<datalist id="categories">
					<option value="Commute">
					<option value="Lunch">
					<option value="Fun">
					<option value="Fun-Taste">
					<option value="Diego School">
				</datalist>
			</div>
		</div>
		<div>
			<div>
				<label>Detail</label>
				<textarea rows="4" cols="40" ></textarea>
			</div>
		</div>
		<div>
			<button>Record Expense</button>
		</div>
	</div>
</body>
</html>