package com.example.maxim.picoto;

import java.util.ArrayList;
import java.util.Collections;

/**
 * Created by maxim on 24.03.18.
 */

public class NameQuery {
    private static ArrayList<String> names;


    public static ArrayList<String> getNames() {

        names=new ArrayList<>();
        Collections.addAll(names,
                "Bicentennial Print",
                "Boy with Sweets",
                "Cap Lombard",
                "Colors from Distance",
                "Cotopaxi",
                "Crucifixion",
                "Divan Japonais",
                "Edith",
                "The Magnificent",
                "Horses",
                "Saint-Remy",
                "Calm",
                "Madonna",
                "Friend Portrait",
                "Picasso",
                "Ritmo plastico",
                "Seawall",
                "Self-Portrait",
                "The Annunciation",
                "Green Boathouse",
                "The Scream",
                "The Starry Night",
                "The Tower of Babel",
                "The Trial",
                "Yellow Sunset",
                "Fishing Boats",
                "Trees in a Lane",
                "Tullia",
                "Orange Tree",
                "Victory",
                "White Zig Zags");

        return names;
    }
}
