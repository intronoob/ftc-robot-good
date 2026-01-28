package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.teamcode.helpers.ArduinoComms;

@Autonomous(name = "robotAuto1")
public class robotAuto1 extends OpMode {
    private LauncherController launchSystem;
    private RobotMovementController WheelDrive;

    private ElapsedTime timer;

    private static final double MOVPWR = 0.25;

    // simple state machine
    private int step = 0;
    private boolean done = 0;

    @Override
    public void init() {
        timer = new ElapsedTime();
        WheelDrive = new RobotMovementController(hardwareMap);
        launchSystem = new LauncherController(hardwareMap);

        telemetry.addData("Status", "Initialized");
        telemetry.update();
    }

    @Override
    public void start() {
        timer.reset();
        step = 0;
    }

    @Override
    public void loop() {

        while(!done) {

            WheelDrive.moveInches(24, 0, 0, MOVPWR);

            WheelDrive.moveInches(0, 12, 0, MOVPWR);

            WheelDrive.moveInches(0, 0, 90, MOVPWR);

            launchSystem.toggleIntake();

            timer.reset();
            if (timer.milliseconds() > 1500) {
                launchSystem.toggleIntake();
                step++;
            }

            launchSystem.runLaunchSequence();

            telemetry.addData("Auto", "Complete");
            telemetry.update();
            done = true
        }

        telemetry.addData("Step", step);
        telemetry.update();
    }
}
