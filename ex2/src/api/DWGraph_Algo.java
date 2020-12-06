package api;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;


import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import org.json.JSONTokener;

public class DWGraph_Algo implements dw_graph_algorithms
{
	private directed_weighted_graph g;
	private HashMap<Integer, Double> t;
	private HashMap<Integer, ArrayList<node_data>> _paths;
	public DWGraph_Algo() // Default constructor
	{
		this.g = new DWGraph_DS();
		this.t = new HashMap<Integer, Double>();
		this._paths = new HashMap<>();
	}

	@Override
	public void init(directed_weighted_graph g) // init graph DWGraph_ds
	{
		this.g = g;
		this.t = new HashMap<Integer, Double>();
		this._paths = new HashMap<>();
	}

	@Override
	public directed_weighted_graph getGraph()
	{
		return g;
	}

	@Override
	public directed_weighted_graph copy()  // deep-copy fo a graph.
	{
			if(g == null) return null;
			directed_weighted_graph newg = new DWGraph_DS();  // the new graph.
		Iterator<node_data> it0 = this.g.getV().iterator();
			while(it0.hasNext()) // copies all the nodes from the copied graph to the new one.
			{
				node_data n = it0.next();
				int k = n.getKey();
				newg.addNode(new NodeData(n));
				newg.getNode(k).setInfo(g.getNode(k).getInfo());  // copies the nodes info as well.
			}

			Iterator<node_data> it1 = this.g.getV().iterator();
			int keyNei;
			int keyNode;
			edge_data w =new EdgeData(0,0,0);
			while(it1.hasNext())  // connect the nodes of the new graph, the same way the copied one is.
			{
				keyNode = it1.next().getKey();
				NodeData N=(NodeData)(g.getNode(keyNode));
				Iterator<node_data> it2 = (N.getNeis().values().iterator());
				while(it2.hasNext())  // checking all nodes for connection.
				{
					keyNei = it2.next().getKey();

					w = g.getEdge(keyNode, keyNei);


					newg.connect(keyNode, keyNei, w.getWeight());
				}
			}
			return newg;
		}

	@Override
	public boolean isConnected()
	{
		// TODO implement isConnected().

		if(g.getV().isEmpty() || g.getV().size() == 1)
		{
			return true; // empty graph or with only 1 node -> considered connected.
		}
		else
		{
			node_data node = g.getV().iterator().next(); // random node (just to check connectivity).
			Dijkstra(node.getKey(),node.getKey(), 0);  // 0 means => only checking connectivity or path distance (see in Dijkstra static method).

			boolean is = true;  // first assumption: is connected.

			Iterator<node_data> it = g.getV().iterator();
			while(it.hasNext() && is)
			{
				if(t.get(it.next().getKey()) == -1) is = false;  // means -> someone HAS NOT been visited. therefore -> false.
			}

			if(is == false) return is;


			node = g.getV().iterator().next(); // random node (just to check connectivity).

			directed_weighted_graph gtemp = this.g;
			this.g = copy_opposite();

			Dijkstra(node.getKey(),node.getKey(), 0);  // 0 means => only checking connectivity or path distance (see in Dijkstra static method).

			it = g.getV().iterator();
			while(it.hasNext() && is)
			{
				if(t.get(it.next().getKey()) == -1) is = false;  // means -> someone HAS NOT been visited. therefore -> false.
			}

			this.g = gtemp;
			return is;
		}
	}

	@Override
	public double shortestPathDist(int src, int dest)
	{
		if(g.getNode(src) == null || g.getNode(dest) == null ) return -1.0; // if the there aren't such nodes in graph
		else if(src == dest) return 0.0; // if they're the same node.
		else
		{
			Dijkstra(src, dest, 0);  // 0 means => only looking for distance(double) or checking connectivity.
			double tag = this.t.get(dest);

			return tag;
		}
	}
	@Override
	public List<node_data> shortestPath(int src, int dest)
	{
		if(g.getNode(src) == null || g.getNode(dest) == null)
			return null; // if the there aren't such nodes in graph
		else
		{
			List<node_data> path = new ArrayList<node_data>();

			if (src == dest) // if they're the same node.
			{
				path.add(g.getNode(src));
				return path;
			}

			Dijkstra(src, dest, 1); // 1 means -> for searching an actual node_info path, for a List<node_info>.

			double tag = this.t.get(dest);
			if (tag == -1.0) return null;

			path = _paths.get(dest);
			return path;
		}
	}

	@Override
	public boolean save(String file) // save file using JSON format
	{
		JSONObject json_object = new JSONObject(); // original json object
		JSONArray edgesList = new  JSONArray(); // json array list for edges
		JSONArray nodesList = new  JSONArray(); // json array list for nodes

		Iterator<node_data> nodes = g.getV().iterator(); // init iterator so I could add all the nodes.

		while(nodes.hasNext())
		{
			JSONObject json_temp = new JSONObject();
			node_data node_temp=nodes.next();

			try // adding nodes' id and pos to the nodeList array
			{
				json_temp.put("pos",((NodeData)node_temp).getLocation().toString());
				json_temp.put("id", node_temp.getKey());

				nodesList.put(json_temp);
			}
			catch (JSONException e)
			{
				e.printStackTrace();
				return false;
			}

			Iterator<node_data> node_neighbors = ((NodeData)node_temp).getNeis().values().iterator();

			while(node_neighbors.hasNext())
			{
				node_data node_neighbors_temp=node_neighbors.next();
				json_temp = new JSONObject();

				try // adding the connections between the nodes in the graph into edgesList array.
				{
					if(node_neighbors_temp.getKey()!=node_temp.getKey())
					{
						json_temp.put("src",node_temp.getKey());
						json_temp.put("w", g.getEdge(node_temp.getKey(), node_neighbors_temp.getKey()).getWeight());
						json_temp.put("dest",node_neighbors_temp.getKey());

						(edgesList).put(json_temp);
					}
				}
				catch (JSONException e)
				{
					e.printStackTrace();
					return false;
				}
			}
		}

		try // adding both arrays to original json object.
		{
			json_object.put("Edges", edgesList);
			json_object.put("Nodes", nodesList);
		}
		catch (JSONException e)
		{
			e.printStackTrace();
			return false;
		}

		try(FileWriter jsonFile = new FileWriter(file))
		{
			jsonFile.write(json_object.toString()); // opining a file writer to convert json object to string and add it to the file
			jsonFile.close();
		}
		catch(IOException e)
		{
			e.printStackTrace();
			return false;
		}
		return true;
	}
	@Override
	public boolean load(String file)
	{
		JSONTokener buffer; // config json tokenizer so i can convert file to json tokenizer

		try
		{
			FileReader file_;
			try
			{
				file_ = new FileReader(file);
				buffer = new JSONTokener(file_); //converting file to json tokenizer
				JSONObject json_object_temp = new JSONObject(); //config temp json object to manipulate the buffer readings

				json_object_temp.put("graph", buffer.nextValue()); // add buffer tokenizer and graph string as a key
				JSONObject json_object = new JSONObject(); // config an original json object
				json_object=(JSONObject) json_object_temp.get("graph"); // extracting the value that appends graph key and insert into json object and casting

				JSONArray edgesList =new  JSONArray(); // configuring edge list and node list json arrays
				JSONArray nodesList =new  JSONArray();

				edgesList=(JSONArray) json_object.get("Edges"); // inserting values into both arrays using the original jason object
				nodesList=(JSONArray) json_object.get("Nodes");

				directed_weighted_graph g_copy = new DWGraph_DS(); // constructing new graph DWGraph_DS

				for(int i=0;i<nodesList.length();i++) // going over nodes list creating the nodes inserting positions and adding later to new graph
				{
					node_data N=new NodeData(nodesList.getJSONObject(i).getInt("id"));
					geo_location p=new GeoLoc(nodesList.getJSONObject(i).getString("pos"));
					N.setLocation(p);
					g_copy.addNode(N);
				}
				for(int i=0;i<edgesList.length();i++) // going over edge list and connecting existing nodes to together
				{
					g_copy.connect(edgesList.getJSONObject(i).getInt("src"), edgesList.getJSONObject(i).getInt("dest"), edgesList.getJSONObject(i).getDouble("w"));

				}

				init(g_copy); // init graph to be the new graph
				file_.close(); //closing file
			} 
			catch (FileNotFoundException e2)
			{
				e2.printStackTrace();
				return false;
			}
		}
		catch(Exception e)
		{
			e.printStackTrace();
			return false;
		}
		return true;
	}
	/* ****************************** */


	private void Dijkstra(int s, int d, int choose)
	// "choose" parameter: 0 -> connectivity/double path, 1 -> node_info List path.
	{
		try
		{
			if(g.getNode(s) != null && g.getNode(d) != null) // if one of the nodes does not exist.
			{
				LinkedList<Integer> queue = new LinkedList<Integer>();

				if (choose == 0)  // if we call Dijkstra for checking connectivity/ double path distance.
				{
					g.getV().forEach(n -> this.t.put(n.getKey(), -1.0)); // default tag.

					queue.add(s);
					this.t.put(s, 0.0); // distance from the source == 0 (it is the source).

					double t1;
					double t2;
					while (!queue.isEmpty())
					{
						s = queue.poll();

						Iterator<node_data> i = ((NodeData)(g.getNode(s))).getNeis().values().iterator();
						while (i.hasNext())
						{
							int k = i.next().getKey();

							t1 = this.t.get(s);
							t2 = g.getEdge(s, k).getWeight();

							if (this.t.get(k) == -1.0)  // haven't been visited
							{
								this.t.put(k,t1 + t2);
								queue.add(k);
							}
							else
							{
								if (this.t.get(k) > t1 +t2)
								{
									this.t.put(k, t1 + t2);
									queue.add(k);
								}
							}
						}
					}
				}
				else  // is call from List<node_info> ShortestPath:
				{
					g.getV().forEach(n -> this.t.put(n.getKey(), -1.0)); //  Initialization:
					_paths.clear();

					for (node_data node : g.getV())
					{
						_paths.put(node.getKey(), new ArrayList<node_data>());
					}

					queue.add(s);
					_paths.get(s).add(g.getNode(s));
					this.t.put(s, 0.0); // distance from the source == 0. (it is the source).

					double t1;
					double t2;
					while (!queue.isEmpty())
					{
						s = queue.poll();

						Iterator<node_data> i = ((NodeData)(g.getNode(s))).getNeis().values().iterator();
						while (i.hasNext())
						{
							int k = i.next().getKey();

							t1 = this.t.get(s);
							t2 = g.getEdge(s, k).getWeight();
							if (this.t.get(k) == -1.0)  // haven't been visited
							{
								_paths.get(k).addAll(_paths.get(s));  // add the ist of nodes that is the fastest way (till now).
								_paths.get(k).add(g.getNode(k));
								this.t.put(k, t1 +t2); //adding +1 to the way from the source.
								queue.add(k);
							}
							else
							{
								if (this.t.get(k) > t1 + t2)   // if the other way has shortest path -> put it instead.
								{
									this.t.put(k, t1 + t2);
									_paths.get(k).removeAll(_paths.get(k));
									_paths.get(k).addAll(_paths.get(s));
									_paths.get(k).add(g.getNode(k));
									queue.add(k);
								}
							}
						}
					}
				}
			}
			else
				return;

		}
		catch (Exception e)
		{
			System.out.println(e + ", Problem: Graph_Algo -> private: Dijkstra");
		}
	}

	public directed_weighted_graph copy_opposite()  // deep-copy fo a graph.
	{
		if(g == null) return null;
		directed_weighted_graph newg = new DWGraph_DS();  // the new graph.
		Iterator<node_data> it0 = this.g.getV().iterator();
		while(it0.hasNext()) // copies all the nodes from the copied graph to the new one.
		{
			node_data n = it0.next();
			int k = n.getKey();
			newg.addNode(new NodeData(n));
			newg.getNode(k).setInfo(g.getNode(k).getInfo());  // copies the nodes info as well.
		}

		Iterator<node_data> it1 = this.g.getV().iterator();
		int keyNei;
		int keyNode;
		edge_data w =new EdgeData(0,0,0);

		while(it1.hasNext())  // connect the nodes of the new graph, the same way the copied one is.
		{
			keyNode = it1.next().getKey();
			NodeData N=(NodeData)(g.getNode(keyNode));
			Iterator<node_data> it2 = (N.getNeis().values().iterator());
			while(it2.hasNext())  // checking all nodes for connection.
			{
				keyNei = it2.next().getKey();
				w = g.getEdge(keyNode, keyNei);
				newg.connect(keyNei, keyNode, w.getWeight());
			}
		}
		return newg;
	}
}

	







