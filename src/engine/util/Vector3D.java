package engine.util;

public class Vector3D {
    double x, y, z;

    public Vector3D(double x, double y, double z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }

    public Vector3D minus(Vector3D b) {
        return new Vector3D(this.x - b.x, this.y - b.y, this.z - b.z);
    }

    public Vector3D plus(Vector3D b) {
        return new Vector3D(this.x + b.x, this.y + b.y, this.z + b.z);
    }

    public Vector3D normalize() {
        double length = length();
        x /= length;
        y /= length;
        z /= length;
        return this;
    }

    public double length() {
        return Math.sqrt((x*x) + (y*y) + (z*z));
    }

    public double dotProduct(Vector3D b) {
        return (this.x * b.x + this.y * b.y + this.z * b.z);
    }

    public Vector3D times(double scalar) {
        return new Vector3D(this.x * scalar, this.y * scalar, this.z * scalar);
    }

    public Vector3D negate() {
        return new Vector3D(-x, -y, - z);
    }
}
