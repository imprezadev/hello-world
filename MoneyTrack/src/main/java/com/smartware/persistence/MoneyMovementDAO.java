package com.smartware.persistence;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.smartware.common.AppDBHelper;
import com.smartware.domain.MoneyMovement;
import com.smartware.domain.catalog.Currency;
import com.smartware.domain.catalog.TransactionType;

public class MoneyMovementDAO {

	public MoneyMovement getMoneyMovement(long id) {
		MoneyMovement moneyMovement = null;
		
		AppDBHelper appDBHelper = new AppDBHelper();
		
		Connection conn = appDBHelper.getMoneyTrackDBConnection();
		if (conn != null) {
			PreparedStatement st = null;
			ResultSet rs = null;
			try {
				st = conn.prepareStatement("SELECT id, type, date, amount, currency FROM money_movement WHERE id = ?");
				st.setLong(1, id);
				rs = st.executeQuery();

				if (rs.next()) {
					moneyMovement = new MoneyMovement();
					moneyMovement.setId(rs.getLong("id"));
					moneyMovement.setType(TransactionType.valueOf(rs.getString("type")));
					moneyMovement.setDate(rs.getTimestamp("date"));
					moneyMovement.setAmount(rs.getFloat("amount"));
					moneyMovement.setCurrency(Currency.valueOf(rs.getString("currency")));
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}

		return moneyMovement;
	}

	public List<MoneyMovement> getMoneyMovements() {
		List<MoneyMovement> moneyMovements = new ArrayList<MoneyMovement>();

		AppDBHelper appDBHelper = new AppDBHelper();
		Connection conn = appDBHelper.getMoneyTrackDBConnection();
		if (conn != null) {
			PreparedStatement st = null;
			ResultSet rs = null;
			try {
				st = conn.prepareStatement("SELECT id, type, date, amount, currency FROM money_movement");
				rs = st.executeQuery();

				MoneyMovement moneyMovement = null;
				while (rs.next()) {
					moneyMovement = new MoneyMovement();
					moneyMovement.setId(rs.getLong("id"));
					moneyMovement.setType(TransactionType.valueOf(rs.getString("type")));
					moneyMovement.setDate(rs.getTimestamp("date"));
					moneyMovement.setAmount(rs.getFloat("amount"));
					moneyMovement.setCurrency(Currency.valueOf(rs.getString("currency")));
					
					moneyMovements.add(moneyMovement);
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}

		return moneyMovements;
	}

	public long insertMoneyMovement(MoneyMovement moneyMovement) {
		long id = -1;

		AppDBHelper appDBHelper = new AppDBHelper();
		Connection conn = appDBHelper.getMoneyTrackDBConnection();
		if (conn != null) {
			PreparedStatement st = null;
			try {
				String sql = "INSERT INTO money_movement (type, date, amount, currency) VALUES (?, ?, ?, ?)";
				st = conn.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS);
				st.setString(1, moneyMovement.getType().name());
				st.setTimestamp(2, new java.sql.Timestamp(moneyMovement.getDate().getTime()));
				st.setFloat(3, moneyMovement.getAmount());
				st.setString(4, moneyMovement.getCurrency().name());

				st.execute();

				ResultSet rs = st.getGeneratedKeys();
				if (rs.next()) {
					id = rs.getLong(1);
				}

			} catch (SQLException e) {
				e.printStackTrace();
			}
		}

		return id;
	}

}
