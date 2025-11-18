package de.uniwue.jpp.oscidrawing.generation;

import de.uniwue.jpp.oscidrawing.Signal;

import java.util.function.DoubleUnaryOperator;

public class transformedSignal extends Signal{
    Signal underlyingSignal;
    DoubleUnaryOperator function;

    public transformedSignal(Signal underlyingSignal, DoubleUnaryOperator function) {
        this.underlyingSignal = underlyingSignal;
        this.function = function;
    }

    @Override
    public boolean isInfinite() {
        return underlyingSignal.isInfinite();
    }

    @Override
    public int getSize() {
        return underlyingSignal.getSize();
    }

    @Override
    public int getChannelCount() {
        return underlyingSignal.getChannelCount();
    }

    @Override
    public int getSampleRate() {
        return underlyingSignal.getSampleRate();
    }

    @Override
    public double getValueAtValid(int channel, int index) {
        return function.applyAsDouble(underlyingSignal.getValueAt(channel,index));
    }
}
