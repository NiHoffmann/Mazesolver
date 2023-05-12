import lejos.nxt.Motor;

import java.awt.*;

class DriveController {
    double radius;
    double axleRadius;
    boolean interrupted = false;
    double altRot = 1;

    /**
     *
     * @param radius Durchmesser der Reifen in cm
     *
     */
    public DriveController(double radius, double axleRadius, int speed){
        this.radius = radius;
        this.axleRadius = axleRadius;
        setSpeed(speed);
    }

    public void setSpeed(int speed){
        Motor.A.setSpeed(speed);
        Motor.B.setSpeed(speed);
    }

    public void drive(){
        Motor.A.forward();
        Motor.B.forward();
    }

    public void stop(){
        Motor.A.stop();
        Motor.B.stop();
    }

    public void searchForPath() {
        rotate(altRot, 90);
        altRot = Math.signum(altRot)  * (Math.abs(altRot) + 3);
        altRot *= -1;
    }

    public void resetAlternateRotate(){
        altRot = 1;
    }

    public void turnToDegree(float goalOrientation,int speed) throws InterruptedException {
        while(main.compass.getOrientation() != goalOrientation){
            double turnBy1 = goalOrientation - main.compass.getOrientation();
            double turnBy2 = main.compass.getOrientation() - goalOrientation;
            if(Math.abs(turnBy1) < Math.abs(turnBy2)){
                rotate(turnBy1, speed);
            }else {
                rotate(turnBy2, speed);
            }
        }
    }

    /**
     *
     * @param distance Distanz in cm
     */
    public void driveDistance(boolean forward,double distance, int speed, boolean await) throws InterruptedException {

        double distance360Degree = circularSection(360, radius);
        double distancePerDegree = (distance360Degree/360);
        double distancePerSecond = distancePerDegree * speed;

        double runTime = ((distance/distancePerSecond)*1e+9);

        Motor.A.setSpeed(speed);
        Motor.B.setSpeed(speed);

        long start = System.nanoTime();
        if(forward) {
            Motor.A.forward();
            Motor.B.forward();
        }else{
            Motor.A.backward();
            Motor.B.backward();
        }
        Thread thread = new Thread(() -> {
            while((System.nanoTime() - start) < runTime && !interrupted){}
            stop();
        });

        thread.start();
        if(await)
            thread.join();
    }

    public void rotate(double angle, int speed){
        double circDist = circularSection(Math.abs(angle), axleRadius);

        double distance360Degree = circularSection(360, radius);
        double distancePerDegree = (distance360Degree/360);
        double distancePerSecond = distancePerDegree * speed;

        double runTime = ((circDist/distancePerSecond)*1e+9);

        Motor.A.setSpeed(speed);
        Motor.B.setSpeed(speed);

        long start = System.nanoTime();
        if(angle < 0) {
            Motor.A.forward();
            Motor.B.backward();
        }else{
            Motor.A.backward();
            Motor.B.forward();
        }

        while((System.nanoTime() - start) < runTime){}

        stop();
    }

    public double circularSection(double alpha, double r){ return (alpha/360) * 2 * r * Math.PI; }

}
