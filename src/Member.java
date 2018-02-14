import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class Member extends JFrame implements ActionListener 
{
	String meddelande = "";
	JFrame medlemframe = new JFrame("Lägga till medlem");
	JPanel medlempanel1 = new JPanel();
	JPanel medlempanel2 = new JPanel();
	JPanel medlempanel3 = new JPanel();
	JButton addButton = new JButton("Lägg Till");
	JButton clearButton = new JButton("Clear");
	JLabel medlemLabel = new JLabel(meddelande);
	
	JLabel förnamnLabel = new JLabel("Förnamn");
	JLabel efternamnLabel = new JLabel("Efternamn");
	JLabel addressLabel = new JLabel("Address");
	JLabel telefonnummerLabel = new JLabel("Telefonnummer");
	JLabel lösenordLabel = new JLabel("Lösenord");
	
	JTextField förnamnTextField = new JTextField(25);
	JTextField efternamnTextField = new JTextField(25);
	JTextField addressTextField = new JTextField(25);
	JTextField telefonnummerTextField = new JTextField(25);
	JTextField lösenordTextField = new JTextField(25);
	
	public void clear()
	{
		this.förnamnTextField.setText("");	
		this.efternamnTextField.setText("");
		this.addressTextField.setText("");
		this.telefonnummerTextField.setText("");
		this.lösenordTextField.setText("");
	}
	
	public Member()
	{
		medlemframe.setLayout(new GridLayout(3,0));
		medlemframe.add(medlempanel1);
		medlemframe.add(medlempanel2);
		medlemframe.add(medlempanel3);
		
		addButton.addActionListener(this);
		clearButton.addActionListener(this);
		
		medlempanel1.add(förnamnLabel);
		medlempanel1.add(förnamnTextField);
		medlempanel1.add(efternamnLabel);
		medlempanel1.add(efternamnTextField);
		medlempanel1.add(addressLabel);
		medlempanel1.add(addressTextField);	
		medlempanel1.add(telefonnummerLabel);
		medlempanel1.add(telefonnummerTextField);
		medlempanel1.add(lösenordLabel);
		medlempanel1.add(lösenordTextField);
	

		medlempanel2.add(addButton);
		medlempanel2.add(clearButton);
		
		medlempanel3.add(medlemLabel);
		medlemLabel.setFont(new Font("Arial", Font.PLAIN, 20));
		
		medlemframe.setSize(400, 420);
		medlemframe.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		medlemframe.setLocationRelativeTo(null);
		medlemframe.setVisible(true);
			
	}

	public void actionPerformed(ActionEvent e) 
	{
		Object sourcee = e.getSource();
		
		if(addButton == sourcee)	
		{
			
			Connection connection = null;
			PreparedStatement prep = null;
			
			try 
			{
				
				connection = DriverManager.getConnection("jdbc:mysql://gymdatabase.cnoju2at9yii.eu-central-1.rds.amazonaws.com:3306/gymdatabase?autoReconnect=true&useSSL=false", "ianmolander","Kubbegatan20");
				prep = connection.prepareStatement("insert into Member(firstname, surname, adress, phoneNr, password) values (?, ?, ?, ?, ?)");
				
				prep.setString(1, förnamnTextField.getText());
				prep.setString(2, efternamnTextField.getText());
				prep.setString(3, addressTextField.getText());
				prep.setString(4, telefonnummerTextField.getText());
				prep.setString(5, lösenordTextField.getText());
				
				prep.executeUpdate();
				
				medlemLabel.setText("medlem tillagd!");
				clear();
				
			} 
			
			catch (Exception e2) 
			{
				e2.printStackTrace();
			}
			
			finally 
			{
				try 
				{
					prep.close();
				} 
				catch (SQLException e1) 
				{
					e1.printStackTrace();
				}
				try 
				{
					connection.close();
				} 
				catch (SQLException e1) 
				{	
					e1.printStackTrace();
				}
				
			}
		}
		
		if(clearButton == sourcee)
		{
			clear();
		}
		
		
	}
	
	
}
