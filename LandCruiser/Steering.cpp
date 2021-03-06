#include <stdio.h>
#include <pigpio.h>

#include "Steering.h"

using namespace std;

JNIEXPORT int JNICALL Java_Steering_turnWheels
(JNIEnv *env, jobject o, jint pin, jint pulseWidth) {

    printf("pin = %d, pulseWidth= %d \n", pin, pulseWidth);

    if (gpioInitialise() < 0) {
        printf("failed gpioInitialise \n");
        return 0;
    }	
    gpioServo(pin, pulseWidth);

    return 1;
}
