INSERT INTO users (username, email, password, role)
VALUES('john_doe', 'john@example.com', 'password123', 'USER'),
('alex', 'alex@example.com', 'alex123', 'USER');

INSERT INTO tasks (title, description, deadline, status, user_id)
VALUES ('Complete homework', 'Finish math and science homework', '2026-03-20', 'PENDING', 1);

INSERT INTO tasks (title, description, deadline, status, user_id)
VALUES ('Fix bug', 'Resolve login bug in auth module', '2026-03-25', 'IN_PROGRESS', 1);