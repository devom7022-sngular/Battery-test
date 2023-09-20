$ANDROID_HOME/emulator/emulator -avd FirstEmulator -wipe-data -port 5790 &
EMULATOR_PID=$!


# Wait for Android to finish booting
echo "Waiting for emulator to finish booting..."
WAIT_CMD=$($ANDROID_HOME/platform-tools/adb -s emulator-5790 wait-for-device shell 'while [[ -z $(getprop sys.boot_completed) ]]; do sleep 1; done; input keyevent 82')
until $WAIT_CMD; do
 sleep 2
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

  # Run the tests (TODO)
  #./gradlew connectedAndroidTest -i

  # Install app (Only for manually tests)
  ${WORKSPACE}/gradlew installDebug
  sleep 10
  ${WORKSPACE}/gradlew testDebugUnitTest
   sleep 10

  #
  echo "Generating batterystats"
  $ANDROID_HOME/platform-tools/adb shell dumpsys batterystats com.example.batterytestapplication > ${WORKSPACE}/batterystats.txt
  $ANDROID_HOME/platform-tools/adb bugreport ${WORKSPACE}/bugreport.zip

  # Stop the background processes
  kill $LOGCAT_PID
  kill $EMULATOR_PID
#fi