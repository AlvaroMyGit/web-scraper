package org.example.scraping;

import org.example.model.Category;
import org.example.model.ProductRyzenCPU;
import org.example.repository.RyzenProductRepository;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.springframework.stereotype.Component;

import java.util.logging.Level;
import java.util.logging.Logger;

@Component
public class RyzenProductScraper implements ProductScraper<ProductRyzenCPU> {

    private static final Logger logger = Logger.getLogger(RyzenProductScraper.class.getName());
    private String productUrl;
    private final RyzenProductRepository productRepository;

    public RyzenProductScraper(RyzenProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @Override
    public ProductRyzenCPU call() {
        ChromeOptions options = new ChromeOptions();
        //options.addArguments("--headless");

        WebDriver driver = new ChromeDriver(options);

        try {
            driver.get(productUrl);
            logger.info("Navigated to product URL: " + productUrl);

            ((JavascriptExecutor) driver).executeScript("window.scrollTo(0, document.body.scrollHeight)");
            Thread.sleep(2000);
            logger.info("Waited for stability after scrolling");

            WebElement productDetails = driver.findElement(By.xpath("//*[@id=\"product-details\"]"));
            ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", productDetails);
            Thread.sleep(1000);
            ((JavascriptExecutor) driver).executeScript("window.scrollBy(0, 100)");

            WebElement elementToClick = productDetails.findElement(By.xpath("//*[@id=\"product-details\"]/div[1]/div[2]"));
            ((JavascriptExecutor) driver).executeScript("arguments[0].click();", elementToClick);
            Thread.sleep(2000);
            logger.info("Clicked 'Specs' tab");

            String price = extractText(driver, "//*[@id=\"product-mini-feature\"]/div/div[1]/div/div[2]/ul/li[3]/span");
            logger.info("Price: " + price);

            String brand = extractText(driver, "//*[@id=\"product-details\"]/div[2]/div[2]/table[2]/tbody/tr[1]/td");
            logger.info("Brand: " + brand);

            String processorsType = extractText(driver, "//*[@id=\"product-details\"]/div[2]/div[2]/table[2]/tbody/tr[2]/td");
            logger.info("Processor Type: " + processorsType);

            String series = extractText(driver, "//*[@id=\"product-details\"]/div[2]/div[2]/table[2]/tbody/tr[3]/td");
            logger.info("Series: " + series);

            String name = extractText(driver, "//*[@id=\"product-details\"]/div[2]/div[2]/table[2]/tbody/tr[4]/td");
            logger.info("Name: " + name);

            String model = extractText(driver, "//*[@id=\"product-details\"]/div[2]/div[2]/table[2]/tbody/tr[5]/td");
            logger.info("Model: " + model);

            String cpuSocketType = extractText(driver, "//*[@id=\"product-details\"]/div[2]/div[2]/table[3]/tbody/tr[1]/td");
            logger.info("Socket: " + cpuSocketType);

            int numberOfCores = parseInt(driver, "//*[@id=\"product-details\"]/div[2]/div[2]/table[3]/tbody/tr[2]/td");
            logger.info("Number of Cores: " + numberOfCores);

            int numberOfThreads = parseInt(driver, "//*[@id=\"product-details\"]/div[2]/div[2]/table[3]/tbody/tr[3]/td");
            logger.info("Number of Threads: " + numberOfThreads);

            double operatingFrequency = parseDouble(driver, "//*[@id=\"product-details\"]/div[2]/div[2]/table[3]/tbody/tr[4]/td");
            logger.info("Operating Frequency: " + operatingFrequency);

            double maxTurboFrequency = parseDouble(driver, "/html/body/div[36]/div[3]/div/div/div/div[2]/div[2]/div/div[1]/div[6]/div[2]/div[2]/table[3]/tbody/tr[5]/td");
            logger.info("Max Turbo Frequency: " + maxTurboFrequency);

            String l1Cache = extractText(driver, "//*[@id=\"product-details\"]/div[2]/div[2]/table[3]/tbody/tr[6]/td");
            logger.info("L1 Cache: " + l1Cache);

            String l2Cache = extractText(driver, "/html/body/div[36]/div[3]/div/div/div/div[2]/div[2]/div/div[1]/div[6]/div[2]/div[2]/table[3]/tbody/tr[7]/td");
            logger.info("L2 Cache: " + l2Cache);

            String l3Cache = extractText(driver, "/html/body/div[36]/div[3]/div/div/div/div[2]/div[2]/div/div[1]/div[6]/div[2]/div[2]/table[3]/tbody/tr[8]/td");
            logger.info("L3 Cache: " + l3Cache);

            String manufacturingTech = extractText(driver, "/html/body/div[36]/div[3]/div/div/div/div[2]/div[2]/div/div[1]/div[6]/div[2]/div[2]/table[3]/tbody/tr[9]/td");
            logger.info("Manufacturing Tech: " + manufacturingTech);

            String support64Bit = extractText(driver, "//*[@id=\"product-details\"]/div[2]/div[2]/table[3]/tbody/tr[10]/td");
            logger.info("64 Bit Support: " + support64Bit);

            String memoryTypes = extractText(driver, "//*[@id=\"product-details\"]/div[2]/div[2]/table[3]/tbody/tr[11]/td");
            logger.info("Memory Types: " + memoryTypes);

            int memoryChannel = parseInt(driver, "/html/body/div[36]/div[3]/div/div/div/div[2]/div[2]/div/div[1]/div[6]/div[2]/div[2]/table[3]/tbody/tr[12]/td");
            logger.info("Memory Channel: " + memoryChannel);

            String isEccMemorySupported = extractText(driver, "//*[@id=\"product-details\"]/div[2]/div[2]/table[3]/tbody/tr[13]/td");
            logger.info("Is ECC Memory Supported: " + isEccMemorySupported);

            String integratedGraphics = extractText(driver, "//*[@id=\"product-details\"]/div[2]/div[2]/table[3]/tbody/tr[14]/td");
            logger.info("Integrated Graphics: " + integratedGraphics);

            int graphicsBaseFrequency = parseInt(driver, "//*[@id=\"product-details\"]/div[2]/div[2]/table[3]/tbody/tr[15]/td");
            logger.info("Graphics Base Frequency: " + graphicsBaseFrequency);

            int graphicsMaxBaseFrequency = parseInt(driver, "//*[@id=\"product-details\"]/div[2]/div[2]/table[3]/tbody/tr[16]/td");
            logger.info("Graphics Max Base Frequency: " + graphicsMaxBaseFrequency);

            String pciExpressRevision = extractText(driver, "//*[@id=\"product-details\"]/div[2]/div[2]/table[3]/tbody/tr[17]/td");
            logger.info("PCI Express Revision: " + pciExpressRevision);

            int thermalDesignPower = parseInt(driver, "//*[@id=\"product-details\"]/div[2]/div[2]/table[3]/tbody/tr[18]/td");
            logger.info("Thermal Design Power: " + thermalDesignPower);

            String coolingDevice = extractText(driver, "//*[@id=\"product-details\"]/div[2]/div[2]/table[3]/tbody/tr[19]/td");
            logger.info("Cooling Device: " + coolingDevice);

            WebElement lastElement = driver.findElement(By.xpath("//*[@id=\"product-details\"]/div[2]/div[2]/table[3]/tbody/tr[20]/td"));

            // Scroll the element into view
            ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", lastElement);
            // Wait for a brief moment to ensure the scroll action completes
            Thread.sleep(1000); // Adjust the wait time as needed

            String operatingSystemSupported = extractText(driver, "//*[@id=\"product-details\"]/div[2]/div[2]/table[3]/tbody/tr[20]/td");
            logger.info("Operating System Supported: " + operatingSystemSupported);

            ProductRyzenCPU product = new ProductRyzenCPU();
            product.setBrand(brand);
            product.setProcessorsType(processorsType);
            product.setSeries(series);
            product.setName(name);
            product.setModel(model);
            product.setCpuSocketType(cpuSocketType);
            product.setNumberOfCores(numberOfCores);
            product.setNumberOfThreads(numberOfThreads);
            product.setOperatingFrequency(operatingFrequency);
            product.setMaxTurboFrequency(maxTurboFrequency);
            product.setL1Cache(l1Cache);
            product.setL2Cache(l2Cache);
            product.setL3Cache(l3Cache);
            product.setManufacturingTech(manufacturingTech);
            product.setSupport64Bit(support64Bit);
            product.setMemoryTypes(memoryTypes);
            product.setMemoryChannel(memoryChannel);
            product.setIsEccMemorySupported(isEccMemorySupported);
            product.setIntegratedGraphics(integratedGraphics);
            product.setGraphicsBaseFrequency(graphicsBaseFrequency);
            product.setGraphicsMaxBaseFrequency(graphicsMaxBaseFrequency);
            product.setPciExpressRevision(pciExpressRevision);
            product.setThermalDesignPower(thermalDesignPower);
            product.setCoolingDevice(coolingDevice);
            product.setOperatingSystemSupported(operatingSystemSupported);
            product.setCategory(new Category("CPU", "Processors"));

            saveProduct(product);

            logger.info("Product saved successfully");

            return product;
        } catch (Exception e) {
            logger.log(Level.SEVERE, "Error scraping or saving product data from URL: " + productUrl, e);
            return null;
        } finally {
            driver.quit();
            logger.info("Driver quit");
        }
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
        if (productUrl.contains("amd")) {
            return "AMD";
        } else {
            return "Unknown";
        }
    }

    @Override
    public void saveProduct(ProductRyzenCPU product) {
        if (product != null) {
            productRepository.save(product);
            logger.info("Product saved successfully");
        } else {
            logger.warning("Attempted to save null product");
        }
    }

    public String getProductUrl() {
        return productUrl;
    }

    public void setProductUrl(String productUrl) {
        this.productUrl = productUrl;
    }
}
