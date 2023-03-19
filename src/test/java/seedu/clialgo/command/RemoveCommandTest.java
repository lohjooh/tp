package seedu.clialgo.command;

import org.junit.jupiter.api.Test;
import seedu.clialgo.TopicManager;
import seedu.clialgo.Ui;
import seedu.clialgo.storage.FileManager;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.PrintStream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class RemoveCommandTest {
    @Test
    public void testRemoveCommandConstructor() {
        RemoveCommand myObj = new RemoveCommand("queue notes");
        assertEquals("queue notes", myObj.getName());
    }

    @Test
    void equals_checkEqualRemoveCommand_expectTrue() {
        String firstName = "dummyName";
        String secondName = "dummyName";
        RemoveCommand firstRemoveCommand = new RemoveCommand(firstName);
        RemoveCommand secondRemoveCommand = new RemoveCommand(secondName);
        assertTrue(firstRemoveCommand.equals(secondRemoveCommand));
    }

    @Test
    void equals_checkUnequalAddCommand_expectFalse() {
        String firstName = "dummyName1";
        String secondName = "dummyName2";
        RemoveCommand firstRemoveCommand = new RemoveCommand(firstName);
        RemoveCommand secondRemoveCommand = new RemoveCommand(secondName);
        assertFalse(firstRemoveCommand.equals(secondRemoveCommand));
    }

    @Test
    void execute_properInput_expectRemoveSuccessfulMessage() {
        String testDataPath = ".\\testdata";
        TopicManager topicManager = new TopicManager();
        Ui ui = new Ui();
        FileManager fileManager = new FileManager(testDataPath, topicManager.getTopicNames());

        new TestModeCommand().execute(topicManager, ui, fileManager);

        ByteArrayOutputStream actualOutput = new ByteArrayOutputStream();
        System.setOut(new PrintStream(actualOutput));

        fileManager.initialize();

        String dummyNoteName = "queue";
        String dummyNoteTopic = "LINKED_LIST";

        new AddCommand(dummyNoteName, dummyNoteTopic).execute(topicManager, ui, fileManager);

        String os = System.getProperty("os.name");
        String expectedOutput = "";

        actualOutput.reset();

        new RemoveCommand(dummyNoteName).execute(topicManager, ui, fileManager);

        if (os.contains("Windows")) {
            expectedOutput = "======================================================\r\n" +
                    "Successfully removed queue.\r\n" +
                    "======================================================\r\n";
        } else {
            expectedOutput = "======================================================\n" +
                    "Successfully removed queue.\n" +
                    "======================================================\n";
        }

        assertEquals(expectedOutput, actualOutput.toString());
        FileManager.deleteAll(new File(testDataPath));
    }

    @Test
    void execute_emptyTopicManagerInput_expectRemoveFailMessage() {
        ByteArrayOutputStream actualOutput = new ByteArrayOutputStream();
        System.setOut(new PrintStream(actualOutput));

        String testDataPath = ".\\testdata";
        TopicManager topicManager = new TopicManager();
        Ui ui = new Ui();
        FileManager fileManager = new FileManager(testDataPath, topicManager.getTopicNames());
        fileManager.initialize();

        actualOutput.reset();

        String os = System.getProperty("os.name");
        String expectedOutput = "";

        String noteName = "queue";

        new RemoveCommand(noteName).execute(topicManager, ui, fileManager);

        if (os.contains("Windows")) {
            // This expected output has "File Created" due to the first
            // initialisation of the FileManager in AddCommandTest.
            expectedOutput = "======================================================\r\n" +
                    "Unsuccessful!\r\n" +
                    "Type 'help c/remove' for assistance on how to remove a note.\r\n" +
                    "======================================================\r\n";
        } else {
            expectedOutput = "======================================================\n" +
                    "Unsuccessful!\n" +
                    "Type 'help c/remove' for assistance on how to remove a note.\n" +
                    "======================================================\n";
        }

        assertEquals(expectedOutput, actualOutput.toString());
        FileManager.deleteAll(new File(testDataPath));
    }
}
