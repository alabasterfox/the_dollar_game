package models;

import graphprototype.Constants;
import java.awt.BasicStroke;
import java.awt.Color;
import java.util.UUID;

public class Edge {
    private String m_ID = UUID.randomUUID().toString();
    public Node m_node1 = null;
    public Node m_node2 = null;
    public Color color = Constants.DEFUALT_EDGE_COLOR;
    public BasicStroke strokeSize = new BasicStroke(4);
    
    public Edge(Node node1, Node node2){
        m_node1 = node1;
        m_node2 = node2;
    }
}
