package com.lc.chatsrv.view;

import javax.swing.GroupLayout;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import com.lc.chatsrv.controller.ChatClientController;

public class DialogMessageView {

	private JTextArea messagesPane;
	private JTextField messageField;
	
	private JPanel panel;
	
	private final Object[] options = {"Enviar", "Sair"};
	
	private ChatClientController controller;
	
	private StringBuilder msgLog;
	
	public DialogMessageView(ChatClientController controller) {
		
		this.controller = controller;
		
		msgLog = new StringBuilder();
		panel = new JPanel();
		
		messagesPane = new JTextArea(20, 30);
		messageField = new JTextField();
		
		GroupLayout layout = new GroupLayout(panel);
		
		layout.setAutoCreateGaps(true);
		layout.setAutoCreateContainerGaps(true);
		
		layout.setVerticalGroup(
			    layout.createSequentialGroup()
			        .addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
			            .addComponent(messagesPane))                       
			        .addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
			            .addComponent(messageField)));

			layout.setHorizontalGroup(
			    layout.createSequentialGroup()
			        .addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
			            .addComponent(messagesPane)
			            .addComponent(messageField)));
		panel.setLayout(layout);
	}
	
	public void show(){
		messagesPane.setEditable(false);
		messagesPane.setText(msgLog.toString());
		
		int opSelected = 0;
		while (opSelected == 0) {
			opSelected = JOptionPane.showOptionDialog(null, panel, "Configurações", JOptionPane.DEFAULT_OPTION, JOptionPane.PLAIN_MESSAGE, null, options, null);
			
			if(opSelected == 0){
				this.sendMessage();
			}
			
			if(opSelected == 1){
				System.exit(0);
			}
		}
	}
	
	public void updateMessage(String newMessage){
		msgLog.append(newMessage).append("\n");
		messagesPane.setText(msgLog.toString());
	}
	
	public void sendMessage(){
		controller.sendMessage(messageField.getText());
		messageField.setText("");
	}
}
