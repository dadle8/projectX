-- phpMyAdmin SQL Dump
-- version 4.5.5.1
-- http://www.phpmyadmin.net
--
-- Хост: 127.0.0.1
-- Время создания: Ноя 08 2016 г., 17:58
-- Версия сервера: 5.7.11
-- Версия PHP: 5.6.19

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- База данных: `projectx`
--

-- --------------------------------------------------------

--
-- Структура таблицы `geolocations`
--

CREATE TABLE `geolocations` (
  `id` int(11) NOT NULL,
  `geolocationX` float(10,6) NOT NULL,
  `geolocationY` float(10,6) NOT NULL,
  `dategeolocation` date NOT NULL,
  `iduser` int(11) DEFAULT NULL
) ENGINE=MyISAM DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Структура таблицы `messages`
--

CREATE TABLE `messages` (
  `id` int(11) NOT NULL,
  `message` varchar(1024) NOT NULL,
  `datemessage` date NOT NULL,
  `isread` tinyint(1) NOT NULL,
  `idfrom` int(11) NOT NULL,
  `idto` int(11) NOT NULL
) ENGINE=MyISAM DEFAULT CHARSET=latin1;

--
-- Дамп данных таблицы `messages`
--

INSERT INTO `messages` (`id`, `message`, `datemessage`, `isread`, `idfrom`, `idto`) VALUES
(1, 'xxx', '2016-11-08', 0, 1, 2),
(2, 'xxx', '2016-11-08', 0, 1, 2),
(3, 'xxx', '2016-11-08', 0, 1, 2),
(4, 'xxx', '2016-11-08', 0, 2, 1),
(5, 'xxx', '2016-11-08', 0, 2, 1),
(6, 'xxxqwd', '2016-11-08', 0, 2, 1),
(7, 'xxx', '2016-11-08', 0, 1, 2),
(8, 'aaa', '2016-11-08', 0, 2, 1),
(9, 'bbb', '2016-11-08', 0, 1, 2);

-- --------------------------------------------------------

--
-- Структура таблицы `user`
--

CREATE TABLE `user` (
  `id` int(11) NOT NULL,
  `login` varchar(32) NOT NULL,
  `password` varchar(32) NOT NULL,
  `email` varchar(32) DEFAULT NULL,
  `loggedIn` tinyint(1) NOT NULL DEFAULT '0',
  `sessionId` varchar(16) DEFAULT NULL
) ENGINE=MyISAM DEFAULT CHARSET=latin1;

--
-- Дамп данных таблицы `user`
--

INSERT INTO `user` (`id`, `login`, `password`, `email`, `loggedIn`, `sessionId`) VALUES
(1, 'root', '63a9f0ea7bb98050796b649e85481845', 'root@root.root', 0, NULL),
(2, 'admin', '63a9f0ea7bb98050796b649e85481845', 'admin@root.admin', 0, NULL);

--
-- Индексы сохранённых таблиц
--

--
-- Индексы таблицы `geolocations`
--
ALTER TABLE `geolocations`
  ADD PRIMARY KEY (`id`),
  ADD KEY `iduser` (`iduser`);

--
-- Индексы таблицы `messages`
--
ALTER TABLE `messages`
  ADD PRIMARY KEY (`id`),
  ADD KEY `idfrom` (`idfrom`),
  ADD KEY `idto` (`idto`);

--
-- Индексы таблицы `user`
--
ALTER TABLE `user`
  ADD PRIMARY KEY (`id`);

--
-- AUTO_INCREMENT для сохранённых таблиц
--

--
-- AUTO_INCREMENT для таблицы `geolocations`
--
ALTER TABLE `geolocations`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT;
--
-- AUTO_INCREMENT для таблицы `messages`
--
ALTER TABLE `messages`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=10;
--
-- AUTO_INCREMENT для таблицы `user`
--
ALTER TABLE `user`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=3;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
