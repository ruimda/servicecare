CREATE TABLE `trucks` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `licensePlate` varchar(20) NOT NULL,
  `brand` varchar(50) NOT NULL,
  `model` varchar(50) NOT NULL,
  PRIMARY KEY (`id`)
);

CREATE TABLE `locations` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `truckId` int(11) NOT NULL,
  `lat` double NOT NULL,
  `long` double NOT NULL,
  `dt` datetime NOT NULL,
  PRIMARY KEY (`id`)
);