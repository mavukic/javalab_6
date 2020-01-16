
import java.awt.BorderLayout;
import java.io.BufferedReader;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.border.EmptyBorder;
import javax.swing.SpringLayout;
import javax.swing.JButton;
import javax.swing.JTextField;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;
import java.awt.event.ActionEvent;
import java.io.PrintWriter;
import java.net.Socket;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Chat extends JFrame {
	private Socket soc;
	private PrintWriter pw;
	private BufferedReader br;
	Logger log = LoggerFactory.getLogger(Chat.class);
	private JPanel contentPane;
	private JTextField textField;
	public JTextArea textField_1;

	public Chat() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		SpringLayout sl_contentPane = new SpringLayout();
		contentPane.setLayout(sl_contentPane);

		JButton btnNewButton = new JButton("Send");
		
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {

				send();
				

			}

		});
		sl_contentPane.putConstraint(SpringLayout.SOUTH, btnNewButton, -5, SpringLayout.SOUTH, contentPane);
		sl_contentPane.putConstraint(SpringLayout.EAST, btnNewButton, -5, SpringLayout.EAST, contentPane);
		contentPane.add(btnNewButton);

		textField = new JTextField();
		textField.setText("s");
		sl_contentPane.putConstraint(SpringLayout.NORTH, textField, 0, SpringLayout.NORTH, btnNewButton);
		sl_contentPane.putConstraint(SpringLayout.WEST, textField, 5, SpringLayout.WEST, contentPane);
		sl_contentPane.putConstraint(SpringLayout.SOUTH, textField, -5, SpringLayout.SOUTH, contentPane);
		sl_contentPane.putConstraint(SpringLayout.EAST, textField, -5, SpringLayout.WEST, btnNewButton);
		contentPane.add(textField);
		textField.setColumns(10);

		textField_1 = new JTextArea();
		textField_1.setEditable(false);
		sl_contentPane.putConstraint(SpringLayout.NORTH, textField_1, 5, SpringLayout.NORTH, contentPane);
		sl_contentPane.putConstraint(SpringLayout.WEST, textField_1, 5, SpringLayout.WEST, contentPane);
		sl_contentPane.putConstraint(SpringLayout.SOUTH, textField_1, -5, SpringLayout.NORTH, textField);
		sl_contentPane.putConstraint(SpringLayout.EAST, textField_1, 0, SpringLayout.EAST, textField);
		contentPane.add(textField_1);
		textField_1.setColumns(10);

		JButton btnNewButton_1 = new JButton("UserConfig");
		btnNewButton_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Postavke dialog = new Postavke();
				dialog.setVisible(true);
				UserConfig.loadParams();
			}
		});
connect();
		sl_contentPane.putConstraint(SpringLayout.WEST, btnNewButton_1, 340, SpringLayout.WEST, textField_1);
		sl_contentPane.putConstraint(SpringLayout.SOUTH, btnNewButton_1, -7, SpringLayout.NORTH, btnNewButton);
		contentPane.add(btnNewButton_1);
	}

	private void connect() {
		try {
			soc = new Socket(UserConfig.getHost(), UserConfig.getPort());
			pw = new PrintWriter(soc.getOutputStream());
			br = new BufferedReader(new InputStreamReader(soc.getInputStream()));
			String response;
			try {
				response = br.readLine();
				if (textField.getText().length() > 0)
					textField_1.append("\n");
				textField_1.append(response);
				textField.setText(null);
			} catch (IOException e) {
				log.error("Greska kod citanja inicijalnog odgovora", e);
				JOptionPane.showMessageDialog(textField_1, "Greska kod citanja inicijalnog odgovora", "Greska!",
						JOptionPane.ERROR_MESSAGE);
			}
		} catch (UnknownHostException e) {
			log.error("Nepoznati host", e);
			this.dispose();
		} catch (IOException e) {
			log.error("IO iznimka", e);
			this.dispose();
		}
		log.info("connected");
	}

	public void send() {
		pw.println(textField.getText());
		if (pw.checkError()) {
			JOptionPane.showMessageDialog(textField_1, "Greska kod slanja poruke", "Greska!",
					JOptionPane.ERROR_MESSAGE);
		}
		String response;
		try {
			response = br.readLine();
			if (textField.getText().length() > 0)
				textField_1.append("\n");
			textField_1.append(response);
			textField.setText(null);
		} catch (IOException e) {
			log.error("Greska kod citanja", e);
			JOptionPane.showMessageDialog(textField_1, "Greska kod citanja odgovora", "Greska!",
					JOptionPane.ERROR_MESSAGE);
		}
		log.info("data sent");
	}

	public static void main(String[] args) {
		UserConfig noviUser = new UserConfig();
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Chat frame = new Chat();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}


}
