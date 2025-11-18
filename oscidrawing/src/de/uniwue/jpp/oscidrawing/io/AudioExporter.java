package de.uniwue.jpp.oscidrawing.io;

import de.uniwue.jpp.oscidrawing.Signal;

import java.io.*;

public class AudioExporter {

    public static boolean writeChannelToFile(String path, Signal signal, int channel) {
        if(signal.isInfinite()) throw new IllegalArgumentException("Signal muss nicht unendlich sein !!");

        try {

            DataOutputStream stream = new DataOutputStream(new FileOutputStream(new File(path + ".raw")));
            for (int i = 0; i < signal.getSize(); i++) {
                try {
                    stream.writeFloat((float) signal.getValueAt(channel,i));
                }catch (IOException e){
                    return false;

                }
            }

        }catch (FileNotFoundException e){
            return false;
        }

        return true;
    }

    public static boolean writeStereoToFiles(String path, Signal signal) {
        if(signal.getChannelCount()!=2) throw new IllegalArgumentException("Signal soll genau 2 Channels besitzen!!");


        return writeChannelToFile(path+ "left",signal,0) && writeChannelToFile(path+ "right",signal,1);
    }
}
