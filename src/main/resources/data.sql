/**
 * CREATE Script for init of DB
 */

-- Create 3 OFFLINE guests

INSERT INTO guests (id, checkIN, checkOUT, first_name, last_name, room_number)
VALUES (1, parsedatetime('10-12-2018 18:47:52.69', 'dd-MM-yyyy hh:mm:ss.SS'),
        parsedatetime('20-12-2018 18:47:52.69', 'dd-MM-yyyy hh:mm:ss.SS'), 'test',
        'test', '12');

INSERT INTO guests (id, checkIN, checkOUT, first_name, last_name, room_number)
VALUES (2, parsedatetime('17-09-2018 18:47:52.69', 'dd-MM-yyyy hh:mm:ss.SS'),
        parsedatetime('21-09-2018 18:47:52.69', 'dd-MM-yyyy hh:mm:ss.SS'), 'test',
        'test', '13');

INSERT INTO guests (id, checkIN, checkOUT, first_name, last_name, room_number)
VALUES (3, parsedatetime('17-01-2019 18:47:52.69', 'dd-MM-yyyy hh:mm:ss.SS'),
        parsedatetime('25-01-2019 18:47:52.69', 'dd-MM-yyyy hh:mm:ss.SS'), 'test',
        'test', '14');