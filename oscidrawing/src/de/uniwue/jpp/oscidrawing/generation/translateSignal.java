package de.uniwue.jpp.oscidrawing.generation;

import de.uniwue.jpp.oscidrawing.Signal;

import java.util.List;

public class translateSignal  extends Signal{
    List<Double> distances;
    Signal source;

    public translateSignal(List<Double> distances, Signal source) {
        if(distances == null || source == null) throw new NullPointerException("Distances und source muss nicht null sein!");

        if(distances.size() != source.getChannelCount())
            throw new IllegalArgumentException("Distances muss genauso viele Elemente wie signal Channels besitzen ");
        this.distances = distances;
        this.source = source;
    }

    @Override
    public boolean isInfinite() {
        return source.isInfinite();
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
        return source.getValueAt(channel,index)+ distances.get(channel);
    }
}
