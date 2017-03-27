package com.smartware.persistence;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.smartware.common.AppDBHelper;
import com.smartware.domain.BankMovement;
import com.smartware.domain.CreditCardMovement;
import com.smartware.domain.catalog.BankOperation;
import com.smartware.domain.catalog.CreditCardOperation;
import com.smartware.domain.catalog.Currency;
import com.smartware.domain.catalog.TransactionType;

public class BankMovementDAO {

	private BankMovement populateBankMovement(ResultSet rs) {
		BankMovement bankMovement = new BankMovement();
		try {
			bankMovement.setId(rs.getLong("id"));
			bankMovement.setType(TransactionType.valueOf(rs.getString("type")));
			bankMovement.setDate(rs.getTimestamp("date"));
			bankMovement.setAmount(rs.getFloat("amount"));
			bankMovement.setCurrency(Currency.valueOf(rs.getString("currency")));
			bankMovement.setOperation(BankOperation.valueOf(rs.getString("operation")));
			bankMovement.setRemarks(rs.getString("remarks"));
		}
		catch (Exception e) {
			e.printStackTrace();
			bankMovement = null;
		}

		return bankMovement;
	}

	public BankMovement getBankMovement(long id) {
		BankMovement bankMovement = null;

		AppDBHelper appDBHelper = new AppDBHelper();

		Connection conn = appDBHelper.getMoneyTrackDBConnection();
		if (conn != null) {
			PreparedStatement st = null;
			ResultSet rs = null;
			try {
				String sql = 
						"SELECT t.id, t.type, t.date, t.amount, t.currency, bm.operation, bm.remarks" +
						"  FROM bank_movement bm" +
						" INNER JOIN transaction t ON t.id = bm.id_transaction" +
						" WHERE bm.id_transaction = ?";

				st = conn.prepareStatement(sql);
				st.setLong(1, id);
				rs = st.executeQuery();

				if (rs.next()) {
					bankMovement = populateBankMovement(rs);
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}

		return bankMovement;
	}

	public List<BankMovement> getBankMovements() {
		List<BankMovement> bankMovements = new ArrayList<BankMovement>();

		AppDBHelper appDBHelper = new AppDBHelper();

		Connection conn = appDBHelper.getMoneyTrackDBConnection();
		if (conn != null) {
			PreparedStatement st = null;
			ResultSet rs = null;
			try {
				String sql = 
						"SELECT t.id, t.type, t.date, t.amount, t.currency, bm.operation, bm.remarks" +
						"  FROM bank_movement bm" +
						" INNER JOIN transaction t ON t.id = bm.id_transaction";

				st = conn.prepareStatement(sql);
				rs = st.executeQuery();

				BankMovement bankMovement = null;
				while (rs.next()) {
					bankMovement = populateBankMovement(rs);
					bankMovements.add(bankMovement);
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}

		return bankMovements;
	}

	public void insertBankMovement(BankMovement bankMovement) {
		AppDBHelper appDBHelper = new AppDBHelper();

		Connection conn = appDBHelper.getMoneyTrackDBConnection();
		if (conn != null) {
			PreparedStatement st = null;
			try {
				String sql = "INSERT INTO bank_movement (id_transaction, operation, remarks) VALUES (?, ?, ?)";
				st = conn.prepareStatement(sql);
				st.setLong(1, bankMovement.getId());
				st.setString(2, bankMovement.getOperation().name());
				st.setString(3, bankMovement.getRemarks());

				st.execute();

			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}

}
