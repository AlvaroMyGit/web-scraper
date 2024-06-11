CREATE TABLE category (
    id SERIAL PRIMARY KEY,
    name VARCHAR(255) UNIQUE NOT NULL,
    description TEXT
);

CREATE TABLE amd_cpu (
    id SERIAL PRIMARY KEY,
    brand VARCHAR(255) NOT NULL,
    processors_type VARCHAR(255),
    series VARCHAR(255),
    name VARCHAR(255) NOT NULL,
    price DECIMAL(10, 2),
    model VARCHAR(255) NOT NULL,
    cpu_socket_type VARCHAR(255),
    number_of_cores INTEGER,
    number_of_threads INTEGER,
    operating_frequency DOUBLE PRECISION,
    max_turbo_frequency DOUBLE PRECISION,
    l1_cache VARCHAR(255),
    l2_cache VARCHAR(255),
    l3_cache VARCHAR(255),
    manufacturing_tech VARCHAR(255),
    support_64_bit VARCHAR(255),
    memory_types VARCHAR(255),
    memory_channel INTEGER,
    ecc_memory_supported VARCHAR(255),
    integrated_graphics VARCHAR(255),
    graphics_base_frequency INTEGER,
    graphics_max_base_frequency INTEGER,
    pci_express_revision VARCHAR(255),
    thermal_design_power INTEGER,
    cooling_device VARCHAR(255),
    operating_system_supported VARCHAR(255),
    category_id INTEGER NOT NULL,  -- Foreign key referencing category(id)
    CONSTRAINT fk_category
        FOREIGN KEY (category_id)
        REFERENCES category(id),
    CONSTRAINT ck_positive_values CHECK (
        number_of_cores > 0 AND
        number_of_threads > 0 AND
        operating_frequency > 0 AND
        max_turbo_frequency > 0 AND
        memory_channel > 0 AND
        graphics_base_frequency > 0 AND
        graphics_max_base_frequency > 0 AND
        thermal_design_power > 0
    )
);

CREATE TABLE intel_cpu (
    id SERIAL PRIMARY KEY,
    brand VARCHAR(255) NOT NULL,
    processors_type VARCHAR(255),
    series VARCHAR(255),
    name VARCHAR(255) NOT NULL,
    price DECIMAL(10, 2),
    model VARCHAR(255) NOT NULL,
    cpu_socket_type VARCHAR(255),
    core_name VARCHAR(255),
    number_of_cores INTEGER,
    number_of_threads INTEGER,
    operating_frequency_performance_core_base DOUBLE PRECISION,
    operating_frequency_efficient_core_base DOUBLE PRECISION,
    max_turbo_frequency_turbo_boost_max_technology DOUBLE PRECISION,
    max_turbo_frequency_p_core DOUBLE PRECISION,
    max_turbo_frequency_e_core DOUBLE PRECISION,
    l2_cache VARCHAR(255),
    l3_cache VARCHAR(255),
    manufacturing_tech VARCHAR(255),
    support_64_bit VARCHAR(255),
    hyper_threading_support VARCHAR(255),
    memory_types VARCHAR(255),
    memory_channel INTEGER,
    max_memory_size INTEGER,
    ecc_memory_supported VARCHAR(255),
    max_memory_bandwidth DOUBLE PRECISION,
    virtualization_technology_support VARCHAR(255),
    integrated_graphics VARCHAR(255),
    graphics_base_frequency INTEGER,
    graphics_max_dynamic_frequency INTEGER,
    scalability VARCHAR(255),
    pci_express_revision VARCHAR(255),
    pci_express_configurations VARCHAR(255),
    max_number_of_pci_express_lanes INTEGER,
    thermal_design_power INTEGER,
    max_turbo_power INTEGER,
    cooling_device VARCHAR(255),
    compatible_desktop_chipsets VARCHAR(255),
    operating_system_supported VARCHAR(255),
    advanced_technologies VARCHAR(255),
    security_and_reliability VARCHAR(255),
    category_id INTEGER NOT NULL,  -- Foreign key referencing category(id)
    CONSTRAINT fk_category
        FOREIGN KEY (category_id)
        REFERENCES category(id),
    CONSTRAINT ck_positive_values CHECK (
        number_of_cores > 0 AND
        number_of_threads > 0 AND
        operating_frequency_performance_core_base > 0 AND
        operating_frequency_efficient_core_base > 0 AND
        max_turbo_frequency_turbo_boost_max_technology > 0 AND
        max_turbo_frequency_p_core > 0 AND
        max_turbo_frequency_e_core > 0 AND
        memory_channel > 0 AND
        max_memory_size > 0 AND
        graphics_base_frequency > 0 AND
        graphics_max_dynamic_frequency > 0 AND
        max_number_of_pci_express_lanes > 0 AND
        thermal_design_power > 0 AND
        max_turbo_power > 0
    )
);

CREATE TABLE product_photo (
    id SERIAL PRIMARY KEY,
    url VARCHAR(255) NOT NULL,  -- or you can store the image binary data if needed
    product_id INTEGER NOT NULL,
    CONSTRAINT fk_product
        FOREIGN KEY (product_id)
        REFERENCES product_cpu(id) ON DELETE CASCADE
);

CREATE TABLE compatibility (
    id SERIAL PRIMARY KEY,
    category1_id INTEGER REFERENCES category(id),
    category2_id INTEGER REFERENCES category(id),
    compatibility_rule TEXT NOT NULL -- JSON or text describing the compatibility rule
);

INSERT INTO category (name, description) VALUES ('CPU', 'Central Processing Units');
