# CS-2852_Lab3 from Winter 2017

Overview

This week you will update your solution to the previous assignment based on feedback from your instructor and enhance its functionality. The key enhancements will allow you to compare the efficiency of using iterators versus indicies when accessing elements in ArrayLists and LinkedLists.
Assignment

In addition to providing all of the functionality required in lab 2, you must add the following:

    Incorporate feedback from your instructor from labs 1 and 2
    Allow the user to select either an ArrayList or a LinkedList when removing dots
    Allow the user to select a dot removal algorithm that uses one of the following two methods for traversing the data structure:
        Index only methods (no iterators)
        Iterator only methods (no indexing operations)
    Display the amount of wall-clock time required to determine the numberDesired most critical dots in a picture using any of the methods described above

If your program is not able to produce a result similar to the balloon100.dot file when applying 100 as the number of desired dots to the balloon.dot picture, it is not ready to be submitted. You may wish to ask your instructor for help if you get stuck.
Details
Refactoring Lab 2 removeDots() Method

In addition to making corrections based on feedback from your instructor you must refactor your method so that it returns a long that represents the number of nanoseconds required for the algorithm to run.

You may find System.nanoTime() for this.
Using Iterator Access for Dot Picking Algorithm

In addition, you must create another version of the above method, called removeDots2() that produces the same result but uses one or more iterators to traverse the list of dots. This method must not use any index based methods to access elements of the list.

Hint: one way to ensure that you are not using any index based methods would be to use a Collection<Dot> reference to point to your list of dots. Since the Collection interface does not have any index based methods, the compiler will give you an error if you try to use any index based methods.
Benchmarking

Your user interface must provide a way to load a picture and then remove dots using any of these four options:

    Calls removeDots() using a Picture object that stores the dots in an ArrayList
    Calls removeDots() using a Picture object that stores the dots in an LinkedList
    Calls removeDots2() using a Picture object that stores the dots in an ArrayList
    Calls removeDots2() using a Picture object that stores the dots in an LinkedList

The benchmarking results may be displayed in the program UI and/or written to a file. The times should be displayed in hh:mm:ss.sss format. Hint: consider using format(), see here, or DecimalFormat, see here.

For example, your results of running your benchmarks on the magician.dot file with fifty desired points might look something like this (don’t stress if your numbers are different):

    Indexed ArrayList:   00:00:13.129
    Indexed LinkedList:  00:09:51.512
    Iterated ArrayList:  00:00:13.054
    Iterated LinkedList: 00:00:17.147

Results and Discussion

Your submission must include:

    Benchmarking results for:
        balloon.dot with 100

desired dots
balloon.dot with 1000
desired dots
skull.dot with 9000

    desired dots

Asymptotic time analysis for four of the following eight situations (do the first four if your last name begins with a letter in the first half of the alphabet, otherwise do the last four), assuming n
is the number of points in the original list. Your justification can be written sentences explaining the basis for your answer and/or equations expressing T(n)

    If your last name begins with a letter in the first half of the alphabet do these:
        removeDots() when using an ArrayList with n−1

desired dots.
removeDots() when using an ArrayList with 3
desired dots.
removeDots() when using a LinkedList with n−1
desired dots.
removeDots() when using a LinkedList with 3

    desired dots.

If your last name begins with a letter in the last half of the alphabet do these:

    removeDots2() when using an ArrayList with n−1

desired dots.
removeDots2() when using an ArrayList with 3
desired dots.
removeDots2() when using a LinkedList with n−1
desired dots.
removeDots2() when using a LinkedList with 3

        desired dots.

Your analysis must include a discussion justifying the O(?)

    answer for each scenerio.

Just For Fun

Ambitious students may wish to:

    Just for Fun suggestions from labs 1 and 2
    Benchmark lecture/homework implementations of the ArrayList and LinkedList
    Create a print to PDF option.
    Overlay the reduced-dot picture on top of the picture with all the dots in the file. You may wish to display the original picture in a lighter color.
    Create an interactive game where the user can draw lines between the dots, but only if they select the dots in the right order.
    Create a custom binary file format to store pictures.

Lab Deliverables

    See your professor’s instructions for details on submission guidelines and due dates.

        Dr. Taylor’s students: See below
        All other students should refer to Blackboard

    If you have any questions, consult your instructor.

Acknowledgment

This laboratory assignment was developed by Dr. Chris Taylor.
