package frontend.ui;

import frontend.controller.Teashop;
import frontend.model.Recipe;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import java.awt.*;
import java.awt.event.*;

public class RecipeWindow extends JFrame implements ActionListener, MouseListener, ChangeListener {
    private JSplitPane panel;
    private JPanel panel_left, panel_right;
    private GridBagLayout layout_left, layout_right;
    private GridBagConstraints constraints;

    private JTable table;
    private JTableHeader header;
    private DefaultTableModel model_recipe;
    private JTextField field;
    private JLabel label_name, label_pearl, label_jelly, label_lemon, label_orange, label_kind;
    private JSpinner spin_pearl, spin_jelly, spin_lemon, spin_orange, spin_kind;
    private SpinnerNumberModel model_pearl, model_jelly, model_lemon, model_orange;
    private SpinnerListModel model_kind;
    private JButton button_add, button_update, button_delete, button_make;

    private String[] columns = {"Name", "Calories"};
    private Object[][] recipes = {{"Red tea", 0}, {"Green tea", 0}, {"Bubble tea", 100}, {"Lemon tea", 50}};
    private String[] teas = {"Red tea", "Green tea"};
    private String[] kinds = {"Milk tea", "Fruit tea"};

    public RecipeWindow() { super("Recipe"); }

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

        model_recipe = new DefaultTableModel();
        model_recipe.setDataVector(recipes, columns);
        table = new JTable(model_recipe);
        header = table.getTableHeader();
        header.addMouseListener(this);
        table.setRowSelectionAllowed(true);
        table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        label_kind = new JLabel("Kind: ");
        model_kind = new SpinnerListModel(kinds);
        spin_kind = new JSpinner(model_kind);
        button_make = new JButton("Make");

        label_name = new JLabel("Name: ");
        field = new JTextField(10);
//        model_tea = new SpinnerListModel(teas);
//        label_tea = new JLabel("Tea: ");
//        spin_tea = new JSpinner(model_tea);
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
//        button_delete = new JButton("Delete");

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
        layout_left.setConstraints(label_kind, constraints);
        panel_left.add(label_kind);

        constraints.gridwidth = GridBagConstraints.REMAINDER;
        constraints.insets = new Insets(5, 10, 5, 0);
        layout_left.setConstraints(spin_kind, constraints);
        panel_left.add(spin_kind);

        constraints.gridwidth = GridBagConstraints.REMAINDER;
        constraints.insets = new Insets(5, 10, 5, 0);
        layout_left.setConstraints(button_make, constraints);
        panel_left.add(button_make);

        constraints.gridwidth = GridBagConstraints.RELATIVE;
        constraints.insets = new Insets(5, 10, 5, 0);
        layout_right.setConstraints(label_name, constraints);
        panel_right.add(label_name);

        constraints.gridwidth = GridBagConstraints.REMAINDER;
        constraints.insets = new Insets(5, 0, 5, 10);
        layout_right.setConstraints(field, constraints);
        panel_right.add(field);

//        constraints.gridwidth = GridBagConstraints.RELATIVE;
//        constraints.insets = new Insets(5, 10, 5, 0);
//        layout_right.setConstraints(label_tea, constraints);
//        panel_right.add(label_tea);
//
//        constraints.gridwidth = GridBagConstraints.REMAINDER;
//        constraints.insets = new Insets(5, 10, 5, 0);
//        layout_right.setConstraints(spin_tea, constraints);
//        panel_right.add(spin_tea);

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

        constraints.gridwidth = GridBagConstraints.RELATIVE;
        constraints.insets = new Insets(5, 10, 5, 0);
        layout_right.setConstraints(button_add, constraints);
        panel_right.add(button_add);

        constraints.gridwidth = GridBagConstraints.REMAINDER;
        constraints.insets = new Insets(5, 10, 5, 0);
        layout_right.setConstraints(button_update, constraints);
        panel_right.add(button_update);

//        constraints.gridwidth = GridBagConstraints.REMAINDER;
//        constraints.insets = new Insets(5, 10, 5, 0);
//        layout_right.setConstraints(button_delete, constraints);
//        panel_right.add(button_delete);

        //listAllRecipe(Teashop.getAllRecipe());
//        model_recipe.addTableModelListener(this);
//        spin_tea.addChangeListener(this);
        spin_pearl.addChangeListener(this);
        spin_jelly.addChangeListener(this);
        spin_lemon.addChangeListener(this);
        spin_orange.addChangeListener(this);

        listRecipe(Teashop.getAllRecipe());
        table.addMouseListener(this);
        button_make.addActionListener(this);
        spin_kind.addChangeListener(this);
        button_add.addActionListener(this);
        button_update.addActionListener(this);
//        button_delete.addActionListener(this);

        this.addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                System.exit(0);
            }
        });
        this.setLocation(700, 400);
        this.setVisible(true);
        this.pack();
    }

    private void listRecipe(Recipe[] data) {
        recipes = new Object[0][0];
        for (int i = 0; i < data.length; i++) {
            recipes[i] = new Object[]{data[i].getName(), data[i].getCalories()};
        }
    }

    @Override
    public void mouseClicked(MouseEvent event) {
        String name = (String)recipes[table.getSelectedRow()][0];
        Recipe recipe = Teashop.getRecipeByName(name);
        field.setText(recipe.getName());
        field.disable();
//        spin_tea.setValue(recipe.getTea());
        spin_pearl.setValue(recipe.getPearl());
        spin_jelly.setValue(recipe.getJelly());
        spin_lemon.setValue(recipe.getLemon());
        spin_orange.setValue(recipe.getOrange());
    }

    @Override
    public void stateChanged(ChangeEvent event) {
        Recipe[] data = Teashop.getRecipeByKind((String)spin_kind.getValue());
        listRecipe(data);
    }

    @Override
    public void actionPerformed(ActionEvent event) {
        Recipe recipe = new Recipe();
        recipe.setName(field.getText());
//        recipe.setTea((String)spin_tea.getValue());
        recipe.setPearl((int)spin_pearl.getValue());
        recipe.setJelly((int)spin_jelly.getValue());
        recipe.setLemon((int)spin_lemon.getValue());
        recipe.setOrange((int)spin_orange.getValue());

        if (event.getSource() == button_add) {
            Teashop.addRecipe(recipe);
        }
        else if (event.getSource() == button_update){
            Teashop.updateRecipe(recipe);
        }
//        else if (event.getSource() == button_delete){
//            Teashop.deleteRecipe(recipe.getName());
//        }
        else {
            Teashop.makeRecipe(recipe.getName());
        }
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
