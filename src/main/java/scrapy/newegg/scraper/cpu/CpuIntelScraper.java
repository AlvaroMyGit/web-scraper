package scrapy.newegg.scraper.cpu;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import scrapy.newegg.config.ScrapingResult;
import scrapy.newegg.model.cpu.ProductCpuIntel;
import scrapy.newegg.parser.ValueParser;
import scrapy.newegg.repository.category.cpu.ProductCpuIntelRepository;
import scrapy.newegg.scraper.ProductScraper;

import java.io.IOException;
import java.util.function.BiConsumer;
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

    @Override
    public ScrapingResult call() {
        try {
            Element specsTabPane = getSpecsTabPane();
            if (specsTabPane != null) {
                Elements specsTables = specsTabPane.select("table.table-horizontal");

                if (!specsTables.isEmpty()) {
                    ProductCpuIntel product = new ProductCpuIntel();
                    product.setBrand("Intel");

                    parseAndLog(specsTables, product,
                            "Name", (value, label, parser) -> parser.parseString(value, label), ProductCpuIntel::setName);
                    parseAndLog(specsTables, product,
                            "Price", (value, label, parser) -> parser.parseBigDecimal(value, label), ProductCpuIntel::setPrice);
                    parseAndLog(specsTables, product,
                            "Processors Type", (value, label, parser) -> parser.parseString(value, label), ProductCpuIntel::setProcessorsType);
                    parseAndLog(specsTables, product,
                            "Series", (value, label, parser) -> parser.parseString(value, label), ProductCpuIntel::setSeries);
                    parseAndLog(specsTables, product,
                            "Model", (value, label, parser) -> parser.parseString(value, label), ProductCpuIntel::setModel);
                    parseAndLog(specsTables, product,
                            "CPU Socket Type", (value, label, parser) -> parser.parseString(value, label), ProductCpuIntel::setCpuSocketType);
                    parseAndLog(specsTables, product,
                            "# of Cores", (value, label, parser) -> parser.parseInt(value, label), ProductCpuIntel::setNumberOfCores);
                    parseAndLog(specsTables, product,
                            "# of Threads", (value, label, parser) -> parser.parseInt(value, label), ProductCpuIntel::setNumberOfThreads);
                    parseAndLog(specsTables, product,
                            "Operating Frequency", (value, label, parser) -> parser.parseDouble(value, label), ProductCpuIntel::setOperatingFrequencyPerformanceCoreBase);
                    parseAndLog(specsTables, product,
                            "Max Turbo Frequency", (value, label, parser) -> parser.parseDouble(value, label), ProductCpuIntel::setMaxTurboFrequencyPCore);
                    parseAndLog(specsTables, product,
                            "L2 Cache", (value, label, parser) -> parser.parseString(value, label), ProductCpuIntel::setL2Cache);
                    parseAndLog(specsTables, product,
                            "L3 Cache", (value, label, parser) -> parser.parseString(value, label), ProductCpuIntel::setL3Cache);
                    parseAndLog(specsTables, product,
                            "Manufacturing Tech", (value, label, parser) -> parser.parseString(value, label), ProductCpuIntel::setManufacturingTech);
                    parseAndLog(specsTables, product,
                            "64-Bit Support", (value, label, parser) -> parser.parseString(value, label), ProductCpuIntel::setSupport64Bit);
                    parseAndLog(specsTables, product,
                            "Memory Types", (value, label, parser) -> parser.parseString(value, label), ProductCpuIntel::setMemoryTypes);
                    parseAndLog(specsTables, product,
                            "Memory Channel", (value, label, parser) -> parser.parseInt(value, label), ProductCpuIntel::setMemoryChannel);
                    parseAndLog(specsTables, product,
                            "ECC Memory", (value, label, parser) -> parser.parseString(value, label), ProductCpuIntel::setEccMemorySupported);
                    parseAndLog(specsTables, product,
                            "Integrated Graphics", (value, label, parser) -> parser.parseString(value, label), ProductCpuIntel::setIntegratedGraphics);
                    parseAndLog(specsTables, product,
                            "Graphics Base Frequency", (value, label, parser) -> parser.parseInt(value, label), ProductCpuIntel::setGraphicsBaseFrequency);
                    parseAndLog(specsTables, product,
                            "Graphics Max Dynamic Frequency", (value, label, parser) -> parser.parseInt(value, label), ProductCpuIntel::setGraphicsMaxDynamicFrequency);
                    parseAndLog(specsTables, product,
                            "PCI Express Revision", (value, label, parser) -> parser.parseString(value, label), ProductCpuIntel::setPciExpressRevision);
                    parseAndLog(specsTables, product,
                            "Thermal Design Power", (value, label, parser) -> parser.parseInt(value, label), ProductCpuIntel::setThermalDesignPower);
                    parseAndLog(specsTables, product,
                            "Cooling Device", (value, label, parser) -> parser.parseString(value, label), ProductCpuIntel::setCoolingDevice);
                    parseAndLog(specsTables, product,
                            "Operating System Supported", (value, label, parser) -> parser.parseString(value, label), ProductCpuIntel::setOperatingSystemSupported);

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

    private <T> void parseAndLog(Elements specsTables, ProductCpuIntel product, String fieldName, ValueParserFunction valueParserFunction, BiConsumer<ProductCpuIntel, T> setter) {
        try {
            for (Element specsTable : specsTables) {
                Element valueElement = getValueElement(specsTable, fieldName);
                if (valueElement != null) {
                    T parsedValue = valueParserFunction.getParserFunction().apply(valueElement.text(), fieldName, valueParser);
                    setter.accept(product, parsedValue);
                    logger.info("Parsed " + fieldName + ": " + parsedValue);
                    return; // Exit after first valid parse
                }
            }
            logger.warning("Failed to parse " + fieldName + ": Value not found in specifications tables.");
        } catch (Exception e) {
            logger.warning("Failed to parse " + fieldName + ": " + e.getMessage());
        }
    }


    public Element getValueElement(Element specsTable, String fieldName) {
        Elements rows = specsTable.select("tr");
        for (Element row : rows) {
            Element th = row.select("th").first();
            if (th != null && th.text().contains(fieldName)) {
                return row.select("td").first();
            }
        }
        return null;
    }

    @Override
    public Element getSpecsTabPane() throws IOException {
        Document doc = Jsoup.connect(productUrl).get();
        Element productDetails = doc.getElementById("product-details");
        if (productDetails != null) {
            for (Element tabPane : productDetails.select("div.tab-pane")) {
                if (tabPane.text().contains("Specifications")) {
                    return tabPane;
                }
            }
        } else {
            logger.warning("No product details section found.");
        }
        return null;
    }

    @Override
    public void saveProduct(ProductCpuIntel product) {
        try {
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
