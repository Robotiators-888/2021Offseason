package frc.robot;

public class RobotMath {

    public static double modAngleRadians(double angle) {
        if (angle < 0) {
            angle += 2 * Math.PI;
        }
        angle %= 2 * Math.PI;
        return angle;
    }

    public static double modAngleDegrees(double angle) {
        if (angle < 0) {
            angle += 720;
        }
        angle %= 360;
        return angle;
    }
}