package com.fms.packer;

import com.fms.exception.ApiException;
import com.fms.messages.Messages;
import com.fms.validation.Validatable;
import com.fms.validation.ValidationFactory;
import org.junit.BeforeClass;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

public class FileTest
{
    private static final String RESOURCES_PATH = "src/test/resources";

    private static Validatable fileValidator;

    @BeforeClass
    public static void init(){
         fileValidator = ValidationFactory.getValidator("fileValidator");
    }


    @Test
    public void fileShouldExist()
    {
        try {
            fileValidator.validate(RESOURCES_PATH+"/case");
        } catch (ApiException ae) {
            assertErrorFor(ae, "file.not.exists");

        }
        }

    @Test
    public void fileShouldNotBeDirectory()
    {
        try {
            fileValidator.validate(RESOURCES_PATH+"/sampleDirectory");
        } catch (ApiException ae) {
            assertErrorFor(ae,"file.is.directory");

        }
    }

    @Test
    public void fileShouldBeReadable()
    {
        try {
            fileValidator.validate(RESOURCES_PATH+"/caseNotReadable");
        } catch (ApiException ae) {
            assertErrorFor(ae,"file.not.readable");

        }
    }

    @Test
    public void fileShouldNotBeEmpty()
    {
        try {
            fileValidator.validate(RESOURCES_PATH+"/caseEmpty");
        } catch (ApiException ae) {
            assertErrorFor(ae,"file.empty");
        }
    }

   protected void assertErrorFor(ApiException ae,String bundleKey){
       assertThat(ae.getMessage(),is(Messages.getMessage(bundleKey)));
   }


}
