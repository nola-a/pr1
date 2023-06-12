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
package com.nolano.pr1;

import java.util.*;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class EngineV1 implements IEngine {

    private static final Logger LOGGER = LoggerFactory.getLogger(EngineV1.class);
    private final List<List<Point>> lines = new ArrayList<>();
    private final Set<Point> points = new HashSet<>();
    private final List<List<Point>> tempNewLines = new ArrayList<>();
    private final Set<Point> tempPoints = new HashSet<>();

    @Override
    public synchronized void addPoint(Point newPoint) {

        LOGGER.debug("adding new point {}", newPoint);

        if (!points.add(newPoint)) {
            LOGGER.debug("the point {} already exists", newPoint);
            return;
        }

        if (lines.isEmpty()) {
            // case 0: the space is empty
            List<Point> newList = new ArrayList<>();
            newList.add(newPoint);
            lines.add(newList);
            LOGGER.debug("the space is empty, so the first point {} is added", newPoint);
            return;
        }

        for (List<Point> line: lines) {

            LOGGER.trace("parsing line {}", line);

            if (line.size() == 1) {
                // case 1: create the first line (built by 2 points)
                line.add(newPoint);
                LOGGER.debug("created the first line [{} {}]", line.get(0), line.get(1));
                continue;
            }

            if (belongsToLine(line.get(0), line.get(1), newPoint)) {
                // case 2: add point to the line
                LOGGER.debug("the point {} belongs to the line [{} {}]", newPoint, line.get(0), line.get(1));
                line.add(newPoint);
                return;
            }

            // case 3: since the point does not belong to the line then creates line.size() new lines
            for (Point p: line) {
                if (!tempPoints.add(p))
                    continue;
                List<Point> newList = new ArrayList<>();
                newList.add(newPoint);
                newList.add(p);
                tempNewLines.add(newList);
                LOGGER.debug("created the line [{} {}]", newPoint, p);
            }
        }

        lines.addAll(tempNewLines);
        tempNewLines.clear();
        tempPoints.clear();
    }

    @Override
    public Set<Point> getPoints() {
        return Collections.unmodifiableSet(points);
    }

    @Override
    public synchronized void clean() {
        LOGGER.info("clean all");
        points.clear();
        lines.clear();
    }

    @Override public List<List<Point>> getLines(int npoints) { return lines.stream().filter(n -> n.size() >= npoints).collect(Collectors.toList());
    }

    public static boolean belongsToLine(Point p, Point q, Point r) {
        // if r belongs to the line (p,q) then equation below is valid
        // (qx - px) * (ry - py) == (qy - py) * (rx - px)
        double left = (q.x - p.x) * (r.y - p.y);
        double right = (q.y - p.y) * (r.x - p.x);
        boolean check = left == right;
        LOGGER.trace("{} belongs to the line {} {} result={}", r, p, q, check);
        return check;
    }
}
