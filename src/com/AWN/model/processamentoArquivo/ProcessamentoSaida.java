package com.AWN.model.processamentoArquivo;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import com.AWN.model.Empresas;
import com.AWN.model.Status;
import com.AWN.model.TabelaProcessamento;
import com.AWN.model.api_dominio.EnviarParaApi;
import com.AWN.model.api_dominio.VerificarToken;
import com.AWN.model.dao.StatusDao;
import com.AWN.model.dao.TabelaProcessamentoDao;
import com.AWN.model.files.Pasta;
import com.AWN.model.files.PastaFunctions;
import com.AWN.model.processamentoArquivo.dao.DominioCheck;
import com.AWN.model.processamentoArquivo.dao.NfcSaidaDao;
import com.AWN.model.processamentoArquivo.dao.NfeSaidaDao;

public class ProcessamentoSaida {

	public static String caminhoCopia;
	public static int qtdNotas = 0;

	public static void processarSaidas(String caminho, Empresas emp, TabelaProcessamento tb, Pasta cp) {
		File diretorio = new File(caminho);
		qtdNotas = 0;

		if (!diretorio.isDirectory()) {
			System.out.println("O caminho especificado não é um diretório válido.");
			return;
		}

		File[] arquivos = diretorio.listFiles((dir, nome) -> nome.toLowerCase().endsWith(".xml"));

		System.out.println(arquivos.toString());
		if (arquivos != null) {
			for (File arquivo : arquivos) {
				VerificarToken.verificarValidadeToken();

				copyFile(arquivo);
				try {
					System.out.println("Começou a processar: " + arquivo.getName());
					processarArquivoXML(arquivo, emp, cp);
					System.out.println("Concluiu o processamento: " + arquivo.getName());
				} catch (Exception e) {
					System.err.println("Erro ao processar o arquivo " + arquivo.getName() + ": " + e.getMessage());
				}
				deleteFile(caminhoCopia);
			}
			
			Status sta = new Status(emp, "NOTAS DE SAÍDA BAIXADAS COM SUCESSO", "Total: " + qtdNotas);
			StatusDao std = new StatusDao(sta);
			std.adicionarStatus();
			
			TabelaProcessamentoDao tpd = new TabelaProcessamentoDao(tb);
			tpd.deletarProcessoTabela();

		}
	}

	public static String copyFile(File sourceFile) {
		try {
			Path sourcePath = sourceFile.toPath();

			String fileName = sourcePath.getFileName().toString();

			Path destinationPath = Path.of("C:\\Windows\\Temp", fileName);

			Files.copy(sourcePath, destinationPath, StandardCopyOption.REPLACE_EXISTING);

			caminhoCopia = destinationPath.toString();
			return destinationPath.toString();
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}
	}

	public static void deleteFile(String filePath) {
		try {
			Path path = Paths.get(filePath);
			Files.deleteIfExists(path);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private static void processarArquivoXML(File arquivo, Empresas emp, Pasta cp) {
		try {
			System.out.println("Processando arquivo: " + arquivo.getName());
			DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
			Document doc = dBuilder.parse(arquivo);

			doc.getDocumentElement().normalize();
			
			String primeiraTag = doc.getDocumentElement().getNodeName();
			
			if (!primeiraTag.equalsIgnoreCase("nfeProc")) {
				PastaFunctions.moverArquivo(arquivo, cp.getPastaCompetencia());
				return;
			}

			String ide_nnf2 = findTagValue(doc.getDocumentElement(), "ide", "nNF");
			Integer ide_nnf = Integer.parseInt(ide_nnf2);
			String mod = findTagValue(doc.getDocumentElement(), "ide", "mod");
			String vNF = findTagValue(doc.getDocumentElement(), "vNF");
			Double vNF2 = Double.parseDouble(vNF);
			String dEmi = findTagValue(doc.getDocumentElement(), "dhEmi");
			String chNFe = findTagValue(doc.getDocumentElement(), "chNFe");
			String emit_cnpj = findTagValue(doc.getDocumentElement(), "emit", "CNPJ");
			String emit_xnome = findTagValue(doc.getDocumentElement(), "emit", "xNome");
			String dest_cnpj1 = findTagValue(doc.getDocumentElement(), "dest", "CNPJ");
			String dest_cpf1 = findTagValue(doc.getDocumentElement(), "dest", "CPF");
			String dest_xnome = findTagValue(doc.getDocumentElement(), "dest", "xNome");
			String cStat = findTagValue(doc.getDocumentElement(), "cStat");
			String temNotaDominio = DominioCheck.temNotaDominioSaida(chNFe);
			String dest_cnpj = "";

			File fl = new File(caminhoCopia);
			EnviarParaApi.enviarNotasApi(fl, emp.getCodigo(), chNFe);

			if (dest_cnpj1 != null && !dest_cnpj1.isEmpty()) {
				dest_cnpj = dest_cnpj1;
			} else if (dest_cpf1 != null && !dest_cpf1.isEmpty()) {
				dest_cnpj = dest_cpf1;
			}

			if (mod.equalsIgnoreCase("55")) {
				qtdNotas++;
				NfeSaidaDao.adicionarNFE(emp.getCodigo(), ide_nnf, vNF2, dEmi, chNFe, emit_cnpj, emit_xnome, dest_cnpj,
						dest_xnome, cStat, temNotaDominio);
				PastaFunctions.moverArquivo(arquivo, cp.getPastaCompetencia());


			}
			if (mod.equalsIgnoreCase("65")) {
				qtdNotas++;
				NfcSaidaDao.adicionarNFC(emp.getCodigo(), ide_nnf, vNF2, dEmi, chNFe, emit_cnpj, emit_xnome, dest_cnpj,
						dest_xnome, cStat, temNotaDominio);
				PastaFunctions.moverArquivo(arquivo, cp.getPastaCompetencia());

				
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private static String findTagValue(Node node, String parentTagName, String childTagName) {
		if (node.getNodeType() == Node.ELEMENT_NODE) {
			if (node.getNodeName().equals(parentTagName)) {
				NodeList nodeList = node.getChildNodes();
				for (int i = 0; i < nodeList.getLength(); i++) {
					Node childNode = nodeList.item(i);
					if (childNode.getNodeType() == Node.ELEMENT_NODE && childNode.getNodeName().equals(childTagName)) {
						return childNode.getTextContent().trim();
					}
				}
			}

			NodeList nodeList = node.getChildNodes();
			for (int i = 0; i < nodeList.getLength(); i++) {
				String result = findTagValue(nodeList.item(i), parentTagName, childTagName);
				if (result != null) {
					return result;
				}
			}
		}

		return null;
	}

	private static String findTagValue(Node node, String tagName) {
		if (node.getNodeType() == Node.ELEMENT_NODE) {
			if (node.getNodeName().equals(tagName)) {
				return node.getTextContent().trim();
			}

			NodeList nodeList = node.getChildNodes();
			for (int i = 0; i < nodeList.getLength(); i++) {
				String result = findTagValue(nodeList.item(i), tagName);
				if (result != null) {
					return result;
				}
			}
		}

		return null;
	}

}
