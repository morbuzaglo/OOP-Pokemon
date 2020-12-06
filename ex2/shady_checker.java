package ex2;

public class shady_checker {

	public static void main(String[] args) {
		directed_weighted_graph original_graph=new DWGraph_DS();
		for(int i=0;i<3;i++)
		{
			node_data N=new NodeData(i);
			geo_location p=new GeoLoc("0,0,0");
			N.setLocation(p);
			original_graph.addNode(N);
		}
		original_graph.connect(0, 1, 1);
		original_graph.connect(0, 2, 9);
		original_graph.connect(1, 0, 1);
		

		dw_graph_algorithms original_algo=new DWGraph_Algo();
		original_algo.init(original_graph);
		original_algo.save("original.json");
		directed_weighted_graph copy_graph=new DWGraph_DS();
		dw_graph_algorithms copy_algo=new DWGraph_Algo();
		copy_graph=original_algo.copy();
		//System.out.println(((DWGraph_DS)copy_graph).nodeSize());
		copy_algo.init(copy_graph);
		copy_algo.save("copy.json");
		System.out.println(original_algo.equals(original_algo.copy()));


		
	}

}
