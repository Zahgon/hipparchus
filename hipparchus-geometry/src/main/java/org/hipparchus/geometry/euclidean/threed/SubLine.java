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
package org.hipparchus.geometry.euclidean.threed;

import java.util.ArrayList;
import java.util.List;
import org.hipparchus.exception.MathIllegalArgumentException;
import org.hipparchus.geometry.euclidean.oned.Interval;
import org.hipparchus.geometry.euclidean.oned.IntervalsSet;
import org.hipparchus.geometry.euclidean.oned.Vector1D;
import org.hipparchus.geometry.partitioning.Region.Location;

/**
 * This class represents a subset of a {@link Line}.
 */
public class SubLine {

    /**
     * Underlying line.
     */
    private final Line line;

    /**
     * Remaining region of the hyperplane.
     */
    private final IntervalsSet remainingRegion;

    /**
     * Simple constructor.
     * @param line underlying line
     * @param remainingRegion remaining region of the line
     */
    public SubLine(final Line line, final IntervalsSet remainingRegion) {
        this.line = line;
        this.remainingRegion = remainingRegion;
    }

    /**
     * Create a sub-line from two endpoints.
     * @param start start point
     * @param end end point
     * @param tolerance tolerance below which points are considered identical
     * @exception MathIllegalArgumentException if the points are equal
     */
    public SubLine(final Vector3D start, final Vector3D end, final double tolerance) throws MathIllegalArgumentException {
        this(new Line(start, end, tolerance), buildIntervalSet(start, end, tolerance));
    }

    /**
     * Create a sub-line from a segment.
     * @param segment single segment forming the sub-line
     * @exception MathIllegalArgumentException if the segment endpoints are equal
     */
    public SubLine(final Segment segment) throws MathIllegalArgumentException {
        this(segment.getLine(), buildIntervalSet(segment.getStart(), segment.getEnd(), segment.getLine().getTolerance()));
    }

    /**
     * Get the endpoints of the sub-line.
     * <p>
     * A subline may be any arbitrary number of disjoints segments, so the endpoints
     * are provided as a list of endpoint pairs. Each element of the list represents
     * one segment, and each segment contains a start point at index 0 and an end point
     * at index 1. If the sub-line is unbounded in the negative infinity direction,
     * the start point of the first segment will have infinite coordinates. If the
     * sub-line is unbounded in the positive infinity direction, the end point of the
     * last segment will have infinite coordinates. So a sub-line covering the whole
     * line will contain just one row and both elements of this row will have infinite
     * coordinates. If the sub-line is empty, the returned list will contain 0 segments.
     * </p>
     * @return list of segments endpoints
     */
    public List<Segment> getSegments() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * Get the intersection of the instance and another sub-line.
     * <p>
     * This method is related to the {@link Line#intersection(Line)
     * intersection} method in the {@link Line Line} class, but in addition
     * to compute the point along infinite lines, it also checks the point
     * lies on both sub-line ranges.
     * </p>
     * @param subLine other sub-line which may intersect instance
     * @param includeEndPoints if true, endpoints are considered to belong to
     * instance (i.e. they are closed sets) and may be returned, otherwise endpoints
     * are considered to not belong to instance (i.e. they are open sets) and intersection
     * occurring on endpoints lead to null being returned
     * @return the intersection point if there is one, null if the sub-lines don't intersect
     */
    public Vector3D intersection(final SubLine subLine, final boolean includeEndPoints) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * Build an interval set from two points.
     * @param start start point
     * @param end end point
     * @return an interval set
     * @param tolerance tolerance below which points are considered identical
     * @exception MathIllegalArgumentException if the points are equal
     */
    private static IntervalsSet buildIntervalSet(final Vector3D start, final Vector3D end, final double tolerance) throws MathIllegalArgumentException {
        final Line line = new Line(start, end, tolerance);
        return new IntervalsSet(line.toSubSpace(start).getX(), line.toSubSpace(end).getX(), tolerance);
    }
}
