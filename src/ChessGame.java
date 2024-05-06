public class ChessGame {
    private ChessBoard board;
    private boolean whiteTurn = true;

    public ChessGame() {
        board = new ChessBoard();
    }

    public ChessBoard getBoard() {
        return this.board;
    }

    public void resetGame() {
        board = new ChessBoard();
        whiteTurn = true;
    }

    public PieceColor getCurrentPlayerColor() {
        return whiteTurn ? PieceColor.WHITE : PieceColor.BLACK;
    }

    public boolean makeMove(Position start, Position end) {
        Piece movingPiece = board.getPiece(start.getRow(), start.getColumn());
        if (movingPiece == null || movingPiece.getColor() != (whiteTurn ? PieceColor.WHITE : PieceColor.BLACK)) {
            return false;
        }

        if (movingPiece.isValidMove(end, board.getBoard())) {
            board.movePiece(start, end);
            whiteTurn = !whiteTurn;
            return true;
        }
        return false;
    }

    public boolean isInCheck(PieceColor kingColor) {
        Position kingPosition = findKingPosition(kingColor);
        for (int row = 0; row < board.getBoard().length; row++) {
            for (int col = 0; col < board.getBoard()[row].length; col++) {
                Piece piece = board.getPiece(row, col);
                if (piece != null && piece.getColor() != kingColor) {
                    if (piece.isValidMove(kingPosition, board.getBoard())) {
                        return true; // An opposing piece can capture the king
                    }
                }
            }
        }
        return false;
    }

    private Position findKingPosition(PieceColor color) {
        for (int row = 0; row < board.getBoard().length; row++) {
            for (int col = 0; row < board.getBoard()[row].length; col++) {
                Piece piece = board.getPiece(row, col);
                if (piece instanceof King && piece.getColor() == color) {
                    return new Position(row, col);
                }
            }
        }
        throw new RuntimeException("No king found for color, which should never happen");
    }

    public boolean isInCheckmate(PieceColor kingColor) {
        if (!isInCheck(kingColor)) {
            return false; // Not in check, so cannot be checkmate
        }

        Position kingPosition = findKingPosition(kingColor);
        King king = (King) board.getPiece(kingPosition.getRow(), kingPosition.getColumn());

        // Attempt to find a move that gets the king out of check
        for (int rowOffset = -1; rowOffset <= 1; rowOffset++) {
            for (int colOffset = -1; colOffset <= 1; colOffset++) {
                if (rowOffset == 0 && colOffset == 0) {
                    continue; // Skip the current position
                }
                Position newPosition = new Position(kingPosition.getRow() + rowOffset, kingPosition.getColumn() + colOffset);

                // Check if moving the king to the new position is a legal move and does not
                // result in a check
                if (isPositionOnBoard(newPosition) && king.isValidMove(newPosition, board.getBoard())) {
                    return false; // A move that gets the king out of check is found
                }
            }
        }
        return true;
    }

    private boolean isPositionOnBoard(Position position) {
        return position.getRow() >= 0 && position.getRow() < board.getBoard().length &&
                position.getColumn() >= 0 && position.getColumn() < board.getBoard()[0].length;

    }

    private boolean wouldBeInCheckAfterMove(PieceColor kingColor, Position from, Position to) {
        // Simulate the move temporarily
        Piece temp = board.getPiece(to.getRow(), to.getColumn());
        board.setPiece(to.getRow(), to.getColumn(), board.getPiece(from.getRow(), from.getColumn()));
        board.setPiece(from.getRow(), from.getColumn(), null);

        boolean inCheck = isInCheck(kingColor);

        // Undo the move
        board.setPiece(from.getRow(), from.getColumn(), board.getPiece(to.getRow(), to.getColumn()));
        board.setPiece(to.getRow(), to.getColumn(), temp);

        return inCheck;

    }

    private Position selectedPosition;

    public boolean isPieceSelected() {
        return selectedPosition != null;
    }

    public boolean handleSquareSelection(int row, int col) {
        if (selectedPosition == null) {
            // Attempt to select a piece
            Piece selectedPiece = board.getPiece(row, col);
            if (selectedPiece != null
                    && selectedPiece.getColor() == (whiteTurn ? PieceColor.WHITE : PieceColor.BLACK)) {
                selectedPosition = new Position(row, col);
                return false; // Indicate a piece was selected but not moved
            }
        } else {
            // Attempt to move the selected piece
            boolean moveMade = makeMove(selectedPosition, new Position(row, col));
            selectedPosition = null; // Reset selection regardless of move success
            return moveMade; // Return true if a move was successfully made
        }
        return false; // Return false if no piece was selected or move was not made
    }
}
