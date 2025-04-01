package app;

import java.io.IOException;
import java.util.List;

import services.TransformarDadosService;

public class Programa {
	public static void main(String[] args) throws IOException {
		TransformarDadosService service = new TransformarDadosService();
		
        String textoExtraido = service.extrairTextoDoPdf();
        
        List<String[]> dadosTabela = service.processarTexto(textoExtraido);
        service.salvarComoCsv(dadosTabela);
        
        service.converterArquivoEmZip();
    }

}

