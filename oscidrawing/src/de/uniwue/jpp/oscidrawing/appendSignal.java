package de.uniwue.jpp.oscidrawing;

import java.util.List;

public class appendSignal extends Signal{
    List<Signal> signals;
    int[] start;

    public appendSignal(List<Signal> signals) {
        if(signals == null) throw new NullPointerException("Die liste kann nicht 0 elemnte haben!");
        if(signals.isEmpty()) throw new IllegalArgumentException("Die Liste soll nicht leer sein ");

        this.signals = signals;
        start = new int[signals.size()];


        int channelCount = signals.get(0).getChannelCount();
        int sampleRate = signals.get(0).getSampleRate();

        int c=0;
        for (int i = 0; i < start.length; i++) {
            if(signals.get(i).getChannelCount()!= channelCount || signals.get(i).getSampleRate() != sampleRate)
                throw new IllegalArgumentException
                        ("Alle Signale in der Liste sollen die gleichen Sampleraten und Channelsanzahlen besitzen");
            start[i] = c;

            if(i!=start.length-1){
                if(signals.get(i).isInfinite()) throw new IllegalArgumentException("Nur das letzte Signal kann unendlich sein!");
                c+= signals.get(i).getSize();
            }
        }
    }

    @Override
    public boolean isInfinite() {
        return signals.get(signals.size()-1).isInfinite();
    }

    @Override
    public int getSize() {
        if(isInfinite()) return 0;

        return start[start.length-1] + signals.get(signals.size()-1).getSize();
    }

    @Override
    public int getChannelCount() {
        return signals.get(0).getChannelCount();
    }

    @Override
    public int getSampleRate() {
        return signals.get(0).getSampleRate();
    }

    @Override
    public double getValueAtValid(int channel, int index) {
        for (int i = 1; i < start.length; i++) {
            if(index<start[i]) return signals.get(i-1).getValueAt(channel,index-start[i-1]);
        }

        return signals.get(signals.size()-1).getValueAt(channel,index-start[start.length-1]);
    }
}
