package com.lc.chatsrv;

import java.io.InputStream;
import java.util.Scanner;

import com.lc.chatsrv.view.DialogMessageView;

public class ClientReceiver implements Runnable {

	private InputStream server;
	private DialogMessageView view;

	public ClientReceiver(InputStream servidor, DialogMessageView view) {
		this.server = servidor;
		this.view = view;
	}

	public void run() {

		// recebe msgs do servidor e imprime na tela
		Scanner s = new Scanner(this.server);
		while (s.hasNextLine()) {
			view.updateMessage(s.nextLine());
		}
		s.close();
	}
}
