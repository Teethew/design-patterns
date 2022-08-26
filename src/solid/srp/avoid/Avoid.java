package solid.srp.avoid;

import java.io.FileNotFoundException;
import java.io.PrintStream;
import java.net.URI;
import java.util.ArrayList;
import java.util.List;

public class Avoid {
    public static void main(String[] args) throws FileNotFoundException {

        PrintStream console = System.out;

        Journal journal = new Journal();
        journal.addEntry("Today I'm going to the mall.");
        journal.addEntry("I bought a bright new pair of shoes!");

        console.println(journal);

        // doesn't make sense
        journal.save("./journal.txt");
    }
}

class Journal {
    private final List<String> entries = new ArrayList<>();
    private static int count = 0;

    public void addEntry(String text) {
        entries.add(++count + ": " + text);
    }

    public void removeEntry(int index) {
        entries.remove(index);
    }

    @Override
    public String toString() {
        return String.join(System.lineSeparator(), entries);
    }

    // clear violation of SRP, persistence shouldn't be a concern of a Journal
    public void save(String filename) throws FileNotFoundException {
        try(PrintStream fileStream = new PrintStream(filename)) {
            fileStream.println(this);
        }
    }

    public void load(URI uri) {}
    public void load(String filename) {}
}
