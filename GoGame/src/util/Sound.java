package util;

import java.applet.*;
import java.net.URL;
import java.net.MalformedURLException;

public class Sound {
    AudioClip   audioClip = null;

    public Sound(String audioFN) {
        String s = System.getProperty("file.separator");
        String urlFN = "";
        for (int i = 0; i < audioFN.length(); i++) {
            if (!audioFN.substring(i, i+1).equals(s)) urlFN = urlFN + audioFN.substring(i, i+1);
            else urlFN = urlFN + "/";
        }
        
        URL fileURL = null;
        try {
            fileURL = new URL("file:///" + urlFN);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        audioClip = Applet.newAudioClip(fileURL);
    }

    public void loop() {
        if (audioClip != null) audioClip.loop();
    }

    public void stop() {
        if (audioClip != null) audioClip.stop();
    }    
}