-- Добавление книг для первых семи авторов (классиков)
INSERT INTO books (title, author_id, isbn, summary, year, number_of_pages, genre_id) VALUES
('Война и мир', (SELECT author_id FROM authors WHERE name = 'Лев Толстой'), '978-3-16-148410-0', 'Эпическая история о Наполеоновских войнах.', 1869, 1225, (SELECT genre_id FROM genres WHERE name = 'Классическая литература')),
('Преступление и наказание', (SELECT author_id FROM authors WHERE name = 'Федор Достоевский'), '978-3-16-148411-7', 'История о моральных дилеммах, преступлении и наказании.', 1866, 671, (SELECT genre_id FROM genres WHERE name = 'Классическая литература')),
('Вишневый сад', (SELECT author_id FROM authors WHERE name = 'Антон Чехов'), '978-3-16-148412-4', 'Пьеса о семейной драме и социальных изменениях в России.', 1904, 92, (SELECT genre_id FROM genres WHERE name = 'Драматургия')),
('Отцы и дети', (SELECT author_id FROM authors WHERE name = 'Иван Тургенев'), '978-3-16-148413-1', 'Роман о конфликте поколений и идеях прогресса.', 1862, 224, (SELECT genre_id FROM genres WHERE name = 'Классическая литература')),
('Мертвые души', (SELECT author_id FROM authors WHERE name = 'Николай Гоголь'), '978-3-16-148414-8', 'Сатирическое изображение русского общества 19-го века.', 1842, 352, (SELECT genre_id FROM genres WHERE name = 'Классическая литература')),
('Евгений Онегин', (SELECT author_id FROM authors WHERE name = 'Александр Пушкин'), '978-3-16-148415-5', 'Роман в стихах, повествующий о жизни дворянской России.', 1833, 224, (SELECT genre_id FROM genres WHERE name = 'Поэзия')),
('Герой нашего времени', (SELECT author_id FROM authors WHERE name = 'Михаил Лермонтов'), '978-3-16-148416-2', 'Роман, рассказывающий о жизни и смерти офицера русской армии.', 1840, 176, (SELECT genre_id FROM genres WHERE name = 'Классическая литература'));

-- Книги для фантастов
INSERT INTO books (title, author_id, isbn, summary, year, number_of_pages, genre_id) VALUES
('Черный отряд', (SELECT author_id FROM authors WHERE name = 'Гленн Кук'), '978-0-312-85723-4', 'История о наемниках в фэнтезийном мире.', 1984, 320, (SELECT genre_id FROM genres WHERE name = 'Фэнтези')),
('Тайный город', (SELECT author_id FROM authors WHERE name = 'Вадим Панов'), '978-5-17-022352-7', 'Магическая Москва, полная тайн и загадок.', 2006, 416, (SELECT genre_id FROM genres WHERE name = 'Фэнтези')),
('Девять принцев Амбера', (SELECT author_id FROM authors WHERE name = 'Роберт Желязны'), '978-0-441-56956-9', 'Путешествие по параллельным мирам в поисках власти и истины.', 1970, 175, (SELECT genre_id FROM genres WHERE name = 'Фэнтези')),
('Путь королей', (SELECT author_id FROM authors WHERE name = 'Брендон Сандерсон'), '978-0-7653-2635-9', 'Эпическая сага в мире, где бури формируют геологию и цивилизацию.', 2010, 1007, (SELECT genre_id FROM genres WHERE name = 'Фэнтези')),
('Ночной дозор', (SELECT author_id FROM authors WHERE name = 'Сергей Лукьяненко'), '978-5-17-015258-9', 'Скрытая борьба между Светом и Тьмой в современной Москве.', 1998, 400, (SELECT genre_id FROM genres WHERE name = 'Фантастика')),
('Дюна', (SELECT author_id FROM authors WHERE name = 'Фрэнк Герберт'), '978-0-441-17271-9', 'Комплексная история о политике, религии и мощи, разворачивающаяся на пустынной планете.', 1965, 412, (SELECT genre_id FROM genres WHERE name = 'Научная фантастика')),
('Основание', (SELECT author_id FROM authors WHERE name = 'Айзек Азимов'), '978-0-553-29335-5', 'Эпическая история о падении и возрождении Галактической Империи.', 1951, 255, (SELECT genre_id FROM genres WHERE name = 'Научная фантастика')),
('2001: Космическая одиссея', (SELECT author_id FROM authors WHERE name = 'Артур Кларк'), '978-0-451-19115-1', 'Исследование космоса и его влияние на человеческое развитие.', 1968, 297, (SELECT genre_id FROM genres WHERE name = 'Научная фантастика')),
('Вспомнить всё', (SELECT author_id FROM authors WHERE name = 'Филип К. Дик'), '978-0-575-07086-3', 'Рассказ о человеке, который обнаруживает, что его воспоминания о жизни на Марсе могут быть искусственными.', 1966, 208, (SELECT genre_id FROM genres WHERE name = 'Научная фантастика'));

-- команда для выполнения скрипта через командую строку PostgreSQL
-- psql -U user -d your_database -f add-books.sql