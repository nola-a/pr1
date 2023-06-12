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
import com.nolano.pr1.EngineV1;
import com.nolano.pr1.IEngine;
import com.nolano.pr1.Point;
import org.junit.Before;
import org.junit.Test;
import org.junit.Assert;

import java.util.List;
import java.util.Random;

public class EngineTest {

    private final IEngine engine = new EngineV1();
    private final Random random = new Random();

    @Before
    public void clean() {
        engine.clean();
    }

    @Test
    public void testEmpty() {
        Assert.assertTrue(engine.getPoints().isEmpty());
    }

    @Test
    public void testOne() {
        engine.addPoint(Point.from("0000", "0000"));
        Assert.assertEquals(1, engine.getPoints().size());
    }

    @Test
    public void testOneLine() {
        engine.addPoint(Point.from("0000", "0000"));
        engine.addPoint(Point.from("123.89284", "123.89284"));
        engine.addPoint(Point.from("456", "456"));
        Assert.assertEquals(3, engine.getPoints().size());
        Assert.assertEquals(1, engine.getLines(3).size());
        Assert.assertEquals(3, engine.getLines(3).get(0).size());
    }

    @Test
    public void testFourLine() {
        engine.addPoint(Point.from("0000", "0000"));
        engine.addPoint(Point.from("123", "123"));
        engine.addPoint(Point.from("456.123", "456.123"));
        engine.addPoint(Point.from("5858", "456"));
        Assert.assertEquals(4, engine.getPoints().size());
        Assert.assertEquals(1, engine.getLines(3).size());
        Assert.assertEquals(4, engine.getLines(2).size());
        Assert.assertEquals(3, engine.getLines(2).get(0).size());
    }

    public Point newRandomPoint(double max, double min) {
        return Point.from(
                String.valueOf(min + random.nextDouble() * max),
                String.valueOf(min + random.nextDouble() * max));
    }

    @Test
    public void testCoherence() {

        // load random points
        for (int i = 0; i < 1000; ++i) {
            Point p = newRandomPoint(1000, -1000);
            engine.addPoint(p);
        }

        // take all the lines
        List<List<Point>> lines = engine.getLines(0);

        //take a random point from one line and make sure that it doesn't belong to other lines
        int randomIndex = random.nextInt(lines.size());
        Point testPoint = lines.get(randomIndex).get(0);

        for (int i = 0; i < lines.size(); ++i) {
            if (lines.get(i).size() >= 2) {
                if (i != randomIndex&& !testPoint.equals(lines.get(i).get(0)) && !testPoint.equals(lines.get(i).get(1))) {
                    Assert.assertFalse(String.format("expected the point %s does not belong to the line [%s %s]", testPoint, lines.get(i).get(0), lines.get(i).get(1)), EngineV1.belongsToLine(lines.get(i).get(0), lines.get(i).get(1), testPoint));
                } else {
                    Assert.assertTrue(String.format("expected the point %s belongs to the line [%s %s]", testPoint, lines.get(i).get(0), lines.get(i).get(1)), EngineV1.belongsToLine(lines.get(i).get(0), lines.get(i).get(1), testPoint));
                }
            }
        }
    }
}
