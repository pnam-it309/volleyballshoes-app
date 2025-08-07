-- Add product_code column to Product table
ALTER TABLE Product ADD product_code NVARCHAR(50) NOT NULL DEFAULT 'PROD' + CAST(NEXT VALUE FOR product_code_seq AS NVARCHAR(20));

-- Create a sequence for product codes if it doesn't exist
IF NOT EXISTS (SELECT * FROM sys.sequences WHERE name = 'product_code_seq')
BEGIN
    CREATE SEQUENCE product_code_seq START WITH 1000 INCREMENT BY 1;
END

-- Update existing rows with unique product codes
UPDATE p
SET p.product_code = 'PROD' + RIGHT('0000000000' + CAST(ROW_NUMBER() OVER (ORDER BY product_id) AS NVARCHAR(10)), 10)
FROM Product p
WHERE p.product_code IS NULL OR p.product_code = '';

-- Add unique constraint on product_code
IF NOT EXISTS (SELECT * FROM sys.indexes WHERE name = 'IX_Product_Code' AND object_id = OBJECT_ID('Product'))
BEGIN
    CREATE UNIQUE INDEX IX_Product_Code ON Product(product_code);
END
