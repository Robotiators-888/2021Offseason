
/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot;

import edu.wpi.first.wpilibj.Joystick;

/**
 * This class is the glue that binds the controls on the physical operator
 * interface to the commands and command groups that allow control of the robot.
 */
public class OI {

    Joystick leftStick;
    Joystick rightStick;
    Joystick gamepad;

    public OI() {

        // Instantiates and declares the joystick objects
        leftStick = new Joystick(RobotMap.LEFT_JOYSTICK);
        rightStick = new Joystick(RobotMap.RIGHT_JOYSTICK);
        gamepad = new Joystick(RobotMap.GAMEPAD_PORT);

    }

    /**
     * @param Axis array value
     * @return Axis value between -1 and 1 for left joystick
     */
    public double getLeftStickAxis(int axis) {
        return leftStick.getRawAxis(axis);
    }

    /**
     * @param Axis array value
     * @return Axis value between -1 and 1 for right joystick
     */
    public double getRightStickAxis(int axis) {
        return rightStick.getRawAxis(axis);
    }

    /**
     * @param Axis array value
     * @return Axis value between -1 and 1 for gamepad joystick
     */
    public double getGamepadAxis(int axis) {
        return gamepad.getRawAxis(axis);
    }

    /**
     * @return The D-Pad values from the secondary controller
     */
    public int getGamepadPOV() {
        return gamepad.getPOV();
    }

    /**
     * @param Button array value
     * @return Button value (true if pressed) from the left joystick
     */
    public boolean getLeftStickButton(int button) {
        return leftStick.getRawButton(button);
    }

    /**
     * @param Button array value
     * @return Button value (true if pressed) from the right joystick
     */
    public boolean getRightStickButton(int button) {
        return rightStick.getRawButton(button);
    }

    /**
     * @param Button array value
     * @return Button value (true if pressed) from the gamepad joystick
     */
    public boolean getGamepadButton(int button) {
        return gamepad.getRawButton(button);
    }

    /**
     * @return True if both the left and right joystick triggers are pressed
     */
    public boolean getTriggers() {
        return (leftStick.getRawButton(1) && rightStick.getRawButton(1));
    }

    public enum ControllerButton {
        BUTTON_A, BUTTON_B, BUTTON_X, BUTTON_Y, BUTTON_NULL;

    }
}