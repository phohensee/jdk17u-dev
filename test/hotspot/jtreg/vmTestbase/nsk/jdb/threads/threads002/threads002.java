/*
 * Copyright (c) 2002, 2024, Oracle and/or its affiliates. All rights reserved.
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
 *
 * @summary converted from VM Testbase nsk/jdb/threads/threads002.
 * VM Testbase keywords: [jpda, jdb]
 * VM Testbase readme:
 * DECSRIPTION
 *  This is a test for jdb 'threads' command.
 *  The debugee starts 5 'MyThreads' that are all suspended on the lock
 *  that the main thread posseses. The 'threads' command is issued
 *  at this point. The test passes if 5 suspended 'MyThreads' are reported.
 * COMMENTS
 *  This test functionally equals to nsk/jdb/threads/threads001 test
 *  and replaces it.
 *
 * @library /vmTestbase
 *          /test/lib
 * @build nsk.jdb.threads.threads002.threads002a
 * @run driver
 *      nsk.jdb.threads.threads002.threads002
 *      -arch=${os.family}-${os.simpleArch}
 *      -waittime=5
 *      -debugee.vmkind=java
 *      -transport.address=dynamic
 *      -jdb=${test.jdk}/bin/jdb
 *      -java.options="${test.vm.opts} ${test.java.opts}"
 *      -workdir=.
 *      -debugee.vmkeys="${test.vm.opts} ${test.java.opts}"
 */

package nsk.jdb.threads.threads002;

import nsk.share.*;
import nsk.share.jdb.*;

import java.io.*;
import java.util.*;

public class threads002 extends JdbTest {

    public static void main (String argv[]) {
        debuggeeClass =  DEBUGGEE_CLASS;
        firstBreak = FIRST_BREAK;
        lastBreak = LAST_BREAK;
        new threads002().runTest(argv);
    }

    static final String PACKAGE_NAME     = "nsk.jdb.threads.threads002";
    static final String TEST_CLASS       = PACKAGE_NAME + ".threads002";
    static final String DEBUGGEE_CLASS   = TEST_CLASS + "a";
    static final String FIRST_BREAK      = DEBUGGEE_CLASS + ".main";
    static final String LAST_BREAK       = DEBUGGEE_CLASS + ".lastBreak";

    static final String THREAD_NAME      = PACKAGE_NAME + ".MyThread";

    protected void runCases() {
        String[] reply;
        Paragrep grep;
        int count;
        Vector v;
        String found;

        jdb.setBreakpointInMethod(LAST_BREAK);
        jdb.receiveReplyFor(JdbCommand.cont);

        reply = jdb.receiveReplyFor(JdbCommand.threads);
        grep = new Paragrep(reply);
        count = grep.find(THREAD_NAME);
        if (count != threads002a.numThreads ) {
            failure("Unexpected number of " + THREAD_NAME + " was listed: " + count +
                "\n\texpected value: " + threads002a.numThreads);
        }

        jdb.contToExit(1);
    }
}
