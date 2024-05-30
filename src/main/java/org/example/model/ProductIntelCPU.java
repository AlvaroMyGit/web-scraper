package org.example.model;

import jakarta.persistence.*;

import java.math.BigDecimal;

@Entity
@Table(name = "intel_cpu")
public class ProductIntelCPU implements Product{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String brand;
    private String processorsType;
    private String series;
    private String name;

    private BigDecimal price;
    private String model;
    private String cpuSocketType;
    private String coreName;
    private Integer numberOfCores;
    private Integer numberOfThreads;
    private Double operatingFrequencyPerformanceCoreBase;
    private Double operatingFrequencyEfficientCoreBase;
    private Double maxTurboFrequencyTurboBoostMaxTechnology;
    private Double maxTurboFrequencyPCore;
    private Double maxTurboFrequencyECore;
    private String l2Cache;
    private String l3Cache;
    private String manufacturingTech;
    private String support64Bit;
    private String hyperThreadingSupport;
    private String memoryTypes;
    private Integer memoryChannel;
    private Integer maxMemorySize;
    private String eccMemorySupported;
    private Double maxMemoryBandwidth;
    private String virtualizationTechnologySupport;
    private String integratedGraphics;
    private Integer graphicsBaseFrequency;
    private Integer graphicsMaxDynamicFrequency;
    private String scalability;
    private String pciExpressRevision;
    private String pciExpressConfigurations;
    private String maxNumberOfPciExpressLanes;
    private Integer thermalDesignPower;
    private Integer maxTurboPower;
    private String coolingDevice;
    private String compatibleDesktopChipsets;
    private String operatingSystemSupported;
    private String advancedTechnologies;
    private String securityAndReliability;

    public ProductIntelCPU () {};
    public ProductIntelCPU(String brand, String processorsType, String series, String name, String model, String cpuSocketType, String coreName, Integer numberOfCores, Integer numberOfThreads, Double operatingFrequencyPerformanceCoreBase, Double operatingFrequencyEfficientCoreBase, Double maxTurboFrequencyTurboBoostMaxTechnology, Double maxTurboFrequencyPCore, Double maxTurboFrequencyECore, String l2Cache, String l3Cache, String manufacturingTech, String support64Bit, String hyperThreadingSupport, String memoryTypes, Integer memoryChannel, Integer maxMemorySize, String eccMemorySupported, Double maxMemoryBandwidth, String virtualizationTechnologySupport, String integratedGraphics, Integer graphicsBaseFrequency, Integer graphicsMaxDynamicFrequency, String scalability, String pciExpressRevision, String pciExpressConfigurations, String maxNumberOfPciExpressLanes, Integer thermalDesignPower, Integer maxTurboPower, String coolingDevice, String compatibleDesktopChipsets, String operatingSystemSupported, String advancedTechnologies, String securityAndReliability) {
        this.brand = brand;
        this.processorsType = processorsType;
        this.series = series;
        this.name = name;
        this.model = model;
        this.cpuSocketType = cpuSocketType;
        this.coreName = coreName;
        this.numberOfCores = numberOfCores;
        this.numberOfThreads = numberOfThreads;
        this.operatingFrequencyPerformanceCoreBase = operatingFrequencyPerformanceCoreBase;
        this.operatingFrequencyEfficientCoreBase = operatingFrequencyEfficientCoreBase;
        this.maxTurboFrequencyTurboBoostMaxTechnology = maxTurboFrequencyTurboBoostMaxTechnology;
        this.maxTurboFrequencyPCore = maxTurboFrequencyPCore;
        this.maxTurboFrequencyECore = maxTurboFrequencyECore;
        this.l2Cache = l2Cache;
        this.l3Cache = l3Cache;
        this.manufacturingTech = manufacturingTech;
        this.support64Bit = support64Bit;
        this.hyperThreadingSupport = hyperThreadingSupport;
        this.memoryTypes = memoryTypes;
        this.memoryChannel = memoryChannel;
        this.maxMemorySize = maxMemorySize;
        this.eccMemorySupported = eccMemorySupported;
        this.maxMemoryBandwidth = maxMemoryBandwidth;
        this.virtualizationTechnologySupport = virtualizationTechnologySupport;
        this.integratedGraphics = integratedGraphics;
        this.graphicsBaseFrequency = graphicsBaseFrequency;
        this.graphicsMaxDynamicFrequency = graphicsMaxDynamicFrequency;
        this.scalability = scalability;
        this.pciExpressRevision = pciExpressRevision;
        this.pciExpressConfigurations = pciExpressConfigurations;
        this.maxNumberOfPciExpressLanes = maxNumberOfPciExpressLanes;
        this.thermalDesignPower = thermalDesignPower;
        this.maxTurboPower = maxTurboPower;
        this.coolingDevice = coolingDevice;
        this.compatibleDesktopChipsets = compatibleDesktopChipsets;
        this.operatingSystemSupported = operatingSystemSupported;
        this.advancedTechnologies = advancedTechnologies;
        this.securityAndReliability = securityAndReliability;
    }

    // Getters and setters


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

    public String getCoreName() {
        return coreName;
    }

    public void setCoreName(String coreName) {
        this.coreName = coreName;
    }

    public Integer getNumberOfCores() {
        return numberOfCores;
    }

    public void setNumberOfCores(Integer numberOfCores) {
        this.numberOfCores = numberOfCores;
    }

    public Integer getNumberOfThreads() {
        return numberOfThreads;
    }

    public void setNumberOfThreads(Integer numberOfThreads) {
        this.numberOfThreads = numberOfThreads;
    }

    public Double getOperatingFrequencyPerformanceCoreBase() {
        return operatingFrequencyPerformanceCoreBase;
    }

    public void setOperatingFrequency(Double operatingFrequencyPerformanceCoreBase) {
        this.operatingFrequencyPerformanceCoreBase = operatingFrequencyPerformanceCoreBase;
    }

    public Double getOperatingFrequencyEfficientCoreBase() {
        return operatingFrequencyEfficientCoreBase;
    }

    public void setOperatingFrequencyEfficientCoreBase(Double operatingFrequencyEfficientCoreBase) {
        this.operatingFrequencyEfficientCoreBase = operatingFrequencyEfficientCoreBase;
    }

    public Double getMaxTurboFrequencyTurboBoostMaxTechnology() {
        return maxTurboFrequencyTurboBoostMaxTechnology;
    }

    public void setMaxTurboFrequency(Double maxTurboFrequencyTurboBoostMaxTechnology) {
        this.maxTurboFrequencyTurboBoostMaxTechnology = maxTurboFrequencyTurboBoostMaxTechnology;
    }

    public Double getMaxTurboFrequencyPCore() {
        return maxTurboFrequencyPCore;
    }

    public void setPCoreFrequency(Double maxTurboFrequencyPCore) {
        this.maxTurboFrequencyPCore = maxTurboFrequencyPCore;
    }

    public Double getMaxTurboFrequencyECore() {
        return maxTurboFrequencyECore;
    }

    public void setECoreFrequency(Double maxTurboFrequencyECore) {
        this.maxTurboFrequencyECore = maxTurboFrequencyECore;
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

    public String getSupport64Bit() {
        return support64Bit;
    }

    public void setSupport64Bit(String support64Bit) {
        this.support64Bit = support64Bit;
    }

    public String getHyperThreadingSupport() {
        return hyperThreadingSupport;
    }

    public void setHyperThreadingSupport(String hyperThreadingSupport) {
        this.hyperThreadingSupport = hyperThreadingSupport;
    }

    public String getMemoryTypes() {
        return memoryTypes;
    }

    public void setMemoryTypes(String memoryTypes) {
        this.memoryTypes = memoryTypes;
    }

    public Integer getMemoryChannel() {
        return memoryChannel;
    }

    public void setMemoryChannel(Integer memoryChannel) {
        this.memoryChannel = memoryChannel;
    }

    public Integer getMaxMemorySize() {
        return maxMemorySize;
    }

    public void setMaxMemorySize(Integer maxMemorySize) {
        this.maxMemorySize = maxMemorySize;
    }

    public String getEccMemorySupported() {
        return eccMemorySupported;
    }

    public void setEccMemorySupported(String eccMemorySupported) {
        this.eccMemorySupported = eccMemorySupported;
    }

    public Double getMaxMemoryBandwidth() {
        return maxMemoryBandwidth;
    }

    public void setMaxMemoryBandwidth(Double maxMemoryBandwidth) {
        this.maxMemoryBandwidth = maxMemoryBandwidth;
    }

    public String getVirtualizationTechnologySupport() {
        return virtualizationTechnologySupport;
    }

    public void setVirtualizationTechnologySupport(String virtualizationTechnologySupport) {
        this.virtualizationTechnologySupport = virtualizationTechnologySupport;
    }

    public String getIntegratedGraphics() {
        return integratedGraphics;
    }

    public void setIntegratedGraphics(String integratedGraphics) {
        this.integratedGraphics = integratedGraphics;
    }

    public Integer getGraphicsBaseFrequency() {
        return graphicsBaseFrequency;
    }

    public void setGraphicsBaseFrequency(Integer graphicsBaseFrequency) {
        this.graphicsBaseFrequency = graphicsBaseFrequency;
    }

    public Integer getGraphicsMaxDynamicFrequency() {
        return graphicsMaxDynamicFrequency;
    }

    public void setGraphicsMaxDynamicFrequency(Integer graphicsMaxDynamicFrequency) {
        this.graphicsMaxDynamicFrequency = graphicsMaxDynamicFrequency;
    }

    public String getScalability() {
        return scalability;
    }

    public void setScalability(String scalability) {
        this.scalability = scalability;
    }

    public String getPciExpressRevision() {
        return pciExpressRevision;
    }

    public void setPciExpressRevision(String pciExpressRevision) {
        this.pciExpressRevision = pciExpressRevision;
    }

    public String getPciExpressConfigurations() {
        return pciExpressConfigurations;
    }

    public void setPciExpressConfigurations(String pciExpressConfigurations) {
        this.pciExpressConfigurations = pciExpressConfigurations;
    }

    public String getMaxNumberOfPciExpressLanes() {
        return maxNumberOfPciExpressLanes;
    }

    public void setMaxNumberOfPciExpressLanes(String maxNumberOfPciExpressLanes) {
        this.maxNumberOfPciExpressLanes = maxNumberOfPciExpressLanes;
    }

    public Integer getThermalDesignPower() {
        return thermalDesignPower;
    }

    public void setThermalDesignPower(Integer thermalDesignPower) {
        this.thermalDesignPower = thermalDesignPower;
    }

    public Integer getMaxTurboPower() {
        return maxTurboPower;
    }

    public void setMaxTurboPower(Integer maxTurboPower) {
        this.maxTurboPower = maxTurboPower;
    }

    public String getCoolingDevice() {
        return coolingDevice;
    }

    public void setCoolingDevice(String coolingDevice) {
        this.coolingDevice = coolingDevice;
    }

    public String getCompatibleDesktopChipsets() {
        return compatibleDesktopChipsets;
    }

    public void setCompatibleDesktopChipsets(String compatibleDesktopChipsets) {
        this.compatibleDesktopChipsets = compatibleDesktopChipsets;
    }

    public String getOperatingSystemSupported() {
        return operatingSystemSupported;
    }

    public void setOperatingSystemSupported(String operatingSystemSupported) {
        this.operatingSystemSupported = operatingSystemSupported;
    }

    public String getAdvancedTechnologies() {
        return advancedTechnologies;
    }

    public void setAdvancedTechnologies(String advancedTechnologies) {
        this.advancedTechnologies = advancedTechnologies;
    }

    public String getSecurityAndReliability() {
        return securityAndReliability;
    }

    public void setSecurityAndReliability(String securityAndReliability) {
        this.securityAndReliability = securityAndReliability;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public void setOperatingFrequencyPerformanceCoreBase(Double operatingFrequencyPerformanceCoreBase) {
        this.operatingFrequencyPerformanceCoreBase = operatingFrequencyPerformanceCoreBase;
    }

    public void setMaxTurboFrequencyTurboBoostMaxTechnology(Double maxTurboFrequencyTurboBoostMaxTechnology) {
        this.maxTurboFrequencyTurboBoostMaxTechnology = maxTurboFrequencyTurboBoostMaxTechnology;
    }

    public void setMaxTurboFrequencyPCore(Double maxTurboFrequencyPCore) {
        this.maxTurboFrequencyPCore = maxTurboFrequencyPCore;
    }

    public void setMaxTurboFrequencyECore(Double maxTurboFrequencyECore) {
        this.maxTurboFrequencyECore = maxTurboFrequencyECore;
    }


}
