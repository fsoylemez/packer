package com.fms.packer;

import com.fms.exception.ApiException;
import com.fms.messages.Messages;
import com.fms.validation.ValidationFactory;
import com.fms.validation.Validatable;
import org.junit.BeforeClass;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

public class LineTest {

    private static Validatable lineValidator;

    @BeforeClass
    public static void init(){
        lineValidator = ValidationFactory.getValidator("lineValidator");
    }

    @Test
    public void lineShouldNotBeEmpty(){
        String line = "";
        try {
            lineValidator.validate(line);
        } catch (ApiException ae) {
            assertErrorFor(ae, "line.empty");
        }
    }

    @Test
    public void lineShouldContainColon(){
        String line = "45 (2,3.22,€52)";
        try {
            lineValidator.validate(line);
        } catch (ApiException ae) {
            assertErrorFor(ae,"line.coloncheck");
        }
    }

    @Test
    public void lineShouldContainOneColon(){
        String line = "45 : (2,3.22,€52) : (4)";
        try {
            lineValidator.validate(line);
        } catch (ApiException ae) {
            assertErrorFor(ae,"line.coloncheck");
        }
    }

    @Test
    public void lineShouldStartWithWeight(){
        String line = "TRYT887 *&(YU 78BJ .LK<";
        try {
            lineValidator.validate(line);
        } catch (ApiException ae) {
            assertErrorFor(ae,"line.coloncheck");
        }
    }

    @Test
    public void weightShouldBeNumber(){
        String line = "TRYT887 : *&(YU 78BJ .LK<";
        try {
            lineValidator.validate(line);
        } catch (ApiException ae) {
            assertErrorFor(ae,"line.weight.number");
        }
    }

    @Test
    public void weightShouldBeUpTo100(){
        String line = "887 : *&(YU 78BJ .LK<";
        try {
            lineValidator.validate(line);
        } catch (ApiException ae) {
            assertErrorFor(ae,"line.weight.exceed");
        }
    }

    @Test
    public void thingShouldMathFormat(){
        String line = "81 : (1,53.38,€45) (2,88.62,$98) (3,78.48,€3) (4,72.30,€76) (5,30.18,€9) (6,46.34,€48)";
        try {
            lineValidator.validate(line);
        } catch (ApiException ae) {
            assertErrorFor(ae,"line.thing.match");
        }
    }


    protected void assertErrorFor(ApiException ae, String bundleKey){
        assertThat(ae.getMessage(),is(Messages.getMessage(bundleKey)));
    }
}
