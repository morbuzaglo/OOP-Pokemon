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

public class Arena
{
	public static final double EPS1 = 0.001, EPS2=EPS1*EPS1, EPS=EPS2;
	private game_service game;
	private directed_weighted_graph _graph;
	private List<CL_Agent> _agents;
	private List<CL_Pokemon> _pokemons;
	private List<String> _info;

	private static Point3D MIN = new Point3D(0, 100,0);
	private static Point3D MAX = new Point3D(0, 100,0);

	public Arena()
	{
		this._info = new ArrayList<String>();
	}

	public Arena(game_service game)
	{
		this.game = game;
		this._info = new ArrayList<String>();

		init();
	}

	public void setPokemons(List<CL_Pokemon> f)
	{
		this._pokemons = f;
	}

	public void setAgents(List<CL_Agent> f)
	{
		this._agents = f;
	}

	public void setGraph(directed_weighted_graph g)
	{
		this._graph = g;
		init();
	}

	private void init()
	{
		String gStr = game.getGraph();
		String pStr = game.getPokemons();

		directed_weighted_graph g = new DWGraph_DS();
		dw_graph_algorithms ga = new DWGraph_Algo();
		ga.init(g);

		String fileName = "JsonGraph.json";
		CreateFile(fileName, gStr);
		ga.load(fileName);
		this._graph = ga.getGraph();

		updatePokemons(pStr);
		updateWinSize();

	}

	public List<CL_Agent> getAgents()  // HAVE TO UPDATE FIRST!
	{
		return _agents;
	}

	public List<CL_Pokemon> getPokemons()
	{
		return _pokemons;
	}  // HAVE TO UPDATE FIRST!

	public directed_weighted_graph getGraph()
	{
		return _graph;
	}

	public List<String> get_info()
	{
		return _info;
	}

	public void set_info(List<String> _info)
	{
		this._info = _info;
	}

	public game_service getGame()
	{
		return this.game;
	}


	/* * * * * * * * * * * * * * * * * *  UPDATE FUNCTIONS * * * * * * * * * * * * * * * * * * * * * * */

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

	public void updateAgents(String currAgentsData, directed_weighted_graph gg) // the string
	{
		ArrayList<CL_Agent> ans = new ArrayList<CL_Agent>();

		try
		{
			JSONObject t = new JSONObject(currAgentsData);
			JSONArray ags = t.getJSONArray("Agents");
			for(int i = 0; i < ags.length(); i++)
			{
				CL_Agent agent = new CL_Agent(gg,0);
				agent.update(ags.get(i).toString());
				ans.add(agent);
			}
		}
		catch (JSONException e)
		{
			e.printStackTrace();
		}
		this._agents =  ans;
	}

	public void updatePokemons(String fs)
	{
		ArrayList<CL_Pokemon> ans = new  ArrayList<CL_Pokemon>();
		try
		{
			JSONObject ttt = new JSONObject(fs);
			JSONArray ags = ttt.getJSONArray("Pokemons");

			for(int i=0;i<ags.length();i++)
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
		catch (JSONException e) {e.printStackTrace();}
		this._pokemons = ans;
	}

	public static void updateEdge(CL_Pokemon fr, directed_weighted_graph g)
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
				if(f) {fr.set_edge(e);}
			}
		}
	}

	private static boolean isOnEdge(geo_location p, geo_location src, geo_location dest )
	{
		boolean ans = false;
		double dist = src.distance(dest);
		double d1 = src.distance(p) + p.distance(dest);
		if(dist>d1-EPS2) {ans = true;}
		return ans;
	}

	private static boolean isOnEdge(geo_location p, int s, int d, directed_weighted_graph g)
	{
		geo_location src = g.getNode(s).getLocation();
		geo_location dest = g.getNode(d).getLocation();
		return isOnEdge(p,src,dest);
	}

	private static boolean isOnEdge(geo_location p, edge_data e, int type, directed_weighted_graph g)
	{
		int src = g.getNode(e.getSrc()).getKey();
		int dest = g.getNode(e.getDest()).getKey();
		if(type<0 && dest>src) {return false;}
		if(type>0 && src>dest) {return false;}
		return isOnEdge(p,src, dest, g);
	}

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

	public static Range2Range w2f(directed_weighted_graph g, Range2D frame)
	{
		Range2D world = GraphRange(g);
		Range2Range ans = new Range2Range(world, frame);
		return ans;
	}

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
