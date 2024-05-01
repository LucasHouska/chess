public class Piece {
    protected Position position;
    protected PieceColor color;

    public Piece (PieceColor color, Position position) {
        this.color = color;
        this.position = position;
    }

    public PieceColor getColor() {
        return color;
    }

    public Position getPosition() {
        return position;
    }

    public void setPosition(Position position) {
        this.position = position;
    }

    public abstract boolean canMoveTo(Position newPosition, Piece[][] board);
}
