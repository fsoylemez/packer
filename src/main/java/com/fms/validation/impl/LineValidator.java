package com.fms.validation.impl;

import com.fms.exception.ApiException;
import com.fms.messages.Messages;
import com.fms.parsing.ParseConstants;
import com.fms.validation.Validatable;

/**
 * Checks the given line to be in the format that we expect
 *
 * @author  Fatih Soylemez
 * @version 1.0
 * @since   2018-04-18
 */
public class LineValidator implements Validatable<String> {


    @Override
    public void validate(String line) {

        if(line==null || line.isEmpty())
            throw new ApiException(Messages.getMessage("line.empty"));
        int colonCount = line.length() - line.replace(ParseConstants.COLON, ParseConstants.NO_CHARACTER).length();
        if(colonCount!=1)
            throw new ApiException(Messages.getMessage("line.coloncheck"));

        String[] weightAndItems = line.split(ParseConstants.COLON);

        int weight;
        try {
             weight = Integer.parseInt(weightAndItems[0].trim());
        } catch (NumberFormatException e) {
            throw new ApiException(Messages.getMessage("line.weight.number"));
        }
        if(weight>ParseConstants.MAX_WEIGHT){
            throw new ApiException(Messages.getMessage("line.weight.exceed"));
        }
        //to support the case that there are no things
        if(weightAndItems.length>1 && weightAndItems[1]!=null && !weightAndItems[1].isEmpty()) {
            String[] things = weightAndItems[1].trim().split(ParseConstants.SPACE);
            for (String thing : things) {
                if (!thing.matches(ParseConstants.THING_REGEX)) {
                    throw new ApiException(Messages.getMessage("line.thing.match"));
                }
            }
        }
    }
}
