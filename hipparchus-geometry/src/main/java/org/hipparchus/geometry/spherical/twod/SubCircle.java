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
package org.hipparchus.geometry.spherical.twod;

import org.hipparchus.geometry.euclidean.threed.Vector3D;
import org.hipparchus.geometry.partitioning.AbstractSubHyperplane;
import org.hipparchus.geometry.partitioning.Region;
import org.hipparchus.geometry.spherical.oned.Arc;
import org.hipparchus.geometry.spherical.oned.ArcsSet;
import org.hipparchus.geometry.spherical.oned.LimitAngle;
import org.hipparchus.geometry.spherical.oned.S1Point;
import org.hipparchus.geometry.spherical.oned.Sphere1D;
import org.hipparchus.geometry.spherical.oned.SubLimitAngle;
import org.hipparchus.util.FastMath;

/**
 * This class represents a sub-hyperplane for {@link Circle}.
 */
public class SubCircle extends AbstractSubHyperplane<Sphere2D, S2Point, Circle, SubCircle, Sphere1D, S1Point, LimitAngle, SubLimitAngle> {

    /**
     * Simple constructor.
     * @param hyperplane underlying hyperplane
     * @param remainingRegion remaining region of the hyperplane
     */
    public SubCircle(final Circle hyperplane, final Region<Sphere1D, S1Point, LimitAngle, SubLimitAngle> remainingRegion) {
        super(hyperplane, remainingRegion);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected SubCircle buildNew(final Circle hyperplane, final Region<Sphere1D, S1Point, LimitAngle, SubLimitAngle> remainingRegion) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public S2Point getInteriorPoint() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public SplitSubHyperplane<Sphere2D, S2Point, Circle, SubCircle> split(final Circle hyperplane) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }
}
