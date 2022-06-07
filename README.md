# Overview

This repository contains test app for [TuTu-ru](https://github.com/tutu-ru-mobile/internship_test).

## Contents

* [How to build](#How-To-Build)
* [Tests](#Tests)

## How To Build

To build APK with command line locate to project folder and run next command:

```shell
 ./gradlew assembleDebug
```

This will build **app-debug.apk** in **{project folder}\app\build\outputs\apk\debug** folder.

Otherwise you can build and run App with Android Studio or other IDE that supporting Gradle.

## Tests

There are two instrumentation test
in [ExampleInstrumentedTest.kt](app/src/androidTest/java/com/lolblach333/tutu/ExampleInstrumentedTest.kt)
. You can run it directly from IDE or with command line:

```shell
./gradlew connectedAndroidTest
```

Note that you need connected device to run instrumented tests.
