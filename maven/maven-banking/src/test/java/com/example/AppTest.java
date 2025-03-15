package com.example;

import org.json.JSONObject;
import org.junit.jupiter.api.Test;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class AppTest {

    @Test
    public void testLeerArchivo() {
        // Ruta del archivo de prueba
        String rutaArchivo = "/workspaces/BuildSolutions_SebastianR/maven/maven-banking/src/resources/transactions.txt";

        // Leer el archivo
        String contenido = App.leerArchivo(rutaArchivo);

        // Verificar que el contenido no está vacío
        assertNotNull(contenido);
        assertFalse(contenido.isEmpty());
    }

    @Test
    public void testObtenerTransacciones() {
        // Ruta del archivo de prueba
        String rutaArchivo = "/workspaces/BuildSolutions_SebastianR/maven/maven-banking/src/resources/transactions.txt";

        // Usuario específico para probar
        String usuario = "juan.jose@urosario.edu.co";

        // Obtener las transacciones del usuario
        List<JSONObject> transacciones = App.obtenerTransacciones(rutaArchivo, usuario);

        // Verificar que se obtuvieron transacciones
        assertNotNull(transacciones);
        assertFalse(transacciones.isEmpty());

        // Verificar que todas las transacciones pertenecen al usuario
        for (JSONObject transaccion : transacciones) {
            assertEquals(usuario, transaccion.getString("usuario"));
        }
    }

    @Test
public void testGenerarExtracto() {
    // Crear una lista de transacciones de prueba
    List<JSONObject> transacciones = new ArrayList<>();
    transacciones.add(new JSONObject()
            .put("fecha", "2025-02-11 14:17:21.921536")
            .put("tipo", "Deposit")
            .put("monto", 50.0));
    transacciones.add(new JSONObject()
            .put("fecha", "2025-02-15 10:30:15.123456")
            .put("tipo", "Withdrawal")
            .put("monto", -20.0));

    // Usuario de prueba
    String usuario = "juan.jose@urosario.edu.co";

    // Generar el extracto
    App.generarExtracto(usuario, transacciones);

    // Verificar que el archivo se creó
    File archivoExtracto = new File(usuario + "_extracto.txt");
    assertTrue(archivoExtracto.exists());

    // Leer el archivo generado y verificar su contenido
    try (BufferedReader reader = new BufferedReader(new FileReader(archivoExtracto))) {
        String linea;
        StringBuilder contenido = new StringBuilder();
        while ((linea = reader.readLine()) != null) {
            contenido.append(linea).append("\n");
        }

        // Verificar que el contenido contiene la información esperada
        assertTrue(contenido.toString().contains("Extracto Bancario - " + usuario));
        assertTrue(contenido.toString().contains("Fecha: 2025-02-11 14:17:21.921536"));
        assertTrue(contenido.toString().contains("Tipo: Deposit"));
        assertTrue(contenido.toString().contains("Monto: 50.0"));
        assertTrue(contenido.toString().contains("Tipo: Withdrawal"));
        assertTrue(contenido.toString().contains("Monto: -20.0"));
    } catch (IOException e) {
        fail("Error al leer el archivo de extracto: " + e.getMessage());
    } finally {
        // Eliminar el archivo de prueba después de la verificación
        if (archivoExtracto.exists()) {
            archivoExtracto.delete();
        }
    }
}

}
