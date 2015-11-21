import java.util.*;

public class MidiScale {
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

    public static int[] getScale(String rootNote, String octave, 
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