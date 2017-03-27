package com.smartware.persistence;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.smartware.common.AppDBHelper;
import com.smartware.domain.CreditCardMovement;
import com.smartware.domain.catalog.CreditCardOperation;
import com.smartware.domain.catalog.Currency;
import com.smartware.domain.catalog.TransactionType;

public class CreditCardMovementDAO {
	
	private CreditCardMovement populateCreditCardMovement(ResultSet rs) {
		CreditCardMovement creditCardMovement = new CreditCardMovement();
		try {
			creditCardMovement.setId(rs.getLong("id"));
			creditCardMovement.setType(TransactionType.valueOf(rs.getString("type")));
			creditCardMovement.setDate(rs.getTimestamp("date"));
			creditCardMovement.setAmount(rs.getFloat("amount"));
			creditCardMovement.setCurrency(Currency.valueOf(rs.getString("currency")));
			creditCardMovement.setOperation(CreditCardOperation.valueOf(rs.getString("operation")));
			creditCardMovement.setRemarks(rs.getString("remarks"));
		}
		catch (Exception e) {
			e.printStackTrace();
			creditCardMovement = null;
		}

		return creditCardMovement;
	}

	public CreditCardMovement getCreditCardMovement(long id) {
		CreditCardMovement creditCardMovement = null;

		AppDBHelper appDBHelper = new AppDBHelper();

		Connection conn = appDBHelper.getMoneyTrackDBConnection();
		if (conn != null) {
			PreparedStatement st = null;
			ResultSet rs = null;
			try {
				String sql = 
						"SELECT t.id, t.type, t.date, t.amount, t.currency, ccm.operation, ccm.remarks" +
						"  FROM credit_card_movement ccm" +
						" INNER JOIN transaction t ON t.id = ccm.id_transaction" +
						" WHERE ccm.id_transaction = ?";

				st = conn.prepareStatement(sql);
				st.setLong(1, id);
				rs = st.executeQuery();

				if (rs.next()) {
					creditCardMovement = populateCreditCardMovement(rs);
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}

		return creditCardMovement;
	}

	public List<CreditCardMovement> getCreditCardMovements() {
		List<CreditCardMovement> creditCardMovements = new ArrayList<CreditCardMovement>();

		AppDBHelper appDBHelper = new AppDBHelper();

		Connection conn = appDBHelper.getMoneyTrackDBConnection();
		if (conn != null) {
			PreparedStatement st = null;
			ResultSet rs = null;
			try {
				String sql = 
						"SELECT t.id, t.type, t.date, t.amount, t.currency, ccm.operation, ccm.remarks" +
						"  FROM credit_card_movement ccm" +
						" INNER JOIN transaction t ON t.id = ccm.id_transaction";

				st = conn.prepareStatement(sql);
				rs = st.executeQuery();

				CreditCardMovement creditCardMovement = null;
				while (rs.next()) {
					creditCardMovement = populateCreditCardMovement(rs);
					creditCardMovements.add(creditCardMovement);
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}

		return creditCardMovements;
	}

	public void insertCreditCardMovement(CreditCardMovement creditCardMovement) {
		AppDBHelper appDBHelper = new AppDBHelper();

		Connection conn = appDBHelper.getMoneyTrackDBConnection();
		if (conn != null) {
			PreparedStatement st = null;
			try {
				String sql = "INSERT INTO credit_card_movement (id_transaction, operation, remarks) VALUES (?, ?, ?)";
				st = conn.prepareStatement(sql);
				st.setLong(1, creditCardMovement.getId());
				st.setString(2, creditCardMovement.getOperation().name());
				st.setString(3, creditCardMovement.getRemarks());

				st.execute();

			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}

}