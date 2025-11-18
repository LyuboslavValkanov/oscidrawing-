package de.uniwue.jpp.oscidrawing.generation;

import de.uniwue.jpp.oscidrawing.*;
import de.uniwue.jpp.oscidrawing.generation.pathutils.Point;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.BiFunction;
import java.util.function.DoubleUnaryOperator;

public abstract class SignalFactory {



    public static Signal fromValues(double[] signalData, int sampleRate){
        if(sampleRate<=0) throw new IllegalArgumentException("SampleRate soll positiv sein !");



         return new SimpleSignalImpl(signalData,sampleRate);

        }





    public static Signal wave(DoubleUnaryOperator function, double frequency, double duration, int sampleRate) {
        if(frequency<=0) throw new IllegalArgumentException("Frequency soll immer positiv und groesser als 0  sein !");
        if(duration<=0) throw new IllegalArgumentException("Duration soll immer positiv und groesser als 0 sein !");
        if(sampleRate<=0) throw new IllegalArgumentException("SampleRate soll immer positiv  und groesser als 0 sein !");
        double step =  ((frequency*Math.PI*2) / sampleRate);
        int length = (int) ( sampleRate*duration);
        double[] values = new double[length];

        for (int i = 0; i < length; i++) {
            values[i] = function.applyAsDouble(i*step);

        }

        return fromValues(values,sampleRate);
    }

    public static Signal rampUp(double duration, int sampleRate) {
        if(duration<=0) throw new IllegalArgumentException ("Duration soll immer positiv und groesser als 0 sein !");
        if(sampleRate<=0) throw new IllegalArgumentException("SampleRate soll immer positiv  und groesser als 0 sein !");
        if(sampleRate<=2) throw new IllegalArgumentException("SampleRate muss groesser als 2 wegen der Duration");


        int length = (int) (sampleRate*duration);
        double[] values = new double[length];

        for (int i = 0; i < length; i++) {
            values[i] = (double) i/((double)length-1);
        }

        return fromValues(values,sampleRate);
    }

    public static Signal combineMonoSignals(List<Signal> signals) {


        return new MultiChannelSignal(signals);
    }

    public static Signal combineMonoSignals(Signal... signals) {

        return combineMonoSignals(Arrays.stream(signals).toList());
    }

    public static Signal stereoFromMonos(Signal left, Signal right) {
        Signal[] stereo = {left,right};
        if(left.getChannelCount()!=1 || right.getChannelCount()!=1)
            throw new IllegalArgumentException("Channel Count von Monos muss 1 sein");
        return combineMonoSignals(stereo);
    }

    public static Signal extractChannels(Signal source, int... channels) {
        if(source==null) throw new NullPointerException("Source soll nicht 0 sein !");
        if(channels.length>source.getChannelCount())throw new IllegalArgumentException("nicht genug channels in source");


        List<Signal> extractedSignals = new ArrayList<>();

        for (Integer i :
                channels) {

            if(i>=source.getChannelCount()) throw new IllegalArgumentException("channel doesnt exist");
            int lengthNew = source.getSize();
            double[] values = new double[lengthNew];

            for (int j = 0; j < lengthNew; j++) {
                values[j] = source.getValueAt(i,j);
            }
            extractedSignals.add(fromValues(values,source.getSampleRate()));
        }

        return combineMonoSignals(extractedSignals);
    }

    public static Signal circle(double frequency, double duration, int sampleRate) {
        return stereoFromMonos(wave(x -> Math.sin(x),frequency,duration,sampleRate),wave(x -> Math.cos(x),frequency,duration,sampleRate));
    }

    public static Signal cycle(Signal signal) {
        if(signal==null) throw new NullPointerException("Signal muss existieren !");
        return new infSignalImpl(signal);
    }

    public static Signal infiniteFromValue(double value, int sampleRate) {
        double[] values = {value};
        return cycle(fromValues(values,sampleRate));
    }

    public static Signal take(int count, Signal source) {
        if(count<0) throw new IllegalArgumentException("Count soll nicht negative sein!");
        List<Signal> extractedSignals = new ArrayList<>();

        for  (int i = 0; i < source.getChannelCount(); i++) {
            double[] values = new double[count];

            for (int j = 0; j < count; j++) {
                if(!source.isInfinite() && j >= source.getSize()) values[j] = 0;
                else values[j] = source.getValueAt(i,j);
            }
            extractedSignals.add(fromValues(values,source.getSampleRate()));
        }

        return combineMonoSignals(extractedSignals);
    }

    public static Signal drop(int count, Signal source) {
        if(count<0) throw new IllegalArgumentException("Count soll nicht negative sein!");
        return new droppedSignal(count,source);
    }

    public static Signal transform(DoubleUnaryOperator function, Signal source) {
        if(function == null || source == null) throw new NullPointerException("Function und Source sollen realen Werten besitzen");
        return new transformedSignal(source,function);
    }

    public static Signal scale(double amplitude, Signal source) {
        DoubleUnaryOperator function = new DoubleUnaryOperator() {
            @Override
            public double applyAsDouble(double operand) {
                return operand*amplitude;
            }
        };

        return transform(function,source);
    }

    public static Signal reverse(Signal source) {
        if(source == null) throw new NullPointerException("Source soll nicht 0 sein ");
        if(source.isInfinite()) throw new IllegalArgumentException("Source soll nicht undendlich sein!");

        return new reverseSignal(source);
    }

    public static Signal rampDown(double duration, int sampleRate) {
        if(duration<=0) throw new IllegalArgumentException("Duration soll immer positiv und groesser als 0 sein !");
        if(sampleRate<=0) throw new IllegalArgumentException("SampleRate soll immer positiv  und groesser als 0 sein !");
        if(sampleRate<=2) throw new IllegalArgumentException("SampleRate muss groesser als 2 wegen der Duration");


        int length = (int) (sampleRate*duration);
        double[] values = new double[length];

        for (int i = 0; i < length; i++) {
            values[i] = 1 - (double) i/((double)length-1);
        }

        return fromValues(values,sampleRate);
    }

    public static Signal merge(BiFunction<Double, Double, Double> function, Signal s1, Signal s2) {
        if(s1==null||s2==null||function==null)
            throw new NullPointerException("S1 und s2  und Function sollen realen Werten haben ,um zu funktionieren");
        if (s1.getSampleRate()!= s2.getSampleRate())
            throw new IllegalArgumentException("s1 und s2 sollen das gleiche Anzahl von Samplerate besitzen");
        if(s1.getChannelCount()!= s2.getChannelCount())
            throw new IllegalArgumentException("s1 und s2 sollen das gleiche Anzahl von Channel besitzen");

        return new mergedSignal(function,s1,s2);

    }

    public static Signal add(Signal s1, Signal s2) {
        return merge(Double::sum,s1,s2);
    }

    public static Signal mult(Signal s1, Signal s2) {
        return merge((x,y)->{
            return x*y;
        },s1,s2);
    }

    public static Signal append(List<Signal> signals) {
        return new appendSignal(signals);
    }

    public static Signal append(Signal... signals) {
        return append(Arrays.stream(signals).toList());
    }

    public static Signal translate(List<Double> distances, Signal signal) {
        return new translateSignal(distances, signal);
    }

    public static Signal fromPath(List<Point> points, double frequency, int sampleRate) {
        if(frequency<=0) throw new IllegalArgumentException("Frequency soll positiv sein");
        if(sampleRate<= 0) throw new IllegalArgumentException("SampleRate soll positiv sein ");

        if(points == null || points.isEmpty() || points.size()<2)
            throw new IllegalArgumentException("Die Liste  muss mindestens 2 Points besitzen! ");

        return new signalFromPath(points,frequency,sampleRate);
    }

    /* Optional */
    public static Signal myCoolSignal() {
        return null;
    }
}
