-- Point Types
insert into point_types(id, description, icon_file, type) values (1, 'Sklep motoryzacyjny', '', 'automotive_shop');

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