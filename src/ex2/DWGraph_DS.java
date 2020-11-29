package ex2;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;

public class DWGraph_DS implements directed_weighted_graph
{
    private int mc;  // this graph's number of changes.
    private int numOfNodes;
    private int numOfEdges;
    private HashMap<Integer, node_data> _nodes;  // HashMap of all the nodes the graph have.
    private HashMap<Integer, HashMap<Integer, edge_data>> _edges;  // HashMap of all the edges between the nodes.

    public DWGraph_DS() //Default constructor
    {
        this.mc = 0;
        this.numOfEdges = 0;
        this.numOfNodes = 0;
        this._nodes = new HashMap<Integer, node_data>();
        this._edges = new HashMap<Integer, HashMap<Integer, edge_data>>();
    }

    public boolean hasEdge(int src, int dest)
    {
        return (_edges.containsKey(src) && _edges.get(src).containsKey(dest)); // if they have a directed connection.
    }

    @Override
    public node_data getNode(int key)
    {
        return _nodes.get(key);  //returns the key associated node_data.
    }

    @Override
    public edge_data getEdge(int src, int dest)
    {
        if(_edges.get(src) != null)
        {
            return _edges.get(src).get(dest);
        }
        else return null;
    }

    @Override
    public void addNode(node_data n)
    {
        //TODO is the NodeData class supposed to be inner or not?
        if(!this._nodes.containsKey(n.getKey()))
        {
            this._nodes.put(n.getKey(),n);
            this._edges.put(n.getKey(), new HashMap<Integer, edge_data>());
            mc++;
            numOfNodes++;
        }
    }

    @Override
    public void connect(int src, int dest, double w)
    {
        try
        {
            if(!((NodeData)(_nodes.get(src))).hasNei(dest)) // if NOT neighbors
            {
                numOfEdges++;
                ((NodeData)(_nodes.get(src))).addNei(_nodes.get(dest)); // ONLY adding to src (DIRECTED).
                _edges.get(src).put(dest,new EdgeData(src,dest,w));
                mc++;
            }
            else if(src != dest && w != _edges.get(src).get(dest).getWeight()) // if already neighbors, but 'w' needs to change AND if they are both not the same key.
            {
                _edges.get(src).put(dest, new EdgeData(src, dest, w));
                mc++;
            }
            else
                return; // if already neighbors && w is the same as before -> DO NOTHING!
        }
        catch (Exception e) // if at least one node does not exist in this graph.
        {
            e.printStackTrace();
            return;
        }
    }

    @Override
    public Collection<node_data> getV()
    {
        return this._nodes.values();
    }

    @Override
    public Collection<edge_data> getE(int node_id)
    {
        return this._edges.get(node_id).values();
    }

    @Override
    public node_data removeNode(int key)
    {
        try
        {
            NodeData node = (NodeData) _nodes.get(key);
            Collection<node_data> List = node.getNeis().values();
            ArrayList<node_data> arrList = new ArrayList<node_data>();

            Iterator<node_data> it = List.iterator();
            while (it.hasNext())     // copy all it's neighbors to a temp ArrayList,
            {					     // because we can't iterate and remove at the same time.
                node_data nei = it.next();
                arrList.add(nei);
            }

            int keyNei;
            int size = List.size();
            for (int j = 0; j < size; j++)
            {
                keyNei = arrList.get(j).getKey();
                if (keyNei != key) // if they keys are same -> DO NOT DELETE EDGE! (the node most still have itself as a neighbor).
                {
                    this.removeEdge(key, keyNei);  // remove BOTH SIDES of connection between the nodes.
                    this.removeEdge(keyNei, key);
                }
            }

            mc++;
            this.numOfNodes--;
            this._nodes.remove(key, node); // remove the node from the graph's list of nodes.
            this._edges.remove(key); // remove the node from the edges list.

            return node;
        }
        catch (Exception e) // if there's no node associated with that key
        {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public edge_data removeEdge(int src, int dest)
    {
        try
        {
            if(src == dest) return null; // DO NOTHING, because this edge cannot be removed.
            else if(((NodeData) _nodes.get(src)).hasNei(dest)) // if they are connected.
            {
                ((NodeData) _nodes.get(src)).getNeis().remove(dest); // remove ONLY this directed connection (edge from dest to src NOT REMOVED!).

                edge_data edge = _edges.get(src).remove(dest);
                mc++;
                numOfEdges--;

                return edge;
            }
            else
                return null; // if they are not connected -> DO NOTHING!
        }
        catch (Exception e) // if at least one of the keys invalid (or not exist).
        {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public int nodeSize()
    {
        return this.numOfNodes;
    }

    @Override
    public int edgeSize()
    {
        return this.numOfEdges;
    }

    @Override
    public int getMC()
    {
        // TODO make sure all the places mc++ should be.
        return this.mc;
    }

}
