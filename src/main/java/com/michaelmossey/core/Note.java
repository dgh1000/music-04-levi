package com.michaelmossey.core;

import javax.sound.midi.*;

public class Note implements Comparable<Note> {
    private double tOn;
    private double tOff;
    private int pitch;

    public Note(double tOn, double tOff, int pitch) {
        this.tOn = tOn;
        this.tOff = tOff;
        this.pitch = pitch;
        // hasBeenEliminated = false;
    }

    public double getTOn() {
        return tOn;
    }

    public double getTOff() {
        return tOff;
    }

    public int getPitch() {
        return pitch;
    }

    public int[] onOffTimestamps(int offset) {
        int[] out = new int[2];
        out[0] = (int)(tOn * 1000 + offset);
        out[1] = (int)(tOff * 1000 + offset);
        return out;
    }

    @Override
    public int compareTo(Note other) {
        return Double.compare(this.tOn, other.tOn);
    }

    public Raw[] toRaw(int offset) {
        int idx = 0;
        Raw[] msgs = new Raw[2];

        try {
            int[] ts = onOffTimestamps(offset);
            ShortMessage m1 = new ShortMessage();
            m1.setMessage(0x90, 1, this.pitch, 64);
            Raw n1 = new Raw(m1, ts[0]);
            ShortMessage m2 = new ShortMessage();
            m2.setMessage(0x80, 1, this.pitch, 64);
            Raw n2 = new Raw(m2, ts[1]);
            // make note on and note off
            msgs[idx++] = n1;
            msgs[idx++] = n2;

        } catch (InvalidMidiDataException e) {
            System.out.println("Strange, InvalidMidiException");
        }

        return msgs;
    }

    @Override
    public String toString() {
        return String.format("on: %S off: %S pit:%d", String.valueOf(tOn), Double.valueOf(tOff), pitch);
    }

}
