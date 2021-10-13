package com.company;

import java.io.*;
import java.util.*;
import java.util.function.IntPredicate;

public class DataCleaner {
    public static StringBuilder stringBuilderFiles(String addresOfFile) throws IOException { //łączenie tekstu
        StringBuilder sb  = new StringBuilder();
        String l;
        FileReader fr = new FileReader(addresOfFile);
        BufferedReader br = new BufferedReader(fr);

        while ((l= br.readLine())!=null)
            sb.append(l);
        br.close();
        return sb;
    }
    public static List<String> getFiles(String file){ // dostęp do listy plików
        File f = new File(file);
        return Arrays.asList(f.list());
    }
    public static List<Map<Character, Long>> proportionList(List<StringBuilder> sbList){ // liczenie proporcji
        List<Map<Character,Long>> listOfProportionMaps = new ArrayList<>();
        for (StringBuilder sb : sbList) {
            Map<Character, Long> map;
            map = new HashMap<>();
            long noLatin = sb.length();
            for (int i = 'a'; i <= 'z'; i++) {
                char letter = (char) i;
                long count = sb.chars().filter(new IntPredicate() {
                    @Override
                    public boolean test(int character) {
                        return ((character == letter) || (character == (letter - 32))); // małe i wielkie litery
                    }
                }).count();
                noLatin -= count;
                map.put((char) i, count);
            }
            map.put('-', noLatin); // nie łacinskie
            map.put('+', (long) sb.length()); // łacinskie
            listOfProportionMaps.add(map);
        }
        return listOfProportionMaps;
    }
    public static List<StringBuilder> ListOfFiles(String dir) throws IOException { // lista gotowych plików
        List<StringBuilder> list = new ArrayList<>();
        List<String> addresOfFile = getFiles(dir);
        for (String addres : addresOfFile)
            list.add(stringBuilderFiles(dir+"\\"+addres));
        return list;
    }
}
