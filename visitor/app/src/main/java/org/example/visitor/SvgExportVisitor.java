package org.example.visitor;

import java.util.Locale;

import org.example.shape.Circle;
import org.example.shape.Rectangle;
import org.example.shape.Triangle;

public class SvgExportVisitor implements ShapeVisitor {
    private static final Locale LOCALE = Locale.US;

    private StringBuilder svgContent = new StringBuilder();

    @Override
    public void visit(Circle c) {
        svgContent.append(String.format(LOCALE, "<circle r=\"%.2f\" />%n", c.getRadius()));
    }

    @Override
    public void visit(Rectangle r) {
        svgContent.append(String.format(LOCALE, "<rect width=\"%.2f\" height=\"%.2f\" />%n", r.getWidth(), r.getHeight()));
    }

    @Override
    public void visit(Triangle t) {
        svgContent.append(String.format(LOCALE, "<!-- triangle b=%.2f h=%.2f -->%n", t.getBase(), t.getHeight()));
    }

    public String getSvgContent() {
        return svgContent.toString();
    }
}