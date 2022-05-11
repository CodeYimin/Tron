package entities;

import misc.Vector;

public abstract class GameObject {
    private Vector position = new Vector(0, 0);
    private Vector velocity = new Vector(0, 0);

    public GameObject() {

    }

    public Vector getPosition() {
        return this.position;
    }

    public void setPosition(Vector position) {
        this.position = position;
    }

    public Vector getVelocity() {
        return this.velocity;
    }

    public void setVelocity(Vector velocity) {
        this.velocity = velocity;
    }

    public void update() {
        setPosition(position.add(velocity));
        System.out.println(velocity);
    }
}
