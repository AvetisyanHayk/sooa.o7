package be.howest.sooa.o7.gui;

import be.howest.sooa.o7.domain.Trainer;
import java.awt.event.ActionEvent;
import java.io.File;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JDialog;
import javax.swing.JOptionPane;

/**
 *
 * @author Hayk
 */
public class SelectTrainerDialog extends javax.swing.JDialog {

    private final MainFrame frame;
    private final static String TRAINERS_PATH = "trainers";
    private String selectedTrainer;

    public SelectTrainerDialog(MainFrame frame) {
        super(frame, true);
        this.frame = frame;
        initComponents();
        fillTrainersList();
        addListeners();
    }

    private void addListeners() {
        addExitButtonActionListener();
        addEditButtonActionListener();
        addAddButtonActionListener();
        addSelectButtonActionListener();
    }

    private void addExitButtonActionListener() {
        exitButton.addActionListener((ActionEvent e) -> {
            frame.close();
        });
    }

    private void addEditButtonActionListener() {
        editButton.addActionListener((ActionEvent e) -> {
            String trainerName = (String) trainersList.getSelectedItem();
            JDialog dialog = new TrainerDialog(this, trainerName);
            dialog.setTitle("Edit Trainer");
            frame.centerScreen(dialog);
            frame.addDialogKeyListener(dialog);
            dialog.setVisible(true);
        });
    }

    private void addAddButtonActionListener() {
        addButton.addActionListener((ActionEvent e) -> {
            showAddTrainerDialog();
        });
    }

    private void addSelectButtonActionListener() {
        selectButton.addActionListener((ActionEvent e) -> {
            if (trainersList.getModel().getSize() == 0) {
                showAddTrainerDialog();
            } else {
                String name = (String) trainersList.getSelectedItem();
                if (name == null) {
                    showWarning("Select a trainer first, please!");
                } else {
                    Trainer trainer = new Trainer(name);
                    frame.setTrainer(trainer);
                    setVisible(false);
                }
            }
        });
    }

    private void showAddTrainerDialog() {
        JDialog dialog = new TrainerDialog(this);
        dialog.setTitle("Add Trainer");
        frame.centerScreen(dialog);
        frame.addDialogKeyListener(dialog);
        dialog.setVisible(true);
    }

    public boolean saveTrainer(String newTrainer) {
        boolean success = false;
        if (!"".equals(newTrainer)) {
            File trainerDirectory = new File(TRAINERS_PATH + "/" + newTrainer);
            selectedTrainer = newTrainer;
            if (!trainerDirectory.exists()) {
                success = trainerDirectory.mkdir();
                fillTrainersList();
            } else {
                showWarning("Trainer " + newTrainer + " already exists");
            }
            trainersList.setSelectedItem(newTrainer);
        }
        return success;
    }

    public boolean saveTrainer(String oldTrainer, String newTrainer) {
        boolean success = false;
        if (!"".equals(newTrainer) && !"".equals(oldTrainer)) {
            File trainerDirectory = new File(TRAINERS_PATH + "/" + oldTrainer);
            File newTrainerDirectory = new File(TRAINERS_PATH + "/" + newTrainer);
            selectedTrainer = newTrainer;
            if (trainerDirectory.exists() && !newTrainerDirectory.exists()) {
                success = trainerDirectory.renameTo(newTrainerDirectory);
                fillTrainersList();
            } else {
                showWarning("Trainer " + newTrainer + " already exists");
            }
            trainersList.setSelectedItem(newTrainer);
        }
        return success;
    }

    private boolean fillTrainersList() {
        boolean success = false;
        String path = "trainers";
        File trainersDirectory = new File(path);
        if (trainersDirectory.exists()) {
            String[] trainers = trainersDirectory
                    .list((File current, String name1)
                            -> new File(current, name1).isDirectory());
            if (trainers.length != 0) {
                showAddTrainerDialog();
            } else {
                DefaultComboBoxModel model = new DefaultComboBoxModel();
                for (String trainer : trainers) {
                    model.addElement(trainer);
                }
                trainersList.setModel(model);
                String selected = (String) trainersList.getSelectedItem();
                editButton.setEnabled(selected != null && !"".equals(selected));
            }
        } else {
            success = trainersDirectory.mkdir();
            showAddTrainerDialog();
        }
        return success;
    }

    private void showWarning(String message) {
        JOptionPane.showMessageDialog(this, message, "Warning",
                JOptionPane.WARNING_MESSAGE);
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        trainersList = new javax.swing.JComboBox<>();
        trainersLabel = new javax.swing.JLabel();
        editButton = new javax.swing.JButton();
        addButton = new javax.swing.JButton();
        exitButton = new javax.swing.JButton();
        selectButton = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Select Trainer");

        trainersLabel.setText("Trainer");

        editButton.setText("Edit...");

        addButton.setText("Add...");

        exitButton.setText("Exit");

        selectButton.setText("Select Trainer");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(trainersLabel)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addComponent(trainersList, 0, 244, Short.MAX_VALUE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(editButton)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(addButton))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(selectButton)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(exitButton)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(trainersLabel)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(trainersList, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(editButton)
                    .addComponent(addButton))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(exitButton)
                    .addComponent(selectButton))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton addButton;
    private javax.swing.JButton editButton;
    private javax.swing.JButton exitButton;
    private javax.swing.JButton selectButton;
    private javax.swing.JLabel trainersLabel;
    private javax.swing.JComboBox<String> trainersList;
    // End of variables declaration//GEN-END:variables
}
