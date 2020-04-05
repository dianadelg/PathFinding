package envandalgo;

import java.util.ArrayList;
import java.util.Stack;

public class DFS {
    
    int dim;
    //row and column locations of neighboring cells (up down left right)
    static int rowNum[] = {-1, 0, 0, 1}; 
    static int colNum[] = {0, -1, 1, 0};
//same as BFS

     public DFS(int dim){
         this.dim = dim;
     }  

     //-----------------------------------------Search Algorithms------------------------------------------------

     public boolean cellValid(int r, int c){ //checks to see if current coordinate is valid and within the boundaries of the matrix
         return (r<dim && r>=0 && c<dim && c>=0);
     }
    //checking if valid

     public int runDFS(int[][] map, coordinate src){ //takes in parameters of: generated map and our source cell in the matrix

         boolean visited[][] = new boolean[dim][dim]; //visited boolean 2d array that is the same size as 2d map
         coordinate visit[][]=new coordinate[dim][dim];
         visit[0][0]=new coordinate(0,0);
//basically, code is the same as BFS except implementing algo using a stack to do DFS - no longer expanding each node to neighbors
         coordinate goal = new coordinate(dim-1,dim-1); //our goal state
         Stack<QueueNode> stack = new Stack<>();
         QueueNode sn = new QueueNode(src, 0, new coordinate(0,0));
         ArrayList<coordinate> pathHold = new ArrayList<>(); 

         visited[src.x][src.y] = true; //mark the start node as visited inside of the visited boolean 2d array

         stack.push(sn); 

         while(!stack.isEmpty()){ 

             QueueNode current = stack.peek(); 
             coordinate c = current.point;
             //get a current node

             if(c.x == goal.x && c.y == goal.y){ //goal has been reached
                //check if end
                Stack<coordinate> stacky = new Stack<>();
                int diana=dim-1;
                int kyle = dim-1;
                coordinate end = new coordinate(dim-1, dim-1);
                stacky.push(end);
                for(int hey = 0; hey< current.pathTotal; hey++){
                    //if error, add + 1 to current.pathTotal
                    stacky.push(visit[diana][kyle]);
                    int tempx = visit[diana][kyle].x;
                    int tempy = visit[diana][kyle].y;
                    diana=tempx;
                    kyle=tempy;
                }

                //traverse backwards path
                 while(!stacky.empty()) {
                         coordinate hold = stacky.pop();
                         int tempx = hold.x;
                         int tempy = hold.y;
                         map[tempx][tempy]=4;	
                 }

                int nodesExplored = 0;
                for(coordinate coor : pathHold){
                    nodesExplored++;
                }
                
                System.out.println("Nodes explored: " + nodesExplored);
                 return current.pathTotal;


             }

             stack.pop(); 

             int row = 0;
             int col = 0;

             for (int i = 0; i < 4; i++) { 

                 row = c.x + rowNum[i]; 
                 col = c.y + colNum[i]; 

               if (cellValid(row, col) && map[row][col] == 1 && !visited[row][col]){ 

                   // mark cell as visited and enqueue it 
                   visited[row][col] = true; 
                   visit[row][col] = new coordinate(c.x, c.y);

                   QueueNode adjC = new QueueNode(new coordinate(row, col), current.pathTotal + 1, new coordinate(0,0)); 
                   stack.push(adjC);
               }

         }  
               pathHold.add(c);

     }
         System.out.println("Path has not been found");
         return -1;

         }
}
