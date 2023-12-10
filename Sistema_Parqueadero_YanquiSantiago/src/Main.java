import java.util.Scanner;
//////SANTIAGO YANQUI/////
public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        Parqueadero parqueadero = new Parqueadero();
        int opc = 0;
        boolean estado=true;
        System.out.println("--------BIENVENIDO AL SISTEMA DE PARQUEADERO--------");
        while (estado == true) {
            System.out.println("1. Ingresar un carro al parqueadero");
            System.out.println("2. Dar salida a un carro del parqueadero");
            System.out.println("3. Informar los ingresos del parqueadero");
            System.out.println("4. Consultar la cantidad de puestos disponibles");
            System.out.println("5. Avanzar el reloj del parqueadero");
            System.out.println("6. Cambiar la tarifa del parqueadero");
            System.out.println("7. Salir Del Sistema");
            System.out.println("Seleccione una opción:");
            opc = scanner.nextInt();
            switch (opc) {
                case 1:
                    System.out.println("Ingrese la placa del carro:");
                    String placa = scanner.next();
                    System.out.println("Hora de llegada:");
                    int horaLlegada = scanner.nextInt();
                    Carro carro = new Carro(placa, horaLlegada);
                    parqueadero.entrarCarro(placa);
                    System.out.println("El carro ha ingresado");
                    break;
                case 2:
                    System.out.println("Ingrese la placa del carro a salir:");
                    String placaSalida = scanner.next();
                    parqueadero.sacarCarro(placaSalida);
                    System.out.println("El auto ha salido");
                    break;
                case 3:
                    int ingresos = parqueadero.darMontoCaja();
                    System.out.println("Los ingresos actuales son $: " + ingresos);
                    break;
                case 4:
                    int puestosDisponibles = parqueadero.calcularPuestosLibres();
                    System.out.println("Puestos libres: " + puestosDisponibles);
                    break;
                case 5:
                    parqueadero.avanzarHora();
                    System.out.println("Se ha avanzado 1 hora");
                    break;
                case 6:
                    System.out.println("Ingrese nueva tarifa:");
                    int nuevaTarifa = scanner.nextInt();
                    parqueadero.cambiarTarifa(nuevaTarifa);
                    System.out.println("Tarifa actualizada");
                    break;
                case 7:
                    estado=false;
                    System.out.println("-----HA SALIDO DEL SISTEMA-----");
                    break;
                default:
                    System.out.println("Opción NO disponible");
                    break;
            }
        }
    }
}