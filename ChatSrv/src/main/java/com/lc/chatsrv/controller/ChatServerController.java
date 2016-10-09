package com.lc.chatsrv.controller;

import java.io.IOException;
import java.io.PrintStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;

import com.lc.chatsrv.MessageHandle;

public class ChatServerController {

	private ServerSocket servidor;
	private Map<String, PrintStream> clients;
	private int port;

	public ChatServerController(int porta) {
		this.port = porta;
		this.clients = new HashMap<String, PrintStream>();
	}

	public void start() throws IOException {
		servidor = new ServerSocket(this.port);
		System.out.println("Estabelecendo conexção na porta " + this.port + "!");

		while (true) {
			Socket cliente = servidor.accept();
			System.out.println("Nova conexão com o cliente " + cliente.getInetAddress().getHostAddress());

			MessageHandle msgHandle = new MessageHandle(cliente, this);
			new Thread(msgHandle).start();
		}
	}
	
	public void finish(){
		if(servidor != null){
			try {
				servidor.close();
			} catch (IOException e) {
				System.out.println("Falha ao finalizar servidor!");
			}
		}
		System.exit(0);
	}
	
	public void addClient(String nickName, PrintStream streamOut){
		this.clients.put(nickName, streamOut);
	}
	
	public void broadcastMessage(String msg) {
		// envia msg para todo mundo
		this.clients.values().forEach(c->c.println(msg));
	}

	public void sendDirectMessage(String dest, String msg) {
		PrintStream streamOut = this.clients.get(dest);
		if(streamOut != null){
			streamOut.println(msg);
		}
	}
}
