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

public class DWGraph_Algo implements dw_graph_algorithms {
	private directed_weighted_graph g= new DWGraph_DS();
	// normal contructor
	public DWGraph_Algo()
	{
		g=new DWGraph_DS();
	}
	// init graph dwgraph ds
	@Override
	public void init(directed_weighted_graph g) {
		this.g=g;
	}

	@Override
	public directed_weighted_graph getGraph() {
		// TODO Auto-generated method stub
		return g;
	}

	@Override
	public directed_weighted_graph copy() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean isConnected() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public double shortestPathDist(int src, int dest) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public List<node_data> shortestPath(int src, int dest) {
		// TODO Auto-generated method stub
		return null;
	}
	// save file using JSON format
	@Override
	public boolean save(String file) {


		// original json object
		JSONObject json_object = new JSONObject();
		// json array list for edges
		JSONArray edgeslist =new  JSONArray();
		// json array list for nodes

		JSONArray nodeslist =new  JSONArray();
		Iterator<node_data> nodes = g.getV().iterator();
		//init itterator so i could add nodes in node list then go through
		//every neighbor of each node so i can add them to edge list 
		//appiding by the format that was asked from us to do
		//then add both lists to the original json object
		while(nodes.hasNext())
		{
			JSONObject json_temp = new JSONObject();
			node_data node_temp=nodes.next();
			try {
				// here i add the nodes id and pos to the nodelist array
				json_temp.put("pos",((NodeData)node_temp).getLocation().toString());
				json_temp.put("id", node_temp.getKey());
				nodeslist.put(json_temp);
			} catch (JSONException e) {
				e.printStackTrace();
				return false;

			}
			Iterator<node_data> node_neighbors = ((NodeData)node_temp).getNeis().values().iterator();
			while(node_neighbors.hasNext())
			{
				node_data node_neighbors_temp=node_neighbors.next();
				json_temp = new JSONObject();
				try {
					if(node_neighbors_temp.getKey()!=node_temp.getKey())
					{
						// here i add the connections between the nodes i the 
						//graph in edges list array
						json_temp.put("src",node_temp.getKey());
						json_temp.put("w", g.getEdge(node_temp.getKey(), node_neighbors_temp.getKey()).getWeight());
						json_temp.put("dest",node_neighbors_temp.getKey());
						( edgeslist).put(json_temp);
					}
				} catch (JSONException e) {
					e.printStackTrace();
					return false;

				}
			}

		}
		try {
			// adding both arrays to original json object
			json_object.put("Edges", edgeslist);
			json_object.put("Nodes", nodeslist);
		} catch (JSONException e) {
			e.printStackTrace();
			return false;

		}
		//printing the json object to check if its alright ( delete)
		System.out.println(json_object.toString());

		try(FileWriter jsonfile=new FileWriter(file))
		{
			// opining a file writer to convert json object to string and add it to the file
			jsonfile.write(json_object.toString());
			jsonfile.close();
		}
		catch(IOException e)
		{
			e.printStackTrace();
			return false;
		}
		return true;
	}

	@Override
	public boolean load(String file) {
		// config json tokener so i can convert file to json tokener
		JSONTokener buffer;

		try {
			FileReader file_;
			try {
				file_ = new FileReader(file);
				//converting file to json tokener
				buffer = new JSONTokener(file_);
				//config temp jsonobjec to manupliate the buffer readings
				JSONObject json_object_temp=new JSONObject();
				// add buffer tokener and graph string as a key
				json_object_temp.put("graph", buffer.nextValue());
				//config an original json object
				JSONObject json_object=new JSONObject();
				//extracting the value that appends graph key and insert into json object and casting
				json_object=(JSONObject) json_object_temp.get("graph");
				// configing edge list and node list json arrays
				JSONArray edgeslist =new  JSONArray();
				JSONArray nodeslist =new  JSONArray();
				// inserting values into both arrays using the original jason object
				edgeslist=(JSONArray) json_object.get("Edges");
				nodeslist=(JSONArray) json_object.get("Nodes");
				// constructing new graph DWgraph ds
				directed_weighted_graph g_copy= new DWGraph_DS();
				// going over nodes list creating the nodes inserting postions and adding later to new graph
				for(int i=0;i<nodeslist.length();i++)
				{
					node_data N=new NodeData(nodeslist.getJSONObject(i).getInt("id"));
					geo_location p=new Geolocation(nodeslist.getJSONObject(i).getString("pos"));
					N.setLocation(p);
					g_copy.addNode(N);
				}
				// going over edge list and connecting existing nodes to together 
				for(int i=0;i<edgeslist.length();i++)
				{
					g_copy.connect(edgeslist.getJSONObject(i).getInt("src"), edgeslist.getJSONObject(i).getInt("dest"), edgeslist.getJSONObject(i).getDouble("w"));

				}
				// init graph to be the new graph
				init(g_copy);
				//closing file
				file_.close();



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






