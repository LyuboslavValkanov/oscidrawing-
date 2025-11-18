package de.uniwue.jpp.oscidrawing.generation;

import de.uniwue.jpp.oscidrawing.Signal;

public class droppedSignal extends Signal {
    int dropCount;
    Signal underlyingSignal;


    public droppedSignal(int dropCount, Signal underlyingSignal) {
        this.dropCount = dropCount;
        this.underlyingSignal = underlyingSignal;
    }

    @Override
    public boolean isInfinite() {
       return underlyingSignal.isInfinite();
    }

    @Override
    public int getSize() {
        if(!underlyingSignal.isInfinite() && underlyingSignal.getSize()<dropCount) return 0;
        else if (!underlyingSignal.isInfinite()) {
            return underlyingSignal.getSize()-dropCount;
        }else return 0;
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
        return underlyingSignal.getValueAtValid(channel,index+dropCount);
    }
}
