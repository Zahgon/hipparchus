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
package org.hipparchus.stat.descriptive.rank;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.Serializable;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import org.hipparchus.analysis.UnivariateFunction;
import org.hipparchus.analysis.interpolation.LinearInterpolator;
import org.hipparchus.analysis.interpolation.NevilleInterpolator;
import org.hipparchus.analysis.interpolation.UnivariateInterpolator;
import org.hipparchus.exception.LocalizedCoreFormats;
import org.hipparchus.exception.MathIllegalArgumentException;
import org.hipparchus.stat.descriptive.AbstractStorelessUnivariateStatistic;
import org.hipparchus.stat.descriptive.StorelessUnivariateStatistic;
import org.hipparchus.util.MathArrays;
import org.hipparchus.util.MathUtils;
import org.hipparchus.util.Precision;

/**
 * A {@link StorelessUnivariateStatistic} estimating percentiles using the
 * <a href=http://www.cs.wustl.edu/~jain/papers/ftp/psqr.pdf>P<SUP>2</SUP></a>
 * Algorithm as explained by <a href=http://www.cse.wustl.edu/~jain/>Raj
 * Jain</a> and Imrich Chlamtac in
 * <a href=http://www.cse.wustl.edu/~jain/papers/psqr.htm>P<SUP>2</SUP> Algorithm
 * for Dynamic Calculation of Quantiles and Histogram Without Storing
 * Observations</a>.
 * <p>
 * Note: This implementation is not synchronized and produces an approximate
 * result. For small samples, where data can be stored and processed in memory,
 * {@link Percentile} should be used.
 */
public class PSquarePercentile extends AbstractStorelessUnivariateStatistic implements StorelessUnivariateStatistic, Serializable {

    /**
     * The maximum array size used for psquare algorithm
     */
    private static final int PSQUARE_CONSTANT = 5;

    /**
     * A Default quantile needed in case if user prefers to use default no
     * argument constructor.
     */
    private static final double DEFAULT_QUANTILE_DESIRED = 50d;

    /**
     * Serial ID
     */
    private static final long serialVersionUID = 20150412L;

    /**
     * A decimal formatter for print convenience
     */
    private static final DecimalFormat DECIMAL_FORMAT = new DecimalFormat("00.00");

    /**
     * Initial list of 5 numbers corresponding to 5 markers. <b>NOTE:</b>watch
     * out for the add methods that are overloaded
     */
    private final List<Double> initialFive = new FixedCapacityList<>(PSQUARE_CONSTANT);

    /**
     * The quantile needed should be in range of 0-1. The constructor
     * {@link #PSquarePercentile(double)} ensures that passed in percentile is
     * divided by 100.
     */
    private final double quantile;

    /**
     * lastObservation is the last observation value/input sample. No need to
     * serialize.
     */
    private transient double lastObservation;

    /**
     * Markers is the marker collection object which comes to effect
     * only after 5 values are inserted
     */
    private PSquareMarkers markers;

    /**
     * Computed p value (i,e percentile value of data set hither to received)
     */
    private double pValue = Double.NaN;

    /**
     * Counter to count the values/observations accepted into this data set
     */
    private long countOfObservations;

    /**
     * Constructs a PSquarePercentile with the specific percentile value.
     * @param p the percentile
     * @throws MathIllegalArgumentException  if p is not greater than 0 and less
     * than or equal to 100
     */
    public PSquarePercentile(final double p) {
        if (p > 100 || p < 0) {
            throw new MathIllegalArgumentException(LocalizedCoreFormats.OUT_OF_RANGE, p, 0, 100);
        }
        // always set it within (0,1]
        this.quantile = p / 100d;
    }

    /**
     * Default constructor that assumes a {@link #DEFAULT_QUANTILE_DESIRED
     * default quantile} needed.
     */
    PSquarePercentile() {
        this(DEFAULT_QUANTILE_DESIRED);
    }

    /**
     * Copy constructor, creates a new {@code PSquarePercentile} identical
     * to the {@code original}.
     *
     * @param original the {@code PSquarePercentile} instance to copy
     * @throws org.hipparchus.exception.NullArgumentException if original is null
     */
    public PSquarePercentile(PSquarePercentile original) {
        this.quantile = original.quantile;
        if (original.markers != null) {
            this.markers = original.markers.copySelf();
        }
        this.countOfObservations = original.countOfObservations;
        this.pValue = original.pValue;
        this.initialFive.addAll(original.initialFive);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int hashCode() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * Returns true iff {@code o} is a {@code PSquarePercentile} returning the
     * same values as this for {@code getResult()} and {@code getN()} and also
     * having equal markers
     *
     * @param o object to compare
     * @return true if {@code o} is a {@code PSquarePercentile} with
     * equivalent internal state
     */
    @Override
    public boolean equals(Object o) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * {@inheritDoc}The internal state updated due to the new value in this
     * context is basically of the marker positions and computation of the
     * approximate quantile.
     *
     * @param observation the observation currently being added.
     */
    @Override
    public void increment(final double observation) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * Returns a string containing the last observation, the current estimate
     * of the quantile and all markers.
     *
     * @return string representation of state data
     */
    @Override
    public String toString() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public long getN() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public PSquarePercentile copy() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * Returns the quantile estimated by this statistic in the range [0.0-1.0]
     *
     * @return quantile estimated by {@link #getResult()}
     */
    public double quantile() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * {@inheritDoc}. This basically clears all the markers, the
     * initialFive list and sets countOfObservations to 0.
     */
    @Override
    public void clear() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public double getResult() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * Get quantile estimated by this statistic.
     * @return the quantile estimated by this statistic
     */
    public double getQuantile() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * Get maximum in the data set added to this statistic.
     * @return maximum in the data set added to this statistic
     */
    private double maximum() {
        double val = Double.NaN;
        if (markers != null) {
            val = markers.height(PSQUARE_CONSTANT);
        } else if (!initialFive.isEmpty()) {
            val = initialFive.get(initialFive.size() - 1);
        }
        return val;
    }

    /**
     * Get minimum in the data set added to this statistic.
     * @return minimum in the data set added to this statistic
     */
    private double minimum() {
        double val = Double.NaN;
        if (markers != null) {
            val = markers.height(1);
        } else if (!initialFive.isEmpty()) {
            val = initialFive.get(0);
        }
        return val;
    }

    /**
     * Markers is an encapsulation of the five markers/buckets as indicated in
     * the original works.
     */
    private static class Markers implements PSquareMarkers, Serializable {

        /**
         * Serial version id
         */
        private static final long serialVersionUID = 1L;

        /**
         * Low marker index
         */
        private static final int LOW = 2;

        /**
         * High marker index
         */
        private static final int HIGH = 4;

        /**
         * Array of 5+1 Markers (The first marker is dummy just so we
         * can match the rest of indexes [1-5] indicated in the original works
         * which follows unit based index)
         */
        private final Marker[] markerArray;

        /**
         * Constructor
         *
         * @param theMarkerArray marker array to be used, a reference to the array will be stored
         */
        private Markers(final Marker[] theMarkerArray) {
            // NOPMD - storing a reference to the array is intentional and documented here
            MathUtils.checkNotNull(theMarkerArray);
            markerArray = theMarkerArray;
            for (int i = 1; i < PSQUARE_CONSTANT; i++) {
                markerArray[i].previous(markerArray[i - 1]).next(markerArray[i + 1]).index(i);
            }
            markerArray[0].previous(markerArray[0]).next(markerArray[1]).index(0);
            markerArray[5].previous(markerArray[4]).next(markerArray[5]).index(5);
        }

        /**
         * Constructor
         *
         * @param initialFive elements required to build Marker
         * @param p quantile required to be computed
         */
        private Markers(final List<Double> initialFive, final double p) {
            this(createMarkerArray(initialFive, p));
        }

        /**
         * Creates a marker array using initial five elements and a quantile
         *
         * @param initialFive list of initial five elements
         * @param p the pth quantile
         * @return Marker array
         */
        private static Marker[] createMarkerArray(final List<Double> initialFive, final double p) {
            final int countObserved = initialFive == null ? -1 : initialFive.size();
            if (countObserved < PSQUARE_CONSTANT) {
                throw new MathIllegalArgumentException(LocalizedCoreFormats.INSUFFICIENT_OBSERVED_POINTS_IN_SAMPLE, countObserved, PSQUARE_CONSTANT);
            }
            Collections.sort(initialFive);
            return new Marker[] { // Null Marker
            new Marker(), new Marker(initialFive.get(0), 1, 0, 1), new Marker(initialFive.get(1), 1 + 2 * p, p / 2, 2), new Marker(initialFive.get(2), 1 + 4 * p, p, 3), new Marker(initialFive.get(3), 3 + 2 * p, (1 + p) / 2, 4), new Marker(initialFive.get(4), 5, 1, 5) };
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public int hashCode() {
            throw new UnsupportedOperationException("STUB: not implemented");
        }

        /**
         * {@inheritDoc}.This equals method basically checks for marker array to
         * be deep equals.
         *
         * @param o is the other object
         * @return true if the object compares with this object are equivalent
         */
        @Override
        public boolean equals(Object o) {
            throw new UnsupportedOperationException("STUB: not implemented");
        }

        /**
         * Process a data point
         *
         * @param inputDataPoint is the data point passed
         * @return computed percentile
         */
        @Override
        public double processDataPoint(final double inputDataPoint) {
            throw new UnsupportedOperationException("STUB: not implemented");
        }

        /**
         * Returns the percentile computed thus far.
         *
         * @return height of mid point marker
         */
        @Override
        public double getPercentileValue() {
            throw new UnsupportedOperationException("STUB: not implemented");
        }

        /**
         * Finds the cell where the input observation / value fits.
         *
         * @param observation the input value to be checked for
         * @return kth cell (of the markers ranging from 1-5) where observed
         *         sample fits
         */
        private int findCellAndUpdateMinMax(final double observation) {
            if (observation < height(1)) {
                markerArray[1].markerHeight = observation;
                return 1;
            } else if (observation < height(2)) {
                return 1;
            } else if (observation < height(3)) {
                return 2;
            } else if (observation < height(4)) {
                return 3;
            } else if (observation <= height(5)) {
                return 4;
            } else {
                markerArray[5].markerHeight = observation;
                return 4;
            }
        }

        /**
         * Adjust marker heights by setting quantile estimates to middle markers.
         */
        private void adjustHeightsOfMarkers() {
            for (int i = LOW; i <= HIGH; i++) {
                estimate(i);
            }
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public double estimate(final int index) {
            throw new UnsupportedOperationException("STUB: not implemented");
        }

        /**
         * Increment positions by d. Refer to algorithm paper for the
         * definition of d.
         *
         * @param d The increment value for the position
         * @param startIndex start index of the marker array
         * @param endIndex end index of the marker array
         */
        private void incrementPositions(final int d, final int startIndex, final int endIndex) {
            for (int i = startIndex; i <= endIndex; i++) {
                markerArray[i].incrementPosition(d);
            }
        }

        /**
         * Desired positions incremented by bucket width. The bucket width is
         * basically the desired increments.
         */
        private void updateDesiredPositions() {
            for (int i = 1; i < markerArray.length; i++) {
                markerArray[i].updateDesiredPosition();
            }
        }

        /**
         * Sets previous and next markers after default read is done.
         *
         * @param anInputStream the input stream to be deserialized
         * @throws ClassNotFoundException thrown when a desired class not found
         * @throws IOException thrown due to any io errors
         */
        private void readObject(ObjectInputStream anInputStream) throws ClassNotFoundException, IOException {
            // always perform the default de-serialization first
            anInputStream.defaultReadObject();
            // Build links
            for (int i = 1; i < PSQUARE_CONSTANT; i++) {
                markerArray[i].previous(markerArray[i - 1]).next(markerArray[i + 1]).index(i);
            }
            markerArray[0].previous(markerArray[0]).next(markerArray[1]).index(0);
            markerArray[5].previous(markerArray[4]).next(markerArray[5]).index(5);
        }

        /**
         * Return marker height given index
         *
         * @param markerIndex index of marker within (1,6)
         * @return marker height
         */
        @Override
        public double height(final int markerIndex) {
            throw new UnsupportedOperationException("STUB: not implemented");
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public Markers copySelf() {
            throw new UnsupportedOperationException("STUB: not implemented");
        }

        /**
         * Returns string representation of the Marker array.
         *
         * @return Markers as a string
         */
        @Override
        public String toString() {
            throw new UnsupportedOperationException("STUB: not implemented");
        }
    }

    /**
     * The class modeling the attributes of the marker of the P-square algorithm
     */
    private static class Marker implements Serializable {

        /**
         * Serial Version ID
         */
        private static final long serialVersionUID = -3575879478288538431L;

        /**
         * The marker index which is just a serial number for the marker in the
         * marker array of 5+1.
         */
        private int index;

        /**
         * The integral marker position. Refer to the variable n in the original
         * works.
         */
        private double intMarkerPosition;

        /**
         * Desired marker position. Refer to the variable n' in the original
         * works.
         */
        private double desiredMarkerPosition;

        /**
         * Marker height or the quantile. Refer to the variable q in the
         * original works.
         */
        private double markerHeight;

        /**
         * Desired marker increment. Refer to the variable dn' in the original
         * works.
         */
        private double desiredMarkerIncrement;

        /**
         * Next and previous markers for easy linked navigation in loops. this
         * is not serialized as they can be rebuilt during deserialization.
         */
        private transient Marker next;

        /**
         * The previous marker links
         */
        private transient Marker previous;

        /**
         * Nonlinear interpolator
         */
        private final UnivariateInterpolator nonLinear = new NevilleInterpolator();

        /**
         * Linear interpolator which is not serializable
         */
        private transient UnivariateInterpolator linear = new LinearInterpolator();

        /**
         * Default constructor
         */
        private Marker() {
            this.next = this.previous = this;
        }

        /**
         * Constructor of the marker with parameters
         *
         * @param heightOfMarker represent the quantile value
         * @param makerPositionDesired represent the desired marker position
         * @param markerPositionIncrement represent increments for position
         * @param markerPositionNumber represent the position number of marker
         */
        private Marker(double heightOfMarker, double makerPositionDesired, double markerPositionIncrement, double markerPositionNumber) {
            this();
            this.markerHeight = heightOfMarker;
            this.desiredMarkerPosition = makerPositionDesired;
            this.desiredMarkerIncrement = markerPositionIncrement;
            this.intMarkerPosition = markerPositionNumber;
        }

        /**
         * Sets the previous marker.
         *
         * @param previousMarker the previous marker to the current marker in
         *            the array of markers
         * @return this instance
         */
        private Marker previous(final Marker previousMarker) {
            MathUtils.checkNotNull(previousMarker);
            this.previous = previousMarker;
            return this;
        }

        /**
         * Sets the next marker.
         *
         * @param nextMarker the next marker to the current marker in the array
         *            of markers
         * @return this instance
         */
        private Marker next(final Marker nextMarker) {
            MathUtils.checkNotNull(nextMarker);
            this.next = nextMarker;
            return this;
        }

        /**
         * Sets the index of the marker.
         *
         * @param indexOfMarker the array index of the marker in marker array
         * @return this instance
         */
        private Marker index(final int indexOfMarker) {
            this.index = indexOfMarker;
            return this;
        }

        /**
         * Update desired Position with increment.
         */
        private void updateDesiredPosition() {
            desiredMarkerPosition += desiredMarkerIncrement;
        }

        /**
         * Increment Position by d.
         *
         * @param d a delta value to increment
         */
        private void incrementPosition(final int d) {
            intMarkerPosition += d;
        }

        /**
         * Difference between desired and actual position
         *
         * @return difference between desired and actual position
         */
        private double difference() {
            return desiredMarkerPosition - intMarkerPosition;
        }

        /**
         * Estimate the quantile for the current marker.
         *
         * @return estimated quantile
         */
        private double estimate() {
            final double di = difference();
            final boolean isNextHigher = next.intMarkerPosition - intMarkerPosition > 1;
            final boolean isPreviousLower = previous.intMarkerPosition - intMarkerPosition < -1;
            if (di >= 1 && isNextHigher || di <= -1 && isPreviousLower) {
                final int d = di >= 0 ? 1 : -1;
                final double[] xval = { previous.intMarkerPosition, intMarkerPosition, next.intMarkerPosition };
                final double[] yval = { previous.markerHeight, markerHeight, next.markerHeight };
                final double xD = intMarkerPosition + d;
                UnivariateFunction univariateFunction = nonLinear.interpolate(xval, yval);
                markerHeight = univariateFunction.value(xD);
                // If parabolic estimate is bad then turn linear
                if (isEstimateBad(yval, markerHeight)) {
                    int delta = xD - xval[1] > 0 ? 1 : -1;
                    final double[] xBad = { xval[1], xval[1 + delta] };
                    final double[] yBad = { yval[1], yval[1 + delta] };
                    // since d can be +/- 1
                    MathArrays.sortInPlace(xBad, yBad);
                    univariateFunction = linear.interpolate(xBad, yBad);
                    markerHeight = univariateFunction.value(xD);
                }
                incrementPosition(d);
            }
            return markerHeight;
        }

        /**
         * Check if parabolic/nonlinear estimate is bad by checking if the
         * ordinate found is beyond the y[0] and y[2].
         *
         * @param y the array to get the bounds
         * @param yD the estimate
         * @return true if yD is a bad estimate
         */
        private boolean isEstimateBad(final double[] y, final double yD) {
            return yD <= y[0] || yD >= y[2];
        }

        /**
         * {@inheritDoc}<i>This equals method checks for marker attributes and
         * as well checks if navigation pointers (next and previous) are the same
         * between this and passed in object</i>
         *
         * @param o Other object
         * @return true if this equals passed in other object o
         */
        @Override
        public boolean equals(Object o) {
            throw new UnsupportedOperationException("STUB: not implemented");
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public int hashCode() {
            throw new UnsupportedOperationException("STUB: not implemented");
        }

        /**
         * Read Object to deserialize.
         *
         * @param anInstream Stream Object data
         * @throws IOException thrown for IO Errors
         * @throws ClassNotFoundException thrown for class not being found
         */
        private void readObject(ObjectInputStream anInstream) throws ClassNotFoundException, IOException {
            anInstream.defaultReadObject();
            previous = next = this;
            linear = new LinearInterpolator();
        }

        /**
         * Copy this instance.
         * @return copy of the instance
         */
        public Marker copySelf() {
            throw new UnsupportedOperationException("STUB: not implemented");
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public String toString() {
            throw new UnsupportedOperationException("STUB: not implemented");
        }
    }

    /**
     * A simple fixed capacity list that has an upper bound to growth.
     * Once its capacity is reached, {@code add} is a no-op, returning
     * {@code false}.
     *
     * @param <E> type of the elements
     */
    private static class FixedCapacityList<E> extends ArrayList<E> implements Serializable {

        /**
         * Serialization Version Id
         */
        private static final long serialVersionUID = 2283952083075725479L;

        /**
         * Capacity of the list
         */
        private final int capacity;

        /**
         * This constructor constructs the list with given capacity and as well
         * as stores the capacity
         *
         * @param fixedCapacity the capacity to be fixed for this list
         */
        FixedCapacityList(final int fixedCapacity) {
            super(fixedCapacity);
            this.capacity = fixedCapacity;
        }

        /**
         * {@inheritDoc} In addition it checks if the {@link #size()} returns a
         * size that is within capacity and if true it adds; otherwise the list
         * contents are unchanged and {@code false} is returned.
         *
         * @return true if addition is successful and false otherwise
         */
        @Override
        public boolean add(final E e) {
            throw new UnsupportedOperationException("STUB: not implemented");
        }

        /**
         * {@inheritDoc} In addition it checks if the sum of Collection size and
         * this instance's {@link #size()} returns a value that is within
         * capacity and if true it adds the collection; otherwise the list
         * contents are unchanged and {@code false} is returned.
         *
         * @return true if addition is successful and false otherwise
         */
        @Override
        public boolean addAll(Collection<? extends E> collection) {
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
    }

    /**
     * A creation method to build Markers
     *
     * @param initialFive list of initial five elements
     * @param p the quantile desired
     * @return an instance of PSquareMarkers
     */
    public static PSquareMarkers newMarkers(final List<Double> initialFive, final double p) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * An interface that encapsulates abstractions of the
     * P-square algorithm markers as is explained in the original works. This
     * interface is exposed with protected access to help in testability.
     */
    protected interface PSquareMarkers {

        /**
         * Returns Percentile value computed thus far.
         *
         * @return percentile
         */
        double getPercentileValue();

        /**
         * A deep copy function to clone the current instance.
         *
         * @return deep copy of this instance
         */
        PSquareMarkers copySelf();

        /**
         * Returns the marker height (or percentile) of a given marker index.
         *
         * @param markerIndex is the index of marker in the marker array
         * @return percentile value of the marker index passed
         * @throws MathIllegalArgumentException in case the index is not within [1-5]
         */
        double height(int markerIndex);

        /**
         * Process a data point by moving the marker heights based on estimator.
         *
         * @param inputDataPoint is the data point passed
         * @return computed percentile
         */
        double processDataPoint(double inputDataPoint);

        /**
         * An Estimate of the percentile value of a given Marker
         *
         * @param index the marker's index in the array of markers
         * @return percentile estimate
         * @throws MathIllegalArgumentException in case if index is not within [1-5]
         */
        double estimate(int index);
    }
}
