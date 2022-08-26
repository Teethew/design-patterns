package solid.srp.encourage;

import java.io.File;
import java.io.IOException;
import java.io.PrintStream;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

public class Encourage {
    public static void main(String[] args) throws IOException {
        PrintStream console = System.out;

        Journal journal = new Journal();
        journal.addEntry("Today I'm going to the mall.");
        journal.addEntry("I bought a bright new pair of shoes!");

        console.println(journal);

        Persistence.saveToFile(journal, "journal.txt");

        console.println(Persistence.load("journal.txt"));
    }
}

class Persistence {
    public static void saveToFile(Journal journal, String filename) throws IOException {
        if (new File(filename).createNewFile()) {
            try (PrintStream fileStream = new PrintStream(filename)) {
                fileStream.println(journal);
            }
        }
    }

    public static Journal load(String filename) throws IOException {
        Journal journal = new Journal();

        try (Stream<String> lines = Files.lines(Paths.get(filename), StandardCharsets.UTF_8)) {
            lines.map(line -> line.split(": ")[1])
                    .forEach(journal::addEntry);
        }

        return journal;
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
}
