public class Galaxy { 
    private int N;
    private double radius;
    private double t;
    private Body[] orbs;

    public Galaxy() { 
        N = StdIn.readInt();
        radius = StdIn.readDouble();
        orbs = new Body[N];
        
        for (int i=0; i<N; i++) {
            Vector p = new Vector(StdIn.readDouble(), StdIn.readDouble());
            Vector v = new Vector(StdIn.readDouble(), StdIn.readDouble());
            
            double mass = StdIn.readDouble();
            String imgName = StdIn.readString();
            
            orbs[i] = new Body(p, v, mass, imgName);
        }

        t = 0.0;
        StdDraw.setXscale(-radius, +radius); // ensures that everything's
        StdDraw.setYscale(-radius, +radius); // displayed in scale
    }
    
    public void elapseTime(double Δt) {
        t+=Δt;
        Vector[] f = new Vector[N];
        // nested loops below make added force vector 
        // for each planet that's later filled in after 
        // calculating the force from every planet around it
        for (int i=0; i<N; i++) {
            f[i] = new Vector(0,0);
            for(int j=0; j<N; j++) {
                if (i!=j)
                    f[i] = f[i].plus(orbs[i].forceFrom(orbs[j]));
            }
        }

        for (int i=0; i<N; i++)
            orbs[i].move(f[i],Δt);
    }
    
    public void draw() {
        StdDraw.picture(0,0,"starfield.jpg");    
        for (Body b : orbs)
            b.draw();    
    }

    public void print() {
        StdOut.printf("%d\n", N);
        StdOut.printf("%.2e\n", radius);
        for (Body b : orbs) 
            b.print();
    }
    
    public static void main(String[] args) {
        Galaxy g = new Galaxy();
        double T = Double.parseDouble(args[0]);
        double Δt = Double.parseDouble(args[1]);
        
        StdAudio.play("2001.mid");
        while (g.t < T) {
            StdDraw.clear();
            g.elapseTime(Δt);
            g.draw();
            StdDraw.show(25); // 40 fps
        }

        g.print();
    }
}
