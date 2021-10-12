package frc.robot;

import com.ctre.phoenix.motorcontrol.can.TalonSRX;

import com.ctre.phoenix.motorcontrol.ControlMode;

import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.I2C;
import frc.robot.ColorSensor.ColorSlice;
import frc.robot.OI.ControllerButton;

public class ColorWheel {

    OI oi;

    TalonSRX colorWheelMotor;

    ColorSensor colorSensor;
    final I2C.Port i2cPort = I2C.Port.kOnboard;

    ControllerButton gamepadButton = ControllerButton.BUTTON_NULL;
    ControllerButton gamepadButtonLast = ControllerButton.BUTTON_NULL;

    ColorSlice colorCurrent;
    ColorSlice colorStarting;
    ColorSlice colorLast;

    double counter;

    boolean aButton = false;
    boolean bButton = false;
    boolean xButton = false;
    boolean yButton = false;

    public ColorWheel(OI oi) {
        this.oi = oi;

        colorWheelMotor = new TalonSRX(RobotMap.COLOR_WHEEL_MOTOR_CANID);

        colorSensor = new ColorSensor(i2cPort);

        counter = 0;

        colorStarting = colorSensor.getColor();
    }

    public void colorWheelMotorTeleopPeriodic() {

        gamepadButtonLast = gamepadButton;

        updateButton();

        colorCurrent = colorSensor.getColor();

        switch (gamepadButton) {
            case BUTTON_A:
                if (gamepadButtonLast.equals(ControllerButton.BUTTON_NULL)) {
                    colorStarting = colorSensor.getColor();
                }
                rotatePosition(getDesiredColor());
                break;
            case BUTTON_B:
                rotateAutomatic(4);
                break;
            case BUTTON_X:
                rotateManual(RobotMap.COLOR_WHEEL_SPEED);
                gamepadButton = ControllerButton.BUTTON_NULL;
                break;
            case BUTTON_Y:
                rotateManual(RobotMap.COLOR_WHEEL_SPEED);
                gamepadButton = ControllerButton.BUTTON_NULL;
                break;
            case BUTTON_NULL:
                rotateManual(0.0);
                break;
        }
    }

    public ColorSlice getDesiredColor() {
        switch (DriverStation.getInstance().getGameSpecificMessage()) {
            case "R":
                return ColorSlice.RED;
            case "Y":
                return ColorSlice.YELLOW;
            case "B":
                return ColorSlice.BLUE;
            case "G":
                return ColorSlice.GREEN;
            default:
                return ColorSlice.UNKNOWN;
        }
    }

    private void rotateManual(double speed) {
        colorWheelMotor.set(ControlMode.PercentOutput, speed);
    }

    private void rotateAutomatic(int numRotations) {

        if (counter < 2 * numRotations) {
            colorWheelMotor.set(ControlMode.PercentOutput, 0.75);
        }
        else {
            gamepadButton = ControllerButton.BUTTON_NULL;
        }

        if (colorCurrent.equals(colorLast)) {
            if (colorCurrent.equals(colorStarting)) {
                counter++;
            }
        }

        colorLast = colorCurrent;
    }

    private void rotatePosition(ColorSlice colorDesired) {

        if (!colorDesired.equals(ColorSlice.UNKNOWN)) {
            if (!(colorCurrent.equals(colorDesired))) {
                double rotationSpeed = colorCurrent.compareTo(colorDesired);
                rotationSpeed = Math.abs(rotationSpeed) > 2
                        ? -Math.signum(rotationSpeed)
                        : Math.signum(rotationSpeed);
                rotateManual(rotationSpeed * RobotMap.COLOR_WHEEL_SPEED);
            }
            else {
                rotateManual(0.0);
                gamepadButton = ControllerButton.BUTTON_NULL;
            }
        }

        else {
            System.out.println("ERROR: NO COLOR AVAILIBLE FROM FMS");
        }
    }

    /**
     * Updates an an enum that holds the last command from the gamepad letter
     * buttons.
     */
    public void updateButton() {

        aButton = oi.getGamepadButton(RobotMap.A_BUTTON);
        bButton = oi.getGamepadButton(RobotMap.B_BUTTON);
        xButton = oi.getGamepadButton(RobotMap.X_BUTTON);
        yButton = oi.getGamepadButton(RobotMap.Y_BUTTON);

        if (aButton) {
            gamepadButton = ControllerButton.BUTTON_A;
        }
        else if (bButton) {
            gamepadButton = ControllerButton.BUTTON_B;
        }
        else if (xButton) {
            gamepadButton = ControllerButton.BUTTON_X;
        }
        else if (yButton) {
            gamepadButton = ControllerButton.BUTTON_Y;
        }
    }
}