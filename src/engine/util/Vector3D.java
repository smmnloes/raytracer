package engine.util;

public class Vector3D {
    double x, y, z;

    public Vector3D(double x, double y, double z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }

    public static Vector3D minus(Vector3D a, Vector3D b) {
        return new Vector3D(a.x - b.x, a.y - b.y, a.z - b.z);
    }

    public Vector3D plus(Vector3D b) {
        return new Vector3D(this.x + b.x, this.y + b.y, this.z + b.z);
    }

    public void normalize() {
        double length = length(this);
        this.x /= length;
        this.y /= length;
        this.z /= length;
    }

    public static double length(Vector3D v) {
        return Math.sqrt(Math.pow(v.x, 2) + Math.pow(v.y, 2) + Math.pow(v.z, 2));
    }

    public static double distance(Vector3D a, Vector3D b) {
        return length(minus(b, a));
    }

    public double dotProduct(Vector3D b) {
        return (this.x * b.x + this.y * b.y + this.z * b.z);
    }

    public Vector3D times(double scalar) {
        return new Vector3D(this.x * scalar, this.y * scalar, this.z * scalar);
    }
}
