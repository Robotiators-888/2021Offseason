package frc.robot;

import edu.wpi.first.wpilibj.DoubleSolenoid;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;
import com.ctre.phoenix.motorcontrol.ControlMode;

public class Intake {

    protected DoubleSolenoid intakeFlipper;
    protected TalonSRX intakeMotor;

    protected int multi;
    protected OI oi;

    protected boolean flipButtonLast = false;
    protected boolean flipButton = false;

    public Intake(OI oi) {
        intakeFlipper = new DoubleSolenoid(RobotMap.PCM, RobotMap.DS_FORWARD_CHANNEL, RobotMap.DS_REVERSE_CHANNEL);
        intakeMotor = new TalonSRX(RobotMap.INTAKE_CANID);

        intakeMotor.setInverted(true);

        this.oi = oi;
    }

    public void intakeTeleopPeriodic() {
        if (oi.getLeftStickButton(RobotMap.JOYSTICK_TRIGGER)) {
            if (oi.getLeftStickButton(RobotMap.JOYSTICK_LEFT_BUTTON)) {
                intakeReverse();
            }
            else {
                intakeIn();
            }
        }
        else {
            intakeStop();
        }

        flipButton = oi.getLeftStickButton(RobotMap.JOYSTICK_CENTER_BUTTON);

        flipIntake(flipButton && !flipButtonLast);

        flipButtonLast = flipButton;

    }

    public void flipIntake(boolean flip) {
        if (flip) {
            if (intakeFlipper.get().equals(DoubleSolenoid.Value.kReverse))
                intakeFlipper.set(DoubleSolenoid.Value.kForward);
            else intakeFlipper.set(DoubleSolenoid.Value.kReverse);
        }
    }

    public void intakeIn() {
        intakeMotor.set(ControlMode.PercentOutput, 0.75);
       
    }

    public void intakeReverse() {
        intakeMotor.set(ControlMode.PercentOutput, -0.75);
        
    }

    public void intakeStop() {
        intakeMotor.set(ControlMode.PercentOutput, 0);
      
    }

    public void flipOut() {
        if (intakeFlipper.get().equals(DoubleSolenoid.Value.kReverse))
                intakeFlipper.set(DoubleSolenoid.Value.kForward);
    }

    public void flipIn() {
        if (intakeFlipper.get().equals(DoubleSolenoid.Value.kForward))
                intakeFlipper.set(DoubleSolenoid.Value.kReverse);
    }

}