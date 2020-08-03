import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.io.*;
import java.util.*;
import java.time.*;

/**
 *
 * Titile: NetAnalyser.java
 * Description: This class displays the GUI and runs the functions of execute the fundamental network diagnostic
 * tool ping and display its raw output together with a histogram of Round Trip Time (RTT) values.
 * Copyright: Copyright (c) 2020
 * @author Jiayi Zhang
 * @version 1.0
 *
 */

public class NetAnalyser extends JFrame implements ActionListener {
    
    JButton processJButton;
    JTextField textJTextField;
    JComboBox<String> probesJComboBox;
    JLabel output1JLabel;
    JLabel histogram1JLabel, histogram2JLabel, histogram3JLabel;
    JLabel histogramStar1JLabel, histogramStar2JLabel, histogramStar3JLabel;
    JTextArea outputJTextArea;
    JFrame myFrame;
    
    /**
     *
     * This is main method. Can acquire the maximum probes users want to set between 10 to 20 inclusive.
     * Also execute the initialization() method to realize the remain functionality.
     *
     */
    
    /**
     *
     * This is main method. Can acquire the maximum probes users want to set between 10 to 20 inclusive.
     * Also execute the initialization() method to realize the remain functionality.
     * @param args[] String of user enter in terminal.
     */

    public static void main(String[] args) {
        
        /**
         *
         * This is main method. Can acquire the maximum probes users want to set between 10 to 20 inclusive.
         * Also execute the initialization() method to realize the remain functionality.
         *
         * @param probesNo int number of number of probes user provides.
         * @param det int number to determine if the enter is legal.
         * @param len int value of args' length.
         *
         */
        
        int probesNo = 0;
        int det = 0;
        int len = args.length;
        
        try {
            probesNo = Integer.parseInt(args[0]); //Force to convert to integer.
           }
           catch (Exception e) {
               System.out.println("Your enter is not a integer.");
               det = 1;
           }
        
        if (len != 1 || det == 1) {
            System.out.println("Exit since illegal entered value.");
            System.exit(1);
        }
        else if (probesNo >= 10 && probesNo <= 20) {
            NetAnalyser myGUI = new NetAnalyser();
            myGUI.initialization(probesNo); //Calling method.
        }
        else {
            System.out.println("Exit since illegal entered value.");
            System.exit(1);
        }
    }
    
    
    public void initialization(int probesNo) {
        
        
        /**
         *
         * This method is to create the initial GUI.
         * @param probesNo int value of the users' valid probes selection.
         *
        */
        
        
        /* The part of initial GUI codes */
        
        myFrame = new JFrame("NetAnalyser V1.0"); //Creating and setting the Frame's name.
        myFrame.setBounds(100,100,1300,450);
        
        myFrame.setLayout(null);
        //Set layout as null.
        
        JLabel instructionJLabel = new JLabel("Enter Test URL & no. of probes and click on Process");
        //Creating and adding the "Enter Test URL & no. of probes and click on Process" JLabel.
        instructionJLabel.setBounds(5,0,400,30); //Setting the layout.
        myFrame.add(instructionJLabel);
        
        JLabel testURLJLabel = new JLabel("Test URL");
        //Creating and adding the "Test URL" JLabel.
        testURLJLabel.setBounds(10,40,60,30);
        myFrame.add(testURLJLabel);
        
        textJTextField = new JTextField();
        //Creating and adding the JTextField for entering the URL.
        textJTextField.setBounds(75,40,250,30);
        myFrame.add(textJTextField);
        
        JLabel probesLabel = new JLabel("No. of probes");
        //Creating and adding the "No. of probes" JLabel.
        probesLabel.setBounds(40,100,100,30);
        myFrame.add(probesLabel);
        
        probesJComboBox = new JComboBox<String>();
        //Creating and adding the JComboBox to meet the probes selection requirement.
        
        for (int i = 1; i <= probesNo; i++) {
            String probesStr = Integer.toString(i);
            probesJComboBox.addItem(probesStr);
        }
        //Add ten selections.
        probesJComboBox.setSelectedIndex(0);
        //Set default choice as "1".
        probesJComboBox.setBounds(140,100,100,40);
        myFrame.add(probesJComboBox);
        
        processJButton = new JButton("Process");
        //Creating and adding the Process JButton.
        processJButton.addActionListener(this);
        processJButton.setBounds(90,170,120,30);
        myFrame.add(processJButton);
        
        outputJTextArea = new JTextArea("Your output will appear here...");
        //Creating and adding the "Your output will appear here..." JTextArea.
        outputJTextArea.setBounds(380,0,480,400);
        myFrame.add(outputJTextArea);
        
        JLabel histogramJLabel = new JLabel("Histogram (Every raw output firstly be rounded up to integer)");
        //Creating and adding the "Histogram" JLabel.
        histogramJLabel.setBounds(900,40,450,30);
        myFrame.add(histogramJLabel);
        
        histogram1JLabel = new JLabel();
        //Creating and adding the histogram's bin JLabel.
        histogram1JLabel.setBounds(900,70,400,30);
        histogram1JLabel.setText("");
        myFrame.add(histogram1JLabel);
        
        histogram2JLabel = new JLabel();
        //Creating and adding the histogram's bin JLabel.
        histogram2JLabel.setBounds(900,100,400,30);
        histogram2JLabel.setText("");
        myFrame.add(histogram2JLabel);
        
        histogram3JLabel = new JLabel();
        //Creating and adding the histogram's bin JLabel.
        histogram3JLabel.setBounds(900,130,400,30);
        histogram3JLabel.setText("");
        myFrame.add(histogram3JLabel);
        
        histogramStar1JLabel = new JLabel();
        //Creating and adding the histogram's display of stars JLabel.
        histogramStar1JLabel.setBounds(1100,70,500,30);
        histogramStar1JLabel.setText("");
        myFrame.add(histogramStar1JLabel);
        
        histogramStar2JLabel = new JLabel();
        //Creating and adding the histogram's display of stars JLabel.
        histogramStar2JLabel.setBounds(1100,100,500,30);
        histogramStar2JLabel.setText("");
        myFrame.add(histogramStar2JLabel);
        
        histogramStar3JLabel = new JLabel();
        //Creating and adding the histogram's display of stars JLabel.
        histogramStar3JLabel.setBounds(1100,130,500,30);
        histogramStar3JLabel.setText("");
        myFrame.add(histogramStar3JLabel);
        
        myFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        myFrame.setSize(1400,500);
        myFrame.setLocationRelativeTo(null);
        myFrame.setVisible(true);
    }
    
    
    public void actionPerformed(ActionEvent event) {
        
        /**
         *
         * This method is to execute ping command within java after pressing the button.
         * And to return the corresponding information to GUI.
         * Also, shows the RTT histogram in the GUI and save the corresponding histogram as a file.
         * @param count  int number of line numbers in cmd ping result.
         * @param i  int number to count the for loop for displaying in GUI.
         * @param RTTtempval1 int number of the loction of the symbol "time=".
         * @param RTTtempval2 int number of the loction of the symbol "ms".
         * @param tempRTTFloatVal float number of RTT value.
         * @param tempRoundedRTTIntVal int value of RTT after rounding.
         * @param len int value of the length of the RTT integer value arraylist.
         * @param max int value of the maximum value of RTT.
         * @param min int value of the minimum value of RTT.
         * @param binSize float value of bin size of RTT histogram.
         * @param histogram1 float value of RTT histogram bounderaies value, totally have 6 float numbers.
         * @param histogram2 float value of RTT histogram bounderaies value, totally have 6 float numbers.
         * @param histogram3 float value of RTT histogram bounderaies value, totally have 6 float numbers.
         * @param histogram4 float value of RTT histogram bounderaies value, totally have 6 float numbers.
         * @param histogram5 float value of RTT histogram bounderaies value, totally have 6 float numbers.
         * @param histogram6 float value of RTT histogram bounderaies value, totally have 6 float numbers.
         * @param year int value of current year.
         * @param month int value of current month.
         * @param day int value of current day
         * @param hour  int value of current hour.
         * @param minute  int value of current minute.
         * @param second  int value of current second.
         *
         */
        
        /* The part of ping codes */
        
        outputJTextArea.setText(""); //Clear the text area so that can display the result.
        histogram1JLabel.setText("");
        histogram2JLabel.setText("");
        histogram3JLabel.setText("");
        histogramStar1JLabel.setText("");
        histogramStar2JLabel.setText("");
        histogramStar3JLabel.setText("");
        
        String enteredURL = textJTextField.getText();
        int dotPosition = enteredURL.indexOf('.');
        
        if (enteredURL.length() == 0 || dotPosition == -1) {
            
            int result = JOptionPane.showConfirmDialog(myFrame, "The URL you entered is invalid. Click OK to terminate the program, click cancel to retry.", "Invalid URL", JOptionPane.OK_CANCEL_OPTION, JOptionPane.INFORMATION_MESSAGE);
            if(result == JOptionPane.OK_OPTION){
                System.exit(0);
            }
        }
        
        else {
            

                String selectedProbes = probesJComboBox.getSelectedItem().toString();
                //Get users' entered URL and selection.
                
                ArrayList<String> outcomes = new ArrayList<String>(); //Every line of cmd outputs.
                
                Process p = null;
                
                try {
                    p = Runtime.getRuntime().exec("ping -c " + selectedProbes + " " + enteredURL);
                }
                catch (IOException e1) {
                    System.out.println("System error: " + e1);
                    System.exit(1);
                }
                
                try {
                    p.waitFor();
                }
                catch (InterruptedException e2) {
                    System.out.println("System wait for error: " + e2);
                    System.exit(1);
                }
                
                try {
                    BufferedReader reader = new BufferedReader(new InputStreamReader(p.getInputStream()));
                    String oneLine = reader.readLine();
                    while (oneLine != null) {
                        oneLine = reader.readLine();
                        outcomes.add(oneLine); //Extract all information.
                        System.out.println(oneLine); //Display in cmd/terminal.
                    }
                    reader.close();
                }
                catch (IOException e3) {
                    System.out.println("Errors occured: " + e3);
                    System.exit(1);
                }
                
                int count = outcomes.size();
                
                for(int i = 0; i < count; i++) {
                    outputJTextArea.append(outcomes.get(i));
                    outputJTextArea.append("\n");
                } //Display ping result on GUI.
                
                /* The part of calculating and showing RTT histogram */
                
                ArrayList<String> RTTvaluestr = new ArrayList<String>(); //RTT in string.
                ArrayList<Integer> RTTvalueint = new ArrayList<Integer>(); //RTT in integer.
                ArrayList<Integer> RTTposition1 = new ArrayList<Integer>(); //One of the coordinates of RTT in every line.
                ArrayList<Integer> RTTposition2 = new ArrayList<Integer>(); //The other coordinate of RTT in every line.
                
                for(int i = 0; i < count; i++) {
                    
                    int RTTtempval1 = -1;
                    int RTTtempval2 = -1;
                    try {
                        if(outcomes.get(i).indexOf("time=") != -1) { //Look up if there is a valid ping first.
                                       RTTposition1.add(outcomes.get(i).indexOf("time="));
                                       RTTposition2.add(outcomes.get(i).indexOf("ms"));
                                       RTTtempval1 = RTTposition1.get(i) + 5; //Eleminating "time=" length.
                                       RTTtempval2 = RTTposition2.get(i) - 1; //Eleminating " " length.
                                       RTTvaluestr.add(outcomes.get(i).substring(RTTtempval1,RTTtempval2)); //Get current RTT value in String type.
                                   }
                                   else { //Loss
                                       RTTposition1.add(-1);
                                       RTTposition2.add(-1);
                                       RTTvaluestr.add("-1");
                                   }
                                   
                                   float tempRTTFloatVal = Float.parseFloat(RTTvaluestr.get(i));
                                   int tempRoundedRTTIntVal = Math.round(tempRTTFloatVal);
                                   
                                   RTTvalueint.add(tempRoundedRTTIntVal);
                                   
                    }
                    catch (NullPointerException e4) { } //Ignore.
                }
                
                
                Iterator<Integer> RTTvalueintIterator = RTTvalueint.iterator();
                while(RTTvalueintIterator.hasNext()){
                    Integer e = RTTvalueintIterator.next();
                    if(e.equals(-1)) { //Eliminating all the invalid values.
                    RTTvalueintIterator.remove();
                    }
                }
                
                int len = RTTvalueint.size();
                int max = -1;
                for(int i = 0; i < len; i++) { //Pick the max value.
                    if (RTTvalueint.get(i) > max) {
                        max = RTTvalueint.get(i);
                    }
                    else {
                        max = max;
                    }
                }
                
                int min = max;
                for(int i = 0; i < len; i++) { //Pick the min value.
                    if (RTTvalueint.get(i) < min) {
                        min = RTTvalueint.get(i);
                    }
                    else {
                        min = min;
                    }
                }
            
                if (min == -1 && max == -1) {
                    
                    int result = JOptionPane.showConfirmDialog(myFrame, "Ping output results in an error. Click OK to terminate the program, click cancel to retry.", "Ping Output Error", JOptionPane.OK_CANCEL_OPTION, JOptionPane.INFORMATION_MESSAGE);
                    if(result == JOptionPane.OK_OPTION){
                        System.exit(0);
                    }
                }
                
                float binSize = (float)(max - min)/3;
                float histogram1 = (float)min, histogram2 = (float)min + binSize, histogram3 = histogram2, histogram4 = (float)max - binSize, histogram5 = histogram4, histogram6 = (float)max;
                
                String histogram1str = String.format("%.2f",histogram1).toString();
                String histogram2str = String.format("%.2f",histogram2).toString();
                String histogram3str = String.format("%.2f",histogram3).toString();
                String histogram4str = String.format("%.2f",histogram4).toString();
                String histogram5str = String.format("%.2f",histogram5).toString();
                String histogram6str = String.format("%.2f",histogram6).toString();
                //Round up
                
                String histogramDisplay1 = histogram1str.concat("<=RTT<" + histogram2str);
                String histogramDisplay2 = histogram3str.concat("<=RTT<" + histogram4str);
                String histogramDisplay3 = histogram5str.concat("<=RTT<" + histogram6str);
                //Display string concatenates.
                
                if (histogram1 == histogram3 && histogram3 == histogram4) {
                    //Determine if all RTT values are in the same interval.
                    //YES.
                    
                    histogram1JLabel.setText(histogramDisplay1);
                    histogram2JLabel.setText("");
                    histogram3JLabel.setText("");
                    
                    String howManyStars = "";
                    for (int i = 0; i < len; i++) {
                        howManyStars = howManyStars.concat(" * ");
                    }
                    histogramStar1JLabel.setText(howManyStars);
                    histogramStar2JLabel.setText("");
                    histogramStar3JLabel.setText("");
                    
                }
                else { //NO.
                    
                    histogram1JLabel.setText(histogramDisplay1);
                    histogram2JLabel.setText(histogramDisplay2);
                    histogram3JLabel.setText(histogramDisplay3);
                    
                    int displayCount1 = 0, displayCount2 = 0, displayCount3 = 0;
                    
                    for (int i = 0; i < len; i++) {
                        
                        if (RTTvalueint.get(i) >= histogram1 && RTTvalueint.get(i) < histogram2) {
                            displayCount1++;
                        }
                        else if (RTTvalueint.get(i) >= histogram3 && RTTvalueint.get(i) < histogram4) {
                            displayCount2++;
                        }
                        else {
                            displayCount3++;
                        }
                    }
                    
                    String howManyStars1 = "";
                    String howManyStars2 = "";
                    String howManyStars3 = "";
                    
                    for (int i = 0; i < displayCount1; i++) {
                        howManyStars1 = howManyStars1.concat(" * ");
                    }
                    
                    for (int i = 0; i < displayCount2; i++) {
                        howManyStars2 = howManyStars2.concat(" * ");
                    }
                    
                    for (int i = 0; i < displayCount3; i++) {
                        howManyStars3 = howManyStars3.concat(" * ");
                    }
                    
                    histogramStar1JLabel.setText(howManyStars1);
                    histogramStar2JLabel.setText(howManyStars2);
                    histogramStar3JLabel.setText(howManyStars3);
                    
                    /* The part of File I/O */
                    
                    String URLToFile = enteredURL.replace('.','-');
                    URLToFile = URLToFile.concat("-");
                    LocalDateTime now = LocalDateTime.now();
                    int year = now.getYear();
                    int month = now.getMonthValue();
                    int day = now.getDayOfMonth();
                    int hour = now.getHour();
                    int minute = now.getMinute();
                    int second = now.getSecond();
                    //Get current time.
                    
                    String fileName = URLToFile + year + "-" + month + "-" + day + "-" + hour + "-" + minute + "-" + second + ".txt";
                    //Modifying the file name.
                    
                    File RTTFile = new File(fileName); //Create a file as required.
                    
                    try {
                        FileWriter fileWriter = new FileWriter(fileName);
                        BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);
                        bufferedWriter.write(fileName);
                        bufferedWriter.newLine(); //Newline.
                        bufferedWriter.newLine();
                        bufferedWriter.write(histogram1str + "-" + histogram2str + ": " + displayCount1);
                        bufferedWriter.newLine();
                        bufferedWriter.write(histogram3str + "-" + histogram4str + ": " + displayCount2);
                        bufferedWriter.newLine();
                        bufferedWriter.write(histogram5str + "-" + histogram6str + ": " + displayCount3);
                        bufferedWriter.close();
                        fileWriter.close();
                    }
                    catch (IOException e) {
                        System.out.println("Errors occured while writing the file.");
                    }
                    
                }

        }
        
    }
        
}
