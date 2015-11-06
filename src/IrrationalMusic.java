import java.util.*;
import javax.sound.midi.*;

// Associate ints 0-11 with 12 note strings C-B
class PitchDictionary {
    private final HashMap<String, Integer> notes;
    
    // Constructor initializes key-value pairs
    public PitchDictionary() {
        notes = new HashMap<>();
        notes.put("C", 0);
        notes.put("C#", 1);
        notes.put("D", 2);
        notes.put("D#", 3);
        notes.put("E", 4);
        notes.put("F", 5);
        notes.put("F#", 6);
        notes.put("G", 7);
        notes.put("G#", 8);
        notes.put("A", 9);
        notes.put("A#", 10);
        notes.put("B", 11);
    }
    
    public int getNumber(String noteName) {
        return notes.get(noteName);
    }
}

public class IrrationalMusic {
    
    static PitchDictionary pitchDict = new PitchDictionary();
    
    public static int[] getMidiScale(String rootNote, int octave, String scale) {
        // Calculates root MIDI note number according to table found at
        // http://bit.ly/1jcEza5
        int rootMidiNote = (octave * 12) + pitchDict.getNumber(rootNote);
        
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
    
    public static void main(String[] args) {
        final int CHANNEL = 0;    // 0 is a piano
        final int VOLUME = 127;    // between 0 and 127
        final int DURATION = 500; // in milliseconds
        
        try {
            Synthesizer synth = MidiSystem.getSynthesizer();
            synth.open();
            MidiChannel[] channels = synth.getChannels();
            
            for(int midiNote : getMidiScale("C", 4, "maj")) {
                channels[CHANNEL].noteOn(midiNote, VOLUME);
                Thread.sleep(DURATION);
                channels[CHANNEL].noteOff(midiNote);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        
        //System.out.println(Arrays.toString(midiNotes("C", 4, "maj")));
    }
    
}