/*
 *CS 2852 - 011
 *Fall 2017
 *Lab 3 - Connect the Dots Generator Revisited
 *Name: Donal Moloney
 *Created: 9/20/2017
 */
package Moloneyda;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.canvas.Canvas;
import javafx.scene.control.*;
import javafx.scene.paint.Color;
import javafx.stage.FileChooser;
import java.io.File;
import java.io.IOException;
import java.util.*;
import java.util.logging.FileHandler;
import java.util.logging.Level;
import java.util.logging.Logger;

/*
*This class handles the action events of DotToDot.fxml
*/
public class DToDController {
    private File file;
    private FileChooser fileChooser = new FileChooser();
    private Picture original;
    private Picture newPicture;
    private final Logger LOGGER = Logger.getLogger(Picture.class.getName());
    private Alert alert = new Alert(Alert.AlertType.INFORMATION);
    private List<Dot> userChoice = null;
    private String collectionDisplay;
    private String indexOrIteratorDisplay;
    private String indexOrIteratorChoice;
    private long nanoTime = 0;
    private static final double NANO_TO_SECONDS_CONVERT = 1000000000;
    @FXML
    Canvas canvas;
    @FXML
    MenuItem menuItemDotsOnly;
    @FXML
    MenuItem menuItemLinesOnly;
    @FXML
    MenuItem menuItemRemoveDots;
    @FXML
    MenuItem menuItemSave;


    /*
    * This is the classes's constructor method
    * It creates a file handler object, sets the file handler level to sever, and adds the file handler to the logger
    * @throws IO Exception - can occur when declaring a new File Handler
    */
    public DToDController() {
        try {
            FileHandler fileHandler = new FileHandler("FileHandler");
            fileHandler.setLevel(Level.SEVERE);
            LOGGER.addHandler(fileHandler);
        } catch (IOException e) {
            LOGGER.log(Level.SEVERE, "I/O Exception occurred while instantiating fileHandler");
            alert.setTitle("Error Dialog");
            alert.setHeaderText("An error has occurred!");
            alert.setContentText("An error occurred instantiating the fileHandler!");
            alert.showAndWait();
        }
    }

    /*
     * This method allows the user to choose a file to open, then draws it by calling Picture's methods
     * This method can throw an IO exception if no file is entered
     * @param actionEvent - a mouse click
     * @throws NumberFormatException - file chosen is in the incorrect format
     * @throws InputMismatchException - file chosen is in an invalid format
     * @throws IOException - when an error occurs passing the file data to the scanner
     */
    public void openFile(ActionEvent actionEvent) throws IOException {
        canvas.getGraphicsContext2D().clearRect(0, 0, canvas.getWidth(), canvas.getHeight());
        file = fileChooser.showOpenDialog(null);
        try {
            if (file != null) {
                original = new Picture(new ArrayList<Dot>());
                fileChooser.setTitle("Select Your Dot File");
                original.load(file);
                canvas.getGraphicsContext2D().setFill(Color.SADDLEBROWN);
                canvas.getGraphicsContext2D().setStroke(Color.CHOCOLATE);
                original.drawDots(canvas);
                original.drawLines(canvas);
                original.getNumberOfDots();
                menuItemDotsOnly.setDisable(false);
                menuItemLinesOnly.setDisable(false);
                menuItemRemoveDots.setDisable(false);
                menuItemSave.setDisable(false);
            }
        } catch (NumberFormatException e) {
            LOGGER.log(Level.SEVERE, "The file trying to be loaded has numbers not in the correct format");
            alert.setTitle("Error Dialog");
            alert.setHeaderText("An error has occurred!");
            alert.setContentText("The file trying to be loaded has numbers in the incorrect format!");
            alert.showAndWait();
        } catch (InputMismatchException e) {
            LOGGER.log(Level.SEVERE, "The file trying to be loaded is an invalid format");
            alert.setTitle("Error Dialog");
            alert.setHeaderText("An error has occurred!");
            alert.setContentText("The file trying to be loaded is an invalid format!");
            alert.showAndWait();
        } catch (IOException e) {
            LOGGER.log(Level.SEVERE, "An error occurred passing the file data to a scanner");
            alert.setTitle("Error Dialog");
            alert.setHeaderText("An error has occurred!");
            alert.setContentText("An error occurred passing the file data to a scanner!");
            alert.showAndWait();
        }
    }

    /*
    * This method allows you to safely end the program
    * @param actionEvent - a mouse click
    * @return void - this method does not return anything
    */
    @FXML
    public void closeFile(ActionEvent actionEvent) {
        Platform.exit();
    }

    /*
    * This method draws the lines only of the file by calling the picture class's method draw lines
    * @param actionEvent - a mouse click
     */
    @FXML
    public void drawLinesOnly(ActionEvent actionEvent) {
        canvas.getGraphicsContext2D().setFill(Color.AQUA);
        canvas.getGraphicsContext2D().clearRect(0, 0, canvas.getWidth(), canvas.getHeight());
        original.drawLines(canvas);

    }

    /*
    *This method draws the dots only of the file by calling the picture class's method draw dots
    * @param action event- a mouse click
     */
    @FXML
    public void drawDotsOnly(ActionEvent actionEvent) {
        canvas.getGraphicsContext2D().setFill(Color.AQUA);
        canvas.getGraphicsContext2D().clearRect(0, 0, canvas.getWidth(), canvas.getHeight());
        original.drawDots(canvas);
    }

    /*
    * This method saves the dots image to a file
    * @param actionEvent - a mouse click
    * @throws IOException when an error has occurred saving the image to the new file
    */
    public void save(ActionEvent actionEvent) {
        try {
            fileChooser.setTitle("Save file");
            file = fileChooser.showSaveDialog(null);
            newPicture.save(new File(file.getPath()));
            alert.setTitle("Information Dialog");
            alert.setHeaderText("Success");
            alert.setContentText("You have successfully saved to a file");
            alert.showAndWait();
        } catch (IOException e) {
            LOGGER.log(Level.SEVERE, "An error occurred in saving the file");
            alert.setTitle("Exception Dialog");
            alert.setHeaderText("Saving Error");
            alert.setContentText("An error with the file occurred while trying to save the dots image");
            alert.showAndWait();
        }
    }

    /*
    * This method removes dots from the dots image
    * @param actionEvent - a mouse click
    * @throws NumberFormatException- when the user input is not an integer
    */
    public void removeDots(ActionEvent actionEvent) {
        try {
            userChoice = selectCollection(userChoice);
            indexOrIteratorChoice = indexOrIterator();
            newPicture = new Picture(original, userChoice);
            TextInputDialog dialog = new TextInputDialog("(enter int)");
            dialog.setTitle("User Input Dialog");
            dialog.setHeaderText("Enter the number of dots to keep");
            dialog.setContentText("Please enter input as an int:");
            Optional<String> userInput = dialog.showAndWait();
            String stringRemove = userInput.get();
            int numberDesired;
            if (stringRemove.contains(" ")) {
                int indexOfSpace = stringRemove.indexOf(" ");
                String removeSpace = stringRemove.substring(indexOfSpace + 1);
                numberDesired = Integer.parseInt(removeSpace);
                if (indexOrIteratorChoice.equals("indexOf")) {
                   nanoTime = newPicture.removeDots(numberDesired);
                } else {
                    nanoTime = newPicture.removeDots2(numberDesired);
                }
            } else {
                numberDesired = Integer.parseInt(stringRemove);
                if (indexOrIteratorChoice.equals("indexOf")) {
                    nanoTime = newPicture.removeDots(numberDesired);
                } else {
                    nanoTime = newPicture.removeDots2(numberDesired);
                }
            }
            canvas.getGraphicsContext2D().setStroke(Color.RED);
            canvas.getGraphicsContext2D().clearRect(0, 0, canvas.getWidth(), canvas.getHeight());
            newPicture.drawDots(canvas);
            newPicture.drawLines(canvas);
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Information");
            alert.setHeaderText("Time using selected collection and indexer");
            alert.setContentText(collectionDisplay + indexOrIteratorDisplay + ": " + formatNanoTime(nanoTime));
            alert.showAndWait();
        } catch (NumberFormatException e) {
            LOGGER.log(Level.SEVERE, "The input entered for the dots to keep was not an integer character");
            alert.setTitle("Exception Dialog");
            alert.setHeaderText("Invalid input!!!");
            alert.setContentText("Your input is not an integer");
            alert.showAndWait();
        }
    }

    /*
    *This method creates the dots collection depending on the user choice
    *@param userChoice the declared collection
    * @return
    */
    public List<Dot> selectCollection(List userChoice) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Choose Collection type");
        alert.setHeaderText("Choose to use either an array list or linked list");
        alert.setContentText("Choose your option.");
        ButtonType buttonArrayList = new ButtonType("ArrayList");
        ButtonType buttonLinkedList = new ButtonType("LinkedList");
        ButtonType buttonCancel = new ButtonType("Cancel", ButtonBar.ButtonData.CANCEL_CLOSE);
        alert.getButtonTypes().setAll(buttonArrayList, buttonLinkedList, buttonCancel);
        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == buttonArrayList) {
            userChoice = new ArrayList();
            collectionDisplay = " ArrayList ";
        } else if (result.get() == buttonLinkedList) {
            userChoice = new LinkedList();
            collectionDisplay = " Linked List ";
        } else if (result.get() == buttonCancel) {
            throw new NullPointerException("A list type must be chose to remove dots");
        }
        return userChoice;
    }

    /*
    * This method returns user choice for how to remove the dots from the list
    * @ returns the user choice index or iterator for removing the dots
     */
    private String indexOrIterator() {
        String choice = null;
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Choose algorithm type");
        alert.setHeaderText("Choose to use either an iterator or index of");
        alert.setContentText("Choose your option.");
        ButtonType buttonIterator = new ButtonType("iterator");
        ButtonType buttonindexOf = new ButtonType("indexOf");
        ButtonType buttonCancel = new ButtonType("Cancel", ButtonBar.ButtonData.CANCEL_CLOSE);
        alert.getButtonTypes().setAll(buttonIterator, buttonindexOf, buttonCancel);
        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == buttonIterator) {
            choice = "iterator";
            indexOrIteratorDisplay = " iterator ";
        } else if (result.get() == buttonindexOf) {
            choice = "indexOf";
            indexOrIteratorChoice = " indexOf ";
        } else if (result.get() == buttonCancel) {
            throw new NullPointerException("A list type must be chose to remove dots");
        }
        return choice;
    }

    /*
    *This method formats the time in nanoseconds for the display
    * @ param nanoTime - the time it took to remove the dots due to user choice of collection and iterator/index
    * @returns formatted time of the length it took to remove the dots given user choice of string and iterator/index
    */
    private String formatNanoTime(long nanoTime){
        Double timeInSeconds = nanoTime / NANO_TO_SECONDS_CONVERT;
        int hours = ((int)(timeInSeconds/360));
        int minutes = ((int)(timeInSeconds%360)*60);
        int seconds = ((int)(timeInSeconds%360)*360);
        double decimal = ((timeInSeconds/360)*360)%1;
        String formatedTime = String.format("%02d:%02d:%02d%.4f",hours,minutes,seconds, decimal);
        return formatedTime;
    }
}
