CREATE ROLE db_viewer_admin LOGIN PASSWORD 'passwd';

- zalozit DB

CREATE DATABASE db_viewer
    WITH OWNER = db_viewer_admin
    ENCODING = 'UTF8';
