## Summary

Android Compose extension.

## library

- Keyboard show/hide extensions.
- Android System UI Controller

## Download alpha - compose 1.7.0-alpha06

require android 14(target 34, compile 34)

Use gradle - compose 1.7.0-alpha06, compose compiler 1.5.11, kotlin 1.9.23

```kotlin
implementation("tech.thdev:extensions-compose-keyboard-state:1.7.0-alpha06")
implementation("tech.thdev:extensions-compose-system-ui-controller:1.7.0-alpha06")
```

## Download Stable - compose 1.6.7

Use gradle - compose 1.6.7, compose compiler 1.5.13, kotlin 1.9.23

```kotlin
implementation("tech.thdev:extensions-compose-keyboard-state:1.6.7")
implementation("tech.thdev:extensions-compose-system-ui-controller:1.6.7")
```

Release version are available in [Sonatyp's repository.](https://search.maven.org/search?q=tech.thdev)

## Use keyboard extensions

### Use scaffold modifier

```kotlin
CompositionLocalProvider(
    LocalMutableExKeyboardStateSourceOwner provides MutableExKeyboardStateSource()
) {
    Scaffold(
        modifier = Modifier
            .removeFocusWhenKeyboardIsHidden()
    ) {
    }
}
```

### when checking status

```kotlin
val keyboardState by mutableKeyboardStateSource.keyboardState()

DisposableEffect(showKeyboard) {
    // Use keyboard state
    onDispose {
    }
}
```

## Use system ui controller

```kotlin
// Remember a SystemUiController
val systemUiController = rememberExSystemUiController()

DisposableEffect(systemUiController) {
    // Update all of the system bar colors to be transparent, and use
    // dark icons if we're in light theme
    systemUiController.setSystemBarsColor(
        color = Color.Transparent,
    )

    // setStatusBarColor() and setNavigationBarColor() also exist

    onDispose {}
}
```

## Sample

![image](images/sample.png)
