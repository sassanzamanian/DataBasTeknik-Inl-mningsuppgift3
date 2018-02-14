import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class HuvudMeny extends JFrame implements ActionListener
{

	
	JFrame frame = new JFrame("Admin App");
	JButton classButton = new JButton("Pass");
	JButton memberButton = new JButton("Medlem");
	JButton employeeButton = new JButton("Anställd");
	JLabel label = new JLabel("Vad vill du lägga till?");
	JPanel panel = new JPanel();
	
	
	public HuvudMeny()
	{
		
		frame.add(panel);
		panel.add(label);
		panel.add(employeeButton);
		panel.add(memberButton);
		panel.add(classButton);
		
		classButton.addActionListener(this);
		memberButton.addActionListener(this);
		employeeButton.addActionListener(this);
		
		
		
		frame.setSize(400, 400);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setLocationRelativeTo(null);
		frame.setVisible(true);
		
	}
	
	
	public void actionPerformed(ActionEvent e) 
	{
		
		Object source = e.getSource();
		
		if(classButton == source)	
		{
			Classs classs = new Classs();
		}
		
		if(memberButton == source)
		{	
			Member member = new Member();
		}
		
		if(employeeButton == source)
		{	
			Employee employee = new Employee();
		}		
				
		frame.dispose();
	}
}
