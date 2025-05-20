import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class HashingExterno {
    private Map<Integer, Bucket> buckets;
    private int profundidadeGlobal;
    private int proximoEndereco;

    public HashingExterno() {
        this.buckets = new HashMap<>();
        this.profundidadeGlobal = 1;
        this.proximoEndereco = 0;
        // Inicializa com dois buckets
        criarNovoBucket();
        criarNovoBucket();
    }

    private void criarNovoBucket() {
        buckets.put(proximoEndereco, new Bucket(proximoEndereco));
        proximoEndereco++;
    }

    private int calcularHash(int chave) {
        return chave % (int) Math.pow(2, profundidadeGlobal);
    }

    private int calcularEnderecoBucket(int hash) {
        return hash % buckets.size();
    }

    public void inserir(int chave) {
        int hash = calcularHash(chave);
        int enderecoBucket = calcularEnderecoBucket(hash);
        Bucket bucket = buckets.get(enderecoBucket);

        if (bucket.estaCheio()) {
            if (bucket.getProfundidadeLocal() == profundidadeGlobal) {
                // Duplicar diretório
                duplicarDiretorio();
                // Tentar inserir novamente
                inserir(chave);
            } else {
                // Dividir bucket
                dividirBucket(bucket);
                // Tentar inserir novamente
                inserir(chave);
            }
        } else {
            bucket.adicionarRegistro(chave);
        }
    }

    private void duplicarDiretorio() {
        profundidadeGlobal++;
        Map<Integer, Bucket> novosBuckets = new HashMap<>();
        
        // Duplica os buckets existentes
        for (Map.Entry<Integer, Bucket> entry : buckets.entrySet()) {
            novosBuckets.put(entry.getKey(), entry.getValue());
            novosBuckets.put(entry.getKey() + buckets.size(), entry.getValue());
        }
        
        buckets = novosBuckets;
    }

    private void dividirBucket(Bucket bucketAntigo) {
        int enderecoNovo = proximoEndereco++;
        Bucket bucketNovo = new Bucket(enderecoNovo);
        bucketNovo.incrementarProfundidadeLocal();
        bucketAntigo.incrementarProfundidadeLocal();

        // Redistribuir registros
        List<Integer> registros = new ArrayList<>(bucketAntigo.getRegistros());
        bucketAntigo.limparRegistros();

        for (int registro : registros) {
            int hash = calcularHash(registro);
            if (hash % (int) Math.pow(2, bucketAntigo.getProfundidadeLocal()) == bucketAntigo.getEndereco()) {
                bucketAntigo.adicionarRegistro(registro);
            } else {
                bucketNovo.adicionarRegistro(registro);
            }
        }

        buckets.put(enderecoNovo, bucketNovo);
    }

    public boolean buscar(int chave) {
        int hash = calcularHash(chave);
        int enderecoBucket = calcularEnderecoBucket(hash);
        Bucket bucket = buckets.get(enderecoBucket);
        return bucket.getRegistros().contains(chave);
    }

    public void imprimirEstado() {
        System.out.println("Profundidade Global: " + profundidadeGlobal);
        System.out.println("Número de Buckets: " + buckets.size());
        for (Bucket bucket : buckets.values()) {
            System.out.println(bucket);
        }
        System.out.println("------------------------");
    }
} 