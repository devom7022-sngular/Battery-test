#!/bin/bash
$ANDROID_HOME/emulator/emulator -avd FirstEmulator -wipe-data -port 5790 &
EMULATOR_PID=$!

$ANDROID_HOME/platform-tools/adb shell dumpsys batterystats --reset

# Wait for Android to finish booting
echo "Waiting for emulator to finish booting..."
WAIT_CMD=$($ANDROID_HOME/platform-tools/adb -s emulator-5790 wait-for-device shell 'while [[ -z $(getprop sys.boot_completed) ]]; do sleep 1; done; input keyevent 82')
until $WAIT_CMD; do
 sleep 1
done

echo "Emulator reported that the startup process is $EMULATOR_STATUS"
sleep 10

echo "Emulator is ready for use"
# Unlock the Lock Screen
$ANDROID_HOME/platform-tools/adb shell input keyevent 82

# Clear and capture logcat
$ANDROID_HOME/platform-tools/adb logcat -c
$ANDROID_HOME/platform-tools/adb logcat > logcat.log &
LOGCAT_PID=$!

let PACKAGE_ID_PARAM=$1
let OS_TYPE_PARAM=$2
let TEST_TYPE_PARAM=$3
let TEST_TIME_PARAM=$4


if [ "$TEST_TYPE_PARAM" = "manual" ]; then
  # Install app (Only for manually tests)
  ${WORKSPACE}/gradlew installDebug
  let minutes=60*$TEST_TIME_PARAM
  echo "Sleep process for $minutes seconds"
  sleep $minutes
else
  # Run automated tests
  ${WORKSPACE}/gradlew :app:connectedCheck :app:installDebug :app:installDebugAndroidTest
fi

# Generates battery stats file
echo "Tests is running"
echo "Generating batterystats"
if [ "$TEST_TYPE_PARAM" = "manual" ]; then

  $ANDROID_HOME/platform-tools/adb shell dumpsys batterystats ${PACKAGE_ID_PARAM} > ${WORKSPACE}/batterystats.txt
else
  $ANDROID_HOME/platform-tools/adb shell dumpsys batterystats ${PACKAGE_ID_PARAM%.test} > ${WORKSPACE}/batterystats.txt
fi

$ANDROID_HOME/platform-tools/adb bugreport ${WORKSPACE}/bugreport.zip

# Stop the background processes
kill $LOGCAT_PID
kill $EMULATOR_PID