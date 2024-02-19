package com.AWN.model;

import java.awt.AWTException;
import java.awt.Robot;
import java.awt.Toolkit;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.Transferable;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;
import java.util.List;

public class VerificarTexto {

	public static boolean naoEncontrado(List<String> textosProcurados, String caminho) {

		try {
			Robot robot = new Robot();

			robot.keyPress(KeyEvent.VK_CONTROL);
			robot.keyPress(KeyEvent.VK_A);
			robot.keyRelease(KeyEvent.VK_A);
			robot.keyRelease(KeyEvent.VK_CONTROL);

			Thread.sleep(200);

			robot.keyPress(KeyEvent.VK_CONTROL);
			robot.keyPress(KeyEvent.VK_C);
			robot.keyRelease(KeyEvent.VK_C);
			robot.keyRelease(KeyEvent.VK_CONTROL);

			Thread.sleep(500);

			int xCoord = 15;
			int yCoord = 112;

			robot.mouseMove(xCoord, yCoord);

			robot.mousePress(InputEvent.BUTTON1_DOWN_MASK);
			robot.mouseRelease(InputEvent.BUTTON1_DOWN_MASK);

			String textoCopiado = getClipboardContents();

			int i = 0;
			int j = 0;

			for (String textoProcurado : textosProcurados) {
				if (textoCopiado.contains(textoProcurado)) {
					i++;
				} else {
					j++;

				}
			}

			if (i > 0) {
				return true;
			}

			if (j > 0) {
				return false;
			}

		} catch (AWTException | InterruptedException e) {
			e.printStackTrace();
		}
		return false;

	}

	public static boolean naoEncontrado(String textoProcurado, String caminho) {

		try {
			Robot robot = new Robot();

			robot.keyPress(KeyEvent.VK_CONTROL);
			robot.keyPress(KeyEvent.VK_A);
			robot.keyRelease(KeyEvent.VK_A);
			robot.keyRelease(KeyEvent.VK_CONTROL);

			Thread.sleep(200);

			robot.keyPress(KeyEvent.VK_CONTROL);
			robot.keyPress(KeyEvent.VK_C);
			robot.keyRelease(KeyEvent.VK_C);
			robot.keyRelease(KeyEvent.VK_CONTROL);

			Thread.sleep(500);

			int xCoord = 1234;
			int yCoord = 150;

			robot.mouseMove(xCoord, yCoord);

			robot.mousePress(InputEvent.BUTTON1_DOWN_MASK);
			robot.mouseRelease(InputEvent.BUTTON1_DOWN_MASK);

			String textoCopiado = getClipboardContents();

			if (textoCopiado.contains(textoProcurado)) {
				return true;
			} else {
				return false;

			}

		} catch (AWTException | InterruptedException e) {
			e.printStackTrace();
		}
		return false;

	}

	public static boolean naoEncontrado(String textoProcurado) {

		try {
			Robot robot = new Robot();

			robot.keyPress(KeyEvent.VK_CONTROL);
			robot.keyPress(KeyEvent.VK_A);
			robot.keyRelease(KeyEvent.VK_A);
			robot.keyRelease(KeyEvent.VK_CONTROL);

			Thread.sleep(200);

			robot.keyPress(KeyEvent.VK_CONTROL);
			robot.keyPress(KeyEvent.VK_C);
			robot.keyRelease(KeyEvent.VK_C);
			robot.keyRelease(KeyEvent.VK_CONTROL);

			Thread.sleep(500);

			int xCoord = 15;
			int yCoord = 112;

			robot.mouseMove(xCoord, yCoord);

			robot.mousePress(InputEvent.BUTTON1_DOWN_MASK);
			robot.mouseRelease(InputEvent.BUTTON1_DOWN_MASK);

			String textoCopiado = getClipboardContents();

			if (textoCopiado.contains(textoProcurado)) {
				System.out.println("Palavra " + textoProcurado + " encontrada!");
				return true;

			} else {
				System.out.println("Palavra " + textoProcurado + " não encontrada!");
				return false;

			}

		} catch (AWTException | InterruptedException e) {
			e.printStackTrace();
		}
		return false;

	}

	public static boolean naoEncontradoInicioExecucao(String textoProcurado) {

		try {
			Robot robot = new Robot();

			int xCoord = 607;
			int yCoord = 177;

			robot.mouseMove(xCoord, yCoord);

			robot.mousePress(InputEvent.BUTTON1_DOWN_MASK);
			robot.mouseRelease(InputEvent.BUTTON1_DOWN_MASK);

			robot.delay(200);

			robot.keyPress(KeyEvent.VK_CONTROL);
			robot.keyPress(KeyEvent.VK_A);
			robot.keyRelease(KeyEvent.VK_A);
			robot.keyRelease(KeyEvent.VK_CONTROL);

			Thread.sleep(200);

			robot.keyPress(KeyEvent.VK_CONTROL);
			robot.keyPress(KeyEvent.VK_C);
			robot.keyRelease(KeyEvent.VK_C);
			robot.keyRelease(KeyEvent.VK_CONTROL);

			Thread.sleep(500);

			robot.mouseMove(xCoord, yCoord);

			robot.mousePress(InputEvent.BUTTON1_DOWN_MASK);
			robot.mouseRelease(InputEvent.BUTTON1_DOWN_MASK);

			String textoCopiado = getClipboardContents();

			if (textoCopiado.contains(textoProcurado)) {
				return true;

			} else {
				return false;

			}

		} catch (AWTException | InterruptedException e) {
			e.printStackTrace();
		}
		return false;

	}

	private static String getClipboardContents() {
		Transferable t = Toolkit.getDefaultToolkit().getSystemClipboard().getContents(null);
		if (t != null && t.isDataFlavorSupported(DataFlavor.stringFlavor)) {
			try {
				return (String) t.getTransferData(DataFlavor.stringFlavor);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return "";
	}

}
