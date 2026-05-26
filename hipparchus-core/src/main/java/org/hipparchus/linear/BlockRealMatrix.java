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

import java.io.Serializable;
import java.util.Arrays;
import org.hipparchus.exception.LocalizedCoreFormats;
import org.hipparchus.exception.MathIllegalArgumentException;
import org.hipparchus.exception.NullArgumentException;
import org.hipparchus.util.FastMath;
import org.hipparchus.util.MathUtils;

/**
 * Cache-friendly implementation of RealMatrix using a flat arrays to store
 * square blocks of the matrix.
 * <p>
 * This implementation is specially designed to be cache-friendly. Square blocks are
 * stored as small arrays and allow efficient traversal of data both in row major direction
 * and columns major direction, one block at a time. This greatly increases performances
 * for algorithms that use crossed directions loops like multiplication or transposition.
 * </p>
 * <p>
 * The size of square blocks is a static parameter. It may be tuned according to the cache
 * size of the target computer processor. As a rule of thumbs, it should be the largest
 * value that allows three blocks to be simultaneously cached (this is necessary for example
 * for matrix multiplication). The default value is to use 52x52 blocks which is well suited
 * for processors with 64k L1 cache (one block holds 2704 values or 21632 bytes). This value
 * could be lowered to 36x36 for processors with 32k L1 cache.
 * </p>
 * <p>
 * The regular blocks represent {@link #BLOCK_SIZE} x {@link #BLOCK_SIZE} squares. Blocks
 * at right hand side and bottom side may be smaller to fit matrix dimensions. The square
 * blocks are flattened in row major order in single dimension arrays which are therefore
 * {@link #BLOCK_SIZE}<sup>2</sup> elements long for regular blocks. The blocks are themselves
 * organized in row major order.
 * </p>
 * <p>
 * As an example, for a block size of 52x52, a 100x60 matrix would be stored in 4 blocks.
 * Block 0 would be a {@code double[2704]} array holding the upper left 52x52 square, block 1
 * would be a {@code double[416]} array holding the upper right 52x8 rectangle, block 2 would
 * be a {@code double[2496]} array holding the lower left 48x52 rectangle and block 3 would
 * be a {@code double[384]} array holding the lower right 48x8 rectangle.
 * </p>
 * <p>
 * The layout complexity overhead versus simple mapping of matrices to java
 * arrays is negligible for small matrices (about 1%). The gain from cache efficiency leads
 * to up to 3-fold improvements for matrices of moderate to large size.
 * </p>
 */
public class BlockRealMatrix extends AbstractRealMatrix implements Serializable {

    /**
     * Block size.
     */
    public static final int BLOCK_SIZE = 52;

    /**
     * Serializable version identifier
     */
    private static final long serialVersionUID = 4991895511313664478L;

    /**
     * Blocks of matrix entries.
     */
    private final double[][] blocks;

    /**
     * Number of rows of the matrix.
     */
    private final int rows;

    /**
     * Number of columns of the matrix.
     */
    private final int columns;

    /**
     * Number of block rows of the matrix.
     */
    private final int blockRows;

    /**
     * Number of block columns of the matrix.
     */
    private final int blockColumns;

    /**
     * Create a new matrix with the supplied row and column dimensions.
     *
     * @param rows  the number of rows in the new matrix
     * @param columns  the number of columns in the new matrix
     * @throws MathIllegalArgumentException if row or column dimension is not
     * positive.
     */
    public BlockRealMatrix(final int rows, final int columns) throws MathIllegalArgumentException {
        super(rows, columns);
        this.rows = rows;
        this.columns = columns;
        // number of blocks
        blockRows = (rows + BLOCK_SIZE - 1) / BLOCK_SIZE;
        blockColumns = (columns + BLOCK_SIZE - 1) / BLOCK_SIZE;
        // allocate storage blocks, taking care of smaller ones at right and bottom
        blocks = createBlocksLayout(rows, columns);
    }

    /**
     * Create a new dense matrix copying entries from raw layout data.
     * <p>The input array <em>must</em> already be in raw layout.</p>
     * <p>Calling this constructor is equivalent to call:</p>
     * <pre>matrix = new BlockRealMatrix(rawData.length, rawData[0].length,
     *                                   toBlocksLayout(rawData), false);</pre>
     *
     * @param rawData data for new matrix, in raw layout
     * @throws MathIllegalArgumentException if the shape of {@code blockData} is
     * inconsistent with block layout.
     * @throws MathIllegalArgumentException if row or column dimension is not
     * positive.
     * @see #BlockRealMatrix(int, int, double[][], boolean)
     */
    public BlockRealMatrix(final double[][] rawData) throws MathIllegalArgumentException {
        this(rawData.length, rawData[0].length, toBlocksLayout(rawData), false);
    }

    /**
     * Create a new dense matrix copying entries from block layout data.
     * <p>The input array <em>must</em> already be in blocks layout.</p>
     *
     * @param rows Number of rows in the new matrix.
     * @param columns Number of columns in the new matrix.
     * @param blockData data for new matrix
     * @param copyArray Whether the input array will be copied or referenced.
     * @throws MathIllegalArgumentException if the shape of {@code blockData} is
     * inconsistent with block layout.
     * @throws MathIllegalArgumentException if row or column dimension is not
     * positive.
     * @see #createBlocksLayout(int, int)
     * @see #toBlocksLayout(double[][])
     * @see #BlockRealMatrix(double[][])
     */
    public BlockRealMatrix(final int rows, final int columns, final double[][] blockData, final boolean copyArray) throws MathIllegalArgumentException {
        super(rows, columns);
        this.rows = rows;
        this.columns = columns;
        // number of blocks
        blockRows = (rows + BLOCK_SIZE - 1) / BLOCK_SIZE;
        blockColumns = (columns + BLOCK_SIZE - 1) / BLOCK_SIZE;
        if (copyArray) {
            // allocate storage blocks, taking care of smaller ones at right and bottom
            blocks = new double[blockRows * blockColumns][];
        } else {
            // reference existing array
            // NOPMD - array copy is taken care of by parameter
            blocks = blockData;
        }
        int index = 0;
        for (int iBlock = 0; iBlock < blockRows; ++iBlock) {
            final int iHeight = blockHeight(iBlock);
            for (int jBlock = 0; jBlock < blockColumns; ++jBlock, ++index) {
                if (blockData[index].length != iHeight * blockWidth(jBlock)) {
                    throw new MathIllegalArgumentException(LocalizedCoreFormats.DIMENSIONS_MISMATCH, blockData[index].length, iHeight * blockWidth(jBlock));
                }
                if (copyArray) {
                    blocks[index] = blockData[index].clone();
                }
            }
        }
    }

    /**
     * Convert a data array from raw layout to blocks layout.
     * <p>
     * Raw layout is the straightforward layout where element at row i and
     * column j is in array element <code>rawData[i][j]</code>. Blocks layout
     * is the layout used in {@link BlockRealMatrix} instances, where the matrix
     * is split in square blocks (except at right and bottom side where blocks may
     * be rectangular to fit matrix size) and each block is stored in a flattened
     * one-dimensional array.
     * </p>
     * <p>
     * This method creates an array in blocks layout from an input array in raw layout.
     * It can be used to provide the array argument of the {@link
     * #BlockRealMatrix(int, int, double[][], boolean)} constructor.
     * </p>
     * @param rawData Data array in raw layout.
     * @return a new data array containing the same entries but in blocks layout.
     * @throws MathIllegalArgumentException if {@code rawData} is not rectangular.
     * @see #createBlocksLayout(int, int)
     * @see #BlockRealMatrix(int, int, double[][], boolean)
     */
    public static double[][] toBlocksLayout(final double[][] rawData) throws MathIllegalArgumentException {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * Create a data array in blocks layout.
     * <p>
     * This method can be used to create the array argument of the {@link
     * #BlockRealMatrix(int, int, double[][], boolean)} constructor.
     * </p>
     * @param rows Number of rows in the new matrix.
     * @param columns Number of columns in the new matrix.
     * @return a new data array in blocks layout.
     * @see #toBlocksLayout(double[][])
     * @see #BlockRealMatrix(int, int, double[][], boolean)
     */
    public static double[][] createBlocksLayout(final int rows, final int columns) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public BlockRealMatrix createMatrix(final int rowDimension, final int columnDimension) throws MathIllegalArgumentException {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public BlockRealMatrix copy() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public BlockRealMatrix add(final RealMatrix m) throws MathIllegalArgumentException {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * Compute the sum of this matrix and {@code m}.
     *
     * @param m Matrix to be added.
     * @return {@code this} + m.
     * @throws MathIllegalArgumentException if {@code m} is not the same
     * size as this matrix.
     */
    public BlockRealMatrix add(final BlockRealMatrix m) throws MathIllegalArgumentException {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public BlockRealMatrix subtract(final RealMatrix m) throws MathIllegalArgumentException {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * Subtract {@code m} from this matrix.
     *
     * @param m Matrix to be subtracted.
     * @return {@code this} - m.
     * @throws MathIllegalArgumentException if {@code m} is not the
     * same size as this matrix.
     */
    public BlockRealMatrix subtract(final BlockRealMatrix m) throws MathIllegalArgumentException {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public BlockRealMatrix scalarAdd(final double d) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public RealMatrix scalarMultiply(final double d) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public BlockRealMatrix multiply(final RealMatrix m) throws MathIllegalArgumentException {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * Returns the result of postmultiplying this by {@code m}.
     *
     * @param m Matrix to postmultiply by.
     * @return {@code this} * m.
     * @throws MathIllegalArgumentException if the matrices are not compatible.
     */
    public BlockRealMatrix multiply(BlockRealMatrix m) throws MathIllegalArgumentException {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * Returns the result of postmultiplying {@code this} by {@code m^T}.
     * @param m matrix to first transpose and second postmultiply by
     * @return {@code this * m^T}
     * @throws MathIllegalArgumentException if
     * {@code columnDimension(this) != columnDimension(m)}
     * @since 1.3
     */
    public BlockRealMatrix multiplyTransposed(BlockRealMatrix m) throws MathIllegalArgumentException {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public BlockRealMatrix multiplyTransposed(final RealMatrix m) throws MathIllegalArgumentException {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * Returns the result of postmultiplying {@code this^T} by {@code m}.
     * @param m matrix to postmultiply by
     * @return {@code this^T * m}
     * @throws MathIllegalArgumentException if
     * {@code columnDimension(this) != columnDimension(m)}
     * @since 1.3
     */
    public BlockRealMatrix transposeMultiply(final BlockRealMatrix m) throws MathIllegalArgumentException {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public BlockRealMatrix transposeMultiply(final RealMatrix m) throws MathIllegalArgumentException {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public double[][] getData() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public double getNorm1() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public double getNormInfty() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public double getFrobeniusNorm() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public BlockRealMatrix getSubMatrix(final int startRow, final int endRow, final int startColumn, final int endColumn) throws MathIllegalArgumentException {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * Copy a part of a block into another one
     * <p>This method can be called only when the specified part fits in both
     * blocks, no verification is done here.</p>
     * @param srcBlock source block
     * @param srcWidth source block width ({@link #BLOCK_SIZE} or smaller)
     * @param srcStartRow start row in the source block
     * @param srcEndRow end row (exclusive) in the source block
     * @param srcStartColumn start column in the source block
     * @param srcEndColumn end column (exclusive) in the source block
     * @param dstBlock destination block
     * @param dstWidth destination block width ({@link #BLOCK_SIZE} or smaller)
     * @param dstStartRow start row in the destination block
     * @param dstStartColumn start column in the destination block
     */
    private void copyBlockPart(final double[] srcBlock, final int srcWidth, final int srcStartRow, final int srcEndRow, final int srcStartColumn, final int srcEndColumn, final double[] dstBlock, final int dstWidth, final int dstStartRow, final int dstStartColumn) {
        final int length = srcEndColumn - srcStartColumn;
        int srcPos = srcStartRow * srcWidth + srcStartColumn;
        int dstPos = dstStartRow * dstWidth + dstStartColumn;
        for (int srcRow = srcStartRow; srcRow < srcEndRow; ++srcRow) {
            System.arraycopy(srcBlock, srcPos, dstBlock, dstPos, length);
            srcPos += srcWidth;
            dstPos += dstWidth;
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setSubMatrix(final double[][] subMatrix, final int row, final int column) throws MathIllegalArgumentException, NullArgumentException {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public BlockRealMatrix getRowMatrix(final int row) throws MathIllegalArgumentException {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setRowMatrix(final int row, final RealMatrix matrix) throws MathIllegalArgumentException {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * Sets the entries in row number <code>row</code>
     * as a row matrix.  Row indices start at 0.
     *
     * @param row the row to be set
     * @param matrix row matrix (must have one row and the same number of columns
     * as the instance)
     * @throws MathIllegalArgumentException if the specified row index is invalid.
     * @throws MathIllegalArgumentException if the matrix dimensions do
     * not match one instance row.
     */
    public void setRowMatrix(final int row, final BlockRealMatrix matrix) throws MathIllegalArgumentException {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public BlockRealMatrix getColumnMatrix(final int column) throws MathIllegalArgumentException {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setColumnMatrix(final int column, final RealMatrix matrix) throws MathIllegalArgumentException {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * Sets the entries in column number <code>column</code>
     * as a column matrix.  Column indices start at 0.
     *
     * @param column the column to be set
     * @param matrix column matrix (must have one column and the same number of rows
     * as the instance)
     * @throws MathIllegalArgumentException if the specified column index is invalid.
     * @throws MathIllegalArgumentException if the matrix dimensions do
     * not match one instance column.
     */
    void setColumnMatrix(final int column, final BlockRealMatrix matrix) throws MathIllegalArgumentException {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public RealVector getRowVector(final int row) throws MathIllegalArgumentException {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setRowVector(final int row, final RealVector vector) throws MathIllegalArgumentException {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public RealVector getColumnVector(final int column) throws MathIllegalArgumentException {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setColumnVector(final int column, final RealVector vector) throws MathIllegalArgumentException {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public double[] getRow(final int row) throws MathIllegalArgumentException {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setRow(final int row, final double[] array) throws MathIllegalArgumentException {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public double[] getColumn(final int column) throws MathIllegalArgumentException {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setColumn(final int column, final double[] array) throws MathIllegalArgumentException {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public double getEntry(final int row, final int column) throws MathIllegalArgumentException {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setEntry(final int row, final int column, final double value) throws MathIllegalArgumentException {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void addToEntry(final int row, final int column, final double increment) throws MathIllegalArgumentException {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void multiplyEntry(final int row, final int column, final double factor) throws MathIllegalArgumentException {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public BlockRealMatrix transpose() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int getRowDimension() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int getColumnDimension() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public double[] operate(final double[] v) throws MathIllegalArgumentException {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public double[] preMultiply(final double[] v) throws MathIllegalArgumentException {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public double walkInRowOrder(final RealMatrixChangingVisitor visitor) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public double walkInRowOrder(final RealMatrixPreservingVisitor visitor) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public double walkInRowOrder(final RealMatrixChangingVisitor visitor, final int startRow, final int endRow, final int startColumn, final int endColumn) throws MathIllegalArgumentException {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public double walkInRowOrder(final RealMatrixPreservingVisitor visitor, final int startRow, final int endRow, final int startColumn, final int endColumn) throws MathIllegalArgumentException {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public double walkInOptimizedOrder(final RealMatrixChangingVisitor visitor) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public double walkInOptimizedOrder(final RealMatrixPreservingVisitor visitor) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public double walkInOptimizedOrder(final RealMatrixChangingVisitor visitor, final int startRow, final int endRow, final int startColumn, final int endColumn) throws MathIllegalArgumentException {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public double walkInOptimizedOrder(final RealMatrixPreservingVisitor visitor, final int startRow, final int endRow, final int startColumn, final int endColumn) throws MathIllegalArgumentException {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * Get the height of a block.
     * @param blockRow row index (in block sense) of the block
     * @return height (number of rows) of the block
     */
    private int blockHeight(final int blockRow) {
        return (blockRow == blockRows - 1) ? rows - blockRow * BLOCK_SIZE : BLOCK_SIZE;
    }

    /**
     * Get the width of a block.
     * @param blockColumn column index (in block sense) of the block
     * @return width (number of columns) of the block
     */
    private int blockWidth(final int blockColumn) {
        return (blockColumn == blockColumns - 1) ? columns - blockColumn * BLOCK_SIZE : BLOCK_SIZE;
    }
}
