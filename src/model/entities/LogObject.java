package model.entities;

public class LogObject {

	private int i, j, k;
	private String currentName, fileName;

	public LogObject(){
		i = 0;
		j = 0; 
		k = 0;
		currentName = null;
		fileName = null;
	}
	public LogObject(int i, int j, int k, String currentName, String fileName) {
		this.i = i;
		this.j = j;
		this.k = k;
		this.currentName = currentName;
		this.fileName = fileName;
	}

	public int getI() {
		return i;
	}

	public void setI(int i) {
		this.i = i;
	}

	public int getJ() {
		return j;
	}

	public void setJ(int j) {
		this.j = j;
	}

	public int getK() {
		return k;
	}

	public void setK(int k) {
		this.k = k;
	}

	public String getCurrentName() {
		return currentName;
	}

	public void setCurrentName(String currentName) {
		this.currentName = currentName;
	}

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public void addI() {
		this.i = i+1;
	}

	public void addJ() {
		this.j = j+1;
	}

	public void addK() {
		this.k = k+1;
	}
	public String toString() {
		return currentName + i + " => " + fileName;
	}

}
