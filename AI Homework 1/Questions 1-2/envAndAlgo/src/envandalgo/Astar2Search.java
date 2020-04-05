package envandalgo;
import java.util.List;
import java.util.PriorityQueue;
import java.util.ArrayList;
import java.util.Comparator;

//euclidean
public class Astar2Search {
//same as Astar but heuristic is the only thing calculated differently -- uses euclidean in this
        static int[][] map;
        static int dim;
        static int nodesExpanded;
      
    
        public Astar2Search(int dim, int[][]map){
             this.dim = dim;
             this.map=map;
        }
    
       
       // private List<QueueNode> path = new ArrayList<>();
        private coordinate src = new coordinate(0,0);
        
        private List<QueueNode> closed = new ArrayList<>(); 
       // private QueueNode temp=null;
        private QueueNode current = new QueueNode(null, src, 0, heuristic(src));  //g , h
        QueueNode neighbor;
        int pathHold=1;
        
        
        private PriorityQueue<QueueNode> open = new PriorityQueue<QueueNode>(new Comparator<QueueNode>() {
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
        
        static int rowNum[] = { -1, 0, 0, 1}; 
        static int colNum[] = { 0, -1, 1, 0};  
    	

     
    	
        public boolean cellValid(int r, int c){ //checks to see if current coordinate is valid and within the boundaries of the matrix
            return (r<dim && r>=0 && c<dim && c>=0);
        }
        
        private double heuristic(coordinate n) {
        	double tempVal1 = n.x-(dim-1);
        	double tempVal2 = n.x-(dim-1);
        	tempVal1 = tempVal1*tempVal1;
        	tempVal2 = tempVal2*tempVal2;
        	double total = tempVal1+tempVal2;
            return Math.sqrt(total); 
        }
        
        public double distance(coordinate n) {
        	return n.x+n.y;
        }
        
        public void setF(QueueNode n){
            n.f=n.g+n.h; //add heuristic and actual distance froom src to create total distance
        }
        
   


   	public int findPathTo(int[][] map, coordinate src){ //takes in parameters of: generated map and our source cell in the matrix
   	
   		nodesExpanded=0;
   		open.add(current);
   		pathHold++;
   		while(!open.isEmpty()) {
   			
   			current=open.poll();
   			closed.add(current);
   			
   			if(current.point.x == dim-1 && current.point.y == dim-1) {
   				//Goal
   				//System.out.println("Goal reached");
   				QueueNode hold = current;
   				int count=0;
   				while(hold!=null) {
   					count++;
   					map[hold.point.x][hold.point.y]=4;
   					//System.out.println(hold.point.toString()+"   f: "+hold.f+"  g:"+hold.g+"  h:" + hold.h);
   					hold=hold.parent;
   				}
   				count--;
   				 //must exclude final node
   				System.out.println("Nodes expanded: "+pathHold); ///2 for every decision of the 2 nodes we try to take
   				
   				return count;
   			}
   			
   		
   		//generate children
   		
        int row = 0;
        int col = 0;
        
        for (int i = 0; i < 4; i++) { 
       
            row = current.point.x + rowNum[i]; 
            col = current.point.y + colNum[i]; 
          

          if (cellValid(row, col) && map[row][col] == 1){ 
        	  
        	// System.out.println(row+","+col+" is a valid neighbor of "+current.point);
        	  coordinate c = new coordinate(row,col);
        	  
        	  boolean inClosed = false;
        	  boolean isHigherG = false;
        	  for(QueueNode cl : closed) {
        		  if(cl.point.x == c.x && cl.point.y == c.y) {
        			  //System.out.println("Neighbor is in closed");
        			  inClosed = true;
        			  //continue;
        			  break;
        		  }
        	  }
        	  
        	  if(inClosed) {
        		 // System.out.println("in closed");
        		  continue;
        	  }
        	  
        	  double h_val = heuristic(c);
        	  double g_val = current.g+distance(c);
        	  QueueNode neighbor = new QueueNode(current, c, g_val , h_val);
              setF(neighbor);
                   	  
        	  
              
        	  
              for(QueueNode op : open) {
            	  //System.out.println("Neighbor is in open");
            	  if(op.point.x == c.x && op.point.y == c.y && neighbor.g >= op.g) {
            		 // System.out.println("Neighbor.g > node in open with same pt.g");
            		      isHigherG=true;
            			  break;
            			  //continue;
            	  }
              }
              
              if(isHigherG) {
            	  continue;
              }
              
             // System.out.println("adding "+neighbor.point.toString()+" to open");
              open.add(neighbor);
              
              
              
        	 
              
              
        	  
            
          }
          
          
        
         
          }pathHold++;
        
       
   	}
		
   		//System.out.println("Nodes expanded: "+pathHold); ///2 for every decision of the 2 nodes we try to take
        return -1;
}
   	
}
