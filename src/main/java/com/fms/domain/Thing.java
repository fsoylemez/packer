package com.fms.domain;

/**
 * Thing that we should decide whether we
 * will take it to the package or leave it.
 * It has index to identify,
 * weight and cost to consider while making the decision
 *
 * i.e. format : (1,53.38,€45)
 * @author  Fatih Soylemez
 * @version 1.0
 * @since   2018-04-18
 */
public class Thing {

    private int index;

    private double weight;

    private double cost;

    public Thing(int index, double weight, double cost) {
        this.index = index;
        this.weight = weight;
        this.cost = cost;
    }
    
    public int getIndex() {
        return index;
    }

    public double getWeight() {
        return weight;
    }

    public double getCost() {
        return cost;
    }

}
