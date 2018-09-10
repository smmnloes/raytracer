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


    public static Vector3D matrixVectMult(double[][] matrix, Vector3D v) {
        double[] vector = new double[]{v.x, v.y, v.z, 1};
        for (double[] line : matrix) {
            if (line.length != vector.length) {
                throw new RuntimeException("Matrix and vector must have compatible dimensions");
            }
        }

        double[] result = new double[vector.length];


        for (int i = 0; i < 4; i++) {
            double resultRow = 0;
            for (int j = 0; j < 4; j++) {
                resultRow += matrix[i][j] * vector[j];
            }
            result[i] = resultRow;
        }

        return new Vector3D(result);

    }

    private static double[][] matrixMatrixMult(double[][] matrix1, double[][] matrix2) {

        for (double[] line : matrix1) {
            if (line.length != matrix2.length) {
                throw new RuntimeException("Matrices must have compatible dimensions");
            }
        }

        double[][] result = new double[matrix2.length][matrix1.length];

        // i = rows in first matrix
        for (int i = 0; i < matrix1.length; i++) {
            // j = columns in first matrix
            for (int j = 0; j < matrix1[i].length; j++) {
                double cellResult = 0;
                // k = rows in second matrix
                for (int k = 0; k < matrix2.length; k++) {
                    cellResult += matrix1[i][j] * matrix2[j][k];
                }
                result[i][j] = cellResult;
            }
        }

        return result;

    }

    public static double[][] createToWorldMatrix(Vector3D translation, Vector3D rotation, Vector3D scale) {
        double[][] rotX = new double[][]
                {
                        {1, 0, 0, 0},
                        {0, cos(rotation.x), -sin(rotation.x), 0},
                        {0, sin(rotation.x), cos(rotation.x), 0},
                        {0, 0, 0, 1}
                };

        double[][] rotY = new double[][]
                {
                        {cos(rotation.y), 0, sin(rotation.y), 0},
                        {0, 1, 0, 0},
                        {-sin(rotation.y), 0, cos(rotation.y), 0},
                        {0, 0, 0, 1}
                };

        double[][] rotZ = new double[][]
                {
                        {cos(rotation.z), -sin(rotation.z), 0, 0},
                        {sin(rotation.z), cos(rotation.z), 0, 0},
                        {0, 0, 1, 0},
                        {0, 0, 0, 1}
                };

        double[][] sca = new double[][]{
                {scale.x, 0, 0, 0},
                {0, scale.y, 0, 0},
                {0, 0, scale.z, 0, 0},
                {0, 0, 0, 1}
        };

        double[][] translate = new double[][]{
                {1, 0, 0, translation.x},
                {0, 1, 0, translation.y},
                {0, 0, 1, translation.z},
                {0, 0, 0, 1}
        };

        return matrixMatrixMult(translate, matrixMatrixMult(rotX, matrixMatrixMult(rotY, matrixMatrixMult(rotZ, sca))));

    }

}
