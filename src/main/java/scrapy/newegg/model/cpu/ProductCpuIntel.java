package scrapy.newegg.model.cpu;

import org.springframework.beans.factory.annotation.Autowired;
import scrapy.newegg.model.AbstractProduct;
import scrapy.newegg.model.ProductCategory;
import scrapy.newegg.repository.CategoryRepository;
import javax.persistence.*;
import java.math.BigDecimal;

@Entity
@Table(name = "intel_cpu")
public class ProductCpuIntel extends AbstractProduct implements ProductCpu{
    private String name;
    private String brand;
    private BigDecimal price;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "category_id")
    private ProductCategory category;
    private String processorsType;
    private String series;
    private String model;
    private String cpuSocketType;
    private String coreName;
    private int numberOfCores;
    private int numberOfThreads;
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
    private int memoryChannel;
    private int maxMemorySize;
    private String eccMemorySupported;
    private Double maxMemoryBandwidth;
    private String virtualizationTechnologySupport;
    private String integratedGraphics;
    private int graphicsBaseFrequency;
    private int graphicsMaxDynamicFrequency;
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

    // Add other fields as needed
    public ProductCpuIntel () {}

    public ProductCpuIntel(ProductCategory category) {
        this.category = category;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String getBrand() {
        return brand;
    }

    @Override
    public void setBrand(String brand) {
        this.brand = brand;
    }

    @Override
    public BigDecimal getPrice() {
        return price;
    }

    @Override
    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    @Override
    public ProductCategory getCategory() {
        return category;
    }

    @Override
    public void setCategory(ProductCategory category) {
        this.category = category;
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

    @Override
    public int getNumberOfCores() {
        return numberOfCores;
    }
    @Override
    public void setNumberOfCores(int numberOfCores) {
        this.numberOfCores = numberOfCores;
    }
    @Override
    public int getNumberOfThreads() {
        return numberOfThreads;
    }
    @Override
    public void setNumberOfThreads(int numberOfThreads) {
        this.numberOfThreads = numberOfThreads;
    }

    public Double getOperatingFrequencyPerformanceCoreBase() {
        return operatingFrequencyPerformanceCoreBase;
    }

    public void setOperatingFrequencyPerformanceCoreBase(Double operatingFrequencyPerformanceCoreBase) {
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

    public void setMaxTurboFrequencyTurboBoostMaxTechnology(Double maxTurboFrequencyTurboBoostMaxTechnology) {
        this.maxTurboFrequencyTurboBoostMaxTechnology = maxTurboFrequencyTurboBoostMaxTechnology;
    }

    public Double getMaxTurboFrequencyPCore() {
        return maxTurboFrequencyPCore;
    }

    public void setMaxTurboFrequencyPCore(Double maxTurboFrequencyPCore) {
        this.maxTurboFrequencyPCore = maxTurboFrequencyPCore;
    }

    public Double getMaxTurboFrequencyECore() {
        return maxTurboFrequencyECore;
    }

    public void setMaxTurboFrequencyECore(Double maxTurboFrequencyECore) {
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

    @Override
    public String toString() {
        return "ProductCpuIntel{" +
                "name='" + name + '\'' +
                ", brand='" + brand + '\'' +
                ", price=" + price +
                ", category=" + category +
                ", processorsType='" + processorsType + '\'' +
                ", series='" + series + '\'' +
                ", model='" + model + '\'' +
                ", cpuSocketType='" + cpuSocketType + '\'' +
                ", coreName='" + coreName + '\'' +
                ", numberOfCores=" + numberOfCores +
                ", numberOfThreads=" + numberOfThreads +
                ", operatingFrequencyPerformanceCoreBase=" + operatingFrequencyPerformanceCoreBase +
                ", operatingFrequencyEfficientCoreBase=" + operatingFrequencyEfficientCoreBase +
                ", maxTurboFrequencyTurboBoostMaxTechnology=" + maxTurboFrequencyTurboBoostMaxTechnology +
                ", maxTurboFrequencyPCore=" + maxTurboFrequencyPCore +
                ", maxTurboFrequencyECore=" + maxTurboFrequencyECore +
                ", l2Cache='" + l2Cache + '\'' +
                ", l3Cache='" + l3Cache + '\'' +
                ", manufacturingTech='" + manufacturingTech + '\'' +
                ", support64Bit='" + support64Bit + '\'' +
                ", hyperThreadingSupport='" + hyperThreadingSupport + '\'' +
                ", memoryTypes='" + memoryTypes + '\'' +
                ", memoryChannel=" + memoryChannel +
                ", maxMemorySize=" + maxMemorySize +
                ", eccMemorySupported='" + eccMemorySupported + '\'' +
                ", maxMemoryBandwidth=" + maxMemoryBandwidth +
                ", virtualizationTechnologySupport='" + virtualizationTechnologySupport + '\'' +
                ", integratedGraphics='" + integratedGraphics + '\'' +
                ", graphicsBaseFrequency=" + graphicsBaseFrequency +
                ", graphicsMaxDynamicFrequency=" + graphicsMaxDynamicFrequency +
                ", scalability='" + scalability + '\'' +
                ", pciExpressRevision='" + pciExpressRevision + '\'' +
                ", pciExpressConfigurations='" + pciExpressConfigurations + '\'' +
                ", maxNumberOfPciExpressLanes='" + maxNumberOfPciExpressLanes + '\'' +
                ", thermalDesignPower=" + thermalDesignPower +
                ", maxTurboPower=" + maxTurboPower +
                ", coolingDevice='" + coolingDevice + '\'' +
                ", compatibleDesktopChipsets='" + compatibleDesktopChipsets + '\'' +
                ", operatingSystemSupported='" + operatingSystemSupported + '\'' +
                ", advancedTechnologies='" + advancedTechnologies + '\'' +
                ", securityAndReliability='" + securityAndReliability + '\'' +
                '}';
    }
}
