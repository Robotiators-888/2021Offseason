package frc.robot.subsystems;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.networktables.NetworkTable;
import edu.wpi.first.networktables.NetworkTableEntry;
import edu.wpi.first.networktables.NetworkTableInstance;
import edu.wpi.first.wpilibj2.command.SubsystemBase;


public class Limelight extends SubsystemBase {
    
    private NetworkTable m_table;
   

    public Limelight(){
        
    NetworkTable table = NetworkTableInstance.getDefault().getTable("limelight");
    NetworkTableEntry tx = table.getEntry("tx");
    NetworkTableEntry ty = table.getEntry("ty");
    NetworkTableEntry ta = table.getEntry("ta");
    NetworkTableEntry tv = table.getEntry("tv");

    //reads the values from the Limelight
    double x = tx.getDouble(0.0);
    double y = ty.getDouble(0.0);
    double area = ta.getDouble(0.0);
    double target = tv.getDouble(0.0);

    //Upadtes to Smartdash

    SmartDashboard.putNumber("LimelightX", x);
    SmartDashboard.putNumber("LimelightY", y);
    SmartDashboard.putNumber("LimelightArea", area);
    SmartDashboard.putNumber("LimelightTarget", target);
  }
  
  //Checks to see if limelight sees target 0 = false and 1 = true
  public boolean getIsTargetFound() {
    NetworkTableEntry tv = m_table.getEntry("tv");
    double v = tv.getDouble(0);
    if (v == 0.0f){
        return false;
    }else {
        return true;
    }
   }

   // Checks to see how far off the crosshair is to the target on the x
   public double getdegRotationToTarget() {
    NetworkTableEntry tx = m_table.getEntry("tx");
    double x = tx.getDouble(0.0);
    return x;
    }

   // Checks to see how far off the crosshair is to the target on the y
    public double getdegVerticalToTarget() {
        NetworkTableEntry ty = m_table.getEntry("ty");
        double y = ty.getDouble(0.0);
        return y;
    }
 
    // Checks to see if the target is 100% in the image 
    public double getTargetArea() {
        NetworkTableEntry ta = m_table.getEntry("ta");
        double a = ta.getDouble(0.0);
        return a;
    }
  
    // Checks to see if the target or camera is askewed 
    public double getSkew_Rotation() {
     NetworkTableEntry ts = m_table.getEntry("ts");
        double s = ts.getDouble(0.0);
        return s;
    }



























  
}