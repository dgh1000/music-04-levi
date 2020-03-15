package com.michaelmossey;

import com.michaelmossey.core.*;
import com.michaelmossey.midi.*;
import com.michaelmossey.util.*;
import java.util.*;

public class MidiApp
{
    public static void piece1() throws Exception
    {
        List<Integer> ps = new ArrayList<>(
            Arrays.asList(
            Pitch.fromString("C#4"),
            Pitch.fromString("E4"),
            Pitch.fromString("G#4")
            ));
        PitchSet p = new PitchSet(ps);
        List<Note> notes = p.playMelody(1.0, 1.0, 0.9);
        MidiInterface.openMidiDevice();
        MidiInterface.playNotes(notes, new ArrayList<Raw>(), 1000);
        MidiInterface.closeMidiDevice();
    }
}