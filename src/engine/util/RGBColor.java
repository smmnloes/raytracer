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
        return new RGBColor((int) (this.red * scalar), (int) (this.green * scalar), (int) (this.blue * scalar));
    }

    public RGBColor times(RGBColor c) {
        return new RGBColor((int) (this.red * c.red), (int) (this.green * c.green), (int) (this.blue * c.blue));
    }

    public RGBColor divideBy(RGBColor c) {
        return new RGBColor((int) (this.red / c.red), (int) (this.green / c.green), (int) (this.blue / c.blue));
    }

}
