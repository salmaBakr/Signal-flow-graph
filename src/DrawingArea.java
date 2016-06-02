import java.awt.AlphaComposite;
import java.awt.BasicStroke;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.RenderingHints;
import java.awt.Shape;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.awt.geom.Ellipse2D;
import java.awt.geom.GeneralPath;
import java.awt.geom.Line2D;
import java.awt.geom.Rectangle2D;
import java.util.ArrayList;

import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JOptionPane;


public class DrawingArea extends JComponent {
	ArrayList<Shape> shapes = new ArrayList<Shape>();
	boolean drawNode = false;
	boolean drawEdge = false;
	Point startDrag, endDrag;
	int from ,to;
	GeneralPath p;
	GeneralPath t;
	ArrayList<GeneralPath> branchs = new ArrayList<GeneralPath>();
	ArrayList<GeneralPath> triangles = new ArrayList<GeneralPath>();
	ArrayList<Edge> edges = new ArrayList<Edge>();
	ArrayList<Double> dis = new ArrayList<Double>();
	public DrawingArea() {
		this.addMouseListener(new MouseAdapter() {
			public void mousePressed(MouseEvent e) {
				if (drawNode) {
					startDrag = new Point(e.getX(), e.getY());
					endDrag = startDrag;
				} else if (drawEdge) {
					startDrag = new Point(e.getX(), e.getY());
					endDrag = startDrag;
					boolean flag = false;
					for (int i = 0; i < shapes.size(); i++) {
						if (shapes.get(i).contains(e.getX(), e.getY())) {
							startDrag = new Point(e.getX(), e.getY());
							endDrag = startDrag;
							flag = true;
							from=i;
							break;
						}
					}
					if (!flag) {
						drawEdge = false;
						JOptionPane.showMessageDialog(null, "draw valid branch", "Draw branches",
								JOptionPane.ERROR_MESSAGE);

					}
				}
				repaint();
			}

			public void mouseReleased(MouseEvent e) {
				if (drawNode) {
					Shape r = new Ellipse2D.Float(e.getX(), e.getY(), 20, 20);
					shapes.add(r);
					dis.add((double) 0);
					startDrag = null;
					endDrag = null;
					drawNode = false;
				}
				if (drawEdge) {
					
					boolean flag = false;
					for (int i = 0; i < shapes.size(); i++) {
						if (shapes.get(i).contains(endDrag.getX(), endDrag.getY())) {
							flag = true;
							to=i;
							break;
						}
					}
					if (!flag) {
						startDrag = null;
						endDrag = null;
						JOptionPane.showMessageDialog(null, "draw valid branch", "Draw branches",
								JOptionPane.ERROR_MESSAGE);

					}
					else {
						try {
							drawEdge = false;
							dis.set(from,dis.get(from)+1);
							dis.set(to,dis.get(to)+1);
							branchs.add((GeneralPath) p.clone());
							triangles.add((GeneralPath) t.clone());
							endDrag = null;
							 String inputValue = JOptionPane.showInputDialog("Please ,enter the value of gain"); 
							 while (!inputValue.matches("^[-+]?\\d+(\\.\\d+)?$")) {
								 inputValue = JOptionPane.showInputDialog("Please ,enter valid gain"); 
							}
							 Edge temp=new Edge();
							 temp.from=from;
							 temp.to=to;
							 temp.gain=Double.parseDouble(inputValue);
							 edges.add(temp);
							 drawEdge=false;
							 startDrag = null;
								endDrag = null;
						} catch (Exception e2) {
							// TODO: handle exception
						}
						
					}
				}
				repaint();
			}
		});

		this.addMouseMotionListener(new MouseMotionAdapter() {
			public void mouseDragged(MouseEvent e) {
				endDrag = new Point(e.getX(), e.getY());
				repaint();
			}
		});
	}

	private void paintBackground(Graphics2D g2) {
		g2.setPaint(Color.LIGHT_GRAY);
		for (int i = 0; i < getSize().width; i += 10) {
			Shape line = new Line2D.Float(i, 0, i, getSize().height);
			g2.draw(line);
		}
		for (int i = 0; i < getSize().height; i += 10) {
			Shape line = new Line2D.Float(0, i, getSize().width, i);
			g2.draw(line);
		}

	}

	public void paint(Graphics g) {
		Graphics2D g2 = (Graphics2D) g;
		//	g2.setPaint(Color.black);
		g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
				RenderingHints.VALUE_ANTIALIAS_ON);
		g2.setStroke(new BasicStroke(2));
		g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER,
				0.50f));
		
		if (startDrag != null && endDrag != null && drawEdge) {
			double x7 = 0, y7 = 0, theta = 0;
			p = new GeneralPath();
			p.moveTo(startDrag.x, startDrag.y);
			// NORMAL
			if (startDrag.distance(endDrag) > 1) {
				theta = Math.atan2(endDrag.y - startDrag.y, endDrag.x
						- startDrag.x);
				double x3 = (startDrag.x + endDrag.x) / 2;
				double y3 = (startDrag.y + endDrag.y) / 2;
				double x4 = x3 + 32 * dis.get(from)  * Math.sin(theta);
				double y4 = y3 - 32 * dis.get(from) * Math.cos(theta);// ***********
				double x5 = (x4 + startDrag.x) / 2;
				double y5 = (y4 + startDrag.y) / 2;
				double x6 = (x4 + endDrag.x) / 2;
				double y6 = (y4 + endDrag.y) / 2;
				x7 = (x5 + x6) / 2;
				y7 = (y5 + y6) / 2;
				p.quadTo(x4, y4, endDrag.x, endDrag.y);
			}
			// SELF LOOP
			else {
				p.curveTo(startDrag.x - 32, startDrag.y - 32, startDrag.x + 32,
						startDrag.y - 32, startDrag.x, startDrag.y);
				x7 = startDrag.x + 4;
				y7 = startDrag.y - 24;
			}
			g2.draw(p);
			triangle((Graphics2D) g, x7, y7, theta, 0.5, 8);
		}
		Font font = new Font("Arial", Font.BOLD, 12);
		g.setFont(font);
		paintBackground(g2);
		try {
			for (int i = 0; i < branchs.size(); i++) {
				g2.setPaint(Color.BLACK);
				g2.draw(branchs.get(i));
				g2.fill(triangles.get(i));
				g.setColor(new Color(255, 51, 153));
				g.drawString(edges.get(i).gain.toString(),(int) triangles.get(i).getBounds2D().getX(),(int) (triangles.get(i).getBounds2D().getY()-5));

			}
		} catch (Exception e) {
			// TODO: handle exception
		}
		
		g2.setColor(Color.black);

		g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER,
				0.99f));
		for (int i=0;i<shapes.size();++i) {
			g2.setColor(Color.black);
			g2.draw(shapes.get(i));
			g2.fill(shapes.get(i));
			g.drawString("a"+i,(int) shapes.get(i).getBounds2D().getX(),(int) (shapes.get(i).getBounds2D().getY()+40));

		}
		g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER,
				0.50f));
	}

	public void triangle(Graphics2D g, double x, double y, double theta,
			double epsilon, double r) {
		t = new GeneralPath();
		t.moveTo(x, y);
		t.lineTo(x - r * Math.cos(theta + epsilon),
				y - r * Math.sin(theta + epsilon));
		t.lineTo(x - r * Math.cos(theta - epsilon),
				y - r * Math.sin(theta - epsilon));
		g.fill(t);
	}
}
