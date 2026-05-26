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
package org.hipparchus.samples;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.RenderingHints;
import java.awt.geom.Rectangle2D;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JTextArea;
import org.hipparchus.exception.LocalizedCoreFormats;
import org.hipparchus.exception.MathIllegalArgumentException;
import org.hipparchus.geometry.euclidean.twod.Vector2D;
import org.hipparchus.random.HaltonSequenceGenerator;
import org.hipparchus.random.JDKRandomGenerator;
import org.hipparchus.random.MersenneTwister;
import org.hipparchus.random.RandomGenerator;
import org.hipparchus.random.RandomVectorGenerator;
import org.hipparchus.random.SobolSequenceGenerator;
import org.hipparchus.random.UncorrelatedRandomVectorGenerator;
import org.hipparchus.random.UniformRandomGenerator;
import org.hipparchus.samples.ExampleUtils.ExampleFrame;
import org.hipparchus.util.FastMath;
import org.hipparchus.util.Pair;

/**
 * Plots 2D samples drawn from various pseudo / quasi-random generators.
 */
// CHECKSTYLE: stop HideUtilityClassConstructor
public class LowDiscrepancyGeneratorComparison {

    /**
     * Empty constructor.
     * <p>
     * This constructor is not strictly necessary, but it prevents spurious
     * javadoc warnings with JDK 18 and later.
     * </p>
     * @since 3.0
     */
    public LowDiscrepancyGeneratorComparison() {
        // NOPMD - unnecessary constructor added intentionally to make javadoc happy
        // nothing to do
    }

    /**
     * Generate points within a circle.
     * @param samples number of points
     * @param generator random generator to generate points
     * @return generated points
     */
    public static List<Vector2D> makeCircle(int samples, final RandomVectorGenerator generator) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * Generate points.
     * @param samples number of points
     * @param generator random generator to generate points
     * @return generated points
     */
    public static List<Vector2D> makeRandom(int samples, RandomVectorGenerator generator) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * Normalize points.
     * @param input input points
     * @return normalized points in the [-1, 1 ] range
     */
    public static List<Vector2D> normalize(final List<Vector2D> input) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * Main frame for displaying low discrepancy points.
     */
    @SuppressWarnings("serial")
    public static class Display extends ExampleFrame {

        /**
         * Simple constructor.
         */
        public Display() {
            setTitle("Hipparchus: Pseudo/Quasi-random examples");
            setSize(800, 800);
            setLayout(new GridBagLayout());
            int[] datasets = new int[] { 256, 1000, 2500, 1000 };
            List<Pair<String, RandomVectorGenerator>> generators = new ArrayList<>();
            generators.add(new Pair<>("Uncorrelated\nUniform(JDK)", new UncorrelatedRandomVectorGenerator(2, new UniformRandomGenerator(new JDKRandomGenerator()))));
            generators.add(new Pair<>("Independent\nRandom(MT)", new RandomVectorGenerator() {

                private final RandomGenerator[] rngs = new RandomGenerator[] { new MersenneTwister(0), new MersenneTwister(1) };

                public double[] nextVector() {
                    throw new UnsupportedOperationException("STUB: not implemented");
                }
            }));
            generators.add(new Pair<>("HaltonSequence", new HaltonSequenceGenerator(2)));
            generators.add(new Pair<>("SobolSequence", new SobolSequenceGenerator(2)));
            GridBagConstraints c = new GridBagConstraints();
            c.fill = GridBagConstraints.VERTICAL;
            c.gridx = 1;
            c.gridy = 0;
            c.insets = new Insets(2, 2, 2, 2);
            for (Pair<String, RandomVectorGenerator> pair : generators) {
                JTextArea text = new JTextArea(pair.getFirst());
                text.setEditable(false);
                text.setOpaque(false);
                add(text, c);
                c.gridx++;
            }
            int saveY = ++c.gridy;
            c.gridx = 0;
            for (int type = 0; type < 4; type++) {
                JLabel text = new JLabel("n=" + String.valueOf(datasets[type]));
                text.setOpaque(false);
                add(text, c);
                c.gridy++;
            }
            c.gridy = saveY;
            for (int type = 0; type < 4; type++) {
                c.gridx = 1;
                for (Pair<String, RandomVectorGenerator> pair : generators) {
                    List<Vector2D> points;
                    int samples = datasets[type];
                    switch(type) {
                        case 0:
                            points = makeRandom(samples, pair.getValue());
                            break;
                        case 1:
                            points = makeRandom(samples, pair.getValue());
                            break;
                        case 2:
                            points = makeRandom(samples, pair.getValue());
                            break;
                        case 3:
                            points = makeCircle(samples, pair.getValue());
                            break;
                        default:
                            throw new MathIllegalArgumentException(LocalizedCoreFormats.INTERNAL_ERROR);
                    }
                    add(new Plot(points), c);
                    c.gridx++;
                }
                c.gridy++;
            }
        }
    }

    /**
     * Plotting component.
     */
    @SuppressWarnings("serial")
    public static class Plot extends JComponent {

        /**
         * Padding.
         */
        private static final double PAD = 10;

        /**
         * Points to plot.
         */
        private List<Vector2D> points;

        /**
         * Simple constructor.
         * @param points points to plot
         */
        public Plot(final List<Vector2D> points) {
            this.points = points;
        }

        /**
         * {@inheritDoc}
         */
        @Override
        protected void paintComponent(Graphics g) {
            throw new UnsupportedOperationException("STUB: not implemented");
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public Dimension getPreferredSize() {
            throw new UnsupportedOperationException("STUB: not implemented");
        }

        /**
         * Transform a point.
         * @param point initial point
         * @param width plot width
         * @param height plot height
         * @return transformed point
         */
        private Vector2D transform(Vector2D point, int width, int height) {
            double[] arr = point.toArray();
            return new Vector2D(new double[] { PAD + (arr[0] + 1) / 2.0 * (width - 2 * PAD), height - PAD - (arr[1] + 1) / 2.0 * (height - 2 * PAD) });
        }
    }

    /**
     * Program entry point.
     * @param args program arguments (unused here)
     */
    public static void main(String[] args) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }
}
