package de.uniwue.jpp.oscidrawing.generation.pathutils;

public class Line {
    Point start;
    Point end;


    public Line(Point start, Point end) {
        this.start = start;
        this.end = end;

    }

    public Point getStart() {
        return start;
    }

    public Point getEnd() {
        return end;
    }

    public double length() {
        return start.distanceTo(end);

    }

    public Point getPointAt(double percentage) {

        return start.interpolateTo(end, percentage);
    }

    @Override
    public String toString() {
        return "Line{" +
                "p1=" + start +
                ", p2=" + end +
                '}';
    }
}