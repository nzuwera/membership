ALTER TABLE member
    DROP CONSTRAINT fk_member_on_plan;

ALTER TABLE member
    ADD plan VARCHAR(255);

DROP TABLE plan CASCADE;

ALTER TABLE member
    DROP COLUMN plan_id;