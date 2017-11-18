package be.howest.sooa.o8.gui;

import java.util.Locale;

/**
 *
 * @author Hayk
 */
public enum ImageType {
    GIF, JPG, PNG;
    
    @Override
    public String toString() {
        return super.toString().toLowerCase(Locale.ENGLISH);
    }
}
