package engine.models.components.geometry;

import engine.models.components.Material;
import engine.util.Intersection;
import engine.util.Ray;
import engine.util.Vector3D;


public class Sphere extends Geometry {
    private double radius;

    public Sphere(Vector3D position, Vector3D rotation, Vector3D scale, double radius, Material material) {
        super(position, rotation, scale, material);
        this.radius = radius;
    }

    @Override
    public Intersection intersects(Ray ray) {
        double t0, t1;

        // analytic solution
        Vector3D L = ray.origin.minus(position);
        double a = ray.direction.dotProduct(ray.direction);
        double b = 2 * ray.direction.dotProduct(L);
        double c = L.dotProduct(L) - (radius * radius);
        double[] quadraticSolution = solveQuadratic(a, b, c);
        if (quadraticSolution == null) {
            return null;
        }

        t0 = quadraticSolution[0];
        t1 = quadraticSolution[1];

        if (t0 > t1) {
            double tmp = t0;
            t0 = t1;
            t1 = tmp;
        }


        if (t0 < 0) {
            t0 = t1; // if t0 is negative, let's use t1 instead
            if (t0 < 0) return null; // both t0 and t1 are negative
        }

        return new Intersection(this, t0);
    }


    double[] solveQuadratic(double a, double b, double c) {
        double discr = b * b - 4 * a * c;
        if (discr < 0) return null;
            // one solution
        else if (discr == 0) {
            return new double[]{-0.5 * b / a};
            // two solutions
        } else {
            double q = (b > 0) ?
                    -0.5 * (b + Math.sqrt(discr)) :
                    -0.5 * (b - Math.sqrt(discr));
            double x0 = q / a;
            double x1 = c / q;
            return (x0 > x1) ? new double[]{x1, x0} : new double[]{x0, x1};
        }
    }

    public Vector3D getNormal(Vector3D hitPoint) {
        return hitPoint.minus(position).normalize();
    }
}
