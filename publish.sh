set -o allexport

echo "Type Archiva username:"
read -r MAVEN_PUBLISH_USERNAME
echo "Type Archiva password:"
read -s -r MAVEN_PUBLISH_PASSWORD

echo "Publishing..."

./gradlew :jtextadventure:publish
./gradlew :jtextadventure-cli:publish
./gradlew :jtextadventure-awt:publish
./gradlew :jtextadventure-android:publish

set +o allexport
