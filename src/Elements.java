package unblockmesolver;

import java.util.ArrayList;
import java.util.BitSet;

/*
    This class contains the data-structures required to store the game-state and elements.
 */
public class Elements {

    public static class Block {

        char type;              // red [r] OR normal-block [b]
        char orientation;       // horizontal [h] OR vertical [v]
        int length;             // length of the block i.e. number of cells
        int x;                  // x-coordinate of top-left cell of the block
        int y;                  // y-coordinate of top-left cell of the block

        public Block() {        // default constructor
            type = 'b';
            orientation = 'h';
            length = 2;
            x = 0;
            y = 0;
        }

        public Block(Block b) {
            this.length = b.length;
            this.orientation = b.orientation;
            this.type = b.type;
            this.x = b.x;
            this.y = b.y;
        }

        public Block(char t, char o, int l, int xpos, int ypos) {        // standard parameterized constructor
            type = t;
            orientation = o;
            length = l;
            x = xpos;
            y = ypos;
        }

        public boolean compare(Block b) {
            return (this.length == b.length
                    && this.orientation == b.orientation
                    && this.type == b.type
                    && this.x == b.x
                    && this.y == b.y);
        }
    }

    public static class Board {

        ArrayList<Block> blocks;   // all the blocks constituting the board
        int parent;                // index of the parent board in the ParentVector from which this board was obtained

        public Board() {           // default constructor
            blocks = new ArrayList<>(0);
            parent = 0;
        }

        public Board(ArrayList<Block> b, int p) {       // standard parameterized constructor
            blocks = new ArrayList<>(b);
            parent = p;
        }

        public Board(Board b) {
            this.blocks = new ArrayList<>(b.blocks);
            this.parent = b.parent;
        }

        public Block getBlock(int index) {
            return blocks.get(index);
        }
    }

    public static class BoardMatrix {

        BitSet occupancy;          // stores the occupancy-state of each cell of a board : empty (0) OR filled (1)

        public BoardMatrix() {     // default constructor
            occupancy = new BitSet(6 * 6);
        }

        public BoardMatrix(BitSet o) {
            occupancy = (BitSet) o.clone();
        }

        public boolean isEmpty(int x, int y) {
            return !(occupancy.get(y * 6 + x));
        }

        public void occupy(int x, int y) {
            if (x < 6 && y < 6) {
                occupancy.set(y * 6 + x, true);
            } else {
                System.err.println("Block is out of bounds.\nExiting program.");
                System.exit(1);
            }
        }

        public BitSet row(int r) {
            return occupancy.get(r * 6 + 0, r * 6 + 6);
        }

        public BitSet column(int c) {
            BitSet col = new BitSet(6);
            for (int i = 0; i < 6; i++) {
                col.set(i, occupancy.get(i * 6 + c));
            }
            return col;
        }

    }

}
