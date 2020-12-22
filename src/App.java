import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.EOFException;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.StringReader;
import java.security.InvalidParameterException;

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
		frame.setBounds(100, 100, 1189, 579);
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
		btnConvertCsv.setBounds(501, 300, 171, 46);
		btnConvertCsv.setFont(new Font("Arial", Font.BOLD, 14));
		btnConvertCsv.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				textAreaCSV.setText("");
				try {
					Json2Csv json = new Json2Csv(textAreaJSON.getText());
					textAreaCSV.append(json.getCsv());
				} catch(InvalidParameterException ipe) {
					textAreaCSV.append(ipe.getMessage());
				} catch(IndexOutOfBoundsException ioobe) {
					textAreaCSV.append("JSON inválido!");
				}
			}
		});
		frame.getContentPane().add(btnConvertCsv);
		
		JButton btnConvertJson = new JButton("CONVERT TO JSON");
		btnConvertJson.setBounds(501, 231, 171, 46);
		btnConvertJson.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				textAreaJSON.setText("");
				try {
					Csv2Json csv = new Csv2Json(textAreaCSV.getText());
					textAreaJSON.append(csv.getJsonObjectArray());
				} catch(InvalidParameterException ipe) {
					textAreaJSON.append(ipe.getMessage());
				} catch(IndexOutOfBoundsException ioobe) {
					textAreaJSON.append("CSV inválido!");
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
				searchFile(tfCsvFile, "csv");
			}
		});
		frame.getContentPane().add(btnBuscarCsv);
		
		JButton btnCarregarCsv = new JButton("Carregar");
		btnCarregarCsv.setBounds(402, 95, 89, 23);
		btnCarregarCsv.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				loadFile(tfCsvFile, textAreaCSV);
			}
		});
		frame.getContentPane().add(btnCarregarCsv);
		
		tfJsonFile = new JTextField();
		tfJsonFile.setBounds(772, 96, 292, 20);
		tfJsonFile.setEditable(false);
		tfJsonFile.setColumns(10);
		frame.getContentPane().add(tfJsonFile);
		
		JButton btnBuscarJson = new JButton("Buscar");
		btnBuscarJson.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				searchFile(tfJsonFile, "json");
			}
		});
		btnBuscarJson.setBounds(682, 95, 80, 23);
		frame.getContentPane().add(btnBuscarJson);
		
		JButton btnCarregarJson = new JButton("Carregar");
		btnCarregarJson.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				loadFile(tfJsonFile, textAreaJSON);
			}
		});
		btnCarregarJson.setBounds(1074, 96, 89, 23);
		frame.getContentPane().add(btnCarregarJson);
		
		JButton btnSalvarJson = new JButton("Salvar");
		btnSalvarJson.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				saveFile(textAreaJSON.getText(), "json");
			}
		});
		btnSalvarJson.setBounds(682, 501, 107, 23);
		frame.getContentPane().add(btnSalvarJson);
		
		JButton btnLimparJson = new JButton("Limpar");
		btnLimparJson.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				textAreaJSON.setText("");
			}
		});
		btnLimparJson.setBounds(799, 501, 89, 23);
		frame.getContentPane().add(btnLimparJson);
		
		JButton btnSalvarCsv = new JButton("Salvar");
		btnSalvarCsv.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				saveFile(textAreaCSV.getText(), "csv");
			}
		});
		btnSalvarCsv.setBounds(10, 501, 107, 23);
		frame.getContentPane().add(btnSalvarCsv);
		
		JButton btnLimparCsv = new JButton("Limpar");
		btnLimparCsv.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				textAreaCSV.setText("");
			}
		});
		btnLimparCsv.setBounds(127, 501, 89, 23);
		frame.getContentPane().add(btnLimparCsv);
	}

	protected void saveFile(String dataToBeSaved, String fileType) {
		String msg = "";
		if(dataToBeSaved.isBlank() || dataToBeSaved.isEmpty())
			msg = "Não há dados a serem salvos!";
		else {
			JFileChooser chooser = new JFileChooser();
			FileNameExtensionFilter filter = new FileNameExtensionFilter(fileType, fileType);
			chooser.setFileFilter(filter);
			int retVal = chooser.showSaveDialog(null);
			if(retVal == JFileChooser.APPROVE_OPTION) {
				File file = chooser.getSelectedFile().getAbsoluteFile();
				try {
					BufferedWriter bw = new BufferedWriter(new FileWriter(file+"."+fileType));
					bw.write(dataToBeSaved);
					bw.close();
					msg = "Arquivo salvo com sucesso!";
				} catch (IOException ioe) {
					ioe.printStackTrace();
					msg = "Não foi possível salvar o arquivo.";
				}
			}
		}
		JOptionPane.showMessageDialog(frame, msg);
	}

	protected void loadFile(JTextField tfContainingPath, JTextArea taToReceiveString) {
		String msg = "";
		File file = new File(tfContainingPath.getText());
		if(tfContainingPath.getText().isEmpty())
			msg = "Busque um arquivo para então carregá-lo.";
		else {
			try {
				BufferedReader br = new BufferedReader(new FileReader(file));
				taToReceiveString.setText("");
					try {
						String line = br.readLine().replace("ï»¿", "");
						while(line != null) {
							taToReceiveString.append(line+"\n");
							line = br.readLine();
						}
						msg = "Arquivo lido com sucesso! (:";
					} catch (EOFException eofe) {
						msg = "Arquivo lido com sucesso! (:";
					} catch (IOException ioe) {
						msg = "Erro na leitura do arquivo. ):";
					}
			} catch (FileNotFoundException fnfe) {
				msg = "O arquivo informado não existe. Verifique antes de prosseguir!";
			}
		}
		JOptionPane.showMessageDialog(frame, msg);
	}

	protected void searchFile(JTextField textField, String fileTypeToBeFiltered) {
		JFileChooser chooser = new JFileChooser();
		FileNameExtensionFilter filter = new FileNameExtensionFilter(fileTypeToBeFiltered, fileTypeToBeFiltered);
		chooser.setFileFilter(filter);
		int retVal = chooser.showOpenDialog(null);
		if(retVal == JFileChooser.APPROVE_OPTION)
			textField.setText(chooser.getSelectedFile().getAbsolutePath());
		else
			JOptionPane.showMessageDialog(frame, "Arquivo não encontrado!");
	}
}
