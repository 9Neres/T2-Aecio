package exercicio_4;

public class Main {
    public static void main(String[] args) {
        HeapFile heapFile = new HeapFile();
        
        // Teste 1: Inserção de lote
        System.out.println("Teste 1: Inserção de 1000 registros");
        heapFile.inserirLote(1000);
        heapFile.estatisticas();

        // Teste 2: Busca de registros existentes
        System.out.println("\nTeste 2: Busca de registros existentes");
        heapFile.buscarRegistro(500);  // Registro no meio
        heapFile.buscarRegistro(999);  // Último registro
        heapFile.buscarRegistro(0);    // Primeiro registro

        // Teste 3: Busca de registro inexistente
        System.out.println("\nTeste 3: Busca de registro inexistente");
        heapFile.buscarRegistro(1001);

        // Teste 4: Inserção adicional e nova busca
        System.out.println("\nTeste 4: Inserção adicional e nova busca");
        heapFile.inserirRegistro(1001);
        heapFile.buscarRegistro(1001);
        heapFile.estatisticas();
    }
} 