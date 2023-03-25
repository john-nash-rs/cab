CREATE TABLE `rider` (
  `id` int NOT NULL AUTO_INCREMENT,
  `name` varchar(45) DEFAULT NULL,
  `phone_number` varchar(45) DEFAULT NULL,
  `country_code` varchar(45) DEFAULT NULL,
  `rider_unique_id` varchar(45) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=11 DEFAULT CHARSET=utf8mb3

CREATE TABLE `driver` (
  `id` int NOT NULL AUTO_INCREMENT,
  `name` varchar(45) DEFAULT NULL,
  `country_code` varchar(45) DEFAULT NULL,
  `phone_number` varchar(45) DEFAULT NULL,
  `driver_unique_id` varchar(45) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=8 DEFAULT CHARSET=utf8mb3


CREATE TABLE `vehicle` (
  `id` int NOT NULL AUTO_INCREMENT,
  `car_number` varchar(45) DEFAULT NULL,
  `lat` bigint DEFAULT NULL,
  `lon` bigint DEFAULT NULL,
  `type` varchar(45) DEFAULT NULL,
  `is_available` tinyint(1) DEFAULT NULL,
  `driver_id` varchar(45) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb3

CREATE TABLE `booking` (
  `id` int NOT NULL AUTO_INCREMENT,
  `booking_id` varchar(45) DEFAULT NULL,
  `rider_user_id` varchar(45) DEFAULT NULL,
  `car_number` varchar(45) DEFAULT NULL,
  `start_time` bigint DEFAULT NULL,
  `end_time` bigint DEFAULT NULL,
  `status` varchar(45) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=13 DEFAULT CHARSET=utf8mb3

CREATE TABLE `bookinghistory` (
  `id` int NOT NULL AUTO_INCREMENT,
  `booking_id` varchar(45) DEFAULT NULL,
  `rider_user_id` varchar(45) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=9 DEFAULT CHARSET=utf8mb3