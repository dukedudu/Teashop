package frontend.ui;

import frontend.controller.Teashop;
import frontend.model.Grocery;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import java.awt.*;
import java.awt.event.*;
import java.sql.Date;

public class GroceryWindow extends JFrame implements ActionListener, MouseListener, ChangeListener {
    private JSplitPane panel;
    private JPanel panel_left, panel_right;
    private GridBagLayout layout_left, layout_right;
    private GridBagConstraints constraints;

    private JTable table;
    private JTableHeader header;
    private DefaultTableModel model_grocery;
    private JLabel label_name, label_amount, label_date, label_duration;
    private JSpinner spin_name, spin_amount, spin_date, spin_duration;
    private SpinnerListModel model_name;
    private SpinnerNumberModel model_amount, model_duration;
    private SpinnerDateModel model_date;
    private JButton button_add, button_update, button_amount, button_date;

    private String[] columns = {"Name", "Amount", "Bought", "Duration", "Expiry"};
    private String[] names = {"Pearl", "Jelly", "Lemon", "Orange"};
    private Object[][] groceries = {};

    public GroceryWindow() { super("Grocery"); }

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

        model_grocery = new DefaultTableModel();
        model_grocery.setDataVector(groceries, columns);
        table = new JTable(model_grocery);
        header = table.getTableHeader();
        header.addMouseListener(this);
        table.setRowSelectionAllowed(true);
        table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        button_amount = new JButton("By Amount");
        button_date = new JButton("By Date");

        label_name = new JLabel("Kind: ");
        model_name = new SpinnerListModel(names);
        spin_name = new JSpinner(model_name);
        label_amount = new JLabel("Amount: ");
        model_amount = new SpinnerNumberModel(0, 0, 100, 1);
        spin_amount = new JSpinner(model_amount);
        label_date = new JLabel("Date: ");
        model_date = new SpinnerDateModel();
        spin_date = new JSpinner(model_date);
        label_duration = new JLabel("Duration: ");
        model_duration = new SpinnerNumberModel();
        spin_duration = new JSpinner(model_duration);
        button_add = new JButton("Add");
        button_update = new JButton("Update");

        panel_left.setLayout(layout_left);
        panel_left.setBorder(BorderFactory.createEmptyBorder(40, 40, 40, 40));
        panel_right.setLayout(layout_right);
        panel_right.setBorder(BorderFactory.createEmptyBorder(40, 40, 40, 40));

        constraints.gridwidth = GridBagConstraints.REMAINDER;
        constraints.insets = new Insets(5, 10, 5, 0);
        layout_left.setConstraints(table, constraints);
        panel_left.add(table);

        constraints.gridwidth = GridBagConstraints.RELATIVE;
        constraints.insets = new Insets(5, 80, 5, 0);
        layout_left.setConstraints(button_amount, constraints);
        panel_left.add(button_amount);

        constraints.gridwidth = GridBagConstraints.REMAINDER;
        constraints.insets = new Insets(5, 10, 5, 0);
        layout_left.setConstraints(button_date, constraints);
        panel_left.add(button_date);

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
        layout_right.setConstraints(label_amount, constraints);
        panel_right.add(label_amount);

        constraints.gridwidth = GridBagConstraints.REMAINDER;
        constraints.insets = new Insets(5, 0, 5, 10);
        layout_right.setConstraints(spin_amount, constraints);
        panel_right.add(spin_amount);

        constraints.gridwidth = GridBagConstraints.RELATIVE;
        constraints.insets = new Insets(5, 10, 5, 0);
        layout_right.setConstraints(label_date, constraints);
        panel_right.add(label_date);

        constraints.gridwidth = GridBagConstraints.REMAINDER;
        constraints.insets = new Insets(5, 0, 5, 10);
        layout_right.setConstraints(spin_date, constraints);
        panel_right.add(spin_date);

        constraints.gridwidth = GridBagConstraints.RELATIVE;
        constraints.insets = new Insets(5, 10, 5, 0);
        layout_right.setConstraints(label_duration, constraints);
        panel_right.add(label_duration);

        constraints.gridwidth = GridBagConstraints.REMAINDER;
        constraints.insets = new Insets(5, 0, 5, 10);
        layout_right.setConstraints(spin_duration, constraints);
        panel_right.add(spin_duration);

        constraints.gridwidth = GridBagConstraints.RELATIVE;
        constraints.insets = new Insets(5, 20, 5, 0);
        layout_right.setConstraints(button_add, constraints);
        panel_right.add(button_add);

        constraints.gridwidth = GridBagConstraints.REMAINDER;
        constraints.insets = new Insets(5, 10, 5, 0);
        layout_right.setConstraints(button_update, constraints);
        panel_right.add(button_update);

        listGrocery(Teashop.getAllGrocery());
        table.addMouseListener(this);
        button_amount.addActionListener(this);
        button_date.addActionListener(this);
        spin_name.addChangeListener(this);
        spin_amount.addChangeListener(this);
        spin_date.addChangeListener(this);
        button_add.addActionListener(this);
        button_update.addActionListener(this);

        this.addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                System.exit(0);
            }
        });
        this.setLocation(700, 450);
        this.setVisible(true);
        this.pack();
    }

    private void listGrocery(Grocery[] data) {
        groceries = new Object[0][0];
        for (int i = 0; i < data.length; i++) {
            groceries[i] = new Object[]{data[i].getName(), data[i].getAmount(), data[i].getBuyingDate(), data[i].getDuration(), data[i].getExpiryDate()};
        }
        model_grocery.setDataVector(groceries, columns);
        model_grocery.fireTableDataChanged();
    }

    @Override
    public void mouseClicked(MouseEvent event) {
        String name = (String)groceries[table.getSelectedRow()][0];
        Grocery grocery = Teashop.getGrocery(name);
        spin_name.setValue(grocery.getName());
        spin_amount.setValue(grocery.getAmount());
        spin_date.setValue(grocery.getBuyingDate());
        spin_duration.setValue(grocery.getDuration());
    }

    @Override
    public void actionPerformed(ActionEvent event) {
        Grocery grocery = new Grocery();
        grocery.setName((String)spin_name.getValue());
        grocery.setAmount((int)spin_amount.getValue());
        grocery.setBuyingDate((Date)spin_date.getValue());
        grocery.setDuration((int)spin_duration.getValue());

        if (event.getSource() == button_add) {
            Teashop.addGrocery(grocery);
        }
        else if (event.getSource() == button_update) {
            Teashop.updateGrocery(grocery);
        }
        else if (event.getSource() == button_date) {
            listGrocery(Teashop.orderGroceryByDate());
        }
        else if (event.getSource() == button_amount) {
            String name = (String)spin_amount.getValue();
            Teashop.getGrocerySum(name);
        }
        else {

        }
    }

    @Override
    public void stateChanged(ChangeEvent e) { }

    @Override
    public void mousePressed(MouseEvent e) { }

    @Override
    public void mouseReleased(MouseEvent e) { }

    @Override
    public void mouseEntered(MouseEvent e) { }

    @Override
    public void mouseExited(MouseEvent e) { }
}
