CREATE TABLE quiz_guest (
    quiz_id BIGINT NOT NULL REFERENCES quiz(id),
    guest_id BIGINT NOT NULL REFERENCES guest(id),
    PRIMARY KEY (quiz_id, guest_id)
);
