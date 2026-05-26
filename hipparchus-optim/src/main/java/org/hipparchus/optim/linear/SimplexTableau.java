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
package org.hipparchus.optim.linear;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;
import org.hipparchus.exception.LocalizedCoreFormats;
import org.hipparchus.exception.MathIllegalArgumentException;
import org.hipparchus.linear.Array2DRowRealMatrix;
import org.hipparchus.linear.RealVector;
import org.hipparchus.optim.PointValuePair;
import org.hipparchus.optim.nonlinear.scalar.GoalType;
import org.hipparchus.util.Precision;

/**
 * A tableau for use in the Simplex method.
 *
 * <p>Example:</p>
 * <pre>
 *   W |  Z |  x1 |  x2 |  x- | s1 |  s2 |  a1 |  RHS
 * ---------------------------------------------------
 *  -1    0    0     0     0     0     0     1     0   &lt;= phase 1 objective
 *   0    1   -15   -10    0     0     0     0     0   &lt;= phase 2 objective
 *   0    0    1     0     0     1     0     0     2   &lt;= constraint 1
 *   0    0    0     1     0     0     1     0     3   &lt;= constraint 2
 *   0    0    1     1     0     0     0     1     4   &lt;= constraint 3
 * </pre>
 * <ul>
 *   <li>W: Phase 1 objective function</li>
 *   <li>Z: Phase 2 objective function</li>
 *   <li>x1 &amp; x2: Decision variables</li>
 *   <li>x-: Extra decision variable to allow for negative values</li>
 *   <li>s1 &amp; s2: Slack/Surplus variables</li>
 *   <li>a1: Artificial variable</li>
 *   <li>RHS: Right hand side</li>
 * </ul>
 */
class SimplexTableau implements Serializable {

    /**
     * Column label for negative vars.
     */
    private static final String NEGATIVE_VAR_COLUMN_LABEL = "x-";

    /**
     * Serializable version identifier.
     */
    private static final long serialVersionUID = -1369660067587938365L;

    /**
     * Linear objective function.
     */
    private final LinearObjectiveFunction f;

    /**
     * Linear constraints.
     */
    private final List<LinearConstraint> constraints;

    /**
     * Whether to restrict the variables to non-negative values.
     */
    private final boolean restrictToNonNegative;

    /**
     * The variables each column represents
     */
    private final List<String> columnLabels;

    /**
     * Simple tableau.
     */
    private transient Array2DRowRealMatrix tableau;

    /**
     * Number of decision variables.
     */
    private final int numDecisionVariables;

    /**
     * Number of slack variables.
     */
    private final int numSlackVariables;

    /**
     * Number of artificial variables.
     */
    private int numArtificialVariables;

    /**
     * Amount of error to accept when checking for optimality.
     */
    private final double epsilon;

    /**
     * Amount of error to accept in floating point comparisons.
     */
    private final int maxUlps;

    /**
     * Maps basic variables to row they are basic in.
     */
    private int[] basicVariables;

    /**
     * Maps rows to their corresponding basic variables.
     */
    private int[] basicRows;

    /**
     * Builds a tableau for a linear problem.
     *
     * @param f Linear objective function.
     * @param constraints Linear constraints.
     * @param goalType Optimization goal: either {@link GoalType#MAXIMIZE}
     * or {@link GoalType#MINIMIZE}.
     * @param restrictToNonNegative Whether to restrict the variables to non-negative values.
     * @param epsilon Amount of error to accept when checking for optimality.
     * @throws MathIllegalArgumentException if the dimension of the constraints does not match the
     *   dimension of the objective function
     */
    SimplexTableau(final LinearObjectiveFunction f, final Collection<LinearConstraint> constraints, final GoalType goalType, final boolean restrictToNonNegative, final double epsilon) {
        this(f, constraints, goalType, restrictToNonNegative, epsilon, SimplexSolver.DEFAULT_ULPS);
    }

    /**
     * Build a tableau for a linear problem.
     * @param f linear objective function
     * @param constraints linear constraints
     * @param goalType type of optimization goal: either {@link GoalType#MAXIMIZE} or {@link GoalType#MINIMIZE}
     * @param restrictToNonNegative whether to restrict the variables to non-negative values
     * @param epsilon amount of error to accept when checking for optimality
     * @param maxUlps amount of error to accept in floating point comparisons
     * @throws MathIllegalArgumentException if the dimension of the constraints does not match the
     *   dimension of the objective function
     */
    SimplexTableau(final LinearObjectiveFunction f, final Collection<LinearConstraint> constraints, final GoalType goalType, final boolean restrictToNonNegative, final double epsilon, final int maxUlps) throws MathIllegalArgumentException {
        checkDimensions(f, constraints);
        this.f = f;
        this.constraints = normalizeConstraints(constraints);
        this.restrictToNonNegative = restrictToNonNegative;
        this.columnLabels = new ArrayList<>();
        this.epsilon = epsilon;
        this.maxUlps = maxUlps;
        this.numDecisionVariables = f.getCoefficients().getDimension() + (restrictToNonNegative ? 0 : 1);
        this.numSlackVariables = getConstraintTypeCounts(Relationship.LEQ) + getConstraintTypeCounts(Relationship.GEQ);
        this.numArtificialVariables = getConstraintTypeCounts(Relationship.EQ) + getConstraintTypeCounts(Relationship.GEQ);
        this.tableau = createTableau(goalType == GoalType.MAXIMIZE);
        // initialize the basic variables for phase 1:
        //   we know that only slack or artificial variables can be basic
        initializeBasicVariables(getSlackVariableOffset());
        initializeColumnLabels();
    }

    /**
     * Checks that the dimensions of the objective function and the constraints match.
     * @param objectiveFunction the objective function
     * @param c the set of constraints
     * @throws MathIllegalArgumentException if the constraint dimensions do not match with the
     *   dimension of the objective function
     */
    private void checkDimensions(final LinearObjectiveFunction objectiveFunction, final Collection<LinearConstraint> c) {
        final int dimension = objectiveFunction.getCoefficients().getDimension();
        for (final LinearConstraint constraint : c) {
            final int constraintDimension = constraint.getCoefficients().getDimension();
            if (constraintDimension != dimension) {
                throw new MathIllegalArgumentException(LocalizedCoreFormats.DIMENSIONS_MISMATCH, constraintDimension, dimension);
            }
        }
    }

    /**
     * Initialize the labels for the columns.
     */
    protected void initializeColumnLabels() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * Create the tableau by itself.
     * @param maximize if true, goal is to maximize the objective function
     * @return created tableau
     */
    protected Array2DRowRealMatrix createTableau(final boolean maximize) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * Get new versions of the constraints which have positive right hand sides.
     * @param originalConstraints original (not normalized) constraints
     * @return new versions of the constraints
     */
    public List<LinearConstraint> normalizeConstraints(Collection<LinearConstraint> originalConstraints) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * Get a new equation equivalent to this one with a positive right hand side.
     * @param constraint reference constraint
     * @return new equation
     */
    private LinearConstraint normalize(final LinearConstraint constraint) {
        if (constraint.getValue() < 0) {
            return new LinearConstraint(constraint.getCoefficients().mapMultiply(-1), constraint.getRelationship().oppositeRelationship(), -1 * constraint.getValue());
        }
        return new LinearConstraint(constraint.getCoefficients(), constraint.getRelationship(), constraint.getValue());
    }

    /**
     * Get the number of objective functions in this tableau.
     * @return 2 for Phase 1.  1 for Phase 2.
     */
    protected final int getNumObjectiveFunctions() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * Get a count of constraints corresponding to a specified relationship.
     * @param relationship relationship to count
     * @return number of constraint with the specified relationship
     */
    private int getConstraintTypeCounts(final Relationship relationship) {
        int count = 0;
        for (final LinearConstraint constraint : constraints) {
            if (constraint.getRelationship() == relationship) {
                ++count;
            }
        }
        return count;
    }

    /**
     * Get the -1 times the sum of all coefficients in the given array.
     * @param coefficients coefficients to sum
     * @return the -1 times the sum of all coefficients in the given array.
     */
    protected static double getInvertedCoefficientSum(final RealVector coefficients) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * Checks whether the given column is basic.
     * @param col index of the column to check
     * @return the row that the variable is basic in.  null if the column is not basic
     */
    protected Integer getBasicRow(final int col) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * Returns the variable that is basic in this row.
     * @param row the index of the row to check
     * @return the variable that is basic for this row.
     */
    protected int getBasicVariable(final int row) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * Initializes the basic variable / row mapping.
     * @param startColumn the column to start
     */
    private void initializeBasicVariables(final int startColumn) {
        basicVariables = new int[getWidth() - 1];
        basicRows = new int[getHeight()];
        Arrays.fill(basicVariables, -1);
        for (int i = startColumn; i < getWidth() - 1; i++) {
            Integer row = findBasicRow(i);
            if (row != null) {
                basicVariables[i] = row;
                basicRows[row] = i;
            }
        }
    }

    /**
     * Returns the row in which the given column is basic.
     * @param col index of the column
     * @return the row that the variable is basic in, or {@code null} if the variable is not basic.
     */
    private Integer findBasicRow(final int col) {
        Integer row = null;
        for (int i = 0; i < getHeight(); i++) {
            final double entry = getEntry(i, col);
            if (Precision.equals(entry, 1d, maxUlps) && (row == null)) {
                row = i;
            } else if (!Precision.equals(entry, 0d, maxUlps)) {
                return null;
            }
        }
        return row;
    }

    /**
     * Removes the phase 1 objective function, positive cost non-artificial variables,
     * and the non-basic artificial variables from this tableau.
     */
    protected void dropPhase1Objective() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * @param src the source array
     * @param dest the destination array
     */
    private void copyArray(final double[] src, final double[] dest) {
        System.arraycopy(src, 0, dest, getNumObjectiveFunctions(), src.length);
    }

    /**
     * Returns whether the problem is at an optimal state.
     * @return whether the model has been solved
     */
    boolean isOptimal() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * Get the current solution.
     * @return current solution
     */
    protected PointValuePair getSolution() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * Perform the row operations of the simplex algorithm with the selected
     * pivot column and row.
     * @param pivotCol the pivot column
     * @param pivotRow the pivot row
     */
    protected void performRowOperations(int pivotCol, int pivotRow) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * Divides one row by a given divisor.
     * <p>
     * After application of this operation, the following will hold:
     * <pre>dividendRow = dividendRow / divisor</pre>
     *
     * @param dividendRowIndex index of the row
     * @param divisor value of the divisor
     */
    protected void divideRow(final int dividendRowIndex, final double divisor) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * Subtracts a multiple of one row from another.
     * <p>
     * After application of this operation, the following will hold:
     * <pre>minuendRow = minuendRow - multiple * subtrahendRow</pre>
     *
     * @param minuendRowIndex row index
     * @param subtrahendRowIndex row index
     * @param multiplier multiplication factor
     */
    protected void subtractRow(final int minuendRowIndex, final int subtrahendRowIndex, final double multiplier) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * Get the width of the tableau.
     * @return width of the tableau
     */
    protected final int getWidth() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * Get the height of the tableau.
     * @return height of the tableau
     */
    protected final int getHeight() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * Get an entry of the tableau.
     * @param row row index
     * @param column column index
     * @return entry at (row, column)
     */
    protected final double getEntry(final int row, final int column) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * Set an entry of the tableau.
     * @param row row index
     * @param column column index
     * @param value for the entry
     */
    protected final void setEntry(final int row, final int column, final double value) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * Get the offset of the first slack variable.
     * @return offset of the first slack variable
     */
    protected final int getSlackVariableOffset() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * Get the offset of the first artificial variable.
     * @return offset of the first artificial variable
     */
    protected final int getArtificialVariableOffset() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * Get the offset of the right hand side.
     * @return offset of the right hand side
     */
    protected final int getRhsOffset() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * Get the number of decision variables.
     * <p>
     * If variables are not restricted to positive values, this will include 1 extra decision variable to represent
     * the absolute value of the most negative variable.
     *
     * @return number of decision variables
     * @see #getOriginalNumDecisionVariables()
     */
    protected final int getNumDecisionVariables() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * Get the original number of decision variables.
     * @return original number of decision variables
     * @see #getNumDecisionVariables()
     */
    protected final int getOriginalNumDecisionVariables() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * Get the number of slack variables.
     * @return number of slack variables
     */
    protected final int getNumSlackVariables() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * Get the number of artificial variables.
     * @return number of artificial variables
     */
    protected final int getNumArtificialVariables() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * Get the row from the tableau.
     * @param row the row index
     * @return the reference to the underlying row data
     */
    protected final double[] getRow(int row) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * Get the tableau data.
     * @return tableau data
     */
    protected final double[][] getData() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean equals(Object other) {
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
     * Serialize the instance.
     * @param oos stream where object should be written
     * @throws IOException if object cannot be written to stream
     */
    private void writeObject(ObjectOutputStream oos) throws IOException {
        oos.defaultWriteObject();
        final int n = tableau.getRowDimension();
        final int m = tableau.getColumnDimension();
        oos.writeInt(n);
        oos.writeInt(m);
        for (int i = 0; i < n; ++i) {
            for (int j = 0; j < m; ++j) {
                oos.writeDouble(tableau.getEntry(i, j));
            }
        }
    }

    /**
     * Deserialize the instance.
     * @param ois stream from which the object should be read
     * @throws ClassNotFoundException if a class in the stream cannot be found
     * @throws IOException if object cannot be read from the stream
     */
    private void readObject(ObjectInputStream ois) throws ClassNotFoundException, IOException {
        ois.defaultReadObject();
        // read the matrix data
        final int n = ois.readInt();
        final int m = ois.readInt();
        final double[][] data = new double[n][m];
        for (int i = 0; i < n; ++i) {
            final double[] dataI = data[i];
            for (int j = 0; j < m; ++j) {
                dataI[j] = ois.readDouble();
            }
        }
        // create the instance
        tableau = new Array2DRowRealMatrix(data, false);
    }
}
