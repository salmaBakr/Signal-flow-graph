//import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.Border;
import javax.swing.border.EmptyBorder;
import javax.swing.text.AbstractDocument.BranchElement;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JToolBar;
import javax.swing.JButton;
import javax.swing.JTextField;
import javax.swing.JLabel;
import javax.swing.ScrollPaneConstants;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.geom.GeneralPath;

import javax.swing.JTextArea;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.ScrollPane;
import java.awt.SystemColor;
import java.awt.Font;
import java.awt.Panel;
import java.awt.Button;
import java.util.ArrayList;

import javax.swing.JScrollPane;


public class GUI extends JFrame {

	private JPanel contentPane=new JPanel();
	private JTextField textField;
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					GUI frame = new GUI();
					frame.setExtendedState(JFrame.MAXIMIZED_BOTH); 
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
	public GUI() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 776, 595);
		contentPane = new JPanel();
		final DrawingArea drawingArea=new DrawingArea();
		drawingArea.setToolTipText("Draw signal flow graph here");
		contentPane.setBackground(SystemColor.windowBorder);
		//contentPane.setBackground(SystemColor.windowBorder);
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		JToolBar toolBar = new JToolBar();
		toolBar.setFloatable(false);
		toolBar.setForeground(SystemColor.windowBorder);
		toolBar.setBackground(SystemColor.windowBorder);
		toolBar.setBounds(10, 0, 1500, 30);
		contentPane.add(toolBar);
		JButton btnNode = new JButton("Node");
		btnNode.setFont(new Font("Times New Roman", Font.BOLD, 15));
		btnNode.setForeground(SystemColor.window);
		btnNode.setSize(150, 50);
		btnNode.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				drawingArea.drawEdge=false;
				drawingArea.drawNode=true;
			}
		});
		btnNode.setBackground(SystemColor.windowBorder);
		toolBar.add(btnNode);
		
		JButton btnEdge = new JButton("edge");
		btnEdge.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				drawingArea.drawNode=false;
				drawingArea.drawEdge=true;
			}
		});
		btnEdge.setFont(new Font("Times New Roman", Font.BOLD, 15));
		btnEdge.setForeground(SystemColor.text);
		btnEdge.setBackground(SystemColor.windowBorder);
		toolBar.add(btnEdge);
		JButton btnNew = new JButton("New");
		toolBar.add(btnNew);
		btnNew.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				drawingArea.dis.clear();
				drawingArea.edges.clear();
				drawingArea.shapes.clear();
				drawingArea.branchs.clear();
				drawingArea.triangles.clear();
				drawingArea.drawEdge=false;
				drawingArea.drawNode=false;
				drawingArea.p=new GeneralPath();
				drawingArea.t=new GeneralPath();
				drawingArea.repaint();
			}
		});
		btnNew.setBackground(SystemColor.windowBorder);
		btnNew.setForeground(SystemColor.window);
		btnNew.setFont(new Font("Times New Roman", Font.BOLD, 15));

		JPanel panel = new JPanel();
		panel.setBounds(244, 41, 1000, 500);
		panel.setLayout(null);
		contentPane.add(panel);
		drawingArea.setBounds(0, 0,1000, 500);
		panel.add(drawingArea);		
		final JTextArea textArea = new JTextArea();
		textArea.setEnabled(false);
		Border border = BorderFactory.createLineBorder(Color.LIGHT_GRAY, 2);
		textArea.setBounds(10, 98, 221, 92);
		textArea.setBorder(border);
		Font font = new Font("Arial", Font.BOLD, 15);
		textArea.setFont(font);
		textArea.setDisabledTextColor(Color.black);
		//textArea.setText("salma :D");
		JScrollPane scrollPane0 = new JScrollPane(textArea);
		scrollPane0.setBounds(10, 98, 221, 92);
	    scrollPane0.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		contentPane.add(scrollPane0);
		final JTextArea textArea_1 = new JTextArea();
		textArea_1.setEnabled(false);
		textArea_1.setBounds(10, 216, 221, 99);
		textArea_1.setBorder(border);
		textArea_1.setFont(font);
		textArea_1.setDisabledTextColor(Color.black);
		JScrollPane scrollPane1 = new JScrollPane(textArea_1);
		scrollPane1.setBounds(10, 216, 221, 99);
	    scrollPane1.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		contentPane.add(scrollPane1);
		final JTextArea textArea_2 = new JTextArea();
		textArea_2.setEnabled(false);
		textArea_2.setBounds(10, 371, 221, 274);
		textArea_2.setBorder(border);
		textArea_2.setFont(font);
		textArea_2.setDisabledTextColor(Color.black);
		JScrollPane scrollPane2 = new JScrollPane(textArea_2);//*************************
		scrollPane2.setBounds(10, 371, 221, 274);
	    scrollPane2.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		contentPane.add(scrollPane2);
//		JScrollPane scroll = new JScrollPane(textArea_2);
//	    contentPane.add(scroll);		
		JLabel lblForwardPaths = new JLabel("Forward Paths");
		lblForwardPaths.setFont(new Font("Times New Roman", Font.BOLD, 15));
		lblForwardPaths.setForeground(SystemColor.text);
		lblForwardPaths.setBounds(10, 80, 94, 14);
		contentPane.add(lblForwardPaths);
		
		JLabel lblLoops = new JLabel("Loops");
		lblLoops.setFont(new Font("Times New Roman", Font.BOLD, 15));
		lblLoops.setForeground(SystemColor.window);
		lblLoops.setBounds(10, 191, 46, 14);
		contentPane.add(lblLoops);
		
		JLabel lblSolution = new JLabel("Solution");
		lblSolution.setFont(new Font("Times New Roman", Font.BOLD, 15));
		lblSolution.setForeground(SystemColor.text);
		lblSolution.setBounds(10, 326, 120, 14);
		contentPane.add(lblSolution);
		final JTextArea textArea_3 = new JTextArea();
		textArea_3.setBounds(244, 580, 1000, 150);
		JScrollPane scrollPane3 = new JScrollPane(textArea_3);
		scrollPane3.setBounds(244, 580, 1000, 150);
	    scrollPane3.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		contentPane.add(scrollPane3);


		
		JLabel lblNonTouchedLoops = new JLabel("non touched loops");
		lblNonTouchedLoops.setFont(new Font("Liberation Serif", Font.BOLD, 16));
		lblNonTouchedLoops.setForeground(Color.WHITE);
		lblNonTouchedLoops.setBounds(254, 540, 149, 28);
		contentPane.add(lblNonTouchedLoops);
		
		JButton btnEvaluate = new JButton("Evaluate");
		btnEvaluate.setBackground(new Color(255, 51, 153));
		btnEvaluate.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				boolean [][] adjMatrix=new boolean[drawingArea.shapes.size()][drawingArea.shapes.size()];
				for (int i = 0; i < drawingArea.edges.size(); i++) {
					adjMatrix[drawingArea.edges.get(i).from][drawingArea.edges.get(i).to]=true;
				}
				for (int j = 0; j < adjMatrix.length; j++) {
					for (int i = 0; i < adjMatrix[0].length; i++) {
						System.out.print(adjMatrix[j][i]);
					}
					System.out.println();
				}
				Cycles cycles=new Cycles();
				cycles.matrix=adjMatrix;
				cycles.Input_Edge =  (ArrayList<Edge>) drawingArea.edges.clone() ; 
				cycles.main_menu();
				
				textArea_1.setText(cycles.pathsString);
				PathLoopGeneration paths=new PathLoopGeneration();
				System.out.println(paths.AllPaths.size());
				textArea.setText(paths.getPaths(adjMatrix, 0,adjMatrix.length-1, drawingArea.edges));
				
				
				calculate sd=new calculate(paths,cycles);
				sd.fcalc();
				sd.Ccalc();
				textArea_2.setText(sd.delta());
				textArea_3.setText(sd.un());
			}
		});
		btnEvaluate.setBounds(10, 39, 221, 30);
		contentPane.add(btnEvaluate);		
		


	}
}
