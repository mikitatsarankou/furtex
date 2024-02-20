DROP TABLE IF EXISTS "requests";
DROP TABLE IF EXISTS "materials";


CREATE TABLE materials
(
    id SERIAL PRIMARY KEY NOT NULL UNIQUE,
    name VARCHAR(50) NOT NULL,
    description varchar(100)
);

CREATE TABLE requests
(
    id SERIAL PRIMARY KEY NOT NULL UNIQUE,
    comment VARCHAR(100),
    first_name VARCHAR(100),
    last_name VARCHAR(100),
    phone_number VARCHAR(13),
    material_id INT NOT NULL,
    FOREIGN KEY (material_id) REFERENCES materials(id)
)

