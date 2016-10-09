package com.lc.chatsrv;

import java.io.IOException;
import java.net.UnknownHostException;

import com.lc.chatsrv.controller.ChatClientController;
import com.lc.chatsrv.controller.ChatServerController;
import com.lc.chatsrv.model.ChatSettings;
import com.lc.chatsrv.view.DialogMessageView;
import com.lc.chatsrv.view.SettingsView;

/**
 * Hello world!
 *
 */
public class ChatApp {

	public static void main(String[] args) throws UnknownHostException, IOException {

		SettingsView settingsView = new SettingsView();
		
		settingsView.show();
	
		ChatSettings settings = settingsView.getSettings();
		
		if(settings.getType().equals(ChatSettings.Type.SERVER)){
			new ChatServerController(settings.getPort()).start();
			
		} else if(settings.getType().equals(ChatSettings.Type.CLIENT)){ 
			
			ChatClientController clientController =  new ChatClientController(settings.getName(), settings.getHost(), settings.getPort());
			clientController.start();
			DialogMessageView messageView = new DialogMessageView(clientController);
			
			try {
				ClientReceiver receiver = new ClientReceiver(clientController.getClient().getInputStream(), messageView);
				Thread thReceiver = new Thread(receiver);
				thReceiver.start();
				clientController.setThreadReceiver(thReceiver);
			} catch (IOException e) {
				System.out.println("Erro ao receber mensagem");
			}
			
			messageView.show();
		}
	}
}
