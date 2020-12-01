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
		try
		{
			if(!this._nodes.containsKey(n.getKey()))
			{
				this._nodes.put(n.getKey(),n);
				this._edges.put(n.getKey(), new HashMap<Integer, edge_data>());
				mc++;
				numOfNodes++;
			}
		}
		catch(Exception NullPointerException)
		{
			System.out.println("DWGraph_DS -> addNode: node_data n is null.");
			return;
		}
	}

	@Override
	public void connect(int src, int dest, double w)
	{
		try
		{
			if(!((NodeData)(_nodes.get(src))).hasNei(dest)) // if NOT neighbors
			{

				((NodeData)(_nodes.get(src))).addNei(_nodes.get(dest)); // ONLY adding to src (DIRECTED).
				_edges.get(src).put(dest,new EdgeData(src,dest,w));
				mc++;
				numOfEdges++;
			}
			else if(src == dest) throw new Exception();
			else if(w != _edges.get(src).get(dest).getWeight()) // if already neighbors, but 'w' needs to change AND if they are both not the same key.
			{
				_edges.get(src).put(dest, new EdgeData(src, dest, w));
				mc++;
			}
			else
				return; // if already neighbors && w is the same as before -> DO NOTHING!
		}
		catch (Exception e) // if at least one node does not exist in this graph.
		{
			System.out.println("DWGraph_DS -> connect: at least one of the nodes not in graph, or src == dest.");
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
		if(this.getNode(key) == null) return null; // DO NOTHING, the key is already not in the graph.
		ArrayList<node_data> nodes = new ArrayList<>();
		ArrayList<edge_data> edges = new ArrayList<>();

		for(node_data nTemp : this.getV())
		{
			nodes.add(new NodeData(nTemp));

			for(edge_data eTemp : this.getE(nTemp.getKey()))
			{
				edges.add(new EdgeData(eTemp));
			}
		}

		NodeData node = (NodeData)_nodes.get(key);

		for(node_data nTemp : nodes) // looking all the nodes for connection to the key's node.
		{
			for(edge_data eTemp : edges)
			{
				if(eTemp.getDest() == node.getKey())
				{
					removeEdge(nTemp.getKey(), node.getKey());
				}
				if(eTemp.getSrc() == node.getKey())
				{
					removeEdge(eTemp.getSrc(),  eTemp.getDest());
				}
			}
		}

		this._edges.remove(node.getKey());
		this._nodes.remove(node.getKey()); // remove it from nodes list
		mc++;
		this.numOfNodes--;

		return node;
	}

	@Override
	public edge_data removeEdge(int src, int dest)
	{
		try
		{
			if(src == dest) throw new Exception(); // CANNOT remove node from itself.
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
			System.out.println("DWGraph_DS -> removeEdge: at least one of the nodes not in graph, or src == dest.");
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
		return this.mc;
	}
	public void copy(directed_weighted_graph g)
	//copies graph and connections
	{
		// if graph is not not and not empty
		if(g.edgeSize()!=0&&g!=null)
		{
			//gets a colllection of all nodes in graph
			Collection<node_data> temp=g.getV();
			Iterator<node_data> iterator = temp.iterator();

			while(iterator.hasNext()) 
			{
				node_data temp2= iterator.next();
				//create a new node with the same key and all traits
				node_data newnode=new NodeData(temp2);
				// add the node to the node hashmap if only its key doesnt exist
				if(!_nodes.containsKey(newnode.getKey()))
				{
					_nodes.put(newnode.getKey(), newnode);
				}
				// config neighbor nodes iterator and also hashmap for edges for the same father node
				Iterator<node_data> neighbors_nodes = ((NodeData)temp2).getNeis().values().iterator();
				HashMap<Integer, edge_data> neighbors_edges = _edges.get(((NodeData)temp2).getKey());

				while(neighbors_nodes.hasNext())
				{
					node_data temp3= neighbors_nodes.next();
					node_data newnode2=new NodeData(temp3);
					//goes through all neighboring nodes of the father node and adds to graph as
					//key doesnt exist in the graph
					if(!_nodes.containsKey(newnode2.getKey()))
					{
						_nodes.put(newnode2.getKey(), newnode2);
					}
					// connects between both nodes with a edge ( connect checks if both nodes are neighbors
					// or not and acts accordingly
					connect(newnode2.getKey(),newnode.getKey(),neighbors_edges.get(temp3.getKey()).getWeight());
				}	
			}
           //update traits of graph
			numOfNodes=g.nodeSize();
			numOfEdges=g.edgeSize();
			mc=g.getMC();
		}
		// if graph not null and empty it clears everything in all hashmaps and updates traits
		else if(g!=null)
		{
		_nodes.clear();
		_edges.clear();
		numOfNodes=g.nodeSize();
		numOfEdges=g.edgeSize();
		mc=g.getMC();
		}

	}
}
