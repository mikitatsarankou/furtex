INSERT INTO materials (name, description)
VALUES
    ('Leather', 'High-quality leather, perfect for car interiors'),
    ('Eco-Leather', 'Environmentally friendly leather option, great for interior upholstery'),
    ('Suede', 'Luxurious suede material, suitable for high-end cars'),
    ('Fabric', 'Durable and comfortable fabric, ideal for seats and door panels'),
    ('Vinyl', 'Easy to clean and maintain, preferred for luxury cars');

INSERT INTO requests (material_id, comment, first_name, last_name, phone_number)
VALUES
    ((select materials.id from materials where materials.name = 'Leather'), 'Request for Leather', 'John', 'Doe', '+12345678901'),
    ((select materials.id from materials where materials.name = 'Eco-Leather'), 'Request for Eco-Leather', 'Jane', 'Doe', '+12345678902'),
    ((select materials.id from materials where materials.name = 'Suede'), 'Request for Suede', 'Jim', 'Doe', '+12345678903'),
    ((select materials.id from materials where materials.name = 'Fabric'), 'Request for Fabric', 'Janet', 'Doe', '+12345678904'),
    ((select materials.id from materials where materials.name = 'Vinyl'), 'Request for Vinyl', 'James', 'Doe', '+12345678905');