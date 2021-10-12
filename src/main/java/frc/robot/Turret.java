package frc.robot;

import edu.wpi.first.wpilibj.Counter;
import edu.wpi.first.wpilibj.DigitalInput;
//import edu.wpi.first.wpilibj.Spark;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;
import com.ctre.phoenix.motorcontrol.ControlMode;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class Turret {
    //Spark turretMotor;
    TalonSRX turretMotor;

    DigitalInput leftLimitSwitch;
    DigitalInput rightLimitSwitch;

    Counter encoder;

    OI oi;

    //int lastEncoderCount;
    //boolean complete;

    //final int totalTeeth = 157/2;

    public Turret(OI oi) {
        //turretMotor = new Spark(1);
        turretMotor = new TalonSRX(1);
        turretMotor.setInverted(true);

        encoder = new Counter(new DigitalInput(9));

        leftLimitSwitch = new DigitalInput(5);
        rightLimitSwitch = new DigitalInput(6);

        this.oi = oi;
    }

    public void turretTeleopPeriodic() {

        // if (leftLimitSwitch.get()) {
        //     resetEncoderCounter();
        //     turnTurretMotor(0.0);
        // }

        // else if (rightLimitSwitch.get()) {
        //     turnTurretMotor(0.0);
        // }

        if (oi.getRightStickButton(RobotMap.JOYSTICK_3D_THUMB_BUTTON)) {
            turnTurretMotor(oi.getRightStickAxis(RobotMap.JOYSTICK_3D_Z_AXIS) );
        }

        else {
            turnTurretMotor(0.0);
        }


        //SmartDashboard.putNumber("turret", encoder.get());

        //lastEncoderCount = encoder.get();

    }

    public boolean turnTurret(double angle) {
        return false;
    }

    private void turnTurretMotor(double speed) {
        //turretMotor.set(speed);
        turretMotor.set(ControlMode.PercentOutput, speed);
    }

    private void resetEncoderCounter() {
        encoder.reset();
    }

    // public boolean turnTurretTwoDegrees() { //this is what dom told me to do dont roast me please
    //     if (!complete) {
    //         turnTurretMotor(0.5);
    //     }

    //     if(encoder.get() - lastEncoderCount > 2) {
    //         complete = true;
    //         turnTurretMotor(0);
    //     }
    //     else {
    //         complete = false;
    //     }

    //     return complete;
    // }

}