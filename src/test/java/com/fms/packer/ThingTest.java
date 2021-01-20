package com.fms.packer;

import com.fms.domain.Thing;
import com.fms.exception.ApiException;
import com.fms.messages.Messages;
import com.fms.parsing.ThingParser;
import com.fms.validation.ValidationFactory;
import com.fms.validation.Validatable;
import org.junit.BeforeClass;
import org.junit.Test;

import static junit.framework.TestCase.fail;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertThat;

public class ThingTest {

    private static Validatable thingValidator;

    @BeforeClass
    public static void init(){
         thingValidator = ValidationFactory.getValidator("thingValidator");
    }

    @Test
    public void thingShouldMatch(){
        String thingStr = "(6,46.34,€48)";
        Thing thing = null;
        try {
            thing = ThingParser.toThing(thingStr);
        } catch (ApiException ae) {
            assertErrorFor(ae, "thing.cannot.process");
        }
        assertNotNull(thing);
        assertEquals(thing.getIndex(),6);
        assertEquals(thing.getWeight(),46.34,0.001);
        assertEquals(thing.getCost(),48,0.001);
    }

    @Test
    public void weightShouldNotExceed100(){
        String thingStr = "(6,176.34,€48)";
        try {
            Thing thing = ThingParser.toThing(thingStr);
            thingValidator.validate(thing);
        } catch (ApiException ae) {
            assertErrorFor(ae,"thing.weight.exceed");
        }
    }

    @Test
    public void costShouldNotExceed100(){
        String thingStr = "(6,76.34,€482)";
        try {
            Thing thing = ThingParser.toThing(thingStr);
            thingValidator.validate(thing);
        } catch (ApiException ae) {
            assertErrorFor(ae,"thing.cost.exceed");
        }
    }

    @Test
    public void maxThingsShouldNotExceed15(){
        String thingStr = "(1,53.38,€45) (2,88.62,€98) (3,78.48,€3) (4,72.30,€76) (5,30.18,€9) (6,46.34,€48) " +
                "(1,53.38,€45) (2,88.62,€98) (3,78.48,€3) (4,72.30,€76) (5,30.18,€9) (6,46.34,€48) (1,53.38,€45) " +
                "(2,88.62,€98) (3,78.48,€3) (4,72.30,€76) (5,30.18,€9) (6,46.34,€48)";
        try {
            ThingParser.toThings(thingStr);
        } catch (ApiException ae) {
            assertErrorFor(ae,"thing.number.exceed");
        }
    }

/*    @Test
    public void thingShouldNotHaveSameIndex(){
        fail("Not implemented yet.");
    }*/

    protected void assertErrorFor(ApiException ae, String bundleKey){
        assertThat(ae.getMessage(),is(Messages.getMessage(bundleKey)));
    }
}
