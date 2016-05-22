public class Vector {
    private double x;
    private double y;
    
    public Vector(double x, double y) {
        this.x = x;
        this.y = y;
    }
    
    public Vector plus(Vector v) { 
        return new Vector(x+v.x,y+v.y);
    }
    
    public Vector minus(Vector v) { 
        return this.plus(v.times(-1));
   }
    
    public Vector times(double d) { 
        return new Vector(x*d,y*d);
    }
    
    public double dot(Vector v) { 
        return (this.x*v.x) + (this.y*v.y);
    }
    
    public Vector getDir() {
        return new Vector(x/getMag(),y/getMag());
    }            
    
    public double getMag() { 
        return Math.sqrt(this.dot(this));
    }
    
    public double getCoord(char c) { 
        switch(c) {
            case 'x': return x;
            case 'y': return y;
            default: return 0;
        }  
    }
}
