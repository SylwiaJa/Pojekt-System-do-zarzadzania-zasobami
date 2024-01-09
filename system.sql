-- phpMyAdmin SQL Dump
-- version 5.2.1
-- https://www.phpmyadmin.net/
--
-- Host: localhost
-- Generation Time: Sty 04, 2024 at 10:40 PM
-- Wersja serwera: 10.4.28-MariaDB
-- Wersja PHP: 8.2.4

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `system`
--

-- --------------------------------------------------------

--
-- Struktura tabeli dla tabeli `component`
--

CREATE TABLE `component` (
  `componentID` int(10) NOT NULL,
  `name` varchar(20) DEFAULT NULL,
  `quantity` int(10) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;




-- --------------------------------------------------------

--
-- Struktura tabeli dla tabeli `employee`
--

CREATE TABLE `employee` (
  `employeeID` int(10) NOT NULL,
  `name` varchar(20) NOT NULL,
  `lastName` varchar(20) NOT NULL,
  `roleID` int(10) DEFAULT NULL,
  `zoneID` int(10) DEFAULT NULL,
  `login` varchar(10) DEFAULT NULL,
  `password` varchar(64) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `employee`
--

INSERT INTO `employee` (`employeeID`, `name`, `lastName`, `roleID`, `zoneID`, `login`, `password`) VALUES
(1, 'John', 'Smith', 1, 1, 'johnSmith', '0b14d501a594442a01c6859541bcb3e8164d183d32937b851835442f69d5c94e'),
(2, 'Alice', 'Johnson', 1, 2, 'aliJohn', 'fbb4a8a163ffa958b4f02bf9cabb30cfefb40de803f2c4c346a9d39b3be1b544'),
(3, 'Michael', 'Davis', 1, 1, 'michDav', '28c58559e2fe38904115fbce1e3a7095c6423d86d25c411c6f9e3ea62de3a4b8'),
(4, 'Emily', 'Clark', 4, 10, 'emilClar', '527ad704c9463211ae9ec71a3d549ca0a3cadc5d808f3768aa87de0ee77ed129'),
(5, 'Robert', 'Turner', 3, 2, 'robTurn', '43f8a2ad1882637f749ce419d33a02f74debb66991d7d65aade4eea9ded2a120'),
(6, 'Samantha', 'Miller', 4, 10, 'samantMil', '557da7437051616d1067efbc8734494717b4f92ff17f2c73708a65c9c3b9e44d'),
(7, 'Daniel', 'White', 2, 10, 'danWhit', '32525916c8a167495ed00d0fb7b0fd33215b2ef7dab70543562d52843becf7ab');

-- --------------------------------------------------------

--
-- Struktura tabeli dla tabeli `employeeLicense`
--

CREATE TABLE `employeeLicense` (
  `employeeID` int(10) NOT NULL,
  `licenseID` int(10) NOT NULL,
  `startDate` date NOT NULL,
  `expirationDate` date DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- --------------------------------------------------------

--
-- Struktura tabeli dla tabeli `equipment`
--

CREATE TABLE `equipment` (
  `equipmentID` int(10) NOT NULL,
  `name` varchar(20) NOT NULL,
  `equipmentCategoryID` int(10) NOT NULL,
  `status` varchar(20) NOT NULL,
  `zoneID` int(10) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;



-- --------------------------------------------------------

--
-- Struktura tabeli dla tabeli `equipmentCategory`
--

CREATE TABLE `equipmentCategory` (
  `equipmentCategoryID` int(10) NOT NULL,
  `name` varchar(20) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;



-- --------------------------------------------------------

--
-- Struktura tabeli dla tabeli `equipmentCategoryLicense`
--

CREATE TABLE `equipmentCategoryLicense` (
  `equipmentCategoryID` int(10) NOT NULL,
  `licenseID` int(10) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- --------------------------------------------------------

--
-- Struktura tabeli dla tabeli `license`
--

CREATE TABLE `license` (
  `licenseID` int(10) NOT NULL,
  `name` varchar(20) NOT NULL,
  `description` varchar(100) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- --------------------------------------------------------

--
-- Struktura tabeli dla tabeli `loginHistory`
--

CREATE TABLE `loginHistory` (
  `loginHistoryID` int(10) NOT NULL,
  `employeeID` int(10) NOT NULL,
  `startTime` timestamp NOT NULL DEFAULT current_timestamp() ON UPDATE current_timestamp(),
  `endTime` timestamp NOT NULL DEFAULT '0000-00-00 00:00:00'
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `loginHistory`
--

INSERT INTO `loginHistory` (`loginHistoryID`, `employeeID`, `startTime`, `endTime`) VALUES
(10, 7, '2024-01-04 20:17:36', '0000-00-00 00:00:00'),
(11, 7, '2024-01-04 20:22:38', '0000-00-00 00:00:00'),
(12, 7, '2024-01-04 20:27:53', '0000-00-00 00:00:00'),
(13, 7, '2024-01-04 20:32:00', '0000-00-00 00:00:00'),
(14, 7, '2024-01-04 20:39:02', '0000-00-00 00:00:00'),
(15, 7, '2024-01-04 20:41:33', '0000-00-00 00:00:00'),
(16, 7, '2024-01-04 21:31:50', '0000-00-00 00:00:00'),
(17, 7, '2024-01-04 21:36:41', '0000-00-00 00:00:00'),
(18, 7, '2024-01-04 21:38:38', '0000-00-00 00:00:00');

-- --------------------------------------------------------

--
-- Struktura tabeli dla tabeli `orders`
--

CREATE TABLE `orders` (
  `OrderID` int(10) NOT NULL,
  `Status` varchar(10) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `orders`
--

INSERT INTO `orders` (`OrderID`, `Status`) VALUES
(1, 'accepted'),
(2, 'progress'),
(3, 'suspended'),
(4, 'completed');

-- --------------------------------------------------------

--
-- Struktura tabeli dla tabeli `orderQuantity`
--

CREATE TABLE `orderQuantity` (
  `orderQuantityID` int(10) NOT NULL,
  `productID` int(10) NOT NULL,
  `quantityOrdered` int(10) NOT NULL,
  `orderID` int(10) NOT NULL,
  `QuantityInProduction` int(10) DEFAULT NULL,
  `OuantityFinished` int(11) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `orderQuantity`
--

INSERT INTO `orderQuantity` (`orderQuantityID`, `productID`, `quantityOrdered`, `orderID`, `QuantityInProduction`, `OuantityFinished`) VALUES
(1, 1, 100, 1, 0, 0),
(2, 2, 100, 1, 0, 0),
(3, 3, 100, 3, 0, 0),
(4, 1, 30, 1, 0, 0),
(5, 2, 40, 1, 0, 0);

-- --------------------------------------------------------

--
-- Struktura tabeli dla tabeli `product`
--

CREATE TABLE `product` (
  `productID` int(10) NOT NULL,
  `name` varchar(20) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `product`
--

INSERT INTO `product` (`productID`, `name`) VALUES
(1, 'Chair'),
(2, 'Table'),
(3, 'Armchair'),
(4, 'Bed'),
(5, 'Desk'),
(6, 'Wardrobe'),
(7, 'Chest of drawers');

-- --------------------------------------------------------

--
-- Struktura tabeli dla tabeli `result`
--

CREATE TABLE `result` (
  `resultID` int(10) NOT NULL,
  `quantityOK` int(10) DEFAULT NULL,
  `quantityNOK` int(10) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;



-- --------------------------------------------------------

--
-- Struktura tabeli dla tabeli `role`
--

CREATE TABLE `role` (
  `roleID` int(10) NOT NULL,
  `roleName` varchar(20) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `role`
--

INSERT INTO `role` (`roleID`, `roleName`) VALUES
(1, 'Production Employee'),
(2, 'Admin'),
(3, 'Leader'),
(4, 'Manager');

-- --------------------------------------------------------

--
-- Struktura tabeli dla tabeli `task`
--

CREATE TABLE `task` (
  `taskID` int(10) NOT NULL,
  `name` varchar(20) NOT NULL,
  `priority` varchar(20) DEFAULT NULL,
  `description` varchar(100) NOT NULL,
  `taskCategory` int(10) NOT NULL,
  `norm` int(10) NOT NULL,
  `resultID` int(10) DEFAULT NULL,
  `productID` int(10) NOT NULL,
  `quantity` int(10) NOT NULL,
  `orderID` int(10) NOT NULL,
  `zoneID` int(10) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;




-- --------------------------------------------------------

--
-- Struktura tabeli dla tabeli `taskCategory`
--

CREATE TABLE `taskCategory` (
  `taskCategoryID` int(10) NOT NULL,
  `name` varchar(20) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `taskCategory`
--

INSERT INTO `taskCategory` (`taskCategoryID`, `name`) VALUES
(1, 'Chair'),
(2, 'Table'),
(3, 'Armchair'),
(4, 'Bed'),
(5, 'Desk'),
(6, 'Wardrobe'),
(7, 'Chest of drawers');

-- --------------------------------------------------------

--
-- Struktura tabeli dla tabeli `taskCategoryComponent`
--

CREATE TABLE `taskCategoryComponent` (
  `taskCategoryID` int(10) NOT NULL,
  `componentID` int(10) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- --------------------------------------------------------

--
-- Struktura tabeli dla tabeli `taskCategoryLicense`
--

CREATE TABLE `taskCategoryLicense` (
  `taskCategoryID` int(10) NOT NULL,
  `licenseID` int(10) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- --------------------------------------------------------

--
-- Struktura tabeli dla tabeli `taskComponent`
--

CREATE TABLE `taskComponent` (
  `taskID` int(10) NOT NULL,
  `componentID` int(10) NOT NULL,
  `quantity` int(10) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;




-- --------------------------------------------------------

--
-- Struktura tabeli dla tabeli `taskEquipment`
--

CREATE TABLE `taskEquipment` (
  `taskID` int(10) NOT NULL,
  `equipmentID` int(10) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;




-- --------------------------------------------------------

--
-- Struktura tabeli dla tabeli `taskEquipmentCategory`
--

CREATE TABLE `taskEquipmentCategory` (
  `taskCategoryID` int(10) NOT NULL,
  `equipmentCategoryID` int(10) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- --------------------------------------------------------

--
-- Struktura tabeli dla tabeli `taskStatus`
--

CREATE TABLE `taskStatus` (
  `taskStatusID` int(10) NOT NULL,
  `taskID` int(10) NOT NULL,
  `employeeID` int(10) NOT NULL,
  `stepName` varchar(20) NOT NULL,
  `startStep` timestamp NOT NULL DEFAULT current_timestamp() ON UPDATE current_timestamp(),
  `endStep` timestamp NOT NULL DEFAULT '0000-00-00 00:00:00'
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;




-- --------------------------------------------------------

--
-- Struktura tabeli dla tabeli `zone`
--

CREATE TABLE `zone` (
  `zoneID` int(10) NOT NULL,
  `name` varchar(20) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `zone`
--

INSERT INTO `zone` (`zoneID`, `name`) VALUES
(1, 'Chair Area'),
(2, 'Table Area'),
(3, 'Armchair Area'),
(4, 'Bed Area'),
(5, 'Desk Area'),
(6, 'Wardrobe Area'),
(7, 'Chest Area'),
(8, 'Warehouse'),
(9, 'Service and Repairs'),
(10, 'Office');

--
-- Indeksy dla zrzutów tabel
--

--
-- Indeksy dla tabeli `component`
--
ALTER TABLE `component`
  ADD PRIMARY KEY (`componentID`);

--
-- Indeksy dla tabeli `employee`
--
ALTER TABLE `employee`
  ADD PRIMARY KEY (`employeeID`),
  ADD KEY `zoneID` (`zoneID`),
  ADD KEY `roleID` (`roleID`);

--
-- Indeksy dla tabeli `employeeLicense`
--
ALTER TABLE `employeeLicense`
  ADD PRIMARY KEY (`employeeID`,`licenseID`),
  ADD KEY `licenseID` (`licenseID`);

--
-- Indeksy dla tabeli `equipment`
--
ALTER TABLE `equipment`
  ADD PRIMARY KEY (`equipmentID`),
  ADD KEY `equipmentCategoryID` (`equipmentCategoryID`),
  ADD KEY `zoneID` (`zoneID`);

--
-- Indeksy dla tabeli `equipmentCategory`
--
ALTER TABLE `equipmentCategory`
  ADD PRIMARY KEY (`equipmentCategoryID`);

--
-- Indeksy dla tabeli `equipmentCategoryLicense`
--
ALTER TABLE `equipmentCategoryLicense`
  ADD PRIMARY KEY (`equipmentCategoryID`,`licenseID`),
  ADD KEY `licenseID` (`licenseID`);

--
-- Indeksy dla tabeli `license`
--
ALTER TABLE `license`
  ADD PRIMARY KEY (`licenseID`);

--
-- Indeksy dla tabeli `loginHistory`
--
ALTER TABLE `loginHistory`
  ADD PRIMARY KEY (`loginHistoryID`),
  ADD KEY `employeeID` (`employeeID`);

--
-- Indeksy dla tabeli `orders`
--
ALTER TABLE `orders`
  ADD PRIMARY KEY (`OrderID`);

--
-- Indeksy dla tabeli `orderQuantity`
--
ALTER TABLE `orderQuantity`
  ADD PRIMARY KEY (`orderQuantityID`),
  ADD KEY `productID` (`productID`),
  ADD KEY `orderID` (`orderID`);

--
-- Indeksy dla tabeli `product`
--
ALTER TABLE `product`
  ADD PRIMARY KEY (`productID`);

--
-- Indeksy dla tabeli `result`
--
ALTER TABLE `result`
  ADD PRIMARY KEY (`resultID`);

--
-- Indeksy dla tabeli `role`
--
ALTER TABLE `role`
  ADD PRIMARY KEY (`roleID`);

--
-- Indeksy dla tabeli `task`
--
ALTER TABLE `task`
  ADD PRIMARY KEY (`taskID`),
  ADD KEY `taskCategory` (`taskCategory`),
  ADD KEY `resultID` (`resultID`),
  ADD KEY `productID` (`productID`),
  ADD KEY `orderID` (`orderID`),
  ADD KEY `zoneID` (`zoneID`);

--
-- Indeksy dla tabeli `taskCategory`
--
ALTER TABLE `taskCategory`
  ADD PRIMARY KEY (`taskCategoryID`);

--
-- Indeksy dla tabeli `taskCategoryComponent`
--
ALTER TABLE `taskCategoryComponent`
  ADD PRIMARY KEY (`taskCategoryID`,`componentID`),
  ADD KEY `componentID` (`componentID`);

--
-- Indeksy dla tabeli `taskCategoryLicense`
--
ALTER TABLE `taskCategoryLicense`
  ADD PRIMARY KEY (`taskCategoryID`,`licenseID`),
  ADD KEY `licenseID` (`licenseID`);

--
-- Indeksy dla tabeli `taskComponent`
--
ALTER TABLE `taskComponent`
  ADD PRIMARY KEY (`taskID`,`componentID`),
  ADD KEY `componentID` (`componentID`);

--
-- Indeksy dla tabeli `taskEquipment`
--
ALTER TABLE `taskEquipment`
  ADD PRIMARY KEY (`taskID`,`equipmentID`),
  ADD KEY `equipmentID` (`equipmentID`);

--
-- Indeksy dla tabeli `taskEquipmentCategory`
--
ALTER TABLE `taskEquipmentCategory`
  ADD PRIMARY KEY (`taskCategoryID`,`equipmentCategoryID`),
  ADD KEY `equipmentCategoryID` (`equipmentCategoryID`);

--
-- Indeksy dla tabeli `taskStatus`
--
ALTER TABLE `taskStatus`
  ADD PRIMARY KEY (`taskStatusID`),
  ADD KEY `taskID` (`taskID`),
  ADD KEY `employeeID` (`employeeID`);

--
-- Indeksy dla tabeli `zone`
--
ALTER TABLE `zone`
  ADD PRIMARY KEY (`zoneID`);

--
-- AUTO_INCREMENT for dumped tables
--

--
-- AUTO_INCREMENT for table `component`
--
ALTER TABLE `component`
  MODIFY `componentID` int(10) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT for table `employee`
--
ALTER TABLE `employee`
  MODIFY `employeeID` int(10) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=8;

--
-- AUTO_INCREMENT for table `equipment`
--
ALTER TABLE `equipment`
  MODIFY `equipmentID` int(10) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT for table `equipmentCategory`
--
ALTER TABLE `equipmentCategory`
  MODIFY `equipmentCategoryID` int(10) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT for table `license`
--
ALTER TABLE `license`
  MODIFY `licenseID` int(10) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT for table `loginHistory`
--
ALTER TABLE `loginHistory`
  MODIFY `loginHistoryID` int(10) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=19;

--
-- AUTO_INCREMENT for table `orders`
--
ALTER TABLE `orders`
  MODIFY `OrderID` int(10) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=6;

--
-- AUTO_INCREMENT for table `orderQuantity`
--
ALTER TABLE `orderQuantity`
  MODIFY `orderQuantityID` int(10) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=6;

--
-- AUTO_INCREMENT for table `product`
--
ALTER TABLE `product`
  MODIFY `productID` int(10) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=8;

--
-- AUTO_INCREMENT for table `result`
--
ALTER TABLE `result`
  MODIFY `resultID` int(10) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT for table `role`
--
ALTER TABLE `role`
  MODIFY `roleID` int(10) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=5;

--
-- AUTO_INCREMENT for table `task`
--
ALTER TABLE `task`
  MODIFY `taskID` int(10) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT for table `taskCategory`
--
ALTER TABLE `taskCategory`
  MODIFY `taskCategoryID` int(10) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=8;

--
-- AUTO_INCREMENT for table `taskStatus`
--
ALTER TABLE `taskStatus`
  MODIFY `taskStatusID` int(10) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT for table `zone`
--
ALTER TABLE `zone`
  MODIFY `zoneID` int(10) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=11;

--
-- Constraints for dumped tables
--

--
-- Constraints for table `employee`
--
ALTER TABLE `employee`
  ADD CONSTRAINT `employee_ibfk_1` FOREIGN KEY (`zoneID`) REFERENCES `zone` (`zoneID`),
  ADD CONSTRAINT `employee_ibfk_2` FOREIGN KEY (`roleID`) REFERENCES `role` (`roleID`);

--
-- Constraints for table `employeeLicense`
--
ALTER TABLE `employeeLicense`
  ADD CONSTRAINT `employeelicense_ibfk_1` FOREIGN KEY (`employeeID`) REFERENCES `employee` (`employeeID`),
  ADD CONSTRAINT `employeelicense_ibfk_2` FOREIGN KEY (`licenseID`) REFERENCES `license` (`licenseID`),
  ADD CONSTRAINT `fk_employee_license` FOREIGN KEY (`employeeID`) REFERENCES `employee` (`employeeID`);

--
-- Constraints for table `equipment`
--
ALTER TABLE `equipment`
  ADD CONSTRAINT `equipment_ibfk_1` FOREIGN KEY (`equipmentCategoryID`) REFERENCES `equipmentCategory` (`equipmentCategoryID`),
  ADD CONSTRAINT `equipment_ibfk_2` FOREIGN KEY (`zoneID`) REFERENCES `zone` (`zoneID`);

--
-- Constraints for table `equipmentCategoryLicense`
--
ALTER TABLE `equipmentCategoryLicense`
  ADD CONSTRAINT `equipmentcategorylicense_ibfk_1` FOREIGN KEY (`equipmentCategoryID`) REFERENCES `equipmentCategory` (`equipmentCategoryID`),
  ADD CONSTRAINT `equipmentcategorylicense_ibfk_2` FOREIGN KEY (`licenseID`) REFERENCES `license` (`licenseID`);

--
-- Constraints for table `loginHistory`
--
ALTER TABLE `loginHistory`
  ADD CONSTRAINT `loginhistory_ibfk_1` FOREIGN KEY (`employeeID`) REFERENCES `employee` (`employeeID`);

--
-- Constraints for table `orderQuantity`
--
ALTER TABLE `orderQuantity`
  ADD CONSTRAINT `orderquantity_ibfk_1` FOREIGN KEY (`productID`) REFERENCES `product` (`productID`),
  ADD CONSTRAINT `orderquantity_ibfk_2` FOREIGN KEY (`orderID`) REFERENCES `orders` (`OrderID`);

--
-- Constraints for table `task`
--
ALTER TABLE `task`
  ADD CONSTRAINT `task_ibfk_1` FOREIGN KEY (`taskCategory`) REFERENCES `taskCategory` (`taskCategoryID`),
  ADD CONSTRAINT `task_ibfk_2` FOREIGN KEY (`resultID`) REFERENCES `result` (`resultID`),
  ADD CONSTRAINT `task_ibfk_3` FOREIGN KEY (`productID`) REFERENCES `product` (`productID`),
  ADD CONSTRAINT `task_ibfk_4` FOREIGN KEY (`orderID`) REFERENCES `orderquantity` (`orderQuantityID`),
  ADD CONSTRAINT `task_ibfk_5` FOREIGN KEY (`zoneID`) REFERENCES `zone` (`zoneID`);

--
-- Constraints for table `taskCategoryComponent`
--
ALTER TABLE `taskCategoryComponent`
  ADD CONSTRAINT `taskcategorycomponent_ibfk_1` FOREIGN KEY (`taskCategoryID`) REFERENCES `taskCategory` (`taskCategoryID`),
  ADD CONSTRAINT `taskcategorycomponent_ibfk_2` FOREIGN KEY (`componentID`) REFERENCES `component` (`componentID`);

--
-- Constraints for table `taskCategoryLicense`
--
ALTER TABLE `taskCategoryLicense`
  ADD CONSTRAINT `taskcategorylicense_ibfk_1` FOREIGN KEY (`taskCategoryID`) REFERENCES `taskCategory` (`taskCategoryID`),
  ADD CONSTRAINT `taskcategorylicense_ibfk_2` FOREIGN KEY (`licenseID`) REFERENCES `license` (`licenseID`);

--
-- Constraints for table `taskComponent`
--
ALTER TABLE `taskComponent`
  ADD CONSTRAINT `taskcomponent_ibfk_1` FOREIGN KEY (`taskID`) REFERENCES `task` (`taskID`),
  ADD CONSTRAINT `taskcomponent_ibfk_2` FOREIGN KEY (`componentID`) REFERENCES `component` (`componentID`);

--
-- Constraints for table `taskEquipment`
--
ALTER TABLE `taskEquipment`
  ADD CONSTRAINT `taskequipment_ibfk_1` FOREIGN KEY (`taskID`) REFERENCES `task` (`taskID`),
  ADD CONSTRAINT `taskequipment_ibfk_2` FOREIGN KEY (`equipmentID`) REFERENCES `equipment` (`equipmentID`);

--
-- Constraints for table `taskEquipmentCategory`
--
ALTER TABLE `taskEquipmentCategory`
  ADD CONSTRAINT `taskequipmentcategory_ibfk_1` FOREIGN KEY (`taskCategoryID`) REFERENCES `taskCategory` (`taskCategoryID`),
  ADD CONSTRAINT `taskequipmentcategory_ibfk_2` FOREIGN KEY (`equipmentCategoryID`) REFERENCES `equipmentCategory` (`equipmentCategoryID`);

--
-- Constraints for table `taskStatus`
--
ALTER TABLE `taskStatus`
  ADD CONSTRAINT `taskstatus_ibfk_1` FOREIGN KEY (`taskID`) REFERENCES `task` (`taskID`),
  ADD CONSTRAINT `taskstatus_ibfk_2` FOREIGN KEY (`employeeID`) REFERENCES `employee` (`employeeID`);
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;


--
-- Dumping data for table `component`
--

INSERT INTO component (name, quantity) VALUES
('comp1', 10),
('comp2', 40),
('comp3', 100);


--
-- Dumping data for table `equipmentCategory`
--

INSERT INTO equipmentCategory (name) VALUES
('equipCat1'),
('equipCat2'),
('equipCat3');

--
-- Dumping data for table `equipment`
--

INSERT INTO equipment (name, equipmentCategoryID, status, zoneID) VALUES
('equip1', 1, 'in use', 1),
('equip2', 1, 'available', 1),
('equip3', 1, 'available', 2),
('equip4', 2, 'out of use', 3);



--
-- Dumping data for table `result`
--

insert into result (quantityOK, quantityNOK) values 
(9,1),
(4,1),
(2,0),
(1,0);

--
-- Dumping data for table `task`
--

INSERT INTO task (name, priority, description, taskCategory, norm, resultID, productID, quantity, orderID, zoneID) VALUES
('task1', 'normal', 'description1', 1, 10, 1, 1, 10, 1, 1),
('task2', 'normal', 'description2', 2, 5, 2, 2, 5, 1, 2), 
('task3', 'high', 'description3', 4, 2, 3, 4, 2, 1, 4), 
('task4', 'low', 'description4', 6, 1, 4, 6, 1, 1, 6);

--
-- Dumping data for table `taskComponent`
--

INSERT INTO taskcomponent (taskID, componentID, quantity) VALUES
(1,1,2),
(1,2,5),
(3,2,3),
(4,3,4),
(3,3,5),
(4,1,2),
(2,2,6);

--
-- Dumping data for table `taskEquipment`
--

INSERT INTO taskequipment (taskID, equipmentID) VALUES
(1,4),
(1,2),
(3,3),
(4,1),
(3,2),
(4,2),
(2,4);

--
-- Dumping data for table `taskStatus`
--

INSERT INTO taskstatus (taskID, employeeID, stepName, startStep, endStep) VALUES
(1,1, 'available', '2024-01-04 10:17:36', '2024-01-04 12:17:36'),
(4,1, 'available', '2024-01-04 10:17:36', '2024-01-04 12:17:36'),
(1,2, 'in progress', '2024-01-04 12:17:36', '2024-01-04 16:17:36'),
(1,2, 'finished', '2024-01-04 16:17:36', '0000-00-00 00:00:00'),
(2,1, 'available', '2024-01-04 12:18:36', '2024-01-04 12:20:36'),
(2,4, 'in progress', '2024-01-04 12:20:36', '2024-01-04 20:18:36'),
(2,4, 'finished', '2024-01-04 20:18:36', '0000-00-00 00:00:00');

INSERT INTO taskstatus (taskID, employeeID, stepName, startStep) VALUES
(4,3, 'in progress', '2024-01-04 12:17:36'),
(3,1, 'available', '2024-01-04 10:19:36');


--
-- Dumping data for table `license`
--
insert into license(name, description) VALUES
('license1', 'description1'),
('license2', 'description2'),
('license3', 'description3'),
('license4', 'for equip cat 1'),
('license5', 'for equip cat 2'),
('license6', 'for equip cat 3');

--
-- Dumping data for table `employeelicense`
--
insert into employeelicense VALUES
(2,1,'2022-01-04', '2025-01-04'),
(2,2,'2022-01-04', '2025-01-04'),
(2,3,'2022-01-04', '2025-01-04'),
(3,1,'2022-01-04', '2025-01-04'),
(3,2,'2022-01-04', '2025-01-04'),
(3,3,'2022-01-04', '2025-01-04'),
(2,4,'2022-01-04', '2025-01-04'),
(2,5,'2022-01-04', '2025-01-04'),
(2,6,'2022-01-04', '2025-01-04');


--
-- Dumping data for table `taskcategorylicense`
--
insert into taskcategorylicense VALUES
(1,1),
(2,1),
(3,1),
(4,1),
(4,2),
(5,1),
(7,3);

--
-- Dumping data for table `equipmentcategorylicense`
--
insert into equipmentcategorylicense VALUES
(1, 4),
(2, 5),
(3, 6);


--
-- Dumping data for table `equipmentcategorylicense`
--
insert into taskcategorycomponent VALUES
(1, 1),
(1, 2),
(2, 2),
(4, 2),
(4, 3),
(6, 1),
(6, 3);

--
-- Dumping data for table `taskequipmentcategory`
--
insert into taskequipmentcategory VALUES
(6, 1),
(1, 1),
(4, 1),
(1, 2),
(2, 2);