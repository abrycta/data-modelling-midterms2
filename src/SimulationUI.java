import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.EventObject;

import javax.swing.*;
import javax.swing.table.*;

public class SimulationUI extends JFrame implements ActionListener {
    private JFrame statFrame;
    private final JTextField simTimeTextField;
    private final JButton runButton;
    private final JButton statButton;
    private final JTable resultTable;
    private boolean runButtonPressed = false;
    private boolean statWindowStarted = false;
    private double avgTotalSystemTime = 0;
    private double avgWaitingQueueTime = 0;
    private double avgQueueTimeNumber = 0;
    private double drillPressUtil = 0;

    public SimulationUI() {
        setTitle("Simulation UI");
        setSize(1300, 700);
        setUndecorated(false);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        JPanel inputPanel = new JPanel(new GridBagLayout());
        inputPanel.setBackground(Color.WHITE);
        GridBagConstraints gridBagConstraints = new GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.insets = new Insets(20, 20, 20, 20);
        gridBagConstraints.anchor = GridBagConstraints.LINE_START;
        inputPanel.add(new JLabel("Simulation Time (minutes): "), gridBagConstraints);
        gridBagConstraints.gridx++;
        simTimeTextField = new JTextField(10);
        simTimeTextField.setBorder(BorderFactory.createLineBorder(Color.GRAY));
        simTimeTextField.setFont(new Font("Arial", Font.PLAIN, 16));
        simTimeTextField.setForeground(Color.BLACK);
        inputPanel.add(simTimeTextField, gridBagConstraints);
        gridBagConstraints.gridx++;
        runButton = new JButton("Run Simulation");
        runButton.addActionListener(this);
        runButton.setFont(new Font("Arial", Font.PLAIN, 16));
        runButton.setBackground(Color.WHITE);
        runButton.setForeground(Color.BLACK);

        statButton = new JButton("Statistics");
        statButton.setFont(new Font("Arial", Font.PLAIN, 16));
        statButton.setBackground(Color.WHITE);
        statButton.setForeground(Color.BLACK);
        inputPanel.add(runButton);
        inputPanel.add(statButton);

        String[] columnNames = {"Entity No.", "Time t", "Event type", "Q(t)", "B(t)", "Arrival time in queue",
                "Arrival time in service","P", "N", "ΣWQ", "WQ*", "ΣTS", "TS*", "∫Q", "Q*", "∫B"};
        DefaultTableModel model = new DefaultTableModel(columnNames, 0);
        resultTable = new JTable(model);
        centerTable(resultTable);
        resultTable.setRowHeight(30);
        resultTable.setShowVerticalLines(false);
        resultTable.setFillsViewportHeight(true);
        resultTable.getTableHeader().setReorderingAllowed(false);
        resultTable.getTableHeader().setBackground(Color.BLACK);
        resultTable.getTableHeader().setForeground(Color.BLACK);
        resultTable.getTableHeader().setFont(new Font("Arial", Font.BOLD, 16));
        JTableHeader header = resultTable.getTableHeader();
        header.setPreferredSize(new Dimension(header.getWidth(), 30));
        JScrollPane scrollPane = new JScrollPane(resultTable);

        // set cells not editable
        DefaultCellEditor nonEditableCellEditor = new DefaultCellEditor(new JTextField()) {
            @Override
            public boolean isCellEditable(EventObject eventObject) {
                return false;
            }
        };

        for (int i = 0; i < resultTable.getColumnCount(); i++) {
            resultTable.getColumnModel().getColumn(i).setCellEditor(nonEditableCellEditor);
        }

        TableColumnModel tableColumnModel = resultTable.getColumnModel();
        tableColumnModel.getColumn(3).setPreferredWidth(10);
        tableColumnModel.getColumn(4).setPreferredWidth(10);
        tableColumnModel.getColumn(5).setPreferredWidth(130);
        tableColumnModel.getColumn(6).setPreferredWidth(130);
        tableColumnModel.getColumn(7).setPreferredWidth(10);
        tableColumnModel.getColumn(8).setPreferredWidth(10);
        tableColumnModel.getColumn(9).setPreferredWidth(10);
        tableColumnModel.getColumn(10).setPreferredWidth(10);
        tableColumnModel.getColumn(11).setPreferredWidth(10);
        tableColumnModel.getColumn(12).setPreferredWidth(10);
        tableColumnModel.getColumn(13).setPreferredWidth(10);
        tableColumnModel.getColumn(14).setPreferredWidth(10);
        tableColumnModel.getColumn(15).setPreferredWidth(10);

        JPanel contentPane = new JPanel(new BorderLayout());
        contentPane.add(inputPanel, BorderLayout.NORTH);
        contentPane.add(scrollPane, BorderLayout.CENTER);
        contentPane.setBackground(Color.WHITE);
        add(contentPane);

        statButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (runButtonPressed) {
                    statFrame.setVisible(true);
                } else {
                    showErrorMessage("You must simulate first", "Error!");
                }
            }
        });
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        // empty table
        clearTable(resultTable);
        String eventType = null;
        Simulator simulator = new Simulator();
        ArrayList<Event> eventArrayList;
        if (e.getSource() == runButton) {
            try {
                // Get simulation time from input field
                int simTime = Integer.parseInt(simTimeTextField.getText());
                eventArrayList = simulator.simulateMinutes(simTime);
                // Run simulation and update result table
                // retrieve defaultTableModel
                DefaultTableModel model = (DefaultTableModel) resultTable.getModel();

                // set the init row
                Object[] initValues = {"-", "0.00", "Init", "0", "0", "0", "0", "0", "0", "0.0", "0.0", "0.0", "0.0", "0.0", "0.0", "0.0"};
                model.addRow(initValues);

                // set the rows
                for (Event event : eventArrayList) {
                    if (event.getEventType() == 1){
                        eventType = "Arrival";
                    } else if (event.getEventType() == 2){
                        eventType = "Departure";
                    }
                    Object[] tableValues = {event.getEventID(), event.getTime(), eventType, event.getNumberOfPartsInQueue(),
                            event.getUtilization(), event.getTimesInQueue(), event.getPartInServiceTime(), event.getPartsProducedSoFar(),
                            event.getNumberOfPartsThatPassedThroughTheQueueSoFar(), event.getWaitingTimeInQueueSoFar(),
                            event.getLongestTimeSpentInQueueSoFar(), event.getTotalTimeSpentInSystemByAllPartsThatHaveDeparted(),
                            event.getLongestTimeInSystem(), event.getAreaUnderQueueLengthCurve(), event.getHighestLevelOfQ(),
                            event.getAreaUnderServerBusy()};

                    model.addRow(tableValues);
                }

                // set the end row
                if (!eventArrayList.isEmpty()) {
                    Event lastEvent = eventArrayList.get(eventArrayList.size() - 1);
                    lastEvent.setTime(simTime);
                    Object[] endValues = {"-", lastEvent.getTime(), "End", lastEvent.getNumberOfPartsInQueue(),
                            lastEvent.getUtilization(), lastEvent.getTimesInQueue(), lastEvent.getPartInServiceTime(), lastEvent.getPartsProducedSoFar(),
                            lastEvent.getNumberOfPartsThatPassedThroughTheQueueSoFar(), lastEvent.getWaitingTimeInQueueSoFar(),
                            lastEvent.getLongestTimeSpentInQueueSoFar(), lastEvent.getTotalTimeSpentInSystemByAllPartsThatHaveDeparted(),
                            lastEvent.getLongestTimeInSystem(), lastEvent.getAreaUnderQueueLengthCurve(), lastEvent.getHighestLevelOfQ(),
                            lastEvent.getAreaUnderServerBusy()};
                    model.addRow(endValues);
                }

                if (statWindowStarted) {
                    statFrame.dispose();
                }

                avgTotalSystemTime = simulator.averageTotalTimeInSystem(eventArrayList);
                avgWaitingQueueTime = simulator.averageWaitingTimeInQueue(eventArrayList);
                avgQueueTimeNumber = simulator.timeAverageNumberInQueue(eventArrayList);
                drillPressUtil = simulator.drillPressUtilization(eventArrayList);

                runButtonPressed = true;
                startStatFrame();
            } catch (NumberFormatException ex) {
                showErrorMessage("Please enter a simulation time", "Invalid");
            }
        }
    }

    public void startStatFrame() {
        statFrame = new JFrame("Statistics");
        JPanel statPanel = new JPanel(new GridLayout(1, 1));

        String[] columnNames = {"Statistics", "Value"};
        Object[][] data = {
                {"Average Total Time in System", ""},
                {"Average Waiting Time in Queue", ""},
                {"Time Average Number in Queue", ""},
                {"Drill Press Utilization", ""}
        };
        DefaultTableModel tableModel = new DefaultTableModel(data, columnNames);
        JTable table = new JTable(tableModel);
        table.getTableHeader().setReorderingAllowed(false);

        tableModel.setValueAt(avgTotalSystemTime, 0, 1); // Average Total Time in System
        tableModel.setValueAt(avgWaitingQueueTime, 1, 1); // Average Waiting Time in Queue
        tableModel.setValueAt(avgQueueTimeNumber, 2, 1); // Time Average Number in Queue
        tableModel.setValueAt(drillPressUtil, 3, 1); // Drill Press Utilization

        JScrollPane scrollPane = new JScrollPane(table);
        statPanel.add(scrollPane);

        statFrame.add(statPanel);
        statFrame.setSize(500, 300);
        statFrame.setLocationRelativeTo(null);
        statFrame.setVisible(false);
        statWindowStarted = true;
    }

    public void clearTable(JTable jtable) {
        DefaultTableModel model = (DefaultTableModel) jtable.getModel();
        model.setRowCount(0);
    }

    public static void showErrorMessage(String message, String title) {
        JOptionPane.showMessageDialog(null, message, title, JOptionPane.ERROR_MESSAGE);
    }

    public static void centerTable(JTable jTable) {
        DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
        centerRenderer.setHorizontalAlignment(SwingConstants.CENTER);

        for (int i = 0; i < jTable.getColumnCount(); i++) {
            jTable.getColumnModel().getColumn(i).setCellRenderer(centerRenderer);
        }
    }

    public static void main(String[] args) {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {
            showErrorMessage("Unable to set LookAndFeel", "Error!");
        }
        SimulationUI ui = new SimulationUI();
        ui.setVisible(true);
    }
}


