package com.mns.fjw;

import static com.mns.fjw.Stacker.Shape.shape;
import static java.util.Arrays.asList;
import static java.util.stream.Collectors.toList;
import static org.hamcrest.collection.IsIterableContainingInOrder.contains;
import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

public class MappingTest {

    /*
    What is a map operation? It is simply applying a transformation to the items in a collection.
     */
    @Test
    public void simpleMappingExamples() {
        //Let's start with a collection of cities:
        List<String> cities =
                asList("London", "Barcelona", "New York", "Los Angeles");

        //How could we convert all the names to uppercase?
        for (String city : cities) {
            city.toUpperCase();
        }

        // The city.toUpperCase is the transformation or mapper in this cases.
        // To do this in a functional fashion you use map.
        assertThat(
                cities.stream().map(city -> city.toUpperCase()).collect(toList()),
                contains("LONDON", "BARCELONA", "NEW YORK", "LOS ANGELES")
        );

        //Why does IntelliJ highlight the city.toUpperCase funny? Because you can express this using a
        // METHOD REFERENCE:
        assertThat(
                cities.stream().map(String::toUpperCase).collect(toList()), // <- ooohhh shiny.
                contains("LONDON", "BARCELONA", "NEW YORK", "LOS ANGELES")
        );
    }

    @Test
    public void mapping_exercise1() {
        /*
        Using mapping to transform stuff.
        Imagine we have the following CSV string in our system.
         */
        String csv = "john, london, john@example.com\n" +
                "bob, birmingham, bob@example.com\n" +
                "wilma, belfast, wilma@example.com";

        // With some TL love, we can convert this to a sequence of lines by doing the following:
        List<String> lines = asList(csv.split("\\n"));

        // Now how would we convert the lines into names?
        // Traditionally:
        List<String> names = new ArrayList<>();
        for (String line : lines) {
            names.add(line.split(",")[0]);
        }
        assertThat(names, contains("john", "bob", "wilma"));

        //How would you do it using a map & lambda?
        assertThat(
                lines.stream().map(line -> line).collect(toList()),
                contains("john", "bob", "wilma")
        );
    }

    @Test
    public void nonFunctionalToFunctionalExercise() {
        List<Stacker.Shape> shapes = asList(
                shape(1, 2, true),
                shape(10, 2, true),
                shape(5, 4, true),
                shape(3, 3, true),
                shape(1, 2, false),
                shape(1, 2, false)
        );
        /*
        Can you convert the internals of stacker to use maps & lambdas?
         */
        Stacker stacker = new Stacker(shapes);
        assertThat(stacker.largeStackableItemsArea(), is((10*2) + (5*4)));
        assertThat(stacker.smallStackableItemsArea(), is((1*2) + (3*3)));
    }
}
