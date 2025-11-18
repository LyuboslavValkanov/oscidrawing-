package de.uniwue.jpp.oscidrawing.visualization;

import de.uniwue.jpp.oscidrawing.Signal;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.Arrays;

public class SignalTimePlotterImpl implements SignalTimePlotter{
    BufferedImage image;


    int bgcol;
    int axiscol;

    double valScale;
    double timeScale;
    public SignalTimePlotterImpl(int width, int height, double valScale, double timeScale, Color bgcol, Color axiscol) {
        image =new BufferedImage(width,height, BufferedImage.TYPE_INT_ARGB);
        this.bgcol = bgcol.getRGB();
        this.axiscol = axiscol.getRGB();
        this.valScale = valScale;
        this.timeScale =timeScale;


        int mid = height/2;  //gerade Zahl


        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {

                if(i==mid){
                    image.setRGB(j,i,axiscol.getRGB());
                }else {
                    image.setRGB(j,i,bgcol.getRGB());
                }


            }
        }


    }

    @Override
    public int sampleIndexToImageXCoord(int sampleIndex, int sampleRate) {

        double inMin = 0;
        double inMax =  sampleRate * timeScale -1 ;
        double outMin = 0;
        double outMax = image.getWidth()-1;

        return (int) ((sampleIndex - inMin) * (outMax - outMin) / (inMax - inMin) + outMin);
        }

    @Override
    public int signalValToImageYCoord(double val) {

        double in_min =  valScale;
        double in_max =  valScale*(-1);
        double out_min = 0;
        double out_max =image.getHeight()-1;
        return  (int)(  (((val - in_min) * (out_max - out_min)) / (in_max - in_min)) + out_min); //-1746,5 /-3=582
    }

    @Override
    public void drawSignalAt(Signal signal, int channel, int index, Color col) {
        try {


            int xcoord = sampleIndexToImageXCoord(index, signal.getSampleRate());
            int ycoord = signalValToImageYCoord(signal.getValueAt(channel, index));


            if (image.getHeight() > ycoord && image.getWidth() > xcoord) {
                try {
                    image.setRGB(xcoord, ycoord, col.getRGB());
                }catch (ArrayIndexOutOfBoundsException e){
                    //do nothing
                }
            }
        }catch (ArithmeticException e){

        }
    }

    @Override
    public void drawSignal(Signal signal, int channel, Color col) {
        for (int i = 0; i < signal.getSize(); i++) {
            drawSignalAt(signal,channel,i,col);
        }
    }

    @Override
    public void drawSignal(Signal signal, Color... colors) {
        if(colors.length != signal.getChannelCount())
            throw new IllegalArgumentException ("Die Anzahl der Channels soll aquivalent zu der Anzahl der Colormenge");

        for (int i = 0; i < signal.getChannelCount(); i++) {
            drawSignal(signal,i,colors[i]);

        }
    }

    @Override
    public BufferedImage getImage() {
        return image;
    }
}
