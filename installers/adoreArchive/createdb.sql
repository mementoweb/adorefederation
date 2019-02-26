DROP DATABASE IF EXISTS arc_registry;
DROP DATABASE IF EXISTS tape_registry;
CREATE DATABASE arc_registry;
CREATE DATABASE tape_registry;
GRANT ALL PRIVILEGES ON arc_registry.* TO arc_registry@localhost IDENTIFIED BY 'password' WITH GRANT OPTION;
GRANT ALL PRIVILEGES ON tape_registry.* TO tape_registry@localhost IDENTIFIED BY 'password' WITH GRANT OPTION;