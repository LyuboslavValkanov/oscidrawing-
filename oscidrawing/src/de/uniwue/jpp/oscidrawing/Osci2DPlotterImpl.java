package de.uniwue.jpp.oscidrawing;

import de.uniwue.jpp.oscidrawing.visualization.Osci2DPlotter;

import java.awt.*;
import java.awt.image.BufferedImage;

public class Osci2DPlotterImpl implements Osci2DPlotter {

    BufferedImage image;
    int size;
    double scale;
    Color bgcol;

    public Osci2DPlotterImpl(int size, double scale, Color bgcol) {

        this.size = size;
        this.scale = scale;
        this.bgcol = bgcol;

        image = new BufferedImage(size, size, BufferedImage.TYPE_INT_ARGB);
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                image.setRGB(j, i, bgcol.getRGB());
            }
        }
    }

    @Override
    public int signalValToImageXCoord(double val) {
        double inMin = scale * (-1);
        double inMax = scale;
        double outMin = 0;
        double outMax = size - 1;
        return (int) ((val - inMin) * (outMax - outMin) / (inMax - inMin) + outMin);

    }

    @Override
    public int signalValToImageYCoord(double val) {
        double in_min = scale;
        double in_max = scale * (-1);
        double out_min = 0;
        double out_max = size - 1;


        return (int) ((val - in_min) * (out_max - out_min) / (in_max - in_min) + out_min);
    }

    @Override
    public void drawSignalAt(Signal signal, int index, Color col) {

        if(signal.getChannelCount()!=2) throw new IllegalArgumentException("Das Signal soll genau 2 Channels besitzen !");

        int xcoord = signalValToImageXCoord(signal.getValueAt(0,index));
        int ycoord = signalValToImageYCoord(signal.getValueAt(1, index));

        if (image.getHeight() > ycoord && image.getWidth() > xcoord) {
            try {
                image.setRGB(xcoord, ycoord, col.getRGB());
            }catch (ArrayIndexOutOfBoundsException e){
                //do nothing
            }
        }

    }

    @Override
    public void drawSignal(Signal signal, Color col) {
        if(signal.isInfinite()) throw new IllegalArgumentException("Das Signal soll nicht unendlich sein !");

        for (int i = 0; i < signal.getSize(); i++) {
            drawSignalAt(signal,i,col);
        }
    }

    @Override
    public BufferedImage getImage() {
        return image;
    }
}
