-- Point Types
insert into point_types(id, description, icon_file, type)
values (1, 'Sklep motoryzacyjny', 'static/markers/blue_MarkerA.png', 'automotive_shop');

insert into point_types(id, description, icon_file, type)
values (2, 'Warsztat samochodowy', 'static/markers/darkgreen_MarkerA.png', 'car_service');


-- Map Points
insert into map_points(id, added_at, added_by, address, approved, city, company_name, info, latitude, longitude, phone,
                                                                                          www, zip_code, point_type_id)
values (1, '2018-10-09', 'admin', 'Juliusza Słowackiego 6', true, 'Nowa Dęba', 'Moto-Art s.c. PPHU. Kosiorowscy', '',
           '50.413028', '21.755959', '15 846 35 34', '', '39-460', 1);

-- Ratings
insert into ratings(id, added_at, added_by, comment, header, rating, map_point_id)
values(1, '2018-10-09', 'admin', 'W sklepie kupowałem wycieraczki. Obsługa bardzo miła, polecam!',
       'Wszystko w porządku', 5, 1);

insert into ratings(id, added_at, added_by, comment, header, rating, map_point_id)
values(2, '2018-10-09', 'Adam', 'Niewielki asortyment dostępny od ręki. Nie było matowego plaka do kokpitu.',
       'Ok', 3, 1);

insert into ratings(id, added_at, added_by, comment, header, rating, map_point_id)
values(3, '2018-10-09', 'Paweł',
       'Cena sprzedanej mi żarówki H4 okazała się zbyt duża w porównaniu do konkurencji! Odradzam kupowania w tym sklepie.',
       'Nie polecam', 2, 1);

insert into ratings(id, added_at, added_by, comment, header, rating, map_point_id)
values(4, '2018-12-1', 'Jacek',
       'Akumulatory dostępne w sklepie są nowej produkcji. U konkurencji można kupić zleżałe, kilkuletnie akumulatory.',
       'Polecam', 5, 1);

-- Rating Comments
insert into comments(id, added_at, added_by, comment, parent)
values (1, '2018-10-10', 'admin', 'Dziękuję za opinie!', 4);

insert into comments(id, added_at, added_by, comment, parent)
values (2, '2018-10-11', 'admin', 'Również dziękuję!', 4);

insert into comments(id, added_at, added_by, comment, parent)
values (3, '2018-10-12', 'admin', 'Super, że podzieliłeś się z nami tą opinią. Nie skorzystam z usług tej firmy.', 4);

insert into comments(id, added_at, added_by, comment, parent)
values (4, '2018-10-13', 'admin', 'Myślę, że kłamiesz.', 4);

insert into comments(id, added_at, added_by, comment, parent)
values (5, '2018-10-13', 'admin', 'On nie okłamał, miałem dokładnie taką samą sytuację.', 4);

insert into comments(id, added_at, added_by, comment, parent)
values (6, '2018-10-10', 'admin', 'Super, dzięki!', 4);

insert into comments(id, added_at, added_by, comment, parent)
values (7, '2018-10-11', 'admin', 'Dobrz, że są tacy ludzie jak Ty!', 4);

insert into comments(id, added_at, added_by, comment, parent)
values (8, '2018-10-12', 'admin', 'Myślę, że pracownicy mieli gorszy dzień.', 4);

insert into comments(id, added_at, added_by, comment, parent)
values (9, '2018-10-13', 'admin', 'Również byłem w tej firmie dwa razy, mam podobne odczucia.', 4);

insert into comments(id, added_at, added_by, comment, parent)
values (10, '2018-10-13', 'admin', 'Dodam jeszcze, że nie ma gdzie usiąść, czekając na auto.', 4);

insert into comments(id, added_at, added_by, comment, parent)
values (11, '2018-10-10', 'admin', 'Na swoje auto czekałem 2 tygodnie!!!', 4);

insert into comments(id, added_at, added_by, comment, parent)
values (12, '2018-10-11', 'admin', 'Dzięki za opinię.', 4);

insert into comments(id, added_at, added_by, comment, parent)
values (13, '2018-10-12', 'admin', 'Naprawiałem u nich głowicę. Skasowali mnie na 3 tysiące złotych!', 4);

insert into comments(id, added_at, added_by, comment, parent)
values (14, '2018-10-13', 'admin', 'Tanio to tam nie mają...', 4);

insert into comments(id, added_at, added_by, comment, parent)
values (15, '2018-10-13', 'admin', 'Szukają jeleni, żeby zarobić...', 4);