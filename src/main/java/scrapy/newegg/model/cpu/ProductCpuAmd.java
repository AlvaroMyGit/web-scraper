package scrapy.newegg.model.cpu;

import org.springframework.beans.factory.annotation.Autowired;
import scrapy.newegg.model.AbstractProduct;

import jakarta.persistence.*;
import scrapy.newegg.model.Category;
import scrapy.newegg.repository.CategoryRepository;

import java.math.BigDecimal;


@Entity
@Table (name = "amd_cpu")
public class ProductCpuAmd extends AbstractProduct implements ProductCpu {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String brand;
    private String name;
    private BigDecimal price;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "category_id")
    private Category category;
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

    @Autowired
    private CategoryRepository categoryRepository;
    // Constructor
    public ProductCpuAmd() {}

    public ProductCpuAmd(Category category) {
        this.category = category;
    }

    @Override
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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
    public Category getCategory() {
        return category;
    }

    @Override
    public void setCategory(Category category) {
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
}
