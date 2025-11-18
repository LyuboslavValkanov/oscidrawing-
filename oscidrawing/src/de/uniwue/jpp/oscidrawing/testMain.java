package de.uniwue.jpp.oscidrawing;

import de.uniwue.jpp.oscidrawing.generation.SignalFactory;
import de.uniwue.jpp.oscidrawing.generation.pathutils.*;
import de.uniwue.jpp.oscidrawing.generation.pathutils.Point;
import de.uniwue.jpp.oscidrawing.io.ImageExporter;
import de.uniwue.jpp.oscidrawing.visualization.SignalTimePlotter;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.util.function.UnaryOperator;

public class testMain {
    public static void main(String[] args) {

        List<Point> pointList = new ArrayList<>();
        pointList.add(new Point(-0.5, -0.5));
        pointList.add(new Point(-0.5, 0.5));
        pointList.add(new Point(0.5, -0.5));
        pointList.add(new Point(0.5, 0.5));
        pointList.add(new Point(-0.5, 0.5));
        pointList.add(new Point(0, 1));
        pointList.add(new Point(0.5, 0.5));
        pointList.add(new Point(-0.5, -0.5));
        pointList.add(new Point(0.5, -0.5));

     //   Signal signal = SignalFactory.fromPath(pointList,0.5,1000);

        SignalTimePlotter stp = SignalTimePlotter.createSignalTimePlotter(1000,1000,2,2, Color.BLACK,Color.BLUE);

       // stp.drawSignal(signal,0,Color.MAGENTA);


        //ImageExporter.writeToPNG("C:\\Users\\Lyubo\\Desktop\\TestsJPP",stp.getImage());


        Signal testSignal = SignalFactory.fromPath(List.of(new Point(0, 0), new Point(1, 1)), 1.000000, 100);
        System.out.println(testSignal.getSize());




    }
}

