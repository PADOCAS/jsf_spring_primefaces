CREATE DATABASE jsf_spring_primefaces WITH TEMPLATE = template0 ENCODING = 'UTF8' LOCALE = 'Portuguese_Brazil.1252';

ALTER DATABASE jsf_spring_primefaces OWNER TO ldsystems;

CREATE FUNCTION public.retira_acentos(text) RETURNS text
    LANGUAGE sql IMMUTABLE STRICT
    AS $_$ 
select 
translate($1,'áàâãäéèêëíìïóòôõöúùûüÁÀÂÃÄÉÈÊËÍÌÏÓÒÔÕÖÚÙÛÜçÇ', 
'aaaaaeeeeiiiooooouuuuAAAAAEEEEIIIOOOOOUUUUcC'); 
$_$;

ALTER FUNCTION public.retira_acentos(text) OWNER TO ldsystems;

SET default_tablespace = '';

SET default_table_access_method = heap;

CREATE TABLE public.cidade (
    codigo bigint NOT NULL,
    nome character varying(50) NOT NULL,
    versionnum integer,
    estado_id bigint NOT NULL
);


ALTER TABLE public.cidade OWNER TO ldsystems;

CREATE TABLE public.cidade_aud (
    codigo bigint NOT NULL,
    rev integer NOT NULL,
    revtype smallint,
    nome character varying(255),
    estado_id bigint
);


ALTER TABLE public.cidade_aud OWNER TO ldsystems;

CREATE SEQUENCE public.cidade_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.cidade_seq OWNER TO ldsystems;

CREATE TABLE public.entidade (
    codigo bigint NOT NULL,
    inativo boolean NOT NULL,
    login character varying(20) NOT NULL,
    senha character varying(150) NOT NULL,
    ultimoacesso timestamp without time zone,
    versionnum integer,
    nome character varying(50) NOT NULL,
    tipo character varying(1) NOT NULL,
    email character varying(100),
    cpf character varying(11) NOT NULL
);


ALTER TABLE public.entidade OWNER TO ldsystems;

CREATE TABLE public.entidade_aud (
    codigo bigint NOT NULL,
    rev integer NOT NULL,
    revtype smallint,
    inativo boolean,
    login character varying(255),
    nome character varying(255),
    senha character varying(255),
    tipo character varying(255),
    ultimoacesso timestamp without time zone,
    email character varying(255),
    cpf character varying(255)
);


ALTER TABLE public.entidade_aud OWNER TO ldsystems;

CREATE SEQUENCE public.entidade_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.entidade_seq OWNER TO ldsystems;

CREATE TABLE public.entidadeacesso (
    codigo bigint NOT NULL,
    esa_codigo character varying(70)
);


ALTER TABLE public.entidadeacesso OWNER TO ldsystems;

CREATE TABLE public.entidadeacesso_aud (
    rev integer NOT NULL,
    codigo bigint NOT NULL,
    esa_codigo character varying(20) NOT NULL,
    revtype smallint
);


ALTER TABLE public.entidadeacesso_aud OWNER TO ldsystems;

CREATE TABLE public.estado (
    codigo bigint NOT NULL,
    nome character varying(50) NOT NULL,
    siglauf character varying(2) NOT NULL,
    versionnum integer,
    pais_id bigint NOT NULL
);


ALTER TABLE public.estado OWNER TO ldsystems;

CREATE TABLE public.estado_aud (
    codigo bigint NOT NULL,
    rev integer NOT NULL,
    revtype smallint,
    nome character varying(255),
    siglauf character varying(255),
    pais_id bigint
);


ALTER TABLE public.estado_aud OWNER TO ldsystems;

CREATE SEQUENCE public.estado_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.estado_seq OWNER TO ldsystems;

CREATE SEQUENCE public.hibernate_sequence
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.hibernate_sequence OWNER TO ldsystems;

CREATE TABLE public.mensagem (
    codigo bigint NOT NULL,
    assunto character varying(80) NOT NULL,
    data_mensagem timestamp without time zone NOT NULL,
    descricao character varying(1000) NOT NULL,
    exigir_resposta boolean NOT NULL,
    lida boolean NOT NULL,
    versionnum integer,
    usuario_dest bigint NOT NULL,
    usuario_orig bigint NOT NULL,
    cod_mensagem_origem bigint
);


ALTER TABLE public.mensagem OWNER TO ldsystems;

CREATE TABLE public.mensagem_aud (
    codigo bigint NOT NULL,
    rev integer NOT NULL,
    revtype smallint,
    assunto character varying(255),
    data_mensagem timestamp without time zone,
    descricao character varying(1000),
    exigir_resposta boolean,
    lida boolean,
    usuario_dest bigint,
    usuario_orig bigint,
    mensagem_origem bigint,
    cod_mensagem_origem bigint
);


ALTER TABLE public.mensagem_aud OWNER TO ldsystems;

CREATE TABLE public.mensagem_resposta (
    codigo bigint NOT NULL,
    versionnum integer,
    cod_mensagem_pai bigint NOT NULL,
    cod_mensagem_resposta bigint NOT NULL
);


ALTER TABLE public.mensagem_resposta OWNER TO ldsystems;

CREATE TABLE public.mensagem_resposta_aud (
    codigo bigint NOT NULL,
    rev integer NOT NULL,
    revtype smallint,
    cod_mensagem_pai bigint,
    cod_mensagem_resposta bigint
);


ALTER TABLE public.mensagem_resposta_aud OWNER TO ldsystems;

CREATE SEQUENCE public.mensagem_resposta_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.mensagem_resposta_seq OWNER TO ldsystems;

CREATE SEQUENCE public.mensagem_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.mensagem_seq OWNER TO ldsystems;

CREATE TABLE public.pais (
    codigo bigint NOT NULL,
    nome character varying(50) NOT NULL,
    sigla character varying(15) NOT NULL,
    versionnum integer
);


ALTER TABLE public.pais OWNER TO ldsystems;

CREATE TABLE public.pais_aud (
    codigo bigint NOT NULL,
    rev integer NOT NULL,
    revtype smallint,
    nome character varying(255),
    sigla character varying(255)
);


ALTER TABLE public.pais_aud OWNER TO ldsystems;

CREATE SEQUENCE public.pais_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.pais_seq OWNER TO ldsystems;

CREATE TABLE public.revinfo (
    rev integer NOT NULL,
    revtstmp bigint
);


ALTER TABLE public.revinfo OWNER TO ldsystems;

CREATE TABLE public.titulo (
    codigo bigint NOT NULL,
    data date NOT NULL,
    pessoa character varying(100) NOT NULL,
    tipo character varying(1) NOT NULL,
    valor numeric(15,2) NOT NULL,
    versionnum integer,
    usuario_abertura bigint NOT NULL
);


ALTER TABLE public.titulo OWNER TO ldsystems;

CREATE TABLE public.titulo_aud (
    codigo bigint NOT NULL,
    rev integer NOT NULL,
    revtype smallint,
    data date,
    pessoa character varying(255),
    tipo character varying(255),
    valor numeric(15,2),
    usuario_abertura bigint
);


ALTER TABLE public.titulo_aud OWNER TO ldsystems;

CREATE SEQUENCE public.titulo_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.titulo_seq OWNER TO ldsystems;

CREATE TABLE public.version (
    installed_rank integer NOT NULL,
    version character varying(50),
    description character varying(200) NOT NULL,
    type character varying(20) NOT NULL,
    script character varying(1000) NOT NULL,
    checksum integer,
    installed_by character varying(100) NOT NULL,
    installed_on timestamp without time zone DEFAULT now() NOT NULL,
    execution_time integer NOT NULL,
    success boolean NOT NULL
);


ALTER TABLE public.version OWNER TO ldsystems;

ALTER TABLE ONLY public.cidade_aud
    ADD CONSTRAINT cidade_aud_pkey PRIMARY KEY (codigo, rev);

ALTER TABLE ONLY public.cidade
    ADD CONSTRAINT cidade_pkey PRIMARY KEY (codigo);


ALTER TABLE ONLY public.entidade_aud
    ADD CONSTRAINT entidade_aud_pkey PRIMARY KEY (codigo, rev);

ALTER TABLE ONLY public.entidade
    ADD CONSTRAINT entidade_ent_login_key UNIQUE (login);


ALTER TABLE ONLY public.entidade
    ADD CONSTRAINT entidade_pkey PRIMARY KEY (codigo);


ALTER TABLE ONLY public.entidadeacesso_aud
    ADD CONSTRAINT entidadeacesso_aud_pkey PRIMARY KEY (rev, codigo, esa_codigo);

ALTER TABLE ONLY public.estado_aud
    ADD CONSTRAINT estado_aud_pkey PRIMARY KEY (codigo, rev);

ALTER TABLE ONLY public.estado
    ADD CONSTRAINT estado_pkey PRIMARY KEY (codigo);

ALTER TABLE ONLY public.mensagem_aud
    ADD CONSTRAINT mensagem_aud_pkey PRIMARY KEY (codigo, rev);

ALTER TABLE ONLY public.mensagem
    ADD CONSTRAINT mensagem_pkey PRIMARY KEY (codigo);

ALTER TABLE ONLY public.mensagem_resposta_aud
    ADD CONSTRAINT mensagem_resposta_aud_pkey PRIMARY KEY (codigo, rev);

ALTER TABLE ONLY public.mensagem_resposta
    ADD CONSTRAINT mensagem_resposta_pkey PRIMARY KEY (codigo);

ALTER TABLE ONLY public.pais_aud
    ADD CONSTRAINT pais_aud_pkey PRIMARY KEY (codigo, rev);

ALTER TABLE ONLY public.pais
    ADD CONSTRAINT pais_pkey PRIMARY KEY (codigo);

ALTER TABLE ONLY public.revinfo
    ADD CONSTRAINT revinfo_pkey PRIMARY KEY (rev);

ALTER TABLE ONLY public.titulo_aud
    ADD CONSTRAINT titulo_aud_pkey PRIMARY KEY (codigo, rev);

ALTER TABLE ONLY public.titulo
    ADD CONSTRAINT titulo_pkey PRIMARY KEY (codigo);

ALTER TABLE ONLY public.entidade
    ADD CONSTRAINT uk_oesmc2oqfw4qgfwotuq5l1u8 UNIQUE (cpf);

ALTER TABLE ONLY public.entidadeacesso
    ADD CONSTRAINT unique_acesso_entidade_uk1 UNIQUE (codigo, esa_codigo);

ALTER TABLE ONLY public.version
    ADD CONSTRAINT version_pk PRIMARY KEY (installed_rank);

CREATE INDEX version_s_idx ON public.version USING btree (success);

CREATE INDEX xlogin ON public.entidade USING btree (login);

ALTER TABLE ONLY public.cidade
    ADD CONSTRAINT cidade_fk1 FOREIGN KEY (estado_id) REFERENCES public.estado(codigo);

ALTER TABLE ONLY public.estado
    ADD CONSTRAINT estado_fk1 FOREIGN KEY (pais_id) REFERENCES public.pais(codigo);

ALTER TABLE ONLY public.pais_aud
    ADD CONSTRAINT fk25jfyl152gc9evyelkbsqaftr FOREIGN KEY (rev) REFERENCES public.revinfo(rev);

ALTER TABLE ONLY public.mensagem_aud
    ADD CONSTRAINT fk3agrqgvd4hw6wunhhh5iolusv FOREIGN KEY (rev) REFERENCES public.revinfo(rev);

ALTER TABLE ONLY public.mensagem_resposta_aud
    ADD CONSTRAINT fk4l9ucckf1oc7sofravk4vck78 FOREIGN KEY (rev) REFERENCES public.revinfo(rev);

ALTER TABLE ONLY public.cidade_aud
    ADD CONSTRAINT fk7dovuf01yq30uup9t5gkk3u5i FOREIGN KEY (rev) REFERENCES public.revinfo(rev);

ALTER TABLE ONLY public.entidade_aud
    ADD CONSTRAINT fka1cw6ovx5dgw5y3ydiu26a0v9 FOREIGN KEY (rev) REFERENCES public.revinfo(rev);

ALTER TABLE ONLY public.entidadeacesso
    ADD CONSTRAINT fkdfab91482877d7f6 FOREIGN KEY (codigo) REFERENCES public.entidade(codigo);

ALTER TABLE ONLY public.estado_aud
    ADD CONSTRAINT fkjub1m477uuhlyhvp5kp48yxsw FOREIGN KEY (rev) REFERENCES public.revinfo(rev);

ALTER TABLE ONLY public.entidadeacesso_aud
    ADD CONSTRAINT fkjvthkqjk64y1y9am6x8w2aacm FOREIGN KEY (rev) REFERENCES public.revinfo(rev);

ALTER TABLE ONLY public.titulo_aud
    ADD CONSTRAINT fkreh4eft0chn5owro9vjoetun0 FOREIGN KEY (rev) REFERENCES public.revinfo(rev);

ALTER TABLE ONLY public.mensagem
    ADD CONSTRAINT mensagem_fk1 FOREIGN KEY (usuario_orig) REFERENCES public.entidade(codigo);

ALTER TABLE ONLY public.mensagem
    ADD CONSTRAINT mensagem_fk2 FOREIGN KEY (usuario_dest) REFERENCES public.entidade(codigo);

ALTER TABLE ONLY public.mensagem
    ADD CONSTRAINT mensagem_fk3 FOREIGN KEY (cod_mensagem_origem) REFERENCES public.mensagem(codigo);

ALTER TABLE ONLY public.mensagem_resposta
    ADD CONSTRAINT mensagem_resposta_fk1 FOREIGN KEY (cod_mensagem_pai) REFERENCES public.mensagem(codigo);

ALTER TABLE ONLY public.mensagem_resposta
    ADD CONSTRAINT mensagem_resposta_fk2 FOREIGN KEY (cod_mensagem_resposta) REFERENCES public.mensagem(codigo);

ALTER TABLE ONLY public.titulo
    ADD CONSTRAINT titulo_fk1 FOREIGN KEY (usuario_abertura) REFERENCES public.entidade(codigo);

-- Dados:
INSERT INTO public.pais (codigo, nome, sigla, versionnum) VALUES (1, 'BRASIL', 'BR', 1);
INSERT INTO public.entidade VALUES (1, false, 'admin', '$2a$10$B0Ugn8Q/r6LL8TcB5Do3DuOaAlXPSWDCfSkjXa79djUL57uWfrqbS', '2023-10-01 11:25:50.445339', 21, 'ADMINISTRADOR', 'A', '', '99289329050');
INSERT INTO public.entidadeacesso VALUES (1, 'ADMIN');
INSERT INTO public.entidadeacesso VALUES (1, 'USER');
INSERT INTO public.entidadeacesso VALUES (1, 'CIDADE_EDITAR');
INSERT INTO public.entidadeacesso VALUES (1, 'CIDADE_EXCLUIR');
INSERT INTO public.entidadeacesso VALUES (1, 'CADASTRO_ACESSAR');                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                             