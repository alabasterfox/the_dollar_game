package components;

import java.awt.Point;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.Random;
import java.util.Timer;
import java.util.TreeMap;
import models.AdjacencyMatrix;
import models.Edge;
import Tasks.KeepTimeTask;
import Tasks.PlayMusicTask;
import graphprototype.Constants;
import models.Node;

public class ViewModel {
    
    private boolean m_bKeepingTime = false;
    
    private ArrayList<Node> m_nodes = new ArrayList<Node>();
    private ArrayList<Edge> m_edges = new ArrayList<Edge>();
    private NodeFactory m_nodeFactory = new NodeFactory();
    private Random m_randomNumberGenerator = new Random();
    private AdjacencyMatrix m_adjacencyMatrix = new AdjacencyMatrix();
    private DJ dj = new DJ();
    private View m_view;
    private Timer m_timer;
    
    private int m_clockTime = 0;
    private int m_total_value_of_all_nodes = 0;
    private int m_number_of_edges = 0;
    private int m_numberOfStepsUserHasTaken = 0;

    public void CreateNewGameboard(){
        StopGameTimerIfRunning();
        ReinitializeAllGameStatistics();
        CreateNodes();
        WireUpNodesWithEdges();
    }
    
    public void StopGameTimerIfRunning(){
        if(m_timer != null){
            if(m_bKeepingTime){
                this.m_timer.cancel();
                this.m_bKeepingTime = false;
            }
        }
    }
    
    public boolean IsLevelSolved(){
        boolean bLevelIsSolved = false;
        
        // Level is solved if no nodes are negative
        for(Node node : this.m_nodes){
            bLevelIsSolved = node.GetValue() >= 0;
            if(!bLevelIsSolved){
                break;
            }
        }
        
        return bLevelIsSolved;
    }
    
    public void StartGameTimer(){
        if(!m_bKeepingTime){
            m_timer = new Timer("clock thread"); // https://stackoverflow.com/questions/21682031/i-cant-start-a-timer-2-times
            m_timer.scheduleAtFixedRate(new KeepTimeTask(this), new Date(), 1000);
            m_bKeepingTime = true;
        }
        
    }
    
    public void UpdateGameTime(){
        this.m_clockTime++;
        this.m_view.UpdateGameTime();
    }
    
    public int GetGameClockTime(){
        return this.m_clockTime;
    }
    
    private void ReinitializeAllGameStatistics(){

        // User dashboard
        m_numberOfStepsUserHasTaken = 0;
        m_clockTime = 0;
        
        m_nodes = new ArrayList<Node>();
        m_edges = new ArrayList<Edge>();
        m_nodeFactory = new NodeFactory();
        m_total_value_of_all_nodes = 0;
        m_number_of_edges = 0;
        m_adjacencyMatrix = new AdjacencyMatrix();
    }
    
    private void CreateNodes(){
        for(int i = 0; i < Constants.INITIAL_NUMBER_OF_NODES; i++){
            Node newNode = this.m_nodeFactory.CreateNode(m_nodes.size());
            this.m_total_value_of_all_nodes += newNode.GetValue();
            this.m_nodes.add(newNode);
        }
    }
    
    private void WireUpNodesWithEdges(){
        boolean bFoundConnection;
        
        Edge newEdge;
        HashSet<Point> set_of_possible_edges = new HashSet<Point>();
        ArrayList<Point> bare_minimum_points = new ArrayList<Point>();
        
        // Calculates all possible unique node-to-node connections
        int m = 1;
        for(int i = 1; i < Constants.INITIAL_NUMBER_OF_NODES; i++){
            for(int j = 0; j < m; j++){
                Point newPoint = new Point(i, j);
                set_of_possible_edges.add(newPoint);
                if(j == i - 1){
                    bare_minimum_points.add(newPoint);
                }
            }
            m++;
        }
        
        // Ensures all nodes are connected at a minimum
        for(Point node2nodeConnection : bare_minimum_points){
            newEdge = 
                new Edge(
                    this.m_nodes.get(node2nodeConnection.x), 
                    this.m_nodes.get(node2nodeConnection.y));
            m_adjacencyMatrix.AddEdge(newEdge);
            this.m_edges.add(newEdge);
                    
            // Removes that connection from our set of possible yet 
            // unrealized connections to prevent duplicating edges
            set_of_possible_edges.remove(node2nodeConnection);
                    
        }
        
        // Chooses and creates n random, unique connections to wire up the nodes
        for(int i = 0; i < Constants.INITIAL_NUMBER_OF_EDGES - bare_minimum_points.size(); i++){
            // Chooses a random node-to-node connection out of the set of 
            // possible connections
            int size = set_of_possible_edges.size();
            int desired_index = new Random().nextInt(size); // In real life, the Random object should be rather more shared than this
            
            // Resets our addressing index to zero since we're restarting the 
            // seek from the beginning
            int index = 0;
            
            // Seeks that node-to-node connection in the set of all possible
            // node-to-node connections
            for(Point node2nodeConnection : set_of_possible_edges)
            {
                bFoundConnection = desired_index == index;
                if (bFoundConnection){
                    // Actualizes node-to-node connection
                    newEdge = new Edge(
                            this.m_nodes.get(node2nodeConnection.x), 
                            this.m_nodes.get(node2nodeConnection.y));
                    m_adjacencyMatrix.AddEdge(newEdge);
                    this.m_edges.add(newEdge);
                    
                    // Removes that connection from our set of possible yet 
                    // unrealized connections to prevent duplicating edges
                    set_of_possible_edges.remove(node2nodeConnection);
                    
                    // Ends seeking
                    break;
                }
                // Keeps track of our connections' addressing
                index++;
            }
        }
    }
    
    public ViewModel(View view){
        CreateNewGameboard();
        m_view = view;
        //new Thread(new PlayMusicTask(this)).run();
    }
    
    public int GetStepUserHasTaken(){
        return m_numberOfStepsUserHasTaken;
    }
    
    public Node GetNodeClickedOnByUser(MouseEvent e){
        boolean bNodesFound = false;
        boolean bUserClickedOnNode = false;
        
        Node closestClickedNode = null;
        TreeMap<Double, Node> nodeNeighborsFound = new TreeMap<>();

        double distanceBetweenClickAndNode;
        float radiusOfNode;
        int mouseX = e.getX();
        int mouseY = e.getY();
        for(Node currentNode : this.GetNodes()){
            distanceBetweenClickAndNode = 
                        Math.sqrt(Math.pow((mouseX - currentNode.GetX()), 2) 
                                + Math.pow((mouseY - currentNode.GetY()), 2));  
            radiusOfNode = (float) currentNode.GetRadius();
            bUserClickedOnNode = distanceBetweenClickAndNode <= (radiusOfNode);
            if(bUserClickedOnNode){
                nodeNeighborsFound.put(distanceBetweenClickAndNode, currentNode);
            }
        }
        
        if(bNodesFound = nodeNeighborsFound.size() > 0){
            return nodeNeighborsFound.firstEntry().getValue();
        }
        
        return null;
    }

    public void DonateFrom(Node node){
        boolean bHasAdjacentNodes;
        
        ArrayList<Node> adjacentNodes = 
                this.m_adjacencyMatrix.GetAdjacentNodes(node);
        
        if(bHasAdjacentNodes = adjacentNodes.size() > 0){
            for(Node adjNode : adjacentNodes){
                node.SetValue(node.GetValue() - 1);
                adjNode.SetValue(adjNode.GetValue() + 1);
            }
            this.m_numberOfStepsUserHasTaken++;
        }
    }
    
    public void DonateTo(Node node){
        boolean bHasAdjacentNodes;
        
        ArrayList<Node> adjacentNodes = 
                this.m_adjacencyMatrix.GetAdjacentNodes(node);
        
        if(bHasAdjacentNodes = adjacentNodes.size() > 0){
            for(Node adjNode : adjacentNodes){
                node.SetValue(node.GetValue() + 1);
                adjNode.SetValue(adjNode.GetValue() - 1);
            }
            this.m_numberOfStepsUserHasTaken++;
        }
    }
    
    /**
     * @return the m_nodes
     */
    public ArrayList<Node> GetNodes() {
        return m_nodes;
    }

    /**
     * @return the m_edges
     */
    public ArrayList<Edge> GetEdges() {
        return m_edges;
    }
}
