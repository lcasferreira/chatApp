package com.lc.chatsrv.view;

import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;

import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JTextField;

import com.lc.chatsrv.model.ChatSettings;

import lombok.Getter;

public class SettingsView {

	private JTextField name;
	private JRadioButton typeServer;
	private JRadioButton typeClient;
	private JTextField host;
	private JTextField srvPort;
	private JPanel panel;
	private JPanel radioPanel;
	private final Object[] options = {"Conectar", "Cancelar"};
	
	@Getter
	private ChatSettings settings;

	
	public SettingsView() {
		panel = new JPanel(new GridLayout(0, 1));
		radioPanel = new JPanel(new FlowLayout());
		
		name = new JTextField();
		typeServer = new JRadioButton("Servidor", false);
		typeClient = new JRadioButton("Cliente", true);
		
		RadioButtonHandler handler = new RadioButtonHandler();
		typeClient.addItemListener(handler);
		typeServer.addItemListener(handler);
		
		host = new JTextField();
		host.setEnabled(false);
		
		srvPort = new JTextField();
	}
	
	private void createView(){
		
		radioPanel.add(new JLabel("Tipo da instância"));
		radioPanel.add(typeServer);
		radioPanel.add(typeClient);

		panel.add(new JLabel("Nome"));
		panel.add(name);
		
		panel.add(radioPanel);
		
		panel.add(new JLabel("Endereço"));
		panel.add(host);
		
		panel.add(new JLabel("Porta"));
		panel.add(srvPort);
		
		panel.setVisible(true);
		
		host.setEnabled(typeClient.isSelected());
	}
	
	public void show(){
		
		createView();
		
		int opSelected = JOptionPane.showOptionDialog(null, panel, "Configurações", JOptionPane.DEFAULT_OPTION, JOptionPane.PLAIN_MESSAGE, null, options, options[0]);
		
		if(opSelected == 0){
			createConfig();
		} else {
			System.exit(0);
		}
	}
	
	private void createConfig(){
		settings = new ChatSettings();
		
		settings.setName(name.getText());
		
		if(typeClient.isSelected()){
			settings.setType(ChatSettings.Type.CLIENT);
			
		} else if(typeServer.isSelected()){
			settings.setType(ChatSettings.Type.SERVER);
		}
		
		if(host.isEnabled()){
			settings.setHost(host.getText());
		}
		
		settings.setPort(Integer.valueOf(srvPort.getText()));
	}
	
	private class RadioButtonHandler implements ItemListener {

		@Override
		public void itemStateChanged(ItemEvent event) {
			if(host != null){
				if(event.getItem().equals(typeClient)){
					if(typeClient.isSelected()){
						typeServer.setSelected(false);
						host.setEnabled(true);
					}
				} else if(event.getItem().equals(typeServer)){
					if(typeServer.isSelected()){
						typeClient.setSelected(false);
						host.setEnabled(false);
					}
				}
			}
			
		}
	}
}
