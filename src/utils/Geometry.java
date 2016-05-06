package utils;

import javafx.geometry.Pos;
import org.newdawn.slick.geom.Vector2f;

/**
 * Created by Termi on 09/09/2015.
 */
public class Geometry {

    public static float dot(Vector2f u, Vector2f v) {
        return u.dot(v);
    }

    public static float det(Vector2f u, Vector2f v) {
        return u.x * v.y - u.y * v.x;
    }

    /**
     * Returns the vector between two points
     * @param a Point A
     * @param b Point B
     * @return The vector AB
     */
    public static Vector2f Vector2f(Vector2f a, Vector2f b) {
        return new Vector2f(b.x - a.x, b.y - a.y);
    }

    public static double lerp(double a, double b, double s) {
        if (s < 0)
            s = 0;
        if (s > 1)
            s = 1;
        return a + s * (b - a);
    }

    /**
     * Checks if circle intersects with a rectangle
     * @param p Center of the circle
     * @param r Radius of the circle
     * @param a Corner A of the rectangle
     * @param b Corner B of the rectangle
     * @param c Corner C of the rectangle
     * @param d Corner D of the rectangle
     * @return True if the circle intersects the rectangle
     */
    public static boolean intersects(Vector2f p, int r, Vector2f a, Vector2f b, Vector2f c, Vector2f d) {
        return intersects(p, r, a, b) ||
                intersects(p, r, b, c) ||
                intersects(p, r, c, d) ||
                intersects(p, r, d, a) ||
                insideRectangle(p, a, b, c, d);

    }

    /**
     * Checks if a circle intersects a triangle
     * @param p Center of the circle
     * @param r Radius of the circle
     * @param a Corner A of the triangle
     * @param b Corner B of the triangle
     * @param c Corner C of the triangle
     * @return True if the circle intersects the triangle
     */
    public static boolean intersects(Vector2f p, int r, Vector2f a, Vector2f b, Vector2f c) {
        return intersects(p, r, a, b) ||
                intersects(p, r, b, c) ||
                intersects(p, r, c, a) ||
                insideTriangle(p, a, b, c);

    }

    /**
     * Checks if circle intersects an edge
     * @param p Center of the circle
     * @param r Radius of the circle
     * @param a Point A of the edge
     * @param b Point B of the edge
     * @return True if the circle intersects the edge
     */
    public static boolean intersects(Vector2f p, int r, Vector2f a, Vector2f b) {
        Vector2f ab = Vector2f(a, b);
        Vector2f ap = Vector2f(a, p);
        float dot = dot(ap, ab);

        boolean betweenAB = dot >= 0 && dot <= dot(ab, ab);
        boolean closeEnough = distance(p, a, b) <= r;
        boolean closeAorB = p.distance(a) <= r || p.distance(b) <= r;
        return (betweenAB && closeEnough) || closeAorB;
    }

    /**
     * Checks if two edges intersect
     * @param a End of first edge
     * @param b Other end of first edge
     * @param c End of second edge
     * @param d Other end of second edge
     * @return True if both edges intersect
     */
    public static boolean intersects(Vector2f a, Vector2f b, Vector2f c, Vector2f d) {
        Vector2f ab = Vector2f(a, b);
        Vector2f intersection = intersection(a, ab, c, d);

        if (intersection == null)
            return false;

        Vector2f ap = Vector2f(a, intersection);
        float dot = dot(ap, ab) / dot(ab, ab);

        return 0 <= dot && dot <= 1;
    }

    /**
     * Returns the distance between point P and edge AB
     * @param p Point P
     * @param a End A of segment
     * @param b End B of segment
     * @return Distance between P and segment AB
     */
    public static float distance(Vector2f p, Vector2f a, Vector2f b) {
        Vector2f ab = Vector2f(a, b);
        Vector2f ap = Vector2f(a, p);

        float projection = dot(ap, ab) / ab.length();
        Vector2f v = ab.normalise().scale(projection);
        Vector2f dis = ap.sub(v);
        return dis.length();
    }

    /**
     * Checks if a point is inside a rectangle
     * @param p Point to check
     * @param a Corner A of the rectangle
     * @param b Corner B of the rectangle
     * @param c Corner C of the rectangle
     * @param d Corner D of the rectangle
     * @return True if the point is inside the rectangle
     */
    public static boolean insideRectangle(Vector2f p, Vector2f a, Vector2f b, Vector2f c, Vector2f d) {
        Vector2f ab = Vector2f(a, b);
        Vector2f ad = Vector2f(a, d);
        Vector2f ap = Vector2f(a, p);
        float dot1 = dot(ap, ab);
        float dot2 = dot(ap, ad);
        return 0 <= dot1 && dot1 <= dot(ab, ab) &&
                0 <= dot2 && dot2 <= dot(ad, ad);
    }

    /**
     * Checks if a point is inside a triangle
     * @param p Point
     * @param a Corner A of the triangle
     * @param b Corner B of the triangle
     * @param c Corner C of the triangle
     * @return True if P is inside triangle ABC, false otherwise
     */
    public static boolean insideTriangle(Vector2f p, Vector2f a, Vector2f b, Vector2f c) {
        Vector2f ap = Vector2f(a, p);
        Vector2f bp = Vector2f(b, p);
        Vector2f cp = Vector2f(c, p);
        Vector2f ab = Vector2f(a, b);
        Vector2f bc = Vector2f(b, c);
        Vector2f ca = Vector2f(c, a);

        float dotA = dot(ap, ab) / dot(ab, ab);
        float dotB = dot(bp, bc) / dot(bc, bc);
        float dotC = dot(cp, ca) / dot(ca, ca);

        return 0 <= dotA && dotA <= 1 &&
                0 <= dotB && dotB <= 1 &&
                0 <= dotC && dotC <= 1;
    }

    /**
     * Calculates ray - edge intersection
     * @param p Position of the moving point
     * @param v Velocity vector of the moving point
     * @param a End A of the edge
     * @param b End B of the edge
     * @return Point of impact, or null if there is no impact
     */
    public static Vector2f intersection(Vector2f p, Vector2f v, Vector2f a, Vector2f b) {
        /*Vector2f impact = null;
        Vector2f ap = Vector2f(a, p);
        Vector2f ab = Vector2f(a, b);
        float s = dot(ap, ab) / dot(ab, ab);
        if (s >= 0 && s <= 1)
            impact = a.copy().add(ab.scale(s));
        return impact;*/

        Vector2f ap = Vector2f(a, p);
        Vector2f ab = Vector2f(a, b);
        Vector2f n = ab.getPerpendicular().normalise();
        float t = -1 * (dot(ap, n)) / (dot(v.getNormal(), n));

        if (t < 0)
            return null;

        Vector2f impact = p.copy().add(v.getNormal().scale(t));
        float dot = dot(Vector2f(a, impact), ab) / dot(ab, ab);
        if (dot >= 0 && dot <= 1)
            return impact;
        else
            return null;
    }

    /**
     * Given a moving point inside a rectangle (aligned with the axes) and an edge inside that triangle
     * @param p Position of the moving point
     * @param v Velocity vector of the moving point
     * @param c1 Upper left corner of the rectangle
     * @param c2 Lower right corner of the rectangle
     * @param a End A of the edge
     * @param b End B of the edge
     * @return [x, y, d] where (x,y) is the point of impact and d is the distance traveled until impact
     */
    public static float[] predictImpact(Vector2f p, Vector2f v, Vector2f c1, Vector2f c2, Vector2f a, Vector2f b) {
        // create copies of the original objects
        p = p.copy();
        v = v.copy();

        // two other corners of the rectangle
        Vector2f c12 = new Vector2f(c2.x, c1.y); // upper right
        Vector2f c21 = new Vector2f(c1.x, c2.y); // lower left

        Vector2f ab = Vector2f(a, b); // ab edge vector

        Vector2f impactPos = null; // position of the predicted impact
        float travelDis = 0; // distance traveled by p before the impact

        boolean lastWasUp = false;
        while (impactPos == null) {
            // check if impact with desired edge AB
            Vector2f aux = intersection(p, v, a, b);
            if (aux != null) {
                travelDis += Vector2f(p, aux).length();
                impactPos = aux;
                continue;
            }
            // check impact with upper edge
            aux = intersection(p, v, c1, c12);
            if (aux != null && !lastWasUp) {
                lastWasUp = true;
                travelDis += Vector2f(p, aux).length();
                p = aux;
                v.y *= -1;
                continue;
            }
            lastWasUp = false;
            // check impact with lower edge
            aux = intersection(p, v, c2, c21);
            if (aux != null) {
                travelDis += Vector2f(p, aux).length();
                p = aux;
                v.y *= -1;
                continue;
            }
            // check impact with left edge
            aux = intersection(p, v, c21, c1);
            if (aux != null) {
                travelDis += Vector2f(p, aux).length();
                p = aux;
                v.x *= -1;
                continue;
            }
            // check impact with right edge
            aux = intersection(p, v, c12, c2);
            if (aux != null) {
                travelDis += Vector2f(p, aux).length();
                p = aux;
                v.x *= -1;
                continue;
            }
        }

        return new float[] {impactPos.x, impactPos.y, travelDis};
    }

    public static void safeZone(Vector2f p, Vector2f c1, Vector2f c2) {
        p.set(
                Math.max(c1.x+1, Math.min(c2.x, p.x)),
                Math.max(c1.y+1, Math.min(c2.y, p.y))
        );
    }
}