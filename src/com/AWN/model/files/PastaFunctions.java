package com.AWN.model.files;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public class PastaFunctions {

	public static void moverArquivo(File arquivo, File destino2) {
		if (arquivo.exists()) {
			try {
				Path origem = arquivo.toPath();
				Path destino = destino2.toPath().resolve(arquivo.getName());

				Files.move(origem, destino, java.nio.file.StandardCopyOption.REPLACE_EXISTING);

				System.out.println("Arquivo movido com sucesso!");
			} catch (IOException e) {
				e.printStackTrace();
				System.out.println("Falha ao mover o arquivo.");
			}
		} else {
			System.out.println("Arquivo não encontrado.");
		}
	}

}
