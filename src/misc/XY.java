package misc;

public class XY {
    public static final XY ZERO = new XY(0, 0);
    public static final XY UP = new XY(0, -1);
    public static final XY DOWN = new XY(0, 1);
    public static final XY LEFT = new XY(-1, 0);
    public static final XY RIGHT = new XY(1, 0);

    private final int x;
    private final int y;

    public XY(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public XY(WidthHeight dimension) {
        this(dimension.getWidth(), dimension.getHeight());
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public XY add(XY v) {
        return new XY(x + v.getX(), y + v.getY());
    }

    public XY add(int scalar) {
        return new XY(x + scalar, y + scalar);
    }

    public XY subtract(XY v) {
        return new XY(x - v.getX(), y - v.getY());
    }

    public XY subtract(int scalar) {
        return new XY(x - scalar, y - scalar);
    }

    public XY multiply(int scalar) {
        return new XY(x * scalar, y * scalar);
    }

    public XY divide(int scalar) {
        return new XY(x / scalar, y / scalar);
    }

    public XY modulus(int scalar) {
        return new XY(x % scalar, y % scalar);
    }

    public boolean inBounds(WidthHeight bounds) {
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
        if (!(o instanceof XY)) {
            return false;
        }
        XY vector = (XY) o;
        return x == vector.getX() && y == vector.getY();
    }
}
