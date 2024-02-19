package com.AWN.controller.execucao.sefazGo;

import java.awt.AWTException;
import java.awt.Robot;
import java.awt.Toolkit;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.StringSelection;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;
import java.io.IOException;

import com.AWN.model.Descompactador;
import com.AWN.model.Empresas;
import com.AWN.model.Status;
import com.AWN.model.TabelaProcessamento;
import com.AWN.model.VerificarTexto;
import com.AWN.model.dao.StatusDao;
import com.AWN.model.dao.TabelaProcessamentoDao;
import com.AWN.model.files.Pasta;
import com.AWN.model.processamentoArquivo.ProcessamentoEntrada;
import com.AWN.model.processamentoArquivo.ProcessamentoSaida;

public class SefazGO_Saidas {

	public static void processarNFE(Empresas empt, String mesAno, String competencia, TabelaProcessamento tb)
			throws IOException, AWTException {

		Pasta cp = new Pasta("NOTAS DE SAÍDA");
		cp.criarPastas("NF-e", empt, mesAno);

		String dataInicial = competencia.replaceAll("/", "");
		String dataFinal = competencia.replaceAll("/", "");

		String insc = empt.getIns_est();
		String pastaCompetencia = cp.getPastaTemporaria().getAbsolutePath();
		String caminho = pastaCompetencia + "\\" + competencia.replaceAll("/", "");

		SefazGO_Portal.abrirPortal();

		try {

			Robot bot = new Robot();

			if (VerificarTexto.naoEncontradoInicioExecucao("Inscrição estadual")) {

				bot.keyPress(KeyEvent.VK_ALT);
				bot.keyPress(KeyEvent.VK_F4);

				bot.keyRelease(KeyEvent.VK_F4);
				bot.keyRelease(KeyEvent.VK_ALT);
				
				TabelaProcessamentoDao tpd = new TabelaProcessamentoDao(tb);
				tpd.fimDaFila();

				return;

			}

			bot.delay(1000);
			bot.keyPress(KeyEvent.VK_TAB);
			bot.keyRelease(KeyEvent.VK_TAB);

			bot.delay(50);

			StringSelection selecao = new StringSelection(dataInicial);
			Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
			clipboard.setContents(selecao, null);

			bot.keyPress(KeyEvent.VK_CONTROL);
			bot.keyPress(KeyEvent.VK_V);

			bot.keyRelease(KeyEvent.VK_V);
			bot.keyRelease(KeyEvent.VK_CONTROL);

			bot.delay(50);
			bot.keyPress(KeyEvent.VK_TAB);
			bot.keyRelease(KeyEvent.VK_TAB);
			bot.delay(50);
			selecao = new StringSelection(dataFinal);
			clipboard.setContents(selecao, null);

			bot.keyPress(KeyEvent.VK_CONTROL);
			bot.keyPress(KeyEvent.VK_V);

			bot.keyRelease(KeyEvent.VK_V);
			bot.keyRelease(KeyEvent.VK_CONTROL);

			bot.delay(50);

			bot.keyPress(KeyEvent.VK_TAB);
			bot.keyRelease(KeyEvent.VK_TAB);
			bot.delay(50);

			selecao = new StringSelection(insc);
			clipboard.setContents(selecao, null);

			bot.keyPress(KeyEvent.VK_CONTROL);
			bot.keyPress(KeyEvent.VK_V);

			bot.keyRelease(KeyEvent.VK_V);
			bot.keyRelease(KeyEvent.VK_CONTROL);

			bot.delay(50);

			bot.keyPress(KeyEvent.VK_TAB);
			bot.keyRelease(KeyEvent.VK_TAB);
			bot.delay(50);

			bot.keyPress(KeyEvent.VK_DOWN);
			bot.keyRelease(KeyEvent.VK_DOWN);
			bot.delay(50);

			bot.keyPress(KeyEvent.VK_TAB);
			bot.keyRelease(KeyEvent.VK_TAB);
			bot.delay(50);

			bot.keyPress(KeyEvent.VK_TAB);
			bot.keyRelease(KeyEvent.VK_TAB);
			bot.delay(50);

			bot.keyPress(KeyEvent.VK_SPACE);
			bot.keyRelease(KeyEvent.VK_SPACE);

			bot.delay(50);

			bot.keyPress(KeyEvent.VK_TAB);
			bot.keyRelease(KeyEvent.VK_TAB);
			bot.delay(50);

			bot.keyPress(KeyEvent.VK_TAB);
			bot.keyRelease(KeyEvent.VK_TAB);
			bot.delay(50);

			bot.keyPress(KeyEvent.VK_TAB);
			bot.keyRelease(KeyEvent.VK_TAB);
			bot.delay(50);

			bot.keyPress(KeyEvent.VK_ENTER);
			bot.keyRelease(KeyEvent.VK_ENTER);
			bot.keyPress(KeyEvent.VK_ENTER);
			bot.keyRelease(KeyEvent.VK_ENTER);

			bot.delay(2500);

			if (VerificarTexto.naoEncontrado("Resultados!", pastaCompetencia)) {

				bot.keyPress(KeyEvent.VK_ALT);
				bot.keyPress(KeyEvent.VK_F4);

				bot.keyRelease(KeyEvent.VK_F4);
				bot.keyRelease(KeyEvent.VK_ALT);
				
				Status status = new Status(empt, "SEM RESULTADOS", "NOTAS DE SAÍDA - SEFAZ GO");
				StatusDao sdDao = new StatusDao(status);
				sdDao.adicionarStatus();
				
				TabelaProcessamentoDao tpd = new TabelaProcessamentoDao(tb);
				tpd.deletarProcessoTabela();

				return;
			}

			if (VerificarTexto.naoEncontrado("Você não tem permissão", pastaCompetencia)) {

				bot.keyPress(KeyEvent.VK_ALT);
				bot.keyPress(KeyEvent.VK_F4);

				bot.keyRelease(KeyEvent.VK_F4);
				bot.keyRelease(KeyEvent.VK_ALT);
				
				Status status = new Status(empt, "SEM PERMISSÃO", "NOTAS DE SAÍDA - SEFAZ GO");
				StatusDao sdDao = new StatusDao(status);
				sdDao.adicionarStatus();
				
				TabelaProcessamentoDao tpd = new TabelaProcessamentoDao(tb);
				tpd.deletarProcessoTabela();

				return;
			}
			
			if (VerificarTexto.naoEncontrado("é obrigatória", pastaCompetencia)) {

				bot.keyPress(KeyEvent.VK_ALT);
				bot.keyPress(KeyEvent.VK_F4);

				bot.keyRelease(KeyEvent.VK_F4);
				bot.keyRelease(KeyEvent.VK_ALT);
				
				Status status = new Status(empt, "ERRO", "EMPRESA SEM INSCRIÇÃO ESTADUAL");
				StatusDao sdDao = new StatusDao(status);
				sdDao.adicionarStatus();
				
				TabelaProcessamentoDao tpd = new TabelaProcessamentoDao(tb);
				tpd.deletarProcessoTabela();

				return;

			}

			if (VerificarTexto.naoEncontrado("Inscrição Estadual", pastaCompetencia)) {

				bot.keyPress(KeyEvent.VK_ALT);
				bot.keyPress(KeyEvent.VK_F4);

				bot.keyRelease(KeyEvent.VK_F4);
				bot.keyRelease(KeyEvent.VK_ALT);
				
				Status status = new Status(empt, "ERRO DESCONHECIDO", "NOTAS DE SAÍDA - SEFAZ GO");
				StatusDao sdDao = new StatusDao(status);
				sdDao.adicionarStatus();
				
				TabelaProcessamentoDao tpd = new TabelaProcessamentoDao(tb);
				tpd.fimDaFila();

				return;

			}

			int xCoord = 772;
			int yCoord = 701;

			bot.mouseMove(xCoord, yCoord);

			bot.mousePress(InputEvent.BUTTON1_DOWN_MASK);
			bot.mouseRelease(InputEvent.BUTTON1_DOWN_MASK);

			bot.delay(2000);

			bot.keyPress(KeyEvent.VK_TAB);
			bot.keyRelease(KeyEvent.VK_TAB);
			bot.delay(50);
			bot.keyPress(KeyEvent.VK_TAB);
			bot.keyRelease(KeyEvent.VK_TAB);
			bot.delay(50);
			bot.keyPress(KeyEvent.VK_TAB);
			bot.keyRelease(KeyEvent.VK_TAB);
			bot.delay(50);
			bot.keyPress(KeyEvent.VK_TAB);
			bot.keyRelease(KeyEvent.VK_TAB);
			bot.delay(50);
			bot.keyPress(KeyEvent.VK_TAB);
			bot.keyRelease(KeyEvent.VK_TAB);
			bot.delay(50);

			bot.keyPress(KeyEvent.VK_ENTER);
			bot.keyRelease(KeyEvent.VK_ENTER);

			bot.delay(15000);

			selecao = new StringSelection(caminho);
			clipboard.setContents(selecao, null);

			bot.keyPress(KeyEvent.VK_CONTROL);
			bot.keyPress(KeyEvent.VK_V);

			bot.keyRelease(KeyEvent.VK_V);
			bot.keyRelease(KeyEvent.VK_CONTROL);

			bot.delay(1000);

			bot.keyPress(KeyEvent.VK_ENTER);
			bot.keyRelease(KeyEvent.VK_ENTER);

			bot.delay(6000);

			bot.keyPress(KeyEvent.VK_ALT);
			bot.keyPress(KeyEvent.VK_F4);

			bot.keyRelease(KeyEvent.VK_F4);
			bot.keyRelease(KeyEvent.VK_ALT);

			bot.delay(6000);

			Descompactador.descompactarArquivoZip(caminho + ".zip", pastaCompetencia);
			bot.delay(4000);

			ProcessamentoSaida.processarSaidas(pastaCompetencia, empt, tb, cp);
			
			ProcessamentoSaida.deleteFile(caminho + ".zip");

		} catch (AWTException e1) {
			e1.printStackTrace();
		}

	}

}
