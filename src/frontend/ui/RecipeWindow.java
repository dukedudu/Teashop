package frontend.ui;

import frontend.controller.Teashop;
import frontend.model.Recipe;

import javax.swing.*;
import javax.swing.event.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class RecipeWindow extends JFrame implements ActionListener, TableModelListener, ChangeListener {
    private Recipe recipe;
    private JSplitPane panel;
    private JPanel panel_left, panel_right;
    private GridBagLayout layout_left, layout_right;
    private GridBagConstraints constraints;
    private JTable table;
    private DefaultTableModel model_recipe;
    private JTextField field;
    private SpinnerListModel model_tea;
    private JLabel label_name, label_tea, label_pearl, label_jelly, label_lemon, label_orange;
    private SpinnerNumberModel model_pearl, model_jelly, model_lemon, model_orange;
    private JSpinner spin_tea, spin_pearl, spin_jelly, spin_lemon, spin_orange;
    private JButton button_add, button_update;
    private String[] columns = {"Name"};
    private Object[][] recipes = {{"Bubble tea"}, {"Fruit tea"}};
    private String[] teas = {"Red tea", "Green tea"};

    public RecipeWindow() { super("Recipe"); };

    public void showFrame() {
        recipe = new Recipe();
        panel = new JSplitPane();
        panel_left = new JPanel();
        panel_right = new JPanel();
        this.setContentPane(panel);
        panel.setLeftComponent(panel_left);
        panel.setRightComponent(panel_right);
        layout_left = new GridBagLayout();
        layout_right = new GridBagLayout();
        constraints = new GridBagConstraints();

        model_recipe = new DefaultTableModel();
        model_recipe.setDataVector(recipes, columns);
        table = new JTable(model_recipe);
        table.setRowSelectionAllowed(true);
        table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        label_name = new JLabel("Name: ");
        field = new JTextField(10);
        model_tea = new SpinnerListModel(teas);
        label_tea = new JLabel("Tea: ");
        spin_tea = new JSpinner(model_tea);
        model_pearl = new SpinnerNumberModel(0, 0, 50, 1);
        model_jelly = new SpinnerNumberModel(0, 0, 50, 1);
        model_lemon = new SpinnerNumberModel(0, 0, 50, 1);
        model_orange = new SpinnerNumberModel(0, 0, 50, 1);
        label_pearl = new JLabel("Pearl: ");
        spin_pearl = new JSpinner(model_pearl);
        label_jelly = new JLabel("Jelly: ");
        spin_jelly = new JSpinner(model_jelly);
        label_lemon = new JLabel("Lemon: ");
        spin_lemon = new JSpinner(model_lemon);
        label_orange = new JLabel("Orange: ");
        spin_orange = new JSpinner(model_orange);
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
        constraints.insets = new Insets(5, 10, 5, 0);
        layout_right.setConstraints(label_name, constraints);
        panel_right.add(label_name);

        constraints.gridwidth = GridBagConstraints.REMAINDER;
        constraints.insets = new Insets(5, 0, 5, 10);
        layout_right.setConstraints(field, constraints);
        panel_right.add(field);

        constraints.gridwidth = GridBagConstraints.RELATIVE;
        constraints.insets = new Insets(5, 10, 5, 0);
        layout_right.setConstraints(label_tea, constraints);
        panel_right.add(label_tea);

        constraints.gridwidth = GridBagConstraints.REMAINDER;
        constraints.insets = new Insets(5, 10, 5, 0);
        layout_right.setConstraints(spin_tea, constraints);
        panel_right.add(spin_tea);

        constraints.gridwidth = GridBagConstraints.RELATIVE;
        constraints.insets = new Insets(5, 10, 5, 0);
        layout_right.setConstraints(label_pearl, constraints);
        panel_right.add(label_pearl);

        constraints.gridwidth = GridBagConstraints.REMAINDER;
        constraints.insets = new Insets(5, 0, 5, 10);
        layout_right.setConstraints(spin_pearl, constraints);
        panel_right.add(spin_pearl);

        constraints.gridwidth = GridBagConstraints.RELATIVE;
        constraints.insets = new Insets(5, 10, 5, 0);
        layout_right.setConstraints(label_jelly, constraints);
        panel_right.add(label_jelly);

        constraints.gridwidth = GridBagConstraints.REMAINDER;
        constraints.insets = new Insets(5, 0, 5, 10);
        layout_right.setConstraints(spin_jelly, constraints);
        panel_right.add(spin_jelly);

        constraints.gridwidth = GridBagConstraints.RELATIVE;
        constraints.insets = new Insets(5, 10, 5, 0);
        layout_right.setConstraints(label_lemon, constraints);
        panel_right.add(label_lemon);

        constraints.gridwidth = GridBagConstraints.REMAINDER;
        constraints.insets = new Insets(5, 0, 5, 10);
        layout_right.setConstraints(spin_lemon, constraints);
        panel_right.add(spin_lemon);

        constraints.gridwidth = GridBagConstraints.RELATIVE;
        constraints.insets = new Insets(5, 10, 5, 0);
        layout_right.setConstraints(label_orange, constraints);
        panel_right.add(label_orange);

        constraints.gridwidth = GridBagConstraints.REMAINDER;
        constraints.insets = new Insets(5, 0, 5, 10);
        layout_right.setConstraints(spin_orange, constraints);
        panel_right.add(spin_orange);

        constraints.gridwidth = GridBagConstraints.REMAINDER;
        constraints.insets = new Insets(5, 10, 5, 0);
        layout_right.setConstraints(button_add, constraints);
        panel_right.add(button_add);

        constraints.gridwidth = GridBagConstraints.REMAINDER;
        constraints.insets = new Insets(5, 10, 5, 0);
        layout_right.setConstraints(button_update, constraints);
        panel_right.add(button_update);

        //listAllRecipe(Teashop.getAllRecipe());
        model_recipe.addTableModelListener(this);
        spin_tea.addChangeListener(this);
        spin_pearl.addChangeListener(this);
        spin_jelly.addChangeListener(this);
        spin_lemon.addChangeListener(this);
        spin_orange.addChangeListener(this);
        this.addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                System.exit(0);
            }
        });
        this.setLocation(850, 450);
        this.setVisible(true);
        this.pack();
    }

    @Override
    public void tableChanged(TableModelEvent event) {
        int id = (int)recipes[table.getSelectedRow()][0];
    }

    private void listAllRecipe(Recipe[] data) {
        for (int i = 0; i < data.length; i++) {
            recipes[i] = new Object[]{data[i].getName()};
        }
    }

    @Override
    public void actionPerformed(ActionEvent event) {
        recipe.setName(field.getText());
        Teashop.addRecipe(recipe);
    }

    @Override
    public void stateChanged(ChangeEvent event) {
        if (event.getSource() == spin_tea) {
            recipe.setTea((String)spin_tea.getValue());
        }
        if (event.getSource() == spin_pearl) {
            recipe.setPearl((int)spin_pearl.getValue());
        }
        if (event.getSource() == spin_jelly) {
            recipe.setJelly((int)spin_jelly.getValue());
        }
        if (event.getSource() == spin_lemon) {
            recipe.setLemon((int)spin_lemon.getValue());
        }
        if (event.getSource() == spin_orange) {
            recipe.setOrange((int)spin_orange.getValue());
        }
    }
}
