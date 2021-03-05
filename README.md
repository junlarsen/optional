# Optional

Implementation of `Option<T>` and `Result<T, E>` for Kotlin.

This package provides a basic and quick implementation of the Result and 
Option types for Kotlin. We may use these types to ensure we have no 
nullables when we're calling Kotlin code from Java or Scala or to perform 
exception-free error handling.

Optional is available through Maven Central.

```groovy
dependencies {
    implementation("org.llvm4j:optional:0.1.0")
}
```

> Note: this package is mostly used internally within llvm4j's projects and 
> shared across multiple projects.

## License

Licensed under the Apache 2.0 License with LLVM exceptions