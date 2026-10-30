--
-- PostgreSQL database dump
--

\restrict HFARVBivgZehnHlT1C4fqPSdzNvfeS9vmYEMMrpGJvvbk2HIMF7vwJoqInO7ZQI

-- Dumped from database version 16.14
-- Dumped by pg_dump version 16.14

SET statement_timeout = 0;
SET lock_timeout = 0;
SET idle_in_transaction_session_timeout = 0;
SET client_encoding = 'UTF8';
SET standard_conforming_strings = on;
SELECT pg_catalog.set_config('search_path', '', false);
SET check_function_bodies = false;
SET xmloption = content;
SET client_min_messages = warning;
SET row_security = off;

ALTER TABLE IF EXISTS ONLY public.booking_agent_route DROP CONSTRAINT IF EXISTS fksy0s02kksfss67gs814lyh5e1;
ALTER TABLE IF EXISTS ONLY public.booking_agent_route DROP CONSTRAINT IF EXISTS fkrs48ioyxsc1f62erpb1dxhxps;
ALTER TABLE IF EXISTS ONLY public.ancillary_purchase DROP CONSTRAINT IF EXISTS fkhjkc6rx17gv8v1nvqpbdp9ev7;
ALTER TABLE IF EXISTS ONLY public.booking_agent DROP CONSTRAINT IF EXISTS fkgbjl0unkketq703eqe6dchx2s;
ALTER TABLE IF EXISTS ONLY public.booking DROP CONSTRAINT IF EXISTS fkabxd6qpdfkp11yan46jj1td90;
ALTER TABLE IF EXISTS ONLY public.booking DROP CONSTRAINT IF EXISTS fk7feae1s8x4vh31y93glwjc2kq;
ALTER TABLE IF EXISTS ONLY public.passenger DROP CONSTRAINT IF EXISTS ukgtws4y8mwwlibp143cqcd31wy;
ALTER TABLE IF EXISTS ONLY public.sales_desk DROP CONSTRAINT IF EXISTS sales_desk_pkey;
ALTER TABLE IF EXISTS ONLY public.route DROP CONSTRAINT IF EXISTS route_pkey;
ALTER TABLE IF EXISTS ONLY public.passenger DROP CONSTRAINT IF EXISTS passenger_pkey;
ALTER TABLE IF EXISTS ONLY public.booking DROP CONSTRAINT IF EXISTS booking_pkey;
ALTER TABLE IF EXISTS ONLY public.booking_agent DROP CONSTRAINT IF EXISTS booking_agent_pkey;
ALTER TABLE IF EXISTS ONLY public.ancillary_purchase DROP CONSTRAINT IF EXISTS ancillary_purchase_pkey;
DROP TABLE IF EXISTS public.sales_desk;
DROP TABLE IF EXISTS public.route;
DROP TABLE IF EXISTS public.passenger;
DROP TABLE IF EXISTS public.booking_agent_route;
DROP TABLE IF EXISTS public.booking_agent;
DROP TABLE IF EXISTS public.booking;
DROP TABLE IF EXISTS public.ancillary_purchase;
SET default_tablespace = '';

SET default_table_access_method = heap;

--
-- Name: ancillary_purchase; Type: TABLE; Schema: public; Owner: -
--

CREATE TABLE public.ancillary_purchase (
    id uuid NOT NULL,
    item_name character varying(255),
    note character varying(255),
    purchase_date date,
    quantity integer NOT NULL,
    booking_id uuid
);


--
-- Name: booking; Type: TABLE; Schema: public; Owner: -
--

CREATE TABLE public.booking (
    id uuid NOT NULL,
    fare_class character varying(255),
    flight_number character varying(255),
    status character varying(255),
    travel_date date,
    agent_id uuid,
    passenger_id uuid,
    CONSTRAINT booking_status_check CHECK (((status)::text = ANY ((ARRAY['RESERVED'::character varying, 'PAID'::character varying, 'TICKETED'::character varying, 'CANCELLED'::character varying])::text[])))
);


--
-- Name: booking_agent; Type: TABLE; Schema: public; Owner: -
--

CREATE TABLE public.booking_agent (
    id uuid NOT NULL,
    email character varying(255),
    first_name character varying(255),
    last_name character varying(255),
    sales_desk_id uuid
);


--
-- Name: booking_agent_route; Type: TABLE; Schema: public; Owner: -
--

CREATE TABLE public.booking_agent_route (
    booking_agent_id uuid NOT NULL,
    route_id uuid NOT NULL
);


--
-- Name: passenger; Type: TABLE; Schema: public; Owner: -
--

CREATE TABLE public.passenger (
    id uuid NOT NULL,
    email character varying(255),
    first_name character varying(255),
    last_name character varying(255),
    passport_number character varying(255),
    phone character varying(255)
);


--
-- Name: route; Type: TABLE; Schema: public; Owner: -
--

CREATE TABLE public.route (
    id uuid NOT NULL,
    name character varying(255)
);


--
-- Name: sales_desk; Type: TABLE; Schema: public; Owner: -
--

CREATE TABLE public.sales_desk (
    id uuid NOT NULL,
    desk_number integer NOT NULL,
    name character varying(255)
);


--
-- Data for Name: ancillary_purchase; Type: TABLE DATA; Schema: public; Owner: -
--

COPY public.ancillary_purchase (id, item_name, note, purchase_date, quantity, booking_id) FROM stdin;
fdb690b8-429a-44df-a952-10ce66bcadb5	Extra baggage 23kg	Paid by mobile money	2026-10-30	1	aeeb8413-4ead-4eb9-8499-9e1aa1bcd9a5
81c0cba0-f597-4042-bd05-d3ef1e3ab6c1	Lounge access	Paid by card	2026-10-30	2	aeeb8413-4ead-4eb9-8499-9e1aa1bcd9a5
8e7c5590-ecec-48fb-a33e-bb8c4d5c4727	Extra baggage 23kg	Paid by card	2026-10-30	2	786f2a58-a8e3-43bd-beee-e53f6fd81f08
\.


--
-- Data for Name: booking; Type: TABLE DATA; Schema: public; Owner: -
--

COPY public.booking (id, fare_class, flight_number, status, travel_date, agent_id, passenger_id) FROM stdin;
aeeb8413-4ead-4eb9-8499-9e1aa1bcd9a5	Economy	WB201	TICKETED	2026-10-05	a96a9dd5-53a7-4352-ab02-9bda4b5c21c6	7be1dd14-280d-4d1d-89cc-c55830f12a77
786f2a58-a8e3-43bd-beee-e53f6fd81f08	Business	WB310	TICKETED	2026-10-12	bd0c5b66-6930-4c10-9bf8-ceb7143a56cf	7be1dd14-280d-4d1d-89cc-c55830f12a77
675cf269-61c6-4eb2-a078-fa5e03652d00	Economy	WB205	CANCELLED	2026-10-20	a96a9dd5-53a7-4352-ab02-9bda4b5c21c6	d39149eb-2620-4b42-9d66-794267362de2
d57c79ba-1172-4556-a49b-92e31f8e2a14	Economy	WB201	PAID	2026-10-20	a96a9dd5-53a7-4352-ab02-9bda4b5c21c6	8b993cf8-8cbf-423b-bdf3-86fefabc85f8
1ac1317b-7788-4aa2-8a9e-89b3c7110059	Economy	WB310	CANCELLED	2026-10-28	b1df6145-7b56-4f97-ab98-37445e6a9bb5	abcecda3-7961-42c8-9d06-e6d1b21e4bcb
39306efd-8095-4c84-b575-7d0014df8a7a	Business	WB205	RESERVED	2026-11-03	a96a9dd5-53a7-4352-ab02-9bda4b5c21c6	7be1dd14-280d-4d1d-89cc-c55830f12a77
def48baf-93b4-4962-99bc-83f95c437f5f	Economy	WB310	PAID	2026-11-10	bd0c5b66-6930-4c10-9bf8-ceb7143a56cf	8d25180a-2695-47d1-84e9-ae2741d8a42b
63bd9862-f14b-4c48-981d-f72c38caad4d	Economy	WB201	RESERVED	2026-11-15	b1df6145-7b56-4f97-ab98-37445e6a9bb5	d39149eb-2620-4b42-9d66-794267362de2
a6d45bb3-d39f-458d-95c3-696bc551c65e	Business	WB205	TICKETED	2026-11-22	b1df6145-7b56-4f97-ab98-37445e6a9bb5	8b993cf8-8cbf-423b-bdf3-86fefabc85f8
16d92915-046a-44e2-8a3d-d33d0789af4e	Economy	WB201	RESERVED	2026-11-28	a96a9dd5-53a7-4352-ab02-9bda4b5c21c6	abcecda3-7961-42c8-9d06-e6d1b21e4bcb
\.


--
-- Data for Name: booking_agent; Type: TABLE DATA; Schema: public; Owner: -
--

COPY public.booking_agent (id, email, first_name, last_name, sales_desk_id) FROM stdin;
a96a9dd5-53a7-4352-ab02-9bda4b5c21c6	claudine@belairline.rw	Claudine	Mukamana	81fbf142-4b74-448b-85c6-444e517b1a99
bd0c5b66-6930-4c10-9bf8-ceb7143a56cf	yves@belairline.rw	Yves	Niyonzima	7c0dff3b-4847-49c2-9a94-2842248ce826
b1df6145-7b56-4f97-ab98-37445e6a9bb5	sandrine@belairline.rw	Sandrine	Keza	9f4a137e-f285-443f-8f24-210193329bf4
\.


--
-- Data for Name: booking_agent_route; Type: TABLE DATA; Schema: public; Owner: -
--

COPY public.booking_agent_route (booking_agent_id, route_id) FROM stdin;
a96a9dd5-53a7-4352-ab02-9bda4b5c21c6	17172cd8-a9d7-4cf8-a09b-e71846a3dc73
a96a9dd5-53a7-4352-ab02-9bda4b5c21c6	22e74096-46d3-48af-a355-756612c9f1ff
bd0c5b66-6930-4c10-9bf8-ceb7143a56cf	89ac1c3a-8d75-4ac3-894f-237ea5e722cd
b1df6145-7b56-4f97-ab98-37445e6a9bb5	17172cd8-a9d7-4cf8-a09b-e71846a3dc73
b1df6145-7b56-4f97-ab98-37445e6a9bb5	22e74096-46d3-48af-a355-756612c9f1ff
b1df6145-7b56-4f97-ab98-37445e6a9bb5	89ac1c3a-8d75-4ac3-894f-237ea5e722cd
\.


--
-- Data for Name: passenger; Type: TABLE DATA; Schema: public; Owner: -
--

COPY public.passenger (id, email, first_name, last_name, passport_number, phone) FROM stdin;
7be1dd14-280d-4d1d-89cc-c55830f12a77	aline.ingabire@example.com	Aline	Ingabire	PC123456	+250788100001
d39149eb-2620-4b42-9d66-794267362de2	eric.ingabire@example.com	Eric	Ingabire	PC234567	+250788100002
8b993cf8-8cbf-423b-bdf3-86fefabc85f8	jean.mugisha@example.com	Jean	Mugisha	PC345678	+250788100003
abcecda3-7961-42c8-9d06-e6d1b21e4bcb	grace.uwase@example.com	Grace	Uwase	PC456789	+250788100004
8d25180a-2695-47d1-84e9-ae2741d8a42b	olivier.habimana@example.com	Olivier	Habimana	PC567890	+250788100005
\.


--
-- Data for Name: route; Type: TABLE DATA; Schema: public; Owner: -
--

COPY public.route (id, name) FROM stdin;
17172cd8-a9d7-4cf8-a09b-e71846a3dc73	Kigali - Nairobi
22e74096-46d3-48af-a355-756612c9f1ff	Kigali - Dubai
89ac1c3a-8d75-4ac3-894f-237ea5e722cd	Kigali - Brussels
80165a23-5447-4c34-8de1-da625113f48a	Kigali - Johannesburg
\.


--
-- Data for Name: sales_desk; Type: TABLE DATA; Schema: public; Owner: -
--

COPY public.sales_desk (id, desk_number, name) FROM stdin;
81fbf142-4b74-448b-85c6-444e517b1a99	1	Kigali Heights Desk
7c0dff3b-4847-49c2-9a94-2842248ce826	2	Airport Departures Desk
9f4a137e-f285-443f-8f24-210193329bf4	3	Town Centre Desk
df49ba2c-def6-4519-b851-e1bb02681be1	4	Airport Arrivals Desk
\.


--
-- Name: ancillary_purchase ancillary_purchase_pkey; Type: CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY public.ancillary_purchase
    ADD CONSTRAINT ancillary_purchase_pkey PRIMARY KEY (id);


--
-- Name: booking_agent booking_agent_pkey; Type: CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY public.booking_agent
    ADD CONSTRAINT booking_agent_pkey PRIMARY KEY (id);


--
-- Name: booking booking_pkey; Type: CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY public.booking
    ADD CONSTRAINT booking_pkey PRIMARY KEY (id);


--
-- Name: passenger passenger_pkey; Type: CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY public.passenger
    ADD CONSTRAINT passenger_pkey PRIMARY KEY (id);


--
-- Name: route route_pkey; Type: CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY public.route
    ADD CONSTRAINT route_pkey PRIMARY KEY (id);


--
-- Name: sales_desk sales_desk_pkey; Type: CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY public.sales_desk
    ADD CONSTRAINT sales_desk_pkey PRIMARY KEY (id);


--
-- Name: passenger ukgtws4y8mwwlibp143cqcd31wy; Type: CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY public.passenger
    ADD CONSTRAINT ukgtws4y8mwwlibp143cqcd31wy UNIQUE (passport_number);


--
-- Name: booking fk7feae1s8x4vh31y93glwjc2kq; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY public.booking
    ADD CONSTRAINT fk7feae1s8x4vh31y93glwjc2kq FOREIGN KEY (agent_id) REFERENCES public.booking_agent(id);


--
-- Name: booking fkabxd6qpdfkp11yan46jj1td90; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY public.booking
    ADD CONSTRAINT fkabxd6qpdfkp11yan46jj1td90 FOREIGN KEY (passenger_id) REFERENCES public.passenger(id);


--
-- Name: booking_agent fkgbjl0unkketq703eqe6dchx2s; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY public.booking_agent
    ADD CONSTRAINT fkgbjl0unkketq703eqe6dchx2s FOREIGN KEY (sales_desk_id) REFERENCES public.sales_desk(id);


--
-- Name: ancillary_purchase fkhjkc6rx17gv8v1nvqpbdp9ev7; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY public.ancillary_purchase
    ADD CONSTRAINT fkhjkc6rx17gv8v1nvqpbdp9ev7 FOREIGN KEY (booking_id) REFERENCES public.booking(id);


--
-- Name: booking_agent_route fkrs48ioyxsc1f62erpb1dxhxps; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY public.booking_agent_route
    ADD CONSTRAINT fkrs48ioyxsc1f62erpb1dxhxps FOREIGN KEY (booking_agent_id) REFERENCES public.booking_agent(id);


--
-- Name: booking_agent_route fksy0s02kksfss67gs814lyh5e1; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY public.booking_agent_route
    ADD CONSTRAINT fksy0s02kksfss67gs814lyh5e1 FOREIGN KEY (route_id) REFERENCES public.route(id);


--
-- PostgreSQL database dump complete
--

\unrestrict HFARVBivgZehnHlT1C4fqPSdzNvfeS9vmYEMMrpGJvvbk2HIMF7vwJoqInO7ZQI

