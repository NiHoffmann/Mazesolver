import lejos.nxt.Button;
import lejos.nxt.LCD;
import lejos.nxt.SensorPort;
import lejos.nxt.addon.CompassHTSensor;

public class Compass {
    CompassHTSensor compass;

    public Compass(SensorPort port){
        compass = new CompassHTSensor(port);
    }

    public float getOrientation(){
        return compass.getDegreesCartesian();
    }

    public void calibrate() throws InterruptedException {
        LCD.clear();
        System.out.println("Starting compass calibration");
        LCD.drawString("Starting compass calibration", 0,0);
        Button.waitForAnyPress();
        compass.startCalibration();
        main.driveController.rotate(720, 45);


        compass.stopCalibration();

        LCD.clear();
        System.out.println("Place on starting Node");
        LCD.drawString("Place on starting Node", 0,0);
        Button.waitForAnyPress();
        Thread.sleep(1000);
        compass.resetCartesianZero();
    }

}
