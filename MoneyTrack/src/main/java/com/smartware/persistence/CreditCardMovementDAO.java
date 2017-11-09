package com.smartware.persistence;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import com.smartware.common.AppDBHelper;
import com.smartware.domain.CreditCardMovement;
import com.smartware.domain.CreditCardPayment;
import com.smartware.domain.catalog.CreditCardOperation;
import com.smartware.domain.catalog.CreditCardPaymentType;
import com.smartware.domain.catalog.Currency;

public class CreditCardMovementDAO {

	private CreditCardMovement populateCreditCardMovement(ResultSet rs) throws Exception {
		CreditCardMovement creditCardMovement = new CreditCardMovement();
		creditCardMovement.setId(rs.getLong("id"));
		creditCardMovement.setDate(rs.getTimestamp("date"));
		creditCardMovement.setAmount(rs.getFloat("amount"));
		creditCardMovement.setCurrency(Currency.valueOf(rs.getString("currency")));
		creditCardMovement.setCreditCardOperation(CreditCardOperation.valueOf(rs.getString("operation")));
		creditCardMovement.setRemarks(rs.getString("remarks"));

		return creditCardMovement;
	}

	private CreditCardPayment populateCreditCardPayment(ResultSet rs) throws Exception {
		CreditCardPayment creditCardPayment = new CreditCardPayment();
		creditCardPayment.setId(rs.getLong("id"));
		creditCardPayment.setDate(rs.getTimestamp("date"));
		creditCardPayment.setAmount(rs.getFloat("amount"));
		creditCardPayment.setCurrency(Currency.valueOf(rs.getString("currency")));
		creditCardPayment.setPaymentType(CreditCardPaymentType.valueOf(rs.getString("payment_type")));
		creditCardPayment.setRemarks(rs.getString("remarks"));

		return creditCardPayment;
	}

	public CreditCardMovement getCreditCardMovement(long id) throws Exception {
		CreditCardMovement creditCardMovement = null;

		Connection conn = null;
		PreparedStatement st = null;
		ResultSet rs = null;
		try {
			String sql = 
					"SELECT mm.id, mm.date, mm.amount, mm.currency, ccm.operation, ccm.remarks" +
					"  FROM credit_card_movement ccm" +
					" INNER JOIN money_movement mm ON mm.id = ccm.id_money_movement" +
					" WHERE ccm.id_money_movement = ?";

			conn = AppDBHelper.getMoneyTrackDBConnection();
			st = conn.prepareStatement(sql);
			st.setLong(1, id);
			rs = st.executeQuery();

			if (rs.next()) {
				creditCardMovement = populateCreditCardMovement(rs);
			}
		} finally {
			AppDBHelper.CloseResutSet(rs);
			AppDBHelper.CloseStatement(st);
			AppDBHelper.CloseConnection(conn);
		}

		return creditCardMovement;
	}

	public List<CreditCardMovement> getCreditCardMovements() throws Exception {
		List<CreditCardMovement> creditCardMovements = new ArrayList<CreditCardMovement>();

		Connection conn = null;
		PreparedStatement st = null;
		ResultSet rs = null;
		try {
			String sql = 
					"SELECT mm.id, mm.type, mm.date, mm.amount, mm.currency, ccm.operation, ccm.remarks" +
					"  FROM credit_card_movement ccm" +
					" INNER JOIN money_movement mm ON mm.id = ccm.id_money_movement";

			conn = AppDBHelper.getMoneyTrackDBConnection();
			st = conn.prepareStatement(sql);
			rs = st.executeQuery();

			CreditCardMovement creditCardMovement = null;
			while (rs.next()) {
				creditCardMovement = populateCreditCardMovement(rs);
				creditCardMovements.add(creditCardMovement);
			}
		} finally {
			AppDBHelper.CloseResutSet(rs);
			AppDBHelper.CloseStatement(st);
			AppDBHelper.CloseConnection(conn);
		}

		return creditCardMovements;
	}

	public void insertCreditCardMovement(CreditCardMovement creditCardMovement) throws Exception {
		Connection conn = null;
		PreparedStatement st = null;
		try {
			String sql = "INSERT INTO credit_card_movement (id_money_movement, operation, remarks) VALUES (?, ?, ?)";

			conn = AppDBHelper.getMoneyTrackDBConnection();
			st = conn.prepareStatement(sql);
			st.setLong(1, creditCardMovement.getId());
			st.setString(2, creditCardMovement.getCreditCardOperation().name());
			st.setString(3, creditCardMovement.getRemarks());

			st.execute();
		} finally {
			AppDBHelper.CloseStatement(st);
			AppDBHelper.CloseConnection(conn);
		}
	}

	public void insertCreditCardMovement(long id, CreditCardOperation creditCardOperation, String remarks) throws Exception {
		CreditCardMovement creditCardMovement = new CreditCardMovement();
		creditCardMovement.setId(id);
		creditCardMovement.setCreditCardOperation(creditCardOperation);
		creditCardMovement.setRemarks(remarks);

		insertCreditCardMovement(creditCardMovement);
	}

	public CreditCardPayment getCreditCardPayment(long id) throws Exception {
		CreditCardPayment creditCardPayment = null;

		Connection conn = null;
		PreparedStatement st = null;
		ResultSet rs = null;
		try {
			String sql =
					"SELECT mm.id" +
					"     , mm.date" +
					"     , mm.amount" +
					"     , mm.currency" +
					"     , CASE bm.id_money_movement" +
					"         WHEN NULL THEN '" + CreditCardPaymentType.DEPOSIT + "'" +
					"         ELSE '" + CreditCardPaymentType.BANK_TRANSFER + "'" +
					"       END AS payment_type" +
					"     , ccm.remarks" +
					"  FROM credit_card_movement ccm" +
					" INNER JOIN money_movement mm ON mm.id = ccm.id_money_movement" +
					"  LEFT JOIN bank_movement bm ON mm.id = bm.id_money_movement" +
					" WHERE ccm.operation = '" + CreditCardOperation.PAYMENT.name() + "' " +
					"   AND ccm.id_money_movement = ?";

			conn = AppDBHelper.getMoneyTrackDBConnection();
			st = conn.prepareStatement(sql);
			st.setLong(1, id);
			rs = st.executeQuery();

			if (rs.next()) {
				creditCardPayment = populateCreditCardPayment(rs);
			}
		} finally {
			AppDBHelper.CloseResutSet(rs);
			AppDBHelper.CloseStatement(st);
			AppDBHelper.CloseConnection(conn);
		}

		return creditCardPayment;
	}

}
