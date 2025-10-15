ALTER TABLE member
    ALTER COLUMN date_of_birth TYPE date
        USING date_of_birth::date;

ALTER TABLE member
    ALTER COLUMN role TYPE VARCHAR(255) USING (role::VARCHAR(255));

ALTER TABLE member
    ALTER COLUMN status TYPE VARCHAR(255) USING (status::VARCHAR(255));