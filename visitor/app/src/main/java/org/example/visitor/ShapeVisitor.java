package org.example.visitor;

import org.example.shape.Circle;
import org.example.shape.Rectangle;
import org.example.shape.Triangle;

public interface ShapeVisitor {
    void visit(Circle circle);
    void visit(Rectangle rectangle);
    void visit(Triangle triangle);
}