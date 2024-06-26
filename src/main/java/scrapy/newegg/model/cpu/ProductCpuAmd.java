package scrapy.newegg.model.cpu;

import scrapy.newegg.model.AbstractProduct;
import scrapy.newegg.model.ProductCategory;

import javax.persistence.*;
import java.math.BigDecimal;

@Entity
@Table (name = "amd_cpu")
public class ProductCpuAmd extends AbstractProduct implements ProductCpu {

    private String brand;
    private String name;
    private BigDecimal price;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "category_id")
    private ProductCategory category;
    private String processorsType;
    private String series;

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

    private String support64Bit;
    private String memoryTypes;
    private int memoryChannel;
    private String isEccMemorySupported;
    private String integratedGraphics;
    private int graphicsBaseFrequency;

    private int graphicsMaxBaseFrequency;
    private String pciExpressRevision;
    private int thermalDesignPower;
    private String coolingDevice;
    private String operatingSystemSupported;

    // Add other fields as needed

    // Constructor
    public ProductCpuAmd() {}

    public ProductCpuAmd(ProductCategory category) {
        this.category = category;
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
    public String getName() {
        return name;
    }

    @Override
    public void setName(String name) {
        this.name = name;
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

    public String getSupport64Bit() {
        return support64Bit;
    }

    public void setSupport64Bit(String support64Bit) {
        this.support64Bit = support64Bit;
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

    public String getIsEccMemorySupported() {
        return isEccMemorySupported;
    }

    public void setIsEccMemorySupported(String isEccMemorySupported) {
        this.isEccMemorySupported = isEccMemorySupported;
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

    public int getGraphicsMaxBaseFrequency() {
        return graphicsMaxBaseFrequency;
    }

    public void setGraphicsMaxBaseFrequency(int graphicsMaxBaseFrequency) {
        this.graphicsMaxBaseFrequency = graphicsMaxBaseFrequency;
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

    @Override
    public String toString() {
        return "ProductCpuAmd{" +
                "brand='" + brand + '\'' +
                ", name='" + name + '\'' +
                ", price=" + price +
                ", category=" + category +
                ", processorsType='" + processorsType + '\'' +
                ", series='" + series + '\'' +
                ", model='" + model + '\'' +
                ", cpuSocketType='" + cpuSocketType + '\'' +
                ", numberOfCores=" + numberOfCores +
                ", numberOfThreads=" + numberOfThreads +
                ", operatingFrequency=" + operatingFrequency +
                ", maxTurboFrequency=" + maxTurboFrequency +
                ", l1Cache='" + l1Cache + '\'' +
                ", l2Cache='" + l2Cache + '\'' +
                ", l3Cache='" + l3Cache + '\'' +
                ", manufacturingTech='" + manufacturingTech + '\'' +
                ", support64Bit='" + support64Bit + '\'' +
                ", memoryTypes='" + memoryTypes + '\'' +
                ", memoryChannel=" + memoryChannel +
                ", isEccMemorySupported='" + isEccMemorySupported + '\'' +
                ", integratedGraphics='" + integratedGraphics + '\'' +
                ", graphicsBaseFrequency=" + graphicsBaseFrequency +
                ", graphicsMaxBaseFrequency=" + graphicsMaxBaseFrequency +
                ", pciExpressRevision='" + pciExpressRevision + '\'' +
                ", thermalDesignPower=" + thermalDesignPower +
                ", coolingDevice='" + coolingDevice + '\'' +
                ", operatingSystemSupported='" + operatingSystemSupported + '\'' +
                '}';
    }
}
