package com.AWN.model.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;

import com.AWN.model.Status;
import com.AWN.model.factory.ConnectionPostgres;

public class StatusDao {

	private Status status;

	public StatusDao() {
	}

	public StatusDao(Status status) {
		this.setStatus(status);
	}

	public Status getStatus() {
		return status;
	}

	public void setStatus(Status status) {
		this.status = status;
	}

	public void adicionarStatus() {

		String sql = "insert into Status(codi_emp, descricao, tipoServico, data) values (?, ?, ?, ?)";

		try (Connection conn = ConnectionPostgres.getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {

			ps.setInt(1, status.getEmp().getCodigo());
			ps.setString(2, status.getDescricao());
			ps.setString(3, status.getTipoServico());
			ps.setString(4, status.getData());

			ps.execute();

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

}
