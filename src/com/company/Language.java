package com.company;

import java.util.List;
import java.util.Map;

public class Language {


    List<Map<Character,Long>> listOfLetterMaps;
    String languageName;




    public String getLanguageName() {
        return languageName;
    }

    public List<Map<Character, Long>> getListOfLetterMaps() {
        return listOfLetterMaps;
    }
    public Language(String languageName, List<Map<Character, Long>> listOfLetterMaps) {
        this.languageName = languageName;
        this.listOfLetterMaps = listOfLetterMaps;
    }
}
