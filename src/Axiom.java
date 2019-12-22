import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Proxy;

public class Axiom {
    /**
     * The only real axiom of this system.
     * Axiom: Proof by contradiction works.
     * @param doubleNegation Proof by contradiction
     * @param <A> Statement to be proven
     * @return The proven statement.
     */
    public static <A extends T> A contradiction(If<If<A, F>, F> doubleNegation) {
        return null;
    }

    public static <A extends T> A explosion(F f) {
        return contradiction(afIf -> f);
    }

    public static <A extends T, B extends T> A conditionalElimination(If<If<A, B>, A> and) {
        return contradiction(afIf -> afIf.apply(and.apply(a -> explosion(afIf.apply(a)))));
    }

    public static <A extends T, B extends T> If<If<A, If<B, F>>, F> conjunctionIntroduction(A a, B b) {
        return aIfIf -> aIfIf.apply(a).apply(b);
    }

    public static <A extends T, B extends T> A conjunctionElimination(If<If<A, If<B, F>>, F> and) {
        return contradiction(afIf -> and.apply(a -> explosion(afIf.apply(a))));
    }

    public static <A extends T, B extends T> B conjunctionElimination2(If<If<A, If<B, F>>, F> and) {
        return contradiction(bfIf -> and.apply(a -> bfIf));
    }

    public static <A extends T, B extends T> If<A, F> negationIntroduction(If<If<If<A, B>, If<If<A, If<B, F>>, F>>, F> a) {
        return a1 -> conjunctionElimination2(a).apply(a1).apply(conjunctionElimination(a).apply(a1));
    }

    public static <A extends T, B extends T> If<A, B> negationElimination(If<A, F> given) {
        return a -> explosion(given.apply(a));
    }

    public static <A extends T, B extends T> If<If<A, F>, B> disjunctionIntroduction(A a) {
        return afIf -> explosion(afIf.apply(a));
    }

    public static <A extends T, B extends T> If<If<A, F>, B> disjunctionIntrodution2(B b) {
        return afIf -> b;
    }

    public static <A extends T, B extends T, C extends T> C disjunctionElimination(If<If<If<If<If<If<A, F>, B>, If<If<A, C>, F>>, F>, If<If<B, C>, F>>, F> given) {
        If<If<A, F>, B> first = conjunctionElimination(conjunctionElimination(given));
        If<A, C> second = conjunctionElimination2(conjunctionElimination(given));
        If<B, C> third = conjunctionElimination2(given);

        return contradiction(cfIf -> {
            If<A, F> a = a1 -> cfIf.apply(second.apply(a1));
            If<B, F> b = b1 -> cfIf.apply(third.apply(b1));
            return b.apply(first.apply(a));
        });
    }
}
