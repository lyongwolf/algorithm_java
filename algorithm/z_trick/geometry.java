package algorithm.z_trick;

/**
* 二维点
*/
record Point(double x, double y) {
    static int cmpx(Point p1, Point p2) { return p1.x < p2.x ? -1 : p1.x > p2.x ? 1 : Double.compare(p1.y, p2.y); }
    static int cmpy(Point p1, Point p2) { return p1.y < p2.y ? -1 : p1.y > p2.y ? 1 : Double.compare(p1.x, p2.x); }
    static double Euclidean(Point p1, Point p2) { return Math.sqrt((p1.x - p2.x) * (p1.x - p2.x) + (p1.y - p2.y) * (p1.y - p2.y)); }
    static double Manhattan(Point p1, Point p2) { return Math.abs(p1.x - p2.x) + Math.abs(p1.y - p2.y); }
}


/**
* 二维向量
*/
record Vec(long x, long y) implements Comparable<Vec> {
    public int compareTo(Vec o) { return x < o.x ? -1 : x > o.x ? 1 : Long.compare(y, o.y); }
    Vec sub(Vec o) { return new Vec(x - o.x, y - o.y); }
    long dot(Vec o) { return x * o.x + y * o.y; }
    double det(Vec o) { return (double) x * o.y - (double) y * o.x; }
}