cd ..
gradle clean build -Pspring.profiles.active=production
mv ./build/libs/search-1.0.0-andrew.wav.jar ./search-1.0.0-andrew.wav.jar
