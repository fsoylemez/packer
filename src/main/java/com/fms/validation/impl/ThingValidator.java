package com.fms.validation.impl;

import com.fms.domain.Thing;
import com.fms.exception.ApiException;
import com.fms.messages.Messages;
import com.fms.parsing.ParseConstants;
import com.fms.validation.Validatable;

public class ThingValidator implements Validatable<Thing> {

    @Override
    public void validate(Thing thing) {
        if (thing.getWeight()> ParseConstants.MAX_WEIGHT) {
            throw new ApiException(Messages.getMessage("thing.weight.exceed"));
        }
        if (thing.getCost() > ParseConstants.MAX_COST) {
            throw new ApiException(Messages.getMessage("thing.cost.exceed"));
        }
    }
}
