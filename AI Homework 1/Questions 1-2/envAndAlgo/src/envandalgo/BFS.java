package envandalgo;
import java.util.*; 

public class BFS {
    
   int dim;
   //row and column locations of neighboring cells (up down left right)
   static int rowNum[] = {-1, 0, 0, 1}; 
   static int colNum[] = {0, -1, 1, 0};  
   // corresponding locations : move left once (-1) in rowNum, do not move vertically (0) in colNum
   // move down once (-1) in rowNum, (0) do not move horizontally 
   // do not move horizontally (0), move down once  (1)
   // move once to the right (1), do not move vertically
   // this corresponds to the immediate right grid square, left grid square, square above, and square immediately below current square
   
   //BFS constructor
   //set dimension to dim specified in driver
   public BFS(int dim){
        this.dim = dim;
   }  
   //check if cell valid is off grid (make sure it is within 0-dim both horizontally and vertically)
    public boolean cellValid(int r, int c){ //checks to see if current coordinate is valid and within the boundaries of the matrix
        return (r<dim && r>=0 && c<dim && c>=0);
    }
    
    public int runBFS(int[][] map, coordinate src){ //takes in parameters of: generated map and our source cell in the matrix
        //use a visited array array to indicate if a grid square has been visited or not (0 for no, 1 for yes)
        boolean visited[][] = new boolean[dim][dim]; //visited boolean 2d array that is the same size as 2d map
        coordinate visit[][]=new coordinate[dim][dim]; //used to track at a current square, where we came from previously
        visit[0][0]=new coordinate(0,0); //let the first coordinate 0,0 be visited by nothing (0,0 to indicate first node)
        
        coordinate goal = new coordinate(dim-1,dim-1); //our goal state, which is coord (dim-1, dim-1)
        Queue<QueueNode> queue = new LinkedList<>(); //use to grab neighbors from each square -- enqueue and dequeue FIFO
        QueueNode sn = new QueueNode(src, 0, new coordinate(0,0)); //let this be a node in the queue 
        //set src as the previous node or the node we came from
        ArrayList<coordinate> pathHold = new ArrayList<>();  //let this hold the coordinates in the path
        
        visited[src.x][src.y] = true; //mark the start node as visited inside of the visited boolean 2d array
        //mark that we just visited the source node
        queue.add(sn); 
        //add to queue src node
        
        while(!queue.isEmpty()){ //continue until queue is empty -- no more neighbors, goal is reached, or, path not found 
      
            QueueNode current = queue.peek(); //look at first node in list
            coordinate c = current.point; //hold its coordinate
           
            if(c.x == goal.x && c.y == goal.y){ //goal has been reached
                //check if coordinate we are examining is the goal
                //if yes, stop algo
               Stack<coordinate> stacky = new Stack<>(); // create stack to iterate through prev nodes
               int diana=dim-1; //use this as counter
               int kyle = dim-1; //use this as counter
               coordinate end = new coordinate(dim-1, dim-1); 
               stacky.push(end); //add end node first onto stack, so when we pop, this is the last node in the path
                //simulate LIFO to retrieve path by starting with goal node
               for(int hey = 0; hey< current.pathTotal; hey++){
            	   //same as DFS
                   //traverse through visit array and get each previous node, then bounce to previous square to grab that previous node
                   // until we reach end -- we tracked total path length so we can iterate this many times through it
            	   stacky.push(visit[diana][kyle]);
            	   int tempx = visit[diana][kyle].x;
            	   int tempy = visit[diana][kyle].y;
            	   diana=tempx;
            	   kyle=tempy;
               }
            	//once we have stack, pop off each one to retrieve original path
               	while(!stacky.empty()) {
               		//System.out.println(stacky.pop().toString());
               		coordinate hold = stacky.pop();
               		//System.out.println(hold.toString());
               		int tempx = hold.x;
               		int tempy = hold.y;
                    //grab the coordinate's xs and ys, and then grab original map and change each square in path to equal 4
                    //where 4 == part of the path in original map -- will be used to trace later
               		map[tempx][tempy]=4;
               		
               	}
            
                int nodesExplored = 0;
                for(coordinate coor : pathHold){
                    nodesExplored++;
                    //to grab the number of nodes explored, pathHold contains this
                }
                
                System.out.println("Nodes explored: " + nodesExplored); 
                return current.pathTotal; //this is the path total -- return shortest path
                
            }
            
            queue.remove(); //pop off queue
            
            int row = 0;
            int col = 0;
            //grab the square's 4 neighbors
            
            for (int i = 0; i < 4; i++) { 
           //set row and col of each neighbor -- 4 neighbors
                row = c.x + rowNum[i]; 
                col = c.y + colNum[i]; 
              

              if (cellValid(row, col) && map[row][col] == 1 && !visited[row][col]){ 
                //of the 4 neigbors, check if valid (not off grid) and make sure not already visited
                  // mark cell as visited and enqueue it 
                  //if in here, wasn't visited so mark true now
                  visited[row][col] = true; 
                  visit[row][col] = new coordinate(c.x, c.y); //we came to this neighbor from the current square 

                  QueueNode Adjcell = new QueueNode(new coordinate(row, col), current.pathTotal + 1, new coordinate(0,0)); 
                  //set adjacent cell as a QueueNode that holds prev, current path total and a new coordinate, to be set
                  queue.add(Adjcell);
                  //add to queue
              }
            
        } 
              pathHold.add(c); //add coordinate as a coord in path examined
            
    } 
     
        //if we reach here, the path was not found and the queue was empty so no more neighbors to explore
        //and goal was not reached
        System.out.println("Path has not been found");
        return -1;
        
        }
        
    }
   


