public class Planet{
    public double xxPos;
    public double yyPos;
    public double xxVel;
    public double yyVel;
    public double mass;
    public String imgFileName;

    public Planet(double xP, double yP, double xV, double yV, double m, String img){
        this.xxPos = xP;
        this.yyPos = yP;
        this.xxVel = xV;
        this.yyVel = yV;
        this.mass = m;
        this.imgFileName = img;
    }

    public Planet(Planet p){
        this.xxPos = p.xxPos;
        this.yyPos = p.yyPos;
        this.xxVel = p.xxVel;
        this.yyVel = p.yyVel;
        this.mass = p.mass;
        this.imgFileName = p.imgFileName;
    }


    //return a double equal to the distance between the supplied planet and the planet that is doing the calculation
    public double calcDistance(Planet p){
        double dx = this.xxPos - p.xxPos;
        double dy = this.yyPos - p.yyPos;
        return Math.sqrt(dx * dx + dy * dy);
    }


    //returns a double describing the force exerted on this planet by the given planet
    public double calcForceExertedBy(Planet p){
        double r = calcDistance(p);
        return (0.0000000000667 * this.mass * p.mass) / (r * r);
    }

    //describe the force exerted in the X and Y directions
    public double calcForceExertedByX(Planet p){
        double force = calcForceExertedBy(p);
        double r = calcDistance(p);
        double dx = p.xxPos - this.xxPos;
        return force / r * dx;
    }

    public double calcForceExertedByY(Planet p){
        double force = calcForceExertedBy(p);
        double r = calcDistance(p);
        double dy = p.yyPos - this.yyPos;
        return force / r * dy;
    }


    //calculate the net X and net Y force exerted by all planets in that array upon the current Planet
    public double calcNetForceExertedByX(Planet[] planets){
        double netX = 0;
        for(int i=0;i<planets.length;i++){
            if(!this.equals(planets[i])){
                netX += calcForceExertedByX(planets[i]);
            }
        }
        return netX;
    }

    public double calcNetForceExertedByY(Planet[] planets){
        double netY = 0;
        for(int i=0;i<planets.length;i++){
            if(!this.equals(planets[i])){
                netY += calcForceExertedByY(planets[i]);
            }
        }
        return netY;
    }


    //the resulting change in the planet’s velocity and position in a small period of time
    public void update(double dt,double fX,double fY){
        double aX = fX / this.mass;
        double aY = fY / this.mass;
        this.xxVel = this.xxVel + aX * dt;
        this.yyVel = this.yyVel + aY * dt;
        this.xxPos = this.xxPos + dt * this.xxVel;
        this.yyPos = this.yyPos + dt * this.yyVel;
    }


    //uses the StdDraw API mentioned above to draw the Planet’s image at the Planet’s position
    public void draw(){
        StdDraw.picture(this.xxPos,this.yyPos,"/images/"+this.imgFileName);
    }
}