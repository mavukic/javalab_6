

import java.awt.BorderLayout;
import java.awt.FlowLayout;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.awt.GridBagLayout;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.GridBagConstraints;
import java.awt.Insets;
import javax.swing.JTextField;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;

public class Postavke extends JDialog {

	private final JPanel contentPanel = new JPanel();
	private JTextField textField;
	private JTextField textField_1;
	private JTextField textField_2;
	String content;
	int confrimed;

	boolean dirty=false;
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		try {
			Postavke dialog = new Postavke();
			dialog.setDefaultCloseOperation(JDialog.DO_NOTHING_ON_CLOSE); 
			dialog.setVisible(true);

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Create the dialog.
	 */
	public Postavke() {
	
		setBounds(100, 100, 450, 300);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		GridBagLayout gbl_contentPanel = new GridBagLayout();
		gbl_contentPanel.columnWidths = new int[] {50, 200};
		gbl_contentPanel.rowHeights = new int[] {35, 35, 35, 50};
		gbl_contentPanel.columnWeights = new double[]{0.0, 1.0}; 
		gbl_contentPanel.rowWeights = new double[]{0.0, 0.0, 0.0, 1.0}; 
		contentPanel.setLayout(gbl_contentPanel);
		{
			JLabel lblNewLabel = new JLabel("Host");
			GridBagConstraints gbc_lblNewLabel = new GridBagConstraints();
			gbc_lblNewLabel.fill = GridBagConstraints.VERTICAL;
			gbc_lblNewLabel.anchor = GridBagConstraints.EAST;
			gbc_lblNewLabel.insets = new Insets(10, 15, 10, 10);
			gbc_lblNewLabel.gridx = 0;
			gbc_lblNewLabel.gridy = 0;
			contentPanel.add(lblNewLabel, gbc_lblNewLabel);
		}
		{
			textField = new JTextField();
			
			textField.addFocusListener(new FocusAdapter() {
				@Override
				public void focusGained(FocusEvent arg0) {
				 content =textField.getText();
				}
				@Override
				public void focusLost(FocusEvent e) {
					if(content!=textField.getText()) {
					dirty=true;
					}
				}
			});
			GridBagConstraints gbc_textField = new GridBagConstraints();
			gbc_textField.anchor = GridBagConstraints.WEST;
			gbc_textField.insets = new Insets(0, 0, 5, 0);
			gbc_textField.fill = GridBagConstraints.HORIZONTAL;
			gbc_textField.gridx = 1;
			gbc_textField.gridy = 0;
			contentPanel.add(textField, gbc_textField);
			textField.setColumns(10);
			
		}
		{
			JLabel lblNewLabel_1 = new JLabel("Port");
			GridBagConstraints gbc_lblNewLabel_1 = new GridBagConstraints();
			gbc_lblNewLabel_1.anchor = GridBagConstraints.EAST;
			gbc_lblNewLabel_1.fill = GridBagConstraints.VERTICAL;
			gbc_lblNewLabel_1.insets = new Insets(10, 15, 10, 10);
			gbc_lblNewLabel_1.gridx = 0;
			gbc_lblNewLabel_1.gridy = 1;
			contentPanel.add(lblNewLabel_1, gbc_lblNewLabel_1);
		}
		{
			textField_1 = new JTextField();
			GridBagConstraints gbc_textField_1 = new GridBagConstraints();
			gbc_textField_1.anchor = GridBagConstraints.WEST;
			gbc_textField_1.insets = new Insets(0, 0, 5, 0);
			gbc_textField_1.fill = GridBagConstraints.HORIZONTAL;
			gbc_textField_1.gridx = 1;
			gbc_textField_1.gridy = 1;
			contentPanel.add(textField_1, gbc_textField_1);
			textField_1.setColumns(10);
		}
		{
			JLabel lblNewLabel_2 = new JLabel("Korisnik");
			GridBagConstraints gbc_lblNewLabel_2 = new GridBagConstraints();
			gbc_lblNewLabel_2.anchor = GridBagConstraints.EAST;
			gbc_lblNewLabel_2.insets = new Insets(10, 15, 10, 10);
			gbc_lblNewLabel_2.gridx = 0;
			gbc_lblNewLabel_2.gridy = 2;
			contentPanel.add(lblNewLabel_2, gbc_lblNewLabel_2);
		}
		{
			textField_2 = new JTextField();
			GridBagConstraints gbc_textField_2 = new GridBagConstraints();
			gbc_textField_2.anchor = GridBagConstraints.WEST;
			gbc_textField_2.insets = new Insets(0, 0, 5, 0);
			gbc_textField_2.fill = GridBagConstraints.HORIZONTAL;
			gbc_textField_2.gridx = 1;
			gbc_textField_2.gridy = 2;
			contentPanel.add(textField_2, gbc_textField_2);
			textField_2.setColumns(10);
		}
		{
			JPanel buttonPane = new JPanel();
			buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
			getContentPane().add(buttonPane, BorderLayout.SOUTH);
			{
				JButton okButton = new JButton("Snimi");
				okButton.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent arg0) {
	
						UserConfig.setHost(textField.getText());
						UserConfig.setPort(Integer.parseInt(textField_1.getText()));
						UserConfig.setKorisnik(textField_2.getText());
						UserConfig.saveParamChange();
						JOptionPane.showMessageDialog(null, "New property saved!");
						dispose();
					
						}
					
				});
				okButton.setActionCommand("Snimi");
				buttonPane.add(okButton);
				getRootPane().setDefaultButton(okButton);
		
			}
			
				JButton cancelButton = new JButton("Odustani");
				cancelButton.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						if (Test())
							dispose();	}
				});
				cancelButton.setActionCommand("Cancel");
				buttonPane.add(cancelButton);
			}
		
	}
	private boolean Test() {
		if( textField.getText().equals(UserConfig.getHost()) && 
				textField_1.getText().equals(String.valueOf(UserConfig.getPort())) &&
				textField_2.getText().equals(UserConfig.getKorisnik()) )
					return true;
		else {
				setDefaultCloseOperation(JDialog.DO_NOTHING_ON_CLOSE);
				int confirmed = JOptionPane.showOptionDialog(contentPanel,
						"Da li ste sigurni da �elite zatvoriti prozor bez snimanja promjena?", "Pitanje!",
						JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE,
						null, new String[] { "Da", "Ne" }, "Ne");
				if (confirmed == 0) 
					return true;
				else
					return false;
		}
	
	}
	
	
}
