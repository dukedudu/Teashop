package frontend.ui;

import frontend.controller.Teashop;
import frontend.model.DailyReport;
import frontend.model.ShoppingList;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import java.awt.*;
import java.awt.event.*;
import java.io.IOException;
import java.sql.Date;

public class ReportListWindow extends JFrame implements ActionListener, ChangeListener {
    private JSplitPane panel;
    private JPanel panel_left, panel_right;
    private GridBagLayout layout_left, layout_right;
    private GridBagConstraints constraints;

    private JTable table_report, table_list;
    private JTableHeader header_report, header_list;
    private DefaultTableModel model_report, model_list;
    private JLabel label_name1, label_date1, label_date2, label_name2;
    private JSpinner spin_name1, spin_date1, spin_date2, spin_name2;
    private SpinnerListModel model_name1, model_name2;
    private SpinnerDateModel model_date1, model_date2;
    private JButton button_recipe, button_grocery, button_name1, button_every, button_date, button_name2;

    private String[] col_report = {"Date", "Pearl", "Jelly", "Lemon", "Orange"};
    private String[] col_list = {"Date", "Name", "Amount"};
    private String[] names = {"Pearl", "Jelly", "Lemon", "Orange"};
    private Object[][] reports = {};
    private Object[][] lists = {};

    public ReportListWindow() { super("Daily Report & Shopping List"); }

    public void showFrame() throws IOException {
        panel = new JSplitPane();
        panel_left = new JPanel();
        panel_right = new JPanel();
        this.setContentPane(panel);
        panel.setLeftComponent(panel_left);
        panel.setRightComponent(panel_right);
        layout_left = new GridBagLayout();
        layout_right = new GridBagLayout();
        constraints = new GridBagConstraints();
        panel_left.setBackground(Color.WHITE);
        panel_right.setBackground(Color.WHITE);

        model_report = new DefaultTableModel();
        model_report.setDataVector(reports, col_report);
        table_report = new JTable(model_report);
        table_report.setRowSelectionAllowed(true);
        table_report.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        header_report = table_report.getTableHeader();
        label_name1 = new JLabel("Name: ");
        model_name1 = new SpinnerListModel(names);
        spin_name1 = new JSpinner(model_name1);
        button_recipe = new JButton("Recipe");
        button_grocery = new JButton("Grocery");
        button_name1 = new JButton("By Name");
        button_every = new JButton("With Every");

        model_list = new DefaultTableModel();
        model_list.setDataVector(lists, col_list);
        table_list = new JTable(model_list);
        table_list.setRowSelectionAllowed(true);
        table_list.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        header_list = table_list.getTableHeader();
        label_date1 = new JLabel("From: ");
        model_date1 = new SpinnerDateModel();
        spin_date1 = new JSpinner(model_date1);
        label_date2 = new JLabel("To: ");
        model_date2 = new SpinnerDateModel();
        spin_date2 = new JSpinner(model_date2);
        label_name2 = new JLabel("Name: ");
        model_name2 = new SpinnerListModel(names);
        spin_name2 = new JSpinner(model_name2);
        button_date = new JButton("By Date");
        button_name2 = new JButton("By Name");

        panel_left.setLayout(layout_left);
        panel_left.setBorder(BorderFactory.createEmptyBorder(40, 40, 40, 40));
        panel_right.setLayout(layout_right);
        panel_right.setBorder(BorderFactory.createEmptyBorder(40, 40, 40, 40));

        constraints.gridwidth = GridBagConstraints.RELATIVE;
        constraints.insets = new Insets(5, 80, 5, 0);
        layout_left.setConstraints(button_recipe, constraints);
        panel_left.add(button_recipe);

        constraints.gridwidth = GridBagConstraints.REMAINDER;
        constraints.insets = new Insets(5, 10, 5, 0);
        layout_left.setConstraints(button_grocery, constraints);
        panel_left.add(button_grocery);

        constraints.gridwidth = GridBagConstraints.REMAINDER;
        constraints.insets = new Insets(5, 10, 0, 0);
        layout_left.setConstraints(header_report, constraints);
        panel_left.add(header_report);

        constraints.gridwidth = GridBagConstraints.REMAINDER;
        constraints.insets = new Insets(5, 10, 5, 0);
        layout_left.setConstraints(table_report, constraints);
        panel_left.add(table_report);

        constraints.gridwidth = GridBagConstraints.RELATIVE;
        constraints.insets = new Insets(5, 10, 5, 0);
        layout_left.setConstraints(label_name1, constraints);
        panel_left.add(label_name1);

        constraints.gridwidth = GridBagConstraints.REMAINDER;
        constraints.insets = new Insets(5, 0, 5, 10);
        layout_left.setConstraints(spin_name1, constraints);
        panel_left.add(spin_name1);

        constraints.gridwidth = GridBagConstraints.RELATIVE;
        constraints.insets = new Insets(5, 80, 5, 0);
        layout_left.setConstraints(button_name1, constraints);
        panel_left.add(button_name1);

        constraints.gridwidth = GridBagConstraints.REMAINDER;
        constraints.insets = new Insets(5, 10, 5, 10);
        layout_left.setConstraints(button_every, constraints);
        panel_left.add(button_every);

        constraints.gridwidth = GridBagConstraints.REMAINDER;
        constraints.insets = new Insets(5, 10, 0, 0);
        layout_right.setConstraints(header_list, constraints);
        panel_right.add(header_list);

        constraints.gridwidth = GridBagConstraints.REMAINDER;
        constraints.insets = new Insets(5, 10, 5, 0);
        layout_right.setConstraints(table_list, constraints);
        panel_right.add(table_list);

        constraints.gridwidth = GridBagConstraints.RELATIVE;
        constraints.insets = new Insets(5, 10, 5, 0);
        layout_right.setConstraints(label_date1, constraints);
        panel_right.add(label_date1);

        constraints.gridwidth = GridBagConstraints.REMAINDER;
        constraints.insets = new Insets(5, 0, 5, 10);
        layout_right.setConstraints(spin_date1, constraints);
        panel_right.add(spin_date1);

        constraints.gridwidth = GridBagConstraints.RELATIVE;
        constraints.insets = new Insets(5, 10, 5, 0);
        layout_right.setConstraints(label_date2, constraints);
        panel_right.add(label_date2);

        constraints.gridwidth = GridBagConstraints.REMAINDER;
        constraints.insets = new Insets(5, 0, 5, 10);
        layout_right.setConstraints(spin_date2, constraints);
        panel_right.add(spin_date2);

        constraints.gridwidth = GridBagConstraints.RELATIVE;
        constraints.insets = new Insets(5, 10, 5, 0);
        layout_right.setConstraints(label_name2, constraints);
        panel_right.add(label_name2);

        constraints.gridwidth = GridBagConstraints.REMAINDER;
        constraints.insets = new Insets(5, 0, 5, 10);
        layout_right.setConstraints(spin_name2, constraints);
        panel_right.add(spin_name2);

        constraints.gridwidth = GridBagConstraints.RELATIVE;
        constraints.insets = new Insets(5, 40, 5, 0);
        layout_right.setConstraints(button_name2, constraints);
        panel_right.add(button_name2);

        constraints.gridwidth = GridBagConstraints.REMAINDER;
        constraints.insets = new Insets(5, 10, 5, 10);
        layout_right.setConstraints(button_date, constraints);
        panel_right.add(button_date);

        listReport(Teashop.getAllReport());
        listList(Teashop.getAllList());
        spin_name1.addChangeListener(this);
        button_name1.addActionListener(this);
        button_every.addActionListener(this);
        button_recipe.addActionListener(this);
        button_grocery.addActionListener(this);

        spin_name2.addChangeListener(this);
        button_name2.addActionListener(this);
        spin_date1.addChangeListener(this);
        spin_date2.addChangeListener(this);
        button_date.addActionListener(this);

        panel_left.setBackground(Color.WHITE);
        panel_right.setBackground(Color.WHITE);
        Image image = ImageIO.read(this.getClass().getResource("/resources/icon.png"));
        this.setIconImage(new ImageIcon(image).getImage());
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.pack();
        this.setVisible(true);
        this.setLocationRelativeTo(null);
    }

    private void listReport(DailyReport[] data) {
        reports = new Object[data.length][5];
        for (int i = 0; i < data.length; i++) {
            reports[i] = new Object[]{data[i].getDate(), data[i].getPearl(), data[i].getJelly(), data[i].getLemon(), data[i].getOrange()};
        }
        model_report.setDataVector(reports, col_report);
        model_report.fireTableDataChanged();
    }

    private void listList(ShoppingList[] data) {
        lists = new Object[data.length][3];
        for (int i = 0; i < data.length; i++) {
            lists[i] = new Object[]{data[i].getDate(), data[i].getGname(), data[i].getAmount()};
        }
        model_list.setDataVector(lists, col_list);
        model_list.fireTableDataChanged();
    }

    @Override
    public void actionPerformed(ActionEvent event) {
        if (event.getSource() == button_recipe) {
            this.dispose();
            RecipeWindow window = new RecipeWindow();
            try {
                window.showFrame();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        else if (event.getSource() == button_grocery) {
            this.dispose();
            GroceryWindow window = new GroceryWindow();
            try {
                window.showFrame();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        else if (event.getSource() == button_name1) {
            String name = (String)spin_name1.getValue();
            listReport(Teashop.getReportByName(name));
        }
        else if (event.getSource() == button_every) {
            listReport(Teashop.getReportWithEvery());
        }
        else if (event.getSource() == button_date) {
            java.util.Date date1 = (java.util.Date)spin_date1.getValue();
            java.util.Date date2 = (java.util.Date)spin_date2.getValue();
            listList(Teashop.getListByDate(new Date(date1.getTime()), new Date(date2.getTime())));
        }
        else {
            String name = (String)spin_name2.getValue();
            listList(Teashop.getListByName(name));
        }
    }

    @Override
    public void stateChanged(ChangeEvent e) { }
}