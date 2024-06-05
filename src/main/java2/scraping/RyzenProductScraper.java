package org.example.scraping;

import org.example.model.Category;
import org.example.model.ProductRyzenCPU;
import org.example.repository.CategoryRepository;
import org.example.repository.RyzenProductRepository;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.logging.Level;
import java.util.logging.Logger;

@Component
public class RyzenProductScraper implements ProductScraper<ProductRyzenCPU> {

    private static final Logger logger = Logger.getLogger(RyzenProductScraper.class.getName());
    private String productUrl;
    private final RyzenProductRepository productRepository;
    private final CategoryRepository categoryRepository;

    public RyzenProductScraper(RyzenProductRepository productRepository, CategoryRepository categoryRepository) {
        this.productRepository = productRepository;
        this.categoryRepository = categoryRepository;
    }

    @Override
    public ProductRyzenCPU call() {
        try {
            Document doc = Jsoup.connect(productUrl).get();
            logger.info("Connected to product URL: " + productUrl);

            Element specsTabPane = doc.getElementById("product-details");
            if (specsTabPane != null) {
                Element specsTable = specsTabPane.select("div.tab-pane").get(1);
                if (specsTable != null) {
                    String brand = getValueByLabel(specsTable, "Brand");
                    logger.info("Brand: " + brand);

                    String processorsType = getValueByLabel(specsTable, "Processors Type");
                    logger.info("Processor Type: " + processorsType);

                    String series = getValueByLabel(specsTable, "Series");
                    logger.info("Series: " + series);

                    String name = getValueByLabel(specsTable, "Name");
                    logger.info("Name: " + name);

                    BigDecimal price = parseBigDecimal(doc, "#app > div:nth-child(3) > div > div > div > div:nth-child(1) > div:nth-child(1) > div:nth-child(1) > div:nth-child(1) > ul > li:nth-child(3)");
                    logger.info("Price: " + price);

                    String model = getValueByLabel(specsTable, "Model");
                    logger.info("Model: " + model);

                    String cpuSocketType = getValueByLabel(specsTable, "CPU Socket Type");
                    logger.info("Socket: " + cpuSocketType);

                    int numberOfCores = parseInt(specsTable, "# of Cores");
                    logger.info("Number of Cores: " + numberOfCores);

                    int numberOfThreads = parseInt(specsTable, "# of Threads");
                    logger.info("Number of Threads: " + numberOfThreads);

                    double operatingFrequency = parseDouble(specsTable, "Operating Frequency");
                    logger.info("Operating Frequency: " + operatingFrequency);

                    double maxTurboFrequency = parseDouble(specsTable, "Max Turbo Frequency");
                    logger.info("Max Turbo Frequency: " + maxTurboFrequency);

                    String l1Cache = getValueByLabel(specsTable, "L1 Cache");
                    logger.info("L1 Cache: " + l1Cache);

                    String l2Cache = getValueByLabel(specsTable, "L2 Cache");
                    logger.info("L2 Cache: " + l2Cache);

                    String l3Cache = getValueByLabel(specsTable, "L3 Cache");
                    logger.info("L3 Cache: " + l3Cache);

                    String manufacturingTech = getValueByLabel(specsTable, "Manufacturing Tech");
                    logger.info("Manufacturing Tech: " + manufacturingTech);

                    String support64Bit = getValueByLabel(specsTable, "64-Bit Support");
                    logger.info("64 Bit Support: " + support64Bit);

                    String memoryTypes = getValueByLabel(specsTable, "Memory Types");
                    logger.info("Memory Types: " + memoryTypes);

                    int memoryChannel = parseInt(specsTable, "Memory Channel");
                    logger.info("Memory Channel: " + memoryChannel);

                    String isEccMemorySupported = getValueByLabel(specsTable, "ECC Memory");
                    logger.info("Is ECC Memory Supported: " + isEccMemorySupported);

                    String integratedGraphics = getValueByLabel(specsTable, "Integrated Graphics");
                    logger.info("Integrated Graphics: " + integratedGraphics);

                    int graphicsBaseFrequency = parseInt(specsTable, "Graphics Base Frequency");
                    logger.info("Graphics Base Frequency: " + graphicsBaseFrequency);

                    int graphicsMaxBaseFrequency = parseInt(specsTable, "Graphics Max Dynamic Frequency");
                    logger.info("Graphics Max Base Frequency: " + graphicsMaxBaseFrequency);

                    String pciExpressRevision = getValueByLabel(specsTable, "PCI Express Revision");
                    logger.info("PCI Express Revision: " + pciExpressRevision);

                    int thermalDesignPower = parseInt(specsTable, "Thermal Design Power");
                    logger.info("Thermal Design Power: " + thermalDesignPower);

                    String coolingDevice = getValueByLabel(specsTable, "Cooling Device");
                    logger.info("Cooling Device: " + coolingDevice);

                    String operatingSystemSupported = getValueByLabel(specsTable, "Operating System Supported");
                    logger.info("Operating System Supported: " + operatingSystemSupported);

                    ProductRyzenCPU product = new ProductRyzenCPU();
                    Category cpuCategory = categoryRepository.findByName("CPU");
                    product.setBrand(brand);
                    product.setProcessorsType(processorsType);
                    product.setSeries(series);
                    product.setName(name);
                    product.setPrice(price);
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
                    product.setCategory(cpuCategory);

                    saveProduct(product);

                    logger.info("Product saved successfully");

                    return product;
                } else {
                    logger.warning("No specifications table found.");
                }
            } else {
                logger.warning("No specs tab pane found.");
            }
        } catch (IOException e) {
            logger.log(Level.SEVERE, "Error connecting to product URL: " + productUrl, e);
        }

        return null;
    }

    private String getValueByLabel(Element table, String label) {
        return table.select("tr:has(th:contains(" + label + ")) td").text();
    }

    private BigDecimal parseBigDecimal(Document doc, String selector) {
        try {
            String text = doc.select(selector).text().replaceAll("[^\\d.]", "");
            return text.isEmpty() ? BigDecimal.ZERO : new BigDecimal(text);
        } catch (Exception e) {
            logger.log(Level.WARNING, "Failed to parse BigDecimal for selector: " + selector, e);
            return BigDecimal.ZERO;
        }
    }

    private int parseInt(Element table, String label) {
        try {
            String text = getValueByLabel(table, label).replaceAll("\\D", "");
            return text.isEmpty() ? 0 : Integer.parseInt(text);
        } catch (Exception e) {
            logger.log(Level.WARNING, "Failed to parse int for label: " + label, e);
            return 0;
        }
    }

    private double parseDouble(Element table, String label) {
        try {
            String text = getValueByLabel(table, label).replaceAll("[^\\d.]", "");
            return text.isEmpty() ? 0.0 : Double.parseDouble(text);
        } catch (Exception e) {
            logger.log(Level.WARNING, "Failed to parse double for label: " + label, e);
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
