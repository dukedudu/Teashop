package frontend.ui;

import frontend.model.Recipe;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class RecipeWindow extends JFrame implements ActionListener {
    private Recipe recipe;
    private JSplitPane panel;
    private JPanel panel_left, panel_right;
    private GridBagLayout layout_left, layout_right;
    private GridBagConstraints constraints;
    private DefaultListModel<String> model_recipe;
    private JList<String> list;
    private JLabel label_pearl, label_jelly, label_lemon, label_orange;
    private SpinnerListModel model_tea;
    private SpinnerNumberModel model_pearl, model_jelly, model_lemon, model_orange;
    private JSpinner spin_tea, spin_pearl, spin_jelly, spin_lemon, spin_orange;
    private JButton button;
    private String[] teas = {"Red tea", "Green tea"};

    public RecipeWindow() { super("Recipe"); };

    public void showFrame() {
        panel = new JSplitPane();
        panel_left = new JPanel();
        panel_right = new JPanel();
        this.setContentPane(panel);
        panel.setDividerLocation(getWidth()/2);
        panel.setLeftComponent(panel_left);
        panel.setRightComponent(panel_right);
        layout_left = new GridBagLayout();
        layout_right = new GridBagLayout();
        constraints = new GridBagConstraints();

        model_recipe = new DefaultListModel<>();
        model_recipe.addElement("Bubble tea");
        model_recipe.addElement("Fruit tea");
        list = new JList<>(model_recipe);
        model_tea = new SpinnerListModel(teas);
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
        button = new JButton("Add");

        panel_left.setLayout(layout_left);
        panel_left.setBorder(BorderFactory.createEmptyBorder(40, 40, 40, 40));
        panel_right.setLayout(layout_right);
        panel_right.setBorder(BorderFactory.createEmptyBorder(40, 40, 40, 40));

        constraints.gridwidth = GridBagConstraints.RELATIVE;
        constraints.insets = new Insets(5, 10, 5, 0);
        layout_left.setConstraints(list, constraints);
        panel_left.add(list);

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
        layout_right.setConstraints(spin_tea, constraints);
        panel_right.add(spin_tea);

        constraints.gridwidth = GridBagConstraints.RELATIVE;
        constraints.insets = new Insets(5, 10, 5, 0);
        layout_right.setConstraints(button, constraints);
        panel_right.add(button);

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
    public void actionPerformed(ActionEvent event) {
        recipe = new Recipe();

    }
}
