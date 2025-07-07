package org.example.shape;

import org.example.visitor.ShapeVisitor;

public interface Shape {
    void accept(ShapeVisitor visitor);
}