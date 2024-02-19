package com.AWN.model.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.AWN.model.Empresas;
import com.AWN.model.factory.ConnectionDominio;

public class EmpresasDaoDominio {

	public static List<Empresas> getEmpresasAll() {

		String sql = "select codi_emp, nome_emp, cgce_emp, iest_emp, imun_emp, cida_emp, esta_emp from bethadba.geempre where stat_emp like 'A' and codi_emp < 10000 and LEN(cgce_emp) = 14 order by codi_emp asc";
		List<Empresas> listaEmpresas = new ArrayList<Empresas>();

		try (Connection conn = ConnectionDominio.getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {

			ResultSet rs = ps.executeQuery();

			while (rs.next()) {
				int codigo = rs.getInt("codi_emp");
				String nome_emp = rs.getString("nome_emp");
				String cgce_emp = rs.getString("cgce_emp");
				String iest_emp = rs.getString("iest_emp");
				String imun_emp = rs.getString("imun_emp");
				String cida_emp = rs.getString("cida_emp");
				String esta_emp = rs.getString("esta_emp");
				Empresas emp = new Empresas(codigo, nome_emp, cgce_emp, iest_emp, imun_emp, cida_emp, esta_emp);
				listaEmpresas.add(emp);
			}

			return listaEmpresas;

		} catch (SQLException e) {
			e.printStackTrace();
		}

		return null;
	}
}
