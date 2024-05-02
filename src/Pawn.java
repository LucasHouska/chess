public class Pawn extends Piece {
    public Pawn(PieceColor color, Position position) {
        super(color, position);
    }

    @Override
    public boolean isValidMove(Position newPosition, Piece[][] board) {
        int forwardDirection = color == PieceColor.WHITE ? 1 : -1;
        int rowDiff = (newPosition.getRow() - position.getRow()) * forwardDirection;
        int colDiff = newPosition.getColumn() - position.getColumn();

        // Forward Move
        if (colDiff == 0 && rowDiff == 1 && board[newPosition.getRow()][newPosition.getColumn()] == null) {
            return true;
        }

        // Initial 2 square move
        boolean isStartingPosition = (color == PieceColor.WHITE && position.getRow() == 1) || (color == PieceColor.BLACK && position.getRow() == 6);
        if (colDiff == 0 && rowDiff == 2 && board[newPosition.getRow()][newPosition.getColumn()] == null && isStartingPosition) {
            // Check the square in between for blocking pieces
            int middleRow = position.getRow() + forwardDirection;
            if (board[middleRow][position.getColumn()] == null) {
                return true; // Move forward two squares
            }
        }

        // Diagonal Capture
        if (Math.abs(colDiff) == 1 && Math.abs(rowDiff) == 1 &&
                board[newPosition.getRow()][newPosition.getColumn()] != null &&
                board[newPosition.getRow()][newPosition.getColumn()].getColor() != color) {
            return true; // Capture enemy piece
        }
        return false;
    }
}