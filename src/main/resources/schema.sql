create table ip_pool (
	ip_pool_id INT AUTO_INCREMENT  PRIMARY KEY,
	description VARCHAR(250),
	total_capacity INT,
	used_capacity INT,
	lower_bound VARCHAR(200),
	upper_bound VARCHAR(200)
	);

create table ip_address (
	ip_address_id INT AUTO_INCREMENT  PRIMARY KEY,
	ip_pool_id INT,  
	value VARCHAR(200),
    FOREIGN KEY(ip_pool_id) REFERENCES ip_pool(ip_pool_id)
	);

