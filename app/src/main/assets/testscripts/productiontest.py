configs = {
    'left': {
        'cw': 'RevvyMotor_CCW',
        'ccw': 'RevvyMotor'
    },
    'right': {
        'cw': 'RevvyMotor',
        'ccw': 'RevvyMotor_CCW'
    }
}
robot.motors[1].configure(configs["left"]["cw"])
robot.motors[1].spin(direction=Motor.DIR_CW, rotation=20, unit_rotation=Motor.UNIT_SPEED_RPM)
time.sleep(3)
robot.motors[1].stop(action=Motor.ACTION_RELEASE)
time.sleep(0.2)
robot.motors[1].spin(direction=Motor.DIR_CCW, rotation=20, unit_rotation=Motor.UNIT_SPEED_RPM)
time.sleep(3)
robot.motors[1].stop(action=Motor.ACTION_RELEASE)
time.sleep(0.2)
robot.motors[1].configure("NotConfigured")