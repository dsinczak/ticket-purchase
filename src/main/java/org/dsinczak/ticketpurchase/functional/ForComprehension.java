package org.dsinczak.ticketpurchase.functional;

import io.vavr.Function3;
import io.vavr.Tuple;
import io.vavr.control.Either;

public abstract class ForComprehension {

    private ForComprehension() {
    }

    /*
     * This is the result of nostalgia resulting from longing for Scala
     */
    public static <E, T1, T2, T3, R> Either<E, R> For(
            Either<E, T1> either1,
            Either<E, T2> either2,
            Either<E, T3> either3,
            Function3<T1, T2, T3, Either<E, R>> producer

    ) {
        return either1
                .flatMap(t1 ->
                        either2.map(t2 -> Tuple.of(t1, t2))
                ).flatMap(t1t2 ->
                        either3.map(t3 -> Tuple.of(t1t2._1(), t1t2._2(), t3))
                ).flatMap(t1t2t3 ->
                        producer.apply(t1t2t3._1(), t1t2t3._2(), t1t2t3._3())
                );
    }

}
