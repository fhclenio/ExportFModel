package Application;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Program {

	public static void main(String[] args) {

		Scanner sc = new Scanner(System.in);

		System.out.print("Type the directory of the json files: ");
		String pathIn = sc.nextLine();
		
		String pathOut =  pathIn + "\\ExtractOutput";
		File outFile = new File(pathOut);
		boolean success = outFile.mkdir();
		if (success == false) {
			outFile.delete();
			outFile.mkdir();
		}
		
		System.out.print("Type the directory of the media files: ");
		String media = sc.nextLine();
		

		File path = new File(pathIn);
		File[] files = path.listFiles(File::isFile);
		int j = 0, k = 0;

		try {
			BufferedReader br = null;
			BufferedWriter bw = new BufferedWriter(new FileWriter(pathOut + "\\log.txt", true));
			
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
				File audioFile = null;
				line = br.readLine();
				while (line != null) {

					if (line.contains("AssetPathName")) {
						line = findString(line);

						audioFile = new File(media + "\\" + line + ".wav");

						listFiles.add(audioFile);
					}

					if (line.equals("]")) {
						for (File fi : listFiles) {
							currentOut = pathOut + "\\" + currentName + i + ".wav";
							if (fi.exists()) {
								fi.renameTo(new File(currentOut));
								StringBuilder strings = new StringBuilder();
								strings.append(fi.getName() + " is " + currentName + i + ".wav");
								bw.write(strings.toString());
								bw.newLine();
								k++;
								i++;
							} else
								j++;

						}
					}

					line = br.readLine();
				}
			}
			
			bw.close();
			br.close();
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
