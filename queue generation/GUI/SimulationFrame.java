package GUI;

import BusinessLogic.SimulationManager;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class SimulationFrame {


    public JFrame frame = new JFrame("Simulation");
    public String[] strategies = {"SHORTEST_QUEUE", "SHORTEST_TIME"};
    public ArrayList<Integer> list;
    public int butonn = 0;
    JComboBox<String> strategyDropdown = new JComboBox<String>(strategies);
    private JLabel tema2 = new JLabel("Generare cozi");
    private JLabel clientsLabel = new JLabel("Number of clients: ");
    private JLabel queueSizeLabel = new JLabel("Queue size: ");
    private JLabel timeLimitLabel = new JLabel("Time Limit:");
    private JLabel minArrivalLabel = new JLabel("Minimum arrival:");
    private JLabel maxArrivalLabel = new JLabel("Maximum arrival ");
    private JLabel minServiceLabel = new JLabel("Minimum service");
    private JLabel maxServiceLabel = new JLabel("Maximum service");
    private JButton startButton = new JButton("Start Simulation");

    private JButton printFromFile = new JButton("Log of events");
    private JButton printFromFile2 = new JButton("Result");
    private JTextField clientsField = new JTextField(10);
    private JTextField queueSizeField = new JTextField(10);
    private JTextField timeLimitField = new JTextField(10);
    private JTextField minArrivalField = new JTextField(10);
    private JTextField maxArrivalField = new JTextField(10);
    private JTextField minServiceField = new JTextField(10);
    private JTextField maxServiceField = new JTextField(10);

    private JTextArea textArea1 = new JTextArea();
    private JTextArea textArea2 = new JTextArea();

    private void printFromFile() {
        try {
            BufferedReader reader = new BufferedReader(new FileReader("filename.txt"));
            String line;
            StringBuilder content = new StringBuilder();
            while ((line = reader.readLine()) != null) {
                content.append(line).append("\n");
            }
            reader.close();

            textArea2.setText(content.toString());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void printFromFile2() {
        try {
            BufferedReader reader = new BufferedReader(new FileReader("rezultat.txt"));
            String line;
            StringBuilder content = new StringBuilder();
            while ((line = reader.readLine()) != null) {
                content.append(line).append("\n");
            }
            reader.close();

            textArea1.setText(content.toString());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public SimulationFrame() {
        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
        Font font = new Font("Times New Roman", Font.PLAIN, 25);
        JScrollPane scrollPane = new JScrollPane(textArea1);
        JScrollPane scrollPane2 = new JScrollPane(textArea2);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        scrollPane2.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        clientsLabel.setFont(font);
        queueSizeLabel.setFont(font);
        timeLimitLabel.setFont(font);
        minArrivalLabel.setFont(font);
        maxServiceLabel.setFont(font);
        maxArrivalLabel.setFont(font);
        minServiceLabel.setFont(font);
        tema2.setFont(font);
        clientsLabel.setForeground(Color.BLUE);
        queueSizeLabel.setForeground(Color.BLUE);
        timeLimitLabel.setForeground(Color.BLUE);
        minArrivalLabel.setForeground(Color.BLUE);
        maxArrivalLabel.setForeground(Color.BLUE);
        minServiceLabel.setForeground(Color.BLUE);
        maxServiceLabel.setForeground(Color.BLUE);
        tema2.setBounds(250, 30, 200, 35);
        clientsLabel.setBounds(20, 70, 200, 25);
        clientsField.setBounds(210, 70, 100, 25);

        queueSizeField.setBounds(210, 105, 100, 25);
        queueSizeLabel.setBounds(20, 105, 200, 25);

        timeLimitLabel.setBounds(20, 140, 200, 25);
        timeLimitField.setBounds(210, 140, 100, 25);

        minArrivalLabel.setBounds(20, 175, 200, 25);
        minArrivalField.setBounds(210, 175, 100, 25);

        maxArrivalLabel.setBounds(20, 210, 200, 25);
        maxArrivalField.setBounds(210, 210, 100, 25);

        minServiceLabel.setBounds(20, 245, 200, 25);
        minServiceField.setBounds(210, 245, 100, 25);

        maxServiceLabel.setBounds(20, 280, 200, 25);
        maxServiceField.setBounds(210, 280, 100, 25);

        textArea1.setBounds(450, 100, 400, 200);
        textArea2.setBounds(450, 350, 400, 400);
        strategyDropdown.setBounds(20, 330, 400, 35);
        strategyDropdown.setFont(font);
        startButton.setBounds(20, 450, 400, 35);
        startButton.setFont(font);
        printFromFile.setBounds(20, 500, 400, 35);
        printFromFile2.setBounds(20, 550, 400, 35);


        frame.add(strategyDropdown);
        frame.add(startButton);
        frame.add(tema2);
        frame.add(printFromFile);
        frame.add(printFromFile2);
        frame.add(maxServiceField);
        frame.add(maxServiceLabel);
        frame.add(minServiceField);
        frame.add(minServiceLabel);
        frame.add(maxArrivalField);
        frame.add(maxArrivalLabel);
        frame.add(minArrivalField);
        frame.add(minArrivalLabel);
        frame.add(timeLimitLabel);
        frame.add(timeLimitField);
        frame.add(queueSizeLabel);
        frame.add(queueSizeField);
        frame.add(clientsLabel);
        frame.add(clientsField);
        frame.add(textArea1);
        frame.add(textArea2);
        frame.add(clientsField);
        frame.add(scrollPane);
        frame.add(scrollPane2);
        frame.add(mainPanel);
        frame.pack();


        startButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                butonn = 1;

            }
        });

        printFromFile.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                printFromFile();
            }
        });

        printFromFile2.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                printFromFile2();
            }
        });
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
        frame.setResizable(false);
        frame.setSize(1000, 850);

    }



    public String getClientsField() {
        return clientsField.getText();
    }

    public String getQueueSizeField() {
        return queueSizeField.getText();
    }

    public String getTimeLimitField() {

        return timeLimitField.getText();
    }

    public String getMinArrivalField() {

        return minArrivalField.getText();
    }

    public String getMaxArrivalField() {

        return maxArrivalField.getText();
    }

    public String getInputMinService() {

        return minServiceField.getText();
    }

    public String getMaxServiceField() {
        return maxServiceField.getText();
    }
    public String getStrategyDropdown() {
        return (String) strategyDropdown.getSelectedItem();
    }




}
