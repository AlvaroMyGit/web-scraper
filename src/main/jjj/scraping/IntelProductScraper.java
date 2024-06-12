package org.example.scraping;

import org.example.model.Category;
import org.example.model.ProductIntelCPU;
import org.example.repository.CategoryRepository;
import org.example.repository.IntelProductRepository;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.logging.Level;
import java.util.logging.Logger;

@Component
public class IntelProductScraper implements org.example.scraping.ProductScraper<ProductIntelCPU> {

    private static final Logger logger = Logger.getLogger(IntelProductScraper.class.getName());
    private String productUrl;
    private final IntelProductRepository intelProductRepository;
    private final CategoryRepository categoryRepository;

    public IntelProductScraper(IntelProductRepository intelProductRepository, CategoryRepository categoryRepository) {
        this.intelProductRepository = intelProductRepository;
        this.categoryRepository = categoryRepository;
    }

    @Override
    public ProductIntelCPU call() {
        try {
            Document doc = Jsoup.connect(productUrl).get();
            logger.info("Connected to product URL: " + productUrl);

            Element specsTabPane = doc.getElementById("product-details");
            if (specsTabPane != null) {
                Element specsTable = specsTabPane.select("div.tab-pane").get(1);
                if (specsTable != null) {
                    String brand = getValueByLabel(specsTable, "Brand");
                    String processorsType = getValueByLabel(specsTable, "Processors Type");
                    String series = getValueByLabel(specsTable, "Series");
                    String name = getValueByLabel(specsTable, "Name");
                    BigDecimal price = parseBigDecimal(doc, "#price_selector"); // Adjust selector as needed
                    String model = getValueByLabel(specsTable, "Model");
                    String cpuSocketType = getValueByLabel(specsTable, "CPU Socket Type");
                    String coreName = getValueByLabel(specsTable, "Core Name");
                    int numberOfCores = parseInt(specsTable, "# of Cores");
                    int numberOfThreads = parseInt(specsTable, "# of Threads");
                    double operatingFrequency = parseDouble(specsTable, "Operating Frequency");
                    double maxTurboFrequency = parseDouble(specsTable, "Max Turbo Frequency");
                    String l2Cache = getValueByLabel(specsTable, "L2 Cache");
                    String l3Cache = getValueByLabel(specsTable, "L3 Cache");
                    String manufacturingTech = getValueByLabel(specsTable, "Manufacturing Tech");
                    String support64Bit = getValueByLabel(specsTable, "64-Bit Support");
                    String hyperThreadingSupport = getValueByLabel(specsTable, "Hyper-Threading Support");
                    String memoryTypes = getValueByLabel(specsTable, "Memory Types");
                    int memoryChannel = parseInt(specsTable, "Memory Channel");
                    int maxMemorySize = parseInt(specsTable, "Max Memory Size");
                    String eccMemory = getValueByLabel(specsTable, "ECC Memory");
                    String virtualizationTechnologySupport = getValueByLabel(specsTable, "Virtualization Technology Support");
                    String integratedGraphics = getValueByLabel(specsTable, "Integrated Graphics");
                    int graphicsBaseFrequency = parseInt(specsTable, "Graphics Base Frequency");
                    int graphicsMaxDynamicFrequency = parseInt(specsTable, "Graphics Max Dynamic Frequency");
                    String scalability = getValueByLabel(specsTable, "Scalability");
                    String pciExpressRevision = getValueByLabel(specsTable, "PCI Express Revision");
                    String pciExpressConfigurations = getValueByLabel(specsTable, "PCI Express Configurations");
                    String maxNumberOfPciExpressLanes = getValueByLabel(specsTable, "Max Number of PCI Express Lanes");
                    int thermalDesignPower = parseInt(specsTable, "Thermal Design Power");
                    int maxTurboPower = parseInt(specsTable, "Max Turbo Power");
                    String coolingDevice = getValueByLabel(specsTable, "Cooling Device");
                    String compatibleDesktopChipsets = getValueByLabel(specsTable, "Compatible Desktop Chipsets");
                    String operatingSystemSupported = getValueByLabel(specsTable, "Operating System Supported");

                    ProductIntelCPU product = new ProductIntelCPU();
                    Category cpuCategory = categoryRepository.findByName("CPU");
                    product.setBrand(brand);
                    product.setProcessorsType(processorsType);
                    product.setSeries(series);
                    product.setName(name);
                    product.setPrice(price);
                    product.setModel(model);
                    product.setCpuSocketType(cpuSocketType);
                    product.setCoreName(coreName);
                    product.setNumberOfCores(numberOfCores);
                    product.setNumberOfThreads(numberOfThreads);
                    product.setOperatingFrequency(operatingFrequency);
                    product.setMaxTurboFrequency(maxTurboFrequency);
                    product.setL2Cache(l2Cache);
                    product.setL3Cache(l3Cache);
                    product.setManufacturingTech(manufacturingTech);
                    product.setSupport64Bit(support64Bit);
                    product.setHyperThreadingSupport(hyperThreadingSupport);
                    product.setMemoryTypes(memoryTypes);
                    product.setMemoryChannel(memoryChannel);
                    product.setMaxMemorySize(maxMemorySize);
                    product.setEccMemorySupported(eccMemory);
                    product.setVirtualizationTechnologySupport(virtualizationTechnologySupport);
                    product.setIntegratedGraphics(integratedGraphics);
                    product.setGraphicsBaseFrequency(graphicsBaseFrequency);
                    product.setGraphicsMaxDynamicFrequency(graphicsMaxDynamicFrequency);
                    product.setScalability(scalability);
                    product.setPciExpressRevision(pciExpressRevision);
                    product.setPciExpressConfigurations(pciExpressConfigurations);
                    product.setMaxNumberOfPciExpressLanes(maxNumberOfPciExpressLanes);
                    product.setThermalDesignPower(thermalDesignPower);
                    product.setMaxTurboPower(maxTurboPower);
                    product.setCoolingDevice(coolingDevice);
                    product.setCompatibleDesktopChipsets(compatibleDesktopChipsets);
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
        if (productUrl.contains("intel")) {
            return "Intel";
        } else {
            return "Unknown";
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

    public String getProductUrl() {
        return productUrl;
    }

    public void setProductUrl(String productUrl) {
        this.productUrl = productUrl;
    }
}
