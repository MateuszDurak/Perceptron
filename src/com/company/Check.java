package com.company;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;


public class Check extends JFrame {

    JLabel answear;
    JTextArea textArea;
    List<Perceptron> perceptrons;
    JButton button;


    public Check(List<Perceptron> perceptrons){
        this.perceptrons = perceptrons;
        textArea = new JTextArea();
        BorderLayout layout = new BorderLayout(50,50);
        this.setLayout(layout);
        this.button = new JButton("Click");
        Dimension d = new Dimension(200,200);
        this.button.setPreferredSize(d);
        this.answear = new JLabel();
        this.button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (!textArea.getText().equals("")){ //If puste
                    List<StringBuilder> sbList = new ArrayList<>();
                    List<Double> attributesColumn = new ArrayList<>();
                    List<Vec> vecs = new ArrayList<>();
                    StringBuilder sb = new StringBuilder(textArea.getText()); //Weź tekst z pola tekstowego
                    sbList.add(sb);
                    Map<Character,Long> map = DataCleaner.proportionList(sbList).get(0); //wez pierwszy i jedyny element
                    for (int i = 'a'; i <= 'z'; i++) // od a do z
                        attributesColumn.add(map.get((char) i) / (double)(map.get('+') - (double)map.get('-'))); //Proporcje do listy
                    Vec v = new Vec(attributesColumn,"unknown");
                    vecs.add(v); //dodaj wektor do listy wektorów
                    v = null;

                    double res = 0;
                    String language="";
                    for (Vec vec : vecs) {
                        vec.normalize();
                        for (Perceptron perceptron : perceptrons) {
                            if (perceptron.rate(vec) > res) {
                                res = perceptron.rate(vec);
                                language= perceptron.getTrainLanguage();
                            }
                        }
                    }
                    answear.setText(language);
                }else{answear.setText("Empty text area");}
                //===========================================================
                String datas = "test";

                List<Language> ListOfLanguages = new ArrayList<>();
                List<String> files = DataCleaner.getFiles("test");
                List<Matrix> matrixList = new ArrayList<>();
                for (String s:files
                     ) {
                    matrixList.add(new Matrix(s));

                }

                for (String s : DataCleaner.getFiles(datas)) {
                    List<StringBuilder> sbList = null; // Lista plików
                    try {
                        sbList = DataCleaner.ListOfFiles(datas+"\\"+s);
                    } catch (IOException ex) {
                        ex.printStackTrace();
                    }

                    List<Map<Character,Long>> listOfMapOfProportions = DataCleaner.proportionList(sbList);
                    Language language = new Language(s, listOfMapOfProportions);
                    ListOfLanguages.add(language);
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


                double res = 0;
                String language="";
                for (Vec vec : trainingList) {
                    vec.normalize();
                    for (Perceptron perceptron : perceptrons) {
                        if (perceptron.rate(vec) > res) {
                            res = perceptron.rate(vec);
                            language= perceptron.getTrainLanguage();
                        }

                    }
                    int i = 0;
                    for (String f:files
                         ) {
                        if(vec.decision.equals(files.get(i))){
                            matrixList.get(i).Z = language;
                            matrixList.get(i).FP = matrixList.get(i).FP+1;
                            if(vec.decision.equals(language)){
                                matrixList.get(i).ZP = matrixList.get(i).ZP+1;
                            }else{
                                matrixList.get(i).ZN = matrixList.get(i).ZN+1;
                            }

                    }
                        i++;

                    }/*else if(vec.decision.equals(files.get(1))){
                        matrixList.get(1).Z = vec.decision;
                        matrixList.get(1).FP = matrixList.get(1).FP+1;
                        if(vec.decision.equals(language)){
                            matrixList.get(1).ZP = matrixList.get(1).ZP+1;
                        }else{
                            matrixList.get(1).ZN = matrixList.get(1).ZN+1;
                        }
                    }*/

                }
                for (Matrix m:matrixList
                     ) {

                    System.out.println();
                    System.out.println(m.F);
                    System.out.println(m.FP+" "+m.ZN);//a b
                    System.out.print(m.FP-m.ZP);//c
                    System.out.print(" ");
                    System.out.print(m.FN-m.ZN);//d
                    System.out.println();
                    System.out.print("Acc = ");
                    System.out.print(((double)((m.ZN+m.ZP/m.FP+m.FN)*100)));
                    System.out.print("%");
                    System.out.println();
                    System.out.print("P = ");
                    System.out.print(((double)(m.FP/(m.FP+(m.FP-m.ZP)))));
                    System.out.println();
                    System.out.print("R = ");
                    System.out.print(((double)((m.FP/(m.FP+m.ZN)))));
                    System.out.println("");
                    System.out.print("F = ");
                    System.out.print((2*((double)(m.FP/(m.FP+(m.FP-m.ZP))))*((double)((m.FP/(m.FP+m.ZN)))))/(((double)(m.FP/(m.FP+(m.FP-m.ZP))))+((double)((m.FP/(m.FP+m.ZN))))));
                }





            }
        });

        getContentPane().add(answear,BorderLayout.SOUTH);
        getContentPane().add(textArea,BorderLayout.CENTER);
        getContentPane().add(button,BorderLayout.NORTH);


        setSize(500,500);
        setVisible(true);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);


    }
}
