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
        return Math.sqrt(Math.pow(this.x, 2) + Math.pow(this.y, 2) + Math.pow(this.z, 2));
    }

    public double distance(Vector3D b) {
        return this.minus(b).length();
    }

    public double dotProduct(Vector3D b) {
        return (this.x * b.x + this.y * b.y + this.z * b.z);
    }

    public Vector3D times(double scalar) {
        return new Vector3D(this.x * scalar, this.y * scalar, this.z * scalar);
    }

    public Vector3D negate() {
        x = -x;
        y = -y;
        z = -z;
        return this;
    }
}
