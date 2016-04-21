/* TYPE = 1 if parent, 0 if student, 2 if actively using time
/* TIME is how many minutes the user has built up
/* TIMEUP is when the user has ran out of time */
CREATE TABLE USERS ( ID INTEGER PRIMARY KEY AUTOINCREMENT,
NAME TEXT NOT NULL, TYPE INTEGER NOT NULL, PIN INTEGER NULL,
GRADE TEXT NULL, TIME INTEGER NULL, TIMEUP INTEGER NULL)
