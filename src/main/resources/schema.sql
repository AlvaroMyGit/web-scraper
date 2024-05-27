CREATE TABLE product (
    id SERIAL PRIMARY KEY,
    brand VARCHAR(255),
    processors_type VARCHAR(255),
    series VARCHAR(255),
    name VARCHAR(255),
    model VARCHAR(255),
    cpu_socket_type VARCHAR(255),
    number_of_cores INTEGER,
    number_of_threads INTEGER,
    operating_frequency DOUBLE PRECISION,
    max_turbo_frequency DOUBLE PRECISION,
    l1_cache VARCHAR(255),
    l2_cache VARCHAR(255),
    l3_cache VARCHAR(255),
    manufacturing_tech VARCHAR(255),
    is_64_bit_support BOOLEAN,
    memory_types VARCHAR(255),
    memory_channel INTEGER,
    is_ecc_memory_supported BOOLEAN,
    integrated_graphics VARCHAR(255),
    graphics_base_frequency INTEGER,
    graphics_max_dynamic_frequency INTEGER,
    pci_express_revision VARCHAR(255),
    thermal_design_power INTEGER,
    cooling_device VARCHAR(255),
    operating_system_supported VARCHAR(255)
);

CREATE TABLE category (
    id SERIAL PRIMARY KEY,
    name VARCHAR(255) UNIQUE NOT NULL,
    description TEXT
);

CREATE TABLE compatibility (
    id SERIAL PRIMARY KEY,
    category1_id INT REFERENCES Category(id),
    category2_id INT REFERENCES Category(id),
    compatibility_rule TEXT NOT NULL -- JSON or text describing the compatibility rule
);