-- Add security-related columns to member table
ALTER TABLE member
    ADD COLUMN password VARCHAR(255) NOT NULL DEFAULT '$2a$10$2b22WwZb0x6H0rXg7QvZyu2bV0x2Qw0p6d0t1Q3Q4r5s6t7u8v9w.',
    ADD COLUMN role VARCHAR(20) NOT NULL DEFAULT 'USER',
    ADD COLUMN status VARCHAR(20) NOT NULL DEFAULT 'ACTIVE';

-- Ensure email is unique
DO $$
BEGIN
    IF NOT EXISTS (
        SELECT 1 FROM information_schema.table_constraints 
        WHERE constraint_type = 'UNIQUE' 
          AND table_name = 'member' 
          AND constraint_name = 'uk_member_email') THEN
        ALTER TABLE member ADD CONSTRAINT uk_member_email UNIQUE (email);
    END IF;
END $$;
