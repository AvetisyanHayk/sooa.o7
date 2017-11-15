package be.howest.sooa.o7.domain;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Hayk
 */
public class EncounterValidation {

    private final List<String> messageList = new ArrayList<>();
    private Encounter encounter;

    public EncounterValidation(Pokemon pokemon, String x, String y) {
        validatePokemon(pokemon);
        int intX = validateX(x);
        int intY = validateY(y);
        if (isValid()) {
            encounter = new Encounter(pokemon, intX, intY);
        }
    }

    public Encounter getEncounter() {
        return encounter;
    }
    
    private void validatePokemon(Pokemon pokemon) {
        if (pokemon == null) {
            messageList.add("A pokemon must be selected");
        }
    }
    
    private int validateX(String x) {
        return validateLocationParameter(x, "X");
    }
    
    private int validateY(String y) {
        return validateLocationParameter(y, "Y");
    }
    
    private int validateLocationParameter(String param, String name) {
        if (param == null || "".equals(param)) {
            messageList.add(String.format("Location %s is mandatory",
                    name));
        } else {
            try {
                int intParam = Integer.parseInt(param);
                return intParam;
            } catch(NumberFormatException ex) {
                messageList.add(String.format("Location %s must have a numeric value",
                    name));
            }
        }
        return -1;
    }

    public String getMessage() {
        return messageList.stream().reduce("", (previous, current) -> 
            ("".equals(previous)) ? previous + current : previous + "\n" + current
        );
    }

    public final boolean isValid() {
        return messageList.isEmpty();
    }
    
    @Override
    public String toString() {
        return getMessage();
    }
}
