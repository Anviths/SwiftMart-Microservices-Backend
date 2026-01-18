-- ============================
-- CATEGORY
-- ============================
INSERT INTO category (id, name)
VALUES (1, 'electronics')
ON CONFLICT (name) DO NOTHING;

-- ============================
-- PRODUCTS (50)
-- ============================
INSERT INTO product (id, product_name, description, price, stock, category_id)
VALUES
(1, 'Smart TV 32 inch', 'HD ready smart TV', 12000, 15, 1),
(2, 'Smart TV 43 inch', 'Full HD smart TV', 22000, 10, 1),
(3, 'Smart TV 55 inch', '4K UHD smart TV', 42000, 8, 1),
(4, 'Bluetooth Speaker', 'Portable speaker with bass', 2500, 30, 1),
(5, 'Wireless Headphones', 'Noise cancelling headphones', 4500, 20, 1),
(6, 'Android Phone 5G', '5G smartphone with AMOLED', 18000, 25, 1),
(7, 'Android Phone Pro', '108MP camera smartphone', 35000, 12, 1),
(8, 'Budget Smartphone', 'Affordable smartphone', 9000, 40, 1),
(9, 'Laptop i5', 'Intel i5 laptop with SSD', 52000, 10, 1),
(10, 'Laptop i7', 'Intel i7 performance laptop', 75000, 6, 1),

(11, 'Wireless Mouse', 'Ergonomic wireless mouse', 800, 100, 1),
(12, 'Mechanical Keyboard', 'RGB gaming keyboard', 3500, 35, 1),
(13, 'USB-C Charger', 'Fast charging USB-C adapter', 1200, 80, 1),
(14, 'Power Bank 20000mAh', 'High capacity power bank', 2200, 60, 1),
(15, 'Smart Watch', 'Fitness tracking smartwatch', 5500, 18, 1),

(16, 'Tablet 10 inch', 'Android tablet with large display', 16000, 14, 1),
(17, 'Gaming Console', 'Next-gen gaming console', 48000, 5, 1),
(18, 'Webcam HD', '1080p HD webcam', 3000, 22, 1),
(19, 'External HDD 1TB', 'Portable hard drive', 4500, 26, 1),
(20, 'External SSD 512GB', 'High-speed SSD', 7500, 19, 1),

(21, 'Router Dual Band', 'High speed WiFi router', 2800, 17, 1),
(22, 'Bluetooth Earbuds', 'True wireless earbuds', 3200, 29, 1),
(23, 'Monitor 24 inch', 'Full HD LED monitor', 13500, 9, 1),
(24, 'Monitor 27 inch', '2K IPS monitor', 22000, 7, 1),
(25, 'Graphics Tablet', 'Digital drawing tablet', 6500, 11, 1),

(26, 'Printer Inkjet', 'Color inkjet printer', 8200, 8, 1),
(27, 'Printer Laser', 'Monochrome laser printer', 14500, 6, 1),
(28, 'UPS 600VA', 'Power backup UPS', 3600, 20, 1),
(29, 'UPS 1100VA', 'High power UPS', 6200, 12, 1),
(30, 'Soundbar', 'Dolby soundbar', 9800, 10, 1),

(31, 'VR Headset', 'Virtual reality headset', 32000, 4, 1),
(32, 'Smart Bulb', 'WiFi smart LED bulb', 900, 50, 1),
(33, 'Smart Plug', 'Remote control smart plug', 1100, 45, 1),
(34, 'Security Camera', 'Indoor WiFi camera', 2700, 23, 1),
(35, 'Projector', 'Portable HD projector', 18000, 6, 1),

(36, 'Microphone USB', 'Studio USB microphone', 4200, 14, 1),
(37, 'Game Controller', 'Wireless game controller', 2600, 21, 1),
(38, 'TV Wall Mount', 'Universal wall mount', 1500, 33, 1),
(39, 'HDMI Cable', 'High speed HDMI cable', 500, 100, 1),
(40, 'Surge Protector', 'Electrical surge protector', 1200, 28, 1),

(41, 'Digital Camera', 'Mirrorless digital camera', 58000, 5, 1),
(42, 'Camera Lens', '50mm prime lens', 21000, 7, 1),
(43, 'Tripod Stand', 'Aluminum tripod stand', 1800, 24, 1),
(44, 'Drone', 'Camera drone with GPS', 72000, 3, 1),
(45, 'Dash Camera', 'Car dash cam', 3400, 16, 1),

(46, 'Car Charger', 'Fast charging car adapter', 700, 60, 1),
(47, 'Wireless Charger', 'Qi wireless charger', 1900, 27, 1),
(48, 'Smart Remote', 'Universal smart remote', 2300, 15, 1),
(49, 'TV Streaming Stick', '4K streaming device', 4200, 13, 1),
(50, 'Noise Meter', 'Digital sound level meter', 5100, 9, 1);

-- ============================
-- PRODUCT IMAGES (2 PER PRODUCT)
-- ============================
INSERT INTO product_image (image_address, product_id)
SELECT
    CONCAT('https://cdn.ecom.com/products/', p.id, '_1.jpg'),
    p.id
FROM product p;

INSERT INTO product_image (image_address, product_id)
SELECT
    CONCAT('https://cdn.ecom.com/products/', p.id, '_2.jpg'),
    p.id
FROM product p;
