package com.AWN.model.files;

import java.io.File;

public abstract class PastasAbstract {
	
	private File pastaRaiz;
	private File pastaTipoNota;
	private File pastaEmpresa;
	private File pastaCompetencia;
	private File pastaTemporaria;
	
	public File getPastaRaiz() {
		return pastaRaiz;
	}
	public void setPastaRaiz(File pastaRaiz) {
		this.pastaRaiz = pastaRaiz;
	}
	public File getPastaTipoNota() {
		return pastaTipoNota;
	}
	public void setPastaTipoNota(File pastaTipoNota) {
		this.pastaTipoNota = pastaTipoNota;
	}
	public File getPastaEmpresa() {
		return pastaEmpresa;
	}
	public void setPastaEmpresa(File pastaEmpresa) {
		this.pastaEmpresa = pastaEmpresa;
	}
	public File getPastaCompetencia() {
		return pastaCompetencia;
	}
	public void setPastaCompetencia(File pastaCompetencia) {
		this.pastaCompetencia = pastaCompetencia;
	}
	public File getPastaTemporaria() {
		return pastaTemporaria;
	}
	public void setPastaTemporaria(File pastaTemporaria) {
		this.pastaTemporaria = pastaTemporaria;
	}
}