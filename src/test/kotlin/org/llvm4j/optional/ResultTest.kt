package org.llvm4j.optional

import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertDoesNotThrow
import kotlin.UnsupportedOperationException
import kotlin.test.assertEquals
import kotlin.test.assertFailsWith
import kotlin.test.assertFalse
import kotlin.test.assertTrue

class ResultTest {
    @Test fun `Results report their state correctly`() {
        val err = Err("something went wrong")
        val ok = Ok("yay")

        assertTrue { err.isErr() }
        assertTrue { ok.isOk() }
        assertFalse { err.isOk() }
        assertFalse { ok.isErr() }
    }

    @Test fun `Unwrapping results has the expected behavior`() {
        val err = Err(UnsupportedOperationException())
        val ok = Ok(true)

        assertDoesNotThrow { ok.unwrap() }
        assertDoesNotThrow { err.err() }
        assertFailsWith<IllegalStateException> { ok.err() }
        assertFailsWith<IllegalStateException> { err.unwrap() }
    }

    @Test fun `Mapping over results yield the right branch values`() {
        val err: Result<Int, String> = Err("not found")
        val ok: Result<Int, UnsupportedOperationException> = Ok(200)

        val res1 = err.mapErr { "error: $it" }
        val res2 = err.map { it + 2 }
        val res3 = ok.mapErr { RuntimeException(it) }
        val res4 = ok.map { it * 10 }

        assertTrue { res1.isErr() }
        assertTrue { res2.isErr() }
        assertTrue { res3.isOk() }
        assertTrue { res4.isOk() }

        assertEquals(Ok(2000), res4)
        assertEquals(Err("error: not found"), res1)
    }
}
