import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.util.*;
public class RoutinePlanner extends JFrame implements ActionListener 
{
    private DefaultTableModel tableModel;
    private JTable table;
    private JTextField timeField, activityField;
    private JButton addWorkBtn, addSatBtn, addSunBtn, saveBtn, showBtn, clearBtn, exitBtn;
    private JComboBox<String> dayBox;
    private java.util.List<String> days = new ArrayList<>(Arrays.asList("Monday", "Tuesday", "Wednesday", "Thursday", "Friday"));
    private Map<String, Map<String, String>> routineMap = new LinkedHashMap<>();
    public RoutinePlanner() 
    {
        super("Weekly Routine Planner");
        setSize(900, 600);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout(10, 10));
        getContentPane().setBackground(new Color(240, 248, 255));
        JLabel title = new JLabel("Weekly Routine Planner", JLabel.CENTER);
        title.setFont(new Font("Segoe UI", Font.BOLD, 22));
        title.setOpaque(true);
        title.setBackground(new Color(173, 216, 230));
        title.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        add(title, BorderLayout.NORTH);
        tableModel = new DefaultTableModel();
        tableModel.addColumn("Time");
        for (String day : days)
            tableModel.addColumn(day);
        table = new JTable(tableModel);
        table.setRowHeight(28);
        table.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        table.getTableHeader().setFont(new Font("Segoe UI", Font.BOLD, 15));
        table.setGridColor(Color.LIGHT_GRAY);
        table.setBackground(new Color(255, 255, 240));
        JScrollPane scrollPane = new JScrollPane(table);
        add(scrollPane, BorderLayout.CENTER);
        JPanel inputPanel = new JPanel(new GridLayout(2, 4, 10, 10));
        inputPanel.setBorder(BorderFactory.createTitledBorder("Add Work"));
        inputPanel.setBackground(new Color(240, 255, 255));
        inputPanel.add(new JLabel("Select Day:", JLabel.RIGHT));
        dayBox = new JComboBox<>(days.toArray(new String[0]));
        inputPanel.add(dayBox);
        inputPanel.add(new JLabel("Time (e.g. 9AM-10AM):", JLabel.RIGHT));
        timeField = new JTextField();
        inputPanel.add(timeField);
        inputPanel.add(new JLabel("Activity:", JLabel.RIGHT));
        activityField = new JTextField();
        inputPanel.add(activityField);
        addWorkBtn = new JButton("Add Work");
        addWorkBtn.setBackground(new Color(173, 216, 230));
        addWorkBtn.setFont(new Font("Segoe UI", Font.BOLD, 13));
        inputPanel.add(addWorkBtn);
        addWorkBtn.addActionListener(this);
        add(inputPanel, BorderLayout.NORTH);
        JPanel btnPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 15, 10));
        btnPanel.setBackground(new Color(230, 245, 255));
        addSatBtn = new JButton("Add Saturday");
        addSunBtn = new JButton("Add Sunday");
        showBtn = new JButton("Show Routine");
        saveBtn = new JButton("Save to File");
        clearBtn = new JButton("Clear Routine");
        exitBtn = new JButton("Exit");
        JButton[] buttons = {addSatBtn, addSunBtn, showBtn, saveBtn, clearBtn, exitBtn};
        for (JButton b : buttons) 
        {
            b.setFont(new Font("Segoe UI", Font.BOLD, 13));
            b.setBackground(new Color(176, 224, 230));
            b.addActionListener(this);
            btnPanel.add(b);
        }
        add(btnPanel, BorderLayout.SOUTH);
        setVisible(true);
    }
    @Override
    public void actionPerformed(ActionEvent e) 
    {
        Object src = e.getSource();
        if (src == addSatBtn) 
        {
            if (!days.contains("Saturday")) 
            {
                days.add("Saturday");
                tableModel.addColumn("Saturday");
                dayBox.addItem("Saturday");
            }
        }
        else if (src == addSunBtn)
        {
            if (!days.contains("Sunday")) 
            {
                days.add("Sunday");
                tableModel.addColumn("Sunday");
                dayBox.addItem("Sunday");
            }
        }
        else if (src == addWorkBtn) 
        {
            String time = timeField.getText().trim();
            String activity = activityField.getText().trim();
            String day = (String) dayBox.getSelectedItem();
            if (time.isEmpty() || activity.isEmpty() || day == null)
                return;
            int rowIndex = findRowByTime(time);
            if (rowIndex == -1) 
            {
                tableModel.addRow(new Object[days.size() + 1]);
                rowIndex = tableModel.getRowCount() - 1;
                tableModel.setValueAt(time, rowIndex, 0);
            }
            int colIndex = days.indexOf(day) + 1;
            tableModel.setValueAt(activity, rowIndex, colIndex);
            routineMap.putIfAbsent(day, new LinkedHashMap<>());
            routineMap.get(day).put(time, activity);
            timeField.setText("");
            activityField.setText("");
        }
        else if (src == showBtn) 
        {
            StringBuilder sb = new StringBuilder();
            for (String day : routineMap.keySet())
            {
                sb.append(day).append(":\n");
                Map<String, String> times = routineMap.get(day);
                for (String t : times.keySet())
                {
                    sb.append("   ").append(t).append(" → ").append(times.get(t)).append("\n");
                }
                sb.append("\n");
            }
            JTextArea area = new JTextArea(sb.toString());
            area.setEditable(false);
            area.setFont(new Font("Consolas", Font.PLAIN, 14));
            JOptionPane.showMessageDialog(this, new JScrollPane(area), "Routine Summary", JOptionPane.INFORMATION_MESSAGE);
        }
        else if (src == saveBtn) {
            try (BufferedWriter writer = new BufferedWriter(new FileWriter("Routine.txt")))
            {
                writer.write("+------------+--------------+----------------------------+\n");
                writer.write("| Day        | Time         | Work                       |\n");
                writer.write("+------------+--------------+----------------------------+\n");

                for (String day : routineMap.keySet()) 
                {
                    Map<String, String> times = routineMap.get(day);
                    for (String t : times.keySet()) 
                    {
                        String dayCol = String.format("%-10s", day);
                        String timeCol = String.format("%-12s", t);
                        String workCol = String.format("%-26s", times.get(t));
                        writer.write("| " + dayCol + " | " + timeCol + " | " + workCol + " |\n");
                    }
                }

                writer.write("+------------+--------------+----------------------------+\n");
                JOptionPane.showMessageDialog(this, "Routine saved as Routine.txt");
            } 
            catch (IOException ex) 
            {
                JOptionPane.showMessageDialog(this, "Error saving routine: " + ex.getMessage());
            }
        }
        else if (src == clearBtn) 
        {
            tableModel.setRowCount(0);
            routineMap.clear();
        }
        else if (src == exitBtn) 
        {
            System.exit(0);
        }
    }
    private int findRowByTime(String time)
    {
        for (int i = 0; i < tableModel.getRowCount(); i++)
        {
            if (time.equalsIgnoreCase((String) tableModel.getValueAt(i, 0)))
            {
                return i;
            }
        }
        return -1;
    }
    public static void main(String[] args) 
    {
        SwingUtilities.invokeLater(RoutinePlanner::new);
    }
}