
package com.michaelmossey.midi;

import java.util.*;
import javax.sound.midi.*;
import com.michaelmossey.core.*;

public class MidiInterface {
    static MidiDevice midiDevice;
    static Receiver receiver;

    static public void openMidiDevice() throws Exception {
        Map<String, String> env = System.getenv();
        String midiInputName = env.get("MMIDI");
        if (midiInputName == null)
            throw new Exception("the MMIDI env variable isn't defined");

        MidiDevice.Info[] infos = MidiSystem.getMidiDeviceInfo();
        MidiDevice.Info theOne = null;
        for (MidiDevice.Info i : infos) {
            if (i.getName().equals(midiInputName)) {
                theOne = i;
            }
        }
        if (theOne == null)
            throw new Exception("No such input MIDI device:" + midiInputName);
        try {
            midiDevice = MidiSystem.getMidiDevice(theOne);
            if (!midiDevice.isOpen()) {
                midiDevice.open();
                receiver = midiDevice.getReceiver();
            } else {
                System.out.println("Strange, MIDI device was already open");
            }

        } catch (MidiUnavailableException e) {
            System.out.println("midi unavailable exception");
        }
        ShortMessage msg = new ShortMessage();
        msg.setMessage(0xc0, 1, 3, 0);
        sendShort(msg);
    }

    static public void closeMidiDevice() {
        midiDevice.close();
    }

    static public void playNotes(List<Note> notes, List<Raw> other, int offset) {
        List<Raw> notesL = new ArrayList<>();

        for (Note n : notes) {
            Raw[] mns = n.toRaw(offset);
            notesL.add(mns[0]);
            notesL.add(mns[1]);
        }
        notesL.addAll(other);
        Collections.sort(notesL);

        try {
            // Receiver r = midiDevice.getReceiver();
            int currentT = 0;
            for (Raw n : notesL) {
                // what does -1 mean in send? send now?
                // why are we sending now? why are we only creating
                // 1 message? where is sending whole comp? Have
                // we tested that yet?
                int t = n.timestamp;
                int ms = t - currentT;
                if (ms <= 0) {
                    receiver.send(n.msg, -1);
                } else {
                    Thread.sleep(ms);
                    receiver.send(n.msg, -1);
                    currentT = t;
                }
            }
        } catch (InterruptedException e) {
            System.out.println("Playback interrupted");
        }
    }

    static public void sendShort(ShortMessage msg) {
        receiver.send(msg, -1);
    }

    static public void inspectDevices() {
        System.out.println("Inspect the existing MIDI interfaces:");
        MidiDevice.Info[] infos = MidiSystem.getMidiDeviceInfo();
        for (MidiDevice.Info i : infos) {
            System.out.println(i.getName());

        }
    }
}