CREATE TABLE member
(
    id            UUID         NOT NULL,
    first_name    VARCHAR(255) NOT NULL,
    last_name     VARCHAR(255) NOT NULL,
    email         VARCHAR(255) NOT NULL,
    date_of_birth TIMESTAMP WITHOUT TIME ZONE NOT NULL,
    plan_id       UUID,
    CONSTRAINT pk_member PRIMARY KEY (id)
);

CREATE TABLE plan
(
    id         UUID                            NOT NULL,
    name       VARCHAR(255)                    NOT NULL,
    start_date TIMESTAMP WITHOUT TIME ZONE NOT NULL,
    end_date   TIMESTAMP WITHOUT TIME ZONE NOT NULL,
    type       VARCHAR(10) DEFAULT 'UNLIMITED' NOT NULL,
    CONSTRAINT pk_plan PRIMARY KEY (id)
);

ALTER TABLE member
    ADD CONSTRAINT FK_MEMBER_ON_PLAN FOREIGN KEY (plan_id) REFERENCES plan (id);