package ex2;

public class EdgeData implements edge_data
{
    int src;
    int dest;
    double weight;
    String info;
    int tag;

    public EdgeData(int src, int dest, double w)
    {
        this.src = src;
        this.dest = dest;
        this.weight = w;
        this.info = "";
        this.tag = 0;
    }

    public EdgeData(edge_data e)
    {
        this.src = e.getSrc();
        this.dest = e.getDest();
        this.weight = e.getWeight();
        this.info = e.getInfo();
        this.tag = e.getTag();
    }

    @Override
    public int getSrc()
    {
        return this.src;
    }

    @Override
    public int getDest()
    {
        return this.dest;
    }

    @Override
    public double getWeight()
    {
        return this.weight;
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
   	//equals method for Edgedata
   	{
   		if(n==null)
   			return false;
   		if(n instanceof edge_data)
   		{
   			if(((EdgeData)n).getSrc()==src&&((EdgeData)n).getDest()==dest&&((EdgeData)n).getWeight()==weight)
   				return true;
   			else return false;
   		}
   		
   	return false;	
   	}
}