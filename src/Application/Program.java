package application;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import model.entities.LogObject;
import model.services.LogService;
import model.services.WavRenameService;

public class Program {

	public static void main(String[] args) {

		Scanner sc = new Scanner(System.in);
		LogObject logObj = new LogObject();

		System.out.print("Program mode (R/L): ");
		String mode = sc.next();
		mode = mode.toLowerCase();
		
		sc.nextLine();
		System.out.print("Type the directory of the json files: ");
		String pathIn = sc.nextLine();

		new File(pathIn + "\\ExtractOutput").mkdir();
		String pathOut = pathIn + "\\ExtractOutput";

		System.out.print("Type the directory of the media files: ");
		String media = sc.nextLine();

		LogService ls = new LogService(logObj, pathOut);
		WavRenameService wrs = new WavRenameService(ls);

		File path = new File(pathIn);
		File[] files = path.listFiles(File::isFile);
		logObj.setJ(0);
		logObj.setK(0);

		try {
			BufferedReader br = null;

			String currentPath, line, currentName;
			for (File f : files) {

				currentPath = f.getPath();
				currentName = f.getName();
				int pos = currentName.lastIndexOf(".");
				currentName = currentName.substring(0, pos);
				br = new BufferedReader(new FileReader(currentPath));

				logObj.setI(1);
				logObj.setCurrentName(currentName);

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
						switch (mode.charAt(0)) {
						case 'r':
							wrs.renameFiles(listFiles, pathOut);
							break;
						case 'l':
							for (File fi : listFiles) {
								logObj.setFileName(fi.getName() + "/.uasset");
								ls.addToLog();
							}
							break;
						default:
							throw new IllegalArgumentException("Invalid option.");
						}
					}
					line = br.readLine();
				}
			}
			
			if(mode.equals("r")) {
			ls.setFlag(1);
			ls.addToLog();
			}
			
			br.close();
		} catch (IOException e) {
			System.out.println("Error: " + e.getMessage());
		} catch (IllegalArgumentException e) {
			System.out.println(e.getMessage());
		}
		
		System.out.println("Program end.");
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
