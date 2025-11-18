package de.uniwue.jpp.oscidrawing.generation.pathutils;

public class Point {
    double x;
    double y;

    public Point(double x, double y) {
        this.x = x;
        this.y = y;
    }

    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }

    public double distanceTo(Point p) {
        return Math.sqrt(Math.pow(getX()-p.getX(),2) + Math.pow(getY()-p.getY(),2) );
    }

    @Override
    public String toString() {
        return "Point{" +
                "x=" + x +
                ", y=" + y +
                "}";
    }




    public Point interpolateTo(Point p, double factor) {
        return new Point(
                getX() + factor * (p.getX()- getX()),
                getY() + factor * (p.getY() - getY())
        );

    }
}
