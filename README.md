# Optional

![](https://img.shields.io/maven-central/v/org.llvm4j/optional)
![](https://img.shields.io/nexus/s/org.llvm4j/optional?server=https%3A%2F%2Fs01.oss.sonatype.org)
[![](https://img.shields.io/badge/license-Apache%202.0%20with%20LLVM%20Exception-blue)](LICENSE)
[![](https://img.shields.io/gitter/room/llvm4j/community)](https://gitter.im/llvm4j/community)


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