/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *      https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
/*
 * This is not the original file distributed by the Apache Software Foundation
 * It has been modified by the Hipparchus project
 */
package org.hipparchus.geometry.euclidean.twod;

import org.hipparchus.util.FastMath;

/**
 * Simple container for a two-points segment.
 */
public class Segment {

    /**
     * Start point of the segment.
     */
    private final Vector2D start;

    /**
     * End point of the segment.
     */
    private final Vector2D end;

    /**
     * Line containing the segment.
     */
    private final Line line;

    /**
     * Build a segment.
     * @param start start point of the segment
     * @param end end point of the segment
     * @param tolerance of the line.
     */
    public Segment(final Vector2D start, final Vector2D end, final double tolerance) {
        this(start, end, new Line(start, end, tolerance));
    }

    /**
     * Build a segment.
     * @param start start point of the segment
     * @param end end point of the segment
     * @param line line containing the segment
     */
    public Segment(final Vector2D start, final Vector2D end, final Line line) {
        this.start = start;
        this.end = end;
        this.line = line;
    }

    /**
     * Get the start point of the segment.
     * @return start point of the segment
     */
    public Vector2D getStart() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * Get the end point of the segment.
     * @return end point of the segment
     */
    public Vector2D getEnd() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * Get the line containing the segment.
     * @return line containing the segment
     */
    public Line getLine() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * Get the length of the line segment.
     *
     * @return line segment length.
     */
    public double getLength() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * Calculates the shortest distance from a point to this line segment.
     * <p>
     * If the perpendicular extension from the point to the line does not
     * cross in the bounds of the line segment, the shortest distance to
     * the two end points will be returned.
     * </p>
     *
     * Algorithm adapted from:
     * <a href="http://www.codeguru.com/forum/printthread.php?s=cc8cf0596231f9a7dba4da6e77c29db3&amp;t=194400&amp;pp=15&amp;page=1">
     * Thread @ Codeguru</a>
     *
     * @param p to check
     * @return distance between the instance and the point
     */
    public double distance(final Vector2D p) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }
}
