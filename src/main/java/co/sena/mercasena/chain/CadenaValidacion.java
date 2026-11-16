
package co.sena.mercasena.chain;

/**
 * Construye la cadena en el orden:
 * Stock -> Monto mínimo -> Fraude.
 */
public final class CadenaValidacion {

    private CadenaValidacion() {
    }

    public static Validador crear() {
        Validador inicio = new ValidadorStock();

        inicio.enlazar(new ValidadorMontoMinimo())
              .enlazar(new ValidadorFraude());

        return inicio;
    }
}
