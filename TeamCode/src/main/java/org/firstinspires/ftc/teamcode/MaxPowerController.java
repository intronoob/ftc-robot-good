package org.firstinspires.ftc.teamcode.helpers;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.HardwareMap;

public class MaxPowerController {

    private final DcMotor rollIn;
    private final DcMotor rollMidRight;
    private final DcMotor launcher;

    private static final double MAX_POWER = 1.0;

    public MaxPowerController(HardwareMap hardwareMap) {
        rollIn = hardwareMap.get(DcMotor.class, "roll_in");
        rollMidRight = hardwareMap.get(DcMotor.class, "roll_mid_right");
        launcher = hardwareMap.get(DcMotor.class, "launch");

        rollIn.setDirection(DcMotor.Direction.REVERSE);
        rollMidRight.setDirection(DcMotor.Direction.REVERSE);
    }

    public void allOn() {
        rollIn.setPower(MAX_POWER);
        rollMidRight.setPower(MAX_POWER);
        launcher.setPower(MAX_POWER);
    }

    public void allOff() {
        rollIn.setPower(0);
        rollMidRight.setPower(0);
        launcher.setPower(0);
    }
}
