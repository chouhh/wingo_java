import java.util.HashMap;
import java.util.LinkedList;

public class Main
{
	static LinkedList<String> new_data;
	static HashMap<String,Integer> existed_data;
	static int[][] dir = {{0,-1},{1,0},{0,1},{-1,0}}; // up, right, down, left [offset_y][offset_x]

	public static void main(String[] args)
	{
		new_data = new LinkedList<String>();
		existed_data = new HashMap<String,Integer>();
		int[][] goal_board = {{1,2,3},{8,0,4},{7,6,5}}; // initial the goal board [y][x]
		int depth = 0;

		String code = encode(goal_board);
		new_data.add(code+" "+depth);
		existed_data.put(code,depth);

		while (new_data.size() != 0) // has further move
		{
			int[][] board = get_board(new_data.get(0));
			depth = get_depth(new_data.get(0));
			int y = get_blank_y(board);			
			int x = get_blank_x(board);

			for (int i=0; i<4; i++) // consider 4 direction moves
			{
				if (is_legal_move(y,x,i))
				{
					int[][] new_board = make_move(board,y,x,i);
					if (!existed(new_board))
					{
						code = encode(new_board);
						new_data.add(code + " " + (depth+1));
						existed_data.put(code,depth+1);
					}
				}
			}

			new_data.remove(0);
		}

		System.out.println("data size = " + existed_data.size());
		System.out.println("depth = " + depth);
		
		int[][] qb = {{8,6,7},{4,1,5},{3,2,0}};
		
		show_board(qb);
		String key = encode(qb);
		
		if (existed_data.containsKey(key))
		{
			System.out.println(existed_data.get(key));
			find_solution(qb);
		}
		else
		{
			System.out.println("Infinite");
		}
	}
	
	static String encode(int[][] b) // encode the board
	{
		String result = "";

		for (int i=0; i<3; i++)
		{
			for (int j=0; j<3; j++)
			{
				result = result + b[i][j];
			}
		}

		return result;
	}

	static int[][] get_board(String d)
	{
		int[][] result = new int[3][3];

		for (int i=2; i>=0; i--)
		{
			for (int j=2; j>=0; j--)
			{
				result[i][j] = d.charAt(3*i+j) - '0';
			}
		}
		
		return result;
	}

	static int get_depth(String d) // get depth of the board from data
	{
		String[] tokens = d.split(" ");
		
		return Integer.parseInt(tokens[1]);
	}

	static int[][] make_move(int[][] b, int y, int x, int d) // make move and return the result board
	{
		int[][] result = new int[3][3];
		
		for (int i=0; i<3; i++)
		{
			for (int j=0; j<3; j++)
			{
				result[i][j] = b[i][j];
			}
		}
		
		int nextY = y + dir[d][0];
		int nextX = x + dir[d][1];

		int temp = result[y][x];
		result[y][x] = result[nextY][nextX];
		result[nextY][nextX] = temp;
		
		return result;
	}

	static boolean is_legal_move(int y, int x, int d) // check whether the move on the direction is legal  or not
	{
		int nextY = y + dir[d][0];
		int nextX = x + dir[d][1];

		if (nextY >=0 && nextY <=2 && nextX >=0 && nextX <=2)
		{
			return true;
		}
		else
		{
			return false;
		}
	}

	static boolean existed(int[][] b)
	{
		String code = encode(b);

		return existed_data.containsKey(code);
	}

	static int get_blank_x(int[][] b)
	{
		for (int i=0; i<3; i++)
		{
			for (int j=0; j<3; j++)
			{
				if (b[i][j] == 0)
				{
					return j;
				}
			}
		}

		return -1;
	}

	static int get_blank_y(int[][] b)
	{
		for (int i=0; i<3; i++)
		{
			for (int j=0; j<3; j++)
			{
				if (b[i][j] == 0)
				{
					return i;
				}
			}
		}

		return -1;
	}

	static boolean is_legal_board(int[][] b)
	{
		int sum = 0;

		for (int i=0; i<3; i++)
		{
			for (int j=0; j<3; j++)
			{
				sum = sum + b[i][j];
			}
		}

		if (sum == 36)
		{
			return true;
		}
		else
		{
			return false;
		}
	}
	
	static void find_solution(int[][] b)
	{
		show_board(b);
		
		String key = encode(b);
		int depth = existed_data.get(key);
		
		if (depth == 0)
		{
			return;
		}
		else
		{
			int y = get_blank_y(b);			
			int x = get_blank_x(b);
			
			for (int i=0; i<4; i++) // consider 4 direction moves
			{
				if (is_legal_move(y,x,i))
				{
					int[][] new_board = make_move(b,y,x,i);
					String new_key = encode(new_board);
					int new_depth = existed_data.get(new_key);
					
					if (new_depth < depth)
					{
						find_solution(new_board);
						return;
					}
				}
			}
		}
	}

	static void show_board(int[][] b)
	{
		System.out.println("------------------");

		for (int i=0; i<3; i++)
		{
			for (int j=0; j<3; j++)
			{
				System.out.print(b[i][j] + " ");
			}

			System.out.println();
		}

		System.out.println("------------------");
	}
}
