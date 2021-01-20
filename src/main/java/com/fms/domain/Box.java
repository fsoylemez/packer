package com.fms.domain;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 *Candidate package that includes things up to the
 * weight boundary
 *
 * @author  Fatih Soylemez
 * @version 1.0
 * @since   2018-04-18
 */
public class Box {

    public Box(List<Thing> things) {
        this.things = things;
    }

    public Box(){}

    private List<Thing> things = new ArrayList<>();

    public List<Thing> getThings() {
        return things;
    }

    public double getCost(){
        if(things ==null || things.size()==0)
            return 0;
        return things.stream().mapToDouble(Thing::getCost).sum();
    }

    public double getWeight(){
        if(things ==null || things.size()==0)
            return 0;
        return things.stream().mapToDouble(Thing::getWeight).sum();
    }

    public String getIdsAsString(){
        if(getThings()==null || getThings().size()==0){
            return "-";
        }
        return getThings().stream().mapToInt(Thing::getIndex)
                .mapToObj(Integer::toString)
                .collect(Collectors.joining(","));
    }
}
