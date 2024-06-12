package scrapy.newegg.config;

import scrapy.newegg.model.Product;

public enum ScrapingResult {
    SUCCESS,
    NO_SPECIFICATIONS_TABLE,
    NO_SPECS_TAB_PANE,
    ERROR
}
