package app;

import services.WebScrapingService;

public class Programa {
	public static void main(String[] args) {
		WebScrapingService webScrapingService = new WebScrapingService();
		webScrapingService.rasparDados(); //chamando método
	}
}
