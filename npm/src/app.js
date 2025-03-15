const fs = require('fs');

// Función para leer el archivo transactions.txt
function leerArchivo() {
    try {
        const data = fs.readFileSync('transactions.txt', 'utf8');
        return JSON.parse(data);
    } catch (err) {
        console.error('Error leyendo el archivo:', err);
        return {};
    }
}

function escribirArchivo(data) {
    try {
        fs.writeFileSync('transactions.txt', JSON.stringify(data, null, 2), 'utf8');
        console.log('Archivo escrito correctamente');
    } catch (err) {
        console.error('Error escribiendo el archivo:', err);
    }
}

function calcularSaldo(usuario) {
    const transacciones = leerArchivo();
    if (!transacciones[usuario]) {
        console.error('Usuario no encontrado');
        return 0;
    }

    const usuarioTransacciones = transacciones[usuario];
    let saldo = 0;

    usuarioTransacciones.forEach(transaccion => {
        // Parse the balance as a number since it is stored as a string
        const monto = parseFloat(transaccion.balance);

        // Check if the transaction type is 'Deposit' or 'Withdrawal'
        if (transaccion.type === 'Deposit') {
            saldo += monto;
        } else if (transaccion.type === 'Withdrawal') {
            saldo -= monto;
        }
    });

    return saldo;
}

function transferir(de, para, monto) {
    const transacciones = leerArchivo();

    if (!transacciones[de]) {
        return { exito: false, mensaje: `Usuario ${de} no encontrado` };
    }

    if (!transacciones[para]) {
        return { exito: false, mensaje: `Usuario ${para} no encontrado` };
    }

    const saldoDe = calcularSaldo(de);
    console.log(saldoDe);
    if (saldoDe < monto) {
        return { exito: false, mensaje: `Saldo insuficiente en la cuenta de ${de}` };
    }

    transacciones[de].push({ tipo: 'retiro', monto });
    transacciones[para].push({ tipo: 'deposito', monto });

    escribirArchivo(transacciones);

    return {
        exito: true,
        mensaje: `Transferencia de ${monto} realizada correctamente de ${de} a ${para}.`
    };
}

const resultado = transferir('juan.jose@urosario.edu.co', 'sara.palaciosc@urosario.edu.co', 50);
console.log(resultado.mensaje);

// Exportar las funciones para pruebas
module.exports = { transferir };
