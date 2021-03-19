package Application;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Program {

	public static void main(String[] args) {

		Scanner sc = new Scanner(System.in);

		System.out.print("Digite o caminho da pasta contendo os jsons: ");
		String pathIn = sc.nextLine();
		boolean success = new File(pathIn + "\\ExtractOutput").mkdir();
		System.out.print("Digite o caminho da pasta contendo as midias: ");
		String media = sc.nextLine();
		String pathOut = null;

		if (success == true) {
			pathOut = pathIn + "\\ExtractOutput";
		} else {
			System.out.println("Digite o caminha da pasta de saida: ");
			pathOut = sc.nextLine();
		}

		File path = new File(pathIn);
		File[] files = path.listFiles(File::isFile);
		int j = 0, k = 0;

		try {
			BufferedReader br = null;
			String currentPath, line, currentName, currentOut;
			for (File f : files) {

				currentPath = f.getPath();
				currentName = f.getName();
				int pos = currentName.lastIndexOf(".");
				currentName = currentName.substring(0, pos);
				br = new BufferedReader(new FileReader(currentPath));

				int i = 1;

				List<File> listFiles = new ArrayList<>();
				listFiles.clear();

				line = br.readLine();
				while (line != null) {

					if (line.contains("AssetPathName")) {
						line = findString(line);

						File audioFile = new File(media + "\\" + line + ".wav");

						listFiles.add(audioFile);
					}

					if (line.equals("]")) {
						for (File fi : listFiles) {
							currentOut = pathOut + "\\" + currentName + i + ".wav";
							if (fi.exists()) {
								fi.renameTo(new File(currentOut));
								k++;
								i++;
							} else
								j++;

						}
					}

					line = br.readLine();
				}
			}

		} catch (IOException e) {
			System.out.println("Error: " + e.getMessage());
		}
		System.out.println("Arquivos transferidos: " + k);
		System.out.println("Aquivos não encontrados: " + j);
		System.out.println("Programa encerrado.");
		sc.close();
	}

	public static String findString(String line) {
		String[] lines;
		line = line.trim();
		int startIndex = (line.lastIndexOf('/') + 1);
		int endIndex = line.lastIndexOf('"');
		line = line.substring(startIndex, endIndex);
		line = line.replace('.', '#');
		lines = line.split("#");
		return lines[0];
	}
}
