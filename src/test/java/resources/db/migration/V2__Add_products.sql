insert into products
(id, title, description, price) values
(1, 'tangerines', 'Description 1', 32.0),
(2, 'sausage', 'Description 2', 80.0),
(3, 'bread', 'Description 3', 80.0),
(4, 'cucumbers', 'Description 4', 30.0),
(5, 'tomato', 'Description 5', 10.0),
(6, 'champagne', 'Description 6', 20.0),
(7, 'wine', 'Description 7', 30.0),
(8, 'grapes', 'Description 8', 40.0),
(9, 'mayonnaise', 'Description 9', 50.0),
(10, 'carrot', 'Description 10', 60.0);

alter sequence products_seq restart with 11;