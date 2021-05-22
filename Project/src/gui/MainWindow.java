package gui;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.beans.PropertyVetoException;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JInternalFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTextArea;
import javax.swing.JToolBar;
import javax.swing.JTree;
import javax.swing.UIManager;
import javax.swing.plaf.nimbus.NimbusLookAndFeel;

public class MainWindow extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) throws Exception{
		// Look
		UIManager.setLookAndFeel(new NimbusLookAndFeel());
		// Start
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					MainWindow frame = new MainWindow();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public MainWindow() {
		// Interface
		this.setTitle("Pont-Simulator");
		this.setBounds(100, 100, 1000, 500);
		this.setLocationRelativeTo(null); // Placer au centre
		this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		
		// Barre de menu
		
		this.setJMenuBar(menu());
		
		// Autres
		
		contentPane = new JPanel();
		contentPane.setBorder(null);
		this.setContentPane(contentPane);
		contentPane.setLayout(new BorderLayout());
		
		contentPane.add(outils(),BorderLayout.NORTH);
		
		JScrollPane list = new JScrollPane(arbre());
		list.setPreferredSize(new Dimension(200,0)); // 0 en hauteur car déjà géré
		//contentPane.add(list,BorderLayout.WEST);
		
		JTextArea editor = new JTextArea();
		//contentPane.add(editor,BorderLayout.CENTER);
		
		JSplitPane split = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT,list,editor);
		contentPane.add(split, BorderLayout.CENTER);
		
		JPopupMenu popup = popup();
	}
	
	private JTree arbre() {
		JTree res = new JTree();
		
		return res;
	}
	
	private JPopupMenu popup() {
		JPopupMenu res = new JPopupMenu();
		
		
		return res;
	}
	
	private JMenuBar menu() {
		
		JMenuBar res = new JMenuBar();
		//___________________________________________________
		
		JMenu file = new JMenu("File"); res.add(file);
		
		JMenuItem newFile = new JMenu("New"); file.add(newFile);
		JMenuItem newTreilli = new JMenuItem("Treilli"); newFile.add(newTreilli);
		newTreilli.addActionListener(this::newTreilli);
		
		JMenuItem openFile = new JMenuItem("Open File..."); file.add(openFile);
		
		file.addSeparator();
		
		JMenuItem saveFile = new JMenuItem("Save"); file.add(saveFile);
		JMenuItem saveFileAs = new JMenuItem("Save As..."); file.add(saveFileAs);
		
		file.addSeparator();
		
		JMenuItem exitFile = new JMenuItem("Exit"); file.add(exitFile);
		//___________________________________________________
		
		JMenu edit = new JMenu("Edit"); res.add(edit);
		
		JMenuItem terrain = new JMenuItem("Terrain Bounds"); edit.add(terrain);
		
		return res;
	}
	
	private void newTreilli(ActionEvent event) { // event lorsqu'on veut créer un nouveau treilli
		//JOptionPane.showMessageDialog(this, "New Treilli");
		TerrainWindow frame = new TerrainWindow(contentPane);
		frame.setVisible(true);
		frame.pack(); // optimise la taille de la fenetre
	}

	private JToolBar outils() {
		
		JToolBar res = new JToolBar(); // pas de positionnement dans la tool bar
		
		JButton triangle = new JButton("Triangle"); res.add(triangle);
		triangle.addActionListener(this::newTriangleTerrain);
		
		res.addSeparator();
		
		JButton appui = new JButton("Appui"); res.add(appui);
		appui.addActionListener(this::newNoeudAppui);
		
		JButton barre1 = new JButton("Barre"); res.add(barre1);
		JButton barre2 = new JButton("Barre entre deux noeuds"); res.add(barre2);
		barre1.addActionListener(this::newBarre1);
		barre2.addActionListener(this::newBarre2);
		
		res.addSeparator();
		
		JButton type = new JButton("Type"); res.add(type);
		
		res.addSeparator();
		
		return res;
	}
	
	private void newTriangleTerrain(ActionEvent event) {
		
	}
	private void newNoeudAppui(ActionEvent event) {
		
	}
	private void newBarre1(ActionEvent event) {
	
	}
	private void newBarre2(ActionEvent event) {
		
	}

}
