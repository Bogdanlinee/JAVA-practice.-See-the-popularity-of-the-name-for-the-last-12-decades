package NameSurferService;

/*
 * File: NameSurferDataBase.java
 * -----------------------------
 * This class keeps track of the complete database of names.
 * The constructor reads in the database from a file, and
 * the only public method makes it possible to look up a
 * name and get back the corresponding NameSurferEntry.
 * Names are matched independent of case, so that "Eric"
 * and "ERIC" are the same names.
 */

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;

public class NameSurferDataBase implements NameSurferConstants {
    /* Database container */
    HashMap<String, NameSurferEntry> data = new HashMap<>();

    /**
     * Creates a new NameSurferDataBase and initializes it using the
     * data in the specified file.  The constructor throws an error
     * exception if the requested file does not exist or if an error
     * occurs as the file is being read.
     */
    public NameSurferDataBase(String filename) {
        /* read database file */
        try {
            BufferedReader reader = new BufferedReader(new FileReader(filename));

            /* add each string of database to database container */
            while (true) {
                String line = reader.readLine();

                if (line == null)
                    break;

                NameSurferEntry entry = new NameSurferEntry(line);

                data.put(entry.getName().toLowerCase(), entry);
            }

            reader.close();

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Returns the NameSurferEntry associated with this name, if one
     * exists.  If the name does not appear in the database, this
     * method returns null.
     */
    public NameSurferEntry findEntry(String name) {

        if (data.containsKey(name.toLowerCase()))
            return data.get(name.toLowerCase());

        return null;
    }
}