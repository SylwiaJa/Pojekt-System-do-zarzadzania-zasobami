-- phpMyAdmin SQL Dump
-- version 5.2.1
-- https://www.phpmyadmin.net/
--
-- Host: localhost
-- Generation Time: Dec 31, 2023 at 07:26 PM
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
(1, 'John', 'Smith', 1, 1, 'johnSmith', '5e884898da28047151d0e56f8dc6292773603d0d6aabbdd62a11ef721d1542d8'),
(2, 'Alice', 'Johnson', 1, 2, 'aliJohn', 'b79b7a3ea501673ba27b00e8521749f12ef6a2e16d3bca1c5112bae8e9970a7e'),
(3, 'Michael', 'Davis', 1, 1, 'michDav', '92a0286daa0f1180b8e4db1755d6c7df7e72f07f03dbb21bf5c19e300ab2f4e9'),
(4, 'Emily', 'Clark', 3, 1, 'emilClar', '83b9947b198f8ed8ef8f53aa50f9f4494f8751ac2b36ecbe5f1e86686d7cbdc6'),
(5, 'Robert', 'Turner', 3, 2, 'robTurn', '897316d8d02c209d3a150ccbc141105c2adde12b1a86b15b39ed305de2f9b1e2'),
(6, 'Samantha', 'Miller', 4, 10, 'samantMil', 'f250e4e5b4b070d755a8e8a0ecf1e1b870538b5a1b7b1151305c6d6ec7846d0a'),
(7, 'Daniel', 'White', 2, 10, 'danWhit', '95e3c2d32fe55b102f640f6a3e64075f3b346785a46e5a12f7e62305e00a0d47');

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

-- --------------------------------------------------------

--
-- Struktura tabeli dla tabeli `order`
--

CREATE TABLE `order` (
  `orderID` int(10) NOT NULL,
  `productID` int(10) NOT NULL,
  `quantity` int(10) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- --------------------------------------------------------

--
-- Struktura tabeli dla tabeli `product`
--

CREATE TABLE `product` (
  `productID` int(10) NOT NULL,
  `name` varchar(20) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

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
  `resultID` int(10) NOT NULL,
  `productID` int(10) NOT NULL,
  `quantity` int(10) NOT NULL,
  `orderID` int(10) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- --------------------------------------------------------

--
-- Struktura tabeli dla tabeli `taskCategory`
--

CREATE TABLE `taskCategory` (
  `taskCategoryID` int(10) NOT NULL,
  `name` varchar(20) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

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
(1, 'SMT Assembly'),
(2, 'Wave Soldering'),
(3, 'Manual Assembly'),
(4, 'Electrical Testing'),
(5, 'Final Assembly'),
(6, 'Quality Control'),
(7, 'Packing'),
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
-- Indeksy dla tabeli `order`
--
ALTER TABLE `order`
  ADD PRIMARY KEY (`orderID`),
  ADD KEY `productID` (`productID`);

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
  ADD KEY `orderID` (`orderID`);

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
  MODIFY `loginHistoryID` int(10) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT for table `order`
--
ALTER TABLE `order`
  MODIFY `orderID` int(10) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT for table `product`
--
ALTER TABLE `product`
  MODIFY `productID` int(10) NOT NULL AUTO_INCREMENT;

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
  MODIFY `taskCategoryID` int(10) NOT NULL AUTO_INCREMENT;

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
-- Constraints for table `order`
--
ALTER TABLE `order`
  ADD CONSTRAINT `order_ibfk_1` FOREIGN KEY (`productID`) REFERENCES `product` (`productID`);

--
-- Constraints for table `task`
--
ALTER TABLE `task`
  ADD CONSTRAINT `task_ibfk_1` FOREIGN KEY (`taskCategory`) REFERENCES `taskCategory` (`taskCategoryID`),
  ADD CONSTRAINT `task_ibfk_2` FOREIGN KEY (`resultID`) REFERENCES `result` (`resultID`),
  ADD CONSTRAINT `task_ibfk_3` FOREIGN KEY (`productID`) REFERENCES `product` (`productID`),
  ADD CONSTRAINT `task_ibfk_4` FOREIGN KEY (`orderID`) REFERENCES `order` (`orderID`);

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
