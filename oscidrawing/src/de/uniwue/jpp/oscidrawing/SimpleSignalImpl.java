package de.uniwue.jpp.oscidrawing;

public class SimpleSignalImpl extends Signal {
    double[] values;
    int sampleRate;


    public SimpleSignalImpl(double[] values, int sampleRate) {
        this.values = values;
        this.sampleRate = sampleRate;

    }

    @Override
    public boolean isInfinite() {
        return false;
    }

    @Override
    public int getSize() {
        return values.length;
    }

    @Override
    public int getChannelCount() {
        return 1;
    }

    @Override
    public int getSampleRate() {
        return sampleRate;
    }

    @Override
    public double getValueAtValid(int channel, int index) {
        return values[index];
    }
}
