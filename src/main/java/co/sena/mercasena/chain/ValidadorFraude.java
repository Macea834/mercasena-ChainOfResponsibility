
package co.sena.mercasena.chain;

import java.math.BigDecimal;

public class ValidadorFraude extends Validador {

    private static final BigDecimal LIMITE_CLIENTE_NUEVO =
            BigDecimal.valueOf(5_000_000);

    @Override
    public Resultado validar(SolicitudPedido s) {

        if (s.clienteNuevo()
                && s.total().compareTo(LIMITE_CLIENTE_NUEVO) > 0) {
            return Resultado.REVISION_FRAUDE;
        }

        return pasarAlSiguiente(s);
    }
}
