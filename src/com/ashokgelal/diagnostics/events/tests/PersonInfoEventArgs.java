package com.ashokgelal.diagnostics.events.tests;

import com.ashokgelal.diagnostics.events.EventArgs;

public class PersonInfoEventArgs extends EventArgs {
	
	private String name;
	private int id;
	
	public PersonInfoEventArgs(String n, int i){
		this.name = n;
		this.id = i;
	}
	public String getName(){
		return name;
	}
	
	public int getId(){
		return id;
	}
}
