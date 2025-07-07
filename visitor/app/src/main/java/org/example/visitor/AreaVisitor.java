package org.example.visitor;

import org.example.shape.Circle;
import org.example.shape.Rectangle;
import org.example.shape.Triangle;

public class AreaVisitor implements ShapeVisitor {
    private double totalArea = 0d;

    @Override
    public void visit(Circle c) {
        totalArea += Math.PI * Math.pow(c.getRadius(), 2);
    }

    @Override
    public void visit(Rectangle r) {
        totalArea += r.getWidth() * r.getHeight();
    }

    @Override
    public void visit(Triangle t) {
        totalArea += 0.5 * t.getBase() * t.getHeight();
    }

    public double getTotalArea() {
        return totalArea;
    }    
}