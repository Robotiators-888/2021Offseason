package frc.robot;

import edu.wpi.first.wpilibj.I2C;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj.util.Color;

import com.revrobotics.ColorSensorV3;
import com.revrobotics.ColorMatchResult;
import com.revrobotics.ColorMatch;

public class ColorSensor {
    protected ColorSensorV3 sensor;
    protected ColorMatch matcher;

    ColorSlice color;

    // Colors
    protected final Color RED_TARGET = ColorMatch.makeColor(255, 0, 0);
    protected final Color YELLOW_TARGET = ColorMatch.makeColor(255, 255, 0);
    protected final Color BLUE_TARGET = ColorMatch.makeColor(0, 255, 255);
    protected final Color GREEN_TARGET = ColorMatch.makeColor(0, 255, 0);

    public ColorSensor(I2C.Port i2cPort) {
        sensor = new ColorSensorV3(i2cPort);
        matcher = new ColorMatch();

        matcher.addColorMatch(RED_TARGET);
        matcher.addColorMatch(YELLOW_TARGET);
        matcher.addColorMatch(BLUE_TARGET);
        matcher.addColorMatch(GREEN_TARGET);
    }

    public ColorSlice getColor() {
        Color detectedColor = sensor.getColor();

        ColorMatchResult result = matcher.matchClosestColor(detectedColor);

        if (result.color == RED_TARGET) {
            color = ColorSlice.RED;
        }
        else if (result.color == YELLOW_TARGET) {
            color = ColorSlice.YELLOW;
        }
        else if (result.color == BLUE_TARGET) {
            color = ColorSlice.BLUE;
        }
        else if (result.color == GREEN_TARGET) {
            color = ColorSlice.GREEN;
        }
        else {
            color = ColorSlice.UNKNOWN;
        }

        // SmartDashboard.putNumber("Red", detectedColor.red);
        // SmartDashboard.putNumber("Green", detectedColor.green);
        // SmartDashboard.putNumber("Blue", detectedColor.blue);
        SmartDashboard.putNumber("Confidence", result.confidence);
        SmartDashboard.putString("Detected Color", color.toString());

        return color;
    }

    public enum ColorSlice implements Comparable<ColorSlice> {

        RED, YELLOW, BLUE, GREEN, UNKNOWN;

    }
}