-- phpMyAdmin SQL Dump
-- version 5.2.1
-- https://www.phpmyadmin.net/
--
-- Host: 127.0.0.1
-- Generation Time: Apr 24, 2024 at 04:25 PM
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
-- Database: `szachy`
--

-- --------------------------------------------------------

--
-- Struktura tabeli dla tabeli `gracz`
--

CREATE TABLE `gracz` (
  `id` int(11) NOT NULL,
  `ranking` int(11) NOT NULL,
  `nickname` varchar(25) DEFAULT NULL,
  `login` varchar(25) NOT NULL,
  `haslo` varchar(25) DEFAULT NULL,
  `wygrane` int(11) NOT NULL DEFAULT 0,
  `przegrane` int(11) NOT NULL DEFAULT 0,
  `remisy` int(11) NOT NULL DEFAULT 0
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `gracz`
--

INSERT INTO `gracz` (`id`, `ranking`, `nickname`, `login`, `haslo`, `wygrane`, `przegrane`, `remisy`) VALUES
(1, 540, 'kacperl', 'kacper', 'legocki', 3, 2, 3),
(2, 540, 'adamb', 'adam', 'burny', 3, 0, 2),
(3, 480, 'mateuszb', 'mateusz', 'bartnik', 0, 1, 0),
(4, 500, 'stanislawm', 'stanislaw', 'makowski', 1, 2, 0),
(5, 490, 'kacperf', 'kacper', 'florczyk', 0, 1, 0),
(9, 500, 'gracznowy', 'gracz', 'nowy', 0, 0, 0),
(10, 500, 'tester', 'test', 'testowski', 0, 0, 0);

-- --------------------------------------------------------

--
-- Struktura tabeli dla tabeli `historia`
--

CREATE TABLE `historia` (
  `id` int(11) NOT NULL,
  `gracz_bialy` varchar(25) NOT NULL,
  `gracz_czarny` varchar(25) NOT NULL,
  `zwyciezca` varchar(25) NOT NULL,
  `wynik` text NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `historia`
--

INSERT INTO `historia` (`id`, `gracz_bialy`, `gracz_czarny`, `zwyciezca`, `wynik`) VALUES
(2, 'kacperl', 'stanislawm', 'stanislawm', 'Poddanie'),
(3, 'kacperl', 'kacperf', 'kacperl', 'Szachmat'),
(6, 'adamb', 'stanislawm', 'adamb', 'Szachmat'),
(7, 'stanislawm', 'mateuszb', 'NULL', 'REMIS'),
(8, 'stanislawm', 'mateuszb', 'stanislawm', 'Poddanie'),
(9, 'adamb', 'kacperl', 'kacperl', 'Poddanie'),
(10, 'adamb', 'kacperl', 'NULL', 'REMIS'),
(11, 'kacperl', 'adamb', 'adamb', 'KONIEC CZASU'),
(12, 'adamb', 'kacperl', 'NULL', 'REMIS'),
(14, 'kacperl', 'stanislawm', 'kacperl', 'Szachmat'),
(15, 'adamb', 'kacperf', 'NULL', 'REMIS'),
(19, 'gracznowy', 'adamb', 'gracznowy', 'Poddanie'),
(20, 'gracznowy', 'adamb', 'NULL', 'REMIS'),
(21, 'adamb', 'gracznowy', 'adamb', 'KONIEC CZASU');

--
-- Indeksy dla zrzutów tabel
--

--
-- Indeksy dla tabeli `gracz`
--
ALTER TABLE `gracz`
  ADD PRIMARY KEY (`id`),
  ADD UNIQUE KEY `haslo` (`haslo`),
  ADD UNIQUE KEY `nickname` (`nickname`);

--
-- Indeksy dla tabeli `historia`
--
ALTER TABLE `historia`
  ADD PRIMARY KEY (`id`);

--
-- AUTO_INCREMENT for dumped tables
--

--
-- AUTO_INCREMENT for table `gracz`
--
ALTER TABLE `gracz`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=11;

--
-- AUTO_INCREMENT for table `historia`
--
ALTER TABLE `historia`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=22;
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
