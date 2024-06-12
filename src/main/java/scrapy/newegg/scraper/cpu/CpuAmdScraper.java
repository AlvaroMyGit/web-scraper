package scrapy.newegg.scraper.cpu;

import org.jsoup.Jsoup;
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
                    product.setBrand("AMD"); // Set brand explicitly for AMD CPUs
                    product.setName(valueParser.parseString(specsTable, "Name"));
                    product.setPrice(valueParser.parseBigDecimal(specsTable, "Price"));
                    product.setProcessorsType(valueParser.parseString(specsTable, "Processors Type"));
                    product.setSeries(valueParser.parseString(specsTable, "Series"));
                    product.setModel(valueParser.parseString(specsTable, "Model"));
                    product.setCpuSocketType(valueParser.parseString(specsTable, "CPU Socket Type"));
                    product.setNumberOfCores(valueParser.parseInt(specsTable, "# of Cores"));
                    product.setNumberOfThreads(valueParser.parseInt(specsTable, "# of Threads"));
                    product.setOperatingFrequency(valueParser.parseDouble(specsTable, "Operating Frequency"));
                    product.setMaxTurboFrequency(valueParser.parseDouble(specsTable, "Max Turbo Frequency"));
                    product.setL1Cache(valueParser.parseString(specsTable, "L1 Cache"));
                    product.setL2Cache(valueParser.parseString(specsTable, "L2 Cache"));
                    product.setL3Cache(valueParser.parseString(specsTable, "L3 Cache"));
                    product.setManufacturingTech(valueParser.parseString(specsTable, "Manufacturing Tech"));
                    product.setSupport64Bit(valueParser.parseString(specsTable, "64-Bit Support"));
                    product.setMemoryTypes(valueParser.parseString(specsTable, "Memory Types"));
                    product.setMemoryChannel(valueParser.parseInt(specsTable, "Memory Channel"));
                    product.setIsEccMemorySupported(valueParser.parseString(specsTable, "ECC Memory"));
                    product.setIntegratedGraphics(valueParser.parseString(specsTable, "Integrated Graphics"));
                    product.setGraphicsBaseFrequency(valueParser.parseInt(specsTable, "Graphics Base Frequency"));
                    product.setGraphicsMaxBaseFrequency(valueParser.parseInt(specsTable, "Graphics Max Dynamic Frequency"));
                    product.setPciExpressRevision(valueParser.parseString(specsTable, "PCI Express Revision"));
                    product.setThermalDesignPower(valueParser.parseInt(specsTable, "Thermal Design Power"));
                    product.setCoolingDevice(valueParser.parseString(specsTable, "Cooling Device"));
                    product.setOperatingSystemSupported(valueParser.parseString(specsTable, "Operating System Supported"));

                    saveProduct(product);
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
    public void saveProduct(ProductCpuAmd product) {
        productCpuAmdRepository.save(product);
    }

    @Override
    public void setProductUrl(String productUrl) {
        this.productUrl = productUrl;
    }
}
