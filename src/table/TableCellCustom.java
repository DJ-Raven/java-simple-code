package table;

import java.awt.Component;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.GridLayout;
import java.awt.event.MouseAdapter;
import javax.swing.DefaultCellEditor;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.UIManager;
import javax.swing.plaf.nimbus.NimbusLookAndFeel;
import javax.swing.table.DefaultTableModel;

public class TableCellCustom extends JFrame {

    public TableCellCustom() {
        //  Frame Option
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(new Dimension(600, 500));
        setLocationRelativeTo(null);

        //  Create Table
        DefaultTableModel model = new DefaultTableModel(new Object[]{"No", "Name", "Description"}, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return column == 2;
            }
        };
        JTable table = new JTable(model);
        table.setRowHeight(25);
        table.getColumnModel().getColumn(2).setPreferredWidth(300);
        model.addRow(new Object[]{1, "Mr 001", "Description 1 (double click here)"});
        model.addRow(new Object[]{2, "Mr 002", "Description 2 (double click here)"});
        model.addRow(new Object[]{3, "Mr 003", "Description 3 (double click here)"});
        model.addRow(new Object[]{4, "Mr 004", "Description 4 (double click here)"});
        model.addRow(new Object[]{5, "Mr 005", "Description 5 (double click here)"});

        //  Install custom cell editor to columne index 2
        table.getColumnModel().getColumn(2).setCellEditor(new CustomCellEditor(table));

        //  Add table with scrollpane to frame
        getContentPane().add(new JScrollPane(table));

    }

    private class CustomCellEditor extends DefaultCellEditor {

        private final JTable table;
        private int row;

        public CustomCellEditor(JTable table) {
            super(new JTextField());
            this.table = table;
        }

        @Override
        public Component getTableCellEditorComponent(JTable table, Object value, boolean isSelected, int row, int column) {
            Component com = super.getTableCellEditorComponent(table, value, isSelected, row, column);
            this.row=row;
            JPanel panel = new JPanel();
            //  Add mouse event to avoid lost cell editor when click on this panel
            panel.addMouseListener(new MouseAdapter() {
            });
            //  Apply default cell border to panel
            panel.setBorder(((JComponent) com).getBorder());

            panel.setLayout(new GridLayout(0, 1, 0, 2));
            //  Create Label for the cell value ( jlabe user can't edit )
            JLabel label = new JLabel(value == null ? "" : value.toString());
            panel.add(label);

            //  Add more custom to this panel
            panel.add(new JCheckBox("Check box"));
            panel.add(new JRadioButton("Radio button"));
            panel.add(new JButton("Button"));
            
            //  Set table row height
            table.setRowHeight(row, panel.getPreferredSize().height);
            return panel;
        }

        @Override
        public boolean stopCellEditing() {
            //  Set table row hight to default on stop cell editing
            table.setRowHeight(row, table.getRowHeight());
            return super.stopCellEditing();
        }   
    }

    public static void main(String[] args) {
        try {
            UIManager.setLookAndFeel(new NimbusLookAndFeel());
        } catch (Exception e) {
        }
        EventQueue.invokeLater(() -> {
            new TableCellCustom().setVisible(true);
        });
    }
}
