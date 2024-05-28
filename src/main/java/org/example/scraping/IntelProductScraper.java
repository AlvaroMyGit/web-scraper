package org.example.scraping;

import org.example.model.ProductIntelCPU;
import org.example.model.ProductRyzenCPU;
import org.example.repository.IntelProductRepository;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.springframework.stereotype.Component;

import java.util.logging.Level;
import java.util.logging.Logger;

@Component
public class IntelProductScraper implements ProductScraper<ProductIntelCPU> {

    private static final Logger logger = Logger.getLogger(IntelProductScraper.class.getName());
    private String productUrl;
    private final IntelProductRepository intelProductRepository;

    public IntelProductScraper(IntelProductRepository intelProductRepository) {
        this.intelProductRepository = intelProductRepository;
    }

    @Override
    public ProductIntelCPU call() {
        ChromeOptions options = new ChromeOptions();
        //options.addArguments("--headless");

        WebDriver driver = new ChromeDriver(options);

        try {
            driver.get(productUrl);
            logger.info("Navigated to product URL: " + productUrl);

            ((JavascriptExecutor) driver).executeScript("window.scrollTo(0, document.body.scrollHeight)");
            Thread.sleep(4000);
            logger.info("Waited for stability after scrolling");

            WebElement productDetails = driver.findElement(By.xpath("//*[@id=\"product-details\"]"));
            ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", productDetails);
            Thread.sleep(2000);
            ((JavascriptExecutor) driver).executeScript("window.scrollBy(0, 100)");

            WebElement elementToClick = productDetails.findElement(By.xpath("//*[@id=\"product-details\"]/div[1]/div[2]"));
            ((JavascriptExecutor) driver).executeScript("arguments[0].click();", elementToClick);
            Thread.sleep(3000);
            logger.info("Clicked 'Specs' tab");

            ProductIntelCPU product = new ProductIntelCPU();
            product.setBrand(extractText(driver, "//*[@id=\"product-details\"]/div[2]/div[2]/table[2]/tbody/tr[1]/td"));
            product.setProcessorsType(extractText(driver, "//*[@id=\"product-details\"]/div[2]/div[2]/table[2]/tbody/tr[2]/td"));
            product.setSeries(extractText(driver, "//*[@id=\"product-details\"]/div[2]/div[2]/table[2]/tbody/tr[3]/td"));
            product.setName(extractText(driver, "//*[@id=\"product-details\"]/div[2]/div[2]/table[2]/tbody/tr[4]/td"));
            product.setModel(extractText(driver, "//*[@id=\"product-details\"]/div[2]/div[2]/table[2]/tbody/tr[5]/td"));
            product.setCpuSocketType(extractText(driver, "//*[@id=\"product-details\"]/div[2]/div[2]/table[3]/tbody/tr[1]/td"));
            product.setCoreName(extractText(driver, "//*[@id=\"product-details\"]/div[2]/div[2]/table[3]/tbody/tr[2]/td"));
            product.setNumberOfCores(parseInt(driver, "//*[@id=\"product-details\"]/div[2]/div[2]/table[3]/tbody/tr[3]/td"));
            product.setNumberOfThreads(parseInt(driver, "//*[@id=\"product-details\"]/div[2]/div[2]/table[3]/tbody/tr[4]/td"));
            product.setOperatingFrequency(parseDouble(driver, "//*[@id=\"product-details\"]/div[2]/div[2]/table[3]/tbody/tr[5]"));
            product.setMaxTurboFrequency(parseDouble(driver, "//*[@id=\"product-details\"]/div[2]/div[2]/table[3]/tbody/tr[6]"));
            product.setPCoreFrequency(parseDouble(driver, "//*[@id=\"product-details\"]/div[2]/div[2]/table[3]/tbody/tr[6]/td/text()[2]"));
            product.setECoreFrequency(parseDouble(driver, "//*[@id=\"product-details\"]/div[2]/div[2]/table[3]/tbody/tr[6]/td/text()[3]"));
            product.setL2Cache(extractText(driver, "//*[@id=\"product-details\"]/div[2]/div[2]/table[3]/tbody/tr[7]/td"));
            product.setL3Cache(extractText(driver, "//*[@id=\"product-details\"]/div[2]/div[2]/table[3]/tbody/tr[8]/td"));
            product.setManufacturingTech(extractText(driver, "//*[@id=\"product-details\"]/div[2]/div[2]/table[3]/tbody/tr[9]/td"));
            product.setSupport64Bit(extractText(driver, "//*[@id=\"product-details\"]/div[2]/div[2]/table[3]/tbody/tr[10]/td"));
            product.setHyperThreadingSupport(extractText(driver, "//*[@id=\"product-details\"]/div[2]/div[2]/table[3]/tbody/tr[11]/td"));
            product.setMemoryTypes(extractText(driver, "//*[@id=\"product-details\"]/div[2]/div[2]/table[3]/tbody/tr[12]/td"));
            product.setMemoryChannel(parseInt(driver, "//*[@id=\"product-details\"]/div[2]/div[2]/table[3]/tbody/tr[13]/td"));
            product.setMaxMemorySize(parseInt(driver, "//*[@id=\"product-details\"]/div[2]/div[2]/table[3]/tbody/tr[14]/td"));
            product.setEccMemorySupported(extractText(driver, "//*[@id=\"product-details\"]/div[2]/div[2]/table[3]/tbody/tr[15]/td"));
            product.setVirtualizationTechnologySupport(extractText(driver, "//*[@id=\"product-details\"]/div[2]/div[2]/table[3]/tbody/tr[17]/td"));
            product.setIntegratedGraphics(extractText(driver, "//*[@id=\"product-details\"]/div[2]/div[2]/table[3]/tbody/tr[18]/td"));
            product.setGraphicsBaseFrequency(parseInt(driver, "//*[@id=\"product-details\"]/div[2]/div[2]/table[3]/tbody/tr[19]/td"));
            product.setGraphicsMaxDynamicFrequency(parseInt(driver, "//*[@id=\"product-details\"]/div[2]/div[2]/table[3]/tbody/tr[20]/td"));
            product.setScalability(extractText(driver, "//*[@id=\"product-details\"]/div[2]/div[2]/table[3]/tbody/tr[21]/td"));
            product.setPciExpressRevision(extractText(driver, "//*[@id=\"product-details\"]/div[2]/div[2]/table[3]/tbody/tr[22]/td"));
            product.setMaxNumberOfPciExpressLanes(extractText(driver, "//*[@id=\"product-details\"]/div[2]/div[2]/table[3]/tbody/tr[24]/td"));
            product.setThermalDesignPower(parseInt(driver, "//*[@id=\"product-details\"]/div[2]/div[2]/table[3]/tbody/tr[25]/td"));
            product.setMaxTurboPower(parseInt(driver, "//*[@id=\"product-details\"]/div[2]/div[2]/table[3]/tbody/tr[26]/td"));
            product.setCoolingDevice(extractText(driver, "//*[@id=\"product-details\"]/div[2]/div[2]/table[3]/tbody/tr[27]/td"));
            product.setCompatibleDesktopChipsets(extractText(driver, "//*[@id=\"product-details\"]/div[2]/div[2]/table[3]/tbody/tr[28]/td"));
            product.setOperatingSystemSupported(extractText(driver, "//*[@id=\"product-details\"]/div[2]/div[2]/table[3]/tbody/tr[29]/td"));
            product.setAdvancedTechnologies(extractText(driver, "//*[@id=\"product-details\"]/div[2]/div[2]/table[3]/tbody/tr[30]/td"));
            product.setSecurityAndReliability(extractText(driver, "//*[@id=\"product-details\"]/div[2]/div[2]/table[3]/tbody/tr[31]/td"));
            // Save the product to the database
            saveProduct(product);

            logger.info("Product saved successfully");
            return product;
        } catch (Exception e) {
            logger.log(Level.SEVERE, "Error scraping product data from URL: " + productUrl, e);
        } finally {
            driver.quit();
            logger.info("Driver quit");
        }
        return null;
    }

    private String extractText(WebDriver driver, String xpath) {
        try {
            return driver.findElement(By.xpath(xpath)).getText();
        } catch (Exception e) {
            logger.log(Level.WARNING, "Failed to extract text for xpath: " + xpath, e);
            return "";
        }
    }

    private int parseInt(WebDriver driver, String xpath) {
        try {
            String text = driver.findElement(By.xpath(xpath)).getText().replaceAll("\\D", "");
            return text.isEmpty() ? 0 : Integer.parseInt(text);
        } catch (Exception e) {
            logger.log(Level.WARNING, "Failed to parse int for xpath: " + xpath, e);
            return 0;
        }
    }

    private double parseDouble(WebDriver driver, String xpath) {
        try {
            String text = driver.findElement(By.xpath(xpath)).getText().replaceAll("[^\\d.]", "");
            return text.isEmpty() ? 0.0 : Double.parseDouble(text);
        } catch (Exception e) {
            logger.log(Level.WARNING, "Failed to parse double for xpath: " + xpath, e);
            return 0.0;
        }
    }

    @Override
    public String extractBrand() {
        // Extract the brand from the product URL
        if (productUrl.contains("intel")) {
            return "INTEL";
        } else {
            return "Unknown"; // Handle the case where brand is not found or unrecognized
        }
    }

    @Override
    public void saveProduct(ProductIntelCPU product) {
        if (product != null) {
            intelProductRepository.save(product);
            logger.info("Product saved successfully");
        } else {
            logger.warning("Attempted to save null product");
        }
    }

    public void setProductUrl(String productUrl) {
        this.productUrl = productUrl;
    }
}
