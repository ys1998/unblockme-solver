package unblockmesolver;

import java.util.ArrayList;
import java.util.BitSet;
import unblockmesolver.Elements.*;

/*
    This class contains all the necessary functions for solving a given puzzle.
 */
public class Functions {

    public static ArrayList<Board> solve(final ArrayList<Board> parent_boards) {

        boolean puzzleSolved = false;
        int i = 0;
        for (; i < parent_boards.size(); i++) {
            // check for terminating condition
            if (canEscape(parent_boards.get(i))) {
                puzzleSolved = true;
                break;
            }
        }
        if (puzzleSolved) {
            ArrayList<Board> result = new ArrayList<>(0);
            result.add(parent_boards.get(i));
            return result;
        } else {
            ArrayList<Board> new_boards = new ArrayList<>(0);
            for (int index = 0; index < parent_boards.size(); index++) {
                Board parent_board = parent_boards.get(index);
                BoardMatrix binary_board = generateBoardMatrix(parent_board);

                for (int pos = 0; pos < parent_board.blocks.size(); pos++) {
                    Block block = parent_board.blocks.get(pos);
                    ArrayList<Block> newPositions = findAllNewPositions(block, binary_board);

                    for (Block new_block : newPositions) {
                        Board new_board = new Board(parent_board);
                        new_board.parent = index;
                        new_board.blocks.remove(pos);
                        new_board.blocks.add(pos, new_block);

                        // code to check for existence of board in new_boards before adding 
                        if (!isPresent(new_board, new_boards)) {
                            new_boards.add(new_board);
                        }
                    }
                }
            }
            ArrayList<Board> result = solve(new_boards);
            result.add(0, parent_boards.get(result.get(0).parent));
            return result;
        }
    }

    public static boolean isPresent(Board board, ArrayList<Board> boards) {
        for (Board b : boards) {
            if (board.blocks.size() == b.blocks.size()) {
                boolean blocksAreSame = true;
                for (int i = 0; i < board.blocks.size(); i++) {
                    if (!board.blocks.get(i).compare(b.blocks.get(i))) {
                        blocksAreSame = false;
                        break;
                    }
                }
                if (blocksAreSame) {
                    return true;
                }
            }
        }
        return false;
    }

    public static boolean canEscape(Board board) {
        // finding the red block
        Block red_block = new Block();
        int index;
        for (index = 0; index < board.blocks.size(); index++) {
            if (board.blocks.get(index).type == 'r') {
                red_block = new Block(board.blocks.get(index));
                break;
            }
        }
        // checking whether red block is detected
        if (index == board.blocks.size()) {
            System.err.println("No red block detected.\nExiting program.");
            System.exit(1);
            return false;
        } else {
            // checking if the detected red block can exit through the exit on the RIGHT 

            BitSet escape_path = generateBoardMatrix(board).row(red_block.y);

            boolean canEscape = true;
            for (int end_xpos = red_block.x + red_block.length; end_xpos < 6; end_xpos++) {
                if (escape_path.get(end_xpos) == true) {
                    canEscape = false;
                    break;
                }
            }
            return canEscape;
        }
    }

    public static BoardMatrix generateBoardMatrix(Board board) {
        BoardMatrix boardMatrix = new BoardMatrix();
        for (Block block : board.blocks) {
            switch (block.orientation) {
                case 'h':
                    for (int i = 0; i < block.length; i++) {
                        if (boardMatrix.isEmpty(block.x + i, block.y)) {
                            boardMatrix.occupy(block.x + i, block.y);
                        } else {
                            System.err.println("Overlapping blocks encountered.\nExiting program.");
                            System.exit(1);
                        }
                    }
                    break;
                case 'v':
                    for (int i = 0; i < block.length; i++) {
                        if (boardMatrix.isEmpty(block.x, block.y + i)) {
                            boardMatrix.occupy(block.x, block.y + i);
                        } else {
                            System.err.println("Overlapping blocks encountered.\nExiting program.");
                            System.exit(1);
                        }
                    }
                    break;
                default:
                    System.err.println("Invalid block-orientation encountered.");
                    System.exit(1);
            }
        }
        return boardMatrix;
    }

    public static ArrayList<Block> findAllNewPositions(Block block, BoardMatrix bm) {
        switch (block.orientation) {
            case 'h':
                return moveHorz(block, bm.row(block.y));
            case 'v':
                return moveVert(block, bm.column(block.x));
            default:
                System.err.println("Invalid block-orientation encountered.");
                System.exit(1);
                return null;
        }
    }

    public static ArrayList<Block> moveHorz(Block block, BitSet row) {
        ArrayList<Block> allHorzPositions = new ArrayList<>(0);

        // checking for possible backward (or leftward) movement
        for (int xpos = block.x - 1; xpos >= 0; xpos--) {
            if (row.get(xpos) == false) {
                Block new_pos = new Block(block.type, 'h', block.length, xpos, block.y);
                allHorzPositions.add(new_pos);
            } else {
                break;
            }
        }
        // checking for possible forward (or rightward) movement
        for (int end_xpos = block.x + block.length; end_xpos < 6; end_xpos++) {
            if (row.get(end_xpos) == false) {
                Block new_pos = new Block(block.type, 'h', block.length, end_xpos - block.length + 1, block.y);
                allHorzPositions.add(new_pos);
            } else {
                break;
            }
        }
        // returning all possible horizontally-new positions of the block 
        // can have ZERO entries also
        return allHorzPositions;
    }

    public static ArrayList<Block> moveVert(Block block, BitSet column) {
        ArrayList<Block> allVertPositions = new ArrayList<>(0);

        // checking for possible backward (or upward) movement
        for (int ypos = block.y - 1; ypos >= 0; ypos--) {
            if (column.get(ypos) == false) {
                Block new_pos = new Block(block.type, 'v', block.length, block.x, ypos);
                allVertPositions.add(new_pos);
            } else {
                break;
            }
        }
        // checking for possible forward (or downward) movement
        for (int end_ypos = block.y + block.length; end_ypos < 6; end_ypos++) {
            if (column.get(end_ypos) == false) {
                Block new_pos = new Block(block.type, 'v', block.length, block.x, end_ypos - block.length + 1);
                allVertPositions.add(new_pos);
            } else {
                break;
            }
        }
        // returning all possible vertically-new positions of the block 
        // can have ZERO entries also
        return allVertPositions;
    }

    public static void showResult(ArrayList<Board> result) {
        System.out.print("The minimum number of steps required to solve this puzzle are " + result.size());
    }

}
