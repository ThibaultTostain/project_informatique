package gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.EventQueue;
import java.awt.FlowLayout;
import java.awt.GridLayout;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class TerrainWindow extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					TerrainWindow frame = new TerrainWindow(null);
					frame.setVisible(true);
					frame.pack(); // optimise la taille de la fenetre
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	
	public TerrainWindow(JPanel mainWindow) {
		
		setTitle("Création du terrain");
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setLocationRelativeTo(mainWindow); // Placer au centre de la fenetre principale
		contentPane = new JPanel();
		setContentPane(contentPane);
		contentPane.setLayout(new BorderLayout());
		
        
        // Trucs à remplir
        JPanel panel = new JPanel(new GridLayout(5,2,10,10)); contentPane.add(panel,BorderLayout.CENTER);
        panel.setBorder(BorderFactory.createEmptyBorder(10,10,10,10));
        panel.setBackground(Color.LIGHT_GRAY);
        
        panel.add(new JLabel("Abs Min: ")); panel.add(new JTextField());
        panel.add(new JLabel("Abs Max: ")); panel.add(new JTextField());
        panel.add(new JLabel("Ord Min: ")); panel.add(new JTextField());
        panel.add(new JLabel("Ord Max: ")); panel.add(new JTextField());
        
        // Boutons du bas
        JPanel buttons = new JPanel( new FlowLayout(FlowLayout.RIGHT)); contentPane.add(buttons,BorderLayout.SOUTH);
        JButton apply = new JButton("Apply");  buttons.add(apply);
        JButton cancel = new JButton("Cancel"); buttons.add(cancel);
        
	}
}
