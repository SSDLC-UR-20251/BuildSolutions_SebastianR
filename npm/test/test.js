const { transferir } = require('../src/app');

test('Transferencia entre cuentas', () => {
    // TODO: Crear una prueba que verifique si la transferencia fue exitosa.
    const resultado = transferir('juan.jose@urosario.edu.co', 'sara.palaciosc@urosario.edu.co', 30);
    expect(resultado.exito).toBe(true);
    expect(resultado.mensaje).toBe('Transferencia de 30 realizada correctamente de juan.jose@urosario.edu.co a sara.palaciosc@urosario.edu.co.');
    // Completar
});

test('Transferencia con saldo insuficiente', () => {
    // TODO: Crear una prueba que verifique que la transferencia falla si el saldo de la cuenta de origen es insuficiente.
    const resultado = transferir('juan.jose@urosario.edu.co', 'sara.palaciosc@urosario.edu.co', 1000);
    expect(resultado.exito).toBe(false);
    expect(resultado.mensaje).toBe('Saldo insuficiente en la cuenta de juan.jose@urosario.edu.co');
    // Completar
});
