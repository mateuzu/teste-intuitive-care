package services;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.text.PDFTextStripper;

public class TransformarDadosService {
	
	private static String caminhoPdf = "resources/out/downloads/Anexo I.pdf";
	private static String caminhoCsv = "Rol_Procedimentos.csv";
    private static String caminhoZip = "Teste_Mateus_Ferreira.zip";

	 public String extrairTextoDoPdf() throws IOException {
	        try (PDDocument document = PDDocument.load(new File(caminhoPdf))) {
	            PDFTextStripper pdfStripper = new PDFTextStripper();
	            System.out.println("Texto extraído do PDF" + pdfStripper.getText(document));
	            return pdfStripper.getText(document);
	        }
	    }

	    public List<String[]> processarTexto(String texto) {
	        List<String[]> dadosTabela = new ArrayList<>();
	        String[] linhas = texto.split("\n");

	        Map<String, String> abreviacoes = Map.of(
	            "OD", "Seg. Odontológica",
	            "AMB", "Seg. Ambulatorial",
	            "HCO", "Seg. Hospitalar Com Obstetrícia",
	            "HSO", "Seg. Hospitalar Sem Obstetrícia",
	            "REF", "Plano Referência",
	            "PAC", "Procedimento de Alta Complexidade",
	            "DUT", "Diretriz de Utilização"
	        );
	        
	        for (String linha : linhas) {
	            String[] colunas = linha.trim().split("\s{2,}");
	            for (int i = 0; i < colunas.length; i++) {
	            	colunas[i] = abreviacoes.getOrDefault(colunas[i], colunas[i]);
	            }
	            if (colunas.length > 1) {
	                dadosTabela.add(colunas);
	            }
	        }
	        System.out.println("Texto processado");
	        return dadosTabela;
	    }

	    public void salvarComoCsv(List<String[]> dados) throws IOException {
	        try (BufferedWriter writer = Files.newBufferedWriter(Paths.get(caminhoCsv), StandardCharsets.UTF_8)) {
	            // Escrevendo cabeçalho
	            writer.write("PROCEDIMENTO,VIGÊNCIA,OD,AMB,HCO,HSO,REF,PAC,DUT,SUBGRUPO,GRUPO,CAPÍTULO");
	            writer.newLine();

	            for (String[] linha : dados) {
	                writer.write(String.join(",", linha));
	                writer.newLine();
	            }
	        }
	        
	        System.out.println("Arquivo salvo como CSV - Caminho " + caminhoCsv);
	    }

	    public void converterArquivoEmZip() throws IOException {
	        try (FileOutputStream fos = new FileOutputStream(caminhoZip);
	             ZipOutputStream zipOut = new ZipOutputStream(fos);
	             FileInputStream fis = new FileInputStream(caminhoCsv)) {
	            ZipEntry zipEntry = new ZipEntry(new File(caminhoCsv).getName());
	            zipOut.putNextEntry(zipEntry);
	            byte[] bytes = new byte[1024];
	            int length;
	            while ((length = fis.read(bytes)) >= 0) {
	                zipOut.write(bytes, 0, length);
	            }
	            System.out.println("Arquivo zipado - Caminho" + zipOut);
	        }
	    }
}
