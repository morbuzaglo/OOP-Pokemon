package gameClient;

import api.*;
import gameClient.util.Point3D;
import org.json.JSONObject;

import javax.swing.*;
import javax.swing.plaf.TableHeaderUI;
import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

public class CL_Agent implements Runnable
{
		public static final double EPS = 0.0001;
		private static int _count = 0;
		private static int _seed = 3331;

		private int _id;
		// private int key;

		private geo_location _pos;
		private double _speed;
		private edge_data _curr_edge;
		private node_data _curr_node;
		private directed_weighted_graph _graph;
		private CL_Pokemon _curr_fruit;
		private long _sg_dt;
		private double _value;
		private Arena ar;
		private ImageIcon agent = new ImageIcon("agent1.png");

		public static int count = 0;
		public static ArrayList<CL_Pokemon> unAvailablePoks = new ArrayList<>();

		public CL_Agent(Arena ar, int start_node)
		{
			this.ar = ar;
			_graph = ar.getGraph();
			setMoney(0);
			this._curr_node = _graph.getNode(start_node);
			_pos = _curr_node.getLocation();
			_id = -1;
			setSpeed(0);
		}


		public void setID(int id)
		{
			this._id = id;
		}

		public void setLocation(geo_location p)
		{
			this._pos = p;
		}

		//@Override
		public int getSrcNode()
		{
			return this._curr_node.getKey();
		}

		public String toJSON() {
			int d = this.getNextNode();
			String ans = "{\"Agent\":{"
					+ "\"id\":"+this._id+","
					+ "\"value\":"+this._value+","
					+ "\"src\":"+this._curr_node.getKey()+","
					+ "\"dest\":"+d+","
					+ "\"speed\":"+this.getSpeed()+","
					+ "\"pos\":\""+_pos.toString()+"\""
					+ "}"
					+ "}";
			return ans;	
		}

		private void setMoney(double v)
		{
			_value = v;
		}
	
		public boolean setNextNode(int dest)
		{
			boolean ans = false;
			int src = this._curr_node.getKey();
			this._curr_edge = _graph.getEdge(src, dest);

			if(_curr_edge != null)
			{
				ans = true;
			}
			else
			{
				_curr_edge = null;
			}
			return ans;
		}

		public void setCurrNode(int src)
		{
			this._curr_node = _graph.getNode(src);
		}

		public boolean isMoving()
		{
			return (this._curr_edge != null);
		}

		public String toString()
		{
			String ans = "" + this.getID() + "," + _pos+", " + isMoving() + "," + this.getValue();
			return ans;
		}

		public int getID()
		{
			return this._id;
		}
	
		public geo_location getLocation()
		{
			return _pos;
		}
		
		public double getValue()
		{
			return this._value;
		}

		public int getNextNode()
		{
			int ans = -2;
			if(this._curr_edge == null)
			{
				ans = -1;
			}
			else
			{
				ans = this._curr_edge.getDest();
			}
			return ans;
		}

		public double getSpeed()
		{
			return this._speed;
		}

		public void setSpeed(double v)
		{
			this._speed = v;
		}

		public CL_Pokemon get_curr_fruit()
		{
			return _curr_fruit;
		}

		public void set_curr_fruit(CL_Pokemon curr_fruit)
		{
			this._curr_fruit = curr_fruit;
		}

		public void set_SDT(long ddtt) // TODO is correct???
		{
			long ddt = ddtt;
			if(this._curr_edge != null)
			{
				double w = get_curr_edge().getWeight();
				geo_location dest = _graph.getNode(get_curr_edge().getDest()).getLocation();
				geo_location src = _graph.getNode(get_curr_edge().getSrc()).getLocation();

				double de = src.distance(dest);  // src -> dest
				double dist = _pos.distance(dest); // _curr_pos -> dest

				if(this.get_curr_fruit().get_edge() == this.get_curr_edge())
				{
					 dist = _curr_fruit.getLocation().distance(this._pos);
				}

				double norm = dist/de;
				double dt = w*norm / this.getSpeed(); 
				ddt = (long)(1000.0*dt);
			}

			this._sg_dt = _sg_dt;
		}

	public long get_sg_dt()
	{
		set_SDT(100);
		return _sg_dt;
	}

	public edge_data get_curr_edge()
		{
			return this._curr_edge;
		}


	public void update(String json)
	{
		JSONObject line;
		try
		{
			line = new JSONObject(json);
			JSONObject t = line.getJSONObject("Agent");
			int id = t.getInt("id");

			if( id == this.getID() || this.getID() == -1)
			{
				if(this.getID() == -1) _id = id;

				double speed = t.getDouble("speed");
				String p = t.getString("pos");
				Point3D pp = new Point3D(p);
				int src = t.getInt("src");
				int dest = t.getInt("dest");
				double value = t.getDouble("value");

				this._pos = pp;
				this.setCurrNode(src);
				this.setSpeed(speed);
				this.setNextNode(dest);
				this.setMoney(value);
			}
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
	}

	public ImageIcon get_image()
	{
		return agent;
	}

	@Override
	public void run()
	{
		while(ar.getGame().isRunning()) // while game IsRunning
		{
			this._curr_fruit = null;

			bestPok();

			CL_Pokemon p  = this._curr_fruit;
			ar.updatePokemons(ar.getGame().getPokemons());

			if(p != null && p.get_edge() != null)
			{
				List<node_data> list = ar.getAlgo().shortestPath(getSrcNode(), p.get_edge().getSrc());
				if(list == null) continue;
				//System.out.println("SrcNode:" + getSrcNode());

				Stack<Integer> st = new Stack<>();

				for(int i =  list.size()-1; i > 0; i--)
				{
					st.push(list.get(i).getKey());
					System.out.print("Path of agent" + getID() + ": "+ list.get(i).getKey() + " ");
				}
				System.out.println();

				boolean gotcha = false;
				boolean almostCaught = false;

				while(!gotcha)
				{
					ar.updateAgents(ar.getGame().getAgents());

					while(isMoving())
					{
						//System.out.println("wait");
						ar.updateAgents(ar.getGame().getAgents());
					}

					ar.updatePokemons(ar.getGame().getPokemons());

					if(!st.isEmpty())
					{
						int n = st.pop();
						this.ar.getGame().chooseNextEdge(this._id, n);

						//System.out.println("PULLED NODE: " + n);

						try
						{
							Thread.sleep(get_sg_dt());
						}
						catch (InterruptedException e)
						{
							e.printStackTrace();
						}
					}
					else if(!almostCaught && getSrcNode() == p.get_edge().getSrc()) // path stack is empty
					{
						//System.out.println("src node: " + getSrcNode());
						int n = p.get_edge().getDest();
						this.ar.getGame().chooseNextEdge(this._id, n);

						almostCaught = true;

						try
						{
							Thread.sleep(get_sg_dt());
						}
						catch (InterruptedException e)
						{
							e.printStackTrace();
						}
					}
					else if(almostCaught && getSrcNode() == p.get_edge().getDest())
					{
						gotcha = true;
						unAvailablePoks.remove(p); // TODO unAvailablePoks doens't good! (pokemon always created as new).
					}
					//System.out.println("almostCaught:" + almostCaught);
					//System.out.println("gotcha:" + gotcha);
					//System.out.println("nextNode:" + getNextNode());

					//System.out.println("pokDest:" + p.get_edge().getDest());
					//System.out.println("srcNode:" + getSrcNode());
					//System.out.println();
				}
			}
		}
	}

	synchronized private void bestPok()
	{
		CL_Pokemon p = null;

		double minDist = Double.POSITIVE_INFINITY;

		dw_graph_algorithms ga = ar.getAlgo();

		//synchronized (ar.getPokemons())
		//{

			for (int i =0; i < ar.getPokemons().size(); i++)  // TODO change -> to בעיית המזכירה!
			{
				ar.updatePokemons(ar.getGame().getPokemons());
				ar.updateAgents(ar.getGame().getAgents());

				if(!unAvailablePoks.contains(ar.getPokemons().get(i)) && ar.getPokemons().get(i).get_edge() != null)
				{
					double d = ga.shortestPathDist(getSrcNode(), ar.getPokemons().get(i).get_edge().getSrc());

					if(d < minDist && d != -1)
					{
						p = ar.getPokemons().get(i);
					}
				}
			}

			this._curr_fruit = p;
			unAvailablePoks.add(p);
		//}
	}
}
