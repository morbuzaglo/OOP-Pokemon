package api;

import java.util.HashMap;

public class NodeData implements node_data
{
    int key;
    geo_location p;
    double weight;
    String info;
    int tag;
    private HashMap<Integer, node_data> Neighbors;

    public NodeData(int key)
    {
        this.key = key;
        this.weight = 0;
        this.p = null;
        this.info = "";
        this.tag = 0;
        this.Neighbors = new HashMap<Integer, node_data>(); // only the neighbors that this directed to.
      //  Neighbors.put(key, this);
    }

    public NodeData(node_data n)
    {
        this.key = n.getKey();
        this.weight = n.getWeight();
        this.p = n.getLocation();
        this.info = n.getInfo();
        this.tag = n.getTag();
        this.Neighbors = new HashMap<Integer, node_data>(); // only the neighbors that this directed to.
        //Neighbors.put(key, this);  // A NODE IS A NEIGHBOR TO ITSELF.
    }

    public HashMap<Integer, node_data> getNeis() // get HashMap of this node's neighbors.
    {
        return Neighbors;
    }

    public boolean hasNei(int key) // is key a neighbor of this node?
    {
        return Neighbors.containsKey(key);
    }

    public void addNei(node_data nei) // adding a neighbor to this node.
    {
        int keyNei = nei.getKey();
        Neighbors.put(keyNei,nei);  // add to the neis list.
    }

    @Override
    public int getKey()
    {
        return this.key;
    }

    @Override
    public geo_location getLocation()
    {
        return this.p;
    }

    @Override
    public void setLocation(geo_location p)
    {
        this.p = p;
    }

    @Override
    public double getWeight()
    {
        return this.weight;
    }

    @Override
    public void setWeight(double w)
    {
        this.weight = w;
    }

    @Override
    public String getInfo()
    {
        return this.info;
    }

    @Override
    public void setInfo(String s)
    {
        this.info = s;
    }

    @Override
    public int getTag()
    {
        return this.tag;
    }

    @Override
    public void setTag(int t)
    {
        this.tag = t;
    }
    @Override
	public boolean equals(Object n)
	//TODO complete this method with neighbors 
	//equals method for nodedata
	{
		if(n==null)
			return false;
		if(n instanceof node_data)
		{
			if(((NodeData)n).getKey()==key&&((NodeData)n).getInfo().equals(info)&&((NodeData)n).getWeight()==weight&&((NodeData)n).getLocation().equals(p))
				return true;
			else return false;
		}
		
	return false;	
	}
    
    
    
}
