package unblockmesolver;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.event.*;
import unblockmesolver.Elements.*;
import java.util.ArrayList;
import javax.swing.*;
import java.awt.Graphics;

class BoardImage extends JPanel {

    Board board;
    int CELL_SIZE;
    int PADDING;

    public BoardImage() {
        board = new Board();
        CELL_SIZE = 66;
        PADDING = 0;
    }

    public void setBoard(Board b) {
        this.board = new Board(b);
        super.repaint();
    }

    @Override
    public void paint(Graphics g) {
        super.paint(g);

        // drawing of blocks is done in this part
        for (Block block : board.blocks) {
            if (block.type == 'r') {
                g.setColor(Color.red);
                g.fillRect(block.x * (PADDING + CELL_SIZE), block.y * (PADDING + CELL_SIZE), block.length * CELL_SIZE, CELL_SIZE);
                g.setColor(Color.black);
                g.drawRect(block.x * (PADDING + CELL_SIZE), block.y * (PADDING + CELL_SIZE), block.length * CELL_SIZE, CELL_SIZE);
            } else if (block.type == 'b') {
                g.setColor(new Color(255, 200, 0));
                if (block.orientation == 'h') {
                    g.fillRect(block.x * (PADDING + CELL_SIZE), block.y * (PADDING + CELL_SIZE), block.length * CELL_SIZE, CELL_SIZE);
                    g.setColor(Color.black);
                    g.drawRect(block.x * (PADDING + CELL_SIZE), block.y * (PADDING + CELL_SIZE), block.length * CELL_SIZE, CELL_SIZE);
                } else if (block.orientation == 'v') {
                    g.fillRect(block.x * (PADDING + CELL_SIZE), block.y * (PADDING + CELL_SIZE), CELL_SIZE, block.length * CELL_SIZE);
                    g.setColor(Color.black);
                    g.drawRect(block.x * (PADDING + CELL_SIZE), block.y * (PADDING + CELL_SIZE), CELL_SIZE, block.length * CELL_SIZE);
                }

            }
        }
    }
}

public class SolutionWindow {

    int index;
    JFrame mainFrame;
    JPanel panel1, panel2;
    JButton nextStep, prevStep;
    JLabel caption;
    BoardImage img;
    ArrayList<Board> result;

    public class ButtonClickListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            String button_pressed = e.getActionCommand();
            if (button_pressed.equals("next")) {
                if (nextStep.isEnabled()) {
                    index++;
                    showSolution();
                }
            } else if (button_pressed.equals("prev")) {
                if (prevStep.isEnabled()) {
                    index--;
                    showSolution();
                }
            }

        }
    }

    public SolutionWindow() {
        // does nothing
    }

    public SolutionWindow(ArrayList<Board> steps) {
        this.result = new ArrayList<>(steps);
        this.index = 0;
        this.mainFrame = new JFrame("Solution Steps");
        this.panel1 = new JPanel();
        this.panel2 = new JPanel();
        this.nextStep = new JButton(" Next > ");
        this.prevStep = new JButton(" < Prev ");
        this.img = new BoardImage();
        this.caption = new JLabel();

        // adding event listeners to buttons
        nextStep.setActionCommand("next");
        prevStep.setActionCommand("prev");
        nextStep.addActionListener(new ButtonClickListener());
        prevStep.addActionListener(new ButtonClickListener());

        showSolution();
    }

    private void showSolution() {

        // miscellaneous actions
        this.mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        img.setBackground(new Color(30, 130, 30));
        img.setBoard(result.get(index));
        caption.setText("Step " + (1 + index) + "/" + result.size());
        nextStep.setEnabled(true);
        prevStep.setEnabled(true);
        if (index == 0) {
            prevStep.setEnabled(false);
        }
        if (index == result.size() - 1) {
            nextStep.setEnabled(false);
        }

        // setting sizes of containers
        mainFrame.setSize(400, 480);
        mainFrame.setResizable(false);
        mainFrame.setLayout(new BorderLayout(5, 5));
        panel1.setSize(400, 40);
        panel2.setSize(400, 40);
        caption.setSize(400, 40);
        img.setSize(400, 400);

        // adding children to parent components/containers
        panel2.add(prevStep);
        panel2.add(nextStep);
        panel1.add(caption);
        mainFrame.add(panel1, BorderLayout.NORTH);
        mainFrame.add(panel2, BorderLayout.SOUTH);
        mainFrame.add(img, BorderLayout.CENTER);

        // displaying the window
        mainFrame.setLocationRelativeTo(null);
        mainFrame.setVisible(true);
    }

}
