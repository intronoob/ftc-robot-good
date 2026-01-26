package org.firstinspires.ftc.teamcode.helpers;

import com.qualcomm.robotcore.hardware.DcMotor;

public class LauncherController {

    private final DcMotor launch;
    private final DcMotor rollIn;
    private final DcMotor rollMidRight;
    private final Servo trapDoor;

    public LauncherController() {
        rollIn = hardwareMap.get(DcMotor.class, "roll_in");
        rollMidRight = hardwareMap.get(DcMotor.class, "roll_mid_right");
        launch = hardwareMap.get(DcMotor.class, "launch");
        trapDoor = hardwareMap.get(Servo.class, "trapdoor");
        rollIn.setDirection(DcMotor.Direction.REVERSE);
        rollMidRight.setDirection(DcMotor.Direction.REVERSE);
    }

    private void sleep(int ms) {
        try {
            Thread.sleep(ms);
        } catch (InterruptedException e) {
            // ignore
        }
    }

    public void runLaunchSequence() {
        // spin up launcher first
        sleep(2000);

        rollIn.setPower(1.0);
        rollMidRight.setPower(1.0);

        sleep(5000);

        launch.setPower(0);
        rollIn.setPower(0);
        rollMidRight.setPower(0);
    }
}
