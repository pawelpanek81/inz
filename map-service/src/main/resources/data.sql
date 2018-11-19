-- Point Types
insert into point_types(id, description, icon_file, type)
values (1, 'Sklep motoryzacyjny', 'static/markers/blue_MarkerA.png', 'automotive_shop');

insert into point_types(id, description, icon_file, type)
values (2, 'Warsztat samochodowy', 'static/markers/darkgreen_MarkerA.png', 'car_service');


-- Map Points
insert into map_points(id, added_at, added_by, address, approved, city, company_name, info, latitude, longitude, phone,
                                                                                          www, zip_code, point_type_id)
values (1, '2018-10-09', 'Karol5', 'Juliusza Słowackiego 1', true, 'Nowa Dęba', 'MotorX Sklep Motoryzacyjny', '',
           '50.412956', '21.754604', '15 846 11 22', '', '39-460', 1);

-- Ratings
insert into ratings(id, added_at, added_by, comment, header, rating, map_point_id)
values(1, '2018-10-09', 'Jacek', 'W sklepie kupiłem wycieraczki. Obsługa okazała sie być bardzo miła, polecam!',
       'Wszystko w porządku', 5, 1);

insert into ratings(id, added_at, added_by, comment, header, rating, map_point_id)
values(2, '2018-10-09', 'Adam', 'Niewielki asortyment dostępny od ręki. Nie było matowego plaka do kokpitu.',
       'Ok', 3, 1);

insert into ratings(id, added_at, added_by, comment, header, rating, map_point_id)
values(3, '2018-10-09', 'Paweł',
       'Cena sprzedanej mi żarówki H4 okazała się zbyt wysoka w porównaniu z konkurencją! Odradzam kupowania w tym sklepie.',
       'Nie polecam', 2, 1);

insert into ratings(id, added_at, added_by, comment, header, rating, map_point_id)
values(4, '2018-11-16', 'pawel96',
       'Akumulatory dostępne w sklepie są nowej produkcji. U konkurencji można kupić zleżałe, kilkuletnie akumulatory.',
       'Polecam', 5, 1);

insert into ratings(id, added_at, added_by, comment, header, rating, map_point_id)
values(5, '2018-11-20', 'user15',
       'Uważam, że pracownicy tego sklepu są bardzo niekompetentni!',
       'Nie polecam', 2, 1);

insert into ratings(id, added_at, added_by, comment, header, rating, map_point_id)
values(6, '2018-11-26', 'Olaa',
       'Sklep nie jest duży, jednak zawiera wszystkie niezbędne artykuły.',
       'Ok', 5, 1);

-- Rating Comments
insert into comments(id, added_at, added_by, comment, parent)
values (1, '2018-10-10', 'Jan111', 'Dziękuję za opinię!', 4);

insert into comments(id, added_at, added_by, comment, parent)
values (2, '2018-10-11', 'AdamK', 'Również dziękuję', 4);

insert into comments(id, added_at, added_by, comment, parent)
values (3, '2018-10-13', 'Grzegorz1', 'Ja również kupowałem tam akumulator. Jestem bardzo zadowolony, użytkuję go już 5 rok.', 3);

insert into comments(id, added_at, added_by, comment, parent)
values (4, '2018-10-10', 'Olaa', 'Super, dzięki.', 4);

insert into comments(id, added_at, added_by, comment, parent)
values (5, '2018-10-11', 'Tomasz44', 'Dobrze, że podzieliłeś się z swoją opinią. Akurat planuję wymienić swój na zimę.', 4);