public class IrrationalMusic {
    
    public static void main(String[] args) {
        
        MidiPlayer player = new MidiPlayer();
        
        String piStr = Double.toString(Math.PI);
        piStr = piStr.substring(0,1) + piStr.substring(2);
        int[] piArray = new int[piStr.length()];
        for(int i=0; i<piStr.length(); i++) {
            piArray[i] = Character.getNumericValue(piStr.toCharArray()[i]);
        }
        player.playSequence(piArray, MidiScale.getScale(args[0], args[1], 
                args[2]), args[3]);
    }
}