import javax.sound.midi.*;

public class MidiPlayer {
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