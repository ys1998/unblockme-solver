package unblockmesolver;

import java.util.Scanner;
import java.util.ArrayList;
import unblockmesolver.Elements.*;


/*
    This class deals with the linking of user-input, game-elements and puzzle-solving-algorithm.
 */
public class Main {
    
    public static void main(String[] args) {
        // Manual entry of data for now - to be automated later
        Scanner cin = new Scanner(System.in);
        ArrayList<Block> init_config = new ArrayList<>(0);
        System.out.println("\nPlease enter initial-block-configuration :\nPress <ENTER> after typing the answer to move on. ");
        String ans = "y";
        boolean red_block_detected = false;
        int index = 1;
        while (ans.equals("y")) {
            Block block = new Block();
            System.out.println("\n\nBLOCK " + index);
            
            if (!red_block_detected) {
                System.out.print("\nType [ red (r) or block (b) ] : ");
                block.type = cin.nextLine().charAt(0);
            } else {
                System.out.print("\nType : b");
                block.type = 'b';
            }
            if (block.type == 'r') {
                red_block_detected = true;
                block.orientation = 'h';
                block.length = 2;
                block.y = 2;
                System.out.println("Position of leftmost cell : ");
                System.out.print("x : ");
                block.x = Integer.parseInt(cin.nextLine());
                
            } else {
                System.out.print("\nOrientation (h/v) : ");
                block.orientation = cin.nextLine().charAt(0);
                System.out.print("\nLength (2 or 3) : ");
                block.length = Integer.parseInt(cin.nextLine());
                System.out.println("\nPosition of top-left corner :");
                System.out.print("x : ");
                block.x = Integer.parseInt(cin.nextLine());
                System.out.print("\ny : ");
                block.y = Integer.parseInt(cin.nextLine());
            }
            init_config.add(block);
            System.out.print("\n\nAny more blocks? (y/n) : ");
            ans = cin.nextLine();
            index++;
        }
        if (red_block_detected) {
            Board parent_board = new Board(init_config, 0);
            ArrayList<Board> initial_board = new ArrayList<>(0);
            initial_board.add(parent_board);
            ArrayList<Board> result = Functions.solve(initial_board);
            Functions.showResult(result);
            SolutionWindow window = new SolutionWindow(result);
        } else {
            System.err.println("Red block is necessary.\nExiting program.");
            System.exit(1);
        }
    }
    
}
