
package co.sena.mercasena.chain;

import java.math.BigDecimal;

public class ValidadorMontoMinimo extends Validador {

    private static final BigDecimal MINIMO =
            BigDecimal.valueOf(20_000);

    @Override
    public Resultado validar(SolicitudPedido s) {

        if (s.total().compareTo(MINIMO) < 0) {
            return Resultado.MONTO_MINIMO;
        }

        // Si cumple, debe continuar al siguiente validador.
        return pasarAlSiguiente(s);
    }
}
