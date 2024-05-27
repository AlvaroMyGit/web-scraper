package org.example.model;

import jakarta.persistence.*;

@Entity
@Table(name = "ryzen_cpu")
public class ProductRyzenCPU {

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
    private String memoryTypes;
    private int memoryChannel;
    private String isEccMemorySupported;
    private String integratedGraphics;
    private int graphicsBaseFrequency;

    private String pciExpressRevision;
    private int thermalDesignPower;
    private String coolingDevice;
    private String operatingSystemSupported;

    @ManyToOne
    @JoinColumn(name = "category_id")
    private Category category;

    // Add other fields as needed

    // Constructor
    public ProductRyzenCPU() {
    }

    // Constructor with parameters
    public ProductRyzenCPU(String brand, String processorsType, String series, String name, String model,
                           String cpuSocketType, int numberOfCores, int numberOfThreads,
                           double operatingFrequency, double maxTurboFrequency,
                           String l1Cache, String l2Cache, String l3Cache,
                           String manufacturingTech, String memoryTypes, int memoryChannel, String isEccMemorySupported,
                           String integratedGraphics, int graphicsBaseFrequency, String pciExpressRevision,
                           int thermalDesignPower, String coolingDevice, String operatingSystemSupported, Category category) {
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
        this.memoryTypes = memoryTypes;
        this.memoryChannel = memoryChannel;
        this.isEccMemorySupported = isEccMemorySupported;
        this.integratedGraphics = integratedGraphics;
        this.graphicsBaseFrequency = graphicsBaseFrequency;
        this.pciExpressRevision = pciExpressRevision;
        this.thermalDesignPower = thermalDesignPower;
        this.coolingDevice = coolingDevice;
        this.operatingSystemSupported = operatingSystemSupported;
        this.category = category;
    }

    // Getters and Setters


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public String getProcessorsType() {
        return processorsType;
    }

    public void setProcessorsType(String processorsType) {
        this.processorsType = processorsType;
    }

    public String getSeries() {
        return series;
    }

    public void setSeries(String series) {
        this.series = series;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public String getCpuSocketType() {
        return cpuSocketType;
    }

    public void setCpuSocketType(String cpuSocketType) {
        this.cpuSocketType = cpuSocketType;
    }

    public int getNumberOfCores() {
        return numberOfCores;
    }

    public void setNumberOfCores(int numberOfCores) {
        this.numberOfCores = numberOfCores;
    }

    public int getNumberOfThreads() {
        return numberOfThreads;
    }

    public void setNumberOfThreads(int numberOfThreads) {
        this.numberOfThreads = numberOfThreads;
    }

    public double getOperatingFrequency() {
        return operatingFrequency;
    }

    public void setOperatingFrequency(double operatingFrequency) {
        this.operatingFrequency = operatingFrequency;
    }

    public double getMaxTurboFrequency() {
        return maxTurboFrequency;
    }

    public void setMaxTurboFrequency(double maxTurboFrequency) {
        this.maxTurboFrequency = maxTurboFrequency;
    }

    public String getL1Cache() {
        return l1Cache;
    }

    public void setL1Cache(String l1Cache) {
        this.l1Cache = l1Cache;
    }

    public String getL2Cache() {
        return l2Cache;
    }

    public void setL2Cache(String l2Cache) {
        this.l2Cache = l2Cache;
    }

    public String getL3Cache() {
        return l3Cache;
    }

    public void setL3Cache(String l3Cache) {
        this.l3Cache = l3Cache;
    }

    public String getManufacturingTech() {
        return manufacturingTech;
    }

    public void setManufacturingTech(String manufacturingTech) {
        this.manufacturingTech = manufacturingTech;
    }

    public String getMemoryTypes() {
        return memoryTypes;
    }

    public void setMemoryTypes(String memoryTypes) {
        this.memoryTypes = memoryTypes;
    }

    public int getMemoryChannel() {
        return memoryChannel;
    }

    public void setMemoryChannel(int memoryChannel) {
        this.memoryChannel = memoryChannel;
    }

    public String isEccMemorySupported() {
        return isEccMemorySupported;
    }

    public void setEccMemorySupported(String eccMemorySupported) {
        isEccMemorySupported = eccMemorySupported;
    }

    public String getIntegratedGraphics() {
        return integratedGraphics;
    }

    public void setIntegratedGraphics(String integratedGraphics) {
        this.integratedGraphics = integratedGraphics;
    }

    public int getGraphicsBaseFrequency() {
        return graphicsBaseFrequency;
    }

    public void setGraphicsBaseFrequency(int graphicsBaseFrequency) {
        this.graphicsBaseFrequency = graphicsBaseFrequency;
    }

    public String getPciExpressRevision() {
        return pciExpressRevision;
    }

    public void setPciExpressRevision(String pciExpressRevision) {
        this.pciExpressRevision = pciExpressRevision;
    }

    public int getThermalDesignPower() {
        return thermalDesignPower;
    }

    public void setThermalDesignPower(int thermalDesignPower) {
        this.thermalDesignPower = thermalDesignPower;
    }

    public String getCoolingDevice() {
        return coolingDevice;
    }

    public void setCoolingDevice(String coolingDevice) {
        this.coolingDevice = coolingDevice;
    }

    public String getOperatingSystemSupported() {
        return operatingSystemSupported;
    }

    public void setOperatingSystemSupported(String operatingSystemSupported) {
        this.operatingSystemSupported = operatingSystemSupported;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public void setIsEccMemorySupported(String isEccMemorySupported) {
        this.isEccMemorySupported = isEccMemorySupported;
    }
}
