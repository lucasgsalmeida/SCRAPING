package com.AWN.model;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class TabelaProcessamento {

	private Empresas empresa;
	private String periodoExecucao;
	private String tipoServiço;
	private String data;

	public TabelaProcessamento() {
	}
	
	public TabelaProcessamento(Empresas emp, String periodo, String tipoServiço, String date) {
		this.empresa = emp;
		this.periodoExecucao = periodo;
		this.tipoServiço = tipoServiço;
		this.data = date;
	}

	public TabelaProcessamento(Empresas emp, String periodo, String tipoServiço) {
		this.empresa = emp;
		this.periodoExecucao = periodo;
		this.tipoServiço = tipoServiço;
		this.data = getDataAtual();
	}
	
	public TabelaProcessamento(Empresas emp, String periodo) {
		this.empresa = emp;
		this.periodoExecucao = periodo;
		this.data = getDataAtual();
	}

	public Empresas getEmpresa() {
		return empresa;
	}

	public void setEmpresa(Empresas emp) {
		this.empresa = emp;
	}

	public String getPeriodoExecucao() {
		return periodoExecucao;
	}

	public void setPeriodoExecucao(String periodo) {
		this.periodoExecucao = periodo;
	}

	public String getTipoServiço() {
		return tipoServiço;
	}

	public void setTipoServiço(String tipoServiço) {
		this.tipoServiço = tipoServiço;
	}

	public String getData() {
		return data;
	}
	
	public void setData(String data) {
		this.data = data;
	}

	private String getDataAtual() {
		LocalDateTime now = LocalDateTime.now();
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy - HH:mm:ss");
		String formattedDateTime = now.format(formatter);
		return formattedDateTime;
	}

}
