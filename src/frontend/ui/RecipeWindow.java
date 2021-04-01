package frontend.ui;

import frontend.controller.Teashop;
import frontend.model.Recipe;
import frontend.model.User;

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

public class RecipeWindow extends JFrame implements ActionListener, MouseListener, ChangeListener {
    private JSplitPane panel;
    private JPanel panel_left, panel_right;
    private GridBagLayout layout_left, layout_right;
    private GridBagConstraints constraints;

    private JTable table;
    private JTableHeader header;
    private DefaultTableModel model_recipe;
    private JTextField field;
    private JLabel label_name, label_pearl, label_jelly, label_lemon, label_orange, label_make;
    private JSpinner spin_pearl, spin_jelly, spin_lemon, spin_orange;
    private SpinnerNumberModel model_pearl, model_jelly, model_lemon, model_orange;
    private JButton button_grocery, button_report, button_make, button_my, button_add, button_update;

    private String[] columns = {"Name", "Pearl", "Jelly", "Lemon", "Orange"};
    private Object[][] recipes = {};

    public RecipeWindow() { super("Recipe"); }

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

        model_recipe = new DefaultTableModel();
        model_recipe.setDataVector(recipes, columns);
        table = new JTable(model_recipe);
        header = table.getTableHeader();
        header.addMouseListener(this);
        table.setRowSelectionAllowed(true);
        table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        button_grocery = new JButton("Grocery");
        button_report = new JButton("Report");
        button_make = new JButton("Make This");
        button_my = new JButton("My Recipes");
        label_make = new JLabel();

        label_name = new JLabel("Name: ");
        field = new JTextField(10);
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

        constraints.gridwidth = GridBagConstraints.RELATIVE;
        constraints.insets = new Insets(5, 80, 5, 0);
        layout_left.setConstraints(button_grocery, constraints);
        panel_left.add(button_grocery);

        constraints.gridwidth = GridBagConstraints.REMAINDER;
        constraints.insets = new Insets(5, 10, 5, 0);
        layout_left.setConstraints(button_report, constraints);
        panel_left.add(button_report);

        constraints.gridwidth = GridBagConstraints.REMAINDER;
        constraints.insets = new Insets(5, 10, 0, 0);
        layout_left.setConstraints(header, constraints);
        panel_left.add(header);

        constraints.gridwidth = GridBagConstraints.REMAINDER;
        constraints.insets = new Insets(5, 10, 5, 0);
        layout_left.setConstraints(table, constraints);
        panel_left.add(table);

        constraints.gridwidth = GridBagConstraints.RELATIVE;
        constraints.insets = new Insets(5, 80, 5, 0);
        layout_left.setConstraints(button_make, constraints);
        panel_left.add(button_make);

        constraints.gridwidth = GridBagConstraints.REMAINDER;
        constraints.insets = new Insets(5, 10, 5, 0);
        layout_left.setConstraints(button_my, constraints);
        panel_left.add(button_my);

        constraints.gridwidth = GridBagConstraints.REMAINDER;
        constraints.insets = new Insets(5, 10, 5, 0);
        layout_left.setConstraints(label_make, constraints);
        panel_left.add(label_make);

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
        constraints.insets = new Insets(5, 40, 5, 0);
        layout_right.setConstraints(button_add, constraints);
        panel_right.add(button_add);

        constraints.gridwidth = GridBagConstraints.REMAINDER;
        constraints.insets = new Insets(5, 10, 5, 0);
        layout_right.setConstraints(button_update, constraints);
        panel_right.add(button_update);

        listRecipe(Teashop.getAllRecipe());
        table.addMouseListener(this);
        button_make.addActionListener(this);
        button_my.addActionListener(this);
        button_grocery.addActionListener(this);
        button_report.addActionListener(this);

        spin_pearl.addChangeListener(this);
        spin_jelly.addChangeListener(this);
        spin_lemon.addChangeListener(this);
        spin_orange.addChangeListener(this);
//        spin_kind.addChangeListener(this);
        button_add.addActionListener(this);
        button_update.addActionListener(this);

        panel_left.setBackground(Color.WHITE);
        panel_right.setBackground(Color.WHITE);
        Image image = ImageIO.read(this.getClass().getResource("/resources/icon.png"));
        this.setIconImage(new ImageIcon(image).getImage());
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.pack();
        this.setVisible(true);
        this.setLocationRelativeTo(null);
    }

    private void listRecipe(Recipe[] data) {
        recipes = new Object[data.length][5];
        for (int i = 0; i < data.length; i++) {
            recipes[i] = new Object[]{data[i].getName(), data[i].getPearl(), data[i].getJelly(), data[i].getLemon(), data[i].getOrange()};
        }
        model_recipe.setDataVector(recipes, columns);
        model_recipe.fireTableDataChanged();
    }

    @Override
    public void mouseClicked(MouseEvent event) {
        String name = (String)recipes[table.getSelectedRow()][0];
        Recipe recipe = Teashop.getRecipeByName(name);
        field.setText(recipe.getName());
        field.disable();
        spin_pearl.setValue(recipe.getPearl());
        spin_jelly.setValue(recipe.getJelly());
        spin_lemon.setValue(recipe.getLemon());
        spin_orange.setValue(recipe.getOrange());
    }

    @Override
    public void actionPerformed(ActionEvent event) {
        String name = field.getText();
        int pearl = (int)spin_pearl.getValue();
        int jelly = (int)spin_jelly.getValue();
        int lemon = (int)spin_lemon.getValue();
        int orange = (int)spin_orange.getValue();
        Recipe recipe = new Recipe(name, pearl, jelly, lemon, orange);

        if (event.getSource() == button_grocery) {
            this.dispose();
            GroceryWindow window = new GroceryWindow();
            try {
                window.showFrame();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        else if (event.getSource() == button_report) {
            this.dispose();
            ReportListWindow window = new ReportListWindow();
            try {
                window.showFrame();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        else if (event.getSource() == button_make) {
            java.util.Date date = new java.util.Date();
//            Teashop.makeRecipe(recipe, new Date(date.getTime()));
            if (Teashop.makeRecipe(recipe, new Date(date.getTime()))) {
                label_make.setText("Success! Please enjoy your tea!");
            } else {
                label_make.setText("Added grocery to shopping list");
            }
        }
        else if (event.getSource() == button_my) {
            User user = LoginWindow.getUser();
            listRecipe(Teashop.getMyRecipe(user.getName()));
        }
        else if (event.getSource() == button_add) {
            Teashop.addRecipe(recipe);
            listRecipe(Teashop.getAllRecipe());
        }
        else {
            Teashop.updateRecipe(recipe);
            listRecipe(Teashop.getAllRecipe());
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

    @Override
    public void stateChanged(ChangeEvent e) { }
}
