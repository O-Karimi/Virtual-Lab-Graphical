package Logic.Systems.Environment.WallSystem;

import Logic.Utils.NDirection;
import Logic.Utils.Point;

public class Wall {
    private NDirection normalDirection;
    private Point referencePoint;

//    public record Point(double x, double y){}
//    public record NDirection(double nx, double ny){}
    Wall (Point point, NDirection n){
        this.referencePoint = point;
        this.normalDirection = n;
    }

    public NDirection getNormalDirection() {
        return normalDirection;
    }

    public void setNormalDirection(NDirection normalDirection) {
        this.normalDirection = normalDirection;
    }

    public Point getReferencePoint() {
        return referencePoint;
    }

    public void setReferencePoint(Point referencePoint) {
        this.referencePoint = referencePoint;
    }
}
