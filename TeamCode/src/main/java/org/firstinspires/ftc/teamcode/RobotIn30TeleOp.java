package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.util.ElapsedTime;
@TeleOp
public class RobotIn30TeleOp extends RobotLinearOpMode {

    private DcMotor leftFrontDriveMotor;
    private DcMotor leftBackDriveMotor;
    private DcMotor rightFrontDriveMotor;
    private DcMotor rightBackDriveMotor;

    private DcMotor launchMotor;
    private DcMotor feedMotor;

    private Servo feedServo;
    private CRServo intakeServo;

    private ElapsedTime aButtonTimer;

    private boolean aButtonPressed;
    double axial;
    double lateral;
    double yaw;

    public void runOpMode() {

        leftBackDriveMotor = hardwareMap.get(DcMotor.class, "leftBack");
        rightBackDriveMotor = hardwareMap.get(DcMotor.class, "rightBack");
        leftFrontDriveMotor = hardwareMap.get(DcMotor.class, "leftFront");
        rightFrontDriveMotor = hardwareMap.get(DcMotor.class, "rightFront");

        rightFrontDriveMotor.setDirection(DcMotorEx.Direction.FORWARD);
        leftFrontDriveMotor.setDirection(DcMotorEx.Direction.REVERSE);
        rightBackDriveMotor.setDirection(DcMotorEx.Direction.FORWARD);
        leftBackDriveMotor.setDirection(DcMotorEx.Direction.REVERSE);
        leftBackDriveMotor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        leftFrontDriveMotor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        rightBackDriveMotor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        rightFrontDriveMotor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);

        launchMotor = hardwareMap.get(DcMotor.class, "launchMotor");
        feedMotor = hardwareMap.get(DcMotor.class, "feedMotor");

        intakeServo = hardwareMap.get(CRServo.class, "intakeServo");
        feedServo = hardwareMap.get(Servo.class, "feedServo");

        aButtonTimer = new ElapsedTime();

        waitForStart();
        while (opModeIsActive()) {
            aButtonTimer.startTime();
            axial = -gamepad1.left_stick_y;
            lateral = -gamepad1.left_stick_x;
            yaw = gamepad1.right_stick_x;



            leftBackDriveMotor.setPower(axial - lateral + yaw);
            leftFrontDriveMotor.setPower(axial + lateral + yaw);
            rightBackDriveMotor.setPower(axial + lateral - yaw);
            rightFrontDriveMotor.setPower(axial - lateral - yaw);

            if (gamepad1.a && !aButtonPressed && aButtonTimer.milliseconds() > 500) {
                aButtonTimer.reset();
                launchMotor.setPower(-1);
                aButtonPressed = true;
                aButtonTimer.startTime();
            } else if (gamepad1.a && aButtonPressed && aButtonTimer.milliseconds() > 500) {
                aButtonTimer.reset();
                launchMotor.setPower(0);
                aButtonPressed = false;
                aButtonTimer.startTime();
            }

            if (gamepad1.b) {
                feedServo.setPosition(.15);
            }

            if (gamepad1.x) {
                feedServo.setPosition(.85);
            }

            if (gamepad1.dpad_up) {
                feedMotor.setPower(1);
            }

            if (gamepad1.dpad_down) {
                feedMotor.setPower(0);
            }

            if (gamepad1.dpad_right) {
                intakeServo.setPower(-1);
            }

            if (gamepad1.dpad_left) {
                intakeServo.setPower(0);
            }


        }

    }
}
