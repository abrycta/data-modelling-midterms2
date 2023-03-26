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

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.UIManager;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;

public class SimulationUI extends JFrame implements ActionListener {

    private final JTextField simTimeTextField;
    private final JButton runButton;
    private final JTable resultTable;

    public SimulationUI() {
        setTitle("Simulation UI");
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        setUndecorated(false);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        JPanel inputPanel = new JPanel(new GridBagLayout());
        inputPanel.setBackground(Color.WHITE);
        GridBagConstraints c = new GridBagConstraints();
        c.gridx = 0;
        c.gridy = 0;
        c.insets = new Insets(20, 20, 20, 20);
        c.anchor = GridBagConstraints.LINE_START;
        inputPanel.add(new JLabel("Simulation Time (minutes): "), c);
        c.gridx++;
        simTimeTextField = new JTextField(10);
        simTimeTextField.setBorder(BorderFactory.createLineBorder(Color.GRAY));
        simTimeTextField.setFont(new Font("Arial", Font.PLAIN, 16));
        simTimeTextField.setForeground(Color.BLACK);
        inputPanel.add(simTimeTextField, c);
        c.gridx++;
        runButton = new JButton("Run Simulation");
        runButton.addActionListener(this);
        runButton.setFont(new Font("Arial", Font.PLAIN, 16));
        runButton.setBackground(Color.WHITE);
        runButton.setForeground(Color.BLACK);
        inputPanel.add(runButton, c);

        String[] columnNames = {"Entity No.", "Time t", "Event type", "Q(t)", "B(t)", "Arrival time in queue",
                "Arrival time in service","P", "N", "ΣWQ", "WQ*", "ΣTS", "TS*", "∫Q", "Q*", "∫B"};
        DefaultTableModel model = new DefaultTableModel(columnNames, 0);
        resultTable = new JTable(model);
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

        JPanel contentPane = new JPanel(new BorderLayout());
        contentPane.add(inputPanel, BorderLayout.NORTH);
        contentPane.add(scrollPane, BorderLayout.CENTER);
        contentPane.setBackground(Color.WHITE);
        add(contentPane);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        Simulator simulator = new Simulator();
        if (e.getSource() == runButton) {
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

            // Create a new row as an object
            // Object[] tableValues = {values, from, simulation...}

            //Add the new row to the table using addRow
            // model.addRow(tableValues)

            //loop for all objects.
        }
    }

    public static void main(String[] args) {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {
            // todo: handle exception
            e.printStackTrace();
        }
        SimulationUI ui = new SimulationUI();
        ui.setVisible(true);
    }
}
