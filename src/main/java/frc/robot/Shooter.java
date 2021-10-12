package frc.robot;

import com.revrobotics.CANSparkMax;
import com.revrobotics.ControlType;
import com.revrobotics.CANSparkMax.IdleMode;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;

// import disc.data.Position;
// import disc.data.Waypoint;
// import disc.data.WaypointMap;

import com.revrobotics.CANEncoder;
import com.revrobotics.CANPIDController;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class Shooter {
    private OI oi;
    private Indexing index;
    private Turret turret;
   // private WaypointMap map;

    private CANSparkMax shooterMotor;
    private CANPIDController pid;
    private CANEncoder encoder;


    //private Waypoint target;
    //private Position pose;

    private double targetRPM;
    private double rpms;

    private boolean lastUp, lastDown;
    private boolean lastCycleUp, lastCycleDown;

    private int selectedRPM;

    private double bangBang; // goes up and down to meet target rpm

    //private UDPReceiver receiver;

    /**
     * receive
     *
     * @param oi
     * @param p_receiveS
     */
    public Shooter(OI oi) {
        this.oi = oi;
        this.turret = turret;
        //this.map = map;

        shooterMotor = new CANSparkMax(RobotMap.SHOOTER_CANID,MotorType.kBrushless);
        shooterMotor.setIdleMode(IdleMode.kCoast);
        pid = shooterMotor.getPIDController();

        encoder = shooterMotor.getEncoder();

        rpms = 0;
        targetRPM = 3700;

        pid.setFF(0.00000481);
        pid.setP(0.000001);
        pid.setI(0.000000000000125);
        pid.setD(0.00000000000001);

        pid.setIZone(500);

        // pid.setIMaxAccum(100, 0);

        pid.setOutputRange(0, 1);

        shooterMotor.setClosedLoopRampRate(0.1);
        shooterMotor.setOpenLoopRampRate(0.1);

        selectedRPM = 0;
    }

    public void shooterTeleopPeriodic() {

        rpms = encoder.getVelocity();
        //pose = location.getPose();

        if(oi.getRightStickButton(6) && !lastCycleUp) {
            if(selectedRPM == RobotMap.NUM_RPM_SETPOINTS-1) {
                selectedRPM = 0;
                targetRPM = RobotMap.RPM_SETPOINTS[selectedRPM];
            }
            else {
                selectedRPM++;
                targetRPM = RobotMap.RPM_SETPOINTS[selectedRPM];
            }
        }

        if(oi.getRightStickButton(4) && !lastCycleDown) {
            if(selectedRPM == 0) {
                selectedRPM = RobotMap.NUM_RPM_SETPOINTS-1;
                targetRPM = RobotMap.RPM_SETPOINTS[selectedRPM];
            }
            else {
                selectedRPM--;
                targetRPM = RobotMap.RPM_SETPOINTS[selectedRPM];
            }
        }

        if(oi.getGamepadButton(RobotMap.Y_BUTTON)) {
            targetRPM = getRPMs();
        }

        //this bitch
        if (oi.getRightStickButton(RobotMap.JOYSTICK_3D_TRIGGER)) {
            setShooterOutputVelocity(targetRPM);
        }
        else {
            stop();
        }

        if (oi.getRightStickButton(RobotMap.JOYSTICK_3D_UPPER_LEFT_BUTTON) && !lastUp) {
            if(targetRPM < 4000)
                targetRPM += 50;
        }
        if (oi.getRightStickButton(RobotMap.JOYSTICK_3D_LOWER_LEFT_BUTTON) && !lastDown) {
            if(targetRPM > 2500)
                targetRPM -= 50;
        }

        lastUp = oi.getRightStickButton(RobotMap.JOYSTICK_3D_UPPER_LEFT_BUTTON);
        lastDown = oi.getRightStickButton(RobotMap.JOYSTICK_3D_LOWER_LEFT_BUTTON);
        
        SmartDashboard.putNumber("Encoder RPMs", rpms);
        SmartDashboard.putNumber("Target RPMs", targetRPM);
        SmartDashboard.putString("Preset", RobotMap.RPM_SETPOINTS_NAMES[selectedRPM]);
        SmartDashboard.putBoolean("Ready to fire", readyToFire());
        // SmartDashboard.putNumber("kP", pid.getP());
        // SmartDashboard.putNumber("kI", pid.getI());
        // SmartDashboard.putNumber("kD", pid.getD());
        // SmartDashboard.putNumber("kFF", pid.getFF());
        //SmartDashboard.putNumber("DistanceAwayFromTarget", receiver.getTarget()[2]);
        lastCycleUp = oi.getRightStickButton(6);
        lastCycleDown = oi.getRightStickButton(4);
        //SmartDashboard.putNumber("bang", bangBang);
    }

    /**
     * Runs the shooter at full speed.
     */
    public void shootFullSpeed() {
        shooterMotor.set(1.0);
    }

    /**
     * Runs the shooter at a certain percentage of full speed.
     *
     * @param percentSpeed A percent value between -1.0 and 1.0.
     */
    public void setShooterPercentSpeed(double percentSpeed) {
        shooterMotor.set(percentSpeed);
    }

    /**
     * Causes the object being shot to exit the shooter at a specified velocity.
     *
     * @param velocity The exit velocity in feet per second.
     */
    public void setShooterOutputVelocity(double target) {
        if(getRPMs() < (target - 500) || getRPMs() > (target + 500) ){
            if(getRPMs() < (target + 50)){
                bangBang = bangBang + 0.005;
            }else if(getRPMs() > (target - 50)) {
                bangBang = bangBang - 0.005;
            }
        }else {
            if(getRPMs() < (target + 50)){
                bangBang = bangBang + 0.001;
            }else if(getRPMs() > (target - 50)) {
                bangBang = bangBang - 0.001;
            }
        }
        
        
        shooterMotor.set(bangBang);
    }

    /**
     * Calculates the shooter's rotations per minute.
     *
     * @return The number of rotations of the shooter wheel in one minute.
     */
    public double getRPMs() {
        return encoder.getVelocity();
    }
    /**
     * Stop spinning the shooter wheel.
     */
    public void stop() {
        shooterMotor.set(0.0);
    }

    /**
     * Determines if shooter is ready to fire ball
     * 
     * @return True if system is ready to fire a ball
     */
    public boolean readyToFire() {
        if(Math.abs(targetRPM - getRPMs()) < 500) {
            return true;
        }
        else {
            return false;
        }
    }

}