package org.example;

import java.util.List;

import org.example.shape.Circle;
import org.example.shape.Rectangle;
import org.example.shape.Shape;
import org.example.shape.Triangle;
import org.example.visitor.AreaVisitor;
import org.example.visitor.SvgExportVisitor;

public class App {
    public static void main(String[] args) {
        List<Shape> shapes = List.of(
            new Circle(3),
            new Rectangle(4, 5),
            new Triangle(6, 7)
        );

        // 1) Compute total area with AreaVisitor
        AreaVisitor areaVisitor = new AreaVisitor();
        shapes.forEach(s -> s.accept(areaVisitor));
        System.out.printf("Total area: %.2f%n", areaVisitor.getTotalArea());

        // 2) Render SVG markup with SvgExportVisitor
        SvgExportVisitor svgVisitor = new SvgExportVisitor();
        shapes.forEach(s -> s.accept(svgVisitor));

        System.out.println("\nSVG output:\n" + svgVisitor.getSvgContent());
    }
}