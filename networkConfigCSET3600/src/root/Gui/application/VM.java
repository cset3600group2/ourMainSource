package root.Gui.application;

import java.util.TreeMap;

public class VM {
	private String name;
	private String os;
	private String src;
	private Double ver;
	private int posX, posY;
	private TreeMap<String, String> interfaces = new TreeMap<String, String>();


	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getSrc() {
		return src;
	}

	public void setSrc(String src) {
		this.src = src;
	}

	public Double getVer() {
		return ver;
	}

	public void setVer(Double ver) {
		this.ver = ver;
	}

	public String getOs() {
		return os;
	}

	public void setOs(String os) {
		this.os = os;
	}

	public TreeMap<String, String> getInterfaces() {
		return this.interfaces;
	}

	public void setInterfaces(TreeMap<String, String> interfaces) {
		this.interfaces = interfaces;
	}

	public void addInterface(String key, String value) {
		this.interfaces.put(key, value);
	}

	public int getPosY() {
		return this.posY;
	}

	public void setPosY(int posY) {
		this.posY = posY;
	}

	public int getPosX() {
		return this.posX;
	}

	public void setPosX(int posX) {
		this.posX = posX;
	}
	
	public void removeInf(String inf) {
		this.getInterfaces().remove(inf);
	}
}
