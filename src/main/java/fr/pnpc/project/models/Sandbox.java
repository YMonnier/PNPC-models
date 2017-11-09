package fr.pnpc.project.models;

import java.util.Locale;
import java.util.ResourceBundle;

/**
 * Created by stephen on 26/10/17.
 */
public class Sandbox {

    public static void main(String[] args) {
        Locale defaultLocale = new Locale("fr","FR");
        ResourceBundle labels = ResourceBundle.getBundle("MessagesBundle_fr_FR", defaultLocale);
        String value = labels.getString("s1");
        System.out.println(value);
    }

}
