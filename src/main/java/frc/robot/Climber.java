package frc.robot;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;

public class Climber {
    protected OI oi;
    protected TalonSRX mainTalon;
    protected TalonSRX followerTalon;

    public Climber(OI oi) {
        mainTalon = new TalonSRX(20);
        //followerTalon = new TalonSRX(21);
        this.oi = oi;

        //followerTalon.follow(mainTalon);

    }

    public void climberTeleopPeriodic() {
        if (oi.getGamepadAxis(RobotMap.GP_R_TRIGGER) > 0.1){
            mainTalon.set(ControlMode.PercentOutput, -oi.getGamepadAxis(RobotMap.GP_R_TRIGGER));
        } else if (oi.getGamepadAxis(RobotMap.GP_L_TRIGGER) > 0.1){
            mainTalon.set(ControlMode.PercentOutput, oi.getGamepadAxis(RobotMap.GP_L_TRIGGER));
        }
        else {
            mainTalon.set(ControlMode.PercentOutput, 0);
        }
    }
}