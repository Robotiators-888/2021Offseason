package frc.robot;

import com.revrobotics.CANSparkMax;
import com.revrobotics.CANPIDController;
import com.revrobotics.CANSparkMaxLowLevel;
import com.revrobotics.CANSparkMax.IdleMode;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 * Interface for the drive train.
 */
public class DriveTrain {

    Navigation nav;

    // Motors
    CANSparkMax frontLeft;
    CANSparkMax rearLeft;
    CANSparkMax frontRight;
    CANSparkMax rearRight;
    CANPIDController frontLeft_PID;
    CANPIDController rearLeft_PID;
    CANPIDController frontRight_PID;
    CANPIDController rearRight_PID;

    double rightEncoderOffset;
    double leftEnccoderOffset;

    boolean brake = false;

    /**
     * Constructor for a drive object.
     */
    public DriveTrain() {

        // instantiate motor controllers with CAN ID and motor type
        frontLeft = new CANSparkMax(RobotMap.MOTOR_FRONT_LEFT,
                CANSparkMaxLowLevel.MotorType.kBrushless);
        rearLeft = new CANSparkMax(RobotMap.MOTOR_REAR_LEFT,
                CANSparkMaxLowLevel.MotorType.kBrushless);
        frontRight = new CANSparkMax(RobotMap.MOTOR_FRONT_RIGHT,
                CANSparkMaxLowLevel.MotorType.kBrushless);
        rearRight = new CANSparkMax(RobotMap.MOTOR_REAR_RIGHT,
                CANSparkMaxLowLevel.MotorType.kBrushless);

        // make rear motors follow front motors
        rearLeft.follow(frontLeft);
        rearRight.follow(frontRight);

        // invert right side
        frontLeft.setInverted(true);
        frontRight.setInverted(false);

        // set motors to coast mode
        frontLeft.setIdleMode(IdleMode.kCoast);
        rearLeft.setIdleMode(IdleMode.kCoast);
        frontRight.setIdleMode(IdleMode.kCoast);
        rearRight.setIdleMode(IdleMode.kCoast);

        frontLeft.setClosedLoopRampRate(RobotMap.RAMP_RATE);
        rearLeft.setClosedLoopRampRate(RobotMap.RAMP_RATE);
        frontRight.setClosedLoopRampRate(RobotMap.RAMP_RATE);
        rearRight.setClosedLoopRampRate(RobotMap.RAMP_RATE);

    }

    /**
     * Sets the speed for the left and right side of the drive train.
     * 
     * @param leftThrust Left side output on a scale of -1.0 t0 1.0.
     * @param rightThrust Right side output on a scale of -1.0 t0 1.0.
     */
    public void move(double leftThrust, double rightThrust) {
        frontLeft.set(leftThrust);
        frontRight.set(rightThrust);
        SmartDashboard.putNumber("Left output", leftThrust);
        SmartDashboard.putNumber("Right output", rightThrust);
    }
    
    /**
     * Gets encoder values for the left and right SparkMAXs.
     */
    public double[] getEncoders() {
        return new double[] {
                -(frontLeft.getEncoder().getPosition() - leftEnccoderOffset),
                -(frontRight.getEncoder().getPosition() - rightEncoderOffset) };
    }

    /**
     * Resets the value of the encoder to zero.
     */
    public void resetEncoderOffset() {
        leftEnccoderOffset = -frontLeft.getEncoder().getPosition();
        rightEncoderOffset = -frontRight.getEncoder().getPosition();
    }

    /**
     * Set motors to brake mode.
     */
    public void brake() {
        if (frontLeft.getIdleMode() == IdleMode.kCoast) {
            frontLeft.setIdleMode(IdleMode.kBrake);
            rearLeft.setIdleMode(IdleMode.kBrake);
            frontRight.setIdleMode(IdleMode.kBrake);
            rearRight.setIdleMode(IdleMode.kBrake);
        }
    }

    /**
     * Set motors to coast mode.
     */
    public void coast() {
        if (frontLeft.getIdleMode() == IdleMode.kBrake) {
            frontLeft.setIdleMode(IdleMode.kCoast);
            rearLeft.setIdleMode(IdleMode.kCoast);
            frontRight.setIdleMode(IdleMode.kCoast);
            rearRight.setIdleMode(IdleMode.kCoast);
        }
    }
}