public class Knight extends Piece {
    public Knight(PieceColor color, Position position) {
        super(color, position);
    }

    @Override
    public boolean isValidMove(Position newPosition, Piece[][] board) {
        if (newPosition.equals(this.position)) {
            return false; // Cannot move to the same position
        }

        // Knights can move in an "L" shape, with a maximum distance of 2 squares in each direction.
        int rowDiff = Math.abs(newPosition.getRow() - position.getRow());
        int colDiff = Math.abs(newPosition.getColumn() - position.getColumn());

        // Check for the 'L' shaped move pattern
        boolean isValidLMove = (rowDiff == 2 && colDiff == 1) || (rowDiff == 1 && colDiff == 2);

        if (!isValidLMove) {
            return false; // Not a valid knight move
        }

        // Move is valid if the destination square is empty or contains an opponent's
        // piece
        Piece targetPiece = board[newPosition.getRow()][newPosition.getColumn()];
        if (targetPiece == null) {
            return true; // The square is empty, move is valid
        } else {
            return targetPiece.getColor() != this.getColor(); // Can capture if it's an opponent's piece
        }
    }
}
