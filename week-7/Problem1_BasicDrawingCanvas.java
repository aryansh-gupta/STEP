public class Problem1_BasicDrawingCanvas {
    public static abstract class Shape {
        private static int counter = 0;
        private final String shapeId;
        public Shape() {
            counter++;
            this.shapeId = "SHAPE-" + counter;
        }
        public abstract double calculateArea();
        public abstract void scale(double factor);
        public void scale(double xFactor, double yFactor) {
            scale(Math.sqrt(xFactor * yFactor));
        }
        public String getShapeId() {
            return shapeId;
        }
    }
    public static class CircleShape extends Shape {
        private double radius;
        public CircleShape(double radius) {
            if (radius <= 0) {
                throw new IllegalArgumentException("Radius must be positive");
            }
            this.radius = radius;
        }
        public double getRadius() {
            return radius;
        }
        @Override
        public double calculateArea() {
            return Math.PI * radius * radius;
        }
        @Override
        public void scale(double factor) {
            if (factor <= 0) {
                throw new IllegalArgumentException("Scale factor must be positive");
            }
            this.radius *= factor;
        }
    }
    public static class SquareShape extends Shape {
        private double side;
        public SquareShape(double side) {
            if (side <= 0) {
                throw new IllegalArgumentException("Side must be positive");
            }
            this.side = side;
        }
        public double getSide() {
            return side;
        }
        @Override
        public double calculateArea() {
            return side * side;
        }
        @Override
        public void scale(double factor) {
            if (factor <= 0) {
                throw new IllegalArgumentException("Scale factor must be positive");
            }
            this.side *= factor;
        }
    }
    public static void printArea(Shape s) {
        if (s != null) {
            System.out.printf("Area of %s: %.2f%n", s.getShapeId(), s.calculateArea());
        }
    }
    public static void main(String[] args) {
        CircleShape c = new CircleShape(5.0);
        System.out.printf("Circle area: %.2f%n", c.calculateArea());
        SquareShape sq = new SquareShape(4.0);
        System.out.println("Square initial area: " + sq.calculateArea());
        sq.scale(2.0);
        System.out.println("Square area after scale(2.0): " + sq.calculateArea());
        printArea(c);
        printArea(sq);
    }
}
