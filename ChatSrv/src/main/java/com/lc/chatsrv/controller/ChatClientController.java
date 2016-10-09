package com.lc.chatsrv.controller;

import java.io.IOException;
import java.io.PrintStream;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Scanner;

import com.lc.chatsrv.enums.ChatCommand;

import lombok.Getter;
import lombok.Setter;

public class ChatClientController {

	private String nickName;
	private String host;
	private int porta;

	@Getter
	private Socket client;
	private PrintStream streamOut;
	private Scanner keyboard;
	private boolean registered = false;
	
	@Setter
	private Thread threadReceiver;

	public ChatClientController(String nickName, String host, int porta) {
		this.nickName = nickName;
		this.host = host;
		this.porta = porta;
	}

	public void start() {
		try {
			client = new Socket(this.host, this.porta);
			
		} catch (UnknownHostException e) {
			System.out.println("Servidor não encontrado!");
			
		} catch (IOException e) {
			System.out.println("Erro inesperado!");
		}

		System.out.println("Conectado ao servidor! " + this.host);
		
//		try {
//			ClientReceiver receiver = new ClientReceiver(client.getInputStream());
//			threadReceiver = new Thread(receiver);
//			threadReceiver.start();
//		} catch (IOException e) {
//			System.out.println("Erro ao receber mensagem");
//		}

		try {

			streamOut = new PrintStream(client.getOutputStream());
			
			StringBuilder message = new StringBuilder();
			if(!registered){
				message.append(ChatCommand.REGISTER.getCommand()).append(" ");
				message.append(this.nickName);
				streamOut.println(message);
				registered = true;
				message = new StringBuilder();
			}
			
		} catch (IOException e) {
			System.out.println("Erro ao enviar mensagem.");
		}
	}
	
	public void sendMessage(String message){
		
		if(message.startsWith(ChatCommand.QUIT.getCommand())){
			this.stop();
		}
		
		StringBuilder msg = new StringBuilder(this.nickName).append(": ");
		msg.append(message);
		streamOut.println(msg.toString());
	}
	
	private void stop(){
		if(streamOut != null){
			streamOut.close();
		}
		if(keyboard != null){
			keyboard.close();
		}
		try {
			client.close();
		} catch (IOException e) {
			System.out.println("Erro ao encerrar cliente!");
		}
		if(threadReceiver != null){
			threadReceiver.interrupt();
			threadReceiver = null;
		}
		System.exit(0);
	}

	public String getNickName() {
		return nickName;
	}
}
