import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.EOFException;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.StringReader;

import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextArea;
import javax.swing.SwingConstants;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.JTextField;
import javax.swing.JScrollPane;

public class App {

	private JFrame frame;
	private JTextField tfCsvFile;
	private JTextField tfJsonFile;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					App window = new App();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public App() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setBounds(100, 100, 1189, 564);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		
		JTextArea textAreaCSV = new JTextArea();
		textAreaCSV.setBounds(10, 121, 481, 369);
		textAreaCSV.setLineWrap(true);
		textAreaCSV.setBackground(Color.LIGHT_GRAY);
		frame.getContentPane().add(textAreaCSV);
		
		JLabel lblTitleCSVBox = new JLabel("CSV");
		lblTitleCSVBox.setBounds(10, 63, 481, 25);
		lblTitleCSVBox.setHorizontalAlignment(SwingConstants.CENTER);
		lblTitleCSVBox.setFont(new Font("Arial", Font.BOLD, 16));
		frame.getContentPane().add(lblTitleCSVBox);
		
		JLabel lblTitleJSONBox = new JLabel("JSON");
		lblTitleJSONBox.setBounds(682, 63, 481, 25);
		lblTitleJSONBox.setHorizontalAlignment(SwingConstants.CENTER);
		lblTitleJSONBox.setFont(new Font("Arial", Font.BOLD, 16));
		frame.getContentPane().add(lblTitleJSONBox);
		
		JScrollPane scrollPaneJson = new JScrollPane();
		scrollPaneJson.setBounds(682, 121, 481, 369);
		frame.getContentPane().add(scrollPaneJson);
		
		JTextArea textAreaJSON = new JTextArea();
		scrollPaneJson.setViewportView(textAreaJSON);
		textAreaJSON.setLineWrap(true);
		textAreaJSON.setBackground(Color.LIGHT_GRAY);
		
		JButton btnConvertCsv = new JButton("CONVERT TO CSV");
		btnConvertCsv.setBounds(501, 226, 171, 46);
		btnConvertCsv.setFont(new Font("Arial", Font.BOLD, 14));
		btnConvertCsv.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			}
		});
		frame.getContentPane().add(btnConvertCsv);
		
		JButton btnConvertJson = new JButton("CONVERT TO JSON");
		btnConvertJson.setBounds(501, 307, 171, 46);
		btnConvertJson.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				textAreaJSON.setText("");
				BufferedReader reader = new BufferedReader(new StringReader(textAreaCSV.getText()));
				try {
					Csv2Json csvConverter = new Csv2Json(reader.readLine());
					String str;
					while((str = reader.readLine()) != null) {
						textAreaJSON.append(csvConverter.getJsonObject(str));
					}
				} catch(IOException ioe) {
					ioe.printStackTrace();
				}
			}
		});
		btnConvertJson.setFont(new Font("Arial", Font.BOLD, 14));
		frame.getContentPane().add(btnConvertJson);
		
		tfCsvFile = new JTextField();
		tfCsvFile.setBounds(100, 95, 292, 20);
		tfCsvFile.setEditable(false);
		frame.getContentPane().add(tfCsvFile);
		tfCsvFile.setColumns(10);
		
		JButton btnBuscarCsv = new JButton("Buscar");
		btnBuscarCsv.setBounds(10, 94, 80, 23);
		btnBuscarCsv.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JFileChooser chooser = new JFileChooser();
				FileNameExtensionFilter filter = new FileNameExtensionFilter("CSV Files", "csv");
				chooser.setFileFilter(filter);
				int retVal = chooser.showOpenDialog(null);
				if(retVal == JFileChooser.APPROVE_OPTION)
					tfCsvFile.setText(chooser.getSelectedFile().getAbsolutePath());
				else
					JOptionPane.showMessageDialog(frame, "Arquivo n�o encontrado!");
			}
		});
		frame.getContentPane().add(btnBuscarCsv);
		
		JButton btnCarregarCsv = new JButton("Carregar");
		btnCarregarCsv.setBounds(402, 95, 89, 23);
		btnCarregarCsv.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String msg = "";
				File file = new File(tfCsvFile.getText());
				if(tfCsvFile.getText().isEmpty())
					msg = "Busque um arquivo para ent�o carreg�-lo.";
				else {
					try {
						BufferedReader br = new BufferedReader(new FileReader(file));
						textAreaCSV.setText("");
							try {
								String line = br.readLine().replace("﻿", "");
								while(line != null) {
									textAreaCSV.append(line+"\n");
									line = br.readLine();
								}
								msg = "Arquivo lido com sucesso! (:";
							} catch (EOFException eofe) {
								msg = "Arquivo lido com sucesso! (:";
							} catch (IOException ioe) {
								msg = "Erro na leitura do arquivo. ):";
							}
					} catch (FileNotFoundException fnfe) {
						msg = "O arquivo informado n�o existe. Verifique antes de prosseguir!";
					}
				}
				JOptionPane.showMessageDialog(frame, msg);
			}
		});
		frame.getContentPane().add(btnCarregarCsv);
		
		JButton btnBuscarJson = new JButton("Buscar");
		btnBuscarJson.setBounds(682, 95, 80, 23);
		frame.getContentPane().add(btnBuscarJson);
		
		tfJsonFile = new JTextField();
		tfJsonFile.setBounds(772, 96, 292, 20);
		tfJsonFile.setEditable(false);
		tfJsonFile.setColumns(10);
		frame.getContentPane().add(tfJsonFile);
		
		JButton btnCarregarJson = new JButton("Carregar");
		btnCarregarJson.setBounds(1074, 96, 89, 23);
		frame.getContentPane().add(btnCarregarJson);
	}
}
