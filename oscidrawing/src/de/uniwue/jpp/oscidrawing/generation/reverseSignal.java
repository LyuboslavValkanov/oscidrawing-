package de.uniwue.jpp.oscidrawing.generation;

import de.uniwue.jpp.oscidrawing.Signal;

public class reverseSignal extends Signal {
    Signal source;

    public reverseSignal(Signal source) {
        this.source = source;
    }

    @Override
    public boolean isInfinite() {
        return false;
    }

    @Override
    public int getSize() {
        return source.getSize();
    }

    @Override
    public int getChannelCount() {
        return source.getChannelCount();
    }

    @Override
    public int getSampleRate() {
        return source.getSampleRate();
    }

    @Override
    public double getValueAtValid(int channel, int index) {
        return source.getValueAt(channel, getSize()-1-index);
    }
}
