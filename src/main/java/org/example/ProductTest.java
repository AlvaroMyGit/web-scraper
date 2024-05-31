package org.example;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;

public class ProductTest {
    public static void main(String[] args) {
        String url = "https://www.newegg.com/intel-core-i7-14700k-core-i7-14th-gen/p/N82E16819118466";

        try {
            // Send HTTP GET request
            Document doc = Jsoup.connect(url).get();
            System.out.println(doc);

            // Check if we are getting the right section of the document
            Element productDetails = doc.getElementById("product-details");
            if (productDetails != null) {
                System.out.println("Product Details Section Found");

                // Find the tab pane containing the specifications
                Element specsTabPane = productDetails.select("div.tab-pane").get(1); // assuming the second tab-pane is Specs
                if (specsTabPane != null) {
                    //System.out.println("Specs Tab Pane HTML:");
                    //System.out.println(specsTabPane);

                    // Find the tables containing the specifications within the tab pane
                    Elements specsTables = specsTabPane.select("table.table-horizontal");

                    if (!specsTables.isEmpty()) {
                        for (Element specsTable : specsTables) {
                            // Extract properties
                            String brand = getValueByLabel(specsTable, "Brand");
                            String series = getValueByLabel(specsTable, "Series");
                            String name = getValueByLabel(specsTable, "Name");
                            String model = getValueByLabel(specsTable, "Model");
                            String cpuSocketType = getValueByLabel(specsTable, "CPU Socket Type");
                            String cores = getValueByLabel(specsTable, "# of Cores");
                            String threads = getValueByLabel(specsTable, "# of Threads");
                            String operatingFrequency = getValueByLabel(specsTable, "Operating Frequency");
                            String maxTurboFrequency = getValueByLabel(specsTable, "Max Turbo Frequency");
                            String l1Cache = getValueByLabel(specsTable, "L1 Cache");
                            String l2Cache = getValueByLabel(specsTable, "L2 Cache");
                            String l3Cache = getValueByLabel(specsTable, "L3 Cache");
                            String manufacturingTech = getValueByLabel(specsTable, "Manufacturing Tech");
                            String support64Bit = getValueByLabel(specsTable, "64-Bit Support");
                            String memoryTypes = getValueByLabel(specsTable, "Memory Types");
                            String memoryChannel = getValueByLabel(specsTable, "Memory Channel");
                            String eccMemory = getValueByLabel(specsTable, "ECC Memory");
                            String integratedGraphics = getValueByLabel(specsTable, "Integrated Graphics");
                            String graphicsBaseFrequency = getValueByLabel(specsTable, "Graphics Base Frequency");
                            String graphicsMaxDynamicFrequency = getValueByLabel(specsTable, "Graphics Max Dynamic Frequency");
                            String pciExpressRevision = getValueByLabel(specsTable, "PCI Express Revision");
                            String thermalDesignPower = getValueByLabel(specsTable, "Thermal Design Power");
                            String coolingDevice = getValueByLabel(specsTable, "Cooling Device");
                            String operatingSystemSupported = getValueByLabel(specsTable, "Operating System Supported");

                            // Print properties if not null or empty
                            if (!brand.isEmpty()) System.out.println("Brand: " + brand);
                            if (!series.isEmpty()) System.out.println("Series: " + series);
                            if (!name.isEmpty()) System.out.println("Name: " + name);
                            if (!model.isEmpty()) System.out.println("Model: " + model);
                            if (!cpuSocketType.isEmpty()) System.out.println("CPU Socket Type: " + cpuSocketType);
                            if (!cores.isEmpty()) System.out.println("# of Cores: " + cores);
                            if (!threads.isEmpty()) System.out.println("# of Threads: " + threads);
                            if (!operatingFrequency.isEmpty()) System.out.println("Operating Frequency: " + operatingFrequency);
                            if (!maxTurboFrequency.isEmpty()) System.out.println("Max Turbo Frequency: " + maxTurboFrequency);
                            if (!l1Cache.isEmpty()) System.out.println("L1 Cache: " + l1Cache);
                            if (!l2Cache.isEmpty()) System.out.println("L2 Cache: " + l2Cache);
                            if (!l3Cache.isEmpty()) System.out.println("L3 Cache: " + l3Cache);
                            if (!manufacturingTech.isEmpty()) System.out.println("Manufacturing Tech: " + manufacturingTech);
                            if (!support64Bit.isEmpty()) System.out.println("64-Bit Support: " + support64Bit);
                            if (!memoryTypes.isEmpty()) System.out.println("Memory Types: " + memoryTypes);
                            if (!memoryChannel.isEmpty()) System.out.println("Memory Channel: " + memoryChannel);
                            if (!eccMemory.isEmpty()) System.out.println("ECC Memory: " + eccMemory);
                            if (!integratedGraphics.isEmpty()) System.out.println("Integrated Graphics: " + integratedGraphics);
                            if (!graphicsBaseFrequency.isEmpty()) System.out.println("Graphics Base Frequency: " + graphicsBaseFrequency);
                            if (!graphicsMaxDynamicFrequency.isEmpty()) System.out.println("Graphics Max Dynamic Frequency: " + graphicsMaxDynamicFrequency);
                            if (!pciExpressRevision.isEmpty()) System.out.println("PCI Express Revision: " + pciExpressRevision);
                            if (!thermalDesignPower.isEmpty()) System.out.println("Thermal Design Power: " + thermalDesignPower);
                            if (!coolingDevice.isEmpty()) System.out.println("Cooling Device: " + coolingDevice);
                            if (!operatingSystemSupported.isEmpty()) System.out.println("Operating System Supported: " + operatingSystemSupported);
                        }
                    } else {
                        System.out.println("No specifications tables found.");
                    }
                } else {
                    System.out.println("No specs tab pane found.");
                }
            } else {
                System.out.println("Product details section not found.");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Helper method to get value by label
    private static String getValueByLabel(Element table, String label) {
        Elements rows = table.select("tr");
        for (Element row : rows) {
            Element th = row.select("th").first();
            if (th != null && th.text().contains(label)) {
                return row.select("td").first().text();
            }
        }
        return "";
    }
}
