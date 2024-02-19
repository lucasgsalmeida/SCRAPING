package com.AWN.model.files;

import java.io.File;

import com.AWN.model.Empresas;

public class Pasta extends PastasAbstract {

	public Pasta(String tipo) {
		criarPastaRaiz(tipo);
		criarPastaTemp();

	}

	public void criarPastas(String tipoNota, Empresas emp, String competencia) {
		criarPastaTipoNotas(tipoNota);
		criarPastaEmpresa(emp);
		criarPastaCompetência(competencia);
	}

	private void criarPastaTemp() {
		String caminho = "C:\\AWN-NOTAS";
		
		File pastaRaiz = new File(caminho);

		File fileTemp = new File(pastaRaiz, "temp");
		
		setPastaTemporaria(fileTemp);
		if (!pastaRaiz.exists()) {
			pastaRaiz.mkdir();
		}
		
		if (!fileTemp.exists()) {
			fileTemp.mkdir();
		} 
	}

	private void criarPastaRaiz(String tipo) {
		String redePath = "\\\\svr-pdc\\AWN-NOTAS";

		File fileMain = new File(redePath);
		File notasSaida = new File(fileMain, tipo);
		setPastaRaiz(notasSaida);
		if (!notasSaida.exists()) {
			notasSaida.mkdir();
		} else {
			System.out.println("A pasta já existe");
		}
	}

	private void criarPastaTipoNotas(String nomePasta) {

		File pastaMain = new File(getPastaRaiz().getAbsolutePath());
		File pastaTipoNota = new File(pastaMain, nomePasta);

		if (!pastaTipoNota.exists()) {
			pastaTipoNota.mkdir();
			setPastaTipoNota(pastaTipoNota);
		} else {
			setPastaTipoNota(pastaTipoNota);
		}
	}

	private void criarPastaEmpresa(Empresas emp) {

		String nome = emp.getCodigo() + "-";

		File pastaMain = new File(getPastaTipoNota().getAbsolutePath());
		File empresaPasta = new File(pastaMain, nome);

		if (!empresaPasta.exists()) {
			empresaPasta.mkdir();
			setPastaEmpresa(empresaPasta);
		} else {
			setPastaEmpresa(empresaPasta);
		}
	}

	private void criarPastaCompetência(String comp) {
		String caminho = getPastaEmpresa().getAbsolutePath();
		comp = comp.replace("/", "");

		File pastaEmpresa = new File(caminho);
		File pastaCompetencia = new File(pastaEmpresa, comp);

		if (!pastaCompetencia.exists()) {
			pastaCompetencia.mkdir();
			setPastaCompetencia(pastaCompetencia);
		} else {
			setPastaCompetencia(pastaCompetencia);
		}
	}
}
