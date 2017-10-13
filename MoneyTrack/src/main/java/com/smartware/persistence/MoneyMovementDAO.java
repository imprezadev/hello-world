package com.smartware.persistence;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.smartware.common.AppDBHelper;
import com.smartware.domain.MoneyMovement;
import com.smartware.domain.catalog.Currency;
import com.smartware.domain.catalog.MoneyMovementOperation;

public class MoneyMovementDAO {

	private MoneyMovement populateMoneyMovement(ResultSet rs) {
		return populateMoneyMovement(rs, false);
	}

	private MoneyMovement populateMoneyMovement(ResultSet rs, Boolean operationColumnExists) {
		MoneyMovement moneyMovement = new MoneyMovement();
		try {
			moneyMovement.setId(rs.getLong("id"));
			moneyMovement.setDate(rs.getTimestamp("date"));
			moneyMovement.setAmount(rs.getFloat("amount"));
			moneyMovement.setCurrency(Currency.valueOf(rs.getString("currency")));
			if (operationColumnExists) moneyMovement.setOperation(MoneyMovementOperation.valueOf(rs.getString("operation")));
		} catch (Exception e) {
			e.printStackTrace();
			moneyMovement = null;
		}
		return moneyMovement;
	}

	public MoneyMovement getMoneyMovement(long id) {
		MoneyMovement moneyMovement = null;
		
		AppDBHelper appDBHelper = new AppDBHelper();
		
		Connection conn = appDBHelper.getMoneyTrackDBConnection();
		if (conn != null) {
			PreparedStatement st = null;
			ResultSet rs = null;
			try {
				st = conn.prepareStatement("SELECT id, date, amount, currency FROM money_movement WHERE id = ?");
				st.setLong(1, id);
				rs = st.executeQuery();

				if (rs.next()) {
					moneyMovement = populateMoneyMovement(rs);
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
				StringBuilder sql = new StringBuilder();
				sql.append("SELECT mm.id, mm.date, mm.amount, mm.currency, '").append(MoneyMovementOperation.EXPENSE).append("' AS operation ");
				sql.append("  FROM expense e ");
				sql.append(" INNER JOIN money_movement mm ON e.id_money_movement = mm.id ");
				sql.append("UNION ");
				sql.append("SELECT mm.id, mm.date, mm.amount, mm.currency, '").append(MoneyMovementOperation.WITHDRAWAL).append("' AS operation ");
				sql.append("  FROM bank_movement bm ");
				sql.append(" INNER JOIN money_movement mm ON bm.id_money_movement = mm.id ");
				sql.append(" WHERE bm.operation = 'WITHDRAWAL' ");
				sql.append("UNION ");
				sql.append("SELECT mm.id, mm.date, mm.amount, mm.currency, '").append(MoneyMovementOperation.CREDIT_CARD_PAYMENT).append("' AS operation ");
				sql.append("  FROM credit_card_movement ccm ");
				sql.append(" INNER JOIN money_movement mm ON ccm.id_money_movement = mm.id ");
				sql.append(" WHERE ccm.operation = 'PAYMENT' ");
				sql.append("UNION ");
				sql.append("SELECT mm.id, mm.date, mm.amount, mm.currency, '").append(MoneyMovementOperation.GOT_SALARY).append("' AS operation ");
				sql.append("  FROM bank_movement bm ");
				sql.append(" INNER JOIN money_movement mm ON bm.id_money_movement = mm.id ");
				sql.append(" WHERE bm.operation = 'TRANSFER_IN' ");
				sql.append("   AND bm.remarks LIKE 'SALARY %' ");

				st = conn.prepareStatement(sql.toString());
				rs = st.executeQuery();

				MoneyMovement moneyMovement = null;
				while (rs.next()) {
					moneyMovement = populateMoneyMovement(rs, true);
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
				String sql = "INSERT INTO money_movement (date, amount, currency) VALUES (?, ?, ?)";
				st = conn.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS);
				st.setTimestamp(1, new java.sql.Timestamp(moneyMovement.getDate().getTime()));
				st.setFloat(2, moneyMovement.getAmount());
				st.setString(3, moneyMovement.getCurrency().name());

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

	public long insertMoneyMovement(Date date, Float amount, Currency currency) {
		MoneyMovement moneyMovement = new MoneyMovement();
		moneyMovement.setDate(date);
		moneyMovement.setAmount(amount);
		moneyMovement.setCurrency(currency);

		return insertMoneyMovement(moneyMovement);
	}

}
