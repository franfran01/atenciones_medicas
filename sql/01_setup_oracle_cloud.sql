-- PASO 1. En Database Actions, conectada como ADMIN.
-- Selecciona todo este archivo y usa "Ejecutar script" (F5), no el boton de una sola sentencia.

BEGIN
    EXECUTE IMMEDIATE 'DROP USER atenciones CASCADE';
EXCEPTION
    WHEN OTHERS THEN
        IF SQLCODE != -1918 THEN
            RAISE;
        END IF;
END;
/

CREATE USER atenciones IDENTIFIED BY "ClaveClinica26"
    DEFAULT TABLESPACE DATA
    QUOTA UNLIMITED ON DATA;

GRANT CREATE SESSION, CREATE TABLE, CREATE SEQUENCE TO atenciones;

BEGIN
    ords_admin.enable_schema(
        p_enabled => TRUE,
        p_schema => 'ATENCIONES',
        p_url_mapping_type => 'BASE_PATH',
        p_url_mapping_pattern => 'atenciones',
        p_auto_rest_auth => NULL
    );
    COMMIT;
END;
/
