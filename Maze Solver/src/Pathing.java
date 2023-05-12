import javax.swing.plaf.basic.BasicInternalFrameTitlePane;

public class Pathing {
    public static Node[] path;

    public static void driveOnTopOfPath(Path path){
        //triangle stuff here
    }

    public static void followPath(Path path) throws InterruptedException {
        boolean pathing = true;
        main.driveController.turnToDegree(path.orientation, 90);
        while(pathing){
            while(main.colorSensor.detectPath()){
                main.driveController.drive();
                main.driveController.resetAlternateRotate();
            }
            main.driveController.stop();
            if(main.colorSensor.detectNode()){
                System.out.println("node Detected");
                pathing = false;
            }else if(main.colorSensor.detectFinish()){
                System.out.println("finish Color Detected");
                main.currentNode.isFinish = true;
                pathing = false;
            }else{
                main.driveController.searchForPath();
            }
        }
        Mapping.driveOnTopOfNode();
    }

    public static boolean findPath(Node node, int i){
        if(node.isFinish){
            System.out.println("Path found!");
            path = new Node[i+1];
            path[i] = node;
            return true;
        }

        if(node.top != null){
            if(findPath(node.top,i+1)){
                path[i] = node;
                return true;
            }
        }
        if(node.left != null){
            if(findPath(node.left,i+1)){
                path[i] = node;
                return true;
            }
        }
        if(node.right != null){
            if(findPath(node.right,i+1)){
                path[i] = node;
                return true;
            }
        }
        if(node.bottom != null){
            if(findPath(node.bottom,i+1)){
                path[i] = node;
                return true;
            }
        }

        return false;
    }

    public static void drivePath() throws InterruptedException {
        System.out.println("Driving Path!");
        for(int i=1;i< path.length;i++){
            if(path[i] == path[i-1].left){
                Pathing.followPath(new Path(90));
            }
            if(path[i] == path[i-1].right){
                Pathing.followPath(new Path(180));
            }
            if(path[i] == path[i-1].bottom){
                Pathing.followPath(new Path(270));
            }
            if(path[i] == path[i-1].top){
                Pathing.followPath(new Path(0));
            }
        }
        System.out.println("Finish Node Reached");
    }
}
