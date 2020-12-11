import api.*;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Random;

import static org.junit.jupiter.api.Assertions.*;

class myDWGraph_AlgoTest
{
    private static DWGraph_DS g;
    private static DWGraph_DS gCopy;
    private static DWGraph_Algo ga;
    private static int n;
    private static ArrayList<Integer> keys;
    private static Random rand;

    private static DecimalFormat df2 = new DecimalFormat("#.##");

    @BeforeAll
    static void setup()
    {
        g = new DWGraph_DS();
        ga = new DWGraph_Algo();
        n = 10;
        keys = new ArrayList<Integer>();
        rand = new Random(1);

    }

    @Test
    void copy()
    {

        createWGraph(n,2*n);
        g.getNode(0).setInfo("Ramat Gan");
        g.getNode(9).setInfo("Herzelia");
        g.getNode(9).setInfo("Beer-Sheva");
        g.getNode(5).setInfo("Ariel");

        g.getNode(0).setWeight(50.0);
        g.getNode(0).setLocation(new GeoLoc(2.0,3.0,0.0));

        ga.init(g);

//        System.out.println("** ORIGINAL GRAPH **");
//        printV(g);
//        System.out.println();

        directed_weighted_graph gr = new DWGraph_DS();
        gr = ga.copy();

        assertEquals(gr,g);

        gr.removeNode(0);
        assertNotEquals(gr,g);

//        System.out.println("\n** COPIED GRAPH **");
//        printV((WGraph_DS)gr);

    }

    @Test
    void isConnected()
    {
        createWGraph(5,0);

//        for(int i =0; i< n-1; i++)
//        {
//            g.connect(i,i+1,(double)(i+1));
//            g.connect(i+1, i, (double)(i+1));
//        }

        g.connect(1,0,5.0);
        g.connect(0,4,5.0);
        g.connect(4,3,5.0);
        g.connect(3,2,5.0);
        g.connect(3,1,5.0);
        g.connect(2,4,5.0);
        g.connect(2,1,5.0);
        g.connect(1,2,5.0);


        ga.init(g);
        assertTrue(ga.isConnected());

        //printV(g);

        g.removeNode(4);
        ga.init(g); // required?
        assertFalse(ga.isConnected());

        //System.out.println("\n");
        //printV(g);

    }

    @Test
    void shortestPathDist()
    {
        createWGraph(5,0);

        g.connect(0,2,2.5);
        g.connect(2,1,1.0);
        g.connect(3,1,10.0);
        g.connect(3,4,5.0);
        g.connect(4,0,1.0);
        g.connect(1,4,3.0);

        ga.init(g);


        assertEquals(9.5, ga.shortestPathDist(3,1));
        assertEquals(3.0, ga.shortestPathDist(1,4));
        assertEquals(6.0, ga.shortestPathDist(3,0));

        g.addNode(new NodeData(16));

        assertEquals(-1.0, ga.shortestPathDist(16,4));
    }

    @Test
    void shortestPath()
    {
        createWGraph(5,0);

        g.connect(0,2,2.5);
        g.connect(2,1,1.0);
        g.connect(3,1,10.0);
        g.connect(3,4,5.0);
        g.connect(4,0,1.0);
        g.connect(1,4,3.0);

        ga.init(g);

        List<node_data> path = new ArrayList<>();
        path = ga.shortestPath(3,1); // 3 4 0 2 1
        printPath(path);

        ga.init(g);

        path = ga.shortestPath(1,1);
        //printPath(path);  // needs to be: 1
        //System.out.println();

        path = ga.shortestPath(1,3);
        //printPath(path); //needs to be: 1 -> 3
        //System.out.println();

        path = ga.shortestPath(1,7);
        //printPath(path); //needs to be: 1 -> 3 -> 6 -> 10 -> 7
        //System.out.println();

        path = ga.shortestPath(1,12);
        //printPath(path);  // needs to be: 1
        //System.out.println();

        path = ga.shortestPath(8,6);
        //printPath(path);  // needs to be: 1
        //System.out.println();

    }

    @Test
    void save() throws IOException {
        createWGraph(n,2*n);
        ga.init(g);
        ga.save("data\\save_test_file.json");
        g.removeNode(0);
        ga.save("data\\save_test_after_deleting_node_file.json");
        FileReader jsonFile1 = new FileReader("data\\save_test_file.json");
       String  s1= jsonFile1.toString();
        jsonFile1.close();
       FileReader jsonFile2 = new FileReader("data\\save_test_after_deleting_node_file.json");
       String s2= jsonFile2.toString();
        jsonFile2.close();
        boolean answer=s1.equals(s2);
        assertFalse(answer,"should not be equal ");
    }

    @Test
    void load()
    {
        directed_weighted_graph g1=new DWGraph_DS();
        directed_weighted_graph g2=new DWGraph_DS();
        dw_graph_algorithms algo1=new DWGraph_Algo();
        dw_graph_algorithms algo2=new DWGraph_Algo();
        algo1.init(g1);
        algo2.init(g2);
        algo1.load("data\\save_test_file.json");
        algo2.load("data\\save_test_after_deleting_node_file.json");
        boolean answer =algo1.getGraph().equals(algo2.getGraph());
        assertFalse(answer,"should not be equals");






    }


    /*  ********   HELP FUNCTIONS ********* */


    static void createWGraph(int numOfNones, int numOfCon)
    {
        rand = new Random(1);
        g = new DWGraph_DS();
        keys = new ArrayList<Integer>();

        for(int i = 0; i < numOfNones; i++)
        {
            g.addNode(new NodeData(i));
            keys.add(i);
        }

        int key1;
        int key2;
        double w;

        for(int i = 0; i < numOfCon; i++)
        {
            key1 = keys.get(rand.nextInt(numOfNones));
            key2 = keys.get(rand.nextInt(numOfNones));
            w = 15.0* rand.nextDouble();

            g.connect(key1, key2, w);
        }

    }

    static void printV(DWGraph_DS gr)
    {
        Iterator<node_data> it1 = gr.getV().iterator();

        while(it1.hasNext())
        {
            node_data node = it1.next();
            System.out.print("\n" + node.getKey());

            Iterator<node_data> it2 = ((NodeData)(node)).getNeis().values().iterator();
            System.out.print(" ----->");

            while(it2.hasNext())
            {
                node_data nei = it2.next();
                System.out.print(" " + nei.getKey() + "(w =" + df2.format(gr.getEdge(nei.getKey(),node.getKey())) + ")"
                        + "(info ='" + gr.getNode(nei.getKey()).getInfo()  + "')" + ",");
            }
        }
    }

    static void printV(node_data node)
    {
        Iterator<node_data> it = ((NodeData)(node)).getNeis().values().iterator();
        System.out.print(node.getKey() + " ----->");

        while(it.hasNext())
        {
            node_data nei = it.next();
            System.out.print(" " + nei.getKey() + "(" + df2.format(g.getEdge(nei.getKey(),node.getKey())) + ")" + ",");
        }
    }

    static void printPath(List<node_data> path)
    {
        if(path == null) return;
        int size = path.size();

        for (int i = 0; i < size; i++)
        {
            System.out.print(path.get(i).getKey() + " ----> ");
        }
    }
}