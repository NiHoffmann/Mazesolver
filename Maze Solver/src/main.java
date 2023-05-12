import lejos.nxt.SensorPort;

public class main
{
    static DriveController driveController;
    static ColorSensor colorSensor;
    static Compass compass;
    static Node currentNode = null;

    public static void initialize() throws InterruptedException {
        driveController = new DriveController(2.85, 8, 90);
        colorSensor = new ColorSensor(SensorPort.S1);
        compass = new Compass(SensorPort.S4);

        colorSensor.calibrate();

        compass.calibrate();
    }

    public static void main(String [] args) throws Exception
    {
        initialize();
        Mapping.driveOnTopOfNode();
        Map.startingNode = new Node(false);
        Mapping.map(Map.startingNode);
        Pathing.findPath(Map.startingNode, 0);
        Pathing.drivePath();
    }
}