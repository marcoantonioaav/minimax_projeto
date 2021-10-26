package agentes;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;

import agentes.util.Agente;
import jogos.util.Jogada;
import jogos.util.Jogo;

public class Minimax implements Agente{
    int numeroNodos;
    HashMap<Integer, Integer> nodosPorNivel;
    int profundidadeMax;
    int cortes;
    int COR_PECA;
    public final int MAX_PONTOS = 100;
    public final int MIN_PONTOS = -100;
    
    public Minimax(int COR_PECA, int profundadeMax){
        
        this.profundidadeMax = profundadeMax;
        numeroNodos = 0;
        cortes = 0;
        nodosPorNivel = new HashMap<Integer,Integer>();
        this.COR_PECA = COR_PECA;
    }

    public Jogada Mover(Jogo jogo, int[][] tabuleiro) throws InterruptedException{
        return Decide(jogo, tabuleiro, COR_PECA, COR_PECA);
    }

    Jogada Decide(Jogo jogo, int[][] tabuleiro, int corPecaJogador, int corPecaAtual) throws InterruptedException{
        numeroNodos+=1;
        if(!nodosPorNivel.containsKey(profundidadeMax)) nodosPorNivel.put(profundidadeMax,1);
        else nodosPorNivel.put(profundidadeMax, nodosPorNivel.get(profundidadeMax)+1) ;
        
        
        float max = Float.MIN_VALUE;;
        Jogada melhorJogada = null;
        for(Jogada j:jogo.listaPossiveisJogadas(corPecaAtual, tabuleiro)){
            int[][] novoTabuleiro = jogo.criaCopiaTabuleiro(tabuleiro);
            jogo.fazJogada(j, novoTabuleiro, false);
            float valor = Min(jogo, novoTabuleiro, corPecaJogador, jogo.invertePeca(corPecaAtual), profundidadeMax);
            if(valor > max)
            {
                melhorJogada = j;
                max = valor;
            }
        }
        return melhorJogada;
    }

    private float Max(Jogo jogo, int[][] tabuleiro, int corPecaJogador, int corPecaAtual, int profundidade) throws InterruptedException{
        numeroNodos+=1;

        if(!nodosPorNivel.containsKey(profundidadeMax - profundidade)) nodosPorNivel.put(profundidadeMax - profundidade,1);
        else nodosPorNivel.put(profundidadeMax - profundidade, nodosPorNivel.get(profundidadeMax - profundidade)+1) ;
        
        
        if(profundidade == 0 ||jogo.verificaFimDeJogo(tabuleiro)){
            float fatorDesconto = (float)Math.pow(0.99, profundidadeMax - profundidade); //TODO: GAMBIARRA BRABA MUDAR DEPOIS
            return jogo.geraCusto(corPecaJogador, tabuleiro, MIN_PONTOS, MAX_PONTOS) * fatorDesconto;
        }
        
        float valor = Float.MIN_VALUE;
        for(Jogada j:jogo.listaPossiveisJogadas(corPecaAtual, tabuleiro)){
            int[][] novoTabuleiro = jogo.criaCopiaTabuleiro(tabuleiro);
            jogo.fazJogada(j, novoTabuleiro, false);
            valor = Math.max(valor, Min(jogo, novoTabuleiro, corPecaJogador, jogo.invertePeca(corPecaAtual), profundidade-1));
            
        }
        return valor;
    }

    private float Min(Jogo jogo, int[][] tabuleiro, int corPecaJogador, int corPecaAtual, int profundidade) throws InterruptedException{
        numeroNodos+=1;
        if(!nodosPorNivel.containsKey(profundidadeMax - profundidade)) nodosPorNivel.put(profundidadeMax - profundidade,1);
        else nodosPorNivel.put(profundidadeMax - profundidade, nodosPorNivel.get(profundidadeMax - profundidade)+1) ;
        
        if(profundidade == 0 ||jogo.verificaFimDeJogo(tabuleiro)){
            float fatorDesconto = (float)Math.pow(0.99, profundidadeMax - profundidade); //TODO: GAMBIARRA BRABA MUDAR DEPOIS
            return jogo.geraCusto(corPecaJogador, tabuleiro, MIN_PONTOS, MAX_PONTOS) * fatorDesconto;
        }
        
        float valor = Float.MAX_VALUE;
        for(Jogada j:jogo.listaPossiveisJogadas(corPecaAtual, tabuleiro)){
            int[][] novoTabuleiro = jogo.criaCopiaTabuleiro(tabuleiro);
            jogo.fazJogada(j, novoTabuleiro, false);
            
            valor = Math.min(valor, Max(jogo, novoTabuleiro, corPecaJogador, jogo.invertePeca(corPecaAtual), profundidade-1));
            
        }
        return valor;
    }

    public int getCorPeca(){
        return COR_PECA;
    }

    @Override
    public String toString()
    {
        String filhosPorNivel = "";
        
        for(int i=0; i < nodosPorNivel.size(); i++)
        {
            int n = nodosPorNivel.get(i);
            filhosPorNivel += "(nvl " + i + ":" +  n + ") ";
        }
        return "\nMINIMAX\nnumero de nodos: " + numeroNodos + "\n" + filhosPorNivel + "\ncortes: " + cortes + "\n";
    }
}
