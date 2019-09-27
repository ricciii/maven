package com.ecc.app;

import java.util.ArrayList;

public class KeyBank {
	private ArrayList<String> keyList = new ArrayList<String>();
	private int count=0;
	
	public KeyBank() {
	}

	public KeyBank(ArrayList<String> list) {
		this.keyList = list;
	}

	public void add(String string) {
		this.keyList.add(string);
		count++;
	}
	public void remove(String string) {
		this.keyList.remove(string);
		count--;
	}
	public boolean contains(String string) {
		return this.keyList.contains(string);
	}
	public void clear() {
		this.keyList.clear();
		this.count=0;
	}
}