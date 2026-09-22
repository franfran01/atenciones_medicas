INSERT INTO PACIENTES (ID, RUT, NOMBRE, APELLIDO, FECHA_NACIMIENTO, TELEFONO, EMAIL) VALUES (1, '11.111.111-1', 'Maria', 'Gonzalez', '1985-03-12', '+56911111111', 'maria.gonzalez@correo.cl');
INSERT INTO PACIENTES (ID, RUT, NOMBRE, APELLIDO, FECHA_NACIMIENTO, TELEFONO, EMAIL) VALUES (2, '12.222.222-2', 'Pedro', 'Soto', '1990-07-21', '+56912222222', 'pedro.soto@correo.cl');
INSERT INTO PACIENTES (ID, RUT, NOMBRE, APELLIDO, FECHA_NACIMIENTO, TELEFONO, EMAIL) VALUES (3, '13.333.333-3', 'Lucia', 'Ramirez', '1978-11-04', '+56913333333', 'lucia.ramirez@correo.cl');
INSERT INTO PACIENTES (ID, RUT, NOMBRE, APELLIDO, FECHA_NACIMIENTO, TELEFONO, EMAIL) VALUES (4, '14.444.444-4', 'Andres', 'Vega', '2001-01-30', '+56914444444', 'andres.vega@correo.cl');
INSERT INTO PACIENTES (ID, RUT, NOMBRE, APELLIDO, FECHA_NACIMIENTO, TELEFONO, EMAIL) VALUES (5, '15.555.555-5', 'Camila', 'Fuentes', '1995-09-18', '+56915555555', 'camila.fuentes@correo.cl');
INSERT INTO PACIENTES (ID, RUT, NOMBRE, APELLIDO, FECHA_NACIMIENTO, TELEFONO, EMAIL) VALUES (6, '16.666.666-6', 'Jorge', 'Castillo', '1969-05-08', '+56916666666', 'jorge.castillo@correo.cl');
INSERT INTO PACIENTES (ID, RUT, NOMBRE, APELLIDO, FECHA_NACIMIENTO, TELEFONO, EMAIL) VALUES (7, '17.777.777-7', 'Sofia', 'Herrera', '1988-12-25', '+56917777777', 'sofia.herrera@correo.cl');
INSERT INTO PACIENTES (ID, RUT, NOMBRE, APELLIDO, FECHA_NACIMIENTO, TELEFONO, EMAIL) VALUES (8, '18.888.888-8', 'Nicolas', 'Paredes', '2010-04-02', '+56918888888', 'nicolas.paredes@correo.cl');

INSERT INTO CONSULTAS (ID, PACIENTE_ID, FECHA, MOTIVO, MEDICO, ESTADO) VALUES (1, 1, '2026-01-10', 'Control de presion arterial', 'Dra. Paula Rios', 'realizada');
INSERT INTO CONSULTAS (ID, PACIENTE_ID, FECHA, MOTIVO, MEDICO, ESTADO) VALUES (2, 1, '2026-03-02', 'Dolor de cabeza persistente', 'Dra. Paula Rios', 'realizada');
INSERT INTO CONSULTAS (ID, PACIENTE_ID, FECHA, MOTIVO, MEDICO, ESTADO) VALUES (3, 2, '2026-02-14', 'Chequeo anual', 'Dr. Tomas Bravo', 'realizada');
INSERT INTO CONSULTAS (ID, PACIENTE_ID, FECHA, MOTIVO, MEDICO, ESTADO) VALUES (4, 2, '2026-04-20', 'Resfriado y fiebre', 'Dr. Tomas Bravo', 'realizada');
INSERT INTO CONSULTAS (ID, PACIENTE_ID, FECHA, MOTIVO, MEDICO, ESTADO) VALUES (5, 3, '2026-01-28', 'Control de diabetes', 'Dra. Elena Mora', 'realizada');
INSERT INTO CONSULTAS (ID, PACIENTE_ID, FECHA, MOTIVO, MEDICO, ESTADO) VALUES (6, 4, '2026-05-06', 'Lesion en rodilla', 'Dr. Ignacio Pino', 'realizada');
INSERT INTO CONSULTAS (ID, PACIENTE_ID, FECHA, MOTIVO, MEDICO, ESTADO) VALUES (7, 5, '2026-06-11', 'Alergia estacional', 'Dra. Paula Rios', 'realizada');
INSERT INTO CONSULTAS (ID, PACIENTE_ID, FECHA, MOTIVO, MEDICO, ESTADO) VALUES (8, 6, '2026-07-03', 'Dolor lumbar', 'Dr. Ignacio Pino', 'realizada');

INSERT INTO ATENCIONES (ID, PACIENTE_ID, CONSULTA_ID, FECHA, DIAGNOSTICO, TRATAMIENTO, MEDICO) VALUES (1, 1, 1, '2026-01-10', 'Hipertension leve', 'Ajuste de dieta y control en 60 dias', 'Dra. Paula Rios');
INSERT INTO ATENCIONES (ID, PACIENTE_ID, CONSULTA_ID, FECHA, DIAGNOSTICO, TRATAMIENTO, MEDICO) VALUES (2, 1, 2, '2026-03-02', 'Migrafia tensional', 'Analgesico y pausas de descanso', 'Dra. Paula Rios');
INSERT INTO ATENCIONES (ID, PACIENTE_ID, CONSULTA_ID, FECHA, DIAGNOSTICO, TRATAMIENTO, MEDICO) VALUES (3, 2, 3, '2026-02-14', 'Paciente sano', 'Mantener actividad fisica y controles anuales', 'Dr. Tomas Bravo');
INSERT INTO ATENCIONES (ID, PACIENTE_ID, CONSULTA_ID, FECHA, DIAGNOSTICO, TRATAMIENTO, MEDICO) VALUES (4, 2, 4, '2026-04-20', 'Infeccion respiratoria viral', 'Hidratacion y reposo por 5 dias', 'Dr. Tomas Bravo');
INSERT INTO ATENCIONES (ID, PACIENTE_ID, CONSULTA_ID, FECHA, DIAGNOSTICO, TRATAMIENTO, MEDICO) VALUES (5, 3, 5, '2026-01-28', 'Diabetes tipo 2 controlada', 'Continuar metformina y control de glicemia', 'Dra. Elena Mora');
INSERT INTO ATENCIONES (ID, PACIENTE_ID, CONSULTA_ID, FECHA, DIAGNOSTICO, TRATAMIENTO, MEDICO) VALUES (6, 4, 6, '2026-05-06', 'Esguince de rodilla grado 1', 'Inmovilizacion relativa y kinesiologia', 'Dr. Ignacio Pino');
INSERT INTO ATENCIONES (ID, PACIENTE_ID, CONSULTA_ID, FECHA, DIAGNOSTICO, TRATAMIENTO, MEDICO) VALUES (7, 5, 7, '2026-06-11', 'Rinitis alergica', 'Antihistaminico y evitar alergenos', 'Dra. Paula Rios');
INSERT INTO ATENCIONES (ID, PACIENTE_ID, CONSULTA_ID, FECHA, DIAGNOSTICO, TRATAMIENTO, MEDICO) VALUES (8, 6, 8, '2026-07-03', 'Lumbago mecanico', 'Antiinflamatorio y ejercicios de core', 'Dr. Ignacio Pino');
