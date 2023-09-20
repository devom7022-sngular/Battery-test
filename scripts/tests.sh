#!/bin/bash

#Start the emulator
$ANDROID_HOME/emulator/emulator -avd Pixel_7_API_31 -wipe-data -port 5790 &
EMULATOR_PID=$!


# Wait for Android to finish booting
echo "Waiting for emulator to finish booting..."
WAIT_CMD="$ANDROID_HOME/platform-tools/adb -s emulator-5790 wait-for-device shell getprop dev.bootcomplete"
until $WAIT_CMD; do
 sleep 2
done

EMULATOR_STATUS=$($ANDROID_HOME/platform-tools/adb -s emulator-5790 wait-for-device shell getprop dev.bootcomplete)

echo "Emulator reported that the startup process is $EMULATOR_STATUS"

if [[ $EMULATOR_STATUS -eq 1 ]]; then
  echo "Emulator is ready for use"
  # Unlock the Lock Screen
  $ANDROID_HOME/platform-tools/adb shell input keyevent 82

  # Clear and capture logcat
  $ANDROID_HOME/platform-tools/adb logcat -c
  $ANDROID_HOME/platform-tools/adb logcat > logcat.log &
  LOGCAT_PID=$!

  # Run the tests (TODO)
  #./gradlew connectedAndroidTest -i

  # Install app (Only for manually tests)
  ${WORKSPACE}/gradlew installDebug
  sleep 20

  #
  echo "Generating batterystats"
  $ANDROID_HOME/platform-tools/adb shell dumpsys batterystats com.example.batterytestapplication > ${WORKSPACE}/batterystats.txt

  # Stop the background processes
  kill $LOGCAT_PID
  kill $EMULATOR_PID
fi