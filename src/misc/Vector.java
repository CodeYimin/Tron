package misc;

public class Vector {
    public static final Vector ZERO = new Vector(0, 0);
    public static final Vector UP = new Vector(0, -1);
    public static final Vector DOWN = new Vector(0, 1);
    public static final Vector LEFT = new Vector(-1, 0);
    public static final Vector RIGHT = new Vector(1, 0);

    private final int x;
    private final int y;

    public Vector(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public Vector(Dimension dimension) {
        this(dimension.getWidth(), dimension.getHeight());
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public Vector add(Vector v) {
        return new Vector(x + v.getX(), y + v.getY());
    }

    public Vector add(int scalar) {
        return new Vector(x + scalar, y + scalar);
    }

    public Vector subtract(Vector v) {
        return new Vector(x - v.getX(), y - v.getY());
    }

    public Vector subtract(int scalar) {
        return new Vector(x - scalar, y - scalar);
    }

    public Vector multiply(int scalar) {
        return new Vector(x * scalar, y * scalar);
    }

    public Vector divide(int scalar) {
        return new Vector(x / scalar, y / scalar);
    }

    public Vector modulus(int scalar) {
        return new Vector(x % scalar, y % scalar);
    }

    public boolean inBounds(Dimension bounds) {
        return x >= 0 && x < bounds.getWidth() && y >= 0 && y < bounds.getHeight();
    }

    @Override
    public String toString() {
        return "(" + x + ", " + y + ")";
    }

    @Override
    public boolean equals(Object o) {
        if (o == this)
            return true;
        if (!(o instanceof Vector)) {
            return false;
        }
        Vector vector = (Vector) o;
        return x == vector.getX() && y == vector.getY();
    }
}
