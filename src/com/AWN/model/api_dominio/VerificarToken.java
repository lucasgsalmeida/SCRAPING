package com.AWN.model.api_dominio;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;

import org.json.JSONObject;

import com.AWN.model.factory.ConnectionPostgres;
import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;

public class VerificarToken {

	public static void verificarValidadeToken() {

		String sql = "select data from tokenDominio where codigo = 1";

		try (Connection conn = ConnectionPostgres.getConnection();
				PreparedStatement preparedStatement = conn.prepareStatement(sql)) {

			try (ResultSet rs = preparedStatement.executeQuery()) {
				while (rs.next()) {
					String data = rs.getString("data");
					System.out.println(data);

					if (!verificarDiferençaDatas(data)) {
						gerarNovoToken();
					}
				}
			}
		} catch (SQLException e) {
			e.printStackTrace(System.out);
		}
	}

	private static String gerarNovaData() {
		ZoneId saoPauloZone = ZoneId.of("America/Sao_Paulo");
		ZonedDateTime horarioSaoPaulo = ZonedDateTime.now(saoPauloZone);
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
		String horarioFormatado = horarioSaoPaulo.format(formatter);
		return horarioFormatado;
	}

	private static boolean verificarDiferençaDatas(String data) {
		ZoneId saoPauloZone = ZoneId.of("America/Sao_Paulo");
		ZonedDateTime horarioSaoPaulo = ZonedDateTime.now(saoPauloZone);
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
		String horarioFormatado = horarioSaoPaulo.format(formatter);
		System.out.println("Horário em São Paulo: " + horarioFormatado);
		LocalDateTime dataHo2 = LocalDateTime.parse(data, formatter);
		Duration diferenca = Duration.between(horarioSaoPaulo.toLocalDateTime(), dataHo2);
		long horas = diferenca.toHours();
		long minutos = diferenca.toMinutes() % 60;
		if (horas > -23 && minutos > -30) {
			return true;
		} else {
			return false;
		}
	}

	private static void gerarNovoToken() {

		Unirest.setTimeouts(0, 0);
		try {
			HttpResponse<String> response = Unirest.post("https://auth.thomsonreuters.com/oauth/token")
					.header("Content-Type", "application/x-www-form-urlencoded")
					.header("Cookie",
							"XXXXX COOKIE")
					.header("Authorization",
							"Basic TOKEN_XXX==")
					.field("grant_type", "client_credentials").field("client_id", "XXXX")
					.field("client_secret", "SECRET_XXX")
					.field("audience", "AUDIENCE_XXX").asString();

			JSONObject jsonResponse = new JSONObject(response.getBody());
			String accessToken = jsonResponse.getString("access_token");

			inserirTokenBD(accessToken);

		} catch (UnirestException e) {
			e.printStackTrace();
		}

	}

	private static void inserirTokenBD(String token) {
		String sql = "update tokenDominio set token = ?, data = ? where codigo = 1";

		try (Connection conn = ConnectionPostgres.getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {
			ps.setString(1, token);
			ps.setString(2, gerarNovaData());
			ps.executeUpdate();

		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

}
