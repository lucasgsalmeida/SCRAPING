package com.AWN.model;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

public class Descompactador {

	public static void descompactarArquivoZip(String caminhoArquivoZip, String destinoExtracao) throws IOException {
		byte[] buffer = new byte[1024];

		File cam = new File(caminhoArquivoZip);

		if (!cam.exists()) {
			return;
		}

		try (ZipInputStream zipInputStream = new ZipInputStream(new FileInputStream(caminhoArquivoZip))) {

			ZipEntry zipEntry = zipInputStream.getNextEntry();

			while (zipEntry != null) {
				String nomeArquivo = zipEntry.getName();
				String caminhoCompleto = destinoExtracao + File.separator + nomeArquivo;

				if (!new File(caminhoCompleto).toPath().normalize().startsWith(new File(destinoExtracao).toPath())) {
					throw new IOException("Arquivo ZIP contém entradas com caminhos inseguros!");
				}

				if (zipEntry.isDirectory()) {
					new File(caminhoCompleto).mkdirs();
				} else {
					try (FileOutputStream fileOutputStream = new FileOutputStream(caminhoCompleto)) {
						int len;
						while ((len = zipInputStream.read(buffer)) > 0) {
							fileOutputStream.write(buffer, 0, len);
						}
					}
				}

				zipEntry = zipInputStream.getNextEntry();
			}

			System.out.println("Arquivo ZIP descompactado com sucesso!");

		} catch (IOException e) {
			e.printStackTrace();
			throw e;
		}
	}

}
