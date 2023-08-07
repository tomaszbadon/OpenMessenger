package net.bean.java.open.messenger.service.implementation;

import io.vavr.control.Try;
import org.junit.jupiter.api.Test;

import static io.vavr.API.$;
import static io.vavr.API.Case;
import static io.vavr.Predicates.instanceOf;

public class SimpleTest {

    @Test
    void test() {

        Try<Integer> integer = Try.of(() -> Integer.parseInt("1234uhu"))
                                  .onFailure(NumberFormatException.class, (e) -> System.out.println("NumberFormatException has occurred"));

        integer = integer.mapFailure(Case($(instanceOf(NumberFormatException.class)), t -> new RuntimeException("Nothing has happened")));

        integer = integer.andThen(i -> System.out.println("The number is: " + i));

        integer = integer.recover(RuntimeException.class,  (r) -> 123);

        integer = integer.andFinallyTry(() -> { throw new IllegalArgumentException();});

        integer = integer.recover(IllegalArgumentException.class, 3);

        int a = integer.get();

        System.out.println(a);

    }

}
