package seedu.clialgo.command;

import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.PrintStream;

import seedu.clialgo.TopicManager;
import seedu.clialgo.Ui;
import seedu.clialgo.storage.FileManager;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertFalse;

/**
 * JUnit test for the <code>AddCommand</code> class methods.
 */
class AddCommandTest {

    /**
     * Checks the <code>equals</code> method of the <code>AddCommand</code> class.
     * Inputs two equal <code>AddCommand</code> objects and expects the method to return true.
     */
    @Test
    void equals_checkEqualAddCommand_expectTrue() {
        String actualName = "dummyName";
        String expectedName = "dummyName";
        String actualTopic = "dummyTopic";
        String expectedTopic = "dummyTopic";
        AddCommand actualAddCommand = new AddCommand(actualName, actualTopic);
        AddCommand expectedAddCommand = new AddCommand(expectedName, expectedTopic);
        assertTrue(actualAddCommand.equals(expectedAddCommand));
    }

    /**
     * Checks the <code>equals</code> method of the <code>AddCommand</code> class.
     * Inputs two unequal <code>AddCommand</code> objects and expects the method to return false.
     */
    @Test
    void equals_checkUnequalAddCommand_expectFalse() {
        String actualName = "actualName";
        String expectedName = "expectedName";
        String actualTopic = "actualTopic";
        String expectedTopic = "expectedTopic";
        AddCommand actualAddCommand = new AddCommand(actualName, actualTopic);
        AddCommand expectedAddCommand = new AddCommand(expectedName, expectedTopic);
        assertFalse(actualAddCommand.equals(expectedAddCommand));
    }

    /**
     * Checks the <code>execute</code> method of the <code>AddCommand</code> class.
     * Inputs proper inputs for <code>AddCommand</code> object and expects the method
     * to print message for successful adding of note.
     */
    @Test
    void execute_properInput_expectAddSuccessfulMessage() {
        String testDataPath = ".\\testdata";
        TopicManager topicManager = new TopicManager();
        Ui ui = new Ui();
        FileManager fileManager = new FileManager(testDataPath, topicManager.getTopicNames());
        fileManager.initialize();

        new TestModeCommand().execute(topicManager, ui, fileManager);

        ByteArrayOutputStream actualOutput = new ByteArrayOutputStream();
        System.setOut(new PrintStream(actualOutput));

        String actualNoteName = "queue";
        String actualNoteTopic = "LINKED_LIST";

        new AddCommand(actualNoteName, actualNoteTopic).execute(topicManager, ui, fileManager);

        String os = System.getProperty("os.name");
        String expectedOutput = "";

        if (os.contains("Windows")) {
            expectedOutput = "======================================================\r\n" +
                    "Successfully added queue into LINKED_LIST.\r\n" +
                    "======================================================\r\n";
        } else {
            expectedOutput = "======================================================\n" +
                    "Successfully added queue into LINKED_LIST.\n" +
                    "======================================================\n";
        }

        assertEquals(expectedOutput, actualOutput.toString());
        FileManager.deleteAll(new File(testDataPath));
    }

    /**
     * Checks the <code>execute</code> method of the <code>AddCommand</code> class.
     * Inputs invalid topic for <code>AddCommand</code> object and expects the method
     * to print message for unsuccessful adding of note due to invalid topic.
     */
    @Test
    void execute_invalidTopicInput_expectAddUnsuccessfulDueToInvalidTopicMessage() {
        String testDataPath = ".\\testdata";
        TopicManager topicManager = new TopicManager();
        Ui ui = new Ui();
        FileManager fileManager = new FileManager(testDataPath, topicManager.getTopicNames());
        fileManager.initialize();

        new TestModeCommand().execute(topicManager, ui, fileManager);

        ByteArrayOutputStream actualOutput = new ByteArrayOutputStream();
        System.setOut(new PrintStream(actualOutput));

        String actualNoteName = "queue";
        String actualNoteTopic = "invalidTopic";

        new AddCommand(actualNoteName, actualNoteTopic).execute(topicManager, ui, fileManager);

        String os = System.getProperty("os.name");
        String expectedOutput = "";

        if (os.contains("Windows")) {
            expectedOutput = "======================================================\r\n" +
                    "Unsuccessful! invalidTopic is not a topic in CS2040C.\r\n" +
                    "Type 'help c/add' for assistance.\r\n" +
                    "======================================================\r\n";
        } else {
            expectedOutput = "======================================================\n" +
                    "Unsuccessful! invalidTopic is not a topic in CS2040C.\n" +
                    "Type 'help c/add' for assistance.\n" +
                    "======================================================\n";
        }

        assertEquals(expectedOutput, actualOutput.toString());
        FileManager.deleteAll(new File(testDataPath));
    }

    /**
     * Checks the <code>execute</code> method of the <code>AddCommand</code> class.
     * Inputs repeated name for <code>AddCommand</code> object and expects the method
     * to print message for unsuccessful adding of note due to invalid command.
     */
    @Test
    void execute_repeatedInput_expectAddUnsuccessfulDueToInvalidCommandMessage() {
        String testDataPath = ".\\testdata";
        TopicManager topicManager = new TopicManager();
        Ui ui = new Ui();
        FileManager fileManager = new FileManager(testDataPath, topicManager.getTopicNames());
        fileManager.initialize();

        new TestModeCommand().execute(topicManager, ui, fileManager);

        ByteArrayOutputStream actualOutput = new ByteArrayOutputStream();
        System.setOut(new PrintStream(actualOutput));

        String actualNoteName = "queue";
        String actualNoteTopic = "LINKED_LIST";

        // Adding the note with same name twice into same topic
        new AddCommand(actualNoteName, actualNoteTopic).execute(topicManager, ui, fileManager);
        new AddCommand(actualNoteName, actualNoteTopic).execute(topicManager, ui, fileManager);

        String os = System.getProperty("os.name");
        String expectedOutput = "";

        if (os.contains("Windows")) {

            expectedOutput = "======================================================\r\n" +
                    "Successfully added queue into LINKED_LIST.\r\n" +
                    "======================================================\r\n" +
                    "======================================================\r\n" +
                    "Unsuccessful! A note with that name already exists.\r\n" +
                    "Type 'list' to view the list of notes.\r\n" +
                    "======================================================\r\n";
        } else {
            expectedOutput = "======================================================\n" +
                    "Successfully added queue into LINKED_LIST.\n" +
                    "======================================================\n" +
                    "======================================================\n" +
                    "Unsuccessful! A note with that name already exists.\n" +
                    "Type 'list' to view the list of notes.\n" +
                    "======================================================\n";
        }

        assertEquals(expectedOutput, actualOutput.toString());
        FileManager.deleteAll(new File(testDataPath));
    }
}
