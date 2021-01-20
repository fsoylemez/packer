package com.fms.packer;

import com.fms.domain.Box;
import com.fms.domain.Thing;
import com.fms.exception.ApiException;
import com.fms.parsing.ParseConstants;
import com.fms.parsing.ThingParser;
import com.fms.validation.Validatable;
import com.fms.validation.ValidationFactory;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.List;
import java.util.Scanner;


/**
 * <h1>Decide which things to put in the package</h1>
 * Packer program chooses from a set of things to put in the
 * package depending on their weight and cost.The purpose is
 * putting the things in a strategy to maximize cost and minimize
 * weight while considering the constraints.(i.e. max weight that
 * a package can carry) 0-1 Knapsack algorithm is used for the solution
 * as it is the de facto algorithm for this kind of problems.
 *
 * @author  Fatih Soylemez
 * @version 1.0
 * @since   2018-04-18
 */
public class Packer {

    private static final String FILE_CHARSET = "UTF-8";

    private Packer() {}

    /**
     * Main method for finding solution to the list of problems
     * which are defined in a file whose path is given as parameter.
     *
     * @param filePath : Path to file
     * @return : Solution as String
     * @throws ApiException
     */
    public static String pack(String filePath) throws ApiException{

        Validatable<String> fileValidator = ValidationFactory.getValidator("fileValidator");
        //validate if file exists,readable and not empty
        fileValidator.validate(filePath);

        FileInputStream inputStream = null;
        Scanner sc = null;
        //use StringBuilder since we will use concatenation frequently
        StringBuilder builder = null;
        try {
            builder = new StringBuilder();
            inputStream = new FileInputStream(filePath);
            sc = new Scanner(inputStream, FILE_CHARSET);
            //read file line by line,because it may be a huge one
            while (sc.hasNextLine()) {
                String line = sc.nextLine();

                Validatable<String> lineValidator = ValidationFactory.getValidator("lineValidator");
                //validate the line against the format and the rules
                lineValidator.validate(line);

                String[] weightAndThings = line.split(ParseConstants.COLON);
                //max weight that package can carry
                int weight = Integer.parseInt(weightAndThings[0].trim());

                //if there are no things skip the calculation
                if(weightAndThings.length>1 && weightAndThings[1]!=null && !weightAndThings[1].isEmpty()) {
                    //things that we can choose from
                    List<Thing> things = ThingParser.toThings(weightAndThings[1].trim());
                    Validatable<Thing> thingValidator = ValidationFactory.getValidator("thingValidator");
                    //validate the things against the rules
                    things.stream().forEach(t -> thingValidator.validate(t));
                    //solve the case and append the solution for this line
                    builder.append(knapsack(things, weight).getIdsAsString());
                }
                else{
                    builder.append("-");
                }
                builder.append("\n");
            }
            // note that Scanner suppresses exceptions
            if (sc.ioException() != null) {
                throw sc.ioException();
            }
        }
        catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (inputStream != null) {
                try {
                    inputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (sc != null) {
                sc.close();
            }
        }

        return builder.toString();
    }

    /**
     *  The objective is to fill the knapsack with items such that we have a maximum cost
     *  without crossing the weight limit of the knapsack. Since this is a 0-1 knapsack problem
     *  hence we can either take an entire item or reject it completely.
     *
     * @param Things list of options that we can pick from
     * @param W maximum weight that package can carry
     * @return Box with things in it
     */
    public static Box knapsack(List<Thing> Things, int W) {
        int N = Things.size(); // Get the total number of Things.
        Box[][] V = new Box[N + 1][W + 1]; //Create a matrix. Things are in rows and weight at in columns +1 on each side
        //What if the knapsack's capacity is 0 - Set all columns at row 0 to be empty
        for (int col = 0; col <= W; col++) {
            V[0][col] = new Box();
        }
        //What if there are no Things at home.  Fill the first row with empty
        for (int row = 0; row <= N; row++) {
            V[row][0] = new Box();
        }
        for (int index=1;index<=N;index++){
            //Let's fill the values row by row
            for (int weight=1;weight<=W;weight++){
                //Is the current Things weight less than or equal to running weight
                if (Things.get(index-1).getWeight()<=weight){
                    //Given a weight, check if the value of the current Thing + value of the Thing that we could afford with the remaining weight
                    //is greater than the value without the current Thing itself
                    V[index][weight]=maxCostMinWeight(Things.get(index-1),V[index-1][weight-(int)Things.get(index-1).getWeight()], V[index-1][weight]);
                }
                else {
                    //If the current Thing's weight is more than the running weight, just carry forward the value without the current Thing
                    V[index][weight]=V[index-1][weight];
                }
            }
        }

        return V[N][W];
    }

    /**
     *
     * @param currentThing thing that we need to decide to take or leave it
     * @param remaining
     * @param without
     * @return
     */
    private static Box maxCostMinWeight(Thing currentThing, Box remaining, Box without) {
        //better to take it since its greater than without
        if(currentThing.getCost()+remaining.getCost()>without.getCost()){
            return createNewWithCurrent(currentThing, remaining);
        }
        //if two things have the same cost,take the one with less weight
        else if(currentThing.getCost()+remaining.getCost()==without.getCost() &&
                currentThing.getWeight()+remaining.getWeight()<without.getWeight()){
                return createNewWithCurrent(currentThing, remaining);
            }
        //leave it
        return new Box(without.getThings());
    }

    private static Box createNewWithCurrent(Thing currentThing, Box remaining){
        Box withCurrentThing = new Box();
        withCurrentThing.getThings().addAll(remaining.getThings());
        withCurrentThing.getThings().add(currentThing);
        return withCurrentThing;
    }
}
