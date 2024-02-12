CREATE DATABASE your_database
    WITH
    OWNER = "user"
    ENCODING 'UTF-8'
    LC_COLLATE = 'en_US.utf8'
    LC_CTYPE = 'en_US.utf8'
    TABLESPACE = pg_default
    CONNECTION LIMIT = -1

-- переключение на созданную базу данных
-- \c your_database;