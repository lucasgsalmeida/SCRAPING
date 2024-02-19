package com.AWN.model.api_dominio.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.AWN.model.factory.ConnectionPostgres;
import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;

public class ApiDao {

	private static String tokenGeral = ApiDao.getTokenGeral();

	public static String getTokenGeral() {

		String sql = "select token from tokenDominio where codigo = 1";

		try (Connection conn = ConnectionPostgres.getConnection();
				PreparedStatement preparedStatement = conn.prepareStatement(sql)) {

			try (ResultSet rs = preparedStatement.executeQuery()) {
				while (rs.next()) {
					String token = rs.getString("token");
					return token;
				}
			}
		} catch (SQLException e) {
			e.printStackTrace(System.out);
		}
		return null;
	}

	public static void setTokenApi(int codi_emp, String token) {
		String sql = "UPDATE Empresas SET token_api = '" + token + "' WHERE codi_emp = " + codi_emp;

		try (Connection conn = ConnectionPostgres.getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {

			ps.executeUpdate();

		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	private static String criarTokenApi(int codi_emp) {

		String integrationKey = getTokenOnvio(codi_emp);

		Unirest.setTimeouts(0, 0);
		try {
			HttpResponse<String> response = Unirest
					.post("https://api.onvio.com.br/dominio/integration/v1/activation/enable")
					.header("Authorization", "Bearer " + tokenGeral).header("x-integration-key", integrationKey)
					.asString();

			if (response.getStatus() >= 200 && response.getStatus() < 300) {
				System.out.println("Resposta bem-sucedida: " + response.getBody().toString());

				String responseBody = response.getBody();
				String integrationKeyFromResponse = responseBody.replaceAll(".*\"integrationKey\":\"([^\"]*)\".*",
						"$1");
				System.out.println("Integration Key: " + integrationKeyFromResponse);

				setTokenApi(codi_emp, integrationKeyFromResponse);

				return integrationKeyFromResponse;

			} else {
				System.err.println("Erro na requisição. Código de status: " + response.getStatus());
			}
		} catch (UnirestException e) {
			e.printStackTrace();
		}

		return null;
	}

	public static String getTokenOnvio(int codi_emp) {
		String sql = "select token from Empresas where codi_emp = ?";

		try (Connection conn = ConnectionPostgres.getConnection();
				PreparedStatement preparedStatement = conn.prepareStatement(sql)) {

			preparedStatement.setInt(1, codi_emp);

			try (ResultSet rs = preparedStatement.executeQuery()) {
				while (rs.next()) {
					String token = rs.getString("token");
					return token;
				}

				conn.close();

			} catch (SQLException e) {
				e.printStackTrace(System.out);
			}

		} catch (SQLException e) {
			e.printStackTrace(System.out);
		}

		return "ERRO";
	}

	public static String getTokenCliente(int codi_emp) {

		String sql = "select token_api from Empresas where codi_emp = ?";

		if (!temTokenApi(codi_emp)) {
			return criarTokenApi(codi_emp);
		}

		try (Connection conn = ConnectionPostgres.getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {

			ps.setInt(1, codi_emp);

			try (ResultSet rs = ps.executeQuery()) {
				while (rs.next()) {
					String token = rs.getString("token_api");
					return token;
				}
			}
		} catch (SQLException e) {
			e.printStackTrace(System.out);
		}
		return null;
	}

	private static boolean temTokenApi(int codi_emp) {

		String sql = "select * from Empresas where codi_emp = ? and token_api is not null";

		try (Connection conn = ConnectionPostgres.getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {

			ps.setInt(1, codi_emp);

			try (ResultSet rs = ps.executeQuery()) {
				if (rs.next()) {
					return true;
				} else {
					return false;
				}
			}
		} catch (SQLException e) {
			e.printStackTrace(System.out);
		}
		return false;
	}

}
