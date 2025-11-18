package de.uniwue.jpp.oscidrawing;

public abstract class Signal {
    public abstract boolean isInfinite();

    public abstract int getSize();

    public abstract int getChannelCount();

    public abstract int getSampleRate();

    public abstract double getValueAtValid(int channel, int index);

    public double getDuration() {
        if(!isInfinite()) return ((double) getSize()/ (double) getSampleRate());

        return 0;
    }

    public double getValueAt(int channel, int index) {
        if(channel >= getChannelCount() || index < 0 || channel < 0 || (!isInfinite()&&index>=getSize())) return 0;

        return getValueAtValid(channel,index);
    }
}
