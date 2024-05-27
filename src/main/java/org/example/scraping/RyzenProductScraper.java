package org.example.scraping;

import org.example.model.Category;
import org.example.model.ProductRyzenCPU;
import org.example.repository.ProductRepository;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.springframework.stereotype.Component;


import java.util.logging.Level;
import java.util.logging.Logger;

@Component
public class RyzenProductScraper implements ProductScraper {


    private static final Logger logger = Logger.getLogger(RyzenProductScraper.class.getName());
    private String productUrl;
    private final ProductRepository productRepository;

    public RyzenProductScraper(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }


    @Override
    public ProductRyzenCPU call() {
        ChromeOptions options = new ChromeOptions();
        // Run in headless mode for better performance
        options.addArguments("--headless");
        WebDriver driver = new ChromeDriver(options);

        try {
            driver.get(productUrl);
            logger.info("Navigated to product URL: " + productUrl);

            // Scroll down the page to load additional content
            ((JavascriptExecutor) driver).executeScript("window.scrollTo(0, document.body.scrollHeight)");

            // Wait for stability after scrolling
            //Thread.sleep(2000); // Adjust the wait time as needed
            logger.info("Waited for stability after scrolling");

            // Locate the product details section
            WebElement productDetails = driver.findElement(By.xpath("//*[@id='product-details']"));

            // Scroll to the product details section
            ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", productDetails);
            //Thread.sleep(1000); // Small wait to ensure scroll has completed

            // Scroll a bit further down to ensure the target element is in view
            ((JavascriptExecutor) driver).executeScript("window.scrollBy(0, 100)");

            // Click on the target element within the product details section
            WebElement elementToClick = productDetails.findElement(By.xpath("//*[@id=\"product-details\"]/div[1]/div[2]"));
            ((JavascriptExecutor) driver).executeScript("arguments[0].click();", elementToClick);
            Thread.sleep(2000); // Wait for the specs tab content to load
            logger.info("Clicked 'Specs' tab");

            String price = driver.findElement(By.cssSelector(".price-current")).getText();
            logger.info("Price: " + price);

            // Now locate the elements under the "Specs" tab
            String brand = driver.findElement(By.xpath("//*[@id=\"product-details\"]/div[2]/div[2]/table[2]/tbody/tr[1]/td")).getText();
            logger.info("Brand:" + brand);

            String processorsType = driver.findElement(By.xpath("//*[@id=\"product-details\"]/div[2]/div[2]/table[2]/tbody/tr[2]/td")).getText();
            logger.info("Processor Type: " + processorsType);

            String series = driver.findElement(By.xpath("//*[@id=\"product-details\"]/div[2]/div[2]/table[2]/tbody/tr[3]/td")).getText();
            logger.info("Series: " + series);

            String name = driver.findElement(By.xpath("//*[@id=\"product-details\"]/div[2]/div[2]/table[2]/tbody/tr[4]/td")).getText();
            logger.info("Name: " + name);

            String model = driver.findElement(By.xpath("//*[@id=\"product-details\"]/div[2]/div[2]/table[2]/tbody/tr[5]/td")).getText();
            logger.info("Model: " + model);

            String cpuSocketType = driver.findElement(By.xpath("//*[@id=\"product-details\"]/div[2]/div[2]/table[3]/tbody/tr[1]/td")).getText();
            logger.info("Socket: " + cpuSocketType);

            String numberOfCoresText = driver.findElement(By.xpath("//*[@id=\"product-details\"]/div[2]/div[2]/table[3]/tbody/tr[2]/td"))
                    .getText()
                    .replaceAll("\\D", ""); // Remove non-digit characters

            int numberOfCores = 0; // Default value in case the text is empty
            if (!numberOfCoresText.isEmpty()) {
                numberOfCores = Integer.parseInt(numberOfCoresText);
            }
            logger.info("Number of Cores: " + numberOfCores);

            String numberOfThreadsText = driver.findElement(By.xpath("//*[@id=\"product-details\"]/div[2]/div[2]/table[3]/tbody/tr[3]/td")).getText();
            String[] numberOfThreadsParts = numberOfThreadsText.split("-");
            int numberOfThreads = Integer.parseInt(numberOfThreadsParts[0].trim());
            logger.info("Number of Threads: " + numberOfThreads);

            String operatingFrequencyText = driver.findElement(By.xpath("//*[@id=\"product-details\"]/div[2]/div[2]/table[3]/tbody/tr[4]/td")).getText();
            double operatingFrequency = Double.parseDouble(operatingFrequencyText.replaceAll("[^0-9.]", ""));
            logger.info("Operating Frequency: " + operatingFrequency);

            // Extracting max turbo frequency from the text
            String maxTurboFrequencyText = driver.findElement(By.xpath("//*[@id=\"product-details\"]/div[2]/div[2]/table[3]/tbody/tr[5]/td"))
                    .getText()
                    .replaceAll("[^\\d.]+", "") // Remove non-digit characters except the first decimal point
                    .replaceFirst("\\.(?=.*\\.)", ""); // Remove all subsequent decimal points

            double maxTurboFrequency = 0.0; // Default value in case the text is empty
            if (!maxTurboFrequencyText.isEmpty()) {
                maxTurboFrequency = Double.parseDouble(maxTurboFrequencyText);
            }
            logger.info("Max Turbo Frequency: " + maxTurboFrequency);

            String l1Cache = driver.findElement(By.xpath("//*[@id=\"product-details\"]/div[2]/div[2]/table[3]/tbody/tr[6]/td")).getText();
            logger.info("L1 Cache: " + l1Cache);

            String l2Cache = driver.findElement(By.xpath("//*[@id=\"product-details\"]/div[2]/div[2]/table[3]/tbody/tr[7]/td")).getText();
            logger.info("L2 Cache: " + l2Cache);

            String l3Cache = driver.findElement(By.xpath("//*[@id=\"product-details\"]/div[2]/div[2]/table[3]/tbody/tr[8]/td")).getText();
            logger.info("L3 Cache: " + l3Cache);

            String manufacturingTech = driver.findElement(By.xpath("//*[@id=\"product-details\"]/div[2]/div[2]/table[3]/tbody/tr[9]/td")).getText();
            logger.info("Manufacturing Tech: " + manufacturingTech);

            String memoryTypes = driver.findElement(By.xpath("//*[@id=\"product-details\"]/div[2]/div[2]/table[3]/tbody/tr[10]/td")).getText();
            logger.info("Memory Types: " + memoryTypes);

            String memoryChannelText = driver.findElement(By.xpath("//*[@id=\"product-details\"]/div[2]/div[2]/table[3]/tbody/tr[11]/td")).getText();
            int memoryChannel = 0; // Default value in case the text is empty or non-numeric
            if (!memoryChannelText.isEmpty()) {
                // Remove non-numeric characters from the string
                String numericPart = memoryChannelText.replaceAll("\\D", "");
                if (!numericPart.isEmpty()) {
                    // Parse the numeric part into an integer
                    memoryChannel = Integer.parseInt(numericPart);
                }
            }
            logger.info("Memory Channel: " + memoryChannel);

            String isEccMemorySupported = driver.findElement(By.xpath("//*[@id=\"product-details\"]/div[2]/div[2]/table[3]/tbody/tr[13]/td")).getText();
            logger.info("Is Ecc Memory Supported: " + isEccMemorySupported);

            String integratedGraphics = driver.findElement(By.xpath("//*[@id=\"product-details\"]/div[2]/div[2]/table[3]/tbody/tr[14]/td")).getText();
            logger.info("Integrated Graphics: " + integratedGraphics);

            String graphicsBaseFrequencyText = driver.findElement(By.xpath("//*[@id=\"product-details\"]/div[2]/div[2]/table[3]/tbody/tr[15]/td")).getText();
            int graphicsBaseFrequency = Integer.parseInt(graphicsBaseFrequencyText.replaceAll("[^0-9]", ""));
            logger.info("Graphics Base Frequency: " + graphicsBaseFrequency);

            String pciExpressRevision = driver.findElement(By.xpath("//*[@id=\"product-details\"]/div[2]/div[2]/table[3]/tbody/tr[16]/td")).getText();
            logger.info("PCI Express Revision: " + pciExpressRevision);

            String thermalDesignPowerText = driver.findElement(By.xpath("//*[@id=\"product-details\"]/div[2]/div[2]/table[3]/tbody/tr[17]/td")).getText();
            int thermalDesignPower = Integer.parseInt(thermalDesignPowerText.replaceAll("[^0-9]", ""));
            logger.info("Thermal Design Power: " + thermalDesignPower);

            String coolingDevice = driver.findElement(By.xpath("//*[@id=\"product-details\"]/div[2]/div[2]/table[3]/tbody/tr[18]/td")).getText();
            logger.info("Cooling Device: " + coolingDevice);

            String operatingSystemSupported = driver.findElement(By.xpath("//*[@id=\"product-details\"]/div[2]/div[2]/table[3]/tbody/tr[19]/td")).getText();
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
            product.setMemoryTypes(memoryTypes);
            product.setMemoryChannel(memoryChannel);
            product.setIsEccMemorySupported(isEccMemorySupported);
            product.setIntegratedGraphics(integratedGraphics);
            product.setGraphicsBaseFrequency(graphicsBaseFrequency);
            product.setPciExpressRevision(pciExpressRevision);
            product.setThermalDesignPower(thermalDesignPower);
            product.setCoolingDevice(coolingDevice);
            product.setOperatingSystemSupported(operatingSystemSupported);
            product.setCategory(new Category("CPU", "Processors"));
            // Save the product to the database
            productRepository.save(product);

            logger.info("Product saved successfully");

            return product;
        } catch (Exception e) {
            logger.log(Level.SEVERE, "Error scraping or saving product data from URL: " + productUrl, e);
            return null; // Or handle the error as needed
        } finally {
            driver.quit();
            logger.info("Driver quit");
        }
    }

    @Override
    public String extractBrand() {
        // Extract the brand from the product URL
        if (productUrl.contains("amd")) {
            return "AMD";
        } else {
            return "Unknown"; // Handle the case where brand is not found or unrecognized
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
