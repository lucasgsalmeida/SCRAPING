package com.AWN.model;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Status {

	private Empresas emp;
	private String descricao;
	private String tipoServico;
	private String data;

	public Status() {
	}

	public Status(Empresas emp, String descricao, String tipoServico) {
		this.emp = emp;
		this.descricao = descricao;
		this.tipoServico = tipoServico;
		this.data = getDataAtual();
	}

	public Empresas getEmp() {
		return emp;
	}

	public void setEmp(Empresas emp) {
		this.emp = emp;
	}

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	public String getTipoServico() {
		return tipoServico;
	}

	public void setTipoServico(String tipoServico) {
		this.tipoServico = tipoServico;
	}

	public String getData() {
		return data;
	}
	
	private String getDataAtual() {
		LocalDateTime now = LocalDateTime.now();
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy - HH:mm:ss");
		String formattedDateTime = now.format(formatter);
		return formattedDateTime;
	}

}
