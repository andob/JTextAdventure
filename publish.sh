set -o allexport

echo "Publishing..."

./gradlew :jtextadventure:publishToMavenLocal
./gradlew :jtextadventure-cli:publishToMavenLocal
./gradlew :jtextadventure-awt:publishToMavenLocal
./gradlew :jtextadventure-android:publishToMavenLocal
