package components;

import graphprototype.Constants;
import java.awt.Canvas;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.Shape;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.geom.Ellipse2D;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import models.Edge;
import models.Node;

public class View extends Canvas implements ActionListener, MouseListener, MouseMotionListener {
    
    private boolean m_bDragDropMode = false;
    boolean m_bUserClickedOnNode = false;
   
    private int m_mouseX;
    private int m_mouseY;
    private int high_threshold = (int)(Constants.MAXIMUM_NODE_VALUE * 0.6);
    
    private ViewModel m_viewModel;
    private Node m_nodeBeingDragged = null;
    private JLabel m_dashboard_label_control;
    private JLabel m_game_time_clock;
    private JLabel m_victory_notification_control;
    private JButton m_button_control;
 
    
    public View(){
        
        m_viewModel = new ViewModel(this);
        
        JFrame frame = new JFrame(Constants.GAME_WINDOW_TITLE);
         
        // Dashboard
        m_dashboard_label_control = new JLabel("Moves: 0");
        m_dashboard_label_control.setForeground(Color.WHITE);
        m_dashboard_label_control.setSize(100, 50);
        m_dashboard_label_control.setLocation((int)(Constants.GAME_FIELD_MAXIMUM_X * 0.1),0);
        m_dashboard_label_control.setBackground(Constants.GAME_FIELD_BACKGROUND_COLOR);
        m_dashboard_label_control.setOpaque(true);
        frame.add(m_dashboard_label_control);
              
        // Game time/clock
        m_game_time_clock = new JLabel("Time: 0");
        m_game_time_clock.setForeground(Color.WHITE);
        m_game_time_clock.setSize(100, 50);
        m_game_time_clock.setLocation((int)(Constants.GAME_FIELD_MAXIMUM_X * 0.8),0);
        m_game_time_clock.setBackground(Constants.GAME_FIELD_BACKGROUND_COLOR);
        m_game_time_clock.setOpaque(true);
        frame.add(m_game_time_clock);
                  
        // Victory notification
        m_victory_notification_control = new JLabel("Well Done!");
        m_victory_notification_control.setSize(100, 50);
        m_victory_notification_control.setLocation(Constants.GAME_FIELD_WIDTH / 2,Constants.GAME_FIELD_HEIGHT / 2);
        m_victory_notification_control.setVisible(false);
        m_victory_notification_control.setOpaque(false);
        frame.add(m_victory_notification_control);
              
        // Button
        m_button_control = new JButton(Constants.BUTTON_INITIAL_TEXT);
        m_button_control.setSize(Constants.GAME_FIELD_WIDTH, 50);
        m_button_control.setLocation(Constants.GAME_FIELD_MINIMUM_X, Constants.GAME_FIELD_MAXIMUM_Y - 50);
        m_button_control.addActionListener(this);
        frame.add(m_button_control);
        
        // Gameboard
        this.setSize(Constants.GAME_FIELD_WIDTH, Constants.GAME_FIELD_HEIGHT);
        this.setBackground(Constants.GAME_FIELD_BACKGROUND_COLOR);
        this.addMouseListener(this);
        this.addMouseMotionListener(this);
        frame.add(this);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setBackground(Constants.GAME_FIELD_BACKGROUND_COLOR);
        frame.pack();
        frame.setVisible(true);
    }
    
    public void UpdateGameTime(){
        m_game_time_clock.setText("Time: " + this.m_viewModel.GetGameClockTime());
        this.repaint();
    }

    
    public void paint(Graphics g) {
        
        Graphics2D g2d = (Graphics2D) g;
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
            RenderingHints.VALUE_ANTIALIAS_ON);
        
        // Draw edges
        for(Edge edge : this.m_viewModel.GetEdges()){
            DrawEdge(g, edge);
            
        }
        
        // Draw nodes
        for(Node node : this.m_viewModel.GetNodes()){
            DrawNode(g2d, node);
       
        }
    
    }
    
    public void DrawNode(Graphics2D g2d, Node node) {
        boolean bValueIsNegative;
        
        
        
        int x = node.GetX();
        int y = node.GetY();
        int r = node.GetRadius();
        
        x = x-(r/2);
        y = y-(r/2);
                      
        // Draw node color
        if(bValueIsNegative = node.GetValue() < 0){
            node.color = Color.RED;
            node.textColor = Color.WHITE;
        }
        else if(node.GetValue() >= high_threshold){
            node.color = Color.YELLOW;
            node.textColor = Color.BLACK;
        }
        else if(0 <= node.GetValue() && node.GetValue() < high_threshold){
            node.color = Color.GREEN;
            node.textColor = Color.BLACK;
        }
        else{
            node.color = Constants.DEFAULT_NODE_COLOR;
            node.textColor = Color.BLACK;
        }
        g2d.setColor(node.color);
        
        // Draw node shape
        //g2d.fillOval(x,y,r,r);
        Shape theCircle = new Ellipse2D.Double(x, y, r, r);
        g2d.fill(theCircle);
        
        // Draw node border
        g2d.setColor(node.borderColor);
        g2d.draw(theCircle);
        
        // Draw node's text
        g2d.setColor(node.textColor);

        g2d.drawString(
            String.format("%d", node.GetValue()),
            node.GetX() - 10, 
            node.GetY() + 5);
    }
    
    public void DrawEdge(Graphics g, Edge edge){
        Graphics2D g2 = (Graphics2D) g;
        
        g2.setStroke(edge.strokeSize);
        g.setColor(edge.color);
        
        g.drawLine(
            edge.m_node1.GetX(), 
            edge.m_node1.GetY(), 
            edge.m_node2.GetX(), 
            edge.m_node2.GetY());
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        // on button click
        this.m_viewModel.StopGameTimerIfRunning();
        m_victory_notification_control.setVisible(false);
        this.m_viewModel.CreateNewGameboard();
        m_dashboard_label_control.setText("Moves: " + this.m_viewModel.GetStepUserHasTaken());
        m_game_time_clock.setText("Time: " + this.m_viewModel.GetGameClockTime());
        this.m_viewModel.StartGameTimer();
        this.repaint(); 
    }
    
    

    @Override
    public void mouseClicked(MouseEvent e) {
        
        boolean bIsLevelSolved = false;
        m_bUserClickedOnNode = false;
        
        Node node = null;
        
        // Resets all colors
        for(Node singleNode : this.m_viewModel.GetNodes()){
            singleNode.color = Constants.DEFAULT_NODE_COLOR;
        }
        
        // Determines if node was clicked
        node = this.m_viewModel.GetNodeClickedOnByUser(e);
        if(m_bUserClickedOnNode = null != node){
            
            this.m_viewModel.StartGameTimer();

            // Highlights newly-selected node
            node.color = Color.MAGENTA;
            
            if(MouseEvent.BUTTON1 == e.getButton()){
                this.m_viewModel.DonateFrom(node);
            }
            else if(MouseEvent.BUTTON3 == e.getButton()){
            this.m_viewModel.DonateTo(node);
            }
            
            m_dashboard_label_control.setText("Moves: " + this.m_viewModel.GetStepUserHasTaken());
            
            bIsLevelSolved = this.m_viewModel.IsLevelSolved();
            if(bIsLevelSolved){
                m_victory_notification_control.setVisible(true);
                this.m_viewModel.StopGameTimerIfRunning();
            }
        }
        
        this.repaint();
    }

    @Override
    public void mousePressed(MouseEvent e) {
        Node node = this.m_viewModel.GetNodeClickedOnByUser(e);
        if(m_bUserClickedOnNode = null != node){
            m_bDragDropMode = true;
            m_nodeBeingDragged = node;
        }
      
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        m_bDragDropMode = false;
        m_nodeBeingDragged = null;
    }

    @Override
    public void mouseEntered(MouseEvent e) {
        
    }

    @Override
    public void mouseExited(MouseEvent e) {
        
    }

    @Override
    public void mouseDragged(MouseEvent e) {
        if(m_bDragDropMode){
            if(m_nodeBeingDragged != null){
                m_nodeBeingDragged.SetX(e.getX());
                m_nodeBeingDragged.SetY(e.getY());
                this.repaint();
            }
        }
    }

    @Override
    public void mouseMoved(MouseEvent e) {
        
    }
    
}
