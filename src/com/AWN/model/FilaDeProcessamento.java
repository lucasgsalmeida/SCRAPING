package com.AWN.model;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import com.AWN.model.dao.EmpresasDaoDominio;
import com.AWN.model.dao.EmpresasDaoPostgres;
import com.AWN.model.dao.TabelaProcessamentoDao;

public class FilaDeProcessamento {

	public FilaDeProcessamento() {
	}

	public void atualizarEmpresasPostgres() {

		List<Empresas> emp = EmpresasDaoDominio.getEmpresasAll();

		for (Empresas empt : emp) {

			EmpresasDaoPostgres ps = new EmpresasDaoPostgres(empt);
			ps.verificarEInserirEmpresa();
		}
	}

	public void gerarFilaProcessamentoEscolha() {

		List<Empresas> emp = EmpresasDaoPostgres.getEmpresasAll();
		List<String> dias = new ArrayList<String>();

		for (Empresas empt : emp) {
			for (String d : dias) {

				if (empt.getBuscaSefaz().equalsIgnoreCase("S")) {
					if (empt.getEsta_emp() != null && empt.getIns_est() != null) {
						TabelaProcessamento tb = new TabelaProcessamento(empt, d, getDataAtual());
						TabelaProcessamentoDao tbDao = new TabelaProcessamentoDao(tb);
						tb.setTipoServiço("SEFAZ-" + tb.getEmpresa().getEsta_emp());
						tbDao.inserirProcessoTabela();
					}
				}

				if (empt.getBuscaPrefeitura().equalsIgnoreCase("S")) {
					if (empt.getCida_emp() != null && empt.getIns_mun() != null) {
						TabelaProcessamento tb = new TabelaProcessamento(empt, d, getDataAtual());
						TabelaProcessamentoDao tbDao = new TabelaProcessamentoDao(tb);
						tb.setTipoServiço("PREFEITURA-" + tb.getEmpresa().getCida_emp());
						tbDao.inserirProcessoTabela();
					}
				}
			}
		}
	}

	public void gerarFilaProcessamento() {

		List<Empresas> emp = EmpresasDaoPostgres.getEmpresasAll();

		for (Empresas empt : emp) {

			if (empt.getBuscaSefaz().equalsIgnoreCase("S")) {
				if (empt.getEsta_emp() != null && empt.getIns_est() != null) {
					TabelaProcessamento tb = new TabelaProcessamento(empt, getPeriodo(), getDataAtual());
					TabelaProcessamentoDao tbDao = new TabelaProcessamentoDao(tb);
					tb.setTipoServiço("SEFAZ-" + tb.getEmpresa().getEsta_emp());
					tbDao.inserirProcessoTabela();
				}
			}

			if (empt.getBuscaPrefeitura().equalsIgnoreCase("S")) {
				if (empt.getCida_emp() != null && empt.getIns_mun() != null) {
					TabelaProcessamento tb = new TabelaProcessamento(empt, getPeriodo(), getDataAtual());
					TabelaProcessamentoDao tbDao = new TabelaProcessamentoDao(tb);
					tb.setTipoServiço("PREFEITURA-" + tb.getEmpresa().getCida_emp());
					tbDao.inserirProcessoTabela();
				}
			}
		}
	}

	public static String getPeriodo() {
		LocalDate yesterday = LocalDate.now().minusDays(1);
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
		return yesterday.format(formatter);
	}

	public static String getDataAtual() {
		LocalDateTime now = LocalDateTime.now();
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy - HH:mm:ss");
		String formattedDateTime = now.format(formatter);
		return formattedDateTime;
	}

	public String transformDate(String inputDate) {
		DateTimeFormatter inputFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
		DateTimeFormatter outputFormatter = DateTimeFormatter.ofPattern("MMyyyy");

		try {
			LocalDate date = LocalDate.parse(inputDate, inputFormatter);
			String outputDate = date.format(outputFormatter);

			return outputDate;
		} catch (Exception e) {
			System.out
					.println("Erro ao transformar a data. Certifique-se de que a entrada está no formato DD/MM/YYYY.");
			return null;
		}
	}

}
