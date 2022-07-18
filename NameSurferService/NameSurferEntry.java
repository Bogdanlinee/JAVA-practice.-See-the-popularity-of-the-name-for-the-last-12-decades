package NameSurferService;

/*
 * File: NameSurferEntry.java
 * --------------------------
 * This class represents a single entry in the database.  Each
 * NameSurferEntry contains a name and a list giving the popularity
 * of that name for each decade stretching back to 1900.
 */

import java.util.*;

public class NameSurferEntry implements NameSurferConstants {
    /* array which contains the rank of every decade(12 decades) */
    private final String[] ranksArray;

    /**
     * Creates a new NameSurferEntry from a data line as it appears
     * in the data file. Each line begins with the name, which is
     * followed by integers giving the rank of that name for each
     * decade.
     */
    public NameSurferEntry(String line) {
        /* add string to array split it using (" ") */
        ranksArray = line.split(" ");
    }

    /**
     * Returns the name associated with this entry.
     */
    public String getName() {
        return ranksArray[0];
    }

    /**
     * Returns the rank associated with an entry for a particular
     * decade.  The decade value is an integer indicating how many
     * decades have passed since the first year in the database,
     * which is given by the constant START_DECADE.  If a name does
     * not appear in a decade, the rank value is 0.
     */
    public int getRank(int decade) {
        ArrayList<Integer> arr = getArrayOfDecades();

        return arr.get(decade);
    }

    /**
     * Returns array which consist of ranks for a given name
     */
    public ArrayList<Integer> getArrayOfDecades() {
        ArrayList<Integer> arr = new ArrayList<>();

        for (int i = 1; i < ranksArray.length; i++) {
            arr.add(Integer.parseInt(ranksArray[i]));
        }

        return arr;
    }

    /**
     * Returns a string that makes it easy to see the value of a
     * NameSurferEntry.
     */
    public String toString() {
        return ranksArray[0] + " " + getArrayOfDecades().toString().replace(",", "");
    }
}