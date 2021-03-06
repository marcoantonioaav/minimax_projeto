package agentes.Minimax;

import agentes.util.IAgent;
import jogos.util.Jogada;
import jogos.util.Jogo;
import agentes.Trees.NegamaxTree;
import java.util.Arrays;
/* 
    Pure Negamax implementation.

    Negamax OK
    NegamaxTree OK
    logNegamax INCOMPLETE

*/
public class Negamax implements IAgent{
    private final String ID = "NEGAMAX";
    protected Jogo lastGamePlayed;
    protected int[][] lastBoardEvaluated;
    
    protected int maxDepth;
    protected int COR_PECA;
    
    protected float executionTime;
    protected long startTime;
    protected long endTime;
    protected int numberNodes;

    public Negamax (int COR_PECA, int profundidadeMax) {
        this.COR_PECA = COR_PECA;
        this.maxDepth = profundidadeMax;
    }
    
    public Jogada Move(Jogo jogo, int[][] tabuleiro, String[] args){
        initializeVariables();
        numberNodes=1;

        float max = Integer.MIN_VALUE;;
        Jogada melhorJogada = null;
        int opponentPiece = jogo.invertePeca(COR_PECA);
        
        for(Jogada j:jogo.listaPossiveisJogadas(COR_PECA, tabuleiro)){
            int[][] novoTabuleiro = jogo.criaCopiaTabuleiro(tabuleiro);
            jogo.fazJogada(j, novoTabuleiro, false);
            float value = -negamax(jogo, novoTabuleiro, opponentPiece, maxDepth-1, -1); 
            if(value > max)
            {
                melhorJogada = j;
                max = value;
            }
        }

        closeVariables();
        lastGamePlayed = jogo;
        lastBoardEvaluated = jogo.criaCopiaTabuleiro(tabuleiro);
        System.out.println("nodes: " + String.valueOf(numberNodes));
        return melhorJogada;
        
    }
    
    public float negamax(Jogo game, int[][] board, int currentPiece, int depth, int sign){ 
        numberNodes++;
        if(depth == 0 ||game.verificaFimDeJogo(board)){
            return game.geraCusto(COR_PECA, board, -100, +100) * sign;
        }
        
        float max = Integer.MIN_VALUE;
        int opponentPiece = game.invertePeca(currentPiece);
        for(Jogada j:game.listaPossiveisJogadas(currentPiece, board)){
            int[][] newBoard = game.criaCopiaTabuleiro(board);
            game.fazJogada(j, newBoard, false);
            max = Math.max(max, -negamax(game, newBoard, opponentPiece, depth-1, sign*-1) * 0.99f);
        }
        return max;
    }

    protected void initializeVariables(){
        executionTime = 0;
        startTime = System.currentTimeMillis();  
    }

    protected void closeVariables(){
        endTime = System.currentTimeMillis();
        executionTime = (endTime - startTime)/1000f;
    }

    public int getProfundidade(){ return maxDepth; }
    public int getCorPeca(){ return COR_PECA; }
    
    @Override
    public String toString(){
        return "";
    }
}
