CREATE TABLE `line_chart` (
  `graph_name` varchar(255) NOT NULL,
  `data_timestamp` bigint(13) NOT NULL,
  `data_value` DECIMAL(8,2) NOT NULL,
  PRIMARY KEY (`graph_name`, `data_timestamp`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;