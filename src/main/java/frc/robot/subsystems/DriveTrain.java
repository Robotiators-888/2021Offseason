package frc.robot;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;

import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;

public class Robot {
  private DifferentialDrive m_myRobot;
  private Joystick m_leftStick;
  private Joystick m_rightStick;
  private static int leftDeviceID = 1; 
  private static int rightDeviceID = 2;
  private CANSparkMax m_leftMotor;
  private CANSparkMax m_rightMotor;

  @override
  public void robotInit(){
      m_leftMotor = new CANSparkMax(leftDeviceID, MotorType.kbrushless);
      m_rightMotor = new CANSparkMax(rightDeviceID, MotorType.kbrushless);


        m_myRobot = new DifferentialDrive(m_leftMotor, m_rightMotor);

        m_leftStick = new Joystick(0);
        m_rightStick = new Joystick(1);
    }
    @override
    public void teleopPeriodic(){
        m_myRobot.tankDrive(m_leftStick.getY(), m_rightStick.getY());
    }
}
