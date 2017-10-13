package com.smartware.persistence;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.smartware.common.AppDBHelper;
import com.smartware.domain.BankMovement;
import com.smartware.domain.catalog.BankOperation;
import com.smartware.domain.catalog.Currency;

public class BankMovementDAO {

	private BankMovement populateBankMovement(ResultSet rs) {
		BankMovement bankMovement = new BankMovement();
		try {
			bankMovement.setId(rs.getLong("id"));
			bankMovement.setDate(rs.getTimestamp("date"));
			bankMovement.setAmount(rs.getFloat("amount"));
			bankMovement.setCurrency(Currency.valueOf(rs.getString("currency")));
			bankMovement.setBankOperation(BankOperation.valueOf(rs.getString("operation")));
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
						"SELECT mm.id, mm.date, mm.amount, mm.currency, bm.operation, bm.remarks" +
						"  FROM bank_movement bm" +
						" INNER JOIN money_movement mm ON mm.id = bm.id_money_movement" +
						" WHERE bm.id_money_movement = ?";

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
						"SELECT mm.id, mm.type, mm.date, mm.amount, mm.currency, bm.operation, bm.remarks" +
						"  FROM bank_movement bm" +
						" INNER JOIN money_movement mm ON mm.id = bm.id_money_movement";

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
				String sql = "INSERT INTO bank_movement (id_money_movement, operation, remarks) VALUES (?, ?, ?)";
				st = conn.prepareStatement(sql);
				st.setLong(1, bankMovement.getId());
				st.setString(2, bankMovement.getBankOperation().name());
				st.setString(3, bankMovement.getRemarks());

				st.execute();

			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}

	public void insertBankMovement(long id, BankOperation bankOperation, String remarks) {
		BankMovement bankMovement = new BankMovement();
		bankMovement.setId(id);
		bankMovement.setBankOperation(bankOperation);
		bankMovement.setRemarks(remarks);

		insertBankMovement(bankMovement);
	}

}
