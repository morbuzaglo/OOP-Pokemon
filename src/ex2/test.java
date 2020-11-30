package ex2;


public class test {

	public static void main(String[] args) {
		dw_graph_algorithms graph=new DWGraph_Algo();
		directed_weighted_graph g0=new DWGraph_DS();
		for(int i=0;i<10;i++)
		{
	     node_data N1=new NodeData(i);
	     geo_location p=new GeoLoc(3.999994494949,4.888888,0);
	     N1.setLocation(p);
	     g0.addNode(N1);
		}
		g0.connect(0, 1, 2.6);
		g0.connect(0, 2, 2.6);
		g0.connect(0, 3, 2.6);
		g0.connect(0, 4, -1);
		g0.connect(0, 5, 0);
		graph.init(g0);
		graph.save("j.json");
		graph.load("j.json");
		graph.save("j8.json");

		
		
	

	}

}
