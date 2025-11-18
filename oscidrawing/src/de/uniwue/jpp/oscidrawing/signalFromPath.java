package de.uniwue.jpp.oscidrawing;

import de.uniwue.jpp.oscidrawing.generation.SignalFactory;
import de.uniwue.jpp.oscidrawing.generation.pathutils.Line;
import de.uniwue.jpp.oscidrawing.generation.pathutils.Point;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class signalFromPath extends Signal{

    Signal stereoSignal;

    public signalFromPath(List<Point> points, double frequency, int sampleRate) {

        double duration = 1 / frequency;

        List<Line> lines = getLines(points);

        Map<Line, Double> lineLengths = getLineLengths(lines);

        double pathLength = getPathLength(lineLengths);

        List<Double> normLineLengths = normalizedLineLengths(lines,lineLengths,pathLength);

        List<Integer> pointsPerLine = getPointsPerLine(duration,lines,normLineLengths,sampleRate);

        List<Point> interpolatedPoints = getInterpolatedPoints(lines,pointsPerLine);

        stereoSignal = createStereoSignalfromPoints(interpolatedPoints,sampleRate);

    }

    static Signal createStereoSignalfromPoints (List<Point> points, int sampleRate){
        double[] x = new double[points.size()];
        double[] y = new double[points.size()];

        for (int i = 0; i < points.size() ; i++) {
            x[i] = points.get(i).getX();
            y[i] = points.get(i).getY();
        }

        return SignalFactory.stereoFromMonos(SignalFactory.fromValues(x,sampleRate),SignalFactory.fromValues(y,sampleRate));
    }

    static List<Point> getInterpolatedPoints (List<Line> lines, List<Integer> pointsPerLine){
        List<Point> interpolatedPoints = new ArrayList<>();

        for (int i = 0; i < lines.size() ; i++) {

            List<Integer> indices = Stream.iterate(0,n  -> n+1 ).limit(pointsPerLine.get(i)).toList();

            List<Double> progress = new ArrayList<>();

            for (Integer x :
                    indices) {
                progress.add((double) x/ (double) pointsPerLine.get(i));
            }

            for (Double x :
                    progress) {
                interpolatedPoints.add(lines.get(i).getPointAt(x));
            }
        }

        return interpolatedPoints;
    }

    static List<Integer> getPointsPerLine (double duration, List<Line> lines, List<Double> normLengths, int sampleRate){
        List<Integer> toReturn = new ArrayList<>();

        for (int i = 0; i < lines.size() ; i++) {
            double lDur = duration * normLengths.get(i);
            double doublePoints = lDur * sampleRate;

            toReturn.add((int) doublePoints);
        }
        
        return toReturn;
    }

    static List<Double> normalizedLineLengths (List<Line> lines, Map<Line, Double> lineLengths, double pathLength){
        List<Double> toReturn = new ArrayList<>();

        for (Line line :
                lines) {
            toReturn.add(lineLengths.get(line)/pathLength);
        }

        return toReturn;
    }

    static double getPathLength (Map<Line, Double> lengths){
        double pathLength = 0;

        for (Double i :
                lengths.values()) {
            pathLength+=i;
        }

        return pathLength;
    }

    static List<Line> getLines (List<Point> points){
        List<Line> toReturn = new ArrayList<>();
        for (int i = 0; i < points.size() - 1; i++) {
            toReturn.add(new Line(points.get(i),points.get(i+1)));
        }
        return toReturn;
    }


    static Map<Line, Double> getLineLengths (List<Line> lines){
        Map<Line ,Double> lineMap = new HashMap<>();
        for (Line i:lines
             ) {
            lineMap.put(i,i.length());


        }


        return lineMap;
    }
    @Override
    public boolean isInfinite() {
        return stereoSignal.isInfinite();
    }

    @Override
    public int getSize() {
        return stereoSignal.getSize();
    }

    @Override
    public int getChannelCount() {
        return stereoSignal.getChannelCount();
    }

    @Override
    public int getSampleRate() {
        return stereoSignal.getSampleRate();
    }

    @Override
    public double getValueAtValid(int channel, int index) {
        return stereoSignal.getValueAt(channel,index);
    }
}
