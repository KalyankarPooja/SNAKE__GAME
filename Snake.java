import javax.swing.*;

public class Snake extends JFrame {
    Board board;
    Snake(){
        board=new Board();
        add(board);
        pack();
       setResizable(false);

        setVisible(true);
    }

    public static void main(String[] args) {
        Snake sn=new Snake();
    }
}
