import java.util.ArrayList;
import java.util.List;

public class Bucket {
    private static final int BUCKET_SIZE = 4; // Tamanho fixo do bucket
    private List<Integer> registros;
    private int profundidadeLocal;
    private int endereco;

    public Bucket(int endereco) {
        this.registros = new ArrayList<>();
        this.profundidadeLocal = 1;
        this.endereco = endereco;
    }

    public boolean estaCheio() {
        return registros.size() >= BUCKET_SIZE;
    }

    public boolean adicionarRegistro(int registro) {
        if (!estaCheio()) {
            registros.add(registro);
            return true;
        }
        return false;
    }

    public List<Integer> getRegistros() {
        return new ArrayList<>(registros);
    }

    public void limparRegistros() {
        registros.clear();
    }

    public int getProfundidadeLocal() {
        return profundidadeLocal;
    }

    public void incrementarProfundidadeLocal() {
        profundidadeLocal++;
    }

    public int getEndereco() {
        return endereco;
    }

    @Override
    public String toString() {
        return "Bucket{" +
                "registros=" + registros +
                ", profundidadeLocal=" + profundidadeLocal +
                ", endereco=" + endereco +
                '}';
    }
} 