package org.example.model;

import jakarta.persistence.*;

@Entity
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String brand;
    private String processorsType;
    private String series;
    private String name;
    private String model;
    private String cpuSocketType;
    private int numberOfCores;
    private int numberOfThreads;
    private double operatingFrequency;
    private double maxTurboFrequency;
    private String l1Cache;
    private String l2Cache;
    private String l3Cache;
    private String manufacturingTech;
    private boolean is64BitSupport;
    private String memoryTypes;
    private int memoryChannel;
    private boolean isEccMemorySupported;
    private String integratedGraphics;
    private int graphicsBaseFrequency;
    private int graphicsMaxDynamicFrequency;
    private String pciExpressRevision;
    private int thermalDesignPower;
    private String coolingDevice;
    private String operatingSystemSupported;

    // Add other fields as needed

    // Constructor
    public Product() {
    }

    // Constructor with parameters
    public Product(String brand, String processorsType, String series, String name, String model,
                   String cpuSocketType, int numberOfCores, int numberOfThreads,
                   double operatingFrequency, double maxTurboFrequency,
                   String l1Cache, String l2Cache, String l3Cache,
                   String manufacturingTech, boolean is64BitSupport,
                   String memoryTypes, int memoryChannel, boolean isEccMemorySupported,
                   String integratedGraphics, int graphicsBaseFrequency,
                   int graphicsMaxDynamicFrequency, String pciExpressRevision,
                   int thermalDesignPower, String coolingDevice, String operatingSystemSupported) {
        this.brand = brand;
        this.processorsType = processorsType;
        this.series = series;
        this.name = name;
        this.model = model;
        this.cpuSocketType = cpuSocketType;
        this.numberOfCores = numberOfCores;
        this.numberOfThreads = numberOfThreads;
        this.operatingFrequency = operatingFrequency;
        this.maxTurboFrequency = maxTurboFrequency;
        this.l1Cache = l1Cache;
        this.l2Cache = l2Cache;
        this.l3Cache = l3Cache;
        this.manufacturingTech = manufacturingTech;
        this.is64BitSupport = is64BitSupport;
        this.memoryTypes = memoryTypes;
        this.memoryChannel = memoryChannel;
        this.isEccMemorySupported = isEccMemorySupported;
        this.integratedGraphics = integratedGraphics;
        this.graphicsBaseFrequency = graphicsBaseFrequency;
        this.graphicsMaxDynamicFrequency = graphicsMaxDynamicFrequency;
        this.pciExpressRevision = pciExpressRevision;
        this.thermalDesignPower = thermalDesignPower;
        this.coolingDevice = coolingDevice;
        this.operatingSystemSupported = operatingSystemSupported;
    }

}
