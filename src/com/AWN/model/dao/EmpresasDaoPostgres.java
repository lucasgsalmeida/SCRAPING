package com.AWN.model.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.AWN.model.Empresas;
import com.AWN.model.factory.ConnectionPostgres;

public class EmpresasDaoPostgres{

	private Empresas emp;

	public Empresas getEmp() {
		return emp;
	}

	public void setEmp(Empresas emp) {
		this.emp = emp;
	}

	public EmpresasDaoPostgres(Empresas emp) {
		this.emp = emp;

	}

	public static Empresas getEmpresaByCodigo(int codi_emp) {

		String sql = "select * from empresas where codi_emp = ?";

		try (Connection conn = ConnectionPostgres.getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {

			ps.setInt(1, codi_emp);

			ResultSet rs = ps.executeQuery();

			if (rs.next()) {

				int codigo = rs.getInt("codi_emp");
				String nome = rs.getString("nome_emp");
				String cnpj = rs.getString("cnpj_emp");
				String ins_est = rs.getString("ins_est");
				String ins_mun = rs.getString("ins_mun");
				String cida_emp = rs.getString("cida_emp");
				String esta_emp = rs.getString("esta_emp");

				Empresas emp = new Empresas(codigo, nome, cnpj, ins_est, ins_mun, cida_emp, esta_emp);

				return emp;

			}

		} catch (SQLException e) {
			e.printStackTrace();
		}

		return null;

	}

	public void verificarEInserirEmpresa() {

		String sql = "select * from empresas where codi_emp = ?";

		try (Connection conn = ConnectionPostgres.getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {

			ps.setInt(1, emp.getCodigo());

			ResultSet rs = ps.executeQuery();

			if (!rs.next()) {
				insertEmpresa();
			} // else {
				// updateEmpresa();
				// }

		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public void insertEmpresa() {

		String sql = "insert into Empresas(codi_emp, nome_emp, cnpj_emp, ins_est, ins_mun, cida_emp, esta_emp, busca_sefaz, busca_prefeitura) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
		String buscaSefaz;
		String buscaPrefeitura;

		if (emp.getIns_est() == null) {
			buscaSefaz = "N";
		} else {
			buscaSefaz = "S";
		}

		if (emp.getIns_mun() == null) {
			buscaPrefeitura = "N";
		} else {
			buscaPrefeitura = "S";
		}

		try (Connection conn = ConnectionPostgres.getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {

			ps.setInt(1, emp.getCodigo());
			ps.setString(2, emp.getNome());
			ps.setString(3, emp.getCnpj());
			ps.setString(4, emp.getIns_est());
			ps.setString(5, emp.getIns_mun());
			ps.setString(6, emp.getCida_emp());
			ps.setString(7, emp.getEsta_emp());
			ps.setString(8, buscaSefaz);
			ps.setString(9, buscaPrefeitura);

			ps.execute();

		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public void deleteEmpresa() {

		String sql = "delete from Empresas where codi_emp = ?";

		try (Connection conn = ConnectionPostgres.getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {
			ps.setInt(1, emp.getCodigo());

			ps.execute();

		} catch (SQLException e) {
			e.printStackTrace();
		}

	}

	public void updateEmpresa() {

		String sql = "UPDATE Empresas SET nome_emp = ?, cnpj_emp = ?, ins_est = ?, ins_mun = ?, cida_emp = ?, esta_emp = ?, busca_sefaz = ?, busca_prefeitura = ? WHERE codi_emp = ?";
		String buscaSefaz;
		String buscaPrefeitura;

		if (emp.getIns_est() == null) {
			buscaSefaz = "N";
		} else {
			buscaSefaz = "S";
		}

		if (emp.getIns_mun() == null) {
			buscaPrefeitura = "N";
		} else {
			buscaPrefeitura = "S";
		}

		try (Connection conn = ConnectionPostgres.getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {

			ps.setString(1, emp.getNome());
			ps.setString(2, emp.getCnpj());
			ps.setString(3, emp.getIns_est());
			ps.setString(4, emp.getIns_mun());
			ps.setString(5, emp.getCida_emp());
			ps.setString(6, emp.getEsta_emp());
			ps.setString(7, buscaSefaz);
			ps.setString(8, buscaPrefeitura);
			ps.setInt(9, emp.getCodigo());

			ps.execute();

		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public static List<Empresas> getEmpresasAll() {

		String sql = "select * from empresas";
		List<Empresas> listaEmpresas = new ArrayList<Empresas>();

		try (Connection conn = ConnectionPostgres.getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {

			ResultSet rs = ps.executeQuery();

			while (rs.next()) {

				int codigo = rs.getInt("codi_emp");
				String nome = rs.getString("nome_emp");
				String cnpj = rs.getString("cnpj_emp");
				String ins_est = rs.getString("ins_est");
				String ins_mun = rs.getString("ins_mun");
				String cida_emp = rs.getString("cida_emp");
				String esta_emp = rs.getString("esta_emp");
				String buscaSefaz = rs.getString("busca_sefaz");
				String buscaPrefeitura = rs.getString("busca_prefeitura");


				Empresas emp = new Empresas(codigo, nome, cnpj, ins_est, ins_mun, cida_emp, esta_emp, buscaSefaz, buscaPrefeitura);
				listaEmpresas.add(emp);

			}

			return listaEmpresas;

		} catch (SQLException e) {
			e.printStackTrace();
		}

		return null;

	}

}
