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
#if [[ $EMULATOR_STATUS -eq 1 ]]; then
echo "Emulator is ready for use"
# Unlock the Lock Screen
$ANDROID_HOME/platform-tools/adb shell input keyevent 82

# Clear and capture logcat
$ANDROID_HOME/platform-tools/adb logcat -c
$ANDROID_HOME/platform-tools/adb logcat > logcat.log &
LOGCAT_PID=$!

if [ "$1" = "manual" ]; then
  # Install app (Only for manually tests)
  ${WORKSPACE}/gradlew installDebug
  let minutes=60*$2
  echo "Sleep process for $minutes seconds"
  sleep $minutes
else
  # Run automated tests
  #./gradlew connectedAndroidTest -i
  #FOR UNIT TEST
  #${WORKSPACE}/gradlew :app:installDebug
  #${WORKSPACE}/gradlew :app:testDebugUnitTest
  #FOR INSTRUMENTATION TEST
  #${WORKSPACE}/gradlew :app:connectedAndroidTest -i
  #sleep 10
  ${WORKSPACE}/gradlew :app:connectedCheck :app:installDebug :app:installDebugAndroidTest
fi

# Generates battery stats file
echo "Generating batterystats"
if [ "$1" = "manual" ]; then
  $ANDROID_HOME/platform-tools/adb shell dumpsys batterystats com.example.batterytestapplication > ${WORKSPACE}/batterystats.txt
else
  $ANDROID_HOME/platform-tools/adb shell dumpsys batterystats com.example.batterytestapplication.test > ${WORKSPACE}/batterystats.txt
fi

$ANDROID_HOME/platform-tools/adb bugreport ${WORKSPACE}/bugreport.zip

# Stop the background processes
kill $LOGCAT_PID
kill $EMULATOR_PID
#fi