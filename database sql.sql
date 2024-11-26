-- phpMyAdmin SQL Dump
-- version 5.2.1
-- https://www.phpmyadmin.net/
--
-- Host: 127.0.0.1
-- Generation Time: Jun 10, 2024 at 06:58 PM
-- Server version: 10.4.32-MariaDB
-- PHP Version: 8.2.12

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `ergasia 2`
--

-- --------------------------------------------------------

--
-- Table structure for table `cars`
--

CREATE TABLE `cars` (
  `car_id` int(11) NOT NULL,
  `type` varchar(30) NOT NULL,
  `seats` int(11) NOT NULL,
  `energy` varchar(30) NOT NULL,
  `price` double NOT NULL,
  `shop_shop_id` int(11) NOT NULL,
  `Rent_state` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `cars`
--

INSERT INTO `cars` (`car_id`, `type`, `seats`, `energy`, `price`, `shop_shop_id`, `Rent_state`) VALUES
(1, 'smart', 4, 'gas', 70, 1, 0),
(2, 'hatchback', 2, 'electric', 70, 1, 0),
(3, 'cabrio', 2, 'TEST', 100, 1, 1),
(4, 'convertible', 4, 'gas', 70, 2, 0),
(5, 'cabrio', 2, 'gas', 150, 2, 0),
(6, 'xsdada', 2, 'gas', 122, 1, 0),
(8, 'dsada', 2, 'gas', 12, 2, 0),
(9, 'Sedan', 5, 'Gasoline', 200, 1, 0),
(10, 'SUV', 7, 'Diesel', 350, 2, 0),
(11, 'Hatchback', 5, 'Electric', 200, 4, 0),
(12, 'Convertible', 2, 'Gasoline', 400, 1, 1),
(13, 'Pickup', 4, 'Diesel', 300, 6, 1),
(14, 'Coupe', 4, 'Electric', 280, 4, 0),
(15, 'Minivan', 7, 'Gasoline', 300, 2, 0),
(16, 'Sedan', 5, 'Hybrid', 270, 6, 0),
(17, 'SUV', 5, 'Electric', 450, 4, 0);

-- --------------------------------------------------------

--
-- Table structure for table `customer`
--

CREATE TABLE `customer` (
  `customer_id` int(11) NOT NULL,
  `name` varchar(30) NOT NULL,
  `surname` varchar(30) NOT NULL,
  `email` varchar(30) NOT NULL,
  `driving_license_id` int(11) NOT NULL,
  `phone_number` int(11) NOT NULL,
  `shop_shop_id` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `customer`
--

INSERT INTO `customer` (`customer_id`, `name`, `surname`, `email`, `driving_license_id`, `phone_number`, `shop_shop_id`) VALUES
(1, 'filippos', 'me', 'odsajdjas', 594834, 50894, 1),
(2, 'dsdsajd', 'dsadas', 'dsadas', 3232, 59490, 1),
(3, 'dsa', 'dsa', 'dsa', 32, 14512, 1),
(4, 'fasf', 'fsafas', 'fas', 433, 321, 1),
(5, 'dsajda', 'dsada', 'poutsa', 534, 314, 1),
(7, '3214', 'dfas', 'fdda', 53, 43, 1),
(8, 'fjhasfas', 'fsafsa', 'fsa', 433, 321, 1),
(9, 'dsadsa', 'dsadas', 'dsadas', 431, 430934, 1),
(10, 'dfsjkadas', 'dasdas', 'dsad', 3213, 14514, 1),
(11, 'filippos', 'dsadsa', 'sdsada', 32, 1212, 1),
(12, 'asda', 'as', 's', 1, 1, 1),
(13, 'filippos', 'mertzanakis', 'sdadas@gmail,com', 1233, 569932315, 6),
(14, 'filippos', 'mertzanakis', 'sdadas@gmail.com', 12314, 83213125, 1),
(15, 'NAI', 'NAI', 'NAI', 3, 1, 2),
(16, 'filipos', 'test', 'test', 32, 123, 1);

-- --------------------------------------------------------

--
-- Table structure for table `rental`
--

CREATE TABLE `rental` (
  `rental_id` int(11) NOT NULL,
  `date_of_pickup` datetime NOT NULL,
  `date_of_return` datetime DEFAULT NULL,
  `city` varchar(30) NOT NULL,
  `place_of_service` varchar(30) DEFAULT NULL,
  `customer_customer_id` int(11) NOT NULL,
  `cars_car_id` int(11) NOT NULL,
  `cars_shop_shop_id` int(11) NOT NULL,
  `shop_shop_id` int(11) NOT NULL,
  `customer_shop_shop_id` int(11) NOT NULL,
  `place_of_return` varchar(40) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `rental`
--

INSERT INTO `rental` (`rental_id`, `date_of_pickup`, `date_of_return`, `city`, `place_of_service`, `customer_customer_id`, `cars_car_id`, `cars_shop_shop_id`, `shop_shop_id`, `customer_shop_shop_id`, `place_of_return`) VALUES
(6, '0001-01-01 01:00:00', '0001-01-01 01:00:00', 'dfsa', 'dsad', 2, 1, 1, 1, 1, ''),
(1, '2024-05-09 00:00:00', '2024-05-16 00:00:00', 'athens', 'psyrh', 5, 3, 1, 1, 1, ''),
(2, '2024-05-23 00:00:00', '2024-05-26 00:00:00', 'thess', 'psyrh', 7, 2, 1, 1, 1, ''),
(3, '0200-05-05 00:00:00', '0001-01-01 00:00:00', 'athens', 'psyrh', 8, 3, 1, 1, 1, ''),
(4, '0001-01-01 00:00:00', '0002-02-02 00:00:00', 'athens', 'psyrh', 9, 3, 1, 1, 1, ''),
(5, '0001-01-01 00:00:00', '0001-01-01 00:00:00', 'athens', 'psyrh', 10, 1, 1, 1, 1, ''),
(7, '2024-05-28 00:00:00', '2024-05-30 00:00:00', 'athens', 'psyrh', 11, 1, 1, 1, 1, 'kalogreza'),
(8, '0001-01-01 00:00:00', '0001-01-01 00:00:00', 'athens', 'psyrh', 12, 1, 1, 1, 1, 'psyrh'),
(9, '2024-01-01 00:00:00', '2024-01-03 00:00:00', 'thessalonikh', 'kamara', 13, 13, 6, 6, 6, 'kamara'),
(10, '2024-02-03 00:00:00', '2024-04-02 00:00:00', 'athens', 'psyrh', 16, 12, 1, 1, 1, 'psyrh');

-- --------------------------------------------------------

--
-- Table structure for table `shop`
--

CREATE TABLE `shop` (
  `shop_id` int(11) NOT NULL,
  `city` varchar(30) NOT NULL,
  `place_of_service` varchar(30) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `shop`
--

INSERT INTO `shop` (`shop_id`, `city`, `place_of_service`) VALUES
(1, 'athens', 'psyrh'),
(2, 'patra', 'aiolou'),
(4, 'athens', 'kalogreza'),
(6, 'thessalonikh', 'kamara');

--
-- Indexes for dumped tables
--

--
-- Indexes for table `cars`
--
ALTER TABLE `cars`
  ADD PRIMARY KEY (`car_id`,`shop_shop_id`),
  ADD KEY `cars_shop_fk` (`shop_shop_id`);

--
-- Indexes for table `customer`
--
ALTER TABLE `customer`
  ADD PRIMARY KEY (`customer_id`,`shop_shop_id`),
  ADD KEY `customer_shop_fk` (`shop_shop_id`);

--
-- Indexes for table `rental`
--
ALTER TABLE `rental`
  ADD PRIMARY KEY (`customer_customer_id`,`customer_shop_shop_id`,`shop_shop_id`,`rental_id`),
  ADD KEY `rental_cars_fk` (`cars_car_id`,`cars_shop_shop_id`),
  ADD KEY `rental_shop_fk` (`shop_shop_id`);

--
-- Indexes for table `shop`
--
ALTER TABLE `shop`
  ADD PRIMARY KEY (`shop_id`);

--
-- Constraints for dumped tables
--

--
-- Constraints for table `cars`
--
ALTER TABLE `cars`
  ADD CONSTRAINT `cars_shop_fk` FOREIGN KEY (`shop_shop_id`) REFERENCES `shop` (`shop_id`);

--
-- Constraints for table `customer`
--
ALTER TABLE `customer`
  ADD CONSTRAINT `customer_shop_fk` FOREIGN KEY (`shop_shop_id`) REFERENCES `shop` (`shop_id`);

--
-- Constraints for table `rental`
--
ALTER TABLE `rental`
  ADD CONSTRAINT `rental_cars_fk` FOREIGN KEY (`cars_car_id`,`cars_shop_shop_id`) REFERENCES `cars` (`car_id`, `shop_shop_id`),
  ADD CONSTRAINT `rental_ibfk_1` FOREIGN KEY (`customer_customer_id`) REFERENCES `customer` (`customer_id`),
  ADD CONSTRAINT `rental_shop_fk` FOREIGN KEY (`shop_shop_id`) REFERENCES `shop` (`shop_id`);
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
