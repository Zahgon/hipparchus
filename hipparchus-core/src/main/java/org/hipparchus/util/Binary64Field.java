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
package org.hipparchus.util;

import java.io.Serializable;
import org.hipparchus.Field;

/**
 * The field of {@link Binary64 double precision floating-point numbers}.
 *
 * @see Binary64
 */
public class Binary64Field implements Field<Binary64>, Serializable {

    /**
     * Serializable version identifier
     */
    private static final long serialVersionUID = 20161219L;

    /**
     * Default constructor.
     */
    private Binary64Field() {
        // Do nothing
    }

    /**
     * Returns the unique instance of this class.
     *
     * @return the unique instance of this class
     */
    public static Binary64Field getInstance() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Binary64 getZero() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Binary64 getOne() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Class<Binary64> getRuntimeClass() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean equals(final Object other) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int hashCode() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    // CHECKSTYLE: stop HideUtilityClassConstructor
    /**
     * Holder for the instance.
     * <p>We use here the Initialization On Demand Holder Idiom.</p>
     */
    private static class LazyHolder {

        /**
         * Cached field instance.
         */
        private static final Binary64Field INSTANCE = new Binary64Field();
    }

    // CHECKSTYLE: resume HideUtilityClassConstructor
    /**
     * Handle deserialization of the singleton.
     * @return the singleton instance
     */
    private Object readResolve() {
        // return the singleton instance
        return LazyHolder.INSTANCE;
    }
}
