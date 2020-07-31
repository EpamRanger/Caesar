package com.df.caesar;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Scanner;

public class Main {

    private static final String EPAM_TEXT = "Еъёчхф Вхзёюлх, адздёиу ф ждэщхб, црбх еёдюэчъщъгюъв южаижжзчх, ждчъёнъгжзчдв. Ъы зёюивй жёхчгюв бюнс ж ъы вдгивъгзхбсгрв аёхкдв. \n" +
            "Зъеъёс вгъ дмъчющгд, мзд гъюэцъьгджзс тздшд аёхкх фчбфъзжф жбъщжзчюъв гъждчъёнъгжзчх мъбдчъмъжадшд югщючющиивх. \n" +
            "Ф юэимюб чхни южздёюу ю чгыж юэвъгъгюф, здмгъъ дзёхэюч еджздфггиу юэвъгмючджзс мъбдчъмъжаюк едёдадч. \n" +
            "Ю зъв гъ въгъъ, еджбъщдчхбх гъищхмх. Ф еёюнъб а чрчдщи, мзд чюгдя чжъви вдя югзъббъаз, х чдэвдьгд, вды мёъэвъёгдъ жзёъвбъгюъ ад чжъви шхёвдгюмгдви. \n" +
            "Гхязю ёънъгюъ вгъ едвдшбх еёдшёхввх югзиюзючгдшд зюех, жеълюхбсгд ждэщхггхф щбф юэимъгюф деёъщъбъггрк жздёдг мъбдчъмъжадя щиню. \n" +
            "Въгф вдьгд гхэчхзс дзлдв Вхзёюлр, х ъы, цъэ еёъичъбюмъгюф, вхзъёсу.";

    public static void main(String[] args) {

        Decoder decoder = new Decoder(new IStringProvider() {
            @Override
            public String getString() {
                System.out.println("Enter the encoded text: ");
                return (new Scanner(System.in).nextLine());
            }
        });

        ArrayList<String> results = new ArrayList<>();
        results.addAll(decoder.decode());

        double[] deviations = new double[results.size()];
        double minDeviation = Double.MAX_VALUE;
        int minDeviationIndex = 0;
        for (int i = 0; i< deviations.length; i++) {
            double currentDeviation = countDeviation(results.get(i));
            if (currentDeviation <= minDeviation) {
                minDeviation = currentDeviation;
                minDeviationIndex = i;
            };
        }

        System.out.println(results.get(minDeviationIndex));

    }

    public static double countDeviation(final String stringForAnalysis) {
        // считаются одной буквой Е и Ё, Ь и Ъ
        HashMap<Character, Double> russianFreqs = new HashMap<>();
        russianFreqs.put('А',0.062); // а
        russianFreqs.put('Б',0.014); // б
        russianFreqs.put('В',0.038); // в
        russianFreqs.put('Г',0.013); // г
        russianFreqs.put('Д',0.025); // д
        russianFreqs.put('Е',0.072); // Е, Ё
        russianFreqs.put('Ж',0.007); // ж
        russianFreqs.put('З',0.016); // з
        russianFreqs.put('И',0.062); // и
        russianFreqs.put('Й',0.010); // й
        russianFreqs.put('К',0.028); // к
        russianFreqs.put('Л',0.035); // л
        russianFreqs.put('М',0.026); // м
        russianFreqs.put('Н',0.053); // н
        russianFreqs.put('О',0.090); // о
        russianFreqs.put('П',0.023); // п
        russianFreqs.put('Р',0.040); // р
        russianFreqs.put('С',0.045); // с
        russianFreqs.put('Т',0.053); // т
        russianFreqs.put('У',0.021); // у
        russianFreqs.put('Ф',0.002); // ф
        russianFreqs.put('Х',0.009); // х
        russianFreqs.put('Ц',0.004); // ц
        russianFreqs.put('Ч',0.012); // ч
        russianFreqs.put('Ш',0.006); // ш
        russianFreqs.put('Щ',0.003); // щ
        russianFreqs.put('Ь',0.014); // Ь, Ъ
        russianFreqs.put('Ы',0.016); // Ы
        russianFreqs.put('Э',0.003); // э
        russianFreqs.put('Ю',0.006); // ю
        russianFreqs.put('Я',0.018); // я

        // 0 подготовить строку - done
        // 1 подсчитать частоты - done
        // 2 подсчитать дисперсию
        // 3 найти минимальную
        String preparedString = stringForAnalysis
                .toUpperCase()
                .replace('Ё', 'Е')
                .replace('Ь', 'Ъ')
                .replaceAll("[\\s.,!?]", "");
        HashMap<Character, Integer> charCounts = new HashMap<>();
        for (int i = 0; i < preparedString.length(); i++) {
            char currentChar = preparedString.charAt(i);
            charCounts.merge(currentChar, 1, Integer::sum);
        }
        HashMap<Character, Double> charFreqs = new HashMap<>();
        for (Character c : charCounts.keySet()) {
            charFreqs.put(c, charCounts.get(c) * 1.0 / stringForAnalysis.length());
        }

        // count deviation
        double squareSum = 0;
        for (Character c : charFreqs.keySet()) {
            squareSum += Math.pow(charFreqs.get(c) - russianFreqs.getOrDefault(c, 0.0), 2);
        }

        return Math.sqrt(squareSum);
    }

}
