package be.howest.sooa.o7.domain;

import java.util.List;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Locale;
import java.util.Objects;

/**
 *
 * @author Hayk
 */
public class Trainer {

    private final String name;
    private int pokeballs;
    private final List<Pokemon> pokemons = new ArrayList<>();

    public Trainer(String name) {
        this.name = name;
    }

    public Trainer(String name, int pokeballs) {
        this.name = name;
        this.pokeballs = pokeballs;
    }

    public String getName() {
        return name;
    }

    public int getPokeballs() {
        return pokeballs;
    }

    public void setPokeballs(int pokeballs) {
        this.pokeballs = pokeballs;
    }

    public List<Pokemon> getPokemons() {
        return Collections.unmodifiableList(pokemons);
    }
    
    public void earnBall() {
        pokeballs++;
    }
    
    public void useBall() {
        if (pokeballs > 0) {
            pokeballs--;
        }
    }
    
    public void addPokemon(Pokemon pokemon) {
        if (pokemon != null) {
            pokemons.add(pokemon);
        }
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 59 * hash + Objects.hashCode(this.name.toLowerCase(Locale.ENGLISH));
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Trainer other = (Trainer) obj;
        return name.equalsIgnoreCase(other.name);
    }
    
    @Override
    public String toString() {
        return name;
    }
}
