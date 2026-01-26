package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import org.firstinspires.ftc.teamcode.helpers.MaxPowerController;

@TeleOp(name = "Battery Test")
public class BatteryTest extends OpMode {

    private MaxPowerController maxPower;
    private boolean running = false;

    @Override
    public void init() {
        maxPower = new MaxPowerController(hardwareMap);
        telemetry.addData("Status", "Ready");
        telemetry.update();
    }

    @Override
    public void loop() {
        // Toggle max power ON/OFF
        if (gamepad1.aWasReleased()) {
            running = !running;

            if (running) {
                maxPower.allOn();
            } else {
                maxPower.allOff();
            }
        }

        telemetry.addData("Motors", running ? "MAX POWER" : "OFF");
        telemetry.update();
    }

    @Override
    public void stop() {
        maxPower.allOff();
    }
}
