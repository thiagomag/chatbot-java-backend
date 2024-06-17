CREATE TABLE users_threads
(
    id serial PRIMARY KEY NOT NULL,
    user_id bigint NOT NULL,
    thread_id varchar(255) NOT NULL
);

ALTER TABLE public.users_threads ADD CONSTRAINT user_threads_users_fk FOREIGN KEY (user_id) REFERENCES users(id) ON DELETE CASCADE;
ALTER TABLE public.users_threads ADD CONSTRAINT user_threads_threads_fk FOREIGN KEY (thread_id) REFERENCES threads(thread_id) ON DELETE CASCADE;

DROP TABLE user_threads;