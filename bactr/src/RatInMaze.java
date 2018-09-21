import java.util.Arrays;

public class RatInMaze {

	public int[][] solution;

	//initialize the solution matrix in constructor.
	public RatInMaze(int N) {
		solution = new int[N][N];
		for (int i = 0; i < N; i++) {
			for (int j = 0; j < N; j++) {
				solution[i][j] = 0;
			}
		}
	}

	public void solveMaze(Block[][] maze, int N) {
		if (findPath(maze, 2, 1, N, "down")) {
			print(solution, N);
		}
                if (findPath(maze, 2, 1, N, "up")) {
			print(solution, N);
                	}else{
			System.out.println("NO PATH FOUND");
		}
		
	}

	public boolean findPath(Block[][] maze, int x, int y, int N, String direction) {
		// check if maze[x][y] is feasible to move
		if(x==3 && y==2){//we have reached
			solution[x][y] = 1;
			return true;
		}
		if (isSafeToGo(maze, x, y, N)) {
			// move to maze[x][y]
			solution[x][y] = 1;			
			// now rat has four options, either go right OR go down or left or go up
			if(direction!="up" && findPath(maze, x+1, y, N, "down")){ //go down
				return true;
			}
			//else go down
			if(direction!="left" && findPath(maze, x, y+1, N,"right")){ //go right
				return true;
			}
			if(direction!="down" && findPath(maze, x-1, y, N, "up")){ //go up
				return true;
			}
			if(direction!="right" &&  findPath(maze, x, y-1, N, "left")){ //go left
				return true;
			}
			//if none of the options work out BACKTRACK undo the move
			solution[x][y] = 0;
			return false;
		}
		return false;
	}

	// this function will check if mouse can move to this cell
	public boolean isSafeToGo(Block[][] maze, int x, int y, int N) {
		// check if x and y are in limits and cell is not blocked
		if (x >= 0 && y >= 0 && x < N  && y < N && maze[x][y].get_height() != 0) {
			return true;
		}
		return false;
	}
	public void print(int [][] solution, int N){
		for (int i = 0; i < N; i++) {
			for (int j = 0; j < N; j++) {
				System.out.print(" " + solution[i][j]);
			}
			System.out.println();
		}
	}
	public static void main(String[] args) {
		int N = 5;
		Block b00 = new Block(0, 1); Block b01 = new Block(0, 1); Block b02 = new Block(0, 1); Block b03 = new Block(0, 1); Block b04 = new Block(0, 0); Block b05 = new Block(0, 1); Block b06 = new Block(0, 1); Block b07 = new Block(0, 0);
		Block b10 = new Block(0, 1); Block b11 = new Block(0, 0); Block b12 = new Block(0, 0); Block b13 = new Block(0, 1); Block b14 = new Block(0, 0); Block b15 = new Block(0, 1); Block b16 = new Block(0, 1); Block b17 = new Block(0, 0);
		Block b20 = new Block(0, 1); Block b21 = new Block(0, 1); Block b22 = new Block(0, 0); Block b23 = new Block(0, 1); Block b24 = new Block(0, 0); Block b25 = new Block(0, 1); Block b26 = new Block(0, 1); Block b27 = new Block(0, 0);
		Block b30 = new Block(0, 0); Block b31 = new Block(0, 0); Block b32 = new Block(0, 1); Block b33 = new Block(5, 1); Block b34 = new Block(0, 0); Block b35 = new Block(0, 1); Block b36 = new Block(0, 1); Block b37 = new Block(0, 0);
		Block b40 = new Block(0, 0); Block b41 = new Block(0, 0); Block b42 = new Block(0, 0); Block b43 = new Block(0, 0); Block b44 = new Block(0, 0); Block b45 = new Block(0, 1); Block b46 = new Block(0, 1); Block b47 = new Block(0, 0);
		Block b50 = new Block(0, 1); Block b51 = new Block(0, 1); Block b52 = new Block(0, 1); Block b53 = new Block(0, 0); Block b54 = new Block(0, 0); Block b55 = new Block(0, 1); Block b56 = new Block(0, 1); Block b57 = new Block(0, 1);
		Block b60 = new Block(0, 1); Block b61 = new Block(0, 1); Block b62 = new Block(0, 1); Block b63 = new Block(0, 1); Block b64 = new Block(0, 1); Block b65 = new Block(0, 1); Block b66 = new Block(0, 1); Block b67 = new Block(0, 1);
		Block b70 = new Block(0, 1); Block b71 = new Block(0, 1); Block b72 = new Block(0, 1); Block b73 = new Block(0, 1); Block b74 = new Block(0, 1); Block b75 = new Block(0, 1); Block b76 = new Block(0, 1); Block b77 = new Block(0, 1);



		int[][] maze = {{ 1, 1, 1, 1, 0 }, 
						{ 1, 0, 0, 1, 0 },
						{ 1, 1, 0, 1, 0 },
						{ 0, 0, 1, 1, 0 },
						{ 0, 0, 0, 0, 0 } };

		Block[][] maze2 = {{b00, b01, b02, b03, b04}
						  ,{b10, b11, b12, b13, b14}
						  ,{b20, b21, b22, b23, b24}
						  ,{b30, b31, b32, b33, b34}
						  ,{b40, b41, b42, b43, b44}};
		//ystem.out.println(Arrays.deepToString(maze2));

		/*for(int i = 0; i < maze2.length; i++) {
			for (int j = 0; j < maze2[0].length; j++) {
				System.out.print(maze2[i][j] + " ");
			}
			System.out.println();
		}*/

		RatInMaze r = new RatInMaze(N);
		r.solveMaze(maze2, N);
	}

}
