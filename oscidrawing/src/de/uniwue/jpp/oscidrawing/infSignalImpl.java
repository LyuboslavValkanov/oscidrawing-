package de.uniwue.jpp.oscidrawing;

public class infSignalImpl extends Signal{
    Signal underlyingSignal;

    public infSignalImpl(Signal underlyingSignal) {
        this.underlyingSignal = underlyingSignal;
    }

    @Override
    public boolean isInfinite() {
        return true;
    }

    @Override
    public int getSize() {
        return 0;
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
        if(underlyingSignal.isInfinite()) return underlyingSignal.getValueAt(channel,index);


        return underlyingSignal.getValueAt(channel,index%underlyingSignal.getSize());
    }
}
