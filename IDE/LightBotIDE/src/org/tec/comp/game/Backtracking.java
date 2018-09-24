package org.tec.comp.game;

import org.tec.comp.game.Block;

import static java.lang.Math.abs;

public class Backtracking 
{

	public int[][] solution;

	
	public Backtracking(int N) 
	{
		solution = new int[N][N];
		for (int i = 0; i < N; i++) 
		{
			for (int j = 0; j < N; j++) 
			{
				solution[i][j] = 0;
			}
		}
	}

	public void solveMaze(Block[][] maze, int x, int y, int i, int j, int N)
	{
		if (findPath(maze, x, y, i, j, N, "down")) 
		{
			//print(solution, N);
		}
		
		if (findPath(maze, x, y, i, j, N, "up")) 
		{
			//print(solution, N);
		}
		
		else
		{
			System.out.println("NO PATH FOUND");
		}
		
	}

	public boolean findPath(Block[][] maze, int x, int y, int i, int j, int N, String direction) 
	{
		
		if(x==i && y==j)
		{//we have reached
			solution[x][y] = 1;
			return true;
		}
		if (isSafeToGo(maze, x, y, N, direction)) 
		{
			solution[x][y] = 1;		
			
			if(direction!="up" && findPath(maze, x+1, y, i, j, N, "down"))
			{
				return true;
			}
			
			else if(direction!="left" && findPath(maze, x, y+1, i, j, N, "right"))
			{
				return true;
			}
			
			else if(direction!="right" &&  findPath(maze, x, y-1, i, j, N, "left"))
			{
				return true;
			}
			
			else if(direction!="down" && findPath(maze, x-1, y, i, j, N, "up"))
			{
				return true;
			}
			
			solution[x][y] = 0;
			
			return false;
		}
		return false;
	}

	
	public boolean isSafeToGo(Block[][] maze, int x, int y, int N, String direction) 
	{
		if (direction.equals("up"))
		{
			if(x-1 < 0)
			{
				return false;
			}
			int a = abs(maze[x][y].get_height() - maze[x-1][y].get_height());
			System.out.println(a);
			if (x >= 0 && y >= 0 && x < N  && y < N &&  abs(maze[x][y].get_height() - maze[x-1][y].get_height())==0 || 
														abs(maze[x][y].get_height() - maze[x-1][y].get_height())==1) 
			{
				
				return true;
			}
			return false;
		}
		
		if (direction.equals("down"))
		{
			if(x+1 > N-1)
			{
				return false;
			}
			int b = abs(maze[x][y].get_height() - maze[x+1][y].get_height());
			System.out.println(b);
			if (x >= 0 && y >= 0 && x < N  && y < N &&  abs(maze[x][y].get_height() - maze[x+1][y].get_height())==0 || 
														abs(maze[x][y].get_height() - maze[x+1][y].get_height())==1) 
			{
				return true;
			}
			return false;
		}
		
		if (direction.equals("left"))
		{
			if(y-1 < 0)
			{
				return false;
			}
			int c = abs(maze[x][y].get_height() - maze[x][y-1].get_height());
			System.out.println(c);
			if (x >= 0 && y >= 0 && x < N  && y < N &&  abs(maze[x][y].get_height() - maze[x][y-1].get_height())==0 || 
														abs(maze[x][y].get_height() - maze[x][y-1].get_height())==1) 
			{
				return true;
			}
			return false;
		}
		
		if (direction.equals("right"))
		{
			if(y+1 > N-1)
			{
				return false;
			}
			int d = abs(maze[x][y].get_height() - maze[x][y+1].get_height());
			System.out.println(d);
			if (x >= 0 && y >= 0 && x < N  && y < N &&  abs(maze[x][y].get_height() - maze[x][y+1].get_height())==0 || 
														abs(maze[x][y].get_height() - maze[x][y+1].get_height())==1) 
			{
				return true;
			}
			return false;
		}
		
			if (x >= 0 && y >= 0 && x < N  && y < N && maze[x][y].get_height() != 0) 
			{
				return true;
			}
			return false;
	}
	
	
	
	public void print(int [][] solution, int N)
	{
		for (int i = 0; i < N; i++) 
		{
			for (int j = 0; j < N; j++) 
			{
				System.out.print(" " + solution[i][j]);
			}
			System.out.println();
		}
	}
	
	
	
	/*public static void main(String[] args) {
		
		
		Block b00 = new Block(0, 1); Block b01 = new Block(0, 2); Block b02 = new Block(0, 3); Block b03 = new Block(0, 1); Block b04 = new Block(0, 0); Block b05 = new Block(0, 0); Block b06 = new Block(0, 0); Block b07 = new Block(0, 0);
		Block b10 = new Block(0, 3); Block b11 = new Block(0, 0); Block b12 = new Block(0, 0); Block b13 = new Block(0, 1); Block b14 = new Block(0, 0); Block b15 = new Block(0, 0); Block b16 = new Block(0, 0); Block b17 = new Block(0, 0);
		Block b20 = new Block(0, 1); Block b21 = new Block(0, 1); Block b22 = new Block(0, 0); Block b23 = new Block(0, 1); Block b24 = new Block(0, 0); Block b25 = new Block(0, 0); Block b26 = new Block(0, 0); Block b27 = new Block(0, 0);
		Block b30 = new Block(0, 0); Block b31 = new Block(0, 0); Block b32 = new Block(0, 1); Block b33 = new Block(0, 1); Block b34 = new Block(0, 0); Block b35 = new Block(0, 0); Block b36 = new Block(0, 0); Block b37 = new Block(0, 0);
		Block b40 = new Block(0, 0); Block b41 = new Block(0, 0); Block b42 = new Block(0, 0); Block b43 = new Block(0, 0); Block b44 = new Block(0, 0); Block b45 = new Block(0, 0); Block b46 = new Block(0, 0); Block b47 = new Block(0, 0);
		Block b50 = new Block(0, 0); Block b51 = new Block(0, 0); Block b52 = new Block(0, 0); Block b53 = new Block(0, 0); Block b54 = new Block(0, 0); Block b55 = new Block(0, 0); Block b56 = new Block(0, 0); Block b57 = new Block(0, 0);
		Block b60 = new Block(0, 0); Block b61 = new Block(0, 0); Block b62 = new Block(0, 0); Block b63 = new Block(0, 0); Block b64 = new Block(0, 0); Block b65 = new Block(0, 0); Block b66 = new Block(0, 0); Block b67 = new Block(0, 0);
		Block b70 = new Block(0, 0); Block b71 = new Block(0, 0); Block b72 = new Block(0, 0); Block b73 = new Block(0, 0); Block b74 = new Block(0, 0); Block b75 = new Block(0, 0); Block b76 = new Block(0, 0); Block b77 = new Block(0, 0);

		int N = 8;
		int[][] maze = {{ 1, 1, 1, 1, 0 }, 
						{ 1, 0, 0, 1, 0 },
						{ 1, 1, 0, 1, 0 },
						{ 0, 0, 1, 1, 0 },
						{ 0, 0, 0, 0, 0 } };

		Block[][] maze2 = { { b00, b01, b02, b03, b04, b05, b06,b07 },
							{ b10, b11, b12, b13, b14, b15, b16,b17 },
							{ b20, b21, b22, b23, b24, b25, b26,b27 },
							{ b30, b31, b32, b33, b34, b35, b36,b37 },
							{ b40, b41, b42, b43, b44, b45, b46,b47 },
							{ b50, b51, b52, b53, b54, b55, b56,b57 },
							{ b60, b61, b62, b63, b64, b65, b66,b67 },
							{ b70, b71, b72, b73, b74, b75, b76,b77 } };


		Backtracking r = new Backtracking(N);
		r.solveMaze(maze2,0, 0, 0, 2, N);
	}*/

}
