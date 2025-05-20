public class Main {
    public static void main(String[] args) {
        HashingExterno hashing = new HashingExterno();
        
        System.out.println("Estado inicial:");
        hashing.imprimirEstado();

        // Inserindo alguns valores para testar
        int[] valores = {10, 20, 30, 40, 50, 60, 70, 80, 90, 100};
        
        System.out.println("Inserindo valores...");
        for (int valor : valores) {
            System.out.println("\nInserindo valor: " + valor);
            hashing.inserir(valor);
            hashing.imprimirEstado();
        }

        // Testando busca
        System.out.println("\nTestando busca:");
        for (int valor : valores) {
            System.out.println("Buscando " + valor + ": " + hashing.buscar(valor));
        }
        System.out.println("Buscando 999 (não existe): " + hashing.buscar(999));
    }
} 