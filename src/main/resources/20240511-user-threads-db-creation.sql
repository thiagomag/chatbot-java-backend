CREATE TABLE user_threads
(
    id serial PRIMARY KEY NOT NULL,
    user_id bigint NOT NULL,
    thread_id bigint NOT NULL
);

ALTER TABLE public.user_threads ADD CONSTRAINT user_threads_users_fk FOREIGN KEY (user_id) REFERENCES users(id) ON DELETE CASCADE;
ALTER TABLE public.user_threads ADD CONSTRAINT user_threads_threads_fk FOREIGN KEY (thread_id) REFERENCES threads(id) ON DELETE CASCADE;

DROP TABLE user_threads;