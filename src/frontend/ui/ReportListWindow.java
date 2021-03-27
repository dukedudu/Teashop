package frontend.ui;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import java.awt.*;
import java.awt.event.*;
import java.sql.Date;

public class ReportListWindow extends JFrame implements ActionListener, MouseListener{
    private JSplitPane panel;
    private JPanel panel_left, panel_right;
    private GridBagLayout layout_left, layout_right;
    private GridBagConstraints constraints;

    private JTable table_report, table_list;
    private JTableHeader header_report, header_list;
    private DefaultTableModel model_report, model_list;
    private JLabel label_date1, label_date2, label_name;
    private JSpinner spin_date1, spin_date2, spin_name;
    private SpinnerDateModel model_date1, model_date2;
    private SpinnerListModel model_name;
    private JButton button_date, button_every, button_name, button_sum;

    private String[] columns_report = {"Date", "Pearl", "Jelly", "Lemon", "Orange"};
    private String[] columns_list = {"Date", "Name", "Amount"};
    private Date date = new Date(new java.util.Date().getTime());
    private Object[][] reports = {{date, 100, 100, 50, 50}, {date, 200, 100, 100, 50}, {date, 100, 200, 50, 100}, {date, 100, 100, 50, 0}};
    private Object[][] lists = {{date, "Pearl", 100}, {date, "Jelly", 50}, {date, "Lemon", 100}, {date, "Orange", 50}};
    private String[] names = {"Pearl", "Jelly", "Lemon", "Orange"};

    public ReportListWindow() { super("Daily Report & Shopping List"); }

    public void showFrame() {
        panel = new JSplitPane();
        panel_left = new JPanel();
        panel_right = new JPanel();
        this.setContentPane(panel);
        panel.setLeftComponent(panel_left);
        panel.setRightComponent(panel_right);
        layout_left = new GridBagLayout();
        layout_right = new GridBagLayout();
        constraints = new GridBagConstraints();

        model_report = new DefaultTableModel();
        model_report.setDataVector(reports, columns_report);
        table_report = new JTable(model_report);
        header_report = table_report.getTableHeader();
        header_report.addMouseListener(this);
        table_report.setRowSelectionAllowed(true);
        table_report.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        label_date1 = new JLabel("From: ");
        model_date1 = new SpinnerDateModel();
        spin_date1 = new JSpinner(model_date1);
        label_date2 = new JLabel("To: ");
        model_date2 = new SpinnerDateModel();
        spin_date2 = new JSpinner(model_date2);
        button_date = new JButton("By Date");
        button_every = new JButton("Every");

        model_list = new DefaultTableModel();
        model_list.setDataVector(lists, columns_list);
        table_list = new JTable(model_list);
        header_list = table_list.getTableHeader();
        header_list.addMouseListener(this);
        table_list.setRowSelectionAllowed(true);
        table_list.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        label_name = new JLabel("Name: ");
        model_name = new SpinnerListModel(names);
        spin_name = new JSpinner(model_name);
        button_name = new JButton("By Name");
        button_sum = new JButton("Sum");

        panel_left.setLayout(layout_left);
        panel_left.setBorder(BorderFactory.createEmptyBorder(40, 40, 40, 40));
        panel_right.setLayout(layout_right);
        panel_right.setBorder(BorderFactory.createEmptyBorder(40, 40, 40, 40));

        constraints.gridwidth = GridBagConstraints.REMAINDER;
        constraints.insets = new Insets(5, 10, 5, 0);
        layout_left.setConstraints(table_report, constraints);
        panel_left.add(table_report);

        constraints.gridwidth = GridBagConstraints.RELATIVE;
        constraints.insets = new Insets(5, 10, 5, 0);
        layout_left.setConstraints(label_date1, constraints);
        panel_left.add(label_date1);

        constraints.gridwidth = GridBagConstraints.REMAINDER;
        constraints.insets = new Insets(5, 0, 5, 10);
        layout_left.setConstraints(spin_date1, constraints);
        panel_left.add(spin_date1);

        constraints.gridwidth = GridBagConstraints.RELATIVE;
        constraints.insets = new Insets(5, 10, 5, 0);
        layout_left.setConstraints(label_date2, constraints);
        panel_left.add(label_date2);

        constraints.gridwidth = GridBagConstraints.REMAINDER;
        constraints.insets = new Insets(5, 0, 5, 10);
        layout_left.setConstraints(spin_date2, constraints);
        panel_left.add(spin_date2);

        constraints.gridwidth = GridBagConstraints.RELATIVE;
        constraints.insets = new Insets(5, 100, 5, 10);
        layout_left.setConstraints(button_date, constraints);
        panel_left.add(button_date);

        constraints.gridwidth = GridBagConstraints.REMAINDER;
        constraints.insets = new Insets(5, 0, 5, 10);
        layout_left.setConstraints(button_every, constraints);
        panel_left.add(button_every);

        constraints.gridwidth = GridBagConstraints.REMAINDER;
        constraints.insets = new Insets(5, 10, 5, 0);
        layout_right.setConstraints(table_list, constraints);
        panel_right.add(table_list);

        constraints.gridwidth = GridBagConstraints.RELATIVE;
        constraints.insets = new Insets(5, 10, 5, 0);
        layout_right.setConstraints(label_name, constraints);
        panel_right.add(label_name);

        constraints.gridwidth = GridBagConstraints.REMAINDER;
        constraints.insets = new Insets(5, 0, 5, 10);
        layout_right.setConstraints(spin_name, constraints);
        panel_right.add(spin_name);

        constraints.gridwidth = GridBagConstraints.RELATIVE;
        constraints.insets = new Insets(5, 10, 5, 0);
        layout_right.setConstraints(button_name, constraints);
        panel_right.add(button_name);

        constraints.gridwidth = GridBagConstraints.REMAINDER;
        constraints.insets = new Insets(5, 10, 5, 0);
        layout_right.setConstraints(button_sum, constraints);
        panel_right.add(button_sum);

        this.addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                System.exit(0);
            }
        });
        this.setLocation(700, 450);
        this.setVisible(true);
        this.pack();
    }

    @Override
    public void mouseClicked(MouseEvent e) {

    }

    @Override
    public void actionPerformed(ActionEvent e) {

    }


    @Override
    public void mousePressed(MouseEvent e) { }

    @Override
    public void mouseReleased(MouseEvent e) { }

    @Override
    public void mouseEntered(MouseEvent e) { }

    @Override
    public void mouseExited(MouseEvent e) { }
}
