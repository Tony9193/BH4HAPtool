package com.example.bh4haptool.update

import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Test

class ReleaseVersionComparatorTest {

    @Test
    fun compare_treatsMissingSegmentsAsZero() {
        assertEquals(0, ReleaseVersionComparator.compare("1.16", "1.16.0"))
    }

    @Test
    fun compare_handlesDoubleDigitMinorVersions() {
        assertTrue(ReleaseVersionComparator.isNewer("1.16", "1.6.0"))
    }

    @Test
    fun compare_handlesVersionPrefix() {
        assertTrue(ReleaseVersionComparator.isNewer("v1.17", "1.16"))
    }

    @Test
    fun compare_rejectsOlderVersion() {
        assertFalse(ReleaseVersionComparator.isNewer("1.15.9", "1.16"))
    }
}
