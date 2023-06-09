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

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class EngineV1Benchmark {

    private List<Point> points() {
        List<Point> points = new ArrayList<>();
        points.add(Point.from("-264.81584621972024","-307.2546848857214"));
        points.add(Point.from("-250.98833514127273","-867.8365807754062"));
        points.add(Point.from("-311.08953689643374","-39.84129983296975"));
        points.add(Point.from("-37.94272842602675","-697.5852178219161"));
        points.add(Point.from("-155.7279273993845","-908.2169228200199"));
        points.add(Point.from("-941.786653181169","-543.1338712283853"));
        points.add(Point.from("-357.20619687441535","-318.64918963166383"));
        points.add(Point.from("-910.0284043510702","-311.4489322851374"));
        points.add(Point.from("-116.70465460538117","-332.22180707283474"));
        points.add(Point.from("-773.7753484890891","-578.0853769353632"));
        return points;
    }

    @Benchmark
    public void testPerformance() {
        IEngine engine = new EngineV1();
        for (Point p: points()) {
            engine.addPoint(p);
        }
    }
}
