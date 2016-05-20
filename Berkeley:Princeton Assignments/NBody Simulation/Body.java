public class Body {
    private Vector p; // position vector 
    private Vector v; // velocity vector 
    private double m; // mass
    private String imgName; // filename of particle's img – animate w/ StdDraw

    public Body(Vector p, Vector v, double m, String imgName) { 
        this.p = p;
        this.v = v;
        this.m = m;
        this.imgName = imgName;
    }
    
    // updates pos and vel vectors for a given Δt
    public void move(Vector f, double Δt) {
        Vector a = f.times(1/m); 
        v = v.plus(a.times(Δt));
        p = p.plus(v.times(Δt));
    }
    
    // computes net force vector on this body from another
    // using newton's law of universal gravitation, among other formulas
    public Vector forceFrom(Body b) {
        Body a = this;
        Vector Δ = a.p.minus(b.p); // vector w/ Δx and Δy coords
        double dist = Δ.getMag();
        double F = -(6.674e-11*a.m*b.m)/(dist*dist); // law of univ gravitation formula
        
        // Δ.x is Δx/dist; times by F to = Fcos(θ)
        // Δ.y is Δy/dist; times by F to = Fsin(θ)
        return Δ.getDir().times(F);
    }
    
    public void draw() {
        StdDraw.picture(p.getCoord('x'), p.getCoord('y'), imgName);
    }

    public void print() {
        StdOut.printf("%11.4e %11.4e %11.4e %11.4e %11.4e %12s\n",
                   p.getCoord('x'), p.getCoord('y'), v.getCoord('x'),
                   v.getCoord('y'), m, imgName);
    }
}