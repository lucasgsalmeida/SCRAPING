
package com.AWN.model.api_dominio;

import java.io.File;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import org.json.JSONObject;

import com.AWN.model.api_dominio.dao.ApiDao;
import com.AWN.model.factory.ConnectionPostgres;
import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;

public class EnviarParaApi {
	
	private static String token = ApiDao.getTokenGeral();

	public static void enviarNotasApi(File file, int codi_emp, String chave) {

		Unirest.setTimeouts(0, 0);

		try {
			HttpResponse<String> response = Unirest.post("https://api.onvio.com.br/dominio/invoice/v3/batches")
					.header("x-integration-key", ApiDao.getTokenCliente(codi_emp)).header("Authorization", "Bearer " + token)
					.field("file[]", file).field("query", "{\"boxeFile\": false}", "application/json").asString();
			int statusCode = response.getStatus();
			String responseBody = response.getBody();

			if (statusCode == 201) {
				System.out.println("Sucesso! Resposta da API: " + responseBody);
				JSONObject jsonResponse = new JSONObject(response.getBody());
				String idRequest = jsonResponse.getString("id");

				String sql = "update nfe set idRequest = ? where chnfe = ?";

				try (Connection conn = ConnectionPostgres.getConnection();
						PreparedStatement ps = conn.prepareStatement(sql)) {

					ps.setString(1, idRequest);
					ps.setString(2, chave);

					ps.execute();

				} catch (SQLException e) {
					e.printStackTrace();
				}
			}

		} catch (UnirestException e) {
			e.printStackTrace();
		}
	}


}
