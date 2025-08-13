-- H2 Database Migration V2 - Seed Data

-- Sample Data for mst_akses
INSERT INTO projectz.mst_akses (nama, deskripsi, created_by, created_date) VALUES
('SUPER_ADMIN', 'Administrator dengan akses penuh ke seluruh sistem', 1, CURRENT_TIMESTAMP()),
('ADMIN', 'Administrator dengan akses terbatas untuk manajemen data', 1, CURRENT_TIMESTAMP()),
('USER', 'Pengguna biasa dengan akses terbatas untuk viewing data', 1, CURRENT_TIMESTAMP());

-- Sample Data for mst_group_menu
INSERT INTO projectz.mst_group_menu (nama, deskripsi, created_by, created_date) VALUES
('Dashboard', 'Menu grup untuk dashboard dan overview', 1, CURRENT_TIMESTAMP()),
('Master Data', 'Menu grup untuk manajemen data master', 1, CURRENT_TIMESTAMP()),
('Report', 'Menu grup untuk laporan dan analisa', 1, CURRENT_TIMESTAMP());

-- Sample Data for mst_menu
INSERT INTO projectz.mst_menu (id_group_menu, nama, path, deskripsi, created_by, created_date) VALUES
(1, 'Home', '/dashboard', 'Dashboard utama sistem', 1, CURRENT_TIMESTAMP()),
(2, 'Produk', '/produk', 'Manajemen data produk', 1, CURRENT_TIMESTAMP()),
(2, 'Kategori', '/kategori', 'Manajemen kategori produk', 1, CURRENT_TIMESTAMP()),
(2, 'Supplier', '/supplier', 'Manajemen data supplier', 1, CURRENT_TIMESTAMP()),
(2, 'User', '/user', 'Manajemen pengguna sistem', 1, CURRENT_TIMESTAMP()),
(3, 'Laporan Produk', '/report/produk', 'Laporan data produk', 1, CURRENT_TIMESTAMP());

-- Sample Data for mst_kategori_produk
INSERT INTO projectz.mst_kategori_produk (nama_produk, deskripsi, notes, created_at, created_by) VALUES
('Elektronik', 'Kategori untuk produk elektronik', 'Mencakup smartphone, laptop, dan gadget lainnya', CURRENT_TIMESTAMP(), 1),
('Fashion', 'Kategori untuk produk fashion', 'Mencakup pakaian, sepatu, dan aksesoris', CURRENT_TIMESTAMP(), 1),
('Makanan', 'Kategori untuk produk makanan', 'Mencakup makanan ringan, minuman, dan bumbu', CURRENT_TIMESTAMP(), 1),
('Buku', 'Kategori untuk produk buku', 'Mencakup buku fiksi, non-fiksi, dan komik', CURRENT_TIMESTAMP(), 1);

-- Sample Data for mst_produk
INSERT INTO projectz.mst_produk (id_kategori_produk, nama_produk, merk, model, warna, deskripsi_produk, stok, created_at, created_by) VALUES
(1, 'Smartphone Android', 'Samsung', 'Galaxy S23', 'Hitam', 'Smartphone dengan kamera 108MP dan layar AMOLED', 50, CURRENT_TIMESTAMP(), 1),
(1, 'Laptop Gaming', 'ASUS', 'ROG Strix G15', 'Silver', 'Laptop gaming dengan RTX 3060 dan Ryzen 7', 25, CURRENT_TIMESTAMP(), 1),
(2, 'Kaos Cotton', 'Uniqlo', 'UT Graphic T-Shirt', 'Putih', 'Kaos katun premium dengan desain grafis', 100, CURRENT_TIMESTAMP(), 1),
(2, 'Sepatu Sneakers', 'Nike', 'Air Max 270', 'Biru', 'Sepatu sneakers dengan teknologi Air Max', 30, CURRENT_TIMESTAMP(), 1),
(3, 'Kopi Arabica', 'Starbucks', 'Pike Place Roast', 'Coklat', 'Kopi arabica dengan rasa medium roast', 200, CURRENT_TIMESTAMP(), 1),
(4, 'Novel Fiksi', 'Gramedia', 'Laskar Pelangi', 'Multicolor', 'Novel karya Andrea Hirata tentang pendidikan', 75, CURRENT_TIMESTAMP(), 1);

-- Sample Data for mst_supplier
INSERT INTO projectz.mst_supplier (nama_supplier, alamat_supplier, created_at, created_by) VALUES
('PT. Teknologi Maju', 'Jl. Sudirman No. 123, Jakarta Selatan', CURRENT_TIMESTAMP(), 1),
('CV. Fashion Store', 'Jl. Malioboro No. 45, Yogyakarta', CURRENT_TIMESTAMP(), 1),
('Toko Buku Sejahtera', 'Jl. Braga No. 67, Bandung', CURRENT_TIMESTAMP(), 1),
('Distributor Kopi Nusantara', 'Jl. Gajah Mada No. 89, Surabaya', CURRENT_TIMESTAMP(), 1);

-- Sample Data for mst_user (Password: "password123" encrypted)
INSERT INTO projectz.mst_user (id_akses, username, password, nama_lengkap, email, no_hp, alamat, tanggal_lahir, is_registered, created_by, created_date) VALUES
(1, 'superadmin', '$2a$10$IW8Z36PmrfIbw1pADJLWUu1le2O4wBfpQEQaIcSoE5e32jm13w0y6', 'Super Administrator', 'superadmin@projectz.com', '081234567890', 'Jakarta, Indonesia', '1990-01-01', true, 1, CURRENT_TIMESTAMP()),
(2, 'admin', '$2a$10$IW8Z36PmrfIbw1pADJLWUu1le2O4wBfpQEQaIcSoE5e32jm13w0y6', 'Administrator', 'admin@projectz.com', '081234567891', 'Bandung, Indonesia', '1992-05-15', true, 1, CURRENT_TIMESTAMP()),
(3, 'user1', '$2a$10$IW8Z36PmrfIbw1pADJLWUu1le2O4wBfpQEQaIcSoE5e32jm13w0y6', 'User Pertama', 'user1@projectz.com', '081234567892', 'Surabaya, Indonesia', '1995-08-20', true, 1, CURRENT_TIMESTAMP());

-- Sample Data for map_akses_menu (Access Control)
INSERT INTO projectz.map_akses_menu (id_akses, id_menu) VALUES
-- Super Admin has access to all menus
(1, 1), (1, 2), (1, 3), (1, 4), (1, 5), (1, 6),
-- Admin has access to dashboard and master data
(2, 1), (2, 2), (2, 3), (2, 4), (2, 5),
-- User only has access to dashboard and reports
(3, 1), (3, 6);

-- Sample Data for map_produk_supplier (Product-Supplier mapping)
INSERT INTO projectz.map_produk_supplier (id_produk, id_supplier) VALUES
(1, 1), (2, 1), -- Electronics from PT. Teknologi Maju
(3, 2), (4, 2), -- Fashion from CV. Fashion Store
(5, 4),         -- Coffee from Distributor Kopi Nusantara
(6, 3);         -- Books from Toko Buku Sejahtera
