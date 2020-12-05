import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.StringReader;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextArea;
import javax.swing.SwingConstants;

public class App {

	private JFrame frame;

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
		textAreaCSV.setLineWrap(true);
		textAreaCSV.setBackground(Color.LIGHT_GRAY);
		textAreaCSV.setBounds(10, 121, 481, 369);
		frame.getContentPane().add(textAreaCSV);
		
		JTextArea textAreaJSON = new JTextArea();
		textAreaJSON.setLineWrap(true);
		textAreaJSON.setBackground(Color.LIGHT_GRAY);
		textAreaJSON.setBounds(682, 121, 481, 369);
		frame.getContentPane().add(textAreaJSON);
		
		JLabel lblTitleCSVBox = new JLabel("CSV");
		lblTitleCSVBox.setHorizontalAlignment(SwingConstants.CENTER);
		lblTitleCSVBox.setFont(new Font("Arial", Font.BOLD, 16));
		lblTitleCSVBox.setBounds(10, 89, 481, 25);
		frame.getContentPane().add(lblTitleCSVBox);
		
		JLabel lblTitleJSONBox = new JLabel("JSON");
		lblTitleJSONBox.setHorizontalAlignment(SwingConstants.CENTER);
		lblTitleJSONBox.setFont(new Font("Arial", Font.BOLD, 16));
		lblTitleJSONBox.setBounds(682, 89, 481, 25);
		frame.getContentPane().add(lblTitleJSONBox);
		
		JButton btnNewButton = new JButton("CONVERT TO CSV");
		btnNewButton.setFont(new Font("Arial", Font.BOLD, 14));
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			}
		});
		btnNewButton.setBounds(501, 226, 171, 46);
		frame.getContentPane().add(btnNewButton);
		
		JButton btnConvertJson = new JButton("CONVERT TO JSON");
		btnConvertJson.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
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
		btnConvertJson.setBounds(501, 307, 171, 46);
		frame.getContentPane().add(btnConvertJson);
	}
}
