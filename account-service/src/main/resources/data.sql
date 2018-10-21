INSERT INTO public.accounts(id, email, name, surname, username, password) VALUES (1, 'admin@admin.pl', 'Jan', 'Kowalski', 'admin', '$2a$04$bJ1tcN5YHfcMNoFBXWMvXuBDnlKOg7VT7NOM5AdVNTc5cz02faJtS');
INSERT INTO public.authorities(id, authority, account) VALUES (1, 'ROLE_ADMIN', 1);

INSERT INTO public.accounts(id, email, name, surname, username, password) VALUES (2, 'pawel@pawel.pl', 'Paweł', 'Panek', 'pawel96', '$2a$04$tGozTK7NiC0UUWhJb5lCueVVKKd8iZRVISI7R5P0lX9sWQJlUFowO');
INSERT INTO public.authorities(id, authority, account) VALUES (2, 'ROLE_USER', 2);