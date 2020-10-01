class Steering {
    private static final int STEERING_CENTER = 1400;
    private static final int STEERING_RIGHT_MAX = 1130;
    private static final int STEERING_LEFT_MAX = 1670;

    private static final int GPIO_PIN = 23;

    public native int turnWheels(int pin, int pulseWidth);

    static {
        System.loadLibrary("steering");
    }	
	
    public static void main(String args[]) {
        Steering steering = new Steering();
        try {
            steering.turnWheels(GPIO_PIN, STEERING_CENTER);
            Thread.sleep(3_000);
            steering.turnWheels(GPIO_PIN, STEERING_RIGHT_MAX);
            Thread.sleep(3_000);
            steering.turnWheels(GPIO_PIN, STEERING_LEFT_MAX);
            Thread.sleep(3_000);
            steering.testFullRange();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
	
    void testFullRange() {
        try {
            boolean goingDown = true;
            int x = STEERING_LEFT_MAX;
            while (true) {
                turnWheels(GPIO_PIN, x);
                Thread.sleep(20);
                if (goingDown) {
                    x-=5;
                } else {
                    x+=5;
                }
                if (x < STEERING_RIGHT_MAX) { goingDown = false; }
                if (x > STEERING_LEFT_MAX) { goingDown = true; }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}