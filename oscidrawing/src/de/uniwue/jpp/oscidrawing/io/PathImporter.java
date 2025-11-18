package de.uniwue.jpp.oscidrawing.io;

import de.uniwue.jpp.oscidrawing.generation.pathutils.Point;

import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class PathImporter {

    public static Optional<List<Point>> fromString(List<String> lines) {

        List<Point> points = new ArrayList<>();

        for (String line :
                lines) {
            String[] data = line.split(",");
            if(data.length!=2) return Optional.empty();

            try {


                points.add(new Point(Double.parseDouble(data[0]), Double.parseDouble(data[1])));

            }catch (Exception e){
                return Optional.empty();
            }
        }

        return Optional.of(points);
    }

    public static Optional<List<Point>> fromFile(String path) {

        try {
            BufferedReader br = new BufferedReader(new FileReader(path));

            return fromString(br.lines().toList());
        } catch (FileNotFoundException e) {
            return Optional.empty();
        }

    }
}
