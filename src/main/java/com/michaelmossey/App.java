package com.michaelmossey;

import com.michaelmossey.midi.*;
import java.util.*;
import javax.sound.midi.*;
/**
 * Hello world!
 *
 */
public class App 
{
    public static void main( String[] args ) throws Exception
    {
        public static void runMusic() throws Exception {
        Scanner console = new Scanner(System.in);
        System.out.print("Type (1: all Notes off), (2: play): ");
        int i = console.nextInt();
        if (i == 1) {
            MidiInterface.openMidiDevice();
            ShortMessage msg = new ShortMessage();
            msg.setMessage(0xb0, 1, 120, 0);
            MidiInterface.sendShort(msg);
            msg.setMessage(0xb0, 1, 123, 0);
            MidiInterface.sendShort(msg);
            MidiInterface.closeMidiDevice();
        } else if (i == 2) {
            MidiApp.piece1();
        } else {
            System.out.println("Unrecognized option.");
        }
        console.close();
    }
}
