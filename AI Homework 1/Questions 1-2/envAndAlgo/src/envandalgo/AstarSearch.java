package envandalgo;
import java.util.List;
import java.util.PriorityQueue;
import java.util.ArrayList;
import java.util.Comparator;

//manhattan
public class AstarSearch {

        static int[][] map;
        static int dim;
        static int nodesExpanded;
      
    
        public AstarSearch(int dim, int[][]map){
             this.dim = dim;
             this.map=map;
        }
    
       //keep an open and closed list to implement algorithm
       // private List<QueueNode> path = new ArrayList<>();
        private coordinate src = new coordinate(0,0);
        
        private List<QueueNode> closed = new ArrayList<>(); 
       // private QueueNode temp=null;
        private QueueNode current = new QueueNode(null, src, 0, heuristic(src));  //g , h
	//set current node
	//prev is null, src is current node, 0 is distance from src to src in this case, get heuristic
        QueueNode neighbor; //to be used later to represent neighbor
        int pathHold=0;
        
        //use a queueNode for open -- goal is to put node with lowest total f score (distance + heuristic) as the node we want to explore next
        private PriorityQueue<QueueNode> open = new PriorityQueue<QueueNode>(new Comparator<QueueNode>() {
		//must override compare to make sure add each add of priority queue, min is always the one with lowest f
        	public int compare(QueueNode q1, QueueNode q2){
                  int hold =  Double.compare((q1.f),(q2.f));
                  if(hold == 0) {
                  	int again= Double.compare(q1.g, q2.g);
                  	if(again==1) {
                  		//System.out.println("q2 has less");
                  	}else if(again==-1){
                  		//System.out.println("q1 has less");
                  	}
                  	if(again==0) {
                      	//System.out.println(q1.point.toString()+" and "+q2.point.toString()+" have the same h val");
                       	int here= Double.compare(q1.h, q2.h);
                      	if(here==1) {
                      		//System.out.println("q2 has less");
                      	}else if(here==-1){
                      		//System.out.println("q1 has less");
                      	}
                      	
                      	return here;
                  	}else {
                  		return again;
                  	}
                  }else {
                  	return hold;
                  }
              }
           });
        
        static int rowNum[] = { 0, 1, -1, 0}; 
        static int colNum[] = { 1, 0, 0, -1};  
    	//same as BFS

     
    	
        public boolean cellValid(int r, int c){ //checks to see if current coordinate is valid and within the boundaries of the matrix
            return (r<dim && r>=0 && c<dim && c>=0);
        }
        
        private double heuristic(coordinate n) {
            return Math.abs(n.x - (dim-1)) + Math.abs(n.y -(dim-1)); 
        } //to be used to get heuristic, or estimate of what the distance is from current node to goal node
        
        public double distance(coordinate n) {
        	return n.x+n.y;
        }
	//distance taken from 0,0 src to current node n, used to set distance field of each node
        
        public void setF(QueueNode n){
            n.f=n.g+n.h; //add heuristic and actual distance froom src to create total distance
        }
	//sets f by adding the two fields of distance and heuristic
        
   


   	public int findPathTo(int[][] map, coordinate src){ //takes in parameters of: generated map and our source cell in the matrix
   	
   		nodesExpanded=0; //to be used later 
   		open.add(current); //add to open
   		while(!open.isEmpty()) {
   			//while open is not empty, we determine if we explore node or not by checking neighbors
   			current=open.poll(); //grab node with lowest f val or distance + heuristic value 
   			closed.add(current); //add it as "we explored this node, now check its neighbors"
   			
   			if(current.point.x == dim-1 && current.point.y == dim-1) {
   				//Goal
				//if this node is the goal ,we stop
   				//System.out.println("Goal reached");
   				QueueNode hold = current;
   				int count=0;
				//to iterate through prev nodes, use current node and backtrack through parents
				//this will give us a way to mark the path in the original map
   				while(hold!=null) {
   					count++;
					//count to be used to return path size
   					map[hold.point.x][hold.point.y]=4;
   					//System.out.println(hold.point.toString()+"   f: "+hold.f+"  g:"+hold.g+"  h:" + hold.h);
   					hold=hold.parent;
   				}
   				count--;
   				//exclude start node as a hop in path so count --
   				System.out.println("Nodes expanded: "+pathHold); ///2 for every decision of the 2 nodes we try to take
   				
   				return count; //total path size
   			}
   			
   		
   		//generate neighbors from path
   		
        int row = 0;
        int col = 0;
        
        for (int i = 0; i < 4; i++) { 
       
            row = current.point.x + rowNum[i]; 
            col = current.point.y + colNum[i]; 
          

          if (cellValid(row, col) && map[row][col] == 1){ 
        	  //just check if on grid and not 0 or a block
        	// System.out.println(row+","+col+" is a valid neighbor of "+current.point);
        	  coordinate c = new coordinate(row,col);
        	  
        	  boolean inClosed = false;
        	  boolean isHigherG = false;
        	  for(QueueNode cl : closed) {
			  //if node in closed, ignore it. we don't need to look again, and look at other neighbors
        		  if(cl.point.x == c.x && cl.point.y == c.y) { //must check coord, not entire node for this
        			  //System.out.println("Neighbor is in closed");
        			  inClosed = true;
        			  //continue;
        			  break;
        		  }
        	  }
        	  //the above wastes time -- can we fix?
        	  if(inClosed) {
        		 // System.out.println("in closed");
			  //this is continuing to the next neighbor
        		  continue;
        	  }
        	  
        	  double h_val = heuristic(c); //get new heuristic from current node, and distance from src, and total f
        	  double g_val = current.g+distance(c);
        	  QueueNode neighbor = new QueueNode(current, c, g_val , h_val);
              setF(neighbor);
                   	  
        	  
              
        	  
              for(QueueNode op : open) {
            	  //System.out.println("Neighbor is in open");
		      //check if node in open waiting to be explored 
            	  if(op.point.x == c.x && op.point.y == c.y && neighbor.g >= op.g) {
            		 // System.out.println("Neighbor.g > node in open with same pt.g");
			  //see if the neighbor has a higher g. if it does, we don't want to take it because it has a bigger path so far
            		  //and the node in open has higher priority, so it's worth exploring that path as f is smaller overall
			  //so less heuristic (path to end) and distance from src combined
			  isHigherG=true;
            			  break;
            			  //continue;
            	  }
              }
              
              if(isHigherG) {
		      //ignore if it's higher 
            	  continue;
              }
              
             // System.out.println("adding "+neighbor.point.toString()+" to open");
              open.add(neighbor); //else, add the node to open, as we will explore it later
              pathHold++;  //add to path size or what we took overall size
              
        	 
              
              
        	  
            
          }
          
          
          
          
          }
        
       
   	}
		//System.out.println("Nodes expanded: "+pathHold); ///2 for every decision of the 2 nodes we try to take
        return -1; //if here, empty open and destination not reached
}
   	
}
