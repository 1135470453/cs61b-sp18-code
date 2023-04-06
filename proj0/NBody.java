public class NBody {
    //return a double corresponding to the radius of the universe in that file
    public static double readRadius(String fileAddress){
        In in = new In(fileAddress);
        in.readInt();
        return in.readDouble();
    }

    //return an array of Planets corresponding to the planets in the file
    public static Planet[] readPlanets(String fileAddress){
        In in = new In(fileAddress);
        int numOfPlanet = in.readInt();
        in.readDouble();
        Planet[] planets = new Planet[numOfPlanet];
        for(int i=0;i<numOfPlanet;i++){
            double xxPos = in.readDouble();
            double yyPos = in.readDouble();
            double xxVel = in.readDouble();
            double yyVel = in.readDouble();
            double mass = in.readDouble();
            String imgFileName = in.readString();
            Planet planet = new Planet(xxPos,yyPos,xxVel,yyVel,mass,imgFileName);
            planets[i] = planet;
        }
        return planets;
    }


    public static void main(String[] args) {
        //Collecting All Needed Input
        if(args.length < 2){
            System.out.println("args error");
            System.exit(0);
        }
        double T =  Double.parseDouble(args[0]);
        double dt = Double.parseDouble(args[1]);
        String filename = args[2];
        Planet[] planets = readPlanets(filename);
        double radius = readRadius(filename);
        //Drawing the Background
        String backgroundImg = "/images/starfield.jpg";
        StdDraw.setScale(-radius,radius);
        //graphics technique to prevent flickering in the animation
        StdDraw.enableDoubleBuffering();
        StdDraw.clear();
        StdDraw.picture(0,0,backgroundImg);
        for(Planet planet : planets){
            planet.draw();
        }
        StdDraw.show();
        double time = 0;
        while(time != T){
            double[] xForces = new double[planets.length];
            double[] yForces = new double[planets.length];
            for(int i=0;i<planets.length;i++){
                xForces[i] = planets[i].calcNetForceExertedByX(planets);
                yForces[i] = planets[i].calcNetForceExertedByY(planets);
            }
            for(int i=0;i<planets.length;i++){
                planets[i].update(dt,xForces[i],yForces[i]);
            }
            StdDraw.clear();
            StdDraw.picture(0,0,backgroundImg);
            for(Planet planet : planets){
                planet.draw();
            }
            StdDraw.show();
            StdDraw.pause(10);
            time += dt;
        }
        StdOut.printf("%d\n", planets.length);
        StdOut.printf("%.2e\n", radius);
        for (int i = 0; i < planets.length; i++) {
            StdOut.printf("%11.4e %11.4e %11.4e %11.4e %11.4e %12s\n",
                    planets[i].xxPos, planets[i].yyPos, planets[i].xxVel,
                    planets[i].yyVel, planets[i].mass, planets[i].imgFileName);
        }
    }
}
