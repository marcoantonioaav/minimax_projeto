import java.util.Random;
import java.util.ArrayList;
import java.util.List;

public class ArvoreDeJogadas {
    private int[][] copiaTabuleiro;
    private int pontos;
    private int profundidade;
    private List<ArvoreDeJogadas> filhos;
    private boolean acessado = false;
    public  int maxPontos = 10;
    public  int minPontos = -10;
    public void setDificulty(int dificuldade){
        if (dificuldade == 1){
            setMaxPontos(3);
            setMinPontos(-3);
        }
        if (dificuldade == 2){
            setMaxPontos(10);
            setMinPontos(-10);
        }
        if (dificuldade == 3){
            setMaxPontos(100);
            setMinPontos(-100);
        }
    }
    public void setMaxPontos(int maxPontos) {
        this.maxPontos = maxPontos;
    }
    public void setMinPontos(int minPontos) {
        this.minPontos = minPontos;
    }
    public int getMaxPontos() {
        return maxPontos;
    }
    public int getMinPontos() {
        return minPontos;
    }

    public ArvoreDeJogadas() {
        this.filhos = new ArrayList<ArvoreDeJogadas>();
    }
    public int[][] getCopiaTabuleiro() {
        return copiaTabuleiro;
    }
    public void setCopiaTabuleiro(int[][] copiaTabuleiro) {
        this.copiaTabuleiro = copiaTabuleiro;
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
    public boolean isAcessado() {
        return acessado;
    }
    public void setAcessado(boolean acessado) {
        this.acessado = acessado;
    }
    private int randomInt(float min, float max){
        Random random = new Random();
        return random.ints((int)min,(int)(max+1)).findFirst().getAsInt();
    }
    public int geraPontosAleatorios() {
        return randomInt(getMinPontos(), getMaxPontos());
    }

    public void geraArvoreAleatoria(int profundidade) {
        setProfundidade(profundidade);
        if(profundidade == 0) {
            setPontos(geraPontosAleatorios());
        }
        else {
            int numeroDeFilhos = randomInt(2,4);
            for(int i=0; i<numeroDeFilhos; i++) {
                addFilho(new ArvoreDeJogadas());
                getFilho(i).geraArvoreAleatoria(profundidade-1);;
            }
        }
    }

    private int numeroNosNivel(int nivelAlvo, int nivelAtual) {
        if(nivelAlvo == nivelAtual){
            if(isAcessado())
                return 1;
            else
                return 0;
        }
        else{
            int resultado = 0;
            for(int i=0; i<getFilhos().size(); i++) {
                resultado += getFilho(i).numeroNosNivel(nivelAlvo, nivelAtual+1);
            }
            return resultado;
        }
    }
    public int numeroNosNivel(int nivel) {
        return numeroNosNivel(nivel, 0);
    }
    public int numeroNosTotal() {
        if(isAcessado()) {
            if(getProfundidade() == 0) {
                return 1;
            }
            else {
                int resultado = 1;
                for(int i=0; i<getFilhos().size(); i++) {
                    resultado += getFilho(i).numeroNosTotal();
                }
                return resultado;
            }
        }
        else{
            return 0;
        }
    }

    public void printaArvore() {
        System.out.println(getPontos()+" ");
        for(int i=0; i<getFilhos().size(); i++) {
            getFilho(i).printaArvore();
        }
    }
    public float getMenorFolha() {
        if (getFilhos().isEmpty()) {
            return getPontos();
        }
        else {
            float menor = getFilho(0).getMenorFolha();
            for(int i=1; i<getFilhos().size(); i++) {
                menor = Math.min(menor,getFilho(i).getMenorFolha());
            }
            return menor;
        }
    }
    private int minimax(boolean estaMaximizando) {
        setAcessado(true);
        if(getProfundidade() != 0) {
            if(estaMaximizando) {
                int pontuacaoMaxima = Integer.MIN_VALUE;
                int pontuacaoFilho;
                for(int i=0; i<getFilhos().size(); i++) {
                    pontuacaoFilho = getFilho(i).minimax(false);
                    pontuacaoMaxima = Math.max(pontuacaoMaxima, pontuacaoFilho);
                }
                setPontos(pontuacaoMaxima);
                return pontuacaoMaxima;
            }
            else {
                int pontuacaoMinima = Integer.MAX_VALUE;
                int pontuacaoFilho;
                for(int i=0; i<getFilhos().size(); i++) {
                    pontuacaoFilho = getFilho(i).minimax(true);
                    pontuacaoMinima = Math.min(pontuacaoMinima, pontuacaoFilho);
                }
                setPontos(pontuacaoMinima);
                return pontuacaoMinima;
            }
        }
        return getPontos();
    }
    public void minimax() {
        minimax(true);
    }
    private int minimaxAlphaBeta(boolean estaMaximizando, float alpha, float beta) {
        setAcessado(true);
        if(getProfundidade() != 0) {
            if(estaMaximizando) {
                int pontuacaoMaxima = Integer.MIN_VALUE;
                int pontuacaoFilho;
                for(int i=0; i<getFilhos().size(); i++) {
                    pontuacaoFilho = getFilho(i).minimaxAlphaBeta(false, alpha, beta);
                    pontuacaoMaxima = Math.max(pontuacaoMaxima, pontuacaoFilho);
                    alpha = Math.max(alpha, pontuacaoFilho);
                    if(beta <= alpha) {
                        break;
                    }
                }
                setPontos(pontuacaoMaxima);
                return pontuacaoMaxima;
            }
            else {
                int pontuacaoMinima = Integer.MAX_VALUE;
                int pontuacaoFilho;
                for(int i=0; i<getFilhos().size(); i++) {
                    pontuacaoFilho = getFilho(i).minimaxAlphaBeta(true, alpha, beta);
                    pontuacaoMinima = Math.min(pontuacaoMinima, pontuacaoFilho);
                    beta = Math.min(beta, pontuacaoFilho);
                    if(beta <= alpha) {
                        break;
                    }
                }
                setPontos(pontuacaoMinima);
                return pontuacaoMinima;
            }
        }
        return getPontos();
    }
    public void minimaxAlphaBeta() {
        minimaxAlphaBeta(true,Integer.MIN_VALUE,Integer.MAX_VALUE);
    }
}