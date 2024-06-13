package scrapy.newegg.scraper.cpu;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import scrapy.newegg.config.ScrapingResult;
import scrapy.newegg.model.cpu.ProductCpuAmd;
import scrapy.newegg.model.cpu.ProductCpuIntel;
import scrapy.newegg.parser.ValueParser;
import scrapy.newegg.parser.ValueParserFunction;
import scrapy.newegg.repository.category.cpu.ProductCpuIntelRepository;
import scrapy.newegg.scraper.ProductScraper;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.concurrent.TimeUnit;
import java.util.function.BiConsumer;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.logging.Level;
import java.util.logging.Logger;

@Component
public class CpuIntelScraper implements ProductScraper<ProductCpuIntel> {
    private static final Logger logger = Logger.getLogger(CpuIntelScraper.class.getName());

    @Autowired
    private ValueParser valueParser;

    @Autowired
    private ProductCpuIntelRepository productCpuIntelRepository;

    private String productUrl;
    private Document doc;

    @Override
    public ScrapingResult call() {
        try {
            Element specsTabPane = getSpecsTabPane();
            if (specsTabPane != null) {
                Elements specsTables = specsTabPane.select("div.tab-pane");

                if (!specsTables.isEmpty()) {
                    ProductCpuIntel product = new ProductCpuIntel();
                    product.setBrand("Intel");

                    for (Element specsTable : specsTables) {

                        waitFor(1, TimeUnit.SECONDS);
                        logger.info("Starting product details scraping.");
                        parseAndLog(specsTable, product, "Name", (value, label, parser) -> parser.parseString(value, label), product::setName);
                        BigDecimal price = valueParser.parsePrice(doc);
                        product.setPrice(price);
                        parseAndLog(specsTable, product, "Processors Type", (value, label, parser) -> parser.parseString(value, label), product::setProcessorsType);
                        parseAndLog(specsTable, product, "Series", (value, label, parser) -> parser.parseString(value, label), product::setSeries);
                        parseAndLog(specsTable, product, "Model", (value, label, parser) -> parser.parseString(value, label), product::setModel);
                        parseAndLog(specsTable, product, "CPU Socket Type", (value, label, parser) -> parser.parseString(value, label), product::setCpuSocketType);
                        parseAndLog(specsTable, product, "Core Name", (value, label, parser) -> parser.parseString(value, label), product::setCoreName);
                        parseAndLog(specsTable, product, "# of Cores", (value, label, parser) -> parser.parseInt(value, label), product::setNumberOfCores);
                        parseAndLog(specsTable, product, "# of Threads", (value, label, parser) -> parser.parseInt(value, label), product::setNumberOfThreads);
                        parseAndLog(specsTable, product, "Operating Frequency", (value, label, parser) -> parser.parseDouble(value, label), product::setOperatingFrequencyPerformanceCoreBase);
                        parseAndLog(specsTable, product, "Efficient-core Base Frequency", (value, label, parser) -> parser.parseDouble(value, label), product::setOperatingFrequencyEfficientCoreBase);
                        parseAndLog(specsTable, product, "Max Turbo Frequency", (value, label, parser) -> parser.parseDouble(value, label), product::setMaxTurboFrequencyTurboBoostMaxTechnology);
                        parseAndLog(specsTable, product, "P-core Max Turbo Frequency", (value, label, parser) -> parser.parseDouble(value, label), product::setMaxTurboFrequencyPCore);
                        parseAndLog(specsTable, product, "E-core Max Turbo Frequency", (value, label, parser) -> parser.parseDouble(value, label), product::setMaxTurboFrequencyECore);
                        parseAndLog(specsTable, product, "L2 Cache", (value, label, parser) -> parser.parseString(value, label), product::setL2Cache);
                        parseAndLog(specsTable, product, "L3 Cache", (value, label, parser) -> parser.parseString(value, label), product::setL3Cache);
                        parseAndLog(specsTable, product, "Manufacturing Tech", (value, label, parser) -> parser.parseString(value, label), product::setManufacturingTech);
                        parseAndLog(specsTable, product, "64-Bit Support", (value, label, parser) -> parser.parseString(value, label), product::setSupport64Bit);
                        parseAndLog(specsTable, product, "Hyper-Threading Support", (value, label, parser) -> parser.parseString(value, label), product::setHyperThreadingSupport);
                        parseAndLog(specsTable, product, "Memory Types", (value, label, parser) -> parser.parseString(value, label), product::setMemoryTypes);
                        parseAndLog(specsTable, product, "Memory Channel", (value, label, parser) -> parser.parseInt(value, label), product::setMemoryChannel);
                        parseAndLog(specsTable, product, "Max Memory Size", (value, label, parser) -> parser.parseInt(value, label), product::setMaxMemorySize);
                        parseAndLog(specsTable, product, "ECC Memory", (value, label, parser) -> parser.parseString(value, label), product::setEccMemorySupported);
                        parseAndLog(specsTable, product, "Max Memory Bandwidth", (value, label, parser) -> parser.parseDouble(value, label), product::setMaxMemoryBandwidth);
                        parseAndLog(specsTable, product, "Virtualization Technology Support", (value, label, parser) -> parser.parseString(value, label), product::setVirtualizationTechnologySupport);
                        parseAndLog(specsTable, product, "Integrated Graphics", (value, label, parser) -> parser.parseString(value, label), product::setIntegratedGraphics);
                        parseAndLog(specsTable, product, "Graphics Base Frequency", (value, label, parser) -> parser.parseInt(value, label), product::setGraphicsBaseFrequency);
                        parseAndLog(specsTable, product, "Graphics Max Dynamic Frequency", (value, label, parser) -> parser.parseInt(value, label), product::setGraphicsMaxDynamicFrequency);
                        parseAndLog(specsTable, product, "Scalability", (value, label, parser) -> parser.parseString(value, label), product::setScalability);
                        parseAndLog(specsTable, product, "PCI Express Revision", (value, label, parser) -> parser.parseString(value, label), product::setPciExpressRevision);
                        parseAndLog(specsTable, product, "PCI Express Configurations", (value, label, parser) -> parser.parseString(value, label), product::setPciExpressConfigurations);
                        parseAndLog(specsTable, product, "Max Number of PCI Express Lanes", (value, label, parser) -> parser.parseString(value, label), product::setMaxNumberOfPciExpressLanes);
                        parseAndLog(specsTable, product, "Thermal Design Power", (value, label, parser) -> parser.parseInt(value, label), product::setThermalDesignPower);
                        parseAndLog(specsTable, product, "Max Turbo Power", (value, label, parser) -> parser.parseInt(value, label), product::setMaxTurboPower);
                        parseAndLog(specsTable, product, "Cooling Device", (value, label, parser) -> parser.parseString(value, label), product::setCoolingDevice);
                        parseAndLog(specsTable, product, "Compatible Desktop Chipsets", (value, label, parser) -> parser.parseString(value, label), product::setCompatibleDesktopChipsets);
                        parseAndLog(specsTable, product, "Operating System Supported", (value, label, parser) -> parser.parseString(value, label), product::setOperatingSystemSupported);
                        parseAndLog(specsTable, product, "Advanced Technologies", (value, label, parser) -> parser.parseString(value, label), product::setAdvancedTechnologies);
                        parseAndLog(specsTable, product, "Security & Reliability", (value, label, parser) -> parser.parseString(value, label), product::setSecurityAndReliability);
                    }

                    saveProduct(product);
                    return ScrapingResult.SUCCESS;
                } else {
                    logger.warning("No specifications tables found.");
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
    public <T> void parseAndLog(Element specsTable, ProductCpuIntel product, String fieldName, ValueParserFunction<T> parserFunction, Consumer<T> setter) {
        try {
            String value = getValueByLabel(specsTable, fieldName);
            logger.info("Extracted value for " + fieldName + ": " + value);
            if (value != null && !value.isEmpty()) {
                T parsedValue = valueParser.apply(specsTable, fieldName, parserFunction);
                setter.accept(parsedValue);
            } else {
                logger.warning("No value found for " + fieldName);
            }
        } catch (Exception e) {
            logger.log(Level.SEVERE, "Error parsing value for " + fieldName, e);
        }
    }

    @Override
    public String getValueByLabel(Element specsTable, String label) {
        Elements rows = specsTable.select("tr:has(th:contains(" + label + "))");
        if (rows.isEmpty()) {
            logger.warning("No row found for label: " + label);
            return null;
        } else {
            Elements tds = rows.first().select("td");
            if (tds.isEmpty()) {
                logger.warning("No value found for label: " + label);
                return null;
            } else {
                String value = tds.first().text();
                logger.info("Value found for label " + label + ": " + value);
                return value;
            }
        }
    }

    @Override
    public Element getSpecsTabPane() {
        try {
            doc = Jsoup.connect(productUrl).get();
            Element productDetails = doc.getElementById("product-details");
            if (productDetails != null) {
                Element specsTabPane = productDetails.select("div.tab-pane").get(1);

                // Print the HTML content for debugging
                //logger.info("HTML content:\n" + specsTabPane.html());

                return specsTabPane;
            } else {
                logger.warning("No product details section found.");
            }
        } catch (IOException e) {
            logger.log(Level.SEVERE, "Error connecting to product URL: " + productUrl, e);
        }
        return null;
    }

    @Override
    public void setProductUrl(String productUrl) {
        this.productUrl = productUrl;
        try {
            doc = Jsoup.connect(productUrl).get();
        } catch (IOException e) {
            logger.log(Level.SEVERE, "Error connecting to URL: " + productUrl, e);
        }
    }

    @Override
    public void saveProduct(ProductCpuIntel product) {
        productCpuIntelRepository.save(product);
        logger.info("Product saved: " + product.toString());
    }

    private void waitFor(int time, TimeUnit timeUnit) {
        try {
            timeUnit.sleep(time);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
}

