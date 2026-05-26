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
package org.hipparchus.linear;

import java.util.Iterator;
import java.util.NoSuchElementException;
import org.hipparchus.analysis.FunctionUtils;
import org.hipparchus.analysis.UnivariateFunction;
import org.hipparchus.analysis.function.Add;
import org.hipparchus.analysis.function.Divide;
import org.hipparchus.analysis.function.Multiply;
import org.hipparchus.exception.LocalizedCoreFormats;
import org.hipparchus.exception.MathIllegalArgumentException;
import org.hipparchus.exception.MathRuntimeException;
import org.hipparchus.util.FastMath;

/**
 * Class defining a real-valued vector with basic algebraic operations.
 * <p>
 * vector element indexing is 0-based -- e.g., {@code getEntry(0)}
 * returns the first element of the vector.
 * </p>
 * <p>
 * The {@code code map} and {@code mapToSelf} methods operate
 * on vectors element-wise, i.e. they perform the same operation (adding a scalar,
 * applying a function ...) on each element in turn. The {@code map}
 * versions create a new vector to hold the result and do not change the instance.
 * The {@code mapToSelf} version uses the instance itself to store the
 * results, so the instance is changed by this method. In all cases, the result
 * vector is returned by the methods, allowing the <i>fluent API</i>
 * style, like this:
 * </p>
 * <pre>
 *   RealVector result = v.mapAddToSelf(3.4).mapToSelf(new Tan()).mapToSelf(new Power(2.3));
 * </pre>
 */
public abstract class RealVector {

    /**
     * Empty constructor.
     * <p>
     * This constructor is not strictly necessary, but it prevents spurious
     * javadoc warnings with JDK 18 and later.
     * </p>
     * @since 3.0
     */
    protected RealVector() {
        // NOPMD - unnecessary constructor added intentionally to make javadoc happy
        // nothing to do
    }

    /**
     * Returns the size of the vector.
     *
     * @return the size of this vector.
     */
    public abstract int getDimension();

    /**
     * Return the entry at the specified index.
     *
     * @param index Index location of entry to be fetched.
     * @return the vector entry at {@code index}.
     * @throws MathIllegalArgumentException if the index is not valid.
     * @see #setEntry(int, double)
     */
    public abstract double getEntry(int index) throws MathIllegalArgumentException;

    /**
     * Set a single element.
     *
     * @param index element index.
     * @param value new value for the element.
     * @throws MathIllegalArgumentException if the index is not valid.
     * @see #getEntry(int)
     */
    public abstract void setEntry(int index, double value) throws MathIllegalArgumentException;

    /**
     * Change an entry at the specified index.
     *
     * @param index Index location of entry to be set.
     * @param increment Value to add to the vector entry.
     * @throws MathIllegalArgumentException if the index is not valid.
     */
    public void addToEntry(int index, double increment) throws MathIllegalArgumentException {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * Construct a new vector by appending a vector to this vector.
     *
     * @param v vector to append to this one.
     * @return a new vector.
     */
    public abstract RealVector append(RealVector v);

    /**
     * Construct a new vector by appending a double to this vector.
     *
     * @param d double to append.
     * @return a new vector.
     */
    public abstract RealVector append(double d);

    /**
     * Get a subvector from consecutive elements.
     *
     * @param index index of first element.
     * @param n number of elements to be retrieved.
     * @return a vector containing n elements.
     * @throws MathIllegalArgumentException if the index is not valid.
     * @throws MathIllegalArgumentException if the number of elements is not positive.
     */
    public abstract RealVector getSubVector(int index, int n) throws MathIllegalArgumentException;

    /**
     * Set a sequence of consecutive elements.
     *
     * @param index index of first element to be set.
     * @param v vector containing the values to set.
     * @throws MathIllegalArgumentException if the index is not valid.
     */
    public abstract void setSubVector(int index, RealVector v) throws MathIllegalArgumentException;

    /**
     * Check whether any coordinate of this vector is {@code NaN}.
     *
     * @return {@code true} if any coordinate of this vector is {@code NaN},
     * {@code false} otherwise.
     */
    public abstract boolean isNaN();

    /**
     * Check whether any coordinate of this vector is infinite and none are {@code NaN}.
     *
     * @return {@code true} if any coordinate of this vector is infinite and
     * none are {@code NaN}, {@code false} otherwise.
     */
    public abstract boolean isInfinite();

    /**
     * Check if instance and specified vectors have the same dimension.
     *
     * @param v Vector to compare instance with.
     * @throws MathIllegalArgumentException if the vectors do not
     * have the same dimension.
     */
    protected void checkVectorDimensions(RealVector v) throws MathIllegalArgumentException {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * Check if instance dimension is equal to some expected value.
     *
     * @param n Expected dimension.
     * @throws MathIllegalArgumentException if the dimension is
     * inconsistent with the vector size.
     */
    protected void checkVectorDimensions(int n) throws MathIllegalArgumentException {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * Check if an index is valid.
     *
     * @param index Index to check.
     * @exception MathIllegalArgumentException if {@code index} is not valid.
     */
    protected void checkIndex(final int index) throws MathIllegalArgumentException {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * Checks that the indices of a subvector are valid.
     *
     * @param start the index of the first entry of the subvector
     * @param end the index of the last entry of the subvector (inclusive)
     * @throws MathIllegalArgumentException if {@code start} of {@code end} are not valid
     * @throws MathIllegalArgumentException if {@code end < start}
     */
    protected void checkIndices(final int start, final int end) throws MathIllegalArgumentException {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * Compute the sum of this vector and {@code v}.
     * Returns a new vector. Does not change instance data.
     *
     * @param v Vector to be added.
     * @return {@code this} + {@code v}.
     * @throws MathIllegalArgumentException if {@code v} is not the same size as
     * {@code this} vector.
     */
    public RealVector add(RealVector v) throws MathIllegalArgumentException {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * Subtract {@code v} from this vector.
     * Returns a new vector. Does not change instance data.
     *
     * @param v Vector to be subtracted.
     * @return {@code this} - {@code v}.
     * @throws MathIllegalArgumentException if {@code v} is not the same size as
     * {@code this} vector.
     */
    public RealVector subtract(RealVector v) throws MathIllegalArgumentException {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * Add a value to each entry.
     * Returns a new vector. Does not change instance data.
     *
     * @param d Value to be added to each entry.
     * @return {@code this} + {@code d}.
     */
    public RealVector mapAdd(double d) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * Add a value to each entry.
     * The instance is changed in-place.
     *
     * @param d Value to be added to each entry.
     * @return {@code this}.
     */
    public RealVector mapAddToSelf(double d) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * Returns a (deep) copy of this vector.
     *
     * @return a vector copy.
     */
    public abstract RealVector copy();

    /**
     * Compute the dot product of this vector with {@code v}.
     *
     * @param v Vector with which dot product should be computed
     * @return the scalar dot product between this instance and {@code v}.
     * @throws MathIllegalArgumentException if {@code v} is not the same size as
     * {@code this} vector.
     */
    public double dotProduct(RealVector v) throws MathIllegalArgumentException {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * Computes the cosine of the angle between this vector and the
     * argument.
     *
     * @param v Vector.
     * @return the cosine of the angle between this vector and {@code v}.
     * @throws MathRuntimeException if {@code this} or {@code v} is the null
     * vector
     * @throws MathIllegalArgumentException if the dimensions of {@code this} and
     * {@code v} do not match
     */
    public double cosine(RealVector v) throws MathIllegalArgumentException, MathRuntimeException {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * Element-by-element division.
     *
     * @param v Vector by which instance elements must be divided.
     * @return a vector containing this[i] / v[i] for all i.
     * @throws MathIllegalArgumentException if {@code v} is not the same size as
     * {@code this} vector.
     */
    public abstract RealVector ebeDivide(RealVector v) throws MathIllegalArgumentException;

    /**
     * Element-by-element multiplication.
     *
     * @param v Vector by which instance elements must be multiplied
     * @return a vector containing this[i] * v[i] for all i.
     * @throws MathIllegalArgumentException if {@code v} is not the same size as
     * {@code this} vector.
     */
    public abstract RealVector ebeMultiply(RealVector v) throws MathIllegalArgumentException;

    /**
     * Distance between two vectors.
     * <p>This method computes the distance consistent with the
     * L<sub>2</sub> norm, i.e. the square root of the sum of
     * element differences, or Euclidean distance.</p>
     *
     * @param v Vector to which distance is requested.
     * @return the distance between two vectors.
     * @throws MathIllegalArgumentException if {@code v} is not the same size as
     * {@code this} vector.
     * @see #getL1Distance(RealVector)
     * @see #getLInfDistance(RealVector)
     * @see #getNorm()
     */
    public double getDistance(RealVector v) throws MathIllegalArgumentException {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * Returns the L<sub>2</sub> norm of the vector.
     * <p>The L<sub>2</sub> norm is the root of the sum of
     * the squared elements.</p>
     *
     * @return the norm.
     * @see #getL1Norm()
     * @see #getLInfNorm()
     * @see #getDistance(RealVector)
     */
    public double getNorm() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * Returns the L<sub>1</sub> norm of the vector.
     * <p>The L<sub>1</sub> norm is the sum of the absolute
     * values of the elements.</p>
     *
     * @return the norm.
     * @see #getNorm()
     * @see #getLInfNorm()
     * @see #getL1Distance(RealVector)
     */
    public double getL1Norm() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * Returns the L<sub>&infin;</sub> norm of the vector.
     * <p>The L<sub>&infin;</sub> norm is the max of the absolute
     * values of the elements.</p>
     *
     * @return the norm.
     * @see #getNorm()
     * @see #getL1Norm()
     * @see #getLInfDistance(RealVector)
     */
    public double getLInfNorm() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * Distance between two vectors.
     * <p>This method computes the distance consistent with
     * L<sub>1</sub> norm, i.e. the sum of the absolute values of
     * the elements differences.</p>
     *
     * @param v Vector to which distance is requested.
     * @return the distance between two vectors.
     * @throws MathIllegalArgumentException if {@code v} is not the same size as
     * {@code this} vector.
     */
    public double getL1Distance(RealVector v) throws MathIllegalArgumentException {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * Distance between two vectors.
     * <p>This method computes the distance consistent with
     * L<sub>&infin;</sub> norm, i.e. the max of the absolute values of
     * element differences.</p>
     *
     * @param v Vector to which distance is requested.
     * @return the distance between two vectors.
     * @throws MathIllegalArgumentException if {@code v} is not the same size as
     * {@code this} vector.
     * @see #getDistance(RealVector)
     * @see #getL1Distance(RealVector)
     * @see #getLInfNorm()
     */
    public double getLInfDistance(RealVector v) throws MathIllegalArgumentException {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * Get the index of the minimum entry.
     *
     * @return the index of the minimum entry or -1 if vector length is 0
     * or all entries are {@code NaN}.
     */
    public int getMinIndex() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * Get the value of the minimum entry.
     *
     * @return the value of the minimum entry or {@code NaN} if all
     * entries are {@code NaN}.
     */
    public double getMinValue() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * Get the index of the maximum entry.
     *
     * @return the index of the maximum entry or -1 if vector length is 0
     * or all entries are {@code NaN}
     */
    public int getMaxIndex() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * Get the value of the maximum entry.
     *
     * @return the value of the maximum entry or {@code NaN} if all
     * entries are {@code NaN}.
     */
    public double getMaxValue() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * Multiply each entry by the argument. Returns a new vector.
     * Does not change instance data.
     *
     * @param d Multiplication factor.
     * @return {@code this} * {@code d}.
     */
    public RealVector mapMultiply(double d) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * Multiply each entry.
     * The instance is changed in-place.
     *
     * @param d Multiplication factor.
     * @return {@code this}.
     */
    public RealVector mapMultiplyToSelf(double d) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * Subtract a value from each entry. Returns a new vector.
     * Does not change instance data.
     *
     * @param d Value to be subtracted.
     * @return {@code this} - {@code d}.
     */
    public RealVector mapSubtract(double d) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * Subtract a value from each entry.
     * The instance is changed in-place.
     *
     * @param d Value to be subtracted.
     * @return {@code this}.
     */
    public RealVector mapSubtractToSelf(double d) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * Divide each entry by the argument. Returns a new vector.
     * Does not change instance data.
     *
     * @param d Value to divide by.
     * @return {@code this} / {@code d}.
     */
    public RealVector mapDivide(double d) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * Divide each entry by the argument.
     * The instance is changed in-place.
     *
     * @param d Value to divide by.
     * @return {@code this}.
     */
    public RealVector mapDivideToSelf(double d) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * Compute the outer product.
     *
     * @param v Vector with which outer product should be computed.
     * @return the matrix outer product between this instance and {@code v}.
     */
    public RealMatrix outerProduct(RealVector v) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * Find the orthogonal projection of this vector onto another vector.
     *
     * @param v vector onto which instance must be projected.
     * @return projection of the instance onto {@code v}.
     * @throws MathIllegalArgumentException if {@code v} is not the same size as
     * {@code this} vector.
     * @throws MathRuntimeException if {@code this} or {@code v} is the null
     * vector
     */
    public RealVector projection(final RealVector v) throws MathIllegalArgumentException, MathRuntimeException {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * Set all elements to a single value.
     *
     * @param value Single value to set for all elements.
     */
    public void set(double value) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * Convert the vector to an array of {@code double}s.
     * The array is independent from this vector data: the elements
     * are copied.
     *
     * @return an array containing a copy of the vector elements.
     */
    public double[] toArray() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * Creates a unit vector pointing in the direction of this vector.
     * The instance is not changed by this method.
     *
     * @return a unit vector pointing in direction of this vector.
     * @throws MathRuntimeException if the norm is zero.
     */
    public RealVector unitVector() throws MathRuntimeException {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * Converts this vector into a unit vector.
     * The instance itself is changed by this method.
     *
     * @throws MathRuntimeException if the norm is zero.
     */
    public void unitize() throws MathRuntimeException {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * Create a sparse iterator over the vector, which may omit some entries.
     * The ommitted entries are either exact zeroes (for dense implementations)
     * or are the entries which are not stored (for real sparse vectors).
     * No guarantees are made about order of iteration.
     *
     * <p>Note: derived classes are required to return an {@link Iterator} that
     * returns non-null {@link Entry} objects as long as {@link Iterator#hasNext()}
     * returns {@code true}.</p>
     *
     * @return a sparse iterator.
     */
    public Iterator<Entry> sparseIterator() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * Generic dense iterator. Iteration is in increasing order
     * of the vector index.
     *
     * <p>Note: derived classes are required to return an {@link Iterator} that
     * returns non-null {@link Entry} objects as long as {@link Iterator#hasNext()}
     * returns {@code true}.</p>
     *
     * @return a dense iterator.
     */
    public Iterator<Entry> iterator() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * Acts as if implemented as:
     * <pre>
     *  return copy().mapToSelf(function);
     * </pre>
     * Returns a new vector. Does not change instance data.
     *
     * @param function Function to apply to each entry.
     * @return a new vector.
     */
    public RealVector map(UnivariateFunction function) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * Acts as if it is implemented as:
     * <pre>
     *  Entry e = null;
     *  for(Iterator&lt;Entry&gt; it = iterator(); it.hasNext(); e = it.next()) {
     *      e.setValue(function.value(e.getValue()));
     *  }
     * </pre>
     * Entries of this vector are modified in-place by this method.
     *
     * @param function Function to apply to each entry.
     * @return a reference to this vector.
     */
    public RealVector mapToSelf(UnivariateFunction function) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * Returns a new vector representing {@code a * this + b * y}, the linear
     * combination of {@code this} and {@code y}.
     * Returns a new vector. Does not change instance data.
     *
     * @param a Coefficient of {@code this}.
     * @param b Coefficient of {@code y}.
     * @param y Vector with which {@code this} is linearly combined.
     * @return a vector containing {@code a * this[i] + b * y[i]} for all
     * {@code i}.
     * @throws MathIllegalArgumentException if {@code y} is not the same size as
     * {@code this} vector.
     */
    public RealVector combine(double a, double b, RealVector y) throws MathIllegalArgumentException {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * Updates {@code this} with the linear combination of {@code this} and
     * {@code y}.
     *
     * @param a Weight of {@code this}.
     * @param b Weight of {@code y}.
     * @param y Vector with which {@code this} is linearly combined.
     * @return {@code this}, with components equal to
     * {@code a * this[i] + b * y[i]} for all {@code i}.
     * @throws MathIllegalArgumentException if {@code y} is not the same size as
     * {@code this} vector.
     */
    public RealVector combineToSelf(double a, double b, RealVector y) throws MathIllegalArgumentException {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * Visits (but does not alter) all entries of this vector in default order
     * (increasing index).
     *
     * @param visitor the visitor to be used to process the entries of this
     * vector
     * @return the value returned by {@link RealVectorPreservingVisitor#end()}
     * at the end of the walk
     */
    public double walkInDefaultOrder(final RealVectorPreservingVisitor visitor) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * Visits (but does not alter) some entries of this vector in default order
     * (increasing index).
     *
     * @param visitor visitor to be used to process the entries of this vector
     * @param start the index of the first entry to be visited
     * @param end the index of the last entry to be visited (inclusive)
     * @return the value returned by {@link RealVectorPreservingVisitor#end()}
     * at the end of the walk
     * @throws MathIllegalArgumentException if {@code end < start}.
     * @throws MathIllegalArgumentException if the indices are not valid.
     */
    public double walkInDefaultOrder(final RealVectorPreservingVisitor visitor, final int start, final int end) throws MathIllegalArgumentException {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * Visits (but does not alter) all entries of this vector in optimized
     * order. The order in which the entries are visited is selected so as to
     * lead to the most efficient implementation; it might depend on the
     * concrete implementation of this abstract class.
     *
     * @param visitor the visitor to be used to process the entries of this
     * vector
     * @return the value returned by {@link RealVectorPreservingVisitor#end()}
     * at the end of the walk
     */
    public double walkInOptimizedOrder(final RealVectorPreservingVisitor visitor) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * Visits (but does not alter) some entries of this vector in optimized
     * order. The order in which the entries are visited is selected so as to
     * lead to the most efficient implementation; it might depend on the
     * concrete implementation of this abstract class.
     *
     * @param visitor visitor to be used to process the entries of this vector
     * @param start the index of the first entry to be visited
     * @param end the index of the last entry to be visited (inclusive)
     * @return the value returned by {@link RealVectorPreservingVisitor#end()}
     * at the end of the walk
     * @throws MathIllegalArgumentException if {@code end < start}.
     * @throws MathIllegalArgumentException if the indices are not valid.
     */
    public double walkInOptimizedOrder(final RealVectorPreservingVisitor visitor, final int start, final int end) throws MathIllegalArgumentException {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * Visits (and possibly alters) all entries of this vector in default order
     * (increasing index).
     *
     * @param visitor the visitor to be used to process and modify the entries
     * of this vector
     * @return the value returned by {@link RealVectorChangingVisitor#end()}
     * at the end of the walk
     */
    public double walkInDefaultOrder(final RealVectorChangingVisitor visitor) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * Visits (and possibly alters) some entries of this vector in default order
     * (increasing index).
     *
     * @param visitor visitor to be used to process the entries of this vector
     * @param start the index of the first entry to be visited
     * @param end the index of the last entry to be visited (inclusive)
     * @return the value returned by {@link RealVectorChangingVisitor#end()}
     * at the end of the walk
     * @throws MathIllegalArgumentException if {@code end < start}.
     * @throws MathIllegalArgumentException if the indices are not valid.
     */
    public double walkInDefaultOrder(final RealVectorChangingVisitor visitor, final int start, final int end) throws MathIllegalArgumentException {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * Visits (and possibly alters) all entries of this vector in optimized
     * order. The order in which the entries are visited is selected so as to
     * lead to the most efficient implementation; it might depend on the
     * concrete implementation of this abstract class.
     *
     * @param visitor the visitor to be used to process the entries of this
     * vector
     * @return the value returned by {@link RealVectorChangingVisitor#end()}
     * at the end of the walk
     */
    public double walkInOptimizedOrder(final RealVectorChangingVisitor visitor) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * Visits (and possibly change) some entries of this vector in optimized
     * order. The order in which the entries are visited is selected so as to
     * lead to the most efficient implementation; it might depend on the
     * concrete implementation of this abstract class.
     *
     * @param visitor visitor to be used to process the entries of this vector
     * @param start the index of the first entry to be visited
     * @param end the index of the last entry to be visited (inclusive)
     * @return the value returned by {@link RealVectorChangingVisitor#end()}
     * at the end of the walk
     * @throws MathIllegalArgumentException if {@code end < start}.
     * @throws MathIllegalArgumentException if the indices are not valid.
     */
    public double walkInOptimizedOrder(final RealVectorChangingVisitor visitor, final int start, final int end) throws MathIllegalArgumentException {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * An entry in the vector.
     */
    public class Entry {

        /**
         * Index of this entry.
         */
        private int index;

        /**
         * Simple constructor.
         */
        public Entry() {
            setIndex(0);
        }

        /**
         * Get the value of the entry.
         *
         * @return the value of the entry.
         */
        public double getValue() {
            throw new UnsupportedOperationException("STUB: not implemented");
        }

        /**
         * Set the value of the entry.
         *
         * @param value New value for the entry.
         */
        public void setValue(double value) {
            throw new UnsupportedOperationException("STUB: not implemented");
        }

        /**
         * Get the index of the entry.
         *
         * @return the index of the entry.
         */
        public int getIndex() {
            throw new UnsupportedOperationException("STUB: not implemented");
        }

        /**
         * Set the index of the entry.
         *
         * @param index New index for the entry.
         */
        public void setIndex(int index) {
            throw new UnsupportedOperationException("STUB: not implemented");
        }
    }

    /**
     * <p>
     * Test for the equality of two real vectors. If all coordinates of two real
     * vectors are exactly the same, and none are {@code NaN}, the two real
     * vectors are considered to be equal. {@code NaN} coordinates are
     * considered to affect globally the vector and be equals to each other -
     * i.e, if either (or all) coordinates of the real vector are equal to
     * {@code NaN}, the real vector is equal to a vector with all {@code NaN}
     * coordinates.
     * </p>
     * <p>
     * This method <em>must</em> be overriden by concrete subclasses of
     * {@link RealVector} (the current implementation throws an exception).
     * </p>
     *
     * @param other Object to test for equality.
     * @return {@code true} if two vector objects are equal, {@code false} if
     * {@code other} is null, not an instance of {@code RealVector}, or
     * not equal to this {@code RealVector} instance.
     * @throws MathRuntimeException if this method is not
     * overridden.
     */
    @Override
    public boolean equals(Object other) throws MathRuntimeException {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * {@inheritDoc}. This method <em>must</em> be overriden by concrete
     * subclasses of {@link RealVector} (current implementation throws an
     * exception).
     *
     * @throws MathRuntimeException if this method is not
     * overridden.
     */
    @Override
    public int hashCode() throws MathRuntimeException {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * This class should rarely be used, but is here to provide
     * a default implementation of sparseIterator(), which is implemented
     * by walking over the entries, skipping those that are zero.
     *
     * Concrete subclasses which are SparseVector implementations should
     * make their own sparse iterator, rather than using this one.
     *
     * This implementation might be useful for ArrayRealVector, when expensive
     * operations which preserve the default value are to be done on the entries,
     * and the fraction of non-default values is small (i.e. someone took a
     * SparseVector, and passed it into the copy-constructor of ArrayRealVector)
     */
    protected class SparseEntryIterator implements Iterator<Entry> {

        /**
         * Dimension of the vector.
         */
        private final int dim;

        /**
         * Last entry returned by {@link #next()}.
         */
        private Entry current;

        /**
         * Next entry for {@link #next()} to return.
         */
        private Entry next;

        /**
         * Simple constructor.
         */
        protected SparseEntryIterator() {
            dim = getDimension();
            current = new Entry();
            next = new Entry();
            if (next.getValue() == 0) {
                advance(next);
            }
        }

        /**
         * Advance an entry up to the next nonzero one.
         *
         * @param e entry to advance.
         */
        protected void advance(Entry e) {
            throw new UnsupportedOperationException("STUB: not implemented");
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public boolean hasNext() {
            throw new UnsupportedOperationException("STUB: not implemented");
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public Entry next() {
            throw new UnsupportedOperationException("STUB: not implemented");
        }

        /**
         * {@inheritDoc}
         *
         * @throws MathRuntimeException in all circumstances.
         */
        @Override
        public void remove() throws MathRuntimeException {
            throw new UnsupportedOperationException("STUB: not implemented");
        }
    }

    /**
     * Returns an unmodifiable view of the specified vector.
     * The returned vector has read-only access. An attempt to modify it will
     * result in a {@link MathRuntimeException}. However, the
     * returned vector is <em>not</em> immutable, since any modification of
     * {@code v} will also change the returned view.
     * For example, in the following piece of code
     * <pre>
     *     RealVector v = new ArrayRealVector(2);
     *     RealVector w = RealVector.unmodifiableRealVector(v);
     *     v.setEntry(0, 1.2);
     *     v.setEntry(1, -3.4);
     * </pre>
     * the changes will be seen in the {@code w} view of {@code v}.
     *
     * @param v Vector for which an unmodifiable view is to be returned.
     * @return an unmodifiable view of {@code v}.
     */
    public static RealVector unmodifiableRealVector(final RealVector v) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }
}
