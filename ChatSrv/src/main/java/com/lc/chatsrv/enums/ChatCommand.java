package com.lc.chatsrv.enums;

public enum ChatCommand {

	QUIT(".quit"),
	REGISTER(".register"),
	DIRECT(".direct:"),
	FINISH_SERVER(".finish");
	
	String command;
	
	private ChatCommand(String cmd) {
		this.command = cmd;
	}
	
	public String getCommand() {
		return command;
	}
}
