import java.util.*;
import javax.sound.midi.*;

class MidiScale {
    private static final HashMap<String, Integer> notes = new HashMap<>();
    
    // Associate ints 0-11 with 12 note strings C-B
    static {
        notes.put("C",  0);
        notes.put("C#", 1);
        notes.put("Db", 1);
        notes.put("D",  2);
        notes.put("D#", 3);
        notes.put("Eb", 3);
        notes.put("E",  4);
        notes.put("F",  5);
        notes.put("F#", 6);
        notes.put("Gb", 6);
        notes.put("G",  7);
        notes.put("G#", 8);
        notes.put("Ab", 8);
        notes.put("A",  9);
        notes.put("A#", 10);
        notes.put("Bb", 10);
        notes.put("B",  11);
    }

    public static int[] getMidiScale(String rootNote, String octave, 
            String scale) {
        // Calculates root MIDI note number according to table found at
        // http://bit.ly/1jcEza5
        int rootMidiNote = (Integer.parseInt(octave) * 12) + 
                notes.get(rootNote);
        
        int[] majIntervals = {0, 2, 4, 5, 7, 9, 11, 12, 14, 16};
        int[] minIntervals = {0, 2, 3, 5, 7, 8, 10, 12, 14, 15};
        
        int[] noteArray = new int[10];
        
        // Adds major or minor intervals to root MIDI note, depending on
        // user's choice
        switch(scale) {
            case "maj":
                for(int i = 0; i < 10; i++) {
                    noteArray[i] = rootMidiNote + majIntervals[i];
                }
                break;
            case "min":
                for(int i = 0; i < 10; i++) {
                    noteArray[i] = rootMidiNote + minIntervals[i];
                }
                break;
        }
        return noteArray;
    }
}

class MidiPlayer {
    private static int midiChannel;    // 0 is a piano
    private static int noteVolume;   // between 0 and 127
    
    public MidiPlayer() {
        this(0, 127);
    }
    
    public MidiPlayer(int channel, int volume) {
        midiChannel = channel;
        noteVolume = volume;
    }
    
    public static void playNote(int note, int duration) {
        try {
            Synthesizer synth = MidiSystem.getSynthesizer();
            MidiChannel[] channels = synth.getChannels();
            
            synth.open();
            channels[midiChannel].noteOn(note, noteVolume);
            Thread.sleep(duration);
            channels[midiChannel].noteOff(note);
            synth.close();
        } 
        catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    public static void playSequence(int[] notesToPlay, int[] scale, 
            String duration) {
        try {
            Synthesizer synth = MidiSystem.getSynthesizer();
            MidiChannel[] channels = synth.getChannels();
            
            synth.open();
            for(int note : notesToPlay) {
                channels[midiChannel].noteOn(scale[note], noteVolume);
                Thread.sleep(Integer.parseInt(duration));
                channels[midiChannel].noteOff(scale[note]);
            }
            synth.close();
        } 
        catch (Exception e) {
            e.printStackTrace();
        }
    }
}

public class IrrationalMusic {
    
    public static void main(String[] args) {
        
        MidiPlayer player = new MidiPlayer();
        String piStr = Double.toString(Math.PI);
        piStr = piStr.substring(0,1) + piStr.substring(2);
        int[] piArray = new int[piStr.length()];
        for(int i=0; i<piStr.length(); i++) {
            piArray[i] = Character.getNumericValue(piStr.toCharArray()[i]);
        }
        player.playSequence(piArray, MidiScale.getMidiScale(args[0], args[1], 
                args[2]), args[3]);
    }
}