## Summary

Android Compose extension.

## library

- Keyboard show/hide extensions.
- Android System UI Controller
- ExWebView(View WebView).

## Download Stable - compose 25.5.0

Use gradle - compose bom 2025.05.00, compose compiler k2, kotlin 2.1.21

```kotlin
implementation("tech.thdev:extensions-compose-keyboard-state:25.5.0")
implementation("tech.thdev:extensions-compose-system-ui-controller:25.5.0")
implementation("tech.thdev:extensions-compose-web-view:25.5.0")
```

Release version are available in [Sonatyp's repository.](https://search.maven.org/search?q=tech.thdev)

## Use keyboard extensions

### Use scaffold modifier

```kotlin
Scaffold(
    modifier = Modifier
        .imePadding()
        .keyboardHide()
) {
}
```

### when checking status

```kotlin
val keyboardState by rememberKeyboardVisible()

LaunchedEffect(showKeyboard) { // Or DisposableEffect(keyboardState) if you want to react when keyboardState changes
    // Use keyboard state
}
```

## Use system ui controller

```kotlin
// Remember a SystemUiController
val systemUiController = rememberSystemUiController()

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

## Use WebView

```kotlin
ExWebView(
    /* */
)
```

## Sample

![image](images/sample.png)
