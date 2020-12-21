package gameClient;

import api.*;
import gameClient.util.Point3D;
import gameClient.util.Range;
import gameClient.util.Range2D;
import gameClient.util.Range2Range;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
/**
 *
 *
 */
public class Arena
{
	/**
	 *
	 *
	 */
	public static final double EPS1 = 0.001, EPS2=EPS1*EPS1, EPS=EPS2;
	private game_service game;
	private directed_weighted_graph _graph;
	private List<CL_Agent> _agents;
	private List<CL_Pokemon> _pokemons;
	private List<String> _info;
	private dw_graph_algorithms ga;
	private static Point3D MIN = new Point3D(0, 100,0);
	private static Point3D MAX = new Point3D(0, 100,0);
	private ArrayList<edge_data> PoksEdges = new ArrayList<>();
	/**
	 *defult constructor of arena
	 *
	 */
	public Arena()
	{
		this._info = new ArrayList<String>();
		this.game = game;
	}
	/**
	 *main constructor using game service
	 *
	 */
	/**
	 *
	 * @param game
	 */
	public Arena(game_service game)
	{
		this.game = game;
		this._info = new ArrayList<String>();

		init();
	}
	/**
	 *returns array list of all pokemons
	 *
	 */
	public ArrayList<edge_data> getPoksEdges()
	{
		return this.PoksEdges;
	}
	/**
	 *sets pokemon list
	 *
	 */
	public void setPokemons(List<CL_Pokemon> f)
	{
		this._pokemons = f;
	}
	/**
	 *sets agebts list
	 *
	 */
	public void setAgents(List<CL_Agent> f)
	{
		this._agents = f;
	}
	/**
	 *sets graph
	 *
	 */
	public void setGraph(directed_weighted_graph g)
	{
		this._graph = g;
		init();
	}
	/**
	 *inits arens with all traits
	 *
	 */
	private void init()
	{
		String gStr = game.getGraph();
		String pStr = game.getPokemons();
		directed_weighted_graph g = new DWGraph_DS();
		dw_graph_algorithms ga = new DWGraph_Algo();
		this.ga = ga;
		ga.init(g);
		String fileName = "data\\JsonGraph.json";
		CreateFile(fileName, gStr);
		ga.load(fileName);
		this._graph = ga.getGraph();
		updatePokemons(pStr);
		updateWinSize();
	}
	/**
	 *returns a list of all agents
	 * @returns list
	 */
	public List<CL_Agent> getAgents()  // HAVE TO UPDATE FIRST!
	{
		return _agents;
	}
	/**
	 *returns a list of all pokemons
	  *@returns list
	 *
	 */
	public List<CL_Pokemon> getPokemons()
	{
		return _pokemons;
	}  // HAVE TO UPDATE FIRST!
	/**
	 *returns graph
	 * @returns graph
	 *
	 */
	public directed_weighted_graph getGraph()
	{
		return _graph;
	}
	/**
	 *
	 *returns a list of all infos
	 *@returns list
	 */
	public List<String> get_info()
	{
		return _info;
	}
	/**
	 *sets info list
	 *
	 */
	public void set_info(List<String> _info)
	{
		this._info = _info;
	}
	/**
	 *returns game
	 *@returns game_service
	 *
	 */
	public game_service getGame()
	{
		return this.game;
	}
	/**
	 *returns the graph algo
	 *@returns graph_algo
	 *
	 */
	public dw_graph_algorithms getAlgo()
	{
		return this.ga;
	}

	/* * * * * * * * * * * * * * * * * *  UPDATE FUNCTIONS * * * * * * * * * * * * * * * * * * * * * * */
	/**
	 *
	 *updates window size
	 */
	public void updateWinSize()
	{

		MIN = null; MAX = null;
		double x0 = 0, x1 = 0, y0 = 0, y1 = 0;

		Iterator<node_data> iter = this._graph.getV().iterator();

		while(iter.hasNext())
		{
			geo_location c = iter.next().getLocation();
			if(MIN == null)
			{
				x0 = c.x();
				y0 = c.y();
				x1 = x0;
				y1 = y0;
				MIN = new Point3D(x0, y0);
			}

			if(c.x() < x0)
			{
				x0 = c.x();
			}
			if(c.y() < y0)
			{
				y0 = c.y();
			}
			if(c.x() > x1)
			{
				x1 = c.x();
			}
			if(c.y() > y1)
			{
				y1 = c.y();
			}
		}

		double dx = x1 - x0;
		double dy = y1 - y0;
		MIN = new Point3D(x0 - dx/10,y0 - dy/10);
		MAX = new Point3D(x1 + dx/10,y1 + dy/10);
	}
	/**
	 *
	 *adding new agents
	 */
	public void addNewAgents(String AgentsData)
	{
		ArrayList<CL_Agent> ans = new ArrayList<>();
		try
		{
			JSONObject t = new JSONObject(AgentsData);
			JSONArray ags = t.getJSONArray("Agents");
			for(int i = 0; i < ags.length(); i++)
			{
				CL_Agent agent = new CL_Agent(this,0);
				ans.add(agent);
				agent.update(ags.get(i).toString());
			}
		}
		catch (JSONException e)
		{
			e.printStackTrace();
		}
		this._agents =  ans;

		double R = MIN.distance(MAX)/2;
		System.out.println("R ====" + R);
		if(_agents.size() == 2 || _agents.size() == 1)
		{
			Iterator<CL_Agent> iter = _agents.iterator();

			while(iter.hasNext())
			{
				CL_Agent ag= iter.next();
				ag.setSearchRadius(R);
				double x = (MIN.x()+MAX.x())/2;
				double y = (MIN.y()+MAX.y())/2;
				ag.setSearchPoint(new Point3D(x,y));
			}
		}
		else
		{
			int size = _agents.size();
			double rad = 2*Math.PI/size;
			double xc = (MIN.x()+MAX.x())/2;
			double yc = (MIN.y()+MAX.y())/2;

			double x;
			double y;

			int count = 0;

			Iterator<CL_Agent> iter = _agents.iterator();

			while(iter.hasNext())
			{
				x = xc + R*Math.sin(count*rad);
				y = yc + R*Math.cos(count*rad);

				count++;

				CL_Agent ag= iter.next();
				ag.setSearchRadius(R);
				ag.setSearchPoint(new Point3D(x,y));
			}
		}
	}
	/**
	 *
	 *updates agents using string from game_service
	 */
	public void updateAgents(String currAgentsData) // the string
	{
		try
		{
			JSONObject t = new JSONObject(currAgentsData);
			JSONArray ags = t.getJSONArray("Agents");
			for(int i = 0; i < ags.length(); i++)
			{
				getAgents().get(i).update(ags.get(i).toString());
			}
		}
		catch (JSONException e)
		{
			e.printStackTrace();
		}
	}
	/**
	 *updates pokemon status in arena using string from game_service
	 *
	 */
	public void updatePokemons(String fs)
	{
		try
		{
			synchronized (game)
			{
				ArrayList<CL_Pokemon> ans = new  ArrayList<CL_Pokemon>();
				try
				{
					JSONObject ttt = new JSONObject(fs);
					JSONArray ags = ttt.getJSONArray("Pokemons");

					for(int i = 0; i < ags.length(); i++)
					{
						JSONObject pp = ags.getJSONObject(i);
						JSONObject pk = pp.getJSONObject("Pokemon");
						int t = pk.getInt("type");
						double v = pk.getDouble("value");
						//double s = 0;//pk.getDouble("speed");
						String p = pk.getString("pos");

						CL_Pokemon f = new CL_Pokemon(new Point3D(p), t, v, 0, null);
						ans.add(f);
					}
				}
				catch (JSONException e)
				{
					e.printStackTrace();
				}
				this._pokemons = ans;

				for(int i = 0; i < ans.size(); i++)
				{
					updateEdge(getPokemons().get(i), getGraph());
				}

				ArrayList<edge_data> temp = new ArrayList<>();

				for(int i = 0; i < getPokemons().size(); i++)
				{
					temp.add(getPokemons().get(i).get_edge());
				}

				PoksEdges = temp;
			}
		}
		catch (Exception e)
		{
			return;
		}
	}
	/**
	 *updates edges on arena
	 *
	 */
	public void updateEdge(CL_Pokemon fr, directed_weighted_graph g)
	{
		//	oop_edge_data ans = null;
		Iterator<node_data> itr = g.getV().iterator();
		while(itr.hasNext())
		{
			node_data v = itr.next();
			Iterator<edge_data> iter = g.getE(v.getKey()).iterator();
			while(iter.hasNext())
			{
				edge_data e = iter.next();
				boolean f = isOnEdge(fr.getLocation(), e,fr.getType(), g);
				if(f)
				{
					fr.set_edge(e);
				}
			}
		}
	}
	/**
	 *check if pkimon is on edge
	 * @retuns boolean
	 */
	private static boolean isOnEdge(geo_location p, geo_location src, geo_location dest )
	{
		boolean ans = false;
		double dist = src.distance(dest);
		double d1 = src.distance(p) + p.distance(dest);
		if(dist>d1-EPS2) {ans = true;}
		return ans;
	}
	/**
	 *check if pkimon is on edge
	 * @retuns boolean
	 */
	private static boolean isOnEdge(geo_location p, int s, int d, directed_weighted_graph g)
	{
		geo_location src = g.getNode(s).getLocation();
		geo_location dest = g.getNode(d).getLocation();
		return isOnEdge(p,src,dest);
	}
	/**
	 *check if pkimon is on edge
	 * @retuns boolean
	 */
	private static boolean isOnEdge(geo_location p, edge_data e, int type, directed_weighted_graph g)
	{
		int src = g.getNode(e.getSrc()).getKey();
		int dest = g.getNode(e.getDest()).getKey();
		if(type<0 && dest>src) {return false;}
		if(type>0 && src>dest) {return false;}
		return isOnEdge(p,src, dest, g);
	}
	/**
	 *returns range2D of window
	 *
	 */
	private static Range2D GraphRange(directed_weighted_graph g)
	{
		Iterator<node_data> itr = g.getV().iterator();
		double x0=0,x1=0,y0=0,y1=0;
		boolean first = true;
		while(itr.hasNext())
		{
			geo_location p = itr.next().getLocation();
			if(first) {
				x0=p.x(); x1=x0;
				y0=p.y(); y1=y0;
				first = false;
			}
			else
			{
				if(p.x()<x0) {x0=p.x();}
				if(p.x()>x1) {x1=p.x();}
				if(p.y()<y0) {y0=p.y();}
				if(p.y()>y1) {y1=p.y();}
			}
		}
		Range xr = new Range(x0,x1);
		Range yr = new Range(y0,y1);
		return new Range2D(xr,yr);
	}
	/**
	 *
	 *returns range2Range of window
	 */
	public static Range2Range w2f(directed_weighted_graph g, Range2D frame)
	{
		Range2D world = GraphRange(g);
		Range2Range ans = new Range2Range(world, frame);
		return ans;
	}
	/**
	 *
	 *creates file
	 */
	public void CreateFile(String name, String g)
	{
		try
		{
			File myObj = new File(name);
			myObj.createNewFile();
		}
		catch (IOException e)
		{
			System.out.println("An error occurred: Arena -> CreateFile - create new file.");
			e.printStackTrace();
		}

		try
		{
			FileWriter myWriter = new FileWriter(name);
			myWriter.write(g);
			myWriter.close();
		} catch (IOException e)
		{
			System.out.println("An error occurred: Arena -> CreateFile - writing to file.");
			e.printStackTrace();
		}
	}
}
