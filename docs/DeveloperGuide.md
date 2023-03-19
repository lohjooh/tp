# Developer Guide

## Acknowledgements

{list here sources of all reused/adapted ideas, code, documentation, and third-party libraries -- include links to the original source as well}

## Design & implementation

### FileManager (Storage) component
API : `FileManager.java`

The file manager can
- Save the added `Notes` in a `.txt` file and read them 
back into the corresponding objects
- All the `Notes` data are stored according to the `Topic` that the 
`Notes` are tagged to
- `Notes` are encoded by a `FileEncoder` object before being written to
the corresponding `Topic` `.txt` file
- `Notes` read from the `Topic` `.txt` are decoded by a `FileDecoder`
object before being converted back into a `Note` object
  - The `Note` is passed to the corresponding `TopicManager` object

## Product scope
### Target user profile

{Describe the target user profile}

### Value proposition

{Describe the value proposition: what problem does it solve?}

## User Stories

|Version| As a ... | I want to ... | So that I can ...|
|--------|----------|---------------|------------------|
|v1.0|new user|see usage instructions|refer to them when I forget how to use the application|
|v2.0|user|find a to-do item by name|locate a to-do without having to go through the entire list|

## Non-Functional Requirements

{Give non-functional requirements}

## Glossary

* *glossary item* - Definition

## Instructions for manual testing

Below are guidelines for testers to test the application

### Initialisation
1. Download the jar file and copy into an empty folder
2. Right click in the folder where the jar file is located and open the 
command-line interface. 
_**Example:**_ `` Open in Terminal``
3. Type: ``java -jar .\[NAME OF JAR FILE]`` where ``[NAME OF JAR FILE]``
is the file name of the jar file.
4. The appliction would then open in the command-line interface.
5. Note that if the application has initialised correctly, there would be a 
`data` folder created with some `.txt` files in the same directory as the 
jar file.

#### _Optional : Test Mode_
1. If the tester does not want their data that they entered to be saved, they 
can type the command: `start-test-mode` to start a debugging mode 
after opening the application.
    1. If done correctly this is what the tester will see:
    ```
    ======================================================
     Starting test mode.
    ======================================================
    ```
   2. There would be a `testdata` folder created with some `.txt` files
   in the same directory as the jar file.
   3. All the below testing will then reflect in the `.txt` files in 
   the `testdata` folder.
      1. If manually editing the data file, it has to be done in the data 
      files within `testdata`.
2. To exit test mode, type the command `exit-test-mode`.
   1. If done correctly this is what the tester will see:
    ```
   ======================================================
    Ending test mode.
   ======================================================
   ======================================================
    Successfully deleted file.
   ======================================================
   ======================================================
    ```
3. The `testdata` folder should now be deleted.   

> **WARNING**
> 
> The user _has_ to exit test mode or the test mode data would not be 
> wiped correctly.

### Shutdown
1. After all the testing is done, type in the command: `exit`.
   1. If done correctly this is what the tester will see:
    ```
   ======================================================
   Thank you for using CLIAlgo! Study hard!
   ======================================================
    ```
   2. The application would then close in the command-line interface.

### Adding a `Note`
1. Type the command: `add n/[NOTE NAME] t/[TOPIC NAME]`.
   1. `[NOTE NAME]` would represent the name of the note.
      1. The note file is in the form `[NOTE NAME].txt`.
   2. `[TOPIC NAME]` would represent the `Topic` the note is tagged to.
      1. **CASE 1 :** The `[TOPIC NAME]` is valid.
      > Example : 
      > 
      > add n/note name t/LINKED_LIST
      2. **CASE 2 :** The `[TOPIC NAME]` is invalid.
      > Example :
      >
      > add n/note name t/linkedlist
      > 
      > add n/note name t/SOMETHING
2. Leaving any fields blank would cause an error message to be printed.
> Example :
>
> add n/ t/
> 
> add n/note t/
3. Leaving out `n/` or `t/` would cause an error message to be printed.
> Example :
>
> add note LINKED_LIST

### Listing all `Notes`
1. Type the command: `list`.
   1. **CASE 1 :** There are some `Notes` stored.
      1. The application would print out all the `Notes` stored.
   2. **CASE 2 :** There are no `Notes` stored.
      1. The application would print out a message indicating that no 
      notes have been stored.

### Deleting a `Note`
1. Type the command: `remove n/[NOTE NAME]`.
   1. `[NOTE NAME]` would represent the name of the note.
   2. **CASE 1 :** The `Note` with `[NOTE NAME]` exists.
      1. The `Note` is deleted successfully and a message would be printed.
   3. **CASE 2 :** The `Note` with `[NOTE NAME]` does not exist.
      1. The application would print out an error message indicating 
      that the note does not exist.
2. Leaving any fields blank would cause an error message to be printed. 
> Example :
>
> remove n/
3. Leaving out `n/` or `t/` would cause an error message to be printed.
> Example :
>
> remove note

### Filtering `Notes`
1. Type the command: `filter k/[KEYWORDS] t/[TOPIC NAME]`.
   1. `[KEYWORD]` would be `topic` representing filtering by `Topic`
   2. `[TOPIC NAME]` would represent the `Topic` the note is tagged to.
      1. **CASE 1 :** The `[TOPIC NAME]` is valid.
      > Example :
      >
      > filter k/topic t/LINKED_LIST
      2. **CASE 2 :** The `[TOPIC NAME]` is invalid.
      > Example :
      >
      > filter k/topic t/linkedlist
      >
      > filter k/topic t/SOMETHING
2. Leaving `k/` blank would cause an error message to be printed.
> Example :
>
> filter k/
3. Leaving out `t/` is valid
> Example :
>
> filter k/topic

### Saving data
1. Notes are represented as : 
`[NOTE NAME]&@[PATH TO NOTE]&@[TOPIC NAME]`
2. The application checks for invalid `[TOPIC NAME]` only
3. The application checks that there are three fields separated by `&@`
4. Corrupted lines of files are ignored by the application and removed 
subsequently
> Example:
> 
> Initial .txt file:
> 
> TEST1&@test1.txt&@wrr
> 
> TEST2&@test2.txt&@SORTING
> 
> After running application:
> 
> TEST2&@test2.txt&@SORTING

> Example:
>
> Initial .txt file:
>
> TEST1&@
>
> TEST2test2.txtSORTING
>
> After running application:
5. If a file is deleted in the middle of the application running somehow the 
application would recreate the file in its last state when running `add` or 
`remove` 