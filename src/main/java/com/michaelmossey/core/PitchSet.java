package com.michaelmossey.core;

import java.util.*;

public class PitchSet
{
    private List<Integer> pitches;

    public PitchSet(List<Integer> pitches)
    {
        this.pitches = pitches;
    }

    public List<Note> playMelody(double tBeg, double span, double dur)
    {
        List<Note> output = new ArrayList<>();
        double t = tBeg;
        for (int p: pitches)
        {
            Note n = new Note(t, t + dur, p);
            output.add(n);
            t += span;
        }
        return output;
    } 
}