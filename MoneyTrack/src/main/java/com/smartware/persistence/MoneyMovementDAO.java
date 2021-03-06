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
import com.smartware.domain.catalog.BankOperation;
import com.smartware.domain.catalog.CreditCardOperation;
import com.smartware.domain.catalog.Currency;
import com.smartware.domain.catalog.MoneyMovementOperation;

public class MoneyMovementDAO {

	private MoneyMovement populateMoneyMovement(ResultSet rs) throws Exception {
		return populateMoneyMovement(rs, false);
	}

	private MoneyMovement populateMoneyMovement(ResultSet rs, Boolean operationColumnExists) throws Exception {
		MoneyMovement moneyMovement = new MoneyMovement();
		moneyMovement.setId(rs.getLong("id"));
		moneyMovement.setDate(rs.getTimestamp("date"));
		moneyMovement.setAmount(rs.getFloat("amount"));
		moneyMovement.setCurrency(Currency.valueOf(rs.getString("currency")));
		if (operationColumnExists) moneyMovement.setOperation(MoneyMovementOperation.valueOf(rs.getString("operation")));

		return moneyMovement;
	}

	public MoneyMovement getMoneyMovement(long id) throws Exception {
		MoneyMovement moneyMovement = null;

		Connection conn = null;
		PreparedStatement st = null;
		ResultSet rs = null;
		try {
			conn = AppDBHelper.getMoneyTrackDBConnection();
			st = conn.prepareStatement("SELECT id, date, amount, currency FROM money_movement WHERE id = ?");
			st.setLong(1, id);
			rs = st.executeQuery();

			if (rs.next()) {
				moneyMovement = populateMoneyMovement(rs);
			}
		} catch (SQLException sqlex) {
			String errMsg = sqlex.getMessage();
			throw new Exception(errMsg);
		} catch (Exception ex) {
			String errMsg = ex.getMessage();
			throw new Exception(errMsg);
		} finally {
			AppDBHelper.CloseResutSet(rs);
			AppDBHelper.CloseStatement(st);
			AppDBHelper.CloseConnection(conn);
		}

		return moneyMovement;
	}

	public List<MoneyMovement> getMoneyMovements() throws Exception {
		List<MoneyMovement> moneyMovements = new ArrayList<MoneyMovement>();

		Connection conn = null;
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
			sql.append(" WHERE bm.operation = '").append(BankOperation.WITHDRAWAL.name()).append("' ");
			sql.append("UNION ");
			sql.append("SELECT mm.id, mm.date, mm.amount, mm.currency, '").append(MoneyMovementOperation.CREDIT_CARD_PAYMENT).append("' AS operation ");
			sql.append("  FROM credit_card_movement ccm ");
			sql.append(" INNER JOIN money_movement mm ON ccm.id_money_movement = mm.id ");
			sql.append(" WHERE ccm.operation = '").append(CreditCardOperation.PAYMENT.name()).append("' ");
			sql.append("UNION ");
			sql.append("SELECT mm.id, mm.date, mm.amount, mm.currency, '").append(MoneyMovementOperation.GOT_SALARY).append("' AS operation ");
			sql.append("  FROM bank_movement bm ");
			sql.append(" INNER JOIN money_movement mm ON bm.id_money_movement = mm.id ");
			sql.append(" WHERE bm.operation = '").append(BankOperation.TRANSFER_IN.name()).append("' ");
			sql.append("   AND bm.remarks LIKE 'SALARY %' ");

			conn = AppDBHelper.getMoneyTrackDBConnection();
			st = conn.prepareStatement(sql.toString());
			rs = st.executeQuery();

			MoneyMovement moneyMovement = null;
			while (rs.next()) {
				moneyMovement = populateMoneyMovement(rs, true);
				moneyMovements.add(moneyMovement);
			}
		} finally {
			AppDBHelper.CloseResutSet(rs);
			AppDBHelper.CloseStatement(st);
			AppDBHelper.CloseConnection(conn);
		}

		return moneyMovements;
	}

	public long insertMoneyMovement(MoneyMovement moneyMovement) throws Exception {
		long id = -1;

		Connection conn = null;
		PreparedStatement st = null;
		ResultSet rs = null;
		try {
			String sql = "INSERT INTO money_movement (date, amount, currency) VALUES (?, ?, ?)";
			
			conn = AppDBHelper.getMoneyTrackDBConnection();
			st = conn.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS);
			st.setTimestamp(1, new java.sql.Timestamp(moneyMovement.getDate().getTime()));
			st.setFloat(2, moneyMovement.getAmount());
			st.setString(3, moneyMovement.getCurrency().name());

			st.execute();

			rs = st.getGeneratedKeys();
			if (rs.next()) {
				id = rs.getLong(1);
			}
		} finally {
			AppDBHelper.CloseResutSet(rs);
			AppDBHelper.CloseStatement(st);
			AppDBHelper.CloseConnection(conn);
		}

		return id;
	}

	public long insertMoneyMovement(Date date, Float amount, Currency currency) throws Exception {
		MoneyMovement moneyMovement = new MoneyMovement();
		moneyMovement.setDate(date);
		moneyMovement.setAmount(amount);
		moneyMovement.setCurrency(currency);

		return insertMoneyMovement(moneyMovement);
	}

}
