package frc.robot.subsystems;

import com.revrobotics.CANSparkMax;
import com.revrobotics.CANPIDController;
import com.revrobotics.CANSparkMaxLowLevel;

import edu.wpi.first.wpilibj.SpeedControllerGroup;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;

public class DriveSubsystem extends SubsystemBase {
  //create motor controller objects
  private CANSparkMax leftPrimary = new CANSparkMax(Constants.ID_LEFT_PRIMARY, CANSparkMaxLowLevel.MotorType.kBrushless);
  private CANSparkMax leftSecondary = new CANSparkMax (Constants.ID_LEFT_SECONDARY, CANSparkMaxLowLevel.MotorType.kBrushless);
  private CANSparkMax rightPrimary = new CANSparkMax(Constants.ID_RIGHT_PRIMARY, CANSparkMaxLowLevel.MotorType.kBrushless);
  private CANSparkMax rightSecondary = new CANSparkMax(Constants.ID_RIGHT_SECONDARY, CANSparkMaxLowLevel.MotorType.kBrushless);

  //create a speed controller group for each side
  private SpeedControllerGroup groupLeft = new SpeedControllerGroup(leftPrimary, leftSecondary);
  private SpeedControllerGroup groupRight = new SpeedControllerGroup(rightPrimary, rightSecondary);

  //create a drive train group with the speed controller groups
  private DifferentialDrive driveTrain = new DifferentialDrive(groupLeft, groupRight);

  public DriveSubsystem() {}

  public void periodic() {}


  /**
  * Sets the drivetrain to inverted
  * @param invert the invert state of the drivetrain. True is inverted, False is not inverted.
  */
  public void invertDrive(boolean invert){
    leftPrimary.setInverted(invert);
    rightPrimary.setInverted(invert);
    leftSecondary.setInverted(invert);
    rightSecondary.setInverted(invert);
  }

  /**
  * Sets speed of the motors in the drivetrain
  * @param leftSpeed Speed of the left drivetrain
  * @param rightSpeed Speed of right drivetrain
  * @param Speed set a precentage of max speed the robot can use, if not provided will default to full power
  */
  public void setMotors(double leftSpeed, double rightSpeed, double Speed) {
    driveTrain.tankDrive(leftSpeed * Speed, rightSpeed * Speed);
  }

  public void setMotors(double leftSpeed, double rightSpeed) {
    driveTrain.tankDrive(leftSpeed, rightSpeed);
  }
}