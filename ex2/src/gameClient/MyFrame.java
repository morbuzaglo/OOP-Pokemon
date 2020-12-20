package gameClient;

import api.directed_weighted_graph;
import api.edge_data;
import api.geo_location;
import api.node_data;
import gameClient.util.Point3D;
import gameClient.util.Range;
import gameClient.util.Range2D;
import org.json.JSONException;
import org.json.JSONObject;
import javax.swing.*;
import javax.swing.border.TitledBorder;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.Iterator;
import java.util.List;

public class MyFrame extends JFrame
{
	private int _ind;
	private Arena _ar;
	private gameClient.util.Range2Range _w2f;
	/**
	 *basic constructor
	 *
	 */
	MyFrame(String a)
	{
		super(a);
		int _ind = 0;
	}
	/**
	 *updates the arena and also updates the main frame
	 */
	public void update(Arena ar)
	{
		this._ar = ar;
		updateFrame();
	}
	/**
	 *updates frame, changes ranges of the frame i.e range in x and y
	 *
	 */
	private void updateFrame()
	{
		Range rx = new Range(100,this.getWidth()-100);
		Range ry = new Range(this.getHeight()-30,300);
		Range2D frame = new Range2D(rx,ry);
		directed_weighted_graph g = _ar.getGraph();
		_w2f = Arena.w2f(g,frame);
	}
	/**
	 *main paint function for frame, it handles anti flaring effect by buffiring and also draws all components
	 *
	 */
	public void paint(Graphics g)
	{
		int w = this.getWidth();
		int h = this.getHeight();
		Image buffer_image;
		Graphics buffer_graphics;
		buffer_image = createImage(w, h);		// Create a new "canvas"
		buffer_graphics=buffer_image.getGraphics();
		paintComponents(buffer_graphics);// Draw on the new "canvas"
		g.drawImage(buffer_image, 0, 0, this);		// "Switch" the old "canvas" for the new one
	}
	/**
	 *paints all sub components on the frame
	 *
	 */
	@Override
	public void paintComponents(Graphics g)
	{
		int w = this.getWidth();
		int h = this.getHeight();
		g.clearRect(0, 0, w, h);
		ImageIcon background =new ImageIcon("data\\background1.png");//adds background
		BufferedImage resizedImage = new BufferedImage(w, h, BufferedImage.TYPE_INT_RGB);
		Graphics2D graphics2D = resizedImage.createGraphics();
		graphics2D.drawImage(background.getImage(), 0, 0, w, h, null);
		graphics2D.dispose();
		g.drawImage(resizedImage,0,0,background.getImageObserver());
		updateFrame();
		drawGraph(g);
		drawAgants(g);
		drawPokemons(g);
	}
	/**
	 *draws info currently not used
	 *
	 */
	private void drawInfo(Graphics g)
	{
		List<String> str = _ar.get_info();
		String dt = "none";
		for(int i=0;i<str.size();i++)
		{
			g.drawString(str.get(i)+" dt: "+dt,100,60+i*20);
		}
	}
	/**
	 *draws the main graph such as nodes edges timer score and move counters
	 *
	 */
	private void drawGraph(Graphics g)
	{
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		JPanel panel_2 = new JPanel();
		panel_2.setBorder(new TitledBorder(null, "", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		panel_2.setBounds(this.getWidth()/2-120, 10, 300, 50);
		panel_2.setLayout(null);
		JLabel timeleft = new JLabel("  Time left to end the game : "+_ar.getGame().timeToEnd()/1000.0);
		timeleft.setFont(new Font("TimesRoman", Font.PLAIN, 16));
		timeleft.setForeground(Color.red);
		this.add(timeleft);
		timeleft.setBounds(6, 0, 300, 50);
		panel_2.add(timeleft);
		this.add(panel_2);
		double sum=0;
		for(int i=0;i<_ar.getAgents().size();i++)
		{
			sum=sum+_ar.getAgents().get(i).getValue();
		}
		JPanel panel_1 = new JPanel();
		panel_1.setBorder(new TitledBorder(null, "", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		panel_1.setBounds(this.getWidth()/2-65, 80, 200, 50);
		panel_1.setLayout(null);
		JLabel score = new JLabel("  current score is  : "+sum);
		score.setFont(new Font("TimesRoman", Font.PLAIN, 16));
		score.setForeground(Color.black);
		this.add(score);
		score.setBounds(6, 0, 200, 50);
		panel_1.add(score);
		this.add(panel_1);
		JPanel panel_3 = new JPanel();
		panel_3.setBorder(new TitledBorder(null, "", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		panel_3.setBounds(this.getWidth()/2-65, 150, 200, 50);
		panel_3.setLayout(null);
		String s="";
		try
		{
			JSONObject game =new JSONObject(_ar.getGame().toString());
			s=game.getJSONObject("GameServer").getInt("moves")+"";
		}
		catch (JSONException e)
		{
			e.printStackTrace();
		}
		JLabel moves = new JLabel("  current moves are  : "+s);
		moves.setFont(new Font("TimesRoman", Font.PLAIN, 16));
		moves.setForeground(Color.black);
		this.add(moves);
		moves.setBounds(6, 0, 200, 50);
		panel_3.add(moves);
		this.add(panel_3);
		directed_weighted_graph gg = _ar.getGraph();
		Iterator<node_data> iter = gg.getV().iterator();
		while(iter.hasNext())
		{
			node_data n = iter.next();
			Iterator<edge_data> itr = gg.getE(n.getKey()).iterator();
			while(itr.hasNext())
			{
				edge_data e = itr.next();
				g.setColor(Color.black);
				drawEdge(e, g);
				g.setColor(Color.blue);
			}
		}
		iter = gg.getV().iterator();
		while(iter.hasNext())
		{
			node_data n = iter.next();
			drawNode(n,5,g);
		}
	}
	/**
	 *draws pockemons
	 *
	 */
	private void drawPokemons(Graphics g)//draws pockemons
	{
		List<CL_Pokemon> fs = _ar.getPokemons();
		if(fs != null)
		{
			Iterator<CL_Pokemon> itr = fs.iterator();

			while(itr.hasNext())
			{
				CL_Pokemon f = itr.next();
				Point3D c = f.getLocation();
				int r=10;
				g.setColor(Color.green);
				if(f.getType() < 0)
				{
					g.setColor(Color.orange);
				}
				if(c!=null)
				{
					geo_location fp = this._w2f.world2frame(c);
					g.drawImage(f.get_image().getImage(),(int)fp.x()-25, (int)fp.y()-25,f.get_image().getImageObserver());
				}
			}
		}
	}
	/**
	 *draws agents ( by pictures)
	 *
	 */
	private void drawAgants(Graphics g)
	{
		List<CL_Agent> rs = _ar.getAgents();
		g.setColor(Color.red);
		int i=0;
		while(rs!=null && i<rs.size())
		{
			geo_location c = rs.get(i).getLocation();
			int r=8;
			if(c!=null)
			{
				g.setFont(new Font("TimesRoman", Font.PLAIN, 28));
				geo_location fp = this._w2f.world2frame(c);
				g.drawImage(rs.get(i).get_image().getImage(), (int) fp.x() - r, (int) fp.y() - r, rs.get(i).get_image().getImageObserver());
				g.drawString(""+rs.get(i).getID(), (int) fp.x() - 39/2, (int) fp.y() - 13/2);
			}

			i++;
		}
	}
	/**
	 *draws nodes ( by pictures)
	 *
	 */
	private void drawNode(node_data n, int r, Graphics g)
	{
		g.setFont(new Font("TimesRoman", Font.PLAIN, 16));
		geo_location pos = n.getLocation();
		geo_location fp = this._w2f.world2frame(pos);
		ImageIcon poky = new ImageIcon("data\\poki_resized.png");
		g.drawImage(poky.getImage(),(int)fp.x()-7, (int)fp.y()-7,poky.getImageObserver());
		g.drawString(""+n.getKey(), (int)fp.x(), (int)fp.y()-4*r);
	}
	/**
	 *draws edges on the graph
	 *
	 */
	private void drawEdge(edge_data e, Graphics g)
	{
		directed_weighted_graph gg = _ar.getGraph();
		geo_location s = gg.getNode(e.getSrc()).getLocation();
		geo_location d = gg.getNode(e.getDest()).getLocation();
		geo_location s0 = this._w2f.world2frame(s);
		geo_location d0 = this._w2f.world2frame(d);
		((Graphics2D)g).setStroke(new BasicStroke(5));
		g.drawLine((int)s0.x(), (int)s0.y(), (int)d0.x(), (int)d0.y());
	}
}