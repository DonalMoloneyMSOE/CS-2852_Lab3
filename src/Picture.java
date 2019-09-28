/*
 *CS 2852 - 011
 *Fall 2017
 *Lab 3 - Connect the Dots Generator Revisited
 *Name: Donal Moloney
 *Created: 9/20/2017
 */
package Moloneyda;

import javafx.scene.canvas.Canvas;
import javafx.scene.control.Alert;
import java.io.*;
import java.util.*;

/*
*This class has methods that draw manipulate the .dot file
*/
public class Picture {
    private List<Dot> emptyList;
    private static final int ENLARGEMENT_FACTOR = 600;
    private static final int DOT_OFF_SET_FACTOR = 5;


    /*
    *This is the default constructor for the picture class
    */
    public Picture() {
    }

    /*
    *This one of the constructors for the picture class it initializes the type of data collection emptyList is
    * @param emptyList - the type of data collection emptyList is
    */
    public Picture(List<Dot> emptyList) {
        this.emptyList = emptyList;
    }

    /*
    *This one of the constructors for the picture class it initializes the type of data collection emptyList is
    * @param original - instance of the picture with the original dots picture in it
    * @param emptyList - a clone of the emptyList in original
    */
    public Picture(Picture original, List<Dot> emptyList) {
        this.emptyList = emptyList;
        for (int i = 0; i < original.emptyList.size(); i++) {
            this.emptyList.add(i, original.emptyList.get(i));
        }
    }

    /*
    *This method returns the index of the lowest critical value in the array of critical values
    *@param file - the file dots image will be written to
    *@ returns void - this method does not return anything
    */
    public void save(File file) throws IOException {
        try {
            File newFile = file;
            FileWriter writer = new FileWriter(newFile);
            for (int i = 0; i < emptyList.size(); i++) {
                String horizontal = Double.toString(emptyList.get(i).getHorizontalPosition());
                String vertical = Double.toString(emptyList.get(i).getVerticalPoisttion());
                writer.write(horizontal);
                writer.write(",");
                writer.write(" ");
                writer.write(vertical);
                writer.write("\n");
            }
            writer.close();
        } catch (IOException e) {
            throw new IOException();
        }
    }


    /*
    *This method loads a .dot file
    *@param file - the file the user chose to load
    *@throws IOException - if an error occurs setting the file's data into the scanner
    *@ returns void - this method does not return anything
    */
    public void load(File file) throws IOException {
        Scanner fileData = new Scanner(new FileReader(file));
        String aLine;
        while (fileData.hasNextLine()) {
            aLine = fileData.nextLine();
            String[] parts = aLine.split(",");
            String xString = parts[0];
            String yString = parts[1];
            double xCordinate = Double.parseDouble(xString);
            double yCoordinate = Double.parseDouble(yString);
            Dot newDots = new Dot(xCordinate, yCoordinate);
            emptyList.add(newDots);
        }
        fileData.close();
    }

    /*
    *This method draws the dots of the image
    * @param canvas - where the dots are drawn upon
    * @returns void - this method does not return anything
    */
    public void drawDots(Canvas canvas) {
        for (int i = 0; i < emptyList.size(); i++) {
            canvas.getGraphicsContext2D().fillOval(emptyList.get(i).getHorizontalPosition() * ENLARGEMENT_FACTOR -
                    DOT_OFF_SET_FACTOR, (1 - emptyList.get(i).getVerticalPoisttion()) *
                    ENLARGEMENT_FACTOR - DOT_OFF_SET_FACTOR, 10, 10);
        }
    }

    /*
    * This method draws the lines of lines of the image
    * @param canvas - where the dots are drawn upon
    * @returns void - this method does not return anything
    */
    public void drawLines(Canvas canvas) {
        double beginXLocation = emptyList.get(0).getHorizontalPosition() * ENLARGEMENT_FACTOR;
        double beginYLocation = (1 - emptyList.get(0).getVerticalPoisttion()) * ENLARGEMENT_FACTOR;
        canvas.getGraphicsContext2D().setLineWidth(2.0);
        canvas.getGraphicsContext2D().beginPath();
        canvas.getGraphicsContext2D().moveTo(beginXLocation, beginYLocation);
        for (int i = 1; i < emptyList.size(); i++) {
            double xPos = emptyList.get(i).getHorizontalPosition() * ENLARGEMENT_FACTOR;
            double yPos = (1 - emptyList.get(i).getVerticalPoisttion()) * ENLARGEMENT_FACTOR;
            canvas.getGraphicsContext2D().lineTo(xPos, yPos);
        }
        canvas.getGraphicsContext2D().lineTo(beginXLocation, beginYLocation);
        canvas.getGraphicsContext2D().closePath();
        canvas.getGraphicsContext2D().stroke();
    }

    /*
    *This method alerts the user to the number of dots in the uploaded file
    *@ returns void - this method does not return anything
    */
    public void getNumberOfDots() {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Information Dialog");
        alert.setHeaderText("File's Total Dots");
        alert.setContentText("The file has " + emptyList.size() + " dots in it");
        alert.showAndWait();
    }

    /*
    *This method returns the index of the lowest critical value in the array of critical values
    * @param cV - the array of critical values
    *@ returns int - the index of the lowest critical value
    */
    private int getLowestArrayListCv(ArrayList<Double> cV) {
        int lowestValueIndex = 0;
        for (int k = 0; k < cV.size(); k++) {
            if (cV.get(lowestValueIndex) > cV.get(k)) {
                lowestValueIndex = k;
            }
        }
        return lowestValueIndex;
    }

    /*
    *This method removes dots from the picture depending on its critical value and that the user specified using index
    * @param numberDesired - the number of dots the user wants to keep in the picture
    *@ returns long - the time in nanoseconds it took to remove all the dots in nano seconds
    */
    public long removeDots(int numberDesired) {
        int previous;
        int next;
        long nanoTime = 0;
        long nanoStart = System.nanoTime();
        long nanoEnd;
        if (emptyList.size() > numberDesired) {
            ArrayList<Double> cV = new ArrayList<Double>();
            for (int i = 0; i < emptyList.size(); i++) {
                previous = i - 1;
                next = i + 1;
                if (previous < 0) {
                    previous = emptyList.size() - 1;
                } else if (next >= emptyList.size()) {
                    next = 0;
                }
                cV.add(emptyList.get(i).calculateCriticalValue(emptyList.get(previous), emptyList.get(next)));
            }
            int dotsRemove = emptyList.size() - numberDesired;
            for (int j = 0; j < dotsRemove; j++) {
                int lowestValue = getLowestArrayListCv(cV);

                cV.remove(lowestValue);
                emptyList.remove(lowestValue);
            }
            nanoEnd = System.nanoTime();
            nanoTime = nanoEnd - nanoStart;
        } else if (numberDesired < 3) {
            throw new IllegalArgumentException();
        } else {
        }
        return nanoTime;
    }

    /*
    *This method removes dots from the picture depending on its critical value and the user specified using an iterator
    *@param numberDesired - the number of dots the user wants to keep in the picture
    *@ returns long - the time in nanoseconds it took to remove all the dots in nanoseconds
    */
    public long removeDots2(int numberDesired) {
        long nanoStart = System.nanoTime();
        Iterator<Dot> dotsCollection;
        Dot first;
        Dot previous;
        Dot current;
        Dot next;
        double val;
        long timeNano = 0;
        int count = 0;
        int totalDots = emptyList.size();
        int dotsToRemove = totalDots - numberDesired;
        if (emptyList.size() > numberDesired) {
            while (count < dotsToRemove) {
                Dot lowest = null;
                double lowestCriticalV = 1000;
                dotsCollection = emptyList.iterator();
                first = dotsCollection.next();
                previous = first;
                current = dotsCollection.next();
                next = dotsCollection.next();
                while (dotsCollection.hasNext()) {
                    val = current.calculateCriticalValue(previous, next);
                    if (val < lowestCriticalV) {
                        lowest = current;
                        lowestCriticalV = val;
                    }
                    previous = current;
                    current = next;
                    next = dotsCollection.next();
                }
                emptyList.remove(lowest);
                count++;
            }
            long nanoEnd = System.nanoTime();
            timeNano = nanoEnd - nanoStart;
        } else {
        }
        return timeNano;
    }
}

