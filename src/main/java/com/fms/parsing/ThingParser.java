package com.fms.parsing;

import com.fms.domain.Thing;
import com.fms.exception.ApiException;
import com.fms.messages.Messages;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ThingParser {

    private ThingParser(){}

    /**
     * Converts a given valid text to a list of Thing objects
     * @param thingsStr
     * @return
     */
    public static List<Thing> toThings(String thingsStr) {
        List<Thing> result = new ArrayList<>();
        String[] things = thingsStr.split(ParseConstants.SPACE);

        for (String singleThing : things) {
            Thing thing = toThing(singleThing);
            result.add(thing);
        }
        if(result.size()>ParseConstants.MAX_THINGS_IN_A_LINE){
            throw new ApiException(Messages.getMessage("thing.number.exceed"));
        }

        return result;
    }

    /**
     * Converts a given valid text to a Thing object
     * @param thingStr
     * @return
     */
    public static Thing toThing(String thingStr) {

            Pattern pattern = Pattern.compile(ParseConstants.THING_REGEX);
            Matcher matcher = pattern.matcher(thingStr);
            if (matcher.matches()) {
                int index = Integer.parseInt(matcher.group(1));
                double weight = Double.parseDouble(matcher.group(2));
                double cost = Double.parseDouble(matcher.group(4));

                return new Thing(index, weight, cost);
            } else {
                throw new ApiException(Messages.getMessage("thing.cannot.process"));
            }
    }
}
