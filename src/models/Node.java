package models;

import graphprototype.Constants;
import java.awt.Color;
import java.awt.Point;

public class Node {
    private int m_ID = -1;
    private int m_value = -1;
    public Point m_location = new Point();
    private int m_radius = Constants.NODE_PHYSICAL_WIDTH / 2;
    
    public Color color = Constants.DEFAULT_NODE_COLOR;
    public Color borderColor = Constants.DEFAULT_NODE_BORDER_COLOR;
    public Color textColor = Color.BLACK;
    
    public Node(int id, Point location, int value){
        this.m_ID = id;
        this.m_location = location;
        this.m_value = value;
    }

    /**
     * @return the m_ID
     */
    public int ID() {
        return m_ID;
    }

    /**
     * @return the m_value
     */
    public int GetValue() {
        return m_value;
    }

    /**
     * @param m_value the m_value to set
     */
    public void SetValue(int m_value) {
        this.m_value = m_value;
    }

    /**
     * @return the m_x
     */
    public int GetX() {
        return this.m_location.x;
    }

    /**
     * @return the m_y
     */
    public int GetY() {
        return this.m_location.y;
    }

    /**
     * @param m_x the m_x to set
     */
    public void SetX(int m_x) {
        this.m_location.x = m_x;
    }

    /**
     * @param m_y the m_y to set
     */
    public void SetY(int m_y) {
        this.m_location.y = m_y;
    }

    /**
     * @return the m_radius
     */
    public int GetRadius() {
        return m_radius;
    }
    
    public Point Location(){
        return this.m_location;
    }
}
