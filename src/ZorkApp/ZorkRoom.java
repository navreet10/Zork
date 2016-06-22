package ZorkApp;

import java.util.Map;

public class ZorkRoom{
	private int num;
	private String name;
	private String desc;
	private int money;	
	private Map<Character,Integer> goesTo;
	
	public ZorkRoom(int num, String name, String desc, int money, Map<Character,Integer> goesTo) {
		this.setNum(num);
		this.name = name;
		this.desc= desc;
		this.money = money;
		this.goesTo = goesTo;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getDesc() {
		return desc;
	}
	public void setDesc(String desc) {
		this.desc = desc;
	}
	public int getMoney() {
		return money;
	}
	public void setMoney(int money) {
		this.money = money;
	}
	public Map<Character, Integer> getGoesTo() {
		return goesTo;
	}
	public void setGoesTo(Map<Character, Integer> goesTo) {
		this.goesTo = goesTo;
	}
	public int getNum() {
		return num;
	}
	public void setNum(int num) {
		this.num = num;
	}

	
}