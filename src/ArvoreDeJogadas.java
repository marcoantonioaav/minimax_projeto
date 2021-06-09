import java.util.Random;
import java.util.ArrayList;
import java.util.List;

public class ArvoreDeJogadas {
    private int[][] tabuleiro;
    private int pontos;
    private int profundidade;
    private List<ArvoreDeJogadas> filhos;
    private static final int MAX_PONTOS = 20;
    private static final int MIN_PONTOS = -20;

    public ArvoreDeJogadas() {
        this.filhos = new ArrayList<ArvoreDeJogadas>();
    }
    public int[][] getTabuleiro() {
        return tabuleiro;
    }
    public void setTabuleiro(int[][] tabuleiro) {
        this.tabuleiro = tabuleiro;
    }
    public int getPontos() {
        return pontos;
    }
    public void setPontos(int pontos) {
        this.pontos = pontos;
    }
    public List<ArvoreDeJogadas> getFilhos() {
        return filhos;
    }
    public void setFilhos(List<ArvoreDeJogadas> filhos) {
        this.filhos = filhos;
    }
    public ArvoreDeJogadas getFilho(int index) {
        return filhos.get(index);
    }
    public void setFilho(int index, ArvoreDeJogadas filho) {
        this.filhos.set(index, filho);
    }
    public void addFilho(ArvoreDeJogadas filho) {
        this.filhos.add(filho);
    }
    public int getProfundidade() {
        return profundidade;
    }
    public void setProfundidade(int profundidade) {
        this.profundidade = profundidade;
    }
    private int randomInt(int min, int max){
        Random random = new Random();
        return random.ints(min,(max+1)).findFirst().getAsInt();
    }
    public int calculaPontos(){
        return randomInt(MIN_PONTOS, MAX_PONTOS);
    }

    public void geraArvoreAleatoria(int profundidade){
        setProfundidade(profundidade);
        if(profundidade == 0){
            setPontos(calculaPontos());
        }
        else{
            int numeroDeFilhos = randomInt(2,4);
            for(int i=0; i<numeroDeFilhos; i++){
                addFilho(new ArvoreDeJogadas());
                getFilho(i).geraArvoreAleatoria(profundidade-1);;
            }
        }
    }

    private int numeroNosNivel(int nivelAlvo, int nivelAtual){
        if(nivelAlvo == nivelAtual){
            return 1;
        }
        else{
            int resultado = 0;
            for(int i=0; i<getFilhos().size(); i++){
                resultado += getFilho(i).numeroNosNivel(nivelAlvo, nivelAtual+1);
            }
            return resultado;
        }
    }
    public int numeroNosNivel(int nivel){
        return numeroNosNivel(nivel, 0);
    }

    public void printaArvore(){
        System.out.println(pontos+" ");
        for(int i=0; i<getFilhos().size(); i++){
            getFilho(i).printaArvore();
        }
    }
    public int getMenorFolha(){
        if (this.filhos.isEmpty()){
            return this.pontos;
        }
        else{
            int menor = getFilho(0).getMenorFolha();
            for(int i=1; i<getFilhos().size(); i++){
                menor = Math.min(menor,getFilho(i).getMenorFolha());
            }
            return menor;
        }
    }
}