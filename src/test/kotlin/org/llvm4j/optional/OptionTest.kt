package org.llvm4j.optional

import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertDoesNotThrow
import kotlin.test.assertEquals
import kotlin.test.assertFailsWith
import kotlin.test.assertFalse
import kotlin.test.assertTrue

class OptionTest {
    @Test fun `Options report their presence correctly`() {
        val some = Some(100)
        val none = None
        val maybe = Option.of(42)

        assertTrue { some.isSome() }
        assertTrue { none.isNone() }
        assertTrue { maybe.isSome() }
        assertFalse { some.isNone() }
        assertFalse { none.isSome() }
        assertFalse { maybe.isNone() }
    }

    @Test fun `Unwrapping options has the expected behavior`() {
        val none = None
        val some = Some('a')

        assertDoesNotThrow {
            some.unwrap()
        }

        assertFailsWith<IllegalStateException> {
            none.unwrap()
        }
    }

    @Test fun `Mapping over options yield the right value`() {
        val some: Option<Int> = Some(4)
        val none: Option<Int> = None

        val res1 = some.map { it * 2 }
        val res2 = none.map { it + 3 }

        assertTrue { res1.isSome() }
        assertTrue { res2.isNone() }

        assertEquals(None, res2)
        assertEquals(Some(8), res1)
    }

    @Test fun `Conversion to Java optionals`() {
        val some = Some(true)
        val none = None

        val opt1 = Option.toJavaOptional(some)
        val opt2 = Option.toJavaOptional(none)

        assertTrue { opt1.isPresent }
        assertFalse { opt2.isPresent }
    }
}