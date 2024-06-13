package scrapy.newegg.scraper.cpu;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import scrapy.newegg.config.ScrapingResult;
import scrapy.newegg.model.cpu.ProductCpuAmd;
import scrapy.newegg.parser.ValueParser;
import scrapy.newegg.parser.ValueParserFunction;
import scrapy.newegg.repository.category.cpu.ProductCpuAmdRepository;
import scrapy.newegg.scraper.ProductScraper;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.concurrent.TimeUnit;
import java.util.function.Consumer;
import java.util.logging.Level;
import java.util.logging.Logger;

@Component
public class CpuAmdScraper implements ProductScraper<ProductCpuAmd> {
    private static final Logger logger = Logger.getLogger(CpuAmdScraper.class.getName());

    @Autowired
    private ValueParser valueParser;

    @Autowired
    private ProductCpuAmdRepository productCpuAmdRepository;

    private String productUrl;

    private Document doc;

    @Override
    public ScrapingResult call() {
        try {
            Element specsTabPane = getSpecsTabPane();
            if (specsTabPane != null) {
                Elements specsTables = specsTabPane.select("div.tab-pane");

                if (!specsTables.isEmpty()) {
                    ProductCpuAmd product = new ProductCpuAmd();
                    product.setBrand("AMD");

                    for (Element specsTable : specsTables) {
                        waitFor(1, TimeUnit.SECONDS);
                        //logger.info("SPECS TABLE content:\n" + specsTable.html());
                        logger.info("Starting product details scraping.");
                        parseAndLog(specsTable, product, "Name", (value, label, parser) -> parser.parseString(value, label), product::setName);
                        BigDecimal price = valueParser.parsePrice(doc);
                        product.setPrice(price);
                        parseAndLog(specsTable, product, "Processors Type", (value, label, parser) -> parser.parseString(value, label), product::setProcessorsType);
                        parseAndLog(specsTable, product, "Series", (value, label, parser) -> parser.parseString(value, label), product::setSeries);
                        parseAndLog(specsTable, product, "Model", (value, label, parser) -> parser.parseString(value, label), product::setModel);
                        parseAndLog(specsTable, product, "CPU Socket Type", (value, label, parser) -> parser.parseString(value, label), product::setCpuSocketType);
                        parseAndLog(specsTable, product, "# of Cores", (value, label, parser) -> parser.parseInt(value, label), product::setNumberOfCores);
                        parseAndLog(specsTable, product, "# of Threads", (value, label, parser) -> parser.parseInt(value, label), product::setNumberOfThreads);
                        parseAndLog(specsTable, product, "Operating Frequency", (value, label, parser) -> parser.parseDouble(value, label), product::setOperatingFrequency);
                        parseAndLog(specsTable, product, "Max Turbo Frequency", (value, label, parser) -> parser.parseDouble(value, label), product::setMaxTurboFrequency);
                        parseAndLog(specsTable, product, "L1 Cache", (value, label, parser) -> parser.parseString(value, label), product::setL1Cache);
                        parseAndLog(specsTable, product, "L2 Cache", (value, label, parser) -> parser.parseString(value, label), product::setL2Cache);
                        parseAndLog(specsTable, product, "L3 Cache", (value, label, parser) -> parser.parseString(value, label), product::setL3Cache);
                        parseAndLog(specsTable, product, "Manufacturing Tech", (value, label, parser) -> parser.parseString(value, label), product::setManufacturingTech);
                        parseAndLog(specsTable, product, "64-Bit Support", (value, label, parser) -> parser.parseString(value, label), product::setSupport64Bit);
                        parseAndLog(specsTable, product, "Memory Types", (value, label, parser) -> parser.parseString(value, label), product::setMemoryTypes);
                        parseAndLog(specsTable, product, "Memory Channel", (value, label, parser) -> parser.parseInt(value, label), product::setMemoryChannel);
                        parseAndLog(specsTable, product, "ECC Memory", (value, label, parser) -> parser.parseString(value, label), product::setIsEccMemorySupported);
                        parseAndLog(specsTable, product, "Integrated Graphics", (value, label, parser) -> parser.parseString(value, label), product::setIntegratedGraphics);
                        parseAndLog(specsTable, product, "Graphics Base Frequency", (value, label, parser) -> parser.parseInt(value, label), product::setGraphicsBaseFrequency);
                        parseAndLog(specsTable, product, "Graphics Max Dynamic Frequency", (value, label, parser) -> parser.parseInt(value, label), product::setGraphicsMaxBaseFrequency);
                        parseAndLog(specsTable, product, "PCI Express Revision", (value, label, parser) -> parser.parseString(value, label), product::setPciExpressRevision);
                        parseAndLog(specsTable, product, "Thermal Design Power", (value, label, parser) -> parser.parseInt(value, label), product::setThermalDesignPower);
                        parseAndLog(specsTable, product, "Cooling Device", (value, label, parser) -> parser.parseString(value, label), product::setCoolingDevice);
                        parseAndLog(specsTable, product, "Operating System Supported", (value, label, parser) -> parser.parseString(value, label), product::setOperatingSystemSupported);
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
    public <T> void parseAndLog(Element specsTable, ProductCpuAmd product, String fieldName, ValueParserFunction<T> parserFunction, Consumer<T> setter) {
        try {
            T parsedValue = valueParser.apply(specsTable, fieldName, parserFunction);
            setter.accept(parsedValue);
            logger.info("Parsed " + fieldName + ": " + parsedValue);
        } catch (Exception e) {
            logger.warning("Failed to parse " + fieldName + ": " + e.getMessage());
        }
    }

    @Override
    public String getValueByLabel(Element table, String label) {
        try {
            Elements rows = table.select("tr:has(th:contains(" + label + "))");

            if (rows.isEmpty()) {
                // Handle case where label is not found in any row
                return "";
            }

            Element row = rows.first();
            Element valueCell = row.selectFirst("td");

            if (valueCell != null) {
                return valueCell.text().trim();
            } else {
                // Handle case where no <td> cell found after <th> containing label
                return "";
            }
        } catch (Exception e) {
            // Handle any unexpected exceptions, e.g., logging or returning default value
            e.printStackTrace(); // Log the exception or handle it according to your needs
            return "";
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

    private void waitFor(long duration, TimeUnit timeUnit) throws InterruptedException {
        Thread.sleep(timeUnit.toMillis(duration));
    }
}
