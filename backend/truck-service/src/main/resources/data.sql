
-- Cleanup
delete from trucks;
delete from locations;

-- Trucks
insert into trucks (id, licensePlate, brand, model) values (1, "345-XYZ-TCB", "Fargo", "Brute Force 1");
insert into trucks (id, licensePlate, brand, model) values (2, "234-LIS", "Opel", "Model XYZ");

-- Truck 1 @ Terreiro Paço
insert into locations (truckId, lat, lng, dt) values (1, 38.706291, -9.143673, '2020-03-01 10:00:00');
insert into locations (truckId, lat, lng, dt) values (1, 38.706571, -9.142010, '2020-03-01 10:03:00');
insert into locations (truckId, lat, lng, dt) values (1, 38.705989, -9.141302, '2020-03-01 10:05:00');
insert into locations (truckId, lat, lng, dt) values (1, 38.706022, -9.139682, '2020-03-01 10:07:00');
insert into locations (truckId, lat, lng, dt) values (1, 38.706432, -9.137864, '2020-03-01 10:10:00');
insert into locations (truckId, lat, lng, dt) values (1, 38.706872, -9.136206, '2020-03-01 10:11:00');

-- Truck 2 @ Santa Cruz
insert into locations (truckId, lat, lng, dt) values (2, 36.951951, -122.026750, '2020-03-01 10:30:00');
insert into locations (truckId, lat, lng, dt) values (2, 36.952133, -122.027771, '2020-03-01 10:31:00');
insert into locations (truckId, lat, lng, dt) values (2, 36.952184, -122.029737, '2020-03-01 10:32:00');