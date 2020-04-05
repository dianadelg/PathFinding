package envandalgo;
import java.util.Random;
import java.util.List;

public class mainDriver {
    
   static int dim = 30;
   
   //----------------------generate our map-------------------------------------------------------------------
    
    public static int[][] mapGen(int dim, double p){
        
       int[][] map = new int[dim][dim]; //initialize our dim x dim array
        
        for(int i = 0; i < dim; i++){ 
            for(int j = 0; j < dim; j++){
                if( new Random().nextDouble() <= p) { //random.nextDouble generates number between 0 and 1. Compares to probability p, and depending on if its less or not, we set current node to 0 or 1
                    map[i][j] = 0;
                }else{
                    map[i][j] = 1;
                }
            }
        }
        
        map[0][0] = 1; //represent our start state (for now)
        map[dim-1][dim-1] = 1; //represent our goal state (for now)
        
        //original map
         System.out.println("Original Map");
         for(int i = 0; i < dim; i++){ //BFS new map
           
            for(int j = 0; j < dim; j++){
                System.out.print("[" + map[i][j] + "]");
            }
            System.out.println();
        }
         
        System.out.println();
         
        return map;
    }
    
    //------------------------------------Main Driver---------------------------------------------------------
    
    public static void main(String[] args){
     
        BFS bfs = new BFS(dim);
        DFS dfs = new DFS(dim);
        coordinate src = new coordinate(0,0);
       
        int[][] mapBFS = mapGen(dim,0.2); //generate a map with given dim parameter and probability parameter
        int[][] mapDFS = new int[dim][dim];
        int[][] mapBiBFS = new int[dim][dim];
        int[][] mapA_star = new int[dim][dim];
        int[][] mapA_star2 = new int[dim][dim];
        
        for(int i = 0; i < dim; i++){
            for(int j = 0 ; j < dim; j++){
                mapDFS[i][j] = mapBFS[i][j];
            }
        }
        
        for(int i = 0; i < dim; i++){
          for(int j = 0 ; j < dim; j++){
              mapBiBFS[i][j] = mapBFS[i][j];
          }
        }
        BiBFS biBfs = new BiBFS(dim, mapBiBFS);
        
        for(int i = 0; i < dim; i++){
          for(int j = 0 ; j < dim; j++){
              mapA_star[i][j] = mapBFS[i][j];
          }
        }
        AstarSearch a_star = new AstarSearch(dim, mapA_star);
      
        for(int i = 0; i < dim; i++){
            for(int j = 0 ; j < dim; j++){
                mapA_star2[i][j] = mapBFS[i][j];
            }
          }
          Astar2Search a_star2 = new Astar2Search(dim, mapA_star2);
        
       System.out.println("BFS");
       long startTime = System.nanoTime();
       int shortestPathBFS = bfs.runBFS(mapBFS, src);
       long elapsedTime = System.nanoTime() - startTime;
       double seconds = (double) elapsedTime / 1000000000;
       System.out.println("Execution time: " + seconds + "s"); 
       System.out.println("Shortest path is: " + shortestPathBFS);
       System.out.println("Path taken by BFS");
       for(int i = 0; i < dim; i++){ //BFS new map
           
            for(int j = 0; j < dim; j++){
                System.out.print("[" + mapBFS[i][j] + "]");
            }
            System.out.println();
        }
       
       System.out.println();
       
       System.out.println("DFS");
       startTime = System.nanoTime();
       int shortestPathDFS = dfs.runDFS(mapDFS, src);
       elapsedTime = System.nanoTime() - startTime;
       seconds = (double) elapsedTime / 1000000000;
       System.out.println("Execution time: " + seconds + "s");
       System.out.println("Shortest path is: " + shortestPathDFS);
       System.out.println("Path taken by DFS");  
       for(int i = 0; i < dim; i++){ //DFS new map
           
            for(int j = 0; j < dim; j++){
                System.out.print("[" + mapDFS[i][j] + "]");
            }
            System.out.println();
        }
       
      System.out.println();
       
      System.out.print("Bi-Directional BFS");
      coordinate src2 = new coordinate(dim-1,dim-1);
      startTime = System.nanoTime();
      int shortestPathBiBFS = biBfs.runBFS(mapBiBFS, src, src2);
      elapsedTime = System.nanoTime() - startTime;
      seconds = (double) elapsedTime / 1000000000;
      System.out.println("Execution time: " + seconds + "s");
      System.out.println("Shortest path is: " + shortestPathBiBFS);
      System.out.println("Path taken by Bi-BFS");  
      for(int i = 0; i < dim; i++){ //DFS new map
           
            for(int j = 0; j < dim; j++){
                System.out.print("[" + mapBiBFS[i][j] + "]");
            }
            System.out.println();
        }
       
      System.out.println();
      
      //A* Manhattan
      
       
       System.out.println();
       
       System.out.println("A* Manhattan");
       Maze m = new Maze();
       m.maze = mapA_star;
       startTime = System.nanoTime();
       
       int shortestPathAstar = a_star.findPathTo(m.maze, src);
       elapsedTime = System.nanoTime() - startTime;
       seconds = (double) elapsedTime / 1000000000;
       System.out.println("Execution time: " + seconds + "s"); 
       System.out.println("Shortest path is: " + shortestPathAstar);
       System.out.println("Path taken by A* Manhattan");
       
       for(int i = 0; i < dim; i++){ //DFS new map
           
           for(int j = 0; j < dim; j++){
               System.out.print("[" + mapA_star[i][j] + "]");
           }
           System.out.println();
       }
       
       //A* Euclidean
       
System.out.println();
       
       System.out.println("A* Euclidean");
       Maze m2 = new Maze();
       m2.maze = mapA_star2;
       startTime = System.nanoTime();
       
       int shortestPathAstar2 = a_star2.findPathTo(m2.maze, src);
       elapsedTime = System.nanoTime() - startTime;
       seconds = (double) elapsedTime / 1000000000;
       System.out.println("Execution time: " + seconds + "s"); 
       System.out.println("Shortest path is: " + shortestPathAstar2);
       System.out.println("Path taken by A* Manhattan");
       
       for(int i = 0; i < dim; i++){ //DFS new map
           
           for(int j = 0; j < dim; j++){
               System.out.print("[" + mapA_star2[i][j] + "]");
           }
           System.out.println();
       }
       
       
       
    }
}

