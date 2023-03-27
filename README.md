## Summary

Modifier for handling keyboard state.

You can know the open/closed state of the keyboard.
If you register the modifier, the keyboard closes when you touch the background, and you can know the status.

- compose 1.5.0-alpha01
- compose compiler 1.4.4
- kotlin 1.8.10

## Download - compose 1.5.0-alpha01

Use gradle - compose 1.5.0-alpha01, compose compiler 1.4.4, kotlin 1.8.10

```groovy
implementation "tech.thdev:extensions-compose-keyboard-state:1.5.0-alpha01"
```

Use gradle.kts

```kotlin
implementation("tech.thdev:extensions-compose-keyboard-state:1.5.0-alpha01")
```

## Download - compose 1.4.0

Use gradle - compose 1.4.0, compose compiler 1.4.3, kotlin 1.8.10

```groovy
implementation "tech.thdev:extensions-compose-keyboard-state:1.4.0"
```

Use gradle.kts

```kotlin
implementation("tech.thdev:extensions-compose-keyboard-state:1.4.0")
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
