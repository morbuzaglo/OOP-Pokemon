package ex2;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Iterator;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class DWGraph_Algo implements dw_graph_algorithms
{
	private directed_weighted_graph g= new DWGraph_DS();

	public DWGraph_Algo()
	{
		g = new DWGraph_DS();
	}
	
	@Override
	public void init(directed_weighted_graph g)
	{
		this.g = g;
	}

	@Override
	public directed_weighted_graph getGraph()
	{
		return g;
	}

	@Override
	public directed_weighted_graph copy()
	{
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean isConnected()
	{
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public double shortestPathDist(int src, int dest)
	{
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public List<node_data> shortestPath(int src, int dest)
	{
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean save(String file)
	{
		
	
		
        JSONObject json_object = new JSONObject();
        
			try {
				json_object.put("key", 5);
			} catch (JSONException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
	
        JSONArray edgeslist =new  JSONArray();
        JSONArray nodeslist =new  JSONArray();
        Iterator<node_data> nodes = g.getV().iterator();
        while(nodes.hasNext())
        {
            JSONObject json_temp = new JSONObject();
        	node_data node_temp=nodes.next();
        	try {
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
    				json_temp.put("src",node_temp.getKey());
    				json_temp.put("w", g.getEdge(node_temp.getKey(), node_neighbors_temp.getKey()));
    				json_temp.put("dest",node_temp.getKey());
    				( edgeslist).put(json_temp);
    			} catch (JSONException e) {
    				e.printStackTrace();
    		        return false;

    			}
            }
            
        }
        try {
			json_object.put("Edges", edgeslist);
			json_object.put("Nodes", nodeslist);
		} catch (JSONException e) {
			e.printStackTrace();
	        return false;

		}
        
        //System.out.println(json_object.toString());
        try(FileWriter jsonfile=new FileWriter(file))
        {
        jsonfile.write(json_object.toString());
        jsonfile.flush();
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
		// TODO Auto-generated method stub
		return false;
	}

	}


