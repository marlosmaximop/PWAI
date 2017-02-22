package br.edu.opet.biblioteca.util;

import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.Document;
import javax.swing.text.DocumentFilter;
import javax.swing.text.PlainDocument;

public class FiltroReal extends PlainDocument
{
    private static final long serialVersionUID = 1L;

    private int               mTamanho;

    public FiltroReal(int pTamanho)
    {
        mTamanho = pTamanho;
        this.setDocumentFilter(new FiltroInterno());
    }

    private class FiltroInterno extends DocumentFilter
    {
        // Método chamado quando ocorre a troca de informação no campo de entrada
        @Override
        public void replace(FilterBypass pFiltro, int pDeslocamento, int pTamanho, String pTexto, AttributeSet pAtributos) throws BadLocationException
        {
            // Caso o texto seja nulo, nada a fazer
            if (pTexto == null)
                return;

            // Obtendo o documento ligado ao filtro
            Document tDoc = pFiltro.getDocument();

            // Obtendo o texto do documento
            String tTexto = tDoc.getText(0, tDoc.getLength());

            // Caso o conteúdo já tenha texto, recuperar a parte não alterada
            if (pTamanho != 0)
                tTexto = tTexto.substring(0, pDeslocamento) + tTexto.substring(pDeslocamento + pTamanho);

            // Montando o texto com a parte alterada
            tTexto = tTexto.substring(0, pDeslocamento) + pTexto + tTexto.substring(pDeslocamento);

            // Validando se o texto alterado é numérico. Se não for, sai do método e não altera
            if (!Validador.numericoReal(tTexto))
                return;

            // Validando se o texto alterado irá estourar o tamanho total do campo. Caso estoure, sai do método
            if (tTexto.length() > mTamanho)
                return;

            // Caso o tamanho e o tipo estejam certo, faz-se a alteração do texto no documento
            super.replace(pFiltro, pDeslocamento, pTamanho, pTexto, pAtributos);
        }
    }
}
