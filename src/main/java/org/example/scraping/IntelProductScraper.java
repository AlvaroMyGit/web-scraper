package org.example.scraping;

import org.example.model.ProductIntelCPU;
import org.example.repository.CategoryRepository;
import org.example.repository.IntelProductRepository;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.time.Duration;
import java.util.logging.Level;
import java.util.logging.Logger;

@Component
public class IntelProductScraper implements ProductScraper<ProductIntelCPU> {

    private static final Logger logger = Logger.getLogger(IntelProductScraper.class.getName());
    private String productUrl;
    private final IntelProductRepository intelProductRepository;

    public IntelProductScraper(IntelProductRepository intelProductRepository, CategoryRepository categoryRepository) {
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

            // Use WebDriverWait to wait for the element to be present
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
            WebElement productDetails = wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//*[@id=\"product-details\"]")));

            ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", productDetails);
            Thread.sleep(1000);

            WebElement elementToClick = productDetails.findElement(By.xpath("//*[@id=\"product-details\"]/div[1]/div[2]"));
            ((JavascriptExecutor) driver).executeScript("arguments[0].click();", elementToClick);
            Thread.sleep(2000);
            logger.info("Clicked 'Specs' tab");

            ((JavascriptExecutor) driver).executeScript("document.body.style.zoom = '80%'");

            ProductIntelCPU product = new ProductIntelCPU();
            // Set Brand
            String brand = extractText(driver, "//*[@id=\"product-details\"]/div[2]/div[2]/table[2]/tbody/tr[1]/td");
            logger.info("Setting brand: " + brand);
            product.setBrand(brand);

// Set Processor Type
            String processorsType = extractText(driver, "//*[@id=\"product-details\"]/div[2]/div[2]/table[2]/tbody/tr[2]/td");
            logger.info("Setting processor type: " + processorsType);
            product.setProcessorsType(processorsType);

// Set Series
            String series = extractText(driver, "//*[@id=\"product-details\"]/div[2]/div[2]/table[2]/tbody/tr[3]/td");
            logger.info("Setting series: " + series);
            product.setSeries(series);

// Set Name
            String name = extractText(driver, "//*[@id=\"product-details\"]/div[2]/div[2]/table[2]/tbody/tr[4]/td");
            logger.info("Setting name: " + name);
            product.setName(name);

// Set Price
            BigDecimal price = parseBigDecimal(driver, "//*[@id=\"app\"]/div[3]/div/div/div/div[1]/div[1]/div[1]/div/ul/li[3]");
            logger.info("Setting price: " + price);
            product.setPrice(price);

// Set Model
            String model = extractText(driver, "//*[@id=\"product-details\"]/div[2]/div[2]/table[2]/tbody/tr[5]/td");
            logger.info("Setting model: " + model);
            product.setModel(model);

// Set CPU Socket Type
            String cpuSocketType = extractText(driver, "//*[@id=\"product-details\"]/div[2]/div[2]/table[3]/tbody/tr[1]/td");
            logger.info("Setting CPU socket type: " + cpuSocketType);
            product.setCpuSocketType(cpuSocketType);

// Set Core Name
            String coreName = extractText(driver, "//*[@id=\"product-details\"]/div[2]/div[2]/table[3]/tbody/tr[2]/td");
            logger.info("Setting core name: " + coreName);
            product.setCoreName(coreName);

// Set Number of Cores
            int numberOfCores = parseInt(driver, "//*[@id=\"product-details\"]/div[2]/div[2]/table[3]/tbody/tr[3]/td");
            logger.info("Setting number of cores: " + numberOfCores);
            product.setNumberOfCores(numberOfCores);

// Set Number of Threads
            int numberOfThreads = parseInt(driver, "//*[@id=\"product-details\"]/div[2]/div[2]/table[3]/tbody/tr[4]/td");
            logger.info("Setting number of threads: " + numberOfThreads);
            product.setNumberOfThreads(numberOfThreads);

// Set Operating Frequency
            double operatingFrequency = parseDouble(driver, "//*[@id=\"product-details\"]/div[2]/div[2]/table[3]/tbody/tr[5]");
            logger.info("Setting operating frequency: " + operatingFrequency);
            product.setOperatingFrequency(operatingFrequency);

// Set Max Turbo Frequency
            double maxTurboFrequency = parseDouble(driver, "//*[@id=\"product-details\"]/div[2]/div[2]/table[3]/tbody/tr[6]");
            logger.info("Setting max turbo frequency: " + maxTurboFrequency);
            product.setMaxTurboFrequency(maxTurboFrequency);

// Set L3 Cache
            String l3Cache = extractText(driver, "//*[@id=\"product-details\"]/div[2]/div[2]/table[3]/tbody/tr[8]/td");
            logger.info("Setting L3 cache: " + l3Cache);
            product.setL3Cache(l3Cache);

// Set Manufacturing Tech
            String manufacturingTech = extractText(driver, "//*[@id=\"product-details\"]/div[2]/div[2]/table[3]/tbody/tr[9]/td");
            logger.info("Setting manufacturing tech: " + manufacturingTech);
            product.setManufacturingTech(manufacturingTech);

// Set 64 Bit Support
            String support64Bit = extractText(driver, "//*[@id=\"product-details\"]/div[2]/div[2]/table[3]/tbody/tr[10]/td");
            logger.info("Setting 64 bit support: " + support64Bit);
            product.setSupport64Bit(support64Bit);

// Set Hyper Threading Support
            String hyperThreadingSupport = extractText(driver, "//*[@id=\"product-details\"]/div[2]/div[2]/table[3]/tbody/tr[11]/td");
            logger.info("Setting hyper threading support: " + hyperThreadingSupport);
            product.setHyperThreadingSupport(hyperThreadingSupport);

// Set Memory Types
            String memoryTypes = extractText(driver, "//*[@id=\"product-details\"]/div[2]/div[2]/table[3]/tbody/tr[12]/td");
            logger.info("Setting memory types: " + memoryTypes);
            product.setMemoryTypes(memoryTypes);

// Set Memory Channel
            int memoryChannel = parseInt(driver, "//*[@id=\"product-details\"]/div[2]/div[2]/table[3]/tbody/tr[13]/td");
            logger.info("Setting memory channel: " + memoryChannel);
            product.setMemoryChannel(memoryChannel);

// Set Max Memory Size
            int maxMemorySize = parseInt(driver, "//*[@id=\"product-details\"]/div[2]/div[2]/table[3]/tbody/tr[14]/td");
            logger.info("Setting max memory size: " + maxMemorySize);
            product.setMaxMemorySize(maxMemorySize);

// Set ECC Memory Supported
            String eccMemorySupported = extractText(driver, "//*[@id=\"product-details\"]/div[2]/div[2]/table[3]/tbody/tr[15]/td");
            logger.info("Setting ECC memory supported: " + eccMemorySupported);
            product.setEccMemorySupported(eccMemorySupported);

// Set Virtualization Technology Support
            String virtualizationTechnologySupport = extractText(driver, "//*[@id=\"product-details\"]/div[2]/div[2]/table[3]/tbody/tr[17]/td");
            logger.info("Setting virtualization technology support: " + virtualizationTechnologySupport);
            product.setVirtualizationTechnologySupport(virtualizationTechnologySupport);

// Set Integrated Graphics
            String integratedGraphics = extractText(driver, "//*[@id=\"product-details\"]/div[2]/div[2]/table[3]/tbody/tr[18]/td");
            logger.info("Setting integrated graphics: " + integratedGraphics);
            product.setIntegratedGraphics(integratedGraphics);

// Set Graphics Base Frequency
            int graphicsBaseFrequency = parseInt(driver, "//*[@id=\"product-details\"]/div[2]/div[2]/table[3]/tbody/tr[19]/td");
            logger.info("Setting graphics base frequency: " + graphicsBaseFrequency);
            product.setGraphicsBaseFrequency(graphicsBaseFrequency);

// Set Graphics Max Dynamic Frequency
            int graphicsMaxDynamicFrequency = parseInt(driver, "//*[@id=\"product-details\"]/div[2]/div[2]/table[3]/tbody/tr[20]/td");

            logger.info("Setting graphics max dynamic frequency: " + graphicsMaxDynamicFrequency);
            product.setGraphicsMaxDynamicFrequency(graphicsMaxDynamicFrequency);

            // Set Scalability
            String scalability = extractText(driver, "//*[@id=\"product-details\"]/div[2]/div[2]/table[3]/tbody/tr[21]/td");
            logger.info("Setting scalability: " + scalability);
            product.setScalability(scalability);

// Set PCI Express Revision
            String pciExpressRevision = extractText(driver, "//*[@id=\"product-details\"]/div[2]/div[2]/table[3]/tbody/tr[22]/td");
            logger.info("Setting PCI Express revision: " + pciExpressRevision);
            product.setPciExpressRevision(pciExpressRevision);

// Set Max Number of PCI Express Lanes
            String maxNumberOfPciExpressLanes = extractText(driver, "//*[@id=\"product-details\"]/div[2]/div[2]/table[3]/tbody/tr[24]/td");
            logger.info("Setting max number of PCI Express lanes: " + maxNumberOfPciExpressLanes);
            product.setMaxNumberOfPciExpressLanes(maxNumberOfPciExpressLanes);

// Set Thermal Design Power
            int thermalDesignPower = parseInt(driver, "//*[@id=\"product-details\"]/div[2]/div[2]/table[3]/tbody/tr[25]/td");
            logger.info("Setting thermal design power: " + thermalDesignPower);
            product.setThermalDesignPower(thermalDesignPower);

// Set Max Turbo Power
            int maxTurboPower = parseInt(driver, "//*[@id=\"product-details\"]/div[2]/div[2]/table[3]/tbody/tr[26]/td");
            logger.info("Setting max turbo power: " + maxTurboPower);
            product.setMaxTurboPower(maxTurboPower);

// Set Cooling Device
            String coolingDevice = extractText(driver, "//*[@id=\"product-details\"]/div[2]/div[2]/table[3]/tbody/tr[27]/td");
            logger.info("Setting cooling device: " + coolingDevice);
            product.setCoolingDevice(coolingDevice);

// Set Compatible Desktop Chipsets
            String compatibleDesktopChipsets = extractText(driver, "//*[@id=\"product-details\"]/div[2]/div[2]/table[3]/tbody/tr[28]/td");
            logger.info("Setting compatible desktop chipsets: " + compatibleDesktopChipsets);
            product.setCompatibleDesktopChipsets(compatibleDesktopChipsets);

// Set Operating System Supported
            String operatingSystemSupported = extractText(driver, "//*[@id=\"product-details\"]/div[2]/div[2]/table[3]/tbody/tr[29]/td");
            logger.info("Setting operating system supported: " + operatingSystemSupported);
            product.setOperatingSystemSupported(operatingSystemSupported);

// Set Advanced Technologies
            String advancedTechnologies = extractText(driver, "//*[@id=\"product-details\"]/div[2]/div[2]/table[3]/tbody/tr[30]/td");
            logger.info("Setting advanced technologies: " + advancedTechnologies);
            product.setAdvancedTechnologies(advancedTechnologies);

// Set Security And Reliability
            String securityAndReliability = extractText(driver, "//*[@id=\"product-details\"]/div[2]/div[2]/table[3]/tbody/tr[31]/td");
            logger.info("Setting security and reliability: " + securityAndReliability);
            product.setSecurityAndReliability(securityAndReliability);

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

    private BigDecimal parseBigDecimal(WebDriver driver, String xpath) {
        try {
            String text = driver.findElement(By.xpath(xpath)).getText().replaceAll("[^\\d.]", "");
            return text.isEmpty() ? BigDecimal.ZERO : new BigDecimal(text);
        } catch (Exception e) {
            logger.log(Level.WARNING, "Failed to parse BigDecimal for xpath: " + xpath, e);
            return BigDecimal.ZERO;
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
