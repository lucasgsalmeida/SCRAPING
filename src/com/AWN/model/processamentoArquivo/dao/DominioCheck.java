package com.AWN.model.processamentoArquivo.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.AWN.model.factory.ConnectionDominio;


public class DominioCheck {
	
	public static String temNotaDominioEntrada(String chave) {
		
		String sql = "select * from bethadba.efentradas where chave_nfe_ent = ?";
		
		try (Connection conn = ConnectionDominio.getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {
			
			ps.setString(1, chave);
			
			ResultSet rs = ps.executeQuery();
			
			System.out.println("EXECUTOU A FUNÇÃO PARA VER SE TEM NOTA NA DOMÍNIO");
			while (rs.next()) {
				return "true";
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return "false";
	}
	
	public static boolean temNotaDominioEntradaBool(String chave) {
		
		String sql = "select * from bethadba.efentradas where chave_nfe_ent = ?";
		
		try (Connection conn = ConnectionDominio.getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {
			
			ps.setString(1, chave);
			
			ResultSet rs = ps.executeQuery();
			
			System.out.println("EXECUTOU A FUNÇÃO PARA VER SE TEM NOTA NA DOMÍNIO");
			while (rs.next()) {
				return true;
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return false;
	}
	
	public static String temNotaDominioSaida(String chave) {
		
		String sql = "select * from bethadba.efsaidas where chave_nfe_sai = ?";
		
		try (Connection conn = ConnectionDominio.getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {
			
			ps.setString(1, chave);
			
			ResultSet rs = ps.executeQuery();
			
			while (rs.next()) {
				return "true";
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return "false";
	}
	
	public static boolean temNotaDominioSaidaBool(String chave) {
		
		String sql = "select * from bethadba.efsaidas where chave_nfe_sai = ?";
		
		try (Connection conn = ConnectionDominio.getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {
			
			ps.setString(1, chave);
			
			ResultSet rs = ps.executeQuery();
			
			while (rs.next()) {
				return true;
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return false;
	}

}
