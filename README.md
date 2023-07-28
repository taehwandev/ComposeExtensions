## Summary

Modifier for handling keyboard state.

You can know the open/closed state of the keyboard.
If you register the modifier, the keyboard closes when you touch the background, and you can know the status.

- compose 1.6.0-alpha02
- compose compiler 1.5.1
- kotlin 1.9.0

## Download - compose 1.6.0-alpha02

require android 14(target 34, compile 34)

Use gradle - compose 1.6.0-alpha02, compose compiler 1.5.1, kotlin 1.9.0

```kotlin
implementation("tech.thdev:extensions-compose-keyboard-state:1.6.0-alpha02")
```

## Download - compose 1.5.0-rc01

Use gradle - compose 1.5.0-rc01, compose compiler 1.5.1, kotlin 1.9.0

```kotlin
implementation("tech.thdev:extensions-compose-keyboard-state:1.5.0-rc01")
```

## Download - compose 1.4.3

Use gradle - compose 1.4.3, compose compiler 1.4.7, kotlin 1.8.21

```kotlin
implementation("tech.thdev:extensions-compose-keyboard-state:1.4.3")
```

Release version are available in [Sonatyp's repository.](https://search.maven.org/search?q=tech.thdev)

## Use Code

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

## Sample

![image](images/sample.png)
