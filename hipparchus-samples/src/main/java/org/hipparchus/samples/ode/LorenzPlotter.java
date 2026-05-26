/*
 * Licensed to the Hipparchus project under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The Hipparchus project licenses this file to You under the Apache License, Version 2.0
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
package org.hipparchus.samples.ode;

import org.hipparchus.ode.ODEIntegrator;
import org.hipparchus.ode.ODEState;
import org.hipparchus.ode.OrdinaryDifferentialEquation;
import org.hipparchus.ode.nonstiff.DormandPrince853Integrator;
import org.hipparchus.ode.sampling.StepNormalizer;
import java.io.File;
import java.io.IOException;
import java.io.PrintStream;
import java.nio.charset.StandardCharsets;
import java.util.Locale;

/**
 * Program plotting the Lorenz attractor.
 */
public class LorenzPlotter {

    /**
     * Duration.
     */
    private double duration;

    /**
     * Step.
     */
    private double step;

    /**
     * Sigma.
     */
    private double sigma;

    /**
     * Rho.
     */
    private double rho;

    /**
     * Beta.
     */
    private double beta;

    /**
     * Output directory (display to terminal if null).
     */
    private File output;

    /**
     * Output width.
     */
    private int width;

    /**
     * Output height.
     */
    private int height;

    /**
     * View X rotation.
     */
    private double viewXRot;

    /**
     * View Z rotation.
     */
    private double viewZRot;

    /**
     * Default constructor.
     */
    private LorenzPlotter() {
        duration = 125.0;
        step = 0.002;
        sigma = 10;
        rho = 28;
        beta = 8.0 / 3.0;
        output = null;
        width = 1000;
        height = 1000;
        viewXRot = 70;
        viewZRot = 20;
    }

    /**
     * Main program.
     * @param args program arguments
     */
    public static void main(String[] args) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * Display usage.
     * @param status exit code
     */
    private static void usage(final int status) {
        System.err.println("usage: java org.hipparchus.samples.ode.LorenzPlotter" + " [--help]" + " [--duration duration] [--step step]" + " [--sigma sigma] [--rho rho] [--beta beta]" + " [--output-dir directory]" + " [--view xRot zRot]");
        System.exit(status);
    }

    /**
     * Plot the system.
     * @throws IOException if gnuplot process cannot be run
     */
    public void plot() throws IOException {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * Lorenz system.
     */
    private class LorenzOde implements OrdinaryDifferentialEquation {

        /**
         * {@inheritDoc}
         */
        @Override
        public int getDimension() {
            throw new UnsupportedOperationException("STUB: not implemented");
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public double[] computeDerivatives(final double t, final double[] y) {
            throw new UnsupportedOperationException("STUB: not implemented");
        }
    }
}
