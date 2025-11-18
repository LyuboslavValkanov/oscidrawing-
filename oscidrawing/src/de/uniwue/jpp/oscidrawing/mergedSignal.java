package de.uniwue.jpp.oscidrawing;

import java.util.function.BiFunction;

public class mergedSignal extends Signal{
    BiFunction<Double,Double,Double> function;
    Signal smallSignal;
    Signal bigSignal;

    public mergedSignal(BiFunction<Double, Double, Double> function, Signal signal1, Signal signal2) {
        this.function = function;

        if(signal1.isInfinite() && signal2.isInfinite()){
            smallSignal=signal1;
            bigSignal = signal2;
        }else if(signal1.getSize() == signal2.getSize()){
            smallSignal=signal1;
            bigSignal = signal2;
        } else if (signal1.getSize()< signal2.getSize()) {
            smallSignal=signal1;
            bigSignal = signal2;
        } else {
            smallSignal = signal2;
            bigSignal = signal1;
        }

    }

    @Override
    public boolean isInfinite() {
        return smallSignal.isInfinite();
    }

    @Override
    public int getSize() {
        return smallSignal.getSize();
    }

    @Override
    public int getChannelCount() {
        return smallSignal.getChannelCount();
    }

    @Override
    public int getSampleRate() {
        return smallSignal.getSampleRate();
    }

    @Override
    public double getValueAtValid(int channel, int index) {
        return function.apply(smallSignal.getValueAt(channel,index),bigSignal.getValueAt(channel,index));
    }
}
