package com.AWN.model.processamentoArquivo.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import com.AWN.model.factory.ConnectionPostgres;

public class NfcSaidaDao {
	
	public static void adicionarNFC(int codi_emp, int ide_nnf, Double vNF, String dEmi, String chNFe, String emit_cnpj, String emit_xnome, String dest_cnpj, String dest_xnome, String cStat, String isNotaDominio) {

		String sql = "insert into nfce(codi_emp, ide_nnf, vNF, dEmi, chNFe, emit_cnpj, emit_xnome, dest_cnpj, dest_xnome, cStat, isNotaDominio) values (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

		try (Connection conn = ConnectionPostgres.getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {

			ps.setInt(1, codi_emp);
			ps.setInt(2, ide_nnf);
			ps.setDouble(3, vNF);
			ps.setString(4, dEmi);
			ps.setString(5, chNFe);
			ps.setString(6, emit_cnpj);
			ps.setString(7, emit_xnome);
			ps.setString(8, dest_cnpj);
			ps.setString(9, dest_xnome);
			ps.setString(10, cStat);
			ps.setString(11, isNotaDominio);

			ps.executeUpdate();
			conn.close();

		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

}
