package misc;

public class Vector {
    private double x;
    private double y;

    public static final Vector ZERO = new Vector(0, 0);
    public static final Vector ONE = new Vector(1, 1);
    public static final Vector NEGATIVE_ONE = new Vector(-1, -1);
    public static final Vector UP = new Vector(0, -1);
    public static final Vector DOWN = new Vector(0, 1);
    public static final Vector LEFT = new Vector(-1, 0);
    public static final Vector RIGHT = new Vector(1, 0);

    public Vector(double x, double y) {
        this.x = x;
        this.y = y;
    }

    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }

    public void setX(double x) {
        this.x = x;
    }

    public void setY(double y) {
        this.y = y;
    }

    public Vector add(Vector v) {
        return new Vector(x + v.getX(), y + v.getY());
    }

    public Vector subtract(Vector v) {
        return new Vector(x - v.getX(), y - v.getY());
    }

    public Vector multiply(double scalar) {
        return new Vector(x * scalar, y * scalar);
    }

    public Vector divide(double scalar) {
        return new Vector(x / scalar, y / scalar);
    }

    public Vector modulus(double scalar) {
        return new Vector(x % scalar, y % scalar);
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
