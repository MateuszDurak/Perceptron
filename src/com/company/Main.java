package com.company;
import javax.swing.*;
import java.io.IOException;
import java.util.*;
import java.util.List;

public class Main {
    public static void main(String[] args) throws IOException {
        String datas = "data";

        List<Language> ListOfLanguages = new ArrayList<>();

        for (String s : DataCleaner.getFiles(datas)) {
            List<StringBuilder> sbList = DataCleaner.ListOfFiles(datas+"\\"+s); // Lista plików
            List<Map<Character,Long>> listOfMapOfProportions = DataCleaner.proportionList(sbList);
            Language language = new Language(s, listOfMapOfProportions);
            ListOfLanguages.add(language);
        }

        List<Perceptron> perceptrons = new ArrayList<>();

        for (Language language : ListOfLanguages) {
        Perceptron p = new Perceptron(language.getListOfLetterMaps().get(0).size(), 0.03, language.getLanguageName());
            perceptrons.add(p); //lista perceptronów
        }
        List<Vec> trainingList = new ArrayList<>();

        for (Language language : ListOfLanguages) {
            for (Map<Character,Long> map : language.getListOfLetterMaps()) {
                List<Double> attributes = new ArrayList<>();
                for (int i = 'a'; i <= 'z'; i++)
                    attributes.add((map.get((char) i) / ((double)(map.get('+')) - ((double)map.get('-')))));
                Vec v = new Vec(attributes,language.getLanguageName());
                trainingList.add(v);
            }
        }

        for (int i = 0; i < ListOfLanguages.size() * 100; i++) {
            Collections.shuffle(trainingList);
            for (Vec vec : trainingList)
                for (Perceptron perceptron : perceptrons)
                    if (vec.getDecision().equals(perceptron.getTrainLanguage())){
                        perceptron.learn(vec, 1);
                    }
                    else {
                        perceptron.learn(vec, 0);
                    }
        }

        for (Perceptron perceptron : perceptrons)
            perceptron.normalization();

        SwingUtilities.invokeLater(() -> new Check(perceptrons));
    }
}
