package engine.util;

public class RGBColor {
    public double red, green, blue;

    public RGBColor(double red, double green, double blue) {
        this.red = red;
        this.green = green;
        this.blue = blue;
    }

    public int toInt() {
        int redInt = Math.min((int) (this.red*255), 255);
        int greenInt = Math.min((int) (this.green*255), 255);
        int blueInt = Math.min((int) (this.blue * 255), 255);
        return 0xFF000000 | (redInt << 16) | (greenInt << 8) | (blueInt);
    }

    public RGBColor times(double scalar) {
        return new RGBColor(this.red * scalar, this.green * scalar, this.blue * scalar);
    }

    public RGBColor times(RGBColor c) {
        return new RGBColor(this.red * c.red, this.green * c.green, this.blue * c.blue);
    }

    public RGBColor divideBy(RGBColor c) {
        return new RGBColor(this.red / c.red, this.green / c.green, this.blue / c.blue);
    }

    public RGBColor divideBy(double scalar) {
        return new RGBColor(this.red / scalar, this.green / scalar, this.blue / scalar);
    }

    public RGBColor add(RGBColor c) {
        return new RGBColor(this.red + c.red, this.blue + c.blue, this.green + c.green);
    }

    public static RGBColor black() {
        return new RGBColor(0, 0, 0);
    }

    public static RGBColor grey() {
        return new RGBColor(0.5, 0.5, 0.5);
    }

    public static RGBColor white() {
        return new RGBColor(1, 1, 1);
    }

    public static RGBColor red() {
        return new RGBColor(1, 0, 0);
    }

    public static RGBColor green() {
        return new RGBColor(0, 1, 0);
    }

    public static RGBColor blue() {
        return new RGBColor(0, 0, 1);
    }
}
