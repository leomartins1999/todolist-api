ALTER TABLE todo
    ADD COLUMN status VARCHAR(64) NOT NULL
    DEFAULT 'TODO';

UPDATE todo
SET status = 'DONE'
WHERE done;

ALTER table todo DROP COLUMN done;
