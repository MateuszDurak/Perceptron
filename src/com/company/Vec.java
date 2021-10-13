package com.company;

import java.util.ArrayList;
import java.util.List;

public class Vec {
    public String decision;
    public List<Double> attributes;


    public Vec(List<Double> attributes, String decision) {
        this.attributes = attributes;
        this.decision = decision;
    }



    public List<Double> getAttributes() {
        return attributes;
    }
    public void normalize(){
        double squaresSum=0;
        List<Double>  normaliedAttributes = new ArrayList<>();
        for (double d : this.attributes)
            squaresSum+=Math.pow(d,2);

        double length = squaresSum;
        length = Math.sqrt(length);
        for (int i = 0; i < this.attributes.size() ; i++)
            normaliedAttributes.add(i,(this.attributes.get(i)/length));

        this.attributes = normaliedAttributes;
    }
    public String getDecision() {
        return decision;
    }


}
