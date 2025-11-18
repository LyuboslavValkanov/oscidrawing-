package de.uniwue.jpp.oscidrawing;

import java.util.List;

public class MultiChannelSignal extends Signal{
    List<Signal> signals;
    int sampleRate;
    int length;
boolean isInf;

    public MultiChannelSignal(List<Signal> signals) {
        if(signals == null) throw new NullPointerException("Signals sollen nicht 0 sein !");
        if(sampleRate<0) throw new IllegalArgumentException("Die simpleRate muss auf jeden fall nicht negativ sein");
        if(signals.isEmpty()) throw new IllegalArgumentException("Die Liste von signals  soll nicht leer sein !");
        length = Integer.MAX_VALUE;
        sampleRate = signals.get(0).getSampleRate();
        for (Signal signal :
                signals) {
            if(signal == null || signal.getChannelCount()<1 || signal.getSampleRate()!=sampleRate)
                throw new IllegalArgumentException("Signal soll auf jeden Fall Monosignal sein!");

            if(!signal.isInfinite() && signal.getSize()< length) length = signal.getSize();

        }
        if (length == Integer.MAX_VALUE) {
            length = 0;
            isInf = true;
        }else isInf=false;

        this.signals = signals;
        this.sampleRate = sampleRate;
    }

    @Override
    public boolean isInfinite() {
        return isInf;
    }

    @Override
    public int getSize() {
        return length;
    }

    @Override
    public int getChannelCount() {
        return signals.size();
    }

    @Override
    public int getSampleRate() {
        return sampleRate;
    }


    @Override
    public double getValueAtValid(int channel, int index) {
        return signals.get(channel).getValueAtValid(0,index);
    }
}
