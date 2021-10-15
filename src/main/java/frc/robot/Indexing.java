package frc.robot;

import com.ctre.phoenix.motorcontrol.can.TalonSRX;
import com.ctre.phoenix.motorcontrol.ControlMode;

import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;

import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class Indexing {
    
    OI oi;
    Intake intake;
    
    TalonSRX funnelMotor;
    TalonSRX lowerFunnelSideIndex;
    TalonSRX lowerFarSideIndex;

    CANSparkMax upperBeltIndex;
    CANSparkMax loadingBeltIndex;

    // 0 is top, 4 is intake
    DigitalInput bannerPos0;
    DigitalInput bannerPos1;
    DigitalInput bannerPos2;
    DigitalInput bannerPos3;
    DigitalInput bannerPos4;

    boolean ballInPos0;
    boolean ballInPos1;
    boolean ballInPos2;
    boolean ballInPos3;
    boolean ballInPos4;

    public void indexAutoPeriodic(boolean ready){
        if (ready){
            runLoadBelt(0.5);
            runUpperBelt(0.5);
            runLowerFarSideIndex(0.5);
            runLowerFunnelSideIndex(0.5);
        }else{
            runLoadBelt(0);
            runUpperBelt(0);
            runLowerFarSideIndex(0);
            runLowerFunnelSideIndex(0);
        }
    }

    public Indexing(OI oi, Intake intake) {
        funnelMotor = new TalonSRX(RobotMap.FUNNEL_MOTOR_CANID);
        lowerFunnelSideIndex = new TalonSRX(
                RobotMap.LOWER_FUNNEL_SIDE_INDEX_CANID);
        lowerFarSideIndex = new TalonSRX(
                RobotMap.LOWER_FAR_SIDE_INDEX_CANID);
        
        upperBeltIndex = new CANSparkMax(RobotMap.UPPER_BELT_CANID, MotorType.kBrushless);
        loadingBeltIndex = new CANSparkMax(RobotMap.LOADING_BELT_CANID, MotorType.kBrushless);

        funnelMotor.setInverted(false);

        upperBeltIndex.setInverted(true);
        loadingBeltIndex.setInverted(true);


        bannerPos0 = new DigitalInput(0);
        bannerPos1 = new DigitalInput(1);
        bannerPos2 = new DigitalInput(2);
        bannerPos3 = new DigitalInput(3);
        bannerPos4 = new DigitalInput(4);

        this.oi = oi;
    }

    public void indexPeriodic() {
        if (oi.getGamepadPOV() == 0) {
            runFunnel(.5);
            runLowerFunnelSideIndex(.5);
            runLowerFarSideIndex(.5);
            runUpperBelt(.5);
            runLoadBelt(.5);
        }

        else if (oi.getGamepadPOV() == 180) {
            runFunnel(-0.5);
            runLowerFunnelSideIndex(-0.5);
            runLowerFarSideIndex(-0.5);
            runUpperBelt(-0.5);
            runLoadBelt(-0.50);
        }

        else {
            stopIndexer();
        }

        updateBallPoitions();
    }

    public void bringToTop() {

        updateBallPoitions();

        if (hasBalls()) {
            switch (getNextBall()) {
                case 0:
                    stopIndexer();
                    break;
                case 1:
                    runLowerFunnelSideIndex(0.5);
                    runLowerFarSideIndex(0.5);
                    runUpperBelt(0.5);
                    runLoadBelt(0.0);
                    if (ballInPos3 || ballInPos4) {
                        runFunnel(1.0);
                    }
                    break;
                case 2:
                    runLowerFunnelSideIndex(0.5);
                    runLowerFarSideIndex(0.5);
                    if (ballInPos3 || ballInPos4) {
                        runFunnel(1.0);
                    }
                    break;
                case 3:
                    runFunnel(1.0);
                    runLowerFunnelSideIndex(0.5);
                    runLowerFarSideIndex(0.5);
                    break;
                case 4:
                    runFunnel(1.0);
                    break;
                default: 
                    stopIndexer();
                    break;
            }
        }
    }

    public void stopIndexer() {
        runFunnel(0.0);
        runLowerFunnelSideIndex(0.0);
        runLowerFarSideIndex(0.0);
        runUpperBelt(0.0);
        runLoadBelt(0.0);
    }

    public boolean loadShooter() {
        if (ballInPos0) {
            runLoadBelt(1.0);
            return false;
        }
        
        runLoadBelt(0.0);
        return true;
    }

    public boolean hasBalls() {
        return ballInPos0 || ballInPos1 || ballInPos2 || ballInPos3 || ballInPos4;
    }

    public boolean readyToFire() {
        return ballInPos0;
    }

    public int getNextBall() {
        if (ballInPos0)
            return 0;
        if (ballInPos1)
            return 1;
        if (ballInPos2)
            return 2;
        if (ballInPos3)
            return 3;
        if (ballInPos4)
            return 4;
        return -1;
    }

    private void updateBallPoitions() {
        ballInPos0 = bannerPos0.get();
        ballInPos1 = bannerPos1.get();
        ballInPos2 = bannerPos2.get();
        ballInPos3 = bannerPos3.get();
        ballInPos4 = bannerPos4.get();

        SmartDashboard.putBoolean("ball 0", ballInPos0);
        SmartDashboard.putBoolean("ball 1", ballInPos1);
        SmartDashboard.putBoolean("ball 2", ballInPos2);
        SmartDashboard.putBoolean("ball 3", ballInPos3);
        SmartDashboard.putBoolean("ball 4", ballInPos4);
    }

    private void runFunnel(double speed) {
        funnelMotor.set(ControlMode.PercentOutput, speed);
    }

    private void runLowerFunnelSideIndex(double speed) {
        lowerFunnelSideIndex.set(ControlMode.PercentOutput, speed);
    }

    private void runLowerFarSideIndex(double speed) {
        lowerFarSideIndex.set(ControlMode.PercentOutput, speed);
    }

    private void runUpperBelt(double speed) {
        upperBeltIndex.set(speed);
    }

    private void runLoadBelt(double speed) {
        loadingBeltIndex.set(speed);
    }

}