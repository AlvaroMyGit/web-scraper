package scrapy.newegg.scraper.cpu;

import org.jsoup.Jsoup;
import org.jsoup.helper.Consumer;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import scrapy.newegg.config.ScrapingResult;
import scrapy.newegg.model.cpu.ProductCpuAmd;
import scrapy.newegg.parser.DefaultValueParser;
import scrapy.newegg.repository.category.cpu.ProductCpuAmdRepository;
import scrapy.newegg.scraper.ProductScraper;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

@Component
public class CpuAmdScraper implements ProductScraper<ProductCpuAmd> {
    private static final Logger logger = Logger.getLogger(CpuAmdScraper.class.getName());

    @Autowired
    private DefaultValueParser valueParser;

    @Autowired
    private ProductCpuAmdRepository productCpuAmdRepository;

    private String productUrl;

    @Override
    public ScrapingResult call() {
        try {
            Element specsTabPane = getSpecsTabPane();
            if (specsTabPane != null) {
                Element specsTable = getSpecsTable(specsTabPane);
                if (specsTable != null) {
                    ProductCpuAmd product = new ProductCpuAmd();
                    product.setBrand("AMD");

                    parseAndLog(specsTable, product, "Name", (value) -> product.setName(value));
                    parseAndLog(specsTable, product, "Price", (value) -> product.setPrice(valueParser.parseBigDecimal(specsTable, "Price")));
                    parseAndLog(specsTable, product, "Processors Type", (value) -> product.setProcessorsType(value));
                    parseAndLog(specsTable, product, "Series", (value) -> product.setSeries(value));
                    parseAndLog(specsTable, product, "Model", (value) -> product.setModel(value));
                    parseAndLog(specsTable, product, "CPU Socket Type", (value) -> product.setCpuSocketType(value));
                    parseAndLog(specsTable, product, "# of Cores", (value) -> product.setNumberOfCores(valueParser.parseInt(specsTable, "# of Cores")));
                    parseAndLog(specsTable, product, "# of Threads", (value) -> product.setNumberOfThreads(valueParser.parseInt(specsTable, "# of Threads")));
                    parseAndLog(specsTable, product, "Operating Frequency", (value) -> product.setOperatingFrequency(valueParser.parseDouble(specsTable, "Operating Frequency")));
                    parseAndLog(specsTable, product, "Max Turbo Frequency", (value) -> product.setMaxTurboFrequency(valueParser.parseDouble(specsTable, "Max Turbo Frequency")));
                    parseAndLog(specsTable, product, "L1 Cache", (value) -> product.setL1Cache(value));
                    parseAndLog(specsTable, product, "L2 Cache", (value) -> product.setL2Cache(value));
                    parseAndLog(specsTable, product, "L3 Cache", (value) -> product.setL3Cache(value));
                    parseAndLog(specsTable, product, "Manufacturing Tech", (value) -> product.setManufacturingTech(value));
                    parseAndLog(specsTable, product, "64-Bit Support", (value) -> product.setSupport64Bit(value));
                    parseAndLog(specsTable, product, "Memory Types", (value) -> product.setMemoryTypes(value));
                    parseAndLog(specsTable, product, "Memory Channel", (value) -> product.setMemoryChannel(valueParser.parseInt(specsTable, "Memory Channel")));
                    parseAndLog(specsTable, product, "ECC Memory", (value) -> product.setIsEccMemorySupported(value));
                    parseAndLog(specsTable, product, "Integrated Graphics", (value) -> product.setIntegratedGraphics(value));
                    parseAndLog(specsTable, product, "Graphics Base Frequency", (value) -> product.setGraphicsBaseFrequency(valueParser.parseInt(specsTable, "Graphics Base Frequency")));
                    parseAndLog(specsTable, product, "Graphics Max Dynamic Frequency", (value) -> product.setGraphicsMaxBaseFrequency(valueParser.parseInt(specsTable, "Graphics Max Dynamic Frequency")));
                    parseAndLog(specsTable, product, "PCI Express Revision", (value) -> product.setPciExpressRevision(value));
                    parseAndLog(specsTable, product, "Thermal Design Power", (value) -> product.setThermalDesignPower(valueParser.parseInt(specsTable, "Thermal Design Power")));
                    parseAndLog(specsTable, product, "Cooling Device", (value) -> product.setCoolingDevice(value));
                    parseAndLog(specsTable, product, "Operating System Supported", (value) -> product.setOperatingSystemSupported(value));

                    saveProduct(product);
                    return ScrapingResult.SUCCESS;
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

    private void parseAndLog(Element specsTable, ProductCpuAmd product, String fieldName, Consumer<String> setter) {
        try {
            String value = valueParser.parseString(specsTable, fieldName);
            setter.accept(value);
            logger.info("Parsed " + fieldName + ": " + value);
        } catch (Exception e) {
            logger.warning("Failed to parse " + fieldName + ": " + e.getMessage());
        }
    }

    @Override
    public Element getSpecsTabPane() {
        try {
            Document doc = Jsoup.connect(productUrl).get();
            Element productDetails = doc.getElementById("product-details");
            if (productDetails != null) {
                // Adjusted logic to find the correct tab-pane
                for (Element tabPane : productDetails.select("div.tab-pane")) {
                    if (tabPane.text().contains("Specifications")) {
                        return tabPane;
                    }
                }
            } else {
                logger.warning("No product details section found.");
            }
        } catch (IOException e) {
            logger.log(Level.SEVERE, "Error connecting to product URL: " + productUrl, e);
        }
        return null;
    }

    @Override
    public Element getSpecsTable(Element specsTabPane) {
        if (specsTabPane != null) {
            return specsTabPane.select("table.table-horizontal").first();
        } else {
            logger.warning("Specs tab pane is null.");
            return null;
        }
    }

    @Override
    public void saveProduct(ProductCpuAmd product) {
        try {
            productCpuAmdRepository.save(product);
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
