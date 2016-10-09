package com.lc.chatsrv.model;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter 
public class ChatSettings {

	public enum Type{
		SERVER,
		CLIENT;
	}
	
	private String name;
	private Type type;
	private int port;
	private String host;
	
}
