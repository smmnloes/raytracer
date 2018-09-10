package engine.util;

import static java.lang.Math.*;

public class Vector3D {
    public double x, y, z;

    public Vector3D(double x, double y, double z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }

    public Vector3D(double[] asArray) {
        this(asArray[0], asArray[1], asArray[2]);
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
        return sqrt((x * x) + (y * y) + (z * z));
    }

    public double dotProduct(Vector3D b) {
        return (this.x * b.x + this.y * b.y + this.z * b.z);
    }

    public Vector3D times(double scalar) {
        return new Vector3D(this.x * scalar, this.y * scalar, this.z * scalar);
    }

    public Vector3D negate() {
        return new Vector3D(-x, -y, -z);
    }

    public Vector3D translate(Vector3D b) {
        return plus(b);
    }

    public Vector3D rotate(Vector3D rotation){
        return rotateX(rotation.x).rotateY(rotation.y).rotateZ(rotation.z);
    }

    public Vector3D scale(Vector3D factor) {
        double[][] scaleMatrix = new double[][]{
                {factor.x, 0, 0, 0},
                {0, factor.y, 0, 0},
                {0, 0, factor.z, 0, 0},
                {0, 0, 0, 1}
        };
        return matrixMult(scaleMatrix);
    }

    public Vector3D transform(Vector3D translation, Vector3D rotation, Vector3D scale) {
        return translate(translation).rotateX(rotation.x).rotateY(rotation.y).rotateZ(rotation.z).scale(scale);
    }

    private Vector3D rotateX(double rotationX) {
        double[][] rotationXMatrix = new double[][]
                {
                        {1, 0, 0, 0},
                        {0, cos(rotationX), -sin(rotationX), 0},
                        {0, sin(rotationX), cos(rotationX), 0},
                        {0, 0, 0, 1}
                };
        return matrixMult(rotationXMatrix);
    }

    private Vector3D rotateY(double rotationY) {
        double[][] rotationYMatrix = new double[][]
                {
                        {cos(rotationY), 0, sin(rotationY), 0},
                        {0, 1, 0, 0},
                        {-sin(rotationY), 0, cos(rotationY), 0},
                        {0, 0, 0, 1}
                };
        return matrixMult(rotationYMatrix);
    }

    private Vector3D rotateZ(double rotationZ) {
        double[][] rotationZMatrix = new double[][]
                {
                        {cos(rotationZ), -sin(rotationZ), 0, 0},
                        {sin(rotationZ), cos(rotationZ), 0, 0},
                        {0, 0, 1, 0},
                        {0, 0, 0, 1}
                };
        return matrixMult(rotationZMatrix);
    }


    private Vector3D matrixMult(double[][] matrix) {
        RuntimeException not4x4quadratic = new RuntimeException("Matrix must be 4x4 and quadratic");
        if (matrix.length != 4) {
            throw not4x4quadratic;
        }

        for (double[] line : matrix) {
            if (line.length != 4) {
                throw not4x4quadratic;
            }
        }

        double[] result = new double[4];
        double[] thisExtended = new double[]{x, y, z, 1};

        for (int i = 0; i < 4; i++) {
            int resultRow = 0;
            for (int j = 0; j < 4; j++) {
                resultRow += matrix[i][j] * thisExtended[j];
            }
            result[i] = resultRow;
        }

        return new Vector3D(result);

    }

}
