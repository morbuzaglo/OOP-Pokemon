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
	private int lastNode;
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
	public String toJSON()
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
	 *sets money
	 *
	 */
	private void setMoney(double v)
	{
		_value = v;
	}
	/**
	 *sets next node for each agents
	 * @return boolean
	 *
	 */
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
	/**
	 *sets current node for each node
	 *
	 */
	public void setCurrNode(int src)
	{
		this._curr_node = _graph.getNode(src);
	}
	/**
	 *check if agent is moving
	 * @return boolean
	 */
	public boolean isMoving()
	{
		return (this._curr_edge != null);
	}
	/**
	 * @ returns string of info
	 *
	 */
	public String toString()
	{
		String ans = "" + this.getID() + "," + _pos+", " + isMoving() + "," + this.getValue();
		return ans;
	}
	/**
	 * @return int
	 * return id of agent
	 *
	 */
	public int getID()
	{
		return this._id;
	}
	/**
	 * @geo_location
	 * gets location of agent
	 *
	 */
	public geo_location getLocation()
	{
		return _pos;
	}
	/**
	 * @return value
	 *returns value of agent
	 */
	public double getValue()
	{
		return this._value;
	}
	/**
	 *gets the next node
	 * @returns int
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
	 *returns speed of agent
	 * @return speed
	 *
	 */
	public double getSpeed()
	{
		return this._speed;
	}
	/**
	 *sets speed
	 *
	 */
	public void setSpeed(double v)
	{
		this._speed = v;
	}
	/**
	 *returns current agent's target
	 * @reurn pokemon
	 */
	public CL_Pokemon get_curr_fruit()
	{
		return _curr_fruit;
	}
	/**
	 *sets agent's next target
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

				if(this.get_curr_fruit().get_edge() == this.get_curr_edge() && stillExist())
				{
					dist = _curr_fruit.getLocation().distance(this._pos);
				}

				double norm = dist/de;
				double dt = w*norm / this.getSpeed();
				ddt = (long)(1000.0*dt);

				//System.out.println("agent: " + getID() + ", speed: " + getSpeed() + ", distToGoal: " + w*norm + ", timeToGoal: " + ddt);
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
			this._curr_fruit = null;
			ar.updatePokemons(ar.getGame().getPokemons());

			bestPok();

			CL_Pokemon p  = this._curr_fruit;

			if(p != null && p.get_edge() != null)
			{
				List<node_data> list = ar.getAlgo().shortestPath(getSrcNode(), p.get_edge().getSrc());
				if(list == null) continue;

				Stack<Integer> st = new Stack<>();

				System.out.print(getID() + ": ");
				for(int i =  list.size()-1; i > 0; i--)
				{
					st.push(list.get(i).getKey());
					System.out.print(list.get(i).getKey() + " ->" );
				}
				System.out.println();

				boolean gotcha = false;
				boolean almostCaught = false;

				this.lastNode = getSrcNode();
				while(!gotcha)
				{
					try
					{
						ar.updateAgents(ar.getGame().getAgents());

						int ind = 0;
						while(isMoving())
						{
							ar.updateAgents(ar.getGame().getAgents());
							//if(ind%1000 == 0) System.out.println("timeToEnd: " + ar.getGame().timeToEnd() + ", id:" + getID() + ": sameEdge?: " + (get_curr_edge() == get_curr_fruit().get_edge()) + ", timeToNext: " + get_sg_dt());
							ind++;
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
							gotcha = true;
							unAvailableEdges.remove(p.get_edge());
						}
						else if(!st.isEmpty() && getSrcNode() == this.lastNode)
						{
							int n = st.pop();
							this.lastNode = n;
							this.ar.getGame().chooseNextEdge(this._id, n);
							//System.out.println(getID() + ": go to -> " + n);
						}
						else if(!almostCaught && getSrcNode() == p.get_edge().getSrc()) // path stack is empty
						{
							int n = p.get_edge().getDest();
							this.ar.getGame().chooseNextEdge(this._id, n);
							//System.out.println(getID() + ": go to -> " + n);

							almostCaught = true;
						}

						if(st.isEmpty() && almostCaught && getSrcNode() == p.get_edge().getDest())
						{
							gotcha = true;
							unAvailableEdges.remove(p.get_edge());
						}

					}
					catch (Exception e)
					{

					}

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