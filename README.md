# Library of Gongolierium
[![Release](https://img.shields.io/github/release/Gongoliers/Library-of-Gongolierium.svg?style=flat)](https://jitpack.io/#Gongoliers/Library-of-Gongolierium)
![](https://github.com/Gongoliers/Library-of-Gongolierium/workflows/Java%20CI/badge.svg)

A library to help teams develop more complex robot programs quicker. 

View the [Wiki](https://github.com/Gongoliers/Library-of-Gongolierium/wiki) for examples.

View the [JavaDocs](https://gongoliers.github.io/Library-of-Gongolierium/).

Up to date with the 2023 FRC build season (beta).

### Features
- Use higher level joystick, sensor, and actuator components
- Use built in math functions
- Simplify drivetrain programming with prebuilt modules

## Installation
To use the the Library of Gongolierium with Gradle projects, you can use [JitPack](https://jitpack.io/) by adding the following lines to your `build.gradle` file:

```Gradle
repositories {
    ...
    maven { url 'https://jitpack.io' }
}

dependencies {
    ...
    compile 'com.github.Gongoliers:Library-of-Gongolierium:7.0.0-beta01'
}
```

## Contributing
Please fork this repo and submit a pull request to contribute. I will review all changes and respond if they are accepted or rejected (as well as reasons, so it will be accepted). All changes should be accompanied by unit tests - unless the change focuses solely on hardware (preferably use an interface in this case to test as much as possible).

## License
This project is published under the [MIT license](LICENSE).

