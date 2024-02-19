package com.AWN.controller.execucao.sefazGo;

import java.awt.AWTException;
import java.awt.Desktop;
import java.awt.Robot;
import java.awt.Toolkit;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.StringSelection;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import com.AWN.model.VerificarTexto;

public class SefazGO_Portal {

	public static void abrirPortal() throws IOException, AWTException {

		String url = "https://www.sefaz.go.gov.br/netaccess/000System/acessoRestrito/";
		String senha = "InsiraASenhaAqui";
		boolean valorBooleano = true;
		Robot bot = null;

		try {
			bot = new Robot();

			while (valorBooleano) {

				if (Desktop.isDesktopSupported()) {
					Desktop desktop = Desktop.getDesktop();
					if (desktop.isSupported(Desktop.Action.BROWSE)) {
						desktop.browse(new URI(url));
					} else {
						System.out.println("Navegação não suportada no navegador padrão.");
					}
				} else {
					System.out.println("Área de trabalho não suportada no sistema operacional.");
				}

				bot.delay(5000);

				bot.keyPress(KeyEvent.VK_ENTER);
				bot.keyRelease(KeyEvent.VK_ENTER);

				bot.delay(6000);

				int x = 750;
				int y = 290;

				bot.mouseMove(x, y);

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
				bot.keyPress(KeyEvent.VK_TAB);
				bot.keyRelease(KeyEvent.VK_TAB);
				bot.delay(50);
				bot.keyPress(KeyEvent.VK_TAB);
				bot.keyRelease(KeyEvent.VK_TAB);
				bot.delay(50);

				bot.keyPress(KeyEvent.VK_ENTER);
				bot.keyRelease(KeyEvent.VK_ENTER);

				bot.delay(2000);

				bot.keyPress(KeyEvent.VK_TAB);
				bot.keyRelease(KeyEvent.VK_TAB);

				bot.delay(50);

				StringSelection selecao = new StringSelection(senha);
				Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
				clipboard.setContents(selecao, null);

				bot.keyPress(KeyEvent.VK_CONTROL);
				bot.keyPress(KeyEvent.VK_V);

				bot.keyRelease(KeyEvent.VK_V);
				bot.keyRelease(KeyEvent.VK_CONTROL);

				bot.delay(50);

				bot.keyPress(KeyEvent.VK_ENTER);
				bot.keyRelease(KeyEvent.VK_ENTER);

				bot.delay(6000);

				if (VerificarTexto.naoEncontrado("AIDF Eletronica")) {

					break;

				} else {

					bot.keyPress(KeyEvent.VK_ALT);
					bot.keyPress(KeyEvent.VK_F4);

					bot.keyRelease(KeyEvent.VK_F4);
					bot.keyRelease(KeyEvent.VK_ALT);

					continue;
				}
			}

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
			bot.keyPress(KeyEvent.VK_TAB);
			bot.keyRelease(KeyEvent.VK_TAB);
			bot.delay(50);
			bot.keyPress(KeyEvent.VK_TAB);
			bot.keyRelease(KeyEvent.VK_TAB);
			bot.delay(50);

			bot.keyPress(KeyEvent.VK_ENTER);
			bot.keyRelease(KeyEvent.VK_ENTER);

			bot.delay(2000);

		} catch (IOException | URISyntaxException e) {
			System.out.println("Erro ao tentar abrir o site: " + e.getMessage());
		}
	}
}