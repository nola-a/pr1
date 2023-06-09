/**
 *  pr1
 *
 *  Copyright (c) 2023 Antonino Nolano. Licensed under the MIT license, as
 * follows:
 *
 *  Permission is hereby granted, free of charge, to any person obtaining a copy
 *  of this software and associated documentation files (the "Software"), to
 * deal in the Software without restriction, including without limitation the
 * rights to use, copy, modify, merge, publish, distribute, sublicense, and/or
 * sell copies of the Software, and to permit persons to whom the Software is
 *  furnished to do so, subject to the following conditions:
 *
 *  The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 *  THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 *  IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 *  FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 *  AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 *  LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING
 * FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS
 * IN THE SOFTWARE.
 */
package com.nolano.pr1.peformance;

import com.nolano.pr1.EngineV1;
import com.nolano.pr1.IEngine;
import com.nolano.pr1.Point;
import org.openjdk.jmh.annotations.Benchmark;

import java.util.Random;

public class EngineV1Benchmark {

    public Point newRandomPoint(Random random, double max, double min) {
        return Point.from(
                String.valueOf(min + random.nextDouble() * max),
                String.valueOf(min + random.nextDouble() * max));
    }

    @Benchmark
    public void testPerformance() {
        IEngine engine = new EngineV1();
        Random random = new Random();
        for (int i = 0; i < 10; ++i) {
            Point p = newRandomPoint(random, 1000, -1000);
            engine.addPoint(p);
        }
    }
}
