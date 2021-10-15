package frc.robot;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;
import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.DoubleSolenoid.Value;

public class Climber {
    protected OI oi;
    protected TalonSRX mainTalon;
    protected TalonSRX followerTalon;
    private DoubleSolenoid lock;

    public Climber(OI oi) {
        mainTalon = new TalonSRX(20);
        //followerTalon = new TalonSRX(21);
        this.oi = oi;
        lock = new DoubleSolenoid(RobotMap.PCM, 2, 3);
        //followerTalon.follow(mainTalon);

    }

    public void climberTeleopPeriodic() {
        if (oi.getGamepadAxis(RobotMap.GP_R_TRIGGER) > 0.1){
            lock.set(Value.kReverse);
            mainTalon.set(ControlMode.PercentOutput, -oi.getGamepadAxis(RobotMap.GP_R_TRIGGER));
        } else if (oi.getGamepadAxis(RobotMap.GP_L_TRIGGER) > 0.1){
            lock.set(Value.kReverse);
            mainTalon.set(ControlMode.PercentOutput, oi.getGamepadAxis(RobotMap.GP_L_TRIGGER));
        }
        else {
            lock.set(Value.kForward);
            mainTalon.set(ControlMode.PercentOutput, 0);
        }
    }
}