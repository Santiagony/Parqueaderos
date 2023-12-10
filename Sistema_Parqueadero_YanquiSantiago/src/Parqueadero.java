import java.util.ArrayList;
public class Parqueadero {
    public static final int TAMANO = 40;
    public static final int NO_HAY_PUESTO = -1;
    public static final int PARQUEADERO_CERRADO = -2;
    public static final int CARRO_NO_EXISTE = -3;
    public static final int CARRO_YA_EXISTE = -4;
    public static final int HORA_INICIAL = 6;
    public static final int HORA_CIERRE = 20;
    public static final int TARIFA_INICIAL = 1200;
    private Puesto puestos[];
    private int tarifa;
    private int caja;
    private int horaActual;
    private boolean abierto;
    public Parqueadero( ){
        horaActual = HORA_INICIAL;
        abierto = true;
        tarifa = TARIFA_INICIAL;
        caja = 0;
        puestos = new Puesto[TAMANO];
        for( int i = 0; i < TAMANO; i++ )
            puestos[ i ] = new Puesto( i );
    }
    public String darPlacaCarro( int pPosicion ){
        String respuesta = "    ";
        if( estaOcupado( pPosicion ) ){
            respuesta = "Placa: " + puestos[ pPosicion ].darCarro( ).darPlaca( );
        }
        else{
            respuesta = "No hay un carro en esta posici�n";
        }
        return respuesta;
    }
    public int entrarCarro( String pPlaca ){
        int resultado = 0;
        if( !abierto ){
            resultado = PARQUEADERO_CERRADO;
        }
        else{
            int numPuestoCarro = buscarPuestoCarro( pPlaca.toUpperCase( ) );
            if( numPuestoCarro != CARRO_NO_EXISTE ){
                resultado = CARRO_YA_EXISTE;
            }
            resultado = buscarPuestoLibre( );
            if( resultado != NO_HAY_PUESTO ){
                Carro carroEntrando = new Carro( pPlaca, horaActual );
                puestos[ resultado ].parquearCarro( carroEntrando );
            }
        }
        return resultado;
    }
    public int sacarCarro( String pPlaca ){
        int resultado = 0;
        if( !abierto ){
            resultado = PARQUEADERO_CERRADO;
        }
        else{
            int numPuesto = buscarPuestoCarro( pPlaca.toUpperCase( ) );
            if( numPuesto == CARRO_NO_EXISTE ){
                resultado = CARRO_NO_EXISTE;
            }
            else{
                Carro carro = puestos[ numPuesto ].darCarro( );
                int nHoras = carro.darTiempoEnParqueadero( horaActual );
                int porPagar = nHoras * tarifa;
                caja = caja + porPagar;
                puestos[ numPuesto ].sacarCarro( );
                resultado = porPagar;
            }
        }
        return resultado;
    }
    public int darMontoCaja( ){
        return caja;
    }
    public int calcularPuestosLibres( ){
        int puestosLibres = 0;
        for( Puesto puesto : puestos ){
            if( !puesto.estaOcupado( ) ){
                puestosLibres = puestosLibres + 1;
            }
        }
        return puestosLibres;
    }
    public void cambiarTarifa( int pTarifa ){
        tarifa = pTarifa;
    }
    private int buscarPuestoLibre( ){
        int puesto = NO_HAY_PUESTO;
        for( int i = 0; i < TAMANO && puesto == NO_HAY_PUESTO; i++ ){
            if( !puestos[ i ].estaOcupado( ) )
            {
                puesto = i;
            }
        }
        return puesto;
    }
    private int buscarPuestoCarro( String pPlaca ){
        int puesto = CARRO_NO_EXISTE;
        for( int i = 0; i < TAMANO && puesto == CARRO_NO_EXISTE; i++ ){
            if( puestos[ i ].tieneCarroConPlaca( pPlaca ) )
            {
                puesto = i;
            }
        }
        return puesto;
    }
    public void avanzarHora( ){
        if( horaActual <= HORA_CIERRE ){
            horaActual = ( horaActual + 1 );
        }
        if( horaActual == HORA_CIERRE ){
            abierto = false;
        }
    }
    public int darHoraActual( ){
        return horaActual;
    }
    public boolean estaAbierto( ){
        return abierto;
    }
    public int darTarifa( ){
        return tarifa;
    }
    public boolean estaOcupado( int pPuesto ){
        boolean ocupado = puestos[ pPuesto ].estaOcupado( );
        return ocupado;
    }
    public double darTiempoPromedio(){
        int totalTiempo = 0;
        int numCarros = 0;
        for (Puesto puesto : puestos){
            Carro carro = puesto.darCarro();
            if (carro != null) {
                totalTiempo += carro.darTiempoEnParqueadero(horaActual);
                numCarros++;
            }
        }
        return totalTiempo / numCarros;
    }
    public Carro hayCarroMasHoras(){
        Carro carroMasHoras = null;
        int maxHoras = 0;
        for (Puesto puesto : puestos){
            Carro carro = puesto.darCarro();
            if (carro != null) {
                int tiempoEnParqueadero = carro.darTiempoEnParqueadero(horaActual);
                if (tiempoEnParqueadero >= maxHoras) {
                    maxHoras = tiempoEnParqueadero;
                    carroMasHoras = carro;
                }
            }
        }
        return carroMasHoras;
    }
    public boolean hayCarroMasDeOchoHoras(){
        boolean masOchoHoras = false;
        for (Puesto puesto : puestos) {
            Carro carro = puesto.darCarro();
            if (carro != null) {
                int tiempoEnParqueadero = carro.darTiempoEnParqueadero(horaActual);
                if (tiempoEnParqueadero>8) {
                    masOchoHoras = true;
                }
            }
        }
        return masOchoHoras;
    }
    public ArrayList<Carro> darCarrosMasDeTresHorasParqueados() {
        ArrayList<Carro> carrosMasDeTresHoras = new ArrayList<>();
        for (Puesto puesto : puestos){
            Carro carro = puesto.darCarro();
            if (carro != null) {
                int tiempoEnParqueadero = carro.darTiempoEnParqueadero(horaActual);
                if (tiempoEnParqueadero > 3) {
                    carrosMasDeTresHoras.add(carro);
                }
            }
        }
        return carrosMasDeTresHoras;
    }
    public boolean hayCarrosPlacaIgual(){
        boolean placaIgual = false;
        ArrayList<String> placas = new ArrayList<>();
        for (Puesto puesto : puestos) {
            Carro carro = puesto.darCarro();
            if (carro != null) {
                String placa = carro.darPlaca();
                if (placas.contains(placa)) {
                    placaIgual = true;
                } else {
                    placas.add(placa);
                }
            }
        }
        return placaIgual;
    }
    public int contarCarrosQueComienzanConPlacaPB(){
        int contPB = 0;
        for (Puesto puesto : puestos) {
            Carro carro = puesto.darCarro();
            if (carro != null && carro.darPlaca().startsWith("PB")) {
                contPB++;
            }
        }
        return contPB;
    }
    public boolean hayCarroCon24Horas(){
        boolean mas24Horas = false;
        for (Puesto puesto : puestos) {
            Carro carro = puesto.darCarro();
            if (carro != null && carro.darTiempoEnParqueadero(horaActual) >= 24) {
                mas24Horas = true;
            }
        }
        return mas24Horas;
    }
    public String metodo1( ){
        String mensaje = "";
        int cantidadCarrosPlacaPB = contarCarrosQueComienzanConPlacaPB();
        boolean hayCarro24Horas = hayCarroCon24Horas();
        if (hayCarro24Horas) {
            mensaje = "Sí";
        } else {
            mensaje = "No";
        }
        return "Cantidad de carros con placa PB"+ cantidadCarrosPlacaPB +"Hay carro parqueado por 24 o más horas: " +mensaje;
    }
    public int desocuparParqueadero(){
        int carrosSacados = 0;
        for (Puesto puesto : puestos) {
            Carro carro = puesto.darCarro();
            if (carro != null) {
                puesto.sacarCarro();
                carrosSacados++;
            }
        }
        return carrosSacados;
    }
    public String metodo2( ){
        int carrosSacados = desocuparParqueadero();
        return "Carros sacados: " + carrosSacados;
    }
}