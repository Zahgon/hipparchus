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
package org.hipparchus.samples.geometry;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.geom.Point2D;
import java.util.ArrayList;
import java.util.List;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JPanel;
import javax.swing.JSplitPane;
import org.hipparchus.geometry.enclosing.Encloser;
import org.hipparchus.geometry.enclosing.EnclosingBall;
import org.hipparchus.geometry.enclosing.WelzlEncloser;
import org.hipparchus.geometry.euclidean.twod.DiskGenerator;
import org.hipparchus.geometry.euclidean.twod.Euclidean2D;
import org.hipparchus.geometry.euclidean.twod.Segment;
import org.hipparchus.geometry.euclidean.twod.Vector2D;
import org.hipparchus.geometry.euclidean.twod.hull.ConvexHull2D;
import org.hipparchus.geometry.euclidean.twod.hull.ConvexHullGenerator2D;
import org.hipparchus.geometry.euclidean.twod.hull.MonotoneChain;
import org.hipparchus.random.MersenneTwister;
import org.hipparchus.random.RandomGenerator;
import org.hipparchus.samples.ExampleUtils;
import org.hipparchus.samples.ExampleUtils.ExampleFrame;
import org.hipparchus.util.FastMath;
import org.hipparchus.util.SinCos;
import org.piccolo2d.PCamera;
import org.piccolo2d.PCanvas;
import org.piccolo2d.PNode;
import org.piccolo2d.event.PBasicInputEventHandler;
import org.piccolo2d.event.PInputEvent;
import org.piccolo2d.event.PMouseWheelZoomEventHandler;
import org.piccolo2d.nodes.PPath;
import org.piccolo2d.nodes.PText;

/**
 * Simple example illustrating some parts of the geometry package.
 *
 * TODO:
 *  - select tolerance level
 *  - allow editing of the point set
 */
//CHECKSTYLE: stop HideUtilityClassConstructor
public class GeometryExample {

    /**
     * Tooltip.
     */
    private static final String TOOLTIP = "tooltip";

    /**
     * Empty constructor.
     * <p>
     * This constructor is not strictly necessary, but it prevents spurious
     * javadoc warnings with JDK 18 and later.
     * </p>
     * @since 3.0
     */
    public GeometryExample() {
        // NOPMD - unnecessary constructor added intentionally to make javadoc happy
        // nothing to do
    }

    /**
     * Create a list of random points.
     * @param size number of points
     * @return random points
     */
    public static List<Vector2D> createRandomPoints(int size) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * Create a circle sprite.
     * @param samples number of points
     * @return vectors describing the sprite
     */
    public static List<Vector2D> createCircle(int samples) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * Create a cross sprite.
     * @return vectors describing the sprite
     */
    public static List<Vector2D> createCross() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * Create a canvas
     * @return canvas
     */
    public static PCanvas createCanvas() {
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
     * Main frame for geometry examples.
     */
    @SuppressWarnings("serial")
    public static class Display extends ExampleFrame {

        /**
         * Points to plot.
         */
        private List<Vector2D> points;

        /**
         * Canvas for plotting.
         */
        private PCanvas canvas;

        /**
         * Container.
         */
        private JComponent container;

        /**
         * Simple constructor.
         */
        public Display() {
            setTitle("Hipparchus: Geometry Examples");
            setSize(800, 700);
            container = new JPanel(new BorderLayout());
            canvas = createCanvas();
            container.add(canvas);
            container.setBorder(BorderFactory.createLineBorder(Color.black, 1));
            JComponent controlPanel = new JPanel();
            JButton random = new JButton("Randomize");
            controlPanel.add(random);
            //                @Override
            random.addActionListener(e -> {
                canvas.getLayer().removeAllChildren();
                points = createRandomPoints(1000);
                paintConvexHull();
            });
            JButton circle = new JButton("Circle");
            controlPanel.add(circle);
            //                @Override
            circle.addActionListener(e -> {
                canvas.getLayer().removeAllChildren();
                points = createCircle(100);
                paintConvexHull();
            });
            JButton cross = new JButton("Cross");
            controlPanel.add(cross);
            //                @Override
            cross.addActionListener(e -> {
                canvas.getLayer().removeAllChildren();
                points = createCross();
                paintConvexHull();
            });
            JSplitPane splitpane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, container, controlPanel);
            splitpane.setDividerLocation(600);
            add(splitpane);
            points = createRandomPoints(1000);
            paintConvexHull();
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public Component getMainPanel() {
            throw new UnsupportedOperationException("STUB: not implemented");
        }

        /**
         * Paint a convex hull.
         */
        public void paintConvexHull() {
            throw new UnsupportedOperationException("STUB: not implemented");
        }
    }

    /**
     * Main entry point.
     * @param argv program arguments (unused here)
     */
    public static void main(final String[] argv) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }
}
