
CREATE TABLE IF NOT EXISTS stackuser(
  id serial PRIMARY KEY,
  full_name VARCHAR(64) NOT NULL,
  password VARCHAR(64) NOT NULL,
  email VARCHAR(64) NOT NULL
);



CREATE TABLE IF NOT EXISTS question(
  id serial PRIMARY KEY,
  user_id INT NOT NULL REFERENCES stackuser(id),
  title VARCHAR(32) NOT NULL,
  text VARCHAR(64) NOT NULL,
  creation_date TIME

);


CREATE TABLE IF NOT EXISTS tag(
  id serial PRIMARY KEY,
  text VARCHAR(32) NOT NULL

);

CREATE TABLE IF NOT EXISTS question_tag(
  id serial PRIMARY KEY ,
  qid INT NOT NULL REFERENCES question(id),
  tid INT NOT NULL REFERENCES tag(id),
  UNIQUE(qid, tid)
);


--each answer is for a question
--each asnwer has author, text, creation date,

CREATE TABLE IF NOT EXISTS answer(
  id serial PRIMARY KEY ,
  question_id INT NOT NULL REFERENCES question(id),
  user_id INT NOT NULL REFERENCES stackuser(id),
  text VARCHAR(64) NOT NULL,
  creation_date TIME

);

CREATE TABLE IF NOT EXISTS vote_answer(
  id serial PRIMARY KEY,
  answer_id INT NOT NULL REFERENCES answer(id),
  user_id INT NOT NULL REFERENCES stackuser(id),
  vote_type VARCHAR(10) NOT NULL,
  UNIQUE(answer_id, user_id)
);

CREATE TABLE IF NOT EXISTS vote_question(
  id serial PRIMARY KEY,
  question_id INT NOT NULL REFERENCES question(id),
  user_id INT NOT NULL REFERENCES stackuser(id),
  vote_type VARCHAR(10) NOT NULL,
  UNIQUE(question_id, user_id)
);




--sa creezi pt fiecare voteAnswer un userId, sa stii daca vrea sa voteze aceeasi demaimulte ori
--sau poate ii ok asa, pt ca ai doar cate un voteAnswer per answer/question pt ca unique

--trebe sa bag si a cui ii voteAnswer-u, vezi diagrama din agenda BT


