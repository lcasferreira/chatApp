package com.lc.chatsrv;

import java.io.IOException;
import java.io.PrintStream;
import java.net.Socket;
import java.util.Scanner;

import com.lc.chatsrv.controller.ChatServerController;
import com.lc.chatsrv.enums.ChatCommand;

public class MessageHandle implements Runnable {

	private Socket cliente;
	private ChatServerController chatServer;

	public MessageHandle(Socket cliente, ChatServerController server) {
		this.cliente = cliente;
		this.chatServer = server;
	}

	public void run() {
		
		try {
			Scanner streamIn = new Scanner(this.cliente.getInputStream());
			
			String msg = "";
			while (streamIn.hasNextLine()) {
				msg = streamIn.nextLine();
				
				if(msg.startsWith(ChatCommand.REGISTER.getCommand())){
					StringBuilder regMsg = new StringBuilder(msg.replace(ChatCommand.REGISTER.getCommand(), "").trim());
					chatServer.addClient(regMsg.toString(), new PrintStream(cliente.getOutputStream()));
					regMsg.append(" entrou na conversa\n");
					chatServer.broadcastMessage(regMsg.toString());
				} else if(msg.contains(ChatCommand.DIRECT.getCommand())){
					String dest = msg.substring(msg.indexOf(ChatCommand.DIRECT.getCommand()), msg.length());
					dest = dest.substring(0, dest.indexOf(" "));
					dest = dest.replace(ChatCommand.DIRECT.getCommand(), "");
					chatServer.sendDirectMessage(dest, msg);
				
				} else if(msg.contains(ChatCommand.DIRECT.getCommand())){
					chatServer.finish();
				} else {
					chatServer.broadcastMessage(msg);
				}
			}
			streamIn.close();
			
		} catch (IOException e) {
			System.out.println("Erro ao enviar mensagem." + cliente.getInetAddress());
		}
	}
}
