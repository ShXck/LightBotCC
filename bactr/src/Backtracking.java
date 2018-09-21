import static java.lang.Math.abs;

public class Backtracking {
    public Backtracking(){}

    //public int[][] solution;

    /**
     * //initialize the solution matrix in constructor.
    public Backtracking(int N) {
        solution = new int[N][N];
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                solution[i][j] = 0;
            }
        }
    }**/

    public boolean solveMaze(Block[][] maze, int N, int i, int j) {
        if (findPath(maze, 1, 1, N, i, j, "down")) {
            return true;
        }else{
            System.out.println("NO PATH FOUND");
        }
    return false;
    }

    public boolean findPath(Block[][] maze, int x, int y, int N, int i, int j, String direction) {
        // check if maze[x][y] is feasible to move
        if(x==i && y==j){//we have reached

            return true;
        }else{
            if (isSafeToGo(maze, x, y, N, direction)) {
                // move to maze[x][y]
                // now rat has four options, either go right OR go down or left or go up
                if(direction!="up" && findPath(maze, x-1, y, N, i,  j, "down")){ //go down
                    return true;
                }
                //else go down
                if(direction!="left" && findPath(maze, x, y-1, N, i, j,"right")){ //go right
                    return true;
                }
                if(direction!="down" && findPath(maze, x+1, y, N, i, j,"up")){ //go up
                    return true;
                }
                if(direction!="right" &&  findPath(maze, x, y+1, N, i, j,"left")){ //go left
                    return true;
                }
                //if none of the options work out BACKTRACK undo the move

                return false;
            }
        }
        return false;
    }

    // this function will check if mouse can move to this cell
    public boolean isSafeToGo(Block[][] maze, int x, int y, int N, String direction) {
        // check if x and y are in limits and cell is not blocked
        if(!direction.equals("up")){
            if (x >= 0 && y >= 0 && x < N  && y < N && (maze[x][y].get_height()  - maze[x][y-1].get_height()) == 0 ||
                    abs(maze[x][y].get_height() - maze[x][y-1].get_height())==1) 
            {
                return true;
            }
            return false;
        } 
        if(!direction.equals("down")){
            if (x >= 0 && y >= 0 && x < N  && y < N && (maze[x][y].get_height()  - maze[x][y+1].get_height()) == 0 ||
                    abs(maze[x][y].get_height() - maze[x][y+1].get_height())==1) 
            {
                return true;
            }
            return false;
        }
        if(!direction.equals("left")){
            if (x >= 0 && y >= 0 && x < N  && y < N && (maze[x][y].get_height()  - maze[x-1][y].get_height()) == 0 ||
                    abs(maze[x][y].get_height() - maze[x-1][y].get_height())==1) 
            {
                return true;
            }
            return false;
        }
        if(!direction.equals("right")){
            if (x >= 0 && y >= 0 && x < N  && y < N && (maze[x][y].get_height()  - maze[x+1][y].get_height()) == 0 ||
                    abs(maze[x][y].get_height() - maze[x+1][y].get_height())==1) 
            {
                return true;
            }
            return false;
        }
         return false;       
    }
    /**public void print(int [][] solution, int N){
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                System.out.print(" " + solution[i][j]);
            }
            System.out.println();
        }
    }**/
    public static void main(String[] args) {
        Backtracking sol = new Backtracking();
        Block b00 = new Block(0, 1); Block b01 = new Block(0, 2); Block b02 = new Block(0, 1); Block b03 = new Block(0, 1); Block b04 = new Block(0, 1); Block b05 = new Block(0, 1); Block b06 = new Block(0, 1); Block b07 = new Block(0, 1);
        Block b10 = new Block(0, 3); Block b11 = new Block(0, 1); Block b12 = new Block(0, 1); Block b13 = new Block(0, 1); Block b14 = new Block(0, 1); Block b15 = new Block(0, 1); Block b16 = new Block(0, 1); Block b17 = new Block(0, 1);
        Block b20 = new Block(0, 1); Block b21 = new Block(0, 1); Block b22 = new Block(0, 1); Block b23 = new Block(0, 1); Block b24 = new Block(0, 1); Block b25 = new Block(0, 1); Block b26 = new Block(0, 1); Block b27 = new Block(0, 1);
        Block b30 = new Block(0, 1); Block b31 = new Block(0, 1); Block b32 = new Block(0, 2); Block b33 = new Block(5, 3); Block b34 = new Block(0, 1); Block b35 = new Block(0, 1); Block b36 = new Block(0, 1); Block b37 = new Block(0, 1);
        Block b40 = new Block(0, 1); Block b41 = new Block(0, 3); Block b42 = new Block(0, 1); Block b43 = new Block(0, 1); Block b44 = new Block(0, 1); Block b45 = new Block(0, 1); Block b46 = new Block(0, 1); Block b47 = new Block(0, 1);
        Block b50 = new Block(0, 1); Block b51 = new Block(0, 1); Block b52 = new Block(0, 1); Block b53 = new Block(0, 1); Block b54 = new Block(0, 1); Block b55 = new Block(0, 1); Block b56 = new Block(0, 1); Block b57 = new Block(0, 1);
        Block b60 = new Block(0, 1); Block b61 = new Block(0, 1); Block b62 = new Block(0, 1); Block b63 = new Block(0, 1); Block b64 = new Block(0, 1); Block b65 = new Block(0, 1); Block b66 = new Block(0, 1); Block b67 = new Block(0, 1);
        Block b70 = new Block(0, 1); Block b71 = new Block(0, 1); Block b72 = new Block(0, 1); Block b73 = new Block(0, 1); Block b74 = new Block(0, 1); Block b75 = new Block(0, 1); Block b76 = new Block(0, 1); Block b77 = new Block(0, 1);
        
        int N = 8;
        Block[][] maze = { { b00, b01, b02, b03, b04, b05, b06,b07 },
                           { b10, b11, b12, b13, b14, b15, b16,b17 },
                           { b20, b21, b22, b23, b24, b25, b26,b27 },
                           { b30, b31, b32, b33, b34, b35, b36,b37 },
                           { b40, b41, b42, b43, b44, b45, b46,b47 },
                           { b50, b51, b52, b53, b54, b55, b56,b57 },
                           { b60, b61, b62, b63, b64, b65, b66,b67 },
                           { b70, b71, b72, b73, b74, b75, b76,b77 }};
        System.out.print(sol.solveMaze(maze, N, 1, 2));
        }
    }
