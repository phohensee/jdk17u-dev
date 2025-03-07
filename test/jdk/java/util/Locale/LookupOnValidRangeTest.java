/*
 * Copyright (c) 2016, 2023, Oracle and/or its affiliates. All rights reserved.
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS FILE HEADER.
 *
 * This code is free software; you can redistribute it and/or modify it
 * under the terms of the GNU General Public License version 2 only, as
 * published by the Free Software Foundation.
 *
 * This code is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or
 * FITNESS FOR A PARTICULAR PURPOSE.  See the GNU General Public License
 * version 2 for more details (a copy is included in the LICENSE file that
 * accompanied this code).
 *
 * You should have received a copy of the GNU General Public License version
 * 2 along with this work; if not, write to the Free Software Foundation,
 * Inc., 51 Franklin St, Fifth Floor, Boston, MA 02110-1301 USA.
 *
 * Please contact Oracle, 500 Oracle Parkway, Redwood Shores, CA 94065 USA
 * or visit www.oracle.com if you need additional information or have any
 * questions.
 */

/*
 * @test
 * @bug 8135061
 * @summary Checks that the Locale.lookup executes properly without throwing
 *          any exception for some specific language ranges
 * @run junit LookupOnValidRangeTest
 */

import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Locale;
import java.util.Locale.LanguageRange;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

public class LookupOnValidRangeTest {

    /**
     * Lookup should run without throwing any exception and return null as
     * the language range does not match with the language tag.
     */
    @Test
    public void lookupReturnNullTest() {
        List<LanguageRange> ranges = LanguageRange.parse("nv");
        Collection<Locale> locales = Collections.singleton(Locale.ENGLISH);
        try {
            Locale match = Locale.lookup(ranges, locales);
            assertNull(match);
        } catch (Exception ex) {
            throw new RuntimeException("[Locale.lookup failed on language"
                    + " range: " + ranges + " and language tags "
                    + locales + "]", ex);
        }
    }

    /**
     * Lookup should run without throwing any exception and return "nv"
     * as the matching tag.
     */
    @Test
    public void lookupReturnValueTest() {
        List<LanguageRange> ranges = LanguageRange.parse("i-navajo");
        Collection<Locale> locales = Collections.singleton(new Locale("nv"));
        try {
            Locale match = Locale.lookup(ranges, locales);
            assertEquals(match.toLanguageTag(), "nv");
        } catch (Exception ex) {
            throw new RuntimeException("[Locale.lookup failed on language"
                    + " range: " + ranges + " and language tags "
                    + locales + "]", ex);
        }
    }
}
