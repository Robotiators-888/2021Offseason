package frc.robot;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;

import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;

public class Robot {
  private DifferentialDrive m_myRobot;
  private Joystick m_leftStickPrimary;
  private Joystick m_leftStickSecondary;
  private Joystick m_rightStickPrimary;
  private Joystick m_rightStickSecondary;
  private static int leftDeviceIDPrimary = 1; 
  private static int leftDeviceIDSecondary = 2;
  private static int rightDeviceIDPrimary = 3;
  private static int rightDeviceIDSecondary = 4; 
  private CANSparkMax m_leftMotorPrimary;
  private CANSparkMax m_leftMotorSecondary;
  private CANSparkMax m_rightMotorPrimary;
  private CANSparkMax m_rightMotorSecondary;
/* */
  

@override
  public void robotInit(){
      m_leftMotorPrimary = new CANSparkMax(leftDeviceIDPrimary, MotorType.kbrushless);
      m_leftMotorSecondary = new CANSparkMax(leftDeviceIDSecondary, MotorType.kbrushless);
      m_rightMotorPrimary = new CANSparkMax(rightDeviceIDPrimary, MotorType.kbrushless);
      m_rightMotorSecondary = new CANSparkMax(rightDeviceIDSecondary, MotorType.kbrushless);
    /* */

        m_myRobot = new DifferentialDrive(m_leftMotorPrimary, m_leftMotorSecondary, m_rightMotorPrimary,m_rightMotorSecondary);

        m_leftStickPrimary = new Joystick(1);
        m_leftStickSecondary = Joystick(1) * -1;
        m_rightStickPrimary = new Joystick(2);
        m_rightStickSecondary = Joystick(2) * -1;
    
    }
   
    @override
    public void teleopPeriodic(){
        m_myRobot.tankDrive(m_leftStick.getY(), m_rightStick.getY());
    }
}
