package com.AWN.model.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.AWN.model.Empresas;
import com.AWN.model.TabelaProcessamento;
import com.AWN.model.factory.ConnectionPostgres;

public class TabelaProcessamentoDao {

	private TabelaProcessamento tab;

	public TabelaProcessamento getTab() {
		return tab;
	}

	public void setTab(TabelaProcessamento tab) {
		this.tab = tab;
	}

	public TabelaProcessamentoDao(TabelaProcessamento tab) {
		this.tab = tab;
	}

	public void inserirProcessoTabela() {

		String sql = "insert into TabelaProcessos(codi_emp, periodoExecucao, tipoServico, data) values (?, ?, ?, ?)";

		try (Connection conn = ConnectionPostgres.getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {

			ps.setInt(1, tab.getEmpresa().getCodigo());
			ps.setString(2, tab.getPeriodoExecucao());
			ps.setString(3, tab.getTipoServiço());
			ps.setString(4, tab.getData());

			ps.execute();

		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public void deletarProcessoTabela() {

		String sql = "delete from TabelaProcessos where codi_emp = ? and periodoExecucao = ? and tipoServico = ?";

		try (Connection conn = ConnectionPostgres.getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {

			ps.setInt(1, tab.getEmpresa().getCodigo());
			ps.setString(2, tab.getPeriodoExecucao());
			ps.setString(3, tab.getTipoServiço());

			ps.execute();

		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public void fimDaFila() {

		String sql = "update TabelaProcessos set data = ? where codi_emp = ? and periodoExecucao = ? and tipoServico = ?";

		try (Connection conn = ConnectionPostgres.getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {

			ps.setString(1, tab.getData());
			ps.setInt(2, tab.getEmpresa().getCodigo());
			ps.setString(3, tab.getPeriodoExecucao());
			ps.setString(4, tab.getTipoServiço());

			ps.executeQuery();

		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public static List<TabelaProcessamento> getProcessamentos() {

		List<TabelaProcessamento> tab = new ArrayList<TabelaProcessamento>();

		String sql = "select * from TabelaProcessos order by data asc";

		try (Connection conn = ConnectionPostgres.getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {

			ResultSet rs = ps.executeQuery();

			while (rs.next()) {
				int codi_emp = rs.getInt("codi_emp");
				String periodoExecucao = rs.getString("periodoExecucao");
				String tipoServico = rs.getString("tipoServico");
				String data = rs.getString("data");

				Empresas emp = EmpresasDaoPostgres.getEmpresaByCodigo(codi_emp);
				TabelaProcessamento proc = new TabelaProcessamento(emp, periodoExecucao, tipoServico, data);
				tab.add(proc);
			}
			return tab;

		} catch (SQLException e) {
			e.printStackTrace();
		}

		return null;

	}

}
