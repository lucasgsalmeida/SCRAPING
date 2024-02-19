package com.AWN.controller.execucao.prefeituraGoiania;

import java.awt.AWTException;
import java.awt.Robot;
import java.awt.Toolkit;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.StringSelection;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;
import java.io.IOException;
import java.time.Duration;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
public class PrefeituraGoiania_Execucao {

    private WebDriver driver;
    private Map<String, Object> vars;

    public void setUp() {
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--ignore-certificate-errors");
        options.addArguments("--ignore-ssl-errors");
        driver = new ChromeDriver(options);
        vars = new HashMap<String, Object>();
    }

    public void tearDown() {
        driver.quit();
    }

    public void processarNotas(String mes, String ano) throws IOException, AWTException, InterruptedException {

        CriarPasta cp = new CriarPasta();
        cp.criarPasta();

        System.out.println("ANTES DE PEGAR A LISTA");
        List<Empresas> empresas = new ArrayList<Empresas>();
        empresas = EmpresasFactory.getEmpresasDominio();
        System.out.println("DEPOIS DE PEGAR A LISTA");

        for (Empresas empt : empresas) {

            System.out.println(empt.getNome() + " | " + empt.getCodigo() + " | " + empt.getInsc_mun());

            String insc = empt.getInsc_mun();

            System.out.println("CONSEGUIU INSC MUN");

            cp.criarPastaNFS();

            System.out.println("CONSEGUIU CRIAR A PASTA");

            cp.criarPastaEmpresaNFS(empt);

            System.out.println("CONSEGUIU CRIAR A PASTA NFS");

            Processamento pro = new Processamento();

            System.out.println("CHAMOU PROCESSAMENTO");

            cp.criarPastaCompetênciaNFS(mes + ano);

            System.out.println("CRIOU PASTA COMPETENCIA + MES E ANO");

            String pastaCompetencia = cp.getPastaCompetenciaEmpresaNFS().getAbsolutePath();

            System.out.println("CAPTUROU A PASTA DE DESTINO");

            String caminho = pastaCompetencia + "\\" + mes + ano + ".xml";

            System.out.println("CAPTUROU O CAMINHO");

            Robot bot = new Robot();

            if (pro.temArquivoTxt(pastaCompetencia)) {
                continue;
            }

            if (pro.temArquivoXml(pastaCompetencia)) {
                continue;
            }

            System.out.println("FEZ AS VERIFICAÇÕES");


            String cpf = "INSIRA O CPF AQUI";
            String senha = "INSIRA A SENHA AQUI";

            setUp();
            driver.get(
                    "https://www10.goiania.go.gov.br/Internet/Login.aspx?OriginalURL=https%3a%2f%2fwww10.goiania.go.gov.br%2fsicaeportal%2fHomePage.aspx");

            try {
                try {
                    String texto = driver.findElement(By.xpath("//*[@id=\"content\"]/div/fieldset")).getText();

                    if (texto.length() > 1) {

                        bot.keyPress(KeyEvent.VK_ALT);
                        bot.keyPress(KeyEvent.VK_F4);

                        bot.keyRelease(KeyEvent.VK_F4);
                        bot.keyRelease(KeyEvent.VK_ALT);

                        tearDown();

                        continue;
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }

                driver.findElement(By.xpath("//*[@id=\"wt17_wtMainContent_wtUserNameInput\"]")).click();
                driver.findElement(By.xpath("//*[@id=\"wt17_wtMainContent_wtUserNameInput\"]")).sendKeys(cpf);
                driver.findElement(By.xpath("//*[@id=\"wt17_wtMainContent_wtPasswordInput\"]")).click();
                driver.findElement(By.xpath("//*[@id=\"wt17_wtMainContent_wtPasswordInput\"]")).sendKeys(senha);
                driver.findElement(By.xpath("//*[@id=\"wt17_wtMainContent_wt30\"]")).click();
                Thread.sleep(2000);

                {
                    WebElement element = driver.findElement(By.xpath("//body/div/div"));
                    Actions builder = new Actions(driver);
                    builder.moveToElement(element).clickAndHold().perform();
                }
                {
                    WebElement element = driver.findElement(By.xpath("//body/div/div"));
                    Actions builder = new Actions(driver);
                    builder.moveToElement(element).perform();
                }
                {
                    WebElement element = driver.findElement(By.xpath("//body/div/div"));
                    Actions builder = new Actions(driver);
                    builder.moveToElement(element).release().perform();
                }

                driver.findElement(By.xpath("//body/div/div")).click();
                driver.switchTo().frame(0);
                driver.findElement(By.xpath("//input[@id=\'WebPatterns_wt8_block_wtMainContent_wt6\']")).click();

                Thread.sleep(2000);

                driver.switchTo().defaultContent();
                driver.findElement(By.xpath("//form[@id=\'WebForm1\']/div[3]/div[2]/div/div")).click();
                driver.findElement(By.xpath("//div[@id=\'s2id_GoianiaTheme_wtTelaPrincipal_block_wtActions_SISEGIntegration_wt115_block_wtAcessos\']/a/span")).click();

                driver.findElement(By.xpath("//div[@id=\'select2-drop\']/div/input")).sendKeys(insc);
                Thread.sleep(500);

                String nãoEncontrado = driver.findElement(By.xpath("//*[@id=\"select2-results-1\"]/li")).getText();

                Thread.sleep(500);

                driver.findElement(By.xpath("//div[@id=\'select2-drop\']/div/input")).sendKeys(Keys.ENTER);

                Thread.sleep(500);

                if (nãoEncontrado.contains("No results matched")) {

                    bot.keyPress(KeyEvent.VK_ALT);
                    bot.keyPress(KeyEvent.VK_F4);

                    bot.keyRelease(KeyEvent.VK_F4);
                    bot.keyRelease(KeyEvent.VK_ALT);
                    cp.criarDocumento("EMPRESA NÃO HABILITADA", pastaCompetencia);

                    tearDown();

                    continue;
                }

                Thread.sleep(5000);


                driver.findElement(By.xpath("//*[@id=\"GoianiaTheme_wtTelaPrincipal_block_wtMainContent_WebPatterns_wt175_block_wtTab1\"]")).click();
                driver.findElement(By.xpath("//div[@id=\'GoianiaTheme_wtTelaPrincipal_block_wtMainContent_WebPatterns_wt175_block_wtContent1_wt56_WebPatterns_wt364_block_wtContent_WebPatterns_wt496_block_wtText\']/span")).click();
                Thread.sleep(2000);
                vars.put("window_handles", driver.getWindowHandles());
                driver.findElement(By.xpath("//div[@id=\'GoianiaTheme_wtTelaPrincipal_block_wtMainContent_WebPatterns_wt175_block_wtContent1_wt56_WebPatterns_wt72_block_wtContent\']")).click();
                driver.findElement(By.xpath("//a[@id=\'GoianiaTheme_wtTelaPrincipal_block_wtMainContent_WebPatterns_wt175_block_wtContent1_wt56_WebPatterns_wt72_block_wtContent_wt302\']/div/span")).click();

			/*

			driver.findElement(By.xpath("//*[@id=\"GoianiaTheme_wtTelaPrincipal_block_wtMainContent_WebPatterns_wt168_block_wtTab1\"]/div")).click();

			try {
				driver.findElement(By.xpath(
						"//*[@id=\"GoianiaTheme_wtTelaPrincipal_block_wtMainContent_WebPatterns_wt168_block_wtContent1_wt55_WebPatterns_wt360_block_wtContent_WebPatterns_wt490_block_wtText_wtNFEletronica\"]"))
						.click();
			} catch (NoSuchElementException e) {

				bot.keyPress(KeyEvent.VK_ALT);
				bot.keyPress(KeyEvent.VK_F4);

				bot.keyRelease(KeyEvent.VK_F4);
				bot.keyRelease(KeyEvent.VK_ALT);
				cp.criarDocumento("BOTÃO NFS NÃO ENCONTRADO", pastaCompetencia);

				tearDown();

				continue;
			}
			driver.findElement(By.xpath("//*[@id=\"GoianiaTheme_wtTelaPrincipal_block_wtMainContent_WebPatterns_wt168_block_wtContent1_wt55_WebPatterns_wt72_block_wtContent\"]")).click();

			driver.findElement(By.xpath(
					"//*[@id=\"GoianiaTheme_wtTelaPrincipal_block_wtMainContent_WebPatterns_wt168_block_wtContent1_wt55_WebPatterns_wt72_block_wtContent_wt298\"]/div/span"))
					.click();
			Thread.sleep(1500);

			*/


                Set<String> guias = driver.getWindowHandles();
                driver.switchTo().window((String) guias.toArray()[1]);
                Thread.sleep(2500);

                try {

                    WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(4));
                    Alert alert = wait.until(ExpectedConditions.alertIsPresent());

                    if (alert != null) {
                        alert.accept();
                    } else {

                        bot.keyPress(KeyEvent.VK_ALT);
                        bot.keyPress(KeyEvent.VK_F4);

                        bot.keyRelease(KeyEvent.VK_F4);
                        bot.keyRelease(KeyEvent.VK_ALT);
                        cp.criarDocumento("EMPRESA NÃO EMITE NFS", pastaCompetencia);

                        tearDown();

                        continue;

                    }
                } catch (TimeoutException e) {
                    e.printStackTrace();

                    bot.keyPress(KeyEvent.VK_ALT);
                    bot.keyPress(KeyEvent.VK_F4);

                    bot.keyRelease(KeyEvent.VK_F4);
                    bot.keyRelease(KeyEvent.VK_ALT);
                    cp.criarDocumento("EMPRESA NÃO EMITE NFS", pastaCompetencia);

                    tearDown();

                    continue;
                }

                try {
                    WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(4));
                    Alert alert = wait.until(ExpectedConditions.alertIsPresent());

                    if (alert != null) {
                        alert.accept();
                    } else {
                        System.out.println("oi");
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }

                driver.switchTo().frame(4);
                driver.findElement(By.cssSelector("a > font")).click();
                driver.findElement(By.cssSelector("tr:nth-child(28) b")).click();
                Thread.sleep(1500);

                {
                    WebElement dropdown = driver.findElement(By.name("txt_dia_inicial"));
                    dropdown.findElement(By.xpath("/html/body/form/table/tbody/tr[8]/td[3]/select[1]/option[1]")).click();
                }

                {
                    WebElement dropdown = driver.findElement(By.name("txt_dia_final"));
                    dropdown.findElement(By.xpath("/html/body/form/table/tbody/tr[8]/td[3]/select[2]/option[31]")).click();
                }

                {
                    WebElement dropdown = driver.findElement(By.name("sel_mes"));
                    dropdown.findElement(
                                    By.xpath("/html/body/form/table/tbody/tr[8]/td[3]/select[3]/option[" + mesServiço(mes) + "]"))
                            .click();
                }

                driver.findElement(By.name("txt_ano")).click();
                driver.findElement(By.name("txt_ano")).sendKeys(ano);

                driver.findElement(By.xpath("/html/body/form/table/tbody/tr[9]/td[3]/input")).click();

                Thread.sleep(3000);

                Set<String> handles = driver.getWindowHandles();
                String lastHandle = null;
                Iterator<String> iterator = handles.iterator();
                while (iterator.hasNext()) {
                    lastHandle = iterator.next();
                    System.out.println("\n-\n-\n-\n-\n-\n-\n-" + lastHandle.toString() + "\n-\n-\n-\n-\n-\n-\n-\n-\n-");

                }
                System.out.println(lastHandle);

                driver.switchTo().window(lastHandle.toString());

                Thread.sleep(2000);

                String texto = driver.findElement(By.xpath("/html/body")).getText();

                if (texto.contains("Use o link abaixo")) {

                    bot.delay(1000);

                    int x = 443;
                    int y = 428;

                    bot.mouseMove(x, y);

                    bot.mousePress(InputEvent.BUTTON1_DOWN_MASK);
                    bot.mouseRelease(InputEvent.BUTTON1_DOWN_MASK);

                    bot.delay(500);

                    bot.keyPress(KeyEvent.VK_TAB);
                    bot.keyRelease(KeyEvent.VK_TAB);

                    bot.delay(500);

                    bot.delay(500);

                    bot.keyPress(KeyEvent.VK_ENTER);
                    bot.keyRelease(KeyEvent.VK_ENTER);

                    bot.delay(4000);

                    bot.keyPress(KeyEvent.VK_CONTROL);
                    bot.keyPress(KeyEvent.VK_S);

                    bot.keyRelease(KeyEvent.VK_S);
                    bot.keyRelease(KeyEvent.VK_S);

                    bot.delay(3000);

                    StringSelection selecao = new StringSelection(caminho);
                    Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
                    clipboard.setContents(selecao, null);

                    bot.keyPress(KeyEvent.VK_CONTROL);
                    bot.keyPress(KeyEvent.VK_V);

                    bot.keyRelease(KeyEvent.VK_V);
                    bot.keyRelease(KeyEvent.VK_CONTROL);

                    bot.delay(500);

                    bot.keyPress(KeyEvent.VK_ENTER);
                    bot.keyRelease(KeyEvent.VK_ENTER);

                    bot.delay(5000);

                } else {

                    bot.keyPress(KeyEvent.VK_ALT);
                    bot.keyPress(KeyEvent.VK_F4);

                    bot.keyRelease(KeyEvent.VK_F4);
                    bot.keyRelease(KeyEvent.VK_ALT);

                    bot.keyPress(KeyEvent.VK_ALT);
                    bot.keyPress(KeyEvent.VK_F4);

                    bot.keyRelease(KeyEvent.VK_F4);
                    bot.keyRelease(KeyEvent.VK_ALT);

                    tearDown();

                    continue;

                }
            } catch (NoSuchElementException e) {

                tearDown();

                continue;
            }
            tearDown();
        }
    }

    public String mesServiço(String mes) {

        switch (mes) {
            case "01":
                return "1";
            case "02":
                return "2";
            case "03":
                return "3";
            case "04":
                return "4";
            case "05":
                return "5";
            case "06":
                return "6";
            case "07":
                return "7";
            case "08":
                return "8";
            case "09":
                return "9";
            case "10":
                return "10";
            case "11":
                return "11";
            case "12":
                return "12";
        }
        return null;
    }
}
