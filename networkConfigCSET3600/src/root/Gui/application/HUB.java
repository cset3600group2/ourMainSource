package root.Gui.application;

import java.util.TreeSet;

public class HUB {
	private String subnet;
	private String netmask;
	private String name;
	private int posX, posY;
	private TreeSet<String> inf = new TreeSet<String>();
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getSubnet() {
		return subnet;
	}

	public void setSubnet(String subnet) {
		this.subnet = subnet;
	}

	public String getNetmask() {
		return netmask;
	}

	public void setNetmask(String netmask) {
		this.netmask = netmask;
	}

	public TreeSet<String> getInfs() {
		return inf;
	}

	public void setInfs(TreeSet<String> input) {
		this.inf = input;
	}

	public void addInf(String input) {
		inf.add(input);
	}

	public int getPosY() {
		return posY;
	}

	public void setPosY(int posY) {
		this.posY = posY;
	}

	public int getPosX() {
		return posX;
	}

	public void setPosX(int posX) {
		this.posX = posX;
	}
	
	public void removeInf(String inf) {
		this.inf.remove(inf);
	}
}
