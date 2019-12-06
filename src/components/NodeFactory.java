package components;

import graphprototype.Constants;
import java.awt.Point;
import java.util.HashMap;
import java.util.Random;
import models.Node;

public class NodeFactory {
    private HashMap<Integer, Node> nodeDictionary = new HashMap<Integer, Node>();
    private Random RandomNumberGenerator = new Random();

    public Node CreateNode(int id) {
        boolean bThereIsACollision = false;
        boolean bOutOfBounds = false;
              
        double distanceBetween2nodes = -1;
        double radius = -1;
        int numberOfAdjustments = 0;
        
        Point potentialLocation = null;
        Node node = null;
  
        // Proposes a potential geographic location for the new node
        potentialLocation = new Point(this.GetRandomX(), this.GetRandomY());
        
        // Checks for any potential collisions with that new location by...
        do{

            // Checking against each existing node
            for(Node existingNode : this.nodeDictionary.values()){
                distanceBetween2nodes = 
                        existingNode.Location().distance(potentialLocation);
                radius = existingNode.GetRadius();
                bThereIsACollision = distanceBetween2nodes <= (2 * radius);
                
                if(numberOfAdjustments > 400){
                    System.out.println("Stopping early");
                    bThereIsACollision = false;
                    break;
                }
                
                // If there are issues...
                if(bThereIsACollision){ // bThereIsACollision
                     System.out.println("Would cause a collision. Adjusting");
                    
                    // Adjust the location slightly
                    potentialLocation.x++;
                    if(bOutOfBounds = 
                            potentialLocation.x >= Constants.SPAWN_AREA_MAXIMUM_X){
                        potentialLocation.x = Constants.SPAWN_AREA_MINIMUM_X;
                    }
                    potentialLocation.y--;
                    if(bOutOfBounds = 
                            potentialLocation.y <= Constants.SPAWN_AREA_MINIMUM_Y){
                        potentialLocation.y = Constants.SPAWN_AREA_MAXIMUM_Y;
                    }
                    
                    System.out.println("Adjusting to: (" + potentialLocation.x 
                            + ", " + potentialLocation.y + ")");
                    
                    numberOfAdjustments++;
                    
                    // Force a reverification
                    break;
                }
            }
        }while(bThereIsACollision); // bThereIsACollision
        
        
        // Create node
        node = new Node(id, potentialLocation, GetRandomValue());

        System.out.println("Created node with value of " + node.GetValue() 
                + " to (" + node.GetX() + ", " + node.GetY() + ")"); // logging

        nodeDictionary.put(id, node);
        
        return node;
    }
//       
//    public Node GetNode(int ID) {
//       boolean bNodeNotFound = false;
//
//       Node node = (Node)nodeDictionary.get(ID);
//
//       bNodeNotFound = node == null;
//       if(bNodeNotFound) {
//             System.out.println("Node not found.");
//             node = CreateNode(ID);
//       }
//       return node;
//    }
//    
    private int GetRandomValue(){
        //50 is the maximum and the 1 is our minimum 
        int n = RandomNumberGenerator.nextInt(Constants.MAXIMUM_NODE_VALUE 
                + 1 - Constants.MINIMUM_NODE_VALUE) 
                + Constants.MINIMUM_NODE_VALUE;
        return n;
    }
    
    private int GetRandomX(){
        //50 is the maximum and the 1 is our minimum 
        int n = RandomNumberGenerator.nextInt(Constants.SPAWN_AREA_MAXIMUM_X 
                + 1 - Constants.SPAWN_AREA_MINIMUM_X) 
                + Constants.SPAWN_AREA_MINIMUM_X;
//        System.out.println(Constants.SPAWN_AREA_MINIMUM_X + " <= x(" + n + ") <= " 
//                + Constants.SPAWN_AREA_MAXIMUM_X);
        return n;
    }
    
    private int GetRandomY(){
        //50 is the maximum and the 1 is our minimum 
        int n = RandomNumberGenerator.nextInt(Constants.SPAWN_AREA_MAXIMUM_Y 
                + 1 - Constants.SPAWN_AREA_MINIMUM_Y) 
                + Constants.SPAWN_AREA_MINIMUM_Y;
//        System.out.println(Constants.SPAWN_AREA_MINIMUM_Y + " <= y(" + n + ") <= " 
//                + Constants.SPAWN_AREA_MAXIMUM_Y);
        return n;
    }

}
