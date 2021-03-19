package org.llvm4j.optional.testing

import org.llvm4j.optional.Option
import org.llvm4j.optional.Result

internal fun <T> assertNone(opt: Option<T>) = assert(opt.isNone()) { "Expected $opt to be None" }
internal fun <T> assertSome(opt: Option<T>) = assert(opt.isSome()) { "Expected $opt to be Some" }
internal fun <T, E> assertOk(res: Result<T, E>) = assert(res.isOk()) { "Expected $res to be Ok" }
internal fun <T, E> assertErr(res: Result<T, E>) = assert(res.isErr()) { "Expected $res to be Err" }
