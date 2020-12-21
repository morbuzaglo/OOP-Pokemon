![](https://i.ibb.co/x7TTXrN/pokemon-Header.jpg)

<h2>Pokémon Game</h2>
<p>Project as part of the object-oriented course JAVA language.</p>
<p>The above project deals with the construction of the data structure Directed and weighted graph -</p>
<p>Which will be a platform for the game &quot;Find the Pokémon&quot;.</p>
<p>The game is divided into two parts:</p>
<ol>
<li><p>The game platform</p>
</li>
<li><p>The game construction</p>
<p>&nbsp;</p>
<h2>Part 1. The game platform - Directed Weighted Graph</h2>
<p>&nbsp;</p>
</li>

</ol>
<p><strong>In this part you can find algorithms that deal with solving various problems:</strong></p>
<ol>
<li>Connectivity of graph.</li>
<li>The length of the shortest path between two nodes.</li>
<li>The list of nodes that are in the shortest path between two nodes.</li>

</ol>
<p>The classes of the part :</p>
<ol>
<li><strong>DWGraph_DS</strong> which implements the interface : <strong>directed_weighted_graph</strong></li>

</ol>
<figure><table>
<thead>
<tr><th>Data members:</th><th>Description</th></tr></thead>
<tbody><tr><td>vertices</td><td>representing by HashMap</td></tr><tr><td>adjacency</td><td>representing by HashMap</td></tr><tr><td>edges</td><td>representing by HashSet</td></tr><tr><td>e</td><td>The number of edges in the graph (int type)</td></tr><tr><td>mc</td><td>The number of operations performed in the graph (int type)</td></tr></tbody>
</table></figure>
<figure><table>
<thead>
<tr><th>Methods:</th><th>Description</th><th>Time complexity</th></tr></thead>
<tbody><tr><td>DWGraph_DS</td><td>constructor</td><td>O(1)</td></tr><tr><td>getNode</td><td>return an object of type node_data that associated with the initial key</td><td>O(1)</td></tr><tr><td>getEdge</td><td>return an object of type edge_data that associated with the initial keys</td><td>O(1)</td></tr><tr><td>hasNode</td><td>return an Boolean value about if the initial node are in the graph</td><td>O(1)</td></tr><tr><td>addNode</td><td>add a new node to the graph</td><td>O(1)</td></tr><tr><td>Connect</td><td>connect between two nodes in the graph with positive weight</td><td>O(1)</td></tr><tr><td>getV</td><td>return the collection of all nodes in the graph</td><td>O(1)</td></tr><tr><td>getV</td><td>return a collection of all the edges that associated with the initial key.</td><td>O(K)</td></tr><tr><td>removeNode</td><td>remove a node and all the edges that are linked to that node in the graph.</td><td>O(K)</td></tr><tr><td>removeEdge</td><td>remove the edge between two nodes in the graph.</td><td>O(1)</td></tr></tbody>
</table></figure>
<ol start='2' >
<li><strong>DWGraph_Algo</strong> which implements the interface : <strong>dw_graph_algorithms</strong></li>

</ol>
<figure><table>
<thead>
<tr><th>Data members</th><th>Description</th></tr></thead>
<tbody><tr><td>graph</td><td>an object (directed_weighted_graph) that represents a graph</td></tr><tr><td>paths</td><td>HashSet that contains all paths combinations</td></tr><tr><td>GRAY</td><td>Color object that use to mark spesific nodes</td></tr><tr><td>EPSILON</td><td>a constant that ensures the most effective ratio</td></tr></tbody>
</table></figure>
<figure><table>
<thead>
<tr><th>Methods:</th><th>Description</th><th>Time complexity</th></tr></thead>
<tbody><tr><td>init</td><td>initialize the graph</td><td>O(1)</td></tr><tr><td>getGraph</td><td>return a graph object</td><td>O(1)</td></tr><tr><td>Copy</td><td>return a deep copy of graph object</td><td><img src="https://camo.githubusercontent.com/9a8e653df4fb157f321f251832ccd619e1099ef1c24774ab8eb9cfdc53c085d7/68747470733a2f2f77696b696d656469612e6f72672f6170692f726573745f76312f6d656469612f6d6174682f72656e6465722f7376672f61376366333137666265333936356165333136346632386331663638353836393661646232336634" style="zoom:%;" /></td></tr><tr><td>isConnected</td><td>return true or false if the graph is a strongly connected. this method use Kosaraju&#39;s algorithm.</td><td><a href='https://camo.githubusercontent.com/9a8e653df4fb157f321f251832ccd619e1099ef1c24774ab8eb9cfdc53c085d7/68747470733a2f2f77696b696d656469612e6f72672f6170692f726573745f76312f6d656469612f6d6174682f72656e6465722f7376672f61376366333137666265333936356165333136346632386331663638353836393661646232336634'><img src="https://camo.githubusercontent.com/9a8e653df4fb157f321f251832ccd619e1099ef1c24774ab8eb9cfdc53c085d7/68747470733a2f2f77696b696d656469612e6f72672f6170692f726573745f76312f6d656469612f6d6174682f72656e6465722f7376672f61376366333137666265333936356165333136346632386331663638353836393661646232336634" referrerpolicy="no-referrer" alt="img"></a></td></tr><tr><td>graphTranspose</td><td>transpose directed wighted graph</td><td>O(V)</td></tr><tr><td>shortestPathDist</td><td>return the length (int) of the shortest path between two nodes this method use Dijkstra&#39;s algorithm.</td><td><a href='https://camo.githubusercontent.com/08a6b9eea3147d57cf08a7a8cb4f2c3255694d82081bcc6ba26dcedf8b6ce5d4/68747470733a2f2f77696b696d656469612e6f72672f6170692f726573745f76312f6d656469612f6d6174682f72656e6465722f7376672f65323231363262653835643036623334366633623766376161643937343664613063313031396339'><img src="https://camo.githubusercontent.com/08a6b9eea3147d57cf08a7a8cb4f2c3255694d82081bcc6ba26dcedf8b6ce5d4/68747470733a2f2f77696b696d656469612e6f72672f6170692f726573745f76312f6d656469612f6d6174682f72656e6465722f7376672f65323231363262653835643036623334366633623766376161643937343664613063313031396339" referrerpolicy="no-referrer" alt="img"></a></td></tr><tr><td>shortestPath</td><td>return a collection that contains all the nodes in the path between two nodes. this method us Dijkstra&#39;s algorithm.</td><td><a href='https://camo.githubusercontent.com/08a6b9eea3147d57cf08a7a8cb4f2c3255694d82081bcc6ba26dcedf8b6ce5d4/68747470733a2f2f77696b696d656469612e6f72672f6170692f726573745f76312f6d656469612f6d6174682f72656e6465722f7376672f65323231363262653835643036623334366633623766376161643937343664613063313031396339'><img src="https://camo.githubusercontent.com/08a6b9eea3147d57cf08a7a8cb4f2c3255694d82081bcc6ba26dcedf8b6ce5d4/68747470733a2f2f77696b696d656469612e6f72672f6170692f726573745f76312f6d656469612f6d6174682f72656e6465722f7376672f65323231363262653835643036623334366633623766376161643937343664613063313031396339" referrerpolicy="no-referrer" alt="img"></a></td></tr><tr><td>save</td><td>graph object serialization (to JSON file)</td><td>&nbsp;</td></tr><tr><td>load</td><td>graph object deserialization (from JSON file)</td><td>&nbsp;</td></tr><tr><td>Reset</td><td>return all the data members of the graph to defalut values</td><td>O(V)</td></tr></tbody>
</table></figure>
<p>	3. <strong>Nodes</strong> which implements the interface : <strong>node_data</strong></p>
<figure><table>
<thead>
<tr><th>Data members</th><th>Description</th></tr></thead>
<tbody><tr><td>pos</td><td>represent the location of the node (geo_location)</td></tr><tr><td>key</td><td>represent the key of each node (Integer)</td></tr><tr><td>tag</td><td>represent the color of each node (Color)</td></tr><tr><td>weight</td><td>represent the weight of each node (Double)</td></tr><tr><td>info</td><td>represent the info of each node (String)</td></tr></tbody>
</table></figure>
<figure><table>
<thead>
<tr><th>Methods:</th><th>Description</th><th>Time complexity</th></tr></thead>
<tbody><tr><td>Nodes</td><td>constructor</td><td>O(1)</td></tr><tr><td>Nodes</td><td>copy constructor</td><td>O(1)</td></tr><tr><td>getLocation and SetLocation</td><td>return or set the location(geo_Location) that assoiciated with the node</td><td>O(1)</td></tr><tr><td>getKey and setKey</td><td>return or set the key (int) that associated with the node</td><td>O(1)</td></tr><tr><td>getInfo and setInfo</td><td>return or set the info (String) that associated with the node</td><td>O(1)</td></tr><tr><td>getTag and setTag</td><td>return or set the color (Color) that associated with the node</td><td>O(1)</td></tr><tr><td>getWeight and setWeight</td><td>return or set the weight (Double) that assoiciated with the node</td><td>O(1)</td></tr></tbody>
</table></figure>
<p>	3.1  <strong>GeoLocation</strong> which implements the interface : <strong>geo_location</strong></p>
<figure><table>
<thead>
<tr><th>Data members</th><th>Description</th></tr></thead>
<tbody><tr><td>point</td><td>represent the location of the node (Point3D - (x,y,z) )</td></tr></tbody>
</table></figure>
<figure><table>
<thead>
<tr><th>Methods:</th><th>Description</th><th>Time complexity</th></tr></thead>
<tbody><tr><td>GeoLocation</td><td>constructor</td><td>O(1)</td></tr><tr><td>x</td><td>return the value of x cordinate</td><td>O(1)</td></tr><tr><td>y</td><td>return the value of y cordinate</td><td>O(1)</td></tr><tr><td>z</td><td>return the value of z cordinate</td><td>O(1)</td></tr></tbody>
</table></figure>
<p>	4. <strong>Edges</strong> which implements the interface : <strong>edge_data</strong></p>
<figure><table>
<thead>
<tr><th>Data members</th><th>Description</th></tr></thead>
<tbody><tr><td>src</td><td>represent the key of the source node (Integer)</td></tr><tr><td>dest</td><td>represent the key of the destination node (Integer)</td></tr><tr><td>w</td><td>represent the w eight of the edge (Double)</td></tr><tr><td>source</td><td>represent the source node of the edge (node_data)</td></tr><tr><td>destination</td><td>represent the destination node of the edge (node_data)</td></tr></tbody>
</table></figure>
<figure><table>
<thead>
<tr><th>Methods:</th><th>Description</th><th>Time complexity</th></tr></thead>
<tbody><tr><td>Edge</td><td>constructor</td><td>O(1)</td></tr><tr><td>Edge</td><td>copy constructor</td><td>O(1)</td></tr><tr><td>getSrc and getSource</td><td>return the value of the source node or the node itself.</td><td>O(1)</td></tr><tr><td>getDest and getDestination</td><td>return the value of the destination node or the node itself.</td><td>O(1)</td></tr><tr><td>getWeight and setWeight</td><td>return or set the weight (Double) that assoiciated with the edge</td><td>O(1)</td></tr></tbody>
</table></figure>
<p>&nbsp;</p>
<h2>Part 2. The game constructor </h2>
<a href="https://ibb.co/rQ2VZzd"><img src="https://i.ibb.co/Fh4FByH/S.png" alt="S" border="0"></a><p>&nbsp;</p>
<blockquote><p><strong>In this part you will find different classes and methods which can be used to build the game</strong> :</p>
</blockquote>
<p>&nbsp;</p>
<h3><strong>Game user interface - Using the classes JFRAME &amp; JPanel</strong> </h3>
<ol>
<li><strong>ourFrame</strong> which extends the class : <strong>JFrame</strong></li>

</ol>
<figure><table>
<thead>
<tr><th>Data members:</th><th>Description</th></tr></thead>
<tbody><tr><td>Arena</td><td>represent all the charecters that use in the game</td></tr><tr><td>ourPanel</td><td>inner frame that running every graphic object </td></tr></tbody>
</table></figure>
<figure><table>
<thead>
<tr><th>Methods:</th><th>Description</th></tr></thead>
<tbody><tr><td>ourFrame</td><td>constructor</td></tr><tr><td>initFrame</td><td>initialize the frame</td></tr><tr><td>getPanel</td><td>return ourPanel object</td></tr></tbody>
</table></figure>
<p>1.1 <strong>ourPanel</strong> which extends the class : <strong>JPanel</strong></p>
<figure><table>
<thead>
<tr><th>Data members:</th><th>Description</th></tr></thead>
<tbody><tr><td>Arena</td><td> object that contains the functions of the characters</td></tr><tr><td>Range2Range</td><td>object that orders the resolution of the objects</td></tr><tr><td>r</td><td>constant that arranges the proportions of the inner frame (Integer)</td></tr><tr><td>grade</td><td>represents the agent&#39;s score (Double)</td></tr><tr><td>moves</td><td>represents the agent&#39;s moves (Integer)</td></tr></tbody>
</table></figure>
<figure><table>
<thead>
<tr><th>Methods:</th><th>Description</th></tr></thead>
<tbody><tr><td>ourPanel</td><td>constructor</td></tr><tr><td>paint</td><td>paint in each run-step all graphics contexts</td></tr><tr><td>resize</td><td>resize the frame </td></tr><tr><td>drawPokemon</td><td>draws the pokemons and their location in the panel</td></tr><tr><td>drawAgents</td><td>draws the agents and their location in the panel</td></tr><tr><td>drawGraph</td><td>draws the graph and its nodes and edges in the panel</td></tr><tr><td>drawNode</td><td>drasws the nodes and their keys in the panel</td></tr><tr><td>drawEdge</td><td>draws the edges and their keys in the panel</td></tr><tr><td>drawArrow</td><td>draws an arrow between two given points</td></tr><tr><td>removeEdge</td><td>remove the edge between two nodes in the graph.</td></tr><tr><td>importPictures</td><td>method that imports images that will be used for the game</td></tr></tbody>
</table></figure>
<h3>Game Client  </h3>
<p>The classes diagram :</p>
<a href="https://ibb.co/ftJMqrn"><img src="https://i.ibb.co/k0ZK5q9/image.png" alt="image" border="0"></a><p><strong>Arena</strong> -  This is a class where there are different methods regarding characters in the game.</p>
<p>You will find in the above class the methods :</p>
<ol>
<li>setPokemons ans getPokemons</li>
<li>setAgents and getAgents </li>
<li>setGraph and getGraph</li>
<li>getGame and setGame</li>

</ol>
<p><strong>Agent</strong> - this is a class that represent the agent “Ash”</p>
<p>You will find in the above class the methods :</p>
<ol>
<li>update - method that update in run time the agents</li>
<li>getSrcNode </li>
<li>setPoints</li>
<li>setNextNode</li>
<li>setCurrNode</li>
<li>getLocation</li>

</ol>
<p><strong>Pokeon</strong> - this is a class that represent the pokemons :  “mewtwo” , “charizard” , “pikachu”</p>
<p>You will find in the above class the methods :</p>
<ol>
<li>update - method that update in run time the agents</li>
<li>getEdges  and setEdges</li>
<li>getLocation</li>
<li>getType</li>
<li>getValue</li>
<li>getDest</li>
<li>getSrc</li>

</ol>
<h3>Ex2  - class that represents the game strategy</h3>
<figure><table>
<thead>
<tr><th>Data members:</th><th>Description</th></tr></thead>
<tbody><tr><td>Frame</td><td>represent the frame of the game</td></tr><tr><td>ManageGame</td><td>represent all the charecters that use in the game (Arena object)</td></tr><tr><td>num_level</td><td>repersent the game level that taken from the server</td></tr><tr><td>graph</td><td>an object (directed_weighted_graph) that represents a graph</td></tr><tr><td>graph_algo</td><td>an object (directed_weighted_graph_algo) that represents a graph using algorithms</td></tr><tr><td>client</td><td>Thread Object</td></tr><tr><td>dt</td><td>the sleeping time for the thread (Integer)</td></tr><tr><td>attack</td><td>represent the target (pokemons) of each agent using HashMap</td></tr></tbody>
</table></figure>
<figure><table>
<thead>
<tr><th>Methods:</th><th>Description</th></tr></thead>
<tbody><tr><td>login</td><td>a login system</td></tr><tr><td>run</td><td>override the run method from the Thread class , this method tun the game and also move the agents in the graph</td></tr><tr><td>loadGraph</td><td>loads the graph from the server</td></tr><tr><td>init</td><td>init the game - first get the pokemons from the server and put the agent in a strategic location</td></tr><tr><td>moveAgents</td><td>moves the agents on the graph</td></tr><tr><td>nextNode</td><td>find the shortest path from agent to pokemon using the Dijkstra algorithm</td></tr><tr><td>ComparatorDist</td><td>comparator that use for PriorityQueue to arrange the Pokemon based on the shortest distance</td></tr></tbody>
</table></figure>
<p>&nbsp;</p>
<p>&nbsp;</p>
