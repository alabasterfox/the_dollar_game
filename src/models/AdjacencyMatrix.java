package models;

import graphprototype.Constants;
import java.util.ArrayList;

public class AdjacencyMatrix {
    
    int n;
    Edge[][] a;
    
    public AdjacencyMatrix(){
        Initialize();
    }
    
    public void Initialize(){
        n = Constants.INITIAL_NUMBER_OF_NODES;
        a = new Edge[n][n];
    }
    
    public void AddEdge(Edge edge) {
        a[edge.m_node1.ID()][edge.m_node2.ID()] = edge;
        a[edge.m_node2.ID()][edge.m_node1.ID()] = edge;
    }
    public void RemoveEdge(Edge edge) {
        a[edge.m_node1.ID()][edge.m_node2.ID()] = null;
        a[edge.m_node2.ID()][edge.m_node1.ID()] = null;
    }
    
    public ArrayList<Node> GetAdjacentNodes(Node node) {
        ArrayList<Node> adjacentNodes = new ArrayList<Node>();
        
        for(Edge edge : a[node.ID()]){
            if(edge != null){
                if(edge.m_node1.ID() == node.ID()){
                    adjacentNodes.add(edge.m_node2);
                }
                else{
                    adjacentNodes.add(edge.m_node1);
                }
            }
        }
        return adjacentNodes;
    }
    
}
