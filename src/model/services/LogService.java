package model.services;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

import model.entities.LogObject;

public class LogService {

	private LogObject obj;
	private String pathOut;
	private int flag = 0;

	public LogService(LogObject obj, String pathOut) {
		this.obj = obj;
		this.pathOut = pathOut;
	}

	public void addToLog() {
		try (BufferedWriter bw = new BufferedWriter(new FileWriter(pathOut + "\\log.txt", true))) {
			if (flag == 0) {
				bw.write(obj.toString());
				bw.newLine();
			}
			else {
				bw.write("Transfered files: " + obj.getK());
				bw.newLine();
				bw.write("Files not found: " + obj.getJ());
			}
		} catch (IOException e) {
			System.out.println("Error: " + e.getMessage());
		}
	}

	public void setFlag(int flag) {
		this.flag = flag;
	}

	public LogObject getObj() {
		return obj;
	}
	

}
