package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.hardware.VoltageSensor;

public class LauncherController {

    private static final double LAUNCH_POWER_INIT = 0.52;
    private static final double LAUNCH_VOLTAGE_INIT = 13.85;

    private final DcMotor launch;
    private final DcMotor rollIn;
    private final DcMotor rollMidRight;
    private final Servo trapDoor;
    private final HardwareMap hardwareMap;

    public LauncherController(HardwareMap hardwareMap) {
        this.hardwareMap = hardwareMap;

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
        } catch (InterruptedException ignored) {
        }
    }

    public void runLaunchSequence() {
        // get battery voltage
        double voltage = hardwareMap.voltageSensor.iterator().next().getVoltage();

        // spin up launcher
        double compensatedPower = LAUNCH_POWER_INIT * (LAUNCH_VOLTAGE_INIT / voltage);
        launch.setPower(compensatedPower);

        sleep(2000);

        // feed rings
        rollIn.setPower(1.0);
        rollMidRight.setPower(1.0);

        sleep(5000);

        // stop everything
        launch.setPower(0);
        rollIn.setPower(0);
        rollMidRight.setPower(0);
    }
}
