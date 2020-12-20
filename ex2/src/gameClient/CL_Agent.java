package gameClient;

import api.*;
import gameClient.util.Point3D;
import org.json.JSONObject;

import javax.swing.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

public class CL_Agent implements Runnable
{
	public static final double EPS = 0.0001;
	private static int _count = 0;
	private static int _seed = 3331;

	private int _id;
	private double searchRadius;
	private Point3D searchPoint = new Point3D(0,0);
	private geo_location _pos;
	private double _speed;
	private edge_data _curr_edge;
	private node_data _curr_node;
	private directed_weighted_graph _graph;
	private CL_Pokemon _curr_fruit;
	private long _sg_dt;
	private double _value;
	private Arena ar;
	private ImageIcon agent = new ImageIcon("data\\agent1.png");
	public static int count = 0;
	public static ArrayList<edge_data> unAvailableEdges = new ArrayList<>();
	/**
	 *constructor of agent using arena and starting node to place him
	 *
	 */
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
	/**
	 *sets id for agent
	 *
	 */
	public void setID(int id)
	{
		this._id = id;
	}
	/**
	 *sets location
	 *
	 */
	public void setLocation(geo_location p)
	{
		this._pos = p;
	}
	/**
	 * returns src node
	 * @return source node
	 */
	public int getSrcNode()
	{
		return this._curr_node.getKey();
	}
	/**
	 *converts agent data to string
	 * @return string
	 */
	public String toJSON() // converts agent data to string
	{
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
	/**
	 *constructor of agent using arena and starting node to place him
	 *
	 */
	private void setMoney(double v)// sets money
	{
		_value = v;
	}
	/**
	 *constructor of agent using arena and starting node to place him
	 *
	 */
	public boolean setNextNode(int dest)//sets next node for each agents
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
	/**
	 *constructor of agent using arena and starting node to place him
	 *
	 */
	public void setCurrNode(int src)//sets current node for each node
	{
		this._curr_node = _graph.getNode(src);
	}
	/**
	 *constructor of agent using arena and starting node to place him
	 *
	 */
	public boolean isMoving()// check if agent is moving
	{
		return (this._curr_edge != null);
	}
	/**
	 *constructor of agent using arena and starting node to place him
	 *
	 */
	public String toString()
	{
		String ans = "" + this.getID() + "," + _pos+", " + isMoving() + "," + this.getValue();
		return ans;
	}
	/**
	 *constructor of agent using arena and starting node to place him
	 *
	 */
	public int getID()
	{
		return this._id;
	}
	/**
	 *constructor of agent using arena and starting node to place him
	 *
	 */
	public geo_location getLocation()
	{
		return _pos;
	}
	/**
	 *constructor of agent using arena and starting node to place him
	 *
	 */
	public double getValue()
	{
		return this._value;
	}
	/**
	 *constructor of agent using arena and starting node to place him
	 *
	 */
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
	/**
	 *constructor of agent using arena and starting node to place him
	 *
	 */

	public double getSpeed()
	{
		return this._speed;
	}
	/**
	 *constructor of agent using arena and starting node to place him
	 *
	 */
	public void setSpeed(double v)
	{
		this._speed = v;
	}
	/**
	 *constructor of agent using arena and starting node to place him
	 *
	 */
	public CL_Pokemon get_curr_fruit()
	{
		return _curr_fruit;
	}
	/**
	 *constructor of agent using arena and starting node to place him
	 *
	 */
	public void set_curr_fruit(CL_Pokemon curr_fruit)
	{
		this._curr_fruit = curr_fruit;
	}
	/**
	 *constructor of agent using arena and starting node to place him
	 *
	 */
	public void set_SDT(long ddtt) // TODO is correct???
	{
		long ddt = ddtt;
		try
		{
			if(this._curr_edge != null)
			{
				double w = get_curr_edge().getWeight();
				geo_location dest = _graph.getNode(get_curr_edge().getDest()).getLocation();
				geo_location src = _graph.getNode(get_curr_edge().getSrc()).getLocation();

				double de = src.distance(dest);  // src -> dest
				double dist = _pos.distance(dest); // _curr_pos -> dest

				if(this.get_curr_fruit().get_edge() == this.get_curr_edge())
				{
					dist = _curr_fruit.getLocation().distance(this._pos) - dist/10;
				}

				double norm = dist/de;
				double dt = w*norm / this.getSpeed();
				ddt = (long)(1000.0*dt);

				System.out.println("agent: " + getID() + ", speed: " + getSpeed() + ", distToGoal: " + w*norm + ", timeToGoal: " + ddt);
			}
		}
		catch (Exception e)
		{
		}

		this._sg_dt = ddt;
	}
	/**
	 *constructor of agent using arena and starting node to place him
	 *
	 */
	public long get_sg_dt()
	{
		set_SDT(100);
		return _sg_dt;
	}
	/**
	 *constructor of agent using arena and starting node to place him
	 *
	 */
	public edge_data get_curr_edge()
	{
		return this._curr_edge;
	}

	/**
	 *constructor of agent using arena and starting node to place him
	 *
	 */
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
	/**
	 *constructor of agent using arena and starting node to place him
	 *
	 */
	public ImageIcon get_image()
	{
		return agent;
	}
	/**
	 *constructor of agent using arena and starting node to place him
	 *
	 */
	@Override
	public void run()
	{
		while(ar.getGame().isRunning()) // while game IsRunning
		{
			//Ex2.moveAgents(ar);
			this._curr_fruit = null;
			ar.updatePokemons(ar.getGame().getPokemons());

			bestPok();

			CL_Pokemon p  = this._curr_fruit;

			if(p != null && p.get_edge() != null)
			{
				List<node_data> list = ar.getAlgo().shortestPath(getSrcNode(), p.get_edge().getSrc());
				if(list == null) continue;
				//System.out.println("agent " + getID() + " to edge: " + p.get_edge().getSrc() + "->" + p.get_edge().getDest());

				Stack<Integer> st = new Stack<>();

				//System.out.print("Path of agent" + getID() + ": ");
				for(int i =  list.size()-1; i > 0; i--)
				{
					st.push(list.get(i).getKey());
					//System.out.print(list.get(i).getKey() + " ");
				}
				//System.out.println();

				boolean gotcha = false;
				boolean almostCaught = false;

				int c = 0;
				while(!gotcha)
				{
					ar.updateAgents(ar.getGame().getAgents());
					c++;

					while(isMoving())
					{
						//System.out.println("wait");
						ar.updateAgents(ar.getGame().getAgents());
//						try
//						{
//							Thread.sleep(get_sg_dt());
//						}
//						catch (InterruptedException e)
//						{
//							e.printStackTrace();
//						}

						//Ex2.moveAgents(ar);
					}

					try
					{
						ar.updatePokemons(ar.getGame().getPokemons());
					}
					catch (Exception e)
					{
					}

					if(!stillExist())
					{
						//System.out.println("already caught!");
						gotcha = true;
						unAvailableEdges.remove(p.get_edge());
						c--;
					}
					else if(!st.isEmpty())
					{
						int n = st.pop();
						this.ar.getGame().chooseNextEdge(this._id, n);
						c--;
						//System.out.println("PULLED NODE: " + n);

//						try
//						{
//							Thread.sleep(get_sg_dt());
//						}
//						catch (InterruptedException e)
//						{
//							e.printStackTrace();
//						}
					}
					else if(!almostCaught && getSrcNode() == p.get_edge().getSrc()) // path stack is empty
					{
						//System.out.println("src node: " + getSrcNode());
						int n = p.get_edge().getDest();
						this.ar.getGame().chooseNextEdge(this._id, n);

						almostCaught = true;
						c--;
//						try
//						{
//							Thread.sleep(get_sg_dt());
//						}
//						catch (InterruptedException e)
//						{
//							e.printStackTrace();
//						}
					}
					else if(almostCaught && getSrcNode() == p.get_edge().getDest())
					{
						gotcha = true;
						unAvailableEdges.remove(p.get_edge());
						c--;
					}

					if(c > 5) // TODO WHY ARE THEY STUCK ??
					{
						break;
					}

					//System.out.println("almostCaught:" + almostCaught);
					//System.out.println("gotcha:" + gotcha);
					//System.out.println("nextNode:" + getNextNode());

					//System.out.println("pokDest:" + p.get_edge().getDest());
					//System.out.println("srcNode:" + getSrcNode());
					//System.out.println();
					//System.out.println("agent " + getID());
				}
			}
		}
	}
	/**
	 *constructor of agent using arena and starting node to place him
	 *
	 */
	synchronized private void bestPok()
	{
		CL_Pokemon p = null;

		double minDist = Double.POSITIVE_INFINITY;

		dw_graph_algorithms ga = ar.getAlgo();

		try
		{
			synchronized(unAvailableEdges)
			{
				ar.updatePokemons(ar.getGame().getPokemons());
				ar.updateAgents(ar.getGame().getAgents());

				CL_Pokemon temp;

				for (int i = 0; i < ar.getPokemons().size(); i++)
				{
					ar.updatePokemons(ar.getGame().getPokemons());
					ar.updateAgents(ar.getGame().getAgents());
					double d;
					temp = ar.getPokemons().get(i);

					if(!unAvailableEdges.contains(temp.get_edge()) && temp.get_edge() != null)
					{
						try
						{
							d = ga.shortestPathDist(getSrcNode(), temp.get_edge().getSrc());
						}
						catch (Exception e)
						{
							continue;
						}

						if(d < minDist && d != -1)
						{
							minDist = d;
							p = temp;
						}
					}
				}
				//System.out.println("agent " + getID() + " best pok: " + p.get_edge().getSrc() + "->" + p.get_edge().getDest());
			}
		}
		catch (Exception e)
		{
		}

		this._curr_fruit = p;
		if(p != null) unAvailableEdges.add(p.get_edge());
	}
	/**
	 *constructor of agent using arena and starting node to place him
	 *
	 */
	private boolean stillExist()
	{
		ar.updatePokemons(ar.getGame().getPokemons());
		synchronized (ar.getGame())
		{
			return ar.getPoksEdges().contains(_curr_fruit.get_edge());
		}
	}
	/**
	 *constructor of agent using arena and starting node to place him
	 *
	 */
	private boolean isInArea(CL_Pokemon pok)
	{
		if(pok.getLocation().distance(new GeoLoc(searchPoint)) < searchRadius)
		{
			return true;
		}
		else
		{
			return false;
		}
	}
	/**
	 *constructor of agent using arena and starting node to place him
	 *
	 */
	public void setSearchRadius(double R)
	{
		this.searchRadius = R;
	}
	/**
	 *constructor of agent using arena and starting node to place him
	 *
	 */
	public double getSearchRadius()
	{
		return this.searchRadius;
	}
	/**
	 *constructor of agent using arena and starting node to place him
	 *
	 */
	public void setSearchPoint(Point3D p)
	{
		this.searchPoint = p;
	}
	/**
	 *constructor of agent using arena and starting node to place him
	 *
	 */
	public Point3D getSearchPoint()
	{
		return this.searchPoint;
	}
}