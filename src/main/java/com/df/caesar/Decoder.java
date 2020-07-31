package com.df.caesar;

import java.util.ArrayList;
import java.util.List;

public class Decoder {
    private IStringProvider stringProvider;

    private final String RUS_BIG = "АБВГДЕЁЖЗИЙКЛМНОПРСТУФХЦЧШЩЪЫЬЭЮЯ";
    private final String RUS_SMALL = RUS_BIG.toLowerCase();
    private List<String> decodedStrings = new ArrayList<>();

    public Decoder(IStringProvider stringProvider) {
        this.stringProvider = stringProvider;
    }

    public List<String> decode() {
        final String input = stringProvider.getString();

        for (int i = 0; i < RUS_BIG.length() ; i++) {
            StringBuilder output = new StringBuilder();
            for (int j = 0; j < input.length(); j++) {
                char c = input.charAt(j);
                char charToAdd;
                String charClass = null;
                if (RUS_SMALL.indexOf((int) c) >= 0) {
                    charToAdd = RUS_SMALL.charAt((RUS_SMALL.indexOf((int) c)+i) % 33 );
                } else if (RUS_BIG.indexOf((int) c) >= 0) {
                    charToAdd = RUS_BIG.charAt((RUS_BIG.indexOf((int) c)+i) % 33 );
                } else {
                    charToAdd = c;
                }
                output.append(charToAdd);

            }
            decodedStrings.add(output.toString());
        }
        return decodedStrings;
    }
}
