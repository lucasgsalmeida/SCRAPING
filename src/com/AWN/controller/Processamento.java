package com.AWN.controller;

import java.awt.AWTException;
import java.io.IOException;
import java.util.List;

import com.AWN.controller.execucao.sefazGo.SefazGO_Entradas;
import com.AWN.controller.execucao.sefazGo.SefazGO_Saidas;
import com.AWN.model.FilaDeProcessamento;
import com.AWN.model.TabelaProcessamento;
import com.AWN.model.dao.TabelaProcessamentoDao;

public class Processamento {

	public void processarEmpresa() throws IOException, AWTException {

		FilaDeProcessamento filaDeProcessamento = new FilaDeProcessamento();

		//filaDeProcessamento.atualizarEmpresasPostgres();
		//filaDeProcessamento.gerarFilaProcessamentoEscolha();

		List<TabelaProcessamento> tab = TabelaProcessamentoDao.getProcessamentos();

		for (TabelaProcessamento tb : tab) {

			if (tb.getTipoServiço().equalsIgnoreCase("SEFAZ-GO")) {

				System.out.println("PERIODO EXECUCAO: \n\n" + tb.getPeriodoExecucao());
				SefazGO_Entradas.processarEntradas(tb.getEmpresa(),
						filaDeProcessamento.transformDate(tb.getPeriodoExecucao()), tb.getPeriodoExecucao(), tb);
				SefazGO_Saidas.processarNFE(tb.getEmpresa(), filaDeProcessamento.transformDate(tb.getPeriodoExecucao()),
						tb.getPeriodoExecucao(), tb);
			}
		}
	}

}
