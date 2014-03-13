hibernate-ogm-neo4j-example
===========================

Without JTA
===========

First Run
---------

Returned persons:8
0 
1 value_sequence_ogm - 2, id_sequence_ogm - hibernate_sequencessequence_namePerson, 
2 value_sequence_ogm - 3, id_sequence_ogm - hibernate_sequencessequence_nameItem, 
3 id - 1, lastName - Doe, _table - Person, firstName - John, 
4 id - 1, name - iPhone, _table - Item, 
5 id - 2, name - iPod, _table - Item, 
6 items_id - 2, Person_id - 1, 
7 items_id - 1, Person_id - 1, 

Second Run
----------

Returned persons:14
0 
1 value_sequence_ogm - 3, id_sequence_ogm - hibernate_sequencessequence_namePerson, 
2 value_sequence_ogm - 5, id_sequence_ogm - hibernate_sequencessequence_nameItem, 
3 id - 1, lastName - Doe, _table - Person, firstName - John, 
4 id - 1, name - iPhone, _table - Item, 
5 id - 2, name - iPod, _table - Item, 
6 items_id - 2, Person_id - 1, 
7 items_id - 1, Person_id - 1, 
8 id - 2, lastName - Doe, _table - Person, firstName - John, 
9 id - 3, name - iPhone, _table - Item, 
10 id - 4, name - iPod, _table - Item, 
11 items_id - 3, Person_id - 2, 
12 items_id - 4, Person_id - 2, 

With JTA
========

First Run
---------

Returned persons:8
0 
1 value_sequence_ogm - 2, id_sequence_ogm - hibernate_sequencessequence_namePerson, 
2 value_sequence_ogm - 3, id_sequence_ogm - hibernate_sequencessequence_nameItem, 

Second Run
----------

Returned persons:6
0 
1 value_sequence_ogm - 3, id_sequence_ogm - hibernate_sequencessequence_namePerson, 
2 value_sequence_ogm - 5, id_sequence_ogm - hibernate_sequencessequence_nameItem, 