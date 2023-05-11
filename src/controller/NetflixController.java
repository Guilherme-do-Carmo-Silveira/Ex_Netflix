package controller;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;

import docarmo.FilaObject.Fila;
import model.serie;

public class NetflixController implements INetflixController {

	public NetflixController() {
		super();
	}

	public void MajorGenre() {

		try {
			GeraFila();
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	private void GeraFila() throws IOException {

		File arq = new File("C:\\TEMP\\NetflixSeries.csv");
		int ctd = 0;
		if (arq.exists() && arq.isFile()) {
			FileInputStream fluxo = new FileInputStream(arq);
			InputStreamReader leitorfluxo = new InputStreamReader(fluxo);
			BufferedReader buffer = new BufferedReader(leitorfluxo);
			String linha = buffer.readLine();
			linha = buffer.readLine();
			while (linha != null) {
				String vtlinha[] = linha.split(";");
				String genero = vtlinha[0];
				Fila fila = new Fila();
				while (genero.contains(vtlinha[0]) && linha != null) {
					serie s = new serie();
					s.major_genre = vtlinha[0];
					s.title = vtlinha[1];
					s.subgenre = vtlinha[2];
					s.premiere_year = Integer.parseInt(vtlinha[4]);
					s.episodes = (vtlinha[10]);
					s.status = vtlinha[6];
					s.imdb_rating = Integer.parseInt(vtlinha[11]);
					fila.insert(s);
					linha = buffer.readLine();
					if (linha != null) {
						vtlinha = linha.split(";");
					}
				}
				GeraArquivo(fila, genero);
			}
		} else {
			throw new IOException("Arquivo invalido");
		}
	}

	private void GeraArquivo(Fila fila, String genero) throws IOException {

		File file = new File("C:\\Temp", genero + ".csv");
		StringBuffer buffer = new StringBuffer();
		int tam = fila.size();

		for (int i = 0; i < tam; i++) {
			serie s = new serie();
			try {
				if(i == 0) {
					buffer.append("Genero principal; Titulo; Subgenero; Ano; Episodios; Status; Nota" + "\n");
					FileWriter filewrite = new FileWriter(file);
					PrintWriter print = new PrintWriter(filewrite);
					print.write(buffer.toString());
					print.flush();
					print.close();
				}
				s = (serie) fila.remove();
				buffer.append(s);
				FileWriter filewrite = new FileWriter(file);
				PrintWriter print = new PrintWriter(filewrite);
				print.write(buffer.toString());
				print.flush();
				print.close();
				filewrite.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
}
