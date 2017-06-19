package unblockmesolver;

import java.awt.BorderLayout;
import java.awt.Color;
import javax.swing.*;
import java.awt.event.*;
import unblockmesolver.Elements.*;
import java.awt.Graphics;
import java.awt.GridLayout;
import java.util.ArrayList;

class InputWindow {

    JFrame mainFrame;
    JPanel panel1, panel2;
    JLabel label;
    JButton horz2, horz3, vert2, vert3, solve, red;
    Board initial_board;
    BoardMatrix initial_boardMatrix;
    InputBoardImage img;
    String button_pressed;

    public Board getBoard() {
        return initial_board;
    }

    class ButtonClickListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            button_pressed = e.getActionCommand();
            if (button_pressed.equals("solve")) {
                button_pressed = "none";
                mainFrame.dispose();
                ArrayList<Board> puzzle = new ArrayList<>(0);
                puzzle.add(getBoard());
                ArrayList<Board> result = Functions.solve(puzzle);
                SolutionWindow window = new SolutionWindow(result);

            } else if (((JButton) e.getSource()).isEnabled()) {
                label.setText("Block selected : \"" + button_pressed + "\"");
            }
        }
    }

    class CellSelectListener implements MouseListener {

        @Override
        public void mouseClicked(MouseEvent e) {
            if (e.getX() < 300 && e.getY() < 300 && SwingUtilities.isLeftMouseButton(e)) {
                if (!button_pressed.equals("none")) {
                    switch (button_pressed) {
                        case "horz2": {
                            Block temp = new Block('b', 'h', 2, (int) (e.getX() / 50), (int) (e.getY() / 50));
                            img.addBlock(temp);
                            break;
                        }
                        case "horz3": {
                            Block temp = new Block('b', 'h', 3, (int) (e.getX() / 50), (int) (e.getY() / 50));
                            img.addBlock(temp);
                            break;
                        }
                        case "vert2": {
                            Block temp = new Block('b', 'v', 2, (int) (e.getX() / 50), (int) (e.getY() / 50));
                            img.addBlock(temp);
                            break;
                        }

                        case "vert3": {
                            Block temp = new Block('b', 'v', 3, (int) (e.getX() / 50), (int) (e.getY() / 50));
                            img.addBlock(temp);
                            break;
                        }

                        case "red": {
                            if (red.isEnabled()) {
                                Block temp = new Block('r', 'h', 2, (int) (e.getX() / 50), (int) (e.getY() / 50));
                                img.addBlock(temp);
                                red.setEnabled(false);
                                break;
                            }
                        }

                        default:
                            break;
                    }
                }
            }
        }

        @Override
        public void mousePressed(MouseEvent e) {
        }

        @Override
        public void mouseReleased(MouseEvent e) {
        }

        @Override
        public void mouseEntered(MouseEvent e) {
        }

        @Override
        public void mouseExited(MouseEvent e) {
        }
    }

    class InputBoardImage extends BoardImage {

        public void addBlock(Block b) {
            board.blocks.add(b);
            initial_board.blocks.add(b);
            initial_boardMatrix = Functions.generateBoardMatrix(initial_board);
            button_pressed = "none";
            label.setText("Click on the block and then on its top-left cell in the grid");
            super.repaint();

        }

        @Override
        public void paint(Graphics g) {
            super.CELL_SIZE = 50;
            super.paint(g);
            // draw background grid here
            g.setColor(Color.white);
            for (int i = 0; i < 7; i++) {
                g.drawLine(0, i * 50, 300, i * 50);
                g.drawLine(i * 50, 0, i * 50, 300);
            }
        }
    }

    public InputWindow() {
        // initialization
        mainFrame = new JFrame("Submit the puzzle");
        panel1 = new JPanel();
        panel2 = new JPanel();
        horz2 = new JButton("Horizontal block of length 2");
        horz3 = new JButton("Horizontal block of length 3");
        vert2 = new JButton(" Vertical block of length 2 ");
        vert3 = new JButton(" Vertical block of length 3 ");
        solve = new JButton("SOLVE PUZZLE");
        red = new JButton("Red block, 'The Prisoner'");
        initial_board = new Board();
        initial_boardMatrix = new BoardMatrix();
        img = new InputBoardImage();
        label = new JLabel();
        button_pressed = "none";

        // adding actionCommands and actionEventListeners to buttons
        horz2.setActionCommand("horz2");
        horz3.setActionCommand("horz3");
        vert2.setActionCommand("vert2");
        vert3.setActionCommand("vert3");
        solve.setActionCommand("solve");
        red.setActionCommand("red");

        horz2.addActionListener(new ButtonClickListener());
        horz3.addActionListener(new ButtonClickListener());
        vert2.addActionListener(new ButtonClickListener());
        vert3.addActionListener(new ButtonClickListener());
        solve.addActionListener(new ButtonClickListener());
        red.addActionListener(new ButtonClickListener());
        img.addMouseListener(new CellSelectListener());

        // showing the window
        showInputWindow();
    }

    private void showInputWindow() {

        // setting sizes/dimensions
        mainFrame.setSize(600, 340);
        mainFrame.setResizable(false);
        mainFrame.setLayout(new BorderLayout());
        img.setSize(300, 300);
        img.setBounds(10, 10, 300, 300);
        horz2.setSize(300, 40);
        horz3.setSize(300, 40);
        vert2.setSize(300, 40);
        vert3.setSize(300, 40);
        red.setSize(300, 40);
        solve.setSize(300, 40);
        panel1.setSize(600, 40);
        panel2.setSize(300, 300);
        panel2.setLayout(new GridLayout(6, 0));

        // miscellaneous actions
        mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        img.setBackground(new Color(30, 130, 30));
        label.setText("Click on the block and then on its top-left cell in the grid");

        // adding components to their parents
        panel2.add(horz2);
        panel2.add(horz3);
        panel2.add(vert2);
        panel2.add(vert3);
        panel2.add(red);
        panel2.add(solve);
        panel1.add(label);
        mainFrame.add(panel2, BorderLayout.EAST);
        mainFrame.add(panel1, BorderLayout.NORTH);
        mainFrame.add(img, BorderLayout.CENTER);

        // showing the window
        mainFrame.setLocationRelativeTo(null);
        mainFrame.setVisible(true);

    }
}

public class GUIMain {

    public static void main(String[] args) {
        InputWindow iw = new InputWindow();
    }
}
