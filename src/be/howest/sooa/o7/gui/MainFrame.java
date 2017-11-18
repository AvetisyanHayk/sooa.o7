package be.howest.sooa.o7.gui;

import be.howest.sooa.o7.data.EncounterRepository;
import be.howest.sooa.o7.data.PokemonRepository;
import be.howest.sooa.o7.domain.Encounter;
import be.howest.sooa.o7.domain.Trainer;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.Window;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.Collections;
import java.util.Set;
import java.util.TreeSet;
import javax.swing.AbstractAction;
import javax.swing.AbstractListModel;
import javax.swing.JComponent;
import javax.swing.JDialog;
import javax.swing.JOptionPane;
import javax.swing.JRootPane;
import javax.swing.KeyStroke;
import javax.swing.ListModel;
import javax.swing.event.ListSelectionEvent;

/**
 *
 * @author Hayk
 */
public class MainFrame extends javax.swing.JFrame {

    private transient PokemonRepository pokemonRepo;
    private transient EncounterRepository encounterRepo;
    private EncounterDialog encounterDialog;
    private transient Trainer trainer;

    /**
     * Creates new form MainFrame
     */
    public MainFrame() {
        initComponents();
    }

    public void confirmAuthentication() {
        pokemonRepo = new PokemonRepository();
        init();
    }

    public void init() {
        encounterDialog = new EncounterDialog(this);
        encounterDialog.loadPokemons(pokemonRepo.findAllWithImagePath(ImageType.GIF));
        centerScreen(encounterDialog);
        addDialogKeyListener(encounterDialog);
        addListeners();
    }

    public Trainer getTrainer() {
        return trainer;
    }

    public void setTrainer(Trainer trainer) {
        this.trainer = trainer;
    }

    public boolean hasTrainer() {
        return trainer != null;
    }

    public void load() {
        if (encounterRepo == null) {
            encounterRepo = new EncounterRepository();
        }
        fillEncounters();
        setTrainerPokemonsImagePath();
    }

    public void fillEncounters() {
        EncountersListModel model = new EncountersListModel();
        encounterRepo.findAll().forEach((encounter) -> {
            model.addEncounter(encounter);
        });
        encountersList.setModel(model);
    }

    // <editor-fold defaultstate="collapsed" desc="Listeners">
    private void addListeners() {
        addAddEncountersButtonActionListener();
        addExitButtonActionListener();
        addTrainerButtonActionListener();
        addEncountersListItemSelectionListener();
        addCatchButtonActionListener();
    }

    private void addAddEncountersButtonActionListener() {
        addEncounterButton.addActionListener((ActionEvent e) -> {
            encounterDialog.setVisible(true);
        });
    }

    private void addExitButtonActionListener() {
        exitButton.addActionListener((ActionEvent e) -> {
            close();
        });
    }

    private void addTrainerButtonActionListener() {
        trainerButton.addActionListener((ActionEvent e) -> {
            TrainerDialog dialog = new TrainerDialog(this);
            centerScreen(dialog);
            addDialogKeyListener(dialog);
            dialog.setVisible(true);
        });
    }

    private void addEncountersListItemSelectionListener() {
        encountersList.addListSelectionListener((ListSelectionEvent e) -> {
            catchButton.setEnabled(!e.getValueIsAdjusting()
                    && encountersList.getSelectedValue() != null);
        });
    }
    
    private void addCatchButtonActionListener() {
        catchButton.addActionListener((ActionEvent e) -> {
            JOptionPane.showMessageDialog(this, "Catch it! (not working yet)");
        });
    }

    public void addDialogKeyListener(JDialog dialog) {
        KeyStroke escapeStroke
                = KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0);
        String dispatchWindowClosingActionMapKey
                = "com.spodding.tackline.dispatch:WINDOW_CLOSING";
        JRootPane root = dialog.getRootPane();
        root.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(
                escapeStroke, dispatchWindowClosingActionMapKey);
        root.getActionMap().put(dispatchWindowClosingActionMapKey,
                new DialogClosingOnEscapeAction(dialog));
    }
    // </editor-fold>
    //
    // <editor-fold defaultstate="collapsed" desc="Data Manipulation">

    // </editor-fold>
    //
    // <editor-fold defaultstate="collapsed" desc="Fill, Clear, Reset, etc.">
    public void addEncounter(Encounter encounter) {
        encounterRepo.save(encounter);
        fillEncounters();
        encounterDialog.setVisible(false);
    }

    public void loadPokemons() {
        encounterDialog.loadPokemons(pokemonRepo.findAllWithImagePath(ImageType.GIF));
    }

    // </editor-fold>
    //
    // <editor-fold defaultstate="collapsed" desc="Custom Functions">
    public void centerScreen(Window window) {
        final Toolkit toolkit = Toolkit.getDefaultToolkit();
        final Dimension screenSize = toolkit.getScreenSize();
        final int x = (screenSize.width - window.getWidth()) / 2;
        final int y = (screenSize.height - window.getHeight()) / 2;
        window.setLocation(x, y);
    }

    private void centerScreen() {
        centerScreen(this);
    }

    private void connectToDatabase() {
        DatabaseConnectionDialog dialog = new DatabaseConnectionDialog(this);
        centerScreen(dialog);
        dialog.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent evt) {
                if (!isConnected()) {
                    close();
                }
            }
        });
        dialog.setVisible(true);
    }

    public void selectTrainer() {
        SelectTrainerDialog dialog = new SelectTrainerDialog(this);
        centerScreen(dialog);
        addDialogKeyListener(dialog);
        dialog.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent evt) {
                if (!hasTrainer()) {
                    close();
                }
            }
        });
        dialog.setVisible(true);
    }

    public void close() {
        setVisible(false);
        dispose();
    }

    private void showWarning(String message) {
        JOptionPane.showMessageDialog(this, message, "Warning",
                JOptionPane.WARNING_MESSAGE);
    }

    public boolean isConnected() {
        return pokemonRepo != null;
    }

    public void setTrainerPokemonsImagePath() {
        if (trainer != null) {
            trainer.setPokemons(PokemonRepository
                    .fillPokemonsWithImagePath(trainer.getPokemons(),
                            ImageType.GIF));
        }
    }

    // </editor-fold>
    //
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        addEncounterButton = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        encountersList = new javax.swing.JList<>();
        exitButton = new javax.swing.JButton();
        trainerButton = new javax.swing.JButton();
        catchButton = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Pokedex");
        setBackground(new java.awt.Color(255, 255, 255));

        addEncounterButton.setText("Add Encounter...");

        encountersList.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        jScrollPane1.setViewportView(encountersList);

        exitButton.setText("Exit");

        trainerButton.setText("Trainer...");

        catchButton.setText("Catch");
        catchButton.setEnabled(false);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 380, Short.MAX_VALUE)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(addEncounterButton)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(trainerButton)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(catchButton)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(exitButton)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 249, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(addEncounterButton)
                    .addComponent(exitButton)
                    .addComponent(trainerButton)
                    .addComponent(catchButton))
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(MainFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(MainFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(MainFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(MainFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(() -> {
            MainFrame newMainFrame = new MainFrame();
            newMainFrame.centerScreen();
            newMainFrame.setVisible(true);
            newMainFrame.connectToDatabase();
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton addEncounterButton;
    private javax.swing.JButton catchButton;
    private javax.swing.JList<Encounter> encountersList;
    private javax.swing.JButton exitButton;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JButton trainerButton;
    // End of variables declaration//GEN-END:variables

    // <editor-fold defaultstate="collapsed" desc="Custom Listeners, Actions, Models">
    private static class DialogClosingOnEscapeAction extends AbstractAction {

        private static final long serialVersionUID = 1L;

        final JDialog dialog;

        DialogClosingOnEscapeAction(JDialog dialog) {
            this.dialog = dialog;
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            dialog.dispatchEvent(new WindowEvent(
                    dialog, WindowEvent.WINDOW_CLOSING));
        }
    }

    private static class EncountersListModel extends AbstractListModel<Encounter> {

        private static final long serialVersionUID = 1L;

        final Set<Encounter> encounters;

        EncountersListModel() {
            this(new TreeSet<>());
        }

        EncountersListModel(Set<Encounter> encounters) {
            super();
            this.encounters = new TreeSet<>();
            if (encounters != null) {
                encounters.forEach((encounter) -> {
                    this.encounters.add(encounter);
                });
            }
        }

        EncountersListModel(ListModel<Encounter> model) {
            super();
            this.encounters = new TreeSet<>();
            if (model != null) {
                for (int i = 0; i < model.getSize(); i++) {
                    encounters.add(model.getElementAt(i));
                }
            }
        }

        @Override
        public int getSize() {
            return encounters.size();
        }

        @Override
        public Encounter getElementAt(int index) {
            return encounters.toArray(new Encounter[getSize()])[index];
        }

        public Set<Encounter> getEncounters() {
            return Collections.unmodifiableSet(encounters);
        }

        public void addEncounter(Encounter encounter) {
            if (encounter != null) {
                encounters.add(encounter);
            }
        }

    }
    // </editor-fold>

}
