import java.awt.geom.Line2D;
import java.awt.geom.Point2D;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.DoubleStream;

/**
 * Represents a polygon, a set of connected points.
 */
public class Polygon {

    private final javafx.scene.shape.Polygon fxPolygon;
    private final List<Point2D> points;
    private final List<Line2D> lines;
    private final double minX;
    private final double maxX;
    private final double minY;
    private final double maxY;

    public Polygon(List<Point2D> points) {
        this.points = points;
        minX = points.stream().map(Point2D::getX).sorted(Double::compareTo).findFirst().get();
        minY = points.stream().map(Point2D::getY).sorted(Double::compareTo).findFirst().get();
        maxX = points.stream().map(Point2D::getX).sorted((a, b) -> -a.compareTo(b)).findFirst().get();
        maxY = points.stream().map(Point2D::getY).sorted((a, b) -> -a.compareTo(b)).findFirst().get();
        lines = new ArrayList<>();
        for (int i = 0; i < points.size() - 1; i++) {
            lines.add(new Line2D.Double(points.get(i), points.get(i+1)));
        }
        lines.add(new Line2D.Double(points.get(points.size() - 1), points.get(0)));
        fxPolygon = new javafx.scene.shape.Polygon(points.stream().flatMapToDouble(p -> DoubleStream.of(p.getX(), p.getY())).toArray());
    }

    public boolean contains(double x, double y) {
        return fxPolygon.contains(x, y);
    }

    private boolean intersects(Line2D line) {
        return this.lines.stream().anyMatch(polygonLine -> polygonLine.intersectsLine(line));
    }

    public boolean intersects(List<Line2D> lines) {
        return lines.stream().anyMatch(this::intersects);
    }

    public List<Line2D> lines() {
        return lines;
    }

    public double minX() {
        return minX;
    }

    public double maxX() {
        return maxX;
    }

    public double maxY() {
        return maxY;
    }

    public double minY() {
        return minY;
    }

    public double width() {
        return maxX - minX;
    }

    public double height() {
        return maxY - minY;
    }

    public boolean isSquare() {
        return points.size() == 4
                && points.get(0).distance(points.get(2)) == points.get(1).distance(points.get(3))
                && points.get(0).distance(points.get(1)) == points.get(1).distance(points.get(2));
    }

    public double squareSize() {
        if (!isSquare()) throw new RuntimeException("this polygon is not a square");
        return points.get(0).distance(points.get(2));
    }

}