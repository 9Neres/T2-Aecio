package exercicio_4;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.time.Duration;
import java.time.Instant;

public class HeapFile {
    private static final int BLOCK_SIZE = 4096; // 4KB por bloco
    private static final int RECORD_SIZE = 100; // 100 bytes por registro
    private List<Block> blocks;
    private int totalRecords;
    private Random random;

    public HeapFile() {
        this.blocks = new ArrayList<>();
        this.totalRecords = 0;
        this.random = new Random();
    }

    private static class Block {
        List<Record> records;
        int blockId;

        public Block(int blockId) {
            this.records = new ArrayList<>();
            this.blockId = blockId;
        }

        public boolean isFull() {
            return records.size() * RECORD_SIZE >= BLOCK_SIZE;
        }
    }

    private static class Record {
        int id;
        byte[] data;

        public Record(int id) {
            this.id = id;
            this.data = new byte[RECORD_SIZE - 4]; // -4 para o ID
        }
    }

    public void inserirRegistro(int id) {
        Record record = new Record(id);
        random.nextBytes(record.data); // Preenche com dados aleatórios

        // Tenta encontrar um bloco com espaço
        for (Block block : blocks) {
            if (!block.isFull()) {
                block.records.add(record);
                totalRecords++;
                return;
            }
        }

        // Se não encontrou bloco com espaço, cria um novo
        Block newBlock = new Block(blocks.size());
        newBlock.records.add(record);
        blocks.add(newBlock);
        totalRecords++;
    }

    public Record buscarRegistro(int id) {
        Instant start = Instant.now();
        int blocosAcessados = 0;

        for (Block block : blocks) {
            blocosAcessados++;
            for (Record record : block.records) {
                if (record.id == id) {
                    Instant end = Instant.now();
                    System.out.printf("Registro encontrado no bloco %d após acessar %d blocos%n", 
                        block.blockId, blocosAcessados);
                    System.out.printf("Tempo de busca: %d ms%n", 
                        Duration.between(start, end).toMillis());
                    return record;
                }
            }
        }

        Instant end = Instant.now();
        System.out.printf("Registro não encontrado após acessar %d blocos%n", blocosAcessados);
        System.out.printf("Tempo de busca: %d ms%n", 
            Duration.between(start, end).toMillis());
        return null;
    }

    public void inserirLote(int quantidade) {
        Instant start = Instant.now();
        for (int i = 0; i < quantidade; i++) {
            inserirRegistro(totalRecords + i);
        }
        Instant end = Instant.now();
        System.out.printf("Inserção de %d registros concluída em %d ms%n", 
            quantidade, Duration.between(start, end).toMillis());
    }

    public void estatisticas() {
        System.out.println("\nEstatísticas do Heap File:");
        System.out.printf("Total de blocos: %d%n", blocks.size());
        System.out.printf("Total de registros: %d%n", totalRecords);
        System.out.printf("Tamanho médio dos blocos: %.2f%%%n", 
            (double) totalRecords * RECORD_SIZE / (blocks.size() * BLOCK_SIZE) * 100);
        
        // Calcula distribuição dos registros
        int[] registrosPorBloco = new int[blocks.size()];
        for (int i = 0; i < blocks.size(); i++) {
            registrosPorBloco[i] = blocks.get(i).records.size();
        }
        
        System.out.println("\nDistribuição de registros por bloco:");
        for (int i = 0; i < registrosPorBloco.length; i++) {
            System.out.printf("Bloco %d: %d registros%n", i, registrosPorBloco[i]);
        }
    }
} 