CREATE TABLE IF NOT EXISTS tasklist (
    id VARCHAR(8) PRIMARY Key,
    task VARCHAR(256),
    deadline VARCHAR(10),
    done INT
);

-- IF NOT EXISTS