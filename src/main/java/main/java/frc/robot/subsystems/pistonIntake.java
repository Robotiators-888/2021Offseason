package main.java.frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.can.TalonSRX;

import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.DoubleSolenoid.Value;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class pistonIntake extends SubsystemBase{
  private TalonSRX motorLeft;
 
 private DoubleSolenoid ds;

  public void piston(){
    
  }
  
  public pistonIntake(){
    ds = new DoubleSolenoid(0, 1);
    motorLeft = new TalonSRX(0);
 
  }
  
  public void flipIntake( boolean ranbool) {
      
      if (ranbool) {
        ds.set​(DoubleSolenoid.Value.kForward);
      }

    else {
      ds.set​(DoubleSolenoid.Value.kReverse);
    }
   }
   

  
    @Override
    public void periodic() {
      // TODO Auto-generated method stub
      super.periodic();
    } 
}

