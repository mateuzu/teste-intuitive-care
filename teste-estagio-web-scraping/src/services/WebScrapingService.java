package services;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Collections;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

import javax.swing.JOptionPane;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

public class WebScrapingService {
	
	private static final String URL = System.getenv("URL_SCRAPING");
    private static final String DIRETORIO_DOWNLOAD = "downloads/";
    private static final String ARQUIVO_ZIP = "anexos.zip";

	public void rasparDados() {
		// camimho do driver
		System.setProperty("webdriver.chrome.driver", "resources/in/chromedriver.exe"); // caminho do driver
		
		// definindo opcoes de navegador
		ChromeOptions options = new ChromeOptions();

		// corrige erros de execuçao
		options.addArguments("--no-sandbox");
		options.addArguments("--disable-dev-shm-usage");

		// para não abrir o navegador durante execuçao
		// options.addArguments("--headless");

		// para evitar a detecção como bot dos navegadores
		options.addArguments("--disable-blink-features=AutomationControlled");
		options.setExperimentalOption("excludeSwitches", Collections.singletonList("enable-automation"));
		options.setExperimentalOption("useAutomationExtension", null);
		options.addArguments(
				"user-agent=Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/74.0.3728.169 Safari/537.36");
		
		//define tamanho da janela
		options.addArguments("window-size=800,600");

		// caminho para salvar os downloads
		String caminhoDownload = "C:\\Workspaces\\Java\\ws-java-web-scrapping\\teste-estagio-web-scraping\\resources\\in";
		options.addArguments("download.default_directory=" + caminhoDownload);

		// para baixar pdfs automaticamente sem abrir a visualizacao
		options.addArguments("--disable-plugins-discovery");
		options.addArguments("plugins.always_open_pdf_externally=true");

		// desativar notificaçoes
		options.addArguments("disable-infobars");
		options.addArguments("--disable-notifications");

		WebDriver driver = new ChromeDriver(options);

		// abrir a urk
		try {
			driver.get(URL);
			
			baixarArquivos(driver, "Anexo I");
			baixarArquivos(driver, "Anexo II");
			
			converterArquivosEmZip();
			JOptionPane.showMessageDialog(null, "Arquivos compactados com sucesso!");
		} catch (Exception e) {
			System.out.println("Erro ao campactar arquivos: " + e.getMessage());
		} finally {
			driver.quit();
		}

	}
	
	private static void baixarArquivos(WebDriver driver, String nomeArquivo) throws IOException {
        WebElement link = driver.findElement(By.partialLinkText(nomeArquivo));
        String urlArquivo = link.getAttribute("href");

        System.out.println("Baixando " + nomeArquivo + "...");

        // criando diretorio de downloads
        Files.createDirectories(Paths.get(DIRETORIO_DOWNLOAD));

        try (InputStream in = new URL(urlArquivo).openStream()) {
            Files.copy(in, Paths.get(DIRETORIO_DOWNLOAD + nomeArquivo + ".pdf"));
        }
    }
	
	private static void converterArquivosEmZip() throws IOException {
        try (FileOutputStream fos = new FileOutputStream(ARQUIVO_ZIP);
             ZipOutputStream zos = new ZipOutputStream(fos)) {

            File dir = new File(DIRETORIO_DOWNLOAD);
            for (File file : dir.listFiles()) {
                try (FileInputStream fis = new FileInputStream(file)) {
                    ZipEntry zipEntry = new ZipEntry(file.getName());
                    zos.putNextEntry(zipEntry);

                    byte[] bytes = new byte[1024];
                    int length;
                    while ((length = fis.read(bytes)) >= 0) {
                        zos.write(bytes, 0, length);
                    }
                }
            }
        }
    }
	
}
