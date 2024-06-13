package scrapy.newegg.scraper.cpu;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import scrapy.newegg.config.ScrapingResult;
import scrapy.newegg.model.cpu.ProductCpuIntel;
import scrapy.newegg.parser.DefaultValueParser;
import scrapy.newegg.repository.category.cpu.ProductCpuIntelRepository;
import scrapy.newegg.scraper.ProductScraper;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

@Component
public class CpuIntelScraper implements ProductScraper<ProductCpuIntel> {

    private static final Logger logger = Logger.getLogger(CpuIntelScraper.class.getName());

    @Autowired
    private DefaultValueParser valueParser;

    @Autowired
    private ProductCpuIntelRepository productCpuIntelRepository;

    private String productUrl;

    @Override
    public ScrapingResult call() {
        try {
            Element specsTabPane = getSpecsTabPane();
            if (specsTabPane != null) {
                Element specsTable = getSpecsTable(specsTabPane);
                if (specsTable != null) {
                    ProductCpuIntel product = new ProductCpuIntel();
                    product.setBrand("Intel"); // Set brand explicitly for Intel CPUs

                    // Log and parse each field
                    logger.info("Parsing Name...");
                    product.setName(valueParser.parseString(specsTable, "Name"));
                    logger.info("Parsed Name: " + product.getName());

                    logger.info("Parsing Price...");
                    product.setPrice(valueParser.parseBigDecimal(specsTable, "Price"));
                    logger.info("Parsed Price: " + product.getPrice());

                    logger.info("Parsing Processors Type...");
                    product.setProcessorsType(valueParser.parseString(specsTable, "Processors Type"));
                    logger.info("Parsed Processors Type: " + product.getProcessorsType());

                    logger.info("Parsing Series...");
                    product.setSeries(valueParser.parseString(specsTable, "Series"));
                    logger.info("Parsed Series: " + product.getSeries());

                    logger.info("Parsing Model...");
                    product.setModel(valueParser.parseString(specsTable, "Model"));
                    logger.info("Parsed Model: " + product.getModel());

                    logger.info("Parsing CPU Socket Type...");
                    product.setCpuSocketType(valueParser.parseString(specsTable, "CPU Socket Type"));
                    logger.info("Parsed CPU Socket Type: " + product.getCpuSocketType());

                    logger.info("Parsing Core Name...");
                    product.setCoreName(valueParser.parseString(specsTable, "Core Name"));
                    logger.info("Parsed Core Name: " + product.getCoreName());

                    logger.info("Parsing Number of Cores...");
                    product.setNumberOfCores(valueParser.parseInt(specsTable, "# of Cores"));
                    logger.info("Parsed Number of Cores: " + product.getNumberOfCores());

                    logger.info("Parsing Number of Threads...");
                    product.setNumberOfThreads(valueParser.parseInt(specsTable, "# of Threads"));
                    logger.info("Parsed Number of Threads: " + product.getNumberOfThreads());

                    logger.info("Parsing Operating Frequency Performance Core Base...");
                    product.setOperatingFrequencyPerformanceCoreBase(valueParser.parseDouble(specsTable, "Operating Frequency"));
                    logger.info("Parsed Operating Frequency Performance Core Base: " + product.getOperatingFrequencyPerformanceCoreBase());

                    logger.info("Parsing Operating Frequency Efficient Core Base...");
                    product.setOperatingFrequencyEfficientCoreBase(valueParser.parseDouble(specsTable, "Efficient-core Base Frequency"));
                    logger.info("Parsed Operating Frequency Efficient Core Base: " + product.getOperatingFrequencyEfficientCoreBase());

                    logger.info("Parsing Max Turbo Frequency Turbo Boost Max Technology...");
                    product.setMaxTurboFrequencyTurboBoostMaxTechnology(valueParser.parseDouble(specsTable, "Max Turbo Frequency"));
                    logger.info("Parsed Max Turbo Frequency Turbo Boost Max Technology: " + product.getMaxTurboFrequencyTurboBoostMaxTechnology());

                    logger.info("Parsing Max Turbo Frequency P-Core...");
                    product.setMaxTurboFrequencyPCore(valueParser.parseDouble(specsTable, "P-core Max Turbo Frequency"));
                    logger.info("Parsed Max Turbo Frequency P-Core: " + product.getMaxTurboFrequencyPCore());

                    logger.info("Parsing Max Turbo Frequency E-Core...");
                    product.setMaxTurboFrequencyECore(valueParser.parseDouble(specsTable, "E-core Max Turbo Frequency"));
                    logger.info("Parsed Max Turbo Frequency E-Core: " + product.getMaxTurboFrequencyECore());

                    logger.info("Parsing L2 Cache...");
                    product.setL2Cache(valueParser.parseString(specsTable, "L2 Cache"));
                    logger.info("Parsed L2 Cache: " + product.getL2Cache());

                    logger.info("Parsing L3 Cache...");
                    product.setL3Cache(valueParser.parseString(specsTable, "L3 Cache"));
                    logger.info("Parsed L3 Cache: " + product.getL3Cache());

                    logger.info("Parsing Manufacturing Tech...");
                    product.setManufacturingTech(valueParser.parseString(specsTable, "Manufacturing Tech"));
                    logger.info("Parsed Manufacturing Tech: " + product.getManufacturingTech());

                    logger.info("Parsing 64-Bit Support...");
                    product.setSupport64Bit(valueParser.parseString(specsTable, "64-Bit Support"));
                    logger.info("Parsed 64-Bit Support: " + product.getSupport64Bit());

                    logger.info("Parsing Hyper-Threading Support...");
                    product.setHyperThreadingSupport(valueParser.parseString(specsTable, "Hyper-Threading Support"));
                    logger.info("Parsed Hyper-Threading Support: " + product.getHyperThreadingSupport());

                    logger.info("Parsing Memory Types...");
                    product.setMemoryTypes(valueParser.parseString(specsTable, "Memory Types"));
                    logger.info("Parsed Memory Types: " + product.getMemoryTypes());

                    logger.info("Parsing Memory Channel...");
                    product.setMemoryChannel(valueParser.parseInt(specsTable, "Memory Channel"));
                    logger.info("Parsed Memory Channel: " + product.getMemoryChannel());

                    logger.info("Parsing Max Memory Size...");
                    product.setMaxMemorySize(valueParser.parseInt(specsTable, "Max Memory Size"));
                    logger.info("Parsed Max Memory Size: " + product.getMaxMemorySize());

                    logger.info("Parsing ECC Memory...");
                    product.setEccMemorySupported(valueParser.parseString(specsTable, "ECC Memory"));
                    logger.info("Parsed ECC Memory: " + product.getEccMemorySupported());

                    logger.info("Parsing Max Memory Bandwidth...");
                    product.setMaxMemoryBandwidth(valueParser.parseDouble(specsTable, "Max Memory Bandwidth"));
                    logger.info("Parsed Max Memory Bandwidth: " + product.getMaxMemoryBandwidth());

                    logger.info("Parsing Virtualization Technology Support...");
                    product.setVirtualizationTechnologySupport(valueParser.parseString(specsTable, "Virtualization Technology Support"));
                    logger.info("Parsed Virtualization Technology Support: " + product.getVirtualizationTechnologySupport());

                    logger.info("Parsing Integrated Graphics...");
                    product.setIntegratedGraphics(valueParser.parseString(specsTable, "Integrated Graphics"));
                    logger.info("Parsed Integrated Graphics: " + product.getIntegratedGraphics());

                    logger.info("Parsing Graphics Base Frequency...");
                    product.setGraphicsBaseFrequency(valueParser.parseInt(specsTable, "Graphics Base Frequency"));
                    logger.info("Parsed Graphics Base Frequency: " + product.getGraphicsBaseFrequency());

                    logger.info("Parsing Graphics Max Dynamic Frequency...");
                    product.setGraphicsMaxDynamicFrequency(valueParser.parseInt(specsTable, "Graphics Max Dynamic Frequency"));
                    logger.info("Parsed Graphics Max Dynamic Frequency: " + product.getGraphicsMaxDynamicFrequency());

                    logger.info("Parsing Scalability...");
                    product.setScalability(valueParser.parseString(specsTable, "Scalability"));
                    logger.info("Parsed Scalability: " + product.getScalability());

                    logger.info("Parsing PCI Express Revision...");
                    product.setPciExpressRevision(valueParser.parseString(specsTable, "PCI Express Revision"));
                    logger.info("Parsed PCI Express Revision: " + product.getPciExpressRevision());

                    logger.info("Parsing PCI Express Configurations...");
                    product.setPciExpressConfigurations(valueParser.parseString(specsTable, "PCI Express Configurations"));
                    logger.info("Parsed PCI Express Configurations: " + product.getPciExpressConfigurations());

                    logger.info("Parsing Max Number of PCI Express Lanes...");
                    product.setMaxNumberOfPciExpressLanes(valueParser.parseString(specsTable, "Max Number of PCI Express Lanes"));
                    logger.info("Parsed Max Number of PCI Express Lanes: " + product.getMaxNumberOfPciExpressLanes());

                    logger.info("Parsing Thermal Design Power...");
                    product.setThermalDesignPower(valueParser.parseInt(specsTable, "Thermal Design Power"));
                    logger.info("Parsed Thermal Design Power: " + product.getThermalDesignPower());

                    logger.info("Parsing Max Turbo Power...");
                    product.setMaxTurboPower(valueParser.parseInt(specsTable, "Max Turbo Power"));
                    logger.info("Parsed Max Turbo Power: " + product.getMaxTurboPower());

                    logger.info("Parsing Cooling Device...");
                    product.setCoolingDevice(valueParser.parseString(specsTable, "Cooling Device"));
                    logger.info("Parsed Cooling Device: " + product.getCoolingDevice());

                    logger.info("Parsing Compatible Desktop Chipsets...");
                    product.setCompatibleDesktopChipsets(valueParser.parseString(specsTable, "Compatible Desktop Chipsets"));
                    logger.info("Parsed Compatible Desktop Chipsets: " + product.getCompatibleDesktopChipsets());

                    logger.info("Parsing Operating System Supported...");
                    product.setOperatingSystemSupported(valueParser.parseString(specsTable, "Operating System Supported"));
                    logger.info("Parsed Operating System Supported: " + product.getOperatingSystemSupported());

                    logger.info("Parsing Advanced Technologies...");
                    product.setAdvancedTechnologies(valueParser.parseString(specsTable, "Advanced Technologies"));
                    logger.info("Parsed Advanced Technologies: " + product.getAdvancedTechnologies());

                    logger.info("Parsing Security & Reliability...");
                    product.setSecurityAndReliability(valueParser.parseString(specsTable, "Security & Reliability"));
                    logger.info("Parsed Security & Reliability: " + product.getSecurityAndReliability());

                    logger.info("Saving product to repository...");
                    saveProduct(product);
                    logger.info("Product saved successfully.");
                    return ScrapingResult.SUCCESS; // Return success indicator
                } else {
                    logger.warning("No specifications table found.");
                    return ScrapingResult.NO_SPECIFICATIONS_TABLE;
                }
            } else {
                logger.warning("No specs tab pane found.");
                return ScrapingResult.NO_SPECS_TAB_PANE;
            }
        } catch (Exception e) {
            logger.log(Level.SEVERE, "Error scraping product details from URL: " + productUrl, e);
            return ScrapingResult.ERROR;
        }
    }

    @Override
    public Element getSpecsTabPane() {
        try {
            // Send HTTP GET request
            Document doc = Jsoup.connect(productUrl).get();

            // Find the product details section
            Element productDetails = doc.getElementById("product-details");
            if (productDetails != null) {
                // Find the tab pane containing the specifications
                Element specsTabPane = productDetails.select("div.tab-pane").get(1); // assuming the second tab-pane is Specs
                if (specsTabPane != null) {
                    return specsTabPane;
                } else {
                    logger.warning("No specs tab pane found within product details section.");
                    return null;
                }
            } else {
                logger.warning("No product details section found.");
                return null;
            }
        } catch (IOException e) {
            logger.log(Level.SEVERE, "Error connecting to product URL: " + productUrl, e);
            return null;
        } catch (IndexOutOfBoundsException e) {
            logger.warning("No specs tab pane found at the expected index.");
            return null;
        }
    }

    @Override
    public Element getSpecsTable(Element specsTabPane) {
        if (specsTabPane != null) {
            // Find the table containing the specifications within the tab pane
            return specsTabPane.select("table.table-horizontal").first();
        } else {
            logger.warning("Specs tab pane is null.");
            return null;
        }
    }

    @Override
    public void saveProduct(ProductCpuIntel product) {
        try {
            logger.info("Saving product to the database: " + product);
            productCpuIntelRepository.save(product);
            logger.info("Product saved successfully: " + product);
        } catch (Exception e) {
            logger.log(Level.SEVERE, "Error saving product to the database: " + product, e);
        }
    }

    @Override
    public void setProductUrl(String productUrl) {
        this.productUrl = productUrl;
    }
}
