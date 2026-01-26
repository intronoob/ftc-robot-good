package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
@Autonomous(name = "robotAuto1")
public class robotAuto1 extends OpMode {
    private RobotMovementController WheelDrive;
    private double LAUNCH_POWER = 0.52;
//    private double STEPS_PER_ROT = 530.0;
//    private double WHEEL_DIAMETER_IN = 4.0;
//    private double WHEEL_CIRCUMFERENCE = Math.PI * WHEEL_DIAMETER_IN;
//    private double WHEEL_TRAVEL_DIST_PER_STEP = WHEEL_CIRCUMFERENCE / STEPS_PER_ROT;
//    private double ENCODER_TRAVEL_SPEED = 0.5;
//    private double MS_PER_DEG_ROT = 17.9722222222222;
    private DcMotor roll_in;
    private Servo trapDoorServo;
    private DcMotor roll_mid_right;
    private DcMotor launch;
    private boolean loopNoDone = false;
    public void sleep(int dt) { //dt = delay time
        telemetry.addData("Status", "Waiting");
        telemetry.update();
        try {
            Thread.sleep(dt);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        telemetry.addData("Status", "Stopped Waiting");
        telemetry.update();
    }
    public void launch() {
        // launch.setVelocity(200);

        sleep(2000);
        roll_in.setPower(1.0);
        roll_mid_right.setPower(1.0);
        sleep(5000);
        launch.setPower(0);
        roll_in.setPower(0);
        roll_mid_right.setPower(0);
    }
    @Override
    public void init() {

        WheelDrive = new RobotMovementController(hardwareMap);
//        drive.drive(0.1, 0, 0);
        roll_in = hardwareMap.get(DcMotor.class, "roll_in");
        roll_mid_right = hardwareMap.get(DcMotor.class, "roll_mid_right");
        launch = hardwareMap.get(DcMotor.class, "launch");
        trapDoorServo = hardwareMap.get(Servo.class,"trapdoor");
        // encoderInit(launch);
        roll_in.setDirection(DcMotor.Direction.REVERSE);
        roll_mid_right.setDirection(DcMotor.Direction.REVERSE);
    }

    @Override
    public void loop() {
        if(!loopNoDone) {
            launch.setPower(LAUNCH_POWER);
//            trapDoorServo.setPosition(0.3);
//            double coords[] = {4.0,0.0};
//            WheelDrive.moveInches(0,4,0.2); //positive x = right
//            robotSetPos(coords);
            launch();
//            rotateDeg(45.0,-1.0);
//            double coords2[] = {-20.0,0.0};
//            robotSetPos(coords2);
//            loopNoDone = true;
        }
    }

}