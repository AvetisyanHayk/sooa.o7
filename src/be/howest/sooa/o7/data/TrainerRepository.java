package be.howest.sooa.o7.data;

import be.howest.sooa.o7.domain.Pokemon;
import be.howest.sooa.o7.domain.Trainer;
import be.howest.sooa.o7.ex.TrainerIOException;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

/**
 *
 * @author hayk
 */
public class TrainerRepository {

    private static final String TRAINERS_PATH = "trainers";
    private static final String FMT_TRAINER_PATH = "trainers/%s";
    private static final File TRAINERS_DIR = new File(TRAINERS_PATH);
    private static final String FMT_POKEBALLS_PATH = TRAINERS_PATH + "/%s/pokeballs";
    private static final String FMT_POKEMONS_PATH = TRAINERS_PATH + "/%s/pokemons";

    private final PokemonRepository pokemonRepo = new PokemonRepository();

    public TrainerRepository() {
        checkDirectory();
    }

    private void checkDirectory() {
        if (!TRAINERS_DIR.exists()) {
            TRAINERS_DIR.mkdir();
        }
    }

    public Trainer findByName(String name) {
        checkDirectory();
        return build(name);
    }

    public List<Trainer> findAll() {
        checkDirectory();
        File trainersDirectory = new File(TRAINERS_PATH);
        List<Trainer> trainers = new ArrayList<>();
        String[] trainerDirectories = trainersDirectory
                .list((File current, String name)
                        -> new File(current, name).isDirectory());
        for (String name : trainerDirectories) {
            trainers.add(build(name));
        }
        return trainers;
    }

    public void save(Trainer trainer) {
        checkDirectory();
        File trainerDirectory
                = new File(String.format(FMT_TRAINER_PATH, trainer.getName()));
        if (!trainerDirectory.exists()) {
            trainerDirectory.mkdir();
        }
        savePokeballsFor(trainer);
        savePokemonsFor(trainer);
    }

    public void updateTrainerName(Trainer trainer, String newName) throws TrainerIOException {
        checkDirectory();
        File newTrainerDirectory
                = new File(String.format(FMT_TRAINER_PATH, newName));
        if (newTrainerDirectory.exists()) {
            throw new TrainerIOException("Trainer " + newName + " already exists.");
        } else {
            File trainerDirectory
                    = new File(String.format(FMT_TRAINER_PATH, trainer.getName()));
            if (trainerDirectory.exists()) {
                trainerDirectory.renameTo(newTrainerDirectory);
            } else {
                save(trainer);
            }
        }
    }

    private Trainer build(String name) {
        if (name != null && !"".equals(name = name.trim())) {
            Trainer trainer = new Trainer(name);
            trainer.setPokeballs(getPokeballsFor(trainer));
            trainer.setPokemons(getPokemonsFor(trainer));
            return trainer;
        }
        return null;
    }

    private int getPokeballsFor(Trainer trainer) {
        String pokeballsPath = String.format(FMT_POKEBALLS_PATH, trainer.getName());
        try (BufferedReader bufferedReader
                = new BufferedReader(new FileReader(pokeballsPath))) {
            String value = "";
            for (int eenByte; (eenByte = bufferedReader.read()) != -1;) {
                value += (char) eenByte;
            }
            System.out.println("till here...");
            return Integer.parseInt(value.trim());
        } catch (IOException | NumberFormatException ex) {
            System.out.println(ex.getMessage());
        }
        return trainer.getPokeballs();
    }

    private List<Pokemon> getPokemonsFor(Trainer trainer) {
        String pokemonsPath = String.format(FMT_POKEMONS_PATH, trainer.getName());
        List<Pokemon> pokemons = new ArrayList<>();
        try (BufferedReader bufferedReader
                = new BufferedReader(new FileReader(pokemonsPath))) {
            Stream<String> lines = bufferedReader.lines();
            lines.map(String::trim).forEach((line) -> {
                pokemons.add(readPokemon(line));
            });
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        }
        return pokemons;
    }

    private Pokemon readPokemon(String pokemonId) {
        if (pokemonId != null && !"".equals(pokemonId)) {
            try {
                long id = Long.parseLong(pokemonId);
                return pokemonRepo.read(id);
            } catch (NumberFormatException ex) {
                System.out.println(ex.getMessage());
            }
        }
        return null;
    }

    private void savePokeballsFor(Trainer trainer) {
        String pokeballsPath = String.format(FMT_POKEBALLS_PATH, trainer.getName());
        try (BufferedWriter bufferedWriter
                = new BufferedWriter(new FileWriter(pokeballsPath))) {
            String pokeballs = String.valueOf(trainer.getPokeballs());
            bufferedWriter.write(pokeballs);
        } catch (IOException | NumberFormatException ex) {
            System.out.println(ex.getMessage());
        }
    }

    private void savePokemonsFor(Trainer trainer) {
        String pokemonsPath = String.format(FMT_POKEMONS_PATH, trainer.getName());
        List<Pokemon> pokemons = new ArrayList<>();
        try (BufferedWriter bufferedWriter
                = new BufferedWriter(new FileWriter(pokemonsPath))) {
            trainer.getPokemons().forEach((pokemon) -> {
                try {
                    String pokemonId = String.valueOf(pokemon.getId());
                    bufferedWriter.write(pokemonId);
                    bufferedWriter.newLine();
                } catch (IOException ex) {
                    System.out.println(ex.getMessage());
                }
            });
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        }
    }
}
