
package envandalgo;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;

public class BiBFS {
    
           static int dim;
           static int[][] map;
           
           public BiBFS(int dim, int[][] map){
               this.dim = dim;
               this.map = map;
           }
	//set dim and map from input
           
	   //row and column locations of neighboring cells (up down left right)
	   static int rowNum1[] = {-1, 0, 0, 1}; 
	   static int colNum1[] = {0, -1, 1, 0};  
	   static int rowNum2[] = {0, 1, 0, -1}; 
	   static int colNum2[] = {1, 0, -1, 0}; 
	
	//same as BFS, but this time, we need two sets because we will use each to iterate through for each src (0,0) and (dim-1, dim-1)
           
	   static coordinate intersect; //let there be an intersect node, once we reach this, stop algo because we can connect the two paths
	   static int pathsize=0; //to hold path size total 
	  
       public static void printfin(coordinate visit1[][], coordinate visit2[][]) {
    	  //represent one path to be 3s and the other to be 4s, but for now, set both srcs to three
    	    map[dim-1][dim-1]=3;
    	    map[0][0]=3;
    	    int tempx = intersect.x; 
    	    int tempy = intersect.y;
    	    map[tempx][tempy] = 3; //will be updated later
            
    	    while(!(tempx==0 && tempy==0)) { //iterate one path until we hit the src1
                coordinate hold = visit1[tempx][tempy];
		//take current node and mark as visited in path 1 (src1 -- from 0,0 to intersect)
                tempx = hold.x;
                tempy=hold.y;
		//set these to 3, as visited, and increase pathSize
                pathsize++;

                map[tempx][tempy]=3;

                tempx = hold.x;
                tempy=hold.y;

                map[tempx][tempy]=3;
    	    }
    	    //do the same for second path
    	    tempx = intersect.x;
    	    tempy = intersect.y;
    	    map[tempx][tempy] = 3; 
    	    int val=dim-1;
            
	    //this is for the other path from the second src
    	    while(!(tempx==val && tempy==val)) {
	    	    coordinate hold = visit2[tempx][tempy];
	    	    tempx = hold.x;
	    	    tempy=hold.y;
	    	    pathsize++;
	    	    
	    	    map[tempx][tempy]=3;
	    	    
	    	    tempx = hold.x;
	    	    tempy=hold.y;
	    	   
	    	    map[tempx][tempy]=4;
    	    }
    
       }
	   public static boolean compareFringe(coordinate q, Queue<QueueNode> queue) {
		   // takes queuenode q1, extracts q1.point, and checks if any of the queueNodes
		   // in queue have queueNode.point = q1.point. Returns true if yes
		   //basically compairing the coord portion of a node in a given queue
		   for(QueueNode node: queue) {
			   //System.out.println("We are comparing q: "+q.toString()+" to node in list: "+node.point.toString());
			   if(q.equals(node.point)) {
				   	//System.out.println("In the fringe. Found intersect");
		    		return true; //returns true is in found, this is the intersect
		    	}
		   }
	    	return false; //not in fringe
	    }
	   
	    //-----------------------------------------Search Algorithms------------------------------------------------
	    
	    public static boolean cellValid(int r, int c){ //checks to see if current coordinate is valid and within the boundaries of the matrix
	        return (r<dim && r>=0 && c<dim && c>=0);
	    }
	    
	    public static int runBFS(int[][]map, coordinate src1, coordinate src2){ //takes in parameters of: generated map and our source cell in the matrix
	      
                boolean visited[][] = new boolean[dim][dim]; //visited boolean 2d array that is the same size as 2d map
                coordinate visit1[][]=new coordinate[dim][dim]; // this is the one used to hold previous location
                coordinate visit2[][]=new coordinate[dim][dim]; // this is the one used to hold previous location
	     
	        visit1[0][0]=new coordinate(0,0); //same as BFS
                
	        visit2[dim-1][dim-1] = new coordinate(dim-1,dim-1);
	        //marked both the 0,0 and dim-1, dim-1 srcs as visited
	        
	        Queue<QueueNode> queue1 = new LinkedList<>(); //queue for src1
	        Queue<QueueNode> queue2 = new LinkedList<>(); //queue for src2
	        
	        QueueNode sn1 = new QueueNode(src1, 0, src1); //src node 1 queue node
	        QueueNode sn2 = new QueueNode(src2, 0, src2);
	       
	        ArrayList<coordinate> pathHold1 = new ArrayList<>(); //for first path
	        ArrayList<coordinate> pathHold2 = new ArrayList<>(); //for second path 
	        
	        visited[src1.x][src1.y] = true; //mark the start node as visited inside of the visited boolean 2d array for first src
	        visited[src2.x][src2.y] = true; //mark the start node as visited inside of the visited boolean 2d array for second src
	        
	        System.out.println();
	        
	        queue1.add(sn1);
	        queue2.add(sn2);
	        //added the start nodes
	        
	        QueueNode current1 = null;
	        QueueNode current2 = null;
	        //to be used later to hold current node being looked at
	        coordinate c1 = null;
	        coordinate c2 = null;
		//coordinates to be examined
	        
	        while(!queue1.isEmpty()&&!queue2.isEmpty()){
	        //keep iterating so long as queues have items -- but if one becomes empty, check at each iteration
			//to avoid null pointer so we are not dequeuing from empty queue
                int maxFringe = 0;
		//use this to check max fringe at each time we add to either queue
                if(queue1.size()>maxFringe || queue2.size()>0) {
               	 if(queue1.size()>maxFringe) {
               		 maxFringe=queue1.size();
               	 }else {
               		 maxFringe=queue2.size();
               	 }
                }
	        
	        	if(!queue1.isEmpty()) { 
				//while first queue is not empty, pop
	        		 current1 = queue1.peek(); // this is n
	                 c1 = current1.point;
	        		 visited[c1.x][c1.y]=true; //mark this node as visited, will check neighbors later
	                 boolean inFringe = false;
	                 inFringe = compareFringe(c1, queue2); //see if intersect or not
	                              
	                 
	                 // compare current point to the queue2 fringe. If not in fringe
	                 if(inFringe) {
	                	 //we have found the duplicate 
	                	 System.out.println("duplicate");
	                	 intersect = c1;
		                 System.out.println("Intersect is :"+c1.x+","+c1.y);
		                 visit1[c1.x][c1.y]=current1.prev; //set prev node or where we came from
		                 
		                 System.out.println("We have reached our goal!");
		          
		                 if(current2 != null) {
					 //so long as paths have values, add length and return
		                 return current1.pathTotal+current2.pathTotal;
		                 }else {
					 //else return only one path 
		                	 return current1.pathTotal;
		                 }
	                 }
				//this is getting the neighbors, same as BFS
	              
	                     int row1 = 0;
	                     int col1 = 0;
	                     
	                     for (int i = 0; i < 4; i++) { 
	                         row1= c1.x + rowNum1[i]; 
	                         col1 = c1.y + colNum1[i]; 
	                         //grab neighbor of current
	                       if (cellValid(row1, col1) && map[row1][col1] == 1 && !visited[row1][col1]){ 
	                    	   
	                           // mark cell as visited and enqueue it 
	                           visited[row1][col1] = true; //we visited the node so mark it
	                           visit1[row1][col1] = new coordinate(c1.x, c1.y); //set prev as current coord

	                           QueueNode Adjcell = new QueueNode(new coordinate(row1, col1), current1.pathTotal + 1, new coordinate(row1-rowNum1[i], col1-colNum1[i])); 
	                           queue1.add(Adjcell); //make it a queueNode to add to q1 for first path
	                       }else if(cellValid(row1, col1) && map[row1][col1] == 1 && (visited[row1][col1]&&visit2[row1][col1]!=null)){
	                    	  // System.out.println("already visited"+row1+","+col1); 
				       //check if already visited and valid (not off grid) and = 1 so we can take path -- 
				       //if so, this means we found intersect
				       //make sure node has been visited in other path to guarantee it is intersect
	                    	   intersect=new coordinate(row1,col1); //set intersect
	                    	   visit1[intersect.x][intersect.y]=new coordinate(c1.x, c1.y); //set where we came from
	                    	   //current1.pathTotal++;
	                    	   printfin(visit1, visit2); //used to print final path -- set map
                                   
                                   int nodesExplored1 = 0;
                                   int nodesExplored2 = 0;
                                    for(coordinate coor : pathHold1){
                                       nodesExplored1++;
                                    }
                                    for(coordinate coor : pathHold2){
                                       nodesExplored2++;
                                    }
				       
				       //get number of nodes expanded in each path, add together

                                   int fin = nodesExplored1+nodesExplored2; //this is final explored nodes in each path
                                   System.out.println("Nodes explored: " + fin);
                                
	                    	   return pathsize; //return total pathsize
	                    	   //break;
	                       }
	                     
	                     
	                 } 
	                     //once all of the neighbors have been visited, dequeue from queue 1 in path 1
	                       queue1.remove(); 
	                       pathHold1.add(c1); //add to explored
	                     
	            }
	        	//now to the same in path2 from (dim-1, dim-1) as src2
	        	if(!queue2.isEmpty()) {
	        		
	        		 current2 = queue2.peek(); // this is n
	                 c2 = current2.point;
	        		 visited[c2.x][c2.y]=true;
	                 boolean inFringe = false;
	                 inFringe = compareFringe(c2, queue1);
	                             
	                 // compare current point to the queue2 fringe. If not in fringe
	                 if(inFringe) {
	                	 //we have found the duplicate 
	                	 System.out.println("duplicate");
	                	 intersect = c2;
		                // System.out.println("Intersect is :"+c2.x+","+c2.y);
		                 visit2[c2.x][c2.y]=current2.prev;
		                 
		                 System.out.println("We have reached our goal!");
		                 
		         
		                 if(current1 != null) {
			                 return current1.pathTotal+current2.pathTotal;
			                 }else {
			                	 return current2.pathTotal;
			                 }
	                 }
	             
	                 	// get neighbors
	                     int row2 = 0;
	                     int col2 = 0;
	                     
	                     for (int i = 0; i < 4; i++) { 
	                         row2= c2.x + rowNum2[i]; 
	                         col2 = c2.y + colNum2[i]; 
	                         
	               
	                       if (cellValid(row2, col2) && map[row2][col2] == 1 && !visited[row2][col2]){ 
	                    	   
	                           // mark cell as visited and enqueue it 
	                           visited[row2][col2] = true; 
	                           visit2[row2][col2] = new coordinate(c2.x, c2.y);

	                           QueueNode Adjcell2 = new QueueNode(new coordinate(row2, col2), current2.pathTotal + 1, new coordinate(row2-rowNum2[i], col2-colNum2[i])); 
	                           queue2.add(Adjcell2);
	                       }else if(cellValid(row2, col2) && map[row2][col2] == 1 && (visited[row2][col2]&&visit1[row2][col2]!=null)){
	                    	   
	                    	   intersect=new coordinate(row2,col2);
	                    	   
	                    	   visit2[intersect.x][intersect.y]=new coordinate(c2.x, c2.y);
	        
	                      
	                           printfin(visit1, visit2);
	                            int nodesExplored1 = 0;
                                    int nodesExplored2 = 0;
                                    for(coordinate coor : pathHold1){
                                       nodesExplored1++;
                                   }
                                   for(coordinate coor : pathHold2){
                                       nodesExplored2++;
                                   }

                                   int fin = nodesExplored1+nodesExplored2;
                                   System.out.println("Nodes explored: " + fin);
	                    	   return pathsize;
	                    	  
	                       }
	                     
	                 } 
	                     //once all of the neighbors have been visited, dequeue 
	                       queue2.remove(); 
	                       pathHold2.add(c2);
	        		
	        	}

	      }
                
                  int nodesExplored1 = 0;
                  int nodesExplored2 = 0;
                  for(coordinate coor : pathHold1){
                     nodesExplored1++;
                 }
                 for(coordinate coor : pathHold2){
                     nodesExplored2++;
                 }

                 int fin = nodesExplored1+nodesExplored2;
                 System.out.println("Nodes explored: " + fin);
	        //if here, means no path was found
	        return -1;
	        
	    }
    
}
