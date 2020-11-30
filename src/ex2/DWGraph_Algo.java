package ex2;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Iterator;
import java.util.List;


import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import org.json.JSONTokener;

public class DWGraph_Algo implements dw_graph_algorithms
{
	private directed_weighted_graph g = new DWGraph_DS();

	public DWGraph_Algo() // Default contructor
	{
		g = new DWGraph_DS();
	}

	@Override
	public void init(directed_weighted_graph g) // init graph DWGraph_ds
	{
		this.g=g;
	}

	@Override
	public directed_weighted_graph getGraph()
	{
		return g;
	}

	@Override
	public directed_weighted_graph copy()
	{
		// TODO implement copy().
		return null;
	}

	@Override
	public boolean isConnected() {
		// TODO implement isConnected().
		return false;
	}

	@Override
	public double shortestPathDist(int src, int dest) {
		// TODO implement shortestPathDist().
		return 0;
	}

	@Override
	public List<node_data> shortestPath(int src, int dest) {
		// TODO implement shortestPath().
		return null;
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

		// System.out.println(json_object.toString()); //printing the json object to check if its alright //TODO Delete.

		try(FileWriter jsonfile = new FileWriter(file))
		{
			jsonfile.write(json_object.toString()); // opining a file writer to convert json object to string and add it to the file
			jsonfile.close();
			//TODO Delete .close() -> https://rules.sonarsource.com/java/RSPEC-4087
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
}






