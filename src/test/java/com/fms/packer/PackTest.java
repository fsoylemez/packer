package com.fms.packer;

import com.fms.exception.ApiException;
import com.fms.messages.Messages;
import org.junit.Test;

import java.io.File;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;

public class PackTest {

    @Test
    public void packCaseSuccess(){
        File file = getFile("case");

        String s = Packer.pack(file.getPath());
        String expected = "4\n" +
                "-\n" +
                "2,7\n" +
                "8,9\n";
        assertEquals(s,expected);
    }

    @Test
    public void packCaseMoreThan15Things(){
        File file = getFile("case18Things");

        try {
             Packer.pack(file.getPath());
        } catch (ApiException ae) {
            assertThat(ae.getMessage(), is(Messages.getMessage("thing.number.exceed")));
        }

    }

    @Test
    public void packSameCostDiffWeight(){
        File file = getFile("caseSameCost");

        String result = Packer.pack(file.getPath());
        assertThat("8,9\n",is(result));
    }

    @Test
    public void packFaultyFormat(){
        File file = getFile("caseFaulty");

        try {
            Packer.pack(file.getPath());
        } catch (ApiException ae) {
            assertThat(ae.getMessage(), is(Messages.getMessage("line.thing.match")));
        }
    }

    @Test
    public void packCase2(){
        File file = getFile("case2");
        String result = Packer.pack(file.getPath());
        assertThat(result,is("3,4\n"+
                "4,5,7\n"+
                "2,3,4\n"+
                "1,2\n"));
    }

    @Test
    public void packCaseNoThing(){
        File file = getFile("caseNoThing");
        String result = Packer.pack(file.getPath());
        String expected = "-\n" +
                "4\n" +
                "2,7\n" +
                "8,9\n";
        assertEquals(result,expected);
    }

    public File getFile(String fileName){
        ClassLoader classLoader = getClass().getClassLoader();
        return new File(classLoader.getResource(fileName).getFile());
    }

}
