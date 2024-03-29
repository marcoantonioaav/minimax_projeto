package jogos;
import java.util.ArrayList;

import jogos.util.*;

public class JogoDaVelha4 extends Jogo {
    public JogoDaVelha4() {
        setNome("Jogo da Velha 4");
    }
    
    @Override
    public void inicializaTabuleiro() {
        for(int y=0; y < ALTURA_TABULEIRO; y++) {
            for(int x=0; x < LARGURA_TABULEIRO; x++) {
                getTabuleiro()[x][y] = SEM_PECA;
            }
        }
    }
    
    @Override
    public boolean verificaMovimento(Movimento movimento, int[][] tabuleiro) {
        return isLegalInsertion(movimento, tabuleiro);
    }
    
    public boolean verificaSimetriaVertical(int tabuleiro [][]){
        for (int x = 0; x < 2; x++){
            for(int y = 0; y < 5; y++){
                if (tabuleiro[x][y] != tabuleiro[4-x][y]) return false;
            }
        }
        return true;
    }

    public boolean verificaSimetriaHorizontal(int tabuleiro [][]){
        for (int y = 0; y < 2; y++){
            for(int x = 0; x < 5; x++){
                if (tabuleiro[x][y] != tabuleiro[x][4-y]) return false;
            }
        }
        return true;
    }

    public boolean verificaSimetriaDiagonal(int tabuleiro [][]){
        if (tabuleiro[0][1] != tabuleiro[1][0]) return false;
        if (tabuleiro[0][2] != tabuleiro[2][0]) return false;
        if (tabuleiro[0][3] != tabuleiro[3][0]) return false;
        if (tabuleiro[0][4] != tabuleiro[4][0]) return false;
        if (tabuleiro[1][2] != tabuleiro[2][1]) return false;
        if (tabuleiro[1][3] != tabuleiro[3][1]) return false;
        if (tabuleiro[1][4] != tabuleiro[4][1]) return false;
        if (tabuleiro[2][3] != tabuleiro[3][2]) return false;
        if (tabuleiro[2][4] != tabuleiro[4][2]) return false;
        if (tabuleiro[3][4] != tabuleiro[4][3]) return false;
        return true;
    }

    public boolean verificaSimetriaOutraDiagonal(int tabuleiro [][]){
        if (tabuleiro[0][0] != tabuleiro[4][4]) return false;
        if (tabuleiro[0][1] != tabuleiro[3][4]) return false;
        if (tabuleiro[0][2] != tabuleiro[2][4]) return false;
        if (tabuleiro[0][3] != tabuleiro[1][4]) return false;
        if (tabuleiro[1][0] != tabuleiro[4][3]) return false;
        if (tabuleiro[1][1] != tabuleiro[3][3]) return false;
        if (tabuleiro[1][2] != tabuleiro[2][3]) return false;
        if (tabuleiro[2][0] != tabuleiro[4][2]) return false;
        if (tabuleiro[2][1] != tabuleiro[3][2]) return false;
        if (tabuleiro[3][0] != tabuleiro[4][1]) return false;
        return true;
    }

    public boolean condicaoLoopSimetria(int larguraMax, boolean temDiagonal, boolean temOutraDiagonal, int x, int y) {
        boolean condicaoLargura = x < larguraMax;
        boolean condicaoDiagonal = true;
        boolean condicaoOutraDiagonal = true;
        if(temOutraDiagonal) {
            condicaoDiagonal = (x+y) < LARGURA_TABULEIRO;
        }
        if(temDiagonal) {
            condicaoOutraDiagonal = x<=y;
        }
        return condicaoLargura && condicaoDiagonal && condicaoOutraDiagonal;
    }
    
    @Override
    public ArrayList<Jogada> listaPossiveisJogadas(int corPeca, int[][] tabuleiro) {
        ArrayList<Jogada> possiveisJogadas = new ArrayList<Jogada>();
        boolean horizontal = verificaSimetriaHorizontal(tabuleiro);
        boolean vertical = verificaSimetriaVertical(tabuleiro);
        boolean diagonal = verificaSimetriaDiagonal(tabuleiro);
        boolean outraDiagonal = verificaSimetriaOutraDiagonal(tabuleiro);
        int alturaMax = ALTURA_TABULEIRO;
        int larguraMax = LARGURA_TABULEIRO;

        if(horizontal) {
            alturaMax = ALTURA_TABULEIRO-2;
        }
        if(vertical) {
            larguraMax = LARGURA_TABULEIRO-2;
        }
        for(int y=0; y < alturaMax; y++) {
            for(int x=0; condicaoLoopSimetria(larguraMax,diagonal,outraDiagonal,x,y); x++) {
                int[] posJogada = {x, y};
                Jogada possibilidade = new Jogada(corPeca, posJogada);
                if(verificaJogada(possibilidade, tabuleiro)) {
                    possiveisJogadas.add(possibilidade);
                }
            }
        }
        return possiveisJogadas;
    }
    

    public Boolean maximoAlinhado(int corPeca, int[][] tabuleiro){
    
        int lineSequence = 0;
        int columnSequence = 0;
        int dpSequence = 0; //diagonal principal
        int dsSequence = 0; //diagonal secundaria

        //testa 4 alinhamentos
        for(int i = 0; i < 5; i++){
            
            lineSequence = 0;
            columnSequence = 0;
            
            //diagonal princial
            if ( (tabuleiro[i][i] == corPeca)) { dpSequence++; }
            else dpSequence = 0;
            //diagonal secundária
            if ( (tabuleiro[i][4 - i] == corPeca)) { dsSequence++; }
            else dsSequence = 0;

            if(dpSequence >= 4 || dsSequence >= 4) return true;
            
            for (int j = 0; j < 5; j++)
            {
                //em linha
                if ((tabuleiro[j][i]== corPeca)) { lineSequence++; }
                else lineSequence = 0;
                //em coluna
                if ( (tabuleiro[i][j] == corPeca)) { columnSequence++; }
                else columnSequence = 0;

                if(lineSequence >= 4 || columnSequence >= 4) return true;
            
            }
            
        }
        
        return false;

    }

/* BACKUP
    public int maximoAlinhado(int corPeca, int[][] tabuleiro) {
        int maximo = 0;
        int contagem;
        for(int y=0; y < ALTURA_TABULEIRO; y++) {
            contagem = 0;
            for(int x=0; x < LARGURA_TABULEIRO; x++) {
                if(tabuleiro[x][y] == corPeca) {
                    contagem++;
                    maximo = Math.max(maximo, contagem);
                }
                else {
                    contagem = 0;
                }
            }
        }
        if (maximo >= 4) {
            return maximo;
        }
        for(int x=0; x < LARGURA_TABULEIRO; x++) {
            contagem = 0;
            for(int y=0; y < ALTURA_TABULEIRO; y++) {
                if(tabuleiro[x][y] == corPeca) {
                    contagem++;
                    maximo = Math.max(maximo, contagem);
                }
                else {
                    contagem = 0;
                }
            }
        }
        if (maximo >= 4) {
            return maximo;
        }
        contagem = 0;
        for(int x=0; x < LARGURA_TABULEIRO; x++) {
            if(tabuleiro[x][x] == corPeca) {
                contagem++;
                maximo = Math.max(maximo, contagem);
            }
            else {
                contagem = 0;
            }
        }
        if (maximo >= 4) {
            return maximo;
        }
        contagem = 0;
        for(int x=1; x < LARGURA_TABULEIRO; x++) {
            if(tabuleiro[x][x-1] == corPeca) {
                contagem++;
                maximo = Math.max(maximo, contagem);
            }
            else {
                contagem = 0;
            }
        }
        if (maximo >= 4) {
            return maximo;
        }
        contagem = 0;
        for(int x=0; x < LARGURA_TABULEIRO-1; x++) {
            if(tabuleiro[x][x+1] == corPeca) {
                contagem++;
                maximo = Math.max(maximo, contagem);
            }
            else {
                contagem = 0;
            }
        } 
        if (maximo >= 4) {
            return maximo;
        }
        contagem = 0;
        for(int x=LARGURA_TABULEIRO-1; x >= 0; x--) {
            if(tabuleiro[x][(LARGURA_TABULEIRO-1)-x] == corPeca) {
                contagem++;
                maximo = Math.max(maximo, contagem);
            }
            else {
                contagem = 0;
            }
        }
        if (maximo >= 4) {
            return maximo;
        }
        contagem = 0;
        for(int x=LARGURA_TABULEIRO-1; x >= 1; x--) {
            if(tabuleiro[x-1][LARGURA_TABULEIRO-1-x] == corPeca) {
                contagem++;
                maximo = Math.max(maximo, contagem);
            }
            else {
                contagem = 0;
            }
        }
        if (maximo >= 4) {
            return maximo;
        }
        contagem = 0;
        for(int x=LARGURA_TABULEIRO-2; x >= 0; x--) {
            if(tabuleiro[x+1][LARGURA_TABULEIRO-1-x] == corPeca) {
                contagem++;
                maximo = Math.max(maximo, contagem);
            }
            else {
                contagem = 0;
            }
        }
        return maximo;
    }
*/
    // * Search for three pieces in sequence
    public boolean tentaAcharTripla(int corPeca, int[][] tabuleiro) {

        for(int i=0; i < ALTURA_TABULEIRO; i++) 
        {
            if(tabuleiro[0][i] == SEM_PECA && tabuleiro[4][i] == SEM_PECA) //horizontal
                if (tabuleiro[1][i] == corPeca && tabuleiro[2][i] == corPeca && tabuleiro[3][i] == corPeca)
                    return true;
            
            if(tabuleiro[i][0] == SEM_PECA && tabuleiro[i][4] == SEM_PECA) //vertical
                if (tabuleiro[i][1] == corPeca && tabuleiro[i][2] == corPeca && tabuleiro[i][3] == corPeca)
                    return true;
        }
        
        if (tabuleiro[0][0] == SEM_PECA && tabuleiro[1][1]== corPeca && tabuleiro [2][2] == corPeca && tabuleiro[3][3] == corPeca && tabuleiro [4][4] == SEM_PECA)
            return true;

        if (tabuleiro[0][4] == SEM_PECA && tabuleiro[1][3]== corPeca && tabuleiro [2][2] == corPeca && tabuleiro[3][1] == corPeca && tabuleiro [4][0] == SEM_PECA)
            return true;

        return false;
    }

    // * Search for two pieces in sequence
    public int contaDuplas(int corPeca, int[][] tabuleiro) {
        int contagem = 0;
        for(int i=0; i < ALTURA_TABULEIRO; i++) {
            
            if(tabuleiro[0][i] == SEM_PECA && tabuleiro[3][i] == SEM_PECA){ //horizontal 1
                if (tabuleiro[1][i] == corPeca && tabuleiro[2][i] == corPeca) {
                    contagem ++;
                }
            }
            
            if(tabuleiro[1][i] == SEM_PECA && tabuleiro[4][i] == SEM_PECA){ //horizontal 2
                if (tabuleiro[2][i] == corPeca && tabuleiro[3][i] == corPeca){
                    contagem ++;
                }
            }

            if(tabuleiro[i][0] == SEM_PECA && tabuleiro[i][3] == SEM_PECA){ //vertical 1
                if (tabuleiro[i][1] == corPeca && tabuleiro[i][2] == corPeca){
                    contagem ++;
                }
            }

            if(tabuleiro[i][1] == SEM_PECA && tabuleiro[i][4] == SEM_PECA){ //vertical 2
                if (tabuleiro[i][2] == corPeca && tabuleiro[i][3] == corPeca){
                    contagem ++;
                }
            }
        }
        
        
        if (tabuleiro[0][0] == SEM_PECA && tabuleiro[1][1] == corPeca && tabuleiro [2][2] == corPeca && tabuleiro[3][3] == SEM_PECA)
            contagem ++;
        if (tabuleiro[1][1] == SEM_PECA && tabuleiro [2][2] == corPeca && tabuleiro[3][3] == corPeca && tabuleiro [4][4] == SEM_PECA)
            contagem ++;
        //Diagonal Principal
        
        if (tabuleiro[0][4] == SEM_PECA && tabuleiro[1][3] == corPeca && tabuleiro[2][2] == corPeca && tabuleiro[3][1] == SEM_PECA)
            contagem ++;
        if (tabuleiro[1][3] == SEM_PECA && tabuleiro[2][2] == corPeca && tabuleiro[3][1] == corPeca && tabuleiro [4][0] == SEM_PECA)
            contagem ++;
        //Diagonal Secundaria

        if (tabuleiro[1][0] == SEM_PECA && tabuleiro[2][1] == corPeca && tabuleiro [3][2] == corPeca && tabuleiro[4][3] == SEM_PECA)
            contagem ++;
        if (tabuleiro[0][1] == SEM_PECA && tabuleiro [1][2] == corPeca && tabuleiro[2][3] == corPeca && tabuleiro [3][4] == SEM_PECA)
            contagem ++;
        //Mais Diagonais Principais 
        
        if (tabuleiro[3][0] == SEM_PECA && tabuleiro[2][1] == corPeca && tabuleiro[1][2] == corPeca && tabuleiro[0][3] == SEM_PECA)
            contagem ++;
        if (tabuleiro[4][1] == SEM_PECA && tabuleiro[3][2] == corPeca && tabuleiro[2][3] == corPeca && tabuleiro [1][4] == SEM_PECA)
            contagem ++;
        //Mais Diagonais Secundarias

        return contagem;
    }

    @Override
    public boolean verificaVitoria(int corPeca, int[][] tabuleiro) {
        return maximoAlinhado(corPeca, tabuleiro);
        /* 
        if(maximoAlinhado(corPeca, tabuleiro) >= 4) {
            return true;
        }
        else {
            return false;
        }
        */
    }
    @Override
    public boolean verificaFimDeJogo(int[][] tabuleiro) {
        return verificaVitoria(PECA_BRANCA, tabuleiro) || verificaVitoria(PECA_PRETA, tabuleiro) || contaPecas(SEM_PECA, tabuleiro) == 0;
    }
    
    public int numeroDeAlinhamentosComVazios(int corPeca, int[][] tabuleiro) {
        int pontos = 0;
        boolean encontrouPecaLinha;

        int nCorPeca;
        int nOutraCor;
        int nConsecutivos;
        
        for(int y=0; y < ALTURA_TABULEIRO; y++) {
            encontrouPecaLinha = false;
            nCorPeca = 0;
            nOutraCor = 0;
            nConsecutivos = 0;
            for(int x=0; x < LARGURA_TABULEIRO; x++) {
                if(tabuleiro[x][y] == corPeca) {
                    nCorPeca++;
                    if(encontrouPecaLinha) nConsecutivos++;
                    else encontrouPecaLinha = true;
                }
                else if(tabuleiro[x][y] == SEM_PECA) {
                    encontrouPecaLinha = false;
                }
                else {
                    nOutraCor++;
                    encontrouPecaLinha = false;
                }
            }
            if(nOutraCor == 0 || (nOutraCor == 1 && (tabuleiro[0][y] == invertePeca(corPeca) || tabuleiro[LARGURA_TABULEIRO-1][y] == invertePeca(corPeca))))
                if(nCorPeca > 0)
                    pontos+=(nCorPeca+nConsecutivos-nOutraCor);
        }

        for(int x=0; x < LARGURA_TABULEIRO; x++) {
            encontrouPecaLinha = false;
            nCorPeca = 0;
            nOutraCor = 0;
            nConsecutivos = 0;
            
            for(int y=0; y < ALTURA_TABULEIRO; y++) {
                if(tabuleiro[x][y] == corPeca) {
                    nCorPeca++;
                    if(encontrouPecaLinha) nConsecutivos++;
                    else encontrouPecaLinha = true;
                }
                else if(tabuleiro[x][y] == SEM_PECA) {
                    encontrouPecaLinha = false;
                }
                else {
                    nOutraCor++;
                    encontrouPecaLinha = false;
                }
            }
            if(nOutraCor == 0 || (nOutraCor == 1 && (tabuleiro[x][0] == invertePeca(corPeca) || tabuleiro[x][ALTURA_TABULEIRO-1] == invertePeca(corPeca))))
                if(nCorPeca > 0)
                    pontos+=(nCorPeca+nConsecutivos-nOutraCor);
        }

        encontrouPecaLinha = false;
        nCorPeca = 0;
        nOutraCor = 0;
        nConsecutivos = 0;
        for(int x=0; x < LARGURA_TABULEIRO; x++) {
            if(tabuleiro[x][x] == corPeca) {
                nCorPeca++;
                if(encontrouPecaLinha) nConsecutivos++;
                else encontrouPecaLinha = true;
            }
            else if(tabuleiro[x][x] == SEM_PECA) {
                encontrouPecaLinha = false;
            }
            else {
                nOutraCor++;
                encontrouPecaLinha = false;
            }
        }
        if(nOutraCor == 0 || (nOutraCor == 1 && (tabuleiro[0][0] == invertePeca(corPeca) || tabuleiro[LARGURA_TABULEIRO-1][ALTURA_TABULEIRO-1] == invertePeca(corPeca))))
                if(nCorPeca > 0)
                    pontos+=(nCorPeca+nConsecutivos-nOutraCor);

        encontrouPecaLinha = false;
        nCorPeca = 0;
        nOutraCor = 0;
        nConsecutivos = 0;
        for(int x=1; x < LARGURA_TABULEIRO; x++) {
            if(tabuleiro[x][x-1] == corPeca) {
                nCorPeca++;
                if(encontrouPecaLinha) nConsecutivos++;
                else encontrouPecaLinha = true;
            }
            else if(tabuleiro[x][x-1] == SEM_PECA) {
                encontrouPecaLinha = false;
            }
            else {
                nOutraCor++;
                encontrouPecaLinha = false;
            }
        }
        if(nOutraCor == 0)
            if(nCorPeca > 0)
                pontos+=(nCorPeca+nConsecutivos);

        encontrouPecaLinha = false;
        nCorPeca = 0;
        nOutraCor = 0;
        nConsecutivos = 0;
        for(int x=0; x < LARGURA_TABULEIRO-1; x++) {
            if(tabuleiro[x][x+1] == corPeca) {
                nCorPeca++;
                if(encontrouPecaLinha) nConsecutivos++;
                else encontrouPecaLinha = true;
            }
            else if(tabuleiro[x][x+1] == SEM_PECA) {
                encontrouPecaLinha = false;
            }
            else {
                nOutraCor++;
                encontrouPecaLinha = false;
            }
        }
        if(nOutraCor == 0)
            if(nCorPeca > 0)
                pontos+=(nCorPeca+nConsecutivos);

        encontrouPecaLinha = false;
        nCorPeca = 0;
        nOutraCor = 0;
        nConsecutivos = 0;
        for(int x=LARGURA_TABULEIRO-1; x >= 0; x--) {
            if(tabuleiro[x][(LARGURA_TABULEIRO-1)-x] == corPeca) {
                nCorPeca++;
                if(encontrouPecaLinha) nConsecutivos++;
                else encontrouPecaLinha = true;
            }
            else if(tabuleiro[x][(LARGURA_TABULEIRO-1)-x] == SEM_PECA) {
                encontrouPecaLinha = false;
            }
            else {
                nOutraCor++;
                encontrouPecaLinha = false;
            }
        }
        if(nOutraCor == 0 || (nOutraCor == 1 && (tabuleiro[4][0] == invertePeca(corPeca) || tabuleiro[0][4] == invertePeca(corPeca))))
                if(nCorPeca > 0)
                    pontos+=(nCorPeca+nConsecutivos-nOutraCor);

        encontrouPecaLinha = false;
        nCorPeca = 0;
        nOutraCor = 0;
        nConsecutivos = 0;
        for(int x=LARGURA_TABULEIRO-1; x >= 1; x--) {
            if(tabuleiro[x-1][LARGURA_TABULEIRO-1-x] == corPeca) {
                nCorPeca++;
                if(encontrouPecaLinha) nConsecutivos++;
                else encontrouPecaLinha = true;
            }
            else if(tabuleiro[x-1][LARGURA_TABULEIRO-1-x] == SEM_PECA) {
                encontrouPecaLinha = false;
            }
            else {
                nOutraCor++;
                encontrouPecaLinha = false;
            }
        }
        if(nOutraCor == 0)
            if(nCorPeca > 0)
                pontos+=(nCorPeca+nConsecutivos);

        encontrouPecaLinha = false;
        nCorPeca = 0;
        nOutraCor = 0;
        nConsecutivos = 0;
        for(int x=LARGURA_TABULEIRO-2; x >= 0; x--) {
            if(tabuleiro[x+1][LARGURA_TABULEIRO-1-x] == corPeca) {
                nCorPeca++;
                if(encontrouPecaLinha) nConsecutivos++;
                else encontrouPecaLinha = true;
            }
            else if(tabuleiro[x+1][LARGURA_TABULEIRO-1-x] == SEM_PECA) {
                encontrouPecaLinha = false;
            }
            else {
                nOutraCor++;
                encontrouPecaLinha = false;
            }
        }
        if(nOutraCor == 0)
            if(nCorPeca > 0)
                pontos+=(nCorPeca+nConsecutivos);

        return pontos;
    }
    
    public float geraCustoPeca(int corPeca, int[][] tabuleiro, int minPontos, int maxPontos) {
        float pontosTripla = (float)minPontos;
        if(tentaAcharTripla(corPeca, tabuleiro)) {
            pontosTripla = (float)maxPontos;
        }
        float pontosDuplas = normalizaPontuacao(0.0f, 8.0f, (float)minPontos, (float)maxPontos, (float)contaDuplas(corPeca,tabuleiro));
        float pontosAlinhamentos = normalizaPontuacao(0.0f, 16.0f, (float)minPontos, (float)maxPontos, (float)numeroDeAlinhamentosComVazios(corPeca,tabuleiro));
        return pontosAlinhamentos*0.5f + pontosDuplas*0.2f + pontosTripla*0.3f;
    }

    @Override
    public float geraCusto(int corPeca, int[][] tabuleiro, int minPontos, int maxPontos) {
        if(verificaVitoria(corPeca, tabuleiro)) {
            return maxPontos;
        }
        else if(verificaVitoria(invertePeca(corPeca), tabuleiro)) {
            return minPontos;
        }
        else{
            return geraCustoPeca(corPeca, tabuleiro, minPontos, maxPontos)*0.3f + geraCustoPeca(invertePeca(corPeca), tabuleiro, minPontos, maxPontos)*-0.7f;
        }        
    }

    @Override
    public Jogada jogadaDanoMinimo(Jogada antigaMelhorJogada, int corPeca) throws InterruptedException {
        ArvoreDeJogadas jogadas = new ArvoreDeJogadas(0);
        ArrayList<Jogada> possiveisJogadasAdversario = listaPossiveisJogadas(invertePeca(corPeca), getTabuleiro());
        int pontuacaoAdversario;
        int maiorPontuacaoAdversario = Integer.MIN_VALUE;
        Jogada melhorJogadaAdversario = possiveisJogadasAdversario.get(0);
        for(Jogada possivelJogada : possiveisJogadasAdversario) {
            int[][] novoTabuleiro = criaCopiaTabuleiro(getTabuleiro());
            fazJogada(possivelJogada, novoTabuleiro, false);
            pontuacaoAdversario = (int)geraCusto(invertePeca(corPeca), novoTabuleiro, jogadas.MIN_PONTOS, jogadas.MAX_PONTOS);
            if(pontuacaoAdversario > maiorPontuacaoAdversario) {
                melhorJogadaAdversario = possivelJogada;
                maiorPontuacaoAdversario = pontuacaoAdversario;
            }
        }
        return new Jogada(corPeca, melhorJogadaAdversario.getMovimentos().get(0).getPosicao1());
    }

    @Override
    public Movimento.Acao proximaAcao(int corPeca, int[][] tabuleiro) {
        return Movimento.Acao.INSERE;
    }
}
