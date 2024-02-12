CREATE TABLE "genres" (
"genre_id" BIGSERIAL PRIMARY KEY,
"name" VARCHAR(255) NOT NULL UNIQUE
);

-- Создание таблицы для авторов
CREATE TABLE "authors" (
"author_id" BIGSERIAL PRIMARY KEY,
"name" VARCHAR(255) NOT NULL UNIQUE,
"bio" TEXT
);

-- Создание таблицы для книг
CREATE TABLE "books" (
"book_id" BIGSERIAL PRIMARY KEY,
"title" VARCHAR(255) NOT NULL,
"author_id" BIGINT NOT NULL,
"isbn" VARCHAR(17) NOT NULL UNIQUE,
"summary" TEXT,
"year" INT,
"number_of_pages" INT,
"genre_id" BIGINT NOT NULL,
FOREIGN KEY ("author_id") REFERENCES "authors"("author_id") ON DELETE CASCADE,
FOREIGN KEY ("genre_id") REFERENCES "genres"("genre_id")
);

-- Создание таблицы для пользователей
CREATE TABLE "users" (
"user_id" BIGSERIAL PRIMARY KEY,
"username" VARCHAR(255) NOT NULL UNIQUE,
"password" VARCHAR(255) NOT NULL,
"email" VARCHAR(255) NOT NULL UNIQUE,
"created_at" TIMESTAMP WITH TIME ZONE NOT NULL,
"enabled" BOOLEAN NOT NULL DEFAULT true
);

-- Создание таблицы для ролей
CREATE TABLE "roles" (
"role_id" BIGSERIAL PRIMARY KEY,
"name" VARCHAR(255) NOT NULL UNIQUE
);

-- Создание таблицы для связи ManyToMany между пользователями и ролями
CREATE TABLE "users_roles" (
"user_id" BIGINT NOT NULL,
"role_id" BIGINT NOT NULL,
CONSTRAINT "fk_user" FOREIGN KEY ("user_id") REFERENCES "users" ("user_id"),
CONSTRAINT "fk_role" FOREIGN KEY (role_id) REFERENCES "roles" ("role_id"),
PRIMARY KEY ("user_id", "role_id")
);

-- Создание таблицы для комментариев
CREATE TABLE "comments" (
"comment_id" BIGSERIAL PRIMARY KEY,
"text" TEXT NOT NULL,
"book_id" BIGINT NOT NULL,
"user_id" BIGINT NOT NULL,
"created_at" TIMESTAMP WITH TIME ZONE NOT NULL,
FOREIGN KEY ("book_id") REFERENCES "books"("book_id") ON DELETE CASCADE,
FOREIGN KEY ("user_id") REFERENCES "users"("user_id") ON DELETE CASCADE
);