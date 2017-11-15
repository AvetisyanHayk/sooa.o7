package be.howest.sooa.o7.gui;

import be.howest.sooa.o7.domain.EncounterValidation;
import be.howest.sooa.o7.domain.Pokemon;
import java.awt.event.ActionEvent;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.util.List;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JOptionPane;

/**
 *
 * @author Hayk
 */
public class EncounterDialog extends javax.swing.JDialog {

    private final MainFrame frame;
    private ImagePanel imagePanel;

    public EncounterDialog(MainFrame frame) {
        super(frame, true);
        this.frame = frame;
        initComponents();
        addListeners();
    }

    private void addListeners() {
        addAddButtonActionListener();
        addCloseButtonActionListener();
        addEnterKeyActionListeners();
        addPokemonsItemListener();
        addWithImageCheckBoxListener();
    }

    private void addAddButtonActionListener() {
        addButton.addActionListener((ActionEvent e) -> {
            EncounterValidation validation = getEncounterValidation();
            if (validation.isValid()) {
                frame.addEncounter(validation.getEncounter());
            } else {
                showWarning(validation.getMessage());
            }
        });
    }

    private EncounterValidation getEncounterValidation() {
        Pokemon pokemon = (Pokemon) pokemonsList.getSelectedItem();
        String x = locationXField.getText().trim();
        String y = locationYField.getText().trim();
        return new EncounterValidation(
                pokemon, x, y);
    }

    private void addCloseButtonActionListener() {
        closeButton.addActionListener((ActionEvent e) -> {
            setVisible(false);
        });
    }

    private void addEnterKeyActionListeners() {
        locationXField.addActionListener((ActionEvent e) -> {
            addButton.doClick();
        });
        locationYField.addActionListener((ActionEvent e) -> {
            addButton.doClick();
        });
    }

    private void addPokemonsItemListener() {
        pokemonsList.addItemListener(new PokemonListItemListener(this));
    }
    
    private void addWithImageCheckBoxListener() {
        withImageCheckBox.addItemListener((ItemEvent e) -> {
            if (e.getStateChange() == ItemEvent.SELECTED) {
                reloadPokemons();
            }
        });
    }
    
    private void reloadPokemons() {
        frame.loadPokemons();
    }

    public void loadPokemons(List<Pokemon> pokemons) {
        DefaultComboBoxModel model = new DefaultComboBoxModel();
        model.addElement(null);
        pokemons.forEach((pokemon) -> {
            String imagePath = ImagePanel.getImagePathFor(pokemon, ImageType.GIF);
            boolean withImage = withImageCheckBox.isSelected();
            if (!withImage || imagePath != null) {
                pokemon.setImagePath(ImagePanel.getImagePathFor(pokemon, ImageType.GIF));
                model.addElement(pokemon);
            }
        });
        pokemonsList.setModel(model);
    }

    @Override
    public void setVisible(boolean visible) {
        reset();
        super.setVisible(visible);
    }

    private void reset() {
        pokemonsList.setSelectedIndex(0);
        fillDetails("", "", "", "");
        locationXField.setText("");
        locationYField.setText("");
        removeImage();
    }

    private void fillDetails() {
        Pokemon pokemon = (Pokemon) pokemonsList.getSelectedItem();
        removeImage();
        if (pokemon == null) {
            fillDetails("", "", "", "");
        } else {
            fillDetails(String.valueOf(pokemon.getSpeciesId()),
                    String.valueOf(pokemon.getBaseExperience()),
                    String.valueOf(pokemon.getHeight()),
                    String.valueOf(pokemon.getWeight()));
            drawImage(pokemon);
        }
    }

    private void removeImage() {
        if (imagePanel != null) {
            imageContainer.remove(imagePanel);
            imageContainer.repaint();
        }
        imagePanel = null;
    }

    private void drawImage(Pokemon pokemon) {
        if (pokemon.getImagePath() != null) {
            imagePanel = new ImagePanel(pokemon.getImagePath(), imageContainer);
            imagePanel.setSize(imageContainer.getWidth(), imageContainer.getHeight());
            imageContainer.add(imagePanel);
            imagePanel.repaint();
        }
    }

    private void fillDetails(String speciesId, String baseExperience,
            String height, String weight) {
        pokemonSpeciesId.setText(speciesId);
        pokemonBaseExperience.setText(baseExperience);
        pokemonHeight.setText(height);
        pokemonWeight.setText(weight);
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

        pokemonsList = new javax.swing.JComboBox<>();
        pokemonsListLabel = new javax.swing.JLabel();
        speciesIdLabel = new javax.swing.JLabel();
        pokemonSpeciesId = new javax.swing.JLabel();
        heightLabel = new javax.swing.JLabel();
        pokemonHeight = new javax.swing.JLabel();
        pokemonWeight = new javax.swing.JLabel();
        weightLabel = new javax.swing.JLabel();
        baseExperienceLabel = new javax.swing.JLabel();
        pokemonBaseExperience = new javax.swing.JLabel();
        locationLabel = new javax.swing.JLabel();
        locationXField = new javax.swing.JTextField();
        locationYField = new javax.swing.JTextField();
        closeButton = new javax.swing.JButton();
        addButton = new javax.swing.JButton();
        imageContainer = new javax.swing.JPanel();
        withImageCheckBox = new javax.swing.JCheckBox();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Add Encounter");

        pokemonsListLabel.setText("Pokemons");

        speciesIdLabel.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        speciesIdLabel.setText("Species Id:");

        pokemonSpeciesId.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        pokemonSpeciesId.setText("0");
        pokemonSpeciesId.setHorizontalTextPosition(javax.swing.SwingConstants.LEFT);

        heightLabel.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        heightLabel.setText("Height:");

        pokemonHeight.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        pokemonHeight.setText("0");
        pokemonHeight.setHorizontalTextPosition(javax.swing.SwingConstants.LEFT);

        pokemonWeight.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        pokemonWeight.setText("0");
        pokemonWeight.setHorizontalTextPosition(javax.swing.SwingConstants.LEFT);

        weightLabel.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        weightLabel.setText("Weight:");

        baseExperienceLabel.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        baseExperienceLabel.setText("Base Experience:");

        pokemonBaseExperience.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        pokemonBaseExperience.setText("0");
        pokemonBaseExperience.setHorizontalTextPosition(javax.swing.SwingConstants.LEFT);

        locationLabel.setText("Location:");

        locationXField.setHorizontalAlignment(javax.swing.JTextField.RIGHT);

        locationYField.setHorizontalAlignment(javax.swing.JTextField.RIGHT);

        closeButton.setText("Close");

        addButton.setText("Add");

        imageContainer.setPreferredSize(new java.awt.Dimension(134, 134));

        javax.swing.GroupLayout imageContainerLayout = new javax.swing.GroupLayout(imageContainer);
        imageContainer.setLayout(imageContainerLayout);
        imageContainerLayout.setHorizontalGroup(
            imageContainerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );
        imageContainerLayout.setVerticalGroup(
            imageContainerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 134, Short.MAX_VALUE)
        );

        withImageCheckBox.setText("Only with image");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(pokemonsListLabel)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(withImageCheckBox))
                            .addComponent(pokemonsList, javax.swing.GroupLayout.PREFERRED_SIZE, 317, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(imageContainer, javax.swing.GroupLayout.DEFAULT_SIZE, 148, Short.MAX_VALUE)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(locationLabel)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(locationXField, javax.swing.GroupLayout.PREFERRED_SIZE, 47, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(locationYField, javax.swing.GroupLayout.PREFERRED_SIZE, 47, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(addButton)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(closeButton)
                                .addGap(10, 10, 10))
                            .addGroup(layout.createSequentialGroup()
                                .addGap(10, 10, 10)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(layout.createSequentialGroup()
                                        .addComponent(speciesIdLabel)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(pokemonSpeciesId))
                                    .addGroup(layout.createSequentialGroup()
                                        .addComponent(heightLabel)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(pokemonHeight))
                                    .addGroup(layout.createSequentialGroup()
                                        .addComponent(weightLabel)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(pokemonWeight))
                                    .addGroup(layout.createSequentialGroup()
                                        .addComponent(baseExperienceLabel)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(pokemonBaseExperience)))
                                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(pokemonsListLabel)
                    .addComponent(withImageCheckBox))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(pokemonsList, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(imageContainer, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(speciesIdLabel)
                            .addComponent(pokemonSpeciesId))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(baseExperienceLabel)
                            .addComponent(pokemonBaseExperience))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(heightLabel)
                            .addComponent(pokemonHeight))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(weightLabel)
                            .addComponent(pokemonWeight))))
                .addGap(18, 18, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(locationLabel)
                    .addComponent(locationXField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(locationYField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(24, 24, 24)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(closeButton)
                    .addComponent(addButton))
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton addButton;
    private javax.swing.JLabel baseExperienceLabel;
    private javax.swing.JButton closeButton;
    private javax.swing.JLabel heightLabel;
    private javax.swing.JPanel imageContainer;
    private javax.swing.JLabel locationLabel;
    private javax.swing.JTextField locationXField;
    private javax.swing.JTextField locationYField;
    private javax.swing.JLabel pokemonBaseExperience;
    private javax.swing.JLabel pokemonHeight;
    private javax.swing.JLabel pokemonSpeciesId;
    private javax.swing.JLabel pokemonWeight;
    private javax.swing.JComboBox<Pokemon> pokemonsList;
    private javax.swing.JLabel pokemonsListLabel;
    private javax.swing.JLabel speciesIdLabel;
    private javax.swing.JLabel weightLabel;
    private javax.swing.JCheckBox withImageCheckBox;
    // End of variables declaration//GEN-END:variables

    private static class PokemonListItemListener implements ItemListener {

        final EncounterDialog dialog;

        PokemonListItemListener(EncounterDialog dialog) {
            this.dialog = dialog;
        }

        @Override
        public void itemStateChanged(ItemEvent e) {
            if (e.getStateChange() == ItemEvent.SELECTED) {
                dialog.fillDetails();
            }
        }

    }

}
