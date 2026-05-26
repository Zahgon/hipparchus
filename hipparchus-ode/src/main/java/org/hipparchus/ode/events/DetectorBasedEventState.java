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
package org.hipparchus.ode.events;

import org.hipparchus.analysis.UnivariateFunction;
import org.hipparchus.analysis.solvers.BracketedUnivariateSolver;
import org.hipparchus.analysis.solvers.BracketedUnivariateSolver.Interval;
import org.hipparchus.exception.MathIllegalArgumentException;
import org.hipparchus.exception.MathIllegalStateException;
import org.hipparchus.exception.MathRuntimeException;
import org.hipparchus.ode.LocalizedODEFormats;
import org.hipparchus.ode.ODEState;
import org.hipparchus.ode.ODEStateAndDerivative;
import org.hipparchus.ode.sampling.ODEStateInterpolator;
import org.hipparchus.util.FastMath;

/**
 * This class handles the state for one {@link ODEEventHandler
 * event handler} during integration steps.
 *
 * <p>Each time the integrator proposes a step, the event handler
 * switching function should be checked. This class handles the state
 * of one handler during one integration step, with references to the
 * state at the end of the preceding step. This information is used to
 * decide if the handler should trigger an event or not during the
 * proposed step.</p>
 */
public class DetectorBasedEventState implements EventState {

    /**
     * Event detector.
     * @since 3.0
     */
    private final ODEEventDetector detector;

    /**
     * Event solver.
     * @since 3.0
     */
    private final BracketedUnivariateSolver<UnivariateFunction> solver;

    /**
     * Event handler.
     */
    private final ODEEventHandler handler;

    /**
     * Time of the previous call to g.
     */
    private double lastT;

    /**
     * Value from the previous call to g.
     */
    private double lastG;

    /**
     * Time at the beginning of the step.
     */
    private double t0;

    /**
     * Value of the events handler at the beginning of the step.
     */
    private double g0;

    /**
     * Sign of g0.
     */
    private boolean g0Positive;

    /**
     * Indicator of event expected during the step.
     */
    private boolean pendingEvent;

    /**
     * Occurrence time of the pending event.
     */
    private double pendingEventTime;

    /**
     * Time to stop propagation if the event is a stop event. Used to enable stopping at
     * an event and then restarting after that event.
     */
    private double stopTime;

    /**
     * Time after the current event.
     */
    private double afterEvent;

    /**
     * Value of the g function after the current event.
     */
    private double afterG;

    /**
     * The earliest time considered for events.
     */
    private double earliestTimeConsidered;

    /**
     * Integration direction.
     */
    private boolean forward;

    /**
     * Direction of g(t) in the propagation direction for the pending event, or if there
     * is no pending event the direction of the previous event.
     */
    private boolean increasing;

    /**
     * Simple constructor.
     * @param detector event detector
     * @since 3.0
     */
    public DetectorBasedEventState(final ODEEventDetector detector) {
        this.detector = detector;
        this.solver = detector.getSolver();
        this.handler = detector.getHandler();
        // some dummy values ...
        t0 = Double.NaN;
        g0 = Double.NaN;
        g0Positive = true;
        pendingEvent = false;
        pendingEventTime = Double.NaN;
        increasing = true;
        earliestTimeConsidered = Double.NaN;
        afterEvent = Double.NaN;
        afterG = Double.NaN;
    }

    /**
     * Get the underlying event detector.
     * @return underlying event detector
     * @since 3.0
     */
    public ODEEventDetector getEventDetector() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void init(final ODEStateAndDerivative s0, final double t) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * Compute the value of the switching function.
     * This function must be continuous (at least in its roots neighborhood),
     * as the integrator will need to find its roots to locate the events.
     * @param s the current state information: date, kinematics, attitude
     * @return value of the switching function
     */
    private double g(final ODEStateAndDerivative s) {
        if (s.getTime() != lastT) {
            lastG = detector.g(s);
            lastT = s.getTime();
        }
        return lastG;
    }

    /**
     * Reinitialize the beginning of the step.
     * @param interpolator valid for the current step
     * @exception MathIllegalStateException if the interpolator throws one because
     * the number of functions evaluations is exceeded
     */
    public void reinitializeBegin(final ODEStateInterpolator interpolator) throws MathIllegalStateException {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean evaluateStep(final ODEStateInterpolator interpolator) throws MathIllegalArgumentException, MathIllegalStateException {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * Estimate next state to check.
     * @param done state already checked
     * @param target target state towards which we are checking
     * @param interpolator step interpolator for the proposed step
     * @return intermediate state to check, or exactly {@code null}
     * if we already have {@code done == target}
     * @since 3.0
     */
    private ODEStateAndDerivative nextCheck(final ODEStateAndDerivative done, final ODEStateAndDerivative target, final ODEStateInterpolator interpolator) {
        if (done == target) {
            // we have already reached target
            return null;
        } else {
            // we have to select some intermediate state
            // attempting to split the remaining time in an integer number of checks
            final double dt = target.getTime() - done.getTime();
            final double maxCheck = detector.getMaxCheckInterval().currentInterval(done, dt >= 0.);
            final int n = FastMath.max(1, (int) FastMath.ceil(FastMath.abs(dt) / maxCheck));
            return n == 1 ? target : interpolator.getInterpolatedState(done.getTime() + dt / n);
        }
    }

    /**
     * Find a root in a bracketing interval.
     *
     * <p> When calling this method one of the following must be true. Either ga == 0, gb
     * == 0, (ga < 0  and gb > 0), or (ga > 0 and gb < 0).
     *
     * @param interpolator that covers the interval.
     * @param ta           earliest possible time for root.
     * @param ga           g(ta).
     * @param tb           latest possible time for root.
     * @param gb           g(tb).
     * @return if a zero crossing was found.
     */
    private boolean findRoot(final ODEStateInterpolator interpolator, final double ta, final double ga, final double tb, final double gb) {
        // check there appears to be a root in [ta, tb]
        check(ga == 0.0 || gb == 0.0 || (ga > 0.0 && gb < 0.0) || (ga < 0.0 && gb > 0.0));
        final int maxIterationCount = detector.getMaxIterationCount();
        final UnivariateFunction f = t -> g(interpolator.getInterpolatedState(t));
        // prepare loop below
        double loopT = ta;
        double loopG = ga;
        // event time, just at or before the actual root.
        double beforeRootT = Double.NaN;
        double beforeRootG = Double.NaN;
        // time on the other side of the root.
        // Initialized the the loop below executes once.
        double afterRootT = ta;
        double afterRootG = 0.0;
        // check for some conditions that the root finders don't like
        // these conditions cannot not happen in the loop below
        // the ga == 0.0 case is handled by the loop below
        if (ta == tb) {
            // both non-zero but times are the same. Probably due to reset state
            beforeRootT = ta;
            beforeRootG = ga;
            afterRootT = shiftedBy(beforeRootT, solver.getAbsoluteAccuracy());
            afterRootG = f.value(afterRootT);
        } else if (ga != 0.0 && gb == 0.0) {
            // hard: ga != 0.0 and gb == 0.0
            // look past gb by up to convergence to find next sign
            // throw an exception if g(t) = 0.0 in [tb, tb + convergence]
            beforeRootT = tb;
            beforeRootG = gb;
            afterRootT = shiftedBy(beforeRootT, solver.getAbsoluteAccuracy());
            afterRootG = f.value(afterRootT);
        } else if (ga != 0.0) {
            final double newGa = f.value(ta);
            if (ga > 0 != newGa > 0) {
                // both non-zero, step sign change at ta, possibly due to reset state
                final double nextT = minTime(shiftedBy(ta, solver.getAbsoluteAccuracy()), tb);
                final double nextG = f.value(nextT);
                if (nextG > 0.0 == g0Positive) {
                    // the sign change between ga and new Ga just moved the root less than one convergence
                    // threshold later, we are still in a regular search for another root before tb,
                    // we just need to fix the bracketing interval
                    // (see issue https://github.com/Hipparchus-Math/hipparchus/issues/184)
                    loopT = nextT;
                    loopG = nextG;
                } else {
                    beforeRootT = ta;
                    beforeRootG = newGa;
                    afterRootT = nextT;
                    afterRootG = nextG;
                }
            }
        }
        // loop to skip through "fake" roots, i.e. where g(t) = g'(t) = 0.0
        // executed once if we didn't hit a special case above
        while ((afterRootG == 0.0 || afterRootG > 0.0 == g0Positive) && strictlyAfter(afterRootT, tb)) {
            if (loopG == 0.0) {
                // ga == 0.0 and gb may or may not be 0.0
                // handle the root at ta first
                beforeRootT = loopT;
                beforeRootG = loopG;
                afterRootT = minTime(shiftedBy(beforeRootT, solver.getAbsoluteAccuracy()), tb);
                afterRootG = f.value(afterRootT);
            } else {
                // both non-zero, the usual case, use a root finder.
                if (forward) {
                    try {
                        final Interval interval = solver.solveInterval(maxIterationCount, f, loopT, tb);
                        beforeRootT = interval.getLeftAbscissa();
                        beforeRootG = interval.getLeftValue();
                        afterRootT = interval.getRightAbscissa();
                        afterRootG = interval.getRightValue();
                        // CHECKSTYLE: stop IllegalCatch check
                    } catch (RuntimeException e) {
                        // NOPMD
                        // CHECKSTYLE: resume IllegalCatch check
                        throw new MathIllegalStateException(e, LocalizedODEFormats.FIND_ROOT, detector, loopT, loopG, tb, gb, lastT, lastG);
                    }
                } else {
                    try {
                        final Interval interval = solver.solveInterval(maxIterationCount, f, tb, loopT);
                        beforeRootT = interval.getRightAbscissa();
                        beforeRootG = interval.getRightValue();
                        afterRootT = interval.getLeftAbscissa();
                        afterRootG = interval.getLeftValue();
                        // CHECKSTYLE: stop IllegalCatch check
                    } catch (RuntimeException e) {
                        // NOPMD
                        // CHECKSTYLE: resume IllegalCatch check
                        throw new MathIllegalStateException(e, LocalizedODEFormats.FIND_ROOT, detector, tb, gb, loopT, loopG, lastT, lastG);
                    }
                }
            }
            // tolerance is set to less than 1 ulp
            // assume tolerance is 1 ulp
            if (beforeRootT == afterRootT) {
                afterRootT = nextAfter(afterRootT);
                afterRootG = f.value(afterRootT);
            }
            // check loop is making some progress
            check((forward && afterRootT > beforeRootT) || (!forward && afterRootT < beforeRootT));
            // setup next iteration
            loopT = afterRootT;
            loopG = afterRootG;
        }
        // figure out the result of root finding, and return accordingly
        if (afterRootG == 0.0 || afterRootG > 0.0 == g0Positive) {
            // loop gave up and didn't find any crossing within this step
            return false;
        } else {
            // real crossing
            check(!Double.isNaN(beforeRootT) && !Double.isNaN(beforeRootG));
            // variation direction, with respect to the integration direction
            increasing = !g0Positive;
            pendingEventTime = beforeRootT;
            stopTime = beforeRootG == 0.0 ? beforeRootT : afterRootT;
            pendingEvent = true;
            afterEvent = afterRootT;
            afterG = afterRootG;
            // check increasing set correctly
            check(afterG > 0 == increasing);
            check(increasing == gb >= ga);
            return true;
        }
    }

    /**
     * Get the next number after the given number in the current propagation direction.
     *
     * @param t input time
     * @return t +/- 1 ulp depending on the direction.
     */
    private double nextAfter(final double t) {
        // direction
        final double dir = forward ? Double.POSITIVE_INFINITY : Double.NEGATIVE_INFINITY;
        return FastMath.nextAfter(t, dir);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public double getEventTime() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * Try to accept the current history up to the given time.
     *
     * <p> It is not necessary to call this method before calling {@link
     * #doEvent(ODEStateAndDerivative)} with the same state. It is necessary to call this
     * method before you call {@link #doEvent(ODEStateAndDerivative)} on some other event
     * detector.
     *
     * @param state        to try to accept.
     * @param interpolator to use to find the new root, if any.
     * @return if the event detector has an event it has not detected before that is on or
     * before the same time as {@code state}. In other words {@code false} means continue
     * on while {@code true} means stop and handle my event first.
     */
    public boolean tryAdvance(final ODEStateAndDerivative state, final ODEStateInterpolator interpolator) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public EventOccurrence doEvent(final ODEStateAndDerivative state) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * Shift a time value along the current integration direction: {@link #forward}.
     *
     * @param t     the time to shift.
     * @param delta the amount to shift.
     * @return t + delta if forward, else t - delta. If the result has to be rounded it
     * will be rounded to be before the true value of t + delta.
     */
    private double shiftedBy(final double t, final double delta) {
        if (forward) {
            final double ret = t + delta;
            if (ret - t > delta) {
                return FastMath.nextDown(ret);
            } else {
                return ret;
            }
        } else {
            final double ret = t - delta;
            if (t - ret > delta) {
                return FastMath.nextUp(ret);
            } else {
                return ret;
            }
        }
    }

    /**
     * Get the time that happens first along the current propagation direction: {@link
     * #forward}.
     *
     * @param a first time
     * @param b second time
     * @return min(a, b) if forward, else max (a, b)
     */
    private double minTime(final double a, final double b) {
        return forward ? FastMath.min(a, b) : FastMath.max(a, b);
    }

    /**
     * Check the ordering of two times.
     *
     * @param t1 the first time.
     * @param t2 the second time.
     * @return true if {@code t2} is strictly after {@code t1} in the propagation
     * direction.
     */
    private boolean strictlyAfter(final double t1, final double t2) {
        return forward ? t1 < t2 : t2 < t1;
    }

    /**
     * Same as keyword assert, but throw a {@link MathRuntimeException}.
     *
     * @param condition to check
     * @throws MathRuntimeException if {@code condition} is false.
     */
    private void check(final boolean condition) throws MathRuntimeException {
        if (!condition) {
            throw MathRuntimeException.createInternalError();
        }
    }
}
