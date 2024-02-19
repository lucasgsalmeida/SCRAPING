package com.AWN.model;

public class Empresas {

	private int codigo;
	private String nome;
	private String cnpj;
	private String Ins_est;
	private String Ins_mun;
	private String cida_emp;
	private String esta_emp;
	private String buscaSefaz;
	private String buscaPrefeitura;

	public Empresas() {
	}

	public Empresas(int codigo, String nome, String cnpj, String Ins_est, String Ins_mun, String cida_emp,
			String esta_emp) {
		this.codigo = codigo;
		this.nome = nome;
		this.cnpj = cnpj;
		this.setIns_est(Ins_est);
		this.setIns_mun(Ins_mun);
		this.cida_emp = cida_emp;
		this.esta_emp = esta_emp;
	}

	public Empresas(int codigo, String nome, String cnpj, String Ins_est, String Ins_mun, String cida_emp,
			String esta_emp, String buscaSefaz, String buscaPrefeitura) {
		this.codigo = codigo;
		this.nome = nome;
		this.cnpj = cnpj;
		this.setIns_est(Ins_est);
		this.setIns_mun(Ins_mun);
		this.cida_emp = cida_emp;
		this.esta_emp = esta_emp;
		this.buscaSefaz = buscaSefaz;
		this.buscaPrefeitura = buscaPrefeitura;
	}
	
	public String getCida_emp() {
		return cida_emp;
	}

	public void setCida_emp(String cida_emp) {
		this.cida_emp = cida_emp;
	}

	public String getEsta_emp() {
		return esta_emp;
	}

	public void setEsta_emp(String esta_emp) {
		this.esta_emp = esta_emp;
	}

	public int getCodigo() {
		return codigo;
	}

	public void setCodigo(int codigo) {
		this.codigo = codigo;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getIns_est() {
		return Ins_est;
	}

	public void setIns_est(String insc) {
		if (insc != null) {
			insc = insc.replace(".", "").replace("-", "");
		}
		this.Ins_est = insc;
	}

	public String getCnpj() {
		return cnpj;
	}

	public void setCnpj(String cnpj) {
		this.cnpj = cnpj;
	}

	public String getIns_mun() {
		return Ins_mun;
	}

	public void setIns_mun(String insc) {
		if (insc != null) {
			insc = insc.replace(".", "").replace("-", "");
		}
		this.Ins_mun = insc;
	}

	public String getBuscaSefaz() {
		return buscaSefaz;
	}

	public void setBuscaSefaz(String buscaSefaz) {
		this.buscaSefaz = buscaSefaz;
	}

	public String getBuscaPrefeitura() {
		return buscaPrefeitura;
	}

	public void setBuscaPrefeitura(String buscaPrefeitura) {
		this.buscaPrefeitura = buscaPrefeitura;
	}
}
