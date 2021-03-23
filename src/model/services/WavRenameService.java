package model.services;

import java.io.File;
import java.util.List;

import model.entities.LogObject;

public class WavRenameService {
	private LogService ls;
	
	public WavRenameService(LogService ls) {
		this.ls = ls;
		
	}
	
	public void renameFiles(List<File> files, String pathOut) {
		LogObject logObj = ls.getObj();
		String currentOut;
		for (File fi : files) {
			logObj.setFileName(fi.getName());
			currentOut = pathOut + "\\" + logObj.getCurrentName() + logObj.getI() + ".wav";
			if (fi.exists()) {
				fi.renameTo(new File(currentOut));
				ls.addToLog();
				logObj.addK();
				logObj.addI();
			} else
				logObj.addJ();
		}
		
	}


}
