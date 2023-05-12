import java.awt.*;

public class Mapping {

    public static RgbVal pathColor;
    public static RgbVal nodeColor;
    public static RgbVal finishColor;

    public static void driveOnTopOfNode() throws InterruptedException {
        while(main.colorSensor.detectNode())
        {
            main.driveController.drive();
        }
        main.driveController.stop();
        main.driveController.driveDistance(true,3, 45, true);
    }

    public static void map(Node node) throws InterruptedException {
        if(node.isFinish){
            return;
        }

        System.out.println("seaching top");
        if(node.top == null && pathTop()){
            System.out.println("Top Path detected"+main.compass.getOrientation());
            System.out.println(main.compass.getOrientation());
            Node nT = new Node(false);
            node.top = nT;
            nT.bottom = node;
            main.currentNode = nT;
            Pathing.followPath(new Path(0));
            map(nT);
            Pathing.followPath(new Path(180));
        }
        System.out.println("seaching right");
        if(node.right == null && pathRight()){
            System.out.println("Right Path detected"+main.compass.getOrientation());
            System.out.println(main.compass.getOrientation());
            Node nR = new Node(false);
            node.right = nR;
            nR.left = node;
            main.currentNode = nR;
            Pathing.followPath(new Path(270));
            map(nR);
            Pathing.followPath(new Path(90));
        }
        System.out.println("seaching bottom");
        if(node.bottom == null && pathBottom()){
            System.out.println("Bottom Path detected"+main.compass.getOrientation());
            System.out.println(main.compass.getOrientation());
            Node nB = new Node(false);
            node.bottom = nB;
            nB.top = node;
            main.currentNode = nB;
            Pathing.followPath(new Path(180));
            map(nB);
            Pathing.followPath(new Path(0));
        }
        System.out.println("seaching left");
        if((node.left == null && pathLeft())){
            System.out.println("Left Path detected"+main.compass.getOrientation());
            System.out.println(main.compass.getOrientation());
            Node nL = new Node(false);
            node.left = nL;
            nL.right = node;
            main.currentNode = nL;
            Pathing.followPath(new Path(90));
            map(nL);
            Pathing.followPath(new Path(270));
        }
    }

    public static boolean pathTop() throws InterruptedException {
        main.driveController.turnToDegree(315, 90);
        for(int i =0; i<9;i++) {
            if(main.colorSensor.detectPath()){
                return true;
            }
            main.driveController.rotate(-10,45);
        }

        return false;
    }

    public static boolean pathLeft() throws InterruptedException {
        main.driveController.turnToDegree(45, 90);
        for(int i =0; i<9;i++) {
            if(main.colorSensor.detectPath()){
                return true;
            }
            main.driveController.rotate(-10,45);
        }

        return false;
    }

    public static boolean pathBottom() throws InterruptedException {
        main.driveController.turnToDegree(135, 90);
        for(int i =0; i<9;i++) {
            if(main.colorSensor.detectPath()){
                return true;
            }
            main.driveController.rotate(-10,45);
        }

        return false;
    }


    public static boolean pathRight() throws InterruptedException {
        main.driveController.turnToDegree(225, 90);
        for(int i =0; i<9;i++) {
            if(main.colorSensor.detectPath()){
                return true;
            }
            main.driveController.rotate(-10,45);
        }

        return false;
    }

}
