import lejos.nxt.Button;
import lejos.nxt.SensorPort;
import lejos.nxt.addon.ColorHTSensor;
import lejos.nxt.LCD;

public class ColorSensor {

    ColorHTSensor cs ;
    int tolerance = 110 ;

    public ColorSensor(SensorPort port) throws InterruptedException {
        cs = new ColorHTSensor(port);
    }

    public RgbVal getColor() throws InterruptedException {
        RgbVal val = new RgbVal(cs.getRGBRaw(0), cs.getRGBRaw(1), cs.getRGBRaw(2));
        Thread.sleep(200);
        return val;
    }

    public void calibrate() throws InterruptedException {
        LCD.clear();
        System.out.println("Callibrating Black Level");
        LCD.drawString("Callibrating Black Level", 0, 0);
        Button.waitForAnyPress();
        Thread.sleep(1000);
        cs.initBlackLevel();


        LCD.clear();
        System.out.println("Callibrating White Level");
        LCD.drawString("Callibrating White Level", 0,0);
        Button.waitForAnyPress();
        Thread.sleep(1000);
        cs.initWhiteBalance();

        LCD.clear();
        System.out.println("Callibrating Path Color");
        LCD.drawString("Callibrating Path Color", 0,0);
        Button.waitForAnyPress();
        Thread.sleep(1000);
        Mapping.pathColor = getColor();

        LCD.clear();
        System.out.println("Callibrating Node Color");
        LCD.drawString("Callibrating Node Color", 0,0);
        Button.waitForAnyPress();
        Thread.sleep(1000);
        Mapping.nodeColor = getColor();

        LCD.clear();
        System.out.println("Callibrating Finish Node Color");
        LCD.drawString("Callibrating Finish Node Color", 0,0);
        Button.waitForAnyPress();
        Thread.sleep(1000);
        Mapping.finishColor = getColor();
    }

    public boolean detectNode() throws InterruptedException {
        return Mapping.nodeColor.approx(getColor() , tolerance);
    }

    public boolean detectPath() throws InterruptedException {
        return Mapping.pathColor.approx(getColor() , tolerance);
    }

    public boolean detectFinish() throws InterruptedException {
        return Mapping.finishColor.approx(getColor() , tolerance);  
    }

}
