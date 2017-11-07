package com.smartware.persistence;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.smartware.common.AppDBHelper;
import com.smartware.domain.BankMovement;
import com.smartware.domain.GotSalary;
import com.smartware.domain.Withdrawal;
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

	private GotSalary populateGotSalary(ResultSet rs) {
		GotSalary gotSalary = new GotSalary();
		try {
			gotSalary.setId(rs.getLong("id"));
			gotSalary.setDate(rs.getTimestamp("date"));
			gotSalary.setAmount(rs.getFloat("amount"));
			gotSalary.setCurrency(Currency.valueOf(rs.getString("currency")));
			gotSalary.setRemarks(rs.getString("remarks"));
		}
		catch (Exception e) {
			e.printStackTrace();
			gotSalary = null;
		}

		return gotSalary;
	}

	private Withdrawal populateWithdrawal(ResultSet rs) {
		Withdrawal withdrawal = new Withdrawal();
		try {
			withdrawal.setId(rs.getLong("id"));
			withdrawal.setDate(rs.getTimestamp("date"));
			withdrawal.setAmount(rs.getFloat("amount"));
			withdrawal.setCurrency(Currency.valueOf(rs.getString("currency")));
			withdrawal.setRemarks(rs.getString("remarks"));
		}
		catch (Exception e) {
			e.printStackTrace();
			withdrawal = null;
		}

		return withdrawal;
	}

	public BankMovement getBankMovement(long id) {
		BankMovement bankMovement = null;

		Connection conn = AppDBHelper.getMoneyTrackDBConnection();
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
			finally {
				AppDBHelper.CloseResutSet(rs);
				AppDBHelper.CloseStatement(st);
				AppDBHelper.CloseConnection(conn);
			}
		}

		return bankMovement;
	}

	public List<BankMovement> getBankMovements() {
		List<BankMovement> bankMovements = new ArrayList<BankMovement>();

		Connection conn = AppDBHelper.getMoneyTrackDBConnection();
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
			finally {
				AppDBHelper.CloseResutSet(rs);
				AppDBHelper.CloseStatement(st);
				AppDBHelper.CloseConnection(conn);
			}
		}

		return bankMovements;
	}

	public void insertBankMovement(BankMovement bankMovement) {
		Connection conn = AppDBHelper.getMoneyTrackDBConnection();
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
			finally {
				AppDBHelper.CloseStatement(st);
				AppDBHelper.CloseConnection(conn);
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

	public GotSalary getGotSalary(long id) {
		GotSalary gotSalary = null;

		Connection conn = AppDBHelper.getMoneyTrackDBConnection();
		if (conn != null) {
			PreparedStatement st = null;
			ResultSet rs = null;
			try {
				String sql =
						"SELECT mm.id" +
						"     , mm.date" +
						"     , mm.amount" +
						"     , mm.currency" +
						"     , bm.remarks" +
						"  FROM bank_movement bm" +
						" INNER JOIN money_movement mm ON mm.id = bm.id_money_movement" +
						" WHERE bm.id_money_movement = ? " +
						"   AND bm.operation = '" + BankOperation.TRANSFER_IN.name() + "'" +
						"   AND bm.remarks LIKE 'SALARY %' ";

				st = conn.prepareStatement(sql);
				st.setLong(1, id);
				rs = st.executeQuery();

				if (rs.next()) {
					gotSalary = populateGotSalary(rs);
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
			finally {
				AppDBHelper.CloseResutSet(rs);
				AppDBHelper.CloseStatement(st);
				AppDBHelper.CloseConnection(conn);
			}
		}

		return gotSalary;
	}

	public Withdrawal getWithdrawal(long id) {
		Withdrawal withdrawal = null;

		Connection conn = AppDBHelper.getMoneyTrackDBConnection();
		if (conn != null) {
			PreparedStatement st = null;
			ResultSet rs = null;
			try {
				String sql =
						"SELECT mm.id" +
						"     , mm.date" +
						"     , mm.amount" +
						"     , mm.currency" +
						"     , bm.remarks" +
						"  FROM bank_movement bm" +
						" INNER JOIN money_movement mm ON mm.id = bm.id_money_movement" +
						" WHERE bm.id_money_movement = ? " +
						"   AND bm.operation = '" + BankOperation.WITHDRAWAL.name() + "'";

				st = conn.prepareStatement(sql);
				st.setLong(1, id);
				rs = st.executeQuery();

				if (rs.next()) {
					withdrawal = populateWithdrawal(rs);
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
			finally {
				AppDBHelper.CloseResutSet(rs);
				AppDBHelper.CloseStatement(st);
				AppDBHelper.CloseConnection(conn);
			}
		}

		return withdrawal;
	}

}
