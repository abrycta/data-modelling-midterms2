import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.EventObject;

import javax.swing.*;
import javax.swing.table.*;

public class SimulationUI extends JFrame implements ActionListener {

    private final JTextField simTimeTextField;
    private final JButton runButton;
    private final JTable resultTable;

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
        inputPanel.add(runButton, gridBagConstraints);

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
        DefaultCellEditor nonEditagbleCellEditor = new DefaultCellEditor(new JTextField()) {
            @Override
            public boolean isCellEditable(EventObject eventObject) {
                return false;
            }
        };

        for (int i = 0; i < resultTable.getColumnCount(); i++) {
            resultTable.getColumnModel().getColumn(i).setCellEditor(nonEditagbleCellEditor);
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
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        // empty table
        clearTable(resultTable);

        Simulator simulator = new Simulator();
        if (e.getSource() == runButton) {
            try {
                // Get simulation time from input field
                int simTime = Integer.parseInt(simTimeTextField.getText());
                ArrayList<Event> eventArrayList = simulator.simulateMinutes(simTime);
                // Run simulation and update result table
                // retrieve defaultTableModel
                DefaultTableModel model = (DefaultTableModel) resultTable.getModel();

                for (Event event : eventArrayList) {
                    Object[] tableValues = {event.getEventID(), event.getTime(), event.getEventType(), event.getNumberOfPartsInQueue(),
                            event.getUtilization(), event.getTimesInQueue(), event.getPartInServiceTime(), event.getPartsProducedSoFar(),
                            event.getNumberOfPartsThatPassedThroughTheQueueSoFar(), event.getWaitingTimeInQueueSoFar(),
                            event.getLongestTimeSpentInQueueSoFar(), event.getTotalTimeSpentInSystemByAllPartsThatHaveDeparted(),
                            event.getLongestTimeInSystem(), event.getAreaUnderQueueLengthCurve(), event.getHighestLevelOfQ(),
                            event.getAreaUnderServerBusy()};

                    model.addRow(tableValues);
                }
            } catch (NumberFormatException ex) {
                showErrorMessage("Please enter a simulation time", "Invalid");
            }
        }
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


