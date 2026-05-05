create table products (
    id bigserial primary key,
    product_type varchar(30) not null,
    serial_number varchar(100) not null,
    manufacturer varchar(100) not null,
    price numeric(12, 2) not null,
    quantity integer not null,
    desktop_form_factor varchar(30),
    laptop_size integer,
    monitor_diagonal numeric(5, 2),
    hard_drive_capacity_gb integer,

    constraint uk_products_serial_number unique (serial_number),
    constraint chk_products_type check (
        product_type in ('DESKTOP_COMPUTER', 'LAPTOP', 'MONITOR', 'HARD_DRIVE')
    ),
    constraint chk_products_price_positive check (price > 0),
    constraint chk_products_quantity_not_negative check (quantity >= 0),
    constraint chk_products_desktop_form_factor check (
        desktop_form_factor is null or desktop_form_factor in ('DESKTOP', 'NETTOP', 'ALL_IN_ONE')
    ),
    constraint chk_products_laptop_size check (
        laptop_size is null or laptop_size in (13, 14, 15, 17)
    ),
    constraint chk_products_monitor_diagonal_positive check (
        monitor_diagonal is null or monitor_diagonal > 0
    ),
    constraint chk_products_hard_drive_capacity_positive check (
        hard_drive_capacity_gb is null or hard_drive_capacity_gb > 0
    ),
    constraint chk_products_type_specific_attributes check (
        (
            product_type = 'DESKTOP_COMPUTER'
            and desktop_form_factor is not null
            and laptop_size is null
            and monitor_diagonal is null
            and hard_drive_capacity_gb is null
        )
        or (
            product_type = 'LAPTOP'
            and desktop_form_factor is null
            and laptop_size is not null
            and monitor_diagonal is null
            and hard_drive_capacity_gb is null
        )
        or (
            product_type = 'MONITOR'
            and desktop_form_factor is null
            and laptop_size is null
            and monitor_diagonal is not null
            and hard_drive_capacity_gb is null
        )
        or (
            product_type = 'HARD_DRIVE'
            and desktop_form_factor is null
            and laptop_size is null
            and monitor_diagonal is null
            and hard_drive_capacity_gb is not null
        )
    )
);

create index idx_products_product_type on products (product_type);
