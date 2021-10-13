package com.company;

import java.util.ArrayList;
import java.util.List;

public class Perceptron {
    String TrainLanguage;
    public List<Double> wVector;
    public double theta;
    public double alpha;

    public String getTrainLanguage() {
        return TrainLanguage;
    }



    public void learn(Vec vec, int correctAnswer){
        double scalar = 0;
        for (int i = 0; i < vec.getAttributes().size() ; i++) // Calculate X * W
            scalar += vec.getAttributes().get(i) * this.wVector.get(i); //X * W sum

        int y;
        if (scalar >= this.theta) y = 1;
        else y = 0;

        if (y != correctAnswer) { //learning
            List<Double> VectorList = new ArrayList<>(this.wVector);
            for (int i = 0; i < vec.getAttributes().size(); i++) // NewW = OldW + (Correct-Y) * a * X (Iteracja po wektorach)
                VectorList.set(i, (this.wVector.get(i) + ((correctAnswer - y) * alpha * vec.getAttributes().get(i))));

            this.wVector = VectorList;
            this.theta = theta + (correctAnswer - y) * alpha * -1;
        }
    }
    public Perceptron(int size, double alpha, String TrainLanguage) {
        this.TrainLanguage = TrainLanguage;
        this.alpha = alpha;
        this.wVector = new ArrayList<>();
        for (int i = 0; i < size ; i++) {
            this.wVector.add(1.0);
        }
        this.theta = 1.0;

    }



    public void normalization(){
        double squaresSum = 0;
        for (double d : this.wVector)
            squaresSum+=Math.pow(d,2);
        double res = Math.sqrt(squaresSum);
        List<Double>  newWVector = new ArrayList<>();
        for (int i = 0; i < this.wVector.size() ; i++)
            newWVector.add(i,(this.wVector.get(i) / res));
        this.theta = theta / res;
        this.wVector = newWVector;
    }
    public double rate(Vec vec){
        double scalar = 0;
        for (int i = 0; i < vec.getAttributes().size() ; i++) //X * W
            scalar += vec.getAttributes().get(i) * this.wVector.get(i);


        return scalar - theta; // NET
    }

}