import javax.swing.*;
import java.awt.*;

public class ChessBoard extends JPanel {
    private Piece[][] board;

    public ChessBoard() {
        board = new Piece[8][8];
        setupPieces();
    }

    private void setupPieces() {
        // TODO: Set up the board
    }
}
