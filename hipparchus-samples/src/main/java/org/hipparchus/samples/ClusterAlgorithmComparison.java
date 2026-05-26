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
import java.awt.Shape;
import java.awt.geom.Ellipse2D;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import javax.swing.JComponent;
import javax.swing.JLabel;
import org.hipparchus.clustering.CentroidCluster;
import org.hipparchus.clustering.Cluster;
import org.hipparchus.clustering.Clusterable;
import org.hipparchus.clustering.Clusterer;
import org.hipparchus.clustering.DBSCANClusterer;
import org.hipparchus.clustering.DoublePoint;
import org.hipparchus.clustering.FuzzyKMeansClusterer;
import org.hipparchus.clustering.KMeansPlusPlusClusterer;
import org.hipparchus.geometry.euclidean.twod.Vector2D;
import org.hipparchus.random.RandomAdaptor;
import org.hipparchus.random.RandomDataGenerator;
import org.hipparchus.random.RandomGenerator;
import org.hipparchus.random.SobolSequenceGenerator;
import org.hipparchus.random.Well19937c;
import org.hipparchus.samples.ExampleUtils.ExampleFrame;
import org.hipparchus.util.FastMath;
import org.hipparchus.util.Pair;
import org.hipparchus.util.SinCos;

/**
 * Plots clustering results for various algorithms and datasets.
 * Based on
 * <a href="http://scikit-learn.org/stable/auto_examples/cluster/plot_cluster_comparison.html">scikit learn</a>.
 */
//CHECKSTYLE: stop HideUtilityClassConstructor
public class ClusterAlgorithmComparison {

    /**
     * Empty constructor.
     * <p>
     * This constructor is not strictly necessary, but it prevents spurious
     * javadoc warnings with JDK 18 and later.
     * </p>
     * @since 3.0
     */
    public ClusterAlgorithmComparison() {
        // NOPMD - unnecessary constructor added intentionally to make javadoc happy
        // nothing to do
    }

    /**
     * Make circles patterns.
     * @param samples number of points
     * @param shuffle if true, shuffle points
     * @param noise noise to add to points position
     * @param factor reduction factor from outer to inner circle
     * @param random generator to use
     * @return circle patterns
     */
    public static List<Vector2D> makeCircles(int samples, boolean shuffle, double noise, double factor, final RandomGenerator random) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * Make Moons patterns.
     * @param samples number of points
     * @param shuffle if true, shuffle points
     * @param noise noise to add to points position
     * @param random generator to use
     * @return Moons patterns
     */
    public static List<Vector2D> makeMoons(int samples, boolean shuffle, double noise, RandomGenerator random) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * Make blobs patterns.
     * @param samples number of points
     * @param centers number of centers
     * @param clusterStd standard deviation of cluster
     * @param min range min value
     * @param max range max value
     * @param shuffle if true, shuffle points
     * @param random generator to use
     * @return blobs patterns
     */
    public static List<Vector2D> makeBlobs(int samples, int centers, double clusterStd, double min, double max, boolean shuffle, RandomGenerator random) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * Make Sobol patterns.
     * @param samples number of points
     * @return Moons patterns
     */
    public static List<Vector2D> makeSobol(int samples) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * Generate a random vector.
     * @param randomGenerator random generator to use
     * @param noise noise level
     * @return random vector
     */
    public static Vector2D generateNoiseVector(RandomGenerator randomGenerator, double noise) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * Normolize points in a rectangular area
     * @param input input points
     * @param minX range min value in X
     * @param maxX range max value in X
     * @param minY range min value in Y
     * @param maxY range max value in Y
     * @return normalized points
     */
    public static List<DoublePoint> normalize(final List<Vector2D> input, double minX, double maxX, double minY, double maxY) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * Build the 2D vector corresponding to the given angle.
     * @param alpha angle
     * @return the corresponding 2D vector
     */
    private static Vector2D buildVector(final double alpha) {
        final SinCos sc = FastMath.sinCos(alpha);
        return new Vector2D(sc.cos(), sc.sin());
    }

    /**
     * Display frame.
     */
    @SuppressWarnings("serial")
    public static class Display extends ExampleFrame {

        /**
         * Simple consructor.
         */
        public Display() {
            setTitle("Hipparchus: Cluster algorithm comparison");
            setSize(800, 800);
            setLayout(new GridBagLayout());
            int nSamples = 1500;
            RandomGenerator rng = new Well19937c(0);
            List<List<DoublePoint>> datasets = new ArrayList<>();
            datasets.add(normalize(makeCircles(nSamples, true, 0.04, 0.5, rng), -1, 1, -1, 1));
            datasets.add(normalize(makeMoons(nSamples, true, 0.04, rng), -1, 2, -1, 1));
            datasets.add(normalize(makeBlobs(nSamples, 3, 1.0, -10, 10, true, rng), -12, 12, -12, 12));
            datasets.add(normalize(makeSobol(nSamples), -1, 1, -1, 1));
            List<Pair<String, Clusterer<DoublePoint>>> algorithms = new ArrayList<>();
            algorithms.add(new Pair<>("KMeans\n(k=2)", new KMeansPlusPlusClusterer<>(2)));
            algorithms.add(new Pair<>("KMeans\n(k=3)", new KMeansPlusPlusClusterer<>(3)));
            algorithms.add(new Pair<>("FuzzyKMeans\n(k=3, fuzzy=2)", new FuzzyKMeansClusterer<>(3, 2)));
            algorithms.add(new Pair<>("FuzzyKMeans\n(k=3, fuzzy=10)", new FuzzyKMeansClusterer<>(3, 10)));
            algorithms.add(new Pair<>("DBSCAN\n(eps=.1, min=3)", new DBSCANClusterer<>(0.1, 3)));
            GridBagConstraints c = new GridBagConstraints();
            c.fill = GridBagConstraints.VERTICAL;
            c.gridx = 0;
            c.gridy = 0;
            c.insets = new Insets(2, 2, 2, 2);
            for (Pair<String, Clusterer<DoublePoint>> pair : algorithms) {
                JLabel text = new JLabel("<html><body>" + pair.getFirst().replace("\n", "<br>"));
                add(text, c);
                c.gridx++;
            }
            c.gridy++;
            for (List<DoublePoint> dataset : datasets) {
                c.gridx = 0;
                for (Pair<String, Clusterer<DoublePoint>> pair : algorithms) {
                    long start = System.currentTimeMillis();
                    List<? extends Cluster<DoublePoint>> clusters = pair.getSecond().cluster(dataset);
                    long end = System.currentTimeMillis();
                    add(new ClusterPlot(clusters, end - start), c);
                    c.gridx++;
                }
                c.gridy++;
            }
        }
    }

    /**
     * Plot component.
     */
    @SuppressWarnings("serial")
    public static class ClusterPlot extends JComponent {

        /**
         * Padding.
         */
        private static final double PAD = 10;

        /**
         * Clusters.
         */
        private List<? extends Cluster<DoublePoint>> clusters;

        /**
         * Duration of the computation.
         */
        private long duration;

        /**
         * Simple constructor.
         * @param clusters clusters to plot
         * @param duration duration of the computation
         */
        public ClusterPlot(final List<? extends Cluster<DoublePoint>> clusters, long duration) {
            this.clusters = clusters;
            this.duration = duration;
        }

        @Override
        protected void paintComponent(Graphics g) {
            throw new UnsupportedOperationException("STUB: not implemented");
        }

        @Override
        public Dimension getPreferredSize() {
            throw new UnsupportedOperationException("STUB: not implemented");
        }

        private Clusterable transform(Clusterable point, int width, int height) {
            double[] arr = point.getPoint();
            return new DoublePoint(new double[] { PAD + (arr[0] + 1) / 2.0 * (width - 2 * PAD), height - PAD - (arr[1] + 1) / 2.0 * (height - 2 * PAD) });
        }
    }

    /**
     * Example entry point.
     * @param args arguments (not used)
     */
    public static void main(String[] args) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }
}
