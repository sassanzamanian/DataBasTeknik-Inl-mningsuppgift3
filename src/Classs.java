import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Types;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class Classs extends JFrame implements ActionListener
{

	String meddelande = "";
	JFrame medlemframe = new JFrame("Lägga till pass");
	JPanel medlempanel1 = new JPanel();
	JPanel medlempanel2 = new JPanel();
	JPanel medlempanel3 = new JPanel();
	JButton addButton = new JButton("Lägg Till");
	JButton clearButton = new JButton("Clear");
	JLabel medlemLabel = new JLabel(meddelande);
	
	JLabel datumOchTidLabel = new JLabel("Datum och tid");
	JLabel instruktörIDLabel = new JLabel("Instruktör-id");
	JLabel salIDLabel = new JLabel("Sal-id");
	JLabel träningsTypIDLabel = new JLabel("Träningstyp-id");
	JLabel antalPlatserLabel = new JLabel("Antal platser");
	
	JTextField datumOchTidTextField = new JTextField(25);
	JTextField antalPlatserTextField = new JTextField(25);
	
	String [] instruktörArray = {"","Sanna","Marcus","Jessica","Eli","Saga","Viktor","Åke"};
	String [] salArray = {"","Danshallen","Stora salen","Lilla salen","Gymmet","Hörnrummet","Vindsrummet"};
	String [] träningsTypArray = {"","Pilates","Yoga","Zumba","Bootcamp","Aerobics","PT-Session"};
	
	JComboBox instruktörComboBox = new JComboBox(instruktörArray);
	JComboBox salComboBox = new JComboBox(salArray);
	JComboBox träningsTypComboBox = new JComboBox(träningsTypArray);
	
	public void clear()
	{
		this.datumOchTidTextField.setText("");	
		this.antalPlatserTextField.setText("");
		this.instruktörComboBox.setSelectedIndex(0);
		this.salComboBox.setSelectedIndex(0);
		this.träningsTypComboBox.setSelectedIndex(0);
	}
	public Classs()
	{
		medlemframe.setLayout(new GridLayout(3,0));
		medlemframe.add(medlempanel1);
		medlemframe.add(medlempanel2);
		medlemframe.add(medlempanel3);
		
		addButton.addActionListener(this);
		clearButton.addActionListener(this);
		
		medlempanel1.add(datumOchTidLabel);
		medlempanel1.add(datumOchTidTextField);	
		medlempanel1.add(antalPlatserLabel);
		medlempanel1.add(antalPlatserTextField);
		medlempanel1.add(instruktörIDLabel);
		medlempanel1.add(instruktörComboBox);	
		medlempanel1.add(salIDLabel);
		medlempanel1.add(salComboBox);
		medlempanel1.add(träningsTypIDLabel);
		medlempanel1.add(träningsTypComboBox);	
		
		medlempanel2.add(addButton);
		medlempanel2.add(clearButton);
		
		medlempanel3.add(medlemLabel);
		medlemLabel.setFont(new Font("Arial", Font.PLAIN, 18));
		
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
			int instructorBusy = 0;
			int roomBusy = 0;
			
			Connection connection = null;
			CallableStatement callCreateClass = null;
			try 
			{
				connection = DriverManager.getConnection("jdbc:mysql://gymdatabase.cnoju2at9yii.eu-central-1.rds.amazonaws.com:3306/gymdatabase?autoReconnect=true&useSSL=false", "ianmolander","Kubbegatan20");
				
				callCreateClass = connection.prepareCall(" {call create_class( ?, ?, ?, ?, ?, ?, ?)}");
				
				callCreateClass.setString(1, datumOchTidTextField.getText());
				callCreateClass.setInt(2, Integer.parseInt(antalPlatserTextField.getText()));
				callCreateClass.setString(3, salComboBox.getSelectedItem().toString());
				callCreateClass.setString(4, träningsTypComboBox.getSelectedItem().toString());
				callCreateClass.setString(5, instruktörComboBox.getSelectedItem().toString());
				callCreateClass.setInt(6, Types.INTEGER);
				callCreateClass.setInt(7, Types.INTEGER);
				
				callCreateClass.execute();
				
				instructorBusy = callCreateClass.getInt(6);
				roomBusy = callCreateClass.getInt(7);

			} 
			catch (SQLException e1) 
			{
				e1.printStackTrace();
			}
			finally
			{
				try 
				{
					callCreateClass.close();
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
			
			
			if(instructorBusy != 0)
			{
				medlemLabel.setText("instruktören är upptagen den tiden!");
			}
			if(roomBusy != 0)
			{
				medlemLabel.setText("salen är upptagen den tiden!");
			}
			if(roomBusy != 0 && instructorBusy != 0)
			{
				medlemLabel.setText("instruktören och salen är upptagna den tiden!");
			}
			if(roomBusy == 0 && instructorBusy == 0)
			{
				medlemLabel.setText("pass tillagd!");
			
			}
			
			
			
		}
		if(clearButton == sourcee)
		{
			clear();

		}
		
		
		
	}
}
