package translator;

import com.example.igor.translator.data.WordEntry;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import javax.inject.Inject;

/**
 * Created by igor on 21.08.16.
 *
 */

public class TestDataFactory {
    public static String randomUuid() {
        return UUID.randomUUID().toString();
    }

    public static WordEntry makeWordEntry() {
        return WordEntry.create(1, "Тест", "Test", "Test", "сущ");
    }

    public static List<WordEntry> makeListRibots(int number) {
        List<WordEntry> wordEntries = new ArrayList<>();
        for (int i = 0; i < number; i++) {
            wordEntries.add(makeWordEntry());
        }
        return wordEntries;
    }
}
